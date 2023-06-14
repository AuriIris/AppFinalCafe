package com.example.appfinalcafe.ui.Productos;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appfinalcafe.Model.DetallePedido;
import com.example.appfinalcafe.Model.Pedido;
import com.example.appfinalcafe.Model.Producto;
import com.example.appfinalcafe.R;
import com.example.appfinalcafe.ui.Pedidos.DetallePedidoFragment;
import com.example.appfinalcafe.ui.Pedidos.ProductosAdapter;

import java.io.Serializable;
import java.util.List;

public class ProductosFragment extends Fragment implements ProductosAdapter.OnItemClickListener {
    private RecyclerView rvProductos;
    private ProductosAdapter productosAdapter;
    private ProductosViewModel productosViewModel;
    private Pedido pedido;

    public ProductosFragment() {
        // Constructor vacío requerido
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_productos, container, false);
        rvProductos = view.findViewById(R.id.rvProductos);
        rvProductos.setLayoutManager(new LinearLayoutManager(requireContext()));
        DetallePedidoFragment.cont=1;


        productosViewModel = new ViewModelProvider(this).get(ProductosViewModel.class);
        Bundle bundle = getArguments();
        Pedido pedido = (Pedido) bundle.getSerializable("pedido");
        Log.d("PEDIDO", pedido+"");
        productosViewModel.obtenerDetallePedidos(pedido.getId());
        productosViewModel.getDetallePedidosLiveData().observe(getViewLifecycleOwner(), new Observer<List<DetallePedido>>() {
            @Override
            public void onChanged(List<DetallePedido> detallesPedido) {
                productosAdapter = new ProductosAdapter(requireContext(), detallesPedido);
                rvProductos.setAdapter(productosAdapter);
            }
        });

        Button buttonAgregar = view.findViewById(R.id.buttonAgregar);
        buttonAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pedido != null) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("pedido", pedido);
                    Log.d("ProductosFragment", pedido+"");
                    Navigation.findNavController(getActivity(),R.id.nav_host_fragment_content_main).navigate(R.id.seleccionarProductoFragment, bundle);
                } else {
                    Log.d("ProductosFragment", "El objeto pedido es nulo");
                }
            }
        });

        return view;
    }

    @Override
    public void onProductoClick(DetallePedido detallePedido) {

    }
}
