package com.example.appfinalcafe.ui.Perfil;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.appfinalcafe.Model.Usuario;
import com.example.appfinalcafe.resource.ApiClientRetrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PerfilViewModel extends AndroidViewModel {
    private MutableLiveData<Usuario> usuarioLiveData = new MutableLiveData<>();
    private ApiClientRetrofit.EndpointCafe apiClient;
    private Context contexto;

    public PerfilViewModel(Application application) {
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

    public void actualizarDatosUsuario(String nombre, int rol, String mail, String clave, int permiso) {
        Usuario usuarioActual = usuarioLiveData.getValue();
        if (usuarioActual != null) {
            usuarioActual.setNombre(nombre);
            usuarioActual.setRol(rol);
            usuarioActual.setPermiso(permiso);
            usuarioActual.setMail(mail);
            SharedPreferences sp = contexto.getSharedPreferences("token.xml", 0);
            String token = sp.getString("token", "");
            int usuarioId = usuarioActual.getId();
            Log.d("ver32", usuarioId+"");
            Log.d("ver32", usuarioActual.toString());
            Log.d("ver32", token);

            Call<Usuario> actualizarPerfilCall = apiClient.actualizarPerfil(token, usuarioId, usuarioActual);
            actualizarPerfilCall.enqueue(new Callback<Usuario>() {
                @Override
                public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                    Log.d("ver", response.message());
                    if (response.isSuccessful()) {
                        usuarioLiveData.setValue(response.body());
                    } else {
                        Toast.makeText(contexto, "Error al actualizar el perfil", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Usuario> call, Throwable t) {

                }
            });

        }
    }
}
