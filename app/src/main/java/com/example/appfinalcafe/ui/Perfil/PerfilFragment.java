package com.example.appfinalcafe.ui.Perfil;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.appfinalcafe.Model.Mesa;
import com.example.appfinalcafe.Model.Usuario;
import com.example.appfinalcafe.R;

public class PerfilFragment extends Fragment {
    private EditText codigoEditText;
    private EditText nombreEditText;
   //private EditText rolEditText;
    private EditText emailEditText;
    private EditText contraseñaEditText;
    //private EditText permisoEditText;
    private Button editarGuardarButton;
    private Spinner rolSpinner;
    private Spinner permisoSpinner;
    private ArrayAdapter<String> rolAdapter;
    private ArrayAdapter<String> permisoAdapter;
    private PerfilViewModel perfilViewModel;
    private String[] rol = {
            "Mozo",
            "Cocina",
            "Barra"
    };
    private String[] permiso = {
            "Admin",
            "Empleado"
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_perfil, container, false);
        rolSpinner = view.findViewById(R.id.spinnerRol);
        rolAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, rol);
        rolSpinner.setAdapter(rolAdapter);

        permisoSpinner = view.findViewById(R.id.spinnerPermiso);
        permisoAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, permiso);
        permisoSpinner.setAdapter(permisoAdapter);


        codigoEditText = view.findViewById(R.id.codigoEditText);
        nombreEditText = view.findViewById(R.id.nombreEditText);

        //rolEditText = view.findViewById(R.id.rolEditText);
        rolSpinner = view.findViewById(R.id.spinnerRol);
        emailEditText = view.findViewById(R.id.emailEditText);
        contraseñaEditText = view.findViewById(R.id.contraseñaEditText);
        //permisoEditText = view.findViewById(R.id.permisoEditText);
        permisoSpinner = view.findViewById(R.id.spinnerPermiso);
        editarGuardarButton = view.findViewById(R.id.editarGuardarButton);
        Context context = requireContext();
        perfilViewModel = new ViewModelProvider(this).get(PerfilViewModel.class);
        perfilViewModel.getUsuarioLiveData().observe(getViewLifecycleOwner(), new Observer<Usuario>() {
            @Override
            public void onChanged(Usuario usuario) {
                mostrarDatosPropietario(usuario);
            }
        });
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
        perfilViewModel.obtenerDatosUsuario();
        return view;
    }

    private void mostrarDatosPropietario(Usuario usuario) {
        codigoEditText.setText(String.valueOf(usuario.getId()));
        nombreEditText.setText(String.valueOf(usuario.getNombre()));
        rolSpinner.setSelection(usuario.getRol());
        //rolEditText.setText(String.valueOf(usuario.getRol()));
        emailEditText.setText(String.valueOf(usuario.getMail()));
        contraseñaEditText.setText(String.valueOf(usuario.getClave()));
        permisoSpinner.setSelection(usuario.getPermiso());
        //permisoEditText.setText(String.valueOf(usuario.getPermiso()));
    }

    private void habilitarEdicion() {
        nombreEditText.setEnabled(true);
        rolSpinner.setEnabled(true);
       // rolEditText.setEnabled(true);
        emailEditText.setEnabled(true);
        contraseñaEditText.setEnabled(true);
        permisoSpinner.setEnabled(true);
       // permisoEditText.setEnabled(true);
        editarGuardarButton.setText("Guardar");
    }

    private void guardarCambios() {
        String nombre = nombreEditText.getText().toString();

        int apellido = rolSpinner.getSelectedItemPosition();
        String email = emailEditText.getText().toString();
        String contraseña = contraseñaEditText.getText().toString();
        int telefono = permisoSpinner.getSelectedItemPosition();

        perfilViewModel.actualizarDatosUsuario(nombre, apellido, email, contraseña, telefono);

        nombreEditText.setEnabled(false);
        rolSpinner.setEnabled(false);
        emailEditText.setEnabled(false);
        contraseñaEditText.setEnabled(false);
        permisoSpinner.setEnabled(false);
        editarGuardarButton.setText("Editar");
    }
}