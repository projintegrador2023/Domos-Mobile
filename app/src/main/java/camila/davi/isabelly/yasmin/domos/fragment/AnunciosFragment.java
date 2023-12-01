package camila.davi.isabelly.yasmin.domos.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import camila.davi.isabelly.yasmin.domos.R;
import camila.davi.isabelly.yasmin.domos.activity.CriarAnuncioActivity;
import camila.davi.isabelly.yasmin.domos.activity.CriarAvisoActivity;
import camila.davi.isabelly.yasmin.domos.activity.HomeActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AnunciosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AnunciosFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AnunciosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AnunciosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AnunciosFragment newInstance() {
        return new AnunciosFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_anuncios, container, false);
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        FloatingActionButton fabPostarAnuncio = view.findViewById(R.id.fabPostarAnuncio);
        fabPostarAnuncio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent((HomeActivity) getActivity(), CriarAnuncioActivity.class);
                startActivity(i);
            }
        });
    }
}