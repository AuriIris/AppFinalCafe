package com.example.appfinalcafe.ui.Pedidos;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.appfinalcafe.Model.DetallePedido;
import com.example.appfinalcafe.Model.Mesa;
import com.example.appfinalcafe.Model.Pedido;
import com.example.appfinalcafe.R;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DetallePedidoFragment extends Fragment {

    private EditText pedidoIdEditText;
    private AppCompatSpinner spinnerMesa;
    private EditText UsuarioIdEditText;
    private AppCompatSpinner spinnerEstado;
    private EditText PrecioTotalEditText;
    private EditText FechayHoraEditText;
    private EditText HoraEditText;
    private Button EditarPedidoBtn;
    private Button AgregarPedidoBtn;
    private DatePicker datePicker;
    private TimePicker timePicker;

    private DetallePedidoViewModel detalleViewModel;
    private ProductosAdapter productosAdapter;
    private RecyclerView rvProductos;
    private ArrayAdapter<String> estadoAdapter;
    private ArrayAdapter<Mesa> mesaAdapter;
    public static int cont=0;

    private List<Mesa> listaMesas = new ArrayList<>();
    private String[] estados = {
            "Solicitado",
            "En Cocina",
            "Consumiendo",
            "Pago"
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detalle_pedido, container, false);

        pedidoIdEditText = view.findViewById(R.id.editTextPedidoId);
        spinnerMesa = view.findViewById(R.id.spinnerMesa);
        UsuarioIdEditText = view.findViewById(R.id.editTextUsuarioId);
        spinnerEstado = view.findViewById(R.id.spinnerEstado);
        PrecioTotalEditText = view.findViewById(R.id.editTextPrecioTotal);
        FechayHoraEditText = view.findViewById(R.id.editTextFecha);
        HoraEditText=view.findViewById(R.id.editTextHora);
        //datePicker = view.findViewById(R.id.datePicker);
        //timePicker = view.findViewById(R.id.timePicker);
        EditarPedidoBtn = view.findViewById(R.id.btnEditarPedido);
        AgregarPedidoBtn= view.findViewById(R.id.btnDPAgregar);

        detalleViewModel = new ViewModelProvider(requireActivity()).get(DetallePedidoViewModel.class);

        Bundle bundle = getArguments();
        if (bundle != null) {
            Pedido pedido = (Pedido) bundle.getSerializable("pedido");
            if (pedido != null&& cont==0) {
                Log.d("Pedido56-", cont+"");
                mostrarDatosPedido(pedido);
            }
        }

        FechayHoraEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        requireContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                // Aquí obtienes la fecha seleccionada y puedes realizar cualquier acción necesaria
                                String fecha = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                                FechayHoraEditText.setText(fecha);
                            }
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                );
                datePickerDialog.show();
            }
        });
        HoraEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar currentTime = Calendar.getInstance();
                int hour = currentTime.get(Calendar.HOUR_OF_DAY);
                int minute = currentTime.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        requireContext(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                // Aquí obtienes la hora seleccionada y puedes realizar cualquier acción necesaria
                                String hora = hourOfDay + ":" + minute;
                                HoraEditText.setText(hora);
                            }
                        },
                        hour,
                        minute,
                        true
                );
                timePickerDialog.show();
            }
        });



        mesaAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, listaMesas);
        spinnerMesa.setAdapter(mesaAdapter);

        detalleViewModel.getMesas().observe(getViewLifecycleOwner(), new Observer<List<Mesa>>() {
            @Override
            public void onChanged(List<Mesa> mesas) {
                Log.d("MEsa", "onChanged: de MEsas" + mesas.size());
                listaMesas.clear();
                listaMesas.addAll(mesas);
                mesaAdapter.notifyDataSetChanged();
            }
        });
        AgregarPedidoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detalleViewModel.getPedidoLiveData();
            }
        });

        estadoAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, estados);
        spinnerEstado.setAdapter(estadoAdapter);

        EditarPedidoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (EditarPedidoBtn.getText().equals("Editar Pedido")) {
                    if (spinnerEstado.getSelectedItemPosition() != 3) {
                        habilitarEdicion();
                    } else {
                        Toast.makeText(getContext(), "El pedido no se puede editar, ya se encuentra pagado", Toast.LENGTH_SHORT).show();
                    }
                } else if (EditarPedidoBtn.getText().equals("Guardar Pedido")) {
                    String idText = pedidoIdEditText.getText().toString();
                    int mesaId = ((Mesa) spinnerMesa.getSelectedItem()).getId();
                    int usuarioId = Integer.parseInt(UsuarioIdEditText.getText().toString());
                    int estado = spinnerEstado.getSelectedItemPosition();
                    Double precio = Double.parseDouble(PrecioTotalEditText.getText().toString());
                    String hora=HoraEditText.getText().toString();
                    if(hora.charAt(1)==':'){
                        hora="0"+hora;
                    }
                    if(hora.charAt(4)==':'){
                        //necesito insertar un caracter en la posicion 3
                        hora = hora.substring(0, 3) + "0" + hora.substring(3);
                    }
                    if(hora.length()==5){
                        hora=hora+":00";
                    }
                    String fecha = FechayHoraEditText.getText().toString()+" "+hora;

                    int id = (idText.equals("")) ? 0 : Integer.parseInt(idText);
                    Pedido pedidoActualizado = new Pedido(id, mesaId, usuarioId, estado, precio, fecha);
                    detalleViewModel.actualizarDatosPedido(pedidoActualizado);

                    // Deshabilitar la edición y cambiar el texto del botón
                    spinnerMesa.setEnabled(false);
                    UsuarioIdEditText.setEnabled(false);
                    spinnerEstado.setEnabled(false);
                    PrecioTotalEditText.setEnabled(false);
                    FechayHoraEditText.setEnabled(false);
                    EditarPedidoBtn.setText("Editar Pedido");
                    Navigation.findNavController(getActivity(),R.id.nav_host_fragment_content_main).navigate(R.id.nav_gallery, bundle);
                }
            }
        });
        detalleViewModel.getPedidoLiveData().observe(getViewLifecycleOwner(), new Observer<Pedido>() {
            @Override
            public void onChanged(Pedido pedido) {
//                Bundle bundle = new Bundle();
//                Log.d("Pedido56+", pedido + " ");
//                bundle.putSerializable("pedido", pedido);
//                Navigation.findNavController(getActivity(),R.id.nav_host_fragment_content_main).navigate(R.id.nav_slideshow, bundle);
            }
        });
        rvProductos = view.findViewById(R.id.rvProdEnDetalleProd);
        rvProductos.setLayoutManager(new LinearLayoutManager(requireContext()));
//        productosAdapter = new ProductosAdapter(requireContext(), new ProductosAdapter.OnItemClickListener() {
//            @Override
//            public void onProductoClick(DetallePedido detallePedido) {
//                    Bundle bundle = new Bundle();
//                    Log.d("ver", "onProductoClick: "+detallePedido.toString());
//                    detalleViewModel.obtenerPedido(detallePedido.getPedidoId());
//
//                }
//        });





        detalleViewModel.getDetallePedidosLiveData().observe(getViewLifecycleOwner(), new Observer<List<DetallePedido>>() {
            @Override
            public void onChanged(List<DetallePedido> detallePedidos) {
                productosAdapter=new ProductosAdapter(getContext(),detallePedidos);
                rvProductos.setAdapter(productosAdapter);
                Log.d("Salida", detallePedidos.size()+"");

            }
        });


        return view;
    }

    private void mostrarDatosPedido(Pedido pedido) {
        pedidoIdEditText.setText(String.valueOf(pedido.getId()));

        int mesaIndex = -1;

        Mesa mesaPedido = pedido.getMesa();
        Log.d("MESA", mesaPedido+"");
        if (mesaPedido != null) {
            for (int i = 0; i < listaMesas.size(); i++) {
                Mesa mesa = listaMesas.get(i);
                if (mesa.getId() == mesaPedido.getId()) {
                    mesaIndex = i;
                    break;
                }
            }
        }
        spinnerMesa.setSelection(pedido.getMesaId());

        try {


            UsuarioIdEditText.setText(String.valueOf(pedido.getUsuarioId()));
            spinnerEstado.setSelection(pedido.getEstado());
            PrecioTotalEditText.setText(String.valueOf(pedido.getPrecioTotal()));
            String fechayHora = pedido.getFecha();
            String[] partes = fechayHora.split(" ");
            String fecha = partes[0];
            String hora = partes[1];
            FechayHoraEditText.setText(fecha);
            HoraEditText.setText(hora);
        }
        catch (Exception ex){

        }
        detalleViewModel.obtenerProductosDePedido(pedido.getId());
    }

    private void habilitarEdicion() {
        spinnerMesa.setEnabled(true);
        UsuarioIdEditText.setEnabled(true);
        spinnerEstado.setEnabled(true);
        PrecioTotalEditText.setEnabled(true);
        FechayHoraEditText.setEnabled(true);
        EditarPedidoBtn.setText("Guardar Pedido");
    }
}
