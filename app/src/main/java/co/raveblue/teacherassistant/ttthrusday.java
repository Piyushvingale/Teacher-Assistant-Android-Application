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

public class ttthrusday extends AppCompatActivity {

    private RecyclerView mThrusdayLectList;

    private DatabaseReference mDatabase;

    private DatabaseReference mDatabaseUsers;
    private DatabaseReference mDatabaseCurrentUser;

    private Query mQueryCurrentUser;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ttthrusday);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth){

                if(firebaseAuth.getCurrentUser() == null){

                    Intent loginIntent  = new Intent(ttthrusday.this, LoginActivity.class);
                    loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(loginIntent);

                }

            }
        };

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Ttthrusday");

        mDatabaseUsers = FirebaseDatabase.getInstance().getReference().child("Users");

        String currentUserId = mAuth.getCurrentUser().getUid();

        mDatabaseCurrentUser = FirebaseDatabase.getInstance().getReference().child("Ttthrusday");

        mQueryCurrentUser = mDatabaseCurrentUser.orderByChild("uid").equalTo(currentUserId);

        mDatabaseUsers.keepSynced(true);
        mDatabase.keepSynced(true);

        mThrusdayLectList = (RecyclerView)findViewById(R.id.thrusdaylect_list);
        mThrusdayLectList.setHasFixedSize(true);
        mThrusdayLectList.setLayoutManager(new LinearLayoutManager(this));

    }


    @Override
    protected void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);

        final FirebaseRecyclerAdapter<Thrusday, ttthrusday.ThrusdayLectureViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Thrusday, ttthrusday.ThrusdayLectureViewHolder>(

                Thrusday.class,
                R.layout.thrusday_row,
                ttthrusday.ThrusdayLectureViewHolder.class,
                mQueryCurrentUser

        ) {
            @Override
            protected void populateViewHolder(ttthrusday.ThrusdayLectureViewHolder viewHolder, Thrusday model, int position) {

                final String post_key = getRef(position).getKey();

                viewHolder.setThrusdayLect_name(model.getThrusdayLect_name());
                viewHolder.setThrusdayfrom_time(model.getThrusdayfrom_time());
                viewHolder.setThrusdayto_time(model.getThrusdayto_time());
                viewHolder.setThrusdayof_course(model.getThrusdayof_course());

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent singleStudentIntent = new Intent(ttthrusday.this, single_ttthrusday.class);
                        singleStudentIntent.putExtra("ttthrusday_id",post_key);
                        startActivity(singleStudentIntent);

                    }
                });

            }
        };

        mThrusdayLectList.setAdapter(firebaseRecyclerAdapter);

    }

    public static class ThrusdayLectureViewHolder extends RecyclerView.ViewHolder{

        View mView;

        public ThrusdayLectureViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

        }

        public void setThrusdayLect_name(String thrusdaylect_name){

            TextView post_lecture = (TextView)mView.findViewById(R.id.post_ttthrusday);
            post_lecture.setText(thrusdaylect_name);

        }

        public void setThrusdayfrom_time(String thrusdayfrom_time){

            TextView post_from = (TextView)mView.findViewById(R.id.ttthrusdayFrom);
            post_from.setText(thrusdayfrom_time);

        }

        public void setThrusdayto_time(String thrusdayto_time){

            TextView post_to = (TextView)mView.findViewById(R.id.ttthrusdayTo);
            post_to.setText(thrusdayto_time);

        }

        public void setThrusdayof_course(String thrusdayof_course){

            TextView post_cou = (TextView)mView.findViewById(R.id.post_ttthrusdaycourse);
            post_cou.setText(thrusdayof_course);

        }


    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(ttthrusday.this, PersonalTimetable.class);
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

            startActivity(new Intent(ttthrusday.this, insert_ttthrusday.class));

        }

        return super.onOptionsItemSelected(item);
    }


}
