package com.example.appfinalcafe;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.appfinalcafe.Model.Usuario;
import com.example.appfinalcafe.resource.ApiClientRetrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityViewModel extends AndroidViewModel {
    private Context contexto;
    private MutableLiveData<Usuario> usuarioLiveData = new MutableLiveData<>();
    private ApiClientRetrofit.EndpointCafe apiClient;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        contexto = application.getApplicationContext();
        apiClient = ApiClientRetrofit.getEndpoint();
    }

    public LiveData<Usuario> getUsuarioLiveData() {
        return usuarioLiveData;
    }

    public void obtenerDatosUsuario() {
        SharedPreferences sp = contexto.getSharedPreferences("token.xml", 0);
        String token = sp.getString("token", "");
        Call<Usuario> perfil = apiClient.obtenerPerfil(token);
        perfil.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful()) {
                    usuarioLiveData.postValue(response.body());
                }
                else{
                    Log.d("ver23", response.message());
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Toast.makeText(contexto, "Error al acceder al perfil", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
