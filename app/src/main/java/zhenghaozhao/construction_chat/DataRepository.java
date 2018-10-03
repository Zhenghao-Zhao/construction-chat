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
    private static final String TAG = "HomePage";

    //asynchronously retrieve all documents
    private FirebaseFirestore db;
    private CollectionReference userDataRef;

    //data lists
    private List<UserData> userData  = new ArrayList<>();
    private List<UserData> managerData = new ArrayList<>();
    private List<UserData> workerData = new ArrayList<>();
    private List<UserData> siteData = new ArrayList<>();

    DataRepository(){
        db = FirebaseFirestore.getInstance();
        userDataRef = db.collection("UserData");
    }

    public void uploadUser(UserData userData){

        db.collection("UserData_Test").add(userData);
    }

    public void fetchData(){
        final List<UserData> allUserData = new ArrayList<>();
        userDataRef.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                            UserData data = documentSnapshot.toObject(UserData.class);
                            allUserData.add(data);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, e.toString());
                    }
                });

        userData = new ArrayList<>(allUserData);
        managerData = new ArrayList<>();
        workerData = new ArrayList<>();
        siteData = new ArrayList<>();

        for (UserData user : allUserData){
            if (user.isManager()){
                managerData.add(user);
            }else {
                workerData.add(user);
            }
        }

    }

    // upload a group data to the database
    public void uploadGroup(GroupData groupData){
        List<UserData> groupMembers = groupData.getMembers();
        DocumentReference document = db.collection("GroupData_Test")
                .document(groupData.getGroupName());

        CollectionReference collection = document
                .collection("Group_UserData");

        for (UserData member : groupMembers){
            collection.add(member).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    Log.d(TAG, "onSuccess: Successful!!!!!!!!!!");
                }
            });
        }

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
