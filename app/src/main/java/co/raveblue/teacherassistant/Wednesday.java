package co.raveblue.teacherassistant;

/**
 * Created by HP on 29-10-2017.
 */
public class Wednesday{

    private String wednesdaylect_name;
    private String wednesdayfrom_time;
    private String wednesdayto_time;
    private String wednesdayof_course;

    public Wednesday(){

    }

    public Wednesday(String wednesdaylect_name, String wednesdayfrom_time, String wednesdayto_time, String wednesdayof_course) {
        this.wednesdaylect_name = wednesdaylect_name;
        this.wednesdayfrom_time = wednesdayfrom_time;
        this.wednesdayto_time = wednesdayto_time;
        this.wednesdayof_course = wednesdayof_course;
    }

    public String getWednesdayLect_name() {
        return wednesdaylect_name;
    }

    public String getWednesdayfrom_time() {
        return wednesdayfrom_time;
    }

    public void setWednesdayfrom_time(String wednesdayfrom_time) {
        this.wednesdayfrom_time = wednesdayfrom_time;
    }

    public String getWednesdayto_time() {
        return wednesdayto_time;
    }

    public void setWednesdayto_time(String wednesdayto_time) {
        this.wednesdayto_time = wednesdayto_time;
    }

    public void setWednesdayLect_name(String wednesdaylect_name) {
        this.wednesdaylect_name = wednesdaylect_name;
    }

    public void setWednesdayof_course(String wednesdayof_course){
        this.wednesdayof_course = wednesdayof_course;
    }

    public String getWednesdayof_course(){
        return wednesdayof_course;
    }

}
