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

import com.example.appfinalcafe.Model.Pedido;
import com.example.appfinalcafe.R;

import java.util.List;

public class PedidosAdapter extends RecyclerView.Adapter<PedidosAdapter.ViewHolder> {
    private Context context;
    private List<Pedido> pedidos;
    private PedidosAdapter.OnItemClickListener listener;

    public interface OnItemClickListener {
        void onPedidoClick(Pedido pedido);
    }

    public PedidosAdapter(Context context, PedidosAdapter.OnItemClickListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
        notifyDataSetChanged();
    }

    @Override
    public PedidosAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_pedido, parent, false);
        return new PedidosAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PedidosAdapter.ViewHolder holder, int position) {
        Pedido pedido = pedidos.get(position);
        holder.bind(pedido);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onPedidoClick(pedido);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (pedidos != null) {
            return pedidos.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvId;
        private TextView tvmesaId;
        private TextView tvUsuarioId;
        private TextView tvestado;
        private TextView tvPrecio;
        private TextView tvfecha;

        public ViewHolder(View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.textViewPedidoId);
            tvmesaId = itemView.findViewById(R.id.textViewMesaId);
            tvUsuarioId = itemView.findViewById(R.id.textViewUsuarioId);
            tvestado = itemView.findViewById(R.id.textViewEstado);
            tvPrecio = itemView.findViewById(R.id.textViewPrecioTotal);
            tvfecha = itemView.findViewById(R.id.textViewFecha);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    Log.d("Pedido56+", pedidos.get(getAdapterPosition()) + " ");
                    bundle.putSerializable("pedido", pedidos.get(getAdapterPosition()));
                    Navigation.findNavController((Activity) context,R.id.nav_host_fragment_content_main).navigate(R.id.detallePedidoFragment, bundle);
                }
            });
        }

        public void bind(Pedido pedido) {
            tvId.setText(String.valueOf(pedido.getId()));
            tvmesaId.setText(String.valueOf(pedido.getMesa().toString()));
            tvUsuarioId.setText(String.valueOf(pedido.getUsuario().toString()));
            tvestado.setText(String.valueOf(pedido.getEstadoNombre()));
            tvPrecio.setText(String.valueOf(pedido.getPrecioTotal()));
            tvfecha.setText(String.valueOf(pedido.getFecha()));
        }
    }
}