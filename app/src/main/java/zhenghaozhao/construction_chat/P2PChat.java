package zhenghaozhao.construction_chat;

public class P2PChat {
    private UserData sender;
    private UserData receiver;
    private String message;
    private int index;

    P2PChat(){}

    P2PChat(UserData sender, UserData receiver, String message, int index){
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.index = index;
    }

    public UserData getReceiver() {
        return receiver;
    }

    public UserData getSender() {
        return sender;
    }

    public String getMessage() {
        return message;
    }

    public int getIndex() {
        return index;
    }
}
