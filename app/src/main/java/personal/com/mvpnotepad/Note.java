package personal.com.mvpnotepad;

public class Note {
    private String Content;
    private String Date;
    private String Name;
    private int id;
    public Note(String Content, String Date, String Name,int id){
        this.Content = Content;
        this.Date = Date;
        this.Name = Name;
        this.id = id;
    }

    public Note(){

    }
    public String getContent() {
        return Content;
    }
    public void setContent(String content) {
        Content = content;
    }
    public String getDate() {
        return Date;
    }
    public void setDate(String date) {
        Date = date;
    }
    public String getName() {
        return Name;
    }
    public void setName(String name) {
        Name = name;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
}
