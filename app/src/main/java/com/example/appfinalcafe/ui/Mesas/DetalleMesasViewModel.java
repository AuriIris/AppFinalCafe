package com.example.appfinalcafe.ui.Mesas;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.appfinalcafe.Model.Mesa;
import com.example.appfinalcafe.Model.Producto;
import com.example.appfinalcafe.resource.ApiClientRetrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleMesasViewModel extends AndroidViewModel {
    private MutableLiveData<Mesa> mesaLiveData = new MutableLiveData<>();
    private ApiClientRetrofit.EndpointCafe apiClient;
    private Context contexto;

    public DetalleMesasViewModel(Application application) {
        super(application);
        contexto = application.getApplicationContext();
        apiClient = ApiClientRetrofit.getEndpoint();
    }

    public LiveData<Mesa> getMesaLiveData() {
        return mesaLiveData;
    }


    public void actualizarDatosMesa(Mesa mesa) {
        SharedPreferences sp = contexto.getSharedPreferences("token.xml", 0);
        String token = sp.getString("token", "");

        Call<Mesa> actualizarMesaCall = apiClient.actualizarMesa(token, mesa.getId(), mesa);
        actualizarMesaCall.enqueue(new Callback<Mesa>() {
            @Override
            public void onResponse(Call<Mesa> call, Response<Mesa> response) {
                if (response.isSuccessful()) {
                    mesaLiveData.setValue(response.body());
                    Toast.makeText(contexto, "Producto actualizado correctamente", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("ver", response.message());
                    Toast.makeText(contexto, "Error al actualizar el Mesa 1", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Mesa> call, Throwable t) {
                Toast.makeText(contexto, "Error al actualizar el producto", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
