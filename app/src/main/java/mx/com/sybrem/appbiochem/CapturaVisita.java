package mx.com.sybrem.appbiochem;

import android.Manifest;
import android.app.ProgressDialog;
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
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
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
import java.util.List;
import java.util.Locale;

public class CapturaVisita extends AppCompatActivity {

    private AutoCompleteTextView autoCompleteClientes;
    private String[] clientes;
    private Spinner sp;
    EditText comentarios;
    TextView mensaje1;
    private CheckBox ult_visita;
    String ultima_visita="";


    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "bladeTablet.db";

    ProgressDialog pdSendVisitas; // Mensaje de progreso en sincronizacion Visitas.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_captura_visita);

        String[] tiProb=new String[]{"Seleccione","POR ENVÍO","POR PRODUCTO","CUENTAS POR COBRAR","OTROS"};
        sp=(Spinner) findViewById(R.id.tipoProblema);
        ArrayAdapter<String> tProb=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,tiProb);
        sp.setAdapter(tProb);

        ArrayAdapter<CharSequence> adaptadorTiProb= ArrayAdapter.createFromResource(this, R.array.valores_tipo_problema, android.R.layout.simple_spinner_item);
        sp = (Spinner)findViewById(R.id.tipoProblema);
        adaptadorTiProb.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adaptadorTiProb);

        final MyDBHandler dbHandler = new MyDBHandler(this, null, null, DATABASE_VERSION);
        dbHandler.checkDBStatus(); // Tiene como fin forzar la creacion de la base de datos.

        //////ESTA PARTE ES PARA LOS DATOS DE LA LATITUDE Y LONGITUDE///////////////////////////////
        mensaje1 = (TextView) findViewById(R.id.mensaje_id);
        mensaje1.setVisibility(View.GONE);

        /* Uso de la clase LocationManager para obtener la localizacion del GPS */
        LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Localizacion Local = new Localizacion();
        Local.setMainActivity(CapturaVisita.this);
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

        clientes = dbHandler.getClientes(); // Metodo que se trae la lista de clientes de la tabla y la copia del array
///PARTE PARA EL AUTOCOMPLETE DE LOS CLIENTES
        autoCompleteClientes = (AutoCompleteTextView)findViewById(R.id.autoCompleteClientes);
        ArrayAdapter adapterClientes = new ArrayAdapter(this, android.R.layout.simple_list_item_1, clientes);
        autoCompleteClientes.setAdapter(adapterClientes);

        ult_visita=(CheckBox)findViewById(R.id.ult_visita);


        Button btnSalir = (Button)findViewById(R.id.btnPedidosSalir);
        btnSalir.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                final MyDBHandler dbHandler = new MyDBHandler(CapturaVisita.this, null, null, 1);
                dbHandler.checkDBStatus(); // Tiene como fin forzar la creacion de la base de datos.

                String usuario = dbHandler.ultimoUsuarioRegistrado();

                Intent explicit_intent;
                explicit_intent = new Intent(CapturaVisita.this, NavDrawerActivity.class);
                explicit_intent.putExtra("usuario", usuario);
                startActivity(explicit_intent);
                return;
            }
        });



        Button btnSaved = (Button)findViewById(R.id.btnSaved);
        btnSaved.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                final MyDBHandler dbHandler = new MyDBHandler(CapturaVisita.this, null, null, 1);
                dbHandler.checkDBStatus(); // Tiene como fin forzar la creacion de la base de datos.

                /*
                LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
                Criteria criteria = new Criteria();
                Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                */

                //Declaramos las variables y tomamos la informacion de los objetos
                comentarios = (EditText)findViewById(R.id.edtComentarios);
                autoCompleteClientes = (AutoCompleteTextView) findViewById(R.id.autoCompleteClientes);
                //sp=(Spinner)findViewById(R.id.tipoProblema);
                String comentariosDB = comentarios.getText().toString();
                String clienteDB = autoCompleteClientes.getText().toString();
                String tipoProblem=sp.getSelectedItem().toString();

                //Obtenermos la localizacion
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

                //Se valida que el usuario y los comentarios se hayan escrito
                if(clienteDB.toString().length() <= 0){
                    Toast toast = Toast.makeText(getApplicationContext(), "Debe seleccionar un cliente", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                if(comentariosDB.toString().length() <= 0){
                    Toast toast = Toast.makeText(getApplicationContext(), "Debe escribir los comentarios", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                if(ult_visita.isChecked()){
                    ultima_visita="1";
                }else{
                    ultima_visita="0";
                }

                //Dividimos la variable de cliente para tomar solamente la clave del cliente
                String[] separated = clienteDB.split("-");
                String cve_cliente = "";
                cve_cliente = separated[0];

                //Para realizar una variable con la fecha del registro
                Calendar cal = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String fechaRegistro = sdf.format(cal.getTime());

                //Se obtiene le usuario logeado del metodo
                String usuario = dbHandler.ultimoUsuarioRegistrado();

                Toast toast = Toast.makeText(getApplicationContext(), "Se inicializo el guardado de la visita Local", Toast.LENGTH_SHORT);
                toast.show();

                ///Se realiza la insercion para pasar a un metodo
                String sqlInsert = "insert into vn_registro_visita (cve_cliente, comentarios, fecha_registro, cve_usuario, cve_compania, latitude, " +
                        "longitude, sincronizado, tipo_problema, ultima_visita) VALUES ('"+ cve_cliente +"', '"+ comentariosDB +"', '"+ fechaRegistro +"', '"+ usuario +"', '019', '"+latitude+"', '"+longitude+"', '0','"+tipoProblem+"','"+ultima_visita+"')";


                dbHandler.insertaVisita(sqlInsert);
                ///Fin se realiza la insercion para pasar a un metodo

                comentarios.setText("");
                autoCompleteClientes.setText("");

                toast = Toast.makeText(getApplicationContext(), "Se finalizo el guardado de la visita Local", Toast.LENGTH_SHORT);
                toast.show();

                if(CheckNetwork.isInternetAvailable(CapturaVisita.this)) //returns true if internet available
                {
                    new CapturaVisita.JsonTaskSendVisitas(dbHandler).execute(); // Envia todas las visitas pendientes x transmitir al sevidor. No los borra local
                }

            }
        });

    }

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

            pdSendVisitas = new ProgressDialog(CapturaVisita.this);
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
            String urlSendvisitas = "http://www.sybrem.com.mx/adsnet/syncmovil/ReceptorMovilVisitasN.php?jsonCad="+ URLEncoder.encode(dbHandler.transmiteVisitas());

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

    /* Aqui empieza la Clase Localizacion */

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
