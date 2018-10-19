package zhenghaozhao.construction_chat;

import android.provider.BaseColumns;

public class FeedReaderContract {
    private FeedReaderContract (){};

    // userProfile table entries
    public static class UserEntry implements BaseColumns {
        public static final String TABLE_NAME_U = "userProfile";
        public static final String _ID ="_id";
        public static final String COLUMN_userName = "name";
        public static final String COLUMN_isOnSite = "isOnSite";
        public static final String COLUMN_isManager = "isManager";
    }

    public static class GroupEntry implements BaseColumns {
        public static final String TABLE_NAME_G = "groupProfile";
        public static final String _ID = "_id";
        public static final String COLUMN_groupName = "groupName";
    }

    public static class MembershipEntry implements BaseColumns {
        public static final String TABLE_NAME_M = "membershipProfile";
        public static final String _ID = "_id";
        public static final String COLUMN_groupName = "groupName";
        public static final String COLUMN_userName = "name";
    }

    public static class SiteEntry implements BaseColumns{
        public static final String TABLE_NAME_S = "siteProfile";
        public static final String _ID ="_id";
        public static final String COLUMN_siteName = "siteName";
        public static final String COLUMN_siteAddress = "siteAddress";
    }

}
