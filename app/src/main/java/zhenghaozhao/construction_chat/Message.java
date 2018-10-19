package zhenghaozhao.construction_chat;

public class Message {
    private String text;
    private boolean isMyMessage;
    private String name;
    private boolean manager;


    public Message (String text, boolean isMyMessage, String name, boolean manager){
        this.text = text;
        this.isMyMessage = isMyMessage;
        this.name = name;
        this.manager = manager;
    }

    public Message (String text, boolean isMyMessage, String name){
        this.text = text;
        this.isMyMessage = isMyMessage;
        this.name = name;
    }

    public boolean isMyMessage() {
        return isMyMessage;
    }

    public String getText (){
        return text;
    }

    public String getName() {
        return name;
    }

    public boolean isManager() {
        return manager;
    }
}
