package co.raveblue.teacherassistant;

/**
 * Created by HP on 29-10-2017.
 */
public class Student{

    private String st_name;
    private String st_class;
    private String image;

    public Student(){

    }

    public Student(String st_name, String st_class, String image) {
        this.st_name = st_name;
        this.st_class = st_class;
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSt_name() {
        return st_name;
    }

    public void setSt_name(String st_name) {
        this.st_name = st_name;
    }

    public String getSt_class() {
        return st_class;
    }

    public void setSt_class(String st_class) {
        this.st_class = st_class;
    }
}
