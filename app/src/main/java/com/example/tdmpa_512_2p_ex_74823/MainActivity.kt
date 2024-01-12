package com.example.tdmpa_512_2p_ex_74823

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import java.text.SimpleDateFormat
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var txtNombre = findViewById<EditText>(R.id.txtNombreUsuario)
        var txtDestino = findViewById<EditText>(R.id.txtDestino)
        var txtFecha = findViewById<EditText>(R.id.txtFecha)
        var txtDuracion = findViewById<EditText>(R.id.txtDuracion)
        var chpGrupoViajes = findViewById<ChipGroup>(R.id.chpGrupoViajes)
        var chpPlaya = findViewById<Chip>(R.id.chpPlaya)
        var chpCompras = findViewById<Chip>(R.id.chpCompras)
        var chpVisita = findViewById<Chip>(R.id.chpVisita)
        var filtrarViajes = findViewById<Spinner>(R.id.spnFiltrarViajes)
        var btnAgrgarViaje = findViewById<Button>(R.id.btnAgregarViaje)
        var txtIndicarFiltroViajes = findViewById<TextView>(R.id.txtIndicarFiltroViajes);

        var nuevoViaje : Viaje;
        var listaViajes : MutableList<Viaje> = mutableListOf();

        var listaViajesChips : MutableList<Chip> = mutableListOf();
        var listaViajesChipsPasados : MutableList<Chip> = mutableListOf();
        var listaViajesChipsProximos : MutableList<Chip> = mutableListOf();


        var filtroV = "";



        btnAgrgarViaje.setOnClickListener{
            if(txtNombre.text.isNotBlank() && txtDestino.text.isNotBlank() && txtFecha.text.isNotBlank() && txtDuracion.text.isNotBlank()){
                txtIndicarFiltroViajes.visibility = View.VISIBLE;
                filtrarViajes.visibility = View.VISIBLE;
                chpGrupoViajes.visibility = View.VISIBLE;

                if(chpPlaya.isChecked == true){
                    nuevoViaje = Viaje(txtNombre.text.toString(), txtDestino.text.toString(), txtFecha.text.toString(), txtDuracion.text.toString().toInt(), chpPlaya.text.toString());
                    listaViajes.add(nuevoViaje);
                    agregarViajesPorFecha(nuevoViaje.fecha,listaViajes, nuevoViaje.destino, nuevoViaje, listaViajesChipsPasados, listaViajesChipsProximos, listaViajesChips);
                }
                else{
                    if(chpCompras.isChecked == true){
                        nuevoViaje = Viaje(txtNombre.text.toString(), txtDestino.text.toString(), txtFecha.text.toString(), txtDuracion.text.toString().toInt(), chpCompras.text.toString());
                        listaViajes.add(nuevoViaje);
                        agregarViajesPorFecha(nuevoViaje.fecha,listaViajes, nuevoViaje.destino, nuevoViaje, listaViajesChipsPasados, listaViajesChipsProximos, listaViajesChips);

                    }
                    else{
                        if(chpVisita.isChecked == true){
                            nuevoViaje = Viaje(txtNombre.text.toString(), txtDestino.text.toString(), txtFecha.text.toString(), txtDuracion.text.toString().toInt(), chpVisita.text.toString());
                            listaViajes.add(nuevoViaje);
                            agregarViajesPorFecha(nuevoViaje.fecha,listaViajes, nuevoViaje.destino, nuevoViaje, listaViajesChipsPasados, listaViajesChipsProximos, listaViajesChips);

                        }
                        else{
                            Toast.makeText(this@MainActivity, "No se ha seleccionado ninguna actividad", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
            else{
                Toast.makeText(this@MainActivity, "Necesita llenar los campos para continuar", Toast.LENGTH_LONG).show()
            }

            txtDestino.setText("");
            txtFecha.setText("yyyy-mm-dd");
            txtDuracion.setText("");


        }

        val viajesMostrados = resources.getStringArray(R.array.viajes);
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, viajesMostrados);
        filtrarViajes.adapter = adapter;

        filtrarViajes.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                filtroV = viajesMostrados[position]
                filtrarViajes(filtroV, listaViajesChipsPasados, listaViajesChipsProximos, listaViajesChips);
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(this@MainActivity, "No se seleccionó nada", Toast.LENGTH_SHORT).show()
            }
        }




        }

        fun agregarChip(destino: String, nuevoV : Viaje, listaV : MutableList<Viaje>,  lista1 : MutableList<Chip>, lista2 : MutableList<Chip>, lista3: MutableList<Chip>): Chip{
        val chip = Chip(this@MainActivity)
        chip.text = destino;
        chip.isClickable = true;
        chip.isCheckable = false;
        val chpViajes =  findViewById<ChipGroup>(R.id.chpGrupoViajes)
        chpViajes.addView(chip as View)

        chip.isCloseIconVisible = true;

        chip.setOnCloseIconClickListener {
            chpViajes.removeView(chip);
            listaV.remove(nuevoV);
            lista1.remove(chip);
            lista2.remove(chip)
            lista3.remove(chip)

        }

        chip.setOnClickListener{
            val intent = Intent(this@MainActivity, TripDetailActivity::class.java);
            intent.putExtra("nombre", nuevoV.nombre);
            intent.putExtra("destino", nuevoV.destino);
            intent.putExtra("fecha", nuevoV.fecha);
            intent.putExtra("duracion", nuevoV.duracion);
            intent.putExtra("actividad", nuevoV.actividad);
            startActivity(intent);
        }

            return chip
    }


    fun agregarViajesPorFecha(fecha: String, listaV : MutableList<Viaje>, destino : String, v:Viaje, lista1 : MutableList<Chip>, lista2 : MutableList<Chip>, lista3: MutableList<Chip>) {
        val fechaHoy = Calendar.getInstance()
        val fechaViaje = SimpleDateFormat("yyyy-MM-dd").parse(fecha)
        val chip = agregarChip("Leon a " + destino, v, listaV, lista1, lista2, lista3)

        if (fechaViaje != null && fechaViaje.before(fechaHoy.time)) {
            lista1.add(chip);
        }
        else{
            if (fechaViaje != null && fechaViaje.after(fechaHoy.time)) {
                lista2.add(chip);
            }
        }

        lista3.add(chip);
    }


    fun filtrarViajes(filtro: String, lista1: MutableList<Chip>, lista2: MutableList<Chip>, lista3: MutableList<Chip>) {
        val chpViajes = findViewById<ChipGroup>(R.id.chpGrupoViajes)
        chpViajes.removeAllViews();

        when (filtro) {
            "Todos" -> {
                for (chip in lista3) {
                    chpViajes.addView(chip as View)
                }
            }
            "Pasados" -> {
                for (chip in lista1) {
                    chpViajes.addView(chip as View)
                }
            }
            "Próximos" -> {
                for (chip in lista2) {
                    chpViajes.addView(chip as View)
                }
            }
        }
    }




}



