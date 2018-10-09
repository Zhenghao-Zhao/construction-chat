package zhenghaozhao.construction_chat;

import java.util.List;

// stores all conversations the user has.
public class ChatRecord {
    List<Conversation> conversations;
    String userName;

    ChatRecord(List<Conversation> conversations, String userName){
        this.conversations = conversations;
        this.userName = userName;
    }
    ChatRecord(){}

    public List<Conversation> getConversations() {
        return conversations;
    }

    public String getUserName() {
        return userName;
    }
}
