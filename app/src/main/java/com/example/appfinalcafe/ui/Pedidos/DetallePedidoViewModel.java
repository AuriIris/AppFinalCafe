package com.example.appfinalcafe.ui.Pedidos;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.appfinalcafe.Model.DetallePedido;
import com.example.appfinalcafe.Model.Mesa;
import com.example.appfinalcafe.Model.Pedido;
import com.example.appfinalcafe.Model.Producto;
import com.example.appfinalcafe.resource.ApiClientRetrofit;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetallePedidoViewModel extends AndroidViewModel {
    private MutableLiveData<Pedido> pedidoLiveData;
    private MutableLiveData<List<Mesa>> mesasLiveData;
    private MutableLiveData<List<DetallePedido>> detallePedidosLiveData;

    private MutableLiveData<List<Producto>> productoLiveData;
    private ApiClientRetrofit.EndpointCafe apiClient;
    private Context contexto;

    public DetallePedidoViewModel(Application application) {
        super(application);
        contexto = application.getApplicationContext();
        apiClient = ApiClientRetrofit.getEndpoint();
    }

    public LiveData<Pedido> getPedidoLiveData() {
        if (pedidoLiveData == null) {
            pedidoLiveData = new MutableLiveData<>();
        }
        return pedidoLiveData;
    }

    public LiveData<List<Mesa>> getMesas() {
        if (mesasLiveData == null) {
            mesasLiveData = new MutableLiveData<>();
            cargarMesa();
        }
        return mesasLiveData;
    }

    public LiveData<List<Producto>> getProductoLiveData() {
        if (productoLiveData == null) {
            productoLiveData = new MutableLiveData<>();
        }
        return productoLiveData;
    }

    public LiveData<List<DetallePedido>> getDetallePedidosLiveData() {
        if (detallePedidosLiveData == null) {
            detallePedidosLiveData = new MutableLiveData<>();
        }
        return detallePedidosLiveData;
    }

    public void obtenerPedido(int id) {
        SharedPreferences sp = contexto.getSharedPreferences("token.xml", 0);
        String token = sp.getString("token", "");
        Call<Pedido> pedidoCall = apiClient.obtenerPedido(token, id);
        pedidoCall.enqueue(new Callback<Pedido>() {
            @Override
            public void onResponse(Call<Pedido> call, Response<Pedido> response) {
                if (response.isSuccessful()) {
                    pedidoLiveData.setValue(response.body());
                } else {
                    Log.d("Ver", response.message());
                    Toast.makeText(contexto, "Error al obtener los Productos del pedido", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Pedido> call, Throwable t) {
                Log.d("Ver", t.getMessage());
                Toast.makeText(contexto, "Error al obtener los Productos del pedido", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void obtenerProductosDePedido(int id) {
        SharedPreferences sp = contexto.getSharedPreferences("token.xml", 0);
        String token = sp.getString("token", "");
        Call<List<DetallePedido>> pedidoCall = apiClient.obtenerProductosDePedido(token, id);
        pedidoCall.enqueue(new Callback<List<DetallePedido>>() {
            @Override
            public void onResponse(Call<List<DetallePedido>> call, Response<List<DetallePedido>> response) {
                if (response.isSuccessful()) {
                    if(response.body().size()>0) {
                        detallePedidosLiveData.postValue(response.body());
                    }
                    else {
                        Log.d("Ver", response.message());
                        ArrayList<DetallePedido> detped = new ArrayList<>();
                        DetallePedido pedidoVacio = new DetallePedido();
                        Producto producto = new Producto(0, "", 0, 0, 0);
                        pedidoVacio.setPedidoId(id);
                        pedidoVacio.setProducto(producto);
                        detped.add(pedidoVacio);
                        detallePedidosLiveData.postValue(detped);
                    }
                }
                else {
                    Toast.makeText(contexto, "Error al obtener los Productos del pedido", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<DetallePedido>> call, Throwable t) {
                Log.d("Ver66", t.getMessage());
                Toast.makeText(contexto, "Error al obtener los Productos del pedido", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void actualizarDatosPedido(Pedido pedido) {
        SharedPreferences sp = contexto.getSharedPreferences("token.xml", 0);
        String token = sp.getString("token", "");

        Call<Pedido> actualizarPedidoCall = apiClient.actualizarPedido(token, pedido.getId(), pedido);
        actualizarPedidoCall.enqueue(new Callback<Pedido>() {
            @Override
            public void onResponse(Call<Pedido> call, Response<Pedido> response) {
                if (response.isSuccessful()) {
                    pedidoLiveData.setValue(response.body());
                    Toast.makeText(contexto, "Pedido actualizado correctamente", Toast.LENGTH_SHORT).show();
                } else {
                    String errorMessage = response.message();
                    if (errorMessage.isEmpty()) {
                        errorMessage = "Error al actualizar el pedido";
                    }
                    Log.d("ver", errorMessage);
                    Toast.makeText(contexto, errorMessage, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Pedido> call, Throwable t) {
                Log.d("Ver", t.getMessage());
                Toast.makeText(contexto, "Error al actualizar el pedido", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void cargarMesa() {
        SharedPreferences sp = contexto.getSharedPreferences("token.xml", 0);
        String token = sp.getString("token", "");
        Call<List<Mesa>> mesas = apiClient.obtenerMesa(token);
        mesas.enqueue(new Callback<List<Mesa>>() {
            @Override
            public void onResponse(Call<List<Mesa>> call, Response<List<Mesa>> response) {
                if (response.isSuccessful()) {
                    List<Mesa> mesas = response.body();
                    mesasLiveData.postValue(mesas);
                } else {
                    Log.d("Salida", response.message());
                    Toast.makeText(contexto, "Error al Buscar Mesas 1", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Mesa>> call, Throwable t) {
                Toast.makeText(contexto, "Error al Buscar Mesas", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
