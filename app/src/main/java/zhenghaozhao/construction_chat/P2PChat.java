package zhenghaozhao.construction_chat;

public class P2PChat {
    private String sender; //primary key
    private String receiver;
    private String message;
    private int index;

    P2PChat(){}

    P2PChat(String sender, String receiver, String message, int index){
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.index = index;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getMessage() {
        return message;
    }

    public int getIndex() {
        return index;
    }
}
