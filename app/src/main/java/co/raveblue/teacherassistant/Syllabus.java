package co.raveblue.teacherassistant;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class Syllabus extends AppCompatActivity {

    private RecyclerView mSyllabusList;

    private DatabaseReference mDatabase;

    private DatabaseReference mDatabaseUsers;
    private DatabaseReference mDatabaseCurrentUser;

    private Query mQueryCurrentUser;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_syllabus);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth){

                if(firebaseAuth.getCurrentUser() == null){

                    Intent loginIntent  = new Intent(Syllabus.this, LoginActivity.class);
                    loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(loginIntent);

                }

            }
        };

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Syllabus");

        mDatabaseUsers = FirebaseDatabase.getInstance().getReference().child("Users");

        String currentUserId = mAuth.getCurrentUser().getUid();

        mDatabaseCurrentUser = FirebaseDatabase.getInstance().getReference().child("Syllabus");

        mQueryCurrentUser = mDatabaseCurrentUser.orderByChild("uid").equalTo(currentUserId);

        mDatabaseUsers.keepSynced(true);
        mDatabase.keepSynced(true);

        mSyllabusList = (RecyclerView)findViewById(R.id.syllabus_list);
        mSyllabusList.setHasFixedSize(true);
        mSyllabusList.setLayoutManager(new LinearLayoutManager(this));

    }


    @Override
    protected void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);

        final FirebaseRecyclerAdapter<Syllabus_model, Syllabus.SyllabusViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Syllabus_model, Syllabus.SyllabusViewHolder>(

                Syllabus_model.class,
                R.layout.syllabus_row,
                Syllabus.SyllabusViewHolder.class,
                mQueryCurrentUser

        ) {
            @Override
            protected void populateViewHolder(SyllabusViewHolder viewHolder, Syllabus_model model, int position) {

                final String post_key = getRef(position).getKey();

                viewHolder.setUrl(model.getUrl());
                viewHolder.setFilename(model.getFilename());
                viewHolder.setDate(model.getDate());
                viewHolder.setDay(model.getDay());

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent singleStudentIntent = new Intent(Syllabus.this, SingleSyllabus.class);
                        singleStudentIntent.putExtra("syllabus_id",post_key);
                        startActivity(singleStudentIntent);

                    }
                });

            }
        };

        mSyllabusList.setAdapter(firebaseRecyclerAdapter);

    }

    public static class SyllabusViewHolder extends RecyclerView.ViewHolder{

        View mView;

        public SyllabusViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

        }


        public void setFilename(String filename){

            TextView fnm = (TextView)mView.findViewById(R.id.post_filename);
            fnm.setText(filename);

        }

        public void setUrl(String url){

            TextView urltext = (TextView)mView.findViewById(R.id.post_url);
            urltext.setText(url);

        }
        public void setDate(String date){

            TextView datetext = (TextView)mView.findViewById(R.id.syllabus_date);
            datetext.setText(date);

        }
        public void setDay(String day){

            TextView daytext = (TextView)mView.findViewById(R.id.syllabus_day);
            daytext.setText(day);

        }


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(Syllabus.this, MainActivity.class);
        startActivity(intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.upload_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_upload){

            startActivity(new Intent(Syllabus.this, SyllabusAdd.class));

        }

        return super.onOptionsItemSelected(item);
    }




}
