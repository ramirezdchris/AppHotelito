package com.example.hotelitoreservacionfacilito.app.administrador;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hotelitoreservacionfacilito.R;
import com.example.hotelitoreservacionfacilito.adapters.RecyclerClientes;
import com.example.hotelitoreservacionfacilito.models.Cliente;
import com.example.hotelitoreservacionfacilito.service.ClienteService;

import java.util.ArrayList;
import java.util.List;

public class ClienteCrud extends AppCompatActivity {

    EditText edtNombre,edtApellido,edtCorreo,edtUsuario,edtClave;
    Button btnAgregar, btnEditar;

    AgregarCliente agregarCliente = new AgregarCliente();
    ClienteById clienteById = new ClienteById();
    EditarCliente editarCliente = new EditarCliente();

    int idCliente = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_crud);
        idCliente = getIntent().getIntExtra("id", 0);

        edtNombre = findViewById(R.id.edtNombre);
        edtApellido = findViewById(R.id.edtApellido);
        edtCorreo = findViewById(R.id.edtCorreo);
        edtUsuario = findViewById(R.id.edtUsuario);
        edtClave = findViewById(R.id.edtClave);

        Cliente cliente = new Cliente();

        btnAgregar = findViewById(R.id.btnAgregar);
        btnEditar = findViewById(R.id.btnEditar);

        if(idCliente != 0) {
            clienteById.execute();
            btnAgregar.setEnabled(false);
        }else{
            btnEditar.setEnabled(false);
        }

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarCliente.execute();
            }
        });

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editarCliente.execute();
            }
        });

    }



    public void reinicarAsysnc(){
        agregarCliente.cancel(true);
        agregarCliente = new AgregarCliente();

        editarCliente.cancel(true);
        editarCliente = new EditarCliente();

        clienteById.cancel(true);
        clienteById = new ClienteById();
    }

    public class AgregarCliente extends AsyncTask<String, String, Cliente> {

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected Cliente doInBackground(String... strings) {
            List<Cliente> listaClientes = new ArrayList<>();
            Cliente cliente = new Cliente();
            try {
                ClienteService clienteService = new ClienteService();
                cliente.setIdCliente(0);
                cliente.setNombre(edtNombre.getText().toString().trim());
                cliente.setApellido(edtApellido.getText().toString().trim());
                cliente.setCorreo(edtCorreo.getText().toString().trim());
                cliente.setUsuario(edtUsuario.getText().toString().trim());
                cliente.setClave(edtClave.getText().toString().trim());

                clienteService.crearCliente(cliente);
            }catch (Exception e){
                System.out.println("Error al traer la lista de clientes: " +e.getMessage());
            }
            return cliente;
        }

        @Override
        protected void onPostExecute(Cliente cliente) {
            super.onPostExecute(cliente);
            try {
                if(!(cliente == null)){
                    //RecyclerClientes adapter = new RecyclerClientes(clientes, getApplicationContext());
                    //rvClientes.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));
                    //rvClientes.setAdapter(adapter);
                    Toast.makeText(getApplicationContext(), "Cliente Agregado" , Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), Clientes.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }
                reinicarAsysnc();
            }catch (Throwable throwable){
                System.out.println("Error al imprimir al agregar clientes: " +throwable.getMessage());
            }
        }
    }

    public class EditarCliente extends AsyncTask<String, String, Cliente> {

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected Cliente doInBackground(String... strings) {
            List<Cliente> listaClientes = new ArrayList<>();
            Cliente cliente = new Cliente();
            try {
                ClienteService clienteService = new ClienteService();
                cliente.setIdCliente(idCliente);
                cliente.setNombre(edtNombre.getText().toString().trim());
                cliente.setApellido(edtApellido.getText().toString().trim());
                cliente.setCorreo(edtCorreo.getText().toString().trim());
                cliente.setUsuario(edtUsuario.getText().toString().trim());
                cliente.setClave(edtClave.getText().toString().trim());

                clienteService.actualizarClientePorId(cliente);
            }catch (Exception e){
                System.out.println("Error al traer la lista de clientes: " +e.getMessage());
            }
            return cliente;
        }

        @Override
        protected void onPostExecute(Cliente cliente) {
            super.onPostExecute(cliente);
            try {
                if(!(cliente == null)){
                    //RecyclerClientes adapter = new RecyclerClientes(clientes, getApplicationContext());
                    //rvClientes.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));
                    //rvClientes.setAdapter(adapter);
                    Toast.makeText(getApplicationContext(), "Cliente Actualizado" , Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), Clientes.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }
                reinicarAsysnc();
            }catch (Throwable throwable){
                System.out.println("Error al imprimir al editar clientes: " +throwable.getMessage());
            }
        }
    }



    public class ClienteById extends AsyncTask<String, String, Cliente> {

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected Cliente doInBackground(String... strings) {
            List<Cliente> listaClientes = new ArrayList<>();
            Cliente cliente = new Cliente();
            try {
                ClienteService clienteService = new ClienteService();
                cliente = clienteService.obtenerClientePorId(idCliente);
                System.out.println("Objeto: " +cliente.toString());
            }catch (Exception e){
                System.out.println("Error al traer la lista de clientes: " +e.getMessage());
            }
            return cliente;
        }

        @Override
        protected void onPostExecute(Cliente cliente) {
            super.onPostExecute(cliente);
            try {
                if(!(cliente == null)){
                    edtNombre.setText(cliente.getNombre());
                    edtApellido.setText(cliente.getApellido());
                    edtCorreo.setText(cliente.getCorreo());
                    edtUsuario.setText(cliente.getUsuario());
                    edtClave.setText(cliente.getClave());
                    Toast.makeText(getApplicationContext(), "Cliente Buscado" , Toast.LENGTH_SHORT).show();
                }
                reinicarAsysnc();
            }catch (Throwable throwable){
                System.out.println("Error al imprimir al buscar clientes: " +throwable.getMessage());
            }
        }
    }
}