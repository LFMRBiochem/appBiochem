package mx.com.sybrem.appbiochem;

import android.animation.Animator;
import android.app.Dialog;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class ListaOfertas extends AppCompatActivity {


    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_ofertas);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Biochem");
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            fab.setScaleX(0);
            fab.setScaleY(0);
            final Interpolator interpolador = AnimationUtils.loadInterpolator(getBaseContext(),
                    android.R.interpolator.fast_out_slow_in);

            fab.animate()
                    .scaleX(1)
                    .scaleY(1)
                    .setInterpolator(interpolador)
                    .setDuration(600)
                    .setStartDelay(400)
                    .setListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            fab.animate()
                                    .scaleY(1)
                                    .scaleX(1)
                                    .setInterpolator(interpolador)
                                    .setDuration(600)
                                    .start();
                        }
                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }
                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });
        }else{
            Animation an;
            an=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_open);
            fab.startAnimation(an);
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final MyDBHandler dbHandler = new MyDBHandler(ListaOfertas.this, null, null, 1);
                dbHandler.checkDBStatus(); // Tiene como fin forzar la creacion de la base de datos.

                String usuario = dbHandler.ultimoUsuarioRegistrado();

                Intent explicit_intent;
                explicit_intent = new Intent(ListaOfertas.this, NavDrawerActivity.class);
                explicit_intent.putExtra("usuario", usuario);
                startActivity(explicit_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                finish();
                return;
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lista_ofertas, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            //return true;
            final MyDBHandler dbHandler = new MyDBHandler(ListaOfertas.this, null, null, 1);
            dbHandler.checkDBStatus(); // Tiene como fin forzar la creacion de la base de datos.

            String usuario = dbHandler.ultimoUsuarioRegistrado();

            Intent explicit_intent;
            explicit_intent = new Intent(ListaOfertas.this, NavDrawerActivity.class);
            explicit_intent.putExtra("usuario", usuario);
            startActivity(explicit_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
            finish();
            //startActivity(explicit_intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static class OAgricola extends Fragment {

        ListView lv;
        ArrayAdapter<String> adapter;
        private String[] ofertasAgricola;
        private String[] desgloce;

        Button btn_Cerrar;

        private static final String ARG_SECTION_NUMBER = "section_number";

        public OAgricola() {
        }

        public static OAgricola newInstance(int sectionNumber) {
            OAgricola fragment = new OAgricola();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_lista_ofertas, container, false);
            cargaView(rootView);
            cargaLista();
            return rootView;
        }
        private void cargaView(View rootview){

            lv=(ListView)rootview.findViewById(R.id.LstOfertasAg);

        }
        private void cargaLista(){
            final MyDBHandler db=new MyDBHandler(getActivity(),null,null,1);
            db.checkDBStatus();
            ofertasAgricola = db.getListadoOfertasAgricola();
            adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,ofertasAgricola);
            lv.setAdapter(adapter);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                public void onItemClick(AdapterView<?> a, View v, int position, long id){
                    int posicion = position;
                    String itemValue = (String)lv.getItemAtPosition(posicion);

                    String[] separated = itemValue.split("-");
                    String cve_producto = "";
                    cve_producto = separated[0];

                    desgloce = db.getListadoOfertasAgricolaDesgloce(cve_producto);

                    final Dialog dia=new Dialog(getActivity());
                    dia.getWindow().getAttributes().windowAnimations=R.style.PauseDialogAnimation;
                    dia.setTitle(itemValue);
                    dia.setContentView(R.layout.popup);

                    ListView listadoDesgloce = (ListView)dia.findViewById(R.id.LstDesgloce);
                    ArrayAdapter adaptadorDesgloce = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, desgloce);
                    listadoDesgloce.setAdapter(adaptadorDesgloce);

                    btn_Cerrar = (Button)dia.findViewById(R.id.id_cerrar);
                    btn_Cerrar.setOnClickListener(new Button.OnClickListener(){

                        @Override
                        public void onClick(View v) {
                            dia.dismiss();
                        }});
                    dia.show();
                }
            });
        }
    }

    public static class OVeterinaria extends Fragment {

        ListView lv;
        ArrayAdapter<String> adapter;
        private String[] ofertasVeterinaria;
        private String[] desgloce;
        Button btn_Cerrar;

        private static final String ARG_SECTION_NUMBER = "section_number";

        public OVeterinaria() {
        }
        public static OVeterinaria newInstance(int sectionNumber) {
            OVeterinaria fragment = new OVeterinaria();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_lista_ofertas_veterinaria, container, false);
            cargaView(rootView);
            cargaLista();
            return rootView;
        }
        private void cargaView(View rootview){

            lv=(ListView)rootview.findViewById(R.id.LstOfertasVet);

        }
        private void cargaLista(){
            final MyDBHandler db=new MyDBHandler(getActivity(),null,null,1);
            db.checkDBStatus();
            ofertasVeterinaria = db.getListadoOfertasVeterinaria();
            adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,ofertasVeterinaria);
            lv.setAdapter(adapter);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                public void onItemClick(AdapterView<?> a, View v, int position, long id){
                    int posicion = position;
                    String itemValue = (String)lv.getItemAtPosition(posicion);

                    String[] separated = itemValue.split("-");
                    String cve_producto = "";
                    cve_producto = separated[0];

                    desgloce = db.getListadoOfertasVeterinariaDesgloce(cve_producto);

                    final Dialog dia=new Dialog(getActivity());
                    dia.getWindow().getAttributes().windowAnimations=R.style.PauseDialogAnimation;
                    dia.setTitle(itemValue);
                    dia.setContentView(R.layout.popup);

                    ListView listadoDesgloce = (ListView)dia.findViewById(R.id.LstDesgloce);
                    ArrayAdapter adaptadorDesgloce = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, desgloce);
                    listadoDesgloce.setAdapter(adaptadorDesgloce);

                    btn_Cerrar = (Button)dia.findViewById(R.id.id_cerrar);
                    btn_Cerrar.setOnClickListener(new Button.OnClickListener(){

                        @Override
                        public void onClick(View v) {
                            dia.dismiss();
                        }});
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
                return OAgricola.newInstance(position + 1);
            }
            else{
                return OVeterinaria.newInstance(position + 1);
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
