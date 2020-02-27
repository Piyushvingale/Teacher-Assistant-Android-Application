package co.raveblue.teacherassistant;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class ttmonday extends AppCompatActivity {

    private RecyclerView mMondayLectList;

    private DatabaseReference mDatabase;

    private DatabaseReference mDatabaseUsers;
    private DatabaseReference mDatabaseCurrentUser;

    private Query mQueryCurrentUser;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ttmonday);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth){

                if(firebaseAuth.getCurrentUser() == null){

                    Intent loginIntent  = new Intent(ttmonday.this, LoginActivity.class);
                    loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(loginIntent);

                }

            }
        };

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Ttmonday");

        mDatabaseUsers = FirebaseDatabase.getInstance().getReference().child("Users");

        String currentUserId = mAuth.getCurrentUser().getUid();

        mDatabaseCurrentUser = FirebaseDatabase.getInstance().getReference().child("Ttmonday");

        mQueryCurrentUser = mDatabaseCurrentUser.orderByChild("uid").equalTo(currentUserId);

        mDatabaseUsers.keepSynced(true);
        mDatabase.keepSynced(true);

        mMondayLectList = (RecyclerView)findViewById(R.id.mondaylect_list);
        mMondayLectList.setHasFixedSize(true);
        mMondayLectList.setLayoutManager(new LinearLayoutManager(this));

    }


    @Override
    protected void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);

        final FirebaseRecyclerAdapter<Monday, ttmonday.MondayLectureViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Monday, MondayLectureViewHolder>(

                Monday.class,
                R.layout.monday_row,
                ttmonday.MondayLectureViewHolder.class,
                mQueryCurrentUser

        ) {
            @Override
            protected void populateViewHolder(ttmonday.MondayLectureViewHolder viewHolder, Monday model, int position) {

                final String post_key = getRef(position).getKey();

                viewHolder.setMondayLect_name(model.getMondayLect_name());
                viewHolder.setMondayfrom_time(model.getMondayfrom_time());
                viewHolder.setMondayto_time(model.getMondayto_time());
                viewHolder.setMondayof_course(model.getMondayof_course());

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent singleStudentIntent = new Intent(ttmonday.this, single_ttmonday.class);
                        singleStudentIntent.putExtra("ttmonday_id",post_key);
                        startActivity(singleStudentIntent);

                    }
                });

            }
        };

        mMondayLectList.setAdapter(firebaseRecyclerAdapter);

    }

    public static class MondayLectureViewHolder extends RecyclerView.ViewHolder{

        View mView;

        public MondayLectureViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

        }

        public void setMondayLect_name(String mondaylect_name){

            TextView post_lecture = (TextView)mView.findViewById(R.id.post_ttmonday);
            post_lecture.setText(mondaylect_name);

        }

        public void setMondayfrom_time(String mondayfrom_time){

            TextView post_from = (TextView)mView.findViewById(R.id.ttmondayFrom);
            post_from.setText(mondayfrom_time);

        }

        public void setMondayto_time(String mondayto_time){

            TextView post_to = (TextView)mView.findViewById(R.id.ttmondayTo);
            post_to.setText(mondayto_time);

        }

        public void setMondayof_course(String mondayof_course){

            TextView post_cou = (TextView)mView.findViewById(R.id.post_ttmondaycourse);
            post_cou.setText(mondayof_course);

        }


    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(ttmonday.this, PersonalTimetable.class);
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

            startActivity(new Intent(ttmonday.this, insert_ttmonday.class));

        }

        return super.onOptionsItemSelected(item);
    }


}
