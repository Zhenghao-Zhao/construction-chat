package zhenghaozhao.construction_chat;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class FBViewModel extends ViewModel {
    private static final CollectionReference userDataRef =
            FirebaseFirestore.getInstance().collection("UserData_Test");

    private final FirebaseQueryLiveData liveData = new FirebaseQueryLiveData(userDataRef);


    @NonNull
    public LiveData<QuerySnapshot> getQuerySnapshotLiveData() {
        return liveData;
    }



}
