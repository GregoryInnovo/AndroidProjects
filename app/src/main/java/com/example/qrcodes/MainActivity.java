package com.example.qrcodes;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    EditText txtTema;
    Spinner spinAreas, SpinSecciones;
    Button btnRegistrar;
    private DatabaseReference Clases;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Clases= FirebaseDatabase.getInstance().getReference("Clases");
        txtTema=(EditText) findViewById(R.id.editText);
        spinAreas=(Spinner) findViewById(R.id.spinner);
        String [] areas={"matematicas","quimica"};
        ArrayAdapter <String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,areas);
        spinAreas.setAdapter(adapter);
        SpinSecciones=(Spinner) findViewById(R.id.spinner2);
        String [] secciones ={"11-1","11-2"};
        ArrayAdapter <String> adapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,secciones);
        SpinSecciones.setAdapter(adapter1);
        btnRegistrar=(Button) findViewById(R.id.button);
    }
    public void registrarClase(View view){
        String seccion=SpinSecciones.getSelectedItem().toString();
        String area=spinAreas.getSelectedItem().toString();
        String tema=txtTema.getText().toString();

        if(!TextUtils.isEmpty(tema)){
            String id=Clases.push().getKey();
            Clases leccion =new Clases (id,seccion,area,tema);
            Clases.child("Lecciones").child(id).setValue(leccion);
            Toast.makeText(this,"Clase registrada",Toast.LENGTH_LONG).show();
        } else{
            Toast.makeText(this,"Debe introducir un tema",Toast.LENGTH_LONG).show();
        }

    }
}