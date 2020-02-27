package co.raveblue.teacherassistant;

/**
 * Created by HP on 29-10-2017.
 */
public class Tuesday{

    private String tuesdaylect_name;
    private String tuesdayfrom_time;
    private String tuesdayto_time;
    private String tuesdayof_course;

    public Tuesday(){

    }

    public Tuesday(String tuesdaylect_name, String tuesdayfrom_time, String tuesdayto_time, String tuesdayof_course) {
        this.tuesdaylect_name = tuesdaylect_name;
        this.tuesdayfrom_time = tuesdayfrom_time;
        this.tuesdayto_time = tuesdayto_time;
        this.tuesdayof_course = tuesdayof_course;
    }

    public String getTuesdayLect_name() {
        return tuesdaylect_name;
    }

    public String getTuesdayfrom_time() {
        return tuesdayfrom_time;
    }

    public void setTuesdayfrom_time(String tuesdayfrom_time) {
        this.tuesdayfrom_time = tuesdayfrom_time;
    }

    public String getTuesdayto_time() {
        return tuesdayto_time;
    }

    public void setTuesdayto_time(String tuesdayto_time) {
        this.tuesdayto_time = tuesdayto_time;
    }

    public void setTuesdayLect_name(String tuesdaylect_name) {
        this.tuesdaylect_name = tuesdaylect_name;
    }

    public void setTuesdayof_course(String tuesdayof_course){
        this.tuesdayof_course = tuesdayof_course;
    }

    public String getTuesdayof_course(){
        return tuesdayof_course;
    }

}
