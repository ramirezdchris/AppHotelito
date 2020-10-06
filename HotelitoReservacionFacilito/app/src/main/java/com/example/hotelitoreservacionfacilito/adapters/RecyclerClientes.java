package com.example.hotelitoreservacionfacilito.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotelitoreservacionfacilito.R;
import com.example.hotelitoreservacionfacilito.models.Cliente;

import java.util.List;

public class RecyclerClientes extends RecyclerView.Adapter<RecyclerClientes.ViewHolder> {

    private List<Cliente> lista;
    private Context context;

    public RecyclerClientes(List<Cliente> lista, Context context) {
        this.lista = lista;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_clientes, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.asignarDatos(lista.get(position));
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView iVFoto;
        TextView tvNombreCliente, tvTelefono;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iVFoto = itemView.findViewById(R.id.iVFoto);
            tvNombreCliente = itemView.findViewById(R.id.tvNombreCliente);
            tvTelefono = itemView.findViewById(R.id.tvTelefono);
        }

        public void asignarDatos(Cliente cliente){
            iVFoto.setImageResource(R.drawable.logocircle);
            tvNombreCliente.setText(cliente.getNombre() + " " +cliente.getApellido());
            tvTelefono.setText(cliente.getCorreo());
        }
    }
}
