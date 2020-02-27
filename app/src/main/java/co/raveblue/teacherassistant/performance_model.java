package co.raveblue.teacherassistant;

/**
 * Created by HP on 29-10-2017.
 */
public class performance_model {

    private String st_name;
    private String st_roll;

    public performance_model(){

    }

    public performance_model(String st_name, String st_roll) {
        this.st_name = st_roll;
    }

    public String getSt_name() {
        return st_name;
    }

    public void setSt_name(String st_name) {
        this.st_name = st_name;
    }

    public String getSt_roll() {
        return st_roll;
    }

    public void setSt_roll(String st_roll) {
        this.st_roll = st_roll;
    }
}
