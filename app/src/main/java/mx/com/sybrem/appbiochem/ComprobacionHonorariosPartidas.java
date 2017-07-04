package mx.com.sybrem.appbiochem;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ComprobacionHonorariosPartidas extends AppCompatActivity {

    private  static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "bladeTablet.db";
    ArrayList<String> Lstproveedores=new ArrayList<String>();
    ArrayList<String> rfcs=new ArrayList<String>();
    String sendFactura="", respuestaComprobacion="";
    String factura="", JSON_Send="";
    String id_cuenta="", concepto="";
    String orden_compra="", bandera_folio="";

    TextView txtRfc1;
    EditText edtRfc1;
    ProgressDialog pd; // Mensaje de progreso en sincronizacion.

    ProgressDialog pdSendComprobaciones;// Mensaje de progreso en sincronizacion Comprobaciones.

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final MyDBHandler dbHandler = new MyDBHandler(this, null, null, DATABASE_VERSION);
        dbHandler.checkDBStatus(); // Tiene como fin forzar la creacion de la base de datos.

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comprobacion_honorarios_partidas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final String usuarioLog = dbHandler.ultimoUsuarioRegistrado();
        String TipoUsuario = dbHandler.tipoUsuario(usuarioLog);
        String ruta = "";

        if (!CheckNetwork.isInternetAvailable(ComprobacionHonorariosPartidas.this)) //returns true if internet available
        {
            String msj = "Sin conexión a internet.\nSe requiere conexión a internet para realizar la comprobación.";
            //Crea ventana de alerta.
            AlertDialog.Builder dialog1 = new AlertDialog.Builder(this);
            dialog1.setMessage(msj);
            //dialog1.setNegativeButton("",null);
            //Establece el boton de Aceptar y que hacer si se selecciona.
            dialog1.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent explicit_intent;
                    explicit_intent = new Intent(ComprobacionHonorariosPartidas.this, NavDrawerActivity.class);
                    explicit_intent.putExtra("usuario", usuarioLog);
                    startActivity(explicit_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                    finish();
                }
            });
            //Muestra la ventana esperando respuesta.
            dialog1.show();
        }
        else {
            if (TipoUsuario.toString().equals("A")) {
                ruta = dbHandler.num_ruta(usuarioLog);
            } else {
                ruta = dbHandler.rutaSeleccionada();
                Log.d("Ruta seleccionada = ", ruta);
            }
            new ComprobacionHonorariosPartidas.JsonTask().execute();

            TextView titulo = (TextView)findViewById(R.id.textView);
            TextView partida1 = (TextView)findViewById(R.id.partida1);

            final TextView txtProveedor1 = (TextView)findViewById(R.id.txtProveedor1);

            //TextView txtRfc1 = (TextView)findViewById(R.id.txtRfc1);

            final TextView txtFechaEmision1 = (TextView)findViewById(R.id.txtFechaEmision1);

            final TextView textview1 = (TextView)findViewById(R.id.textview1);

            TextView txtMonto1 = (TextView)findViewById(R.id.txtMonto1);

            TextView txtHM1 = (TextView)findViewById(R.id.txtHM1);

            final AutoCompleteTextView autoCompleteProveedor1 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProveedor1);

            //final EditText edtRfc1 = (EditText)findViewById(R.id.edtRfc1);

            final EditText edtMonto1 = (EditText)findViewById(R.id.edtMonto1);

            final EditText edtHoraMinuto1 = (EditText)findViewById(R.id.edtHoraMinuto1);

            Button btn1 = (Button)findViewById(R.id.btn1);

            final Button btnSalir = (Button)findViewById(R.id.btnSalir);
            Button btn_enviar = (Button)findViewById(R.id.btnSaved);

            TextView txtFolio=(TextView)findViewById(R.id.txtFolio);
            final EditText edtFolio=(EditText)findViewById(R.id.edtFolio);

            ///PARTE PARA EL AUTOCOMPLETE DE LOS CLIENTES
            ArrayAdapter adapterProveedores = new ArrayAdapter(ComprobacionHonorariosPartidas.this, android.R.layout.simple_list_item_1, Lstproveedores);

            Intent intent=getIntent();
            Bundle extras = intent.getExtras();
            id_cuenta=(String)extras.get("infox");
            concepto=(String)extras.get("concepto");

            titulo.setText("Comprobación: "+concepto);
            autoCompleteProveedor1.setAdapter(adapterProveedores);

            bandera_folio=(String)extras.get("band_folio");

            if(!bandera_folio.equals("8080")){
                txtFolio.setVisibility(LinearLayout.INVISIBLE);
                edtFolio.setVisibility(LinearLayout.INVISIBLE);
            }
            else{
                txtHM1.setVisibility(LinearLayout.INVISIBLE);
                edtHoraMinuto1.setVisibility(LinearLayout.INVISIBLE);
            }

            Integer partida = 1;
            //String informacion1 = dbHandler.getPartidasOC(orden_compra, partida);
            if(concepto.length() > 0){
                partida1.setText(concepto);
                autoCompleteProveedor1.setAdapter(adapterProveedores);

                String proveedor_selec = String.valueOf(autoCompleteProveedor1.getText().toString());



                //Cuando se da clic en el boton del calendario
                btn1.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        DialogFragment newFragment = new DatePickerFragment();
                        newFragment.show(getSupportFragmentManager(), "datePicker");
                    }
                });

            }
            else{
                partida1.setVisibility(LinearLayout.INVISIBLE);
                txtProveedor1.setVisibility(LinearLayout.INVISIBLE);
                txtRfc1.setVisibility(LinearLayout.INVISIBLE);
                txtFechaEmision1.setVisibility(LinearLayout.INVISIBLE);
                textview1.setVisibility(LinearLayout.INVISIBLE);
                txtMonto1.setVisibility(LinearLayout.INVISIBLE);
                txtHM1.setVisibility(LinearLayout.INVISIBLE);
                autoCompleteProveedor1.setVisibility(LinearLayout.INVISIBLE);
                edtRfc1.setVisibility(LinearLayout.INVISIBLE);
                edtMonto1.setVisibility(LinearLayout.INVISIBLE);
                edtHoraMinuto1.setVisibility(LinearLayout.INVISIBLE);
                btn1.setVisibility(LinearLayout.INVISIBLE);
            }

            //Damos la opcion de guardar
            Button btnGuardar = (Button)findViewById(R.id.btnSaved);
            btnGuardar.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    final MyDBHandler dbHandler = new MyDBHandler(ComprobacionHonorariosPartidas.this, null, null, 1);
                    dbHandler.checkDBStatus(); // Tiene como fin forzar la creacion de la base de datos.
                    String uuid = "";
                    Integer id_reserva = 0;
                    Double total_pagado = 0.0;
                    Double precio_unitario = 0.0;
                    Double monto_actualizar = 0.0;

                    String proveedor1 = autoCompleteProveedor1.getText().toString();
                    //String rfc1 = edtRfc1.getText().toString();
                    String monto1 = edtMonto1.getText().toString();
                    String fecha1 = textview1.getText().toString();
                    String hour = edtHoraMinuto1.getText().toString();
                    String hora1 = (hour.equals(""))?"NA":hour;

                    if(bandera_folio.equals("8080")){
                        String foli=edtFolio.getText().toString();
                        if(foli.length()<8){
                            Toast toast = Toast.makeText(getApplicationContext(), "Debe escribir los primeros 8 caracteres del folio fiscal", Toast.LENGTH_SHORT);
                            toast.show();
                            return;
                        }
                    }

                    if(proveedor1.length() == 0){
                        Toast toast = Toast.makeText(getApplicationContext(), "Debe escribir un proveedor", Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }
                    if(monto1.length() == 0){
                        Toast toast = Toast.makeText(getApplicationContext(), "Escriba un monto", Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }/*if(hora1.length() == 0){
                                        Toast toast = Toast.makeText(getApplicationContext(), "Escriba la hora de emisión de la factura", Toast.LENGTH_SHORT);
                                        toast.show();
                                        return;
                                    }*/
                    if(fecha1.toString().equals("_/_/_")){
                        Toast toast = Toast.makeText(getApplicationContext(), "Seleccione una fecha", Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }
                    ///Si la partida 1 tiene informacion
                    if(proveedor1.length() > 0 && monto1.length() > 0){

                        if(fecha1.toString().equals("_/_/_")){
                            Toast toast = Toast.makeText(getApplicationContext(), "Seleccione una fecha", Toast.LENGTH_SHORT);
                            toast.show();
                            return;
                        }

                        String[] separatedFec = fecha1.split("/");
                        String anio = "";
                        String mes = "";
                        String dia = "";

                        anio = separatedFec[0];
                        mes = separatedFec[1];
                        dia = separatedFec[2];

                        //se le agrega un 0 al dia
                        if(dia.toString().equals("1")){
                            dia = "01";
                        }
                        if(dia.toString().equals("2")){
                            dia = "02";
                        }
                        if(dia.toString().equals("3")){
                            dia = "03";
                        }
                        if(dia.toString().equals("4")){
                            dia = "04";
                        }
                        if(dia.toString().equals("5")){
                            dia = "05";
                        }
                        if(dia.toString().equals("6")){
                            dia = "06";
                        }
                        if(dia.toString().equals("7")){
                            dia = "07";
                        }
                        if(dia.toString().equals("8")){
                            dia = "08";
                        }
                        if(dia.toString().equals("9")){
                            dia = "09";
                        }

                        //se le agrega un 0 al mes
                        if(mes.toString().equals("1")){
                            mes = "01";
                        }
                        if(mes.toString().equals("2")){
                            mes = "02";
                        }
                        if(mes.toString().equals("3")){
                            mes = "03";
                        }
                        if(mes.toString().equals("4")){
                            mes = "04";
                        }
                        if(mes.toString().equals("5")){
                            mes = "05";
                        }
                        if(mes.toString().equals("6")){
                            mes = "06";
                        }
                        if(mes.toString().equals("7")){
                            mes = "07";
                        }
                        if(mes.toString().equals("8")){
                            mes = "08";
                        }
                        if(mes.toString().equals("9")){
                            mes = "09";
                        }

                        fecha1 = anio+"-"+mes+"-"+dia;
                        String fecha_armada=fecha1+" "+hora1;

                        String folio_fiscal=(edtFolio.getText().toString().equals(""))?"nA":edtFolio.getText().toString();



                        sendFactura=(folio_fiscal.equals("nA"))?proveedor1+"*"+monto1+"*"+fecha_armada+"*nA":proveedor1+"*"+monto1+"*"+fecha_armada+"*"+folio_fiscal;
                        Log.d("variable sendfactura"," "+sendFactura);
                        try {
                            System.out.println("sendfactura: "+URLEncoder.encode(sendFactura,"UTF-8"));
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        new BuscaFactura(sendFactura).execute();
                        SystemClock.sleep(1000);

                        autoCompleteProveedor1.setText("");
                        //edtRfc1.setText("");
                        edtMonto1.setText("");
                        textview1.setText("_/_/_");
                        edtHoraMinuto1.setText("");
                    }
                }
            });
            //Damos la opcion de salir
            btnSalir.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    final MyDBHandler dbHandler = new MyDBHandler(ComprobacionHonorariosPartidas.this, null, null, 1);
                    dbHandler.checkDBStatus(); // Tiene como fin forzar la creacion de la base de datos.

                    String usuario = dbHandler.ultimoUsuarioRegistrado();

                    Intent explicit_intent;
                    explicit_intent = new Intent(ComprobacionHonorariosPartidas.this, Comprobacion.class);
                    explicit_intent.putExtra("usuario", usuario);
                    startActivity(explicit_intent);
                    return;
                }
            });
        }

    }// Fin del onCreate

    //JSON OBTIENE PROVEEDORES

    class JsonTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();

            pd = new ProgressDialog(ComprobacionHonorariosPartidas.this);
            pd.setTitle("Conectando a servidor Biochem...");
            pd.setMessage("Obteniendo lista de proveedores...");
            pd.setCancelable(false);
            pd.show();
        }

        protected String doInBackground(String... params) {

            String urlCtReserva = "http://www.sybrem.com.mx/adsnet/syncmovil/SyncMovil.php?ruta=0&tabla=reserva";
            /*************************************************************
             * Bloque para la tabla de la reserva                      *
             * ***********************************************************/

            HttpURLConnection connection = null;
            BufferedReader reader = null;
            String JsonUrlCtReserva = "";
            ArrayList<String> RSRV = new ArrayList<String>();

            try {
                URL url = new URL(urlCtReserva);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
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
            return JsonUrlCtReserva;
        } // Fin del doInBackground

        @Override
        protected void onPostExecute(String result) {
            try {
                // Convierte el string con la informacion de los clientes en un array JSON
                JSONArray jsonArr_CtReserva = new JSONArray(result);


                for (int i = 0; i < jsonArr_CtReserva.length(); i++)
                {
                    JSONObject jsonObjCtReserva= jsonArr_CtReserva.getJSONObject(i);
                    Lstproveedores.add(jsonObjCtReserva.getString("provider"));
                }
            } // Fin del Try
            catch (JSONException e) {
                e.printStackTrace();
            } // fin del catch
            super.onPostExecute(result);

            if (pd.isShowing()) {

                pd.dismiss();
                //reload();
            }

        }
    } // Fin de la clase JsonTask donde busca los PROVEEDORES

    //JSON BUSCA FACTURAS

    class BuscaFactura extends AsyncTask<String, String, String> {

        private String facci;
        public BuscaFactura(String s)
        {
            this.facci = s;
        }

        protected void onPreExecute() {
            super.onPreExecute();

            pd = new ProgressDialog(ComprobacionHonorariosPartidas.this);
            pd.setTitle("Conectando a servidor Biochem...");
            pd.setMessage("Buscando factura, espere...");
            pd.setCancelable(false);
            pd.show();
        }

        protected String doInBackground(String... params) {

            String urlCtReserva = "http://www.sybrem.com.mx/adsnet/syncmovil/BuscaFacturaComprobacion.1.php?ruta=0&tabla=reserva&factura="+URLEncoder.encode(sendFactura);
            /*************************************************************
             * Bloque para la tabla de la reserva                      *
             * ***********************************************************/
            Log.d("//++++SENDFACTURA++++//",sendFactura);
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            String BuscaFact = "";

            try {
                URL url = new URL(urlCtReserva);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                    // Log.d("Salida: ", "> " + line);   //Se hace una salida por monitoreo en la consola. ELIMINAR / COMENTAR
                }
                BuscaFact = buffer.toString();
                factura=BuscaFact;
                SystemClock.sleep(3000);


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

            //return JsonUrlCtReserva;
            return BuscaFact;
        } // Fin del doInBackground

        @Override
        protected void onPostExecute(String result) {
            Log.d("//++++RESULT++++//","\""+result+"\"");
            Log.d("//++++FACTURILLA++++//","\""+factura+"\"");
            final MyDBHandler dbHandler = new MyDBHandler(ComprobacionHonorariosPartidas.this, null, null, DATABASE_VERSION);
            dbHandler.checkDBStatus();
            final String usuarioLog = dbHandler.ultimoUsuarioRegistrado();

            String facci2=factura.trim();
            String msje="";
            String id_reserv="";
            if(facci2.equals("E3306")){
                msje="ERROR\nOcurrió un error al conectarse a la base de datos";
                getMensjeError(msje,usuarioLog,"");
            }else if(facci2.equals("E404")){
                msje="ERROR\nOcurrió un error al ejecutar la consulta";
                getMensjeError(msje,usuarioLog,"");
            }else if(facci2.equals("E120")){
                msje="ERROR\nNo se encontró la factura con los datos ingresados\nVerifique los datos e intente de nuevo";
                getMensjeError(msje,usuarioLog,"");
            }
            else if(facci2.equals("E240")) {
                msje = "ERROR\nSe encontraron 2 o más facturas con los datos ingresados.\nIngrese la hora con el siguiente formato\nejemplo: 06:23:45\n";
                getMensjeError(msje, usuarioLog, "");
            }
            else if(facci2.equals("E241")) {
                msje = "ERROR\nSe encontraron 2 o más facturas con los datos ingresados.\nIngrese los primeros 8 dígitos del FOLIO FISCAL de la factura a comprobar\n";
                getMensjeError2Hora(msje, usuarioLog, "8080");
            }
            else{
                id_reserv=facci2;
                //Se saca la fecha de registro
                Calendar cal = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String fechaRegistro = sdf.format(cal.getTime());

                //Se obtiene le usuario logeado del metodo
                String usuario = dbHandler.ultimoUsuarioRegistrado();
                //Se prepara el JSON a enviar al servidor
                JSON_Send = "[";
                JSON_Send += "{\"id_cuenta\":\"" + id_cuenta + "\",";
                JSON_Send += "\"id_reserva\":\"" + id_reserv + "\",";
                JSON_Send += "\"usuario\":\"" + usuario + "\",";
                JSON_Send += "\"fecha_registro\":\"" + fechaRegistro + "\"}";
                JSON_Send += "]";
                Log.d("//+++++JSON+++++//",JSON_Send);
                // se envía el JSON a través de otro AsyncTask
                new ComprobacionHonorariosPartidas.JsonTaskSendComprobaciones().execute();
            }


            super.onPostExecute(result);

            if (pd.isShowing()) {

                pd.dismiss();
                //reload();
            }

        }
    } // Fin de la clase JsonTask donde BUSCA FACTURAS


    // JSonTask que comprueba la factura

    class JsonTaskSendComprobaciones extends AsyncTask<String, String, String>
    {

        protected void onPreExecute()
        {
            super.onPreExecute();

            pdSendComprobaciones = new ProgressDialog(ComprobacionHonorariosPartidas.this);
            pdSendComprobaciones.setTitle("Conectando a servidor Biochem...");
            pdSendComprobaciones.setMessage("Realizando la comprobación, espere...");
            pdSendComprobaciones.setCancelable(false);
            pdSendComprobaciones.show();
        }

        protected String doInBackground(String... params)
        {


            HttpURLConnection connection = null;
            BufferedReader reader = null;
            String JsonUrlEnvioComprobaciones = "";
            String urlSendcomprobaciones = "http://www.sybrem.com.mx/adsnet/syncmovil/ReceptorComprobacionesHonorarios.php?jsonCad="+URLEncoder.encode(JSON_Send);

            try {

                URL url = new URL(urlSendcomprobaciones);
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
                JsonUrlEnvioComprobaciones = buffer.toString();
                // se obtiene la respuesta del servidor una vez se comprueba la factura
                // en el archivo ReceptorComprobaciones.php
                respuestaComprobacion=JsonUrlEnvioComprobaciones.toString();

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

            return JsonUrlEnvioComprobaciones;

        }

        @Override
        protected void onPostExecute(String result)
        {
            final MyDBHandler dbHandler = new MyDBHandler(ComprobacionHonorariosPartidas.this, null, null, DATABASE_VERSION);
            dbHandler.checkDBStatus();
            final String usuarioLog = dbHandler.ultimoUsuarioRegistrado();
            String resp="";
            String msje="";
            resp=respuestaComprobacion.trim();
            Log.d("//+++++RESP-1+++++//",respuestaComprobacion);
            //Log.d("//+++++RESP-2+++++//",resp);
            if(resp.equals("OK")){// si la reespuesta es OK, muestra el siguiente msje.
                msje="Comprobación exitosa!\nPulse Aceptar para continuar.";
                getMensjeError(msje,usuarioLog,"");
            }
            else{// si la respuesta no es OK, muestra el msje. de error que envíe el script ReceptorComprobaciones.php
                getMensjeError(respuestaComprobacion,usuarioLog,"");
            }
            super.onPostExecute(result);

            if (pdSendComprobaciones.isShowing())
            {
                pdSendComprobaciones.dismiss();
            }

        }
    }// fin JsonTask donde se comprueba la factura

    //Metodo para refrescar el menu
    public void reload(){

        final MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);

        String usuario = dbHandler.ultimoUsuarioRegistrado();

        Intent explicit_intent;
        explicit_intent = new Intent(ComprobacionHonorariosPartidas.this, ComprobacionHonorarios.class);
        explicit_intent.putExtra("usuario", usuario);
        startActivity(explicit_intent);
        return;

    }
    public void getMensjeError(String msje, final String usuarioLog, final String bandera){
        //Crea ventana de alerta.
        AlertDialog.Builder dialog1 = new AlertDialog.Builder(ComprobacionHonorariosPartidas.this);
        dialog1.setMessage(msje);
        //Establece el boton de Aceptar y que hacer si se selecciona.
        dialog1.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent explicit_intent;
                explicit_intent = new Intent(mx.com.sybrem.appbiochem.ComprobacionHonorariosPartidas.this, ComprobacionHonorarios.class);
                explicit_intent.putExtra("usuario", usuarioLog);
                explicit_intent.putExtra("band_folio",bandera);
                startActivity(explicit_intent);
                //finish();
            }
        });
        //Muestra la ventana esperando respuesta.
        dialog1.show();
    }

    // función que solicita el folio fiscal en caso de que existan facturas a comprobar
    // con los mismos datos enviados (RFC, monto de la factura y hora de emisión de la factura)
    public void getMensjeError2Hora(String msje, final String usuarioLog, final String bandera){
        //Crea ventana de alerta.
        AlertDialog.Builder dialog1 = new AlertDialog.Builder(ComprobacionHonorariosPartidas.this);
        dialog1.setMessage(msje);
        //Establece el boton de Aceptar y que hacer si se selecciona.
        dialog1.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent explicit_intent;
                explicit_intent = new Intent(mx.com.sybrem.appbiochem.ComprobacionHonorariosPartidas.this, ComprobacionHonorarios.class);
                explicit_intent.putExtra("usuario", usuarioLog);
                explicit_intent.putExtra("oc", orden_compra);
                explicit_intent.putExtra("infox",id_cuenta);
                explicit_intent.putExtra("band_folio",bandera);
                startActivity(explicit_intent);
                //finish();
            }
        });
        //Muestra la ventana esperando respuesta.
        dialog1.show();
    }

}
