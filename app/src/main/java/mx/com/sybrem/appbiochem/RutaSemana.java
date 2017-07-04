package mx.com.sybrem.appbiochem;

import android.animation.Animator;
import android.content.Intent;
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

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class RutaSemana extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ruta_semana);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Biochem");
        setSupportActionBar(toolbar);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

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
                final MyDBHandler dbHandler = new MyDBHandler(RutaSemana.this, null, null, 1);

                String usuario = dbHandler.ultimoUsuarioRegistrado();

                Intent explicit_intent;
                explicit_intent = new Intent(RutaSemana.this, NavDrawerActivity.class);
                explicit_intent.putExtra("usuario", usuario);
                startActivity(explicit_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                finish();
                return;
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_ruta_semana, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            final MyDBHandler dbHandler = new MyDBHandler(RutaSemana.this, null, null, 1);

            String usuario = dbHandler.ultimoUsuarioRegistrado();

            Intent explicit_intent;
            explicit_intent = new Intent(RutaSemana.this, NavDrawerActivity.class);
            explicit_intent.putExtra("usuario", usuario);
            startActivity(explicit_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static class Dia1 extends Fragment {

        ListView lv;
        ArrayAdapter<String> adapter;
        private String[] ruta;
        Integer dia = 1;


        Button btn_Cerrar;

        private static final String ARG_SECTION_NUMBER = "section_number";

        public Dia1() {
        }

        public static Dia1 newInstance(int sectionNumber) {
            Dia1 fragment = new Dia1();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_ruta_semana, container, false);
            cargaView(rootView);
            cargaLista();
            return rootView;
        }
        private void cargaView(View rootview){

            lv=(ListView)rootview.findViewById(R.id.LstDia1);

        }
        private void cargaLista(){
            final MyDBHandler db=new MyDBHandler(getActivity(),null,null,1);
            dia=1;
            String fecha_ruta = db.getFechaRuta(dia);
            ruta = db.getRutaSemanal(dia);
            if(ruta==null || ruta.length==0){
                ruta=new String[]{"Sin datos"};
            }
            adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,ruta);
            lv.setAdapter(adapter);

        }
    }
    //DÍA 2
    public static class Dia2 extends Fragment {

        ListView lv;
        ArrayAdapter<String> adapter;
        private String[] ruta;
        Integer dia = 1;


        Button btn_Cerrar;

        private static final String ARG_SECTION_NUMBER = "section_number";

        public Dia2() {
        }

        public static Dia2 newInstance(int sectionNumber) {
            Dia2 fragment = new Dia2();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_ruta_semana2, container, false);
            cargaView(rootView);
            cargaLista();
            return rootView;
        }
        private void cargaView(View rootview){

            lv=(ListView)rootview.findViewById(R.id.LstDia2);

        }
        private void cargaLista(){
            final MyDBHandler db=new MyDBHandler(getActivity(),null,null,1);
            dia=2;
            String fecha_ruta = db.getFechaRuta(dia);
            ruta = db.getRutaSemanal(dia);
            if(ruta==null || ruta.length==0){
                ruta=new String[]{"Sin datos"};
            }
            adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,ruta);
            lv.setAdapter(adapter);

        }
    }
    //DÍA 3
    public static class Dia3 extends Fragment {

        ListView lv;
        ArrayAdapter<String> adapter;
        private String[] ruta;
        //private String[] desgloce;
        Integer dia = 1;


        Button btn_Cerrar;

        private static final String ARG_SECTION_NUMBER = "section_number";

        public Dia3() {
        }
        public static Dia3 newInstance(int sectionNumber) {
            Dia3 fragment = new Dia3();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_ruta_semana3, container, false);
            cargaView(rootView);
            cargaLista();
            return rootView;
        }
        private void cargaView(View rootview){

            lv=(ListView)rootview.findViewById(R.id.LstDia3);

        }
        private void cargaLista(){
            final MyDBHandler db=new MyDBHandler(getActivity(),null,null,1);
            //db.checkDBStatus();
            dia=3;
            String fecha_ruta = db.getFechaRuta(dia);
            ruta = db.getRutaSemanal(dia);
            if(ruta==null || ruta.length==0){
                ruta=new String[]{"Sin datos"};
            }
            adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,ruta);
            lv.setAdapter(adapter);

        }
    }
    //DÍA 4
    public static class Dia4 extends Fragment {

        ListView lv;
        ArrayAdapter<String> adapter;
        private String[] ruta;
        Integer dia = 1;


        Button btn_Cerrar;

        private static final String ARG_SECTION_NUMBER = "section_number";

        public Dia4() {
        }

        public static Dia4 newInstance(int sectionNumber) {
            Dia4 fragment = new Dia4();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_ruta_semana4, container, false);
            cargaView(rootView);
            cargaLista();
            return rootView;
        }
        private void cargaView(View rootview){

            lv=(ListView)rootview.findViewById(R.id.LstDia4);

        }
        private void cargaLista(){
            final MyDBHandler db=new MyDBHandler(getActivity(),null,null,1);
            dia=4;
            String fecha_ruta = db.getFechaRuta(dia);
            ruta = db.getRutaSemanal(dia);
            if(ruta==null || ruta.length==0){
                ruta=new String[]{"Sin datos"};
            }
            adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,ruta);
            lv.setAdapter(adapter);

        }
    }
    //DÍA 5
    public static class Dia5 extends Fragment {

        ListView lv;
        ArrayAdapter<String> adapter;
        private String[] ruta;
        Integer dia = 1;


        Button btn_Cerrar;

        private static final String ARG_SECTION_NUMBER = "section_number";

        public Dia5() {
        }

        public static Dia5 newInstance(int sectionNumber) {
            Dia5 fragment = new Dia5();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_ruta_semana5, container, false);
            cargaView(rootView);
            cargaLista();
            return rootView;
        }
        private void cargaView(View rootview){

            lv=(ListView)rootview.findViewById(R.id.LstDia5);

        }
        private void cargaLista(){
            final MyDBHandler db=new MyDBHandler(getActivity(),null,null,1);
            dia=5;
            String fecha_ruta = db.getFechaRuta(dia);
            ruta = db.getRutaSemanal(dia);
            if(ruta==null || ruta.length==0){
                ruta=new String[]{"Sin datos"};
            }
            adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,ruta);
            lv.setAdapter(adapter);

        }
    }

    /**
     * Obtiene la fecha que aparecera en las tabs
     */
    public String obtieneFecha(int dia){
        final MyDBHandler db2=new MyDBHandler(getApplicationContext(),null,null,1);
        String fecha_ruta = db2.getFechaRuta(dia);
        return fecha_ruta;
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if(position==0){return Dia1.newInstance(position + 1);}
            else if(position==1){return Dia2.newInstance(position + 1);}
            else if(position==2){return Dia3.newInstance(position + 1);}
            else if(position==3){return Dia4.newInstance(position + 1);}
            else {return Dia5.newInstance(position + 1);}
        }

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            switch (position) {
                case 0:
                    String d1=obtieneFecha(1);
                    if(d1!=null || d1.isEmpty() || d1!=""){return ""+d1;}
                    else {return "Día 1";}
                case 1:
                    String d2=obtieneFecha(2);
                    if(d2!=null || d2.isEmpty() || d2!=""){return ""+d2;}
                    else {return "Día 2";}
                case 2:
                    String d3=obtieneFecha(3);
                    if(d3!=null || d3.isEmpty() || d3!=""){return ""+d3;}
                    else {return "Día 3";}
                case 3:
                    String d4=obtieneFecha(4);
                    if(d4!=null || d4.isEmpty() || d4!=""){return ""+d4;}
                    else {return "Día 4";}
                case 4:
                    String d5=obtieneFecha(5);
                    if(d5!=null || d5.isEmpty() || d5!=""){return ""+d5;}
                    else {return "Día 5";}
            }
            return null;
        }
    }
}
