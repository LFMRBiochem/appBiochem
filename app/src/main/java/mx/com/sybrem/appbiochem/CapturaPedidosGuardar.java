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
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import mx.com.sybrem.appbiochem.MyDBHandler;

public class CapturaPedidosGuardar extends AppCompatActivity {

    TextView cliente, familia, subtipo, tipoventa, conducto, comentarios;

    TextView leyendaMano, leyendaAlmacen;

    TextView producto1, precio1, porcentaje1, cantidad1, total1,
    producto2, precio2, porcentaje2, cantidad2, total2,
    producto3, precio3, porcentaje3, cantidad3, total3,
    producto4, precio4, porcentaje4, cantidad4, total4,
    producto5, precio5, porcentaje5, cantidad5, total5,
    producto6, precio6, porcentaje6, cantidad6, total6,
    producto7, precio7, porcentaje7, cantidad7, total7,
    producto8, precio8, porcentaje8, cantidad8, total8,
    producto9, precio9, porcentaje9, cantidad9, total9,
    producto10, precio10, porcentaje10, cantidad10, total10,
            producto11, precio11, porcentaje11, cantidad11, total11,
            producto12, precio12, porcentaje12, cantidad12, total12,
            producto13, precio13, porcentaje13, cantidad13, total13,
            producto14, precio14, porcentaje14, cantidad14, total14,
            producto15, precio15, porcentaje15, cantidad15, total15,
            producto16, precio16, porcentaje16, cantidad16, total16,
            producto17, precio17, porcentaje17, cantidad17, total17,
            producto18, precio18, porcentaje18, cantidad18, total18,
            producto19, precio19, porcentaje19, cantidad19, total19,
            producto20, precio20, porcentaje20, cantidad20, total20,
            producto21, precio21, porcentaje21, cantidad21, total21,
            producto22, precio22, porcentaje22, cantidad22, total22,
            producto23, precio23, porcentaje23, cantidad23, total23,
            producto24, precio24, porcentaje24, cantidad24, total24,
            producto25, precio25, porcentaje25, cantidad25, total25,
            producto26, precio26, porcentaje26, cantidad26, total26,
            producto27, precio27, porcentaje27, cantidad27, total27,
            producto28, precio28, porcentaje28, cantidad28, total28,
            producto29, precio29, porcentaje29, cantidad29, total29,
            producto30, precio30, porcentaje30, cantidad30, total30,
            producto31, precio31, porcentaje31, cantidad31, total31,
            producto32, precio32, porcentaje32, cantidad32, total32,
            producto33, precio33, porcentaje33, cantidad33, total33,
            producto34, precio34, porcentaje34, cantidad34, total34,
            producto35, precio35, porcentaje35, cantidad35, total35,
            producto36, precio36, porcentaje36, cantidad36, total36,
            producto37, precio37, porcentaje37, cantidad37, total37,
            producto38, precio38, porcentaje38, cantidad38, total38,
            producto39, precio39, porcentaje39, cantidad39, total39,
            producto40, precio40, porcentaje40, cantidad40, total40,
            producto41, precio41, porcentaje41, cantidad41, total41,
            producto42, precio42, porcentaje42, cantidad42, total42,
            producto43, precio43, porcentaje43, cantidad43, total43,
            producto44, precio44, porcentaje44, cantidad44, total44,
            producto45, precio45, porcentaje45, cantidad45, total45, sumaTotal, descuentoTotal, totalTotal;

    private String usuario;

    ProgressDialog pdSender; // Mensaje de progreso en sincronización para JSonTaskSender.

    TextView mensaje1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        dbHandler.checkDBStatus(); // Tiene como fin forzar la creacion de la base de datos.

        /* Uso de la clase LocationManager para obtener la localizacion del GPS */
        LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Localizacion Local = new Localizacion();
        Local.setMainActivity(CapturaPedidosGuardar.this);
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

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_captura_pedidos_guardar);

        //////ESTA PARTE ES PARA LOS DATOS DE LA LATITUDE Y LONGITUDE///////////////////////////////
        mensaje1 = (TextView)findViewById(R.id.mensaje_idPedidos);
        mensaje1.setVisibility(View.GONE);

        //Leyendas de los almacenes
        leyendaMano = (TextView)findViewById(R.id.txtManoLeyenda);
        leyendaAlmacen = (TextView)findViewById(R.id.txtAlmacenLeyenda);

        // Se extraen los datos del otro activity CapturaPedidos2
        cliente = (TextView)findViewById(R.id.clienteGuardar);
        familia = (TextView)findViewById(R.id.FamiliaGuardar);
        subtipo = (TextView)findViewById(R.id.SubTipoGuardar);
        tipoventa = (TextView)findViewById(R.id.TipoVentaGuardar);
        conducto = (TextView)findViewById(R.id.ConductoGuardar);
        comentarios = (TextView)findViewById(R.id.comentariosGuardar);

        producto1 = (TextView)findViewById(R.id.Producto1);
        precio1 = (TextView)findViewById(R.id.Precio1);
        porcentaje1 = (TextView)findViewById(R.id.Porcentaje1);
        cantidad1 = (TextView)findViewById(R.id.Cantidad1);
        total1 = (TextView)findViewById(R.id.Total1);

        producto2 = (TextView)findViewById(R.id.Producto2);
        precio2 = (TextView)findViewById(R.id.Precio2);
        porcentaje2 = (TextView)findViewById(R.id.Porcentaje2);
        cantidad2 = (TextView)findViewById(R.id.Cantidad2);
        total2 = (TextView)findViewById(R.id.Total2);

        producto3 = (TextView)findViewById(R.id.Producto3);
        precio3 = (TextView)findViewById(R.id.Precio3);
        porcentaje3 = (TextView)findViewById(R.id.Porcentaje3);
        cantidad3 = (TextView)findViewById(R.id.Cantidad3);
        total3 = (TextView)findViewById(R.id.Total3);

        producto4 = (TextView)findViewById(R.id.Producto4);
        precio4 = (TextView)findViewById(R.id.Precio4);
        porcentaje4 = (TextView)findViewById(R.id.Porcentaje4);
        cantidad4 = (TextView)findViewById(R.id.Cantidad4);
        total4 = (TextView)findViewById(R.id.Total4);

        producto5 = (TextView)findViewById(R.id.Producto5);
        precio5 = (TextView)findViewById(R.id.Precio5);
        porcentaje5 = (TextView)findViewById(R.id.Porcentaje5);
        cantidad5 = (TextView)findViewById(R.id.Cantidad5);
        total5 = (TextView)findViewById(R.id.Total5);

        producto6 = (TextView)findViewById(R.id.Producto6);
        precio6 = (TextView)findViewById(R.id.Precio6);
        porcentaje6 = (TextView)findViewById(R.id.Porcentaje6);
        cantidad6 = (TextView)findViewById(R.id.Cantidad6);
        total6 = (TextView)findViewById(R.id.Total6);

        producto7 = (TextView)findViewById(R.id.Producto7);
        precio7 = (TextView)findViewById(R.id.Precio7);
        porcentaje7 = (TextView)findViewById(R.id.Porcentaje7);
        cantidad7 = (TextView)findViewById(R.id.Cantidad7);
        total7 = (TextView)findViewById(R.id.Total7);

        producto8 = (TextView)findViewById(R.id.Producto8);
        precio8 = (TextView)findViewById(R.id.Precio8);
        porcentaje8 = (TextView)findViewById(R.id.Porcentaje8);
        cantidad8 = (TextView)findViewById(R.id.Cantidad8);
        total8 = (TextView)findViewById(R.id.Total8);

        producto9 = (TextView)findViewById(R.id.Producto9);
        precio9 = (TextView)findViewById(R.id.Precio9);
        porcentaje9 = (TextView)findViewById(R.id.Porcentaje9);
        cantidad9 = (TextView)findViewById(R.id.Cantidad9);
        total9 = (TextView)findViewById(R.id.Total9);

        producto10 = (TextView)findViewById(R.id.Producto10);
        precio10 = (TextView)findViewById(R.id.Precio10);
        porcentaje10 = (TextView)findViewById(R.id.Porcentaje10);
        cantidad10 = (TextView)findViewById(R.id.Cantidad10);
        total10 = (TextView)findViewById(R.id.Total10);

        producto11 = (TextView)findViewById(R.id.Producto11);
        precio11 = (TextView)findViewById(R.id.Precio11);
        porcentaje11 = (TextView)findViewById(R.id.Porcentaje11);
        cantidad11 = (TextView)findViewById(R.id.Cantidad11);
        total11 = (TextView)findViewById(R.id.Total11);

        producto12 = (TextView)findViewById(R.id.Producto12);
        precio12 = (TextView)findViewById(R.id.Precio12);
        porcentaje12 = (TextView)findViewById(R.id.Porcentaje12);
        cantidad12 = (TextView)findViewById(R.id.Cantidad12);
        total12 = (TextView)findViewById(R.id.Total12);

        producto13 = (TextView)findViewById(R.id.Producto13);
        precio13 = (TextView)findViewById(R.id.Precio13);
        porcentaje13 = (TextView)findViewById(R.id.Porcentaje13);
        cantidad13 = (TextView)findViewById(R.id.Cantidad13);
        total13 = (TextView)findViewById(R.id.Total13);

        producto14 = (TextView)findViewById(R.id.Producto14);
        precio14 = (TextView)findViewById(R.id.Precio14);
        porcentaje14 = (TextView)findViewById(R.id.Porcentaje14);
        cantidad14 = (TextView)findViewById(R.id.Cantidad14);
        total14 = (TextView)findViewById(R.id.Total14);

        producto15 = (TextView)findViewById(R.id.Producto15);
        precio15 = (TextView)findViewById(R.id.Precio15);
        porcentaje15 = (TextView)findViewById(R.id.Porcentaje15);
        cantidad15 = (TextView)findViewById(R.id.Cantidad15);
        total15 = (TextView)findViewById(R.id.Total15);

        producto16 = (TextView)findViewById(R.id.Producto16);
        precio16 = (TextView)findViewById(R.id.Precio16);
        porcentaje16 = (TextView)findViewById(R.id.Porcentaje16);
        cantidad16 = (TextView)findViewById(R.id.Cantidad16);
        total16 = (TextView)findViewById(R.id.Total16);

        producto17 = (TextView)findViewById(R.id.Producto17);
        precio17 = (TextView)findViewById(R.id.Precio17);
        porcentaje17 = (TextView)findViewById(R.id.Porcentaje17);
        cantidad17 = (TextView)findViewById(R.id.Cantidad17);
        total17 = (TextView)findViewById(R.id.Total17);

        producto18 = (TextView)findViewById(R.id.Producto18);
        precio18 = (TextView)findViewById(R.id.Precio18);
        porcentaje18 = (TextView)findViewById(R.id.Porcentaje18);
        cantidad18 = (TextView)findViewById(R.id.Cantidad18);
        total18 = (TextView)findViewById(R.id.Total18);

        producto19 = (TextView)findViewById(R.id.Producto19);
        precio19 = (TextView)findViewById(R.id.Precio19);
        porcentaje19 = (TextView)findViewById(R.id.Porcentaje19);
        cantidad19 = (TextView)findViewById(R.id.Cantidad19);
        total19 = (TextView)findViewById(R.id.Total19);

        producto20 = (TextView)findViewById(R.id.Producto20);
        precio20 = (TextView)findViewById(R.id.Precio20);
        porcentaje20 = (TextView)findViewById(R.id.Porcentaje20);
        cantidad20 = (TextView)findViewById(R.id.Cantidad20);
        total20 = (TextView)findViewById(R.id.Total20);

        producto21 = (TextView)findViewById(R.id.Producto21);
        precio21 = (TextView)findViewById(R.id.Precio21);
        porcentaje21 = (TextView)findViewById(R.id.Porcentaje21);
        cantidad21 = (TextView)findViewById(R.id.Cantidad21);
        total21 = (TextView)findViewById(R.id.Total21);

        producto22 = (TextView)findViewById(R.id.Producto22);
        precio22 = (TextView)findViewById(R.id.Precio22);
        porcentaje22 = (TextView)findViewById(R.id.Porcentaje22);
        cantidad22 = (TextView)findViewById(R.id.Cantidad22);
        total22 = (TextView)findViewById(R.id.Total22);

        producto23 = (TextView)findViewById(R.id.Producto23);
        precio23 = (TextView)findViewById(R.id.Precio23);
        porcentaje23 = (TextView)findViewById(R.id.Porcentaje23);
        cantidad23 = (TextView)findViewById(R.id.Cantidad23);
        total23 = (TextView)findViewById(R.id.Total23);

        producto24 = (TextView)findViewById(R.id.Producto24);
        precio24 = (TextView)findViewById(R.id.Precio24);
        porcentaje24 = (TextView)findViewById(R.id.Porcentaje24);
        cantidad24 = (TextView)findViewById(R.id.Cantidad24);
        total24 = (TextView)findViewById(R.id.Total24);

        producto25 = (TextView)findViewById(R.id.Producto25);
        precio25 = (TextView)findViewById(R.id.Precio25);
        porcentaje25 = (TextView)findViewById(R.id.Porcentaje25);
        cantidad25 = (TextView)findViewById(R.id.Cantidad25);
        total25 = (TextView)findViewById(R.id.Total25);

        producto26 = (TextView)findViewById(R.id.Producto26);
        precio26 = (TextView)findViewById(R.id.Precio26);
        porcentaje26 = (TextView)findViewById(R.id.Porcentaje26);
        cantidad26 = (TextView)findViewById(R.id.Cantidad26);
        total26 = (TextView)findViewById(R.id.Total26);

        producto27 = (TextView)findViewById(R.id.Producto27);
        precio27 = (TextView)findViewById(R.id.Precio27);
        porcentaje27 = (TextView)findViewById(R.id.Porcentaje27);
        cantidad27 = (TextView)findViewById(R.id.Cantidad27);
        total27 = (TextView)findViewById(R.id.Total27);

        producto28 = (TextView)findViewById(R.id.Producto28);
        precio28 = (TextView)findViewById(R.id.Precio28);
        porcentaje28 = (TextView)findViewById(R.id.Porcentaje28);
        cantidad28 = (TextView)findViewById(R.id.Cantidad28);
        total28 = (TextView)findViewById(R.id.Total28);

        producto29 = (TextView)findViewById(R.id.Producto29);
        precio29 = (TextView)findViewById(R.id.Precio29);
        porcentaje29 = (TextView)findViewById(R.id.Porcentaje29);
        cantidad29 = (TextView)findViewById(R.id.Cantidad29);
        total29 = (TextView)findViewById(R.id.Total29);

        producto30 = (TextView)findViewById(R.id.Producto30);
        precio30 = (TextView)findViewById(R.id.Precio30);
        porcentaje30 = (TextView)findViewById(R.id.Porcentaje30);
        cantidad30 = (TextView)findViewById(R.id.Cantidad30);
        total30 = (TextView)findViewById(R.id.Total30);

        producto31 = (TextView)findViewById(R.id.Producto31);
        precio31 = (TextView)findViewById(R.id.Precio31);
        porcentaje31 = (TextView)findViewById(R.id.Porcentaje31);
        cantidad31 = (TextView)findViewById(R.id.Cantidad31);
        total31 = (TextView)findViewById(R.id.Total31);

        producto32 = (TextView)findViewById(R.id.Producto32);
        precio32 = (TextView)findViewById(R.id.Precio32);
        porcentaje32 = (TextView)findViewById(R.id.Porcentaje32);
        cantidad32 = (TextView)findViewById(R.id.Cantidad32);
        total32 = (TextView)findViewById(R.id.Total32);

        producto33 = (TextView)findViewById(R.id.Producto33);
        precio33 = (TextView)findViewById(R.id.Precio33);
        porcentaje33 = (TextView)findViewById(R.id.Porcentaje33);
        cantidad33 = (TextView)findViewById(R.id.Cantidad33);
        total33 = (TextView)findViewById(R.id.Total33);

        producto34 = (TextView)findViewById(R.id.Producto34);
        precio34 = (TextView)findViewById(R.id.Precio34);
        porcentaje34 = (TextView)findViewById(R.id.Porcentaje34);
        cantidad34 = (TextView)findViewById(R.id.Cantidad34);
        total34 = (TextView)findViewById(R.id.Total34);

        producto35 = (TextView)findViewById(R.id.Producto35);
        precio35 = (TextView)findViewById(R.id.Precio35);
        porcentaje35 = (TextView)findViewById(R.id.Porcentaje35);
        cantidad35 = (TextView)findViewById(R.id.Cantidad35);
        total35 = (TextView)findViewById(R.id.Total35);

        producto36 = (TextView)findViewById(R.id.Producto36);
        precio36 = (TextView)findViewById(R.id.Precio36);
        porcentaje36 = (TextView)findViewById(R.id.Porcentaje36);
        cantidad36 = (TextView)findViewById(R.id.Cantidad36);
        total36 = (TextView)findViewById(R.id.Total36);

        producto37 = (TextView)findViewById(R.id.Producto37);
        precio37 = (TextView)findViewById(R.id.Precio37);
        porcentaje37 = (TextView)findViewById(R.id.Porcentaje37);
        cantidad37 = (TextView)findViewById(R.id.Cantidad37);
        total37 = (TextView)findViewById(R.id.Total37);

        producto38 = (TextView)findViewById(R.id.Producto38);
        precio38 = (TextView)findViewById(R.id.Precio38);
        porcentaje38 = (TextView)findViewById(R.id.Porcentaje38);
        cantidad38 = (TextView)findViewById(R.id.Cantidad38);
        total38 = (TextView)findViewById(R.id.Total38);

        producto39 = (TextView)findViewById(R.id.Producto39);
        precio39 = (TextView)findViewById(R.id.Precio39);
        porcentaje39 = (TextView)findViewById(R.id.Porcentaje39);
        cantidad39 = (TextView)findViewById(R.id.Cantidad39);
        total39 = (TextView)findViewById(R.id.Total39);

        producto40 = (TextView)findViewById(R.id.Producto40);
        precio40 = (TextView)findViewById(R.id.Precio40);
        porcentaje40 = (TextView)findViewById(R.id.Porcentaje40);
        cantidad40 = (TextView)findViewById(R.id.Cantidad40);
        total40 = (TextView)findViewById(R.id.Total40);

        producto41 = (TextView)findViewById(R.id.Producto41);
        precio41 = (TextView)findViewById(R.id.Precio41);
        porcentaje41 = (TextView)findViewById(R.id.Porcentaje41);
        cantidad41 = (TextView)findViewById(R.id.Cantidad41);
        total41 = (TextView)findViewById(R.id.Total41);

        producto42 = (TextView)findViewById(R.id.Producto42);
        precio42 = (TextView)findViewById(R.id.Precio42);
        porcentaje42 = (TextView)findViewById(R.id.Porcentaje42);
        cantidad42 = (TextView)findViewById(R.id.Cantidad42);
        total42 = (TextView)findViewById(R.id.Total42);

        producto43 = (TextView)findViewById(R.id.Producto43);
        precio43 = (TextView)findViewById(R.id.Precio43);
        porcentaje43 = (TextView)findViewById(R.id.Porcentaje43);
        cantidad43 = (TextView)findViewById(R.id.Cantidad43);
        total43 = (TextView)findViewById(R.id.Total43);

        producto44 = (TextView)findViewById(R.id.Producto44);
        precio44 = (TextView)findViewById(R.id.Precio44);
        porcentaje44 = (TextView)findViewById(R.id.Porcentaje44);
        cantidad44 = (TextView)findViewById(R.id.Cantidad44);
        total44 = (TextView)findViewById(R.id.Total44);

        producto45 = (TextView)findViewById(R.id.Producto45);
        precio45 = (TextView)findViewById(R.id.Precio45);
        porcentaje45 = (TextView)findViewById(R.id.Porcentaje45);
        cantidad45 = (TextView)findViewById(R.id.Cantidad45);
        total45 = (TextView)findViewById(R.id.Total45);

        sumaTotal = (TextView)findViewById(R.id.TxtSumaTotal);
        descuentoTotal = (TextView)findViewById(R.id.TxtDescuentoTotal);
        totalTotal = (TextView)findViewById(R.id.TxtTotalTotal);

        Intent intent=getIntent();
        Bundle extras = intent.getExtras();

        //Datos del encabezado
        final String clienteDato = (String)extras.get("cliente");
        final String familiaDato = (String)extras.get("familia");
        final String subtipoDato = (String)extras.get("subtipo");
        final String tipoventaDato = (String)extras.get("tipoventa");
        final String conductoDato = (String)extras.get("conducto");
        final String comentariosDato = (String)extras.get("comentarios");

        //Datos de las partidas
        final String Producto1 = (String)extras.get("producto1");
        final String Precio1 = (String)extras.get("precio1");
        final String Descuento1 = (String)extras.get("descuento1");
        final String Cantidad1 = (String)extras.get("cantidad1");
        final String Total1 = (String)extras.get("total1");

        final String Producto2 = (String)extras.get("producto2");
        final String Precio2 = (String)extras.get("precio2");
        final String Descuento2 = (String)extras.get("descuento2");
        final String Cantidad2 = (String)extras.get("cantidad2");
        final String Total2 = (String)extras.get("total2");

        final String Producto3 = (String)extras.get("producto3");
        final String Precio3 = (String)extras.get("precio3");
        final String Descuento3 = (String)extras.get("descuento3");
        final String Cantidad3 = (String)extras.get("cantidad3");
        final String Total3 = (String)extras.get("total3");

        final String Producto4 = (String)extras.get("producto4");
        final String Precio4 = (String)extras.get("precio4");
        final String Descuento4 = (String)extras.get("descuento4");
        final String Cantidad4 = (String)extras.get("cantidad4");
        final String Total4 = (String)extras.get("total4");

        final String Producto5 = (String)extras.get("producto5");
        final String Precio5 = (String)extras.get("precio5");
        final String Descuento5 = (String)extras.get("descuento5");
        final String Cantidad5 = (String)extras.get("cantidad5");
        final String Total5 = (String)extras.get("total5");

        final String Producto6 = (String)extras.get("producto6");
        final String Precio6 = (String)extras.get("precio6");
        final String Descuento6 = (String)extras.get("descuento6");
        final String Cantidad6 = (String)extras.get("cantidad6");
        final String Total6 = (String)extras.get("total6");

        final String Producto7 = (String)extras.get("producto7");
        final String Precio7 = (String)extras.get("precio7");
        final String Descuento7 = (String)extras.get("descuento7");
        final String Cantidad7 = (String)extras.get("cantidad7");
        final String Total7 = (String)extras.get("total7");

        final String Producto8 = (String)extras.get("producto8");
        final String Precio8 = (String)extras.get("precio8");
        final String Descuento8 = (String)extras.get("descuento8");
        final String Cantidad8 = (String)extras.get("cantidad8");
        final String Total8 = (String)extras.get("total8");

        final String Producto9 = (String)extras.get("producto9");
        final String Precio9 = (String)extras.get("precio9");
        final String Descuento9 = (String)extras.get("descuento9");
        final String Cantidad9 = (String)extras.get("cantidad9");
        final String Total9 = (String)extras.get("total9");

        final String Producto10 = (String)extras.get("producto10");
        final String Precio10 = (String)extras.get("precio10");
        final String Descuento10 = (String)extras.get("descuento10");
        final String Cantidad10 = (String)extras.get("cantidad10");
        final String Total10 = (String)extras.get("total10");

        final String Producto11 = (String)extras.get("producto11");
        final String Precio11 = (String)extras.get("precio11");
        final String Descuento11 = (String)extras.get("descuento11");
        final String Cantidad11 = (String)extras.get("cantidad11");
        final String Total11 = (String)extras.get("total11");

        final String Producto12 = (String)extras.get("producto12");
        final String Precio12 = (String)extras.get("precio12");
        final String Descuento12 = (String)extras.get("descuento12");
        final String Cantidad12 = (String)extras.get("cantidad12");
        final String Total12 = (String)extras.get("total12");

        final String Producto13 = (String)extras.get("producto13");
        final String Precio13 = (String)extras.get("precio13");
        final String Descuento13 = (String)extras.get("descuento13");
        final String Cantidad13 = (String)extras.get("cantidad13");
        final String Total13 = (String)extras.get("total13");

        final String Producto14 = (String)extras.get("producto14");
        final String Precio14 = (String)extras.get("precio14");
        final String Descuento14 = (String)extras.get("descuento14");
        final String Cantidad14 = (String)extras.get("cantidad14");
        final String Total14 = (String)extras.get("total14");

        final String Producto15 = (String)extras.get("producto15");
        final String Precio15 = (String)extras.get("precio15");
        final String Descuento15 = (String)extras.get("descuento15");
        final String Cantidad15 = (String)extras.get("cantidad15");
        final String Total15 = (String)extras.get("total15");

        final String Producto16 = (String)extras.get("producto16");
        final String Precio16 = (String)extras.get("precio16");
        final String Descuento16 = (String)extras.get("descuento16");
        final String Cantidad16 = (String)extras.get("cantidad16");
        final String Total16 = (String)extras.get("total16");

        final String Producto17 = (String)extras.get("producto17");
        final String Precio17 = (String)extras.get("precio17");
        final String Descuento17 = (String)extras.get("descuento17");
        final String Cantidad17 = (String)extras.get("cantidad17");
        final String Total17 = (String)extras.get("total17");

        final String Producto18 = (String)extras.get("producto18");
        final String Precio18 = (String)extras.get("precio18");
        final String Descuento18 = (String)extras.get("descuento18");
        final String Cantidad18 = (String)extras.get("cantidad18");
        final String Total18 = (String)extras.get("total18");

        final String Producto19 = (String)extras.get("producto19");
        final String Precio19 = (String)extras.get("precio19");
        final String Descuento19 = (String)extras.get("descuento19");
        final String Cantidad19 = (String)extras.get("cantidad19");
        final String Total19 = (String)extras.get("total19");

        final String Producto20 = (String)extras.get("producto20");
        final String Precio20 = (String)extras.get("precio20");
        final String Descuento20 = (String)extras.get("descuento20");
        final String Cantidad20 = (String)extras.get("cantidad20");
        final String Total20 = (String)extras.get("total20");

        final String Producto21 = (String)extras.get("producto21");
        final String Precio21 = (String)extras.get("precio21");
        final String Descuento21 = (String)extras.get("descuento21");
        final String Cantidad21 = (String)extras.get("cantidad21");
        final String Total21 = (String)extras.get("total21");

        final String Producto22 = (String)extras.get("producto22");
        final String Precio22 = (String)extras.get("precio22");
        final String Descuento22 = (String)extras.get("descuento22");
        final String Cantidad22 = (String)extras.get("cantidad22");
        final String Total22 = (String)extras.get("total22");

        final String Producto23 = (String)extras.get("producto23");
        final String Precio23 = (String)extras.get("precio23");
        final String Descuento23 = (String)extras.get("descuento23");
        final String Cantidad23 = (String)extras.get("cantidad23");
        final String Total23 = (String)extras.get("total23");

        final String Producto24 = (String)extras.get("producto24");
        final String Precio24 = (String)extras.get("precio24");
        final String Descuento24 = (String)extras.get("descuento24");
        final String Cantidad24 = (String)extras.get("cantidad24");
        final String Total24 = (String)extras.get("total24");

        final String Producto25 = (String)extras.get("producto25");
        final String Precio25 = (String)extras.get("precio25");
        final String Descuento25 = (String)extras.get("descuento25");
        final String Cantidad25 = (String)extras.get("cantidad25");
        final String Total25 = (String)extras.get("total25");

        final String Producto26 = (String)extras.get("producto26");
        final String Precio26 = (String)extras.get("precio26");
        final String Descuento26 = (String)extras.get("descuento26");
        final String Cantidad26 = (String)extras.get("cantidad26");
        final String Total26 = (String)extras.get("total26");

        final String Producto27 = (String)extras.get("producto27");
        final String Precio27 = (String)extras.get("precio27");
        final String Descuento27 = (String)extras.get("descuento27");
        final String Cantidad27 = (String)extras.get("cantidad27");
        final String Total27 = (String)extras.get("total27");

        final String Producto28 = (String)extras.get("producto28");
        final String Precio28 = (String)extras.get("precio28");
        final String Descuento28 = (String)extras.get("descuento28");
        final String Cantidad28 = (String)extras.get("cantidad28");
        final String Total28 = (String)extras.get("total28");

        final String Producto29 = (String)extras.get("producto29");
        final String Precio29 = (String)extras.get("precio29");
        final String Descuento29 = (String)extras.get("descuento29");
        final String Cantidad29 = (String)extras.get("cantidad29");
        final String Total29 = (String)extras.get("total29");

        final String Producto30 = (String)extras.get("producto30");
        final String Precio30 = (String)extras.get("precio30");
        final String Descuento30 = (String)extras.get("descuento30");
        final String Cantidad30 = (String)extras.get("cantidad30");
        final String Total30 = (String)extras.get("total30");

        final String Producto31 = (String)extras.get("producto31");
        final String Precio31 = (String)extras.get("precio31");
        final String Descuento31 = (String)extras.get("descuento31");
        final String Cantidad31 = (String)extras.get("cantidad31");
        final String Total31 = (String)extras.get("total31");

        final String Producto32 = (String)extras.get("producto32");
        final String Precio32 = (String)extras.get("precio32");
        final String Descuento32 = (String)extras.get("descuento32");
        final String Cantidad32 = (String)extras.get("cantidad32");
        final String Total32 = (String)extras.get("total32");

        final String Producto33 = (String)extras.get("producto33");
        final String Precio33 = (String)extras.get("precio33");
        final String Descuento33 = (String)extras.get("descuento33");
        final String Cantidad33 = (String)extras.get("cantidad33");
        final String Total33 = (String)extras.get("total33");

        final String Producto34 = (String)extras.get("producto34");
        final String Precio34 = (String)extras.get("precio34");
        final String Descuento34 = (String)extras.get("descuento34");
        final String Cantidad34 = (String)extras.get("cantidad34");
        final String Total34 = (String)extras.get("total34");

        final String Producto35 = (String)extras.get("producto35");
        final String Precio35 = (String)extras.get("precio35");
        final String Descuento35 = (String)extras.get("descuento35");
        final String Cantidad35 = (String)extras.get("cantidad35");
        final String Total35 = (String)extras.get("total35");

        final String Producto36 = (String)extras.get("producto36");
        final String Precio36 = (String)extras.get("precio36");
        final String Descuento36 = (String)extras.get("descuento36");
        final String Cantidad36 = (String)extras.get("cantidad36");
        final String Total36 = (String)extras.get("total36");

        final String Producto37 = (String)extras.get("producto37");
        final String Precio37 = (String)extras.get("precio37");
        final String Descuento37 = (String)extras.get("descuento37");
        final String Cantidad37 = (String)extras.get("cantidad37");
        final String Total37 = (String)extras.get("total37");

        final String Producto38 = (String)extras.get("producto38");
        final String Precio38 = (String)extras.get("precio38");
        final String Descuento38 = (String)extras.get("descuento38");
        final String Cantidad38 = (String)extras.get("cantidad38");
        final String Total38 = (String)extras.get("total38");

        final String Producto39 = (String)extras.get("producto39");
        final String Precio39 = (String)extras.get("precio39");
        final String Descuento39 = (String)extras.get("descuento39");
        final String Cantidad39 = (String)extras.get("cantidad39");
        final String Total39 = (String)extras.get("total39");

        final String Producto40 = (String)extras.get("producto40");
        final String Precio40 = (String)extras.get("precio40");
        final String Descuento40 = (String)extras.get("descuento40");
        final String Cantidad40 = (String)extras.get("cantidad40");
        final String Total40 = (String)extras.get("total40");

        final String Producto41 = (String)extras.get("producto41");
        final String Precio41 = (String)extras.get("precio41");
        final String Descuento41 = (String)extras.get("descuento41");
        final String Cantidad41 = (String)extras.get("cantidad41");
        final String Total41 = (String)extras.get("total41");

        final String Producto42 = (String)extras.get("producto42");
        final String Precio42 = (String)extras.get("precio42");
        final String Descuento42 = (String)extras.get("descuento42");
        final String Cantidad42 = (String)extras.get("cantidad42");
        final String Total42 = (String)extras.get("total42");

        final String Producto43 = (String)extras.get("producto43");
        final String Precio43 = (String)extras.get("precio43");
        final String Descuento43 = (String)extras.get("descuento43");
        final String Cantidad43 = (String)extras.get("cantidad43");
        final String Total43 = (String)extras.get("total43");

        final String Producto44 = (String)extras.get("producto44");
        final String Precio44 = (String)extras.get("precio44");
        final String Descuento44 = (String)extras.get("descuento44");
        final String Cantidad44 = (String)extras.get("cantidad44");
        final String Total44 = (String)extras.get("total44");

        final String Producto45 = (String)extras.get("producto45");
        final String Precio45 = (String)extras.get("precio45");
        final String Descuento45 = (String)extras.get("descuento45");
        final String Cantidad45 = (String)extras.get("cantidad45");
        final String Total45 = (String)extras.get("total45");

        final String SumaTotal = (String)extras.get("sumaTotal");
        final String DescuentoTotal = (String)extras.get("descuentoTotal");
        final String TotalTotal = (String)extras.get("totalTotal");

        //Se colocan los datos en la parte del encabezado
        cliente.setText("" + clienteDato);
        familia.setText("" + familiaDato);
        subtipo.setText("" + subtipoDato);
        tipoventa.setText("" + tipoventaDato);
        conducto.setText("" + conductoDato);
        comentarios.setText("" + comentariosDato);

        sumaTotal.setText("" + SumaTotal);
        descuentoTotal.setText("" + DescuentoTotal);
        totalTotal.setText("" + TotalTotal);

        if(tipoventaDato.toString().equals("Extendido")){
            leyendaMano.setText("MANO");
            leyendaAlmacen.setText("ALMACEN");
        }

        //Se colocan los datos en la parte de las partidas
        if(Producto1.length() >= 1) {
            producto1.setText("" + Producto1);
            precio1.setText("" + Precio1);
            porcentaje1.setText("" + Descuento1);
            cantidad1.setText("" + Cantidad1);
            total1.setText("" + Total1);
        }

        if(Producto2.length() >= 1) {
            producto2.setText("" + Producto2);
            precio2.setText("" + Precio2);
            porcentaje2.setText("" + Descuento2);
            cantidad2.setText("" + Cantidad2);
            total2.setText("" + Total2);
        }

        if(Producto3.length() >= 1) {
            producto3.setText("" + Producto3);
            precio3.setText("" + Precio3);
            porcentaje3.setText("" + Descuento3);
            cantidad3.setText("" + Cantidad3);
            total3.setText("" + Total3);
        }

        if(Producto4.length() >= 1) {
            producto4.setText("" + Producto4);
            precio4.setText("" + Precio4);
            porcentaje4.setText("" + Descuento4);
            cantidad4.setText("" + Cantidad4);
            total4.setText("" + Total4);
        }

        if(Producto5.length() >= 1) {
            producto5.setText("" + Producto5);
            precio5.setText("" + Precio5);
            porcentaje5.setText("" + Descuento5);
            cantidad5.setText("" + Cantidad5);
            total5.setText("" + Total5);
        }

        if(Producto6.length() >= 1) {
            producto6.setText("" + Producto6);
            precio6.setText("" + Precio6);
            porcentaje6.setText("" + Descuento6);
            cantidad6.setText("" + Cantidad6);
            total6.setText("" + Total6);
        }

        if(Producto7.length() >= 1) {
            producto7.setText("" + Producto7);
            precio7.setText("" + Precio7);
            porcentaje7.setText("" + Descuento7);
            cantidad7.setText("" + Cantidad7);
            total7.setText("" + Total7);
        }

        if(Producto8.length() >= 1) {
            producto8.setText("" + Producto8);
            precio8.setText("" + Precio8);
            porcentaje8.setText("" + Descuento8);
            cantidad8.setText("" + Cantidad8);
            total8.setText("" + Total8);
        }

        if(Producto9.length() >= 1) {
            producto9.setText("" + Producto9);
            precio9.setText("" + Precio9);
            porcentaje9.setText("" + Descuento9);
            cantidad9.setText("" + Cantidad9);
            total9.setText("" + Total9);
        }

        if(Producto10.length() >= 1) {
            producto10.setText("" + Producto10);
            precio10.setText("" + Precio10);
            porcentaje10.setText("" + Descuento10);
            cantidad10.setText("" + Cantidad10);
            total10.setText("" + Total10);
        }

        if(Producto11.length() >= 1) {
            producto11.setText("" + Producto11);
            precio11.setText("" + Precio11);
            porcentaje11.setText("" + Descuento11);
            cantidad11.setText("" + Cantidad11);
            total11.setText("" + Total11);
        }

        if(Producto12.length() >= 1) {
            producto12.setText("" + Producto12);
            precio12.setText("" + Precio12);
            porcentaje12.setText("" + Descuento12);
            cantidad12.setText("" + Cantidad12);
            total12.setText("" + Total12);
        }

        if(Producto13.length() >= 1) {
            producto13.setText("" + Producto13);
            precio13.setText("" + Precio13);
            porcentaje13.setText("" + Descuento13);
            cantidad13.setText("" + Cantidad13);
            total13.setText("" + Total13);
        }

        if(Producto14.length() >= 1) {
            producto14.setText("" + Producto14);
            precio14.setText("" + Precio14);
            porcentaje14.setText("" + Descuento14);
            cantidad14.setText("" + Cantidad14);
            total14.setText("" + Total14);
        }

        if(Producto15.length() >= 1) {
            producto15.setText("" + Producto15);
            precio15.setText("" + Precio15);
            porcentaje15.setText("" + Descuento15);
            cantidad15.setText("" + Cantidad15);
            total15.setText("" + Total15);
        }

        if(Producto16.length() >= 1) {
            producto16.setText("" + Producto16);
            precio16.setText("" + Precio16);
            porcentaje16.setText("" + Descuento16);
            cantidad16.setText("" + Cantidad16);
            total16.setText("" + Total16);
        }

        if(Producto17.length() >= 1) {
            producto17.setText("" + Producto17);
            precio17.setText("" + Precio17);
            porcentaje17.setText("" + Descuento17);
            cantidad17.setText("" + Cantidad17);
            total17.setText("" + Total17);
        }

        if(Producto18.length() >= 1) {
            producto18.setText("" + Producto18);
            precio18.setText("" + Precio18);
            porcentaje18.setText("" + Descuento18);
            cantidad18.setText("" + Cantidad18);
            total18.setText("" + Total18);
        }

        if(Producto19.length() >= 1) {
            producto19.setText("" + Producto19);
            precio19.setText("" + Precio19);
            porcentaje19.setText("" + Descuento19);
            cantidad19.setText("" + Cantidad19);
            total19.setText("" + Total19);
        }

        if(Producto20.length() >= 1) {
            producto20.setText("" + Producto20);
            precio20.setText("" + Precio20);
            porcentaje20.setText("" + Descuento20);
            cantidad20.setText("" + Cantidad20);
            total20.setText("" + Total20);
        }

        if(Producto21.length() >= 1) {
            producto21.setText("" + Producto21);
            precio21.setText("" + Precio21);
            porcentaje21.setText("" + Descuento21);
            cantidad21.setText("" + Cantidad21);
            total21.setText("" + Total21);
        }

        if(Producto22.length() >= 1) {
            producto22.setText("" + Producto22);
            precio22.setText("" + Precio22);
            porcentaje22.setText("" + Descuento22);
            cantidad22.setText("" + Cantidad22);
            total22.setText("" + Total22);
        }

        if(Producto23.length() >= 1) {
            producto23.setText("" + Producto23);
            precio23.setText("" + Precio23);
            porcentaje23.setText("" + Descuento23);
            cantidad23.setText("" + Cantidad23);
            total23.setText("" + Total23);
        }

        if(Producto24.length() >= 1) {
            producto24.setText("" + Producto24);
            precio24.setText("" + Precio24);
            porcentaje24.setText("" + Descuento24);
            cantidad24.setText("" + Cantidad24);
            total24.setText("" + Total24);
        }

        if(Producto25.length() >= 1) {
            producto25.setText("" + Producto25);
            precio25.setText("" + Precio25);
            porcentaje25.setText("" + Descuento25);
            cantidad25.setText("" + Cantidad25);
            total25.setText("" + Total25);
        }

        if(Producto26.length() >= 1) {
            producto26.setText("" + Producto26);
            precio26.setText("" + Precio26);
            porcentaje26.setText("" + Descuento26);
            cantidad26.setText("" + Cantidad26);
            total26.setText("" + Total26);
        }

        if(Producto27.length() >= 1) {
            producto27.setText("" + Producto27);
            precio27.setText("" + Precio27);
            porcentaje27.setText("" + Descuento27);
            cantidad27.setText("" + Cantidad27);
            total27.setText("" + Total27);
        }

        if(Producto28.length() >= 1) {
            producto28.setText("" + Producto28);
            precio28.setText("" + Precio28);
            porcentaje28.setText("" + Descuento28);
            cantidad28.setText("" + Cantidad28);
            total28.setText("" + Total28);
        }

        if(Producto29.length() >= 1) {
            producto29.setText("" + Producto29);
            precio29.setText("" + Precio29);
            porcentaje29.setText("" + Descuento29);
            cantidad29.setText("" + Cantidad29);
            total29.setText("" + Total29);
        }

        if(Producto30.length() >= 1) {
            producto30.setText("" + Producto30);
            precio30.setText("" + Precio30);
            porcentaje30.setText("" + Descuento30);
            cantidad30.setText("" + Cantidad30);
            total30.setText("" + Total30);
        }

        if(Producto31.length() >= 1) {
            producto31.setText("" + Producto31);
            precio31.setText("" + Precio31);
            porcentaje31.setText("" + Descuento31);
            cantidad31.setText("" + Cantidad31);
            total31.setText("" + Total31);
        }

        if(Producto32.length() >= 1) {
            producto32.setText("" + Producto32);
            precio32.setText("" + Precio32);
            porcentaje32.setText("" + Descuento32);
            cantidad32.setText("" + Cantidad32);
            total32.setText("" + Total32);
        }

        if(Producto33.length() >= 1) {
            producto33.setText("" + Producto33);
            precio33.setText("" + Precio33);
            porcentaje33.setText("" + Descuento33);
            cantidad33.setText("" + Cantidad33);
            total33.setText("" + Total33);
        }

        if(Producto34.length() >= 1) {
            producto34.setText("" + Producto34);
            precio34.setText("" + Precio34);
            porcentaje34.setText("" + Descuento34);
            cantidad34.setText("" + Cantidad34);
            total34.setText("" + Total34);
        }

        if(Producto35.length() >= 1) {
            producto35.setText("" + Producto35);
            precio35.setText("" + Precio35);
            porcentaje35.setText("" + Descuento35);
            cantidad35.setText("" + Cantidad35);
            total35.setText("" + Total35);
        }

        if(Producto36.length() >= 1) {
            producto36.setText("" + Producto36);
            precio36.setText("" + Precio36);
            porcentaje36.setText("" + Descuento36);
            cantidad36.setText("" + Cantidad36);
            total36.setText("" + Total36);
        }

        if(Producto37.length() >= 1) {
            producto37.setText("" + Producto37);
            precio37.setText("" + Precio37);
            porcentaje37.setText("" + Descuento37);
            cantidad37.setText("" + Cantidad37);
            total37.setText("" + Total37);
        }

        if(Producto38.length() >= 1) {
            producto38.setText("" + Producto38);
            precio38.setText("" + Precio38);
            porcentaje38.setText("" + Descuento38);
            cantidad38.setText("" + Cantidad38);
            total38.setText("" + Total38);
        }

        if(Producto39.length() >= 1) {
            producto39.setText("" + Producto39);
            precio39.setText("" + Precio39);
            porcentaje39.setText("" + Descuento39);
            cantidad39.setText("" + Cantidad39);
            total39.setText("" + Total39);
        }

        if(Producto40.length() >= 1) {
            producto40.setText("" + Producto40);
            precio40.setText("" + Precio40);
            porcentaje40.setText("" + Descuento40);
            cantidad40.setText("" + Cantidad40);
            total40.setText("" + Total40);
        }

        if(Producto41.length() >= 1) {
            producto41.setText("" + Producto41);
            precio41.setText("" + Precio41);
            porcentaje41.setText("" + Descuento41);
            cantidad41.setText("" + Cantidad41);
            total41.setText("" + Total41);
        }

        if(Producto42.length() >= 1) {
            producto42.setText("" + Producto42);
            precio42.setText("" + Precio42);
            porcentaje42.setText("" + Descuento42);
            cantidad42.setText("" + Cantidad42);
            total42.setText("" + Total42);
        }

        if(Producto43.length() >= 1) {
            producto43.setText("" + Producto43);
            precio43.setText("" + Precio43);
            porcentaje43.setText("" + Descuento43);
            cantidad43.setText("" + Cantidad43);
            total43.setText("" + Total43);
        }

        if(Producto44.length() >= 1) {
            producto44.setText("" + Producto44);
            precio44.setText("" + Precio44);
            porcentaje44.setText("" + Descuento44);
            cantidad44.setText("" + Cantidad44);
            total44.setText("" + Total44);
        }

        if(Producto45.length() >= 1) {
            producto45.setText("" + Producto45);
            precio45.setText("" + Precio45);
            porcentaje45.setText("" + Descuento45);
            cantidad45.setText("" + Cantidad45);
            total45.setText("" + Total45);
        }

        Button btn_saved = (Button)findViewById(R.id.btnSaved);
        ///Inicia metodo para guardar el pedido
        btn_saved.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                Toast toast = Toast.makeText(getApplicationContext(), "Se inicializo el guardado de pedidos Local", Toast.LENGTH_SHORT);
                toast.show();


                ///Metodo para sacar el siguiente numero de pedido en la tabla local
                String siguiente_pedido = dbHandler.siguiente();


                ///Se declaran las variables para el encabezado
                String cliente_cve = clienteDato;
                String cve_cliente = "";
                String lstViewSubtipo = subtipoDato;
                Integer subtipo = 2;

                String comentarios = comentariosDato;
                String lstViewFamilia = familiaDato;
                String letraFamilia = "";

                ///Se verifica el subtipo del pedido
                if(lstViewSubtipo.toString().equals("Normal")){
                    subtipo = 2;
                }
                if(lstViewSubtipo.toString().equals("Muestras Gratis")){
                    subtipo = 4;
                }
                if(lstViewSubtipo.toString().equals("Paq. Granos")){
                    subtipo = 8;
                }

                ///Se extrae la clave del cliente
                if(cliente_cve.length() >= 1){
                    String[] separated = cliente_cve.split("-");
                    cve_cliente = separated[0];
                }
                if(cliente_cve.length() == 0){
                    toast = Toast.makeText(getApplicationContext(), "Debe seleccionar el cliente", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }

                ///Se verifica los comentarios
                if(comentarios.length() == 0){
                    toast = Toast.makeText(getApplicationContext(), "Debe escribir algunos comentarios", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }

                ///Se verifica la familia
                if(lstViewFamilia.toString().equals("Agricola")){
                    letraFamilia = "A";
                }
                if(lstViewFamilia.toString().equals("Veterinaria")){
                    letraFamilia = "V";
                }
                if(lstViewFamilia.toString().equals("Seleccione una familia...")){
                    toast = Toast.makeText(getApplicationContext(), "Debe seleccionar la familia", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }

                double opSumaT = Double.valueOf(sumaTotal.getText().toString());
                double opDescuentoT = Double.valueOf(descuentoTotal.getText().toString());
                double opTotalT = Double.valueOf(totalTotal.getText().toString());
                usuario = dbHandler.ultimoUsuarioRegistrado();
                String cve_agente = "";
                String tipoUsuario = dbHandler.tipoUsuario(usuario);
                if(tipoUsuario.toString().equals("A")) {
                    cve_agente = usuario;
                }
                else{
                    cve_agente = dbHandler.agenteSeleccionado();
                }

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
                    toast = Toast.makeText(getApplicationContext(), "Active el GPS de la tableta para poder guardar.", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                */

                Calendar cal = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String fechaRegistro = sdf.format(cal.getTime());

                ///Se realiza la insercion para pasar a un metodo
                String sqlInsert = "insert into vn_pedidos_encabezado (cve_compania, num_pedido, tipo_pedido, estatus, fecha_pedido, cve_moneda, " +
                        "cve_cliente, cve_agente, cve_usuario_captura, suma, descuento, subtotal, impuesto, total, comentarios, familia, impreso, latitude, longitude) VALUES ('019', '"+ siguiente_pedido +"', '"+ subtipo +"', 'G', '"+ fechaRegistro +"', 'PES', '" + cve_cliente + "'" +
                        ", '"+ cve_agente +"', '"+ usuario +"', '"+ opSumaT +"', '"+ opDescuentoT +"', '"+ opTotalT +"', '0', '"+ opTotalT +"', '"+ comentarios +"', '"+ letraFamilia +"', '0', '"+ latitude +"', '"+ longitude +"')";


                dbHandler.insertaEncabezado(sqlInsert);
                ///Fin se realiza la insercion para pasar a un metodo

                ///Se declaran las variables para las partidas
                String tipo_venta=tipoventaDato;
                String oplstViewConducto = conductoDato;
                String cve_conducto;
                String[] separatedCond = oplstViewConducto.split("-");
                cve_conducto = separatedCond[0];
                Integer partida = 0;

                if(total1.length() > 0){
                    partida++;

                    ///Se extrae la cve_cat_producto del campo del producto
                    String cve_cat_producto;
                    String opProducto1 = String.valueOf(producto1.getText().toString());
                    String[] separated = opProducto1.split("-");
                    cve_cat_producto = separated[0];
                    double multiplicacion = 0;
                    double opCant1 = Double.valueOf(cantidad1.getText().toString());
                    double total = Double.valueOf(total1.getText().toString());
                    double opPrecio1 = Double.valueOf(precio1.getText().toString());
                    double porcentajeDesc = (porcentaje1.getText().toString().equals(""))?0:Double.valueOf(porcentaje1.getText().toString());
                    double descuento = 0;
                    multiplicacion = opCant1*opPrecio1;
                    descuento = multiplicacion - total;

                    String tipo_venta_guardar = tipo_venta;
                    if(tipo_venta.toString().equals("Extendido")){
                        tipo_venta_guardar = "Mano";
                    }

                    String insertaPartida = "INSERT INTO vn_pedidos_partidas (cve_compania, num_pedido, num_partida, cve_cat_producto, cantidad, precio_unitario, porcentaje_descuento, " +
                            "suma, descuento, subtotal, impuesto, total, cve_centro_costo, cve_conducto, tipo_conducto, estatus) VALUES ('019', '"+ siguiente_pedido +"', '"+ partida +"', " +
                            "'"+ cve_cat_producto +"', '"+ opCant1 +"', '"+ opPrecio1 +"', '"+ porcentajeDesc +"', '"+ multiplicacion +"', '"+ descuento +"', '"+ total +"', '0', '"+ total +"', " +
                            "'"+ tipo_venta_guardar +"', '"+ cve_conducto +"', 'D', 'A')";

                    dbHandler.insertaPartidas(insertaPartida);

                }

                if(total2.length() > 0){
                    partida++;

                    ///Se extrae la cve_cat_producto del campo del producto
                    String cve_cat_producto;
                    String opProducto1 = String.valueOf(producto2.getText().toString());
                    String[] separated = opProducto1.split("-");
                    cve_cat_producto = separated[0];
                    double multiplicacion = 0;
                    double opCant1 = Double.valueOf(cantidad2.getText().toString());
                    double total = Double.valueOf(total2.getText().toString());
                    double opPrecio1 = Double.valueOf(precio2.getText().toString());
                    double porcentajeDesc = Double.valueOf(porcentaje2.getText().toString());
                    double descuento = 0;
                    multiplicacion = opCant1*opPrecio1;
                    descuento = multiplicacion - total;

                    String tipo_venta_guardar = tipo_venta;
                    if(tipo_venta.toString().equals("Extendido")){
                        tipo_venta_guardar = "Mano";
                    }

                    String insertaPartida = "INSERT INTO vn_pedidos_partidas (cve_compania, num_pedido, num_partida, cve_cat_producto, cantidad, precio_unitario, porcentaje_descuento, " +
                            "suma, descuento, subtotal, impuesto, total, cve_centro_costo, cve_conducto, tipo_conducto, estatus) VALUES ('019', '"+ siguiente_pedido +"', '"+ partida +"', " +
                            "'"+ cve_cat_producto +"', '"+ opCant1 +"', '"+ opPrecio1 +"', '"+ porcentajeDesc +"', '"+ multiplicacion +"', '"+ descuento +"', '"+ total +"', '0', '"+ total +"', " +
                            "'"+ tipo_venta_guardar +"', '"+ cve_conducto +"', 'D', 'A')";

                    dbHandler.insertaPartidas(insertaPartida);

                }

                if(total3.length() > 0){
                    partida++;

                    ///Se extrae la cve_cat_producto del campo del producto
                    String cve_cat_producto;
                    String opProducto1 = String.valueOf(producto3.getText().toString());
                    String[] separated = opProducto1.split("-");
                    cve_cat_producto = separated[0];
                    double multiplicacion = 0;
                    double opCant1 = Double.valueOf(cantidad3.getText().toString());
                    double total = Double.valueOf(total3.getText().toString());
                    double opPrecio1 = Double.valueOf(precio3.getText().toString());
                    double porcentajeDesc = Double.valueOf(porcentaje3.getText().toString());
                    double descuento = 0;
                    multiplicacion = opCant1*opPrecio1;
                    descuento = multiplicacion - total;

                    String tipo_venta_guardar = tipo_venta;
                    if(tipo_venta.toString().equals("Extendido")){
                        tipo_venta_guardar = "Mano";
                    }

                    String insertaPartida = "INSERT INTO vn_pedidos_partidas (cve_compania, num_pedido, num_partida, cve_cat_producto, cantidad, precio_unitario, porcentaje_descuento, " +
                            "suma, descuento, subtotal, impuesto, total, cve_centro_costo, cve_conducto, tipo_conducto, estatus) VALUES ('019', '"+ siguiente_pedido +"', '"+ partida +"', " +
                            "'"+ cve_cat_producto +"', '"+ opCant1 +"', '"+ opPrecio1 +"', '"+ porcentajeDesc +"', '"+ multiplicacion +"', '"+ descuento +"', '"+ total +"', '0', '"+ total +"', " +
                            "'"+ tipo_venta_guardar +"', '"+ cve_conducto +"', 'D', 'A')";

                    dbHandler.insertaPartidas(insertaPartida);

                }

                if(total4.length() > 0){
                    partida++;

                    ///Se extrae la cve_cat_producto del campo del producto
                    String cve_cat_producto;
                    String opProducto1 = String.valueOf(producto4.getText().toString());
                    String[] separated = opProducto1.split("-");
                    cve_cat_producto = separated[0];
                    double multiplicacion = 0;
                    double opCant1 = Double.valueOf(cantidad4.getText().toString());
                    double total = Double.valueOf(total4.getText().toString());
                    double opPrecio1 = Double.valueOf(precio4.getText().toString());
                    double porcentajeDesc = Double.valueOf(porcentaje4.getText().toString());
                    double descuento = 0;
                    multiplicacion = opCant1*opPrecio1;
                    descuento = multiplicacion - total;

                    String tipo_venta_guardar = tipo_venta;
                    if(tipo_venta.toString().equals("Extendido")){
                        tipo_venta_guardar = "Mano";
                    }

                    String insertaPartida = "INSERT INTO vn_pedidos_partidas (cve_compania, num_pedido, num_partida, cve_cat_producto, cantidad, precio_unitario, porcentaje_descuento, " +
                            "suma, descuento, subtotal, impuesto, total, cve_centro_costo, cve_conducto, tipo_conducto, estatus) VALUES ('019', '"+ siguiente_pedido +"', '"+ partida +"', " +
                            "'"+ cve_cat_producto +"', '"+ opCant1 +"', '"+ opPrecio1 +"', '"+ porcentajeDesc +"', '"+ multiplicacion +"', '"+ descuento +"', '"+ total +"', '0', '"+ total +"', " +
                            "'"+ tipo_venta_guardar +"', '"+ cve_conducto +"', 'D', 'A')";

                    dbHandler.insertaPartidas(insertaPartida);

                }

                if(total5.length() > 0){
                    partida++;

                    ///Se extrae la cve_cat_producto del campo del producto
                    String cve_cat_producto;
                    String opProducto1 = String.valueOf(producto5.getText().toString());
                    String[] separated = opProducto1.split("-");
                    cve_cat_producto = separated[0];
                    double multiplicacion = 0;
                    double opCant1 = Double.valueOf(cantidad5.getText().toString());
                    double total = Double.valueOf(total5.getText().toString());
                    double opPrecio1 = Double.valueOf(precio5.getText().toString());
                    double porcentajeDesc = Double.valueOf(porcentaje5.getText().toString());
                    double descuento = 0;
                    multiplicacion = opCant1*opPrecio1;
                    descuento = multiplicacion - total;

                    String tipo_venta_guardar = tipo_venta;
                    if(tipo_venta.toString().equals("Extendido")){
                        tipo_venta_guardar = "Mano";
                    }

                    String insertaPartida = "INSERT INTO vn_pedidos_partidas (cve_compania, num_pedido, num_partida, cve_cat_producto, cantidad, precio_unitario, porcentaje_descuento, " +
                            "suma, descuento, subtotal, impuesto, total, cve_centro_costo, cve_conducto, tipo_conducto, estatus) VALUES ('019', '"+ siguiente_pedido +"', '"+ partida +"', " +
                            "'"+ cve_cat_producto +"', '"+ opCant1 +"', '"+ opPrecio1 +"', '"+ porcentajeDesc +"', '"+ multiplicacion +"', '"+ descuento +"', '"+ total +"', '0', '"+ total +"', " +
                            "'"+ tipo_venta_guardar +"', '"+ cve_conducto +"', 'D', 'A')";

                    dbHandler.insertaPartidas(insertaPartida);

                }

                if(total6.length() > 0){
                    partida++;

                    ///Se extrae la cve_cat_producto del campo del producto
                    String cve_cat_producto;
                    String opProducto1 = String.valueOf(producto6.getText().toString());
                    String[] separated = opProducto1.split("-");
                    cve_cat_producto = separated[0];
                    double multiplicacion = 0;
                    double opCant1 = Double.valueOf(cantidad6.getText().toString());
                    double total = Double.valueOf(total6.getText().toString());
                    double opPrecio1 = Double.valueOf(precio6.getText().toString());
                    double porcentajeDesc = Double.valueOf(porcentaje6.getText().toString());
                    double descuento = 0;
                    multiplicacion = opCant1*opPrecio1;
                    descuento = multiplicacion - total;

                    String tipo_venta_guardar = tipo_venta;
                    if(tipo_venta.toString().equals("Extendido")){
                        tipo_venta_guardar = "Mano";
                    }

                    String insertaPartida = "INSERT INTO vn_pedidos_partidas (cve_compania, num_pedido, num_partida, cve_cat_producto, cantidad, precio_unitario, porcentaje_descuento, " +
                            "suma, descuento, subtotal, impuesto, total, cve_centro_costo, cve_conducto, tipo_conducto, estatus) VALUES ('019', '"+ siguiente_pedido +"', '"+ partida +"', " +
                            "'"+ cve_cat_producto +"', '"+ opCant1 +"', '"+ opPrecio1 +"', '"+ porcentajeDesc +"', '"+ multiplicacion +"', '"+ descuento +"', '"+ total +"', '0', '"+ total +"', " +
                            "'"+ tipo_venta_guardar +"', '"+ cve_conducto +"', 'D', 'A')";

                    dbHandler.insertaPartidas(insertaPartida);

                }

                if(total7.length() > 0){
                    partida++;

                    ///Se extrae la cve_cat_producto del campo del producto
                    String cve_cat_producto;
                    String opProducto1 = String.valueOf(producto7.getText().toString());
                    String[] separated = opProducto1.split("-");
                    cve_cat_producto = separated[0];
                    double multiplicacion = 0;
                    double opCant1 = Double.valueOf(cantidad7.getText().toString());
                    double total = Double.valueOf(total7.getText().toString());
                    double opPrecio1 = Double.valueOf(precio7.getText().toString());
                    double porcentajeDesc = Double.valueOf(porcentaje7.getText().toString());
                    double descuento = 0;
                    multiplicacion = opCant1*opPrecio1;
                    descuento = multiplicacion - total;

                    String tipo_venta_guardar = tipo_venta;
                    if(tipo_venta.toString().equals("Extendido")){
                        tipo_venta_guardar = "Mano";
                    }

                    String insertaPartida = "INSERT INTO vn_pedidos_partidas (cve_compania, num_pedido, num_partida, cve_cat_producto, cantidad, precio_unitario, porcentaje_descuento, " +
                            "suma, descuento, subtotal, impuesto, total, cve_centro_costo, cve_conducto, tipo_conducto, estatus) VALUES ('019', '"+ siguiente_pedido +"', '"+ partida +"', " +
                            "'"+ cve_cat_producto +"', '"+ opCant1 +"', '"+ opPrecio1 +"', '"+ porcentajeDesc +"', '"+ multiplicacion +"', '"+ descuento +"', '"+ total +"', '0', '"+ total +"', " +
                            "'"+ tipo_venta_guardar +"', '"+ cve_conducto +"', 'D', 'A')";

                    dbHandler.insertaPartidas(insertaPartida);

                }

                if(total8.length() > 0){
                    partida++;

                    ///Se extrae la cve_cat_producto del campo del producto
                    String cve_cat_producto;
                    String opProducto1 = String.valueOf(producto8.getText().toString());
                    String[] separated = opProducto1.split("-");
                    cve_cat_producto = separated[0];
                    double multiplicacion = 0;
                    double opCant1 = Double.valueOf(cantidad8.getText().toString());
                    double total = Double.valueOf(total8.getText().toString());
                    double opPrecio1 = Double.valueOf(precio8.getText().toString());
                    double porcentajeDesc = Double.valueOf(porcentaje8.getText().toString());
                    double descuento = 0;
                    multiplicacion = opCant1*opPrecio1;
                    descuento = multiplicacion - total;

                    String tipo_venta_guardar = tipo_venta;
                    if(tipo_venta.toString().equals("Extendido")){
                        tipo_venta_guardar = "Mano";
                    }

                    String insertaPartida = "INSERT INTO vn_pedidos_partidas (cve_compania, num_pedido, num_partida, cve_cat_producto, cantidad, precio_unitario, porcentaje_descuento, " +
                            "suma, descuento, subtotal, impuesto, total, cve_centro_costo, cve_conducto, tipo_conducto, estatus) VALUES ('019', '"+ siguiente_pedido +"', '"+ partida +"', " +
                            "'"+ cve_cat_producto +"', '"+ opCant1 +"', '"+ opPrecio1 +"', '"+ porcentajeDesc +"', '"+ multiplicacion +"', '"+ descuento +"', '"+ total +"', '0', '"+ total +"', " +
                            "'"+ tipo_venta_guardar +"', '"+ cve_conducto +"', 'D', 'A')";

                    dbHandler.insertaPartidas(insertaPartida);

                }

                if(total9.length() > 0){
                    partida++;

                    ///Se extrae la cve_cat_producto del campo del producto
                    String cve_cat_producto;
                    String opProducto1 = String.valueOf(producto9.getText().toString());
                    String[] separated = opProducto1.split("-");
                    cve_cat_producto = separated[0];
                    double multiplicacion = 0;
                    double opCant1 = Double.valueOf(cantidad9.getText().toString());
                    double total = Double.valueOf(total9.getText().toString());
                    double opPrecio1 = Double.valueOf(precio9.getText().toString());
                    double porcentajeDesc = Double.valueOf(porcentaje9.getText().toString());
                    double descuento = 0;
                    multiplicacion = opCant1*opPrecio1;
                    descuento = multiplicacion - total;

                    String tipo_venta_guardar = tipo_venta;
                    if(tipo_venta.toString().equals("Extendido")){
                        tipo_venta_guardar = "Mano";
                    }

                    String insertaPartida = "INSERT INTO vn_pedidos_partidas (cve_compania, num_pedido, num_partida, cve_cat_producto, cantidad, precio_unitario, porcentaje_descuento, " +
                            "suma, descuento, subtotal, impuesto, total, cve_centro_costo, cve_conducto, tipo_conducto, estatus) VALUES ('019', '"+ siguiente_pedido +"', '"+ partida +"', " +
                            "'"+ cve_cat_producto +"', '"+ opCant1 +"', '"+ opPrecio1 +"', '"+ porcentajeDesc +"', '"+ multiplicacion +"', '"+ descuento +"', '"+ total +"', '0', '"+ total +"', " +
                            "'"+ tipo_venta_guardar +"', '"+ cve_conducto +"', 'D', 'A')";

                    dbHandler.insertaPartidas(insertaPartida);

                }

                if(total10.length() > 0){
                    partida++;

                    ///Se extrae la cve_cat_producto del campo del producto
                    String cve_cat_producto;
                    String opProducto1 = String.valueOf(producto10.getText().toString());
                    String[] separated = opProducto1.split("-");
                    cve_cat_producto = separated[0];
                    double multiplicacion = 0;
                    double opCant1 = Double.valueOf(cantidad10.getText().toString());
                    double total = Double.valueOf(total10.getText().toString());
                    double opPrecio1 = Double.valueOf(precio10.getText().toString());
                    double porcentajeDesc = Double.valueOf(porcentaje10.getText().toString());
                    double descuento = 0;
                    multiplicacion = opCant1*opPrecio1;
                    descuento = multiplicacion - total;

                    String tipo_venta_guardar = tipo_venta;
                    if(tipo_venta.toString().equals("Extendido")){
                        tipo_venta_guardar = "Mano";
                    }

                    String insertaPartida = "INSERT INTO vn_pedidos_partidas (cve_compania, num_pedido, num_partida, cve_cat_producto, cantidad, precio_unitario, porcentaje_descuento, " +
                            "suma, descuento, subtotal, impuesto, total, cve_centro_costo, cve_conducto, tipo_conducto, estatus) VALUES ('019', '"+ siguiente_pedido +"', '"+ partida +"', " +
                            "'"+ cve_cat_producto +"', '"+ opCant1 +"', '"+ opPrecio1 +"', '"+ porcentajeDesc +"', '"+ multiplicacion +"', '"+ descuento +"', '"+ total +"', '0', '"+ total +"', " +
                            "'"+ tipo_venta_guardar +"', '"+ cve_conducto +"', 'D', 'A')";

                    dbHandler.insertaPartidas(insertaPartida);

                }

                if(total11.length() > 0){
                    partida++;

                    ///Se extrae la cve_cat_producto del campo del producto
                    String cve_cat_producto;
                    String opProducto1 = String.valueOf(producto11.getText().toString());
                    String[] separated = opProducto1.split("-");
                    cve_cat_producto = separated[0];
                    double multiplicacion = 0;
                    double opCant1 = Double.valueOf(cantidad11.getText().toString());
                    double total = Double.valueOf(total11.getText().toString());
                    double opPrecio1 = Double.valueOf(precio11.getText().toString());
                    double porcentajeDesc = Double.valueOf(porcentaje11.getText().toString());
                    double descuento = 0;
                    multiplicacion = opCant1*opPrecio1;
                    descuento = multiplicacion - total;

                    String tipo_venta_guardar = tipo_venta;
                    if(tipo_venta.toString().equals("Extendido")){
                        tipo_venta_guardar = "Mano";
                    }

                    String insertaPartida = "INSERT INTO vn_pedidos_partidas (cve_compania, num_pedido, num_partida, cve_cat_producto, cantidad, precio_unitario, porcentaje_descuento, " +
                            "suma, descuento, subtotal, impuesto, total, cve_centro_costo, cve_conducto, tipo_conducto, estatus) VALUES ('019', '"+ siguiente_pedido +"', '"+ partida +"', " +
                            "'"+ cve_cat_producto +"', '"+ opCant1 +"', '"+ opPrecio1 +"', '"+ porcentajeDesc +"', '"+ multiplicacion +"', '"+ descuento +"', '"+ total +"', '0', '"+ total +"', " +
                            "'"+ tipo_venta_guardar +"', '"+ cve_conducto +"', 'D', 'A')";

                    dbHandler.insertaPartidas(insertaPartida);

                }

                if(total12.length() > 0){
                    partida++;

                    ///Se extrae la cve_cat_producto del campo del producto
                    String cve_cat_producto;
                    String opProducto1 = String.valueOf(producto12.getText().toString());
                    String[] separated = opProducto1.split("-");
                    cve_cat_producto = separated[0];
                    double multiplicacion = 0;
                    double opCant1 = Double.valueOf(cantidad12.getText().toString());
                    double total = Double.valueOf(total12.getText().toString());
                    double opPrecio1 = Double.valueOf(precio12.getText().toString());
                    double porcentajeDesc = Double.valueOf(porcentaje12.getText().toString());
                    double descuento = 0;
                    multiplicacion = opCant1*opPrecio1;
                    descuento = multiplicacion - total;

                    String tipo_venta_guardar = tipo_venta;
                    if(tipo_venta.toString().equals("Extendido")){
                        tipo_venta_guardar = "Mano";
                    }

                    String insertaPartida = "INSERT INTO vn_pedidos_partidas (cve_compania, num_pedido, num_partida, cve_cat_producto, cantidad, precio_unitario, porcentaje_descuento, " +
                            "suma, descuento, subtotal, impuesto, total, cve_centro_costo, cve_conducto, tipo_conducto, estatus) VALUES ('019', '"+ siguiente_pedido +"', '"+ partida +"', " +
                            "'"+ cve_cat_producto +"', '"+ opCant1 +"', '"+ opPrecio1 +"', '"+ porcentajeDesc +"', '"+ multiplicacion +"', '"+ descuento +"', '"+ total +"', '0', '"+ total +"', " +
                            "'"+ tipo_venta_guardar +"', '"+ cve_conducto +"', 'D', 'A')";

                    dbHandler.insertaPartidas(insertaPartida);

                }

                if(total13.length() > 0){
                    partida++;

                    ///Se extrae la cve_cat_producto del campo del producto
                    String cve_cat_producto;
                    String opProducto1 = String.valueOf(producto13.getText().toString());
                    String[] separated = opProducto1.split("-");
                    cve_cat_producto = separated[0];
                    double multiplicacion = 0;
                    double opCant1 = Double.valueOf(cantidad13.getText().toString());
                    double total = Double.valueOf(total13.getText().toString());
                    double opPrecio1 = Double.valueOf(precio13.getText().toString());
                    double porcentajeDesc = Double.valueOf(porcentaje13.getText().toString());
                    double descuento = 0;
                    multiplicacion = opCant1*opPrecio1;
                    descuento = multiplicacion - total;

                    String tipo_venta_guardar = tipo_venta;
                    if(tipo_venta.toString().equals("Extendido")){
                        tipo_venta_guardar = "Mano";
                    }

                    String insertaPartida = "INSERT INTO vn_pedidos_partidas (cve_compania, num_pedido, num_partida, cve_cat_producto, cantidad, precio_unitario, porcentaje_descuento, " +
                            "suma, descuento, subtotal, impuesto, total, cve_centro_costo, cve_conducto, tipo_conducto, estatus) VALUES ('019', '"+ siguiente_pedido +"', '"+ partida +"', " +
                            "'"+ cve_cat_producto +"', '"+ opCant1 +"', '"+ opPrecio1 +"', '"+ porcentajeDesc +"', '"+ multiplicacion +"', '"+ descuento +"', '"+ total +"', '0', '"+ total +"', " +
                            "'"+ tipo_venta_guardar +"', '"+ cve_conducto +"', 'D', 'A')";

                    dbHandler.insertaPartidas(insertaPartida);

                }

                if(total14.length() > 0){
                    partida++;

                    ///Se extrae la cve_cat_producto del campo del producto
                    String cve_cat_producto;
                    String opProducto1 = String.valueOf(producto14.getText().toString());
                    String[] separated = opProducto1.split("-");
                    cve_cat_producto = separated[0];
                    double multiplicacion = 0;
                    double opCant1 = Double.valueOf(cantidad14.getText().toString());
                    double total = Double.valueOf(total14.getText().toString());
                    double opPrecio1 = Double.valueOf(precio14.getText().toString());
                    double porcentajeDesc = Double.valueOf(porcentaje14.getText().toString());
                    double descuento = 0;
                    multiplicacion = opCant1*opPrecio1;
                    descuento = multiplicacion - total;

                    String tipo_venta_guardar = tipo_venta;
                    if(tipo_venta.toString().equals("Extendido")){
                        tipo_venta_guardar = "Mano";
                    }

                    String insertaPartida = "INSERT INTO vn_pedidos_partidas (cve_compania, num_pedido, num_partida, cve_cat_producto, cantidad, precio_unitario, porcentaje_descuento, " +
                            "suma, descuento, subtotal, impuesto, total, cve_centro_costo, cve_conducto, tipo_conducto, estatus) VALUES ('019', '"+ siguiente_pedido +"', '"+ partida +"', " +
                            "'"+ cve_cat_producto +"', '"+ opCant1 +"', '"+ opPrecio1 +"', '"+ porcentajeDesc +"', '"+ multiplicacion +"', '"+ descuento +"', '"+ total +"', '0', '"+ total +"', " +
                            "'"+ tipo_venta_guardar +"', '"+ cve_conducto +"', 'D', 'A')";

                    dbHandler.insertaPartidas(insertaPartida);

                }

                if(total15.length() > 0){
                    partida++;

                    ///Se extrae la cve_cat_producto del campo del producto
                    String cve_cat_producto;
                    String opProducto1 = String.valueOf(producto15.getText().toString());
                    String[] separated = opProducto1.split("-");
                    cve_cat_producto = separated[0];
                    double multiplicacion = 0;
                    double opCant1 = Double.valueOf(cantidad15.getText().toString());
                    double total = Double.valueOf(total15.getText().toString());
                    double opPrecio1 = Double.valueOf(precio15.getText().toString());
                    double porcentajeDesc = Double.valueOf(porcentaje15.getText().toString());
                    double descuento = 0;
                    multiplicacion = opCant1*opPrecio1;
                    descuento = multiplicacion - total;

                    String tipo_venta_guardar = tipo_venta;
                    if(tipo_venta.toString().equals("Extendido")){
                        tipo_venta_guardar = "Mano";
                    }

                    String insertaPartida = "INSERT INTO vn_pedidos_partidas (cve_compania, num_pedido, num_partida, cve_cat_producto, cantidad, precio_unitario, porcentaje_descuento, " +
                            "suma, descuento, subtotal, impuesto, total, cve_centro_costo, cve_conducto, tipo_conducto, estatus) VALUES ('019', '"+ siguiente_pedido +"', '"+ partida +"', " +
                            "'"+ cve_cat_producto +"', '"+ opCant1 +"', '"+ opPrecio1 +"', '"+ porcentajeDesc +"', '"+ multiplicacion +"', '"+ descuento +"', '"+ total +"', '0', '"+ total +"', " +
                            "'"+ tipo_venta_guardar +"', '"+ cve_conducto +"', 'D', 'A')";

                    dbHandler.insertaPartidas(insertaPartida);

                }

                if(total16.length() > 0){
                    partida++;

                    ///Se extrae la cve_cat_producto del campo del producto
                    String cve_cat_producto;
                    String opProducto1 = String.valueOf(producto16.getText().toString());
                    String[] separated = opProducto1.split("-");
                    cve_cat_producto = separated[0];
                    double multiplicacion = 0;
                    double opCant1 = Double.valueOf(cantidad16.getText().toString());
                    double total = Double.valueOf(total16.getText().toString());
                    double opPrecio1 = Double.valueOf(precio16.getText().toString());
                    double porcentajeDesc = Double.valueOf(porcentaje16.getText().toString());
                    double descuento = 0;
                    multiplicacion = opCant1*opPrecio1;
                    descuento = multiplicacion - total;

                    String tipo_venta_guardar = tipo_venta;
                    if(tipo_venta.toString().equals("Extendido")){
                        tipo_venta_guardar = "Mano";
                    }

                    String insertaPartida = "INSERT INTO vn_pedidos_partidas (cve_compania, num_pedido, num_partida, cve_cat_producto, cantidad, precio_unitario, porcentaje_descuento, " +
                            "suma, descuento, subtotal, impuesto, total, cve_centro_costo, cve_conducto, tipo_conducto, estatus) VALUES ('019', '"+ siguiente_pedido +"', '"+ partida +"', " +
                            "'"+ cve_cat_producto +"', '"+ opCant1 +"', '"+ opPrecio1 +"', '"+ porcentajeDesc +"', '"+ multiplicacion +"', '"+ descuento +"', '"+ total +"', '0', '"+ total +"', " +
                            "'"+ tipo_venta_guardar +"', '"+ cve_conducto +"', 'D', 'A')";

                    dbHandler.insertaPartidas(insertaPartida);

                }

                if(total17.length() > 0){
                    partida++;

                    ///Se extrae la cve_cat_producto del campo del producto
                    String cve_cat_producto;
                    String opProducto1 = String.valueOf(producto17.getText().toString());
                    String[] separated = opProducto1.split("-");
                    cve_cat_producto = separated[0];
                    double multiplicacion = 0;
                    double opCant1 = Double.valueOf(cantidad17.getText().toString());
                    double total = Double.valueOf(total17.getText().toString());
                    double opPrecio1 = Double.valueOf(precio17.getText().toString());
                    double porcentajeDesc = Double.valueOf(porcentaje17.getText().toString());
                    double descuento = 0;
                    multiplicacion = opCant1*opPrecio1;
                    descuento = multiplicacion - total;

                    String tipo_venta_guardar = tipo_venta;
                    if(tipo_venta.toString().equals("Extendido")){
                        tipo_venta_guardar = "Mano";
                    }

                    String insertaPartida = "INSERT INTO vn_pedidos_partidas (cve_compania, num_pedido, num_partida, cve_cat_producto, cantidad, precio_unitario, porcentaje_descuento, " +
                            "suma, descuento, subtotal, impuesto, total, cve_centro_costo, cve_conducto, tipo_conducto, estatus) VALUES ('019', '"+ siguiente_pedido +"', '"+ partida +"', " +
                            "'"+ cve_cat_producto +"', '"+ opCant1 +"', '"+ opPrecio1 +"', '"+ porcentajeDesc +"', '"+ multiplicacion +"', '"+ descuento +"', '"+ total +"', '0', '"+ total +"', " +
                            "'"+ tipo_venta_guardar +"', '"+ cve_conducto +"', 'D', 'A')";

                    dbHandler.insertaPartidas(insertaPartida);

                }

                if(total18.length() > 0){
                    partida++;

                    ///Se extrae la cve_cat_producto del campo del producto
                    String cve_cat_producto;
                    String opProducto1 = String.valueOf(producto18.getText().toString());
                    String[] separated = opProducto1.split("-");
                    cve_cat_producto = separated[0];
                    double multiplicacion = 0;
                    double opCant1 = Double.valueOf(cantidad18.getText().toString());
                    double total = Double.valueOf(total18.getText().toString());
                    double opPrecio1 = Double.valueOf(precio18.getText().toString());
                    double porcentajeDesc = Double.valueOf(porcentaje18.getText().toString());
                    double descuento = 0;
                    multiplicacion = opCant1*opPrecio1;
                    descuento = multiplicacion - total;

                    String tipo_venta_guardar = tipo_venta;
                    if(tipo_venta.toString().equals("Extendido")){
                        tipo_venta_guardar = "Mano";
                    }

                    String insertaPartida = "INSERT INTO vn_pedidos_partidas (cve_compania, num_pedido, num_partida, cve_cat_producto, cantidad, precio_unitario, porcentaje_descuento, " +
                            "suma, descuento, subtotal, impuesto, total, cve_centro_costo, cve_conducto, tipo_conducto, estatus) VALUES ('019', '"+ siguiente_pedido +"', '"+ partida +"', " +
                            "'"+ cve_cat_producto +"', '"+ opCant1 +"', '"+ opPrecio1 +"', '"+ porcentajeDesc +"', '"+ multiplicacion +"', '"+ descuento +"', '"+ total +"', '0', '"+ total +"', " +
                            "'"+ tipo_venta_guardar +"', '"+ cve_conducto +"', 'D', 'A')";

                    dbHandler.insertaPartidas(insertaPartida);

                }

                if(total19.length() > 0){
                    partida++;

                    ///Se extrae la cve_cat_producto del campo del producto
                    String cve_cat_producto;
                    String opProducto1 = String.valueOf(producto19.getText().toString());
                    String[] separated = opProducto1.split("-");
                    cve_cat_producto = separated[0];
                    double multiplicacion = 0;
                    double opCant1 = Double.valueOf(cantidad19.getText().toString());
                    double total = Double.valueOf(total19.getText().toString());
                    double opPrecio1 = Double.valueOf(precio19.getText().toString());
                    double porcentajeDesc = Double.valueOf(porcentaje19.getText().toString());
                    double descuento = 0;
                    multiplicacion = opCant1*opPrecio1;
                    descuento = multiplicacion - total;

                    String tipo_venta_guardar = tipo_venta;
                    if(tipo_venta.toString().equals("Extendido")){
                        tipo_venta_guardar = "Mano";
                    }

                    String insertaPartida = "INSERT INTO vn_pedidos_partidas (cve_compania, num_pedido, num_partida, cve_cat_producto, cantidad, precio_unitario, porcentaje_descuento, " +
                            "suma, descuento, subtotal, impuesto, total, cve_centro_costo, cve_conducto, tipo_conducto, estatus) VALUES ('019', '"+ siguiente_pedido +"', '"+ partida +"', " +
                            "'"+ cve_cat_producto +"', '"+ opCant1 +"', '"+ opPrecio1 +"', '"+ porcentajeDesc +"', '"+ multiplicacion +"', '"+ descuento +"', '"+ total +"', '0', '"+ total +"', " +
                            "'"+ tipo_venta_guardar +"', '"+ cve_conducto +"', 'D', 'A')";

                    dbHandler.insertaPartidas(insertaPartida);

                }

                if(total20.length() > 0){
                    partida++;

                    ///Se extrae la cve_cat_producto del campo del producto
                    String cve_cat_producto;
                    String opProducto1 = String.valueOf(producto20.getText().toString());
                    String[] separated = opProducto1.split("-");
                    cve_cat_producto = separated[0];
                    double multiplicacion = 0;
                    double opCant1 = Double.valueOf(cantidad20.getText().toString());
                    double total = Double.valueOf(total20.getText().toString());
                    double opPrecio1 = Double.valueOf(precio20.getText().toString());
                    double porcentajeDesc = Double.valueOf(porcentaje20.getText().toString());
                    double descuento = 0;
                    multiplicacion = opCant1*opPrecio1;
                    descuento = multiplicacion - total;

                    String tipo_venta_guardar = tipo_venta;
                    if(tipo_venta.toString().equals("Extendido")){
                        tipo_venta_guardar = "Mano";
                    }

                    String insertaPartida = "INSERT INTO vn_pedidos_partidas (cve_compania, num_pedido, num_partida, cve_cat_producto, cantidad, precio_unitario, porcentaje_descuento, " +
                            "suma, descuento, subtotal, impuesto, total, cve_centro_costo, cve_conducto, tipo_conducto, estatus) VALUES ('019', '"+ siguiente_pedido +"', '"+ partida +"', " +
                            "'"+ cve_cat_producto +"', '"+ opCant1 +"', '"+ opPrecio1 +"', '"+ porcentajeDesc +"', '"+ multiplicacion +"', '"+ descuento +"', '"+ total +"', '0', '"+ total +"', " +
                            "'"+ tipo_venta_guardar +"', '"+ cve_conducto +"', 'D', 'A')";

                    dbHandler.insertaPartidas(insertaPartida);

                }

                if(total21.length() > 0){
                    partida++;

                    ///Se extrae la cve_cat_producto del campo del producto
                    String cve_cat_producto;
                    String opProducto1 = String.valueOf(producto21.getText().toString());
                    String[] separated = opProducto1.split("-");
                    cve_cat_producto = separated[0];
                    double multiplicacion = 0;
                    double opCant1 = Double.valueOf(cantidad21.getText().toString());
                    double total = Double.valueOf(total21.getText().toString());
                    double opPrecio1 = Double.valueOf(precio21.getText().toString());
                    double porcentajeDesc = Double.valueOf(porcentaje21.getText().toString());
                    double descuento = 0;
                    multiplicacion = opCant1*opPrecio1;
                    descuento = multiplicacion - total;

                    String tipo_venta_guardar = tipo_venta;
                    if(tipo_venta.toString().equals("Extendido")){
                        tipo_venta_guardar = "Mano";
                    }

                    String insertaPartida = "INSERT INTO vn_pedidos_partidas (cve_compania, num_pedido, num_partida, cve_cat_producto, cantidad, precio_unitario, porcentaje_descuento, " +
                            "suma, descuento, subtotal, impuesto, total, cve_centro_costo, cve_conducto, tipo_conducto, estatus) VALUES ('019', '"+ siguiente_pedido +"', '"+ partida +"', " +
                            "'"+ cve_cat_producto +"', '"+ opCant1 +"', '"+ opPrecio1 +"', '"+ porcentajeDesc +"', '"+ multiplicacion +"', '"+ descuento +"', '"+ total +"', '0', '"+ total +"', " +
                            "'"+ tipo_venta_guardar +"', '"+ cve_conducto +"', 'D', 'A')";

                    dbHandler.insertaPartidas(insertaPartida);

                }

                if(total22.length() > 0){
                    partida++;

                    ///Se extrae la cve_cat_producto del campo del producto
                    String cve_cat_producto;
                    String opProducto1 = String.valueOf(producto22.getText().toString());
                    String[] separated = opProducto1.split("-");
                    cve_cat_producto = separated[0];
                    double multiplicacion = 0;
                    double opCant1 = Double.valueOf(cantidad22.getText().toString());
                    double total = Double.valueOf(total22.getText().toString());
                    double opPrecio1 = Double.valueOf(precio22.getText().toString());
                    double porcentajeDesc = Double.valueOf(porcentaje22.getText().toString());
                    double descuento = 0;
                    multiplicacion = opCant1*opPrecio1;
                    descuento = multiplicacion - total;

                    String tipo_venta_guardar = tipo_venta;
                    if(tipo_venta.toString().equals("Extendido")){
                        tipo_venta_guardar = "Mano";
                    }

                    String insertaPartida = "INSERT INTO vn_pedidos_partidas (cve_compania, num_pedido, num_partida, cve_cat_producto, cantidad, precio_unitario, porcentaje_descuento, " +
                            "suma, descuento, subtotal, impuesto, total, cve_centro_costo, cve_conducto, tipo_conducto, estatus) VALUES ('019', '"+ siguiente_pedido +"', '"+ partida +"', " +
                            "'"+ cve_cat_producto +"', '"+ opCant1 +"', '"+ opPrecio1 +"', '"+ porcentajeDesc +"', '"+ multiplicacion +"', '"+ descuento +"', '"+ total +"', '0', '"+ total +"', " +
                            "'"+ tipo_venta_guardar +"', '"+ cve_conducto +"', 'D', 'A')";

                    dbHandler.insertaPartidas(insertaPartida);

                }

                if(total23.length() > 0){
                    partida++;

                    ///Se extrae la cve_cat_producto del campo del producto
                    String cve_cat_producto;
                    String opProducto1 = String.valueOf(producto23.getText().toString());
                    String[] separated = opProducto1.split("-");
                    cve_cat_producto = separated[0];
                    double multiplicacion = 0;
                    double opCant1 = Double.valueOf(cantidad23.getText().toString());
                    double total = Double.valueOf(total23.getText().toString());
                    double opPrecio1 = Double.valueOf(precio23.getText().toString());
                    double porcentajeDesc = Double.valueOf(porcentaje23.getText().toString());
                    double descuento = 0;
                    multiplicacion = opCant1*opPrecio1;
                    descuento = multiplicacion - total;

                    String tipo_venta_guardar = tipo_venta;
                    if(tipo_venta.toString().equals("Extendido")){
                        tipo_venta_guardar = "Almacen";
                    }

                    String insertaPartida = "INSERT INTO vn_pedidos_partidas (cve_compania, num_pedido, num_partida, cve_cat_producto, cantidad, precio_unitario, porcentaje_descuento, " +
                            "suma, descuento, subtotal, impuesto, total, cve_centro_costo, cve_conducto, tipo_conducto, estatus) VALUES ('019', '"+ siguiente_pedido +"', '"+ partida +"', " +
                            "'"+ cve_cat_producto +"', '"+ opCant1 +"', '"+ opPrecio1 +"', '"+ porcentajeDesc +"', '"+ multiplicacion +"', '"+ descuento +"', '"+ total +"', '0', '"+ total +"', " +
                            "'"+ tipo_venta_guardar +"', '"+ cve_conducto +"', 'D', 'A')";

                    dbHandler.insertaPartidas(insertaPartida);

                }

                if(total24.length() > 0){
                    partida++;

                    ///Se extrae la cve_cat_producto del campo del producto
                    String cve_cat_producto;
                    String opProducto1 = String.valueOf(producto24.getText().toString());
                    String[] separated = opProducto1.split("-");
                    cve_cat_producto = separated[0];
                    double multiplicacion = 0;
                    double opCant1 = Double.valueOf(cantidad24.getText().toString());
                    double total = Double.valueOf(total24.getText().toString());
                    double opPrecio1 = Double.valueOf(precio24.getText().toString());
                    double porcentajeDesc = Double.valueOf(porcentaje24.getText().toString());
                    double descuento = 0;
                    multiplicacion = opCant1*opPrecio1;
                    descuento = multiplicacion - total;

                    String tipo_venta_guardar = tipo_venta;
                    if(tipo_venta.toString().equals("Extendido")){
                        tipo_venta_guardar = "Almacen";
                    }

                    String insertaPartida = "INSERT INTO vn_pedidos_partidas (cve_compania, num_pedido, num_partida, cve_cat_producto, cantidad, precio_unitario, porcentaje_descuento, " +
                            "suma, descuento, subtotal, impuesto, total, cve_centro_costo, cve_conducto, tipo_conducto, estatus) VALUES ('019', '"+ siguiente_pedido +"', '"+ partida +"', " +
                            "'"+ cve_cat_producto +"', '"+ opCant1 +"', '"+ opPrecio1 +"', '"+ porcentajeDesc +"', '"+ multiplicacion +"', '"+ descuento +"', '"+ total +"', '0', '"+ total +"', " +
                            "'"+ tipo_venta_guardar +"', '"+ cve_conducto +"', 'D', 'A')";

                    dbHandler.insertaPartidas(insertaPartida);

                }

                if(total25.length() > 0){
                    partida++;

                    ///Se extrae la cve_cat_producto del campo del producto
                    String cve_cat_producto;
                    String opProducto1 = String.valueOf(producto25.getText().toString());
                    String[] separated = opProducto1.split("-");
                    cve_cat_producto = separated[0];
                    double multiplicacion = 0;
                    double opCant1 = Double.valueOf(cantidad25.getText().toString());
                    double total = Double.valueOf(total25.getText().toString());
                    double opPrecio1 = Double.valueOf(precio25.getText().toString());
                    double porcentajeDesc = Double.valueOf(porcentaje25.getText().toString());
                    double descuento = 0;
                    multiplicacion = opCant1*opPrecio1;
                    descuento = multiplicacion - total;

                    String tipo_venta_guardar = tipo_venta;
                    if(tipo_venta.toString().equals("Extendido")){
                        tipo_venta_guardar = "Almacen";
                    }

                    String insertaPartida = "INSERT INTO vn_pedidos_partidas (cve_compania, num_pedido, num_partida, cve_cat_producto, cantidad, precio_unitario, porcentaje_descuento, " +
                            "suma, descuento, subtotal, impuesto, total, cve_centro_costo, cve_conducto, tipo_conducto, estatus) VALUES ('019', '"+ siguiente_pedido +"', '"+ partida +"', " +
                            "'"+ cve_cat_producto +"', '"+ opCant1 +"', '"+ opPrecio1 +"', '"+ porcentajeDesc +"', '"+ multiplicacion +"', '"+ descuento +"', '"+ total +"', '0', '"+ total +"', " +
                            "'"+ tipo_venta_guardar +"', '"+ cve_conducto +"', 'D', 'A')";

                    dbHandler.insertaPartidas(insertaPartida);

                }

                if(total26.length() > 0){
                    partida++;

                    ///Se extrae la cve_cat_producto del campo del producto
                    String cve_cat_producto;
                    String opProducto1 = String.valueOf(producto26.getText().toString());
                    String[] separated = opProducto1.split("-");
                    cve_cat_producto = separated[0];
                    double multiplicacion = 0;
                    double opCant1 = Double.valueOf(cantidad26.getText().toString());
                    double total = Double.valueOf(total26.getText().toString());
                    double opPrecio1 = Double.valueOf(precio26.getText().toString());
                    double porcentajeDesc = Double.valueOf(porcentaje26.getText().toString());
                    double descuento = 0;
                    multiplicacion = opCant1*opPrecio1;
                    descuento = multiplicacion - total;

                    String tipo_venta_guardar = tipo_venta;
                    if(tipo_venta.toString().equals("Extendido")){
                        tipo_venta_guardar = "Almacen";
                    }

                    String insertaPartida = "INSERT INTO vn_pedidos_partidas (cve_compania, num_pedido, num_partida, cve_cat_producto, cantidad, precio_unitario, porcentaje_descuento, " +
                            "suma, descuento, subtotal, impuesto, total, cve_centro_costo, cve_conducto, tipo_conducto, estatus) VALUES ('019', '"+ siguiente_pedido +"', '"+ partida +"', " +
                            "'"+ cve_cat_producto +"', '"+ opCant1 +"', '"+ opPrecio1 +"', '"+ porcentajeDesc +"', '"+ multiplicacion +"', '"+ descuento +"', '"+ total +"', '0', '"+ total +"', " +
                            "'"+ tipo_venta_guardar +"', '"+ cve_conducto +"', 'D', 'A')";

                    dbHandler.insertaPartidas(insertaPartida);

                }

                if(total27.length() > 0){
                    partida++;

                    ///Se extrae la cve_cat_producto del campo del producto
                    String cve_cat_producto;
                    String opProducto1 = String.valueOf(producto27.getText().toString());
                    String[] separated = opProducto1.split("-");
                    cve_cat_producto = separated[0];
                    double multiplicacion = 0;
                    double opCant1 = Double.valueOf(cantidad27.getText().toString());
                    double total = Double.valueOf(total27.getText().toString());
                    double opPrecio1 = Double.valueOf(precio27.getText().toString());
                    double porcentajeDesc = Double.valueOf(porcentaje27.getText().toString());
                    double descuento = 0;
                    multiplicacion = opCant1*opPrecio1;
                    descuento = multiplicacion - total;

                    String tipo_venta_guardar = tipo_venta;
                    if(tipo_venta.toString().equals("Extendido")){
                        tipo_venta_guardar = "Almacen";
                    }

                    String insertaPartida = "INSERT INTO vn_pedidos_partidas (cve_compania, num_pedido, num_partida, cve_cat_producto, cantidad, precio_unitario, porcentaje_descuento, " +
                            "suma, descuento, subtotal, impuesto, total, cve_centro_costo, cve_conducto, tipo_conducto, estatus) VALUES ('019', '"+ siguiente_pedido +"', '"+ partida +"', " +
                            "'"+ cve_cat_producto +"', '"+ opCant1 +"', '"+ opPrecio1 +"', '"+ porcentajeDesc +"', '"+ multiplicacion +"', '"+ descuento +"', '"+ total +"', '0', '"+ total +"', " +
                            "'"+ tipo_venta_guardar +"', '"+ cve_conducto +"', 'D', 'A')";

                    dbHandler.insertaPartidas(insertaPartida);

                }

                if(total28.length() > 0){
                    partida++;

                    ///Se extrae la cve_cat_producto del campo del producto
                    String cve_cat_producto;
                    String opProducto1 = String.valueOf(producto28.getText().toString());
                    String[] separated = opProducto1.split("-");
                    cve_cat_producto = separated[0];
                    double multiplicacion = 0;
                    double opCant1 = Double.valueOf(cantidad28.getText().toString());
                    double total = Double.valueOf(total28.getText().toString());
                    double opPrecio1 = Double.valueOf(precio28.getText().toString());
                    double porcentajeDesc = Double.valueOf(porcentaje28.getText().toString());
                    double descuento = 0;
                    multiplicacion = opCant1*opPrecio1;
                    descuento = multiplicacion - total;

                    String tipo_venta_guardar = tipo_venta;
                    if(tipo_venta.toString().equals("Extendido")){
                        tipo_venta_guardar = "Almacen";
                    }

                    String insertaPartida = "INSERT INTO vn_pedidos_partidas (cve_compania, num_pedido, num_partida, cve_cat_producto, cantidad, precio_unitario, porcentaje_descuento, " +
                            "suma, descuento, subtotal, impuesto, total, cve_centro_costo, cve_conducto, tipo_conducto, estatus) VALUES ('019', '"+ siguiente_pedido +"', '"+ partida +"', " +
                            "'"+ cve_cat_producto +"', '"+ opCant1 +"', '"+ opPrecio1 +"', '"+ porcentajeDesc +"', '"+ multiplicacion +"', '"+ descuento +"', '"+ total +"', '0', '"+ total +"', " +
                            "'"+ tipo_venta_guardar +"', '"+ cve_conducto +"', 'D', 'A')";

                    dbHandler.insertaPartidas(insertaPartida);

                }

                if(total29.length() > 0){
                    partida++;

                    ///Se extrae la cve_cat_producto del campo del producto
                    String cve_cat_producto;
                    String opProducto1 = String.valueOf(producto29.getText().toString());
                    String[] separated = opProducto1.split("-");
                    cve_cat_producto = separated[0];
                    double multiplicacion = 0;
                    double opCant1 = Double.valueOf(cantidad29.getText().toString());
                    double total = Double.valueOf(total29.getText().toString());
                    double opPrecio1 = Double.valueOf(precio29.getText().toString());
                    double porcentajeDesc = Double.valueOf(porcentaje29.getText().toString());
                    double descuento = 0;
                    multiplicacion = opCant1*opPrecio1;
                    descuento = multiplicacion - total;

                    String tipo_venta_guardar = tipo_venta;
                    if(tipo_venta.toString().equals("Extendido")){
                        tipo_venta_guardar = "Almacen";
                    }

                    String insertaPartida = "INSERT INTO vn_pedidos_partidas (cve_compania, num_pedido, num_partida, cve_cat_producto, cantidad, precio_unitario, porcentaje_descuento, " +
                            "suma, descuento, subtotal, impuesto, total, cve_centro_costo, cve_conducto, tipo_conducto, estatus) VALUES ('019', '"+ siguiente_pedido +"', '"+ partida +"', " +
                            "'"+ cve_cat_producto +"', '"+ opCant1 +"', '"+ opPrecio1 +"', '"+ porcentajeDesc +"', '"+ multiplicacion +"', '"+ descuento +"', '"+ total +"', '0', '"+ total +"', " +
                            "'"+ tipo_venta_guardar +"', '"+ cve_conducto +"', 'D', 'A')";

                    dbHandler.insertaPartidas(insertaPartida);

                }

                if(total30.length() > 0){
                    partida++;

                    ///Se extrae la cve_cat_producto del campo del producto
                    String cve_cat_producto;
                    String opProducto1 = String.valueOf(producto30.getText().toString());
                    String[] separated = opProducto1.split("-");
                    cve_cat_producto = separated[0];
                    double multiplicacion = 0;
                    double opCant1 = Double.valueOf(cantidad30.getText().toString());
                    double total = Double.valueOf(total30.getText().toString());
                    double opPrecio1 = Double.valueOf(precio30.getText().toString());
                    double porcentajeDesc = Double.valueOf(porcentaje30.getText().toString());
                    double descuento = 0;
                    multiplicacion = opCant1*opPrecio1;
                    descuento = multiplicacion - total;

                    String tipo_venta_guardar = tipo_venta;
                    if(tipo_venta.toString().equals("Extendido")){
                        tipo_venta_guardar = "Almacen";
                    }

                    String insertaPartida = "INSERT INTO vn_pedidos_partidas (cve_compania, num_pedido, num_partida, cve_cat_producto, cantidad, precio_unitario, porcentaje_descuento, " +
                            "suma, descuento, subtotal, impuesto, total, cve_centro_costo, cve_conducto, tipo_conducto, estatus) VALUES ('019', '"+ siguiente_pedido +"', '"+ partida +"', " +
                            "'"+ cve_cat_producto +"', '"+ opCant1 +"', '"+ opPrecio1 +"', '"+ porcentajeDesc +"', '"+ multiplicacion +"', '"+ descuento +"', '"+ total +"', '0', '"+ total +"', " +
                            "'"+ tipo_venta_guardar +"', '"+ cve_conducto +"', 'D', 'A')";

                    dbHandler.insertaPartidas(insertaPartida);

                }

                if(total31.length() > 0){
                    partida++;

                    ///Se extrae la cve_cat_producto del campo del producto
                    String cve_cat_producto;
                    String opProducto1 = String.valueOf(producto31.getText().toString());
                    String[] separated = opProducto1.split("-");
                    cve_cat_producto = separated[0];
                    double multiplicacion = 0;
                    double opCant1 = Double.valueOf(cantidad31.getText().toString());
                    double total = Double.valueOf(total31.getText().toString());
                    double opPrecio1 = Double.valueOf(precio31.getText().toString());
                    double porcentajeDesc = Double.valueOf(porcentaje31.getText().toString());
                    double descuento = 0;
                    multiplicacion = opCant1*opPrecio1;
                    descuento = multiplicacion - total;

                    String tipo_venta_guardar = tipo_venta;
                    if(tipo_venta.toString().equals("Extendido")){
                        tipo_venta_guardar = "Almacen";
                    }

                    String insertaPartida = "INSERT INTO vn_pedidos_partidas (cve_compania, num_pedido, num_partida, cve_cat_producto, cantidad, precio_unitario, porcentaje_descuento, " +
                            "suma, descuento, subtotal, impuesto, total, cve_centro_costo, cve_conducto, tipo_conducto, estatus) VALUES ('019', '"+ siguiente_pedido +"', '"+ partida +"', " +
                            "'"+ cve_cat_producto +"', '"+ opCant1 +"', '"+ opPrecio1 +"', '"+ porcentajeDesc +"', '"+ multiplicacion +"', '"+ descuento +"', '"+ total +"', '0', '"+ total +"', " +
                            "'"+ tipo_venta_guardar +"', '"+ cve_conducto +"', 'D', 'A')";

                    dbHandler.insertaPartidas(insertaPartida);

                }

                if(total32.length() > 0){
                    partida++;

                    ///Se extrae la cve_cat_producto del campo del producto
                    String cve_cat_producto;
                    String opProducto1 = String.valueOf(producto32.getText().toString());
                    String[] separated = opProducto1.split("-");
                    cve_cat_producto = separated[0];
                    double multiplicacion = 0;
                    double opCant1 = Double.valueOf(cantidad32.getText().toString());
                    double total = Double.valueOf(total32.getText().toString());
                    double opPrecio1 = Double.valueOf(precio32.getText().toString());
                    double porcentajeDesc = Double.valueOf(porcentaje32.getText().toString());
                    double descuento = 0;
                    multiplicacion = opCant1*opPrecio1;
                    descuento = multiplicacion - total;

                    String tipo_venta_guardar = tipo_venta;
                    if(tipo_venta.toString().equals("Extendido")){
                        tipo_venta_guardar = "Almacen";
                    }

                    String insertaPartida = "INSERT INTO vn_pedidos_partidas (cve_compania, num_pedido, num_partida, cve_cat_producto, cantidad, precio_unitario, porcentaje_descuento, " +
                            "suma, descuento, subtotal, impuesto, total, cve_centro_costo, cve_conducto, tipo_conducto, estatus) VALUES ('019', '"+ siguiente_pedido +"', '"+ partida +"', " +
                            "'"+ cve_cat_producto +"', '"+ opCant1 +"', '"+ opPrecio1 +"', '"+ porcentajeDesc +"', '"+ multiplicacion +"', '"+ descuento +"', '"+ total +"', '0', '"+ total +"', " +
                            "'"+ tipo_venta_guardar +"', '"+ cve_conducto +"', 'D', 'A')";

                    dbHandler.insertaPartidas(insertaPartida);

                }

                if(total33.length() > 0){
                    partida++;

                    ///Se extrae la cve_cat_producto del campo del producto
                    String cve_cat_producto;
                    String opProducto1 = String.valueOf(producto33.getText().toString());
                    String[] separated = opProducto1.split("-");
                    cve_cat_producto = separated[0];
                    double multiplicacion = 0;
                    double opCant1 = Double.valueOf(cantidad33.getText().toString());
                    double total = Double.valueOf(total33.getText().toString());
                    double opPrecio1 = Double.valueOf(precio33.getText().toString());
                    double porcentajeDesc = Double.valueOf(porcentaje33.getText().toString());
                    double descuento = 0;
                    multiplicacion = opCant1*opPrecio1;
                    descuento = multiplicacion - total;

                    String tipo_venta_guardar = tipo_venta;
                    if(tipo_venta.toString().equals("Extendido")){
                        tipo_venta_guardar = "Almacen";
                    }

                    String insertaPartida = "INSERT INTO vn_pedidos_partidas (cve_compania, num_pedido, num_partida, cve_cat_producto, cantidad, precio_unitario, porcentaje_descuento, " +
                            "suma, descuento, subtotal, impuesto, total, cve_centro_costo, cve_conducto, tipo_conducto, estatus) VALUES ('019', '"+ siguiente_pedido +"', '"+ partida +"', " +
                            "'"+ cve_cat_producto +"', '"+ opCant1 +"', '"+ opPrecio1 +"', '"+ porcentajeDesc +"', '"+ multiplicacion +"', '"+ descuento +"', '"+ total +"', '0', '"+ total +"', " +
                            "'"+ tipo_venta_guardar +"', '"+ cve_conducto +"', 'D', 'A')";

                    dbHandler.insertaPartidas(insertaPartida);

                }

                if(total34.length() > 0){
                    partida++;

                    ///Se extrae la cve_cat_producto del campo del producto
                    String cve_cat_producto;
                    String opProducto1 = String.valueOf(producto34.getText().toString());
                    String[] separated = opProducto1.split("-");
                    cve_cat_producto = separated[0];
                    double multiplicacion = 0;
                    double opCant1 = Double.valueOf(cantidad34.getText().toString());
                    double total = Double.valueOf(total34.getText().toString());
                    double opPrecio1 = Double.valueOf(precio34.getText().toString());
                    double porcentajeDesc = Double.valueOf(porcentaje34.getText().toString());
                    double descuento = 0;
                    multiplicacion = opCant1*opPrecio1;
                    descuento = multiplicacion - total;

                    String tipo_venta_guardar = tipo_venta;
                    if(tipo_venta.toString().equals("Extendido")){
                        tipo_venta_guardar = "Almacen";
                    }

                    String insertaPartida = "INSERT INTO vn_pedidos_partidas (cve_compania, num_pedido, num_partida, cve_cat_producto, cantidad, precio_unitario, porcentaje_descuento, " +
                            "suma, descuento, subtotal, impuesto, total, cve_centro_costo, cve_conducto, tipo_conducto, estatus) VALUES ('019', '"+ siguiente_pedido +"', '"+ partida +"', " +
                            "'"+ cve_cat_producto +"', '"+ opCant1 +"', '"+ opPrecio1 +"', '"+ porcentajeDesc +"', '"+ multiplicacion +"', '"+ descuento +"', '"+ total +"', '0', '"+ total +"', " +
                            "'"+ tipo_venta_guardar +"', '"+ cve_conducto +"', 'D', 'A')";

                    dbHandler.insertaPartidas(insertaPartida);

                }

                if(total35.length() > 0){
                    partida++;

                    ///Se extrae la cve_cat_producto del campo del producto
                    String cve_cat_producto;
                    String opProducto1 = String.valueOf(producto35.getText().toString());
                    String[] separated = opProducto1.split("-");
                    cve_cat_producto = separated[0];
                    double multiplicacion = 0;
                    double opCant1 = Double.valueOf(cantidad35.getText().toString());
                    double total = Double.valueOf(total35.getText().toString());
                    double opPrecio1 = Double.valueOf(precio35.getText().toString());
                    double porcentajeDesc = Double.valueOf(porcentaje35.getText().toString());
                    double descuento = 0;
                    multiplicacion = opCant1*opPrecio1;
                    descuento = multiplicacion - total;

                    String tipo_venta_guardar = tipo_venta;
                    if(tipo_venta.toString().equals("Extendido")){
                        tipo_venta_guardar = "Almacen";
                    }

                    String insertaPartida = "INSERT INTO vn_pedidos_partidas (cve_compania, num_pedido, num_partida, cve_cat_producto, cantidad, precio_unitario, porcentaje_descuento, " +
                            "suma, descuento, subtotal, impuesto, total, cve_centro_costo, cve_conducto, tipo_conducto, estatus) VALUES ('019', '"+ siguiente_pedido +"', '"+ partida +"', " +
                            "'"+ cve_cat_producto +"', '"+ opCant1 +"', '"+ opPrecio1 +"', '"+ porcentajeDesc +"', '"+ multiplicacion +"', '"+ descuento +"', '"+ total +"', '0', '"+ total +"', " +
                            "'"+ tipo_venta_guardar +"', '"+ cve_conducto +"', 'D', 'A')";

                    dbHandler.insertaPartidas(insertaPartida);

                }

                if(total36.length() > 0){
                    partida++;

                    ///Se extrae la cve_cat_producto del campo del producto
                    String cve_cat_producto;
                    String opProducto1 = String.valueOf(producto36.getText().toString());
                    String[] separated = opProducto1.split("-");
                    cve_cat_producto = separated[0];
                    double multiplicacion = 0;
                    double opCant1 = Double.valueOf(cantidad36.getText().toString());
                    double total = Double.valueOf(total36.getText().toString());
                    double opPrecio1 = Double.valueOf(precio36.getText().toString());
                    double porcentajeDesc = Double.valueOf(porcentaje36.getText().toString());
                    double descuento = 0;
                    multiplicacion = opCant1*opPrecio1;
                    descuento = multiplicacion - total;

                    String tipo_venta_guardar = tipo_venta;
                    if(tipo_venta.toString().equals("Extendido")){
                        tipo_venta_guardar = "Almacen";
                    }

                    String insertaPartida = "INSERT INTO vn_pedidos_partidas (cve_compania, num_pedido, num_partida, cve_cat_producto, cantidad, precio_unitario, porcentaje_descuento, " +
                            "suma, descuento, subtotal, impuesto, total, cve_centro_costo, cve_conducto, tipo_conducto, estatus) VALUES ('019', '"+ siguiente_pedido +"', '"+ partida +"', " +
                            "'"+ cve_cat_producto +"', '"+ opCant1 +"', '"+ opPrecio1 +"', '"+ porcentajeDesc +"', '"+ multiplicacion +"', '"+ descuento +"', '"+ total +"', '0', '"+ total +"', " +
                            "'"+ tipo_venta_guardar +"', '"+ cve_conducto +"', 'D', 'A')";

                    dbHandler.insertaPartidas(insertaPartida);

                }

                if(total37.length() > 0){
                    partida++;

                    ///Se extrae la cve_cat_producto del campo del producto
                    String cve_cat_producto;
                    String opProducto1 = String.valueOf(producto37.getText().toString());
                    String[] separated = opProducto1.split("-");
                    cve_cat_producto = separated[0];
                    double multiplicacion = 0;
                    double opCant1 = Double.valueOf(cantidad37.getText().toString());
                    double total = Double.valueOf(total37.getText().toString());
                    double opPrecio1 = Double.valueOf(precio37.getText().toString());
                    double porcentajeDesc = Double.valueOf(porcentaje37.getText().toString());
                    double descuento = 0;
                    multiplicacion = opCant1*opPrecio1;
                    descuento = multiplicacion - total;

                    String tipo_venta_guardar = tipo_venta;
                    if(tipo_venta.toString().equals("Extendido")){
                        tipo_venta_guardar = "Almacen";
                    }

                    String insertaPartida = "INSERT INTO vn_pedidos_partidas (cve_compania, num_pedido, num_partida, cve_cat_producto, cantidad, precio_unitario, porcentaje_descuento, " +
                            "suma, descuento, subtotal, impuesto, total, cve_centro_costo, cve_conducto, tipo_conducto, estatus) VALUES ('019', '"+ siguiente_pedido +"', '"+ partida +"', " +
                            "'"+ cve_cat_producto +"', '"+ opCant1 +"', '"+ opPrecio1 +"', '"+ porcentajeDesc +"', '"+ multiplicacion +"', '"+ descuento +"', '"+ total +"', '0', '"+ total +"', " +
                            "'"+ tipo_venta_guardar +"', '"+ cve_conducto +"', 'D', 'A')";

                    dbHandler.insertaPartidas(insertaPartida);

                }

                if(total38.length() > 0){
                    partida++;

                    ///Se extrae la cve_cat_producto del campo del producto
                    String cve_cat_producto;
                    String opProducto1 = String.valueOf(producto38.getText().toString());
                    String[] separated = opProducto1.split("-");
                    cve_cat_producto = separated[0];
                    double multiplicacion = 0;
                    double opCant1 = Double.valueOf(cantidad38.getText().toString());
                    double total = Double.valueOf(total38.getText().toString());
                    double opPrecio1 = Double.valueOf(precio38.getText().toString());
                    double porcentajeDesc = Double.valueOf(porcentaje38.getText().toString());
                    double descuento = 0;
                    multiplicacion = opCant1*opPrecio1;
                    descuento = multiplicacion - total;

                    String tipo_venta_guardar = tipo_venta;
                    if(tipo_venta.toString().equals("Extendido")){
                        tipo_venta_guardar = "Almacen";
                    }

                    String insertaPartida = "INSERT INTO vn_pedidos_partidas (cve_compania, num_pedido, num_partida, cve_cat_producto, cantidad, precio_unitario, porcentaje_descuento, " +
                            "suma, descuento, subtotal, impuesto, total, cve_centro_costo, cve_conducto, tipo_conducto, estatus) VALUES ('019', '"+ siguiente_pedido +"', '"+ partida +"', " +
                            "'"+ cve_cat_producto +"', '"+ opCant1 +"', '"+ opPrecio1 +"', '"+ porcentajeDesc +"', '"+ multiplicacion +"', '"+ descuento +"', '"+ total +"', '0', '"+ total +"', " +
                            "'"+ tipo_venta_guardar +"', '"+ cve_conducto +"', 'D', 'A')";

                    dbHandler.insertaPartidas(insertaPartida);

                }

                if(total39.length() > 0){
                    partida++;

                    ///Se extrae la cve_cat_producto del campo del producto
                    String cve_cat_producto;
                    String opProducto1 = String.valueOf(producto39.getText().toString());
                    String[] separated = opProducto1.split("-");
                    cve_cat_producto = separated[0];
                    double multiplicacion = 0;
                    double opCant1 = Double.valueOf(cantidad39.getText().toString());
                    double total = Double.valueOf(total39.getText().toString());
                    double opPrecio1 = Double.valueOf(precio39.getText().toString());
                    double porcentajeDesc = Double.valueOf(porcentaje39.getText().toString());
                    double descuento = 0;
                    multiplicacion = opCant1*opPrecio1;
                    descuento = multiplicacion - total;

                    String tipo_venta_guardar = tipo_venta;
                    if(tipo_venta.toString().equals("Extendido")){
                        tipo_venta_guardar = "Almacen";
                    }

                    String insertaPartida = "INSERT INTO vn_pedidos_partidas (cve_compania, num_pedido, num_partida, cve_cat_producto, cantidad, precio_unitario, porcentaje_descuento, " +
                            "suma, descuento, subtotal, impuesto, total, cve_centro_costo, cve_conducto, tipo_conducto, estatus) VALUES ('019', '"+ siguiente_pedido +"', '"+ partida +"', " +
                            "'"+ cve_cat_producto +"', '"+ opCant1 +"', '"+ opPrecio1 +"', '"+ porcentajeDesc +"', '"+ multiplicacion +"', '"+ descuento +"', '"+ total +"', '0', '"+ total +"', " +
                            "'"+ tipo_venta_guardar +"', '"+ cve_conducto +"', 'D', 'A')";

                    dbHandler.insertaPartidas(insertaPartida);

                }

                if(total40.length() > 0){
                    partida++;

                    ///Se extrae la cve_cat_producto del campo del producto
                    String cve_cat_producto;
                    String opProducto1 = String.valueOf(producto40.getText().toString());
                    String[] separated = opProducto1.split("-");
                    cve_cat_producto = separated[0];
                    double multiplicacion = 0;
                    double opCant1 = Double.valueOf(cantidad40.getText().toString());
                    double total = Double.valueOf(total40.getText().toString());
                    double opPrecio1 = Double.valueOf(precio40.getText().toString());
                    double porcentajeDesc = Double.valueOf(porcentaje40.getText().toString());
                    double descuento = 0;
                    multiplicacion = opCant1*opPrecio1;
                    descuento = multiplicacion - total;

                    String tipo_venta_guardar = tipo_venta;
                    if(tipo_venta.toString().equals("Extendido")){
                        tipo_venta_guardar = "Almacen";
                    }

                    String insertaPartida = "INSERT INTO vn_pedidos_partidas (cve_compania, num_pedido, num_partida, cve_cat_producto, cantidad, precio_unitario, porcentaje_descuento, " +
                            "suma, descuento, subtotal, impuesto, total, cve_centro_costo, cve_conducto, tipo_conducto, estatus) VALUES ('019', '"+ siguiente_pedido +"', '"+ partida +"', " +
                            "'"+ cve_cat_producto +"', '"+ opCant1 +"', '"+ opPrecio1 +"', '"+ porcentajeDesc +"', '"+ multiplicacion +"', '"+ descuento +"', '"+ total +"', '0', '"+ total +"', " +
                            "'"+ tipo_venta_guardar +"', '"+ cve_conducto +"', 'D', 'A')";

                    dbHandler.insertaPartidas(insertaPartida);

                }

                if(total41.length() > 0){
                    partida++;

                    ///Se extrae la cve_cat_producto del campo del producto
                    String cve_cat_producto;
                    String opProducto1 = String.valueOf(producto41.getText().toString());
                    String[] separated = opProducto1.split("-");
                    cve_cat_producto = separated[0];
                    double multiplicacion = 0;
                    double opCant1 = Double.valueOf(cantidad41.getText().toString());
                    double total = Double.valueOf(total41.getText().toString());
                    double opPrecio1 = Double.valueOf(precio41.getText().toString());
                    double porcentajeDesc = Double.valueOf(porcentaje41.getText().toString());
                    double descuento = 0;
                    multiplicacion = opCant1*opPrecio1;
                    descuento = multiplicacion - total;

                    String tipo_venta_guardar = tipo_venta;
                    if(tipo_venta.toString().equals("Extendido")){
                        tipo_venta_guardar = "Almacen";
                    }

                    String insertaPartida = "INSERT INTO vn_pedidos_partidas (cve_compania, num_pedido, num_partida, cve_cat_producto, cantidad, precio_unitario, porcentaje_descuento, " +
                            "suma, descuento, subtotal, impuesto, total, cve_centro_costo, cve_conducto, tipo_conducto, estatus) VALUES ('019', '"+ siguiente_pedido +"', '"+ partida +"', " +
                            "'"+ cve_cat_producto +"', '"+ opCant1 +"', '"+ opPrecio1 +"', '"+ porcentajeDesc +"', '"+ multiplicacion +"', '"+ descuento +"', '"+ total +"', '0', '"+ total +"', " +
                            "'"+ tipo_venta_guardar +"', '"+ cve_conducto +"', 'D', 'A')";

                    dbHandler.insertaPartidas(insertaPartida);

                }

                if(total42.length() > 0){
                    partida++;

                    ///Se extrae la cve_cat_producto del campo del producto
                    String cve_cat_producto;
                    String opProducto1 = String.valueOf(producto42.getText().toString());
                    String[] separated = opProducto1.split("-");
                    cve_cat_producto = separated[0];
                    double multiplicacion = 0;
                    double opCant1 = Double.valueOf(cantidad42.getText().toString());
                    double total = Double.valueOf(total42.getText().toString());
                    double opPrecio1 = Double.valueOf(precio42.getText().toString());
                    double porcentajeDesc = Double.valueOf(porcentaje42.getText().toString());
                    double descuento = 0;
                    multiplicacion = opCant1*opPrecio1;
                    descuento = multiplicacion - total;

                    String tipo_venta_guardar = tipo_venta;
                    if(tipo_venta.toString().equals("Extendido")){
                        tipo_venta_guardar = "Almacen";
                    }

                    String insertaPartida = "INSERT INTO vn_pedidos_partidas (cve_compania, num_pedido, num_partida, cve_cat_producto, cantidad, precio_unitario, porcentaje_descuento, " +
                            "suma, descuento, subtotal, impuesto, total, cve_centro_costo, cve_conducto, tipo_conducto, estatus) VALUES ('019', '"+ siguiente_pedido +"', '"+ partida +"', " +
                            "'"+ cve_cat_producto +"', '"+ opCant1 +"', '"+ opPrecio1 +"', '"+ porcentajeDesc +"', '"+ multiplicacion +"', '"+ descuento +"', '"+ total +"', '0', '"+ total +"', " +
                            "'"+ tipo_venta_guardar +"', '"+ cve_conducto +"', 'D', 'A')";

                    dbHandler.insertaPartidas(insertaPartida);

                }

                if(total43.length() > 0){
                    partida++;

                    ///Se extrae la cve_cat_producto del campo del producto
                    String cve_cat_producto;
                    String opProducto1 = String.valueOf(producto43.getText().toString());
                    String[] separated = opProducto1.split("-");
                    cve_cat_producto = separated[0];
                    double multiplicacion = 0;
                    double opCant1 = Double.valueOf(cantidad43.getText().toString());
                    double total = Double.valueOf(total43.getText().toString());
                    double opPrecio1 = Double.valueOf(precio43.getText().toString());
                    double porcentajeDesc = Double.valueOf(porcentaje43.getText().toString());
                    double descuento = 0;
                    multiplicacion = opCant1*opPrecio1;
                    descuento = multiplicacion - total;

                    String tipo_venta_guardar = tipo_venta;
                    if(tipo_venta.toString().equals("Extendido")){
                        tipo_venta_guardar = "Almacen";
                    }

                    String insertaPartida = "INSERT INTO vn_pedidos_partidas (cve_compania, num_pedido, num_partida, cve_cat_producto, cantidad, precio_unitario, porcentaje_descuento, " +
                            "suma, descuento, subtotal, impuesto, total, cve_centro_costo, cve_conducto, tipo_conducto, estatus) VALUES ('019', '"+ siguiente_pedido +"', '"+ partida +"', " +
                            "'"+ cve_cat_producto +"', '"+ opCant1 +"', '"+ opPrecio1 +"', '"+ porcentajeDesc +"', '"+ multiplicacion +"', '"+ descuento +"', '"+ total +"', '0', '"+ total +"', " +
                            "'"+ tipo_venta_guardar +"', '"+ cve_conducto +"', 'D', 'A')";

                    dbHandler.insertaPartidas(insertaPartida);

                }

                if(total44.length() > 0){
                    partida++;

                    ///Se extrae la cve_cat_producto del campo del producto
                    String cve_cat_producto;
                    String opProducto1 = String.valueOf(producto44.getText().toString());
                    String[] separated = opProducto1.split("-");
                    cve_cat_producto = separated[0];
                    double multiplicacion = 0;
                    double opCant1 = Double.valueOf(cantidad44.getText().toString());
                    double total = Double.valueOf(total44.getText().toString());
                    double opPrecio1 = Double.valueOf(precio44.getText().toString());
                    double porcentajeDesc = Double.valueOf(porcentaje44.getText().toString());
                    double descuento = 0;
                    multiplicacion = opCant1*opPrecio1;
                    descuento = multiplicacion - total;

                    String tipo_venta_guardar = tipo_venta;
                    if(tipo_venta.toString().equals("Extendido")){
                        tipo_venta_guardar = "Almacen";
                    }

                    String insertaPartida = "INSERT INTO vn_pedidos_partidas (cve_compania, num_pedido, num_partida, cve_cat_producto, cantidad, precio_unitario, porcentaje_descuento, " +
                            "suma, descuento, subtotal, impuesto, total, cve_centro_costo, cve_conducto, tipo_conducto, estatus) VALUES ('019', '"+ siguiente_pedido +"', '"+ partida +"', " +
                            "'"+ cve_cat_producto +"', '"+ opCant1 +"', '"+ opPrecio1 +"', '"+ porcentajeDesc +"', '"+ multiplicacion +"', '"+ descuento +"', '"+ total +"', '0', '"+ total +"', " +
                            "'"+ tipo_venta_guardar +"', '"+ cve_conducto +"', 'D', 'A')";

                    dbHandler.insertaPartidas(insertaPartida);

                }

                if(total45.length() > 0){
                    partida++;

                    ///Se extrae la cve_cat_producto del campo del producto
                    String cve_cat_producto;
                    String opProducto1 = String.valueOf(producto45.getText().toString());
                    String[] separated = opProducto1.split("-");
                    cve_cat_producto = separated[0];
                    double multiplicacion = 0;
                    double opCant1 = Double.valueOf(cantidad45.getText().toString());
                    double total = Double.valueOf(total45.getText().toString());
                    double opPrecio1 = Double.valueOf(precio45.getText().toString());
                    double porcentajeDesc = Double.valueOf(porcentaje45.getText().toString());
                    double descuento = 0;
                    multiplicacion = opCant1*opPrecio1;
                    descuento = multiplicacion - total;

                    String tipo_venta_guardar = tipo_venta;
                    if(tipo_venta.toString().equals("Extendido")){
                        tipo_venta_guardar = "Almacen";
                    }

                    String insertaPartida = "INSERT INTO vn_pedidos_partidas (cve_compania, num_pedido, num_partida, cve_cat_producto, cantidad, precio_unitario, porcentaje_descuento, " +
                            "suma, descuento, subtotal, impuesto, total, cve_centro_costo, cve_conducto, tipo_conducto, estatus) VALUES ('019', '"+ siguiente_pedido +"', '"+ partida +"', " +
                            "'"+ cve_cat_producto +"', '"+ opCant1 +"', '"+ opPrecio1 +"', '"+ porcentajeDesc +"', '"+ multiplicacion +"', '"+ descuento +"', '"+ total +"', '0', '"+ total +"', " +
                            "'"+ tipo_venta_guardar +"', '"+ cve_conducto +"', 'D', 'A')";

                    dbHandler.insertaPartidas(insertaPartida);

                }

                ///para verificar si se activo la oferta arma tu paquete y se inserta el producto de regalo
                String cve_cat_producto;
                String opProducto1 = String.valueOf(producto1.getText().toString());
                String[] separated = opProducto1.split("-");
                cve_cat_producto = separated[0];
                if(cve_cat_producto.equals("12591")){
                    partida++;
                    cve_cat_producto = "12649";
                    double multiplicacion = 1;
                    double opCant1 = 1;
                    double total = 0;
                    double opPrecio1 = 1;
                    double porcentajeDesc = 100;
                    double descuento = 1;
                    String insertaPartida = "INSERT INTO vn_pedidos_partidas (cve_compania, num_pedido, num_partida, cve_cat_producto, cantidad, precio_unitario, porcentaje_descuento, " +
                            "suma, descuento, subtotal, impuesto, total, cve_centro_costo, cve_conducto, tipo_conducto, estatus) VALUES ('019', '"+ siguiente_pedido +"', '"+ partida +"', " +
                            "'"+ cve_cat_producto +"', '"+ opCant1 +"', '"+ opPrecio1 +"', '"+ porcentajeDesc +"', '"+ multiplicacion +"', '"+ descuento +"', '"+ total +"', '0', '"+ total +"', " +
                            "'Almacen', '"+ cve_conducto +"', 'D', 'A')";

                    dbHandler.insertaPartidas(insertaPartida);
                }

                ///fin se declaran las variables para las partidas

                toast = Toast.makeText(getApplicationContext(), "Se finaliza el guardado de pedidos Local", Toast.LENGTH_SHORT);
                toast.show();

                toast = Toast.makeText(getApplicationContext(), "Su numero de pedido local es: "+siguiente_pedido, Toast.LENGTH_LONG);
                toast.show();

                reload(tipo_venta, opTotalT, clienteDato);

            }

        });//fin del metodo guardar el pedido

    }

    public void reload(String tipo_venta, Double opTotalT, String cliente){

        final MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        dbHandler.checkDBStatus(); // Tiene como fin forzar la creacion de la base de datos.

        if(tipo_venta.toString().equals("Extendido") || tipo_venta.toString().equals("Mano")) {

                Intent explicit_intent;
                explicit_intent = new Intent(CapturaPedidosGuardar.this, CapturaBonificacion.class);
                explicit_intent.putExtra("bonificacion", opTotalT);
                explicit_intent.putExtra("cliente", cliente);
                startActivity(explicit_intent);
                return;

        }
        else{

            if(CheckNetwork.isInternetAvailable(CapturaPedidosGuardar.this)) //returns true if internet available
            {
                new CapturaPedidosGuardar.JsonTaskSender(dbHandler).execute(); // Envia todos los pedidos pendientes x transmitir al sevidor. No los borra local
            }

            usuario = dbHandler.ultimoUsuarioRegistrado();

            Intent explicit_intent;
            explicit_intent = new Intent(CapturaPedidosGuardar.this, CapturaPedidos.class);
            explicit_intent.putExtra("usuario", usuario);
            startActivity(explicit_intent);
            return;
        }

    }


    private class JsonTaskSender extends AsyncTask<String, String, String>
    {
        // Bloque para poder usar los metodos de la clase MyDBHandler en la clase JsonTask
        private MyDBHandler dbHandler;
        public JsonTaskSender(MyDBHandler dbHandler)
        {
            this.dbHandler = dbHandler;
        }
        // Termina bloque de inclusion de los metodos de MyDBHandler (Se uso esta practica debido a que ya esta usado extends en la clase

        protected void onPreExecute()
        {
            super.onPreExecute();

            pdSender = new ProgressDialog(CapturaPedidosGuardar.this);
            pdSender.setTitle("Conectando a servidor Biochem...");
            pdSender.setMessage("Iniciando transmision...");
            pdSender.setCancelable(false);
            pdSender.show();
        }

        protected String doInBackground(String... params)
        {

            /*************************************************************
             * Bloque para envio de los pedidos :                        *
             * ***********************************************************/
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            String JsonUrlEnvioPedidos = "";
            String urlSendPurchase = "http://www.sybrem.com.mx/adsnet/syncmovil/ReceptorMovil.php?jsonCad="+ URLEncoder.encode(dbHandler.transmitePedidos());

            try {

                URL url = new URL(urlSendPurchase);
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
                //Log.d("Modo debug 1.2", buffer.toString());
                JsonUrlEnvioPedidos = buffer.toString().substring(0,12);
                if (JsonUrlEnvioPedidos.toString().equals("SINCRONIZADO"))
                {
                    // Sincronizacion exitosa, se eliminan pedidos
                    dbHandler.resetPedidos();

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

        } // FIn del doInBackground

        @Override
        protected void onPostExecute(String result)
        {
            super.onPostExecute(result);

            if (pdSender.isShowing())
            {
                pdSender.dismiss();
            }

        } // Fin del onPostExecute
    } // Fin de la clase JsonTaskSender

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
        CapturaPedidosGuardar mainActivity;

        public CapturaPedidosGuardar getMainActivity() {
            return mainActivity;
        }

        public void setMainActivity(CapturaPedidosGuardar mainActivity) {
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
