package zhenghaozhao.construction_chat;

import java.util.List;

public class UserData {
    private String name;
    private boolean isManager;
    private boolean isOnSite;
    private List<String> groupNames;
    private int avatar; // assigned in HomeActivity class

    // groupNames: contains at least one element - user's current site
    public UserData(String name, boolean isManager, boolean isOnSite, List<String> groupNames){
        this.name = name;
        this.isManager = isManager;
        this.isOnSite = isOnSite;
        this.groupNames = groupNames;
    }

    public UserData(String name, boolean isManager, boolean isOnSite){
        this.name = name;
        this.isManager = isManager;
        this.isOnSite = isOnSite;
    }

    // required by Firestore
    public UserData(){};

    public UserData(String name, int Manager, int OnSite){
        this.name = name;
        this.isManager = (Manager == 1);
        this.isOnSite = (OnSite == 1);
    }

    public String getName() {
        return name;
    }

    public boolean isManager() {
        return isManager;
    }

    public boolean isOnSite(){
        return isOnSite;
    }

    public List<String> getGroupNames() {
        return groupNames;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setGroupNames(List<String> groupNames) {
        this.groupNames = groupNames;
    }

    public void setManager(boolean isManager){
        this.isManager = isManager;
    }

    public void setOnSite(boolean isOnSite){
        this.isOnSite = isOnSite;
    }

    public int dbIsManager() {return isManager? 1 : 0;}

    public int dbIsOnSite() {return isOnSite? 1 : 0;}
}
