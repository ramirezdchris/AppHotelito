package com.example.hotelitoreservacionfacilito.interfaces;

import com.example.hotelitoreservacionfacilito.models.Cliente;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ClienteAPI {

    @GET()
    Call<List<Cliente>> getCliente();
}
