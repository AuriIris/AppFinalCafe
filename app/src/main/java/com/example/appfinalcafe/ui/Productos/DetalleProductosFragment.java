package com.example.appfinalcafe.ui.Productos;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.appfinalcafe.Model.Producto;
import com.example.appfinalcafe.Model.Usuario;
import com.example.appfinalcafe.R;
import com.example.appfinalcafe.ui.Perfil.PerfilViewModel;
import com.example.appfinalcafe.ui.Productos.DetalleProductosViewModel;


public class DetalleProductosFragment extends Fragment {

    private EditText codigoEditText;
    private EditText nombreEditText;
    private EditText precioEditText;
    private EditText categoriaEditText;
    private Button editarGuardarButton;

    private DetalleProductosViewModel detalleViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detalle_productos, container, false);

        // Obtener referencias a los elementos de la interfaz
        codigoEditText = view.findViewById(R.id.textViewProductoID);
        nombreEditText = view.findViewById(R.id.editTextNombre);
        precioEditText = view.findViewById(R.id.editTextPrecio);
        categoriaEditText = view.findViewById(R.id.editTextCategoria);
        editarGuardarButton = view.findViewById(R.id.buttonEditarGuardar);

        // Obtener el ViewModel
        detalleViewModel = new ViewModelProvider(requireActivity()).get(DetalleProductosViewModel.class);

        // Obtener el producto del Bundle de argumentos
        Bundle bundle = getArguments();
        if (bundle != null) {
            Producto producto = (Producto) bundle.getSerializable("pedido");
            if (producto != null) {
                mostrarDatosProducto(producto);
            }
        }

        // Configurar el botón editar/guardar
        editarGuardarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editarGuardarButton.getText().equals("Editar")) {
                    habilitarEdicion();
                } else if (editarGuardarButton.getText().equals("Guardar")) {
                    guardarCambios();
                }
            }
        });

        return view;
    }

    private void mostrarDatosProducto(Producto producto) {
        codigoEditText.setText(String.valueOf(producto.getId()));
        nombreEditText.setText(producto.getNombre());
        precioEditText.setText(String.valueOf(producto.getPrecio()));
        categoriaEditText.setText(String.valueOf(producto.getCategoria()));
    }

    private void habilitarEdicion() {
        nombreEditText.setEnabled(true);
        precioEditText.setEnabled(true);
        categoriaEditText.setEnabled(true);
        editarGuardarButton.setText("Guardar");
    }

    private void guardarCambios() {
        String idText = codigoEditText.getText().toString();
        String nombre = nombreEditText.getText().toString();
        double precio = Double.parseDouble(precioEditText.getText().toString());
        int categoria = Integer.parseInt(categoriaEditText.getText().toString());

        if (idText == null || idText.isEmpty()) {
            // El ID es nulo o vacío, guardar un nuevo producto
            //Producto nuevoProducto = new Producto(0,nombre, precio, categoria);
            //detalleViewModel.actualizarDatosProducto(nuevoProducto);
        } else {
            // El ID está presente, actualizar el producto existente
            int id = Integer.parseInt(idText);
            //Producto productoActualizado = new Producto(id, nombre, precio, categoria);
            //detalleViewModel.actualizarDatosProducto(productoActualizado);
        }

        // Deshabilitar la edición y cambiar el texto del botón
        nombreEditText.setEnabled(false);
        precioEditText.setEnabled(false);
        categoriaEditText.setEnabled(false);
        editarGuardarButton.setText("Editar");
    }

}