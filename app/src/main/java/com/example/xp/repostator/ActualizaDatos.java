package com.example.xp.repostator;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

public class ActualizaDatos extends AppCompatActivity {

    int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_repostaje);
    }

    private void leeDatos(int indice){
        EditText editPrecio = (EditText)findViewById(R.id.precio);
        EditText editKilometos = (EditText) findViewById(R.id.kilometros);
        EditText editLitros = (EditText) findViewById(R.id.litros);
        DatePicker editFecha = (DatePicker) findViewById(R.id.fecha);

        SharedPreferences sp = getSharedPreferences("datos", Context.MODE_PRIVATE);
        String precio = sp.getString("precio_" + indice, null);
        String kilometros = sp.getString("kilometros_" + indice, null);
        String litros = sp.getString("litros_" + indice, null);
        int dia = sp.getInt("dia_" + indice, 0) ;
        int mes = sp.getInt("mes_" + indice, 0) ;
        int anyo = sp.getInt("anyo_" + indice, 0);

        editPrecio.setText(precio);
        editKilometos.setText(kilometros);
        editLitros.setText(litros);
        editFecha.updateDate(anyo, mes,dia);
    }

    private void escribeDatos(int indice){
        EditText editPrecio = (EditText) findViewById(R.id.precio);
        EditText editKilometos = (EditText) findViewById(R.id.kilometros);
        EditText editLitros = (EditText) findViewById(R.id.litros);
        DatePicker editFecha = (DatePicker) findViewById(R.id.fecha);

        SharedPreferences sp = getSharedPreferences("datos", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        String precio = editPrecio.getText().toString();
        String kilometros = editKilometos.getText().toString();
        String litros = editLitros.getText().toString();

        if (precio.equals("")){precio = "0";}
        if (kilometros.equals("")){kilometros = "0";}
        if (litros.equals("")){litros = "0";}

        editor.putString("precio_" + indice, precio);
        editor.putString("kilometros_" + indice, kilometros);
        editor.putString("litros_" + indice, litros);

        editor.putInt("dia_" + indice, editFecha.getDayOfMonth());
        editor.putInt("mes_" + indice, editFecha.getMonth());
        editor.putInt("anyo_" + indice, editFecha.getYear());

        editor.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Bundle parametros = getIntent().getExtras();
        if(parametros != null)
        {
            position = parametros.getInt("posicion");
            position++;
            leeDatos(position);
        }
        final EditText editPrecio = (EditText) findViewById(R.id.precio);
        editPrecio.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE)
                {
                    escribeDatos(position);
                    finish();
                }
                return false;
            }
        });

        Button botonGuardar = (Button) findViewById(R.id.botonGuardar);
        botonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                escribeDatos(position);
                finish();
            }
        });
    }
}
