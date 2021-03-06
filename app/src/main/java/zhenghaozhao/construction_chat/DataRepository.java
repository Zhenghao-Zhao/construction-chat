package zhenghaozhao.construction_chat;

import androidx.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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
    public static CollectionReference userDataRef;
    private static CollectionReference groupDataRef;
    private static CollectionReference P2PDataRef;
    public static CollectionReference chatDataRef;
    public static CollectionReference credDataRef;

    //data lists
    private static List<UserData> userData  = new ArrayList<>();
    private static List<UserData> managerData = new ArrayList<>();
    private static List<UserData> workerData = new ArrayList<>();
    private static List<UserData> siteData = new ArrayList<>();

    private static UserData myData = fetchUserData("Yuefang");
    private static ChatRecord myChatRecord = new ChatRecord(myData.getID(), new ArrayList<String>());

    DataRepository(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        userDataRef = db.collection("UserData_Test");
        groupDataRef = db.collection("GroupData_Test");
        P2PDataRef = db.collection("P2PData_Test");
        chatDataRef = db.collection("ChatRecord_Test");
        credDataRef = db.collection("Credential_Test");

    }

    public static void uploadUser(UserData userData){
        userDataRef.document(userData.getID()).set(userData);
    }

    // upload a group data to the database
    public static void uploadGroupData(GroupData groupData){
        groupDataRef.add(groupData);
    }

    public static void uploadP2PChatData(P2PChat chat){
        P2PDataRef.add(chat);
    }

    public static void uploadRecord(final ChatRecord chatRecord){
        chatDataRef.document(chatRecord.getId()).set(chatRecord);
    }

    //currently considering name as a primary key
    public static UserData fetchUserData(final String userName){
        final UserData[] rst = new UserData[1]; rst[0] = new UserData("Your_name", false, false);
        final DocumentReference docRef = DataRepository.userDataRef.document(userName);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        rst[0] = document.toObject(UserData.class);
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
        return rst[0];
    }

    public static List<P2PChat> fetchP2PData(final String name){
        final List<P2PChat> container = new ArrayList<>();
        P2PDataRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots){
                    if (snapshot.getData().get("receiverId").equals(name) ||
                            snapshot.getData().get("senderId").equals(name)){
                        P2PChat chatData = snapshot.toObject(P2PChat.class);
                        System.out.println(chatData.getSenderId());
                        container.add(chatData);
                    }
                }

            }
        });
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

    public static ChatRecord getMyChatRecord() {
        return myChatRecord;
    }

    public static void setMyData(UserData myData) {
        DataRepository.myData = myData;
    }

    public static void setMyChatRecord(ChatRecord myChatRecord) {
        DataRepository.myChatRecord = myChatRecord;
    }

    public static UserData getMyData() {
        return myData;
    }

}
