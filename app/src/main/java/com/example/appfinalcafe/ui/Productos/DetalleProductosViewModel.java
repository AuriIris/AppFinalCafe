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
import com.example.appfinalcafe.Model.Producto;
import com.example.appfinalcafe.Model.Usuario;
import com.example.appfinalcafe.resource.ApiClientRetrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleProductosViewModel extends AndroidViewModel {
    private MutableLiveData<Producto> productoLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Producto>> productosLiveData = new MutableLiveData<>();
    private MutableLiveData<List<DetallePedido>> detallePedidoLiveData = new MutableLiveData<>();
    private ApiClientRetrofit.EndpointCafe apiClient;
    private Context contexto;

    public DetalleProductosViewModel(Application application) {
        super(application);
        contexto = application.getApplicationContext();
        apiClient = ApiClientRetrofit.getEndpoint();
    }

    public LiveData<Producto> getProductoLiveData() {
        return productoLiveData;
    }
    public LiveData<List<Producto>> getProductosLiveData() {
        return productosLiveData;
    }
    public void obtenerProductos(int id) {
        SharedPreferences sp = contexto.getSharedPreferences("token.xml", 0);
        String token = sp.getString("token", "");
        Call<List<DetallePedido>> productoCall = apiClient.obtenerProductosDePedido(token, id);
        productoCall.enqueue(new Callback<List<DetallePedido>>() {
            @Override
            public void onResponse(Call<List<DetallePedido>> call, Response<List<DetallePedido>> response) {
                if (response.isSuccessful()) {
                    detallePedidoLiveData.setValue(response.body());
                } else {
                    Toast.makeText(contexto, "Error al obtener los datos del producto", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<DetallePedido>> call, Throwable t) {
                Toast.makeText(contexto, "Error al obtener los datos del producto", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void actualizarDatosProducto(Producto producto) {
        SharedPreferences sp = contexto.getSharedPreferences("token.xml", 0);
        String token = sp.getString("token", "");

        Call<Producto> actualizarProductoCall = apiClient.actualizarProducto(token, producto.getId(), producto);
        actualizarProductoCall.enqueue(new Callback<Producto>() {
            @Override
            public void onResponse(Call<Producto> call, Response<Producto> response) {
                if (response.isSuccessful()) {
                    productoLiveData.setValue(response.body());
                    Toast.makeText(contexto, "Producto actualizado correctamente", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("ver", response.message());//imprime Not Found
                    Toast.makeText(contexto, "Error al actualizar el producto", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Producto> call, Throwable t) {
                Toast.makeText(contexto, "Error al actualizar el producto", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
