package com.example.hotelitoreservacionfacilito.app.administrador;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import com.example.hotelitoreservacionfacilito.R;
import com.example.hotelitoreservacionfacilito.adapters.RecyclerClientes;
import com.example.hotelitoreservacionfacilito.models.Cliente;
import com.example.hotelitoreservacionfacilito.service.ClienteService;

import java.util.ArrayList;
import java.util.List;

public class Clientes extends AppCompatActivity {

    TextView tvTitulo;
    RecyclerView rvClientes;

    ClientesTask clientesTask = new ClientesTask();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientes);

        tvTitulo = findViewById(R.id.tvTitulo);
        rvClientes = findViewById(R.id.rvClientes);

        clientesTask.execute();
    }

    public void reinicarAsysnc(){
        clientesTask.cancel(true);
        clientesTask = new ClientesTask();
    }

    public class ClientesTask extends AsyncTask<String, String, List<Cliente>>{

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected List<Cliente> doInBackground(String... strings) {
            List<Cliente> listaClientes = new ArrayList<>();
            try {
                ClienteService clienteService = new ClienteService();
                listaClientes = clienteService.obtenerClientes();
            }catch (Exception e){
                System.out.println("Error al traer la lista de clientes: " +e.getMessage());
            }
            return listaClientes;
        }

        @Override
        protected void onPostExecute(List<Cliente> clientes) {
            super.onPostExecute(clientes);
            try {
                if(!clientes.isEmpty()){
                    RecyclerClientes adapter = new RecyclerClientes(clientes, getApplicationContext());
                    rvClientes.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));
                    rvClientes.setAdapter(adapter);
                }
                reinicarAsysnc();
            }catch (Throwable throwable){
                System.out.println("Error al imprimir la lista de clientes: " +throwable.getMessage());
            }
        }
    }
}