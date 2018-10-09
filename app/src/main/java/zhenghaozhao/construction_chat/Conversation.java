package zhenghaozhao.construction_chat;

import java.util.ArrayList;
import java.util.List;

// a Conversation is a series of messages between a sender and receiver
public class Conversation {
    private List<P2PChat> chats;
    private String other;

    Conversation(List<P2PChat> chats){
        this.chats = chats;
        putOther(chats.get(0));
    }

    Conversation(P2PChat chat){
        this.chats = new ArrayList<>();
        chats.add(chat);
        putOther(chat);
    }

    Conversation(){};

    public void setChats(List<P2PChat> chats) {
        this.chats = chats;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public void addChat(P2PChat chat){
        chats.add(chat);
    }

    public String getOther() {
        return other;
    }

    public List<P2PChat> getChats() {
        return chats;
    }

    public void putOther(P2PChat chat){
        if (chat.getSender().equals(DataRepository.getMyData().getName())){
            other = chat.getReceiver();
        }else{
            other = chat.getSender();
        }
    }
}
