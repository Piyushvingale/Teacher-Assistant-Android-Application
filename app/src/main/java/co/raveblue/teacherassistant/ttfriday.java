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

public class ttfriday extends AppCompatActivity {

    private RecyclerView mFridayLectList;

    private DatabaseReference mDatabase;

    private DatabaseReference mDatabaseUsers;
    private DatabaseReference mDatabaseCurrentUser;

    private Query mQueryCurrentUser;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ttfriday);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth){

                if(firebaseAuth.getCurrentUser() == null){

                    Intent loginIntent  = new Intent(ttfriday.this, LoginActivity.class);
                    loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(loginIntent);

                }

            }
        };

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Ttfriday");

        mDatabaseUsers = FirebaseDatabase.getInstance().getReference().child("Users");

        String currentUserId = mAuth.getCurrentUser().getUid();

        mDatabaseCurrentUser = FirebaseDatabase.getInstance().getReference().child("Ttfriday");

        mQueryCurrentUser = mDatabaseCurrentUser.orderByChild("uid").equalTo(currentUserId);

        mDatabaseUsers.keepSynced(true);
        mDatabase.keepSynced(true);

        mFridayLectList = (RecyclerView)findViewById(R.id.fridaylect_list);
        mFridayLectList.setHasFixedSize(true);
        mFridayLectList.setLayoutManager(new LinearLayoutManager(this));

    }


    @Override
    protected void onStart() {
        super.onStart();


        final FirebaseRecyclerAdapter<Friday, ttfriday.FridayLectureViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Friday, ttfriday.FridayLectureViewHolder>(

                Friday.class,
                R.layout.friday_row,
                ttfriday.FridayLectureViewHolder.class,
                mQueryCurrentUser

        ) {
            @Override
            protected void populateViewHolder(ttfriday.FridayLectureViewHolder viewHolder, Friday model, int position) {

                final String post_key = getRef(position).getKey();

                viewHolder.setFridayLect_name(model.getFridayLect_name());
                viewHolder.setFridayfrom_time(model.getFridayfrom_time());
                viewHolder.setFridayto_time(model.getFridayto_time());
                viewHolder.setFridayof_course(model.getFridayof_course());

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent singleStudentIntent = new Intent(ttfriday.this, single_ttfriday.class);
                        singleStudentIntent.putExtra("ttfriday_id",post_key);
                        startActivity(singleStudentIntent);

                    }
                });

            }
        };

        mFridayLectList.setAdapter(firebaseRecyclerAdapter);

    }

    public static class FridayLectureViewHolder extends RecyclerView.ViewHolder{

        View mView;

        public FridayLectureViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

        }

        public void setFridayLect_name(String fridaylect_name){

            TextView post_lecture = (TextView)mView.findViewById(R.id.post_ttfriday);
            post_lecture.setText(fridaylect_name);

        }

        public void setFridayfrom_time(String fridayfrom_time){

            TextView post_from = (TextView)mView.findViewById(R.id.ttfridayFrom);
            post_from.setText(fridayfrom_time);

        }

        public void setFridayto_time(String fridayto_time){

            TextView post_to = (TextView)mView.findViewById(R.id.ttfridayTo);
            post_to.setText(fridayto_time);

        }

        public void setFridayof_course(String fridayof_course){

            TextView post_cou = (TextView)mView.findViewById(R.id.post_ttfridaycourse);
            post_cou.setText(fridayof_course);

        }


    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(ttfriday.this, PersonalTimetable.class);
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

            startActivity(new Intent(ttfriday.this, insert_ttfriday.class));

        }

        return super.onOptionsItemSelected(item);
    }


}
