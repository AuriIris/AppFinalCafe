package com.example.appfinalcafe.ui.Pedidos;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.appfinalcafe.Model.Pedido;
import com.example.appfinalcafe.Model.Producto;
import com.example.appfinalcafe.resource.ApiClientRetrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PedidosViewModel  extends AndroidViewModel {

    private MutableLiveData<List<Pedido>> pedidosLiveData;
    private ApiClientRetrofit.EndpointCafe apiClient;
    private Context contexto;

    public PedidosViewModel(Application application) {
        super(application);
        contexto = application.getApplicationContext();
        apiClient = ApiClientRetrofit.getEndpoint();
    }

    public LiveData<List<Pedido>> getPedidos() {
        if (pedidosLiveData == null) {
            pedidosLiveData = new MutableLiveData<>();

        }
        return pedidosLiveData;
    }

    public void cargarPedidos() {
        SharedPreferences sp = contexto.getSharedPreferences("token.xml", 0);
        String token = sp.getString("token", "");
        Call<List<Pedido>> call = apiClient.obtenerPedidos(token);
        call.enqueue(new Callback<List<Pedido>>() {
            @Override
            public void onResponse(Call<List<Pedido>> call, Response<List<Pedido>> response) {
                if (response.isSuccessful()) {
                    List<Pedido> pedidos = response.body();
                    for (Pedido pedido : pedidos) {
                        Log.d("Ver22", "Usuario ID: " + pedido.toString());
                        Log.d("Ver22", "Pedido ID: " + pedido.getId());
                        Log.d("Ver22", "Mesa ID: " + pedido.getMesaId());
                        Log.d("Ver22", "Mesa ID: " + pedido.getMesa().toString());
                        Log.d("Ver22", "Usuario ID: " + pedido.getUsuarioId());
                        Log.d("Ver22", "Usuario ID: " + pedido.getUsuario().toString());
                        Log.d("Ver22", "Estado: " + pedido.getEstado());
                        Log.d("Ver22", "Precio Total: " + pedido.getPrecioTotal());
                        Log.d("Ver22", "Fecha: " + pedido.getFecha());
                        Log.d("Ver22", "----------------------");
                    }
                    pedidosLiveData.postValue(pedidos);

                } else {
                    Log.d("Salida", response.message());
                    Toast.makeText(contexto, "Error al Buscar Productos 1", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Pedido>> call, Throwable t) {
                Log.d("Ver", t.getMessage());//D/Ver: java.lang.IllegalStateException: Expected BEGIN_OBJECT but was STRING at line 1 column 293 path $[0].fecha

                Toast.makeText(contexto, "Error al Buscar Productos", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
