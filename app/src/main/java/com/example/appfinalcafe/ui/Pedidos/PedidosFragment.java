package com.example.appfinalcafe.ui.Pedidos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appfinalcafe.Model.Pedido;
import com.example.appfinalcafe.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PedidosFragment extends Fragment implements PedidosAdapter.OnItemClickListener {
    private RecyclerView recyclerView;
    private PedidosAdapter pedidosAdapter;
    private PedidosViewModel pedidosViewModel;


    public PedidosFragment() {
        // Constructor vacío requerido
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pedidos, container, false);

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        Button detalleButton = view.findViewById(R.id.buttonaddPedido);
        DetallePedidoFragment.cont=0;
        detalleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pedido pedido = new Pedido();
                pedido.setId(0);
                pedido.setEstado(0);
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                String fechaActual = dateFormat.format(new Date());
                pedido.setFecha(fechaActual);
                pedido.setMesaId(0);
                pedido.setPrecioTotal(0);
                pedido.setUsuarioId(0);
                //Modificar el estado de la mesa

//                Bundle bundle = new Bundle();
//                bundle.putSerializable("pedido", pedido);
//                Navigation.findNavController(requireView()).navigate(R.id.action_nav_gallery_to_detallePedidoFragment, bundle);
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

       pedidosAdapter = new PedidosAdapter(getActivity(), this);


        pedidosViewModel = new ViewModelProvider(this).get(PedidosViewModel.class);
        pedidosViewModel.getPedidos().observe(getViewLifecycleOwner(), new Observer<List<Pedido>>() {
            @Override
            public void onChanged(List<Pedido> pedidos) {
                recyclerView = view.findViewById(R.id.rvPedido);
                recyclerView.setAdapter(pedidosAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                pedidosAdapter.setPedidos(pedidos);
            }
        });
        pedidosViewModel.cargarPedidos();
    }

    @Override
    public void onResume() {
        super.onResume();
        pedidosViewModel.cargarPedidos();
    }

    @Override
    public void onPedidoClick(Pedido pedido) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("pedido", pedido);
        Navigation.findNavController(requireView()).navigate(R.id.action_nav_gallery_to_detallePedidoFragment, bundle);
    }
}