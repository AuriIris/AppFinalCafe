package com.example.appfinalcafe.ui.Mesas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.appfinalcafe.Model.Mesa;
import com.example.appfinalcafe.Model.Producto;
import com.example.appfinalcafe.R;
import com.example.appfinalcafe.ui.Mesas.MesasAdapter;

import java.util.List;

public class MesasAdapter extends RecyclerView.Adapter<MesasAdapter.ViewHolder> {
    private Context context;
    private List<Mesa> mesas;
    private MesasAdapter.OnItemClickListener listener;

    public interface OnItemClickListener {
        void onMesaClick(Mesa mesas);
    }

    public MesasAdapter(Context context, MesasAdapter.OnItemClickListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public void setMesas(List<Mesa> mesas) {
        this.mesas = mesas;
        notifyDataSetChanged();
    }

    @Override
    public MesasAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_mesa, parent, false);
        return new MesasAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MesasAdapter.ViewHolder holder, int position) {
        Mesa mesa = mesas.get(position);
        holder.bind(mesa);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onMesaClick(mesa);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mesas != null) {
            return mesas.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvId;
        private TextView tvSucursal;
        private TextView tvNumero;
        private TextView tvEstado;

        public ViewHolder(View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.textViewMesaId);
            tvNumero = itemView.findViewById(R.id.textViewNumero);
            tvSucursal = itemView.findViewById(R.id.textViewSucursalId);
            tvEstado = itemView.findViewById(R.id.textViewEstado);
        }

        public void bind(Mesa mesas) {
            tvId.setText(String.valueOf(mesas.getId()));
            tvSucursal.setText(String.valueOf(mesas.getSucursalId()));
            tvEstado.setText(String.valueOf(mesas.getEstado()));
            tvNumero.setText(String.valueOf(mesas.getNumero()));
        }
    }
}