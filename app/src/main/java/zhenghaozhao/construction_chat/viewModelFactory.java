package zhenghaozhao.construction_chat;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

public class viewModelFactory extends ViewModelProvider.NewInstanceFactory{
    private String mParam;


    public viewModelFactory(String param) {
        mParam = param;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new viewModel(mParam);
    }
}
