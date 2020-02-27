package co.raveblue.teacherassistant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ProfileAdd extends AppCompatActivity {

    private DatabaseReference mDatabase;

    private TextView mProfileSingleName;
    private TextView mProfileSingleAddress;
    private TextView mProfileSingleContact;
    private TextView mProfileSingleEmail;
    private ImageView mProfileSingleImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_add);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Profile/-L3TeZwZ4v6B57h5iXWG");

        mProfileSingleAddress = (TextView) findViewById(R.id.singleProfileAddress);
        mProfileSingleName = (TextView) findViewById(R.id.singleProfileName);
        mProfileSingleContact = (TextView) findViewById(R.id.singleProfileContact);
        mProfileSingleEmail = (TextView) findViewById(R.id.singleProfileEmail);
        mProfileSingleImage = (ImageView) findViewById(R.id.singleImageField);


        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String post_name = (String) dataSnapshot.child("name").getValue();
                String post_address = (String) dataSnapshot.child("address").getValue();
                String post_contact = (String) dataSnapshot.child("contact").getValue();
                String post_email = (String) dataSnapshot.child("email").getValue();
                String post_image = (String) dataSnapshot.child("image").getValue();

                mProfileSingleName.setText(post_name);
                mProfileSingleAddress.setText(post_address);
                mProfileSingleContact.setText(post_contact);
                mProfileSingleEmail.setText(post_email);
                Picasso.with(ProfileAdd.this).load(post_image).into(mProfileSingleImage);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.edit_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_editprofile){

            Intent intent = new Intent(ProfileAdd.this, ProfileEdit.class);
            startActivity(intent);

        }

        return super.onOptionsItemSelected(item);
    }

}
