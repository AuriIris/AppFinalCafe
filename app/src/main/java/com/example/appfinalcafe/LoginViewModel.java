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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

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
    public void loginGoogle(String token) {
        ApiClientRetrofit.EndpointCafe endpoint = ApiClientRetrofit.getEndpoint();
        token="Bearer " + token;
        Log.d("salida", "Token"+token);
        Call<String> call = endpoint.loginGoogle(token);
        Log.d("salida", "Token"+token);
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
                Log.d("ver64", t.getMessage());
                Toast.makeText(context, "Error al llamar al login", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void firebaseAuthWithGoogle(String googleToken) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        AuthCredential credential = GoogleAuthProvider.getCredential(googleToken, null);

        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // El inicio de sesión con Google fue exitoso
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            String uid = user.getUid();
                            String email = user.getEmail();
                            SharedPreferences sp = context.getSharedPreferences("user_data.xml", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString("uid", uid);
                            editor.putString("email", email);
                            editor.apply();
                        } else {
                            // Fallo en el inicio de sesión con Google
                            Log.e("LoginActivity", "Fallo en el inicio de sesión con Google: " + task.getException().getMessage());
                        }
                    }
                });
    }



}
