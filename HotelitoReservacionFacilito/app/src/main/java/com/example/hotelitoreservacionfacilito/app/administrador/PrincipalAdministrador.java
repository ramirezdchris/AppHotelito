package com.example.hotelitoreservacionfacilito.app.administrador;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.hotelitoreservacionfacilito.Logued;
import com.example.hotelitoreservacionfacilito.R;

public class PrincipalAdministrador extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_administrador);

        textView = findViewById(R.id.textView);
        textView.setText(Logued.usuarioEmpleadoLogued.getPersonal().getRol().getNombre_rol());

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Clientes.class);
                startActivity(i);
            }
        });
    }
}