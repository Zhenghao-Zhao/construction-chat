package zhenghaozhao.construction_chat;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory{
    private String mParam;


    public ViewModelFactory(String param) {
        mParam = param;
    }

    @NonNull
    @Override
    public <T extends androidx.lifecycle.ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MyViewModel(mParam);
    }
}
