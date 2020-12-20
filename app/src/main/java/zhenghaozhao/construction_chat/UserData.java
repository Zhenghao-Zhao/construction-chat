package zhenghaozhao.construction_chat;

import java.util.UUID;

public class UserData {
    private String name;
    private boolean isManager;
    private boolean isOnSite;
    private String ID;


    // groupNames: contains at least one element - user's current site
    public UserData(String name, boolean isManager, boolean isOnSite, String id){
        this.name = name;
        this.isManager = isManager;
        this.isOnSite = isOnSite;
        this.ID = id;
    }

    public UserData(String name, boolean isManager, boolean isOnSite){
        this.name = name;
        this.isManager = isManager;
        this.isOnSite = isOnSite;
        ID = UUID.randomUUID().toString();
    }

    // required by Firestore
    public UserData(){};


    public String getName() {
        return name;
    }

    public String getID() {
        return ID;
    }

    public boolean isManager() {
        return isManager;
    }

    public boolean isOnSite(){
        return isOnSite;
    }

    public void setManager(boolean isManager){
        this.isManager = isManager;
    }

    public void setOnSite(boolean isOnSite){
        this.isOnSite = isOnSite;
    }

}
