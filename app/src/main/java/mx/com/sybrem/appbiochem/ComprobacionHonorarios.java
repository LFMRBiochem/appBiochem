package mx.com.sybrem.appbiochem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import devazt.devazt.networking.HttpClient;
import devazt.devazt.networking.OnHttpRequestComplete;
import devazt.devazt.networking.Response;

public class ComprobacionHonorarios extends AppCompatActivity {

    ListView lv;
    ArrayAdapter<String> adapter;
    private String[] oc;
    Button btnSalir;
    ProgressDialog pd;
    TextView encabezao;

    private  static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "bladeTablet.db";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comprobacion_honorarios);

        pd = new ProgressDialog(ComprobacionHonorarios.this);
        pd.setTitle("Conectando a servidor Biochem...");
        pd.setMessage("Cargando comprobaciones, espere...");
        pd.setCancelable(false);
        pd.show();
        final MyDBHandler dbHandler = new MyDBHandler(this, null, null, DATABASE_VERSION);
        dbHandler.checkDBStatus(); // Tiene como fin forzar la creacion de la base de datos.
        final String usuarioLog = dbHandler.ultimoUsuarioRegistrado();
        String TipoUsuario = dbHandler.tipoUsuario(usuarioLog);
        String ruta = "";

        if (!CheckNetwork.isInternetAvailable(ComprobacionHonorarios.this)) //returns true if internet available
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
                    explicit_intent = new Intent(ComprobacionHonorarios.this, NavDrawerActivity.class);
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

            HttpClient cliente = new HttpClient(new OnHttpRequestComplete() {
                @Override
                public void onComplete(Response status) {
                    if(status.isSuccess()){

                        Gson gson =new GsonBuilder().create();
                        try{
                            if (pd.isShowing()) {
                                pd.dismiss();
                            }
                            JSONObject jsono = new JSONObject(status.getResult());
                            JSONArray jsonArray=jsono.getJSONArray("honorarios");
                            final ArrayList<OrdenesHonorarios> ordenes= new ArrayList<OrdenesHonorarios>();
                            final ArrayList<String> ordenes_compra=new ArrayList<String>();
                            final ArrayList<String> info_partida=new ArrayList<String>();
                            String nom_agente="", cuenta_contable_agente="", saldo_x_comprobar="", saldo_x_comprobar_total="";
                            for(int i=0; i<jsonArray.length();i++) {
                                String orden = jsonArray.getString(i);
                                OrdenesHonorarios or = gson.fromJson(orden, OrdenesHonorarios.class);
                                ordenes.add(or);
                                ordenes_compra.add((i+1)+".- Concepto: " + or.getConcepto());//+ "\nMonto: $"+String.format("%.2f", Double.parseDouble(or.getprecio_unitario()))+"\nTotal Comprobado: $"+String.format("%.2f",Double.parseDouble(or.gettotal_comprobado()))+"\nTotal por comprobar: $"+String.format("%.2f",Double.parseDouble(or.getPor_comprobar())));
                                info_partida.add(or.getId_cuenta());
                                nom_agente=or.getAgente();
                                cuenta_contable_agente=or.getCuenta_contable();
                                saldo_x_comprobar=or.getPor_comprobar();
                                saldo_x_comprobar_total=or.getSaldo_total();
                            }
                            encabezao = (TextView) findViewById(R.id.AgentilloH);
                            encabezao.setText("Agente: "+nom_agente+"\nCuenta contable: "+cuenta_contable_agente+"\nSaldo por comprobar total: $"+saldo_x_comprobar_total+"\n\nSaldo por comprobar de HONORARIOS: $"+saldo_x_comprobar);
                            lv = (ListView) findViewById(R.id.LstCH);
                            adapter = new ArrayAdapter(ComprobacionHonorarios.this, android.R.layout.simple_list_item_1, ordenes_compra);
                            lv.setAdapter(adapter);
                            //Se verifica a que orden de compra se le da clic
                            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                                    int posicion = position;
                                    String itemValue = (String) lv.getItemAtPosition(posicion);

                                    String info="", concept="";
                                    for(int j=0;j<info_partida.size();j++){
                                        if(j==posicion) {
                                            info=info_partida.get(posicion);
                                            concept=ordenes_compra.get(posicion);
                                            break;
                                        }
                                    }
                                    Intent explicit_intent;
                                    explicit_intent = new Intent(ComprobacionHonorarios.this, ComprobacionHonorariosPartidas.class);
                                    explicit_intent.putExtra("infox",info);
                                    explicit_intent.putExtra("concepto",concept);
                                    explicit_intent.putExtra("band_folio","8086");
                                    startActivity(explicit_intent);

                                }
                            });
                            //Damos la opcion de salir
                            Button btnSalir = (Button) findViewById(R.id.btnSalir);
                            btnSalir.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    final MyDBHandler dbHandler = new MyDBHandler(ComprobacionHonorarios.this, null, null, 1);
                                    dbHandler.checkDBStatus(); // Tiene como fin forzar la creacion de la base de datos.

                                    String usuario = dbHandler.ultimoUsuarioRegistrado();

                                    Intent explicit_intent;
                                    explicit_intent = new Intent(ComprobacionHonorarios.this, MenuComprobaciones.class);
                                    explicit_intent.putExtra("usuario", usuario);
                                    startActivity(explicit_intent);
                                    return;
                                }
                            });
                        }catch(Exception e){
                            //Damos la opcion de salir
                            Button btnSalir = (Button) findViewById(R.id.btnSalir);
                            btnSalir.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    final MyDBHandler dbHandler = new MyDBHandler(ComprobacionHonorarios.this, null, null, 1);
                                    dbHandler.checkDBStatus(); // Tiene como fin forzar la creacion de la base de datos.

                                    String usuario = dbHandler.ultimoUsuarioRegistrado();

                                    Intent explicit_intent;
                                    explicit_intent = new Intent(ComprobacionHonorarios.this, MenuComprobaciones.class);
                                    explicit_intent.putExtra("usuario", usuario);
                                    startActivity(explicit_intent);
                                    return;
                                }
                            });
                            Toast.makeText(ComprobacionHonorarios.this,"Error desconocido",Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }else{
                        Toast.makeText(ComprobacionHonorarios.this,"Ocurrió un error verifique la conexión a internet",Toast.LENGTH_LONG).show();
                    }
                }
            });
            String urlCmOC = "http://www.sybrem.com.mx/adsnet/syncmovil/SyncMovil.php?ruta=" + ruta + "&tabla=ocH";
            cliente.excecute(urlCmOC);

        }



        }
}
