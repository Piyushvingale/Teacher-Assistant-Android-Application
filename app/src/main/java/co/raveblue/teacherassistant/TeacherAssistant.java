package co.raveblue.teacherassistant;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by HP on 24-12-2017.
 */

public class TeacherAssistant extends Application{

    @Override
    public void onCreate(){
        super.onCreate();

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

    }

}
