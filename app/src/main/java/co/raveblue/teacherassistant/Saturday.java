package co.raveblue.teacherassistant;

/**
 * Created by HP on 29-10-2017.
 */
public class Saturday{

    private String saturdaylect_name;
    private String saturdayfrom_time;
    private String saturdayto_time;
    private String saturdayof_course;

    public Saturday(){

    }

    public Saturday(String saturdaylect_name, String saturdayfrom_time, String saturdayto_time, String saturdayof_course) {
        this.saturdaylect_name = saturdaylect_name;
        this.saturdayfrom_time = saturdayfrom_time;
        this.saturdayto_time = saturdayto_time;
        this.saturdayof_course = saturdayof_course;
    }

    public String getSaturdayLect_name() {
        return saturdaylect_name;
    }

    public String getSaturdayfrom_time() {
        return saturdayfrom_time;
    }

    public void setSaturdayfrom_time(String saturdayfrom_time) {
        this.saturdayfrom_time = saturdayfrom_time;
    }

    public String getSaturdayto_time() {
        return saturdayto_time;
    }

    public void setSaturdayto_time(String saturdayto_time) {
        this.saturdayto_time = saturdayto_time;
    }

    public void setSaturdayLect_name(String saturdaylect_name) {
        this.saturdaylect_name = saturdaylect_name;
    }

    public void setSaturdayof_course(String saturdayof_course){
        this.saturdayof_course = saturdayof_course;
    }

    public String getSaturdayof_course(){
        return saturdayof_course;
    }

}
