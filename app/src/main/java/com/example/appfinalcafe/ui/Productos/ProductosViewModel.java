package com.example.appfinalcafe.ui.Productos;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.appfinalcafe.Model.DetallePedido;
import com.example.appfinalcafe.Model.Pedido;
import com.example.appfinalcafe.Model.Producto;
import com.example.appfinalcafe.resource.ApiClientRetrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductosViewModel extends AndroidViewModel {
    private MutableLiveData<List<DetallePedido>> detallePedidosLiveData = new MutableLiveData<>();
    private MutableLiveData<Pedido> pedidoLiveData = new MutableLiveData<>();
    private ApiClientRetrofit.EndpointCafe apiClient;
    private Context contexto;

    public ProductosViewModel(Application application) {
        super(application);
        contexto = application.getApplicationContext();
        apiClient = ApiClientRetrofit.getEndpoint();
    }

    public LiveData<List<DetallePedido>> getDetallePedidosLiveData() {
        return detallePedidosLiveData;
    }

    public LiveData<Pedido> getPedidoLiveData() {
        if (pedidoLiveData == null) {
            pedidoLiveData = new MutableLiveData<>();
        }
        return pedidoLiveData;
    }

    public void obtenerDetallePedidos(int id) {
        SharedPreferences sp = contexto.getSharedPreferences("token.xml", 0);
        String token = sp.getString("token", "");
        Call<List<DetallePedido>> detallePedidoCall = apiClient.obtenerProductosDePedido(token, id);
        detallePedidoCall.enqueue(new Callback<List<DetallePedido>>() {
            @Override
            public void onResponse(Call<List<DetallePedido>> call, Response<List<DetallePedido>> response) {
                if (response.isSuccessful()) {
                    detallePedidosLiveData.setValue(response.body());
                } else {
                    Toast.makeText(contexto, "Error al obtener los datos del detalle de pedido", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<DetallePedido>> call, Throwable t) {
                Toast.makeText(contexto, "Error al obtener los datos del detalle de pedido", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void obtenerPedido(int id) {
        SharedPreferences sp = contexto.getSharedPreferences("token.xml", 0);
        String token = sp.getString("token", "");
        Call<Pedido> pedidoCall = apiClient.obtenerPedido(token, id);
        pedidoCall.enqueue(new Callback<Pedido>() {
            @Override
            public void onResponse(Call<Pedido> call, Response<Pedido> response) {
                if (response.isSuccessful()) {
                    Pedido pedido = response.body();
                    pedidoLiveData.postValue(pedido);
                    obtenerDetallePedidos(pedido.getId());
                } else {
                    Log.d("Salida", response.message());
                    Toast.makeText(contexto, "Error al buscar el pedido", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Pedido> call, Throwable t) {
                Toast.makeText(contexto, "Error al buscar el pedido", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
