package com.example.appfinalcafe.ui.Mesas;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.appfinalcafe.Model.Mesa;
import com.example.appfinalcafe.R;

import java.util.List;


public class MesasFragment extends Fragment implements MesasAdapter.OnItemClickListener {
    private RecyclerView recyclerView;
    private MesasAdapter mesasAdapter;
    private MesasViewModel mesasViewModel;

    public MesasFragment() {
        // Constructor vacío requerido
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mesas, container, false);
        recyclerView = view.findViewById(R.id.rvMesa);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        try {
            ActionBar actionBar = activity.getSupportActionBar();
            actionBar.setTitle("Inmuebles ");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Button detalleButton = view.findViewById(R.id.buttonAgregar);

        detalleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();

              //  Navigation.findNavController(requireView()).navigate(R.id.action_mesasFragment_to_detalleMesasFragment, bundle);
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mesasAdapter = new MesasAdapter(getActivity(), this);
        recyclerView.setAdapter(mesasAdapter);

        mesasViewModel = new ViewModelProvider(this).get(MesasViewModel.class);
        mesasViewModel.getMesas().observe(getViewLifecycleOwner(), new Observer<List<Mesa>>() {
            @Override
            public void onChanged(List<Mesa> mesas) {

                mesasAdapter.setMesas(mesas);
            }
        });
        mesasViewModel.getMesas(); // Cargar los datos de los inmuebles
    }

    @Override
    public void onMesaClick(Mesa mesa) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("mesa", mesa);
       // Navigation.findNavController(requireView()).navigate(R.id.action_mesasFragment_to_detalleMesasFragment, bundle);
    }
}