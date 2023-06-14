package com.example.appfinalcafe.ui.Pedidos;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appfinalcafe.Model.DetallePedido;
import com.example.appfinalcafe.Model.Producto;
import com.example.appfinalcafe.R;
import com.example.appfinalcafe.ui.Mesas.MesasAdapter;

import java.util.List;

public class ProductosAdapter extends RecyclerView.Adapter<ProductosAdapter.ViewHolder> {
    private Context context;
    private List<DetallePedido> detallePedidos;
    private OnItemClickListener listener;
    private int selectedPosition = RecyclerView.NO_POSITION;


    public interface OnItemClickListener {
        void onProductoClick(DetallePedido detallePedido);
    }

    public ProductosAdapter(Context context,List<DetallePedido> detallePedidos) {
        this.context = context;
        this.detallePedidos=detallePedidos;
    }

    public void setProductos(List<DetallePedido> detallePedidos) {
        this.detallePedidos = detallePedidos;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_producto, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final DetallePedido detallePedido = detallePedidos.get(position);
        holder.bind(detallePedido);
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                listener.onProductoClick(detallePedido);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        if (detallePedidos != null) {
            return detallePedidos.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvId;
        private TextView tvPrecio;
        private TextView tvNombre;
        private TextView tvCategoria;
        private TextView tvCantidad;

        private TextView tvEstado;
        private TextView tvIdL;
        private TextView tvPrecioL;
        private TextView tvNombreL;
        private TextView tvCategoriaL;
        private TextView tvCantidadL;

        private TextView tvEstadoL;
        public ViewHolder(View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.textViewProductoID);
            tvNombre = itemView.findViewById(R.id.textViewNombre);
            tvPrecio = itemView.findViewById(R.id.textViewPrecio);
            tvCategoria = itemView.findViewById(R.id.textViewCategoria);
            tvEstado = itemView.findViewById(R.id.textViewEstado);
            tvCantidad = itemView.findViewById(R.id.textViewCantidad);
            tvIdL = itemView.findViewById(R.id.textViewProductoIDLabel);
            tvNombreL = itemView.findViewById(R.id.textViewNombreLabel);
            tvPrecioL = itemView.findViewById(R.id.textViewPrecioLabel);
            tvCategoriaL = itemView.findViewById(R.id.textViewCategoriaLabel);
            tvEstadoL = itemView.findViewById(R.id.textViewEstadoLabel1);
            tvCantidadL = itemView.findViewById(R.id.textViewCantidadLabel1);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    Log.d("Pedido56+de prod a adapter", detallePedidos.get(getAdapterPosition()).getPedido() + " ");
                    bundle.putSerializable("pedido", detallePedidos.get(getAdapterPosition()).getPedido());
                    Navigation.findNavController((Activity) context,R.id.nav_host_fragment_content_main).navigate(R.id.nav_slideshow, bundle);
                }
            });
        }

        public void bind(DetallePedido detallePedido) {
            if(detallePedido.getProducto().getId()!=0){

            tvId.setText(String.valueOf(detallePedido.getProducto().getId()));
            tvId.setVisibility(View.GONE);
            tvIdL.setVisibility(View.GONE);
            tvNombre.setText(String.valueOf(detallePedido.getProducto().getNombre()));
            tvPrecio.setText(String.valueOf(detallePedido.getPrecio()));
            tvCategoria.setText(String.valueOf(detallePedido.getProducto().getCategoriaNombre()));
            tvEstado.setText(String.valueOf(detallePedido.getProducto().getEstadoNombre()));
            tvCantidad.setText(String.valueOf(detallePedido.getCantidad()));

                tvNombreL.setVisibility(View.VISIBLE);
                tvPrecioL.setVisibility(View.VISIBLE);
                tvCategoriaL.setVisibility(View.VISIBLE);
                tvEstadoL.setVisibility(View.VISIBLE);
                tvCantidadL.setVisibility(View.VISIBLE);
            }
            else{
                tvIdL.setText("Ingresar Producto");
                tvNombreL.setVisibility(View.GONE);
                tvPrecioL.setVisibility(View.GONE);
                tvCategoriaL.setVisibility(View.GONE);
                tvEstadoL.setVisibility(View.GONE);
                tvCantidadL.setVisibility(View.GONE);

            }
        }

    }
}