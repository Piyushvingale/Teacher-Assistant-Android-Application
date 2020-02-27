package co.raveblue.teacherassistant;

/**
 * Created by HP on 29-10-2017.
 */
public class Monday{

    private String mondaylect_name;
    private String mondayfrom_time;
    private String mondayto_time;
    private String mondayof_course;

    public Monday(){

    }

    public Monday(String mondaylect_name, String mondayfrom_time, String mondayto_time, String mondayof_course) {
        this.mondaylect_name = mondaylect_name;
        this.mondayfrom_time = mondayfrom_time;
        this.mondayto_time = mondayto_time;
        this.mondayof_course = mondayof_course;
    }

    public String getMondayLect_name() {
        return mondaylect_name;
    }

    public String getMondayfrom_time() {
        return mondayfrom_time;
    }

    public void setMondayfrom_time(String mondayfrom_time) {
        this.mondayfrom_time = mondayfrom_time;
    }

    public String getMondayto_time() {
        return mondayto_time;
    }

    public void setMondayto_time(String mondayto_time) {
        this.mondayto_time = mondayto_time;
    }

    public void setMondayLect_name(String mondaylect_name) {
        this.mondaylect_name = mondaylect_name;
    }

    public void setMondayof_course(String mondayof_course){
        this.mondayof_course = mondayof_course;
    }

    public String getMondayof_course(){
        return mondayof_course;
    }

}
