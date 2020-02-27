package co.raveblue.teacherassistant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class Performance extends AppCompatActivity {

    private RecyclerView mStudentList;
    private EditText mSearchField;
    private ImageButton mSearchBtn;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_performance);


        mDatabase = FirebaseDatabase.getInstance().getReference().child("Student_Info");

        mSearchField = (EditText)findViewById(R.id.search_field_per);
        mSearchBtn = (ImageButton)findViewById(R.id.search_btn_per);

        mStudentList = (RecyclerView)findViewById(R.id.student_list_per);
        mStudentList.setHasFixedSize(true);
        mStudentList.setLayoutManager(new LinearLayoutManager(this));

        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String searchText = mSearchField.getText().toString();
                firebaseStudentSearch(searchText);

            }

            private void firebaseStudentSearch(String searchText) {

                Query firebaseSearchQuery = mDatabase.orderByChild("st_name").startAt(searchText).endAt(searchText + "\uf8ff");

                final FirebaseRecyclerAdapter<performance_model, Performance.StudentViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<performance_model, Performance.StudentViewHolder>(

                        performance_model.class,
                        R.layout.performance_row,
                        Performance.StudentViewHolder.class,
                        firebaseSearchQuery

                ) {
                    @Override
                    protected void populateViewHolder(Performance.StudentViewHolder viewHolder, performance_model model, int position) {

                        final String post_key = getRef(position).getKey();

                        viewHolder.setSt_name(model.getSt_name());
                        viewHolder.setSt_roll(model.getSt_roll());

                        viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

//                                Intent singleStudentIntent = new Intent(Performance.this, SinglePerformance.class);
  //                              singleStudentIntent.putExtra("student_id",post_key);
    //                            startActivity(singleStudentIntent);

                            }
                        });

                        viewHolder.editProfileButton.setOnClickListener(new View.OnClickListener(){

                            @Override
                            public void onClick(View view) {

                                Intent singleStudentIntent = new Intent(Performance.this, SingleEditPerformance.class);
                                singleStudentIntent.putExtra("student_id",post_key);
                                startActivity(singleStudentIntent);

                            }
                        });


                        viewHolder.checkProfileButton.setOnClickListener(new View.OnClickListener(){

                            @Override
                            public void onClick(View view) {

                                Intent singleStudentIntent = new Intent(Performance.this, SinglePerformance.class);
                                singleStudentIntent.putExtra("student_id",post_key);
                                startActivity(singleStudentIntent);

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
        ImageButton checkProfileButton;


        public StudentViewHolder(View itemView) {
            super(itemView);

            editProfileButton = (ImageButton)itemView.findViewById(R.id.editPerformance);
            checkProfileButton = (ImageButton)itemView.findViewById(R.id.checkPerformance);
            mView = itemView;

        }

        public void setSt_name(String st_name){

            TextView post_stname = (TextView)mView.findViewById(R.id.post_stname);
            post_stname.setText(st_name);

        }


        public void setSt_roll(String st_roll){

            TextView post_stroll = (TextView)mView.findViewById(R.id.post_stroll);
            post_stroll.setText(st_roll);

        }

    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(Performance.this, MainActivity.class);
        startActivity(intent);

    }

}
