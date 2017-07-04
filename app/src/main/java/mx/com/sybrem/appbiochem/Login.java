package mx.com.sybrem.appbiochem;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class Login extends AppCompatActivity {

    // Se declara por separado la variable de array de la asignaciÑn del contenido de la tabla.
    private String[] clientes;
    Button cmSyncAccesos; // Con este botÑn descarga solo la tabla de accesos para poder firmarse en el sistema.
    ProgressDialog pd; // Mensaje de progreso en sincronizacion.

    private Context context;
    private Autoupdater updater; //Uso de la clase que usa la clase de la autoActualización

    private  static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "bladeTablet.db";

    String currentVersionName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Definición de la base de datos

        final MyDBHandler dbHandler = new MyDBHandler(this, null, null, DATABASE_VERSION);
        dbHandler.checkDBStatus(); // Tiene como fin forzar la creacion de la base de datos.

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Resources res = getResources();

        currentVersionName=BuildConfig.VERSION_NAME;

        TextView tviu=(TextView)findViewById(R.id.versionApp);
        tviu.setText("V"+currentVersionName);

        Button btn = (Button) findViewById(R.id.btnLogin);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //EditText usuario = (EditText)findViewById(R.id.txtUsuario);
                //EditText password = (EditText)findViewById(R.id.txtPass);

                String usuario = ((EditText)findViewById(R.id.txtUsuario)).getText().toString();
                String password = ((EditText)findViewById(R.id.txtPass)).getText().toString();
                Boolean valida = false;

                if(usuario.toString().length() <= 0 || password.toString().length() <= 0){
                    Toast toast = Toast.makeText(getApplicationContext(), "Proporcione su usuario y password", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                Boolean info = dbHandler.checkAccesos();
                if(info == false){
                    if(CheckNetwork.isInternetAvailable(Login.this)) //returns true if internet available
                    {
                        dbHandler.resetGlAccesos();
                        new JsonTaskAccess(dbHandler).execute();
                        valida = dbHandler.validacion(usuario, password);
                    }
                    else{
                        Toast toast = Toast.makeText(getApplicationContext(), "Sin conexion a Internet", Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }
                }

                else {
                    valida = dbHandler.validacion(usuario, password);
                }

                if(valida == true) {

                    String tipo_usuario = dbHandler.tipoUsuario(usuario);

                        if (tipo_usuario.toString().equals("A")) {

                            Intent explicit_intent;
                            explicit_intent = new Intent(Login.this, NavDrawerActivity.class);
                            explicit_intent.putExtra("usuario", usuario);
                            startActivity(explicit_intent);
                            return;
                        } else {

                            String ruta = dbHandler.rutaSeleccionada();

                            if(ruta.length() > 0) {
                                Intent explicit_intent;
                                explicit_intent = new Intent(Login.this, NavDrawerActivity.class);
                                explicit_intent.putExtra("usuario", usuario);
                                startActivity(explicit_intent);
                                return;
                            }
                            else{
                                Intent explicit_intent;
                                explicit_intent = new Intent(Login.this, EligeRuta.class);
                                explicit_intent.putExtra("usuario", usuario);
                                startActivity(explicit_intent);
                                return;
                            }
                        }

                }
                else{
                    Toast toast = Toast.makeText(getApplicationContext(), "El usuario no esta registrado o los datos son incorrectos", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }

            } });
        comenzarActualizar();
    }


    private class JsonTaskAccess extends AsyncTask<String, String, String>
    {
        String url5 = "http://www.sybrem.com.mx/adsnet/syncmovil/SyncMovil.php?ruta=0&tabla=axesos";
        String url4 = "http://www.sybrem.com.mx/adsnet/syncmovil/SyncMovil.php?ruta=0&tabla=rutas";
        String urlVnCatAgentes = "http://www.sybrem.com.mx/adsnet/syncmovil/SyncMovil.php?ruta=0&tabla=agentes";

        // Bloque para poder usar los metodos de la clase MyDBHandler en la clase JsonTask
        private MyDBHandler dbHandler;
        public JsonTaskAccess(MyDBHandler dbHandler)
        {
            this.dbHandler = dbHandler;
        }
        // Termina bloque de inclusion de los metodos de MyDBHandler (Se uso esta practica debido a que ya esta usado extends en la clase

        protected void onPreExecute()
        {
            super.onPreExecute();

            pd = new ProgressDialog(Login.this);
            pd.setTitle("Conectando a servidor Biochem...");
            pd.setMessage("Descargando datos de usuario...");
            pd.setCancelable(false);
            pd.show();
        }

        protected String doInBackground(String... params)
        {

            /*************************************************************
             * Bloque para el catalogo de Accesos:                       *
             * ***********************************************************/
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            String JsonUrl5 = "";

            // Log.d("En proceso =====> ", url5);

            try {
                URL url = new URL(url5);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null)
                {
                    buffer.append(line+"\n");
                    // Log.d("Salida: ", "> " + line);   //Se hace una salida por monitoreo en la consola. ELIMINAR / COMENTAR
                }
                JsonUrl5 = buffer.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            // Una vez obtenida la cadena JSon del segundo catalogo se lee linea por lina para insertar en la tabla
            try
            {
                // Convierte el string con la informaciÑn de los clientes en un array JSON
                JSONArray jsonArr_accesos = new JSONArray(JsonUrl5);

                for (int i = 0; i < jsonArr_accesos.length(); i++)
                {
                    JSONObject jsonObj5 = jsonArr_accesos.getJSONObject(i);
                    dbHandler.insertaGlAccesos(jsonObj5);
                }
            } // Fin del Try
            catch (JSONException e)
            {
                e.printStackTrace();
            } // fin del catch

            /*************************************************************
             * Bloque para el catalogo de Rutas:                         *
             * ***********************************************************/
            connection = null;
            reader = null;
            String JsonUrl4 = "";

            // Log.d("En proceso =====> ", url4);
            //pd.setMessage("Descargando el catalogo de rutas...");
            try {
                URL url = new URL(url4);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null)
                {
                    buffer.append(line+"\n");
                    // Log.d("Salida: ", "> " + line);   //Se hace una salida por monitoreo en la consola. ELIMINAR / COMENTAR
                }
                JsonUrl4 = buffer.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            // Una vez obtenida la cadena JSon del segundo catalogo se lee linea por lina para insertar en la tabla
            try
            {
                // Convierte el string con la informaciÑn de los clientes en un array JSON
                JSONArray jsonArr_rutas = new JSONArray(JsonUrl4);

                for (int i = 0; i < jsonArr_rutas.length(); i++)
                {
                    JSONObject jsonObj4 = jsonArr_rutas.getJSONObject(i);
                    dbHandler.insertaVnCatRutas(jsonObj4);
                }
            } // Fin del Try
            catch (JSONException e)
            {
                e.printStackTrace();
            } // fin del catch

            /*****************************************************************************
             * Bloque para vn_cat_agentes *
             * **************************************************************************/
            connection = null;
            reader = null;
            String JsonUrlVnCatAgentes = "";

            try {
                URL url = new URL(urlVnCatAgentes);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null)
                {
                    buffer.append(line+"\n");
                }
                JsonUrlVnCatAgentes = buffer.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            // Una vez obtenida la cadena JSon del segundo catalogo se lee linea por lina para insertar en la tabla
            try
            {
                // Convierte el string con la información de los clientes en un array JSON
                JSONArray jsonArr_vnCatAgentes = new JSONArray(JsonUrlVnCatAgentes);

                for (int i = 0; i < jsonArr_vnCatAgentes.length(); i++)
                {
                    JSONObject jsonObjVnCatAgentes = jsonArr_vnCatAgentes.getJSONObject(i);
                    dbHandler.insertaVnCatAgentes(jsonObjVnCatAgentes);
                }
            } // Fin del Try
            catch (JSONException e)
            {
                e.printStackTrace();
            } // fin del catch

            return null;

        }

        @Override
        protected void onPostExecute(String result)
        {
            super.onPostExecute(result);

            if (pd.isShowing())
            {
                pd.dismiss();
            }

        }
    } // Fin de la clase JsonTaskAccess

    private void comenzarActualizar(){
        //Para tener el contexto mas a mano.
        context = this;
        //Creamos el Autoupdater.
        updater = new Autoupdater(this);
        //Ponemos a correr el ProgressBar.
        //loadingPanel.setVisibility(View.VISIBLE);
        //pdUpdate.show();
        //Ejecutamos el primer metodo del Autoupdater.
        updater.DownloadData(finishBackgroundDownload);
    }

    private Runnable finishBackgroundDownload = new Runnable() {
        @Override
        public void run() {
            //Volvemos el ProgressBar a invisible.
            //loadingPanel.setVisibility(View.GONE);
            //Comprueba que halla nueva versión.
            if(updater.isNewVersionAvailable()){

                //Crea mensaje con datos de versión.
                String msj = "¡Nueva versión disponible!";
                msj += "\n"+updater.getMejoras();
                msj += "\nVersión actual: " + updater.getCurrentVersionName() + "(" + updater.getCurrentVersionCode() + ")";
                msj += "\n\nÚltima versión: " + updater.getLatestVersionName() + "(" + updater.getLatestVersionCode() +")";
                msj += "\n\nSe actualizará a la nueva versión";
                //Crea ventana de alerta.
                AlertDialog.Builder dialog1 = new AlertDialog.Builder(context);
                dialog1.setMessage(msj);
                //dialog1.setNegativeButton("",null);
                //Establece el boton de Aceptar y que hacer si se selecciona.
                dialog1.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Vuelve a poner el ProgressBar mientras se baja e instala.
                        //loadingPanel.setVisibility(View.VISIBLE);

                        //pdUpdate.show();
                        //Se ejecuta el Autoupdater con la orden de instalar. Se puede poner un listener o no
                        updater.InstallNewVersion(null);

                    }
                });
                //Muestra la ventana esperando respuesta.
                dialog1.show();
            }else{
                //No existen Actualizaciones.
                Log.d("","No Hay actualizaciones");
            }
        }
    };


}