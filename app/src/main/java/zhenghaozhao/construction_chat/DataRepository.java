package zhenghaozhao.construction_chat;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/*
    Contains methods for interacting with FireStore (online database)
 */
public class DataRepository {
    private static final String TAG = "DataRepository";

    //asynchronously retrieve all documents
    private static CollectionReference userDataRef;
    private static CollectionReference groupDataRef;
    public static CollectionReference P2PDataRef;

    //data lists
    private static List<UserData> userData  = new ArrayList<>();
    private static List<UserData> managerData = new ArrayList<>();
    private static List<UserData> workerData = new ArrayList<>();
    private static List<UserData> siteData = new ArrayList<>();

    private static UserData myData = new UserData("Smoove", true, true);

    DataRepository(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        userDataRef = db.collection("UserData_Test");
        groupDataRef = db.collection("GroupData_Test");
        P2PDataRef = db.collection("P2PData_Test");
    }

    public static void uploadUser(UserData userData){
        userDataRef.add(userData);
    }

    // upload a group data to the database
    public static void uploadGroupData(GroupData groupData){
        groupDataRef.add(groupData);
    }

    public static void uploadP2PChatData(P2PChat chat){
        P2PDataRef.add(chat);
    }

    // fetches all groups specified by the user
    public static List<GroupData> fetchGroupData(UserData userData){
        List<String> groupNames = userData.getGroupNames();
        final List<GroupData> groupData = new ArrayList<>();
        for (String name : groupNames) {
            groupDataRef.whereEqualTo("groupName", name)
                    .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot querySnapshots) {
                    System.out.println("Its successful...");
                    for (QueryDocumentSnapshot documentSnapshot : querySnapshots) {
                        GroupData data = documentSnapshot.toObject(GroupData.class);
                        groupData.add(data);
                    }
                    for (GroupData data : groupData) {
                        System.out.println("Group name: " + data.getGroupName());
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(TAG, "onFailure: " + e.toString());
                }
            });
        }
        return groupData;
    }

    //currently considering userName as a primary key
    public static UserData fetchUserData(final String userName){
        final List<UserData> container = new ArrayList<>();
        userDataRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots){
                    if (snapshot.getData().get("name").equals(userName)){
                        UserData userData = snapshot.toObject(UserData.class);
                        container.add(userData);
                    }
                }
            }
        });
        return container.size() > 0? container.get(0) : new UserData("Your_Name", false, false);
    }

    public static List<P2PChat> fetchP2PData(final String name){
        final List<P2PChat> container = new ArrayList<>();
        System.out.println("the name is : " + name);
        P2PDataRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots){
                    if (snapshot.getData().get("receiver").equals(name) ||
                            snapshot.getData().get("sender").equals(name)){
                        P2PChat chatData = snapshot.toObject(P2PChat.class);
                        System.out.println(chatData.getSender());
                        container.add(chatData);
                    }
                }

            }
        });
        System.out.println(container.size());

        return container;
    }

    // this method sorts retrieved data into different categories
    public static void addRepoData(List<UserData> allData){
        userData = new ArrayList<>(allData);
        managerData = new ArrayList<>();
        workerData = new ArrayList<>();
        siteData = new ArrayList<>();

        for (UserData user : allData){
            if (user.isManager()){
                managerData.add(user);
            }else {
                workerData.add(user);
            }
        }
    }

    public static List<UserData> getUserData() {
        return userData;
    }

    public static List<UserData> getManagerData() {
        return managerData;
    }

    public static List<UserData> getWorkerData() {
        return workerData;
    }

    public static List<UserData> getSiteData() {
        return siteData;
    }

    public static void setMyData(UserData myData) {
        DataRepository.myData = myData;
    }

    public static UserData getMyData() {
        return myData;
    }
}
