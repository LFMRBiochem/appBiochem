package mx.com.sybrem.appbiochem;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ReporteVenta extends AppCompatActivity {
    /*
Declarar instancias globales
*/
    private  static final int DATABASE_VERSION = 2;
    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;
    public String JSONReporte="", diaReporte="", ruta="";
    int diaAndroid, mesAndroid,añoAndroid;
    TextView titulo_reporte;
    Button btnMenuPrincipal;
    ProgressDialog pd;

    int[] images={R.drawable.petmaxadulto,R.drawable.petmaxpuppy,R.drawable.shampopets,R.drawable.shampopets,R.drawable.shampopets};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte_venta);

        final MyDBHandler dbHandler = new MyDBHandler(ReporteVenta.this, null, null, DATABASE_VERSION);
        dbHandler.checkDBStatus();
        final String usuarioLog = dbHandler.ultimoUsuarioRegistrado();
        String TipoUsuario = dbHandler.tipoUsuario(usuarioLog);
        if (TipoUsuario.toString().equals("A")) {
            ruta = dbHandler.num_ruta(usuarioLog);
        } else {
            ruta = dbHandler.rutaSeleccionada();
            Log.d("Ruta seleccionada = ",ruta);
        }

        btnMenuPrincipal=(Button)findViewById(R.id.btnNext);
        btnMenuPrincipal.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                final MyDBHandler dbHandler = new MyDBHandler(ReporteVenta.this, null, null, 1);
                dbHandler.checkDBStatus(); // Tiene como fin forzar la creacion de la base de datos.

                String usuario = dbHandler.ultimoUsuarioRegistrado();

                Intent explicit_intent;
                explicit_intent = new Intent(ReporteVenta.this, NavDrawerActivity.class);
                explicit_intent.putExtra("usuario", usuario);
                startActivity(explicit_intent);
                return;
            }
        });
        diaReporte=dbHandler.getReporteVentasFecha();
        titulo_reporte=(TextView)findViewById(R.id.title_reporte);

        //si no existe informacion del reporte
        if(diaReporte.equals("")){
            //Verifica la conexion a internet
            if (!CheckNetwork.isInternetAvailable(ReporteVenta.this)) {
                String msj = "No hay datos para mostrar se necesita una conexion a internet para mostrar el reporte de venta por producto ";
                //Crea ventana de alerta.
                AlertDialog.Builder dialog1 = new AlertDialog.Builder(this);
                dialog1.setMessage(msj);
                //dialog1.setNegativeButton("",null);
                //Establece el boton de Aceptar y que hacer si se selecciona.
                dialog1.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        return;
                    }
                });
                //Muestra la ventana esperando respuesta.
                dialog1.show();
            }else{
                new ReporteVenta.JsonTaskGeneraReporteVentas(dbHandler).execute();
                /*diaReporte=dbHandler.getReporteVentasFecha();
                // Obtenemos la fecha del reporte de ventas
                String[] partsFechaHora = diaReporte.split(" ");
                String Fecha= partsFechaHora[0];
                String Hora = partsFechaHora[1];
                String[] partsFecha =Fecha.split("-");
                String mesFecha="", diaFecha="", añoFecha="",mes="";
                diaFecha=partsFecha[2];
                mesFecha=partsFecha[1];
                añoFecha=partsFecha[0];

                switch(mesFecha){
                    case "01":mes="Enero";break;
                    case "02":mes="Febrero";break;
                    case "03":mes="Marzo";break;
                    case "04":mes="Abril";break;
                    case "05":mes="Mayo";break;
                    case "06":mes="Junio";break;
                    case "07":mes="Jilio";break;
                    case "08":mes="Agosto";break;
                    case "09":mes="Septiembre";break;
                    case "10":mes="Octubre";break;
                    case "11":mes="Noviembre";break;
                    case "12":mes="Diciembre";break;
                }
                final String txtFechaReporte="Reporte de venta por producto al\n"+diaFecha+" de "+mes+" del "+añoFecha+" a las "+Hora;

                List items = new ArrayList();
                titulo_reporte.setText(txtFechaReporte);
                JSONReporte=dbHandler.getReporteVentas();
                try {
                    JSONArray jsonReporte = new JSONArray(JSONReporte);
                    for (int i3 = 0; i3 < jsonReporte.length(); i3++)
                    {
                        JSONObject obj = jsonReporte.getJSONObject(i3);
                        String nom_producto=obj.getString("nom_producto").toString();
                        String vendido = obj.getString("vendido").toString();
                        items.add(new RepVenta(images[i3], nom_producto, vendido));

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }*/
            }
        }
        //si existe informacion en el reporte
        else{
            // Obtenemos la fecha del reporte de ventas
            String[] partsFechaHora = diaReporte.split(" ");
            String Fecha= partsFechaHora[0];
            String Hora = partsFechaHora[1];
            String[] partsFecha =Fecha.split("-");
            String mesFecha="", diaFecha="", añoFecha="",mes="";
            diaFecha=partsFecha[2];
            mesFecha=partsFecha[1];
            añoFecha=partsFecha[0];

            switch(mesFecha){
                case "01":mes="Enero";break;
                case "02":mes="Febrero";break;
                case "03":mes="Marzo";break;
                case "04":mes="Abril";break;
                case "05":mes="Mayo";break;
                case "06":mes="Junio";break;
                case "07":mes="Julio";break;
                case "08":mes="Agosto";break;
                case "09":mes="Septiembre";break;
                case "10":mes="Octubre";break;
                case "11":mes="Noviembre";break;
                case "12":mes="Diciembre";break;
            }
            final String txtFechaReporte="Reporte de venta por producto al\n"+diaFecha+" de "+mes+" del "+añoFecha+" a las "+Hora;

            //Obtenemos la fecha del dispositivo Android
            final Calendar c = Calendar.getInstance();
            diaAndroid=c.get(Calendar.DAY_OF_MONTH);
            mesAndroid=c.get(Calendar.MONTH);
            añoAndroid=c.get(Calendar.YEAR);
            String mesString=""+mesAndroid, diaString=""+diaAndroid,mesStringAndroid="",diaStringAndroid="";

            mesStringAndroid=(mesString.length()==1)?"0"+mesString:mesString;
            diaStringAndroid=(diaString.length()==1)?"0"+diaString:diaString;

            String FechaAndroid=añoAndroid+mesStringAndroid+diaStringAndroid;
            String FechaReporte=Fecha.replace("-","");
            int intFechaAndroid=Integer.parseInt(FechaAndroid);
            int intFechaReporte=Integer.parseInt(FechaReporte);

            //Comparamos la fecha del reporte con la fecha del dispositivo
            if(intFechaReporte>=intFechaAndroid){
                List items = new ArrayList();
                titulo_reporte.setText(txtFechaReporte);
                JSONReporte=dbHandler.getReporteVentas();
                try {
                    JSONArray jsonReporte = new JSONArray(JSONReporte);
                    for (int i = 0; i < jsonReporte.length(); i++)
                    {
                        JSONObject obj = jsonReporte.getJSONObject(i);
                        String nom_producto=obj.getString("nom_producto").toString();
                        String vendido = obj.getString("vendido").toString();
                        items.add(new RepVenta(images[i], nom_producto, vendido));

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                recycler = (RecyclerView) findViewById(R.id.reciclador);
                recycler.setHasFixedSize(true);

                // Usar un administrador para LinearLayout
                lManager = new LinearLayoutManager(this);
                recycler.setLayoutManager(lManager);

                // Crear un nuevo adaptador
                adapter = new RepVentaAdapter(items);
                recycler.setAdapter(adapter);
            }
            // Si la fecha del dispositivo es mayor a la fecha del reporte
            else{
                //Verificamos conexion a internet y pregunta si se desea actualizar el reporte
                if (!CheckNetwork.isInternetAvailable(ReporteVenta.this))
                {
                    String msj = "El reporte de venta por producto es de una fecha anterior a la fecha actual.\nSe mostrará el reporte del dia "+diaFecha+" de "+mes+" del "+añoFecha+" a las "+Hora;
                    //Crea ventana de alerta.
                    AlertDialog.Builder dialog1 = new AlertDialog.Builder(this);
                    dialog1.setMessage(msj);
                    //dialog1.setNegativeButton("",null);
                    //Establece el boton de Aceptar y que hacer si se selecciona.
                    dialog1.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            return;
                        }
                    });
                    //Muestra la ventana esperando respuesta.
                    dialog1.show();
                    //Se genera el reporte con los datos almacenados en la base de datos
                    List items = new ArrayList();
                    titulo_reporte.setText(txtFechaReporte);
                    JSONReporte=dbHandler.getReporteVentas();
                    try {
                        JSONArray jsonReporte = new JSONArray(JSONReporte);
                        for (int i = 0; i < jsonReporte.length(); i++)
                        {
                            JSONObject obj = jsonReporte.getJSONObject(i);
                            String nom_producto=obj.getString("nom_producto").toString();
                            String vendido = obj.getString("vendido").toString();
                            items.add(new RepVenta(images[i], nom_producto, vendido));

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    recycler = (RecyclerView) findViewById(R.id.reciclador);
                    recycler.setHasFixedSize(true);

                    // Usar un administrador para LinearLayout
                    lManager = new LinearLayoutManager(this);
                    recycler.setLayoutManager(lManager);

                    // Crear un nuevo adaptador
                    adapter = new RepVentaAdapter(items);
                    recycler.setAdapter(adapter);
                }
                else{
                    String msj = "El reporte de venta por producto es de una fecha anterior a la fecha actual.\nEl reporte es del dia "+diaFecha+" de "+mes+" del "+añoFecha+" a las "+Hora+"\nDesea actualizar el reporte o mostrar el reporte almacenado?";
                    //Crea ventana de alerta.
                    AlertDialog.Builder dialog1 = new AlertDialog.Builder(this);
                    dialog1.setMessage(msj);
                    //dialog1.setNegativeButton("",null);
                    //Establece el boton de Actualizar y que hacer si se selecciona.
                    dialog1.setPositiveButton("Actualizar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dbHandler.resetReporteVentas();
                            new ReporteVenta.JsonTaskGeneraReporteVentas(dbHandler).execute();
                            diaReporte=dbHandler.getReporteVentasFecha();
                            // Obtenemos la fecha del reporte de ventas
                            String[] partsFechaHora = diaReporte.split(" ");
                            String Fecha= partsFechaHora[0];
                            String Hora = partsFechaHora[1];
                            String[] partsFecha =Fecha.split("-");
                            String mesFecha="", diaFecha="", añoFecha="",mes="";
                            diaFecha=partsFecha[2];
                            mesFecha=partsFecha[1];
                            añoFecha=partsFecha[0];

                            switch(mesFecha){
                                case "01":mes="Enero";break;
                                case "02":mes="Febrero";break;
                                case "03":mes="Marzo";break;
                                case "04":mes="Abril";break;
                                case "05":mes="Mayo";break;
                                case "06":mes="Junio";break;
                                case "07":mes="Julio";break;
                                case "08":mes="Agosto";break;
                                case "09":mes="Septiembre";break;
                                case "10":mes="Octubre";break;
                                case "11":mes="Noviembre";break;
                                case "12":mes="Diciembre";break;
                            }
                            final String txtFechaReporte="Reporte de venta por producto al\n"+diaFecha+" de "+mes+" del "+añoFecha+" a las "+Hora;

                            List items = new ArrayList();
                            titulo_reporte.setText(txtFechaReporte);
                            JSONReporte=dbHandler.getReporteVentas();
                            try {
                                JSONArray jsonReporte = new JSONArray(JSONReporte);
                                for (int i3 = 0; i3 < jsonReporte.length(); i3++)
                                {
                                    JSONObject obj = jsonReporte.getJSONObject(i3);
                                    String nom_producto=obj.getString("nom_producto").toString();
                                    String vendido = obj.getString("vendido").toString();
                                    items.add(new RepVenta(images[i3], nom_producto, vendido));

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            recycler = (RecyclerView) findViewById(R.id.reciclador);
                            recycler.setHasFixedSize(true);

                            // Usar un administrador para LinearLayout
                            lManager = new LinearLayoutManager(getApplicationContext());
                            recycler.setLayoutManager(lManager);

                            // Crear un nuevo adaptador
                            adapter = new RepVentaAdapter(items);
                            recycler.setAdapter(adapter);
                            return;
                        }
                    });
                    dialog1.setNegativeButton("No Actualizar",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            List items = new ArrayList();
                            titulo_reporte.setText(txtFechaReporte);
                            JSONReporte=dbHandler.getReporteVentas();
                            try {
                                JSONArray jsonReporte = new JSONArray(JSONReporte);
                                for (int i2 = 0; i2 < jsonReporte.length(); i2++)
                                {
                                    JSONObject obj = jsonReporte.getJSONObject(i2);
                                    String nom_producto=obj.getString("nom_producto").toString();
                                    String vendido = obj.getString("vendido").toString();
                                    items.add(new RepVenta(images[i2], nom_producto, vendido));

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            recycler = (RecyclerView) findViewById(R.id.reciclador);
                            recycler.setHasFixedSize(true);

                            // Usar un administrador para LinearLayout
                            lManager = new LinearLayoutManager(getApplicationContext());
                            recycler.setLayoutManager(lManager);

                            // Crear un nuevo adaptador
                            adapter = new RepVentaAdapter(items);
                            recycler.setAdapter(adapter);
                            return;
                        }
                    });
                    //Muestra la ventana esperando respuesta.
                    dialog1.show();
                }
            }

        }
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        /*JSONReporte=dbHandler.getReporteVentas();

        // Inicializar Reporte de Venta
        List items = new ArrayList();

        items.add(new RepVenta(R.drawable.petmaxadulto, "Angel Beats", ""));
        items.add(new RepVenta(R.drawable.petmaxpuppy, "Death Note", ""));
        items.add(new RepVenta(R.drawable.shampopets, "Fate Stay Night", ""));
        items.add(new RepVenta(R.drawable.shampopets, "Welcome to the NHK", ""));
        items.add(new RepVenta(R.drawable.shampopets, "Suzumiya Haruhi", ""));

        // Obtener el Recycler
        recycler = (RecyclerView) findViewById(R.id.reciclador);
        recycler.setHasFixedSize(true);

        // Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(lManager);

        // Crear un nuevo adaptador
        adapter = new RepVentaAdapter(items);
        recycler.setAdapter(adapter);*/
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++



    }// ------------------------------------------------------ FIN DEL onCreate ----------------------------------------------------------------

    class JsonTaskGeneraReporteVentas extends AsyncTask<String, String, String>
    {
        // Bloque para poder usar los metodos de la clase MyDBHandler en la clase JsonTask
        public MyDBHandler dbHandler;

        public JsonTaskGeneraReporteVentas(MyDBHandler dbHandler)
        {
            this.dbHandler = dbHandler;
        }
        // Termina bloque de inclusion de los metodos de MyDBHandler (Se uso esta practica debido a que ya esta usado extends en la clase

        protected void onPreExecute()
        {
            super.onPreExecute();

            pd = new ProgressDialog(ReporteVenta.this);
            pd.setTitle("Conectando a servidor Biochem...");
            pd.setMessage("Obteniendo datos del reporte, espere...");
            pd.setCancelable(false);
            pd.show();
        }

        protected String doInBackground(String... params)
        {


            HttpURLConnection connection = null;
            BufferedReader reader = null;
            String JsonUrlReporteVentas = "";
            String urlReporteVentas = "http://www.sybrem.com.mx/adsnet/syncmovil/SyncMovil.php?ruta="+ruta+"&tabla=rv";

            try {

                URL url = new URL(urlReporteVentas);
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
                JsonUrlReporteVentas = buffer.toString();
                /* se obtiene la respuesta del servidor una vez se genera el auxiliar
                * en el archivo AuxiliarMovil.php
                */

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
                JSONArray jsonArr_ReporteVentas = new JSONArray(JsonUrlReporteVentas);

                for (int i = 0; i < jsonArr_ReporteVentas.length(); i++)
                {
                    JSONObject jsonObjRV = jsonArr_ReporteVentas.getJSONObject(i);
                    dbHandler.insertaReporteVentas(jsonObjRV);
                }
            } // Fin del Try
            catch (JSONException e)
            {
                e.printStackTrace();
            } // fin del catch
            //return JsonUrlReporteVentas;
            return null;
        }
        @Override
        protected void onPostExecute(String result){
            super.onPostExecute(result);
            diaReporte=dbHandler.getReporteVentasFecha();
            // Obtenemos la fecha del reporte de ventas
            String[] partsFechaHora = diaReporte.split(" ");
            String Fecha= partsFechaHora[0];
            String Hora = partsFechaHora[1];
            String[] partsFecha =Fecha.split("-");
            String mesFecha="", diaFecha="", añoFecha="",mes="";
            diaFecha=partsFecha[2];
            mesFecha=partsFecha[1];
            añoFecha=partsFecha[0];

            switch(mesFecha){
                case "01":mes="Enero";break;
                case "02":mes="Febrero";break;
                case "03":mes="Marzo";break;
                case "04":mes="Abril";break;
                case "05":mes="Mayo";break;
                case "06":mes="Junio";break;
                case "07":mes="Julio";break;
                case "08":mes="Agosto";break;
                case "09":mes="Septiembre";break;
                case "10":mes="Octubre";break;
                case "11":mes="Noviembre";break;
                case "12":mes="Diciembre";break;
            }
            final String txtFechaReporte="Reporte de venta por producto al\n"+diaFecha+" de "+mes+" del "+añoFecha+" a las "+Hora;

            List items = new ArrayList();
            titulo_reporte.setText(txtFechaReporte);
            JSONReporte=dbHandler.getReporteVentas();
            try {
                JSONArray jsonReporte = new JSONArray(JSONReporte);
                for (int i3 = 0; i3 < jsonReporte.length(); i3++)
                {
                    JSONObject obj = jsonReporte.getJSONObject(i3);
                    String nom_producto=obj.getString("nom_producto").toString();
                    String vendido = obj.getString("vendido").toString();
                    items.add(new RepVenta(images[i3], nom_producto, vendido));

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            recycler = (RecyclerView) findViewById(R.id.reciclador);
            recycler.setHasFixedSize(true);

            // Usar un administrador para LinearLayout
            lManager = new LinearLayoutManager(getApplicationContext());
            recycler.setLayoutManager(lManager);

            // Crear un nuevo adaptador
            adapter = new RepVentaAdapter(items);
            recycler.setAdapter(adapter);

            if (pd.isShowing())
            {
                pd.dismiss();
            }
        }
    }// -------------------------------------------------------------Fin de JsonTaskGeneraReporteVentas --------------------------------------------------------------------

    @Override
    public void onBackPressed() {
        // do nothing
    }

}
