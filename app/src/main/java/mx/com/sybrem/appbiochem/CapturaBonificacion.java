package mx.com.sybrem.appbiochem;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

public class CapturaBonificacion extends AppCompatActivity {

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

    TextView bonificacion;
    String cliente = "";

    Double Bonificacion;

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
            cant15;

    AutoCompleteTextView producto1, producto2,
            producto3,producto4,
            producto5,producto6,
            producto7,producto8,
            producto9,producto10,
            producto11,producto12,
            producto13,producto14,
            producto15;

    TextView sumaTotal;

    //ARRAY PARA LOS PRODUCTOS
    private String[] productos;

    //SPINNER DE LAS PARTIDAS DE LOS ALMACENES
    Spinner lstAlmacen1, lstAlmacen2, lstAlmacen3, lstAlmacen4, lstAlmacen5, lstAlmacen6, lstAlmacen7, lstAlmacen8, lstAlmacen9,
    lstAlmacen10, lstAlmacen11, lstAlmacen12, lstAlmacen13, lstAlmacen14, lstAlmacen15;

    ProgressDialog pdSender; // Mensaje de progreso en sincronización para JSonTaskSender.

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        dbHandler.checkDBStatus(); // Tiene como fin forzar la creacion de la base de datos.

        productos = dbHandler.getProductosVeterinarios(); // Metodo que se trae la lista de productos de la tabla y la copia del array

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_captura_bonificacion);

        bonificacion = (TextView)findViewById(R.id.txtBonificacion);

        Intent intent=getIntent();
        Bundle extras = intent.getExtras();
        Bonificacion = (Double) extras.get("bonificacion");
        cliente = (String) extras.get("cliente");

        Double bonificacion_final = (Bonificacion*0.22);

        bonificacion.setText("" + bonificacion_final);

        //AUTOCOMPLETE DE LOS PRODUCTOS
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

        final TextView sumaTotal = (TextView)findViewById(R.id.txtSuma);

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

        ///PARA PONER LOS VALORES DEL LISTADO DE LOS ALMACENES
        ArrayAdapter<CharSequence> adaptador1 = ArrayAdapter.createFromResource(this, R.array.valores_bonificacion, android.R.layout.simple_spinner_item);
        lstAlmacen1 = (Spinner)findViewById(R.id.lstAlmacen1);
        adaptador1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lstAlmacen1.setAdapter(adaptador1);

        ArrayAdapter<CharSequence> adaptador2 = ArrayAdapter.createFromResource(this, R.array.valores_bonificacion, android.R.layout.simple_spinner_item);
        lstAlmacen2 = (Spinner)findViewById(R.id.lstAlmacen2);
        adaptador2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lstAlmacen2.setAdapter(adaptador2);

        ArrayAdapter<CharSequence> adaptador3 = ArrayAdapter.createFromResource(this, R.array.valores_bonificacion, android.R.layout.simple_spinner_item);
        lstAlmacen3 = (Spinner)findViewById(R.id.lstAlmacen3);
        adaptador3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lstAlmacen3.setAdapter(adaptador3);

        ArrayAdapter<CharSequence> adaptador4 = ArrayAdapter.createFromResource(this, R.array.valores_bonificacion, android.R.layout.simple_spinner_item);
        lstAlmacen4 = (Spinner)findViewById(R.id.lstAlmacen4);
        adaptador4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lstAlmacen4.setAdapter(adaptador4);

        ArrayAdapter<CharSequence> adaptador5 = ArrayAdapter.createFromResource(this, R.array.valores_bonificacion, android.R.layout.simple_spinner_item);
        lstAlmacen5 = (Spinner)findViewById(R.id.lstAlmacen5);
        adaptador5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lstAlmacen5.setAdapter(adaptador5);

        ArrayAdapter<CharSequence> adaptador6 = ArrayAdapter.createFromResource(this, R.array.valores_bonificacion, android.R.layout.simple_spinner_item);
        lstAlmacen6 = (Spinner)findViewById(R.id.lstAlmacen6);
        adaptador6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lstAlmacen6.setAdapter(adaptador6);

        ArrayAdapter<CharSequence> adaptador7 = ArrayAdapter.createFromResource(this, R.array.valores_bonificacion, android.R.layout.simple_spinner_item);
        lstAlmacen7 = (Spinner)findViewById(R.id.lstAlmacen7);
        adaptador7.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lstAlmacen7.setAdapter(adaptador7);

        ArrayAdapter<CharSequence> adaptador8 = ArrayAdapter.createFromResource(this, R.array.valores_bonificacion, android.R.layout.simple_spinner_item);
        lstAlmacen8 = (Spinner)findViewById(R.id.lstAlmacen8);
        adaptador8.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lstAlmacen8.setAdapter(adaptador8);

        ArrayAdapter<CharSequence> adaptador9 = ArrayAdapter.createFromResource(this, R.array.valores_bonificacion, android.R.layout.simple_spinner_item);
        lstAlmacen9 = (Spinner)findViewById(R.id.lstAlmacen9);
        adaptador9.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lstAlmacen9.setAdapter(adaptador9);

        ArrayAdapter<CharSequence> adaptador10 = ArrayAdapter.createFromResource(this, R.array.valores_bonificacion, android.R.layout.simple_spinner_item);
        lstAlmacen10 = (Spinner)findViewById(R.id.lstAlmacen10);
        adaptador10.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lstAlmacen10.setAdapter(adaptador10);

        ArrayAdapter<CharSequence> adaptador11 = ArrayAdapter.createFromResource(this, R.array.valores_bonificacion, android.R.layout.simple_spinner_item);
        lstAlmacen11 = (Spinner)findViewById(R.id.lstAlmacen11);
        adaptador11.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lstAlmacen11.setAdapter(adaptador11);

        ArrayAdapter<CharSequence> adaptador12 = ArrayAdapter.createFromResource(this, R.array.valores_bonificacion, android.R.layout.simple_spinner_item);
        lstAlmacen12 = (Spinner)findViewById(R.id.lstAlmacen12);
        adaptador12.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lstAlmacen12.setAdapter(adaptador12);

        ArrayAdapter<CharSequence> adaptador13 = ArrayAdapter.createFromResource(this, R.array.valores_bonificacion, android.R.layout.simple_spinner_item);
        lstAlmacen13 = (Spinner)findViewById(R.id.lstAlmacen13);
        adaptador13.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lstAlmacen13.setAdapter(adaptador13);

        ArrayAdapter<CharSequence> adaptador14 = ArrayAdapter.createFromResource(this, R.array.valores_bonificacion, android.R.layout.simple_spinner_item);
        lstAlmacen14 = (Spinner)findViewById(R.id.lstAlmacen14);
        adaptador14.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lstAlmacen14.setAdapter(adaptador14);

        ArrayAdapter<CharSequence> adaptador15 = ArrayAdapter.createFromResource(this, R.array.valores_bonificacion, android.R.layout.simple_spinner_item);
        lstAlmacen15 = (Spinner)findViewById(R.id.lstAlmacen15);
        adaptador15.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lstAlmacen15.setAdapter(adaptador15);

        View.OnFocusChangeListener listener;

        listener = new View.OnFocusChangeListener() {
            //Inicio del seOnFocusChangeListener
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

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


                ///Se rompe la cadena de producto para sacar el precio del producto y colocarlo en TextView correspondiente
                if (opProducto1.length() >= 1) {
                    String[] separated = opProducto1.split("-");
                    String cve_cat_producto = "";
                    cve_cat_producto = separated[0];

                    //Se valida si se selecciona mano y si hay existencia en el almacen
                    String tipo_venta_guardar = lstAlmacen1.getSelectedItem().toString();
                    if(tipo_venta_guardar.toString().equals("Mano")){
                        //Validacion para la existencia en almacen de mano
                        if(cant1.toString() != "0" && cant1.toString() != "") {
                                //Invocamos el metodo para valida que haya exitencia de mano
                                String cve_cliente = "";
                                String[] separatedCliente = cliente.split("-");
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

                        }//fin Validacion para la existencia en almacen de mano
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

                    //Se valida si se selecciona mano y si hay existencia en el almacen
                    String tipo_venta_guardar = lstAlmacen2.getSelectedItem().toString();
                    if(tipo_venta_guardar.toString().equals("Mano")){
                        //Validacion para la existencia en almacen de mano
                        if(cant2.toString() != "0" && cant2.toString() != "") {
                            //Invocamos el metodo para valida que haya exitencia de mano
                            String cve_cliente = "";
                            String[] separatedCliente = cliente.split("-");
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

                        }//fin Validacion para la existencia en almacen de mano
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

                    //Se valida si se selecciona mano y si hay existencia en el almacen
                    String tipo_venta_guardar = lstAlmacen3.getSelectedItem().toString();
                    if(tipo_venta_guardar.toString().equals("Mano")){
                        //Validacion para la existencia en almacen de mano
                        if(cant3.toString() != "0" && cant3.toString() != "") {
                            //Invocamos el metodo para valida que haya exitencia de mano
                            String cve_cliente = "";
                            String[] separatedCliente = cliente.split("-");
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

                        }//fin Validacion para la existencia en almacen de mano
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

                    //Se valida si se selecciona mano y si hay existencia en el almacen
                    String tipo_venta_guardar = lstAlmacen4.getSelectedItem().toString();
                    if(tipo_venta_guardar.toString().equals("Mano")){
                        //Validacion para la existencia en almacen de mano
                        if(cant4.toString() != "0" && cant4.toString() != "") {
                            //Invocamos el metodo para valida que haya exitencia de mano
                            String cve_cliente = "";
                            String[] separatedCliente = cliente.split("-");
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

                        }//fin Validacion para la existencia en almacen de mano
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

                    //Se valida si se selecciona mano y si hay existencia en el almacen
                    String tipo_venta_guardar = lstAlmacen5.getSelectedItem().toString();
                    if(tipo_venta_guardar.toString().equals("Mano")){
                        //Validacion para la existencia en almacen de mano
                        if(cant5.toString() != "0" && cant5.toString() != "") {
                            //Invocamos el metodo para valida que haya exitencia de mano
                            String cve_cliente = "";
                            String[] separatedCliente = cliente.split("-");
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

                        }//fin Validacion para la existencia en almacen de mano
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

                    //Se valida si se selecciona mano y si hay existencia en el almacen
                    String tipo_venta_guardar = lstAlmacen6.getSelectedItem().toString();
                    if(tipo_venta_guardar.toString().equals("Mano")){
                        //Validacion para la existencia en almacen de mano
                        if(cant6.toString() != "0" && cant6.toString() != "") {
                            //Invocamos el metodo para valida que haya exitencia de mano
                            String cve_cliente = "";
                            String[] separatedCliente = cliente.split("-");
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

                        }//fin Validacion para la existencia en almacen de mano
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

                    //Se valida si se selecciona mano y si hay existencia en el almacen
                    String tipo_venta_guardar = lstAlmacen7.getSelectedItem().toString();
                    if(tipo_venta_guardar.toString().equals("Mano")){
                        //Validacion para la existencia en almacen de mano
                        if(cant7.toString() != "0" && cant7.toString() != "") {
                            //Invocamos el metodo para valida que haya exitencia de mano
                            String cve_cliente = "";
                            String[] separatedCliente = cliente.split("-");
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

                        }//fin Validacion para la existencia en almacen de mano
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

                    //Se valida si se selecciona mano y si hay existencia en el almacen
                    String tipo_venta_guardar = lstAlmacen8.getSelectedItem().toString();
                    if(tipo_venta_guardar.toString().equals("Mano")){
                        //Validacion para la existencia en almacen de mano
                        if(cant8.toString() != "0" && cant8.toString() != "") {
                            //Invocamos el metodo para valida que haya exitencia de mano
                            String cve_cliente = "";
                            String[] separatedCliente = cliente.split("-");
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

                        }//fin Validacion para la existencia en almacen de mano
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

                    //Se valida si se selecciona mano y si hay existencia en el almacen
                    String tipo_venta_guardar = lstAlmacen9.getSelectedItem().toString();
                    if(tipo_venta_guardar.toString().equals("Mano")){
                        //Validacion para la existencia en almacen de mano
                        if(cant9.toString() != "0" && cant9.toString() != "") {
                            //Invocamos el metodo para valida que haya exitencia de mano
                            String cve_cliente = "";
                            String[] separatedCliente = cliente.split("-");
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

                        }//fin Validacion para la existencia en almacen de mano
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

                    //Se valida si se selecciona mano y si hay existencia en el almacen
                    String tipo_venta_guardar = lstAlmacen10.getSelectedItem().toString();
                    if(tipo_venta_guardar.toString().equals("Mano")){
                        //Validacion para la existencia en almacen de mano
                        if(cant10.toString() != "0" && cant10.toString() != "") {
                            //Invocamos el metodo para valida que haya exitencia de mano
                            String cve_cliente = "";
                            String[] separatedCliente = cliente.split("-");
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

                        }//fin Validacion para la existencia en almacen de mano
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

                    //Se valida si se selecciona mano y si hay existencia en el almacen
                    String tipo_venta_guardar = lstAlmacen11.getSelectedItem().toString();
                    if(tipo_venta_guardar.toString().equals("Mano")){
                        //Validacion para la existencia en almacen de mano
                        if(cant11.toString() != "0" && cant11.toString() != "") {
                            //Invocamos el metodo para valida que haya exitencia de mano
                            String cve_cliente = "";
                            String[] separatedCliente = cliente.split("-");
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

                        }//fin Validacion para la existencia en almacen de mano
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

                    //Se valida si se selecciona mano y si hay existencia en el almacen
                    String tipo_venta_guardar = lstAlmacen12.getSelectedItem().toString();
                    if(tipo_venta_guardar.toString().equals("Mano")){
                        //Validacion para la existencia en almacen de mano
                        if(cant12.toString() != "0" && cant12.toString() != "") {
                            //Invocamos el metodo para valida que haya exitencia de mano
                            String cve_cliente = "";
                            String[] separatedCliente = cliente.split("-");
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

                        }//fin Validacion para la existencia en almacen de mano
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

                    //Se valida si se selecciona mano y si hay existencia en el almacen
                    String tipo_venta_guardar = lstAlmacen13.getSelectedItem().toString();
                    if(tipo_venta_guardar.toString().equals("Mano")){
                        //Validacion para la existencia en almacen de mano
                        if(cant13.toString() != "0" && cant13.toString() != "") {
                            //Invocamos el metodo para valida que haya exitencia de mano
                            String cve_cliente = "";
                            String[] separatedCliente = cliente.split("-");
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

                        }//fin Validacion para la existencia en almacen de mano
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

                    //Se valida si se selecciona mano y si hay existencia en el almacen
                    String tipo_venta_guardar = lstAlmacen14.getSelectedItem().toString();
                    if(tipo_venta_guardar.toString().equals("Mano")){
                        //Validacion para la existencia en almacen de mano
                        if(cant14.toString() != "0" && cant14.toString() != "") {
                            //Invocamos el metodo para valida que haya exitencia de mano
                            String cve_cliente = "";
                            String[] separatedCliente = cliente.split("-");
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
                                    Toast toast = Toast.makeText(getApplicationContext(), "El producto en la partida 10 no tiene suficiente existencia en su almacen", Toast.LENGTH_SHORT);
                                    toast.show();
                                    return;
                                }
                            }

                        }//fin Validacion para la existencia en almacen de mano
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

                    //Se valida si se selecciona mano y si hay existencia en el almacen
                    String tipo_venta_guardar = lstAlmacen15.getSelectedItem().toString();
                    if(tipo_venta_guardar.toString().equals("Mano")){
                        //Validacion para la existencia en almacen de mano
                        if(cant15.toString() != "0" && cant15.toString() != "") {
                            //Invocamos el metodo para valida que haya exitencia de mano
                            String cve_cliente = "";
                            String[] separatedCliente = cliente.split("-");
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

                        }//fin Validacion para la existencia en almacen de mano
                    }

                    //Invocamos el metodo para sacar el precio unitario del producto
                    Double precio_unitario = dbHandler.precioProducto(cve_cat_producto);
                    precio15.setText("" + precio_unitario);
                }
                if (opProducto15.length() == 0) {
                    precio15.setText("" + 0.0);
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
                if (opCant3 != 0 && opPrecio3 != 0) {
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

                //PORCENTAJE DE DESCUENTO DE LAS BONIFICACIONES
                porcentaje_desc = 100;

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

                ///***PARA PONER EL DATO EN SUMA, DESCUENTO Y TOTAL
                sumaTotal.setText("" + suma);
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

        Button btn_no = (Button)findViewById(R.id.btnPedidosNoGracias);
        ///Inicia metodo para no guardar bonificaciones
        btn_no.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                final MyDBHandler dbHandler = new MyDBHandler(CapturaBonificacion.this, null, null, 1);
                dbHandler.checkDBStatus(); // Tiene como fin forzar la creacion de la base de datos.

                String usuario = dbHandler.ultimoUsuarioRegistrado();

                Intent explicit_intent;
                explicit_intent = new Intent(CapturaBonificacion.this, CapturaPedidos.class);
                explicit_intent.putExtra("usuario", usuario);
                startActivity(explicit_intent);

                if(CheckNetwork.isInternetAvailable(CapturaBonificacion.this)) //returns true if internet available
                {
                    new CapturaBonificacion.JsonTaskSender(dbHandler).execute(); // Envia todos los pedidos pendientes x transmitir al sevidor. No los borra local
                }

                return;
            }

        });//fin del metodo para no guardar bonificaciones


        Button btn_saved = (Button)findViewById(R.id.btnPedidosGuardarBonificacion);
        ///Inicia metodo para guardar el pedido
        btn_saved.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                String strBonificacion = String.valueOf(bonificacion.getText().toString());
                String strTotal = String.valueOf(sumaTotal.getText().toString());

                Double bonificacion = Double.parseDouble(strBonificacion);
                Double suma = Double.parseDouble(strTotal);

                if(suma > bonificacion){
                    Toast toast = Toast.makeText(getApplicationContext(), "El monto capturado es mayor que la bonificacion", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }

                Toast toast = Toast.makeText(getApplicationContext(), "Se inicializo el guardado de pedidos Local", Toast.LENGTH_SHORT);
                toast.show();


                ///Metodo para sacar el siguiente numero de pedido en la tabla local
                String siguiente_pedido = dbHandler.pedido_actual();

                ///Metodo para sacar el siguiente numero de partida en la tabla local
                String num_partida = dbHandler.partida_actual();
                int partida = Integer.parseInt(num_partida);

                ///Metodo para sacar el conducto del pedido que se esta usando
                String cve_conducto = dbHandler.conducto_pedido();

                ///Se declaran las variables para las partidas

                if(producto1.length() > 0){
                    partida++;

                    ///Se extrae la cve_cat_producto del campo del producto
                    String cve_cat_producto;
                    String opProducto1 = String.valueOf(producto1.getText().toString());
                    String[] separated = opProducto1.split("-");
                    cve_cat_producto = separated[0];
                    String tipo_venta_guardar = lstAlmacen1.getSelectedItem().toString();
                    if(tipo_venta_guardar.length() <= 0){
                        tipo_venta_guardar = "Almacen";
                    }
                    double multiplicacion = 0;
                    double opCant1 = Double.valueOf(cant1.getText().toString());
                    double total = Double.valueOf(total1.getText().toString());
                    double opPrecio1 = Double.valueOf(precio1.getText().toString());
                    double descuento = 0;
                    multiplicacion = opCant1*opPrecio1;
                    descuento = multiplicacion - total;



                    String insertaPartida = "INSERT INTO vn_pedidos_partidas (cve_compania, num_pedido, num_partida, cve_cat_producto, cantidad, precio_unitario, porcentaje_descuento, " +
                            "suma, descuento, subtotal, impuesto, total, cve_centro_costo, cve_conducto, tipo_conducto, estatus) VALUES ('019', '"+ siguiente_pedido +"', '"+ partida +"', " +
                            "'"+ cve_cat_producto +"', '"+ opCant1 +"', '"+ opPrecio1 +"', '100', '"+ multiplicacion +"', '"+ descuento +"', '"+ total +"', '0', '"+ total +"', " +
                            "'"+ tipo_venta_guardar +"', '"+ cve_conducto +"', 'D', 'A')";

                    dbHandler.insertaPartidas(insertaPartida);

                }

                if(producto2.length() > 0){
                    partida++;

                    ///Se extrae la cve_cat_producto del campo del producto
                    String cve_cat_producto;
                    String opProducto1 = String.valueOf(producto2.getText().toString());
                    String[] separated = opProducto1.split("-");
                    cve_cat_producto = separated[0];
                    String tipo_venta_guardar = lstAlmacen2.getSelectedItem().toString();
                    if(tipo_venta_guardar.length() <= 0){
                        tipo_venta_guardar = "Almacen";
                    }
                    double multiplicacion = 0;
                    double opCant1 = Double.valueOf(cant2.getText().toString());
                    double total = Double.valueOf(total2.getText().toString());
                    double opPrecio1 = Double.valueOf(precio2.getText().toString());
                    double descuento = 0;
                    multiplicacion = opCant1*opPrecio1;
                    descuento = multiplicacion - total;


                    String insertaPartida = "INSERT INTO vn_pedidos_partidas (cve_compania, num_pedido, num_partida, cve_cat_producto, cantidad, precio_unitario, porcentaje_descuento, " +
                            "suma, descuento, subtotal, impuesto, total, cve_centro_costo, cve_conducto, tipo_conducto, estatus) VALUES ('019', '"+ siguiente_pedido +"', '"+ partida +"', " +
                            "'"+ cve_cat_producto +"', '"+ opCant1 +"', '"+ opPrecio1 +"', '100', '"+ multiplicacion +"', '"+ descuento +"', '"+ total +"', '0', '"+ total +"', " +
                            "'"+ tipo_venta_guardar +"', '"+ cve_conducto +"', 'D', 'A')";

                    dbHandler.insertaPartidas(insertaPartida);

                }

                if(producto3.length() > 0){
                    partida++;

                    ///Se extrae la cve_cat_producto del campo del producto
                    String cve_cat_producto;
                    String opProducto1 = String.valueOf(producto3.getText().toString());
                    String[] separated = opProducto1.split("-");
                    cve_cat_producto = separated[0];
                    String tipo_venta_guardar = lstAlmacen3.getSelectedItem().toString();
                    if(tipo_venta_guardar.length() <= 0){
                        tipo_venta_guardar = "Almacen";
                    }
                    double multiplicacion = 0;
                    double opCant1 = Double.valueOf(cant3.getText().toString());
                    double total = Double.valueOf(total3.getText().toString());
                    double opPrecio1 = Double.valueOf(precio3.getText().toString());
                    double descuento = 0;
                    multiplicacion = opCant1*opPrecio1;
                    descuento = multiplicacion - total;

                    String insertaPartida = "INSERT INTO vn_pedidos_partidas (cve_compania, num_pedido, num_partida, cve_cat_producto, cantidad, precio_unitario, porcentaje_descuento, " +
                            "suma, descuento, subtotal, impuesto, total, cve_centro_costo, cve_conducto, tipo_conducto, estatus) VALUES ('019', '"+ siguiente_pedido +"', '"+ partida +"', " +
                            "'"+ cve_cat_producto +"', '"+ opCant1 +"', '"+ opPrecio1 +"', '100', '"+ multiplicacion +"', '"+ descuento +"', '"+ total +"', '0', '"+ total +"', " +
                            "'"+ tipo_venta_guardar +"', '"+ cve_conducto +"', 'D', 'A')";

                    dbHandler.insertaPartidas(insertaPartida);

                }

                if(producto4.length() > 0){
                    partida++;

                    ///Se extrae la cve_cat_producto del campo del producto
                    String cve_cat_producto;
                    String opProducto1 = String.valueOf(producto4.getText().toString());
                    String[] separated = opProducto1.split("-");
                    cve_cat_producto = separated[0];
                    String tipo_venta_guardar = lstAlmacen4.getSelectedItem().toString();
                    if(tipo_venta_guardar.length() <= 0){
                        tipo_venta_guardar = "Almacen";
                    }
                    double multiplicacion = 0;
                    double opCant1 = Double.valueOf(cant4.getText().toString());
                    double total = Double.valueOf(total4.getText().toString());
                    double opPrecio1 = Double.valueOf(precio4.getText().toString());
                    double descuento = 0;
                    multiplicacion = opCant1*opPrecio1;
                    descuento = multiplicacion - total;

                    String insertaPartida = "INSERT INTO vn_pedidos_partidas (cve_compania, num_pedido, num_partida, cve_cat_producto, cantidad, precio_unitario, porcentaje_descuento, " +
                            "suma, descuento, subtotal, impuesto, total, cve_centro_costo, cve_conducto, tipo_conducto, estatus) VALUES ('019', '"+ siguiente_pedido +"', '"+ partida +"', " +
                            "'"+ cve_cat_producto +"', '"+ opCant1 +"', '"+ opPrecio1 +"', '100', '"+ multiplicacion +"', '"+ descuento +"', '"+ total +"', '0', '"+ total +"', " +
                            "'"+ tipo_venta_guardar +"', '"+ cve_conducto +"', 'D', 'A')";

                    dbHandler.insertaPartidas(insertaPartida);

                }

                if(producto5.length() > 0){
                    partida++;

                    ///Se extrae la cve_cat_producto del campo del producto
                    String cve_cat_producto;
                    String opProducto1 = String.valueOf(producto5.getText().toString());
                    String[] separated = opProducto1.split("-");
                    cve_cat_producto = separated[0];
                    String tipo_venta_guardar = lstAlmacen1.getSelectedItem().toString();
                    if(tipo_venta_guardar.length() <= 0){
                        tipo_venta_guardar = "Almacen";
                    }
                    double multiplicacion = 0;
                    double opCant1 = Double.valueOf(cant5.getText().toString());
                    double total = Double.valueOf(total5.getText().toString());
                    double opPrecio1 = Double.valueOf(precio5.getText().toString());
                    double descuento = 0;
                    multiplicacion = opCant1*opPrecio1;
                    descuento = multiplicacion - total;

                    String insertaPartida = "INSERT INTO vn_pedidos_partidas (cve_compania, num_pedido, num_partida, cve_cat_producto, cantidad, precio_unitario, porcentaje_descuento, " +
                            "suma, descuento, subtotal, impuesto, total, cve_centro_costo, cve_conducto, tipo_conducto, estatus) VALUES ('019', '"+ siguiente_pedido +"', '"+ partida +"', " +
                            "'"+ cve_cat_producto +"', '"+ opCant1 +"', '"+ opPrecio1 +"', '100', '"+ multiplicacion +"', '"+ descuento +"', '"+ total +"', '0', '"+ total +"', " +
                            "'"+ tipo_venta_guardar +"', '"+ cve_conducto +"', 'D', 'A')";

                    dbHandler.insertaPartidas(insertaPartida);

                }

                if(producto6.length() > 0){
                    partida++;

                    ///Se extrae la cve_cat_producto del campo del producto
                    String cve_cat_producto;
                    String opProducto1 = String.valueOf(producto6.getText().toString());
                    String[] separated = opProducto1.split("-");
                    cve_cat_producto = separated[0];
                    String tipo_venta_guardar = lstAlmacen1.getSelectedItem().toString();
                    if(tipo_venta_guardar.length() <= 0){
                        tipo_venta_guardar = "Almacen";
                    }
                    double multiplicacion = 0;
                    double opCant1 = Double.valueOf(cant6.getText().toString());
                    double total = Double.valueOf(total6.getText().toString());
                    double opPrecio1 = Double.valueOf(precio6.getText().toString());
                    double descuento = 0;
                    multiplicacion = opCant1*opPrecio1;
                    descuento = multiplicacion - total;

                    String insertaPartida = "INSERT INTO vn_pedidos_partidas (cve_compania, num_pedido, num_partida, cve_cat_producto, cantidad, precio_unitario, porcentaje_descuento, " +
                            "suma, descuento, subtotal, impuesto, total, cve_centro_costo, cve_conducto, tipo_conducto, estatus) VALUES ('019', '"+ siguiente_pedido +"', '"+ partida +"', " +
                            "'"+ cve_cat_producto +"', '"+ opCant1 +"', '"+ opPrecio1 +"', '100', '"+ multiplicacion +"', '"+ descuento +"', '"+ total +"', '0', '"+ total +"', " +
                            "'"+ tipo_venta_guardar +"', '"+ cve_conducto +"', 'D', 'A')";

                    dbHandler.insertaPartidas(insertaPartida);

                }

                if(producto7.length() > 0){
                    partida++;

                    ///Se extrae la cve_cat_producto del campo del producto
                    String cve_cat_producto;
                    String opProducto1 = String.valueOf(producto7.getText().toString());
                    String[] separated = opProducto1.split("-");
                    cve_cat_producto = separated[0];
                    String tipo_venta_guardar = lstAlmacen7.getSelectedItem().toString();
                    if(tipo_venta_guardar.length() <= 0){
                        tipo_venta_guardar = "Almacen";
                    }
                    double multiplicacion = 0;
                    double opCant1 = Double.valueOf(cant7.getText().toString());
                    double total = Double.valueOf(total7.getText().toString());
                    double opPrecio1 = Double.valueOf(precio7.getText().toString());
                    double descuento = 0;
                    multiplicacion = opCant1*opPrecio1;
                    descuento = multiplicacion - total;

                    String insertaPartida = "INSERT INTO vn_pedidos_partidas (cve_compania, num_pedido, num_partida, cve_cat_producto, cantidad, precio_unitario, porcentaje_descuento, " +
                            "suma, descuento, subtotal, impuesto, total, cve_centro_costo, cve_conducto, tipo_conducto, estatus) VALUES ('019', '"+ siguiente_pedido +"', '"+ partida +"', " +
                            "'"+ cve_cat_producto +"', '"+ opCant1 +"', '"+ opPrecio1 +"', '100', '"+ multiplicacion +"', '"+ descuento +"', '"+ total +"', '0', '"+ total +"', " +
                            "'"+ tipo_venta_guardar +"', '"+ cve_conducto +"', 'D', 'A')";

                    dbHandler.insertaPartidas(insertaPartida);

                }

                if(producto8.length() > 0){
                    partida++;

                    ///Se extrae la cve_cat_producto del campo del producto
                    String cve_cat_producto;
                    String opProducto1 = String.valueOf(producto8.getText().toString());
                    String[] separated = opProducto1.split("-");
                    cve_cat_producto = separated[0];
                    String tipo_venta_guardar = lstAlmacen8.getSelectedItem().toString();
                    if(tipo_venta_guardar.length() <= 0){
                        tipo_venta_guardar = "Almacen";
                    }
                    double multiplicacion = 0;
                    double opCant1 = Double.valueOf(cant8.getText().toString());
                    double total = Double.valueOf(total8.getText().toString());
                    double opPrecio1 = Double.valueOf(precio8.getText().toString());
                    double descuento = 0;
                    multiplicacion = opCant1*opPrecio1;
                    descuento = multiplicacion - total;

                    String insertaPartida = "INSERT INTO vn_pedidos_partidas (cve_compania, num_pedido, num_partida, cve_cat_producto, cantidad, precio_unitario, porcentaje_descuento, " +
                            "suma, descuento, subtotal, impuesto, total, cve_centro_costo, cve_conducto, tipo_conducto, estatus) VALUES ('019', '"+ siguiente_pedido +"', '"+ partida +"', " +
                            "'"+ cve_cat_producto +"', '"+ opCant1 +"', '"+ opPrecio1 +"', '100', '"+ multiplicacion +"', '"+ descuento +"', '"+ total +"', '0', '"+ total +"', " +
                            "'"+ tipo_venta_guardar +"', '"+ cve_conducto +"', 'D', 'A')";

                    dbHandler.insertaPartidas(insertaPartida);

                }

                if(producto9.length() > 0){
                    partida++;

                    ///Se extrae la cve_cat_producto del campo del producto
                    String cve_cat_producto;
                    String opProducto1 = String.valueOf(producto9.getText().toString());
                    String[] separated = opProducto1.split("-");
                    cve_cat_producto = separated[0];
                    String tipo_venta_guardar = lstAlmacen9.getSelectedItem().toString();
                    if(tipo_venta_guardar.length() <= 0){
                        tipo_venta_guardar = "Almacen";
                    }
                    double multiplicacion = 0;
                    double opCant1 = Double.valueOf(cant9.getText().toString());
                    double total = Double.valueOf(total9.getText().toString());
                    double opPrecio1 = Double.valueOf(precio9.getText().toString());
                    double descuento = 0;
                    multiplicacion = opCant1*opPrecio1;
                    descuento = multiplicacion - total;

                    String insertaPartida = "INSERT INTO vn_pedidos_partidas (cve_compania, num_pedido, num_partida, cve_cat_producto, cantidad, precio_unitario, porcentaje_descuento, " +
                            "suma, descuento, subtotal, impuesto, total, cve_centro_costo, cve_conducto, tipo_conducto, estatus) VALUES ('019', '"+ siguiente_pedido +"', '"+ partida +"', " +
                            "'"+ cve_cat_producto +"', '"+ opCant1 +"', '"+ opPrecio1 +"', '100', '"+ multiplicacion +"', '"+ descuento +"', '"+ total +"', '0', '"+ total +"', " +
                            "'"+ tipo_venta_guardar +"', '"+ cve_conducto +"', 'D', 'A')";

                    dbHandler.insertaPartidas(insertaPartida);

                }

                if(producto10.length() > 0){
                    partida++;

                    ///Se extrae la cve_cat_producto del campo del producto
                    String cve_cat_producto;
                    String opProducto1 = String.valueOf(producto10.getText().toString());
                    String[] separated = opProducto1.split("-");
                    cve_cat_producto = separated[0];
                    String tipo_venta_guardar = lstAlmacen10.getSelectedItem().toString();
                    if(tipo_venta_guardar.length() <= 0){
                        tipo_venta_guardar = "Almacen";
                    }
                    double multiplicacion = 0;
                    double opCant1 = Double.valueOf(cant10.getText().toString());
                    double total = Double.valueOf(total10.getText().toString());
                    double opPrecio1 = Double.valueOf(precio10.getText().toString());
                    double descuento = 0;
                    multiplicacion = opCant1*opPrecio1;
                    descuento = multiplicacion - total;


                    String insertaPartida = "INSERT INTO vn_pedidos_partidas (cve_compania, num_pedido, num_partida, cve_cat_producto, cantidad, precio_unitario, porcentaje_descuento, " +
                            "suma, descuento, subtotal, impuesto, total, cve_centro_costo, cve_conducto, tipo_conducto, estatus) VALUES ('019', '"+ siguiente_pedido +"', '"+ partida +"', " +
                            "'"+ cve_cat_producto +"', '"+ opCant1 +"', '"+ opPrecio1 +"', '100', '"+ multiplicacion +"', '"+ descuento +"', '"+ total +"', '0', '"+ total +"', " +
                            "'"+ tipo_venta_guardar +"', '"+ cve_conducto +"', 'D', 'A')";

                    dbHandler.insertaPartidas(insertaPartida);

                }

                if(producto11.length() > 0){
                    partida++;

                    ///Se extrae la cve_cat_producto del campo del producto
                    String cve_cat_producto;
                    String opProducto1 = String.valueOf(producto11.getText().toString());
                    String[] separated = opProducto1.split("-");
                    cve_cat_producto = separated[0];
                    String tipo_venta_guardar = lstAlmacen11.getSelectedItem().toString();
                    if(tipo_venta_guardar.length() <= 0){
                        tipo_venta_guardar = "Almacen";
                    }
                    double multiplicacion = 0;
                    double opCant1 = Double.valueOf(cant11.getText().toString());
                    double total = Double.valueOf(total11.getText().toString());
                    double opPrecio1 = Double.valueOf(precio11.getText().toString());
                    double descuento = 0;
                    multiplicacion = opCant1*opPrecio1;
                    descuento = multiplicacion - total;

                    String insertaPartida = "INSERT INTO vn_pedidos_partidas (cve_compania, num_pedido, num_partida, cve_cat_producto, cantidad, precio_unitario, porcentaje_descuento, " +
                            "suma, descuento, subtotal, impuesto, total, cve_centro_costo, cve_conducto, tipo_conducto, estatus) VALUES ('019', '"+ siguiente_pedido +"', '"+ partida +"', " +
                            "'"+ cve_cat_producto +"', '"+ opCant1 +"', '"+ opPrecio1 +"', '100', '"+ multiplicacion +"', '"+ descuento +"', '"+ total +"', '0', '"+ total +"', " +
                            "'"+ tipo_venta_guardar +"', '"+ cve_conducto +"', 'D', 'A')";

                    dbHandler.insertaPartidas(insertaPartida);

                }

                if(producto12.length() > 0){
                    partida++;

                    ///Se extrae la cve_cat_producto del campo del producto
                    String cve_cat_producto;
                    String opProducto1 = String.valueOf(producto12.getText().toString());
                    String[] separated = opProducto1.split("-");
                    cve_cat_producto = separated[0];
                    String tipo_venta_guardar = lstAlmacen12.getSelectedItem().toString();
                    if(tipo_venta_guardar.length() <= 0){
                        tipo_venta_guardar = "Almacen";
                    }
                    double multiplicacion = 0;
                    double opCant1 = Double.valueOf(cant12.getText().toString());
                    double total = Double.valueOf(total12.getText().toString());
                    double opPrecio1 = Double.valueOf(precio12.getText().toString());
                    double descuento = 0;
                    multiplicacion = opCant1*opPrecio1;
                    descuento = multiplicacion - total;

                    String insertaPartida = "INSERT INTO vn_pedidos_partidas (cve_compania, num_pedido, num_partida, cve_cat_producto, cantidad, precio_unitario, porcentaje_descuento, " +
                            "suma, descuento, subtotal, impuesto, total, cve_centro_costo, cve_conducto, tipo_conducto, estatus) VALUES ('019', '"+ siguiente_pedido +"', '"+ partida +"', " +
                            "'"+ cve_cat_producto +"', '"+ opCant1 +"', '"+ opPrecio1 +"', '100', '"+ multiplicacion +"', '"+ descuento +"', '"+ total +"', '0', '"+ total +"', " +
                            "'"+ tipo_venta_guardar +"', '"+ cve_conducto +"', 'D', 'A')";

                    dbHandler.insertaPartidas(insertaPartida);

                }

                if(producto13.length() > 0){
                    partida++;

                    ///Se extrae la cve_cat_producto del campo del producto
                    String cve_cat_producto;
                    String opProducto1 = String.valueOf(producto13.getText().toString());
                    String[] separated = opProducto1.split("-");
                    cve_cat_producto = separated[0];
                    String tipo_venta_guardar = lstAlmacen13.getSelectedItem().toString();
                    if(tipo_venta_guardar.length() <= 0){
                        tipo_venta_guardar = "Almacen";
                    }
                    double multiplicacion = 0;
                    double opCant1 = Double.valueOf(cant13.getText().toString());
                    double total = Double.valueOf(total13.getText().toString());
                    double opPrecio1 = Double.valueOf(precio13.getText().toString());
                    double descuento = 0;
                    multiplicacion = opCant1*opPrecio1;
                    descuento = multiplicacion - total;

                    String insertaPartida = "INSERT INTO vn_pedidos_partidas (cve_compania, num_pedido, num_partida, cve_cat_producto, cantidad, precio_unitario, porcentaje_descuento, " +
                            "suma, descuento, subtotal, impuesto, total, cve_centro_costo, cve_conducto, tipo_conducto, estatus) VALUES ('019', '"+ siguiente_pedido +"', '"+ partida +"', " +
                            "'"+ cve_cat_producto +"', '"+ opCant1 +"', '"+ opPrecio1 +"', '100', '"+ multiplicacion +"', '"+ descuento +"', '"+ total +"', '0', '"+ total +"', " +
                            "'"+ tipo_venta_guardar +"', '"+ cve_conducto +"', 'D', 'A')";

                    dbHandler.insertaPartidas(insertaPartida);

                }

                if(producto14.length() > 0){
                    partida++;

                    ///Se extrae la cve_cat_producto del campo del producto
                    String cve_cat_producto;
                    String opProducto1 = String.valueOf(producto14.getText().toString());
                    String[] separated = opProducto1.split("-");
                    cve_cat_producto = separated[0];
                    String tipo_venta_guardar = lstAlmacen14.getSelectedItem().toString();
                    if(tipo_venta_guardar.length() <= 0){
                        tipo_venta_guardar = "Almacen";
                    }
                    double multiplicacion = 0;
                    double opCant1 = Double.valueOf(cant14.getText().toString());
                    double total = Double.valueOf(total14.getText().toString());
                    double opPrecio1 = Double.valueOf(precio14.getText().toString());
                    double descuento = 0;
                    multiplicacion = opCant1*opPrecio1;
                    descuento = multiplicacion - total;

                    String insertaPartida = "INSERT INTO vn_pedidos_partidas (cve_compania, num_pedido, num_partida, cve_cat_producto, cantidad, precio_unitario, porcentaje_descuento, " +
                            "suma, descuento, subtotal, impuesto, total, cve_centro_costo, cve_conducto, tipo_conducto, estatus) VALUES ('019', '"+ siguiente_pedido +"', '"+ partida +"', " +
                            "'"+ cve_cat_producto +"', '"+ opCant1 +"', '"+ opPrecio1 +"', '100', '"+ multiplicacion +"', '"+ descuento +"', '"+ total +"', '0', '"+ total +"', " +
                            "'"+ tipo_venta_guardar +"', '"+ cve_conducto +"', 'D', 'A')";

                    dbHandler.insertaPartidas(insertaPartida);

                }

                if(producto15.length() > 0){
                    partida++;

                    ///Se extrae la cve_cat_producto del campo del producto
                    String cve_cat_producto;
                    String opProducto1 = String.valueOf(producto15.getText().toString());
                    String[] separated = opProducto1.split("-");
                    cve_cat_producto = separated[0];
                    String tipo_venta_guardar = lstAlmacen15.getSelectedItem().toString();
                    if(tipo_venta_guardar.length() <= 0){
                        tipo_venta_guardar = "Almacen";
                    }
                    double multiplicacion = 0;
                    double opCant1 = Double.valueOf(cant15.getText().toString());
                    double total = Double.valueOf(total15.getText().toString());
                    double opPrecio1 = Double.valueOf(precio15.getText().toString());
                    double descuento = 0;
                    multiplicacion = opCant1*opPrecio1;
                    descuento = multiplicacion - total;

                    String insertaPartida = "INSERT INTO vn_pedidos_partidas (cve_compania, num_pedido, num_partida, cve_cat_producto, cantidad, precio_unitario, porcentaje_descuento, " +
                            "suma, descuento, subtotal, impuesto, total, cve_centro_costo, cve_conducto, tipo_conducto, estatus) VALUES ('019', '"+ siguiente_pedido +"', '"+ partida +"', " +
                            "'"+ cve_cat_producto +"', '"+ opCant1 +"', '"+ opPrecio1 +"', '100', '"+ multiplicacion +"', '"+ descuento +"', '"+ total +"', '0', '"+ total +"', " +
                            "'"+ tipo_venta_guardar +"', '"+ cve_conducto +"', 'D', 'A')";

                    dbHandler.insertaPartidas(insertaPartida);

                }


                ///fin se declaran las variables para las partidas

                toast = Toast.makeText(getApplicationContext(), "Se finaliza el guardado de pedidos Local", Toast.LENGTH_SHORT);
                toast.show();

                reload();

                if(CheckNetwork.isInternetAvailable(CapturaBonificacion.this)) //returns true if internet available
                {
                    new CapturaBonificacion.JsonTaskSender(dbHandler).execute(); // Envia todos los pedidos pendientes x transmitir al sevidor. No los borra local
                }

            }

        });//fin del metodo guardar el pedido

    }

    public void reload(){

        final MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        dbHandler.checkDBStatus(); // Tiene como fin forzar la creacion de la base de datos.

            String usuario = dbHandler.ultimoUsuarioRegistrado();

            Intent explicit_intent;
            explicit_intent = new Intent(CapturaBonificacion.this, CapturaPedidos.class);
            explicit_intent.putExtra("usuario", usuario);
            startActivity(explicit_intent);
            return;

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

            pdSender = new ProgressDialog(CapturaBonificacion.this);
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

}
