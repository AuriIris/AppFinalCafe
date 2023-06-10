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

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MesasViewModel extends AndroidViewModel {

    private MutableLiveData<List<Mesa>> mesasLiveData;
    private ApiClientRetrofit.EndpointCafe apiClient;
    private Context contexto;

    public MesasViewModel(Application application) {
        super(application);
        contexto = application.getApplicationContext();
        apiClient = ApiClientRetrofit.getEndpoint();
    }

    public LiveData<List<Mesa>> getMesas() {
        if (mesasLiveData == null) {
            mesasLiveData = new MutableLiveData<>();
            cargarMesa();
        }
        return mesasLiveData;
    }

    private void cargarMesa() {
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