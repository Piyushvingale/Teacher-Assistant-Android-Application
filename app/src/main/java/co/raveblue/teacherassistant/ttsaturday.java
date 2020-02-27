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

public class ttsaturday extends AppCompatActivity {

    private RecyclerView mSaturdayLectList;

    private DatabaseReference mDatabase;

    private DatabaseReference mDatabaseUsers;
    private DatabaseReference mDatabaseCurrentUser;

    private Query mQueryCurrentUser;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ttsaturday);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth){

                if(firebaseAuth.getCurrentUser() == null){

                    Intent loginIntent  = new Intent(ttsaturday.this, LoginActivity.class);
                    loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(loginIntent);

                }

            }
        };

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Ttsaturday");

        mDatabaseUsers = FirebaseDatabase.getInstance().getReference().child("Users");

        String currentUserId = mAuth.getCurrentUser().getUid();

        mDatabaseCurrentUser = FirebaseDatabase.getInstance().getReference().child("Ttsaturday");

        mQueryCurrentUser = mDatabaseCurrentUser.orderByChild("uid").equalTo(currentUserId);

        mDatabaseUsers.keepSynced(true);
        mDatabase.keepSynced(true);

        mSaturdayLectList = (RecyclerView)findViewById(R.id.saturdaylect_list);
        mSaturdayLectList.setHasFixedSize(true);
        mSaturdayLectList.setLayoutManager(new LinearLayoutManager(this));

    }


    @Override
    protected void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);

        final FirebaseRecyclerAdapter<Saturday, ttsaturday.SaturdayLectureViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Saturday, ttsaturday.SaturdayLectureViewHolder>(

                Saturday.class,
                R.layout.saturday_row,
                ttsaturday.SaturdayLectureViewHolder.class,
                mQueryCurrentUser

        ) {
            @Override
            protected void populateViewHolder(ttsaturday.SaturdayLectureViewHolder viewHolder, Saturday model, int position) {

                final String post_key = getRef(position).getKey();

                viewHolder.setSaturdayLect_name(model.getSaturdayLect_name());
                viewHolder.setSaturdayfrom_time(model.getSaturdayfrom_time());
                viewHolder.setSaturdayto_time(model.getSaturdayto_time());
                viewHolder.setSaturdayof_course(model.getSaturdayof_course());

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent singleStudentIntent = new Intent(ttsaturday.this, single_ttsaturday.class);
                        singleStudentIntent.putExtra("ttsaturday_id",post_key);
                        startActivity(singleStudentIntent);

                    }
                });

            }
        };

        mSaturdayLectList.setAdapter(firebaseRecyclerAdapter);

    }

    public static class SaturdayLectureViewHolder extends RecyclerView.ViewHolder{

        View mView;

        public SaturdayLectureViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

        }

        public void setSaturdayLect_name(String saturdaylect_name){

            TextView post_lecture = (TextView)mView.findViewById(R.id.post_ttsaturday);
            post_lecture.setText(saturdaylect_name);

        }

        public void setSaturdayfrom_time(String saturdayfrom_time){

            TextView post_from = (TextView)mView.findViewById(R.id.ttsaturdayFrom);
            post_from.setText(saturdayfrom_time);

        }

        public void setSaturdayto_time(String saturdayto_time){

            TextView post_to = (TextView)mView.findViewById(R.id.ttsaturdayTo);
            post_to.setText(saturdayto_time);

        }

        public void setSaturdayof_course(String saturdayof_course){

            TextView post_cou = (TextView)mView.findViewById(R.id.post_ttsaturdaycourse);
            post_cou.setText(saturdayof_course);

        }


    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(ttsaturday.this, PersonalTimetable.class);
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

            startActivity(new Intent(ttsaturday.this, insert_ttsaturday.class));

        }

        return super.onOptionsItemSelected(item);
    }


}
