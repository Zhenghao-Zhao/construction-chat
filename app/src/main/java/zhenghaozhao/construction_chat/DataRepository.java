package zhenghaozhao.construction_chat;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataRepository {
    private static final String TAG = "DataRepository";

    //asynchronously retrieve all documents
    private FirebaseFirestore db;
    private CollectionReference userDataRef;
    private CollectionReference groupDataRef;

    //data lists
    public static List<UserData> userData  = new ArrayList<>();
    public static List<UserData> managerData = new ArrayList<>();
    public static List<UserData> workerData = new ArrayList<>();
    public static List<UserData> siteData = new ArrayList<>();

    DataRepository(){
        db = FirebaseFirestore.getInstance();
        userDataRef = db.collection("UserData_Test");
        groupDataRef = db.collection("GroupData_Test");
    }

    public void uploadUser(UserData userData){
        userDataRef.add(userData);
    }

    // upload a group data to the database
    public void uploadGroupData(GroupData groupData){
        groupDataRef.add(groupData);
    }

    // fetches all groups specified by the user
    public List<GroupData> fetchGroupData(UserData userData){
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

    public void addRepoData(List<UserData> allData){
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

    public List<UserData> getUserData() {
        return userData;
    }

    public List<UserData> getManagerData() {
        return managerData;
    }

    public List<UserData> getWorkerData() {
        return workerData;
    }

    public List<UserData> getSiteData() {
        return siteData;
    }
}
