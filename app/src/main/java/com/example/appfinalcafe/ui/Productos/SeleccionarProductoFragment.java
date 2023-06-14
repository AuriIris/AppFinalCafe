package com.example.appfinalcafe.ui.Productos;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
    private Button buttonAgregar;
    public SeleccionarProductoFragment() {
        // Constructor vacío requerido
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_seleccionar_producto, container, false);
        recyclerView = view.findViewById(R.id.rvProductos);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        buttonAgregar = view.findViewById(R.id.buttonAgregar1);


        Bundle bundle = getArguments();
        Pedido pedido = null;
        if (bundle != null) {
            pedido = (Pedido) bundle.getSerializable("pedido");
        }
        final Pedido pedidoFinal = pedido;;
        etCantidad = view.findViewById(R.id.etCantidad);
        buttonAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Verificar si el pedido es nulo antes de utilizarlo
                if (pedidoFinal != null) {
                    Producto productoSeleccionado = productosAdapter.getProductoSeleccionado();
                    String cantidadString = etCantidad.getText().toString();

                    //Log.d("Detalle Pedido", pedidoFinal.getId()+" " +productoSeleccionado.getId() + " " + cantidad + " " + productoSeleccionado.getPrecio());
                    try {
                        int cantidad = Integer.parseInt(cantidadString);
                        seleccionarProductoViewModel.agregarProductoalPedido(pedidoFinal.getId(), productoSeleccionado.getId(), cantidad, productoSeleccionado.getPrecio());
                        Bundle bundle = new Bundle();
                        Log.d("Pedido56", pedidoFinal + " ");
                        bundle.putSerializable("pedido", pedidoFinal);
                        NavOptions op = new NavOptions.Builder()
                                .setLaunchSingleTop(true)
                                .setPopUpTo(R.id.nav_slideshow,false)
                                .build();

                        Navigation.findNavController(getActivity(),R.id.nav_host_fragment_content_main).navigate(R.id.action_seleccionarProductoFragment_to_nav_slideshow, bundle,op);
                    }
                    catch(Exception ex){
                        Toast.makeText(activity, "Seleccione Producto e Ingrese Cantidad", Toast.LENGTH_SHORT).show();
                    }

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
