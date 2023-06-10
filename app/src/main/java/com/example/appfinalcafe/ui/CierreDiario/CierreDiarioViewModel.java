package com.example.appfinalcafe.ui.CierreDiario;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.appfinalcafe.Model.Pedido;
import com.example.appfinalcafe.resource.ApiClientRetrofit;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CierreDiarioViewModel extends AndroidViewModel {

    private MutableLiveData<String> totalCierreLiveDAta = new MutableLiveData<>();
    private ApiClientRetrofit.EndpointCafe apiClient;
    private Context contexto;

    public CierreDiarioViewModel(@NonNull Application application) {
        super(application);
        contexto = application.getApplicationContext();
        apiClient = ApiClientRetrofit.getEndpoint();
    }
    public LiveData<String> getTotalCierreLiveData() {
        if (totalCierreLiveDAta == null) {
            totalCierreLiveDAta = new MutableLiveData<>();
        }
        return totalCierreLiveDAta;
    }
    public void calcularCierre(String fecha) {
        SharedPreferences sp = contexto.getSharedPreferences("token.xml", 0);
        String token = sp.getString("token", "");
        Log.d("Ver45", "calcularCierre: " + fecha);
        Call<ResponseBody> cierreCall = apiClient.calcularCierre(token, fecha);
        cierreCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String responseBody = response.body().string();
                        Log.d("VERLALAL", responseBody);
                        totalCierreLiveDAta.setValue(responseBody);
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(contexto, "Error al procesar la respuesta", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.d("Ver46", response.message());
                    Toast.makeText(contexto, "Error al Calcular cierre1", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("Ver55", t.getMessage());
                Toast.makeText(contexto, "Error al Calcular cierre", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
