package zhenghaozhao.construction_chat;

import java.util.List;

public class SiteData {
    String name;
    List<UserData> onSiteWorkers;

    public SiteData(String siteName, List<UserData> workers){
        name = siteName;
        onSiteWorkers = workers;
    }


    public String getName() {
        return name;
    }

    public List<UserData> getOnSiteWorkers() {
        return onSiteWorkers;
    }
}
