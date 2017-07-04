package mx.com.sybrem.appbiochem;

import android.animation.Animator;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.github.snowdream.android.widget.SmartImageView;

public class ListaProductos extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_productos);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Biochem");
        setSupportActionBar(toolbar);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lista_productos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            final MyDBHandler dbHandler = new MyDBHandler(ListaProductos.this, null, null, 1);
            dbHandler.checkDBStatus(); // Tiene como fin forzar la creacion de la base de datos.

            String usuario = dbHandler.ultimoUsuarioRegistrado();

            Intent explicit_intent;
            explicit_intent = new Intent(ListaProductos.this, NavDrawerActivity.class);
            explicit_intent.putExtra("usuario", usuario);
            startActivity(explicit_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static class Agricola extends Fragment {

        ListView lv;
        ArrayAdapter<String> adapter;
        private String[] productosAgricola;
        private String[] desgloce;

        Button btn_Cerrar, btn_InfoBasica,btn_InfoComercial,btn_InfoCompleta,btn_salirts;
        Button btn_Intro, btn_Ventajas, btn_Indicaciones;

        private static final String ARG_SECTION_NUMBER = "section_number";

        public Agricola() {
        }

        public static Agricola newInstance(int sectionNumber) {
            Agricola fragment = new Agricola();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_lista_productos, container, false);
            cargaView(rootView);
            cargaLista();
            return rootView;
        }
        private void cargaView(View rootview){

            lv=(ListView)rootview.findViewById(R.id.LstProductosAgricola2);
            btn_salirts=(Button)rootview.findViewById(R.id.btnSalirts);
            btn_salirts.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final MyDBHandler db=new MyDBHandler(getActivity(),null,null,1);
                    db.checkDBStatus();
                    String usuario = db.ultimoUsuarioRegistrado();
                    Intent explicit_intent;
                    explicit_intent = new Intent(getContext(), NavDrawerActivity.class);
                    explicit_intent.putExtra("usuario", usuario);
                    startActivity(explicit_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                    return;
                }
            });

        }
        private void cargaLista(){
            final MyDBHandler db=new MyDBHandler(getActivity(),null,null,1);
            db.checkDBStatus();
            productosAgricola=db.getListadoProductosAgricola();
            adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,productosAgricola);
            lv.setAdapter(adapter);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                public void onItemClick(AdapterView<?> a, View v, int position, long id){
                    int posicion = position;
                    String itemValue = (String)lv.getItemAtPosition(posicion);

                    String[] separated = itemValue.split("-");
                    String cve_producto = "";
                    cve_producto = separated[0];

                    final String cve_producto2=cve_producto;

                    desgloce = db.getListadoProductosDesgloce(cve_producto);

                    final Dialog dia=new Dialog(getActivity());
                    dia.getWindow().getAttributes().windowAnimations=R.style.PauseDialogAnimation;
                    dia.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dia.setContentView(R.layout.popup_provisional);

                    int geto=getActivity().getResources().getConfiguration().orientation;
                    int width=0, height=0;

                    if(geto==Configuration.ORIENTATION_LANDSCAPE){
                        width = (int)(getResources().getDisplayMetrics().widthPixels*0.82);
                        height = (int)(getResources().getDisplayMetrics().heightPixels*0.72);
                    }else{
                        width = (int)(getResources().getDisplayMetrics().widthPixels*0.82);
                        height = (int)(getResources().getDisplayMetrics().heightPixels*0.54);
                    }

                    dia.getWindow().setLayout(width, height);

                    SmartImageView sv=(SmartImageView)dia.findViewById(R.id.img_producto);
                    String g=db.getRutaImagenProductos(cve_producto);

                    if(g==null || g.isEmpty()){
                        Rect rect2=new Rect(sv.getLeft(),sv.getTop(),sv.getRight(),sv.getBottom());
                        sv.setImageUrl("",rect2,R.drawable.logo_biochem);
                    }
                    else {
                        String url=db.getImagenProductos(g);
                        Rect rect=new Rect(sv.getLeft(),sv.getTop(),sv.getRight(),sv.getBottom());
                        sv.setImageUrl(url,rect);
                    }

                    String Nom_Producto=separated[1];
                    TextView tv=(TextView)dia.findViewById(R.id.titulo_producto);
                    tv.setText(Nom_Producto);

                    /*ListView listadoDesgloce = (ListView)dia.findViewById(R.id.LstDesgloce);
                    ArrayAdapter adaptadorDesgloce = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, desgloce);
                    listadoDesgloce.setAdapter(adaptadorDesgloce);*/
                    TextView tvPrecio=(TextView)dia.findViewById(R.id.tvPrecio);
                    TextView tvPiezas=(TextView)dia.findViewById(R.id.tvPiezas);
                    TextView tvUnidades=(TextView)dia.findViewById(R.id.tvUnidad);

                    String tvPrice=desgloce[0];
                    String tvPieces=desgloce[1];
                    String tvUnities=desgloce[2];

                    tvPrecio.setText(tvPrice);
                    tvPiezas.setText(tvPieces);
                    tvUnidades.setText(tvUnities);

                    btn_Cerrar = (Button)dia.findViewById(R.id.id_cerrar);
                    btn_Cerrar.setOnClickListener(new Button.OnClickListener(){

                        @Override
                        public void onClick(View v) {
                            dia.dismiss();
                        }});

                    btn_InfoBasica=(Button)dia.findViewById(R.id.inf_tecnica);
                    btn_InfoBasica.setOnClickListener(new Button.OnClickListener(){
                        @Override
                        public void onClick(View v) {
                            final Dialog dia2=new Dialog(getActivity());
                            dia2.getWindow().getAttributes().windowAnimations=R.style.PauseDialogAnimation;
                            //dia2.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dia2.setTitle("Información Básica");
                            dia2.setContentView(R.layout.popup);
                            int width = (int)(getResources().getDisplayMetrics().widthPixels*0.75);
                            int height = (int)(getResources().getDisplayMetrics().heightPixels*0.60);
                            dia2.getWindow().setLayout(width, height);

                            String [] desgloce2 = db.getInfoBasica(cve_producto2);
                            ListView listadoDesgloce = (ListView)dia2.findViewById(R.id.LstDesgloce);
                            ArrayAdapter adaptadorDesgloce = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, desgloce2);
                            listadoDesgloce.setAdapter(adaptadorDesgloce);

                            btn_Cerrar = (Button)dia2.findViewById(R.id.id_cerrar);
                            btn_Cerrar.setOnClickListener(new Button.OnClickListener(){
                                @Override
                                public void onClick(View v) {
                                    dia2.dismiss();
                                }});

                            dia2.show();
                        }
                    });
                    btn_InfoComercial=(Button)dia.findViewById(R.id.inf_comercial);
                    btn_InfoComercial.setOnClickListener(new Button.OnClickListener(){
                        @Override
                        public void onClick(View v) {
                            final Dialog dia2=new Dialog(getActivity());
                            dia2.getWindow().getAttributes().windowAnimations=R.style.PauseDialogAnimation;
                            //dia2.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dia2.setTitle("Información Comercial");
                            dia2.setContentView(R.layout.popup_productos);
                            int width = (int)(getResources().getDisplayMetrics().widthPixels*0.75);
                            int height = (int)(getResources().getDisplayMetrics().heightPixels*0.60);
                            dia2.getWindow().setLayout(width, height);

                            btn_Intro=(Button)dia2.findViewById(R.id.prod_intro);
                            btn_Intro.setOnClickListener(new Button.OnClickListener(){
                                @Override
                                public void onClick(View v) {
                                    final Dialog dia3=new Dialog(getActivity());
                                    dia3.getWindow().getAttributes().windowAnimations=R.style.PauseDialogAnimation;
                                    dia3.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                    dia3.setContentView(R.layout.popup);
                                    int width = (int)(getResources().getDisplayMetrics().widthPixels*0.75);
                                    int height = (int)(getResources().getDisplayMetrics().heightPixels*0.60);
                                    dia3.getWindow().setLayout(width, height);

                                    String [] desgloce2 = db.getIntroProductos(cve_producto2);
                                    ListView listadoDesgloce = (ListView)dia3.findViewById(R.id.LstDesgloce);
                                    ArrayAdapter adaptadorDesgloce = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, desgloce2);
                                    listadoDesgloce.setAdapter(adaptadorDesgloce);

                                    btn_Cerrar = (Button)dia3.findViewById(R.id.id_cerrar);
                                    btn_Cerrar.setOnClickListener(new Button.OnClickListener(){
                                        @Override
                                        public void onClick(View v) {
                                            dia3.dismiss();
                                        }});
                                    dia3.show();
                                }
                            });

                            btn_Ventajas=(Button)dia2.findViewById(R.id.prod_ventajas);
                            btn_Ventajas.setOnClickListener(new Button.OnClickListener(){
                                @Override
                                public void onClick(View v) {
                                    final Dialog dia3=new Dialog(getActivity());
                                    dia3.getWindow().getAttributes().windowAnimations=R.style.PauseDialogAnimation;
                                    dia3.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                    dia3.setContentView(R.layout.popup);
                                    int width = (int)(getResources().getDisplayMetrics().widthPixels*0.75);
                                    int height = (int)(getResources().getDisplayMetrics().heightPixels*0.60);
                                    dia3.getWindow().setLayout(width, height);

                                    String [] desgloce2 = db.getVentajasProductos(cve_producto2);
                                    ListView listadoDesgloce = (ListView)dia3.findViewById(R.id.LstDesgloce);
                                    ArrayAdapter adaptadorDesgloce = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, desgloce2);
                                    listadoDesgloce.setAdapter(adaptadorDesgloce);

                                    btn_Cerrar = (Button)dia3.findViewById(R.id.id_cerrar);
                                    btn_Cerrar.setOnClickListener(new Button.OnClickListener(){
                                        @Override
                                        public void onClick(View v) {
                                            dia3.dismiss();
                                        }});
                                    dia3.show();
                                }
                            });

                            btn_Indicaciones=(Button)dia2.findViewById(R.id.prod_indicaciones);
                            btn_Indicaciones.setOnClickListener(new Button.OnClickListener(){
                                @Override
                                public void onClick(View v) {
                                    final Dialog dia3=new Dialog(getActivity());
                                    dia3.getWindow().getAttributes().windowAnimations=R.style.PauseDialogAnimation;
                                    dia3.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                    dia3.setContentView(R.layout.popup);
                                    int width = (int)(getResources().getDisplayMetrics().widthPixels*0.75);
                                    int height = (int)(getResources().getDisplayMetrics().heightPixels*0.60);
                                    dia3.getWindow().setLayout(width, height);

                                    String [] desgloce2 = db.getIndicacionesProductos(cve_producto2);
                                    ListView listadoDesgloce = (ListView)dia3.findViewById(R.id.LstDesgloce);
                                    ArrayAdapter adaptadorDesgloce = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, desgloce2);
                                    listadoDesgloce.setAdapter(adaptadorDesgloce);

                                    btn_Cerrar = (Button)dia3.findViewById(R.id.id_cerrar);
                                    btn_Cerrar.setOnClickListener(new Button.OnClickListener(){
                                        @Override
                                        public void onClick(View v) {
                                            dia3.dismiss();
                                        }});
                                    dia3.show();
                                }
                            });


                            /*String [] desgloce2 = db.getInfoComercial(cve_producto2);
                            ListView listadoDesgloce = (ListView)dia2.findViewById(R.id.LstDesgloce);
                            ArrayAdapter adaptadorDesgloce = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, desgloce2);
                            listadoDesgloce.setAdapter(adaptadorDesgloce);*/

                            btn_Cerrar = (Button)dia2.findViewById(R.id.id_cerrar);
                            btn_Cerrar.setOnClickListener(new Button.OnClickListener(){
                                @Override
                                public void onClick(View v) {
                                    dia2.dismiss();
                                }});

                            dia2.show();
                        }
                    });
                    btn_InfoCompleta=(Button)dia.findViewById(R.id.inf_completa);
                    btn_InfoCompleta.setOnClickListener(new Button.OnClickListener(){
                        @Override
                        public void onClick(View v) {
                            final Dialog dia2=new Dialog(getActivity());
                            dia2.getWindow().getAttributes().windowAnimations=R.style.PauseDialogAnimation;
                            //dia2.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dia2.setTitle("Información Técnica Completa");
                            dia2.setContentView(R.layout.popup);
                            int width = (int)(getResources().getDisplayMetrics().widthPixels*0.75);
                            int height = (int)(getResources().getDisplayMetrics().heightPixels*0.60);
                            dia2.getWindow().setLayout(width, height);

                            String [] desgloce2 = db.getInfoCompleta(cve_producto2);
                            ListView listadoDesgloce = (ListView)dia2.findViewById(R.id.LstDesgloce);
                            ArrayAdapter adaptadorDesgloce = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, desgloce2);
                            listadoDesgloce.setAdapter(adaptadorDesgloce);

                            btn_Cerrar = (Button)dia2.findViewById(R.id.id_cerrar);
                            btn_Cerrar.setOnClickListener(new Button.OnClickListener(){
                                @Override
                                public void onClick(View v) {
                                    dia2.dismiss();
                                }});

                            dia2.show();
                        }
                    });

                    dia.show();
                }
            });
        }
    }
    public static class Veterinaria extends Fragment {

        ListView lv;
        ArrayAdapter<String> adapter;
        private String[] productosVeterinaria;
        private String[] desgloce;

        Button btn_Cerrar, btn_InfoBasica,btn_InfoComercial,btn_InfoCompleta;
        Button btn_Intro, btn_Ventajas, btn_Indicaciones,btn_salirts;

        private static final String ARG_SECTION_NUMBER = "section_number";

        public Veterinaria() {
        }

        public static Veterinaria newInstance(int sectionNumber) {
            Veterinaria fragment = new Veterinaria();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_lista_productos_veterinaria, container, false);
            cargaView(rootView);
            cargaLista();
            return rootView;
        }
        private void cargaView(View rootview){

            lv=(ListView)rootview.findViewById(R.id.LstProductosVeterinaria2);
            btn_salirts=(Button)rootview.findViewById(R.id.btnSalirts);
            btn_salirts.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final MyDBHandler db=new MyDBHandler(getActivity(),null,null,1);
                    db.checkDBStatus();
                    String usuario = db.ultimoUsuarioRegistrado();
                    Intent explicit_intent;
                    explicit_intent = new Intent(getContext(), NavDrawerActivity.class);
                    explicit_intent.putExtra("usuario", usuario);
                    startActivity(explicit_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                    return;
                }
            });

        }
        private void cargaLista(){
            final MyDBHandler db=new MyDBHandler(getActivity(),null,null,1);
            db.checkDBStatus();
            productosVeterinaria = db.getListadoProductosVeterinaria();
            adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,productosVeterinaria);
            lv.setAdapter(adapter);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                public void onItemClick(AdapterView<?> a, View v, int position, long id){
                    int posicion = position;
                    String itemValue = (String)lv.getItemAtPosition(posicion);

                    String[] separated = itemValue.split("-");
                    String cve_producto = "";
                    cve_producto = separated[0];
                    String Nom_Producto=separated[1];

                    final String cve_producto2=cve_producto;

                    desgloce = db.getListadoProductosDesgloce(cve_producto);

                    final Dialog dia=new Dialog(getActivity());
                    dia.getWindow().getAttributes().windowAnimations=R.style.PauseDialogAnimation;
                    dia.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dia.setContentView(R.layout.popup_provisional);

                    int geto=getActivity().getResources().getConfiguration().orientation;
                    int width=0, height=0;

                    if(geto==Configuration.ORIENTATION_LANDSCAPE){
                        width = (int)(getResources().getDisplayMetrics().widthPixels*0.82);
                        height = (int)(getResources().getDisplayMetrics().heightPixels*0.72);
                    }else{
                        width = (int)(getResources().getDisplayMetrics().widthPixels*0.82);
                        height = (int)(getResources().getDisplayMetrics().heightPixels*0.54);
                    }

                    dia.getWindow().setLayout(width, height);

                    SmartImageView sv=(SmartImageView)dia.findViewById(R.id.img_producto);
                    String g=db.getRutaImagenProductos(cve_producto);

                    if(g==null || g.isEmpty()){
                        Rect rect2=new Rect(sv.getLeft(),sv.getTop(),sv.getRight(),sv.getBottom());
                        sv.setImageUrl("",rect2,R.drawable.logo_biochem);
                    }
                    else {
                        String url=db.getImagenProductos(g);
                        Rect rect=new Rect(sv.getLeft(),sv.getTop(),sv.getRight(),sv.getBottom());
                        sv.setImageUrl(url,rect);
                    }
                    TextView tv=(TextView)dia.findViewById(R.id.titulo_producto);
                    tv.setText(Nom_Producto);

                    /*ListView listadoDesgloce = (ListView)dia.findViewById(R.id.LstDesgloce);
                    ArrayAdapter adaptadorDesgloce = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, desgloce);
                    listadoDesgloce.setAdapter(adaptadorDesgloce);*/
                    TextView tvPrecio=(TextView)dia.findViewById(R.id.tvPrecio);
                    TextView tvPiezas=(TextView)dia.findViewById(R.id.tvPiezas);
                    TextView tvUnidades=(TextView)dia.findViewById(R.id.tvUnidad);

                    String tvPrice=desgloce[0];
                    String tvPieces=desgloce[1];
                    String tvUnities=desgloce[2];

                    tvPrecio.setText(tvPrice);
                    tvPiezas.setText(tvPieces);
                    tvUnidades.setText(tvUnities);

                    btn_Cerrar = (Button)dia.findViewById(R.id.id_cerrar);
                    btn_Cerrar.setOnClickListener(new Button.OnClickListener(){

                        @Override
                        public void onClick(View v) {
                            dia.dismiss();
                        }});

                    btn_InfoBasica=(Button)dia.findViewById(R.id.inf_tecnica);
                    btn_InfoBasica.setOnClickListener(new Button.OnClickListener(){
                        @Override
                        public void onClick(View v) {
                            final Dialog dia2=new Dialog(getActivity());
                            dia2.getWindow().getAttributes().windowAnimations=R.style.PauseDialogAnimation;
                            //dia2.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dia2.setTitle("Información Básica");
                            dia2.setContentView(R.layout.popup);
                            int width = (int)(getResources().getDisplayMetrics().widthPixels*0.75);
                            int height = (int)(getResources().getDisplayMetrics().heightPixels*0.60);
                            dia2.getWindow().setLayout(width, height);

                            String [] desgloce2 = db.getInfoBasica(cve_producto2);
                            ListView listadoDesgloce = (ListView)dia2.findViewById(R.id.LstDesgloce);
                            ArrayAdapter adaptadorDesgloce = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, desgloce2);
                            listadoDesgloce.setAdapter(adaptadorDesgloce);

                            btn_Cerrar = (Button)dia2.findViewById(R.id.id_cerrar);
                            btn_Cerrar.setOnClickListener(new Button.OnClickListener(){
                                @Override
                                public void onClick(View v) {
                                    dia2.dismiss();
                                }});

                            dia2.show();
                        }
                    });
                    btn_InfoComercial=(Button)dia.findViewById(R.id.inf_comercial);
                    btn_InfoComercial.setOnClickListener(new Button.OnClickListener(){
                        @Override
                        public void onClick(View v) {
                            final Dialog dia2=new Dialog(getActivity());
                            dia2.getWindow().getAttributes().windowAnimations=R.style.PauseDialogAnimation;
                            //dia2.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dia2.setTitle("Información Comercial");
                            dia2.setContentView(R.layout.popup_productos);
                            int width = (int)(getResources().getDisplayMetrics().widthPixels*0.75);
                            int height = (int)(getResources().getDisplayMetrics().heightPixels*0.60);
                            dia2.getWindow().setLayout(width, height);

                            btn_Intro=(Button)dia2.findViewById(R.id.prod_intro);
                            btn_Intro.setOnClickListener(new Button.OnClickListener(){
                                @Override
                                public void onClick(View v) {
                                    final Dialog dia3=new Dialog(getActivity());
                                    dia3.getWindow().getAttributes().windowAnimations=R.style.PauseDialogAnimation;
                                    dia3.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                    dia3.setContentView(R.layout.popup);
                                    int width = (int)(getResources().getDisplayMetrics().widthPixels*0.75);
                                    int height = (int)(getResources().getDisplayMetrics().heightPixels*0.60);
                                    dia3.getWindow().setLayout(width, height);

                                    String [] desgloce2 = db.getIntroProductos(cve_producto2);
                                    ListView listadoDesgloce = (ListView)dia3.findViewById(R.id.LstDesgloce);
                                    ArrayAdapter adaptadorDesgloce = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, desgloce2);
                                    listadoDesgloce.setAdapter(adaptadorDesgloce);

                                    btn_Cerrar = (Button)dia3.findViewById(R.id.id_cerrar);
                                    btn_Cerrar.setOnClickListener(new Button.OnClickListener(){
                                        @Override
                                        public void onClick(View v) {
                                            dia3.dismiss();
                                        }});
                                    dia3.show();
                                }
                            });

                            btn_Ventajas=(Button)dia2.findViewById(R.id.prod_ventajas);
                            btn_Ventajas.setOnClickListener(new Button.OnClickListener(){
                                @Override
                                public void onClick(View v) {
                                    final Dialog dia3=new Dialog(getActivity());
                                    dia3.getWindow().getAttributes().windowAnimations=R.style.PauseDialogAnimation;
                                    dia3.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                    dia3.setContentView(R.layout.popup);
                                    int width = (int)(getResources().getDisplayMetrics().widthPixels*0.75);
                                    int height = (int)(getResources().getDisplayMetrics().heightPixels*0.60);
                                    dia3.getWindow().setLayout(width, height);

                                    String [] desgloce2 = db.getVentajasProductos(cve_producto2);
                                    ListView listadoDesgloce = (ListView)dia3.findViewById(R.id.LstDesgloce);
                                    ArrayAdapter adaptadorDesgloce = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, desgloce2);
                                    listadoDesgloce.setAdapter(adaptadorDesgloce);

                                    btn_Cerrar = (Button)dia3.findViewById(R.id.id_cerrar);
                                    btn_Cerrar.setOnClickListener(new Button.OnClickListener(){
                                        @Override
                                        public void onClick(View v) {
                                            dia3.dismiss();
                                        }});
                                    dia3.show();
                                }
                            });

                            btn_Indicaciones=(Button)dia2.findViewById(R.id.prod_indicaciones);
                            btn_Indicaciones.setOnClickListener(new Button.OnClickListener(){
                                @Override
                                public void onClick(View v) {
                                    final Dialog dia3=new Dialog(getActivity());
                                    dia3.getWindow().getAttributes().windowAnimations=R.style.PauseDialogAnimation;
                                    dia3.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                    dia3.setContentView(R.layout.popup);
                                    int width = (int)(getResources().getDisplayMetrics().widthPixels*0.75);
                                    int height = (int)(getResources().getDisplayMetrics().heightPixels*0.60);
                                    dia3.getWindow().setLayout(width, height);

                                    String [] desgloce2 = db.getIndicacionesProductos(cve_producto2);
                                    ListView listadoDesgloce = (ListView)dia3.findViewById(R.id.LstDesgloce);
                                    ArrayAdapter adaptadorDesgloce = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, desgloce2);
                                    listadoDesgloce.setAdapter(adaptadorDesgloce);

                                    btn_Cerrar = (Button)dia3.findViewById(R.id.id_cerrar);
                                    btn_Cerrar.setOnClickListener(new Button.OnClickListener(){
                                        @Override
                                        public void onClick(View v) {
                                            dia3.dismiss();
                                        }});
                                    dia3.show();
                                }
                            });

                            /*String [] desgloce2 = db.getInfoComercial(cve_producto2);
                            ListView listadoDesgloce = (ListView)dia2.findViewById(R.id.LstDesgloce);
                            ArrayAdapter adaptadorDesgloce = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, desgloce2);
                            listadoDesgloce.setAdapter(adaptadorDesgloce);*/

                            btn_Cerrar = (Button)dia2.findViewById(R.id.id_cerrar);
                            btn_Cerrar.setOnClickListener(new Button.OnClickListener(){
                                @Override
                                public void onClick(View v) {
                                    dia2.dismiss();
                                }});

                            dia2.show();
                        }
                    });
                    btn_InfoCompleta=(Button)dia.findViewById(R.id.inf_completa);
                    btn_InfoCompleta.setOnClickListener(new Button.OnClickListener(){
                        @Override
                        public void onClick(View v) {
                            final Dialog dia2=new Dialog(getActivity());
                            dia2.getWindow().getAttributes().windowAnimations=R.style.PauseDialogAnimation;
                            //dia2.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dia2.setTitle("Información Técnica Completa");
                            dia2.setContentView(R.layout.popup);
                            int width = (int)(getResources().getDisplayMetrics().widthPixels*0.75);
                            int height = (int)(getResources().getDisplayMetrics().heightPixels*0.60);
                            dia2.getWindow().setLayout(width, height);

                            String [] desgloce2 = db.getInfoCompleta(cve_producto2);
                            ListView listadoDesgloce = (ListView)dia2.findViewById(R.id.LstDesgloce);
                            ArrayAdapter adaptadorDesgloce = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, desgloce2);
                            listadoDesgloce.setAdapter(adaptadorDesgloce);

                            btn_Cerrar = (Button)dia2.findViewById(R.id.id_cerrar);
                            btn_Cerrar.setOnClickListener(new Button.OnClickListener(){
                                @Override
                                public void onClick(View v) {
                                    dia2.dismiss();
                                }});

                            dia2.show();
                        }
                    });

                    dia.show();
                }
            });
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if(position==0){
                return Agricola.newInstance(position + 1);
            }
            else {
                return Veterinaria.newInstance(position + 1);
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Agrícola";
                case 1:
                    return "Veterinaria";
            }
            return null;
        }
    }
}
