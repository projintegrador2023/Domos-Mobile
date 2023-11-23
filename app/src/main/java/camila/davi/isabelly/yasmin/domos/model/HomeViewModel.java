package camila.davi.isabelly.yasmin.domos.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.ArrayList;
import java.util.List;

import camila.davi.isabelly.yasmin.domos.R;
import camila.davi.isabelly.yasmin.domos.bd.Aviso;

public class HomeViewModel extends AndroidViewModel {

    int navigationOpSelected = R.id.btnAvisos;
    List<Aviso> avisos = new ArrayList<>();

    public HomeViewModel(@NonNull Application application) {
        super(application);
    }


    // retorna os itens da lista de itens
    public List<Aviso> getAvisos() {
        return avisos;
    }

    public int getNavigationOpSelected() {
        return navigationOpSelected;
    }

    public void setNavigationOpSelected(int navigationOpSelected) {
        this.navigationOpSelected = navigationOpSelected;
    }
}
