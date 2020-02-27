package co.raveblue.teacherassistant;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.service.autofill.TextValueSanitizer;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyachi.stepview.VerticalStepView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class first_fragment extends Fragment {

    private FirebaseAuth mAuth;

    private FirebaseUser user;
    VerticalStepView verticalStepView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        View v = inflater.inflate(R.layout.first_fragment, container, false);

        //agenda
        String currentUserId = null;

        if (user != null) {
            currentUserId = user.getUid();
        }
        else {
            Log.d("TAG","Firebase user is null");
        }

        DatabaseReference mDatabaseCurrentUser = FirebaseDatabase.getInstance().getReference();
        Query mQueryCurrentUser = mDatabaseCurrentUser.orderByChild("uid").equalTo(currentUserId);

        verticalStepView = v.findViewById(R.id.verticalStepView);

        Calendar calendar = Calendar.getInstance();
        final int day = calendar.get(Calendar.DAY_OF_WEEK);

        switch (day)
        {
            case Calendar.SUNDAY:
                mDatabaseCurrentUser = FirebaseDatabase.getInstance().getReference().child("Ttmonday");

                mQueryCurrentUser = mDatabaseCurrentUser.orderByChild("uid").equalTo(currentUserId);

                break;
            case Calendar.MONDAY:
                mDatabaseCurrentUser = FirebaseDatabase.getInstance().getReference().child("Ttmonday");

                mQueryCurrentUser = mDatabaseCurrentUser.orderByChild("uid").equalTo(currentUserId);
                break;
            case Calendar.TUESDAY:
                mDatabaseCurrentUser = FirebaseDatabase.getInstance().getReference().child("Tttuesday");

                mQueryCurrentUser = mDatabaseCurrentUser.orderByChild("uid").equalTo(currentUserId);
                break;
            case Calendar.WEDNESDAY:
                mDatabaseCurrentUser = FirebaseDatabase.getInstance().getReference().child("Ttwednesday");

                mQueryCurrentUser = mDatabaseCurrentUser.orderByChild("uid").equalTo(currentUserId);
                break;
            case Calendar.THURSDAY:
                mDatabaseCurrentUser = FirebaseDatabase.getInstance().getReference().child("Ttthrusday");

                mQueryCurrentUser = mDatabaseCurrentUser.orderByChild("uid").equalTo(currentUserId);
                break;
            case Calendar.FRIDAY:
                mDatabaseCurrentUser = FirebaseDatabase.getInstance().getReference().child("Ttfriday");

                mQueryCurrentUser = mDatabaseCurrentUser.orderByChild("uid").equalTo(currentUserId);
                break;
            case Calendar.SATURDAY:
                mDatabaseCurrentUser = FirebaseDatabase.getInstance().getReference().child("Ttsaturday");

                mQueryCurrentUser = mDatabaseCurrentUser.orderByChild("uid").equalTo(currentUserId);
                break;

        }

        //Courses
        mQueryCurrentUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ArrayList<String> sources = new ArrayList<String>();
                String course = "null";
                String time = "null";
                switch (day)
                {
                    case Calendar.SUNDAY:
                        course = "mondayof_course";
                        time = "mondayfrom_time";
                        break;
                    case Calendar.MONDAY:
                        course = "mondayof_course";
                        time = "mondayfrom_time";
                        break;
                    case Calendar.TUESDAY:
                        course = "tuesdayof_course";
                        time = "tuesdayfrom_time";
                        break;
                    case Calendar.WEDNESDAY:
                        course = "wednesdayof_course";
                        time = "wednesdayfrom_time";
                        break;
                    case Calendar.THURSDAY:
                        course = "thrusdayof_course";
                        time = "thrusdayfrom_time";
                        break;
                    case Calendar.FRIDAY:
                        course = "fridayof_course";
                        time = "fridayfrom_time";
                        break;
                    case Calendar.SATURDAY:
                        course = "saturdayof_course";
                        time = "saturdayfrom_time";
                        break;

                }

                int cnt = 0;
                int lectrem = 0;
                // Result will be holded Here
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    String sid = Objects.requireNonNull(dsp.child(course).getValue()).toString(); //add result into array list
                    String tid = Objects.requireNonNull(dsp.child(time).getValue()).toString();
                    String[] ht = tid.split(":");
                    String hrtime = ht[0];
                    int hr = Integer.parseInt(hrtime);
                    //   String mintime = ht[1].substring(0,2);
                    //     int min = Integer.parseInt(mintime);
                    String ampm = ht[1].substring(2);

                    if (cnt == 0)
                    {
                        cnt++;
                        ampm = ht[1].substring(2,4).trim();
                    }

                    if (ampm.compareTo("pm") == 0)
                    {
                        hr = hr + 12;
                    }
                    Calendar calendar1 = Calendar.getInstance();
                    int cuhr = calendar1.get(Calendar.HOUR_OF_DAY);
                    // int cumin = calendar1.get(Calendar.MINUTE);
                    if (cuhr <= hr)
                    {
                        lectrem++;
                    }
                    sources.add(sid);
                    //        Toast.makeText(getApplicationContext(), cnt + "", Toast.LENGTH_LONG).show();
                }

                verticalStepView.setStepsViewIndicatorComplectingPosition(sources.size() - lectrem)
                        .reverseDraw(false)
                        .setStepViewTexts(sources)
                        .setLinePaddingProportion(0.85f)
                        .setStepsViewIndicatorUnCompletedLineColor(Color.parseColor("#FFFFFF"))
                        .setStepViewComplectedTextColor(Color.parseColor("#FFFFFF"))
                        .setStepViewUnComplectedTextColor(ContextCompat.getColor(Objects.requireNonNull(getContext()),R.color.uncompleted_text_color))
                        .setStepsViewIndicatorUnCompletedLineColor(Color.parseColor("#FFFFFF"))
                        .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(getContext(),R.drawable.complted))
                        .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(getContext(),R.drawable.attention))
                        .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(getContext(), R.drawable.default_icon));


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), "something went wrong", Toast.LENGTH_LONG).show();
            }
        });

        return v;
    }

    public static first_fragment newInstance(String text){

        first_fragment f = new first_fragment();

        Bundle b = new Bundle();
        b.putString("Message",text);

        return f;
    }

}
