package co.raveblue.teacherassistant;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
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
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.apache.poi.ss.formula.functions.T;

public class EditStudentInfo extends AppCompatActivity {

    private String mPost_key = null;

    private DatabaseReference mDatabase;

    private TextView mStudentSingleName;
    private TextView mStudentSingleAddress;
    private TextView mStudentSingleContact;
    private TextView mStudentSingleEmail;
    private TextView mStudentSingleRoll;
    private TextView mStudentSingleCourse;
    private ImageButton mStudentSingleImage;

    private Button mSubmitBtn;

    private Uri mImageUri = null;
    private static final int GALLERY_REQUEST=1;

    private ProgressDialog mProgress;
    private StorageReference mStorage;

    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;
    private DatabaseReference mDatabaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_student_info);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Student_Info");

        mPost_key = getIntent().getExtras().getString("student_id");

        mStudentSingleAddress = (TextView) findViewById(R.id.editAddressField);
        mStudentSingleName = (TextView) findViewById(R.id.editNameField);
        mStudentSingleContact = (TextView) findViewById(R.id.editContactField);
        mStudentSingleEmail = (TextView) findViewById(R.id.editEmailField);
        mStudentSingleRoll = (TextView)findViewById(R.id.editRollField);
        mStudentSingleCourse = (TextView)findViewById(R.id.editCourseField);
        mStudentSingleImage = (ImageButton)findViewById(R.id.imageSelect);

        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();
        mDatabaseUser = FirebaseDatabase.getInstance().getReference().child("Users").child(mCurrentUser.getUid());

        mStorage = FirebaseStorage.getInstance().getReference();

        mSubmitBtn = (Button) findViewById(R.id.submitBtn);

        mStudentSingleImage.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){

                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GALLERY_REQUEST);

            }
        });

        mProgress = new ProgressDialog(this);

        mDatabase.child(mPost_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String post_name = (String) dataSnapshot.child("st_name").getValue();
                String post_address = (String) dataSnapshot.child("st_address").getValue();
                String post_contact = (String) dataSnapshot.child("st_contact").getValue();
                String post_email = (String) dataSnapshot.child("st_email").getValue();
                String post_roll = (String) dataSnapshot.child("st_roll").getValue();
                String post_class = (String) dataSnapshot.child("st_class").getValue();
                String post_image = (String) dataSnapshot.child("image").getValue();

                mStudentSingleName.setText(post_name);
                mStudentSingleAddress.setText(post_address);
                mStudentSingleContact.setText(post_contact);
                mStudentSingleEmail.setText(post_email);
                mStudentSingleRoll.setText(post_roll);
                mStudentSingleCourse.setText(post_class);
                Picasso.with(EditStudentInfo.this).load(post_image).into(mStudentSingleImage);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startPosting();
                Intent mainIntent = new Intent(EditStudentInfo.this, AddStudentDetails.class);
                startActivity(mainIntent);

            }
        });

    }

    private void startPosting() {

        mProgress.setMessage("Saving ....");

        final String name_val = mStudentSingleName.getText().toString().trim();
        final String contact_val = mStudentSingleContact.getText().toString().trim();
        final String address_val = mStudentSingleAddress.getText().toString().trim();
        final String email_val = mStudentSingleEmail.getText().toString().trim();
        final String roll_val = mStudentSingleRoll.getText().toString().trim();


            mProgress.show();

            StorageReference filepath = mStorage.child("Profile_Images").child(mImageUri.getLastPathSegment());

            filepath.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    mStorage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            DatabaseReference newPost = FirebaseDatabase.getInstance().getReference("Student_Info").child(mPost_key);

                    String id = newPost.push().getKey();
                    newPost.child("st_id").setValue(id);
                    newPost.child("st_name").setValue(name_val);
                    newPost.child("st_contact").setValue(contact_val);
                    newPost.child("st_address").setValue(address_val);
                    newPost.child("st_email").setValue(email_val);
                    newPost.child("st_roll").setValue(roll_val);
                    newPost.child("image").setValue(uri.toString());

                        }
                    });

                }
            });




            mProgress.dismiss();


        startActivity(new Intent(EditStudentInfo.this, AddStudentDetails.class));


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK){

            mImageUri = data.getData();

            mStudentSingleImage.setImageURI(mImageUri);

        }

    }

}
