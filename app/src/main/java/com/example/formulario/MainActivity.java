package com.example.formulario;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.os.LocaleListCompat;

public class MainActivity extends AppCompatActivity {

    EditText nombre;
    EditText apellido;
    EditText edad;

    TextView mensaje;
    RadioGroup genero;
    Spinner estadoCivil;
    Spinner idioma;
    Switch hijos;

    Button datos;

    Button reiniciar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        nombre = findViewById(R.id.etextNombre);

        apellido = findViewById(R.id.etextApellido);

        edad = findViewById(R.id.etextEdad);

        mensaje = findViewById(R.id.txtvMensaje);

        genero = findViewById(R.id.rgroupGenero);

        estadoCivil = findViewById(R.id.spnEstadoCivil);
        ArrayAdapter<CharSequence> adpEstadiCivil = ArrayAdapter.createFromResource(
                this,
                R.array.spinnerEstadoCivil,
                android.R.layout.simple_spinner_item
        );
        adpEstadiCivil.setDropDownViewResource(android.R.layout.simple_spinner_item);
        estadoCivil.setAdapter(adpEstadiCivil);

        idioma = findViewById(R.id.spnIdioma);
        ArrayAdapter<CharSequence> adpIdioma = ArrayAdapter.createFromResource(
                this,
                R.array.spinnerIdioma,
                android.R.layout.simple_spinner_item
        );
        adpIdioma.setDropDownViewResource(android.R.layout.simple_spinner_item);
        idioma.setAdapter(adpIdioma);

        String[] codigosIdiomas = {"", "es", "en", "fr"};
        idioma.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, android.view.View view, int position, long id) {
                if (position > 0) {
                    AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(codigosIdiomas[position]));
                }
            }
            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {}
        });

        hijos = findViewById(R.id.swtHijos);

        datos = findViewById(R.id.btnDatos);
        datos.setOnClickListener(v ->
                {
                    String generoStr = "";
                    int generoID = genero.getCheckedRadioButtonId();
                    if (generoID != -1) {
                        RadioButton radioButton = findViewById(generoID);
                        generoStr = radioButton.getText().toString();
                    }

                    boolean comprobaciones = true;
                    String camposFaltantes = getString(R.string.camposFaltantes);

                    if(nombre.getText().toString().isBlank())
                    {
                        camposFaltantes += " \"" + getString(R.string.mensajeNombre) + "\"";
                        comprobaciones = false;
                    }

                    if(apellido.getText().toString().isBlank())
                    {
                        camposFaltantes += " \"" + getString(R.string.mensajeApellido) + "\" ";
                        comprobaciones = false;
                    }

                    if(edad.getText().toString().isBlank())
                    {
                        camposFaltantes += " \"" + getString(R.string.mensajeEdad) + "\" ";
                        comprobaciones = false;
                    }

                    if(generoID <= 0)
                    {
                        camposFaltantes += " \"" + getString(R.string.mensajeGenero) + "\" ";
                        comprobaciones = false;
                    }

                    if(estadoCivil.getSelectedItemPosition() <= 0)
                    {
                        camposFaltantes += " \"" + getString(R.string.mensajeEstadoCivil) + "\" ";
                        comprobaciones = false;
                    }


                    if(comprobaciones == true)
                    {

                        String mayoriaEdad = "";
                        String paternidad = "";

                        if(Integer.parseInt(edad.getText().toString())>=18)
                        {
                            mayoriaEdad = getString(R.string.mayorEdad);
                        }
                        else
                        {
                            mayoriaEdad = getString(R.string.menorEdad);
                        }

                        if (hijos.isChecked())
                        {
                            paternidad = getString(R.string.tieneHijos);
                        }
                        else
                        {
                            paternidad = getString(R.string.sinHijos);
                        }

                        mensaje.setText("");
                        String mensajeMostrar = getString(R.string.tusDatos) + " " + nombre.getText().toString() + " " + apellido.getText().toString() + ", " + mayoriaEdad + ", " + generoStr + ", " + estadoCivil.getSelectedItem().toString() + ", " + paternidad;
                        Toast.makeText(this, mensajeMostrar, Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        mensaje.setTextColor(getResources().getColor(R.color.rojo_error));
                        mensaje.setText(camposFaltantes);
                    }
                }
        );

        reiniciar = findViewById(R.id.btnReiniciar);
        reiniciar.setOnClickListener(v -> {
                    nombre.setText("");
                    apellido.setText("");
                    edad.setText("");
                    genero.clearCheck();
                    estadoCivil.setSelection(0);
                    hijos.setChecked(false);
                }
        );

    }
}