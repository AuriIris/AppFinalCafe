package com.example.appfinalcafe;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.appfinalcafe.Model.Usuario;
import com.example.appfinalcafe.resource.ApiClientRetrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends AndroidViewModel {
    private Context context;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public void login(String mail, String pass) {
        Usuario user = new Usuario(mail, pass);
        ApiClientRetrofit.EndpointCafe endpoint = ApiClientRetrofit.getEndpoint();
        Call<String> call = endpoint.login(user);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        // Guardar token en archivo de preferencias e iniciar activity
                        Log.d("salida", response.body());
                        SharedPreferences sp = context.getSharedPreferences("token.xml", 0);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("token", "Bearer " + response.body());
                        editor.apply();
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                } else {
                    Log.d("salida", response.message());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("ver", t.getMessage());
                Toast.makeText(context, "Error al llamar al login", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
