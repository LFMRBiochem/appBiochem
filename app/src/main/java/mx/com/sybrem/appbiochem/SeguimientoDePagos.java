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

public class SeguimientoDePagos extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seguimiento_de_pagos);

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
        }
        else{
            Animation an;
            an=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_open);
            fab.startAnimation(an);
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final MyDBHandler dbHandler = new MyDBHandler(SeguimientoDePagos.this, null, null, 1);
                dbHandler.checkDBStatus(); // Tiene como fin forzar la creacion de la base de datos.

                String usuario = dbHandler.ultimoUsuarioRegistrado();

                Intent explicit_intent;
                explicit_intent = new Intent(SeguimientoDePagos.this, NavDrawerActivity.class);
                explicit_intent.putExtra("usuario", usuario);
                startActivity(explicit_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                finish();
                //startActivity(explicit_intent);
                return;
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_seguimiento_de_pagos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            final MyDBHandler dbHandler = new MyDBHandler(SeguimientoDePagos.this, null, null, 1);
            dbHandler.checkDBStatus(); // Tiene como fin forzar la creacion de la base de datos.

            String usuario = dbHandler.ultimoUsuarioRegistrado();

            Intent explicit_intent;
            explicit_intent = new Intent(SeguimientoDePagos.this, NavDrawerActivity.class);
            explicit_intent.putExtra("usuario", usuario);
            startActivity(explicit_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class Pagos extends Fragment {
        ListView lv;
        ArrayAdapter<String> adapter;
        private String[] segPagos;
        private String[] desgloce;

        Button btn_Cerrar;
        private static final String ARG_SECTION_NUMBER = "section_number";

        public Pagos() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static Pagos newInstance(int sectionNumber) {
            Pagos fragment = new Pagos();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_seguimiento_de_pagos, container, false);
            cargaView(rootView);
            cargaLista();
            return rootView;
        }
        private void cargaView(View rootview){

            lv=(ListView)rootview.findViewById(R.id.SegPagos);
        }
        private void cargaLista(){
            final MyDBHandler db=new MyDBHandler(getActivity(),null,null,1);
            db.checkDBStatus();
            segPagos=db.getListadoSeguimientoDePagos();
            adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,segPagos);
            lv.setAdapter(adapter);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                public void onItemClick(AdapterView<?> a, View v, int position, long id){
                    int posicion = position;
                    String itemValue = (String)lv.getItemAtPosition(posicion);

                    String[] separated = itemValue.split(" Cliente:");
                    String uno = "";
                    uno = separated[0];

                    String[] separated2 = uno.split(": ");
                    String dos = "";
                    dos = separated2[1];

                    desgloce = db.getListadoSeguimientoDePagosDesgloce(dos);

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
            return Pagos.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            return 1;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SEGUIMIENTO DE PAGOS";
            }
            return null;
        }
    }
}
