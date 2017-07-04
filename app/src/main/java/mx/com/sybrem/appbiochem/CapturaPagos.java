package mx.com.sybrem.appbiochem;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static android.R.attr.maxDate;

public class CapturaPagos extends AppCompatActivity {

    AutoCompleteTextView autoCompleteTextView;
    Spinner lstViewFormaPago;
    Spinner lstViewPersona;
    Spinner lstViewDocumento;
    Spinner lstViewBancoDeposito;
    Spinner lstViewBancoEmisor;
    private String[] clientes;
    private String[] tiposPago;
    private String[] bancosClientes;
    private String[] bancosDepositos;
    private String[] documentosRespaldos;
    private String[] documentosDepositos;
    EditText edtcomentarios;
    EditText edtmonto;
    EditText edtrecibo;
    EditText edtreferencia;
    EditText edtcuenta;
    EditText edtcheque;
    TextView txtfecha;

    ProgressDialog pdSendPayment; // Mensaje de progreso en sincronizacion.

    TextView mensaje1;

    private  static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "bladeTablet.db";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_captura_pagos);

        final MyDBHandler dbHandler = new MyDBHandler(this, null, null, DATABASE_VERSION);
        dbHandler.checkDBStatus(); // Tiene como fin forzar la creacion de la base de datos.

        /* Uso de la clase LocationManager para obtener la localizacion del GPS */
        LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        CapturaPagos.Localizacion Local = new CapturaPagos.Localizacion();
        Local.setMainActivity(CapturaPagos.this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0,
                (LocationListener) Local);
        //////ESTA PARTE ES PARA LOS DATOS DE LA LATITUDE Y LONGITUDE///////////////////////////////

        //////ESTA PARTE ES PARA LOS DATOS DE LA LATITUDE Y LONGITUDE///////////////////////////////
        mensaje1 = (TextView)findViewById(R.id.mensaje_idPagos);
        mensaje1.setVisibility(View.GONE);

        clientes = dbHandler.getClientes2(); // Metodo que se trae la lista de clientes de la tabla y la copia del array
        tiposPago = dbHandler.getTiposPago(); // Metodo que se trae la lista de tipos pagos de la tabla y la copia del array
        bancosClientes = dbHandler.getBancosClientes(); // Metodo que se trae la lista de bancos de la tabla y la copia del array
        bancosDepositos = dbHandler.getBancoDeposito(); // Metodo que se trae la lista de bancos de la tabla y la copia del array
        documentosRespaldos = dbHandler.getDocumentosRespaldos(); // Metodo que se trae la lista de documentos respaldos de la tabla y la copia del array
        documentosDepositos = dbHandler.getDocumentosDeposito(); // Metodo que se trae la lista de documentos depositos de la tabla y la copia del array

        ///PARTE PARA EL AUTOCOMPLETE DE LOS CLIENTES
        final AutoCompleteTextView textClientes = (AutoCompleteTextView)findViewById(R.id.autoCompleteTextView);
        ArrayAdapter adapterClientes = new ArrayAdapter(this, android.R.layout.simple_list_item_1, clientes);
        textClientes.setAdapter(adapterClientes);

        ///PARA PONER LOS VALORES DEL LISTADO DE FORMA DE PAGO
        final Spinner lstViewFormaPago = (Spinner)findViewById(R.id.lstViewFormaPago);
        ArrayAdapter adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1, tiposPago);
        lstViewFormaPago.setAdapter(adaptador);

        ///PARA PONER LOS VALORES DEL LISTADO DE PERSONA DEPOSITO
        final Spinner lstViewPersona = (Spinner)findViewById(R.id.lstViewPersona);
        ArrayAdapter adaptador2 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, documentosDepositos);
        lstViewPersona.setAdapter(adaptador2);

        ///PARA PONER LOS VALORES DEL LISTADO DE DOCUMENTO QUE AMPARA
        final Spinner lstViewDocumento = (Spinner)findViewById(R.id.lstViewDocumento);
        ArrayAdapter adaptador3 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, documentosRespaldos);
        lstViewDocumento.setAdapter(adaptador3);

        ///PARA PONER LOS VALORES DEL BANCO EMISOR
        final Spinner lstViewBancoEmisor = (Spinner)findViewById(R.id.lstViewBancoEmisor);
        ArrayAdapter adaptador4 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, bancosClientes);
        lstViewBancoEmisor.setAdapter(adaptador4);

        ///PARA PONER LOS VALORES DEL BANCO DEPOSITO
        final Spinner lstViewBancoDeposito = (Spinner)findViewById(R.id.lstViewBancoDeposito);
        ArrayAdapter adaptador5 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, bancosDepositos);
        lstViewBancoDeposito.setAdapter(adaptador5);

        ///Para el boton de salir
        Button btnSalir = (Button)findViewById(R.id.btnPagosSalir);
        btnSalir.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                final MyDBHandler dbHandler = new MyDBHandler(CapturaPagos.this, null, null, 1);
                dbHandler.checkDBStatus(); // Tiene como fin forzar la creacion de la base de datos.

                String usuario = dbHandler.ultimoUsuarioRegistrado();

                Intent explicit_intent;
                explicit_intent = new Intent(CapturaPagos.this, NavDrawerActivity.class);
                explicit_intent.putExtra("usuario", usuario);
                startActivity(explicit_intent);
                return;
            }
        });

        ///Para el boton de guardar el pago
        Button btnSave = (Button)findViewById(R.id.btnSaved);
        btnSave.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                final MyDBHandler dbHandler = new MyDBHandler(CapturaPagos.this, null, null, 1);
                dbHandler.checkDBStatus(); // Tiene como fin forzar la creacion de la base de datos.

                //Se asigna el valor de los objetos a una variable de los campos comentarios y monto, recibo, referencia, cuenta, cheque y fecha
                edtcomentarios = (EditText)findViewById(R.id.edtComentarios);
                edtmonto = (EditText)findViewById(R.id.edtMonto);
                edtrecibo = (EditText)findViewById(R.id.edtReciboPago);
                edtreferencia = (EditText)findViewById(R.id.edtReferencia);
                edtcuenta = (EditText)findViewById(R.id.edtCuenta);
                edtcheque = (EditText)findViewById(R.id.edtCheque);
                txtfecha = (TextView)findViewById(R.id.textview1);

                ///Se extrae la informacion de los campos para ser guardados
                String cliente = textClientes.getText().toString();
                String formaPago = lstViewFormaPago.getSelectedItem().toString();
                String persona = lstViewPersona.getSelectedItem().toString();
                String documento = lstViewDocumento.getSelectedItem().toString();
                String bancoEmisor = lstViewBancoEmisor.getSelectedItem().toString();
                String bancoDeposito = lstViewBancoDeposito.getSelectedItem().toString();
                String comentarios = edtcomentarios.getText().toString();
                String monto = edtmonto.getText().toString();
                String recibo = edtrecibo.getText().toString();
                String referencia = edtreferencia.getText().toString();
                String cuenta = edtcuenta.getText().toString();
                String cheque = edtcheque.getText().toString();
                String fecha = txtfecha.getText().toString();

                //Obtenemos la localizacion
                String datosLocalizacion = mensaje1.getText().toString();
                //Partimos el string el primero es latitude y el segundo longitude
                String[] separatedLoc = datosLocalizacion.split(" ");
                String latitude = "";
                String longitude = "";
                latitude = separatedLoc[0];
                longitude = separatedLoc[1];

                /*
                if(latitude.toString().equals("GPS")){
                    Toast toast = Toast.makeText(getApplicationContext(), "Active el GPS de la tableta para poder guardar.", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                */

                ///Se valida que se haya seleccionado algun cliente
                if(cliente.length() == 0) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Seleccione un cliente", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }

                ///Se valida que se haya seleccionado algun cliente
                if(cliente.length() == 0) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Seleccione un cliente", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }

                ///Si no se escribio algun numero de recibo
                if(recibo.length() == 0) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Escriba el numero de recibo", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                if(referencia.length()==0){
                    Toast toast = Toast.makeText(getApplicationContext(), "Escriba la referencia", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }

                ///Si no se escribio el monto del pago
                if(monto.length() == 0) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Escriba el monto del pago", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }

                ///Si no se escribio algunos comentarios
                if(comentarios.length() == 0) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Escriba algunos comentarios", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }

                ///Si no se selecciono la fecha
                if(fecha.toString().equals("_/_/_")) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Debe seleccionar la fecha", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }


                ContentValues documentosEncabezado = new ContentValues();
                ContentValues documentosPartidas = new ContentValues();

                //Variables que faltan para el llenado
                String dbCveCompania = "019";
                String dbCveDocumento = "PAG";

                //Se obtiene el siguiente numero de pago local
                long dbNumDocumento = dbHandler.getSiguientePago();

                //Se saca la fecha de registro
                Calendar cal = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String fechaRegistro = sdf.format(cal.getTime());

                //Se divide el dato del cliente para obtener la clave del cliente
                String[] separatedCliente = cliente.split("-");
                String cve_cliente = separatedCliente[0];

                //Se obtiene el agente al cual esta asociado el cliente
                String usuario = dbHandler.ultimoUsuarioRegistrado();
                String cve_agente = "";
                String tipoUsuario = dbHandler.tipoUsuario(usuario);
                if(tipoUsuario.toString().equals("A")) {
                    cve_agente = usuario;
                }
                else{
                    cve_agente = dbHandler.agenteSeleccionado();
                }

                //Se divide el dato de persona
                String[] separatedPersona = persona.split("-");
                String cve_persona = separatedPersona[0];

                //Se divide el dato documento
                String[] separatedDocumento = documento.split("-");
                String cve_documento = separatedDocumento[0];

                //Se divide el dato forma de pago
                String[] separatedFormaPago = formaPago.split("-");
                String cve_forma_pago = separatedFormaPago[0];

                //Se divide el dato banco emisor
                String[] separatedBancoEmisor = bancoEmisor.split("-");
                String cve_banco_emisor = separatedBancoEmisor[0];

                //Se divide el dato banco deposito
                String[] separatedBancoDeposito = bancoDeposito.split("-");
                String cve_banco_deposito = separatedBancoDeposito[0];

                // Asigna el encabezado de la tabla vn_documentos_encabezado
                documentosEncabezado.put(dbHandler.COL_VNDOCUMENTOSENCABEZADO_CVECOMPANIA, dbCveCompania);
                documentosEncabezado.put(dbHandler.COL_VNDOCUMENTOSENCABEZADO_CVEDOCUMENTO, dbCveDocumento);
                documentosEncabezado.put(dbHandler.COL_VNDOCUMENTOSENCABEZADO_NUMDOCUMENTO, dbNumDocumento);
                documentosEncabezado.put(dbHandler.COL_VNDOCUMENTOSENCABEZADO_FECHADOCUMENTO, fecha);
                documentosEncabezado.put(dbHandler.COL_VNDOCUMENTOSENCABEZADO_FECHAREGISTRO, fechaRegistro);
                documentosEncabezado.put(dbHandler.COL_VNDOCUMENTOSENCABEZADO_TIPODOCUMENTO, "");
                documentosEncabezado.put(dbHandler.COL_VNDOCUMENTOSENCABEZADO_SUMA, monto);
                documentosEncabezado.put(dbHandler.COL_VNDOCUMENTOSENCABEZADO_DESCUENTO, "0");
                documentosEncabezado.put(dbHandler.COL_VNDOCUMENTOSENCABEZADO_SUBTOTAL, monto);
                documentosEncabezado.put(dbHandler.COL_VNDOCUMENTOSENCABEZADO_TOTAL, monto);
                documentosEncabezado.put(dbHandler.COL_VNDOCUMENTOSENCABEZADO_CVECLIENTE, cve_cliente);
                documentosEncabezado.put(dbHandler.COL_VNDOCUMENTOSENCABEZADO_CVEAGENTE, cve_agente);
                documentosEncabezado.put(dbHandler.COL_VNDOCUMENTOSENCABEZADO_CVEUSUARIO, usuario);
                documentosEncabezado.put(dbHandler.COL_VNDOCUMENTOSENCABEZADO_CVEMONEDA, "PES");
                documentosEncabezado.put(dbHandler.COL_VNDOCUMENTOSENCABEZADO_TIPOCAMBIO, "1");
                documentosEncabezado.put(dbHandler.COL_VNDOCUMENTOSENCABEZADO_COMENTARIOS, comentarios);
                documentosEncabezado.put(dbHandler.COL_VNDOCUMENTOSENCABEZADO_ESTATUS, "G");
                documentosEncabezado.put(dbHandler.COL_VNDOCUMENTOSENCABEZADO_TOTALPAGADO, monto);
                documentosEncabezado.put(dbHandler.COL_VNDOCUMENTOSENCABEZADO_RECIBOPAGO, recibo);
                documentosEncabezado.put(dbHandler.COL_VNDOCUMENTOSENCABEZADO_CONCILIADO, "0");
                documentosEncabezado.put(dbHandler.COL_VNDOCUMENTOSENCABEZADO_FECHACONCILIACION, "");
                documentosEncabezado.put(dbHandler.COL_VNDOCUMENTOSENCABEZADO_REFERENCIACONCILIACION, "");
                documentosEncabezado.put(dbHandler.COL_VNDOCUMENTOSENCABEZADO_EXISTEACLARACION, "");
                documentosEncabezado.put(dbHandler.COL_VNDOCUMENTOSENCABEZADO_PERSONADEPOSITO, cve_persona);
                documentosEncabezado.put(dbHandler.COL_VNDOCUMENTOSENCABEZADO_DOCTORESPALDO, cve_documento);
                documentosEncabezado.put(dbHandler.COL_VNDOCUMENTOSENCABEZADO_CVEUSUARIOCONCILIACION, "");
                documentosEncabezado.put(dbHandler.COL_VNDOCUMENTOSENCABEZADO_AUDITORIA, "SYNC");
                documentosEncabezado.put(dbHandler.COL_VNDOCUMENTOSENCABEZADO_COMENTARIOSAUDITORIA, "");
                documentosEncabezado.put(dbHandler.COL_VNDOCUMENTOSENCABEZADO_COMENTARIOSOTROS, "");
                documentosEncabezado.put(dbHandler.COL_VNDOCUMENTOSENCABEZADO_CVEUSUARIODESCONCILIACION, "");
                documentosEncabezado.put(dbHandler.COL_VNDOCUMENTOSENCABEZADO_COMENTARIOSDESCONCILIACION, "");
                documentosEncabezado.put(dbHandler.COL_VNDOCUMENTOSENCABEZADO_IEPS3, "");
                documentosEncabezado.put(dbHandler.COL_VNDOCUMENTOSENCABEZADO_IEPS35, "");
                documentosEncabezado.put(dbHandler.COL_VNDOCUMENTOSENCABEZADO_TOTALIEPS, "");
                documentosEncabezado.put(dbHandler.COL_VNDOCUMENTOSENCABEZADO_IEPS6, "");
                documentosEncabezado.put(dbHandler.COL_VNDOCUMENTOSENCABEZADO_IEPS7, "");
                documentosEncabezado.put(dbHandler.COL_VNDOCUMENTOSENCABEZADO_LATITUDE, latitude);
                documentosEncabezado.put(dbHandler.COL_VNDOCUMENTOSENCABEZADO_LONGITUDE, longitude);
                documentosEncabezado.put(dbHandler.COL_VNDOCUMENTOSENCABEZADO_TIPOCOBRANZA, "");

                // Asigna las patrtidas de la tabla vn_documentos_partidas
                documentosPartidas.put(dbHandler.COL_VNDOCUMENTOSPARTIDAS_CVECOMPANIA, dbCveCompania);
                documentosPartidas.put(dbHandler.COL_VNDOCUMENTOSPARTIDAS_CVEDOCUMENTO, dbCveDocumento);
                documentosPartidas.put(dbHandler.COL_VNDOCUMENTOSPARTIDAS_NUMDOCUMENTO, dbNumDocumento);
                documentosPartidas.put(dbHandler.COL_VNDOCUMENTOSPARTIDAS_NUMPARTIDA, "1");
                documentosPartidas.put(dbHandler.COL_VNDOCUMENTOSPARTIDAS_CVETIPOPAGO, cve_forma_pago);
                documentosPartidas.put(dbHandler.COL_VNDOCUMENTOSPARTIDAS_CVEBANCOEMISOR, cve_banco_emisor);
                documentosPartidas.put(dbHandler.COL_VNDOCUMENTOSPARTIDAS_CUENTACHEQUECLIENTE, cuenta);
                documentosPartidas.put(dbHandler.COL_VNDOCUMENTOSPARTIDAS_NUMCHEQUE, cheque);
                documentosPartidas.put(dbHandler.COL_VNDOCUMENTOSPARTIDAS_CVEBANCO, cve_banco_deposito);
                documentosPartidas.put(dbHandler.COL_VNDOCUMENTOSPARTIDAS_REFERENCIA, referencia);
                documentosPartidas.put(dbHandler.COL_VNDOCUMENTOSPARTIDAS_FECHA_BANCO, fecha);
                documentosPartidas.put(dbHandler.COL_VNDOCUMENTOSPARTIDAS_TOTAL, monto);

                if (dbHandler.registraPago(documentosEncabezado, documentosPartidas))
                {
                    Toast toast = Toast.makeText(getApplicationContext(), "El pago se ha guardado correctamente con el numero: "+ dbNumDocumento, Toast.LENGTH_SHORT);
                    toast.show();

                    if(CheckNetwork.isInternetAvailable(CapturaPagos.this)) //returns true if internet available
                    {
                        new CapturaPagos.JsonTaskSendPayment(dbHandler).execute(); // Envia todos los pagos pendientes x transmitir al sevidor. No los borra local
                    }

                    reload();
                }


            }
        }); ///Fin del metodo para guardar el pago

    }

    //Metodo para refrescar la pantalla cuando se guarda el pago
    public void reload(){

        final MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        dbHandler.checkDBStatus(); // Tiene como fin forzar la creacion de la base de datos.

            String usuario = dbHandler.ultimoUsuarioRegistrado();

            Intent explicit_intent;
            explicit_intent = new Intent(CapturaPagos.this, CapturaPagos.class);
            explicit_intent.putExtra("usuario", usuario);
            startActivity(explicit_intent);
            return;

    }

    //Metodo para mostrar el calendario
    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }


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

            pdSendPayment = new ProgressDialog(CapturaPagos.this);
            pdSendPayment.setTitle("Conectando a servidor Biochem...");
            pdSendPayment.setMessage("Mandando pagos guardados");
            pdSendPayment.setCancelable(false);
            pdSendPayment.show();
        }

        protected String doInBackground(String... params)
        {

            /*************************************************************
             * Bloque para envio de los pagos :                        *
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

    public void setLocation(Location loc) {
        //Obtener la direccion de la calle a partir de la latitud y la longitud
        if (loc.getLatitude() != 0.0 && loc.getLongitude() != 0.0) {
            try {
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> list = geocoder.getFromLocation(
                        loc.getLatitude(), loc.getLongitude(), 1);
                if (!list.isEmpty()) {

                    Address DirCalle = list.get(0);
                    /*
                    mensaje2.setText("Mi direccion es: \n"
                            + DirCalle.getAddressLine(0));
                    */
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public class Localizacion implements LocationListener {
        CapturaPagos mainActivity;

        public CapturaPagos getMainActivity() {
            return mainActivity;
        }

        public void setMainActivity(CapturaPagos mainActivity) {
            this.mainActivity = mainActivity;
        }

        @Override
        public void onLocationChanged(Location loc) {
            // Este metodo se ejecuta cada vez que el GPS recibe nuevas coordenadas
            // debido a la deteccion de un cambio de ubicacion

            loc.getLatitude();
            loc.getLongitude();

            String Text = ""+ loc.getLatitude() + " " + loc.getLongitude();
            mensaje1.setText(Text);
            //CapturaVisita.this.setLocation(loc);
        }

        @Override
        public void onProviderDisabled(String provider) {
            // Este metodo se ejecuta cuando el GPS es desactivado
            mensaje1.setText("GPS Desactivado");
        }

        @Override
        public void onProviderEnabled(String provider) {
            // Este metodo se ejecuta cuando el GPS es activado
            mensaje1.setText("GPS Activado");
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            // Este metodo se ejecuta cada vez que se detecta un cambio en el
            // status del proveedor de localizacion (GPS)
            // Los diferentes Status son:
            // OUT_OF_SERVICE -> Si el proveedor esta fuera de servicio
            // TEMPORARILY_UNAVAILABLE -> Temporalmente no disponible pero se
            // espera que este disponible en breve
            // AVAILABLE -> Disponible
        }

    }/* Fin de la clase localizacion */

}
