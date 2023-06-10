package com.example.appfinalcafe.ui.CierreDiario;

import android.os.Bundle;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


import com.example.appfinalcafe.R;

public class CierreDiarioFragment extends Fragment {

    private TextView total;

    private CierreDiarioViewModel cierreDiarioViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        cierreDiarioViewModel = new ViewModelProvider(this).get(CierreDiarioViewModel.class);

        View view = inflater.inflate(R.layout.fragment_cierre_diario, container, false);

        EditText editTextFechaDesde = view.findViewById(R.id.editTextFechaDesde);
        EditText editTextHoraDesde = view.findViewById(R.id.editTextHoraDesde);
        EditText editTextFechaHasta = view.findViewById(R.id.editTextFechaHasta);
        EditText editTextHoraHasta = view.findViewById(R.id.editTextHoraHasta);
        total=view.findViewById(R.id.tvResultado);
        Button btnCalcular = view.findViewById(R.id.btnCalcular);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        String fechaActual = dateFormat.format(calendar.getTime());
        String horaActual = timeFormat.format(calendar.getTime());

        editTextFechaDesde.setText(fechaActual);
        editTextHoraDesde.setText(horaActual);

        editTextFechaHasta.setText(fechaActual);
        editTextHoraHasta.setText(horaActual);

        editTextFechaDesde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        requireContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                String fechaSeleccionada = String.format(Locale.getDefault(), "%02d/%02d/%04d", dayOfMonth, monthOfYear + 1, year);
                                editTextFechaDesde.setText(fechaSeleccionada);

                            }
                        },
                        year, month, day);

                datePickerDialog.show();
            }
        });

        editTextHoraDesde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        requireContext(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                String horaSeleccionada = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute);
                                editTextHoraDesde.setText(horaSeleccionada);

                            }
                        },
                        hour, minute, true);

                timePickerDialog.show();
            }
        });
        cierreDiarioViewModel.getTotalCierreLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String totalCierre) {
                total.setText(totalCierre);
            }
        });

        editTextFechaHasta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        requireContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                String fechaSeleccionada = String.format(Locale.getDefault(), "%02d/%02d/%04d", dayOfMonth, monthOfYear + 1, year);
                                editTextFechaHasta.setText(fechaSeleccionada);
                            }
                        },
                        year, month, day);

                datePickerDialog.show();
            }
        });

        editTextHoraHasta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        requireContext(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                // Actualizar el EditText con la hora seleccionada
                                String horaSeleccionada = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute);
                                editTextHoraHasta.setText(horaSeleccionada);
                            }
                        },
                        hour, minute, true);

                timePickerDialog.show();
            }
        });
        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fecha = editTextFechaDesde.getText().toString() +" "+ editTextHoraDesde.getText().toString() + " "+
                        editTextFechaHasta.getText().toString() +" "+ editTextHoraHasta.getText().toString();

                    cierreDiarioViewModel.calcularCierre(fecha);
                    //necesito mostrar en total el resultado
            }
        });

        return view;
    }
}