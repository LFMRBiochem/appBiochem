package mx.com.sybrem.appbiochem;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
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

public class AltaCliente extends AppCompatActivity {

    private  static final int DATABASE_VERSION = 2;
    //private static final String DATABASE_NAME = "bladeTablet.db";

    private String[] entidades,localidades, tiposContribuyente;
    private Spinner spEntidades,spLocalidades, spContribuyente;
    private EditText cliente,rfc,domicilio,colonia, cp, telefono,email;
    private Button btnSalir, btnGuardar;
    private TextView mensajeGps;

    ProgressDialog pdSendProspectos;

    //private String[] productos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alta_cliente);

        final MyDBHandler dbHandler = new MyDBHandler(this, null, null, DATABASE_VERSION);
        dbHandler.checkDBStatus();



        /* Uso de la clase LocationManager para obtener la localizacion del GPS */
        LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Localizacion Local =new Localizacion();
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

        mensajeGps = (TextView) findViewById(R.id.mensaje_gps);
        mensajeGps.setVisibility(View.GONE);
        //////ESTA PARTE ES PARA LOS DATOS DE LA LATITUDE Y LONGITUDE///////////////////////////////

        cliente=(EditText)findViewById(R.id.NCliente);
        rfc=(EditText)findViewById(R.id.RFC);
        domicilio=(EditText)findViewById(R.id.Domicilio);
        colonia=(EditText)findViewById(R.id.Colonia);
        cp=(EditText)findViewById(R.id.CP);
        telefono=(EditText)findViewById(R.id.phone);
        email=(EditText)findViewById(R.id.email);

        entidades=dbHandler.getEntidades();
        spEntidades = (Spinner)findViewById(R.id.Estado);
        ArrayAdapter<String> adapterEntidades=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,entidades);
        adapterEntidades.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spEntidades.setAdapter(adapterEntidades);

        spEntidades.setOnItemSelectedListener((new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int posicion = position;
                String itemValue = (String)spEntidades.getItemAtPosition(posicion);

                String[] separated = itemValue.split("-");
                String cve_entidad = "";
                cve_entidad = separated[0];

                localidades=dbHandler.getLocalidades(cve_entidad);
                spLocalidades = (Spinner)findViewById(R.id.Localidad);
                ArrayAdapter<String> adapterLocalidades=new ArrayAdapter<String>(AltaCliente.this,android.R.layout.simple_spinner_item,localidades);
                adapterLocalidades.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spLocalidades.setAdapter(adapterLocalidades);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        }
        ));

        tiposContribuyente=new String []{"Seleccione","Moral","Físico"};
        spContribuyente=(Spinner)findViewById(R.id.tipoContribuyente);
        ArrayAdapter<String> adapterContribuyente=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,tiposContribuyente);
        adapterContribuyente.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spContribuyente.setAdapter(adapterContribuyente);

        btnSalir=(Button)findViewById(R.id.Regresar);
        btnGuardar=(Button)findViewById(R.id.Guardar);

        btnSalir.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                final MyDBHandler dbHandler = new MyDBHandler(AltaCliente.this, null, null, 1);
                dbHandler.checkDBStatus(); // Tiene como fin forzar la creacion de la base de datos.

                String usuario = dbHandler.ultimoUsuarioRegistrado();

                Intent explicit_intent;
                explicit_intent = new Intent(AltaCliente.this, NavDrawerActivity.class);
                explicit_intent.putExtra("usuario", usuario);
                startActivity(explicit_intent);
                return;
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                final MyDBHandler dbHandler = new MyDBHandler(AltaCliente.this, null, null, 1);
                dbHandler.checkDBStatus(); // Tiene como fin forzar la creacion de la base de datos.

                //obtiene los valores necesarios para la validación
                String getCliente=cliente.getText().toString();
                String getRFC=rfc.getText().toString();
                String getDomicilio=domicilio.getText().toString();
                String getColonia=colonia.getText().toString();
                String getCP=cp.getText().toString();
                String getTelefono=telefono.getText().toString();
                String getEmail=email.getText().toString();
                String getTipoContribuyente=spContribuyente.getSelectedItem().toString();
                String getEstado=spEntidades.getSelectedItem().toString();
                String getLocalidad=spLocalidades.getSelectedItem().toString();

                String[] separated=getEstado.split("-");
                String cve_entidad="";
                cve_entidad=separated[0];

                String[] separated2=getLocalidad.split("-");
                String cve_localidad="";
                cve_localidad=separated2[0];

                //Obtenermos la localizacion
                String datosLocalizacion = mensajeGps.getText().toString();
                //Partimos el string el primero es latitude y el segundo longitude
                String[] separatedLoc = datosLocalizacion.split(" ");
                String latitude = "";
                String longitude = "";
                latitude = separatedLoc[0];
                longitude = separatedLoc[1];

                //valida los EditText
                if(getCliente.length()<=0){
                    Toast toast = Toast.makeText(getApplicationContext(), "Debe escribir el nombre del prospecto de cliente", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                if(getRFC.length()<=0){
                    Toast toast = Toast.makeText(getApplicationContext(), "Debe escribir el RFC del prospecto de cliente", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                if(getDomicilio.length()<=0){
                    Toast toast = Toast.makeText(getApplicationContext(), "Debe escribir el Domicilio del prospecto de cliente", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                if(getColonia.length()<=0){
                    Toast toast = Toast.makeText(getApplicationContext(), "Debe escribir la Colonia del prospecto de cliente", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                if(getTelefono.length()<=0){
                    Toast toast = Toast.makeText(getApplicationContext(), "Debe escribir el teléfono del prospecto de cliente", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                if(getEmail.length()<=0){
                    Toast toast = Toast.makeText(getApplicationContext(), "Debe escribir el Email del prospecto de cliente", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                if(getTipoContribuyente.equals("Seleccione")){
                    Toast toast = Toast.makeText(getApplicationContext(), "Seleccione el tipo de contribuyente", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }

                //Se saca la fecha de registro
                Calendar cal = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String fechaRegistroProspecto = sdf.format(cal.getTime());


                ContentValues tablaVnClientesSeg=new ContentValues();
                String CveCompania = "019";

                String usuarioReg = dbHandler.ultimoUsuarioRegistrado();

                //Se agregan los registros a la tabla vn_clientes_seguimiento de la base de datos local (SQLite)
                tablaVnClientesSeg.put(dbHandler.COL_VNCLIENTESSEGUIMIENTO_CVE_COMPANIA,CveCompania);
                /*tablaVnClientesSeg.put(dbHandler.COL_VNCLIENTESSEGUIMIENTO_CVE_CLIENTE,"");
                tablaVnClientesSeg.put(dbHandler.COL_VNCLIENTESSEGUIMIENTO_SOLICITUD,"");
                tablaVnClientesSeg.put(dbHandler.COL_VNCLIENTESSEGUIMIENTO_ATENCION_BIOCHEM,"");
                tablaVnClientesSeg.put(dbHandler.COL_VNCLIENTESSEGUIMIENTO_ENVIO_A,"");*/
                tablaVnClientesSeg.put(dbHandler.COL_VNCLIENTESSEGUIMIENTO_FECHA_REGISTRO,fechaRegistroProspecto);
                tablaVnClientesSeg.put(dbHandler.COL_VNCLIENTESSEGUIMIENTO_CVE_USUARIO_CAPTURA,usuarioReg);
                /*tablaVnClientesSeg.put(dbHandler.COL_VNCLIENTESSEGUIMIENTO_CC,"");
                tablaVnClientesSeg.put(dbHandler.COL_VNCLIENTESSEGUIMIENTO_CC2,"");*/
                tablaVnClientesSeg.put(dbHandler.COL_VNCLIENTESSEGUIMIENTO_NOMBRE_CLIENTE,getCliente);
                tablaVnClientesSeg.put(dbHandler.COL_VNCLIENTESSEGUIMIENTO_RFC,getRFC);
                tablaVnClientesSeg.put(dbHandler.COL_VNCLIENTESSEGUIMIENTO_TIPO_CONTRIBUYENTE,getTipoContribuyente);
                tablaVnClientesSeg.put(dbHandler.COL_VNCLIENTESSEGUIMIENTO_DOMICILIO,getDomicilio);
                tablaVnClientesSeg.put(dbHandler.COL_VNCLIENTESSEGUIMIENTO_COLONIA,getColonia);
                tablaVnClientesSeg.put(dbHandler.COL_VNCLIENTESSEGUIMIENTO_CP,getCP);
                tablaVnClientesSeg.put(dbHandler.COL_VNCLIENTESSEGUIMIENTO_TELEFONO,getTelefono);
                tablaVnClientesSeg.put(dbHandler.COL_VNCLIENTESSEGUIMIENTO_EMAIL,getEmail);
                tablaVnClientesSeg.put(dbHandler.COL_VNCLIENTESSEGUIMIENTO_ENTIDAD,getEstado);
                tablaVnClientesSeg.put(dbHandler.COL_VNCLIENTESSEGUIMIENTO_LOCALIDAD,getLocalidad);
                tablaVnClientesSeg.put(dbHandler.COL_VNCLIENTESSEGUIMIENTO_LATITUDE,latitude);
                tablaVnClientesSeg.put(dbHandler.COL_VNCLIENTESSEGUIMIENTO_LONGITUDE,longitude);
                tablaVnClientesSeg.put(dbHandler.COL_VNCLIENTESSEGUIMIENTO_SINCRONIZADO,"0");

                if(dbHandler.registraProspecto(tablaVnClientesSeg)){
                    Toast toast = Toast.makeText(getApplicationContext(), "El prospecto de cliente se ha guardado correctamente", Toast.LENGTH_SHORT);
                    toast.show();
                    if(CheckNetwork.isInternetAvailable(AltaCliente.this)) //returns true if internet available
                    {
                        new AltaCliente.JsonTaskSendProspectos(dbHandler).execute(); // Envia todos los prospectos pendientes x transmitir al sevidor. No los borra local
                    }
                    reload();
                }


            }
        });


    }

    //Metodo para refrescar la pantalla cuando se guarda el prospecto
    public void reload(){

        final MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        dbHandler.checkDBStatus(); // Tiene como fin forzar la creacion de la base de datos.

        String usuario = dbHandler.ultimoUsuarioRegistrado();

        Intent explicit_intent;
        explicit_intent = new Intent(AltaCliente.this, AltaCliente.class);
        explicit_intent.putExtra("usuario", usuario);
        startActivity(explicit_intent);
        return;

    }
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

            pdSendProspectos = new ProgressDialog(AltaCliente.this);
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

    public class Localizacion implements LocationListener {
        CapturaVisita mainActivity;

        public CapturaVisita getMainActivity() {
            return mainActivity;
        }

        public void setMainActivity(CapturaVisita mainActivity) {
            this.mainActivity = mainActivity;
        }

        @Override
        public void onLocationChanged(Location loc) {
            // Este metodo se ejecuta cada vez que el GPS recibe nuevas coordenadas
            // debido a la deteccion de un cambio de ubicacion

            loc.getLatitude();
            loc.getLongitude();

            String Text = ""+ loc.getLatitude() + " " + loc.getLongitude();
            mensajeGps.setText(Text);
            //CapturaVisita.this.setLocation(loc);
        }

        @Override
        public void onProviderDisabled(String provider) {
            // Este metodo se ejecuta cuando el GPS es desactivado
            mensajeGps.setText("GPS Desactivado");
        }

        @Override
        public void onProviderEnabled(String provider) {
            // Este metodo se ejecuta cuando el GPS es activado
            mensajeGps.setText("GPS Activado");
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
