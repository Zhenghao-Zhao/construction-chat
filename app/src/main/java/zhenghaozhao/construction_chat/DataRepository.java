package zhenghaozhao.construction_chat;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
    Contains methods for interacting with FireStore (online database)
 */
public class DataRepository {
    private static final String TAG = "DataRepository";

    //asynchronously retrieve all documents
    private static CollectionReference userDataRef;
    private static CollectionReference groupDataRef;
    private static CollectionReference P2PDataRef;

    //data lists
    private static List<UserData> userData  = new ArrayList<>();
    private static List<UserData> managerData = new ArrayList<>();
    private static List<UserData> workerData = new ArrayList<>();
    private static List<UserData> siteData = new ArrayList<>();

    private UserData

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
        final List<UserData> dataList = new ArrayList<>();
        DocumentReference docRef = userDataRef.document(userName);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    UserData userData = documentSnapshot.toObject(UserData.class);
                    dataList.add(userData);
                    Log.d(TAG, "onSuccess: " + userData.getName());
                }
            }
        });
        return dataList.get(0);
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
}
