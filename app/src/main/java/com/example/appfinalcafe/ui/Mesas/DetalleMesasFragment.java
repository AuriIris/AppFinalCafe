package com.example.appfinalcafe.ui.Mesas;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.appfinalcafe.Model.Mesa;
import com.example.appfinalcafe.Model.Producto;
import com.example.appfinalcafe.R;
import com.example.appfinalcafe.ui.Productos.DetalleProductosViewModel;

public class DetalleMesasFragment extends Fragment {


    private EditText codigoEditText;
    private EditText numeroEditText;
    private EditText estadoEditText;
    private EditText sucursalEditText;
    private Button editarGuardarButton;

    private DetalleMesasViewModel detalleViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detalle_mesas, container, false);

        // Obtener referencias a los elementos de la interfaz
        codigoEditText = view.findViewById(R.id.editTextMesaId);
        numeroEditText = view.findViewById(R.id.editTextNumero);
        estadoEditText = view.findViewById(R.id.editTextEstado);
        sucursalEditText = view.findViewById(R.id.editTextSucursalId);
        editarGuardarButton = view.findViewById(R.id.buttonEditarGuardar);

        // Obtener el ViewModel
        detalleViewModel = new ViewModelProvider(requireActivity()).get(DetalleMesasViewModel.class);

        // Obtener el producto del Bundle de argumentos
        Bundle bundle = getArguments();
        if (bundle != null) {
            Mesa mesa = (Mesa) bundle.getSerializable("mesa");
            if (mesa != null) {
                mostrarDatosProducto(mesa);
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

    private void mostrarDatosProducto(Mesa mesa) {
        codigoEditText.setText(String.valueOf(mesa.getId()));
        numeroEditText.setText(String.valueOf(mesa.getNumero()));
        estadoEditText.setText(String.valueOf(mesa.getEstado()));
        sucursalEditText.setText(String.valueOf(mesa.getSucursalId()));
    }

    private void habilitarEdicion() {
        numeroEditText.setEnabled(true);
        estadoEditText.setEnabled(true);
        sucursalEditText.setEnabled(true);
        editarGuardarButton.setText("Guardar");
    }

    private void guardarCambios() {
        String idText = codigoEditText.getText().toString();
        int numero = Integer.parseInt(numeroEditText.getText().toString());
        int estado = Integer.parseInt(estadoEditText.getText().toString());
        int sucursal = Integer.parseInt(sucursalEditText.getText().toString());


        if (idText == null || idText.isEmpty()) {
            // El ID es nulo o vacío, guardar un nuevo producto
            Mesa mesaActualizado = new Mesa(0,sucursal,numero, estado );
            detalleViewModel.actualizarDatosMesa(mesaActualizado);
        } else {
            // El ID está presente, actualizar el producto existente
            int id = Integer.parseInt(idText);
            Mesa mesaActualizado = new Mesa(id,sucursal,numero, estado );
            detalleViewModel.actualizarDatosMesa(mesaActualizado);
        }

        // Deshabilitar la edición y cambiar el texto del botón
        numeroEditText.setEnabled(false);
        estadoEditText.setEnabled(false);
        sucursalEditText.setEnabled(false);
        editarGuardarButton.setText("Editar");
    }


}