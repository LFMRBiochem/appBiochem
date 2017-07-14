package mx.com.sybrem.appbiochem;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
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
import java.net.URLEncoder;
import java.util.ArrayList;

import static android.R.attr.name;

public class GeneraAuxiliar extends AppCompatActivity {

    private  static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "bladeTablet.db";

    String fechaInicio="", ruta="";
    String fechaFin="", respuestaAuxiliar="";
    String respuestaComprobacion="", JSON_Send="";

    ProgressDialog pd;

    ArrayList<String> auxiliar;
    ArrayAdapter<String> adapter;

    TextView poliza,fecha_poliza, conceptoTV, cargoTV, abonoTV, saldoTV;
    TableLayout tl;
    TableRow tr;
    Button salir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genera_auxiliar);

        final MyDBHandler dbHandler = new MyDBHandler(this, null, null, DATABASE_VERSION);
        dbHandler.checkDBStatus(); // Tiene como fin forzar la creacion de la base de datos.

        final String usuarioLog = dbHandler.ultimoUsuarioRegistrado();
        String TipoUsuario = dbHandler.tipoUsuario(usuarioLog);
        //String ruta = "";
        salir=(Button)findViewById(R.id.btnSalir);
        salir.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                final MyDBHandler dbHandler = new MyDBHandler(GeneraAuxiliar.this, null, null, 1);
                dbHandler.checkDBStatus(); // Tiene como fin forzar la creacion de la base de datos.

                String usuario = dbHandler.ultimoUsuarioRegistrado();

                Intent explicit_intent;
                explicit_intent = new Intent(GeneraAuxiliar.this, AuxiliarMenu.class);
                explicit_intent.putExtra("usuario", usuario);
                startActivity(explicit_intent);
                return;
            }
        });
        if (!CheckNetwork.isInternetAvailable(GeneraAuxiliar.this)) //returns true if internet available
        {
            String msj = "Sin conexión a internet.\nSe requiere conexión a internet para generar el auxiliar contable";
            //Crea ventana de alerta.
            AlertDialog.Builder dialog1 = new AlertDialog.Builder(this);
            dialog1.setMessage(msj);
            //dialog1.setNegativeButton("",null);
            //Establece el boton de Aceptar y que hacer si se selecciona.
            dialog1.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent explicit_intent;
                    explicit_intent = new Intent(GeneraAuxiliar.this, NavDrawerActivity.class);
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
            Intent intent=getIntent();
            Bundle extras = intent.getExtras();
            fechaInicio=(String)extras.get("fi");
            fechaFin=(String)extras.get("ff");

            JSON_Send = "[";
            JSON_Send += "{\"ruta\":\"" + ruta + "\",";
            JSON_Send += "\"FI\":\"" + fechaInicio + "\",";
            JSON_Send += "\"FF\":\"" + fechaFin + "\"}";
            JSON_Send += "]";

            tl=(TableLayout)findViewById(R.id.tblPartidas);
            new GeneraAuxiliar.JsonTaskGeneraAuxiliar().execute();
        }

    }// ----------------------------------------------- Fin del onCreate() --------------------------------------------------------


    // JSonTask que comprueba la factura

    class JsonTaskGeneraAuxiliar extends AsyncTask<String, String, String>
    {

        protected void onPreExecute()
        {
            super.onPreExecute();

            pd = new ProgressDialog(GeneraAuxiliar.this);
            pd.setTitle("Conectando a servidor Biochem...");
            pd.setMessage("Generando auxiliar contable, espere...");
            pd.setCancelable(false);
            pd.show();
        }

        protected String doInBackground(String... params)
        {


            HttpURLConnection connection = null;
            BufferedReader reader = null;
            String JsonUrlAuxiliar = "";
            String urlSendcomprobaciones = "http://www.sybrem.com.mx/adsnet/syncmovil/AuxiliarMovil.php?jsonCad="+ URLEncoder.encode(JSON_Send);

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
                JsonUrlAuxiliar = buffer.toString();
                /* se obtiene la respuesta del servidor una vez se genera el auxiliar
                * en el archivo AuxiliarMovil.php
                */
                respuestaAuxiliar=JsonUrlAuxiliar.toString();

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

            return JsonUrlAuxiliar;

        }

        @Override
        protected void onPostExecute(String result)
        {
            final MyDBHandler dbHandler = new MyDBHandler(GeneraAuxiliar.this, null, null, DATABASE_VERSION);
            dbHandler.checkDBStatus();
            final String usuarioLog = dbHandler.ultimoUsuarioRegistrado();
            String resp="";
            String msje="";
            resp=respuestaAuxiliar.substring(0,2);
            Log.d("//+++++ERRNO:+++++//",resp);
            Log.d("//+++++RESP+++++//",respuestaAuxiliar);
            // Si el servidor responde con un error
            if(resp.equals("404")){// si la respuesta contiene un 404
                msje=respuestaAuxiliar.substring(2);
                getMensjeError(msje,usuarioLog);
            }
            else{// si la respuesta es el JSON del auxiliar
                try
                {
                    // Convierte el string con la información en un array JSON
                    JSONArray jsonAuxiliar = new JSONArray(respuestaAuxiliar);

                    for (int i = 0; i < jsonAuxiliar.length(); i++)
                    {
                        JSONObject obj = jsonAuxiliar.getJSONObject(i);
                        String folio_poliza=(!(obj.getString("folio_poliza").toString()).equals(""))?obj.getString("folio_poliza").toString():"-";
                        String fecha = (!(obj.getString("fecha").toString()).equals(""))?obj.getString("fecha").toString():"-";
                        String concepto = obj.getString("concepto").toString();
                        String cargo = obj.getString("cargos").toString();
                        String abono = obj.getString("abonos").toString();
                        String saldo = obj.getString("saldos").toString();

                        concepto=(concepto.length()>29)?concepto.substring(0,29):concepto;

                        tr = new TableRow(getApplicationContext());
                        tr.setLayoutParams(new TableLayout.LayoutParams(
                                TableLayout.LayoutParams.MATCH_PARENT,
                                TableLayout.LayoutParams.MATCH_PARENT));
                        //apartado donde se genera el numero de poliza
                        poliza = new TextView(getApplicationContext());
                        poliza.setText(folio_poliza);
                        poliza.setTextColor(Color.BLACK);
                        poliza.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
                        poliza.setPadding(5, 5, 5, 5);
                        tr.addView(poliza);

                        fecha_poliza=new TextView(getApplicationContext());
                        fecha_poliza.setText(fecha);
                        fecha_poliza.setTextColor(Color.BLACK);
                        fecha_poliza.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
                        fecha_poliza.setPadding(5, 5, 5, 5);
                        tr.addView(fecha_poliza);

                        conceptoTV=new TextView(getApplicationContext());
                        conceptoTV.setText(concepto);
                        conceptoTV.setTextColor(Color.BLACK);
                        conceptoTV.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
                        conceptoTV.setPadding(5, 5, 5, 5);
                        tr.addView(conceptoTV);

                        cargoTV=new TextView(getApplicationContext());
                        cargoTV.setText(cargo);
                        cargoTV.setTextColor(Color.BLACK);
                        cargoTV.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
                        cargoTV.setPadding(5, 5, 5, 5);
                        tr.addView(cargoTV);

                        abonoTV=new TextView(getApplicationContext());
                        abonoTV.setText(abono);
                        abonoTV.setTextColor(Color.BLACK);
                        abonoTV.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
                        abonoTV.setPadding(5, 5, 5, 5);
                        tr.addView(abonoTV);

                        saldoTV=new TextView(getApplicationContext());
                        saldoTV.setText(saldo);
                        saldoTV.setTextColor(Color.BLACK);
                        saldoTV.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
                        saldoTV.setPadding(5, 5, 5, 5);
                        tr.addView(saldoTV);

                        tl.addView(tr, new TableLayout.LayoutParams(
                                TableLayout.LayoutParams.MATCH_PARENT,
                                TableLayout.LayoutParams.MATCH_PARENT));

                    }
                } // Fin del Try
                catch (JSONException e)
                {
                    e.printStackTrace();
                } // fin del catch
            }
            super.onPostExecute(result);

            if (pd.isShowing())
            {
                pd.dismiss();
            }

        }
    }// ------------------------------------------------- fin del JsonTask -----------------------------------------------------

    public void getMensjeError(String msje, final String usuarioLog){
        //Crea ventana de alerta.
        AlertDialog.Builder dialog1 = new AlertDialog.Builder(GeneraAuxiliar.this);
        dialog1.setMessage(msje);
        //Establece el boton de Aceptar y que hacer si se selecciona.
        dialog1.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent explicit_intent;
                explicit_intent = new Intent(GeneraAuxiliar.this, AuxiliarMenu.class);
                explicit_intent.putExtra("usuario", usuarioLog);
                startActivity(explicit_intent);
                //finish();
            }
        });
        //Muestra la ventana esperando respuesta.
        dialog1.show();
    }

    @Override
    public void onBackPressed() {
        // do nothing
    }
}
