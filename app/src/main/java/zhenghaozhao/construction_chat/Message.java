package zhenghaozhao.construction_chat;

public class Message {
    private String text;
    private boolean isMyMessage;
    private UserData data;


    public Message (String text, boolean isMyMessage, UserData data){
        this.text = text;
        this.isMyMessage = isMyMessage;
        this.data = data;
    }

    public String getText (){
        return text;
    }

    public UserData getData() {
        return data;
    }

    public boolean checkIsMyMessage (){
        return isMyMessage;
    }


}
