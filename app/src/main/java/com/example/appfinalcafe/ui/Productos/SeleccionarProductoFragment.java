package com.example.appfinalcafe.ui.Productos;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.appfinalcafe.Model.DetallePedido;
import com.example.appfinalcafe.Model.Mesa;
import com.example.appfinalcafe.Model.Pedido;
import com.example.appfinalcafe.Model.Producto;
import com.example.appfinalcafe.R;
import com.example.appfinalcafe.ui.Mesas.MesasAdapter;
import com.example.appfinalcafe.ui.Mesas.MesasViewModel;
import com.example.appfinalcafe.ui.Pedidos.ProductosAdapter;

import java.util.List;


public class SeleccionarProductoFragment extends Fragment implements SeleccionarProductosAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private EditText etCantidad;
    private SeleccionarProductosAdapter productosAdapter;
    private SeleccionarProductoViewModel seleccionarProductoViewModel;
    public SeleccionarProductoFragment() {
        // Constructor vacío requerido
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_seleccionar_producto, container, false);
        recyclerView = view.findViewById(R.id.rvProductos);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        Button detalleButton = view.findViewById(R.id.buttonAgregar);

        detalleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                Navigation.findNavController(requireView()).navigate(R.id.action_seleccionarProductoFragment_to_nav_slideshow, bundle);
            }
        });

        Bundle bundle = getArguments();
        Pedido pedido = null;
        if (bundle != null) {
            pedido = (Pedido) bundle.getSerializable("pedido");
        }
        final Pedido pedidoFinal = pedido;
        Button buttonAgregar = view.findViewById(R.id.buttonAgregar);
        etCantidad = view.findViewById(R.id.etCantidad);
        buttonAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Verificar si el pedido es nulo antes de utilizarlo
                if (pedidoFinal != null) {
                    Producto productoSeleccionado = productosAdapter.getProductoSeleccionado();
                    String cantidadString = etCantidad.getText().toString();
                    int cantidad = Integer.parseInt(cantidadString);
                    Log.d("Detalle Pedido", pedidoFinal.getId()+" " +productoSeleccionado.getId() + " " + cantidad + " " + productoSeleccionado.getPrecio());
                    seleccionarProductoViewModel.agregarProductoalPedido(pedidoFinal.getId(), productoSeleccionado.getId(), cantidad, productoSeleccionado.getPrecio());
                    Bundle bundle = new Bundle();
                    Log.d("Pedido56", pedidoFinal + " ");
                    bundle.putSerializable("pedido", pedidoFinal);
                    Navigation.findNavController(requireView()).navigate(R.id.action_seleccionarProductoFragment_to_nav_slideshow, bundle);
                } else {
                    Log.d("Detalle Pedido", "El pedido es nulo");
                }
            }
        });


        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        productosAdapter = new SeleccionarProductosAdapter(getActivity(), this::onProductoClick);
        recyclerView.setAdapter(productosAdapter);

        seleccionarProductoViewModel = new ViewModelProvider(this).get(SeleccionarProductoViewModel.class);
        seleccionarProductoViewModel.getProductoLiveData().observe(getViewLifecycleOwner(), new Observer<List<Producto>>() {
            @Override
            public void onChanged(List<Producto> prod) {

                productosAdapter.setProductos(prod);
            }
        });
        seleccionarProductoViewModel.obtenerDatosProducto(); // Cargar los datos de los inmuebles
    }
    @Override
    public void onProductoClick(Producto producto) {

    }


}
