package co.raveblue.teacherassistant;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

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
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddStudentInfo extends AppCompatActivity {

    private EditText mPostName;
    private EditText mPostContact;
    private EditText mPostAddress;
    private EditText mPostEmail;
    private EditText mPostRoll;
    private Spinner classes;
    private Button mPostBtn;

    private ImageButton mSelectImage;
    private Uri mImageUri = null;
    private static final int GALLERY_REQUEST=1;

    private DatabaseReference mDatabase;
    private ProgressDialog mProgress;
    private StorageReference mStorage;

    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;
    private DatabaseReference mDatabaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student_info);
        mPostName = (EditText)findViewById(R.id.nameField);
        mPostContact = (EditText)findViewById(R.id.contactField);
        mPostAddress = (EditText)findViewById(R.id.addressField);
        mPostEmail = (EditText)findViewById(R.id.emailField);
        mPostRoll = (EditText)findViewById(R.id.rollField);
        classes = (Spinner)findViewById(R.id.spinner);

        mSelectImage = (ImageButton) findViewById(R.id.imageSelect);

        mPostBtn = (Button)findViewById(R.id.courseBtn);
        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();

        mStorage = FirebaseStorage.getInstance().getReference();

        mDatabase= FirebaseDatabase.getInstance().getReference().child("Student_Info");

        mDatabaseUser = FirebaseDatabase.getInstance().getReference().child("Users").child(mCurrentUser.getUid());

        mProgress = new ProgressDialog(this);

        mSelectImage.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){

                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GALLERY_REQUEST);

            }
        });

        mPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startPosting();

            }
        });

    }

    private void startPosting() {

        mProgress.setMessage("Saving ....");

        final String name_val = mPostName.getText().toString().trim();
        final String contact_val = mPostContact.getText().toString().trim();
        final String address_val = mPostAddress.getText().toString().trim();
        final String email_val = mPostEmail.getText().toString().trim();
        final String roll_val = mPostRoll.getText().toString().trim();
        final String classname = classes.getSelectedItem().toString();

        if (!TextUtils.isEmpty(name_val)){

            mProgress.show();

            StorageReference filepath = mStorage.child("Profile_Images").child(mImageUri.getLastPathSegment());

            filepath.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    mStorage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            final Uri downloadUrl =  uri;

                            final DatabaseReference newPost = mDatabase.push();
                            mDatabaseUser.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                String id = newPost.push().getKey();
                                assert downloadUrl != null;
                                newPost.child("image").setValue(downloadUrl.toString());
                                newPost.child("st_id").setValue(id);
                                newPost.child("st_name").setValue(name_val);
                                newPost.child("st_contact").setValue(contact_val);
                                newPost.child("st_address").setValue(address_val);
                                newPost.child("st_email").setValue(email_val);
                                newPost.child("st_roll").setValue(roll_val);
                                newPost.child("st_class").setValue(classname);
                                newPost.child("marks1").setValue("0");
                                newPost.child("marks2").setValue("0");
                                newPost.child("attendance").setValue("1");
                                newPost.child("totallecture").setValue("1");
                                newPost.child("overallper").setValue("0");
                                newPost.child("actpart").setValue("0");
                                newPost.child("uid").setValue(mCurrentUser.getUid());
                                newPost.child("username").setValue(dataSnapshot.child("name").getValue()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                       if (task.isSuccessful()) {

                                           startActivity(new Intent(AddStudentInfo.this, AddStudentDetails.class));

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

                    mProgress.dismiss();

                }
            });

        }

    }

    public void startImport(View view) {

        Intent intent = new Intent(AddStudentInfo.this, ReadExcel.class);
        startActivity(intent);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK){

            mImageUri = data.getData();

            mSelectImage.setImageURI(mImageUri);

        }

    }
}




