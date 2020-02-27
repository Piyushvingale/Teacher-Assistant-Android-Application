package co.raveblue.teacherassistant;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

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
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

public class ProfileEdit extends AppCompatActivity {

    private ImageButton mSelectImage;
    private EditText mPostName;
    private EditText mPostAddress;
    private EditText mPostContact;
    private EditText mPostEmail;

    private Button mSubmitBtn;

    private Uri mImageUri = null;

    private static final int GALLERY_REQUEST=1;

    private StorageReference mStorage;
    private DatabaseReference mDatabase;
    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        mStorage = FirebaseStorage.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Profile");

        mSelectImage = (ImageButton) findViewById(R.id.imageSelect);

        mPostName = (EditText) findViewById(R.id.nameField);
        mPostAddress = (EditText) findViewById(R.id.addressField);
        mPostContact = (EditText) findViewById(R.id.contactField);
        mPostEmail = (EditText) findViewById(R.id.emailField);

        mSubmitBtn = (Button) findViewById(R.id.submitBtn);

        mProgress = new ProgressDialog(this);

        mSelectImage.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){

                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GALLERY_REQUEST);

            }
        });

        mSubmitBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                startPosting();

            }
        });

    }


    private void startPosting() {

        mProgress.setMessage("Saving ....");

        final String name_val = mPostName.getText().toString().trim();
        final String address_val = mPostAddress.getText().toString().trim();
        final String contact_val = mPostContact.getText().toString().trim();
        final String email_val = mPostEmail.getText().toString().trim();

        if (!TextUtils.isEmpty(name_val)){

            mProgress.show();

            StorageReference filepath = mStorage.child("Profile_Images").child(mImageUri.getLastPathSegment());

            filepath.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    mStorage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            final Uri downloadUrl = uri;

                            final DatabaseReference newPost = mDatabase.push();

                            mDatabase.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {


                                    newPost.child("name").setValue(name_val);
                                    newPost.child("address").setValue(address_val);
                                    newPost.child("contact").setValue(contact_val);
                                    newPost.child("email").setValue(email_val);
                                    newPost.child("image").setValue(downloadUrl.toString());
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                            mProgress.dismiss();

                        }
                    });

                }
            });

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK){

            mImageUri = data.getData();

            CropImage.activity(mImageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1, 1)
                    .start(this);

            mSelectImage.setImageURI(mImageUri);

        }

    }

}
