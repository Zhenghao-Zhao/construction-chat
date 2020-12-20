package zhenghaozhao.construction_chat;

/*
    A P2PChat contains a primary key from two users (one being senderId, the other being receiverId),
    a message and index (used to maintain the order of messages)
 */
public class P2PChat {
    private String senderId; //primary key
    private String receiverId;
    private String message;
    private int index;

    P2PChat(){}

    P2PChat(String senderId, String receiverId, String message, int index){
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.message = message;
        this.index = index;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public String getMessage() {
        return message;
    }

    public int getIndex() {
        return index;
    }
}
