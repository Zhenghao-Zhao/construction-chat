package zhenghaozhao.construction_chat;

import java.util.ArrayList;
import java.util.List;

public class GroupData {
    private String groupName;
    private List<UserData> members;

    public GroupData(){};

    GroupData(String grpName, List<UserData> allMembers){
        groupName = grpName;
        members = new ArrayList<>(allMembers);

    }

    public void add(List<UserData> newMembers){
        members.addAll(newMembers);

    }

    public void add(UserData member){
        members.add(member);

    }

    public List<UserData> getMembers() {
        return members;
    }

    public String getGroupName() {
        return groupName;
    }




}
