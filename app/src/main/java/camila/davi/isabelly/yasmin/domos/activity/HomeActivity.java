package camila.davi.isabelly.yasmin.domos.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import camila.davi.isabelly.yasmin.domos.R;
import camila.davi.isabelly.yasmin.domos.fragment.AnunciosFragment;
import camila.davi.isabelly.yasmin.domos.fragment.AvisosFragment;
import camila.davi.isabelly.yasmin.domos.fragment.PerfilFragment;
import camila.davi.isabelly.yasmin.domos.model.HomeViewModel;


public class HomeActivity extends AppCompatActivity {



    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final HomeViewModel vm = new ViewModelProvider(this).get(HomeViewModel.class);

        bottomNavigationView = findViewById(R.id.btnMenu);
        // define a função da nav
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            // define ação quando for selecionado algum botão da nav bar
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // botão selecionado
                vm.setNavigationOpSelected(item.getItemId());
                switch (item.getItemId()) {
                    case R.id.btnAvisos:
                        AvisosFragment avisosFragment = AvisosFragment.newInstance();
                        setFragment(avisosFragment);
                        break;
                    case R.id.btnAnuncios:
                        AnunciosFragment anunciosFragment = AnunciosFragment.newInstance();
                        setFragment(anunciosFragment);
                        break;
                    case R.id.btnPerfil:
                        PerfilFragment perfilFragment = PerfilFragment.newInstance();
                        setFragment(perfilFragment);
                        break;
                }
                return true;
            }
        });
        bottomNavigationView.setSelectedItemId(vm.getNavigationOpSelected());

    }

    public void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragContainerHome, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }


}