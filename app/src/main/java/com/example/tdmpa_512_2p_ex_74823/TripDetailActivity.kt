package com.example.tdmpa_512_2p_ex_74823

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class TripDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trip_detail)

        var txtDestino = findViewById<TextView>(R.id.txtDestinoElegido);
        var txtFecha = findViewById<TextView>(R.id.txtFechaPuesta);
        var txtDuracion = findViewById<TextView>(R.id.txtDuracionPuesta);
        var txtAgradecimientoUsuario = findViewById<TextView>(R.id.txtAgradecimiento);
        var btnRegresar = findViewById<Button>(R.id.btnRegresar);
        var txtActividad = findViewById<TextView>(R.id.txtActividad);



        var destino  = intent?.extras?.getString("destino");
        var fecha = intent?.extras?.getString("fecha");
        var duracion = intent?.extras?.getInt("duracion");
        var actividad = intent?.extras?.getString("actividad")
        var nombre = intent?.extras?.getString("nombre");





        txtDestino.text = "Destino 🏙️: " + destino;
        txtFecha.text = "Fecha 📅: " + fecha;
        txtDuracion.text = "Duración 🧳: " + duracion.toString() + " días."
        txtActividad.text = "Actividad 🛍️: " + actividad;
        txtAgradecimientoUsuario.text = "¡Muchas gracias ${nombre} por usar esta aplicación y " +
                "que tengas un buen viaje o que te la hayas pasado excelente en un viaje antiguo!"

        btnRegresar.setOnClickListener{
            finish();
        }

    }
}