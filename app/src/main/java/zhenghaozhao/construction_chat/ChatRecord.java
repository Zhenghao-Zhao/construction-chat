package zhenghaozhao.construction_chat;

import java.util.ArrayList;
import java.util.List;

// stores all names;
public class ChatRecord {
    List<String> conversers = new ArrayList<>();
    String name;

    ChatRecord(String name, List<String> conversers){
        this.conversers = conversers;
        this.name = name;
    }
    ChatRecord(){}

    public List<String> getConversers() {
        return conversers;
    }

    public String getName() {
        return name;
    }

    public void addConverser(String name){
        conversers.add(name);
    }
}
