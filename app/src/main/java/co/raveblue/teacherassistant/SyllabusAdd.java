package co.raveblue.teacherassistant;

//import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bignerdranch.android.multiselector.MultiSelector;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SyllabusAdd extends AppCompatActivity {

    private Button mSelectBtn;
    private Button mPauseBtn;
    private Button mCancelBtn;

    private ProgressBar mProgress;
    private final static int FILE_SELECT_CODE = 1;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabase;

    private TextView mSizeLabel;
//    private TextView mProgressLabel;

    private TextView mFilenameLabel;

    private StorageTask mStorageTask;

    private String filename;
    private String saveday;
    private String savedate;
    private int sd;

    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;
    private DatabaseReference mDatabaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_syllabus_add);

        mSelectBtn = (Button) findViewById(R.id.select_btn);
        mPauseBtn = (Button) findViewById(R.id.pause_btn);
        mCancelBtn = (Button) findViewById(R.id.cancel_btn);

        mFilenameLabel = (TextView) findViewById(R.id.filename_label);

        mSizeLabel = (TextView) findViewById(R.id.size_label);
//        mProgressLabel = (TextView) findViewById(R.id.progress_label);

        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();

        mStorageRef = FirebaseStorage.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Syllabus");

        mDatabaseUser = FirebaseDatabase.getInstance().getReference().child("Users").child(mCurrentUser.getUid());

        mProgress = (ProgressBar) findViewById(R.id.upload_progress);

        mSelectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openFileSelector();

            }
        });

        mPauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String btnText = mPauseBtn.getText().toString();

                if (btnText.equals("Pause Upload")) {

                    mStorageTask.pause();
                    mPauseBtn.setText("Resume Upload");

                }
                else {

                    mStorageTask.resume();
                    mPauseBtn.setText("Pause Upload");

                }

            }
        });

        mCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mStorageTask.cancel();

            }
        });

    }

    private void openFileSelector() {

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(
                    Intent.createChooser(intent, "Select s File to Upload"),
                    FILE_SELECT_CODE);
        }catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "Please Install File Manager",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == FILE_SELECT_CODE && resultCode == RESULT_OK){

            Uri fileUri = data.getData();

            Toast.makeText(this, "You have selected a file", Toast.LENGTH_SHORT).show();
            String uriString = fileUri.toString();

            File myFile = new File(uriString);
//            String path = myFile.getAbsolutePath();

            String displayName = null;

            if (uriString.startsWith("content://")){

                Cursor cursor = null;
                try {
                    cursor = SyllabusAdd.this.getContentResolver().query(fileUri,null,null,null,null);
                    if (cursor != null && cursor.moveToFirst()){
                        displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                    }
                } finally {
                    cursor.close();
                }

            }else if (uriString.startsWith("file://")){
                displayName = myFile.getName();
            }

            mFilenameLabel.setText(displayName);
            filename = displayName;


            StorageReference riversRef = mStorageRef.child("files/"+ displayName);

            mStorageTask = riversRef.putFile(fileUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // Get a URL to the uploaded content
                            mStorageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    final Uri downloadUrl = uri;

                                    final DatabaseReference newPost = mDatabase.push();

                                    //save day as mon, tue
                                    Calendar calendar = Calendar.getInstance();
                                    Date date = calendar.getTime();
                                    saveday = new SimpleDateFormat("EE", Locale.ENGLISH).format(date.getTime());

                                    //save date as 1, 23
                                    sd = calendar.get(Calendar.DAY_OF_MONTH);
                                    savedate = String.valueOf(sd);

                                    //save data to firebase
                                    mDatabaseUser.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {

                                            assert downloadUrl != null;
                                            newPost.child("url").setValue(downloadUrl.toString());
                                            newPost.child("filename").setValue(filename);
                                            newPost.child("day").setValue(saveday);
                                            newPost.child("date").setValue(savedate);
                                            newPost.child("uid").setValue(mCurrentUser.getUid());
                                            newPost.child("username").setValue(dataSnapshot.child("name").getValue()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {

                                                    if (task.isSuccessful()) {

                                                        Toast.makeText(SyllabusAdd.this, "File Uploaded !", Toast.LENGTH_SHORT).show();

                                                        Intent intent = new Intent(SyllabusAdd.this, Syllabus.class);
                                                        startActivity(intent);

                                                    }
                                                }
                                            });
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });

                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads

                            Toast.makeText(SyllabusAdd.this, "Uploading Error !", Toast.LENGTH_SHORT).show();

                            // ...
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                    double progress = (100.0 * taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();

                    mProgress.setProgress((int) progress);

                    String progressText = taskSnapshot.getBytesTransferred()/(1024 * 1024) + " / " + taskSnapshot.getTotalByteCount()/(1024 * 1024) + "mb";

                    mSizeLabel.setText(progressText);
                }
            });

        }

        super.onActivityResult(requestCode, resultCode, data);

    }


}
