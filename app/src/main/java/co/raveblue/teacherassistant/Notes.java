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

public class Notes extends AppCompatActivity {

    private RecyclerView mNoteList;

    private DatabaseReference mDatabase;

    private DatabaseReference mDatabaseUsers;
    private DatabaseReference mDatabaseCurrentUser;

    private Query mQueryCurrentUser;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth){

                if(firebaseAuth.getCurrentUser() == null){

                    Intent loginIntent  = new Intent(Notes.this, LoginActivity.class);
                    loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(loginIntent);

                }

            }
        };

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Notes");

        mDatabaseUsers = FirebaseDatabase.getInstance().getReference().child("Users");

        String currentUserId = mAuth.getCurrentUser().getUid();

        mDatabaseCurrentUser = FirebaseDatabase.getInstance().getReference().child("Notes");

        mQueryCurrentUser = mDatabaseCurrentUser.orderByChild("uid").equalTo(currentUserId);

        mDatabaseUsers.keepSynced(true);
        mDatabase.keepSynced(true);


        mNoteList = (RecyclerView)findViewById(R.id.notes_list);
        mNoteList.setHasFixedSize(true);
        mNoteList.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);

        FirebaseRecyclerAdapter<Note, NoteViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Note, NoteViewHolder>(

                Note.class,
                R.layout.note_row,
                NoteViewHolder.class,
                mQueryCurrentUser

        ) {
            @Override
            protected void populateViewHolder(NoteViewHolder viewHolder, Note model, int position) {

                final String post_key = getRef(position).getKey();

                viewHolder.setNote_title(model.getNote_title());
                viewHolder.setDate(model.getDate());
                viewHolder.setDay(model.getDay());

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent singleStudentIntent = new Intent(Notes.this, single_note.class);
                        singleStudentIntent.putExtra("note_id",post_key);
                        startActivity(singleStudentIntent);

                    }
                });

            }
        };

        mNoteList.setAdapter(firebaseRecyclerAdapter);

    }


    public static class NoteViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public NoteViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

        }

        public void setNote_title(String note_title) {

            TextView post_notename = (TextView) mView.findViewById(R.id.post_notename);
            post_notename.setText(note_title);

        }

        public void setDate(String date){

            TextView datetext = (TextView)mView.findViewById(R.id.note_date);
            datetext.setText(date);

        }
        public void setDay(String day){

            TextView daytext = (TextView)mView.findViewById(R.id.note_day);
            daytext.setText(day);

        }

    }



        @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(Notes.this, MainActivity.class);
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

            startActivity(new Intent(Notes.this, NewNote.class));

        }

        return super.onOptionsItemSelected(item);
    }


}
