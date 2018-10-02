package zhenghaozhao.construction_chat;

import android.content.Context;
import android.widget.ImageButton;
import android.widget.ImageView;

public class UserData {
    private String name;
    private boolean isManager;
    private boolean isOnSite;
    private int userAvatar; // assigned in HomeActivity class

    public UserData(String name, boolean isManager, boolean isOnSite){
        this.name = name;
        this.isManager = isManager;
        this.isOnSite = isOnSite;
    }

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

    public void setManager(boolean isManager){
        this.isManager = isManager;
    }

    public void setOnSite(boolean isOnSite){
        this.isOnSite = isOnSite;
    }

    public int getAvatar(){return userAvatar;}

    public int dbIsManager() {return isManager? 1 : 0;}

    public int dbIsOnSite() {return isOnSite? 1 : 0;}
}
