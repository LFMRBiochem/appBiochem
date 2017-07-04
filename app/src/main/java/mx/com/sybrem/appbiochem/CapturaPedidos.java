package mx.com.sybrem.appbiochem;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URLEncoder;

import mx.com.sybrem.appbiochem.MyDBHandler;

public class CapturaPedidos extends AppCompatActivity{

    Spinner lstViewFamilia;
    Spinner lstViewSubtipo;
    Spinner lstViewTipoVenta;
    Spinner lstViewConducto;
    Spinner sp;

    private AutoCompleteTextView textClientes;

    AutoCompleteTextView cliente;
    EditText comentarios;

    private String ultimoUsuario = "";
    private String usuarioLogeado = "";

    //ARRAY PARA LOS CLIENTES
    // private String[] clientes = {"Atanacio Macias de Anda", "Fernando Lopez Partida", "Maria Guadalupe Luna", "Agricola el Vencedor"};
    private String[] clientes;

    private  static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "bladeTablet.db";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final MyDBHandler dbHandler = new MyDBHandler(this, null, null, DATABASE_VERSION);
        dbHandler.checkDBStatus(); // Tiene como fin forzar la creacion de la base de datos.

        clientes = dbHandler.getClientes(); // Metodo que se trae la lista de clientes de la tabla y la copia del array

        //Se obtiene el usuario logeado

        Intent intent=getIntent();
        Bundle extras = intent.getExtras();
        usuarioLogeado = (String)extras.get("usuario");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_captura_pedidos);
        Resources res = getResources();

        /*
        cmSync = (ImageButton)findViewById(R.id.btnSyncCat);
        cmSync.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                dbHandler.resetCatalogs();;
                new JsonTask(dbHandler).execute();
            }
        });

        // Inicializa boton de sincronizacion de pedidos al servidor
        cmSyncPed = (Button)findViewById(R.id.buttonSyncPed);
        cmSyncPed.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                new JsonTaskSender(dbHandler).execute();
            }
        });
        */

        ///PARTE PARA EL AUTOCOMPLETE DE LOS CLIENTES
        textClientes = (AutoCompleteTextView)findViewById(R.id.autoCompleteTextView);
        ArrayAdapter adapterClientes = new ArrayAdapter(this, android.R.layout.simple_list_item_1, clientes);
        textClientes.setAdapter(adapterClientes);

        ///PARA PONER LOS VALORES DEL LISTADO DE FAMILIA
        ArrayAdapter<CharSequence> adaptador = ArrayAdapter.createFromResource(this, R.array.valores_familia, android.R.layout.simple_spinner_item);
        lstViewFamilia = (Spinner)findViewById(R.id.lstViewFamilia);
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lstViewFamilia.setAdapter(adaptador);

        ///PARA PONER EL SUBTIPO DE VENTA
        ArrayAdapter<CharSequence> adaptador2 = ArrayAdapter.createFromResource(this, R.array.valores_sub_tipo, android.R.layout.simple_spinner_item);
        lstViewSubtipo = (Spinner) findViewById(R.id.lstViewSubtipo);
        adaptador2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lstViewSubtipo.setAdapter(adaptador2);

        ///PARA PONER LOS VALORES DEL TIPO DE VENTA
        ArrayAdapter<CharSequence> adaptador3 = ArrayAdapter.createFromResource(this, R.array.valores_tipo_venta, android.R.layout.simple_spinner_item);
        lstViewTipoVenta = (Spinner)findViewById(R.id.lstViewTipoVenta);
        adaptador3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lstViewTipoVenta.setAdapter(adaptador3);

        ///PARA PONER LOS VALORES DEL CONDUCTO
        ArrayAdapter<CharSequence> adaptador4= ArrayAdapter.createFromResource(this, R.array.valores_conductos, android.R.layout.simple_spinner_item);
        lstViewConducto = (Spinner)findViewById(R.id.lstViewConducto);
        adaptador4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lstViewConducto.setAdapter(adaptador4);

        ///PARA LA PARTE DE LOS COMENTARIOS
        comentarios = (EditText)findViewById(R.id.edtComentarios);

        Button btnSalir = (Button)findViewById(R.id.btnPedidosSalir);
        btnSalir.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                final MyDBHandler dbHandler = new MyDBHandler(CapturaPedidos.this, null, null, 1);
                dbHandler.checkDBStatus(); // Tiene como fin forzar la creacion de la base de datos.

                String usuario = dbHandler.ultimoUsuarioRegistrado();

                Intent explicit_intent;
                explicit_intent = new Intent(CapturaPedidos.this, NavDrawerActivity.class);
                explicit_intent.putExtra("usuario", usuario);
                startActivity(explicit_intent);
                return;
            }
        });

        Button btn = (Button)findViewById(R.id.btnPedidosPartidas);
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent explicit_intent;

                String VerificaTipoVenta = lstViewTipoVenta.getSelectedItem().toString();

                if(VerificaTipoVenta.toString().equals("Extendido")) {
                    explicit_intent = new Intent(CapturaPedidos.this, CapturaPedidoExtendido.class);
                    String clienteAuto = textClientes.getText().toString();
                    String familiaAuto = lstViewFamilia.getSelectedItem().toString();
                    String subtipoAuto = lstViewSubtipo.getSelectedItem().toString();
                    String tipoventaAuto = lstViewTipoVenta.getSelectedItem().toString();
                    String conductoAuto = lstViewConducto.getSelectedItem().toString();
                    String comentariosAuto = comentarios.getText().toString();

                    if(clienteAuto.toString().length() <= 0){
                        Toast toast = Toast.makeText(getApplicationContext(), "Capture el cliente", Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }
                    if(familiaAuto.toString().equals("Seleccione una familia...")){
                        Toast toast = Toast.makeText(getApplicationContext(), "Seleccione una familia", Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }
                    if(subtipoAuto.toString().equals("Seleccione un sub tipo...")){
                        Toast toast = Toast.makeText(getApplicationContext(), "Seleccione un subtipo", Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }
                    if(tipoventaAuto.toString().equals("Tipo de Venta...")){
                        Toast toast = Toast.makeText(getApplicationContext(), "Seleccione un tipo de venta", Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }
                    if(conductoAuto.toString().equals("Conducto...")){
                        Toast toast = Toast.makeText(getApplicationContext(), "Seleccione un conducto", Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }
                    if(comentariosAuto.toString().length() <= 0){
                        Toast toast = Toast.makeText(getApplicationContext(), "Escriba un comentario", Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }
                    if(familiaAuto.toString().equals("Agricola") && tipoventaAuto.toString().equals("Mano")){
                        Toast toast = Toast.makeText(getApplicationContext(), "No hay pedidos de mano en agricola", Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }
                    if(familiaAuto.toString().equals("Agricola") && tipoventaAuto.toString().equals("Extendido")){
                        Toast toast = Toast.makeText(getApplicationContext(), "No hay pedidos extendidos en agricola", Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }
                    if(familiaAuto.toString().equals("Veterinaria") && subtipoAuto.toString().equals("Paq. Granos")){
                        Toast toast = Toast.makeText(getApplicationContext(), "No hay paquete granos en veterinaria", Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }
                    if(subtipoAuto.toString().equals("Muestras Gratis") && tipoventaAuto.toString().equals("Almacen")){
                        Toast toast = Toast.makeText(getApplicationContext(), "No hay muestras gratis en almacen", Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }
                    if(familiaAuto.toString().equals("Agricola") && subtipoAuto.toString().equals("Muestras Gratis")){
                        Toast toast = Toast.makeText(getApplicationContext(), "No hay muestras gratis en agricola", Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }
                    if(subtipoAuto.toString().equals("Paq. Granos")){
                        String cve_cliente = "";
                        String punto = "";
                        String[] separated = clienteAuto.split("-");
                        cve_cliente = separated[0];

                        String[] separated2 = cve_cliente.split(".");
                        punto = separated[1];

                        if(punto.toString().equals("")){
                            Toast toast = Toast.makeText(getApplicationContext(), "El cliente que seleccionaste no es para paquete granos", Toast.LENGTH_SHORT);
                            toast.show();
                            return;
                        }

                    }

                    explicit_intent.putExtra("cliente", clienteAuto);
                    explicit_intent.putExtra("familia", familiaAuto);
                    explicit_intent.putExtra("subtipo", subtipoAuto);
                    explicit_intent.putExtra("tipoventa", tipoventaAuto);
                    explicit_intent.putExtra("conducto", conductoAuto);
                    explicit_intent.putExtra("comentarios", comentariosAuto);


                    startActivity(explicit_intent);
                }

                else {
                    explicit_intent = new Intent(CapturaPedidos.this, CapturaPedidos2.class);
                    String clienteAuto = textClientes.getText().toString();
                    String familiaAuto = lstViewFamilia.getSelectedItem().toString();
                    String subtipoAuto = lstViewSubtipo.getSelectedItem().toString();
                    String tipoventaAuto = lstViewTipoVenta.getSelectedItem().toString();
                    String conductoAuto = lstViewConducto.getSelectedItem().toString();
                    String comentariosAuto = comentarios.getText().toString();

                    if(clienteAuto.toString().length() <= 0){
                        Toast toast = Toast.makeText(getApplicationContext(), "Capture el cliente", Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }
                    if(familiaAuto.toString().equals("Seleccione una familia...")){
                        Toast toast = Toast.makeText(getApplicationContext(), "Seleccione una familia", Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }
                    if(subtipoAuto.toString().equals("Seleccione un sub tipo...")){
                        Toast toast = Toast.makeText(getApplicationContext(), "Seleccione un subtipo", Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }
                    if(tipoventaAuto.toString().equals("Tipo de Venta...")){
                        Toast toast = Toast.makeText(getApplicationContext(), "Seleccione un tipo de venta", Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }
                    if(conductoAuto.toString().equals("Conducto...")){
                        Toast toast = Toast.makeText(getApplicationContext(), "Seleccione un conducto", Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }
                    if(comentariosAuto.toString().length() <= 0){
                        Toast toast = Toast.makeText(getApplicationContext(), "Escriba un comentario", Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }
                    if(familiaAuto.toString().equals("Agricola") && tipoventaAuto.toString().equals("Mano")){
                        Toast toast = Toast.makeText(getApplicationContext(), "No hay pedidos de mano en agricola", Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }
                    if(familiaAuto.toString().equals("Agricola") && tipoventaAuto.toString().equals("Extendido")){
                        Toast toast = Toast.makeText(getApplicationContext(), "No hay pedidos extendidos en agricola", Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }
                    if(familiaAuto.toString().equals("Veterinaria") && subtipoAuto.toString().equals("Paq. Granos")){
                        Toast toast = Toast.makeText(getApplicationContext(), "No hay paquete granos en veterinaria", Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }
                    if(familiaAuto.toString().equals("Agricola") && subtipoAuto.toString().equals("Muestras Gratis")){
                        Toast toast = Toast.makeText(getApplicationContext(), "No hay muestras gratis en agricola", Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }
                    if(subtipoAuto.toString().equals("Paq. Granos")){
                        String cve_cliente = "";
                        String punto = "";
                        String[] separated = clienteAuto.split("-");
                        cve_cliente = separated[0];

                        String[] separated2 = cve_cliente.split(".");
                        punto = separated[1];

                        if(punto.toString().equals("")){
                            Toast toast = Toast.makeText(getApplicationContext(), "El cliente que seleccionaste no es para paquete granos", Toast.LENGTH_SHORT);
                            toast.show();
                            return;
                        }

                    }

                    explicit_intent.putExtra("cliente", clienteAuto);
                    explicit_intent.putExtra("familia", familiaAuto);
                    explicit_intent.putExtra("subtipo", subtipoAuto);
                    explicit_intent.putExtra("tipoventa", tipoventaAuto);
                    explicit_intent.putExtra("conducto", conductoAuto);
                    explicit_intent.putExtra("comentarios", comentariosAuto);

                    startActivity(explicit_intent);
                }
            }
        });

    }

} // Fin de la clase CapturaPedidos

