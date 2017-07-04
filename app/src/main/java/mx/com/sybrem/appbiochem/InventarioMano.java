package mx.com.sybrem.appbiochem;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TabHost;

public class InventarioMano extends AppCompatActivity {

    ListView listadoSeguimiento;
    private String[] inventario;
    Button btn_Cerrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);

        setContentView(R.layout.activity_inventario_mano);
        Resources res = getResources();

        TabHost tabs=(TabHost)findViewById(android.R.id.tabhost);
        tabs.setup();

        TabHost.TabSpec spec = tabs.newTabSpec("mitab1");

        spec.setContent(R.id.tab1);
        spec.setIndicator("Inventario de Mano", //si se quita lo que esta en comillas aparecera un icono el que se selecciono
                res.getDrawable(android.R.drawable.ic_btn_speak_now));
        tabs.addTab(spec);

        tabs.setCurrentTab(0);

        inventario = dbHandler.getInventarioMano();
        final ListView listadoInventarioMano = (ListView)findViewById(R.id.LstInventarioMano);
        ArrayAdapter adaptadorInventario = new ArrayAdapter(this, android.R.layout.simple_list_item_1, inventario);
        listadoInventarioMano.setAdapter(adaptadorInventario);

        Button btnSalir = (Button)findViewById(R.id.btnPedidosSalir);
        btnSalir.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                final MyDBHandler dbHandler = new MyDBHandler(InventarioMano.this, null, null, 1);

                String usuario = dbHandler.ultimoUsuarioRegistrado();

                Intent explicit_intent;
                explicit_intent = new Intent(InventarioMano.this, NavDrawerActivity.class);
                explicit_intent.putExtra("usuario", usuario);
                startActivity(explicit_intent);
                return;
            }
        });

    }
}
