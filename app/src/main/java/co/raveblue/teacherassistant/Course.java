package co.raveblue.teacherassistant;

/**
 * Created by HP on 29-10-2017.
 */
public class Course{

    private String course_name;

    public Course(){

    }

    public Course(String course_name) {
        this.course_name = course_name;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }
}
