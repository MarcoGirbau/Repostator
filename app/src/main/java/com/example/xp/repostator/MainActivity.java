package com.example.xp.repostator;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Intent ventanaCaptura = new Intent(this,Entrada_Datos.class);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(ventanaCaptura);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        ArrayList<String> listadoRepostajes = new ArrayList<>();
        ListView listaVista;
        SharedPreferences sp = getSharedPreferences("datos", Context.MODE_PRIVATE);
        int size = sp.getInt("Listado_size", 0);
        String precio, kilometros, litros, dia, mes, anyo;
        double costeTotal;

        for(int i = 1; i <= size; i++)
        {
            precio = sp.getString("precio_" + i, "0");
            kilometros = sp.getString("kilometros_" + i, "0");
            litros = sp.getString("litros_" + i, "0");
            costeTotal = Double.valueOf(precio) * Double.valueOf(litros);

            dia = String.valueOf(sp.getInt("dia_" + i, 0));
            mes = String.valueOf(sp.getInt("mes_" + i, 0));
            anyo = String.valueOf(sp.getInt("anyo_" + i, 0));
            listadoRepostajes.add
                    (kilometros + " km " + litros + " L " + precio + " €/L , coste: "
                            + String.format("%.2f", costeTotal)
                            + "fecha: " + dia + "-" + mes + "-" + anyo);
        }
        listaVista = (ListView) findViewById(R.id.marcoLista);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, listadoRepostajes);
        listaVista.setAdapter(arrayAdapter);

        listaVista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            final Intent ventana = new Intent(MainActivity.this, ActualizaDatos.class);
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long num) {
                ventana.putExtra("posicion", position);
                startActivity(ventana);
            }
        });
    }
}
