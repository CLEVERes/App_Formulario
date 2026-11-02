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

import android.view.View;
import android.widget.AdapterView;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.os.LocaleListCompat;

public class MainActivity extends AppCompatActivity {

    EditText nombre, apellido, edad;
    TextView mensaje;
    RadioGroup genero;
    Spinner estadoCivil, idioma;
    Switch hijos;
    Button datos, reiniciar;

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

        //creacion del spinner
        estadoCivil = findViewById(R.id.spnEstadoCivil);
        ArrayAdapter<CharSequence> adpEstadoCivil = ArrayAdapter.createFromResource(
                this,
                R.array.spnEstadoCivil,
                android.R.layout.simple_spinner_item
        );
        adpEstadoCivil.setDropDownViewResource(android.R.layout.simple_spinner_item);
        estadoCivil.setAdapter(adpEstadoCivil);

        idioma = findViewById(R.id.spnIdioma);
        ArrayAdapter<CharSequence> adpIdioma = ArrayAdapter.createFromResource(
                this,
                R.array.spnIdioma,
                android.R.layout.simple_spinner_item
        );
        adpIdioma.setDropDownViewResource(android.R.layout.simple_spinner_item);
        idioma.setAdapter(adpIdioma);

        //seleccion del lenguaje
        String idiomaActual = AppCompatDelegate.getApplicationLocales().toLanguageTags();
        if (idiomaActual.startsWith("es")) {
            idioma.setSelection(1);
        } else if (idiomaActual.startsWith("en")) {
            idioma.setSelection(2);
        } else if (idiomaActual.startsWith("fr")) {
            idioma.setSelection(3);
        }

        idioma.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String idiomaSeleccionado;

                switch (position) {
                    case 1:
                        idiomaSeleccionado = "es";
                        break;
                    case 2:
                        idiomaSeleccionado = "en";
                        break;
                    case 3:
                        idiomaSeleccionado = "fr";
                        break;
                    default:
                        return; // No cambiar el idioma al elegir el texto inicial
                }

                AppCompatDelegate.setApplicationLocales(
                        LocaleListCompat.forLanguageTags(idiomaSeleccionado)
                );
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
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
                    String camposFaltantes = getString(R.string.errorFaltantes);

                    if(nombre.getText().toString().isBlank())
                    {
                        camposFaltantes += " \"" + getString(R.string.errorNombre) + "\"";
                        comprobaciones = false;
                    }

                    if(apellido.getText().toString().isBlank())
                    {
                        camposFaltantes += " \"" + getString(R.string.errorApellido) + "\" ";
                        comprobaciones = false;
                    }

                    if(edad.getText().toString().isBlank())
                    {
                        camposFaltantes += " \"" + getString(R.string.errorEdad) + "\" ";
                        comprobaciones = false;
                    }

                    if(generoID <= 0)
                    {
                        camposFaltantes += " \"" + getString(R.string.errorGenero) + "\" ";
                        comprobaciones = false;
                    }

                    if(estadoCivil.getSelectedItemPosition() <= 0)
                    {
                        camposFaltantes += " \"" + getString(R.string.errorEstadoCivil) + "\" ";
                        comprobaciones = false;
                    }


                    if(comprobaciones)
                    {

                        String mayoriaEdad;
                        String paternidad;

                        if(Integer.parseInt(edad.getText().toString())>=18)
                        {
                            mayoriaEdad = getString(R.string.mensajeMayorEdad);
                        }
                        else
                        {
                            mayoriaEdad = getString(R.string.mensajeMenorEdad);
                        }

                        if (hijos.isChecked())
                        {
                            paternidad = getString(R.string.mensajeTieneHijos);
                        }
                        else
                        {
                            paternidad = getString(R.string.mensajeSinHijos);
                        }

                        mensaje.setText("");
                        String mensajeMostrar = getString(R.string.mensajeDatos) + " " + nombre.getText().toString() + " " + apellido.getText().toString() + ", " + mayoriaEdad + ", " + generoStr + ", " + estadoCivil.getSelectedItem().toString() + ", " + paternidad;
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
                    mensaje.setText("");
                }
        );

    }
}