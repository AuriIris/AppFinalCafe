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

public class SeleccionarProductoViewModel extends AndroidViewModel {
    private MutableLiveData<List<Producto>> productoLiveData = new MutableLiveData<>();
    private MutableLiveData<DetallePedido> detallePedidoLiveData = new MutableLiveData<>();
    private MutableLiveData<Pedido> pedidoLiveData = new MutableLiveData<>();
    private ApiClientRetrofit.EndpointCafe apiClient;
    private Context contexto;
    public LiveData<DetallePedido> getDetallePedidoLiveData() {
        return detallePedidoLiveData;
    }

    public void setDetallePedido(DetallePedido detallePedido) {
        detallePedidoLiveData.setValue(detallePedido);
    }

    public SeleccionarProductoViewModel(Application application) {
        super(application);
        contexto = application.getApplicationContext();
        apiClient = ApiClientRetrofit.getEndpoint();
    }

    public LiveData<List<Producto>> getProductoLiveData() {
        return productoLiveData;
    }

    public void obtenerDatosProducto() {
        SharedPreferences sp = contexto.getSharedPreferences("token.xml", 0);
        String token = sp.getString("token", "");
        Call<List<Producto>> productoCall = apiClient.obtenerProductos(token);
        productoCall.enqueue(new Callback<List<Producto>>() {
            @Override
            public void onResponse(Call<List<Producto>> call, Response<List<Producto>> response) {
                if (response.isSuccessful()) {
                    productoLiveData.setValue(response.body());
                } else {
                    Toast.makeText(contexto, "Error al obtener los datos del producto", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Producto>> call, Throwable t) {
                Toast.makeText(contexto, "Error al obtener los datos del producto", Toast.LENGTH_SHORT).show();
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
                    Log.d("pedido", "aca");
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
    public void agregarProductoalPedido(int id_pedido, int id_Producto, int cantidad, Double precio) {

        DetallePedido detallePedido = new DetallePedido( 0,id_pedido,  id_Producto,  cantidad,  precio*cantidad);
        SharedPreferences sp = contexto.getSharedPreferences("token.xml", 0);
        String token = sp.getString("token", "");
        Call<DetallePedido> agregarProdaPedCall = apiClient.agregarProductoaPedido(token,detallePedido);
        agregarProdaPedCall.enqueue(new Callback<DetallePedido>() {
            @Override
            public void onResponse(Call<DetallePedido> call, Response<DetallePedido> response) {
                    Log.d("ver", response.message());
                    if (response.isSuccessful()) {
                        detallePedidoLiveData.setValue(response.body());
                    } else {
                        Toast.makeText(contexto, "Error al agregar producto 1", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<DetallePedido> call, Throwable t) {
                    Toast.makeText(contexto, "Error al agregar producto", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }




