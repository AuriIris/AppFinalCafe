package com.example.appfinalcafe.resource;

import com.example.appfinalcafe.Model.DetallePedido;
import com.example.appfinalcafe.Model.Mesa;
import com.example.appfinalcafe.Model.Pedido;
import com.example.appfinalcafe.Model.Producto;
import com.example.appfinalcafe.Model.Usuario;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;


public class ApiClientRetrofit {

    private static final String BASE_URL = "http://192.168.0.2:5207/";
    private static EndpointCafe endpoint;

    public static EndpointCafe getEndpoint() {

        Gson gson = new GsonBuilder().setLenient().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        endpoint = retrofit.create(EndpointCafe.class);
        return endpoint;
    }

    public interface EndpointCafe {
        @POST("Usuario/verify")
        Call<String> loginGoogle(@Header("Authorization") String token);

        @POST("Usuario/login")
        Call<String> login(@Body Usuario user);


        @GET("Usuario/perfil")
        Call<Usuario> obtenerPerfil(@Header("Authorization") String token);

        @PUT("Usuario/{id}")
        Call<Usuario> actualizarPerfil(@Header("Authorization") String token, @Path("id") int id, @Body Usuario usuario);

        //PRODUCTOS
        @GET("Producto")
        Call<List<Producto>> obtenerProductos(@Header("Authorization") String token);
        @GET("Producto/{id}")
        Call<Producto> obtenerProducto(@Header("Authorization") String token, @Path("id") int id);

        @POST("Producto")
        Call<Producto> guardarNuevoProducto(@Header("Authorization") String token,  @Body Producto producto);

        @GET("Producto/Pedido/{id}")
        Call<List<DetallePedido>> obtenerProductosDePedido(@Header("Authorization") String token, @Path("id") int id);

        @PUT("Producto/{id}")
        Call<Producto> actualizarProducto(@Header("Authorization") String token, @Path("id") int id, @Body Producto producto);

        //MESAS
        @GET("Mesa")
        Call<List<Mesa>> obtenerMesa(@Header("Authorization") String token);
        @GET("Mesa/{id}")
        Call<Mesa> obtenerMesa(@Header("Authorization") String token, @Path("id") int id);

        @PUT("Mesa/{id}")
        Call<Mesa> actualizarMesa(@Header("Authorization") String token, @Path("id") int id, @Body Mesa mesa);

        //PEDIDOS
        @GET("Pedido")
        Call<List<Pedido>> obtenerPedidos(@Header("Authorization") String token);
        @GET("Pedido/{id}")
        Call<Pedido> obtenerPedido(@Header("Authorization") String token, @Path("id") int id);

        @PUT("Pedido/{id}")
        Call<Pedido> actualizarPedido(@Header("Authorization") String token, @Path("id") int id, @Body Pedido pedido);

        @GET("Pedido/Cierre/{fechas}")
        Call<ResponseBody> calcularCierre(@Header("Authorization") String token, @Path("fechas") String fechas);


        //DETALLE PEDIDO
        @POST("DetallePedido")
        Call<DetallePedido> agregarProductoaPedido(@Header("Authorization") String token, @Body DetallePedido detallePedido);

    }
}