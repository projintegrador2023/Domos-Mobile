package camila.davi.isabelly.yasmin.domos.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelKt;
import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.PagingLiveData;

import java.util.ArrayList;
import java.util.List;

import camila.davi.isabelly.yasmin.domos.R;
import camila.davi.isabelly.yasmin.domos.bd.Aviso;
import kotlinx.coroutines.CoroutineScope;

public class HomeViewModel extends AndroidViewModel {
    LiveData<PagingData<Aviso>> avisosLd;
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

    public LiveData<PagingData<Aviso>> getAvisosLd(String importancia) {
        DomosRepository domosRepository = new DomosRepository(getApplication());
        CoroutineScope viewModelScope = ViewModelKt.getViewModelScope(this);
        Pager<Integer, Aviso> pager = new Pager(new PagingConfig(10), () -> new AvisosPagingSource(domosRepository, importancia));
        avisosLd = PagingLiveData.cachedIn(PagingLiveData.getLiveData(pager), viewModelScope);
        return avisosLd;
    }
}
