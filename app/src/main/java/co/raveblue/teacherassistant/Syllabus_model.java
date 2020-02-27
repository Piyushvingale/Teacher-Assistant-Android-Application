package co.raveblue.teacherassistant;

/**
 * Created by HP on 14-01-2018.
 */

public class Syllabus_model {

    private String url;
    private String filename;
    private String day;
    private String date;

    public Syllabus_model(){

    }

    public Syllabus_model(String url, String filename, String day, String date)
    {
        this.url = url;
        this.filename = filename;
        this.day = day;
        this.date = date;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getFilename()
    {
        return filename;
    }

    public void setFilename(String filename)
    {
        this.filename = filename;
    }

    public String getDay()
    {
        return day;
    }

    public void setDay(String day)
    {
        this.day = day;
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

}
