package zhenghaozhao.construction_chat;

import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory{
    private String mParam;


    public ViewModelFactory(String param) {
        mParam = param;
    }

    @NonNull
    @Override
    public <T extends android.arch.lifecycle.ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MyViewModel(mParam);
    }
}
