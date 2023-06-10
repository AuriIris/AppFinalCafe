package com.example.appfinalcafe.ui.Productos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.appfinalcafe.Model.DetallePedido;
import com.example.appfinalcafe.Model.Producto;
import com.example.appfinalcafe.R;

import java.util.List;

public class SeleccionarProductosAdapter extends RecyclerView.Adapter<SeleccionarProductosAdapter.ViewHolder> {
    private Context context;
    private List<Producto> productos;
    private OnItemClickListener listener;
    private int selectedPosition = RecyclerView.NO_POSITION;

    public interface OnItemClickListener {
        void onProductoClick(Producto producto);
    }

    public SeleccionarProductosAdapter(Context context, OnItemClickListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
        notifyDataSetChanged();
    }

    public Producto getProductoSeleccionado() {
        if (selectedPosition != RecyclerView.NO_POSITION) {
            return productos.get(selectedPosition);
        }
        return null;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_producto, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Producto producto = productos.get(position);
        holder.bind(producto);

        // Establecer el estilo de selección según la posición
        if (position == selectedPosition) {
            // Aplicar el estilo de selección al card
            holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.card_selected_background));
        } else {
            // Restaurar el estilo predeterminado del card
            holder.itemView.setBackgroundColor(context.getResources().getColor(android.R.color.transparent));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Actualizar la posición seleccionada
                selectedPosition = holder.getAdapterPosition();
                notifyDataSetChanged();

                listener.onProductoClick(producto);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (productos != null) {
            return productos.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvId;
        private TextView tvPrecio;
        private TextView tvNombre;
        private TextView tvCategoria;

        public ViewHolder(View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.textViewProductoID);
            tvNombre = itemView.findViewById(R.id.textViewNombre);
            tvPrecio = itemView.findViewById(R.id.textViewPrecio);
            tvCategoria = itemView.findViewById(R.id.textViewCategoria);
        }

        public void bind(Producto producto) {
            tvId.setText(String.valueOf(producto.getId()));
            tvNombre.setText(String.valueOf(producto.getNombre()));
            tvPrecio.setText(String.valueOf(producto.getPrecio()));
            tvCategoria.setText(String.valueOf(producto.getCategoria()));
        }
    }
}