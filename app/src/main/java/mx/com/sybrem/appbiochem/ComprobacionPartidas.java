package mx.com.sybrem.appbiochem;

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

import devazt.devazt.networking.HttpClient;
import devazt.devazt.networking.OnHttpRequestComplete;
import devazt.devazt.networking.Response;

public class ComprobacionPartidas extends AppCompatActivity {

    private  static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "bladeTablet.db";
    ArrayList<String> Lstproveedores=new ArrayList<String>();
    ArrayList<String> rfcs=new ArrayList<String>();
    String sendFactura="", respuestaComprobacion="";
    String factura="", JSON_Send="";
    String n_partida="";
    String orden_compra="", bandera_folio="";

    TextView Titulo, partida1, partida2, partida3, partida4, partida5, txtProveedor1, txtRfc1, txtFechaEmision1, textview1, txtMonto1, txtHM1
            , txtProveedor2, txtRfc2, txtFechaEmision2, textview2, txtMonto2, txtHM2
            , txtProveedor3, txtRfc3, txtFechaEmision3, textview3, txtMonto3, txtHM3
            , txtProveedor4, txtRfc4, txtFechaEmision4, textview4, txtMonto4, txtHM4
            , txtProveedor5, txtRfc5, txtFechaEmision5, textview5, txtMonto5, txtHM5;

    AutoCompleteTextView autoCompleteProveedor1, autoCompleteProveedor2, autoCompleteProveedor3, autoCompleteProveedor4, autoCompleteProveedor5;

    EditText edtRfc1, edtMonto1, edtHoraMinuto1,
            edtRfc2, edtMonto2, edtHoraMinuto2,
            edtRfc3, edtMonto3, edtHoraMinuto3,
            edtRfc4, edtMonto4, edtHoraMinuto4,
            edtRfc5, edtMonto5, edtHoraMinuto5;

    Button btn1, btn2, btn3, btn4, btn5, btnSalir, btn_enviar;

    private String[] proveedores;

    ProgressDialog pd; // Mensaje de progreso en sincronizacion.

    ProgressDialog pdSendComprobaciones;// Mensaje de progreso en sincronizacion Comprobaciones.



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final MyDBHandler dbHandler = new MyDBHandler(this, null, null, DATABASE_VERSION);
        dbHandler.checkDBStatus(); // Tiene como fin forzar la creacion de la base de datos.

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comprobacion_partidas);

        final String usuarioLog = dbHandler.ultimoUsuarioRegistrado();
        String TipoUsuario = dbHandler.tipoUsuario(usuarioLog);
        String ruta = "";

        if (!CheckNetwork.isInternetAvailable(ComprobacionPartidas.this)) //returns true if internet available
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
                    explicit_intent = new Intent(ComprobacionPartidas.this, NavDrawerActivity.class);
                    explicit_intent.putExtra("usuario", usuarioLog);
                    startActivity(explicit_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                    finish();
                }
            });
            //Muestra la ventana esperando respuesta.
            dialog1.show();
        }
        else{
            if (TipoUsuario.toString().equals("A")) {
                ruta = dbHandler.num_ruta(usuarioLog);
            } else {
                ruta = dbHandler.rutaSeleccionada();
                Log.d("Ruta seleccionada = ",ruta);
            }

            //dbHandler.borraReserva();

            new ComprobacionPartidas.JsonTask().execute();

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
                            ArrayAdapter adapterProveedores = new ArrayAdapter(ComprobacionPartidas.this, android.R.layout.simple_list_item_1, Lstproveedores);

                            Intent intent=getIntent();
                            Bundle extras = intent.getExtras();
                            orden_compra = (String)extras.get("oc");
                            n_partida=(String)extras.get("infox");


                            titulo.setText("OC: "+orden_compra);
                            partida1.setText(n_partida);
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
                            if(n_partida.length() > 0){
                                partida1.setText(n_partida);
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
                                    final MyDBHandler dbHandler = new MyDBHandler(ComprobacionPartidas.this, null, null, 1);
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
                                        /*String facci2=factura.trim();
                                        String msje="";
                                        String id_reserv="";
                                        if(facci2.equals("E3306")){
                                            msje="ERROR\nOcurrió un error al conectarse a la base de datos";
                                            getMensjeError(msje,usuarioLog);
                                        }else if(facci2.equals("E404")){
                                            msje="ERROR\nOcurrió un error al ejecutar la consulta";
                                            getMensjeError(msje,usuarioLog);
                                        }else if(facci2.equals("E120")){
                                            msje="ERROR\nNo se encontró la factura con los datos ingresados\nVerifique los datos e intente de nuevo";
                                            getMensjeError(msje,usuarioLog);
                                        }
                                        else{
                                            id_reserv=facci2;
                                            //Se saca la fecha de registro
                                            Calendar cal = Calendar.getInstance();
                                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                            String fechaRegistro = sdf.format(cal.getTime());

                                            //Se obtiene le usuario logeado del metodo
                                            String usuario = dbHandler.ultimoUsuarioRegistrado();
                                            String partid= n_partida.substring(0,1);
                                            //Se prepara el JSON a enviar al servidor
                                            JSON_Send="[";
                                            JSON_Send+="{\"oc\":\""+orden_compra+"\",";
                                            JSON_Send+="\"partida\":\""+partid+"\",";
                                            JSON_Send+="\"id_reserva\":\""+id_reserv+"\",";
                                            JSON_Send+="\"usuario\":\""+usuario+"\",";
                                            JSON_Send+="\"fecha_registro\":\""+fechaRegistro+"\"}";
                                            JSON_Send+="]";
                                            Log.d("//+++++JSON+++++//",JSON_Send);
                                            int y=0;
                                            y=y+65;
                                            y++;
                                            y++;
                                            y++;
                                            //new ComprobacionPartidas.JsonTaskSendComprobaciones().execute();
                                            String resp="";
                                            resp=respuestaComprobacion.trim();
                                            Log.d("//+++++RESP-1+++++//",respuestaComprobacion);
                                            Log.d("//+++++RESP-2+++++//",resp);
                                            if(resp.equals("OK")){
                                                msje="Comprobación exitosa!\nPulse Aceptar para continuar.";
                                                getMensjeError(msje,usuarioLog);
                                            }else{
                                                getMensjeError(respuestaComprobacion,usuarioLog);
                                            }


                                        }*/


                                        ////////SE INICIALIZA LA PARTE DEL GUARDADO EN LA TABLA LOCAL

                                        //Se saca la fecha de registro
                                        /*Calendar cal = Calendar.getInstance();
                                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                        String fechaRegistro = sdf.format(cal.getTime());

                                        //Se obtiene le usuario logeado del metodo
                                        String usuario = dbHandler.ultimoUsuarioRegistrado();

                                        //Se obtiene el id_rserva del uuid
                                        id_reserva = dbHandler.idReserva(uuid);

                                        ///Se realiza la insercion para pasar a un metodo
                                        String sqlInsert = "INSERT INTO ct_comprobacion (uuid, usuario, fecha_registro, sincronizado, oc, partida, id_reserva) " +
                                                "VALUES ('"+uuid+"', '"+usuario+"', '"+fechaRegistro+"', '0', '"+orden_compra+"', '1', '"+id_reserva+"');";


                                        dbHandler.insertaComprobacion(sqlInsert);

                                        ///Se obtiene el total_pagado de la oc para despues ser actualizada
                                        total_pagado = 0.0;
                                        total_pagado = dbHandler.totalComprobado(1, orden_compra);

                                        ///Se obtiene el precio unitario de l oc
                                        precio_unitario = 0.0;
                                        precio_unitario = dbHandler.precioUnitarioOC(1, orden_compra);

                                        ///Sumamos la cantidad de la factura + el total_pagado
                                        monto_actualizar = 0.0;
                                        Double monto = Double.parseDouble(monto1);
                                        monto_actualizar = total_pagado+monto;

                                        ///Si el precio unitario es menor al monto a actualizar
                                        if(precio_unitario < monto_actualizar){
                                            Double monto_actualizar_qr = precio_unitario;
                                            ///Se actualiza el campo total comprobado de la tabla cm_ordenes_compra
                                            String sqlUpdate = "UPDATE cm_ordenes_compra SET total_comprobado = '"+monto_actualizar_qr+"' WHERE " +
                                                    "num_orden = '"+orden_compra+"' AND num_partida_oc = '1';";
                                            dbHandler.actualizaComprobacion(sqlUpdate);
                                        }
                                        ///Si el precio unitario es menor al monto a actualizar
                                        else{
                                            Double monto_actualizar_qr = monto_actualizar;
                                            ///Se actualiza el campo total comprobado de la tabla cm_ordenes_compra
                                            String sqlUpdate = "UPDATE cm_ordenes_compra SET total_comprobado = '"+monto_actualizar_qr+"' WHERE " +
                                                    "num_orden = '"+orden_compra+"' AND num_partida_oc = '1';";
                                            dbHandler.actualizaComprobacion(sqlUpdate);
                                        }*/

                                        ///Fin se realiza la insercion para pasar a un metodo

                                        autoCompleteProveedor1.setText("");
                                        //edtRfc1.setText("");
                                        edtMonto1.setText("");
                                        textview1.setText("_/_/_");
                                        edtHoraMinuto1.setText("");
                                    }








                                    //Toast toast = Toast.makeText(getApplicationContext(), "Se guardo con exito la(s) comprobacion(es)", Toast.LENGTH_SHORT);
                                    //toast.show();

                                    /*if(CheckNetwork.isInternetAvailable(ComprobacionPartidas.this)) //returns true if internet available
                                    {
                                        new ComprobacionPartidas.JsonTaskSendComprobaciones(dbHandler).execute(); // Envia todos los prospectos pendientes x transmitir al sevidor. No los borra local

                                        // Se tienen que volver a descargar los catalogos del sistema.
                                        //dbHandler.resetOc(); // Elimina la información de las oc
                                        //new JsonTaskOc(dbHandler).execute(); // Descarga de la base de datos las oc
                                    }*/

                                    /*String usuario = dbHandler.ultimoUsuarioRegistrado();

                                    Intent explicit_intent;
                                    explicit_intent = new Intent(ComprobacionPartidas.this, Comprobacion.class);
                                    explicit_intent.putExtra("usuario", usuario);
                                    startActivity(explicit_intent);
                                    return;*/


                                }
                            });

                            //Damos la opcion de salir
                            btnSalir.setOnClickListener(new View.OnClickListener(){
                                @Override
                                public void onClick(View v){
                                    final MyDBHandler dbHandler = new MyDBHandler(ComprobacionPartidas.this, null, null, 1);
                                    dbHandler.checkDBStatus(); // Tiene como fin forzar la creacion de la base de datos.

                                    String usuario = dbHandler.ultimoUsuarioRegistrado();

                                    Intent explicit_intent;
                                    explicit_intent = new Intent(ComprobacionPartidas.this, Comprobacion.class);
                                    explicit_intent.putExtra("usuario", usuario);
                                    startActivity(explicit_intent);
                                    return;
                                }
                            });



        }

        //proveedores = dbHandler.getProveedores(); // Metodo que se trae la lista de clientes de la tabla y la copia del array




    }

    //JSON OBTIENE PROVEEDORES

         class JsonTask extends AsyncTask<String, String, String> {

            protected void onPreExecute() {
                super.onPreExecute();

                pd = new ProgressDialog(ComprobacionPartidas.this);
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
                /*
                // Una vez obtenida la cadena JSon del segundo catalogo se lee linea por lina para insertar en la tabla
                try {
                    // Convierte el string con la informaciÑn de los clientes en un array JSON
                    JSONArray jsonArr_CtReserva = new JSONArray(JsonUrlCtReserva);


                for (int i = 0; i < jsonArr_CtReserva.length(); i++)
                {
                    JSONObject jsonObjCtReserva= jsonArr_CtReserva.getJSONObject(i);
                    //arrL.add(jsonObjCtReserva.getString("nombre_proveedor"));
                    //dbHandler.insertaCtReserva(jsonObjCtReserva);
                }
                } // Fin del Try
                catch (JSONException e) {
                    e.printStackTrace();
                } // fin del catch
                */

                //return JsonUrlCtReserva;
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
                        //Lstproveedores.add(jsonObjCtReserva.getString("nombre_proveedor"));
                        //rfcs.add(jsonObjCtReserva.getString("rfc"));
                        //dbHandler.insertaCtReserva(jsonObjCtReserva);
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
        } // Fin de la clase JsonTask

    //JSON BUSCA FACTURAS

    class BuscaFactura extends AsyncTask<String, String, String> {

        private String facci;
        public BuscaFactura(String s)
        {
            this.facci = s;
        }

        protected void onPreExecute() {
            super.onPreExecute();

            pd = new ProgressDialog(ComprobacionPartidas.this);
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
            final MyDBHandler dbHandler = new MyDBHandler(ComprobacionPartidas.this, null, null, DATABASE_VERSION);
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
                String partid= n_partida.substring(0,1);
                //Se prepara el JSON a enviar al servidor
                JSON_Send = "[";
                JSON_Send += "{\"oc\":\"" + orden_compra + "\",";
                JSON_Send += "\"partida\":\"" + partid + "\",";
                JSON_Send += "\"id_reserva\":\"" + id_reserv + "\",";
                JSON_Send += "\"usuario\":\"" + usuario + "\",";
                JSON_Send += "\"fecha_registro\":\"" + fechaRegistro + "\"}";
                JSON_Send += "]";
                Log.d("//+++++JSON+++++//",JSON_Send);
                // se envía el JSON a través de otro AsyncTask
                new ComprobacionPartidas.JsonTaskSendComprobaciones().execute();
            }


            super.onPostExecute(result);

            if (pd.isShowing()) {

                pd.dismiss();
                //reload();
            }

        }
    } // Fin de la clase JsonTask


// JSonTask que comprueba la factura

    class JsonTaskSendComprobaciones extends AsyncTask<String, String, String>
    {

        protected void onPreExecute()
        {
            super.onPreExecute();

            pdSendComprobaciones = new ProgressDialog(ComprobacionPartidas.this);
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
            String urlSendcomprobaciones = "http://www.sybrem.com.mx/adsnet/syncmovil/ReceptorComprobaciones.php?jsonCad="+URLEncoder.encode(JSON_Send);

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
            final MyDBHandler dbHandler = new MyDBHandler(ComprobacionPartidas.this, null, null, DATABASE_VERSION);
            dbHandler.checkDBStatus();
            final String usuarioLog = dbHandler.ultimoUsuarioRegistrado();
            String resp="";
            String msje="";
            resp=respuestaComprobacion.trim();
            Log.d("//+++++RESP-1+++++//",respuestaComprobacion);
            Log.d("//+++++RESP-2+++++//",resp);
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
    }

    //Metodo para refrescar el menu
    public void reload(){

        final MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);

        String usuario = dbHandler.ultimoUsuarioRegistrado();

        Intent explicit_intent;
        explicit_intent = new Intent(ComprobacionPartidas.this, Comprobacion.class);
        explicit_intent.putExtra("usuario", usuario);
        startActivity(explicit_intent);
        return;

    }
    public void getMensjeError(String msje, final String usuarioLog, final String bandera){
        //Crea ventana de alerta.
        AlertDialog.Builder dialog1 = new AlertDialog.Builder(ComprobacionPartidas.this);
        dialog1.setMessage(msje);
        //Establece el boton de Aceptar y que hacer si se selecciona.
        dialog1.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent explicit_intent;
                explicit_intent = new Intent(mx.com.sybrem.appbiochem.ComprobacionPartidas.this, Comprobacion.class);
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
        AlertDialog.Builder dialog1 = new AlertDialog.Builder(ComprobacionPartidas.this);
        dialog1.setMessage(msje);
        //Establece el boton de Aceptar y que hacer si se selecciona.
        dialog1.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent explicit_intent;
                explicit_intent = new Intent(mx.com.sybrem.appbiochem.ComprobacionPartidas.this, ComprobacionPartidas.class);
                explicit_intent.putExtra("usuario", usuarioLog);
                explicit_intent.putExtra("oc", orden_compra);
                explicit_intent.putExtra("infox",n_partida);
                explicit_intent.putExtra("band_folio",bandera);
                startActivity(explicit_intent);
                //finish();
            }
        });
        //Muestra la ventana esperando respuesta.
        dialog1.show();
    }

}
