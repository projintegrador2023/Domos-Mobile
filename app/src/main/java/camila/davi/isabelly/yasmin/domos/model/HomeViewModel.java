package camila.davi.isabelly.yasmin.domos.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import camila.davi.isabelly.yasmin.domos.R;

public class HomeViewModel extends AndroidViewModel {

    int navigationOpSelected = R.id.btnAvisos;

    public HomeViewModel(@NonNull Application application) {
        super(application);
    }

    public int getNavigationOpSelected() {
        return navigationOpSelected;
    }

    public void setNavigationOpSelected(int navigationOpSelected) {
        this.navigationOpSelected = navigationOpSelected;
    }
}
