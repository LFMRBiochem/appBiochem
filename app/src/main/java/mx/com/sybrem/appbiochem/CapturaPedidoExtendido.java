package mx.com.sybrem.appbiochem;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
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

public class CapturaPedidoExtendido extends AppCompatActivity {

    TextView cliente, familia, subtipo, tipoventa, conducto, comentarios;

    ProgressDialog pd; // Mensaje de progreso en sincronizacion.
    private AutoCompleteTextView autoCompleteProducto1;
    private AutoCompleteTextView autoCompleteProducto2;
    private AutoCompleteTextView autoCompleteProducto3;
    private AutoCompleteTextView autoCompleteProducto4;
    private AutoCompleteTextView autoCompleteProducto5;
    private AutoCompleteTextView autoCompleteProducto6;
    private AutoCompleteTextView autoCompleteProducto7;
    private AutoCompleteTextView autoCompleteProducto8;
    private AutoCompleteTextView autoCompleteProducto9;
    private AutoCompleteTextView autoCompleteProducto10;
    private AutoCompleteTextView autoCompleteProducto11;
    private AutoCompleteTextView autoCompleteProducto12;
    private AutoCompleteTextView autoCompleteProducto13;
    private AutoCompleteTextView autoCompleteProducto14;
    private AutoCompleteTextView autoCompleteProducto15;
    private AutoCompleteTextView autoCompleteProducto16;
    private AutoCompleteTextView autoCompleteProducto17;
    private AutoCompleteTextView autoCompleteProducto18;
    private AutoCompleteTextView autoCompleteProducto19;
    private AutoCompleteTextView autoCompleteProducto20;
    private AutoCompleteTextView autoCompleteProducto21;
    private AutoCompleteTextView autoCompleteProducto22;
    private AutoCompleteTextView autoCompleteProducto23;
    private AutoCompleteTextView autoCompleteProducto24;
    private AutoCompleteTextView autoCompleteProducto25;
    private AutoCompleteTextView autoCompleteProducto26;
    private AutoCompleteTextView autoCompleteProducto27;
    private AutoCompleteTextView autoCompleteProducto28;
    private AutoCompleteTextView autoCompleteProducto29;
    private AutoCompleteTextView autoCompleteProducto30;
    private AutoCompleteTextView autoCompleteProducto31;
    private AutoCompleteTextView autoCompleteProducto32;
    private AutoCompleteTextView autoCompleteProducto33;
    private AutoCompleteTextView autoCompleteProducto34;
    private AutoCompleteTextView autoCompleteProducto35;
    private AutoCompleteTextView autoCompleteProducto36;
    private AutoCompleteTextView autoCompleteProducto37;
    private AutoCompleteTextView autoCompleteProducto38;
    private AutoCompleteTextView autoCompleteProducto39;
    private AutoCompleteTextView autoCompleteProducto40;
    private AutoCompleteTextView autoCompleteProducto41;
    private AutoCompleteTextView autoCompleteProducto42;
    private AutoCompleteTextView autoCompleteProducto43;
    private AutoCompleteTextView autoCompleteProducto44;
    private AutoCompleteTextView autoCompleteProducto45;

    EditText cant1,
            cant2,
            cant3,
            cant4,
            cant5,
            cant6,
            cant7,
            cant8,
            cant9,
            cant10,
            cant11,
            cant12,
            cant13,
            cant14,
            cant15,
            cant16,
            cant17,
            cant18,
            cant19,
            cant20,
            cant21,
            cant22,
            cant23,
            cant24,
            cant25,
            cant26,
            cant27,
            cant28,
            cant29,
            cant30,
            cant31,
            cant32,
            cant33,
            cant34,
            cant35,
            cant36,
            cant37,
            cant38,
            cant39,
            cant40,
            cant41,
            cant42,
            cant43,
            cant44,
            cant45;

    AutoCompleteTextView producto1, producto2,
            producto3,producto4,
            producto5,producto6,
            producto7,producto8,
            producto9,producto10,
            producto11,producto12,
            producto13,producto14,
            producto15,producto16,
            producto17,producto18,
            producto19,producto20,
            producto21,producto22,
            producto23,producto24,
            producto25,producto26,
            producto27,producto28,
            producto29,producto30,
            producto31, producto32,
            producto33,producto34,
            producto35,producto36,
            producto37,producto38,
            producto39,producto40,
            producto41, producto42,
            producto43,producto44,
            producto45;


    //ARRAY PARA LOS PRODUCTOS
    //private String[] productos = {"Shampoo Aloe Vera", "Garrametrin Forte", "Pulgaton Shampoo", "Control Rat"};
    private String[] productos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        dbHandler.checkDBStatus(); // Tiene como fin forzar la creacion de la base de datos.

        productos = dbHandler.getProductosVeterinarios(); // Metodo que se trae la lista de productos de la tabla y la copia del array

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_captura_pedido_extendido);

        // Se extraen los datos del otro activity CapturaPedidos2
        cliente = (TextView)findViewById(R.id.clientePartidas);
        familia = (TextView)findViewById(R.id.FamiliaPartidas);
        subtipo = (TextView)findViewById(R.id.SubTipoPartidas);
        tipoventa = (TextView)findViewById(R.id.TipoVentaPartidas);
        conducto = (TextView)findViewById(R.id.ConductoPartidas);
        comentarios = (TextView)findViewById(R.id.comentariosPartidas);

        Intent intent=getIntent();
        Bundle extras = intent.getExtras();
        final String clienteDato = (String)extras.get("cliente");
        final String familiaDato = (String)extras.get("familia");
        final String subtipoDato = (String)extras.get("subtipo");
        final String tipoventaDato = (String)extras.get("tipoventa");
        final String conductoDato = (String)extras.get("conducto");
        final String comentariosDato = (String)extras.get("comentarios");

        cliente.setText("" + clienteDato);
        familia.setText("" + familiaDato);
        subtipo.setText("" + subtipoDato);
        tipoventa.setText("" + tipoventaDato);
        conducto.setText("" + conductoDato);
        comentarios.setText("" + comentariosDato);
        // Fin se extraen los datos del otro activity CapturaPedidos2

        producto1 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto1);
        final TextView precio1 = (TextView) findViewById(R.id.precioProducto1);
        final TextView desc1 = (TextView) findViewById(R.id.porcentajeDescProducto1);
        cant1 = (EditText) findViewById(R.id.cantidadProducto1);
        final TextView total1 = (TextView) findViewById(R.id.totalProducto1);

        producto2 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto2);
        final TextView precio2 = (TextView) findViewById(R.id.precioProducto2);
        final TextView desc2 = (TextView) findViewById(R.id.porcentajeDescProducto2);
        cant2 = (EditText) findViewById(R.id.cantidadProducto2);
        final TextView total2 = (TextView) findViewById(R.id.totalProducto2);

        producto3 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto3);
        final TextView precio3 = (TextView) findViewById(R.id.precioProducto3);
        final TextView desc3 = (TextView) findViewById(R.id.porcentajeDescProducto3);
        cant3 = (EditText) findViewById(R.id.cantidadProducto3);
        final TextView total3 = (TextView) findViewById(R.id.totalProducto3);

        producto4 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto4);
        final TextView precio4 = (TextView) findViewById(R.id.precioProducto4);
        final TextView desc4 = (TextView) findViewById(R.id.porcentajeDescProducto4);
        cant4 = (EditText) findViewById(R.id.cantidadProducto4);
        final TextView total4 = (TextView) findViewById(R.id.totalProducto4);

        producto5 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto5);
        final TextView precio5 = (TextView) findViewById(R.id.precioProducto5);
        final TextView desc5 = (TextView) findViewById(R.id.porcentajeDescProducto5);
        cant5 = (EditText) findViewById(R.id.cantidadProducto5);
        final TextView total5 = (TextView) findViewById(R.id.totalProducto5);

        producto6 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto6);
        final TextView  precio6 = (TextView) findViewById(R.id.precioProducto6);
        final TextView desc6 = (TextView) findViewById(R.id.porcentajeDescProducto6);
        cant6 = (EditText) findViewById(R.id.cantidadProducto6);
        final TextView total6 = (TextView) findViewById(R.id.totalProducto6);

        producto7 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto7);
        final TextView precio7 = (TextView) findViewById(R.id.precioProducto7);
        final TextView desc7 = (TextView) findViewById(R.id.porcentajeDescProducto7);
        cant7 = (EditText) findViewById(R.id.cantidadProducto7);
        final TextView total7 = (TextView) findViewById(R.id.totalProducto7);

        producto8 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto8);
        final TextView precio8 = (TextView) findViewById(R.id.precioProducto8);
        final TextView desc8 = (TextView) findViewById(R.id.porcentajeDescProducto8);
        cant8 = (EditText) findViewById(R.id.cantidadProducto8);
        final TextView total8 = (TextView) findViewById(R.id.totalProducto8);

        producto9 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto9);
        final TextView precio9 = (TextView) findViewById(R.id.precioProducto9);
        final TextView desc9 = (TextView) findViewById(R.id.porcentajeDescProducto9);
        cant9 = (EditText) findViewById(R.id.cantidadProducto9);
        final TextView total9 = (TextView) findViewById(R.id.totalProducto9);

        producto10 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto10);
        final TextView precio10 = (TextView) findViewById(R.id.precioProducto10);
        final TextView desc10 = (TextView) findViewById(R.id.porcentajeDescProducto10);
        cant10 = (EditText) findViewById(R.id.cantidadProducto10);
        final TextView total10 = (TextView) findViewById(R.id.totalProducto10);

        producto11 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto11);
        final TextView  precio11 = (TextView) findViewById(R.id.precioProducto11);
        final TextView desc11 = (TextView) findViewById(R.id.porcentajeDescProducto11);
        cant11 = (EditText) findViewById(R.id.cantidadProducto11);
        final TextView total11 = (TextView) findViewById(R.id.totalProducto11);

        producto12 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto12);
        final TextView precio12 = (TextView) findViewById(R.id.precioProducto12);
        final TextView desc12 = (TextView) findViewById(R.id.porcentajeDescProducto12);
        cant12 = (EditText) findViewById(R.id.cantidadProducto12);
        final TextView total12 = (TextView) findViewById(R.id.totalProducto12);

        producto13 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto13);
        final TextView precio13 = (TextView) findViewById(R.id.precioProducto13);
        final TextView desc13 = (TextView) findViewById(R.id.porcentajeDescProducto13);
        cant13 = (EditText) findViewById(R.id.cantidadProducto13);
        final TextView total13 = (TextView) findViewById(R.id.totalProducto13);

        producto14 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto14);
        final TextView precio14 = (TextView) findViewById(R.id.precioProducto14);
        final TextView desc14 = (TextView) findViewById(R.id.porcentajeDescProducto14);
        cant14 = (EditText) findViewById(R.id.cantidadProducto14);
        final TextView total14 = (TextView) findViewById(R.id.totalProducto14);

        producto15 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto15);
        final TextView precio15 = (TextView) findViewById(R.id.precioProducto15);
        final TextView desc15 = (TextView) findViewById(R.id.porcentajeDescProducto15);
        cant15 = (EditText) findViewById(R.id.cantidadProducto15);
        final TextView total15 = (TextView) findViewById(R.id.totalProducto15);

        producto16 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto16);
        final TextView precio16 = (TextView) findViewById(R.id.precioProducto16);
        final TextView desc16 = (TextView) findViewById(R.id.porcentajeDescProducto16);
        cant16 = (EditText) findViewById(R.id.cantidadProducto16);
        final TextView total16 = (TextView) findViewById(R.id.totalProducto16);

        producto17 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto17);
        final TextView precio17 = (TextView) findViewById(R.id.precioProducto17);
        final TextView desc17 = (TextView) findViewById(R.id.porcentajeDescProducto17);
        cant17 = (EditText) findViewById(R.id.cantidadProducto17);
        final TextView total17 = (TextView) findViewById(R.id.totalProducto17);

        producto18 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto18);
        final TextView precio18 = (TextView) findViewById(R.id.precioProducto18);
        final TextView desc18 = (TextView) findViewById(R.id.porcentajeDescProducto18);
        cant18 = (EditText) findViewById(R.id.cantidadProducto18);
        final TextView total18 = (TextView) findViewById(R.id.totalProducto18);

        producto19 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto19);
        final TextView precio19 = (TextView) findViewById(R.id.precioProducto19);
        final TextView desc19 = (TextView) findViewById(R.id.porcentajeDescProducto19);
        cant19 = (EditText) findViewById(R.id.cantidadProducto19);
        final TextView total19 = (TextView) findViewById(R.id.totalProducto19);

        producto20 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto20);
        final TextView precio20 = (TextView) findViewById(R.id.precioProducto20);
        final TextView desc20 = (TextView) findViewById(R.id.porcentajeDescProducto20);
        cant20 = (EditText) findViewById(R.id.cantidadProducto20);
        final TextView total20 = (TextView) findViewById(R.id.totalProducto20);

        producto21 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto21);
        final TextView precio21 = (TextView) findViewById(R.id.precioProducto21);
        final TextView desc21 = (TextView) findViewById(R.id.porcentajeDescProducto21);
        cant21 = (EditText) findViewById(R.id.cantidadProducto21);
        final TextView total21 = (TextView) findViewById(R.id.totalProducto21);

        producto22 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto22);
        final TextView precio22 = (TextView) findViewById(R.id.precioProducto22);
        final TextView desc22 = (TextView) findViewById(R.id.porcentajeDescProducto22);
        cant22 = (EditText) findViewById(R.id.cantidadProducto22);
        final TextView total22 = (TextView) findViewById(R.id.totalProducto22);

        producto23 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto23);
        final TextView precio23 = (TextView) findViewById(R.id.precioProducto23);
        final TextView desc23 = (TextView) findViewById(R.id.porcentajeDescProducto23);
        cant23 = (EditText) findViewById(R.id.cantidadProducto23);
        final TextView total23 = (TextView) findViewById(R.id.totalProducto23);

        producto24 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto24);
        final TextView precio24 = (TextView) findViewById(R.id.precioProducto24);
        final TextView desc24 = (TextView) findViewById(R.id.porcentajeDescProducto24);
        cant24 = (EditText) findViewById(R.id.cantidadProducto24);
        final TextView total24 = (TextView) findViewById(R.id.totalProducto24);

        producto25 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto25);
        final TextView precio25 = (TextView) findViewById(R.id.precioProducto25);
        final TextView desc25 = (TextView) findViewById(R.id.porcentajeDescProducto25);
        cant25 = (EditText) findViewById(R.id.cantidadProducto25);
        final TextView total25 = (TextView) findViewById(R.id.totalProducto25);

        producto26 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto26);
        final TextView precio26 = (TextView) findViewById(R.id.precioProducto26);
        final TextView desc26 = (TextView) findViewById(R.id.porcentajeDescProducto26);
        cant26 = (EditText) findViewById(R.id.cantidadProducto26);
        final TextView total26 = (TextView) findViewById(R.id.totalProducto26);

        producto27 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto27);
        final TextView precio27 = (TextView) findViewById(R.id.precioProducto27);
        final TextView desc27 = (TextView) findViewById(R.id.porcentajeDescProducto27);
        cant27 = (EditText) findViewById(R.id.cantidadProducto27);
        final TextView total27 = (TextView) findViewById(R.id.totalProducto27);

        producto28 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto28);
        final TextView precio28 = (TextView) findViewById(R.id.precioProducto28);
        final TextView desc28 = (TextView) findViewById(R.id.porcentajeDescProducto28);
        cant28 = (EditText) findViewById(R.id.cantidadProducto28);
        final TextView total28 = (TextView) findViewById(R.id.totalProducto28);

        producto29 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto29);
        final TextView precio29 = (TextView) findViewById(R.id.precioProducto29);
        final TextView desc29 = (TextView) findViewById(R.id.porcentajeDescProducto29);
        cant29 = (EditText) findViewById(R.id.cantidadProducto29);
        final TextView total29 = (TextView) findViewById(R.id.totalProducto29);

        producto30 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto30);
        final TextView  precio30 = (TextView) findViewById(R.id.precioProducto30);
        final TextView desc30 = (TextView) findViewById(R.id.porcentajeDescProducto30);
        cant30 = (EditText) findViewById(R.id.cantidadProducto30);
        final TextView total30 = (TextView) findViewById(R.id.totalProducto30);

        producto31 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto31);
        final TextView precio31 = (TextView) findViewById(R.id.precioProducto31);
        final TextView desc31 = (TextView) findViewById(R.id.porcentajeDescProducto31);
        cant31 = (EditText) findViewById(R.id.cantidadProducto31);
        final TextView total31 = (TextView) findViewById(R.id.totalProducto31);

        producto32 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto32);
        final TextView precio32 = (TextView) findViewById(R.id.precioProducto32);
        final TextView desc32 = (TextView) findViewById(R.id.porcentajeDescProducto32);
        cant32 = (EditText) findViewById(R.id.cantidadProducto32);
        final TextView total32 = (TextView) findViewById(R.id.totalProducto32);

        producto33 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto33);
        final TextView precio33 = (TextView) findViewById(R.id.precioProducto33);
        final TextView desc33 = (TextView) findViewById(R.id.porcentajeDescProducto33);
        cant33 = (EditText) findViewById(R.id.cantidadProducto33);
        final TextView total33 = (TextView) findViewById(R.id.totalProducto33);

        producto34 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto34);
        final TextView precio34 = (TextView) findViewById(R.id.precioProducto34);
        final TextView desc34 = (TextView) findViewById(R.id.porcentajeDescProducto34);
        cant34 = (EditText) findViewById(R.id.cantidadProducto34);
        final TextView total34 = (TextView) findViewById(R.id.totalProducto34);

        producto35 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto35);
        final TextView precio35 = (TextView) findViewById(R.id.precioProducto35);
        final TextView desc35 = (TextView) findViewById(R.id.porcentajeDescProducto35);
        cant35 = (EditText) findViewById(R.id.cantidadProducto35);
        final TextView total35 = (TextView) findViewById(R.id.totalProducto35);

        producto36 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto36);
        final TextView  precio36 = (TextView) findViewById(R.id.precioProducto36);
        final TextView desc36 = (TextView) findViewById(R.id.porcentajeDescProducto36);
        cant36 = (EditText) findViewById(R.id.cantidadProducto36);
        final TextView total36 = (TextView) findViewById(R.id.totalProducto36);

        producto37 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto37);
        final TextView precio37 = (TextView) findViewById(R.id.precioProducto37);
        final TextView desc37 = (TextView) findViewById(R.id.porcentajeDescProducto37);
        cant37 = (EditText) findViewById(R.id.cantidadProducto37);
        final TextView total37 = (TextView) findViewById(R.id.totalProducto37);

        producto38 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto38);
        final TextView precio38 = (TextView) findViewById(R.id.precioProducto38);
        final TextView desc38 = (TextView) findViewById(R.id.porcentajeDescProducto38);
        cant38 = (EditText) findViewById(R.id.cantidadProducto38);
        final TextView total38 = (TextView) findViewById(R.id.totalProducto38);

        producto39 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto39);
        final TextView precio39 = (TextView) findViewById(R.id.precioProducto39);
        final TextView desc39 = (TextView) findViewById(R.id.porcentajeDescProducto39);
        cant39 = (EditText) findViewById(R.id.cantidadProducto39);
        final TextView total39 = (TextView) findViewById(R.id.totalProducto39);

        producto40 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto40);
        final TextView precio40 = (TextView) findViewById(R.id.precioProducto40);
        final TextView desc40 = (TextView) findViewById(R.id.porcentajeDescProducto40);
        cant40 = (EditText) findViewById(R.id.cantidadProducto40);
        final TextView total40 = (TextView) findViewById(R.id.totalProducto40);

        producto41 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto41);
        final TextView precio41 = (TextView) findViewById(R.id.precioProducto41);
        final TextView desc41 = (TextView) findViewById(R.id.porcentajeDescProducto41);
        cant41 = (EditText) findViewById(R.id.cantidadProducto41);
        final TextView total41 = (TextView) findViewById(R.id.totalProducto41);

        producto42 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto42);
        final TextView precio42 = (TextView) findViewById(R.id.precioProducto42);
        final TextView desc42 = (TextView) findViewById(R.id.porcentajeDescProducto42);
        cant42 = (EditText) findViewById(R.id.cantidadProducto42);
        final TextView total42 = (TextView) findViewById(R.id.totalProducto42);

        producto43 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto43);
        final TextView precio43 = (TextView) findViewById(R.id.precioProducto43);
        final TextView desc43 = (TextView) findViewById(R.id.porcentajeDescProducto43);
        cant43 = (EditText) findViewById(R.id.cantidadProducto43);
        final TextView total43 = (TextView) findViewById(R.id.totalProducto43);

        producto44 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto44);
        final TextView precio44 = (TextView) findViewById(R.id.precioProducto44);
        final TextView desc44 = (TextView) findViewById(R.id.porcentajeDescProducto44);
        cant44 = (EditText) findViewById(R.id.cantidadProducto44);
        final TextView total44 = (TextView) findViewById(R.id.totalProducto44);

        producto45 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto45);
        final TextView precio45 = (TextView) findViewById(R.id.precioProducto45);
        final TextView desc45 = (TextView) findViewById(R.id.porcentajeDescProducto45);
        cant45 = (EditText) findViewById(R.id.cantidadProducto45);
        final TextView total45 = (TextView) findViewById(R.id.totalProducto45);

        final TextView sumaTotal = (TextView)findViewById(R.id.txtSuma);
        final TextView descuentoTotal = (TextView)findViewById(R.id.txtdescuento);
        final TextView totalTotal = (TextView)findViewById(R.id.txtTotal);


        ///PARTE PARA EL AUTOCOMPLETE DE LOS PRODUCTOS
        autoCompleteProducto1 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto1);
        ArrayAdapter adapterProductos = new ArrayAdapter(this, android.R.layout.simple_list_item_1, productos);
        autoCompleteProducto1.setAdapter(adapterProductos);

        autoCompleteProducto2 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto2);
        ArrayAdapter adapterProductos2 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, productos);
        autoCompleteProducto2.setAdapter(adapterProductos2);

        autoCompleteProducto3 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto3);
        ArrayAdapter adapterProductos3 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, productos);
        autoCompleteProducto3.setAdapter(adapterProductos3);

        autoCompleteProducto4 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto4);
        ArrayAdapter adapterProductos4 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, productos);
        autoCompleteProducto4.setAdapter(adapterProductos4);

        autoCompleteProducto5 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto5);
        ArrayAdapter adapterProductos5 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, productos);
        autoCompleteProducto5.setAdapter(adapterProductos5);

        autoCompleteProducto6 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto6);
        ArrayAdapter adapterProductos6 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, productos);
        autoCompleteProducto6.setAdapter(adapterProductos6);

        autoCompleteProducto7 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto7);
        ArrayAdapter adapterProductos7 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, productos);
        autoCompleteProducto7.setAdapter(adapterProductos7);

        autoCompleteProducto8 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto8);
        ArrayAdapter adapterProductos8 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, productos);
        autoCompleteProducto8.setAdapter(adapterProductos8);

        autoCompleteProducto9 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto9);
        ArrayAdapter adapterProductos9 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, productos);
        autoCompleteProducto9.setAdapter(adapterProductos9);

        autoCompleteProducto10 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto10);
        ArrayAdapter adapterProductos10 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, productos);
        autoCompleteProducto10.setAdapter(adapterProductos10);

        autoCompleteProducto11 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto11);
        ArrayAdapter adapterProductos11 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, productos);
        autoCompleteProducto11.setAdapter(adapterProductos11);

        autoCompleteProducto12 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto12);
        ArrayAdapter adapterProductos12 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, productos);
        autoCompleteProducto12.setAdapter(adapterProductos12);

        autoCompleteProducto13 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto13);
        ArrayAdapter adapterProductos13 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, productos);
        autoCompleteProducto13.setAdapter(adapterProductos13);

        autoCompleteProducto14 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto14);
        ArrayAdapter adapterProductos14 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, productos);
        autoCompleteProducto14.setAdapter(adapterProductos14);

        autoCompleteProducto15 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto15);
        ArrayAdapter adapterProductos15 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, productos);
        autoCompleteProducto15.setAdapter(adapterProductos15);

        autoCompleteProducto16 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto16);
        ArrayAdapter adapterProductos16 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, productos);
        autoCompleteProducto16.setAdapter(adapterProductos16);

        autoCompleteProducto17 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto17);
        ArrayAdapter adapterProductos17 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, productos);
        autoCompleteProducto17.setAdapter(adapterProductos17);

        autoCompleteProducto18 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto18);
        ArrayAdapter adapterProductos18 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, productos);
        autoCompleteProducto18.setAdapter(adapterProductos18);

        autoCompleteProducto19 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto19);
        ArrayAdapter adapterProductos19 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, productos);
        autoCompleteProducto19.setAdapter(adapterProductos19);

        autoCompleteProducto20 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto20);
        ArrayAdapter adapterProductos20 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, productos);
        autoCompleteProducto20.setAdapter(adapterProductos20);

        autoCompleteProducto21 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto21);
        ArrayAdapter adapterProductos21 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, productos);
        autoCompleteProducto21.setAdapter(adapterProductos);

        autoCompleteProducto22 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto22);
        ArrayAdapter adapterProductos22 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, productos);
        autoCompleteProducto22.setAdapter(adapterProductos22);

        autoCompleteProducto23 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto23);
        ArrayAdapter adapterProductos23 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, productos);
        autoCompleteProducto23.setAdapter(adapterProductos23);

        autoCompleteProducto24 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto24);
        ArrayAdapter adapterProductos24 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, productos);
        autoCompleteProducto24.setAdapter(adapterProductos24);

        autoCompleteProducto25 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto25);
        ArrayAdapter adapterProductos25 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, productos);
        autoCompleteProducto25.setAdapter(adapterProductos25);

        autoCompleteProducto26 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto26);
        ArrayAdapter adapterProductos26 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, productos);
        autoCompleteProducto26.setAdapter(adapterProductos26);

        autoCompleteProducto27 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto27);
        ArrayAdapter adapterProductos27 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, productos);
        autoCompleteProducto27.setAdapter(adapterProductos27);

        autoCompleteProducto28 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto28);
        ArrayAdapter adapterProductos28 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, productos);
        autoCompleteProducto28.setAdapter(adapterProductos28);

        autoCompleteProducto29 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto29);
        ArrayAdapter adapterProductos29 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, productos);
        autoCompleteProducto29.setAdapter(adapterProductos29);

        autoCompleteProducto30 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto30);
        ArrayAdapter adapterProductos30 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, productos);
        autoCompleteProducto30.setAdapter(adapterProductos30);

        autoCompleteProducto31 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto31);
        ArrayAdapter adapterProductos31 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, productos);
        autoCompleteProducto31.setAdapter(adapterProductos31);

        autoCompleteProducto32 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto32);
        ArrayAdapter adapterProductos32 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, productos);
        autoCompleteProducto32.setAdapter(adapterProductos32);

        autoCompleteProducto33 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto33);
        ArrayAdapter adapterProductos33 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, productos);
        autoCompleteProducto33.setAdapter(adapterProductos33);

        autoCompleteProducto34 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto34);
        ArrayAdapter adapterProductos34 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, productos);
        autoCompleteProducto34.setAdapter(adapterProductos34);

        autoCompleteProducto35 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto35);
        ArrayAdapter adapterProductos35 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, productos);
        autoCompleteProducto35.setAdapter(adapterProductos35);

        autoCompleteProducto36 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto36);
        ArrayAdapter adapterProductos36 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, productos);
        autoCompleteProducto36.setAdapter(adapterProductos36);

        autoCompleteProducto37 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto37);
        ArrayAdapter adapterProductos37 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, productos);
        autoCompleteProducto37.setAdapter(adapterProductos37);

        autoCompleteProducto38 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto38);
        ArrayAdapter adapterProductos38 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, productos);
        autoCompleteProducto38.setAdapter(adapterProductos38);

        autoCompleteProducto39 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto39);
        ArrayAdapter adapterProductos39 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, productos);
        autoCompleteProducto39.setAdapter(adapterProductos39);

        autoCompleteProducto40 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto40);
        ArrayAdapter adapterProductos40 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, productos);
        autoCompleteProducto40.setAdapter(adapterProductos40);

        autoCompleteProducto41 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto41);
        ArrayAdapter adapterProductos41 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, productos);
        autoCompleteProducto41.setAdapter(adapterProductos41);

        autoCompleteProducto42 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto42);
        ArrayAdapter adapterProductos42 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, productos);
        autoCompleteProducto42.setAdapter(adapterProductos42);

        autoCompleteProducto43 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto43);
        ArrayAdapter adapterProductos43 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, productos);
        autoCompleteProducto43.setAdapter(adapterProductos43);

        autoCompleteProducto44 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto44);
        ArrayAdapter adapterProductos44 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, productos);
        autoCompleteProducto44.setAdapter(adapterProductos44);

        autoCompleteProducto45 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto45);
        ArrayAdapter adapterProductos45 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, productos);
        autoCompleteProducto45.setAdapter(adapterProductos45);


        View.OnFocusChangeListener listener;

        listener = new View.OnFocusChangeListener() {
            //Inicio del seOnFocusChangeListener
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                String familia = familiaDato;
                String tipo_venta = tipoventaDato;
                String subtipo = subtipoDato;

                if (familia.equals("Seleccione una familia...")) {

                    Toast toast = Toast.makeText(getApplicationContext(), "Debe seleccionar la familia", Toast.LENGTH_SHORT);
                    toast.show();
                    return;

                }

                if (tipo_venta.equals("Tipo de Venta...")) {

                    Toast toast = Toast.makeText(getApplicationContext(), "Debe seleccionar el tipo de venta", Toast.LENGTH_SHORT);
                    toast.show();
                    return;

                    /*
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Atenci—n!!");
                            builder.setMessage("Seleccione el tipo de venta");
                            builder.setCancelable(false);
                            builder.setNeutralButton("Aceptar",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                    */
                }

                if (subtipo.equals("Seleccione un sub tipo...")) {

                    Toast toast = Toast.makeText(getApplicationContext(), "Debe seleccionar el subtipo", Toast.LENGTH_SHORT);
                    toast.show();
                    return;

                }

                if (subtipo.equals("Paq. Granos") && familia.equals("Veterinaria")) {

                    Toast toast = Toast.makeText(getApplicationContext(), "Paq. Granos solo es para familia agricola", Toast.LENGTH_SHORT);
                    toast.show();
                    return;

                }

                String opProducto1 = String.valueOf(producto1.getText().toString());
                String opProducto2 = String.valueOf(producto2.getText().toString());
                String opProducto3 = String.valueOf(producto3.getText().toString());
                String opProducto4 = String.valueOf(producto4.getText().toString());
                String opProducto5 = String.valueOf(producto5.getText().toString());
                String opProducto6 = String.valueOf(producto6.getText().toString());
                String opProducto7 = String.valueOf(producto7.getText().toString());
                String opProducto8 = String.valueOf(producto8.getText().toString());
                String opProducto9 = String.valueOf(producto9.getText().toString());
                String opProducto10 = String.valueOf(producto10.getText().toString());
                String opProducto11 = String.valueOf(producto11.getText().toString());
                String opProducto12 = String.valueOf(producto12.getText().toString());
                String opProducto13 = String.valueOf(producto13.getText().toString());
                String opProducto14 = String.valueOf(producto14.getText().toString());
                String opProducto15 = String.valueOf(producto15.getText().toString());
                String opProducto16 = String.valueOf(producto16.getText().toString());
                String opProducto17 = String.valueOf(producto17.getText().toString());
                String opProducto18 = String.valueOf(producto18.getText().toString());
                String opProducto19 = String.valueOf(producto19.getText().toString());
                String opProducto20 = String.valueOf(producto20.getText().toString());
                String opProducto21 = String.valueOf(producto21.getText().toString());
                String opProducto22 = String.valueOf(producto22.getText().toString());
                String opProducto23 = String.valueOf(producto23.getText().toString());
                String opProducto24 = String.valueOf(producto24.getText().toString());
                String opProducto25 = String.valueOf(producto25.getText().toString());
                String opProducto26 = String.valueOf(producto26.getText().toString());
                String opProducto27 = String.valueOf(producto27.getText().toString());
                String opProducto28 = String.valueOf(producto28.getText().toString());
                String opProducto29 = String.valueOf(producto29.getText().toString());
                String opProducto30 = String.valueOf(producto30.getText().toString());
                String opProducto31 = String.valueOf(producto31.getText().toString());
                String opProducto32 = String.valueOf(producto32.getText().toString());
                String opProducto33 = String.valueOf(producto33.getText().toString());
                String opProducto34 = String.valueOf(producto34.getText().toString());
                String opProducto35 = String.valueOf(producto35.getText().toString());
                String opProducto36 = String.valueOf(producto36.getText().toString());
                String opProducto37 = String.valueOf(producto37.getText().toString());
                String opProducto38 = String.valueOf(producto38.getText().toString());
                String opProducto39 = String.valueOf(producto39.getText().toString());
                String opProducto40 = String.valueOf(producto40.getText().toString());
                String opProducto41 = String.valueOf(producto41.getText().toString());
                String opProducto42 = String.valueOf(producto42.getText().toString());
                String opProducto43 = String.valueOf(producto43.getText().toString());
                String opProducto44 = String.valueOf(producto44.getText().toString());
                String opProducto45 = String.valueOf(producto45.getText().toString());

                if(cant1.length() == 0){
                    cant1.setText(""+0);
                    total1.setText(""+0.0);
                    desc1.setText("");
                }
                if(cant2.length() == 0){
                    cant2.setText(""+0);
                    total2.setText(""+0.0);
                    desc2.setText("");
                }
                if(cant3.length() == 0){
                    cant3.setText(""+0);
                    total3.setText(""+0.0);
                    desc3.setText("");
                }
                if(cant4.length() == 0){
                    cant4.setText(""+0);
                    total4.setText(""+0.0);
                    desc4.setText("");
                }
                if(cant5.length() == 0){
                    cant5.setText(""+0);
                    total5.setText(""+0.0);
                    desc5.setText("");
                }
                if(cant6.length() == 0){
                    cant6.setText(""+0);
                    total6.setText(""+0.0);
                    desc6.setText("");
                }
                if(cant7.length() == 0){
                    cant7.setText(""+0);
                    total7.setText(""+0.0);
                    desc7.setText("");
                }
                if(cant8.length() == 0){
                    cant8.setText(""+0);
                    total8.setText(""+0.0);
                    desc8.setText("");
                }
                if(cant9.length() == 0){
                    cant9.setText(""+0);
                    total9.setText(""+0.0);
                    desc9.setText("");
                }
                if(cant10.length() == 0){
                    cant10.setText(""+0);
                    total10.setText(""+0.0);
                    desc10.setText("");
                }
                if(cant11.length() == 0){
                    cant11.setText(""+0);
                    total11.setText(""+0.0);
                    desc11.setText("");
                }
                if(cant12.length() == 0){
                    cant12.setText(""+0);
                    total12.setText(""+0.0);
                    desc12.setText("");
                }
                if(cant13.length() == 0){
                    cant13.setText(""+0);
                    total13.setText(""+0.0);
                    desc13.setText("");
                }
                if(cant14.length() == 0){
                    cant14.setText(""+0);
                    total14.setText(""+0.0);
                    desc14.setText("");
                }
                if(cant15.length() == 0){
                    cant15.setText(""+0);
                    total15.setText(""+0.0);
                    desc15.setText("");
                }
                if(cant16.length() == 0){
                    cant16.setText(""+0);
                    total16.setText(""+0.0);
                    desc16.setText("");
                }
                if(cant17.length() == 0){
                    cant17.setText(""+0);
                    total17.setText(""+0.0);
                    desc17.setText("");
                }
                if(cant18.length() == 0){
                    cant18.setText(""+0);
                    total18.setText(""+0.0);
                    desc18.setText("");
                }
                if(cant19.length() == 0){
                    cant19.setText(""+0);
                    total19.setText(""+0.0);
                    desc19.setText("");
                }
                if(cant20.length() == 0){
                    cant20.setText(""+0);
                    total20.setText(""+0.0);
                    desc20.setText("");
                }
                if(cant21.length() == 0){
                    cant21.setText(""+0);
                    total21.setText(""+0.0);
                    desc21.setText("");
                }
                if(cant22.length() == 0){
                    cant22.setText(""+0);
                    total22.setText(""+0.0);
                    desc22.setText("");
                }
                if(cant23.length() == 0){
                    cant23.setText(""+0);
                    total23.setText(""+0.0);
                    desc23.setText("");
                }
                if(cant24.length() == 0){
                    cant24.setText(""+0);
                    total24.setText(""+0.0);
                    desc24.setText("");
                }
                if(cant25.length() == 0){
                    cant25.setText(""+0);
                    total25.setText(""+0.0);
                    desc25.setText("");
                }
                if(cant26.length() == 0){
                    cant26.setText(""+0);
                    total26.setText(""+0.0);
                    desc26.setText("");
                }
                if(cant27.length() == 0){
                    cant27.setText(""+0);
                    total27.setText(""+0.0);
                    desc27.setText("");
                }
                if(cant28.length() == 0){
                    cant28.setText(""+0);
                    total28.setText(""+0.0);
                    desc28.setText("");
                }
                if(cant29.length() == 0){
                    cant29.setText(""+0);
                    total29.setText(""+0.0);
                    desc29.setText("");
                }
                if(cant30.length() == 0){
                    cant30.setText(""+0);
                    total30.setText(""+0.0);
                    desc30.setText("");
                }
                if(cant31.length() == 0){
                    cant31.setText(""+0);
                    total31.setText(""+0.0);
                    desc31.setText("");
                }
                if(cant32.length() == 0){
                    cant32.setText(""+0);
                    total32.setText(""+0.0);
                    desc32.setText("");
                }
                if(cant33.length() == 0){
                    cant33.setText(""+0);
                    total33.setText(""+0.0);
                    desc33.setText("");
                }
                if(cant34.length() == 0){
                    cant34.setText(""+0);
                    total34.setText(""+0.0);
                    desc34.setText("");
                }
                if(cant35.length() == 0){
                    cant35.setText(""+0);
                    total35.setText(""+0.0);
                    desc35.setText("");
                }
                if(cant36.length() == 0){
                    cant36.setText(""+0);
                    total36.setText(""+0.0);
                    desc36.setText("");
                }
                if(cant37.length() == 0){
                    cant37.setText(""+0);
                    total37.setText(""+0.0);
                    desc37.setText("");
                }
                if(cant38.length() == 0){
                    cant38.setText(""+0);
                    total38.setText(""+0.0);
                    desc38.setText("");
                }
                if(cant39.length() == 0){
                    cant39.setText(""+0);
                    total39.setText(""+0.0);
                    desc39.setText("");
                }
                if(cant40.length() == 0){
                    cant40.setText(""+0);
                    total40.setText(""+0.0);
                    desc40.setText("");
                }
                if(cant41.length() == 0){
                    cant41.setText(""+0);
                    total41.setText(""+0.0);
                    desc41.setText("");
                }
                if(cant42.length() == 0){
                    cant42.setText(""+0);
                    total42.setText(""+0.0);
                    desc42.setText("");
                }
                if(cant43.length() == 0){
                    cant43.setText(""+0);
                    total43.setText(""+0.0);
                    desc43.setText("");
                }
                if(cant44.length() == 0){
                    cant44.setText(""+0);
                    total44.setText(""+0.0);
                    desc44.setText("");
                }
                if(cant45.length() == 0){
                    cant45.setText(""+0);
                    total45.setText(""+0.0);
                    desc45.setText("");
                }

                ///Se rompe la cadena de producto para sacar el precio del producto y colocarlo en TextView correspondiente
                if (opProducto1.length() >= 1) {
                    String[] separated = opProducto1.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Validacion para la existencia en almacen de mano
                    if(cant1.toString() != "0" && cant1.toString() != "") {
                        if (tipo_venta.toString().equals("Mano") || tipo_venta.toString().equals("Extendido")) {
                            //Invocamos el metodo para valida que haya exitencia de mano
                            String cve_cliente = "";
                            String[] separatedCliente = clienteDato.split("-");
                            cve_cliente = separatedCliente[0];

                            Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                            if(paquete == false) {
                                String existencia = dbHandler.existenciaMano(cve_cat_producto, cve_cliente);

                            /*
                            Toast toast = Toast.makeText(getApplicationContext(), "existencia "+ cve_cliente +"", Toast.LENGTH_SHORT);
                            toast.show();
                            return;
                            */

                                Integer cantEnt = Integer.valueOf(cant1.getText().toString());
                                Integer existenciaEnt = Integer.parseInt(existencia);

                                if (existenciaEnt < cantEnt) {
                                    producto1.setText("");
                                    precio1.setText("" + 0.0);
                                    desc1.setText("");
                                    cant1.setText("" + 0);
                                    total1.setText("");
                                    Toast toast = Toast.makeText(getApplicationContext(), "El producto en la partida 1 no tiene suficiente existencia en su almacen", Toast.LENGTH_SHORT);
                                    toast.show();
                                    return;
                                }
                            }


                        }
                    }//fin Validacion para la existencia en almacen de mano

                    //Invocamos el mettodo validaProductosFamilia para validar que los productos no se convinen de distintas familias
                    Boolean valida_familia = dbHandler.validaProductosFamilia(cve_cat_producto, familia);
                    if (valida_familia == false) {
                        producto1.setText("");
                        precio1.setText(""+0.0);
                        desc1.setText("");
                        cant1.setText(""+0);
                        total1.setText("");
                        Toast toast = Toast.makeText(getApplicationContext(), "El producto en la partida 1 no corresponde ala familia que seleccionaste", Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }

                    //Invocamos el metodo para sacar el precio unitario del producto
                    Double precio_unitario = dbHandler.precioProducto(cve_cat_producto);
                    precio1.setText("" + precio_unitario);

                }
                if (opProducto1.length() == 0) {
                    precio1.setText("" + 0.0);
                }

                if (opProducto2.length() >= 1) {
                    String[] separated = opProducto2.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Validacion para la existencia en almacen de mano
                    if(cant2.toString() != "0" && cant2.toString() != "") {
                        if (tipo_venta.toString().equals("Mano") || tipo_venta.toString().equals("Extendido")) {
                            //Invocamos el metodo para valida que haya exitencia de mano
                            String cve_cliente = "";
                            String[] separatedCliente = clienteDato.split("-");
                            cve_cliente = separatedCliente[0];

                            Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                            if(paquete == false) {
                                String existencia = dbHandler.existenciaMano(cve_cat_producto, cve_cliente);

                            /*
                            Toast toast = Toast.makeText(getApplicationContext(), "existencia "+ cve_cliente +"", Toast.LENGTH_SHORT);
                            toast.show();
                            return;
                            */

                                Integer cantEnt = Integer.valueOf(cant2.getText().toString());
                                Integer existenciaEnt = Integer.parseInt(existencia);

                                if (existenciaEnt < cantEnt) {
                                    producto2.setText("");
                                    precio2.setText("" + 0.0);
                                    desc2.setText("");
                                    cant2.setText("" + 0);
                                    total2.setText("");
                                    Toast toast = Toast.makeText(getApplicationContext(), "El producto en la partida 2 no tiene suficiente existencia en su almacen", Toast.LENGTH_SHORT);
                                    toast.show();
                                    return;
                                }
                            }


                        }
                    }//fin Validacion para la existencia en almacen de mano

                    //Invocamos el mettodo validaProductosFamilia para validar que los productos no se convinen de distintas familias
                    Boolean valida_familia = dbHandler.validaProductosFamilia(cve_cat_producto, familia);
                    if (valida_familia == false) {
                        producto2.setText("");
                        precio2.setText(""+0.0);
                        desc2.setText("");
                        cant2.setText(""+0);
                        total2.setText("");
                        Toast toast = Toast.makeText(getApplicationContext(), "El producto en la partida 2 no corresponde ala familia que seleccionaste", Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }

                    //Invocamos el metodo para sacar el precio unitario del producto
                    Double precio_unitario = dbHandler.precioProducto(cve_cat_producto);
                    precio2.setText("" + precio_unitario);
                }
                if (opProducto2.length() == 0) {
                    precio2.setText("" + 0.0);
                }

                if (opProducto3.length() >= 1) {
                    String[] separated = opProducto3.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Validacion para la existencia en almacen de mano
                    if(cant3.toString() != "0" && cant3.toString() != "") {
                        if (tipo_venta.toString().equals("Mano") || tipo_venta.toString().equals("Extendido")) {
                            //Invocamos el metodo para valida que haya exitencia de mano
                            String cve_cliente = "";
                            String[] separatedCliente = clienteDato.split("-");
                            cve_cliente = separatedCliente[0];

                            Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                            if(paquete == false) {
                                String existencia = dbHandler.existenciaMano(cve_cat_producto, cve_cliente);

                            /*
                            Toast toast = Toast.makeText(getApplicationContext(), "existencia "+ cve_cliente +"", Toast.LENGTH_SHORT);
                            toast.show();
                            return;
                            */

                                Integer cantEnt = Integer.valueOf(cant3.getText().toString());
                                Integer existenciaEnt = Integer.parseInt(existencia);

                                if (existenciaEnt < cantEnt) {
                                    producto3.setText("");
                                    precio3.setText("" + 0.0);
                                    desc3.setText("");
                                    cant3.setText("" + 0);
                                    total3.setText("");
                                    Toast toast = Toast.makeText(getApplicationContext(), "El producto en la partida 3 no tiene suficiente existencia en su almacen", Toast.LENGTH_SHORT);
                                    toast.show();
                                    return;
                                }
                            }


                        }
                    }//fin Validacion para la existencia en almacen de mano

                    //Invocamos el mettodo validaProductosFamilia para validar que los productos no se convinen de distintas familias
                    Boolean valida_familia = dbHandler.validaProductosFamilia(cve_cat_producto, familia);
                    if (valida_familia == false) {
                        producto3.setText("");
                        precio3.setText(""+0.0);
                        desc3.setText("");
                        cant3.setText(""+0);
                        total3.setText("");
                        Toast toast = Toast.makeText(getApplicationContext(), "El producto en la partida 3 no corresponde ala familia que seleccionaste", Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }

                    //Invocamos el metodo para sacar el precio unitario del producto
                    Double precio_unitario = dbHandler.precioProducto(cve_cat_producto);
                    precio3.setText("" + precio_unitario);
                }
                if (opProducto3.length() == 0) {
                    precio3.setText("" + 0.0);
                }

                if (opProducto4.length() >= 1) {
                    String[] separated = opProducto4.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Validacion para la existencia en almacen de mano
                    if(cant4.toString() != "0" && cant4.toString() != "") {
                        if (tipo_venta.toString().equals("Mano") || tipo_venta.toString().equals("Extendido")) {
                            //Invocamos el metodo para valida que haya exitencia de mano
                            String cve_cliente = "";
                            String[] separatedCliente = clienteDato.split("-");
                            cve_cliente = separatedCliente[0];

                            Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                            if(paquete == false) {
                                String existencia = dbHandler.existenciaMano(cve_cat_producto, cve_cliente);

                            /*
                            Toast toast = Toast.makeText(getApplicationContext(), "existencia "+ cve_cliente +"", Toast.LENGTH_SHORT);
                            toast.show();
                            return;
                            */

                                Integer cantEnt = Integer.valueOf(cant4.getText().toString());
                                Integer existenciaEnt = Integer.parseInt(existencia);

                                if (existenciaEnt < cantEnt) {
                                    producto4.setText("");
                                    precio4.setText("" + 0.0);
                                    desc4.setText("");
                                    cant4.setText("" + 0);
                                    total4.setText("");
                                    Toast toast = Toast.makeText(getApplicationContext(), "El producto en la partida 4 no tiene suficiente existencia en su almacen", Toast.LENGTH_SHORT);
                                    toast.show();
                                    return;
                                }
                            }


                        }
                    }//fin Validacion para la existencia en almacen de mano

                    //Invocamos el mettodo validaProductosFamilia para validar que los productos no se convinen de distintas familias
                    Boolean valida_familia = dbHandler.validaProductosFamilia(cve_cat_producto, familia);
                    if (valida_familia == false) {
                        producto4.setText("");
                        precio4.setText(""+0.0);
                        desc4.setText("");
                        cant4.setText(""+0);
                        total4.setText("");
                        Toast toast = Toast.makeText(getApplicationContext(), "El producto en la partida 4 no corresponde ala familia que seleccionaste", Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }

                    //Invocamos el metodo para sacar el precio unitario del producto
                    Double precio_unitario = dbHandler.precioProducto(cve_cat_producto);
                    precio4.setText("" + precio_unitario);
                }
                if (opProducto4.length() == 0) {
                    precio4.setText("" + 0.0);
                }

                if (opProducto5.length() >= 1) {
                    String[] separated = opProducto5.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Validacion para la existencia en almacen de mano
                    if(cant5.toString() != "0" && cant5.toString() != "") {
                        if (tipo_venta.toString().equals("Mano") || tipo_venta.toString().equals("Extendido")) {
                            //Invocamos el metodo para valida que haya exitencia de mano
                            String cve_cliente = "";
                            String[] separatedCliente = clienteDato.split("-");
                            cve_cliente = separatedCliente[0];

                            Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                            if(paquete == false) {
                                String existencia = dbHandler.existenciaMano(cve_cat_producto, cve_cliente);

                            /*
                            Toast toast = Toast.makeText(getApplicationContext(), "existencia "+ cve_cliente +"", Toast.LENGTH_SHORT);
                            toast.show();
                            return;
                            */

                                Integer cantEnt = Integer.valueOf(cant5.getText().toString());
                                Integer existenciaEnt = Integer.parseInt(existencia);

                                if (existenciaEnt < cantEnt) {
                                    producto5.setText("");
                                    precio5.setText("" + 0.0);
                                    desc5.setText("");
                                    cant5.setText("" + 0);
                                    total5.setText("");
                                    Toast toast = Toast.makeText(getApplicationContext(), "El producto en la partida 5 no tiene suficiente existencia en su almacen", Toast.LENGTH_SHORT);
                                    toast.show();
                                    return;
                                }
                            }


                        }
                    }//fin Validacion para la existencia en almacen de mano

                    //Invocamos el mettodo validaProductosFamilia para validar que los productos no se convinen de distintas familias
                    Boolean valida_familia = dbHandler.validaProductosFamilia(cve_cat_producto, familia);
                    if (valida_familia == false) {
                        producto5.setText("");
                        precio5.setText(""+0.0);
                        desc5.setText("");
                        cant5.setText(""+0);
                        total5.setText("");
                        Toast toast = Toast.makeText(getApplicationContext(), "El producto en la partida 5 no corresponde ala familia que seleccionaste", Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }

                    //Invocamos el metodo para sacar el precio unitario del producto
                    Double precio_unitario = dbHandler.precioProducto(cve_cat_producto);
                    precio5.setText("" + precio_unitario);
                }
                if (opProducto5.length() == 0) {
                    precio5.setText("" + 0.0);
                }

                if (opProducto6.length() >= 1) {
                    String[] separated = opProducto6.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Validacion para la existencia en almacen de mano
                    if(cant6.toString() != "0" && cant6.toString() != "") {
                        if (tipo_venta.toString().equals("Mano") || tipo_venta.toString().equals("Extendido")) {
                            //Invocamos el metodo para valida que haya exitencia de mano
                            String cve_cliente = "";
                            String[] separatedCliente = clienteDato.split("-");
                            cve_cliente = separatedCliente[0];

                            Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                            if(paquete == false) {
                                String existencia = dbHandler.existenciaMano(cve_cat_producto, cve_cliente);

                            /*
                            Toast toast = Toast.makeText(getApplicationContext(), "existencia "+ cve_cliente +"", Toast.LENGTH_SHORT);
                            toast.show();
                            return;
                            */

                                Integer cantEnt = Integer.valueOf(cant6.getText().toString());
                                Integer existenciaEnt = Integer.parseInt(existencia);

                                if (existenciaEnt < cantEnt) {
                                    producto6.setText("");
                                    precio6.setText("" + 0.0);
                                    desc6.setText("");
                                    cant6.setText("" + 0);
                                    total6.setText("");
                                    Toast toast = Toast.makeText(getApplicationContext(), "El producto en la partida 6 no tiene suficiente existencia en su almacen", Toast.LENGTH_SHORT);
                                    toast.show();
                                    return;
                                }
                            }


                        }
                    }//fin Validacion para la existencia en almacen de mano

                    //Invocamos el mettodo validaProductosFamilia para validar que los productos no se convinen de distintas familias
                    Boolean valida_familia = dbHandler.validaProductosFamilia(cve_cat_producto, familia);
                    if (valida_familia == false) {
                        producto6.setText("");
                        precio6.setText(""+0.0);
                        desc6.setText("");
                        cant6.setText(""+0);
                        total6.setText("");
                        Toast toast = Toast.makeText(getApplicationContext(), "El producto en la partida 6 no corresponde ala familia que seleccionaste", Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }

                    //Invocamos el metodo para sacar el precio unitario del producto
                    Double precio_unitario = dbHandler.precioProducto(cve_cat_producto);
                    precio6.setText("" + precio_unitario);
                }
                if (opProducto6.length() == 0) {
                    precio6.setText("" + 0.0);
                }

                if (opProducto7.length() >= 1) {
                    String[] separated = opProducto7.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Validacion para la existencia en almacen de mano
                    if(cant7.toString() != "0" && cant7.toString() != "") {
                        if (tipo_venta.toString().equals("Mano") || tipo_venta.toString().equals("Extendido")) {
                            //Invocamos el metodo para valida que haya exitencia de mano
                            String cve_cliente = "";
                            String[] separatedCliente = clienteDato.split("-");
                            cve_cliente = separatedCliente[0];

                            Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                            if(paquete == false) {
                                String existencia = dbHandler.existenciaMano(cve_cat_producto, cve_cliente);

                            /*
                            Toast toast = Toast.makeText(getApplicationContext(), "existencia "+ cve_cliente +"", Toast.LENGTH_SHORT);
                            toast.show();
                            return;
                            */

                                Integer cantEnt = Integer.valueOf(cant7.getText().toString());
                                Integer existenciaEnt = Integer.parseInt(existencia);

                                if (existenciaEnt < cantEnt) {
                                    producto7.setText("");
                                    precio7.setText("" + 0.0);
                                    desc7.setText("");
                                    cant7.setText("" + 0);
                                    total7.setText("");
                                    Toast toast = Toast.makeText(getApplicationContext(), "El producto en la partida 7 no tiene suficiente existencia en su almacen", Toast.LENGTH_SHORT);
                                    toast.show();
                                    return;
                                }
                            }


                        }
                    }//fin Validacion para la existencia en almacen de mano

                    //Invocamos el mettodo validaProductosFamilia para validar que los productos no se convinen de distintas familias
                    Boolean valida_familia = dbHandler.validaProductosFamilia(cve_cat_producto, familia);
                    if (valida_familia == false) {
                        producto7.setText("");
                        precio7.setText(""+0.0);
                        desc7.setText("");
                        cant7.setText(""+0);
                        total7.setText("");
                        Toast toast = Toast.makeText(getApplicationContext(), "El producto en la partida 7 no corresponde ala familia que seleccionaste", Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }

                    //Invocamos el metodo para sacar el precio unitario del producto
                    Double precio_unitario = dbHandler.precioProducto(cve_cat_producto);
                    precio7.setText("" + precio_unitario);
                }
                if (opProducto7.length() == 0) {
                    precio7.setText("" + 0.0);
                }

                if (opProducto8.length() >= 1) {
                    String[] separated = opProducto8.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Validacion para la existencia en almacen de mano
                    if(cant8.toString() != "0" && cant8.toString() != "") {
                        if (tipo_venta.toString().equals("Mano") || tipo_venta.toString().equals("Extendido")) {
                            //Invocamos el metodo para valida que haya exitencia de mano
                            String cve_cliente = "";
                            String[] separatedCliente = clienteDato.split("-");
                            cve_cliente = separatedCliente[0];

                            Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                            if(paquete == false) {
                                String existencia = dbHandler.existenciaMano(cve_cat_producto, cve_cliente);

                            /*
                            Toast toast = Toast.makeText(getApplicationContext(), "existencia "+ cve_cliente +"", Toast.LENGTH_SHORT);
                            toast.show();
                            return;
                            */

                                Integer cantEnt = Integer.valueOf(cant8.getText().toString());
                                Integer existenciaEnt = Integer.parseInt(existencia);

                                if (existenciaEnt < cantEnt) {
                                    producto8.setText("");
                                    precio8.setText("" + 0.0);
                                    desc8.setText("");
                                    cant8.setText("" + 0);
                                    total8.setText("");
                                    Toast toast = Toast.makeText(getApplicationContext(), "El producto en la partida 8 no tiene suficiente existencia en su almacen", Toast.LENGTH_SHORT);
                                    toast.show();
                                    return;
                                }
                            }


                        }
                    }//fin Validacion para la existencia en almacen de mano

                    //Invocamos el mettodo validaProductosFamilia para validar que los productos no se convinen de distintas familias
                    Boolean valida_familia = dbHandler.validaProductosFamilia(cve_cat_producto, familia);
                    if (valida_familia == false) {
                        producto8.setText("");
                        precio8.setText(""+0.0);
                        desc8.setText("");
                        cant8.setText(""+0);
                        total8.setText("");
                        Toast toast = Toast.makeText(getApplicationContext(), "El producto en la partida 8 no corresponde ala familia que seleccionaste", Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }

                    //Invocamos el metodo para sacar el precio unitario del producto
                    Double precio_unitario = dbHandler.precioProducto(cve_cat_producto);
                    precio8.setText("" + precio_unitario);
                }
                if (opProducto8.length() == 0) {
                    precio8.setText("" + 0.0);
                }

                if (opProducto9.length() >= 1) {
                    String[] separated = opProducto9.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Validacion para la existencia en almacen de mano
                    if(cant9.toString() != "0" && cant9.toString() != "") {
                        if (tipo_venta.toString().equals("Mano") || tipo_venta.toString().equals("Extendido")) {
                            //Invocamos el metodo para valida que haya exitencia de mano
                            String cve_cliente = "";
                            String[] separatedCliente = clienteDato.split("-");
                            cve_cliente = separatedCliente[0];

                            Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                            if(paquete == false) {
                                String existencia = dbHandler.existenciaMano(cve_cat_producto, cve_cliente);

                            /*
                            Toast toast = Toast.makeText(getApplicationContext(), "existencia "+ cve_cliente +"", Toast.LENGTH_SHORT);
                            toast.show();
                            return;
                            */

                                Integer cantEnt = Integer.valueOf(cant9.getText().toString());
                                Integer existenciaEnt = Integer.parseInt(existencia);

                                if (existenciaEnt < cantEnt) {
                                    producto9.setText("");
                                    precio9.setText("" + 0.0);
                                    desc9.setText("");
                                    cant9.setText("" + 0);
                                    total9.setText("");
                                    Toast toast = Toast.makeText(getApplicationContext(), "El producto en la partida 9 no tiene suficiente existencia en su almacen", Toast.LENGTH_SHORT);
                                    toast.show();
                                    return;
                                }
                            }


                        }
                    }//fin Validacion para la existencia en almacen de mano

                    //Invocamos el mettodo validaProductosFamilia para validar que los productos no se convinen de distintas familias
                    Boolean valida_familia = dbHandler.validaProductosFamilia(cve_cat_producto, familia);
                    if (valida_familia == false) {
                        producto9.setText("");
                        precio9.setText(""+0.0);
                        desc9.setText("");
                        cant9.setText(""+0);
                        total9.setText("");
                        Toast toast = Toast.makeText(getApplicationContext(), "El producto en la partida 9 no corresponde ala familia que seleccionaste", Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }

                    //Invocamos el metodo para sacar el precio unitario del producto
                    Double precio_unitario = dbHandler.precioProducto(cve_cat_producto);
                    precio9.setText("" + precio_unitario);
                }
                if (opProducto9.length() == 0) {
                    precio9.setText("" + 0.0);
                }

                if (opProducto10.length() >= 1) {
                    String[] separated = opProducto10.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Validacion para la existencia en almacen de mano
                    if(cant10.toString() != "0" && cant10.toString() != "") {
                        if (tipo_venta.toString().equals("Mano") || tipo_venta.toString().equals("Extendido")) {
                            //Invocamos el metodo para valida que haya exitencia de mano
                            String cve_cliente = "";
                            String[] separatedCliente = clienteDato.split("-");
                            cve_cliente = separatedCliente[0];

                            Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                            if(paquete == false) {
                                String existencia = dbHandler.existenciaMano(cve_cat_producto, cve_cliente);

                            /*
                            Toast toast = Toast.makeText(getApplicationContext(), "existencia "+ cve_cliente +"", Toast.LENGTH_SHORT);
                            toast.show();
                            return;
                            */

                                Integer cantEnt = Integer.valueOf(cant10.getText().toString());
                                Integer existenciaEnt = Integer.parseInt(existencia);

                                if (existenciaEnt < cantEnt) {
                                    producto10.setText("");
                                    precio10.setText("" + 0.0);
                                    desc10.setText("");
                                    cant10.setText("" + 0);
                                    total10.setText("");
                                    Toast toast = Toast.makeText(getApplicationContext(), "El producto en la partida 10 no tiene suficiente existencia en su almacen", Toast.LENGTH_SHORT);
                                    toast.show();
                                    return;
                                }
                            }


                        }
                    }//fin Validacion para la existencia en almacen de mano

                    //Invocamos el mettodo validaProductosFamilia para validar que los productos no se convinen de distintas familias
                    Boolean valida_familia = dbHandler.validaProductosFamilia(cve_cat_producto, familia);
                    if (valida_familia == false) {
                        producto10.setText("");
                        precio10.setText(""+0.0);
                        desc10.setText("");
                        cant10.setText(""+0);
                        total10.setText("");
                        Toast toast = Toast.makeText(getApplicationContext(), "El producto en la partida 10 no corresponde ala familia que seleccionaste", Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }

                    //Invocamos el metodo para sacar el precio unitario del producto
                    Double precio_unitario = dbHandler.precioProducto(cve_cat_producto);
                    precio10.setText("" + precio_unitario);
                }
                if (opProducto10.length() == 0) {
                    precio10.setText("" + 0.0);
                }

                if (opProducto11.length() >= 1) {
                    String[] separated = opProducto11.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Validacion para la existencia en almacen de mano
                    if(cant11.toString() != "0" && cant11.toString() != "") {
                        if (tipo_venta.toString().equals("Mano") || tipo_venta.toString().equals("Extendido")) {
                            //Invocamos el metodo para valida que haya exitencia de mano
                            String cve_cliente = "";
                            String[] separatedCliente = clienteDato.split("-");
                            cve_cliente = separatedCliente[0];

                            Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                            if(paquete == false) {
                                String existencia = dbHandler.existenciaMano(cve_cat_producto, cve_cliente);

                            /*
                            Toast toast = Toast.makeText(getApplicationContext(), "existencia "+ cve_cliente +"", Toast.LENGTH_SHORT);
                            toast.show();
                            return;
                            */

                                Integer cantEnt = Integer.valueOf(cant11.getText().toString());
                                Integer existenciaEnt = Integer.parseInt(existencia);

                                if (existenciaEnt < cantEnt) {
                                    producto11.setText("");
                                    precio11.setText("" + 0.0);
                                    desc11.setText("");
                                    cant11.setText("" + 0);
                                    total11.setText("");
                                    Toast toast = Toast.makeText(getApplicationContext(), "El producto en la partida 11 no tiene suficiente existencia en su almacen", Toast.LENGTH_SHORT);
                                    toast.show();
                                    return;
                                }
                            }


                        }
                    }//fin Validacion para la existencia en almacen de mano

                    //Invocamos el mettodo validaProductosFamilia para validar que los productos no se convinen de distintas familias
                    Boolean valida_familia = dbHandler.validaProductosFamilia(cve_cat_producto, familia);
                    if (valida_familia == false) {
                        producto11.setText("");
                        precio11.setText(""+0.0);
                        desc11.setText("");
                        cant11.setText(""+0);
                        total11.setText("");
                        Toast toast = Toast.makeText(getApplicationContext(), "El producto en la partida 11 no corresponde ala familia que seleccionaste", Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }

                    //Invocamos el metodo para sacar el precio unitario del producto
                    Double precio_unitario = dbHandler.precioProducto(cve_cat_producto);
                    precio11.setText("" + precio_unitario);
                }
                if (opProducto11.length() == 0) {
                    precio11.setText("" + 0.0);
                }

                if (opProducto12.length() >= 1) {
                    String[] separated = opProducto12.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Validacion para la existencia en almacen de mano
                    if(cant12.toString() != "0" && cant12.toString() != "") {
                        if (tipo_venta.toString().equals("Mano") || tipo_venta.toString().equals("Extendido")) {
                            //Invocamos el metodo para valida que haya exitencia de mano
                            String cve_cliente = "";
                            String[] separatedCliente = clienteDato.split("-");
                            cve_cliente = separatedCliente[0];

                            Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                            if(paquete == false) {
                                String existencia = dbHandler.existenciaMano(cve_cat_producto, cve_cliente);

                            /*
                            Toast toast = Toast.makeText(getApplicationContext(), "existencia "+ cve_cliente +"", Toast.LENGTH_SHORT);
                            toast.show();
                            return;
                            */

                                Integer cantEnt = Integer.valueOf(cant12.getText().toString());
                                Integer existenciaEnt = Integer.parseInt(existencia);

                                if (existenciaEnt < cantEnt) {
                                    producto12.setText("");
                                    precio12.setText("" + 0.0);
                                    desc12.setText("");
                                    cant12.setText("" + 0);
                                    total12.setText("");
                                    Toast toast = Toast.makeText(getApplicationContext(), "El producto en la partida 12 no tiene suficiente existencia en su almacen", Toast.LENGTH_SHORT);
                                    toast.show();
                                    return;
                                }
                            }


                        }
                    }//fin Validacion para la existencia en almacen de mano

                    //Invocamos el mettodo validaProductosFamilia para validar que los productos no se convinen de distintas familias
                    Boolean valida_familia = dbHandler.validaProductosFamilia(cve_cat_producto, familia);
                    if (valida_familia == false) {
                        producto12.setText("");
                        precio12.setText(""+0.0);
                        desc12.setText("");
                        cant12.setText(""+0);
                        total12.setText("");
                        Toast toast = Toast.makeText(getApplicationContext(), "El producto en la partida 12 no corresponde ala familia que seleccionaste", Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }

                    //Invocamos el metodo para sacar el precio unitario del producto
                    Double precio_unitario = dbHandler.precioProducto(cve_cat_producto);
                    precio12.setText("" + precio_unitario);
                }
                if (opProducto12.length() == 0) {
                    precio12.setText("" + 0.0);
                }

                if (opProducto13.length() >= 1) {
                    String[] separated = opProducto13.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Validacion para la existencia en almacen de mano
                    if(cant13.toString() != "0" && cant13.toString() != "") {
                        if (tipo_venta.toString().equals("Mano") || tipo_venta.toString().equals("Extendido")) {
                            //Invocamos el metodo para valida que haya exitencia de mano
                            String cve_cliente = "";
                            String[] separatedCliente = clienteDato.split("-");
                            cve_cliente = separatedCliente[0];

                            Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                            if(paquete == false) {
                                String existencia = dbHandler.existenciaMano(cve_cat_producto, cve_cliente);

                            /*
                            Toast toast = Toast.makeText(getApplicationContext(), "existencia "+ cve_cliente +"", Toast.LENGTH_SHORT);
                            toast.show();
                            return;
                            */

                                Integer cantEnt = Integer.valueOf(cant13.getText().toString());
                                Integer existenciaEnt = Integer.parseInt(existencia);

                                if (existenciaEnt < cantEnt) {
                                    producto13.setText("");
                                    precio13.setText("" + 0.0);
                                    desc13.setText("");
                                    cant13.setText("" + 0);
                                    total13.setText("");
                                    Toast toast = Toast.makeText(getApplicationContext(), "El producto en la partida 13 no tiene suficiente existencia en su almacen", Toast.LENGTH_SHORT);
                                    toast.show();
                                    return;
                                }
                            }


                        }
                    }//fin Validacion para la existencia en almacen de mano

                    //Invocamos el mettodo validaProductosFamilia para validar que los productos no se convinen de distintas familias
                    Boolean valida_familia = dbHandler.validaProductosFamilia(cve_cat_producto, familia);
                    if (valida_familia == false) {
                        producto13.setText("");
                        precio13.setText(""+0.0);
                        desc13.setText("");
                        cant13.setText(""+0);
                        total13.setText("");
                        Toast toast = Toast.makeText(getApplicationContext(), "El producto en la partida 13 no corresponde ala familia que seleccionaste", Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }

                    //Invocamos el metodo para sacar el precio unitario del producto
                    Double precio_unitario = dbHandler.precioProducto(cve_cat_producto);
                    precio13.setText("" + precio_unitario);
                }
                if (opProducto13.length() == 0) {
                    precio13.setText("" + 0.0);
                }

                if (opProducto14.length() >= 1) {
                    String[] separated = opProducto14.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Validacion para la existencia en almacen de mano
                    if(cant14.toString() != "0" && cant14.toString() != "") {
                        if (tipo_venta.toString().equals("Mano") || tipo_venta.toString().equals("Extendido")) {
                            //Invocamos el metodo para valida que haya exitencia de mano
                            String cve_cliente = "";
                            String[] separatedCliente = clienteDato.split("-");
                            cve_cliente = separatedCliente[0];

                            Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                            if(paquete == false) {
                                String existencia = dbHandler.existenciaMano(cve_cat_producto, cve_cliente);

                            /*
                            Toast toast = Toast.makeText(getApplicationContext(), "existencia "+ cve_cliente +"", Toast.LENGTH_SHORT);
                            toast.show();
                            return;
                            */

                                Integer cantEnt = Integer.valueOf(cant14.getText().toString());
                                Integer existenciaEnt = Integer.parseInt(existencia);

                                if (existenciaEnt < cantEnt) {
                                    producto14.setText("");
                                    precio14.setText("" + 0.0);
                                    desc14.setText("");
                                    cant14.setText("" + 0);
                                    total14.setText("");
                                    Toast toast = Toast.makeText(getApplicationContext(), "El producto en la partida 14 no tiene suficiente existencia en su almacen", Toast.LENGTH_SHORT);
                                    toast.show();
                                    return;
                                }
                            }


                        }
                    }//fin Validacion para la existencia en almacen de mano

                    //Invocamos el mettodo validaProductosFamilia para validar que los productos no se convinen de distintas familias
                    Boolean valida_familia = dbHandler.validaProductosFamilia(cve_cat_producto, familia);
                    if (valida_familia == false) {
                        producto14.setText("");
                        precio14.setText(""+0.0);
                        desc14.setText("");
                        cant14.setText(""+0);
                        total14.setText("");
                        Toast toast = Toast.makeText(getApplicationContext(), "El producto en la partida 14 no corresponde ala familia que seleccionaste", Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }

                    //Invocamos el metodo para sacar el precio unitario del producto
                    Double precio_unitario = dbHandler.precioProducto(cve_cat_producto);
                    precio14.setText("" + precio_unitario);
                }
                if (opProducto14.length() == 0) {
                    precio14.setText("" + 0.0);
                }

                if (opProducto15.length() >= 1) {
                    String[] separated = opProducto15.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Validacion para la existencia en almacen de mano
                    if(cant15.toString() != "0" && cant15.toString() != "") {
                        if (tipo_venta.toString().equals("Mano") || tipo_venta.toString().equals("Extendido")) {
                            //Invocamos el metodo para valida que haya exitencia de mano
                            String cve_cliente = "";
                            String[] separatedCliente = clienteDato.split("-");
                            cve_cliente = separatedCliente[0];

                            Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                            if(paquete == false) {
                                String existencia = dbHandler.existenciaMano(cve_cat_producto, cve_cliente);

                            /*
                            Toast toast = Toast.makeText(getApplicationContext(), "existencia "+ cve_cliente +"", Toast.LENGTH_SHORT);
                            toast.show();
                            return;
                            */

                                Integer cantEnt = Integer.valueOf(cant15.getText().toString());
                                Integer existenciaEnt = Integer.parseInt(existencia);

                                if (existenciaEnt < cantEnt) {
                                    producto15.setText("");
                                    precio15.setText("" + 0.0);
                                    desc15.setText("");
                                    cant15.setText("" + 0);
                                    total15.setText("");
                                    Toast toast = Toast.makeText(getApplicationContext(), "El producto en la partida 15 no tiene suficiente existencia en su almacen", Toast.LENGTH_SHORT);
                                    toast.show();
                                    return;
                                }
                            }


                        }
                    }//fin Validacion para la existencia en almacen de mano

                    //Invocamos el mettodo validaProductosFamilia para validar que los productos no se convinen de distintas familias
                    Boolean valida_familia = dbHandler.validaProductosFamilia(cve_cat_producto, familia);
                    if (valida_familia == false) {
                        producto15.setText("");
                        precio15.setText(""+0.0);
                        desc15.setText("");
                        cant15.setText(""+0);
                        total15.setText("");
                        Toast toast = Toast.makeText(getApplicationContext(), "El producto en la partida 15 no corresponde ala familia que seleccionaste", Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }

                    //Invocamos el metodo para sacar el precio unitario del producto
                    Double precio_unitario = dbHandler.precioProducto(cve_cat_producto);
                    precio15.setText("" + precio_unitario);
                }
                if (opProducto15.length() == 0) {
                    precio15.setText("" + 0.0);
                }

                if (opProducto16.length() >= 1) {
                    String[] separated = opProducto16.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Validacion para la existencia en almacen de mano
                    if(cant16.toString() != "0" && cant16.toString() != "") {
                        if (tipo_venta.toString().equals("Mano") || tipo_venta.toString().equals("Extendido")) {
                            //Invocamos el metodo para valida que haya exitencia de mano
                            String cve_cliente = "";
                            String[] separatedCliente = clienteDato.split("-");
                            cve_cliente = separatedCliente[0];

                            Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                            if(paquete == false) {
                                String existencia = dbHandler.existenciaMano(cve_cat_producto, cve_cliente);

                            /*
                            Toast toast = Toast.makeText(getApplicationContext(), "existencia "+ cve_cliente +"", Toast.LENGTH_SHORT);
                            toast.show();
                            return;
                            */

                                Integer cantEnt = Integer.valueOf(cant16.getText().toString());
                                Integer existenciaEnt = Integer.parseInt(existencia);

                                if (existenciaEnt < cantEnt) {
                                    producto16.setText("");
                                    precio16.setText("" + 0.0);
                                    desc16.setText("");
                                    cant16.setText("" + 0);
                                    total16.setText("");
                                    Toast toast = Toast.makeText(getApplicationContext(), "El producto en la partida 16 no tiene suficiente existencia en su almacen", Toast.LENGTH_SHORT);
                                    toast.show();
                                    return;
                                }
                            }


                        }
                    }//fin Validacion para la existencia en almacen de mano

                    //Invocamos el mettodo validaProductosFamilia para validar que los productos no se convinen de distintas familias
                    Boolean valida_familia = dbHandler.validaProductosFamilia(cve_cat_producto, familia);
                    if (valida_familia == false) {
                        producto16.setText("");
                        precio16.setText(""+0.0);
                        desc16.setText("");
                        cant16.setText(""+0);
                        total16.setText("");
                        Toast toast = Toast.makeText(getApplicationContext(), "El producto en la partida 16 no corresponde ala familia que seleccionaste", Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }

                    //Invocamos el metodo para sacar el precio unitario del producto
                    Double precio_unitario = dbHandler.precioProducto(cve_cat_producto);
                    precio16.setText("" + precio_unitario);
                }
                if (opProducto16.length() == 0) {
                    precio16.setText("" + 0.0);
                }

                if (opProducto17.length() >= 1) {
                    String[] separated = opProducto17.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Validacion para la existencia en almacen de mano
                    if(cant17.toString() != "0" && cant17.toString() != "") {
                        if (tipo_venta.toString().equals("Mano") || tipo_venta.toString().equals("Extendido")) {
                            //Invocamos el metodo para valida que haya exitencia de mano
                            String cve_cliente = "";
                            String[] separatedCliente = clienteDato.split("-");
                            cve_cliente = separatedCliente[0];

                            Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                            if(paquete == false) {
                                String existencia = dbHandler.existenciaMano(cve_cat_producto, cve_cliente);

                            /*
                            Toast toast = Toast.makeText(getApplicationContext(), "existencia "+ cve_cliente +"", Toast.LENGTH_SHORT);
                            toast.show();
                            return;
                            */

                                Integer cantEnt = Integer.valueOf(cant17.getText().toString());
                                Integer existenciaEnt = Integer.parseInt(existencia);

                                if (existenciaEnt < cantEnt) {
                                    producto17.setText("");
                                    precio17.setText("" + 0.0);
                                    desc17.setText("");
                                    cant17.setText("" + 0);
                                    total17.setText("");
                                    Toast toast = Toast.makeText(getApplicationContext(), "El producto en la partida 17 no tiene suficiente existencia en su almacen", Toast.LENGTH_SHORT);
                                    toast.show();
                                    return;
                                }
                            }


                        }
                    }//fin Validacion para la existencia en almacen de mano

                    //Invocamos el mettodo validaProductosFamilia para validar que los productos no se convinen de distintas familias
                    Boolean valida_familia = dbHandler.validaProductosFamilia(cve_cat_producto, familia);
                    if (valida_familia == false) {
                        producto17.setText("");
                        precio17.setText(""+0.0);
                        desc17.setText("");
                        cant17.setText(""+0);
                        total17.setText("");
                        Toast toast = Toast.makeText(getApplicationContext(), "El producto en la partida 17 no corresponde ala familia que seleccionaste", Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }

                    //Invocamos el metodo para sacar el precio unitario del producto
                    Double precio_unitario = dbHandler.precioProducto(cve_cat_producto);
                    precio17.setText("" + precio_unitario);
                }
                if (opProducto17.length() == 0) {
                    precio17.setText("" + 0.0);
                }

                if (opProducto18.length() >= 1) {
                    String[] separated = opProducto18.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Validacion para la existencia en almacen de mano
                    if(cant18.toString() != "0" && cant18.toString() != "") {
                        if (tipo_venta.toString().equals("Mano") || tipo_venta.toString().equals("Extendido")) {
                            //Invocamos el metodo para valida que haya exitencia de mano
                            String cve_cliente = "";
                            String[] separatedCliente = clienteDato.split("-");
                            cve_cliente = separatedCliente[0];

                            Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                            if(paquete == false) {
                                String existencia = dbHandler.existenciaMano(cve_cat_producto, cve_cliente);

                            /*
                            Toast toast = Toast.makeText(getApplicationContext(), "existencia "+ cve_cliente +"", Toast.LENGTH_SHORT);
                            toast.show();
                            return;
                            */

                                Integer cantEnt = Integer.valueOf(cant18.getText().toString());
                                Integer existenciaEnt = Integer.parseInt(existencia);

                                if (existenciaEnt < cantEnt) {
                                    producto18.setText("");
                                    precio18.setText("" + 0.0);
                                    desc18.setText("");
                                    cant18.setText("" + 0);
                                    total18.setText("");
                                    Toast toast = Toast.makeText(getApplicationContext(), "El producto en la partida 18 no tiene suficiente existencia en su almacen", Toast.LENGTH_SHORT);
                                    toast.show();
                                    return;
                                }
                            }


                        }
                    }//fin Validacion para la existencia en almacen de mano

                    //Invocamos el mettodo validaProductosFamilia para validar que los productos no se convinen de distintas familias
                    Boolean valida_familia = dbHandler.validaProductosFamilia(cve_cat_producto, familia);
                    if (valida_familia == false) {
                        producto18.setText("");
                        precio18.setText(""+0.0);
                        desc18.setText("");
                        cant18.setText(""+0);
                        total18.setText("");
                        Toast toast = Toast.makeText(getApplicationContext(), "El producto en la partida 18 no corresponde ala familia que seleccionaste", Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }

                    //Invocamos el metodo para sacar el precio unitario del producto
                    Double precio_unitario = dbHandler.precioProducto(cve_cat_producto);
                    precio18.setText("" + precio_unitario);
                }
                if (opProducto18.length() == 0) {
                    precio18.setText("" + 0.0);
                }

                if (opProducto19.length() >= 1) {
                    String[] separated = opProducto19.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Validacion para la existencia en almacen de mano
                    if(cant19.toString() != "0" && cant19.toString() != "") {
                        if (tipo_venta.toString().equals("Mano") || tipo_venta.toString().equals("Extendido")) {
                            //Invocamos el metodo para valida que haya exitencia de mano
                            String cve_cliente = "";
                            String[] separatedCliente = clienteDato.split("-");
                            cve_cliente = separatedCliente[0];

                            Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                            if(paquete == false) {
                                String existencia = dbHandler.existenciaMano(cve_cat_producto, cve_cliente);

                            /*
                            Toast toast = Toast.makeText(getApplicationContext(), "existencia "+ cve_cliente +"", Toast.LENGTH_SHORT);
                            toast.show();
                            return;
                            */

                                Integer cantEnt = Integer.valueOf(cant19.getText().toString());
                                Integer existenciaEnt = Integer.parseInt(existencia);

                                if (existenciaEnt < cantEnt) {
                                    producto19.setText("");
                                    precio19.setText("" + 0.0);
                                    desc19.setText("");
                                    cant19.setText("" + 0);
                                    total19.setText("");
                                    Toast toast = Toast.makeText(getApplicationContext(), "El producto en la partida 19 no tiene suficiente existencia en su almacen", Toast.LENGTH_SHORT);
                                    toast.show();
                                    return;
                                }
                            }


                        }
                    }//fin Validacion para la existencia en almacen de mano

                    //Invocamos el mettodo validaProductosFamilia para validar que los productos no se convinen de distintas familias
                    Boolean valida_familia = dbHandler.validaProductosFamilia(cve_cat_producto, familia);
                    if (valida_familia == false) {
                        producto19.setText("");
                        precio19.setText(""+0.0);
                        desc19.setText("");
                        cant19.setText(""+0);
                        total19.setText("");
                        Toast toast = Toast.makeText(getApplicationContext(), "El producto en la partida 19 no corresponde ala familia que seleccionaste", Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }

                    //Invocamos el metodo para sacar el precio unitario del producto
                    Double precio_unitario = dbHandler.precioProducto(cve_cat_producto);
                    precio19.setText("" + precio_unitario);
                }
                if (opProducto19.length() == 0) {
                    precio19.setText("" + 0.0);
                }

                if (opProducto20.length() >= 1) {
                    String[] separated = opProducto20.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Validacion para la existencia en almacen de mano
                    if(cant20.toString() != "0" && cant20.toString() != "") {
                        if (tipo_venta.toString().equals("Mano") || tipo_venta.toString().equals("Extendido")) {
                            //Invocamos el metodo para valida que haya exitencia de mano
                            String cve_cliente = "";
                            String[] separatedCliente = clienteDato.split("-");
                            cve_cliente = separatedCliente[0];

                            Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                            if(paquete == false) {
                                String existencia = dbHandler.existenciaMano(cve_cat_producto, cve_cliente);

                            /*
                            Toast toast = Toast.makeText(getApplicationContext(), "existencia "+ cve_cliente +"", Toast.LENGTH_SHORT);
                            toast.show();
                            return;
                            */

                                Integer cantEnt = Integer.valueOf(cant20.getText().toString());
                                Integer existenciaEnt = Integer.parseInt(existencia);

                                if (existenciaEnt < cantEnt) {
                                    producto20.setText("");
                                    precio20.setText("" + 0.0);
                                    desc20.setText("");
                                    cant20.setText("" + 0);
                                    total20.setText("");
                                    Toast toast = Toast.makeText(getApplicationContext(), "El producto en la partida 20 no tiene suficiente existencia en su almacen", Toast.LENGTH_SHORT);
                                    toast.show();
                                    return;
                                }
                            }


                        }
                    }//fin Validacion para la existencia en almacen de mano

                    //Invocamos el mettodo validaProductosFamilia para validar que los productos no se convinen de distintas familias
                    Boolean valida_familia = dbHandler.validaProductosFamilia(cve_cat_producto, familia);
                    if (valida_familia == false) {
                        producto20.setText("");
                        precio20.setText(""+0.0);
                        desc20.setText("");
                        cant20.setText(""+0);
                        total20.setText("");
                        Toast toast = Toast.makeText(getApplicationContext(), "El producto en la partida 20 no corresponde ala familia que seleccionaste", Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }

                    //Invocamos el metodo para sacar el precio unitario del producto
                    Double precio_unitario = dbHandler.precioProducto(cve_cat_producto);
                    precio20.setText("" + precio_unitario);
                }
                if (opProducto20.length() == 0) {
                    precio20.setText("" + 0.0);
                }

                if (opProducto21.length() >= 1) {
                    String[] separated = opProducto21.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Validacion para la existencia en almacen de mano
                    if(cant21.toString() != "0" && cant21.toString() != "") {
                        if (tipo_venta.toString().equals("Mano") || tipo_venta.toString().equals("Extendido")) {
                            //Invocamos el metodo para valida que haya exitencia de mano
                            String cve_cliente = "";
                            String[] separatedCliente = clienteDato.split("-");
                            cve_cliente = separatedCliente[0];

                            Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                            if(paquete == false) {
                                String existencia = dbHandler.existenciaMano(cve_cat_producto, cve_cliente);

                            /*
                            Toast toast = Toast.makeText(getApplicationContext(), "existencia "+ cve_cliente +"", Toast.LENGTH_SHORT);
                            toast.show();
                            return;
                            */

                                Integer cantEnt = Integer.valueOf(cant21.getText().toString());
                                Integer existenciaEnt = Integer.parseInt(existencia);

                                if (existenciaEnt < cantEnt) {
                                    producto21.setText("");
                                    precio21.setText("" + 0.0);
                                    desc21.setText("");
                                    cant21.setText("" + 0);
                                    total21.setText("");
                                    Toast toast = Toast.makeText(getApplicationContext(), "El producto en la partida 21 no tiene suficiente existencia en su almacen", Toast.LENGTH_SHORT);
                                    toast.show();
                                    return;
                                }
                            }


                        }
                    }//fin Validacion para la existencia en almacen de mano

                    //Invocamos el mettodo validaProductosFamilia para validar que los productos no se convinen de distintas familias
                    Boolean valida_familia = dbHandler.validaProductosFamilia(cve_cat_producto, familia);
                    if (valida_familia == false) {
                        producto21.setText("");
                        precio21.setText(""+0.0);
                        desc21.setText("");
                        cant21.setText(""+0);
                        total21.setText("");
                        Toast toast = Toast.makeText(getApplicationContext(), "El producto en la partida 21 no corresponde ala familia que seleccionaste", Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }

                    //Invocamos el metodo para sacar el precio unitario del producto
                    Double precio_unitario = dbHandler.precioProducto(cve_cat_producto);
                    precio21.setText("" + precio_unitario);
                }
                if (opProducto21.length() == 0) {
                    precio21.setText("" + 0.0);
                }

                if (opProducto22.length() >= 1) {
                    String[] separated = opProducto22.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Validacion para la existencia en almacen de mano
                    if(cant22.toString() != "0" && cant22.toString() != "") {
                        if (tipo_venta.toString().equals("Mano") || tipo_venta.toString().equals("Extendido")) {
                            //Invocamos el metodo para valida que haya exitencia de mano
                            String cve_cliente = "";
                            String[] separatedCliente = clienteDato.split("-");
                            cve_cliente = separatedCliente[0];

                            Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                            if(paquete == false) {
                                String existencia = dbHandler.existenciaMano(cve_cat_producto, cve_cliente);

                            /*
                            Toast toast = Toast.makeText(getApplicationContext(), "existencia "+ cve_cliente +"", Toast.LENGTH_SHORT);
                            toast.show();
                            return;
                            */

                                Integer cantEnt = Integer.valueOf(cant22.getText().toString());
                                Integer existenciaEnt = Integer.parseInt(existencia);

                                if (existenciaEnt < cantEnt) {
                                    producto22.setText("");
                                    precio22.setText("" + 0.0);
                                    desc22.setText("");
                                    cant22.setText("" + 0);
                                    total22.setText("");
                                    Toast toast = Toast.makeText(getApplicationContext(), "El producto en la partida 22 no tiene suficiente existencia en su almacen", Toast.LENGTH_SHORT);
                                    toast.show();
                                    return;
                                }
                            }


                        }
                    }//fin Validacion para la existencia en almacen de mano

                    //Invocamos el mettodo validaProductosFamilia para validar que los productos no se convinen de distintas familias
                    Boolean valida_familia = dbHandler.validaProductosFamilia(cve_cat_producto, familia);
                    if (valida_familia == false) {
                        producto22.setText("");
                        precio22.setText(""+0.0);
                        desc22.setText("");
                        cant22.setText(""+0);
                        total22.setText("");
                        Toast toast = Toast.makeText(getApplicationContext(), "El producto en la partida 22 no corresponde ala familia que seleccionaste", Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }

                    //Invocamos el metodo para sacar el precio unitario del producto
                    Double precio_unitario = dbHandler.precioProducto(cve_cat_producto);
                    precio22.setText("" + precio_unitario);
                }
                if (opProducto22.length() == 0) {
                    precio22.setText("" + 0.0);
                }

                if (opProducto23.length() >= 1) {
                    String[] separated = opProducto23.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el mettodo validaProductosFamilia para validar que los productos no se convinen de distintas familias
                    Boolean valida_familia = dbHandler.validaProductosFamilia(cve_cat_producto, familia);
                    if (valida_familia == false) {
                        producto23.setText("");
                        precio23.setText(""+0.0);
                        desc23.setText("");
                        cant23.setText(""+0);
                        total23.setText("");
                        Toast toast = Toast.makeText(getApplicationContext(), "El producto en la partida 23 no corresponde ala familia que seleccionaste", Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }

                    //Invocamos el metodo para sacar el precio unitario del producto
                    Double precio_unitario = dbHandler.precioProducto(cve_cat_producto);
                    precio23.setText("" + precio_unitario);
                }
                if (opProducto23.length() == 0) {
                    precio23.setText("" + 0.0);
                }

                if (opProducto24.length() >= 1) {
                    String[] separated = opProducto24.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el mettodo validaProductosFamilia para validar que los productos no se convinen de distintas familias
                    Boolean valida_familia = dbHandler.validaProductosFamilia(cve_cat_producto, familia);
                    if (valida_familia == false) {
                        producto24.setText("");
                        precio24.setText(""+0.0);
                        desc24.setText("");
                        cant24.setText(""+0);
                        total24.setText("");
                        Toast toast = Toast.makeText(getApplicationContext(), "El producto en la partida 24 no corresponde ala familia que seleccionaste", Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }

                    //Invocamos el metodo para sacar el precio unitario del producto
                    Double precio_unitario = dbHandler.precioProducto(cve_cat_producto);
                    precio24.setText("" + precio_unitario);
                }
                if (opProducto24.length() == 0) {
                    precio24.setText("" + 0.0);
                }

                if (opProducto25.length() >= 1) {
                    String[] separated = opProducto25.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el mettodo validaProductosFamilia para validar que los productos no se convinen de distintas familias
                    Boolean valida_familia = dbHandler.validaProductosFamilia(cve_cat_producto, familia);
                    if (valida_familia == false) {
                        producto25.setText("");
                        precio25.setText(""+0.0);
                        desc25.setText("");
                        cant25.setText(""+0);
                        total25.setText("");
                        Toast toast = Toast.makeText(getApplicationContext(), "El producto en la partida 25 no corresponde ala familia que seleccionaste", Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }

                    //Invocamos el metodo para sacar el precio unitario del producto
                    Double precio_unitario = dbHandler.precioProducto(cve_cat_producto);
                    precio25.setText("" + precio_unitario);
                }
                if (opProducto25.length() == 0) {
                    precio25.setText("" + 0.0);
                }

                if (opProducto26.length() >= 1) {
                    String[] separated = opProducto26.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el mettodo validaProductosFamilia para validar que los productos no se convinen de distintas familias
                    Boolean valida_familia = dbHandler.validaProductosFamilia(cve_cat_producto, familia);
                    if (valida_familia == false) {
                        producto26.setText("");
                        precio26.setText(""+0.0);
                        desc26.setText("");
                        cant26.setText(""+0);
                        total26.setText("");
                        Toast toast = Toast.makeText(getApplicationContext(), "El producto en la partida 26 no corresponde ala familia que seleccionaste", Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }

                    //Invocamos el metodo para sacar el precio unitario del producto
                    Double precio_unitario = dbHandler.precioProducto(cve_cat_producto);
                    precio26.setText("" + precio_unitario);
                }
                if (opProducto26.length() == 0) {
                    precio26.setText("" + 0.0);
                }

                if (opProducto27.length() >= 1) {
                    String[] separated = opProducto27.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el mettodo validaProductosFamilia para validar que los productos no se convinen de distintas familias
                    Boolean valida_familia = dbHandler.validaProductosFamilia(cve_cat_producto, familia);
                    if (valida_familia == false) {
                        producto27.setText("");
                        precio27.setText(""+0.0);
                        desc27.setText("");
                        cant27.setText(""+0);
                        total27.setText("");
                        Toast toast = Toast.makeText(getApplicationContext(), "El producto en la partida 27 no corresponde ala familia que seleccionaste", Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }

                    //Invocamos el metodo para sacar el precio unitario del producto
                    Double precio_unitario = dbHandler.precioProducto(cve_cat_producto);
                    precio27.setText("" + precio_unitario);
                }
                if (opProducto27.length() == 0) {
                    precio27.setText("" + 0.0);
                }

                if (opProducto28.length() >= 1) {
                    String[] separated = opProducto28.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el mettodo validaProductosFamilia para validar que los productos no se convinen de distintas familias
                    Boolean valida_familia = dbHandler.validaProductosFamilia(cve_cat_producto, familia);
                    if (valida_familia == false) {
                        producto28.setText("");
                        precio28.setText(""+0.0);
                        desc28.setText("");
                        cant28.setText(""+0);
                        total28.setText("");
                        Toast toast = Toast.makeText(getApplicationContext(), "El producto en la partida 28 no corresponde ala familia que seleccionaste", Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }

                    //Invocamos el metodo para sacar el precio unitario del producto
                    Double precio_unitario = dbHandler.precioProducto(cve_cat_producto);
                    precio28.setText("" + precio_unitario);
                }
                if (opProducto28.length() == 0) {
                    precio28.setText("" + 0.0);
                }

                if (opProducto29.length() >= 1) {
                    String[] separated = opProducto29.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el mettodo validaProductosFamilia para validar que los productos no se convinen de distintas familias
                    Boolean valida_familia = dbHandler.validaProductosFamilia(cve_cat_producto, familia);
                    if (valida_familia == false) {
                        producto29.setText("");
                        precio29.setText(""+0.0);
                        desc29.setText("");
                        cant29.setText(""+0);
                        total29.setText("");
                        Toast toast = Toast.makeText(getApplicationContext(), "El producto en la partida 29 no corresponde ala familia que seleccionaste", Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }

                    //Invocamos el metodo para sacar el precio unitario del producto
                    Double precio_unitario = dbHandler.precioProducto(cve_cat_producto);
                    precio29.setText("" + precio_unitario);
                }
                if (opProducto29.length() == 0) {
                    precio29.setText("" + 0.0);
                }

                if (opProducto30.length() >= 1) {
                    String[] separated = opProducto30.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el mettodo validaProductosFamilia para validar que los productos no se convinen de distintas familias
                    Boolean valida_familia = dbHandler.validaProductosFamilia(cve_cat_producto, familia);
                    if (valida_familia == false) {
                        producto30.setText("");
                        precio30.setText(""+0.0);
                        desc30.setText("");
                        cant30.setText(""+0);
                        total30.setText("");
                        Toast toast = Toast.makeText(getApplicationContext(), "El producto en la partida 30 no corresponde ala familia que seleccionaste", Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }

                    //Invocamos el metodo para sacar el precio unitario del producto
                    Double precio_unitario = dbHandler.precioProducto(cve_cat_producto);
                    precio30.setText("" + precio_unitario);
                }
                if (opProducto30.length() == 0) {
                    precio30.setText("" + 0.0);
                }

                if (opProducto31.length() >= 1) {
                    String[] separated = opProducto31.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el mettodo validaProductosFamilia para validar que los productos no se convinen de distintas familias
                    Boolean valida_familia = dbHandler.validaProductosFamilia(cve_cat_producto, familia);
                    if (valida_familia == false) {
                        producto31.setText("");
                        precio31.setText(""+0.0);
                        desc31.setText("");
                        cant31.setText(""+0);
                        total31.setText("");
                        Toast toast = Toast.makeText(getApplicationContext(), "El producto en la partida 31 no corresponde ala familia que seleccionaste", Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }

                    //Invocamos el metodo para sacar el precio unitario del producto
                    Double precio_unitario = dbHandler.precioProducto(cve_cat_producto);
                    precio31.setText("" + precio_unitario);

                }
                if (opProducto31.length() == 0) {
                    precio31.setText("" + 0.0);
                }

                if (opProducto32.length() >= 1) {
                    String[] separated = opProducto32.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el mettodo validaProductosFamilia para validar que los productos no se convinen de distintas familias
                    Boolean valida_familia = dbHandler.validaProductosFamilia(cve_cat_producto, familia);
                    if (valida_familia == false) {
                        producto32.setText("");
                        precio32.setText(""+0.0);
                        desc32.setText("");
                        cant32.setText(""+0);
                        total32.setText("");
                        Toast toast = Toast.makeText(getApplicationContext(), "El producto en la partida 32 no corresponde ala familia que seleccionaste", Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }

                    //Invocamos el metodo para sacar el precio unitario del producto
                    Double precio_unitario = dbHandler.precioProducto(cve_cat_producto);
                    precio32.setText("" + precio_unitario);
                }
                if (opProducto32.length() == 0) {
                    precio32.setText("" + 0.0);
                }

                if (opProducto33.length() >= 1) {
                    String[] separated = opProducto33.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el mettodo validaProductosFamilia para validar que los productos no se convinen de distintas familias
                    Boolean valida_familia = dbHandler.validaProductosFamilia(cve_cat_producto, familia);
                    if (valida_familia == false) {
                        producto33.setText("");
                        precio33.setText(""+0.0);
                        desc33.setText("");
                        cant33.setText(""+0);
                        total33.setText("");
                        Toast toast = Toast.makeText(getApplicationContext(), "El producto en la partida 33 no corresponde ala familia que seleccionaste", Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }

                    //Invocamos el metodo para sacar el precio unitario del producto
                    Double precio_unitario = dbHandler.precioProducto(cve_cat_producto);
                    precio33.setText("" + precio_unitario);
                }
                if (opProducto33.length() == 0) {
                    precio33.setText("" + 0.0);
                }

                if (opProducto34.length() >= 1) {
                    String[] separated = opProducto34.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el mettodo validaProductosFamilia para validar que los productos no se convinen de distintas familias
                    Boolean valida_familia = dbHandler.validaProductosFamilia(cve_cat_producto, familia);
                    if (valida_familia == false) {
                        producto34.setText("");
                        precio34.setText(""+0.0);
                        desc34.setText("");
                        cant34.setText(""+0);
                        total34.setText("");
                        Toast toast = Toast.makeText(getApplicationContext(), "El producto en la partida 34 no corresponde ala familia que seleccionaste", Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }

                    //Invocamos el metodo para sacar el precio unitario del producto
                    Double precio_unitario = dbHandler.precioProducto(cve_cat_producto);
                    precio34.setText("" + precio_unitario);
                }
                if (opProducto34.length() == 0) {
                    precio34.setText("" + 0.0);
                }

                if (opProducto35.length() >= 1) {
                    String[] separated = opProducto35.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el mettodo validaProductosFamilia para validar que los productos no se convinen de distintas familias
                    Boolean valida_familia = dbHandler.validaProductosFamilia(cve_cat_producto, familia);
                    if (valida_familia == false) {
                        producto35.setText("");
                        precio35.setText(""+0.0);
                        desc35.setText("");
                        cant35.setText(""+0);
                        total35.setText("");
                        Toast toast = Toast.makeText(getApplicationContext(), "El producto en la partida 35 no corresponde ala familia que seleccionaste", Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }

                    //Invocamos el metodo para sacar el precio unitario del producto
                    Double precio_unitario = dbHandler.precioProducto(cve_cat_producto);
                    precio35.setText("" + precio_unitario);
                }
                if (opProducto35.length() == 0) {
                    precio35.setText("" + 0.0);
                }

                if (opProducto36.length() >= 1) {
                    String[] separated = opProducto36.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el mettodo validaProductosFamilia para validar que los productos no se convinen de distintas familias
                    Boolean valida_familia = dbHandler.validaProductosFamilia(cve_cat_producto, familia);
                    if (valida_familia == false) {
                        producto36.setText("");
                        precio36.setText(""+0.0);
                        desc36.setText("");
                        cant36.setText(""+0);
                        total36.setText("");
                        Toast toast = Toast.makeText(getApplicationContext(), "El producto en la partida 36 no corresponde ala familia que seleccionaste", Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }

                    //Invocamos el metodo para sacar el precio unitario del producto
                    Double precio_unitario = dbHandler.precioProducto(cve_cat_producto);
                    precio36.setText("" + precio_unitario);
                }
                if (opProducto36.length() == 0) {
                    precio36.setText("" + 0.0);
                }

                if (opProducto37.length() >= 1) {
                    String[] separated = opProducto37.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el mettodo validaProductosFamilia para validar que los productos no se convinen de distintas familias
                    Boolean valida_familia = dbHandler.validaProductosFamilia(cve_cat_producto, familia);
                    if (valida_familia == false) {
                        producto37.setText("");
                        precio37.setText(""+0.0);
                        desc37.setText("");
                        cant37.setText(""+0);
                        total37.setText("");
                        Toast toast = Toast.makeText(getApplicationContext(), "El producto en la partida 37 no corresponde ala familia que seleccionaste", Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }

                    //Invocamos el metodo para sacar el precio unitario del producto
                    Double precio_unitario = dbHandler.precioProducto(cve_cat_producto);
                    precio37.setText("" + precio_unitario);
                }
                if (opProducto37.length() == 0) {
                    precio37.setText("" + 0.0);
                }

                if (opProducto38.length() >= 1) {
                    String[] separated = opProducto38.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el mettodo validaProductosFamilia para validar que los productos no se convinen de distintas familias
                    Boolean valida_familia = dbHandler.validaProductosFamilia(cve_cat_producto, familia);
                    if (valida_familia == false) {
                        producto38.setText("");
                        precio38.setText(""+0.0);
                        desc38.setText("");
                        cant38.setText(""+0);
                        total38.setText("");
                        Toast toast = Toast.makeText(getApplicationContext(), "El producto en la partida 38 no corresponde ala familia que seleccionaste", Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }

                    //Invocamos el metodo para sacar el precio unitario del producto
                    Double precio_unitario = dbHandler.precioProducto(cve_cat_producto);
                    precio38.setText("" + precio_unitario);
                }
                if (opProducto38.length() == 0) {
                    precio38.setText("" + 0.0);
                }

                if (opProducto39.length() >= 1) {
                    String[] separated = opProducto39.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el mettodo validaProductosFamilia para validar que los productos no se convinen de distintas familias
                    Boolean valida_familia = dbHandler.validaProductosFamilia(cve_cat_producto, familia);
                    if (valida_familia == false) {
                        producto39.setText("");
                        precio39.setText(""+0.0);
                        desc39.setText("");
                        cant39.setText(""+0);
                        total39.setText("");
                        Toast toast = Toast.makeText(getApplicationContext(), "El producto en la partida 39 no corresponde ala familia que seleccionaste", Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }

                    //Invocamos el metodo para sacar el precio unitario del producto
                    Double precio_unitario = dbHandler.precioProducto(cve_cat_producto);
                    precio39.setText("" + precio_unitario);
                }
                if (opProducto39.length() == 0) {
                    precio39.setText("" + 0.0);
                }

                if (opProducto40.length() >= 1) {
                    String[] separated = opProducto40.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

//Invocamos el mettodo validaProductosFamilia para validar que los productos no se convinen de distintas familias
                    Boolean valida_familia = dbHandler.validaProductosFamilia(cve_cat_producto, familia);
                    if (valida_familia == false) {
                        producto40.setText("");
                        precio40.setText(""+0.0);
                        desc40.setText("");
                        cant40.setText(""+0);
                        total40.setText("");
                        Toast toast = Toast.makeText(getApplicationContext(), "El producto en la partida 40 no corresponde ala familia que seleccionaste", Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }

                    //Invocamos el metodo para sacar el precio unitario del producto
                    Double precio_unitario = dbHandler.precioProducto(cve_cat_producto);
                    precio40.setText("" + precio_unitario);
                }
                if (opProducto40.length() == 0) {
                    precio40.setText("" + 0.0);
                }

                if (opProducto41.length() >= 1) {
                    String[] separated = opProducto41.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el mettodo validaProductosFamilia para validar que los productos no se convinen de distintas familias
                    Boolean valida_familia = dbHandler.validaProductosFamilia(cve_cat_producto, familia);
                    if (valida_familia == false) {
                        producto41.setText("");
                        precio41.setText(""+0.0);
                        desc41.setText("");
                        cant41.setText(""+0);
                        total41.setText("");
                        Toast toast = Toast.makeText(getApplicationContext(), "El producto en la partida 41 no corresponde ala familia que seleccionaste", Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }

                    //Invocamos el metodo para sacar el precio unitario del producto
                    Double precio_unitario = dbHandler.precioProducto(cve_cat_producto);
                    precio41.setText("" + precio_unitario);

                }
                if (opProducto41.length() == 0) {
                    precio41.setText("" + 0.0);
                }

                if (opProducto42.length() >= 1) {
                    String[] separated = opProducto42.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el mettodo validaProductosFamilia para validar que los productos no se convinen de distintas familias
                    Boolean valida_familia = dbHandler.validaProductosFamilia(cve_cat_producto, familia);
                    if (valida_familia == false) {
                        producto42.setText("");
                        precio42.setText(""+0.0);
                        desc42.setText("");
                        cant42.setText(""+0);
                        total42.setText("");
                        Toast toast = Toast.makeText(getApplicationContext(), "El producto en la partida 42 no corresponde ala familia que seleccionaste", Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }

                    //Invocamos el metodo para sacar el precio unitario del producto
                    Double precio_unitario = dbHandler.precioProducto(cve_cat_producto);
                    precio42.setText("" + precio_unitario);
                }
                if (opProducto42.length() == 0) {
                    precio42.setText("" + 0.0);
                }

                if (opProducto43.length() >= 1) {
                    String[] separated = opProducto43.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el mettodo validaProductosFamilia para validar que los productos no se convinen de distintas familias
                    Boolean valida_familia = dbHandler.validaProductosFamilia(cve_cat_producto, familia);
                    if (valida_familia == false) {
                        producto43.setText("");
                        precio43.setText(""+0.0);
                        desc43.setText("");
                        cant43.setText(""+0);
                        total43.setText("");
                        Toast toast = Toast.makeText(getApplicationContext(), "El producto en la partida 43 no corresponde ala familia que seleccionaste", Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }

                    //Invocamos el metodo para sacar el precio unitario del producto
                    Double precio_unitario = dbHandler.precioProducto(cve_cat_producto);
                    precio43.setText("" + precio_unitario);
                }
                if (opProducto43.length() == 0) {
                    precio43.setText("" + 0.0);
                }

                if (opProducto44.length() >= 1) {
                    String[] separated = opProducto44.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el mettodo validaProductosFamilia para validar que los productos no se convinen de distintas familias
                    Boolean valida_familia = dbHandler.validaProductosFamilia(cve_cat_producto, familia);
                    if (valida_familia == false) {
                        producto44.setText("");
                        precio44.setText(""+0.0);
                        desc44.setText("");
                        cant44.setText(""+0);
                        total44.setText("");
                        Toast toast = Toast.makeText(getApplicationContext(), "El producto en la partida 44 no corresponde ala familia que seleccionaste", Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }

                    //Invocamos el metodo para sacar el precio unitario del producto
                    Double precio_unitario = dbHandler.precioProducto(cve_cat_producto);
                    precio44.setText("" + precio_unitario);
                }
                if (opProducto44.length() == 0) {
                    precio44.setText("" + 0.0);
                }

                if (opProducto45.length() >= 1) {
                    String[] separated = opProducto45.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el mettodo validaProductosFamilia para validar que los productos no se convinen de distintas familias
                    Boolean valida_familia = dbHandler.validaProductosFamilia(cve_cat_producto, familia);
                    if (valida_familia == false) {
                        producto45.setText("");
                        precio45.setText(""+0.0);
                        desc45.setText("");
                        cant45.setText(""+0);
                        total45.setText("");
                        Toast toast = Toast.makeText(getApplicationContext(), "El producto en la partida 45 no corresponde ala familia que seleccionaste", Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }

                    //Invocamos el metodo para sacar el precio unitario del producto
                    Double precio_unitario = dbHandler.precioProducto(cve_cat_producto);
                    precio45.setText("" + precio_unitario);
                }
                if (opProducto45.length() == 0) {
                    precio45.setText("" + 0.0);
                }


                double opPrecio1 = Double.valueOf(precio1.getText().toString());
                double opCant1 = Double.valueOf(cant1.getText().toString());
                double opPrecio2 = Double.valueOf(precio2.getText().toString());
                double opCant2 = Double.valueOf(cant2.getText().toString());
                double opPrecio3 = Double.valueOf(precio3.getText().toString());
                double opCant3 = Double.valueOf(cant3.getText().toString());
                double opPrecio4 = Double.valueOf(precio4.getText().toString());
                double opCant4 = Double.valueOf(cant4.getText().toString());
                double opPrecio5 = Double.valueOf(precio5.getText().toString());
                double opCant5 = Double.valueOf(cant5.getText().toString());
                double opPrecio6 = Double.valueOf(precio6.getText().toString());
                double opCant6 = Double.valueOf(cant6.getText().toString());
                double opPrecio7 = Double.valueOf(precio7.getText().toString());
                double opCant7 = Double.valueOf(cant7.getText().toString());
                double opPrecio8 = Double.valueOf(precio8.getText().toString());
                double opCant8 = Double.valueOf(cant8.getText().toString());
                double opPrecio9 = Double.valueOf(precio9.getText().toString());
                double opCant9 = Double.valueOf(cant9.getText().toString());
                double opPrecio10 = Double.valueOf(precio10.getText().toString());
                double opCant10 = Double.valueOf(cant10.getText().toString());

                double opPrecio11 = Double.valueOf(precio11.getText().toString());
                double opCant11 = Double.valueOf(cant11.getText().toString());
                double opPrecio12 = Double.valueOf(precio12.getText().toString());
                double opCant12 = Double.valueOf(cant12.getText().toString());
                double opPrecio13 = Double.valueOf(precio13.getText().toString());
                double opCant13 = Double.valueOf(cant13.getText().toString());
                double opPrecio14 = Double.valueOf(precio14.getText().toString());
                double opCant14 = Double.valueOf(cant14.getText().toString());
                double opPrecio15 = Double.valueOf(precio15.getText().toString());
                double opCant15 = Double.valueOf(cant15.getText().toString());
                double opPrecio16 = Double.valueOf(precio16.getText().toString());
                double opCant16 = Double.valueOf(cant16.getText().toString());
                double opPrecio17 = Double.valueOf(precio17.getText().toString());
                double opCant17 = Double.valueOf(cant17.getText().toString());
                double opPrecio18 = Double.valueOf(precio18.getText().toString());
                double opCant18 = Double.valueOf(cant18.getText().toString());
                double opPrecio19 = Double.valueOf(precio19.getText().toString());
                double opCant19 = Double.valueOf(cant19.getText().toString());
                double opPrecio20 = Double.valueOf(precio20.getText().toString());
                double opCant20 = Double.valueOf(cant20.getText().toString());

                double opPrecio21 = Double.valueOf(precio21.getText().toString());
                double opCant21 = Double.valueOf(cant21.getText().toString());
                double opPrecio22 = Double.valueOf(precio22.getText().toString());
                double opCant22 = Double.valueOf(cant22.getText().toString());
                double opPrecio23 = Double.valueOf(precio23.getText().toString());
                double opCant23 = Double.valueOf(cant23.getText().toString());
                double opPrecio24 = Double.valueOf(precio24.getText().toString());
                double opCant24 = Double.valueOf(cant24.getText().toString());
                double opPrecio25 = Double.valueOf(precio25.getText().toString());
                double opCant25 = Double.valueOf(cant25.getText().toString());
                double opPrecio26 = Double.valueOf(precio26.getText().toString());
                double opCant26 = Double.valueOf(cant26.getText().toString());
                double opPrecio27 = Double.valueOf(precio27.getText().toString());
                double opCant27 = Double.valueOf(cant27.getText().toString());
                double opPrecio28 = Double.valueOf(precio28.getText().toString());
                double opCant28 = Double.valueOf(cant28.getText().toString());
                double opPrecio29 = Double.valueOf(precio29.getText().toString());
                double opCant29 = Double.valueOf(cant29.getText().toString());
                double opPrecio30 = Double.valueOf(precio30.getText().toString());
                double opCant30 = Double.valueOf(cant30.getText().toString());

                double opPrecio31 = Double.valueOf(precio31.getText().toString());
                double opCant31 = Double.valueOf(cant31.getText().toString());
                double opPrecio32 = Double.valueOf(precio32.getText().toString());
                double opCant32 = Double.valueOf(cant32.getText().toString());
                double opPrecio33 = Double.valueOf(precio33.getText().toString());
                double opCant33 = Double.valueOf(cant33.getText().toString());
                double opPrecio34 = Double.valueOf(precio34.getText().toString());
                double opCant34 = Double.valueOf(cant34.getText().toString());
                double opPrecio35 = Double.valueOf(precio35.getText().toString());
                double opCant35 = Double.valueOf(cant35.getText().toString());
                double opPrecio36 = Double.valueOf(precio36.getText().toString());
                double opCant36 = Double.valueOf(cant36.getText().toString());
                double opPrecio37 = Double.valueOf(precio37.getText().toString());
                double opCant37 = Double.valueOf(cant37.getText().toString());
                double opPrecio38 = Double.valueOf(precio38.getText().toString());
                double opCant38 = Double.valueOf(cant38.getText().toString());
                double opPrecio39 = Double.valueOf(precio39.getText().toString());
                double opCant39 = Double.valueOf(cant39.getText().toString());
                double opPrecio40 = Double.valueOf(precio40.getText().toString());
                double opCant40 = Double.valueOf(cant40.getText().toString());

                double opPrecio41 = Double.valueOf(precio41.getText().toString());
                double opCant41 = Double.valueOf(cant41.getText().toString());
                double opPrecio42 = Double.valueOf(precio42.getText().toString());
                double opCant42 = Double.valueOf(cant42.getText().toString());
                double opPrecio43 = Double.valueOf(precio43.getText().toString());
                double opCant43 = Double.valueOf(cant43.getText().toString());
                double opPrecio44 = Double.valueOf(precio44.getText().toString());
                double opCant44 = Double.valueOf(cant44.getText().toString());
                double opPrecio45 = Double.valueOf(precio45.getText().toString());
                double opCant45 = Double.valueOf(cant45.getText().toString());

                double operacion = 0;
                double suma = 0;
                double porcentaje_desc = 0;
                double resultado = 0;
                double resultado2 = 0;
                double resultado_general = 0;
                double descuento_Total = 0;
                double total_Total = 0;

                ///SE REALIZAN LAS OPERACION Y SE VA INCREMENTANDO LA VARIABLE SUMA
                if (opCant1 > 0 && opPrecio1 > 0) {
                    operacion = opCant1 * opPrecio1;
                    suma += operacion;
                }
                if (opCant2 > 0 && opPrecio2 > 0) {
                    operacion = opCant2 * opPrecio2;
                    suma += operacion;
                }
                if (opCant3 > 0 && opPrecio3 > 0) {
                    operacion = opCant3 * opPrecio3;
                    suma += operacion;
                }
                if (opCant4 > 0 && opPrecio4 > 0) {
                    operacion = opCant4 * opPrecio4;
                    suma += operacion;
                }
                if (opCant5 > 0 && opPrecio5 > 0) {
                    operacion = opCant5 * opPrecio5;
                    suma += operacion;
                }
                if (opCant6 > 0 && opPrecio6 > 0) {
                    operacion = opCant6 * opPrecio6;
                    suma += operacion;
                }
                if (opCant7 > 0 && opPrecio7 > 0) {
                    operacion = opCant7 * opPrecio7;
                    suma += operacion;
                }
                if (opCant8 > 0 && opPrecio8 > 0) {
                    operacion = opCant8 * opPrecio8;
                    suma += operacion;
                }
                if (opCant9 > 0 && opPrecio9 > 0) {
                    operacion = opCant9 * opPrecio9;
                    suma += operacion;
                }
                if (opCant10 > 0 && opPrecio10 > 0) {
                    operacion = opCant10 * opPrecio10;
                    suma += operacion;
                }

                if (opCant11 > 0 && opPrecio11 > 0) {
                    operacion = opCant11 * opPrecio11;
                    suma += operacion;
                }
                if (opCant12 > 0 && opPrecio12 > 0) {
                    operacion = opCant12 * opPrecio12;
                    suma += operacion;
                }
                if (opCant13 > 0 && opPrecio13 > 0) {
                    operacion = opCant13 * opPrecio13;
                    suma += operacion;
                }
                if (opCant14 > 0 && opPrecio14 > 0) {
                    operacion = opCant14 * opPrecio14;
                    suma += operacion;
                }
                if (opCant15 > 0 && opPrecio15 > 0) {
                    operacion = opCant15 * opPrecio15;
                    suma += operacion;
                }
                if (opCant16 > 0 && opPrecio16 > 0) {
                    operacion = opCant16 * opPrecio16;
                    suma += operacion;
                }
                if (opCant17 > 0 && opPrecio17 > 0) {
                    operacion = opCant17 * opPrecio17;
                    suma += operacion;
                }
                if (opCant18 > 0 && opPrecio18 > 0) {
                    operacion = opCant18 * opPrecio18;
                    suma += operacion;
                }
                if (opCant19 > 0 && opPrecio19 > 0) {
                    operacion = opCant19 * opPrecio19;
                    suma += operacion;
                }
                if (opCant20 > 0 && opPrecio20 > 0) {
                    operacion = opCant20 * opPrecio20;
                    suma += operacion;
                }

                if (opCant21 > 0 && opPrecio21 > 0) {
                    operacion = opCant21 * opPrecio21;
                    suma += operacion;
                }
                if (opCant22 > 0 && opPrecio22 > 0) {
                    operacion = opCant22 * opPrecio22;
                    suma += operacion;
                }
                if (opCant23 > 0 && opPrecio23 > 0) {
                    operacion = opCant23 * opPrecio23;
                    suma += operacion;
                }
                if (opCant24 > 0 && opPrecio24 > 0) {
                    operacion = opCant24 * opPrecio24;
                    suma += operacion;
                }
                if (opCant25 > 0 && opPrecio25 > 0) {
                    operacion = opCant25 * opPrecio25;
                    suma += operacion;
                }
                if (opCant26 > 0 && opPrecio26 > 0) {
                    operacion = opCant26 * opPrecio26;
                    suma += operacion;
                }
                if (opCant27 > 0 && opPrecio27 > 0) {
                    operacion = opCant27 * opPrecio27;
                    suma += operacion;
                }
                if (opCant28 > 0 && opPrecio28 > 0) {
                    operacion = opCant28 * opPrecio28;
                    suma += operacion;
                }
                if (opCant29 > 0 && opPrecio29 > 0) {
                    operacion = opCant29 * opPrecio29;
                    suma += operacion;
                }
                if (opCant30 > 0 && opPrecio30 > 0) {
                    operacion = opCant30 * opPrecio30;
                    suma += operacion;
                }

                if (opCant31 > 0 && opPrecio31 > 0) {
                    operacion = opCant31 * opPrecio31;
                    suma += operacion;
                }
                if (opCant32 > 0 && opPrecio32 > 0) {
                    operacion = opCant32 * opPrecio32;
                    suma += operacion;
                }
                if (opCant33 > 0 && opPrecio33 > 0) {
                    operacion = opCant33 * opPrecio33;
                    suma += operacion;
                }
                if (opCant34 > 0 && opPrecio34 > 0) {
                    operacion = opCant34 * opPrecio34;
                    suma += operacion;
                }
                if (opCant35 > 0 && opPrecio35 > 0) {
                    operacion = opCant35 * opPrecio35;
                    suma += operacion;
                }
                if (opCant36 > 0 && opPrecio36 > 0) {
                    operacion = opCant36 * opPrecio36;
                    suma += operacion;
                }
                if (opCant37 > 0 && opPrecio37 > 0) {
                    operacion = opCant37 * opPrecio37;
                    suma += operacion;
                }
                if (opCant38 > 0 && opPrecio38 > 0) {
                    operacion = opCant38 * opPrecio38;
                    suma += operacion;
                }
                if (opCant39 > 0 && opPrecio39 > 0) {
                    operacion = opCant39 * opPrecio39;
                    suma += operacion;
                }
                if (opCant40 > 0 && opPrecio40 > 0) {
                    operacion = opCant40 * opPrecio40;
                    suma += operacion;
                }

                if (opCant41 > 0 && opPrecio41 > 0) {
                    operacion = opCant41 * opPrecio41;
                    suma += operacion;
                }
                if (opCant42 > 0 && opPrecio42 > 0) {
                    operacion = opCant42 * opPrecio42;
                    suma += operacion;
                }
                if (opCant43 > 0 && opPrecio43 > 0) {
                    operacion = opCant43 * opPrecio43;
                    suma += operacion;
                }
                if (opCant44 > 0 && opPrecio44 > 0) {
                    operacion = opCant44 * opPrecio44;
                    suma += operacion;
                }
                if (opCant45 > 0 && opPrecio45 > 0) {
                    operacion = opCant45 * opPrecio45;
                    suma += operacion;
                }

                ///***CUANDO EL SUBTIPO DE PEDIDOS ES NORMAL Y SE SELECCIONA ALMACEN DE MANO (ERROR)
                if (tipo_venta.equals("Almacen") && subtipo.equals("Muestras Gratis")) {
                    producto1.setText("");
                    precio1.setText(""+0.0);
                    desc1.setText("");
                    cant1.setText(""+0);
                    total1.setText("");

                    producto2.setText("");
                    precio2.setText(""+0.0);
                    desc2.setText("");
                    cant2.setText(""+0);
                    total2.setText("");

                    producto3.setText("");
                    precio3.setText(""+0.0);
                    desc3.setText("");
                    cant3.setText(""+0);
                    total3.setText("");

                    producto4.setText("");
                    precio4.setText(""+0.0);
                    desc4.setText("");
                    cant4.setText(""+0);
                    total4.setText("");

                    producto5.setText("");
                    precio5.setText(""+0.0);
                    desc5.setText("");
                    cant5.setText(""+0);
                    total5.setText("");

                    producto6.setText("");
                    precio6.setText(""+0.0);
                    desc6.setText("");
                    cant6.setText(""+0);
                    total6.setText("");

                    producto7.setText("");
                    precio7.setText(""+0.0);
                    desc7.setText("");
                    cant7.setText(""+0);
                    total7.setText("");

                    producto8.setText("");
                    precio8.setText(""+0.0);
                    desc8.setText("");
                    cant8.setText(""+0);
                    total8.setText("");

                    producto9.setText("");
                    precio9.setText(""+0.0);
                    desc9.setText("");
                    cant9.setText(""+0);
                    total9.setText("");

                    producto10.setText("");
                    precio10.setText(""+0.0);
                    desc10.setText("");
                    cant10.setText(""+0);
                    total10.setText("");

                    producto11.setText("");
                    precio11.setText(""+0.0);
                    desc11.setText("");
                    cant11.setText(""+0);
                    total11.setText("");

                    producto12.setText("");
                    precio12.setText(""+0.0);
                    desc12.setText("");
                    cant12.setText(""+0);
                    total12.setText("");

                    producto13.setText("");
                    precio13.setText(""+0.0);
                    desc13.setText("");
                    cant13.setText(""+0);
                    total13.setText("");

                    producto14.setText("");
                    precio14.setText(""+0.0);
                    desc14.setText("");
                    cant14.setText(""+0);
                    total14.setText("");

                    producto15.setText("");
                    precio15.setText(""+0.0);
                    desc15.setText("");
                    cant15.setText(""+0);
                    total15.setText("");

                    producto16.setText("");
                    precio16.setText(""+0.0);
                    desc16.setText("");
                    cant16.setText(""+0);
                    total16.setText("");

                    producto17.setText("");
                    precio17.setText(""+0.0);
                    desc17.setText("");
                    cant17.setText(""+0);
                    total17.setText("");

                    producto18.setText("");
                    precio18.setText(""+0.0);
                    desc18.setText("");
                    cant18.setText(""+0);
                    total18.setText("");

                    producto19.setText("");
                    precio19.setText(""+0.0);
                    desc19.setText("");
                    cant19.setText(""+0);
                    total19.setText("");

                    producto20.setText("");
                    precio20.setText(""+0.0);
                    desc20.setText("");
                    cant20.setText(""+0);
                    total20.setText("");

                    producto21.setText("");
                    precio21.setText(""+0.0);
                    desc21.setText("");
                    cant21.setText(""+0);
                    total21.setText("");

                    producto22.setText("");
                    precio22.setText(""+0.0);
                    desc22.setText("");
                    cant22.setText(""+0);
                    total22.setText("");

                    producto23.setText("");
                    precio23.setText(""+0.0);
                    desc23.setText("");
                    cant23.setText(""+0);
                    total23.setText("");

                    producto24.setText("");
                    precio24.setText(""+0.0);
                    desc24.setText("");
                    cant24.setText(""+0);
                    total24.setText("");

                    producto25.setText("");
                    precio25.setText(""+0.0);
                    desc25.setText("");
                    cant25.setText(""+0);
                    total25.setText("");

                    producto26.setText("");
                    precio26.setText(""+0.0);
                    desc26.setText("");
                    cant26.setText(""+0);
                    total26.setText("");

                    producto27.setText("");
                    precio27.setText(""+0.0);
                    desc27.setText("");
                    cant27.setText(""+0);
                    total27.setText("");

                    producto28.setText("");
                    precio28.setText(""+0.0);
                    desc28.setText("");
                    cant28.setText(""+0);
                    total28.setText("");

                    producto29.setText("");
                    precio29.setText(""+0.0);
                    desc29.setText("");
                    cant29.setText(""+0);
                    total29.setText("");

                    producto30.setText("");
                    precio30.setText(""+0.0);
                    desc30.setText("");
                    cant30.setText(""+0);
                    total30.setText("");

                    producto31.setText("");
                    precio31.setText(""+0.0);
                    desc31.setText("");
                    cant31.setText(""+0);
                    total31.setText("");

                    producto32.setText("");
                    precio32.setText(""+0.0);
                    desc32.setText("");
                    cant32.setText(""+0);
                    total32.setText("");

                    producto33.setText("");
                    precio33.setText(""+0.0);
                    desc33.setText("");
                    cant33.setText(""+0);
                    total33.setText("");

                    producto34.setText("");
                    precio34.setText(""+0.0);
                    desc34.setText("");
                    cant34.setText(""+0);
                    total34.setText("");

                    producto35.setText("");
                    precio35.setText(""+0.0);
                    desc35.setText("");
                    cant35.setText(""+0);
                    total35.setText("");

                    producto36.setText("");
                    precio36.setText(""+0.0);
                    desc36.setText("");
                    cant36.setText(""+0);
                    total36.setText("");

                    producto37.setText("");
                    precio37.setText(""+0.0);
                    desc37.setText("");
                    cant37.setText(""+0);
                    total37.setText("");

                    producto38.setText("");
                    precio38.setText(""+0.0);
                    desc38.setText("");
                    cant38.setText(""+0);
                    total38.setText("");

                    producto39.setText("");
                    precio39.setText(""+0.0);
                    desc39.setText("");
                    cant39.setText(""+0);
                    total39.setText("");

                    producto40.setText("");
                    precio40.setText(""+0.0);
                    desc40.setText("");
                    cant40.setText(""+0);
                    total40.setText("");

                    producto41.setText("");
                    precio41.setText(""+0.0);
                    desc41.setText("");
                    cant41.setText(""+0);
                    total41.setText("");

                    producto42.setText("");
                    precio42.setText(""+0.0);
                    desc42.setText("");
                    cant42.setText(""+0);
                    total42.setText("");

                    producto43.setText("");
                    precio43.setText(""+0.0);
                    desc43.setText("");
                    cant43.setText(""+0);
                    total43.setText("");

                    producto44.setText("");
                    precio44.setText(""+0.0);
                    desc44.setText("");
                    cant44.setText(""+0);
                    total44.setText("");

                    producto45.setText("");
                    precio45.setText(""+0.0);
                    desc45.setText("");
                    cant45.setText(""+0);
                    total45.setText("");

                    Toast toast = Toast.makeText(getApplicationContext(), "De almacen no pueden salir muestras gratis favor de modificar", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }

                ///***CUANDO LA FAMILIA ES AGRICOLA Y SE SELECCIONA MUESTRAS GRATIS (ERROR)
                if (familia.equals("Agricola") && subtipo.equals("Muestras Gratis")) {
                    producto1.setText("");
                    precio1.setText(""+0.0);
                    desc1.setText("");
                    cant1.setText(""+0);
                    total1.setText("");

                    producto2.setText("");
                    precio2.setText(""+0.0);
                    desc2.setText("");
                    cant2.setText(""+0);
                    total2.setText("");

                    producto3.setText("");
                    precio3.setText(""+0.0);
                    desc3.setText("");
                    cant3.setText(""+0);
                    total3.setText("");

                    producto4.setText("");
                    precio4.setText(""+0.0);
                    desc4.setText("");
                    cant4.setText(""+0);
                    total4.setText("");

                    producto5.setText("");
                    precio5.setText(""+0.0);
                    desc5.setText("");
                    cant5.setText(""+0);
                    total5.setText("");

                    producto6.setText("");
                    precio6.setText(""+0.0);
                    desc6.setText("");
                    cant6.setText(""+0);
                    total6.setText("");

                    producto7.setText("");
                    precio7.setText(""+0.0);
                    desc7.setText("");
                    cant7.setText(""+0);
                    total7.setText("");

                    producto8.setText("");
                    precio8.setText(""+0.0);
                    desc8.setText("");
                    cant8.setText(""+0);
                    total8.setText("");

                    producto9.setText("");
                    precio9.setText(""+0.0);
                    desc9.setText("");
                    cant9.setText(""+0);
                    total9.setText("");

                    producto10.setText("");
                    precio10.setText(""+0.0);
                    desc10.setText("");
                    cant10.setText(""+0);
                    total10.setText("");

                    producto11.setText("");
                    precio11.setText(""+0.0);
                    desc11.setText("");
                    cant11.setText(""+0);
                    total11.setText("");

                    producto12.setText("");
                    precio12.setText(""+0.0);
                    desc12.setText("");
                    cant12.setText(""+0);
                    total12.setText("");

                    producto13.setText("");
                    precio13.setText(""+0.0);
                    desc13.setText("");
                    cant13.setText(""+0);
                    total13.setText("");

                    producto14.setText("");
                    precio14.setText(""+0.0);
                    desc14.setText("");
                    cant14.setText(""+0);
                    total14.setText("");

                    producto15.setText("");
                    precio15.setText(""+0.0);
                    desc15.setText("");
                    cant15.setText(""+0);
                    total15.setText("");

                    producto16.setText("");
                    precio16.setText(""+0.0);
                    desc16.setText("");
                    cant16.setText(""+0);
                    total16.setText("");

                    producto17.setText("");
                    precio17.setText(""+0.0);
                    desc17.setText("");
                    cant17.setText(""+0);
                    total17.setText("");

                    producto18.setText("");
                    precio18.setText(""+0.0);
                    desc18.setText("");
                    cant18.setText(""+0);
                    total18.setText("");

                    producto19.setText("");
                    precio19.setText(""+0.0);
                    desc19.setText("");
                    cant19.setText(""+0);
                    total19.setText("");

                    producto20.setText("");
                    precio20.setText(""+0.0);
                    desc20.setText("");
                    cant20.setText(""+0);
                    total20.setText("");

                    producto21.setText("");
                    precio21.setText(""+0.0);
                    desc21.setText("");
                    cant21.setText(""+0);
                    total21.setText("");

                    producto22.setText("");
                    precio22.setText(""+0.0);
                    desc22.setText("");
                    cant22.setText(""+0);
                    total22.setText("");

                    producto23.setText("");
                    precio23.setText(""+0.0);
                    desc23.setText("");
                    cant23.setText(""+0);
                    total23.setText("");

                    producto24.setText("");
                    precio24.setText(""+0.0);
                    desc24.setText("");
                    cant24.setText(""+0);
                    total24.setText("");

                    producto25.setText("");
                    precio25.setText(""+0.0);
                    desc25.setText("");
                    cant25.setText(""+0);
                    total25.setText("");

                    producto26.setText("");
                    precio26.setText(""+0.0);
                    desc26.setText("");
                    cant26.setText(""+0);
                    total26.setText("");

                    producto27.setText("");
                    precio27.setText(""+0.0);
                    desc27.setText("");
                    cant27.setText(""+0);
                    total27.setText("");

                    producto28.setText("");
                    precio28.setText(""+0.0);
                    desc28.setText("");
                    cant28.setText(""+0);
                    total28.setText("");

                    producto29.setText("");
                    precio29.setText(""+0.0);
                    desc29.setText("");
                    cant29.setText(""+0);
                    total29.setText("");

                    producto30.setText("");
                    precio30.setText(""+0.0);
                    desc30.setText("");
                    cant30.setText(""+0);
                    total30.setText("");

                    producto31.setText("");
                    precio31.setText(""+0.0);
                    desc31.setText("");
                    cant31.setText(""+0);
                    total31.setText("");

                    producto32.setText("");
                    precio32.setText(""+0.0);
                    desc32.setText("");
                    cant32.setText(""+0);
                    total32.setText("");

                    producto33.setText("");
                    precio33.setText(""+0.0);
                    desc33.setText("");
                    cant33.setText(""+0);
                    total33.setText("");

                    producto34.setText("");
                    precio34.setText(""+0.0);
                    desc34.setText("");
                    cant34.setText(""+0);
                    total34.setText("");

                    producto35.setText("");
                    precio35.setText(""+0.0);
                    desc35.setText("");
                    cant35.setText(""+0);
                    total35.setText("");

                    producto36.setText("");
                    precio36.setText(""+0.0);
                    desc36.setText("");
                    cant36.setText(""+0);
                    total36.setText("");

                    producto37.setText("");
                    precio37.setText(""+0.0);
                    desc37.setText("");
                    cant37.setText(""+0);
                    total37.setText("");

                    producto38.setText("");
                    precio38.setText(""+0.0);
                    desc38.setText("");
                    cant38.setText(""+0);
                    total38.setText("");

                    producto39.setText("");
                    precio39.setText(""+0.0);
                    desc39.setText("");
                    cant39.setText(""+0);
                    total39.setText("");

                    producto40.setText("");
                    precio40.setText(""+0.0);
                    desc40.setText("");
                    cant40.setText(""+0);
                    total40.setText("");

                    producto41.setText("");
                    precio41.setText(""+0.0);
                    desc41.setText("");
                    cant41.setText(""+0);
                    total41.setText("");

                    producto42.setText("");
                    precio42.setText(""+0.0);
                    desc42.setText("");
                    cant42.setText(""+0);
                    total42.setText("");

                    producto43.setText("");
                    precio43.setText(""+0.0);
                    desc43.setText("");
                    cant43.setText(""+0);
                    total43.setText("");

                    producto44.setText("");
                    precio44.setText(""+0.0);
                    desc44.setText("");
                    cant44.setText(""+0);
                    total44.setText("");

                    producto45.setText("");
                    precio45.setText(""+0.0);
                    desc45.setText("");
                    cant45.setText(""+0);
                    total45.setText("");

                    Toast toast = Toast.makeText(getApplicationContext(), "En familia agricola no hay muestras gratis", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }

                ///***CUANDO EL SUBTIPO DE PEDIDO ES EXTENDIDO
                porcentaje_desc = 40;

                ///***SE AGREGA EL PORCENTAJE DE DESCUENTO A LOS CAMPOS DE PORCENTAJE
                if (opCant1 > 0 && opPrecio1 > 0) {
                    String[] separated = opProducto1.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el metodo esPaquete para validar que el producto es paquete
                    Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                    if (paquete == false) {
                        desc1.setText("" + porcentaje_desc);
                    }
                    if (paquete == true) {
                        desc1.setText("" + 0.0);
                    }
                }
                if (opCant2 > 0 && opPrecio2 > 0) {
                    String[] separated = opProducto2.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el metodo esPaquete para validar que el producto es paquete
                    Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                    if (paquete == false) {
                        desc2.setText("" + porcentaje_desc);
                    }
                    if (paquete == true) {
                        desc2.setText("" + 0.0);
                    }
                }
                if (opCant3 > 0 && opPrecio3 > 0) {
                    String[] separated = opProducto3.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el metodo esPaquete para validar que el producto es paquete
                    Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                    if (paquete == false) {
                        desc3.setText("" + porcentaje_desc);
                    }
                    if (paquete == true) {
                        desc3.setText("" + 0.0);
                    }
                }
                if (opCant4 > 0 && opPrecio4 > 0) {
                    String[] separated = opProducto4.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el metodo esPaquete para validar que el producto es paquete
                    Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                    if (paquete == false) {
                        desc4.setText("" + porcentaje_desc);
                    }
                    if (paquete == true) {
                        desc4.setText("" + 0.0);
                    }
                }
                if (opCant5 > 0 && opPrecio5 > 0) {
                    String[] separated = opProducto5.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el metodo esPaquete para validar que el producto es paquete
                    Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                    if (paquete == false) {
                        desc5.setText("" + porcentaje_desc);
                    }
                    if (paquete == true) {
                        desc5.setText("" + 0.0);
                    }
                }
                if (opCant6 > 0 && opPrecio6 > 0) {
                    String[] separated = opProducto6.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el metodo esPaquete para validar que el producto es paquete
                    Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                    if (paquete == false) {
                        desc6.setText("" + porcentaje_desc);
                    }
                    if (paquete == true) {
                        desc6.setText("" + 0.0);
                    }
                }
                if (opCant7 > 0 && opPrecio7 > 0) {
                    String[] separated = opProducto7.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el metodo esPaquete para validar que el producto es paquete
                    Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                    if (paquete == false) {
                        desc7.setText("" + porcentaje_desc);
                    }
                    if (paquete == true) {
                        desc7.setText("" + 0.0);
                    }
                }
                if (opCant8 > 0 && opPrecio8 > 0) {
                    String[] separated = opProducto8.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el metodo esPaquete para validar que el producto es paquete
                    Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                    if (paquete == false) {
                        desc8.setText("" + porcentaje_desc);
                    }
                    if (paquete == true) {
                        desc8.setText("" + 0.0);
                    }
                }
                if (opCant9 > 0 && opPrecio9 > 0) {
                    String[] separated = opProducto9.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el metodo esPaquete para validar que el producto es paquete
                    Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                    if (paquete == false) {
                        desc9.setText("" + porcentaje_desc);
                    }
                    if (paquete == true) {
                        desc9.setText("" + 0.0);
                    }
                }
                if (opCant10 > 0 && opPrecio10 > 0) {
                    String[] separated = opProducto10.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el metodo esPaquete para validar que el producto es paquete
                    Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                    if (paquete == false) {
                        desc10.setText("" + porcentaje_desc);
                    }
                    if (paquete == true) {
                        desc10.setText("" + 0.0);
                    }
                }
                if (opCant11 > 0 && opPrecio11 > 0) {
                    String[] separated = opProducto11.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el metodo esPaquete para validar que el producto es paquete
                    Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                    if (paquete == false) {
                        desc11.setText("" + porcentaje_desc);
                    }
                    if (paquete == true) {
                        desc11.setText("" + 0.0);
                    }
                }
                if (opCant12 > 0 && opPrecio12 > 0) {
                    String[] separated = opProducto12.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el metodo esPaquete para validar que el producto es paquete
                    Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                    if (paquete == false) {
                        desc12.setText("" + porcentaje_desc);
                    }
                    if (paquete == true) {
                        desc12.setText("" + 0.0);
                    }
                }
                if (opCant13 > 0 && opPrecio13 > 0) {
                    String[] separated = opProducto13.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el metodo esPaquete para validar que el producto es paquete
                    Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                    if (paquete == false) {
                        desc13.setText("" + porcentaje_desc);
                    }
                    if (paquete == true) {
                        desc13.setText("" + 0.0);
                    }
                }
                if (opCant14 > 0 && opPrecio14 > 0) {
                    String[] separated = opProducto14.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el metodo esPaquete para validar que el producto es paquete
                    Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                    if (paquete == false) {
                        desc14.setText("" + porcentaje_desc);
                    }
                    if (paquete == true) {
                        desc14.setText("" + 0.0);
                    }
                }
                if (opCant15 > 0 && opPrecio15 > 0) {
                    String[] separated = opProducto15.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el metodo esPaquete para validar que el producto es paquete
                    Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                    if (paquete == false) {
                        desc15.setText("" + porcentaje_desc);
                    }
                    if (paquete == true) {
                        desc15.setText("" + 0.0);
                    }
                }
                if (opCant16 > 0 && opPrecio16 > 0) {
                    String[] separated = opProducto16.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el metodo esPaquete para validar que el producto es paquete
                    Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                    if (paquete == false) {
                        desc16.setText("" + porcentaje_desc);
                    }
                    if (paquete == true) {
                        desc16.setText("" + 0.0);
                    }
                }
                if (opCant17 > 0 && opPrecio17 > 0) {
                    String[] separated = opProducto17.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el metodo esPaquete para validar que el producto es paquete
                    Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                    if (paquete == false) {
                        desc17.setText("" + porcentaje_desc);
                    }
                    if (paquete == true) {
                        desc17.setText("" + 0.0);
                    }
                }
                if (opCant18 > 0 && opPrecio18 > 0) {
                    String[] separated = opProducto18.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el metodo esPaquete para validar que el producto es paquete
                    Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                    if (paquete == false) {
                        desc18.setText("" + porcentaje_desc);
                    }
                    if (paquete == true) {
                        desc18.setText("" + 0.0);
                    }
                }
                if (opCant19 > 0 && opPrecio19 > 0) {
                    String[] separated = opProducto19.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el metodo esPaquete para validar que el producto es paquete
                    Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                    if (paquete == false) {
                        desc19.setText("" + porcentaje_desc);
                    }
                    if (paquete == true) {
                        desc19.setText("" + 0.0);
                    }
                }
                if (opCant20 > 0 && opPrecio20 > 0) {
                    String[] separated = opProducto20.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el metodo esPaquete para validar que el producto es paquete
                    Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                    if (paquete == false) {
                        desc20.setText("" + porcentaje_desc);
                    }
                    if (paquete == true) {
                        desc20.setText("" + 0.0);
                    }
                }
                if (opCant21 > 0 && opPrecio21 > 0) {
                    String[] separated = opProducto21.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el metodo esPaquete para validar que el producto es paquete
                    Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                    if (paquete == false) {
                        desc21.setText("" + porcentaje_desc);
                    }
                    if (paquete == true) {
                        desc21.setText("" + 0.0);
                    }
                }
                if (opCant22 > 0 && opPrecio22 > 0) {
                    String[] separated = opProducto22.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el metodo esPaquete para validar que el producto es paquete
                    Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                    if (paquete == false) {
                        desc22.setText("" + porcentaje_desc);
                    }
                    if (paquete == true) {
                        desc22.setText("" + 0.0);
                    }
                }
                if (opCant23 > 0 && opPrecio23 > 0) {
                    String[] separated = opProducto23.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el metodo esPaquete para validar que el producto es paquete
                    Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                    if (paquete == false) {
                        desc23.setText("" + porcentaje_desc);
                    }
                    if (paquete == true) {
                        desc23.setText("" + 0.0);
                    }
                }
                if (opCant24 > 0 && opPrecio24 > 0) {
                    String[] separated = opProducto24.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el metodo esPaquete para validar que el producto es paquete
                    Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                    if (paquete == false) {
                        desc24.setText("" + porcentaje_desc);
                    }
                    if (paquete == true) {
                        desc24.setText("" + 0.0);
                    }
                }
                if (opCant25 > 0 && opPrecio25 > 0) {
                    String[] separated = opProducto25.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el metodo esPaquete para validar que el producto es paquete
                    Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                    if (paquete == false) {
                        desc25.setText("" + porcentaje_desc);
                    }
                    if (paquete == true) {
                        desc25.setText("" + 0.0);
                    }
                }
                if (opCant26 > 0 && opPrecio26 > 0) {
                    String[] separated = opProducto26.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el metodo esPaquete para validar que el producto es paquete
                    Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                    if (paquete == false) {
                        desc26.setText("" + porcentaje_desc);
                    }
                    if (paquete == true) {
                        desc26.setText("" + 0.0);
                    }
                }
                if (opCant27 > 0 && opPrecio27 > 0) {
                    String[] separated = opProducto27.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el metodo esPaquete para validar que el producto es paquete
                    Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                    if (paquete == false) {
                        desc27.setText("" + porcentaje_desc);
                    }
                    if (paquete == true) {
                        desc27.setText("" + 0.0);
                    }
                }
                if (opCant28 > 0 && opPrecio28 > 0) {
                    String[] separated = opProducto28.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el metodo esPaquete para validar que el producto es paquete
                    Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                    if (paquete == false) {
                        desc28.setText("" + porcentaje_desc);
                    }
                    if (paquete == true) {
                        desc28.setText("" + 0.0);
                    }
                }
                if (opCant29 > 0 && opPrecio29 > 0) {
                    String[] separated = opProducto29.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el metodo esPaquete para validar que el producto es paquete
                    Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                    if (paquete == false) {
                        desc29.setText("" + porcentaje_desc);
                    }
                    if (paquete == true) {
                        desc29.setText("" + 0.0);
                    }
                }
                if (opCant30 > 0 && opPrecio30 > 0) {
                    String[] separated = opProducto30.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el metodo esPaquete para validar que el producto es paquete
                    Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                    if (paquete == false) {
                        desc30.setText("" + porcentaje_desc);
                    }
                    if (paquete == true) {
                        desc30.setText("" + 0.0);
                    }
                }

                if (opCant31 > 0 && opPrecio31 > 0) {
                    String[] separated = opProducto31.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el metodo esPaquete para validar que el producto es paquete
                    Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                    if (paquete == false) {
                        desc31.setText("" + porcentaje_desc);
                    }
                    if (paquete == true) {
                        desc31.setText("" + 0.0);
                    }
                }
                if (opCant32 > 0 && opPrecio32 > 0) {
                    String[] separated = opProducto32.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el metodo esPaquete para validar que el producto es paquete
                    Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                    if (paquete == false) {
                        desc32.setText("" + porcentaje_desc);
                    }
                    if (paquete == true) {
                        desc32.setText("" + 0.0);
                    }
                }
                if (opCant33 > 0 && opPrecio33 > 0) {
                    String[] separated = opProducto33.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el metodo esPaquete para validar que el producto es paquete
                    Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                    if (paquete == false) {
                        desc33.setText("" + porcentaje_desc);
                    }
                    if (paquete == true) {
                        desc33.setText("" + 0.0);
                    }
                }
                if (opCant34 > 0 && opPrecio34 > 0) {
                    String[] separated = opProducto34.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el metodo esPaquete para validar que el producto es paquete
                    Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                    if (paquete == false) {
                        desc34.setText("" + porcentaje_desc);
                    }
                    if (paquete == true) {
                        desc34.setText("" + 0.0);
                    }
                }
                if (opCant35 > 0 && opPrecio35 > 0) {
                    String[] separated = opProducto35.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el metodo esPaquete para validar que el producto es paquete
                    Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                    if (paquete == false) {
                        desc35.setText("" + porcentaje_desc);
                    }
                    if (paquete == true) {
                        desc35.setText("" + 0.0);
                    }
                }
                if (opCant36 > 0 && opPrecio36 > 0) {
                    String[] separated = opProducto36.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el metodo esPaquete para validar que el producto es paquete
                    Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                    if (paquete == false) {
                        desc36.setText("" + porcentaje_desc);
                    }
                    if (paquete == true) {
                        desc36.setText("" + 0.0);
                    }
                }
                if (opCant37 > 0 && opPrecio37 > 0) {
                    String[] separated = opProducto37.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el metodo esPaquete para validar que el producto es paquete
                    Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                    if (paquete == false) {
                        desc37.setText("" + porcentaje_desc);
                    }
                    if (paquete == true) {
                        desc37.setText("" + 0.0);
                    }
                }
                if (opCant38 > 0 && opPrecio38 > 0) {
                    String[] separated = opProducto38.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el metodo esPaquete para validar que el producto es paquete
                    Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                    if (paquete == false) {
                        desc38.setText("" + porcentaje_desc);
                    }
                    if (paquete == true) {
                        desc38.setText("" + 0.0);
                    }
                }
                if (opCant39 > 0 && opPrecio39 > 0) {
                    String[] separated = opProducto39.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el metodo esPaquete para validar que el producto es paquete
                    Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                    if (paquete == false) {
                        desc39.setText("" + porcentaje_desc);
                    }
                    if (paquete == true) {
                        desc39.setText("" + 0.0);
                    }
                }
                if (opCant40 > 0 && opPrecio40 > 0) {
                    String[] separated = opProducto40.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el metodo esPaquete para validar que el producto es paquete
                    Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                    if (paquete == false) {
                        desc40.setText("" + porcentaje_desc);
                    }
                    if (paquete == true) {
                        desc40.setText("" + 0.0);
                    }
                }
                if (opCant41 > 0 && opPrecio41 > 0) {
                    String[] separated = opProducto41.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el metodo esPaquete para validar que el producto es paquete
                    Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                    if (paquete == false) {
                        desc41.setText("" + porcentaje_desc);
                    }
                    if (paquete == true) {
                        desc41.setText("" + 0.0);
                    }
                }
                if (opCant42 > 0 && opPrecio42 > 0) {
                    String[] separated = opProducto42.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el metodo esPaquete para validar que el producto es paquete
                    Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                    if (paquete == false) {
                        desc42.setText("" + porcentaje_desc);
                    }
                    if (paquete == true) {
                        desc42.setText("" + 0.0);
                    }
                }
                if (opCant43 > 0 && opPrecio43 > 0) {
                    String[] separated = opProducto43.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el metodo esPaquete para validar que el producto es paquete
                    Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                    if (paquete == false) {
                        desc43.setText("" + porcentaje_desc);
                    }
                    if (paquete == true) {
                        desc43.setText("" + 0.0);
                    }
                }
                if (opCant44 > 0 && opPrecio44 > 0) {
                    String[] separated = opProducto44.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el metodo esPaquete para validar que el producto es paquete
                    Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                    if (paquete == false) {
                        desc44.setText("" + porcentaje_desc);
                    }
                    if (paquete == true) {
                        desc44.setText("" + 0.0);
                    }
                }
                if (opCant45 > 0 && opPrecio45 > 0) {
                    String[] separated = opProducto45.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el metodo esPaquete para validar que el producto es paquete
                    Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                    if (paquete == false) {
                        desc45.setText("" + porcentaje_desc);
                    }
                    if (paquete == true) {
                        desc45.setText("" + 0.0);
                    }
                }

                ///***SE REALIZAN LAS OPERACIONES YA CON EL PORCENTAJE DE DESCUENTO
                if (opCant1 > 0 && opPrecio1 > 0) {
                    String[] separated = opProducto1.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el metodo esPaquete para validar que el producto es paquete
                    Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                    if (paquete == false) {
                        resultado = opPrecio1 * opCant1;
                        resultado2 = (resultado * porcentaje_desc) / 100;
                        resultado_general = resultado - resultado2;
                        descuento_Total += resultado2;
                        total_Total += resultado_general;
                        total1.setText("" + resultado_general);
                    }
                    if (paquete == true) {
                        resultado = opPrecio1 * opCant1;
                        total_Total += resultado;
                        total1.setText("" + resultado);
                    }
                }
                if (opCant2 > 0 && opPrecio2 > 0) {
                    String[] separated = opProducto2.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el metodo esPaquete para validar que el producto es paquete
                    Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                    if (paquete == false) {
                        resultado = opPrecio2 * opCant2;
                        resultado2 = (resultado * porcentaje_desc) / 100;
                        resultado_general = resultado - resultado2;
                        descuento_Total += resultado2;
                        total_Total += resultado_general;
                        total2.setText("" + resultado_general);
                    }
                    if (paquete == true) {
                        resultado = opPrecio2 * opCant2;
                        total_Total += resultado;
                        total2.setText("" + resultado);
                    }
                }
                if (opCant3 > 0 && opPrecio3 > 0) {
                    String[] separated = opProducto3.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el metodo esPaquete para validar que el producto es paquete
                    Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                    if (paquete == false) {
                        resultado = opPrecio3 * opCant3;
                        resultado2 = (resultado * porcentaje_desc) / 100;
                        resultado_general = resultado - resultado2;
                        descuento_Total += resultado2;
                        total_Total += resultado_general;
                        total3.setText("" + resultado_general);
                    }
                    if (paquete == true) {
                        resultado = opPrecio3 * opCant3;
                        total_Total += resultado;
                        total3.setText("" + resultado);
                    }
                }
                if (opCant4 > 0 && opPrecio4 > 0) {
                    String[] separated = opProducto4.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el metodo esPaquete para validar que el producto es paquete
                    Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                    if (paquete == false) {
                        resultado = opPrecio4 * opCant4;
                        resultado2 = (resultado * porcentaje_desc) / 100;
                        resultado_general = resultado - resultado2;
                        descuento_Total += resultado2;
                        total_Total += resultado_general;
                        total4.setText("" + resultado_general);
                    }
                    if (paquete == true) {
                        resultado = opPrecio4 * opCant4;
                        total_Total += resultado;
                        total4.setText("" + resultado);
                    }
                }
                if (opCant5 > 0 && opPrecio5 > 0) {
                    String[] separated = opProducto5.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el metodo esPaquete para validar que el producto es paquete
                    Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                    if (paquete == false) {
                        resultado = opPrecio5 * opCant5;
                        resultado2 = (resultado * porcentaje_desc) / 100;
                        resultado_general = resultado - resultado2;
                        descuento_Total += resultado2;
                        total_Total += resultado_general;
                        total5.setText("" + resultado_general);
                    }
                    if (paquete == true) {
                        resultado = opPrecio5 * opCant5;
                        total_Total += resultado;
                        total5.setText("" + resultado);
                    }
                }
                if (opCant6 > 0 && opPrecio6 > 0) {
                    String[] separated = opProducto6.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el metodo esPaquete para validar que el producto es paquete
                    Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                    if (paquete == false) {
                        resultado = opPrecio6 * opCant6;
                        resultado2 = (resultado * porcentaje_desc) / 100;
                        resultado_general = resultado - resultado2;
                        descuento_Total += resultado2;
                        total_Total += resultado_general;
                        total6.setText("" + resultado_general);
                    }
                    if (paquete == true) {
                        resultado = opPrecio6 * opCant6;
                        total_Total += resultado;
                        total6.setText("" + resultado);
                    }
                }
                if (opCant7 > 0 && opPrecio7 > 0) {
                    String[] separated = opProducto7.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el metodo esPaquete para validar que el producto es paquete
                    Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                    if (paquete == false) {
                        resultado = opPrecio7 * opCant7;
                        resultado2 = (resultado * porcentaje_desc) / 100;
                        resultado_general = resultado - resultado2;
                        descuento_Total += resultado2;
                        total_Total += resultado_general;
                        total7.setText("" + resultado_general);
                    }
                    if (paquete == true) {
                        resultado = opPrecio7 * opCant7;
                        total_Total += resultado;
                        total7.setText("" + resultado);
                    }
                }
                if (opCant8 > 0 && opPrecio8 > 0) {
                    String[] separated = opProducto8.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el metodo esPaquete para validar que el producto es paquete
                    Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                    if (paquete == false) {
                        resultado = opPrecio8 * opCant8;
                        resultado2 = (resultado * porcentaje_desc) / 100;
                        resultado_general = resultado - resultado2;
                        descuento_Total += resultado2;
                        total_Total += resultado_general;
                        total8.setText("" + resultado_general);
                    }
                    if (paquete == true) {
                        resultado = opPrecio8 * opCant8;
                        total_Total += resultado;
                        total8.setText("" + resultado);
                    }
                }
                if (opCant9 > 0 && opPrecio9 > 0) {
                    String[] separated = opProducto9.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el metodo esPaquete para validar que el producto es paquete
                    Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                    if (paquete == false) {
                        resultado = opPrecio9 * opCant9;
                        resultado2 = (resultado * porcentaje_desc) / 100;
                        resultado_general = resultado - resultado2;
                        descuento_Total += resultado2;
                        total_Total += resultado_general;
                        total9.setText("" + resultado_general);
                    }
                    if (paquete == true) {
                        resultado = opPrecio9 * opCant9;
                        total_Total += resultado;
                        total9.setText("" + resultado);
                    }
                }
                if (opCant10 > 0 && opPrecio10 > 0) {
                    String[] separated = opProducto10.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el metodo esPaquete para validar que el producto es paquete
                    Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                    if (paquete == false) {
                        resultado = opPrecio10 * opCant10;
                        resultado2 = (resultado * porcentaje_desc) / 100;
                        resultado_general = resultado - resultado2;
                        descuento_Total += resultado2;
                        total_Total += resultado_general;
                        total10.setText("" + resultado_general);
                    }
                    if (paquete == true) {
                        resultado = opPrecio10 * opCant10;
                        total_Total += resultado;
                        total10.setText("" + resultado);
                    }
                }
                if (opCant11 > 0 && opPrecio11 > 0) {
                    String[] separated = opProducto11.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el metodo esPaquete para validar que el producto es paquete
                    Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                    if (paquete == false) {
                        resultado = opPrecio11 * opCant11;
                        resultado2 = (resultado * porcentaje_desc) / 100;
                        resultado_general = resultado - resultado2;
                        descuento_Total += resultado2;
                        total_Total += resultado_general;
                        total11.setText("" + resultado_general);
                    }
                    if (paquete == true) {
                        resultado = opPrecio11 * opCant11;
                        total_Total += resultado;
                        total11.setText("" + resultado);
                    }
                }
                if (opCant12 > 0 && opPrecio12 > 0) {
                    String[] separated = opProducto12.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el metodo esPaquete para validar que el producto es paquete
                    Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                    if (paquete == false) {
                        resultado = opPrecio12 * opCant12;
                        resultado2 = (resultado * porcentaje_desc) / 100;
                        resultado_general = resultado - resultado2;
                        descuento_Total += resultado2;
                        total_Total += resultado_general;
                        total12.setText("" + resultado_general);
                    }
                    if (paquete == true) {
                        resultado = opPrecio12 * opCant12;
                        total_Total += resultado;
                        total12.setText("" + resultado);
                    }
                }
                if (opCant13 > 0 && opPrecio13 > 0) {
                    String[] separated = opProducto13.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el metodo esPaquete para validar que el producto es paquete
                    Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                    if (paquete == false) {
                        resultado = opPrecio13 * opCant13;
                        resultado2 = (resultado * porcentaje_desc) / 100;
                        resultado_general = resultado - resultado2;
                        descuento_Total += resultado2;
                        total_Total += resultado_general;
                        total13.setText("" + resultado_general);
                    }
                    if (paquete == true) {
                        resultado = opPrecio13 * opCant13;
                        total_Total += resultado;
                        total13.setText("" + resultado);
                    }
                }
                if (opCant14 > 0 && opPrecio14 > 0) {
                    String[] separated = opProducto14.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el metodo esPaquete para validar que el producto es paquete
                    Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                    if (paquete == false) {
                        resultado = opPrecio14 * opCant14;
                        resultado2 = (resultado * porcentaje_desc) / 100;
                        resultado_general = resultado - resultado2;
                        descuento_Total += resultado2;
                        total_Total += resultado_general;
                        total14.setText("" + resultado_general);
                    }
                    if (paquete == true) {
                        resultado = opPrecio14 * opCant14;
                        total_Total += resultado;
                        total14.setText("" + resultado);
                    }
                }
                if (opCant15 > 0 && opPrecio15 > 0) {
                    String[] separated = opProducto15.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el metodo esPaquete para validar que el producto es paquete
                    Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                    if (paquete == false) {
                        resultado = opPrecio15 * opCant15;
                        resultado2 = (resultado * porcentaje_desc) / 100;
                        resultado_general = resultado - resultado2;
                        descuento_Total += resultado2;
                        total_Total += resultado_general;
                        total15.setText("" + resultado_general);
                    }
                    if (paquete == true) {
                        resultado = opPrecio15 * opCant15;
                        total_Total += resultado;
                        total15.setText("" + resultado);
                    }
                }
                if (opCant16 > 0 && opPrecio16 > 0) {
                    String[] separated = opProducto16.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el metodo esPaquete para validar que el producto es paquete
                    Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                    if (paquete == false) {
                        resultado = opPrecio16 * opCant16;
                        resultado2 = (resultado * porcentaje_desc) / 100;
                        resultado_general = resultado - resultado2;
                        descuento_Total += resultado2;
                        total_Total += resultado_general;
                        total16.setText("" + resultado_general);
                    }
                    if (paquete == true) {
                        resultado = opPrecio16 * opCant16;
                        total_Total += resultado;
                        total16.setText("" + resultado);
                    }
                }
                if (opCant17 > 0 && opPrecio17 > 0) {
                    String[] separated = opProducto17.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el metodo esPaquete para validar que el producto es paquete
                    Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                    if (paquete == false) {
                        resultado = opPrecio17 * opCant17;
                        resultado2 = (resultado * porcentaje_desc) / 100;
                        resultado_general = resultado - resultado2;
                        descuento_Total += resultado2;
                        total_Total += resultado_general;
                        total17.setText("" + resultado_general);
                    }
                    if (paquete == true) {
                        resultado = opPrecio17 * opCant17;
                        total_Total += resultado;
                        total17.setText("" + resultado);
                    }
                }
                if (opCant18 > 0 && opPrecio18 > 0) {
                    String[] separated = opProducto18.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el metodo esPaquete para validar que el producto es paquete
                    Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                    if (paquete == false) {
                        resultado = opPrecio18 * opCant18;
                        resultado2 = (resultado * porcentaje_desc) / 100;
                        resultado_general = resultado - resultado2;
                        descuento_Total += resultado2;
                        total_Total += resultado_general;
                        total18.setText("" + resultado_general);
                    }
                    if (paquete == true) {
                        resultado = opPrecio18 * opCant18;
                        total_Total += resultado;
                        total18.setText("" + resultado);
                    }
                }
                if (opCant19 > 0 && opPrecio19 > 0) {
                    String[] separated = opProducto19.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el metodo esPaquete para validar que el producto es paquete
                    Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                    if (paquete == false) {
                        resultado = opPrecio19 * opCant19;
                        resultado2 = (resultado * porcentaje_desc) / 100;
                        resultado_general = resultado - resultado2;
                        descuento_Total += resultado2;
                        total_Total += resultado_general;
                        total19.setText("" + resultado_general);
                    }
                    if (paquete == true) {
                        resultado = opPrecio19 * opCant19;
                        total_Total += resultado;
                        total19.setText("" + resultado);
                    }
                }
                if (opCant20 > 0 && opPrecio20 > 0) {
                    String[] separated = opProducto20.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el metodo esPaquete para validar que el producto es paquete
                    Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                    if (paquete == false) {
                        resultado = opPrecio20 * opCant20;
                        resultado2 = (resultado * porcentaje_desc) / 100;
                        resultado_general = resultado - resultado2;
                        descuento_Total += resultado2;
                        total_Total += resultado_general;
                        total20.setText("" + resultado_general);
                    }
                    if (paquete == true) {
                        resultado = opPrecio20 * opCant20;
                        total_Total += resultado;
                        total20.setText("" + resultado);
                    }
                }
                if (opCant21 > 0 && opPrecio21 > 0) {
                    String[] separated = opProducto21.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el metodo esPaquete para validar que el producto es paquete
                    Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                    if (paquete == false) {
                        resultado = opPrecio21 * opCant21;
                        resultado2 = (resultado * porcentaje_desc) / 100;
                        resultado_general = resultado - resultado2;
                        descuento_Total += resultado2;
                        total_Total += resultado_general;
                        total21.setText("" + resultado_general);
                    }
                    if (paquete == true) {
                        resultado = opPrecio21 * opCant21;
                        total_Total += resultado;
                        total21.setText("" + resultado);
                    }
                }
                if (opCant22 > 0 && opPrecio22 > 0) {
                    String[] separated = opProducto22.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el metodo esPaquete para validar que el producto es paquete
                    Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                    if (paquete == false) {
                        resultado = opPrecio22 * opCant22;
                        resultado2 = (resultado * porcentaje_desc) / 100;
                        resultado_general = resultado - resultado2;
                        descuento_Total += resultado2;
                        total_Total += resultado_general;
                        total22.setText("" + resultado_general);
                    }
                    if (paquete == true) {
                        resultado = opPrecio22 * opCant22;
                        total_Total += resultado;
                        total22.setText("" + resultado);
                    }
                }
                if (opCant23 > 0 && opPrecio23 > 0) {
                    String[] separated = opProducto23.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el metodo esPaquete para validar que el producto es paquete
                    Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                    if (paquete == false) {
                        resultado = opPrecio23 * opCant23;
                        resultado2 = (resultado * porcentaje_desc) / 100;
                        resultado_general = resultado - resultado2;
                        descuento_Total += resultado2;
                        total_Total += resultado_general;
                        total23.setText("" + resultado_general);
                    }
                    if (paquete == true) {
                        resultado = opPrecio23 * opCant23;
                        total_Total += resultado;
                        total23.setText("" + resultado);
                    }
                }
                if (opCant24 > 0 && opPrecio24 > 0) {
                    String[] separated = opProducto24.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el metodo esPaquete para validar que el producto es paquete
                    Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                    if (paquete == false) {
                        resultado = opPrecio24 * opCant24;
                        resultado2 = (resultado * porcentaje_desc) / 100;
                        resultado_general = resultado - resultado2;
                        descuento_Total += resultado2;
                        total_Total += resultado_general;
                        total24.setText("" + resultado_general);
                    }
                    if (paquete == true) {
                        resultado = opPrecio24 * opCant24;
                        total_Total += resultado;
                        total24.setText("" + resultado);
                    }
                }
                if (opCant25 > 0 && opPrecio25 > 0) {
                    String[] separated = opProducto25.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el metodo esPaquete para validar que el producto es paquete
                    Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                    if (paquete == false) {
                        resultado = opPrecio25 * opCant25;
                        resultado2 = (resultado * porcentaje_desc) / 100;
                        resultado_general = resultado - resultado2;
                        descuento_Total += resultado2;
                        total_Total += resultado_general;
                        total25.setText("" + resultado_general);
                    }
                    if (paquete == true) {
                        resultado = opPrecio25 * opCant25;
                        total_Total += resultado;
                        total25.setText("" + resultado);
                    }
                }
                if (opCant26 > 0 && opPrecio26 > 0) {
                    String[] separated = opProducto26.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el metodo esPaquete para validar que el producto es paquete
                    Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                    if (paquete == false) {
                        resultado = opPrecio26 * opCant26;
                        resultado2 = (resultado * porcentaje_desc) / 100;
                        resultado_general = resultado - resultado2;
                        descuento_Total += resultado2;
                        total_Total += resultado_general;
                        total26.setText("" + resultado_general);
                    }
                    if (paquete == true) {
                        resultado = opPrecio26 * opCant26;
                        total_Total += resultado;
                        total26.setText("" + resultado);
                    }
                }
                if (opCant27 > 0 && opPrecio27 > 0) {
                    String[] separated = opProducto27.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el metodo esPaquete para validar que el producto es paquete
                    Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                    if (paquete == false) {
                        resultado = opPrecio27 * opCant27;
                        resultado2 = (resultado * porcentaje_desc) / 100;
                        resultado_general = resultado - resultado2;
                        descuento_Total += resultado2;
                        total_Total += resultado_general;
                        total27.setText("" + resultado_general);
                    }
                    if (paquete == true) {
                        resultado = opPrecio27 * opCant27;
                        total_Total += resultado;
                        total27.setText("" + resultado);
                    }
                }
                if (opCant28 > 0 && opPrecio28 > 0) {
                    String[] separated = opProducto28.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el metodo esPaquete para validar que el producto es paquete
                    Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                    if (paquete == false) {
                        resultado = opPrecio28 * opCant28;
                        resultado2 = (resultado * porcentaje_desc) / 100;
                        resultado_general = resultado - resultado2;
                        descuento_Total += resultado2;
                        total_Total += resultado_general;
                        total28.setText("" + resultado_general);
                    }
                    if (paquete == true) {
                        resultado = opPrecio28 * opCant28;
                        total_Total += resultado;
                        total28.setText("" + resultado);
                    }
                }
                if (opCant29 > 0 && opPrecio29 > 0) {
                    String[] separated = opProducto29.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el metodo esPaquete para validar que el producto es paquete
                    Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                    if (paquete == false) {
                        resultado = opPrecio29 * opCant29;
                        resultado2 = (resultado * porcentaje_desc) / 100;
                        resultado_general = resultado - resultado2;
                        descuento_Total += resultado2;
                        total_Total += resultado_general;
                        total29.setText("" + resultado_general);
                    }
                    if (paquete == true) {
                        resultado = opPrecio29 * opCant29;
                        total_Total += resultado;
                        total29.setText("" + resultado);
                    }
                }
                if (opCant30 > 0 && opPrecio30 > 0) {
                    String[] separated = opProducto30.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el metodo esPaquete para validar que el producto es paquete
                    Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                    if (paquete == false) {
                        resultado = opPrecio30 * opCant30;
                        resultado2 = (resultado * porcentaje_desc) / 100;
                        resultado_general = resultado - resultado2;
                        descuento_Total += resultado2;
                        total_Total += resultado_general;
                        total30.setText("" + resultado_general);
                    }
                    if (paquete == true) {
                        resultado = opPrecio30 * opCant30;
                        total_Total += resultado;
                        total30.setText("" + resultado);
                    }
                }

                if (opCant31 > 0 && opPrecio31 > 0) {
                    String[] separated = opProducto31.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el metodo esPaquete para validar que el producto es paquete
                    Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                    if (paquete == false) {
                        resultado = opPrecio31 * opCant31;
                        resultado2 = (resultado * porcentaje_desc) / 100;
                        resultado_general = resultado - resultado2;
                        descuento_Total += resultado2;
                        total_Total += resultado_general;
                        total31.setText("" + resultado_general);
                    }
                    if (paquete == true) {
                        resultado = opPrecio31 * opCant31;
                        total_Total += resultado;
                        total31.setText("" + resultado);
                    }
                }
                if (opCant32 > 0 && opPrecio32 > 0) {
                    String[] separated = opProducto32.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el metodo esPaquete para validar que el producto es paquete
                    Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                    if (paquete == false) {
                        resultado = opPrecio32 * opCant32;
                        resultado2 = (resultado * porcentaje_desc) / 100;
                        resultado_general = resultado - resultado2;
                        descuento_Total += resultado2;
                        total_Total += resultado_general;
                        total32.setText("" + resultado_general);
                    }
                    if (paquete == true) {
                        resultado = opPrecio32 * opCant32;
                        total_Total += resultado;
                        total32.setText("" + resultado);
                    }
                }
                if (opCant33 > 0 && opPrecio33 > 0) {
                    String[] separated = opProducto33.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el metodo esPaquete para validar que el producto es paquete
                    Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                    if (paquete == false) {
                        resultado = opPrecio33 * opCant33;
                        resultado2 = (resultado * porcentaje_desc) / 100;
                        resultado_general = resultado - resultado2;
                        descuento_Total += resultado2;
                        total_Total += resultado_general;
                        total33.setText("" + resultado_general);
                    }
                    if (paquete == true) {
                        resultado = opPrecio33 * opCant33;
                        total_Total += resultado;
                        total33.setText("" + resultado);
                    }
                }
                if (opCant34 > 0 && opPrecio34 > 0) {
                    String[] separated = opProducto34.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el metodo esPaquete para validar que el producto es paquete
                    Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                    if (paquete == false) {
                        resultado = opPrecio34 * opCant34;
                        resultado2 = (resultado * porcentaje_desc) / 100;
                        resultado_general = resultado - resultado2;
                        descuento_Total += resultado2;
                        total_Total += resultado_general;
                        total34.setText("" + resultado_general);
                    }
                    if (paquete == true) {
                        resultado = opPrecio34 * opCant34;
                        total_Total += resultado;
                        total34.setText("" + resultado);
                    }
                }
                if (opCant35 > 0 && opPrecio35 > 0) {
                    String[] separated = opProducto35.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el metodo esPaquete para validar que el producto es paquete
                    Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                    if (paquete == false) {
                        resultado = opPrecio35 * opCant35;
                        resultado2 = (resultado * porcentaje_desc) / 100;
                        resultado_general = resultado - resultado2;
                        descuento_Total += resultado2;
                        total_Total += resultado_general;
                        total35.setText("" + resultado_general);
                    }
                    if (paquete == true) {
                        resultado = opPrecio35 * opCant35;
                        total_Total += resultado;
                        total35.setText("" + resultado);
                    }
                }
                if (opCant36 > 0 && opPrecio36 > 0) {
                    String[] separated = opProducto36.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el metodo esPaquete para validar que el producto es paquete
                    Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                    if (paquete == false) {
                        resultado = opPrecio36 * opCant36;
                        resultado2 = (resultado * porcentaje_desc) / 100;
                        resultado_general = resultado - resultado2;
                        descuento_Total += resultado2;
                        total_Total += resultado_general;
                        total36.setText("" + resultado_general);
                    }
                    if (paquete == true) {
                        resultado = opPrecio36 * opCant36;
                        total_Total += resultado;
                        total36.setText("" + resultado);
                    }
                }
                if (opCant37 > 0 && opPrecio37 > 0) {
                    String[] separated = opProducto37.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el metodo esPaquete para validar que el producto es paquete
                    Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                    if (paquete == false) {
                        resultado = opPrecio37 * opCant37;
                        resultado2 = (resultado * porcentaje_desc) / 100;
                        resultado_general = resultado - resultado2;
                        descuento_Total += resultado2;
                        total_Total += resultado_general;
                        total37.setText("" + resultado_general);
                    }
                    if (paquete == true) {
                        resultado = opPrecio37 * opCant37;
                        total_Total += resultado;
                        total37.setText("" + resultado);
                    }
                }
                if (opCant38 > 0 && opPrecio38 > 0) {
                    String[] separated = opProducto38.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el metodo esPaquete para validar que el producto es paquete
                    Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                    if (paquete == false) {
                        resultado = opPrecio38 * opCant38;
                        resultado2 = (resultado * porcentaje_desc) / 100;
                        resultado_general = resultado - resultado2;
                        descuento_Total += resultado2;
                        total_Total += resultado_general;
                        total38.setText("" + resultado_general);
                    }
                    if (paquete == true) {
                        resultado = opPrecio38 * opCant38;
                        total_Total += resultado;
                        total38.setText("" + resultado);
                    }
                }
                if (opCant39 > 0 && opPrecio39 > 0) {
                    String[] separated = opProducto39.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el metodo esPaquete para validar que el producto es paquete
                    Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                    if (paquete == false) {
                        resultado = opPrecio39 * opCant39;
                        resultado2 = (resultado * porcentaje_desc) / 100;
                        resultado_general = resultado - resultado2;
                        descuento_Total += resultado2;
                        total_Total += resultado_general;
                        total39.setText("" + resultado_general);
                    }
                    if (paquete == true) {
                        resultado = opPrecio39 * opCant39;
                        total_Total += resultado;
                        total39.setText("" + resultado);
                    }
                }
                if (opCant40 > 0 && opPrecio40 > 0) {
                    String[] separated = opProducto40.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el metodo esPaquete para validar que el producto es paquete
                    Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                    if (paquete == false) {
                        resultado = opPrecio40 * opCant40;
                        resultado2 = (resultado * porcentaje_desc) / 100;
                        resultado_general = resultado - resultado2;
                        descuento_Total += resultado2;
                        total_Total += resultado_general;
                        total40.setText("" + resultado_general);
                    }
                    if (paquete == true) {
                        resultado = opPrecio40 * opCant40;
                        total_Total += resultado;
                        total40.setText("" + resultado);
                    }
                }

                if (opCant41 > 0 && opPrecio41 > 0) {
                    String[] separated = opProducto41.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el metodo esPaquete para validar que el producto es paquete
                    Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                    if (paquete == false) {
                        resultado = opPrecio41 * opCant41;
                        resultado2 = (resultado * porcentaje_desc) / 100;
                        resultado_general = resultado - resultado2;
                        descuento_Total += resultado2;
                        total_Total += resultado_general;
                        total41.setText("" + resultado_general);
                    }
                    if (paquete == true) {
                        resultado = opPrecio41 * opCant41;
                        total_Total += resultado;
                        total41.setText("" + resultado);
                    }
                }
                if (opCant42 > 0 && opPrecio42 > 0) {
                    String[] separated = opProducto42.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el metodo esPaquete para validar que el producto es paquete
                    Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                    if (paquete == false) {
                        resultado = opPrecio42 * opCant42;
                        resultado2 = (resultado * porcentaje_desc) / 100;
                        resultado_general = resultado - resultado2;
                        descuento_Total += resultado2;
                        total_Total += resultado_general;
                        total42.setText("" + resultado_general);
                    }
                    if (paquete == true) {
                        resultado = opPrecio42 * opCant42;
                        total_Total += resultado;
                        total42.setText("" + resultado);
                    }
                }
                if (opCant43 > 0 && opPrecio43 > 0) {
                    String[] separated = opProducto43.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el metodo esPaquete para validar que el producto es paquete
                    Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                    if (paquete == false) {
                        resultado = opPrecio43 * opCant43;
                        resultado2 = (resultado * porcentaje_desc) / 100;
                        resultado_general = resultado - resultado2;
                        descuento_Total += resultado2;
                        total_Total += resultado_general;
                        total43.setText("" + resultado_general);
                    }
                    if (paquete == true) {
                        resultado = opPrecio43 * opCant43;
                        total_Total += resultado;
                        total43.setText("" + resultado);
                    }
                }
                if (opCant44 > 0 && opPrecio44 > 0) {
                    String[] separated = opProducto44.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el metodo esPaquete para validar que el producto es paquete
                    Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                    if (paquete == false) {
                        resultado = opPrecio44 * opCant44;
                        resultado2 = (resultado * porcentaje_desc) / 100;
                        resultado_general = resultado - resultado2;
                        descuento_Total += resultado2;
                        total_Total += resultado_general;
                        total44.setText("" + resultado_general);
                    }
                    if (paquete == true) {
                        resultado = opPrecio44 * opCant44;
                        total_Total += resultado;
                        total44.setText("" + resultado);
                    }
                }
                if (opCant45 > 0 && opPrecio45 > 0) {
                    String[] separated = opProducto45.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Invocamos el metodo esPaquete para validar que el producto es paquete
                    Boolean paquete = dbHandler.esPaquete(cve_cat_producto);
                    if (paquete == false) {
                        resultado = opPrecio45 * opCant45;
                        resultado2 = (resultado * porcentaje_desc) / 100;
                        resultado_general = resultado - resultado2;
                        descuento_Total += resultado2;
                        total_Total += resultado_general;
                        total45.setText("" + resultado_general);
                    }
                    if (paquete == true) {
                        resultado = opPrecio45 * opCant45;
                        total_Total += resultado;
                        total45.setText("" + resultado);
                    }
                }

                ///***PARA PONER EL DATO EN SUMA, DESCUENTO Y TOTAL
                sumaTotal.setText("" + suma);
                descuentoTotal.setText("" + descuento_Total);
                totalTotal.setText("" + total_Total);
            }
        }; //Fin del seOnFocusChangeListener

        cant1.setOnFocusChangeListener(listener);
        cant2.setOnFocusChangeListener(listener);
        cant3.setOnFocusChangeListener(listener);
        cant4.setOnFocusChangeListener(listener);
        cant5.setOnFocusChangeListener(listener);
        cant6.setOnFocusChangeListener(listener);
        cant7.setOnFocusChangeListener(listener);
        cant8.setOnFocusChangeListener(listener);
        cant9.setOnFocusChangeListener(listener);
        cant10.setOnFocusChangeListener(listener);
        cant11.setOnFocusChangeListener(listener);
        cant12.setOnFocusChangeListener(listener);
        cant13.setOnFocusChangeListener(listener);
        cant14.setOnFocusChangeListener(listener);
        cant15.setOnFocusChangeListener(listener);
        cant16.setOnFocusChangeListener(listener);
        cant17.setOnFocusChangeListener(listener);
        cant18.setOnFocusChangeListener(listener);
        cant19.setOnFocusChangeListener(listener);
        cant20.setOnFocusChangeListener(listener);
        cant21.setOnFocusChangeListener(listener);
        cant22.setOnFocusChangeListener(listener);
        cant23.setOnFocusChangeListener(listener);
        cant24.setOnFocusChangeListener(listener);
        cant25.setOnFocusChangeListener(listener);
        cant26.setOnFocusChangeListener(listener);
        cant27.setOnFocusChangeListener(listener);
        cant28.setOnFocusChangeListener(listener);
        cant29.setOnFocusChangeListener(listener);
        cant30.setOnFocusChangeListener(listener);
        cant31.setOnFocusChangeListener(listener);
        cant32.setOnFocusChangeListener(listener);
        cant33.setOnFocusChangeListener(listener);
        cant34.setOnFocusChangeListener(listener);
        cant35.setOnFocusChangeListener(listener);
        cant36.setOnFocusChangeListener(listener);
        cant37.setOnFocusChangeListener(listener);
        cant38.setOnFocusChangeListener(listener);
        cant39.setOnFocusChangeListener(listener);
        cant40.setOnFocusChangeListener(listener);
        cant41.setOnFocusChangeListener(listener);
        cant42.setOnFocusChangeListener(listener);
        cant43.setOnFocusChangeListener(listener);
        cant44.setOnFocusChangeListener(listener);
        cant45.setOnFocusChangeListener(listener);

        Button btnSigGuardar = (Button)findViewById(R.id.btnPedidosGuardar);
        btnSigGuardar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent explicit_intent;

                explicit_intent = new Intent(CapturaPedidoExtendido.this,CapturaPedidosGuardar.class);

                //Encabezado del pedido
                String clienteGuardar= clienteDato;
                String familiaGuardar= familiaDato;
                String subtipoGuardar= subtipoDato;
                String tipoventaGuardar= tipoventaDato;
                String conductoGuardar= conductoDato;
                String comentariosGuardar= comentariosDato;

                //Partidas del pedido
                AutoCompleteTextView productoGuardar1 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto1);
                TextView precioGuarda1 = (TextView) findViewById(R.id.precioProducto1);
                TextView descGuarda1 = (TextView) findViewById(R.id.porcentajeDescProducto1);
                EditText cantGuarda1 = (EditText) findViewById(R.id.cantidadProducto1);
                TextView totalGuarda1 = (TextView) findViewById(R.id.totalProducto1);
                String ProductoGuardar1 = String.valueOf(productoGuardar1.getText().toString());
                String PrecioGuardar1 = String.valueOf(precioGuarda1.getText().toString());
                String DescuentoGuardar1 = String.valueOf(descGuarda1.getText().toString());
                String CantidadGuardar1 = String.valueOf(cantGuarda1.getText().toString());
                String TotalGuardar1 = String.valueOf(totalGuarda1.getText().toString());

                AutoCompleteTextView productoGuardar2 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto2);
                TextView precioGuarda2 = (TextView) findViewById(R.id.precioProducto2);
                TextView descGuarda2 = (TextView) findViewById(R.id.porcentajeDescProducto2);
                EditText cantGuarda2 = (EditText) findViewById(R.id.cantidadProducto2);
                TextView totalGuarda2 = (TextView) findViewById(R.id.totalProducto2);
                String ProductoGuardar2 = String.valueOf(productoGuardar2.getText().toString());
                String PrecioGuardar2 = String.valueOf(precioGuarda2.getText().toString());
                String DescuentoGuardar2 = String.valueOf(descGuarda2.getText().toString());
                String CantidadGuardar2 = String.valueOf(cantGuarda2.getText().toString());
                String TotalGuardar2 = String.valueOf(totalGuarda2.getText().toString());

                AutoCompleteTextView productoGuardar3 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto3);
                TextView precioGuarda3 = (TextView) findViewById(R.id.precioProducto3);
                TextView descGuarda3 = (TextView) findViewById(R.id.porcentajeDescProducto3);
                EditText cantGuarda3 = (EditText) findViewById(R.id.cantidadProducto3);
                TextView totalGuarda3 = (TextView) findViewById(R.id.totalProducto3);
                String ProductoGuardar3 = String.valueOf(productoGuardar3.getText().toString());
                String PrecioGuardar3 = String.valueOf(precioGuarda3.getText().toString());
                String DescuentoGuardar3 = String.valueOf(descGuarda3.getText().toString());
                String CantidadGuardar3 = String.valueOf(cantGuarda3.getText().toString());
                String TotalGuardar3 = String.valueOf(totalGuarda3.getText().toString());

                AutoCompleteTextView productoGuardar4 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto4);
                TextView precioGuarda4 = (TextView) findViewById(R.id.precioProducto4);
                TextView descGuarda4 = (TextView) findViewById(R.id.porcentajeDescProducto4);
                EditText cantGuarda4 = (EditText) findViewById(R.id.cantidadProducto4);
                TextView totalGuarda4 = (TextView) findViewById(R.id.totalProducto4);
                String ProductoGuardar4 = String.valueOf(productoGuardar4.getText().toString());
                String PrecioGuardar4 = String.valueOf(precioGuarda4.getText().toString());
                String DescuentoGuardar4 = String.valueOf(descGuarda4.getText().toString());
                String CantidadGuardar4 = String.valueOf(cantGuarda4.getText().toString());
                String TotalGuardar4 = String.valueOf(totalGuarda4.getText().toString());

                AutoCompleteTextView productoGuardar5 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto5);
                TextView precioGuarda5 = (TextView) findViewById(R.id.precioProducto5);
                TextView descGuarda5 = (TextView) findViewById(R.id.porcentajeDescProducto5);
                EditText cantGuarda5 = (EditText) findViewById(R.id.cantidadProducto5);
                TextView totalGuarda5 = (TextView) findViewById(R.id.totalProducto5);
                String ProductoGuardar5 = String.valueOf(productoGuardar5.getText().toString());
                String PrecioGuardar5 = String.valueOf(precioGuarda5.getText().toString());
                String DescuentoGuardar5 = String.valueOf(descGuarda5.getText().toString());
                String CantidadGuardar5 = String.valueOf(cantGuarda5.getText().toString());
                String TotalGuardar5 = String.valueOf(totalGuarda5.getText().toString());

                AutoCompleteTextView productoGuardar6 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto6);
                TextView precioGuarda6 = (TextView) findViewById(R.id.precioProducto6);
                TextView descGuarda6 = (TextView) findViewById(R.id.porcentajeDescProducto6);
                EditText cantGuarda6 = (EditText) findViewById(R.id.cantidadProducto6);
                TextView totalGuarda6 = (TextView) findViewById(R.id.totalProducto6);
                String ProductoGuardar6 = String.valueOf(productoGuardar6.getText().toString());
                String PrecioGuardar6 = String.valueOf(precioGuarda6.getText().toString());
                String DescuentoGuardar6 = String.valueOf(descGuarda6.getText().toString());
                String CantidadGuardar6 = String.valueOf(cantGuarda6.getText().toString());
                String TotalGuardar6 = String.valueOf(totalGuarda6.getText().toString());

                AutoCompleteTextView productoGuardar7 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto7);
                TextView precioGuarda7 = (TextView) findViewById(R.id.precioProducto7);
                TextView descGuarda7 = (TextView) findViewById(R.id.porcentajeDescProducto7);
                EditText cantGuarda7 = (EditText) findViewById(R.id.cantidadProducto7);
                TextView totalGuarda7 = (TextView) findViewById(R.id.totalProducto7);
                String ProductoGuardar7 = String.valueOf(productoGuardar7.getText().toString());
                String PrecioGuardar7 = String.valueOf(precioGuarda7.getText().toString());
                String DescuentoGuardar7 = String.valueOf(descGuarda7.getText().toString());
                String CantidadGuardar7 = String.valueOf(cantGuarda7.getText().toString());
                String TotalGuardar7 = String.valueOf(totalGuarda7.getText().toString());

                AutoCompleteTextView productoGuardar8 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto8);
                TextView precioGuarda8 = (TextView) findViewById(R.id.precioProducto8);
                TextView descGuarda8 = (TextView) findViewById(R.id.porcentajeDescProducto8);
                EditText cantGuarda8 = (EditText) findViewById(R.id.cantidadProducto8);
                TextView totalGuarda8 = (TextView) findViewById(R.id.totalProducto8);
                String ProductoGuardar8 = String.valueOf(productoGuardar8.getText().toString());
                String PrecioGuardar8 = String.valueOf(precioGuarda8.getText().toString());
                String DescuentoGuardar8 = String.valueOf(descGuarda8.getText().toString());
                String CantidadGuardar8 = String.valueOf(cantGuarda8.getText().toString());
                String TotalGuardar8 = String.valueOf(totalGuarda8.getText().toString());

                AutoCompleteTextView productoGuardar9 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto9);
                TextView precioGuarda9 = (TextView) findViewById(R.id.precioProducto9);
                TextView descGuarda9 = (TextView) findViewById(R.id.porcentajeDescProducto9);
                EditText cantGuarda9 = (EditText) findViewById(R.id.cantidadProducto9);
                TextView totalGuarda9 = (TextView) findViewById(R.id.totalProducto9);
                String ProductoGuardar9 = String.valueOf(productoGuardar9.getText().toString());
                String PrecioGuardar9 = String.valueOf(precioGuarda9.getText().toString());
                String DescuentoGuardar9 = String.valueOf(descGuarda9.getText().toString());
                String CantidadGuardar9 = String.valueOf(cantGuarda9.getText().toString());
                String TotalGuardar9 = String.valueOf(totalGuarda9.getText().toString());

                AutoCompleteTextView productoGuardar10 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto10);
                TextView precioGuarda10 = (TextView) findViewById(R.id.precioProducto10);
                TextView descGuarda10 = (TextView) findViewById(R.id.porcentajeDescProducto10);
                EditText cantGuarda10 = (EditText) findViewById(R.id.cantidadProducto10);
                TextView totalGuarda10 = (TextView) findViewById(R.id.totalProducto10);
                String ProductoGuardar10 = String.valueOf(productoGuardar10.getText().toString());
                String PrecioGuardar10 = String.valueOf(precioGuarda10.getText().toString());
                String DescuentoGuardar10 = String.valueOf(descGuarda10.getText().toString());
                String CantidadGuardar10 = String.valueOf(cantGuarda10.getText().toString());
                String TotalGuardar10 = String.valueOf(totalGuarda10.getText().toString());

                AutoCompleteTextView productoGuardar11 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto11);
                TextView precioGuarda11 = (TextView) findViewById(R.id.precioProducto11);
                TextView descGuarda11 = (TextView) findViewById(R.id.porcentajeDescProducto11);
                EditText cantGuarda11 = (EditText) findViewById(R.id.cantidadProducto11);
                TextView totalGuarda11 = (TextView) findViewById(R.id.totalProducto11);
                String ProductoGuardar11 = String.valueOf(productoGuardar11.getText().toString());
                String PrecioGuardar11 = String.valueOf(precioGuarda11.getText().toString());
                String DescuentoGuardar11 = String.valueOf(descGuarda11.getText().toString());
                String CantidadGuardar11 = String.valueOf(cantGuarda11.getText().toString());
                String TotalGuardar11 = String.valueOf(totalGuarda11.getText().toString());

                AutoCompleteTextView productoGuardar12 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto12);
                TextView precioGuarda12 = (TextView) findViewById(R.id.precioProducto12);
                TextView descGuarda12 = (TextView) findViewById(R.id.porcentajeDescProducto12);
                EditText cantGuarda12 = (EditText) findViewById(R.id.cantidadProducto12);
                TextView totalGuarda12 = (TextView) findViewById(R.id.totalProducto12);
                String ProductoGuardar12 = String.valueOf(productoGuardar12.getText().toString());
                String PrecioGuardar12 = String.valueOf(precioGuarda12.getText().toString());
                String DescuentoGuardar12 = String.valueOf(descGuarda12.getText().toString());
                String CantidadGuardar12 = String.valueOf(cantGuarda12.getText().toString());
                String TotalGuardar12 = String.valueOf(totalGuarda12.getText().toString());

                AutoCompleteTextView productoGuardar13 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto13);
                TextView precioGuarda13 = (TextView) findViewById(R.id.precioProducto13);
                TextView descGuarda13 = (TextView) findViewById(R.id.porcentajeDescProducto13);
                EditText cantGuarda13 = (EditText) findViewById(R.id.cantidadProducto13);
                TextView totalGuarda13 = (TextView) findViewById(R.id.totalProducto13);
                String ProductoGuardar13 = String.valueOf(productoGuardar13.getText().toString());
                String PrecioGuardar13 = String.valueOf(precioGuarda13.getText().toString());
                String DescuentoGuardar13 = String.valueOf(descGuarda13.getText().toString());
                String CantidadGuardar13 = String.valueOf(cantGuarda13.getText().toString());
                String TotalGuardar13 = String.valueOf(totalGuarda13.getText().toString());

                AutoCompleteTextView productoGuardar14 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto14);
                TextView precioGuarda14 = (TextView) findViewById(R.id.precioProducto14);
                TextView descGuarda14 = (TextView) findViewById(R.id.porcentajeDescProducto14);
                EditText cantGuarda14 = (EditText) findViewById(R.id.cantidadProducto14);
                TextView totalGuarda14 = (TextView) findViewById(R.id.totalProducto14);
                String ProductoGuardar14 = String.valueOf(productoGuardar14.getText().toString());
                String PrecioGuardar14 = String.valueOf(precioGuarda14.getText().toString());
                String DescuentoGuardar14 = String.valueOf(descGuarda14.getText().toString());
                String CantidadGuardar14 = String.valueOf(cantGuarda14.getText().toString());
                String TotalGuardar14 = String.valueOf(totalGuarda14.getText().toString());

                AutoCompleteTextView productoGuardar15 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto15);
                TextView precioGuarda15 = (TextView) findViewById(R.id.precioProducto15);
                TextView descGuarda15 = (TextView) findViewById(R.id.porcentajeDescProducto15);
                EditText cantGuarda15 = (EditText) findViewById(R.id.cantidadProducto15);
                TextView totalGuarda15 = (TextView) findViewById(R.id.totalProducto15);
                String ProductoGuardar15 = String.valueOf(productoGuardar15.getText().toString());
                String PrecioGuardar15 = String.valueOf(precioGuarda15.getText().toString());
                String DescuentoGuardar15 = String.valueOf(descGuarda15.getText().toString());
                String CantidadGuardar15 = String.valueOf(cantGuarda15.getText().toString());
                String TotalGuardar15 = String.valueOf(totalGuarda15.getText().toString());

                AutoCompleteTextView productoGuardar16 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto16);
                TextView precioGuarda16 = (TextView) findViewById(R.id.precioProducto16);
                TextView descGuarda16 = (TextView) findViewById(R.id.porcentajeDescProducto16);
                EditText cantGuarda16 = (EditText) findViewById(R.id.cantidadProducto16);
                TextView totalGuarda16 = (TextView) findViewById(R.id.totalProducto16);
                String ProductoGuardar16 = String.valueOf(productoGuardar16.getText().toString());
                String PrecioGuardar16 = String.valueOf(precioGuarda16.getText().toString());
                String DescuentoGuardar16 = String.valueOf(descGuarda16.getText().toString());
                String CantidadGuardar16 = String.valueOf(cantGuarda16.getText().toString());
                String TotalGuardar16 = String.valueOf(totalGuarda16.getText().toString());

                AutoCompleteTextView productoGuardar17 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto17);
                TextView precioGuarda17 = (TextView) findViewById(R.id.precioProducto17);
                TextView descGuarda17 = (TextView) findViewById(R.id.porcentajeDescProducto17);
                EditText cantGuarda17 = (EditText) findViewById(R.id.cantidadProducto17);
                TextView totalGuarda17 = (TextView) findViewById(R.id.totalProducto17);
                String ProductoGuardar17 = String.valueOf(productoGuardar17.getText().toString());
                String PrecioGuardar17 = String.valueOf(precioGuarda17.getText().toString());
                String DescuentoGuardar17 = String.valueOf(descGuarda17.getText().toString());
                String CantidadGuardar17 = String.valueOf(cantGuarda17.getText().toString());
                String TotalGuardar17 = String.valueOf(totalGuarda17.getText().toString());

                AutoCompleteTextView productoGuardar18 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto18);
                TextView precioGuarda18 = (TextView) findViewById(R.id.precioProducto18);
                TextView descGuarda18 = (TextView) findViewById(R.id.porcentajeDescProducto18);
                EditText cantGuarda18 = (EditText) findViewById(R.id.cantidadProducto18);
                TextView totalGuarda18 = (TextView) findViewById(R.id.totalProducto18);
                String ProductoGuardar18 = String.valueOf(productoGuardar18.getText().toString());
                String PrecioGuardar18 = String.valueOf(precioGuarda18.getText().toString());
                String DescuentoGuardar18 = String.valueOf(descGuarda18.getText().toString());
                String CantidadGuardar18 = String.valueOf(cantGuarda18.getText().toString());
                String TotalGuardar18 = String.valueOf(totalGuarda18.getText().toString());

                AutoCompleteTextView productoGuardar19 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto19);
                TextView precioGuarda19 = (TextView) findViewById(R.id.precioProducto19);
                TextView descGuarda19 = (TextView) findViewById(R.id.porcentajeDescProducto19);
                EditText cantGuarda19 = (EditText) findViewById(R.id.cantidadProducto19);
                TextView totalGuarda19 = (TextView) findViewById(R.id.totalProducto19);
                String ProductoGuardar19 = String.valueOf(productoGuardar19.getText().toString());
                String PrecioGuardar19 = String.valueOf(precioGuarda19.getText().toString());
                String DescuentoGuardar19 = String.valueOf(descGuarda19.getText().toString());
                String CantidadGuardar19 = String.valueOf(cantGuarda19.getText().toString());
                String TotalGuardar19 = String.valueOf(totalGuarda19.getText().toString());

                AutoCompleteTextView productoGuardar20 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto20);
                TextView precioGuarda20 = (TextView) findViewById(R.id.precioProducto20);
                TextView descGuarda20 = (TextView) findViewById(R.id.porcentajeDescProducto20);
                EditText cantGuarda20 = (EditText) findViewById(R.id.cantidadProducto20);
                TextView totalGuarda20 = (TextView) findViewById(R.id.totalProducto20);
                String ProductoGuardar20 = String.valueOf(productoGuardar20.getText().toString());
                String PrecioGuardar20 = String.valueOf(precioGuarda20.getText().toString());
                String DescuentoGuardar20 = String.valueOf(descGuarda20.getText().toString());
                String CantidadGuardar20 = String.valueOf(cantGuarda20.getText().toString());
                String TotalGuardar20 = String.valueOf(totalGuarda20.getText().toString());

                AutoCompleteTextView productoGuardar21 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto21);
                TextView precioGuarda21 = (TextView) findViewById(R.id.precioProducto21);
                TextView descGuarda21 = (TextView) findViewById(R.id.porcentajeDescProducto21);
                EditText cantGuarda21 = (EditText) findViewById(R.id.cantidadProducto21);
                TextView totalGuarda21 = (TextView) findViewById(R.id.totalProducto21);
                String ProductoGuardar21 = String.valueOf(productoGuardar21.getText().toString());
                String PrecioGuardar21 = String.valueOf(precioGuarda21.getText().toString());
                String DescuentoGuardar21 = String.valueOf(descGuarda21.getText().toString());
                String CantidadGuardar21 = String.valueOf(cantGuarda21.getText().toString());
                String TotalGuardar21 = String.valueOf(totalGuarda21.getText().toString());

                AutoCompleteTextView productoGuardar22 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto22);
                TextView precioGuarda22 = (TextView) findViewById(R.id.precioProducto22);
                TextView descGuarda22 = (TextView) findViewById(R.id.porcentajeDescProducto22);
                EditText cantGuarda22 = (EditText) findViewById(R.id.cantidadProducto22);
                TextView totalGuarda22 = (TextView) findViewById(R.id.totalProducto22);
                String ProductoGuardar22 = String.valueOf(productoGuardar22.getText().toString());
                String PrecioGuardar22 = String.valueOf(precioGuarda22.getText().toString());
                String DescuentoGuardar22 = String.valueOf(descGuarda22.getText().toString());
                String CantidadGuardar22 = String.valueOf(cantGuarda22.getText().toString());
                String TotalGuardar22 = String.valueOf(totalGuarda22.getText().toString());

                AutoCompleteTextView productoGuardar23 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto23);
                TextView precioGuarda23 = (TextView) findViewById(R.id.precioProducto23);
                TextView descGuarda23 = (TextView) findViewById(R.id.porcentajeDescProducto23);
                EditText cantGuarda23 = (EditText) findViewById(R.id.cantidadProducto23);
                TextView totalGuarda23 = (TextView) findViewById(R.id.totalProducto23);
                String ProductoGuardar23 = String.valueOf(productoGuardar23.getText().toString());
                String PrecioGuardar23 = String.valueOf(precioGuarda23.getText().toString());
                String DescuentoGuardar23 = String.valueOf(descGuarda23.getText().toString());
                String CantidadGuardar23 = String.valueOf(cantGuarda23.getText().toString());
                String TotalGuardar23 = String.valueOf(totalGuarda23.getText().toString());

                AutoCompleteTextView productoGuardar24 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto24);
                TextView precioGuarda24 = (TextView) findViewById(R.id.precioProducto24);
                TextView descGuarda24 = (TextView) findViewById(R.id.porcentajeDescProducto24);
                EditText cantGuarda24 = (EditText) findViewById(R.id.cantidadProducto24);
                TextView totalGuarda24 = (TextView) findViewById(R.id.totalProducto24);
                String ProductoGuardar24 = String.valueOf(productoGuardar24.getText().toString());
                String PrecioGuardar24 = String.valueOf(precioGuarda24.getText().toString());
                String DescuentoGuardar24 = String.valueOf(descGuarda24.getText().toString());
                String CantidadGuardar24 = String.valueOf(cantGuarda24.getText().toString());
                String TotalGuardar24 = String.valueOf(totalGuarda24.getText().toString());

                AutoCompleteTextView productoGuardar25 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto25);
                TextView precioGuarda25 = (TextView) findViewById(R.id.precioProducto25);
                TextView descGuarda25 = (TextView) findViewById(R.id.porcentajeDescProducto25);
                EditText cantGuarda25 = (EditText) findViewById(R.id.cantidadProducto25);
                TextView totalGuarda25 = (TextView) findViewById(R.id.totalProducto25);
                String ProductoGuardar25 = String.valueOf(productoGuardar25.getText().toString());
                String PrecioGuardar25 = String.valueOf(precioGuarda25.getText().toString());
                String DescuentoGuardar25 = String.valueOf(descGuarda25.getText().toString());
                String CantidadGuardar25 = String.valueOf(cantGuarda25.getText().toString());
                String TotalGuardar25 = String.valueOf(totalGuarda25.getText().toString());

                AutoCompleteTextView productoGuardar26 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto26);
                TextView precioGuarda26 = (TextView) findViewById(R.id.precioProducto26);
                TextView descGuarda26 = (TextView) findViewById(R.id.porcentajeDescProducto26);
                EditText cantGuarda26 = (EditText) findViewById(R.id.cantidadProducto26);
                TextView totalGuarda26 = (TextView) findViewById(R.id.totalProducto26);
                String ProductoGuardar26 = String.valueOf(productoGuardar26.getText().toString());
                String PrecioGuardar26 = String.valueOf(precioGuarda26.getText().toString());
                String DescuentoGuardar26 = String.valueOf(descGuarda26.getText().toString());
                String CantidadGuardar26 = String.valueOf(cantGuarda26.getText().toString());
                String TotalGuardar26 = String.valueOf(totalGuarda26.getText().toString());

                AutoCompleteTextView productoGuardar27 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto27);
                TextView precioGuarda27 = (TextView) findViewById(R.id.precioProducto27);
                TextView descGuarda27 = (TextView) findViewById(R.id.porcentajeDescProducto27);
                EditText cantGuarda27 = (EditText) findViewById(R.id.cantidadProducto27);
                TextView totalGuarda27 = (TextView) findViewById(R.id.totalProducto27);
                String ProductoGuardar27 = String.valueOf(productoGuardar27.getText().toString());
                String PrecioGuardar27 = String.valueOf(precioGuarda27.getText().toString());
                String DescuentoGuardar27 = String.valueOf(descGuarda27.getText().toString());
                String CantidadGuardar27 = String.valueOf(cantGuarda27.getText().toString());
                String TotalGuardar27 = String.valueOf(totalGuarda27.getText().toString());

                AutoCompleteTextView productoGuardar28 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto28);
                TextView precioGuarda28 = (TextView) findViewById(R.id.precioProducto28);
                TextView descGuarda28 = (TextView) findViewById(R.id.porcentajeDescProducto28);
                EditText cantGuarda28 = (EditText) findViewById(R.id.cantidadProducto28);
                TextView totalGuarda28 = (TextView) findViewById(R.id.totalProducto28);
                String ProductoGuardar28 = String.valueOf(productoGuardar28.getText().toString());
                String PrecioGuardar28 = String.valueOf(precioGuarda28.getText().toString());
                String DescuentoGuardar28 = String.valueOf(descGuarda28.getText().toString());
                String CantidadGuardar28 = String.valueOf(cantGuarda28.getText().toString());
                String TotalGuardar28 = String.valueOf(totalGuarda28.getText().toString());

                AutoCompleteTextView productoGuardar29 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto29);
                TextView precioGuarda29 = (TextView) findViewById(R.id.precioProducto29);
                TextView descGuarda29 = (TextView) findViewById(R.id.porcentajeDescProducto29);
                EditText cantGuarda29 = (EditText) findViewById(R.id.cantidadProducto29);
                TextView totalGuarda29 = (TextView) findViewById(R.id.totalProducto29);
                String ProductoGuardar29 = String.valueOf(productoGuardar29.getText().toString());
                String PrecioGuardar29 = String.valueOf(precioGuarda29.getText().toString());
                String DescuentoGuardar29 = String.valueOf(descGuarda29.getText().toString());
                String CantidadGuardar29 = String.valueOf(cantGuarda29.getText().toString());
                String TotalGuardar29 = String.valueOf(totalGuarda29.getText().toString());

                AutoCompleteTextView productoGuardar30 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto30);
                TextView precioGuarda30 = (TextView) findViewById(R.id.precioProducto30);
                TextView descGuarda30 = (TextView) findViewById(R.id.porcentajeDescProducto30);
                EditText cantGuarda30 = (EditText) findViewById(R.id.cantidadProducto30);
                TextView totalGuarda30 = (TextView) findViewById(R.id.totalProducto30);
                String ProductoGuardar30 = String.valueOf(productoGuardar30.getText().toString());
                String PrecioGuardar30 = String.valueOf(precioGuarda30.getText().toString());
                String DescuentoGuardar30 = String.valueOf(descGuarda30.getText().toString());
                String CantidadGuardar30 = String.valueOf(cantGuarda30.getText().toString());
                String TotalGuardar30 = String.valueOf(totalGuarda30.getText().toString());

                AutoCompleteTextView productoGuardar31 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto31);
                TextView precioGuarda31 = (TextView) findViewById(R.id.precioProducto31);
                TextView descGuarda31 = (TextView) findViewById(R.id.porcentajeDescProducto31);
                EditText cantGuarda31 = (EditText) findViewById(R.id.cantidadProducto31);
                TextView totalGuarda31 = (TextView) findViewById(R.id.totalProducto31);
                String ProductoGuardar31 = String.valueOf(productoGuardar31.getText().toString());
                String PrecioGuardar31 = String.valueOf(precioGuarda31.getText().toString());
                String DescuentoGuardar31 = String.valueOf(descGuarda31.getText().toString());
                String CantidadGuardar31 = String.valueOf(cantGuarda31.getText().toString());
                String TotalGuardar31 = String.valueOf(totalGuarda31.getText().toString());

                AutoCompleteTextView productoGuardar32 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto32);
                TextView precioGuarda32 = (TextView) findViewById(R.id.precioProducto32);
                TextView descGuarda32 = (TextView) findViewById(R.id.porcentajeDescProducto32);
                EditText cantGuarda32 = (EditText) findViewById(R.id.cantidadProducto32);
                TextView totalGuarda32 = (TextView) findViewById(R.id.totalProducto32);
                String ProductoGuardar32 = String.valueOf(productoGuardar32.getText().toString());
                String PrecioGuardar32 = String.valueOf(precioGuarda32.getText().toString());
                String DescuentoGuardar32 = String.valueOf(descGuarda32.getText().toString());
                String CantidadGuardar32 = String.valueOf(cantGuarda32.getText().toString());
                String TotalGuardar32 = String.valueOf(totalGuarda32.getText().toString());

                AutoCompleteTextView productoGuardar33 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto33);
                TextView precioGuarda33 = (TextView) findViewById(R.id.precioProducto33);
                TextView descGuarda33 = (TextView) findViewById(R.id.porcentajeDescProducto33);
                EditText cantGuarda33 = (EditText) findViewById(R.id.cantidadProducto33);
                TextView totalGuarda33 = (TextView) findViewById(R.id.totalProducto33);
                String ProductoGuardar33 = String.valueOf(productoGuardar33.getText().toString());
                String PrecioGuardar33 = String.valueOf(precioGuarda33.getText().toString());
                String DescuentoGuardar33 = String.valueOf(descGuarda33.getText().toString());
                String CantidadGuardar33 = String.valueOf(cantGuarda33.getText().toString());
                String TotalGuardar33 = String.valueOf(totalGuarda33.getText().toString());

                AutoCompleteTextView productoGuardar34 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto34);
                TextView precioGuarda34 = (TextView) findViewById(R.id.precioProducto34);
                TextView descGuarda34 = (TextView) findViewById(R.id.porcentajeDescProducto34);
                EditText cantGuarda34 = (EditText) findViewById(R.id.cantidadProducto34);
                TextView totalGuarda34 = (TextView) findViewById(R.id.totalProducto34);
                String ProductoGuardar34 = String.valueOf(productoGuardar34.getText().toString());
                String PrecioGuardar34 = String.valueOf(precioGuarda34.getText().toString());
                String DescuentoGuardar34 = String.valueOf(descGuarda34.getText().toString());
                String CantidadGuardar34 = String.valueOf(cantGuarda34.getText().toString());
                String TotalGuardar34 = String.valueOf(totalGuarda34.getText().toString());

                AutoCompleteTextView productoGuardar35 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto35);
                TextView precioGuarda35 = (TextView) findViewById(R.id.precioProducto35);
                TextView descGuarda35 = (TextView) findViewById(R.id.porcentajeDescProducto35);
                EditText cantGuarda35 = (EditText) findViewById(R.id.cantidadProducto35);
                TextView totalGuarda35 = (TextView) findViewById(R.id.totalProducto35);
                String ProductoGuardar35 = String.valueOf(productoGuardar35.getText().toString());
                String PrecioGuardar35 = String.valueOf(precioGuarda35.getText().toString());
                String DescuentoGuardar35 = String.valueOf(descGuarda35.getText().toString());
                String CantidadGuardar35 = String.valueOf(cantGuarda35.getText().toString());
                String TotalGuardar35 = String.valueOf(totalGuarda35.getText().toString());

                AutoCompleteTextView productoGuardar36 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto36);
                TextView precioGuarda36 = (TextView) findViewById(R.id.precioProducto36);
                TextView descGuarda36 = (TextView) findViewById(R.id.porcentajeDescProducto36);
                EditText cantGuarda36 = (EditText) findViewById(R.id.cantidadProducto36);
                TextView totalGuarda36 = (TextView) findViewById(R.id.totalProducto36);
                String ProductoGuardar36 = String.valueOf(productoGuardar36.getText().toString());
                String PrecioGuardar36 = String.valueOf(precioGuarda36.getText().toString());
                String DescuentoGuardar36 = String.valueOf(descGuarda36.getText().toString());
                String CantidadGuardar36 = String.valueOf(cantGuarda36.getText().toString());
                String TotalGuardar36 = String.valueOf(totalGuarda36.getText().toString());

                AutoCompleteTextView productoGuardar37 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto37);
                TextView precioGuarda37 = (TextView) findViewById(R.id.precioProducto37);
                TextView descGuarda37 = (TextView) findViewById(R.id.porcentajeDescProducto37);
                EditText cantGuarda37 = (EditText) findViewById(R.id.cantidadProducto37);
                TextView totalGuarda37 = (TextView) findViewById(R.id.totalProducto37);
                String ProductoGuardar37 = String.valueOf(productoGuardar37.getText().toString());
                String PrecioGuardar37 = String.valueOf(precioGuarda37.getText().toString());
                String DescuentoGuardar37 = String.valueOf(descGuarda37.getText().toString());
                String CantidadGuardar37 = String.valueOf(cantGuarda37.getText().toString());
                String TotalGuardar37 = String.valueOf(totalGuarda37.getText().toString());

                AutoCompleteTextView productoGuardar38 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto38);
                TextView precioGuarda38 = (TextView) findViewById(R.id.precioProducto38);
                TextView descGuarda38 = (TextView) findViewById(R.id.porcentajeDescProducto38);
                EditText cantGuarda38 = (EditText) findViewById(R.id.cantidadProducto38);
                TextView totalGuarda38 = (TextView) findViewById(R.id.totalProducto38);
                String ProductoGuardar38 = String.valueOf(productoGuardar38.getText().toString());
                String PrecioGuardar38 = String.valueOf(precioGuarda38.getText().toString());
                String DescuentoGuardar38 = String.valueOf(descGuarda38.getText().toString());
                String CantidadGuardar38 = String.valueOf(cantGuarda38.getText().toString());
                String TotalGuardar38 = String.valueOf(totalGuarda38.getText().toString());

                AutoCompleteTextView productoGuardar39 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto39);
                TextView precioGuarda39 = (TextView) findViewById(R.id.precioProducto39);
                TextView descGuarda39 = (TextView) findViewById(R.id.porcentajeDescProducto39);
                EditText cantGuarda39 = (EditText) findViewById(R.id.cantidadProducto39);
                TextView totalGuarda39 = (TextView) findViewById(R.id.totalProducto39);
                String ProductoGuardar39 = String.valueOf(productoGuardar39.getText().toString());
                String PrecioGuardar39 = String.valueOf(precioGuarda39.getText().toString());
                String DescuentoGuardar39 = String.valueOf(descGuarda39.getText().toString());
                String CantidadGuardar39 = String.valueOf(cantGuarda39.getText().toString());
                String TotalGuardar39 = String.valueOf(totalGuarda39.getText().toString());

                AutoCompleteTextView productoGuardar40 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto40);
                TextView precioGuarda40 = (TextView) findViewById(R.id.precioProducto40);
                TextView descGuarda40 = (TextView) findViewById(R.id.porcentajeDescProducto40);
                EditText cantGuarda40 = (EditText) findViewById(R.id.cantidadProducto40);
                TextView totalGuarda40 = (TextView) findViewById(R.id.totalProducto40);
                String ProductoGuardar40 = String.valueOf(productoGuardar40.getText().toString());
                String PrecioGuardar40 = String.valueOf(precioGuarda40.getText().toString());
                String DescuentoGuardar40 = String.valueOf(descGuarda40.getText().toString());
                String CantidadGuardar40 = String.valueOf(cantGuarda40.getText().toString());
                String TotalGuardar40 = String.valueOf(totalGuarda40.getText().toString());

                AutoCompleteTextView productoGuardar41 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto41);
                TextView precioGuarda41 = (TextView) findViewById(R.id.precioProducto41);
                TextView descGuarda41 = (TextView) findViewById(R.id.porcentajeDescProducto41);
                EditText cantGuarda41 = (EditText) findViewById(R.id.cantidadProducto41);
                TextView totalGuarda41 = (TextView) findViewById(R.id.totalProducto41);
                String ProductoGuardar41 = String.valueOf(productoGuardar41.getText().toString());
                String PrecioGuardar41 = String.valueOf(precioGuarda41.getText().toString());
                String DescuentoGuardar41 = String.valueOf(descGuarda41.getText().toString());
                String CantidadGuardar41 = String.valueOf(cantGuarda41.getText().toString());
                String TotalGuardar41 = String.valueOf(totalGuarda41.getText().toString());

                AutoCompleteTextView productoGuardar42 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto42);
                TextView precioGuarda42 = (TextView) findViewById(R.id.precioProducto42);
                TextView descGuarda42 = (TextView) findViewById(R.id.porcentajeDescProducto42);
                EditText cantGuarda42 = (EditText) findViewById(R.id.cantidadProducto42);
                TextView totalGuarda42 = (TextView) findViewById(R.id.totalProducto42);
                String ProductoGuardar42 = String.valueOf(productoGuardar42.getText().toString());
                String PrecioGuardar42 = String.valueOf(precioGuarda42.getText().toString());
                String DescuentoGuardar42 = String.valueOf(descGuarda42.getText().toString());
                String CantidadGuardar42 = String.valueOf(cantGuarda42.getText().toString());
                String TotalGuardar42 = String.valueOf(totalGuarda42.getText().toString());

                AutoCompleteTextView productoGuardar43 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto43);
                TextView precioGuarda43 = (TextView) findViewById(R.id.precioProducto43);
                TextView descGuarda43 = (TextView) findViewById(R.id.porcentajeDescProducto43);
                EditText cantGuarda43 = (EditText) findViewById(R.id.cantidadProducto43);
                TextView totalGuarda43 = (TextView) findViewById(R.id.totalProducto43);
                String ProductoGuardar43 = String.valueOf(productoGuardar43.getText().toString());
                String PrecioGuardar43 = String.valueOf(precioGuarda43.getText().toString());
                String DescuentoGuardar43 = String.valueOf(descGuarda43.getText().toString());
                String CantidadGuardar43 = String.valueOf(cantGuarda43.getText().toString());
                String TotalGuardar43 = String.valueOf(totalGuarda43.getText().toString());

                AutoCompleteTextView productoGuardar44 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto44);
                TextView precioGuarda44 = (TextView) findViewById(R.id.precioProducto44);
                TextView descGuarda44 = (TextView) findViewById(R.id.porcentajeDescProducto44);
                EditText cantGuarda44 = (EditText) findViewById(R.id.cantidadProducto44);
                TextView totalGuarda44 = (TextView) findViewById(R.id.totalProducto44);
                String ProductoGuardar44 = String.valueOf(productoGuardar44.getText().toString());
                String PrecioGuardar44 = String.valueOf(precioGuarda44.getText().toString());
                String DescuentoGuardar44 = String.valueOf(descGuarda44.getText().toString());
                String CantidadGuardar44 = String.valueOf(cantGuarda44.getText().toString());
                String TotalGuardar44 = String.valueOf(totalGuarda44.getText().toString());

                AutoCompleteTextView productoGuardar45 = (AutoCompleteTextView)findViewById(R.id.autoCompleteProducto45);
                TextView precioGuarda45 = (TextView) findViewById(R.id.precioProducto45);
                TextView descGuarda45 = (TextView) findViewById(R.id.porcentajeDescProducto45);
                EditText cantGuarda45 = (EditText) findViewById(R.id.cantidadProducto45);
                TextView totalGuarda45 = (TextView) findViewById(R.id.totalProducto45);
                String ProductoGuardar45 = String.valueOf(productoGuardar45.getText().toString());
                String PrecioGuardar45 = String.valueOf(precioGuarda45.getText().toString());
                String DescuentoGuardar45 = String.valueOf(descGuarda45.getText().toString());
                String CantidadGuardar45 = String.valueOf(cantGuarda45.getText().toString());
                String TotalGuardar45 = String.valueOf(totalGuarda45.getText().toString());

                TextView SumaTotal = (TextView) findViewById(R.id.txtSuma);
                TextView DescuentoTotal = (TextView) findViewById(R.id.txtdescuento);
                TextView TotalTotal = (TextView) findViewById(R.id.txtTotal);
                String Suma_Total = String.valueOf(SumaTotal.getText().toString());
                String Descuento_Total = String.valueOf(DescuentoTotal.getText().toString());
                String Total_Total = String.valueOf(TotalTotal.getText().toString());


                if(ProductoGuardar1.toString().length() <= 0){
                    Toast toast = Toast.makeText(getApplicationContext(), "Debe capturar algo para la parte de mano", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                if(ProductoGuardar23.toString().length() <= 0){
                    Toast toast = Toast.makeText(getApplicationContext(), "Debe capturar algo para la parte de almacen", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }

                double sumaMano = 0;
                double sumaTotal = 0;
                double totalOperacionM1 = 0;
                double totalOperacionM2 = 0;
                double totalOperacionM3 = 0;
                double totalOperacionM4 = 0;
                double totalOperacionM5 = 0;
                double totalOperacionM6 = 0;
                double totalOperacionM7 = 0;
                double totalOperacionM8 = 0;
                double totalOperacionM9 = 0;
                double totalOperacionM10 = 0;
                double totalOperacionM11 = 0;
                double totalOperacionM12 = 0;
                double totalOperacionM13 = 0;
                double totalOperacionM14 = 0;
                double totalOperacionM15 = 0;
                double totalOperacionM16 = 0;
                double totalOperacionM17 = 0;
                double totalOperacionM18 = 0;
                double totalOperacionM19 = 0;
                double totalOperacionM20 = 0;
                double totalOperacionM21 = 0;
                double totalOperacionM22 = 0;

                totalOperacionM1 = Double.parseDouble(TotalGuardar1);
                totalOperacionM2 = Double.parseDouble(TotalGuardar2);
                totalOperacionM3 = Double.parseDouble(TotalGuardar3);
                totalOperacionM4 = Double.parseDouble(TotalGuardar4);
                totalOperacionM5 = Double.parseDouble(TotalGuardar5);
                totalOperacionM6 = Double.parseDouble(TotalGuardar6);
                totalOperacionM7 = Double.parseDouble(TotalGuardar7);
                totalOperacionM8 = Double.parseDouble(TotalGuardar8);
                totalOperacionM9 = Double.parseDouble(TotalGuardar9);
                totalOperacionM10 = Double.parseDouble(TotalGuardar10);
                totalOperacionM11 = Double.parseDouble(TotalGuardar11);
                totalOperacionM12 = Double.parseDouble(TotalGuardar12);
                totalOperacionM13 = Double.parseDouble(TotalGuardar13);
                totalOperacionM14 = Double.parseDouble(TotalGuardar14);
                totalOperacionM15 = Double.parseDouble(TotalGuardar15);
                totalOperacionM16 = Double.parseDouble(TotalGuardar16);
                totalOperacionM17 = Double.parseDouble(TotalGuardar17);
                totalOperacionM18 = Double.parseDouble(TotalGuardar18);
                totalOperacionM19 = Double.parseDouble(TotalGuardar19);
                totalOperacionM20 = Double.parseDouble(TotalGuardar20);
                totalOperacionM21 = Double.parseDouble(TotalGuardar21);
                totalOperacionM22 = Double.parseDouble(TotalGuardar22);

                if(totalOperacionM1 > 0) {
                    sumaMano += totalOperacionM1;
                    sumaTotal += totalOperacionM1;
                }
                if(totalOperacionM2 > 0) {
                    sumaMano += totalOperacionM2;
                    sumaTotal += totalOperacionM2;
                }
                if(totalOperacionM3 > 0) {
                    sumaMano += totalOperacionM3;
                    sumaTotal += totalOperacionM3;
                }
                if(totalOperacionM4 > 0) {
                    sumaMano += totalOperacionM4;
                    sumaTotal += totalOperacionM4;
                }
                if(totalOperacionM5 > 0) {
                    sumaMano += totalOperacionM5;
                    sumaTotal += totalOperacionM5;
                }
                if(totalOperacionM6 > 0) {
                    sumaMano += totalOperacionM6;
                    sumaTotal += totalOperacionM6;
                }
                if(totalOperacionM7 > 0) {
                    sumaMano += totalOperacionM7;
                    sumaTotal += totalOperacionM7;
                }
                if(totalOperacionM8 > 0) {
                    sumaMano += totalOperacionM8;
                    sumaTotal += totalOperacionM8;
                }
                if(totalOperacionM9 > 0) {
                    sumaMano += totalOperacionM9;
                    sumaTotal += totalOperacionM9;
                }
                if(totalOperacionM10 > 0) {
                    sumaMano += totalOperacionM10;
                    sumaTotal += totalOperacionM10;
                }
                if(totalOperacionM11 > 0) {
                    sumaMano += totalOperacionM11;
                    sumaTotal += totalOperacionM11;
                }
                if(totalOperacionM12 > 0) {
                    sumaMano += totalOperacionM12;
                    sumaTotal += totalOperacionM12;
                }
                if(totalOperacionM13 > 0) {
                    sumaMano += totalOperacionM13;
                    sumaTotal += totalOperacionM13;
                }
                if(totalOperacionM14 > 0) {
                    sumaMano += totalOperacionM14;
                    sumaTotal += totalOperacionM14;
                }
                if(totalOperacionM15 > 0) {
                    sumaMano += totalOperacionM15;
                    sumaTotal += totalOperacionM15;
                }
                if(totalOperacionM16 > 0) {
                    sumaMano += totalOperacionM16;
                    sumaTotal += totalOperacionM16;
                }
                if(totalOperacionM17 > 0) {
                    sumaMano += totalOperacionM17;
                    sumaTotal += totalOperacionM17;
                }
                if(totalOperacionM18 > 0) {
                    sumaMano += totalOperacionM18;
                    sumaTotal += totalOperacionM18;
                }
                if(totalOperacionM19 > 0) {
                    sumaMano += totalOperacionM19;
                    sumaTotal += totalOperacionM19;
                }
                if(totalOperacionM20 > 0) {
                    sumaMano += totalOperacionM20;
                    sumaTotal += totalOperacionM20;
                }
                if(totalOperacionM21 > 0) {
                    sumaMano += totalOperacionM21;
                    sumaTotal += totalOperacionM21;
                }
                if(totalOperacionM22 > 0) {
                    sumaMano += totalOperacionM22;
                    sumaTotal += totalOperacionM22;
                }

                double sumaAlmacen = 0;
                double totalOperacion1 = 0;
                double totalOperacion2 = 0;
                double totalOperacion3 = 0;
                double totalOperacion4 = 0;
                double totalOperacion5 = 0;
                double totalOperacion6 = 0;
                double totalOperacion7 = 0;
                double totalOperacion8 = 0;
                double totalOperacion9 = 0;
                double totalOperacion10 = 0;
                double totalOperacion11 = 0;
                double totalOperacion12 = 0;
                double totalOperacion13 = 0;
                double totalOperacion14 = 0;
                double totalOperacion15 = 0;
                double totalOperacion16 = 0;
                double totalOperacion17 = 0;
                double totalOperacion18 = 0;
                double totalOperacion19 = 0;
                double totalOperacion20 = 0;
                double totalOperacion21 = 0;
                double totalOperacion22 = 0;
                double totalOperacion23 = 0;

                totalOperacion1 = Double.parseDouble(TotalGuardar23);
                totalOperacion2 = Double.parseDouble(TotalGuardar24);
                totalOperacion3 = Double.parseDouble(TotalGuardar25);
                totalOperacion4 = Double.parseDouble(TotalGuardar26);
                totalOperacion5 = Double.parseDouble(TotalGuardar27);
                totalOperacion6 = Double.parseDouble(TotalGuardar28);
                totalOperacion7 = Double.parseDouble(TotalGuardar29);
                totalOperacion8 = Double.parseDouble(TotalGuardar30);
                totalOperacion9 = Double.parseDouble(TotalGuardar31);
                totalOperacion10 = Double.parseDouble(TotalGuardar32);
                totalOperacion11 = Double.parseDouble(TotalGuardar33);
                totalOperacion12 = Double.parseDouble(TotalGuardar34);
                totalOperacion13 = Double.parseDouble(TotalGuardar35);
                totalOperacion14 = Double.parseDouble(TotalGuardar36);
                totalOperacion15 = Double.parseDouble(TotalGuardar37);
                totalOperacion16 = Double.parseDouble(TotalGuardar38);
                totalOperacion17 = Double.parseDouble(TotalGuardar39);
                totalOperacion18 = Double.parseDouble(TotalGuardar40);
                totalOperacion19 = Double.parseDouble(TotalGuardar41);
                totalOperacion20 = Double.parseDouble(TotalGuardar42);
                totalOperacion21 = Double.parseDouble(TotalGuardar43);
                totalOperacion22 = Double.parseDouble(TotalGuardar44);
                totalOperacion23 = Double.parseDouble(TotalGuardar45);


                if(totalOperacion1 > 0) {
                    sumaAlmacen += totalOperacion1;
                    sumaTotal += totalOperacion1;
                }
                if(totalOperacion2 > 0) {
                    sumaAlmacen += totalOperacion2;
                    sumaTotal += totalOperacion2;
                }
                if(totalOperacion3 > 0) {
                    sumaAlmacen += totalOperacion3;
                    sumaTotal += totalOperacion3;
                }
                if(totalOperacion4 > 0) {
                    sumaAlmacen += totalOperacion4;
                    sumaTotal += totalOperacion4;
                }
                if(totalOperacion5 > 0) {
                    sumaAlmacen += totalOperacion5;
                    sumaTotal += totalOperacion5;
                }
                if(totalOperacion6 > 0) {
                    sumaAlmacen += totalOperacion6;
                    sumaTotal += totalOperacion6;
                }
                if(totalOperacion7 > 0) {
                    sumaAlmacen += totalOperacion7;
                    sumaTotal += totalOperacion7;
                }
                if(totalOperacion8 > 0) {
                    sumaAlmacen += totalOperacion8;
                    sumaTotal += totalOperacion8;
                }
                if(totalOperacion9 > 0) {
                    sumaAlmacen += totalOperacion9;
                    sumaTotal += totalOperacion9;
                }
                if(totalOperacion10 > 0) {
                    sumaAlmacen += totalOperacion10;
                    sumaTotal += totalOperacion10;
                }
                if(totalOperacion11 > 0) {
                    sumaAlmacen += totalOperacion11;
                    sumaTotal += totalOperacion11;
                }
                if(totalOperacion12 > 0) {
                    sumaAlmacen += totalOperacion12;
                    sumaTotal += totalOperacion12;
                }
                if(totalOperacion13 > 0) {
                    sumaAlmacen += totalOperacion13;
                    sumaTotal += totalOperacion13;
                }
                if(totalOperacion14 > 0) {
                    sumaAlmacen += totalOperacion14;
                    sumaTotal += totalOperacion14;
                }
                if(totalOperacion15 > 0) {
                    sumaAlmacen += totalOperacion15;
                    sumaTotal += totalOperacion15;
                }
                if(totalOperacion16 > 0) {
                    sumaAlmacen += totalOperacion16;
                    sumaTotal += totalOperacion16;
                }
                if(totalOperacion17 > 0) {
                    sumaAlmacen += totalOperacion17;
                    sumaTotal += totalOperacion17;
                }
                if(totalOperacion18 > 0) {
                    sumaAlmacen += totalOperacion18;
                    sumaTotal += totalOperacion18;
                }
                if(totalOperacion19 > 0) {
                    sumaAlmacen += totalOperacion19;
                    sumaTotal += totalOperacion19;
                }
                if(totalOperacion20 > 0) {
                    sumaAlmacen += totalOperacion20;
                    sumaTotal += totalOperacion20;
                }
                if(totalOperacion21 > 0) {
                    sumaAlmacen += totalOperacion21;
                    sumaTotal += totalOperacion21;
                }
                if(totalOperacion22 > 0) {
                    sumaAlmacen += totalOperacion22;
                    sumaTotal += totalOperacion22;
                }
                if(totalOperacion23 > 0) {
                    sumaAlmacen += totalOperacion23;
                    sumaTotal += totalOperacion23;
                }


                ///SE VALIDA LA PARTE DE PEDIDO COMBINADO
                if(sumaMano <= 0){
                    Toast toast = Toast.makeText(getApplicationContext(), "Debe capturar algun producto en la venta de mano", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }

                if(sumaAlmacen < 800){
                    Toast toast = Toast.makeText(getApplicationContext(), "En el pedido combinado la parte de almacen debe ser minimo 800 pesos", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }

                if(sumaTotal < 1600){
                    Toast toast = Toast.makeText(getApplicationContext(), "En el pedido combinado el monto minimo es de 1600", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }


                //Encabezado del pedido
                explicit_intent.putExtra("cliente",clienteGuardar);
                explicit_intent.putExtra("familia",familiaGuardar);
                explicit_intent.putExtra("subtipo",subtipoGuardar);
                explicit_intent.putExtra("tipoventa",tipoventaGuardar);
                explicit_intent.putExtra("conducto",conductoGuardar);
                explicit_intent.putExtra("comentarios",comentariosGuardar);

                //Partidas del pedido
                explicit_intent.putExtra("producto1",ProductoGuardar1);
                explicit_intent.putExtra("precio1",PrecioGuardar1);
                explicit_intent.putExtra("descuento1",DescuentoGuardar1);
                explicit_intent.putExtra("cantidad1",CantidadGuardar1);
                explicit_intent.putExtra("total1",TotalGuardar1);

                explicit_intent.putExtra("producto2",ProductoGuardar2);
                explicit_intent.putExtra("precio2",PrecioGuardar2);
                explicit_intent.putExtra("descuento2",DescuentoGuardar2);
                explicit_intent.putExtra("cantidad2",CantidadGuardar2);
                explicit_intent.putExtra("total2",TotalGuardar2);

                explicit_intent.putExtra("producto3",ProductoGuardar3);
                explicit_intent.putExtra("precio3",PrecioGuardar3);
                explicit_intent.putExtra("descuento3",DescuentoGuardar3);
                explicit_intent.putExtra("cantidad3",CantidadGuardar3);
                explicit_intent.putExtra("total3",TotalGuardar3);

                explicit_intent.putExtra("producto4",ProductoGuardar4);
                explicit_intent.putExtra("precio4",PrecioGuardar4);
                explicit_intent.putExtra("descuento4",DescuentoGuardar4);
                explicit_intent.putExtra("cantidad4",CantidadGuardar4);
                explicit_intent.putExtra("total4",TotalGuardar4);

                explicit_intent.putExtra("producto5",ProductoGuardar5);
                explicit_intent.putExtra("precio5",PrecioGuardar5);
                explicit_intent.putExtra("descuento5",DescuentoGuardar5);
                explicit_intent.putExtra("cantidad5",CantidadGuardar5);
                explicit_intent.putExtra("total5",TotalGuardar5);

                explicit_intent.putExtra("producto6",ProductoGuardar6);
                explicit_intent.putExtra("precio6",PrecioGuardar6);
                explicit_intent.putExtra("descuento6",DescuentoGuardar6);
                explicit_intent.putExtra("cantidad6",CantidadGuardar6);
                explicit_intent.putExtra("total6",TotalGuardar6);

                explicit_intent.putExtra("producto7",ProductoGuardar7);
                explicit_intent.putExtra("precio7",PrecioGuardar7);
                explicit_intent.putExtra("descuento7",DescuentoGuardar7);
                explicit_intent.putExtra("cantidad7",CantidadGuardar7);
                explicit_intent.putExtra("total7",TotalGuardar7);

                explicit_intent.putExtra("producto8",ProductoGuardar8);
                explicit_intent.putExtra("precio8",PrecioGuardar8);
                explicit_intent.putExtra("descuento8",DescuentoGuardar8);
                explicit_intent.putExtra("cantidad8",CantidadGuardar8);
                explicit_intent.putExtra("total8",TotalGuardar8);

                explicit_intent.putExtra("producto9",ProductoGuardar9);
                explicit_intent.putExtra("precio9",PrecioGuardar9);
                explicit_intent.putExtra("descuento9",DescuentoGuardar9);
                explicit_intent.putExtra("cantidad9",CantidadGuardar9);
                explicit_intent.putExtra("total9",TotalGuardar9);

                explicit_intent.putExtra("producto10",ProductoGuardar10);
                explicit_intent.putExtra("precio10",PrecioGuardar10);
                explicit_intent.putExtra("descuento10",DescuentoGuardar10);
                explicit_intent.putExtra("cantidad10",CantidadGuardar10);
                explicit_intent.putExtra("total10",TotalGuardar10);

                explicit_intent.putExtra("producto11",ProductoGuardar11);
                explicit_intent.putExtra("precio11",PrecioGuardar11);
                explicit_intent.putExtra("descuento11",DescuentoGuardar11);
                explicit_intent.putExtra("cantidad11",CantidadGuardar11);
                explicit_intent.putExtra("total11",TotalGuardar11);

                explicit_intent.putExtra("producto12",ProductoGuardar12);
                explicit_intent.putExtra("precio12",PrecioGuardar12);
                explicit_intent.putExtra("descuento12",DescuentoGuardar12);
                explicit_intent.putExtra("cantidad12",CantidadGuardar12);
                explicit_intent.putExtra("total12",TotalGuardar12);

                explicit_intent.putExtra("producto13",ProductoGuardar13);
                explicit_intent.putExtra("precio13",PrecioGuardar13);
                explicit_intent.putExtra("descuento13",DescuentoGuardar13);
                explicit_intent.putExtra("cantidad13",CantidadGuardar13);
                explicit_intent.putExtra("total13",TotalGuardar13);

                explicit_intent.putExtra("producto14",ProductoGuardar14);
                explicit_intent.putExtra("precio14",PrecioGuardar14);
                explicit_intent.putExtra("descuento14",DescuentoGuardar14);
                explicit_intent.putExtra("cantidad14",CantidadGuardar14);
                explicit_intent.putExtra("total14",TotalGuardar14);

                explicit_intent.putExtra("producto15",ProductoGuardar15);
                explicit_intent.putExtra("precio15",PrecioGuardar15);
                explicit_intent.putExtra("descuento15",DescuentoGuardar15);
                explicit_intent.putExtra("cantidad15",CantidadGuardar15);
                explicit_intent.putExtra("total15",TotalGuardar15);

                explicit_intent.putExtra("producto16",ProductoGuardar16);
                explicit_intent.putExtra("precio16",PrecioGuardar16);
                explicit_intent.putExtra("descuento16",DescuentoGuardar16);
                explicit_intent.putExtra("cantidad16",CantidadGuardar16);
                explicit_intent.putExtra("total16",TotalGuardar16);

                explicit_intent.putExtra("producto17",ProductoGuardar17);
                explicit_intent.putExtra("precio17",PrecioGuardar17);
                explicit_intent.putExtra("descuento17",DescuentoGuardar17);
                explicit_intent.putExtra("cantidad17",CantidadGuardar17);
                explicit_intent.putExtra("total17",TotalGuardar17);

                explicit_intent.putExtra("producto18",ProductoGuardar18);
                explicit_intent.putExtra("precio18",PrecioGuardar18);
                explicit_intent.putExtra("descuento18",DescuentoGuardar18);
                explicit_intent.putExtra("cantidad18",CantidadGuardar18);
                explicit_intent.putExtra("total18",TotalGuardar18);

                explicit_intent.putExtra("producto19",ProductoGuardar19);
                explicit_intent.putExtra("precio19",PrecioGuardar19);
                explicit_intent.putExtra("descuento19",DescuentoGuardar19);
                explicit_intent.putExtra("cantidad19",CantidadGuardar19);
                explicit_intent.putExtra("total19",TotalGuardar19);

                explicit_intent.putExtra("producto20",ProductoGuardar20);
                explicit_intent.putExtra("precio20",PrecioGuardar20);
                explicit_intent.putExtra("descuento20",DescuentoGuardar20);
                explicit_intent.putExtra("cantidad20",CantidadGuardar20);
                explicit_intent.putExtra("total20",TotalGuardar20);

                explicit_intent.putExtra("producto21",ProductoGuardar21);
                explicit_intent.putExtra("precio21",PrecioGuardar21);
                explicit_intent.putExtra("descuento21",DescuentoGuardar21);
                explicit_intent.putExtra("cantidad21",CantidadGuardar21);
                explicit_intent.putExtra("total21",TotalGuardar21);

                explicit_intent.putExtra("producto22",ProductoGuardar22);
                explicit_intent.putExtra("precio22",PrecioGuardar22);
                explicit_intent.putExtra("descuento22",DescuentoGuardar22);
                explicit_intent.putExtra("cantidad22",CantidadGuardar22);
                explicit_intent.putExtra("total22",TotalGuardar22);

                explicit_intent.putExtra("producto23",ProductoGuardar23);
                explicit_intent.putExtra("precio23",PrecioGuardar23);
                explicit_intent.putExtra("descuento23",DescuentoGuardar23);
                explicit_intent.putExtra("cantidad23",CantidadGuardar23);
                explicit_intent.putExtra("total23",TotalGuardar23);

                explicit_intent.putExtra("producto24",ProductoGuardar24);
                explicit_intent.putExtra("precio24",PrecioGuardar24);
                explicit_intent.putExtra("descuento24",DescuentoGuardar24);
                explicit_intent.putExtra("cantidad24",CantidadGuardar24);
                explicit_intent.putExtra("total24",TotalGuardar24);

                explicit_intent.putExtra("producto25",ProductoGuardar25);
                explicit_intent.putExtra("precio25",PrecioGuardar25);
                explicit_intent.putExtra("descuento25",DescuentoGuardar25);
                explicit_intent.putExtra("cantidad25",CantidadGuardar25);
                explicit_intent.putExtra("total25",TotalGuardar25);

                explicit_intent.putExtra("producto26",ProductoGuardar26);
                explicit_intent.putExtra("precio26",PrecioGuardar26);
                explicit_intent.putExtra("descuento26",DescuentoGuardar26);
                explicit_intent.putExtra("cantidad26",CantidadGuardar26);
                explicit_intent.putExtra("total26",TotalGuardar26);

                explicit_intent.putExtra("producto27",ProductoGuardar27);
                explicit_intent.putExtra("precio27",PrecioGuardar27);
                explicit_intent.putExtra("descuento27",DescuentoGuardar27);
                explicit_intent.putExtra("cantidad27",CantidadGuardar27);
                explicit_intent.putExtra("total27",TotalGuardar27);

                explicit_intent.putExtra("producto28",ProductoGuardar28);
                explicit_intent.putExtra("precio28",PrecioGuardar28);
                explicit_intent.putExtra("descuento28",DescuentoGuardar28);
                explicit_intent.putExtra("cantidad28",CantidadGuardar28);
                explicit_intent.putExtra("total28",TotalGuardar28);

                explicit_intent.putExtra("producto29",ProductoGuardar29);
                explicit_intent.putExtra("precio29",PrecioGuardar29);
                explicit_intent.putExtra("descuento29",DescuentoGuardar29);
                explicit_intent.putExtra("cantidad29",CantidadGuardar29);
                explicit_intent.putExtra("total29",TotalGuardar29);

                explicit_intent.putExtra("producto30",ProductoGuardar30);
                explicit_intent.putExtra("precio30",PrecioGuardar30);
                explicit_intent.putExtra("descuento30",DescuentoGuardar30);
                explicit_intent.putExtra("cantidad30",CantidadGuardar30);
                explicit_intent.putExtra("total30",TotalGuardar30);

                explicit_intent.putExtra("producto31",ProductoGuardar31);
                explicit_intent.putExtra("precio31",PrecioGuardar31);
                explicit_intent.putExtra("descuento31",DescuentoGuardar31);
                explicit_intent.putExtra("cantidad31",CantidadGuardar31);
                explicit_intent.putExtra("total31",TotalGuardar31);

                explicit_intent.putExtra("producto32",ProductoGuardar32);
                explicit_intent.putExtra("precio32",PrecioGuardar32);
                explicit_intent.putExtra("descuento32",DescuentoGuardar32);
                explicit_intent.putExtra("cantidad32",CantidadGuardar32);
                explicit_intent.putExtra("total32",TotalGuardar32);

                explicit_intent.putExtra("producto33",ProductoGuardar33);
                explicit_intent.putExtra("precio33",PrecioGuardar33);
                explicit_intent.putExtra("descuento33",DescuentoGuardar33);
                explicit_intent.putExtra("cantidad33",CantidadGuardar33);
                explicit_intent.putExtra("total33",TotalGuardar33);

                explicit_intent.putExtra("producto34",ProductoGuardar34);
                explicit_intent.putExtra("precio34",PrecioGuardar34);
                explicit_intent.putExtra("descuento34",DescuentoGuardar34);
                explicit_intent.putExtra("cantidad34",CantidadGuardar34);
                explicit_intent.putExtra("total34",TotalGuardar34);

                explicit_intent.putExtra("producto35",ProductoGuardar35);
                explicit_intent.putExtra("precio35",PrecioGuardar35);
                explicit_intent.putExtra("descuento35",DescuentoGuardar35);
                explicit_intent.putExtra("cantidad35",CantidadGuardar35);
                explicit_intent.putExtra("total35",TotalGuardar35);

                explicit_intent.putExtra("producto36",ProductoGuardar36);
                explicit_intent.putExtra("precio36",PrecioGuardar36);
                explicit_intent.putExtra("descuento36",DescuentoGuardar36);
                explicit_intent.putExtra("cantidad36",CantidadGuardar36);
                explicit_intent.putExtra("total36",TotalGuardar36);

                explicit_intent.putExtra("producto37",ProductoGuardar37);
                explicit_intent.putExtra("precio37",PrecioGuardar37);
                explicit_intent.putExtra("descuento37",DescuentoGuardar37);
                explicit_intent.putExtra("cantidad37",CantidadGuardar37);
                explicit_intent.putExtra("total37",TotalGuardar37);

                explicit_intent.putExtra("producto38",ProductoGuardar38);
                explicit_intent.putExtra("precio38",PrecioGuardar38);
                explicit_intent.putExtra("descuento38",DescuentoGuardar38);
                explicit_intent.putExtra("cantidad38",CantidadGuardar38);
                explicit_intent.putExtra("total38",TotalGuardar38);

                explicit_intent.putExtra("producto39",ProductoGuardar39);
                explicit_intent.putExtra("precio39",PrecioGuardar39);
                explicit_intent.putExtra("descuento39",DescuentoGuardar39);
                explicit_intent.putExtra("cantidad39",CantidadGuardar39);
                explicit_intent.putExtra("total39",TotalGuardar39);

                explicit_intent.putExtra("producto40",ProductoGuardar40);
                explicit_intent.putExtra("precio40",PrecioGuardar40);
                explicit_intent.putExtra("descuento40",DescuentoGuardar40);
                explicit_intent.putExtra("cantidad40",CantidadGuardar40);
                explicit_intent.putExtra("total40",TotalGuardar40);

                explicit_intent.putExtra("producto41",ProductoGuardar41);
                explicit_intent.putExtra("precio41",PrecioGuardar41);
                explicit_intent.putExtra("descuento41",DescuentoGuardar41);
                explicit_intent.putExtra("cantidad41",CantidadGuardar41);
                explicit_intent.putExtra("total41",TotalGuardar41);

                explicit_intent.putExtra("producto42",ProductoGuardar42);
                explicit_intent.putExtra("precio42",PrecioGuardar42);
                explicit_intent.putExtra("descuento42",DescuentoGuardar42);
                explicit_intent.putExtra("cantidad42",CantidadGuardar42);
                explicit_intent.putExtra("total42",TotalGuardar42);

                explicit_intent.putExtra("producto43",ProductoGuardar43);
                explicit_intent.putExtra("precio43",PrecioGuardar43);
                explicit_intent.putExtra("descuento43",DescuentoGuardar43);
                explicit_intent.putExtra("cantidad43",CantidadGuardar43);
                explicit_intent.putExtra("total43",TotalGuardar43);

                explicit_intent.putExtra("producto44",ProductoGuardar44);
                explicit_intent.putExtra("precio44",PrecioGuardar44);
                explicit_intent.putExtra("descuento44",DescuentoGuardar44);
                explicit_intent.putExtra("cantidad44",CantidadGuardar44);
                explicit_intent.putExtra("total44",TotalGuardar44);

                explicit_intent.putExtra("producto45",ProductoGuardar45);
                explicit_intent.putExtra("precio45",PrecioGuardar45);
                explicit_intent.putExtra("descuento45",DescuentoGuardar45);
                explicit_intent.putExtra("cantidad45",CantidadGuardar45);
                explicit_intent.putExtra("total45",TotalGuardar45);

                explicit_intent.putExtra("sumaTotal",Suma_Total);
                explicit_intent.putExtra("descuentoTotal",Descuento_Total);
                explicit_intent.putExtra("totalTotal",Total_Total);

                startActivity(explicit_intent);
            }
        });

    }

}