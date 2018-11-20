package com.example.christian.app_aurora;
import java.util.Random;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.os.Build;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import java.text.Normalizer;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, TextToSpeech.OnInitListener {

    private static final int RECONOCEDOR_VOZ = 30;
    private TextView escuchando;
    private TextView respuesta;
    private ArrayList<Respuestas> respuest;
    private TextToSpeech leer;
    private TextView nombre;
    private Switch swEstado;
    public boolean baderaAlarma=false;
    Switch nav_lucesCochera1,nav_lucesCocina1;
    Random random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
             //   Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
               //         .setAction("Action", null).show();
                hablar(view);

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        escuchando = (TextView)findViewById(R.id.tvEscuchado);
        respuesta = (TextView)findViewById(R.id.tvRespuesta);
        respuest = proveerDatos();
        leer = new TextToSpeech(this,this);




    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }




    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_lucesBaño){


            Switch simpleSwitch = (Switch) findViewById (R.id.nav_lucesBaño);

            // verificar el estado actual de un Switch (verdadero o falso).
            Boolean switchState = simpleSwitch.isChecked ();
            if(switchState==true)
            {
                simpleSwitch.setChecked (false);
                simpleSwitch.setEnabled(false);



            }else
            {
                simpleSwitch.setChecked (true);
                operacion("baño");
                simpleSwitch.setEnabled(false);
            }

        } else if (id == R.id.nav_lucesCochera) {
            operacion("cochera");
        } else if (id == R.id.nav_lucesCocina) {
            operacion("habitación");
        } else if (id == R.id.nav_lucesComedor) {
            operacion("sala");
        } else if (id == R.id.nav_lucesHabitación) {
            operacion("habitación");
        } else if (id == R.id.nav_lucesSala) {
            operacion("habitación");
        }  else if (id == R.id.nav_lucesServicio) {
            operacion("habitación");
        } else if (id == R.id.nav_alarm) {
            operacion("activar alarma");
        }
        else if (id == R.id.nav_Televisión) {
            operacion("Televisión");
        }
        else if (id == R.id.nav_Estéreo) {
            operacion("Estéreo");
        }
        else if (id == R.id.nav_Portón) {
            operacion("habitación");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public ArrayList<Respuestas> proveerDatos(){
        ArrayList<Respuestas> respuestas = new ArrayList<>();
        // Mensajes de la casa domotica

        respuestas.add(new Respuestas("defecto", "defecto"));

        respuestas.add(new Respuestas("baño", "Encendiendo luces del baño"));
        respuestas.add(new Respuestas("habitación", "Encendiendo luces de la habitacion"));
        respuestas.add(new Respuestas("Portón", "Abriendo portón"));
        respuestas.add(new Respuestas("estereo", "Encendiendo Televisión"));
        respuestas.add(new Respuestas("televicion", "Encendiendo Estéreo"));
        respuestas.add(new Respuestas("cochera", "Encendiendo luces de la Cochera"));
        respuestas.add(new Respuestas("sala", "Encendiendo luces de la sala"));
        respuestas.add(new Respuestas("cocina", "Encendiendo luces de la cocina"));
        respuestas.add(new Respuestas("activar alarma", "alarma activada"));
        respuestas.add(new Respuestas("desactivar alarma", "alarma desactivada"));
        respuestas.add(new Respuestas("hola", "hola que tal"));
        respuestas.add(new Respuestas("adios", "que descanses"));
        respuestas.add(new Respuestas("como estas", "esperando serte de ayuda"));
        respuestas.add(new Respuestas("nombre", "mis amigos me llaman Aurora"));
        respuestas.add(new Respuestas("creo", "Christian"));
        respuestas.add(new Respuestas("función", "hacer mas intuitiva el control de la casa domotica"));
        respuestas.add(new Respuestas("cuando te crearon", "el 14 de septiembre de 2018"));
        respuestas.add(new Respuestas("estado de la alarma", ""));
        respuestas.add(new Respuestas("cerrar", ""));


        return respuestas;

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && requestCode == RECONOCEDOR_VOZ){
            ArrayList<String> reconocido = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            String escuchado = reconocido.get(0);
            escuchando.setText(escuchado);
            prepararRespuesta(escuchado);
        }
    }

    private void prepararRespuesta(String escuchado) {
        String normalizar = Normalizer.normalize(escuchado, Normalizer.Form.NFD);
        String sintilde = normalizar.replaceAll("[^\\p{ASCII}]", "");

        int resultado;
        String respuesta = respuest.get(0).getRespuestas();

        for (int i = 0; i < respuest.size(); i++) {
            resultado = sintilde.toLowerCase().indexOf(respuest.get(i).getCuestion());
            if(resultado != -1){
                respuesta = respuest.get(i).getRespuestas();
                operacion(respuest.get(i).getCuestion());
            }
        }

      /*  if(respuesta=="defecto")
        {
            Random aleatorio = new Random(System.currentTimeMillis());
// Producir nuevo int aleatorio entre 0 y 99
            int intAletorio = aleatorio.nextInt(2);
// Más código

            switch (intAletorio) {
                case 0:
                    respuesta="no puedo responder a eso";

                case 1:
                    respuesta="lo siento, no tengo esa informaciòn";

                    break;
                case 2:
                    respuesta="lo siento, no tengo esa funcionalidad aun";

                    break;
                case 3:
                    respuesta="lo siento, no existe esa funciòn";


                    break;

            }


        }*/

        responder(respuesta);


    }

    int r=0;
    private void operacion(String cuestion) {
        switch (cuestion) {
            case "cocina":
                Toast.makeText(this, "Luces de cosina encendidas ", Toast.LENGTH_SHORT).show();
                break;
            case "baño":
                Toast.makeText(this, "Luces del baño encendidas ", Toast.LENGTH_SHORT).show();
                break;
            case "habitación":
                Toast.makeText(this, "Luces de la habitación encendidas ", Toast.LENGTH_SHORT).show();
                break;
            case "sala":


                Toast.makeText(this, "Luces de la sala encendidas ", Toast.LENGTH_SHORT).show();
                break;
            case "activar alarma":
                if(baderaAlarma==false) {
                    baderaAlarma = true;
                    Toast.makeText(this, "Alarma encendida ", Toast.LENGTH_SHORT).show();
                }else
                {
                    Toast.makeText(this, "La Alarma ya esta activada", Toast.LENGTH_SHORT).show();
                }
                break;
            case "desactivar alarma":
                if(baderaAlarma==true) {
                    baderaAlarma = false;
                    Toast.makeText(this, "Alarma desactivada ", Toast.LENGTH_SHORT).show();
                    break;
                }else
                {
                    Toast.makeText(this, "La Alarma ya esta desactivada", Toast.LENGTH_SHORT).show();
                }
            case "estado de la alarma":
                if(baderaAlarma==true)
                {


                }



                break;
            case "cerrar":
                finish();
                break;
            case "adios":
                finish();
                break;

        }

    }

    private void responder(String respuestita) {
        respuesta.setText(respuestita);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            leer.speak(respuestita, TextToSpeech.QUEUE_FLUSH, null, null);
        }else {
            leer.speak(respuestita, TextToSpeech.QUEUE_FLUSH, null);
        }
    }


    public void hablar(View view){
        Intent hablar = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        hablar.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "es-MX");
        startActivityForResult(hablar, RECONOCEDOR_VOZ);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu miMenu)
    {
        getMenuInflater().inflate(R.menu.main,miMenu);
        return true;
    }

    @Override
    public void onInit(int status) {

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
    public void onPointerCaptureChanged(boolean hasCapture) {

        //   Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
        //         .setAction("Action", null).show();

        Toast.makeText(this, "No se reconocio", Toast.LENGTH_SHORT).show();
    }
}
