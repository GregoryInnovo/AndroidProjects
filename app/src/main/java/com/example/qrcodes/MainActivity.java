package com.example.qrcodes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button scanBtn, guardar, eliminar, ver;
    private EditText nombreTxt, telefonoTxt, emailTxt, codigoTxt;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Se Instancia el botón de Scan
        scanBtn = (Button)findViewById(R.id.scan_button);
        //Se Instancia el Campo de Texto para el nombre del formato de código de barra
        nombreTxt = (EditText)findViewById(R.id.scan_nombre);
        //Se Instancia el Campo de Texto para el contenido  del código de barra
        telefonoTxt = (EditText)findViewById(R.id.scan_telefono);

        emailTxt = (EditText)findViewById(R.id.scan_email);
        //Se agrega la clase MainActivity.java como Listener del evento click del botón de Scan
        codigoTxt = (EditText)findViewById(R.id.scan_codigo);
        scanBtn.setOnClickListener(this);

        guardar=(Button)findViewById(R.id.guardar);
        guardar.setOnClickListener(this);

        ver=(Button)findViewById(R.id.ver);
        ver.setOnClickListener(this);

        eliminar=(Button)findViewById(R.id.eliminar);
        eliminar.setOnClickListener(this);

    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        //Se obtiene el resultado del proceso de scaneo y se parsea
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
            //Quiere decir que se obtuvo resultado pro lo tanto:
            //Desplegamos en pantalla el contenido del código de barra scaneado
            String scanContent = scanningResult.getContents();

            StringTokenizer t= new StringTokenizer(scanContent,"*");
            final String nombre=t.nextToken();
            final String telefono=t.nextToken();
            final String email=t.nextToken();
            nombreTxt.setText(""+nombre);
            //Desplegamos en pantalla el nombre del formato del código de barra scaneado

            telefonoTxt.setText("" + telefono);

            //String scanFormat = scanningResult.getFormatName();
            emailTxt.setText("" + email);










        }else{
            //Quiere decir que NO se obtuvo resultado
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No se ha recibido datos del scaneo!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    public void onClick(View v) {
        //Se responde al evento click
        if(v.getId()==R.id.scan_button){
            //Se instancia un objeto de la clase IntentIntegrator
            IntentIntegrator scanIntegrator = new IntentIntegrator(this);
            //Se procede con el proceso de scaneo
            scanIntegrator.initiateScan();
        }

        if(v.getId()==R.id.guardar) {


            AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(MainActivity.this,"museo2",null,1);
            SQLiteDatabase bd=admin.getWritableDatabase();

            ContentValues registro= new ContentValues();

            registro.put("nombre",nombreTxt.getText().toString());
            registro.put("telefono",telefonoTxt.getText().toString());
            registro.put("email",emailTxt.getText().toString());
            bd.insert("obras",null,registro);
            bd.close();


            Toast.makeText(MainActivity.this,"Se guardaron los datos de la obra satisfatoriamente", Toast.LENGTH_SHORT).show();

        }

        if(v.getId()==R.id.ver) {

            AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(MainActivity.this,"museo2",null,1);
            SQLiteDatabase bd=admin.getWritableDatabase();
            String cdg = codigoTxt.getText().toString();

            Cursor registro = bd.rawQuery("select nombre,telefono,email from obras where codigo="+cdg,null);

            if (registro.moveToFirst()){

                nombreTxt.setText(registro.getString(0));
                telefonoTxt.setText(registro.getString(1));
                emailTxt.setText(registro.getString(2));

            }else
                Toast.makeText(MainActivity.this,"No existe una obra con ese código", Toast.LENGTH_SHORT).show();

            bd.close();


        }

        if(v.getId()==R.id.eliminar) {

            AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(MainActivity.this,"museo2",null,1);
            SQLiteDatabase bd=admin.getWritableDatabase();
            String cdg = codigoTxt.getText().toString();

            int cant = bd.delete("obras","codigo="+cdg,null);
            bd.close();
            codigoTxt.setText("");
            nombreTxt.setText("");
            telefonoTxt.setText("");
            emailTxt.setText("");
            if(cant==1)
                Toast.makeText(MainActivity.this,"Los datos se borraron satisfatoriamente", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(MainActivity.this,"No existe una obra con ese código", Toast.LENGTH_SHORT).show();

        }
    }
}