package zhenghaozhao.construction_chat;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class MyViewModel extends android.arch.lifecycle.ViewModel {

    private FirebaseQueryLiveData liveData;

    MyViewModel(String collection){
        Query ref = FirebaseFirestore.getInstance().collection(collection);
        if (collection.equals("P2PData_Test")) {
            ref = ref.orderBy("index");
        }
        liveData = new FirebaseQueryLiveData(ref);
    }


    @NonNull
    public LiveData<QuerySnapshot> getQuerySnapshotLiveData() {
        return liveData;
    }



}
