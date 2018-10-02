package zhenghaozhao.construction_chat;

import java.util.ArrayList;
import java.util.List;

public class GroupData {
    private String groupName;
    private List<UserData> members;
    private UserData host;

    GroupData(String grpName, UserData grpHost, List<UserData> allMembers){
        groupName = grpName;
        host = grpHost;
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

    public UserData getHost() {
        return host;
    }

}
