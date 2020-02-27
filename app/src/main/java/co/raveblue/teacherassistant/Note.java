package co.raveblue.teacherassistant;

/**
 * Created by HP on 10-11-2017.
 */

public class Note {

    private String note_title;
    private String day;
    private String date;

    public Note(){


    }

    public Note(String note_title, String day, String date) {
        this.note_title = note_title;
        this.day = day;
        this.date = date;

    }

    public String getNote_title() {
        return note_title;
    }

    public void setNote_title(String note_title) {
        this.note_title = note_title;
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
