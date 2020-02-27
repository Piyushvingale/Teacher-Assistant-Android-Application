package co.raveblue.teacherassistant;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class overall_timetable extends AppCompatActivity {

    private RecyclerView mMondayLectList;
    private RecyclerView mTuesdayLectList;
    private RecyclerView mWednesdayLectList;
    private RecyclerView mThrusdayLectList;
    private RecyclerView mFridayLectList;
    private RecyclerView mSaturdayLectList;

    private DatabaseReference mDatabase1;
    private DatabaseReference mDatabase2;
    private DatabaseReference mDatabase3;
    private DatabaseReference mDatabase4;
    private DatabaseReference mDatabase5;
    private DatabaseReference mDatabase6;

    private DatabaseReference mDatabaseUsers;
    private Query mQueryCurrentUser1;
    private Query mQueryCurrentUser2;
    private Query mQueryCurrentUser3;
    private Query mQueryCurrentUser4;
    private Query mQueryCurrentUser5;
    private Query mQueryCurrentUser6;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overall_timetable);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth){

                if(firebaseAuth.getCurrentUser() == null){

                    Intent loginIntent  = new Intent(overall_timetable.this, LoginActivity.class);
                    loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(loginIntent);

                }

            }
        };

        mDatabaseUsers = FirebaseDatabase.getInstance().getReference().child("Users");
        String currentUserId = mAuth.getCurrentUser().getUid();

        mDatabase1 = FirebaseDatabase.getInstance().getReference().child("Ttmonday");
        mDatabase1.keepSynced(true);

        mQueryCurrentUser1 = mDatabase1.orderByChild("uid").equalTo(currentUserId);
        mDatabaseUsers.keepSynced(true);

        mDatabase2 = FirebaseDatabase.getInstance().getReference().child("Tttuesday");
        mDatabase2.keepSynced(true);

        mQueryCurrentUser2 = mDatabase2.orderByChild("uid").equalTo(currentUserId);
        mDatabaseUsers.keepSynced(true);

        mDatabase3 = FirebaseDatabase.getInstance().getReference().child("Ttwednesday");
        mDatabase3.keepSynced(true);

        mQueryCurrentUser3 = mDatabase3.orderByChild("uid").equalTo(currentUserId);
        mDatabaseUsers.keepSynced(true);

        mDatabase4 = FirebaseDatabase.getInstance().getReference().child("Ttthrusday");
        mDatabase4.keepSynced(true);

        mQueryCurrentUser4 = mDatabase4.orderByChild("uid").equalTo(currentUserId);
        mDatabaseUsers.keepSynced(true);

        mDatabase5 = FirebaseDatabase.getInstance().getReference().child("Ttfriday");
        mDatabase5.keepSynced(true);

        mQueryCurrentUser5 = mDatabase5.orderByChild("uid").equalTo(currentUserId);
        mDatabaseUsers.keepSynced(true);

        mDatabase6 = FirebaseDatabase.getInstance().getReference().child("Ttsaturday");
        mDatabase6.keepSynced(true);

        mQueryCurrentUser6 = mDatabase6.orderByChild("uid").equalTo(currentUserId);
        mDatabaseUsers.keepSynced(true);


        mMondayLectList = (RecyclerView)findViewById(R.id.overall_mondaytt);
        mMondayLectList.setHasFixedSize(true);
        mMondayLectList.setLayoutManager(new LinearLayoutManager(this));

        mTuesdayLectList = (RecyclerView)findViewById(R.id.overall_tuesdaytt);
        mTuesdayLectList.setHasFixedSize(true);
        mTuesdayLectList.setLayoutManager(new LinearLayoutManager(this));

        mWednesdayLectList = (RecyclerView)findViewById(R.id.overall_wednesdaytt);
        mWednesdayLectList.setHasFixedSize(true);
        mWednesdayLectList.setLayoutManager(new LinearLayoutManager(this));

        mThrusdayLectList = (RecyclerView)findViewById(R.id.overall_thrusdaytt);
        mThrusdayLectList.setHasFixedSize(true);
        mThrusdayLectList.setLayoutManager(new LinearLayoutManager(this));

        mFridayLectList = (RecyclerView)findViewById(R.id.overall_fridaytt);
        mFridayLectList.setHasFixedSize(true);
        mFridayLectList.setLayoutManager(new LinearLayoutManager(this));

        mSaturdayLectList = (RecyclerView)findViewById(R.id.overall_saturdaytt);
        mSaturdayLectList.setHasFixedSize(true);
        mSaturdayLectList.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);

        final FirebaseRecyclerAdapter<overalltt, overall_timetable.MondayLectureViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<overalltt, overall_timetable.MondayLectureViewHolder>(

                overalltt.class,
                R.layout.overalltt_row,
                overall_timetable.MondayLectureViewHolder.class,
                mQueryCurrentUser1

        ) {
            @Override
            protected void populateViewHolder(overall_timetable.MondayLectureViewHolder viewHolder, overalltt model, int position) {

                final String post_key = getRef(position).getKey();

                viewHolder.setMondayLect_name(model.getMondayLect_name());
                viewHolder.setMondayfrom_time(model.getMondayfrom_time());

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent singleStudentIntent = new Intent(overall_timetable.this, single_ttmonday.class);
                        singleStudentIntent.putExtra("ttmonday_id",post_key);
                        startActivity(singleStudentIntent);

                    }
                });

            }
        };

        mMondayLectList.setAdapter(firebaseRecyclerAdapter);

        final FirebaseRecyclerAdapter<overalltt, overall_timetable.TuesdayLectureViewHolder> firebaseRecyclerAdapter1 = new FirebaseRecyclerAdapter<overalltt, overall_timetable.TuesdayLectureViewHolder>(

                overalltt.class,
                R.layout.overalltt_row,
                overall_timetable.TuesdayLectureViewHolder.class,
                mQueryCurrentUser2

        ) {
            @Override
            protected void populateViewHolder(overall_timetable.TuesdayLectureViewHolder viewHolder, overalltt model, int position) {

                final String post_key = getRef(position).getKey();

                viewHolder.setTuesdayLect_name(model.getTuesdaylect_name());
                viewHolder.setTuesdayfrom_time(model.getTuesdayfrom_time());

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent singleStudentIntent1 = new Intent(overall_timetable.this, single_tttuesday.class);
                        singleStudentIntent1.putExtra("tttuesday_id",post_key);
                        startActivity(singleStudentIntent1);

                    }
                });

            }
        };

        mTuesdayLectList.setAdapter(firebaseRecyclerAdapter1);


        final FirebaseRecyclerAdapter<overalltt, overall_timetable.WednesdayLectureViewHolder> firebaseRecyclerAdapter2 = new FirebaseRecyclerAdapter<overalltt, overall_timetable.WednesdayLectureViewHolder>(

                overalltt.class,
                R.layout.overalltt_row,
                overall_timetable.WednesdayLectureViewHolder.class,
                mQueryCurrentUser3

        ) {
            @Override
            protected void populateViewHolder(overall_timetable.WednesdayLectureViewHolder viewHolder, overalltt model, int position) {

                final String post_key = getRef(position).getKey();

                viewHolder.setWednesdayLect_name(model.getWednesdaylect_name());
                viewHolder.setWednesdayfrom_time(model.getWednesdayfrom_time());

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent singleStudentIntent2 = new Intent(overall_timetable.this, single_ttwednesday.class);
                        singleStudentIntent2.putExtra("ttwednesday_id",post_key);
                        startActivity(singleStudentIntent2);

                    }
                });

            }
        };

        mWednesdayLectList.setAdapter(firebaseRecyclerAdapter2);



        final FirebaseRecyclerAdapter<overalltt, overall_timetable.ThrusdayLectureViewHolder> firebaseRecyclerAdapter3 = new FirebaseRecyclerAdapter<overalltt, overall_timetable.ThrusdayLectureViewHolder>(

                overalltt.class,
                R.layout.overalltt_row,
                overall_timetable.ThrusdayLectureViewHolder.class,
                mQueryCurrentUser4

        ) {
            @Override
            protected void populateViewHolder(overall_timetable.ThrusdayLectureViewHolder viewHolder, overalltt model, int position) {

                final String post_key = getRef(position).getKey();

                viewHolder.setThrusdayLect_name(model.getThrusdaylect_name());
                viewHolder.setThrusdayfrom_time(model.getThrusdayfrom_time());

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent singleStudentIntent3 = new Intent(overall_timetable.this, single_ttthrusday.class);
                        singleStudentIntent3.putExtra("ttthrusday_id",post_key);
                        startActivity(singleStudentIntent3);

                    }
                });

            }
        };

        mThrusdayLectList.setAdapter(firebaseRecyclerAdapter3);



        final FirebaseRecyclerAdapter<overalltt, overall_timetable.FridayLectureViewHolder> firebaseRecyclerAdapter4 = new FirebaseRecyclerAdapter<overalltt, overall_timetable.FridayLectureViewHolder>(

                overalltt.class,
                R.layout.overalltt_row,
                overall_timetable.FridayLectureViewHolder.class,
                mQueryCurrentUser5

        ) {
            @Override
            protected void populateViewHolder(overall_timetable.FridayLectureViewHolder viewHolder, overalltt model, int position) {

                final String post_key = getRef(position).getKey();

                viewHolder.setFridayLect_name(model.getFridaylect_name());
                viewHolder.setFridayfrom_time(model.getFridayfrom_time());

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent singleStudentIntent4 = new Intent(overall_timetable.this, single_ttfriday.class);
                        singleStudentIntent4.putExtra("ttfriday_id",post_key);
                        startActivity(singleStudentIntent4);

                    }
                });

            }
        };

        mFridayLectList.setAdapter(firebaseRecyclerAdapter4);



        final FirebaseRecyclerAdapter<overalltt, overall_timetable.SaturdayLectureViewHolder> firebaseRecyclerAdapter5 = new FirebaseRecyclerAdapter<overalltt, overall_timetable.SaturdayLectureViewHolder>(

                overalltt.class,
                R.layout.overalltt_row,
                overall_timetable.SaturdayLectureViewHolder.class,
                mQueryCurrentUser6

        ) {
            @Override
            protected void populateViewHolder(overall_timetable.SaturdayLectureViewHolder viewHolder, overalltt model, int position) {

                final String post_key = getRef(position).getKey();

                viewHolder.setSaturdayLect_name(model.getSaturdaylect_name());
                viewHolder.setSaturdayfrom_time(model.getSaturdayfrom_time());

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent singleStudentIntent5 = new Intent(overall_timetable.this, single_ttsaturday.class);
                        singleStudentIntent5.putExtra("ttsaturday_id",post_key);
                        startActivity(singleStudentIntent5);

                    }
                });

            }
        };

        mSaturdayLectList.setAdapter(firebaseRecyclerAdapter5);

    }

    public static class MondayLectureViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public MondayLectureViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

        }

        public void setMondayLect_name(String mondaylect_name) {

            TextView post_lecture = (TextView) mView.findViewById(R.id.overalltt_lect);
            post_lecture.setText(mondaylect_name);

        }

        public void setMondayfrom_time(String mondayfrom_time) {

            TextView post_from = (TextView) mView.findViewById(R.id.overalltt_from);
            post_from.setText(mondayfrom_time);

        }
    }


    public static class TuesdayLectureViewHolder extends RecyclerView.ViewHolder{

        View mView;

        public TuesdayLectureViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

        }

        public void setTuesdayLect_name(String tuesdaylect_name){

            TextView post_lecture = (TextView)mView.findViewById(R.id.overalltt_lect);
            post_lecture.setText(tuesdaylect_name);

        }

        public void setTuesdayfrom_time(String tuesdayfrom_time){

            TextView post_from = (TextView)mView.findViewById(R.id.overalltt_from);
            post_from.setText(tuesdayfrom_time);

        }


    }

    public static class WednesdayLectureViewHolder extends RecyclerView.ViewHolder{

        View mView;

        public WednesdayLectureViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

        }

        public void setWednesdayLect_name(String wednesdaylect_name){

            TextView post_lecture = (TextView)mView.findViewById(R.id.overalltt_lect);
            post_lecture.setText(wednesdaylect_name);

        }

        public void setWednesdayfrom_time(String wednesdayfrom_time){

            TextView post_from = (TextView)mView.findViewById(R.id.overalltt_from);
            post_from.setText(wednesdayfrom_time);

        }

    }

    public static class ThrusdayLectureViewHolder extends RecyclerView.ViewHolder{

        View mView;

        public ThrusdayLectureViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

        }

        public void setThrusdayLect_name(String thrusdaylect_name){

            TextView post_lecture = (TextView)mView.findViewById(R.id.overalltt_lect);
            post_lecture.setText(thrusdaylect_name);

        }

        public void setThrusdayfrom_time(String thrusdayfrom_time){

            TextView post_from = (TextView)mView.findViewById(R.id.overalltt_from);
            post_from.setText(thrusdayfrom_time);

        }

    }

    public static class FridayLectureViewHolder extends RecyclerView.ViewHolder{

        View mView;

        public FridayLectureViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

        }

        public void setFridayLect_name(String fridaylect_name){

            TextView post_lecture = (TextView)mView.findViewById(R.id.overalltt_lect);
            post_lecture.setText(fridaylect_name);

        }

        public void setFridayfrom_time(String fridayfrom_time){

            TextView post_from = (TextView)mView.findViewById(R.id.overalltt_from);
            post_from.setText(fridayfrom_time);

        }

    }

    public static class SaturdayLectureViewHolder extends RecyclerView.ViewHolder{

        View mView;

        public SaturdayLectureViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

        }

        public void setSaturdayLect_name(String saturdaylect_name){

            TextView post_lecture = (TextView)mView.findViewById(R.id.overalltt_lect);
            post_lecture.setText(saturdaylect_name);

        }

        public void setSaturdayfrom_time(String saturdayfrom_time){

            TextView post_from = (TextView)mView.findViewById(R.id.overalltt_from);
            post_from.setText(saturdayfrom_time);

        }

    }





    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(overall_timetable.this, PersonalTimetable.class);
        startActivity(intent);

    }


}
