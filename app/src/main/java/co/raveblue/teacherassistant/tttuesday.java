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

public class tttuesday extends AppCompatActivity {

    private RecyclerView mTuesdayLectList;

    private DatabaseReference mDatabase;

    private DatabaseReference mDatabaseUsers;
    private DatabaseReference mDatabaseCurrentUser;

    private Query mQueryCurrentUser;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tttuesday);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth){

                if(firebaseAuth.getCurrentUser() == null){

                    Intent loginIntent  = new Intent(tttuesday.this, LoginActivity.class);
                    loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(loginIntent);

                }

            }
        };

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Tttuesday");

        mDatabaseUsers = FirebaseDatabase.getInstance().getReference().child("Users");

        String currentUserId = mAuth.getCurrentUser().getUid();

        mDatabaseCurrentUser = FirebaseDatabase.getInstance().getReference().child("Tttuesday");

        mQueryCurrentUser = mDatabaseCurrentUser.orderByChild("uid").equalTo(currentUserId);

        mDatabaseUsers.keepSynced(true);
        mDatabase.keepSynced(true);

        mTuesdayLectList = (RecyclerView)findViewById(R.id.tuesdaylect_list);
        mTuesdayLectList.setHasFixedSize(true);
        mTuesdayLectList.setLayoutManager(new LinearLayoutManager(this));

    }


    @Override
    protected void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);

        final FirebaseRecyclerAdapter<Tuesday, tttuesday.TuesdayLectureViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Tuesday, tttuesday.TuesdayLectureViewHolder>(

                Tuesday.class,
                R.layout.tuesday_row,
                tttuesday.TuesdayLectureViewHolder.class,
                mQueryCurrentUser

        ) {
            @Override
            protected void populateViewHolder(tttuesday.TuesdayLectureViewHolder viewHolder, Tuesday model, int position) {

                final String post_key = getRef(position).getKey();

                viewHolder.setTuesdayLect_name(model.getTuesdayLect_name());
                viewHolder.setTuesdayfrom_time(model.getTuesdayfrom_time());
                viewHolder.setTuesdayto_time(model.getTuesdayto_time());
                viewHolder.setTuesdayof_course(model.getTuesdayof_course());

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent singleStudentIntent = new Intent(tttuesday.this, single_tttuesday.class);
                        singleStudentIntent.putExtra("tttuesday_id",post_key);
                        startActivity(singleStudentIntent);

                    }
                });

            }
        };

        mTuesdayLectList.setAdapter(firebaseRecyclerAdapter);

    }

    public static class TuesdayLectureViewHolder extends RecyclerView.ViewHolder{

        View mView;

        public TuesdayLectureViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

        }

        public void setTuesdayLect_name(String tuesdaylect_name){

            TextView post_lecture = (TextView)mView.findViewById(R.id.post_tttuesday);
            post_lecture.setText(tuesdaylect_name);

        }

        public void setTuesdayfrom_time(String tuesdayfrom_time){

            TextView post_from = (TextView)mView.findViewById(R.id.tttuesdayFrom);
            post_from.setText(tuesdayfrom_time);

        }

        public void setTuesdayto_time(String tuesdayto_time){

            TextView post_to = (TextView)mView.findViewById(R.id.tttuesdayTo);
            post_to.setText(tuesdayto_time);

        }

        public void setTuesdayof_course(String tuesdayof_course){

            TextView post_cou = (TextView)mView.findViewById(R.id.post_tttuesdaycourse);
            post_cou.setText(tuesdayof_course);

        }


    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(tttuesday.this, PersonalTimetable.class);
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

            startActivity(new Intent(tttuesday.this, insert_tttuesday.class));

        }

        return super.onOptionsItemSelected(item);
    }


}
