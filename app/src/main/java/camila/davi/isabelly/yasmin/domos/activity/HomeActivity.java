package camila.davi.isabelly.yasmin.domos.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import java.io.FileNotFoundException;
import java.util.List;

import camila.davi.isabelly.yasmin.domos.R;
import camila.davi.isabelly.yasmin.domos.bd.Aviso;
import camila.davi.isabelly.yasmin.domos.fragment.AnunciosFragment;
import camila.davi.isabelly.yasmin.domos.fragment.AvisosFragment;
import camila.davi.isabelly.yasmin.domos.fragment.PerfilFragment;
import camila.davi.isabelly.yasmin.domos.fragment.RegimentoFragment;
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

                        setContentView(R.layout.activity_home);

                        // encontra os itens da Activity
                        RecyclerView rvAvisos = findViewById(R.id.rvAvisos);
                        // BOTAO ADICIONAR AVISO SINDICO FloatingActionButton favAddItem = findViewById(R.id.fabAddNewItem);

                        // cria a view model para a main activity que vai guardar os itens da lista
                        HomeViewModel vm = new ViewModelProvider(HomeActivity.this).get(HomeViewModel.class);
                        List<Aviso> itens = vm.getAvisos();

                        // cria myAdapter para salvar conteúdo da lista da mainActivity
                        myAdapter  = new MyAdapter(this, itens);
                        // define o myAdapter como o adapter da lista da mainActivity
                        rvAvisos.setAdapter(myAdapter);
                        // define que a lista tem itens com o tamanho fixo
                        rvAvisos.setHasFixedSize(true);

                        // cria o layoutManager para a lista, que é responsável por dispor os itens na activity
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
                        // define o layoutManager como o manager da lista da mainActivity
                        rvItens.setLayoutManager(layoutManager);

                        // cria o dividerItemDecoration para a lista, que organiza os intens verticalmente
                        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvItens.getContext(),
                                DividerItemDecoration.VERTICAL);
                        // define a decoration dos itens como o dividerItemDecoration, que é vertical
                        rvItens.addItemDecoration(dividerItemDecoration);

                        // define a ação que será feita quando o botão for acionado
                        favAddItem.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // cria a Intent
                                Intent i = new Intent(MainActivity.this, NewItemActivity.class);
                                // define a ação da Intent e a executa
                                startActivityForResult(i, NEW_ITEM_REQUEST);
                            }
                        });
                }

                // ação que recebe o valor inserido pelo usuário na NewItemActivity
                @Override
                protected void onActivityResult(int requestCode, int resultCode, Intent data) {
                    super.onActivityResult(requestCode, resultCode, data);
                    // condição para verificar se a resposta recebida é referente ao NEW_ITEM_REQUEST
                    if (requestCode == NEW_ITEM_REQUEST) {
                        // condição para verificar se a request foi realmente realizada
                        if (resultCode == Activity.RESULT_OK) {
                            // cria um novo item
                            MyItem myItem = new MyItem();
                            // adiciona os valores inseridos pelo usuário na NewItemActivity ao item
                            myItem.title = data.getStringExtra("title");
                            myItem.description = data.getStringExtra("description");

                            // atribui o endereço da foto escolhida a variável
                            Uri selectedPhotoUri = data.getData();

                            try {
                                // carrega a foto no bitmap
                                Bitmap photo = Util.getBitmap(MainActivity.this, selectedPhotoUri,
                                        100, 100);
                                // define o conteúdo do item da foto como o bitmap
                                myItem.photo = photo;
                            } catch (FileNotFoundException e) {
                                // caso o arquivo não for encontrado, imprime o erro
                                e.printStackTrace();
                            }

                            // cria a view model para a main activity que vai guardar os itens da lista
                            MainActivityViewModel vm = new ViewModelProvider(this).get(MainActivityViewModel.class);
                            List<MyItem> itens = vm.getItens();

                            // adiciona o novo item a lista de itens
                            itens.add(myItem);

                            // insere o item ao display
                            myAdapter.notifyItemInserted(itens.size() - 1);
                        }
                    }
                        break;
                    case R.id.btnAnuncios:
                        AnunciosFragment anunciosFragment = AnunciosFragment.newInstance();
                        setFragment(anunciosFragment);
                        break;
                    case R.id.btnRegimento:
                        RegimentoFragment regimentoFragment = RegimentoFragment.newInstance();
                        setFragment(regimentoFragment);
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