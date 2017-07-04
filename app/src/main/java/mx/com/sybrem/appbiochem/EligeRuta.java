package mx.com.sybrem.appbiochem;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class EligeRuta extends AppCompatActivity {

    TextView txtUsuario;
    ListView listadoAgentes;
    private String[] agentes;

    private  static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "bladeTablet.db";

    ProgressDialog pd; // Mensaje de progreso en sincronizacion.
    ProgressDialog pdSender; // Mensaje de progreso en sincronización para JSonTaskSender.
    ProgressDialog pdSendPayment; // Mensaje de progreso en sincronizacion.
    ProgressDialog pdSendVisitas; // Mensaje de progreso en sincronizacion Visitas.
    ProgressDialog pdSendProspectos;// Mensaje de progreso en sincronizacion Prospectos.
    ProgressDialog pdSendComprobaciones; // Mensaje de progreso en sincronizacion Visitas.

    public static int MILISEGUNDOS_ESPERA = 10000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elige_ruta);

        final MyDBHandler dbHandler = new MyDBHandler(this, null, null, DATABASE_VERSION);
        dbHandler.checkDBStatus(); // Tiene como fin forzar la creacion de la base de datos.

        //Se pasan los datos del usuario que se logeo
        String usuarioLogeado = "";
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        usuarioLogeado = (String) extras.get("usuario");

        //se asigna la variable al objeto textvie
        txtUsuario = (TextView)findViewById(R.id.textViewUsuarioLog);
        txtUsuario.setText("Usuario: " + usuarioLogeado);

        //Actuaizamos en la bitacora el usuario logeado
        dbHandler.registraBitacora(usuarioLogeado);

        //Obtenemos el listado de los agentes de la base de datos
        agentes = dbHandler.getListadoAgentes();

        final ListView listadoAgentes = (ListView)findViewById(R.id.LstAgentes);
        ArrayAdapter adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1, agentes);
        listadoAgentes.setAdapter(adaptador);

        //metodo para ver que se selecciona del listado de reportes
        listadoAgentes.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> a, View v, int position, long id){
                int posicion = position;
                String itemValue = (String)listadoAgentes.getItemAtPosition(posicion);

                String[] separated = itemValue.split("-");
                String num_ruta = "";
                num_ruta = separated[0];

                dbHandler.registraRutaSeleccionada(num_ruta);
                String usuario = dbHandler.ultimoUsuarioRegistrado();

                if(CheckNetwork.isInternetAvailable(EligeRuta.this)) //returns true if internet available
                {
                    // Primero nos preocupamos por el envío de los pedidos que no se han transmitido
                    new EligeRuta.JsonTaskSender(dbHandler).execute(); // envia todos los pedidos pendientes (no los borra) cambia a impreso = 0
                    new EligeRuta.JsonTaskSendPayment(dbHandler).execute(); // Envia todos los pagos pendientes x transmitir al sevidor. No los borra local
                    new EligeRuta.JsonTaskSendVisitas(dbHandler).execute(); // Envia todas las visitas pendientes x transmitir al servidor SI LOS BORRA
                    new EligeRuta.JsonTaskSendProspectos(dbHandler).execute();// Envia todos los prospectos pendientes x transmitir al sevidor. No los borra local

                    // Se tienen que volver a descargar los catalogos del sistema.
                    dbHandler.resetCatalogs(); // Elimina la información de los catálogos existentes
                    new EligeRuta.JsonTask(dbHandler).execute(); // Descarga de la base de datos los catalogos.

                }
                else{
                    Toast toast = Toast.makeText(getApplicationContext(), "Sin conexion a Internet", Toast.LENGTH_SHORT);
                    toast.show();
                } // Fin del if que verifica la conexión a internet

                Handler handler = new Handler();

                /*handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        reload();
                    }
                }, MILISEGUNDOS_ESPERA);*/


            }
        });

    }

    //Metodo para refrescar la pantalla cuando se guarda el pago
    public void reload(){

        final MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        dbHandler.checkDBStatus(); // Tiene como fin forzar la creacion de la base de datos.

        String usuario = dbHandler.ultimoUsuarioRegistrado();

        Intent explicit_intent;
        explicit_intent = new Intent(EligeRuta.this, NavDrawerActivity.class);
        explicit_intent.putExtra("usuario", usuario);
        startActivity(explicit_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
        //return;

    }

    private class JsonTask extends AsyncTask<String, String, String>
    {
        // Bloque para poder usar los metodos de la clase MyDBHandler en la clase JsonTask
        public MyDBHandler dbHandler;

        public JsonTask(MyDBHandler dbHandler)
        {
            this.dbHandler = dbHandler;
        }
        // Termina bloque de inclusion de los metodos de MyDBHandler (Se uso esta practica debido a que ya esta usado extends en la clase

        protected void onPreExecute()
        {
            super.onPreExecute();

            pd = new ProgressDialog(EligeRuta.this);
            pd.setTitle("Conectando a servidor Biochem...");
            pd.setMessage("Sincronizando base de datos...");
            pd.setCancelable(false);
            pd.show();
        }

        protected String doInBackground(String... params)
        {

            String usuarioLog = dbHandler.ultimoUsuarioRegistrado();
            String TipoUsuario = dbHandler.tipoUsuario(usuarioLog);
            String ruta = "";


            if(TipoUsuario.toString().equals("A")) {
                ruta = dbHandler.num_ruta(usuarioLog);
            }
            else{
                ruta = dbHandler.rutaSeleccionada();
            }


            String urlCatalogoClientes = "http://www.sybrem.com.mx/adsnet/syncmovil/SyncMovil.php?ruta="+ ruta +"&tabla=clientes";
            String urlCatalogoProductos = "http://www.sybrem.com.mx/adsnet/syncmovil/SyncMovil.php?ruta=0&tabla=productos";
            String urlCatalogoConductos = "http://www.sybrem.com.mx/adsnet/syncmovil/SyncMovil.php?ruta=0&tabla=conductos";
            String urlCatalogoAccesos = "http://www.sybrem.com.mx/adsnet/syncmovil/SyncMovil.php?ruta=0&tabla=axesos";
            String urlTablaInventarios  = "http://www.sybrem.com.mx/adsnet/syncmovil/SyncMovil.php?ruta="+ ruta +"&tabla=inventarios";
            String urlAgentesParametros = "http://www.sybrem.com.mx/adsnet/syncmovil/SyncMovil.php?ruta="+ ruta +"&tabla=parametros";
            String urlRutasAsociaciones = "http://www.sybrem.com.mx/adsnet/syncmovil/SyncMovil.php?ruta="+ ruta +"&tabla=asociaciones";
            String urlPaquetesPartidas = "http://www.sybrem.com.mx/adsnet/syncmovil/SyncMovil.php?ruta=0&tabla=paquetes";

            // ++++++++++++++++++ Se agregan los siguientes URL para el nuevo bloque de pagos ++++++++++++
            String urlTiposPago = "http://www.sybrem.com.mx/adsnet/syncmovil/SyncMovil.php?ruta=0&tabla=tiposPago";
            String urlBancosClientes = "http://www.sybrem.com.mx/adsnet/syncmovil/SyncMovil.php?ruta=0&tabla=bancosClientes";
            String urlDoctosDepositos = "http://www.sybrem.com.mx/adsnet/syncmovil/SyncMovil.php?ruta=0&tabla=doctosDepositos";
            String urlDoctosRespaldos = "http://www.sybrem.com.mx/adsnet/syncmovil/SyncMovil.php?ruta=0&tabla=doctosRespaldos";
            String urlCatalogoBancos = "http://www.sybrem.com.mx/adsnet/syncmovil/SyncMovil.php?ruta=0&tabla=catBancos";

            String urlVnSeguimientoDePedidos = "http://www.sybrem.com.mx/adsnet/syncmovil/SyncMovil.php?ruta="+ ruta +"&tabla=seguimiento";
            String url4 = "http://www.sybrem.com.mx/adsnet/syncmovil/SyncMovil.php?ruta=0&tabla=rutas";
            String urlVnCatAgentes = "http://www.sybrem.com.mx/adsnet/syncmovil/SyncMovil.php?ruta=0&tabla=agentes";
            String urlVnSeguimientoDePagos = "http://www.sybrem.com.mx/adsnet/syncmovil/SyncMovil.php?ruta="+ ruta +"&tabla=seguimientoPagos";
            String urlRutaSemanal = "http://www.sybrem.com.mx/adsnet/syncmovil/SyncMovil.php?ruta="+ ruta +"&tabla=rutaSemanal2";
            String urlGlCatEntidades = "http://www.sybrem.com.mx/adsnet/syncmovil/SyncMovil.php?ruta="+ ruta +"&tabla=entidades";
            String urlGlCatLocalidades = "http://www.sybrem.com.mx/adsnet/syncmovil/SyncMovil.php?ruta="+ ruta +"&tabla=localidades";
            //String urlCtReserva = "http://www.sybrem.com.mx/adsnet/syncmovil/SyncMovil.php?ruta=0&tabla=reserva";
            //String urlCmOC = "http://www.sybrem.com.mx/adsnet/syncmovil/SyncMovil.php?ruta="+ ruta +"&tabla=oc";

            String urlVnPoliticas="http://www.sybrem.com.mx/adsnet/syncmovil/ReceptorMovilPoliticas.php";
            /*************************************************************
             * Bloque para el catalogo de clientes:                      *
             * ***********************************************************/
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            String JsonUrlCatalogoClientes = "";

            try {
                URL url = new URL(urlCatalogoClientes);
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
                JsonUrlCatalogoClientes = buffer.toString();


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

            // Una vez obtenida la cadena JSon del primer catalogo se lee linea por lina para insertar en la tabla
            try
            {
                // Convierte el string con la informaciÑn de los clientes en un array JSON
                JSONArray jsonArr_clientes = new JSONArray(JsonUrlCatalogoClientes);

                for (int i = 0; i < jsonArr_clientes.length(); i++)
                {
                    JSONObject jsonObjCatalogoClientes = jsonArr_clientes.getJSONObject(i);
                    dbHandler.insertaVnCatClientes(jsonObjCatalogoClientes);
                }
            } // Fin del Try
            catch (JSONException e)
            {
                e.printStackTrace();
            } // fin del catch

            /*************************************************************
             * Bloque para el catalogo de Accesos:                       *
             * ***********************************************************/
            connection = null;
            reader = null;
            String JsonUrl5 = "";

            // Log.d("En proceso =====> ", url5);

            try {
                URL url = new URL(urlCatalogoAccesos);
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


            /*************************************************************
             * Bloque para el catalogo de Productos:                      *
             * ***********************************************************/
            connection = null;
            reader = null;
            String JsonUrlCatalogoProductos = "";

            try {
                URL url = new URL(urlCatalogoProductos);
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
                JsonUrlCatalogoProductos = buffer.toString();


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
                JSONArray jsonArr_productos = new JSONArray(JsonUrlCatalogoProductos);

                for (int i = 0; i < jsonArr_productos.length(); i++)
                {
                    JSONObject jsonObjCatalogoProductos = jsonArr_productos.getJSONObject(i);
                    dbHandler.insertaInCatProductos(jsonObjCatalogoProductos);
                }
            } // Fin del Try
            catch (JSONException e)
            {
                e.printStackTrace();
            } // fin del catch


            /*************************************************************
             * Bloque para el catalogo de Conductos:                     *
             * ***********************************************************/
            connection = null;
            reader = null;
            String JsonUrlCatalogoConductos = "";

            try {
                URL url = new URL(urlCatalogoConductos);
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
                JsonUrlCatalogoConductos = buffer.toString();


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
                JSONArray jsonArr_conductos = new JSONArray(JsonUrlCatalogoConductos);

                for (int i = 0; i < jsonArr_conductos.length(); i++)
                {
                    JSONObject jsonObjCatalogoConductos = jsonArr_conductos.getJSONObject(i);
                    dbHandler.insertaVnCatConductos(jsonObjCatalogoConductos);
                }
            } // Fin del Try
            catch (JSONException e)
            {
                e.printStackTrace();
            } // fin del catch

            /*************************************************************
             * Bloque para la tabla de inventarios                       *
             * ***********************************************************/
            connection = null;
            reader = null;
            String JsonUrlTablaInventarios = "";

            try {
                URL url = new URL(urlTablaInventarios);
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
                JsonUrlTablaInventarios = buffer.toString();


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
                // Convierte el string con la informaciÑn en un array JSON
                JSONArray jsonArr_inventarios = new JSONArray(JsonUrlTablaInventarios);

                for (int i = 0; i < jsonArr_inventarios.length(); i++)
                {
                    JSONObject jsonObjTablaInventarios = jsonArr_inventarios.getJSONObject(i);
                    dbHandler.insertaInCentrosInventarios(jsonObjTablaInventarios);
                }
            } // Fin del Try
            catch (JSONException e)
            {
                e.printStackTrace();
            } // fin del catch


            /*************************************************************
             * Bloque para la tabla de vn_cat_agentes_parametros                       *
             * ***********************************************************/
            connection = null;
            reader = null;
            String JsonUrlAgentesParametros = "";

            try {
                URL url = new URL(urlAgentesParametros);
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
                JsonUrlAgentesParametros = buffer.toString();


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
                // Convierte el string con la informaciÑn en un array JSON
                JSONArray jsonArr_parametros = new JSONArray(JsonUrlAgentesParametros);

                for (int i = 0; i < jsonArr_parametros.length(); i++)
                {
                    JSONObject jsonObjAgentesParametros = jsonArr_parametros.getJSONObject(i);
                    dbHandler.insertaVnCatAgentesParametros(jsonObjAgentesParametros);
                }
            } // Fin del Try
            catch (JSONException e)
            {
                e.printStackTrace();
            } // fin del catch


            /*************************************************************
             * Bloque para la tabla de vn_rutas_asociaciones              *
             * ***********************************************************/
            connection = null;
            reader = null;
            String JsonUrlRutasAsociaciones = "";

            try {
                URL url = new URL(urlRutasAsociaciones);
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
                JsonUrlRutasAsociaciones = buffer.toString();


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
                // Convierte el string con la informaciÑn en un array JSON
                JSONArray jsonArr_asoicaciones = new JSONArray(JsonUrlRutasAsociaciones);

                for (int i = 0; i < jsonArr_asoicaciones.length(); i++)
                {
                    JSONObject jsonObjRutasAsociaciones = jsonArr_asoicaciones.getJSONObject(i);
                    dbHandler.insertaVnRutasAsociaciones(jsonObjRutasAsociaciones);
                }
            } // Fin del Try
            catch (JSONException e)
            {
                e.printStackTrace();
            } // fin del catch


            /*************************************************************
             * Bloque para la tabla de vn_paquetes_partidas              *
             * ***********************************************************/
            connection = null;
            reader = null;
            String JsonUrlPaquetesPartidas = "";

            try {
                URL url = new URL(urlPaquetesPartidas);
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
                JsonUrlPaquetesPartidas = buffer.toString();


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
                // Convierte el string con la informaciÑn en un array JSON
                JSONArray jsonArr_paquetes = new JSONArray(JsonUrlPaquetesPartidas);

                for (int i = 0; i < jsonArr_paquetes.length(); i++)
                {
                    JSONObject jsonObjPaquetesPartidas = jsonArr_paquetes.getJSONObject(i);
                    dbHandler.insertaVnPaquetesPartidas(jsonObjPaquetesPartidas);
                }
            } // Fin del Try
            catch (JSONException e)
            {
                e.printStackTrace();
            } // fin del catch


            // +++++++++++++++ Boque NUEVO OJO ++++++++++++++++++++++++++++++
            /*****************************************************************
             * Bloque para el catalogo de Tipos de pago (Efec/Che/Post/Trns: *
             * ***************************************************************/
            connection = null;
            reader = null;
            String JsonUrlTiposPago = "";

            try {
                URL url = new URL(urlTiposPago);
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
                JsonUrlTiposPago = buffer.toString();


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
                JSONArray jsonArr_tiposPago = new JSONArray(JsonUrlTiposPago);

                for (int i = 0; i < jsonArr_tiposPago.length(); i++)
                {
                    JSONObject jsonObjTiposPago = jsonArr_tiposPago.getJSONObject(i);
                    dbHandler.insertatiposPago(jsonObjTiposPago);
                }
            } // Fin del Try
            catch (JSONException e)
            {
                e.printStackTrace();
            } // fin del catch


            /*****************************************************************
             * Bloque para el catalogo de Bancos de Clientes (Listado)       *
             * ***************************************************************/
            connection = null;
            reader = null;
            String JsonUrlBancosClientes = "";


            try {
                URL url = new URL(urlBancosClientes);
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
                JsonUrlBancosClientes = buffer.toString();


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
                JSONArray jsonArr_bancosClientes = new JSONArray(JsonUrlBancosClientes);

                for (int i = 0; i < jsonArr_bancosClientes.length(); i++)
                {
                    JSONObject jsonObjBancosClientes = jsonArr_bancosClientes.getJSONObject(i);
                    dbHandler.insertabancosClientes(jsonObjBancosClientes);
                }
            } // Fin del Try
            catch (JSONException e)
            {
                e.printStackTrace();
            } // fin del catch


            /******************************************************************
             * Bloque para el catalogo de personas que hicieron el deposito   *
             * ***************************************************************/
            connection = null;
            reader = null;
            String JsonUrlDoctosDepositos = "";

            try {
                URL url = new URL(urlDoctosDepositos);
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
                JsonUrlDoctosDepositos = buffer.toString();


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
                JSONArray jsonArr_doctosDepositos = new JSONArray(JsonUrlDoctosDepositos);

                for (int i = 0; i < jsonArr_doctosDepositos.length(); i++)
                {
                    JSONObject jsonObjDoctosDepositos = jsonArr_doctosDepositos.getJSONObject(i);
                    dbHandler.insertadoctosDepositos(jsonObjDoctosDepositos);
                }
            } // Fin del Try
            catch (JSONException e)
            {
                e.printStackTrace();
            } // fin del catch


            /*****************************************************************************
             * Bloque para el catalogo de documentos con los que se respaldo un deposito *
             * **************************************************************************/
            connection = null;
            reader = null;
            String JsonUrlDoctosRespaldos = "";

            try {
                URL url = new URL(urlDoctosRespaldos);
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
                JsonUrlDoctosRespaldos = buffer.toString();


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
                JSONArray jsonArr_doctosRespaldos = new JSONArray(JsonUrlDoctosRespaldos);

                for (int i = 0; i < jsonArr_doctosRespaldos.length(); i++)
                {
                    JSONObject jsonObjDoctosRespaldos = jsonArr_doctosRespaldos.getJSONObject(i);
                    dbHandler.insertadoctosRespaldos(jsonObjDoctosRespaldos);
                }
            } // Fin del Try
            catch (JSONException e)
            {
                e.printStackTrace();
            } // fin del catch


            /*****************************************************************************
             * Bloque para la importacion del catalogo ts_cat_bancos                     *
             * **************************************************************************/
            connection = null;
            reader = null;
            String JsonUrlCatalogoBancos = "";

            try {
                URL url = new URL(urlCatalogoBancos);
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
                JsonUrlCatalogoBancos = buffer.toString();


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
                JSONArray jsonArr_catBancos = new JSONArray(JsonUrlCatalogoBancos);

                for (int i = 0; i < jsonArr_catBancos.length(); i++)
                {
                    JSONObject jsonObjCatalogoBancos = jsonArr_catBancos.getJSONObject(i);
                    dbHandler.insertacatBancos(jsonObjCatalogoBancos);
                }
            } // Fin del Try
            catch (JSONException e)
            {
                e.printStackTrace();
            } // fin del catch

            /*****************************************************************************
             * Bloque para el seguimiento_de_pedidos *
             * **************************************************************************/
            connection = null;
            reader = null;
            String JsonUrlVnSeguimientoDePedidos = "";

            try {
                URL url = new URL(urlVnSeguimientoDePedidos);
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
                JsonUrlVnSeguimientoDePedidos = buffer.toString();


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
                JSONArray jsonArr_vnSeguimientoDePedidos = new JSONArray(JsonUrlVnSeguimientoDePedidos);

                for (int i = 0; i < jsonArr_vnSeguimientoDePedidos.length(); i++)
                {
                    JSONObject jsonObjVnSeguimientoDePedidos = jsonArr_vnSeguimientoDePedidos.getJSONObject(i);
                    dbHandler.insertaVnSeguimientoDePedidos(jsonObjVnSeguimientoDePedidos);
                }
            } // Fin del Try
            catch (JSONException e)
            {
                e.printStackTrace();
            } // fin del catch


            /*****************************************************************************
             * Bloque para el seguimiento_de_pagos *
             * **************************************************************************/
            connection = null;
            reader = null;
            String JsonUrlVnSeguimientoDePagos = "";

            try {
                URL url = new URL(urlVnSeguimientoDePagos);
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
                JsonUrlVnSeguimientoDePagos = buffer.toString();


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
                JSONArray jsonArr_vnSeguimientoDePagos = new JSONArray(JsonUrlVnSeguimientoDePagos);

                for (int i = 0; i < jsonArr_vnSeguimientoDePagos.length(); i++)
                {
                    JSONObject jsonObjVnSeguimientoDePagos = jsonArr_vnSeguimientoDePagos.getJSONObject(i);
                    dbHandler.insertaVnSeguimientoDePagos(jsonObjVnSeguimientoDePagos);
                }
            } // Fin del Try
            catch (JSONException e)
            {
                e.printStackTrace();
            } // fin del catch


            /*****************************************************************************
             * Bloque para el ruta semanal *
             * **************************************************************************/
            connection = null;
            reader = null;
            String JsonUrlRutaSemanal = "";

            try {
                URL url = new URL(urlRutaSemanal);
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
                JsonUrlRutaSemanal = buffer.toString();


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
                JSONArray jsonArr_RutaSemanal = new JSONArray(JsonUrlRutaSemanal);

                for (int i = 0; i < jsonArr_RutaSemanal.length(); i++)
                {
                    JSONObject jsonObjRutaSemanal = jsonArr_RutaSemanal.getJSONObject(i);
                    dbHandler.insertaVnProgramaRutasSemanales(jsonObjRutaSemanal);
                }
            } // Fin del Try
            catch (JSONException e)
            {
                e.printStackTrace();
            } // fin del catch

            /*************************************************************
             * Bloque para el catalogo de entidades:                      *
             * ***********************************************************/
            connection = null;
            reader = null;
            String JsonUrlCatalogoEntidades = "";

            try {
                URL url = new URL(urlGlCatEntidades);
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
                JsonUrlCatalogoEntidades = buffer.toString();


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
                JSONArray jsonArr_entidades = new JSONArray(JsonUrlCatalogoEntidades);

                for (int i = 0; i < jsonArr_entidades.length(); i++)
                {
                    JSONObject jsonObjCatalogoEntidades= jsonArr_entidades.getJSONObject(i);
                    dbHandler.insertaGlCatEntidades(jsonObjCatalogoEntidades);
                }
            } // Fin del Try
            catch (JSONException e)
            {
                e.printStackTrace();
            } // fin del catch
            /*************************************************************
             * Bloque para el catalogo de localidades:                      *
             * ***********************************************************/
            connection = null;
            reader = null;
            String JsonUrlCatalogoLocalidades = "";

            try {
                URL url = new URL(urlGlCatLocalidades);
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
                JsonUrlCatalogoLocalidades = buffer.toString();


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
                JSONArray jsonArr_localidades = new JSONArray(JsonUrlCatalogoLocalidades);

                for (int i = 0; i < jsonArr_localidades.length(); i++)
                {
                    JSONObject jsonObjCatalogoLocalidades= jsonArr_localidades.getJSONObject(i);
                    dbHandler.insertaGlCatLocalidades(jsonObjCatalogoLocalidades);
                }
            } // Fin del Try
            catch (JSONException e)
            {
                e.printStackTrace();
            } // fin del catch


            /*************************************************************
             * Bloque para la tabla de la reserva                      *
             * ***********************************************************/
/*
            connection = null;
            reader = null;
            String JsonUrlCtReserva = "";

            try {
                URL url = new URL(urlCtReserva);
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
                JsonUrlCtReserva = buffer.toString();


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
                JSONArray jsonArr_CtReserva = new JSONArray(JsonUrlCtReserva);

                for (int i = 0; i < jsonArr_CtReserva.length(); i++)
                {
                    JSONObject jsonObjCtReserva= jsonArr_CtReserva.getJSONObject(i);
                    dbHandler.insertaCtReserva(jsonObjCtReserva);
                }
            } // Fin del Try
            catch (JSONException e)
            {
                e.printStackTrace();
            } // fin del catch
*/
            /*************************************************************
             * Bloque para la tabla de las OC                    *
             * ***********************************************************/
            /*
            connection = null;
            reader = null;
            String JsonUrlCmOC = "";

            try {
                URL url = new URL(urlCmOC);
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
                JsonUrlCmOC = buffer.toString();


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
                JSONArray jsonArr_CmOC = new JSONArray(JsonUrlCmOC);

                for (int i = 0; i < jsonArr_CmOC.length(); i++)
                {
                    JSONObject jsonObjCmOC= jsonArr_CmOC.getJSONObject(i);
                    dbHandler.insertaCmOrdenesCompra(jsonObjCmOC);
                }
            } // Fin del Try
            catch (JSONException e)
            {
                e.printStackTrace();
            } // fin del catch
            */
            /*************************************************************
             * Bloque para la tabla de las imagenes de las políticas                   *
             * ***********************************************************/

            connection = null;
            reader = null;
            String JsonUrlCatalogoPoliticas = "";

            try {
                URL url = new URL(urlVnPoliticas);
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
                JsonUrlCatalogoPoliticas = buffer.toString();


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
                JSONArray jsonArr_politicas = new JSONArray(JsonUrlCatalogoPoliticas);

                for (int i = 0; i < jsonArr_politicas.length(); i++)
                {
                    JSONObject jsonObjCatalogoPoliticas= jsonArr_politicas.getJSONObject(i);
                    dbHandler.insertaVnPoliticas(jsonObjCatalogoPoliticas);
                }
            } // Fin del Try
            catch (JSONException e)
            {
                e.printStackTrace();
            } // fin del catch


            return null;
        } // Fin del doInBackground

        @Override
        protected void onPostExecute(String result)
        {
            super.onPostExecute(result);

            if (pd.isShowing())
            {
                pd.dismiss();
                reload();
            }

        }
    } // Fin de la clase JsonTask


    private class JsonTaskSender extends AsyncTask<String, String, String>
    {
        // Bloque para poder usar los metodos de la clase MyDBHandler en la clase JsonTask
        private MyDBHandler dbHandler;
        public JsonTaskSender(MyDBHandler dbHandler)
        {
            this.dbHandler = dbHandler;
        }
        // Termina bloque de inclusion de los metodos de MyDBHandler (Se uso esta practica debido a que ya esta usado extends en la clase

        protected void onPreExecute()
        {
            super.onPreExecute();

            pdSender = new ProgressDialog(EligeRuta.this);
            pdSender.setTitle("Conectando a servidor Biochem...");
            pdSender.setMessage("Iniciando transmision...");
            pdSender.setCancelable(false);
            pdSender.show();
        }

        protected String doInBackground(String... params)
        {

            /*************************************************************
             * Bloque para envio de los pedidos :                        *
             * ***********************************************************/
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            String JsonUrlEnvioPedidos = "";
            String urlSendPurchase = "http://www.sybrem.com.mx/adsnet/syncmovil/ReceptorMovil.php?jsonCad="+ URLEncoder.encode(dbHandler.transmitePedidos());

            try {

                URL url = new URL(urlSendPurchase);
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
                //Log.d("Modo debug 1.2", buffer.toString());
                JsonUrlEnvioPedidos = buffer.toString().substring(0,12);
                if (JsonUrlEnvioPedidos.toString().equals("SINCRONIZADO"))
                {
                    // Sincronizacion exitosa, se eliminan pedidos
                    dbHandler.resetPedidos();

                }
                else
                {
                    // Sincronizacion no exitosa. No se eliminan pedidos
                }

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

            return null;

        } // FIn del doInBackground

        @Override
        protected void onPostExecute(String result)
        {
            super.onPostExecute(result);

            if (pdSender.isShowing())
            {
                pdSender.dismiss();
            }

        } // Fin del onPostExecute
    } // Fin de la clase JsonTaskSender

    private class JsonTaskSendPayment extends AsyncTask<String, String, String>
    {


        // Bloque para poder usar los metodos de la clase MyDBHandler en la clase JsonTask
        private MyDBHandler dbHandler;
        public JsonTaskSendPayment(MyDBHandler dbHandler)
        {
            this.dbHandler = dbHandler;
        }

        protected void onPreExecute()
        {
            super.onPreExecute();

            pdSendPayment = new ProgressDialog(EligeRuta.this);
            pdSendPayment.setTitle("Conectando a servidor Biochem...");
            pdSendPayment.setMessage("Sincronizando información");
            pdSendPayment.setCancelable(false);
            pdSendPayment.show();
        }

        protected String doInBackground(String... params)
        {

            /*************************************************************
             * Bloque para envio de los pedidos :                        *
             * ***********************************************************/
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            String JsonUrlEnvioPagos = "";
            String urlSendpayment = "http://www.sybrem.com.mx/adsnet/syncmovil/ReceptorMovilPagosN.php?jsonCad="+ URLEncoder.encode(dbHandler.transmitePagos());

            try {

                URL url = new URL(urlSendpayment);
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
                JsonUrlEnvioPagos = buffer.toString().substring(0,11);
                if (JsonUrlEnvioPagos.toString().equals("SYNCPAGOSOK"))
                {
                    dbHandler.resetPagos();
                }
                else
                {
                    // Sincronizacion no exitosa. No se eliminan pedidos
                }

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

            return null;

        }

        @Override
        protected void onPostExecute(String result)
        {
            super.onPostExecute(result);

            if (pdSendPayment.isShowing())
            {
                pdSendPayment.dismiss();
            }

        }
    } // Fin de la clase JsonTaskSendPayment



    private class JsonTaskSendVisitas extends AsyncTask<String, String, String>
    {


        // Bloque para poder usar los metodos de la clase MyDBHandler en la clase JsonTask
        private MyDBHandler dbHandler;
        public JsonTaskSendVisitas(MyDBHandler dbHandler)
        {
            this.dbHandler = dbHandler;
        }

        protected void onPreExecute()
        {
            super.onPreExecute();

            pdSendVisitas = new ProgressDialog(EligeRuta.this);
            pdSendVisitas.setTitle("Conectando a servidor Biochem...");
            pdSendVisitas.setMessage("Sincronizando información");
            pdSendVisitas.setCancelable(false);
            pdSendVisitas.show();
        }

        protected String doInBackground(String... params)
        {

            /*************************************************************
             * Bloque para envio de las visitas :                        *
             * ***********************************************************/
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            String JsonUrlEnvioVisitas = "";
            String urlSendvisitas = "http://www.sybrem.com.mx/adsnet/syncmovil/ReceptorMovilVisitas.php?jsonCad="+ URLEncoder.encode(dbHandler.transmiteVisitas());

            try {

                URL url = new URL(urlSendvisitas);
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
                JsonUrlEnvioVisitas = buffer.toString().substring(0,12);
                if (JsonUrlEnvioVisitas.toString().equals("SYNVISITASOK"))
                {
                    dbHandler.resetVisitas();
                }
                else
                {
                    // Sincronizacion no exitosa. No se eliminan las visitas
                }

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

            return null;

        }

        @Override
        protected void onPostExecute(String result)
        {
            super.onPostExecute(result);

            if (pdSendVisitas.isShowing())
            {
                pdSendVisitas.dismiss();
            }

        }
    } // Fin de la clase JsonTaskSendVisitas

    private class JsonTaskSendProspectos extends AsyncTask<String, String, String>
    {


        // Bloque para poder usar los metodos de la clase MyDBHandler en la clase JsonTask
        private MyDBHandler dbHandler;
        public JsonTaskSendProspectos(MyDBHandler dbHandler)
        {
            this.dbHandler = dbHandler;
        }

        protected void onPreExecute()
        {
            super.onPreExecute();

            pdSendProspectos = new ProgressDialog(EligeRuta.this);
            pdSendProspectos.setTitle("Conectando a servidor Biochem...");
            pdSendProspectos.setMessage("Sincronizando información");
            pdSendProspectos.setCancelable(false);
            pdSendProspectos.show();
        }

        protected String doInBackground(String... params)
        {

            /*************************************************************
             * Bloque para envio de los prospectos :                        *
             * ***********************************************************/
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            String JsonUrlEnvioProspectos = "";
            String urlSendprospectos = "http://www.sybrem.com.mx/adsnet/syncmovil/ReceptorMovilProspectos.php?jsonCad="+ URLEncoder.encode(dbHandler.transmiteProspectos());

            try {

                URL url = new URL(urlSendprospectos);
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
                JsonUrlEnvioProspectos = buffer.toString().substring(0,15);
                if (JsonUrlEnvioProspectos.toString().equals("SYNPROSPECTOSOK"))
                {
                    dbHandler.resetProspectos();
                }
                else
                {
                    // Sincronizacion no exitosa. No se eliminan las visitas
                }

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

            return null;

        }

        @Override
        protected void onPostExecute(String result)
        {
            super.onPostExecute(result);

            if (pdSendProspectos.isShowing())
            {
                pdSendProspectos.dismiss();
            }

        }
    }




}
