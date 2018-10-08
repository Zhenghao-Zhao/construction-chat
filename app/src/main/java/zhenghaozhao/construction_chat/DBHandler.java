package zhenghaozhao.construction_chat;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import static zhenghaozhao.construction_chat.FeedReaderContract.UserEntry;
import static zhenghaozhao.construction_chat.FeedReaderContract.UserEntry.TABLE_NAME_U;
import static zhenghaozhao.construction_chat.FeedReaderContract.GroupEntry;
import static zhenghaozhao.construction_chat.FeedReaderContract.GroupEntry.TABLE_NAME_G;
import static zhenghaozhao.construction_chat.FeedReaderContract.MembershipEntry;
import static zhenghaozhao.construction_chat.FeedReaderContract.MembershipEntry.TABLE_NAME_M;


public class DBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 3; // needs updating if schema has been changed
    private static final String DATABASE_NAME = "Construction_Chat.db";
    private static final String CREATE_ENTIRES =
            "CREATE TABLE " + TABLE_NAME_U + " (" +
                    UserEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    UserEntry.COLUMN_userName + " TEXT, " +
                    UserEntry.COLUMN_isManager + " INTEGER, " +
                    UserEntry.COLUMN_isOnSite + " INTEGER" +
                    ");";

    private static final String CREATE_GROUP =
            "CREATE TABLE " + TABLE_NAME_G + " (" +
                    GroupEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    GroupEntry.COLUMN_groupName + " TEXT" +
                    ");";

    private static final String CREATE_MEMBERSHIP =
            "CREATE TABLE " + TABLE_NAME_M + " (" +
                    MembershipEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    MembershipEntry.COLUMN_groupName + " TEXT, " +
                    MembershipEntry.COLUMN_userName + " TEXT" +
                    ");";



    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ENTIRES);
        db.execSQL(CREATE_GROUP);
        db.execSQL(CREATE_MEMBERSHIP);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropQuery = "DROP TABLE IF EXISTS " + UserEntry.TABLE_NAME_U;
        db.execSQL(dropQuery);
        dropQuery = "DROP TABLE IF EXISTS " + GroupEntry.TABLE_NAME_G;
        db.execSQL(dropQuery);
        dropQuery = "DROP TABLE IF EXISTS " + MembershipEntry.TABLE_NAME_M;
        db.execSQL(dropQuery);
        onCreate(db);
    }

    public void deleteAll(){
        SQLiteDatabase db = getWritableDatabase();
        String deleteQuery = "delete from " + UserEntry.TABLE_NAME_U;
        db.execSQL(deleteQuery);
        deleteQuery = "delete from " + GroupEntry.TABLE_NAME_G;
        db.execSQL(deleteQuery);
        deleteQuery = "delete from " + MembershipEntry.TABLE_NAME_M;
        db.execSQL(deleteQuery);
        db.close();
    }

    // add a new row to the database
    public void addUser(UserData userData){
        ContentValues values = new ContentValues();
        values.put(UserEntry.COLUMN_userName, userData.getName());
//        values.put(UserEntry.COLUMN_isManager, userData.dbIsManager());
//        values.put(UserEntry.COLUMN_isOnSite, userData.dbIsOnSite());
        SQLiteDatabase myDb = getWritableDatabase();
        myDb.insert(TABLE_NAME_U, null, values);
        myDb.close();

    }

    // this adds a new group and its membership to the database
    public void addGroup (GroupData groupData){
        List<UserData> members = groupData.getMembers();
        ContentValues groupValues = new ContentValues();
        ContentValues membershipValues = new ContentValues();
        groupValues.put(GroupEntry.COLUMN_groupName, groupData.getGroupName());

        for (UserData m : members){
            membershipValues.put(MembershipEntry.COLUMN_groupName, groupData.getGroupName());
            membershipValues.put(MembershipEntry.COLUMN_userName, m.getName());
        }
        SQLiteDatabase myDb = getWritableDatabase();
        myDb.insert(TABLE_NAME_U, null, membershipValues);
        myDb.close();

    }
    //add new members to existing group
    public void addMembership (String groupName, List<UserData> newMembers){
        ContentValues membershipValues = new ContentValues();
        for (UserData m : newMembers){
            membershipValues.put(MembershipEntry.COLUMN_groupName, groupName);
            membershipValues.put(MembershipEntry.COLUMN_userName, m.getName());
        }
        SQLiteDatabase myDb = getWritableDatabase();
        myDb.insert(TABLE_NAME_U, null, membershipValues);
        myDb.close();
    }

    // deletes user name by passing in the user name; success returns true;
    public boolean deleteUserName(String[] names){
        SQLiteDatabase myDb = getWritableDatabase();
        return myDb.delete(TABLE_NAME_U, UserEntry.COLUMN_userName + " LIKE ?", names) > 0;
    }

    public boolean updateUserName(String[] oldUserNames, String newUserName){
        SQLiteDatabase myDb = getWritableDatabase();
        ContentValues newValues = new ContentValues();
        newValues.put(UserEntry.COLUMN_userName, newUserName);
        return myDb.update(TABLE_NAME_U, newValues, UserEntry.COLUMN_userName + " LIKE ?", oldUserNames) > 0;
    }

    public String readAndPrint(String[] requestedNames){
        SQLiteDatabase myDb = getWritableDatabase();
        String results = "";
        String[] projection = {BaseColumns._ID, UserEntry.COLUMN_userName,
                UserEntry.COLUMN_isManager, UserEntry.COLUMN_isOnSite};
        String sortOrder = UserEntry.COLUMN_userName + " DESC";

        Cursor c = myDb.query(TABLE_NAME_U, projection, UserEntry.COLUMN_userName + " LIKE ?", requestedNames, null, null, sortOrder);
        c.moveToFirst();
        while (!c.isAfterLast()){
            if (c.getString(c.getColumnIndex(UserEntry.COLUMN_userName)) != null){
                results += c.getString(c.getColumnIndex(UserEntry.COLUMN_userName));
                results += "\n";
            }
        }
        c.close();
        return results;
    }

    public List<UserData> allUserData_create(){
        SQLiteDatabase myDb = getWritableDatabase();
        List<UserData> userDataList = new ArrayList<>();
        UserData temp;
        String[] projection = {UserEntry.COLUMN_userName,
                UserEntry.COLUMN_isManager, UserEntry.COLUMN_isOnSite};
        Cursor c = myDb.query(TABLE_NAME_U, projection, null, null, null, null, null);
        c.moveToFirst();
        while (!c.isAfterLast()){
            if (c.getString(c.getColumnIndex(UserEntry.COLUMN_userName)) != null){
                temp = new UserData(c.getString(c.getColumnIndex(UserEntry.COLUMN_userName)),
                        c.getInt(c.getColumnIndex(UserEntry.COLUMN_isManager)),
                        c.getInt(c.getColumnIndex(UserEntry.COLUMN_isOnSite)));
                userDataList.add(temp);
            }
            c.moveToNext();
        }
        c.close();
        return userDataList;

    }

    public List<UserData> managerData_create(){
        SQLiteDatabase myDb = getWritableDatabase();
        List<UserData> userDataList = new ArrayList<>();
        UserData temp;
        String[] projection = {UserEntry.COLUMN_userName,
                UserEntry.COLUMN_isManager, UserEntry.COLUMN_isOnSite};
        Cursor c = myDb.query(TABLE_NAME_U, projection, null, null, null, null, null);
        c.moveToFirst();
        while (!c.isAfterLast()){
            if (c.getString(c.getColumnIndex(UserEntry.COLUMN_userName)) != null && c.getInt(c.getColumnIndex(UserEntry.COLUMN_isManager)) == 1){
                temp = new UserData(c.getString(c.getColumnIndex(UserEntry.COLUMN_userName)),
                        c.getInt(c.getColumnIndex(UserEntry.COLUMN_isManager)),
                        c.getInt(c.getColumnIndex(UserEntry.COLUMN_isOnSite)));
                userDataList.add(temp);
            }
            c.moveToNext();
        }
        c.close();
        return userDataList;
    }

    public List<UserData> workerData_create(){
        SQLiteDatabase myDb = getWritableDatabase();
        List<UserData> userDataList = new ArrayList<>();
        UserData temp;
        String[] projection = {UserEntry.COLUMN_userName,
                UserEntry.COLUMN_isManager, UserEntry.COLUMN_isOnSite};
        Cursor c = myDb.query(TABLE_NAME_U, projection, null, null, null, null, null);
        c.moveToFirst();
        while (!c.isAfterLast()){
            if (c.getString(c.getColumnIndex(UserEntry.COLUMN_userName)) != null && c.getInt(c.getColumnIndex(UserEntry.COLUMN_isManager)) == 0){
                temp = new UserData(c.getString(c.getColumnIndex(UserEntry.COLUMN_userName)),
                        c.getInt(c.getColumnIndex(UserEntry.COLUMN_isManager)),
                        c.getInt(c.getColumnIndex(UserEntry.COLUMN_isOnSite)));
                userDataList.add(temp);
            }
            c.moveToNext();
        }
        c.close();
        return userDataList;
    }
}
