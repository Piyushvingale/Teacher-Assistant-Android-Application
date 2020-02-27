package co.raveblue.teacherassistant;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class ttwednesday extends AppCompatActivity {

    private RecyclerView mWednesdayLectList;

    private DatabaseReference mDatabase;

    private DatabaseReference mDatabaseUsers;
    private DatabaseReference mDatabaseCurrentUser;

    private Query mQueryCurrentUser;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ttwednesday);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth){

                if(firebaseAuth.getCurrentUser() == null){

                    Intent loginIntent  = new Intent(ttwednesday.this, LoginActivity.class);
                    loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(loginIntent);

                }

            }
        };

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Ttwednesday");

        mDatabaseUsers = FirebaseDatabase.getInstance().getReference().child("Users");

        String currentUserId = mAuth.getCurrentUser().getUid();

        mDatabaseCurrentUser = FirebaseDatabase.getInstance().getReference().child("Ttwednesday");

        mQueryCurrentUser = mDatabaseCurrentUser.orderByChild("uid").equalTo(currentUserId);

        mDatabaseUsers.keepSynced(true);
        mDatabase.keepSynced(true);

        mWednesdayLectList = (RecyclerView)findViewById(R.id.wednesdaylect_list);
        mWednesdayLectList.setHasFixedSize(true);
        mWednesdayLectList.setLayoutManager(new LinearLayoutManager(this));

    }


    @Override
    protected void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);

        final FirebaseRecyclerAdapter<Wednesday, ttwednesday.WednesdayLectureViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Wednesday, ttwednesday.WednesdayLectureViewHolder>(

                Wednesday.class,
                R.layout.wednesday_row,
                ttwednesday.WednesdayLectureViewHolder.class,
                mQueryCurrentUser

        ) {
            @Override
            protected void populateViewHolder(ttwednesday.WednesdayLectureViewHolder viewHolder, Wednesday model, int position) {

                final String post_key = getRef(position).getKey();

                viewHolder.setWednesdayLect_name(model.getWednesdayLect_name());
                viewHolder.setWednesdayfrom_time(model.getWednesdayfrom_time());
                viewHolder.setWednesdayto_time(model.getWednesdayto_time());
                viewHolder.setWednesdayof_course(model.getWednesdayof_course());

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent singleStudentIntent = new Intent(ttwednesday.this, single_ttwednesday.class);
                        singleStudentIntent.putExtra("ttwednesday_id",post_key);
                        startActivity(singleStudentIntent);

                    }
                });

            }
        };

        mWednesdayLectList.setAdapter(firebaseRecyclerAdapter);

    }

    public static class WednesdayLectureViewHolder extends RecyclerView.ViewHolder{

        View mView;

        public WednesdayLectureViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

        }

        public void setWednesdayLect_name(String wednesdaylect_name){

            TextView post_lecture = (TextView)mView.findViewById(R.id.post_ttwednesday);
            post_lecture.setText(wednesdaylect_name);

        }

        public void setWednesdayfrom_time(String wednesdayfrom_time){

            TextView post_from = (TextView)mView.findViewById(R.id.ttwednesdayFrom);
            post_from.setText(wednesdayfrom_time);

        }

        public void setWednesdayto_time(String wednesdayto_time){

            TextView post_to = (TextView)mView.findViewById(R.id.ttwednesdayTo);
            post_to.setText(wednesdayto_time);

        }

        public void setWednesdayof_course(String wednesdayof_course){

            TextView post_cou = (TextView)mView.findViewById(R.id.post_ttwednesdaycourse);
            post_cou.setText(wednesdayof_course);

        }


    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(ttwednesday.this, PersonalTimetable.class);
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

            startActivity(new Intent(ttwednesday.this, insert_ttwednesday.class));

        }

        return super.onOptionsItemSelected(item);
    }


}
