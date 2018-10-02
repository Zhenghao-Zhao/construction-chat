package zhenghaozhao.construction_chat;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class DataViewModel extends AndroidViewModel {
    LiveData<List<UserData>> allUserData;
    DataRepository repository;
    public DataViewModel(@NonNull Application application) {
        super(application);
    }
}
