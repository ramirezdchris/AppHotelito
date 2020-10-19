package com.example.hotelitoreservacionfacilito.app.administrador;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hotelitoreservacionfacilito.R;
import com.example.hotelitoreservacionfacilito.adapters.RecyclerClientes;
import com.example.hotelitoreservacionfacilito.interfaces.ClienteAPI;
import com.example.hotelitoreservacionfacilito.models.Cliente;
import com.example.hotelitoreservacionfacilito.service.ClienteService;
import com.example.hotelitoreservacionfacilito.service.Constantes;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Clientes extends AppCompatActivity {

    TextView tvTitulo;
    Button btnAgregarCliente;
    RecyclerView rvClientes;

    ClientesTask clientesTask = new ClientesTask();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientes);

        tvTitulo = findViewById(R.id.tvTitulo);
        btnAgregarCliente = findViewById(R.id.btnAgregarCliente);
        rvClientes = findViewById(R.id.rvClientes);

        clientesTask.execute();

        //getCliente();

        btnAgregarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ClienteCrud.class);
                startActivity(i);
            }
        });
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

    public void getCliente(){
        System.out.println("URL: " + Constantes.URL_CLIENTE);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constantes.URL_CLIENTE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ClienteAPI clienteAPI = retrofit.create(ClienteAPI.class);
        Call<List<Cliente>> call = clienteAPI.getCliente();

        call.enqueue(new Callback<List<Cliente>>() {
            @Override
            public void onResponse(Call<List<Cliente>> call, Response<List<Cliente>> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Respuesta: " +response.message(), Toast.LENGTH_SHORT).show();
                    return;
                }
                List<Cliente> clientes = response.body();
                RecyclerClientes adapter = new RecyclerClientes(clientes, getApplicationContext());
                rvClientes.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));
                rvClientes.setAdapter(adapter);

                //Toast.makeText(getApplicationContext(), "Cantidad de Clientes: " +clientes.size(), Toast.LENGTH_SHORT).show();
                System.out.println("Cantidad de clientes: " +clientes.size());
                //return datos;
            }

            @Override
            public void onFailure(Call<List<Cliente>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error al consultar el servidor: " +t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
}