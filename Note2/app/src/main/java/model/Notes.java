package model;

public class Notes {
    private String title;       //标题
    private int id;             //便签在数据库里的排序（一般按照创建时间）
    private String content;     //便签内容节选
    private String time;        //最后一次修改的时间

    public Notes(String title,int id,String content,String time){
        this.content=content;
        this.time=time;
        this.id=id;
        this.title=title;
    }
    public Notes(String title,String content,String time){
        this.title=title;
        this.content=content;
        this.time=time;
    }
    public Notes(int id,String title,String time){
        this.id=id;
        this.title=title;
        this.time=time;
    }
    public Notes(String title,String content){
        this.title=title;
        this.content=content;
    }


    public int getId(){
        return id;
    }
    public String getTitle(){
        return title;
    }
    public String getContent(){
        return content;
    }

    public String getTime() {
        return time;
    }
}
