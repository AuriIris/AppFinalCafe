package com.example.appfinalcafe.ui.Pedidos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

    public ProductosAdapter(Context context, ProductosAdapter.OnItemClickListener listener) {
        this.context = context;
        this.listener = listener;
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
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onProductoClick(detallePedido);
            }
        });
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
        public ViewHolder(View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.textViewProductoID);
            tvNombre = itemView.findViewById(R.id.textViewNombre);
            tvPrecio = itemView.findViewById(R.id.textViewPrecio);
            tvCategoria = itemView.findViewById(R.id.textViewCategoria);
            tvEstado = itemView.findViewById(R.id.textViewEstado);
            tvCantidad = itemView.findViewById(R.id.textViewCantidad);
        }

        public void bind(DetallePedido detallePedido) {
            tvId.setText(String.valueOf(detallePedido.getProducto().getId()));
            tvNombre.setText(String.valueOf(detallePedido.getProducto().getNombre()));
            tvPrecio.setText(String.valueOf(detallePedido.getPrecio()));
            tvCategoria.setText(String.valueOf(detallePedido.getProducto().getCategoriaNombre()));
            tvEstado.setText(String.valueOf(detallePedido.getProducto().getEstadoNombre()));
            tvCantidad.setText(String.valueOf(detallePedido.getCantidad()));
        }
    }
}