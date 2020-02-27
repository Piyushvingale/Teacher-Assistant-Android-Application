package co.raveblue.teacherassistant;

/**
 * Created by HP on 29-10-2017.
 */
public class Thrusday{

    private String thrusdaylect_name;
    private String thrusdayfrom_time;
    private String thrusdayto_time;
    private String thrusdayof_course;

    public Thrusday(){

    }

    public Thrusday(String thrusdaylect_name, String thrusdayfrom_time, String thrusdayto_time, String thrusdayof_course) {
        this.thrusdaylect_name = thrusdaylect_name;
        this.thrusdayfrom_time = thrusdayfrom_time;
        this.thrusdayto_time = thrusdayto_time;
        this.thrusdayof_course = thrusdayof_course;
    }

    public String getThrusdayLect_name() {
        return thrusdaylect_name;
    }

    public String getThrusdayfrom_time() {
        return thrusdayfrom_time;
    }

    public void setThrusdayfrom_time(String thrusdayfrom_time) {
        this.thrusdayfrom_time = thrusdayfrom_time;
    }

    public String getThrusdayto_time() {
        return thrusdayto_time;
    }

    public void setThrusdayto_time(String thrusdayto_time) {
        this.thrusdayto_time = thrusdayto_time;
    }

    public void setThrusdayLect_name(String thrusdaylect_name) {
        this.thrusdaylect_name = thrusdaylect_name;
    }

    public void setThrusdayof_course(String thrusdayof_course){
        this.thrusdayof_course = thrusdayof_course;
    }

    public String getThrusdayof_course(){
        return thrusdayof_course;
    }

}
