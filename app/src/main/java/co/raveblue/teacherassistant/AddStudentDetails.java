package co.raveblue.teacherassistant;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import static co.raveblue.teacherassistant.R.id.post_course;
import static co.raveblue.teacherassistant.R.id.profileAddress;
import static co.raveblue.teacherassistant.R.id.profileImg;

public class AddStudentDetails extends AppCompatActivity {


    private RecyclerView mStudentList;
    private EditText mSearchField;
    private ImageButton mSearchBtn;

    private DatabaseReference mDatabase;

    private DatabaseReference mDatabaseUsers;
    private DatabaseReference mDatabaseCurrentUser;

    private Query mQueryCurrentUser;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    Dialog myDailog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student_details);

        myDailog = new Dialog(this);
        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth){

                if(firebaseAuth.getCurrentUser() == null){

                    Intent loginIntent  = new Intent(AddStudentDetails.this, LoginActivity.class);
                    loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(loginIntent);

                }

            }
        };

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Student_Info");

        mDatabaseUsers = FirebaseDatabase.getInstance().getReference().child("Users");

        String currentUserId = mAuth.getCurrentUser().getUid();

        mDatabaseCurrentUser = FirebaseDatabase.getInstance().getReference().child("Student_Info");

        mQueryCurrentUser = mDatabaseCurrentUser.orderByChild("uid").equalTo(currentUserId);

        mDatabaseUsers.keepSynced(true);
        mDatabase.keepSynced(true);

        mSearchField = (EditText)findViewById(R.id.search_field);
        mSearchBtn = (ImageButton)findViewById(R.id.search_btn);

        mStudentList = (RecyclerView)findViewById(R.id.student_list);
        mStudentList.setHasFixedSize(true);
        mStudentList.setLayoutManager(new LinearLayoutManager(this));

        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String searchText = mSearchField.getText().toString();
                firebaseStudentSearch(searchText);

            }

            private void firebaseStudentSearch(String searchText) {

                mAuth.addAuthStateListener(mAuthListener);

                Query firebaseSearchQuery = mDatabase.orderByChild("st_name").startAt(searchText).endAt(searchText + "\uf8ff");

                final FirebaseRecyclerAdapter<Student, AddStudentDetails.StudentViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Student, AddStudentDetails.StudentViewHolder>(

                        Student.class,
                        R.layout.student_row,
                        AddStudentDetails.StudentViewHolder.class,
                        firebaseSearchQuery

                ) {
                    @Override
                    protected void populateViewHolder(AddStudentDetails.StudentViewHolder viewHolder, final Student model, int position) {

                        final String post_key = getRef(position).getKey();

                        viewHolder.setSt_name(model.getSt_name());
                        viewHolder.setSt_class(model.getSt_class());
                        viewHolder.setImage(getApplication(), model.getImage());

                        viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

//                                Intent singleStudentIntent = new Intent(AddStudentDetails.this, SingleStudent.class);
  //                              singleStudentIntent.putExtra("student_id",post_key);
    //                            startActivity(singleStudentIntent);

                            }
                        });

                        viewHolder.editProfileButton.setOnClickListener(new View.OnClickListener(){

                            @Override
                            public void onClick(View view) {

                                Intent singleStudentIntent = new Intent(AddStudentDetails.this, EditStudentInfo.class);
                                singleStudentIntent.putExtra("student_id",post_key);
                                startActivity(singleStudentIntent);
                                
                            }
                        });


                        viewHolder.deleteProfileButton.setOnClickListener(new View.OnClickListener(){

                            @Override
                            public void onClick(View view) {

                                mDatabase.child(post_key).removeValue();

                            }
                        });


                        viewHolder.openProfileButton.setOnClickListener(new View.OnClickListener(){

                            @Override
                            public void onClick(View view) {

//                                Intent singleStudentIntent = new Intent(AddStudentDetails.this, SingleStudent.class);
  //                              singleStudentIntent.putExtra("student_id",post_key);
    //                            startActivity(singleStudentIntent);


                                TextView closeBtn;
                                final TextView studentName;
                                final TextView studentCourse;
                                final ImageView profileImg;
                                final TextView profileRoll;
                                final TextView profileContact;
                                final TextView profileEmail;
                                final TextView profileAddress;

                                myDailog.setContentView(R.layout.student_profile_popup);
                                closeBtn = (TextView) myDailog.findViewById(R.id.closeBtn);
                                profileImg = (ImageView) myDailog.findViewById(R.id.profileImg);
                                studentCourse = (TextView) myDailog.findViewById(R.id.studentCourse);
                                studentName = (TextView) myDailog.findViewById(R.id.studentName);
                                profileContact = (TextView) myDailog.findViewById(R.id.profileContact);
                                profileRoll = (TextView) myDailog.findViewById(R.id.profileRoll);
                                profileEmail = (TextView) myDailog.findViewById(R.id.profileEmail);
                                profileAddress = (TextView) myDailog.findViewById(R.id.profileAddress);

                                mDatabase.child(post_key).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {

                                        String post_name = (String) dataSnapshot.child("st_name").getValue();
                                        String post_address = (String) dataSnapshot.child("st_address").getValue();
                                        String post_contact = (String) dataSnapshot.child("st_contact").getValue();
                                        String post_email = (String) dataSnapshot.child("st_email").getValue();
                                        String post_roll = (String) dataSnapshot.child("st_roll").getValue();
                                        String post_class = (String) dataSnapshot.child("st_class").getValue();
                                        String post_image = (String) dataSnapshot.child("image").getValue();

                                        studentName.setText(post_name);
                                        profileAddress.setText(post_address);
                                        profileContact.setText(post_contact);
                                        profileEmail.setText(post_email);
                                        profileRoll.setText(post_roll);
                                        studentCourse.setText(post_class);
                                        Picasso.with(AddStudentDetails.this).load(post_image).into(profileImg);

                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });


                                closeBtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        myDailog.dismiss();
                                    }
                                });
                                myDailog.show();

                            }
                        });

                    }
                };

                mStudentList.setAdapter(firebaseRecyclerAdapter);


            }
        });

    }


    public static class StudentViewHolder extends RecyclerView.ViewHolder{

        View mView;
        ImageButton editProfileButton;
        ImageButton deleteProfileButton;
        android.support.v7.widget.CardView openProfileButton;

        public StudentViewHolder(View itemView) {
            super(itemView);

            editProfileButton = (ImageButton)itemView.findViewById(R.id.editProfile);
            deleteProfileButton = (ImageButton)itemView.findViewById(R.id.deleteProfile);
            openProfileButton = (android.support.v7.widget.CardView)itemView.findViewById(R.id.openProfile);
            mView = itemView;

        }

        public void setSt_name(String st_name){

            TextView post_stname = (TextView)mView.findViewById(R.id.post_stname);
            post_stname.setText(st_name);

        }

        public void setSt_class(String st_class){

            TextView post_stclass = (TextView)mView.findViewById(R.id.post_stcourse);
            post_stclass.setText(st_class);

        }

        public void setImage(final Context ctx, final String image){

            final ImageView post_image = (ImageView) mView.findViewById(R.id.post_image);
            // Picasso.with(ctx).load(image).into(post_image);

            Picasso.with(ctx).load(image).networkPolicy(NetworkPolicy.OFFLINE).into(post_image, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError() {

                    Picasso.with(ctx).load(image).into(post_image);

                }
            });
        }


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(AddStudentDetails.this, MainActivity.class);
        startActivity(intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.class_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_addclass){

            startActivity(new Intent(AddStudentDetails.this, AddStudentInfo.class));

        }

        return super.onOptionsItemSelected(item);
    }


}
