package co.raveblue.teacherassistant;

/**
 * Created by HP on 29-10-2017.
 */
public class Friday{

    private String fridaylect_name;
    private String fridayfrom_time;
    private String fridayto_time;
    private String fridayof_course;

    public Friday(){

    }

    public Friday(String fridaylect_name, String fridayfrom_time, String fridayto_time, String fridayof_course) {
        this.fridaylect_name = fridaylect_name;
        this.fridayfrom_time = fridayfrom_time;
        this.fridayto_time = fridayto_time;
        this.fridayof_course = fridayof_course;
    }

    public String getFridayLect_name() {
        return fridaylect_name;
    }

    public String getFridayfrom_time() {
        return fridayfrom_time;
    }

    public void setFridayfrom_time(String fridayfrom_time) {
        this.fridayfrom_time = fridayfrom_time;
    }

    public String getFridayto_time() {
        return fridayto_time;
    }

    public void setFridayto_time(String fridayto_time) {
        this.fridayto_time = fridayto_time;
    }

    public void setfridayLect_name(String fridaylect_name) {
        this.fridaylect_name = fridaylect_name;
    }

    public void setFridayof_course(String fridayof_course){
        this.fridayof_course = fridayof_course;
    }

    public String getFridayof_course(){
        return fridayof_course;
    }

}
