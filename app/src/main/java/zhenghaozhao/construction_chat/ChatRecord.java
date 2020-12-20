package zhenghaozhao.construction_chat;

import java.util.ArrayList;
import java.util.List;

// stores all names;
public class ChatRecord {
    private List<String> conversers = new ArrayList<>();
    String id;

    ChatRecord(String id, List<String> conversers){
        this.conversers = conversers;
        this.id = id;
    }
    ChatRecord(){}

    public List<String> getConversers() {
        return conversers;
    }

    public String getId() {
        return id;
    }

    public void addConverser(String id){
        conversers.add(id);
    }
}
