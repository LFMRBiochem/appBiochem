package mx.com.sybrem.appbiochem;

import android.animation.Animator;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
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
import android.widget.ListView;
import android.widget.TextView;

import com.github.snowdream.android.widget.SmartImageView;

public class Politicas extends AppCompatActivity {

    public static String id_politica = "";
    public Intent intent = null;
    public Bundle extras = null;

    public String[] img_politics;
    public String[] urls_politics;

    public static String url_politica1="";
    public static String url_politica2="";
    public static String url_politica3="";
    public static String url_politica4="";
    public static String url_politica5="";
    public static String url_politica6="";
    public static String url_politica7="";
    public static String url_politica8="";
    public static String url_politica9="";
    public static String url_politica10="";

    public static String url_rutaFijaImagenes="http://www.sybrem.com.mx/adsnet/syncmovil/img_politicas/";

    /*private static String url_politica1="http://www.sybrem.com.mx/adsnet/syncmovil/img_politicas/politic1.png";
    private static String url_politica2="http://www.sybrem.com.mx/adsnet/syncmovil/img_politicas/politic2.png";
    private static String url_politica3="http://www.sybrem.com.mx/adsnet/syncmovil/img_politicas/politic3.png";
    private static String url_politica4="http://www.sybrem.com.mx/adsnet/syncmovil/img_politicas/politic4.png";
    private static String url_politica5="http://www.sybrem.com.mx/adsnet/syncmovil/img_politicas/politic5.png";
    public static String url_politica6="http://www.sybrem.com.mx/adsnet/syncmovil/img_politicas/politic6.png";*/

    private SectionsPagerAdapter mSectionsPagerAdapter;


    private ViewPager mViewPager;

    int k=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_politicas);

        intent = getIntent();
        extras = intent.getExtras();
        id_politica = (String)extras.get("id_politic");

        final MyDBHandler dbHandler = new MyDBHandler(Politicas.this, null, null, 1);
        dbHandler.checkDBStatus();

        img_politics=dbHandler.getImagenesPoliticas(id_politica);
        urls_politics=new String[10];
        int m=0;
        for(int n=0; n<urls_politics.length;n++){
            if(m<img_politics.length){
                if(img_politics[n].equals("0")){
                    urls_politics[n]="0";
                    m++;
                }else{
                    urls_politics[n]=url_rutaFijaImagenes+img_politics[n];
                    m++;
                }
            }else{
                urls_politics[n]="0";
                m++;
            }
        }
        url_politica1=urls_politics[0];
        url_politica2=urls_politics[1];
        url_politica3=urls_politics[2];
        url_politica4=urls_politics[3];
        url_politica5=urls_politics[4];
        url_politica6=urls_politics[5];
        url_politica7=urls_politics[6];
        url_politica8=urls_politics[7];
        url_politica9=urls_politics[8];
        url_politica10=urls_politics[9];


        for(int j=0; j<urls_politics.length; j++){
            if(!urls_politics[j].equals("0")){
                k++;
            }
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Biochem");
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);


        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);


        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
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
                Animation an;
                an=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);
                fab.startAnimation(an);
                // Tiene como fin forzar la creacion de la base de datos.

                String usuario = dbHandler.ultimoUsuarioRegistrado();

                Intent explicit_intent;
                explicit_intent = new Intent(Politicas.this, Politics.class);
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
        getMenuInflater().inflate(R.menu.menu_politicas, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Fragments de politicas
     */
    public static class Politica1 extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public Politica1() {
        }

        public static Politica1 newInstance(int sectionNumber) {
            Politica1 fragment = new Politica1();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_politicas, container, false);
            SmartImageView sv=(SmartImageView)rootView.findViewById(R.id.img_politica);
            Rect rect=new Rect(sv.getLeft(),sv.getTop(),sv.getRight(),sv.getBottom());
            if(url_politica1.equals("0")){
                sv.setImageUrl("",rect,R.drawable.logo_biochem);
            }else{
                sv.setImageUrl(url_politica1,rect);
            }
            return rootView;
        }
    }
    public static class Politica2 extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public Politica2() {
        }

        public static Politica2 newInstance(int sectionNumber) {
            Politica2 fragment = new Politica2();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_politicas, container, false);
            SmartImageView sv=(SmartImageView)rootView.findViewById(R.id.img_politica);
            Rect rect=new Rect(sv.getLeft(),sv.getTop(),sv.getRight(),sv.getBottom());
            if(url_politica2.equals("0")){
                sv.setImageUrl("",rect,R.drawable.logo_biochem);
            }else{
                sv.setImageUrl(url_politica2,rect);
            }
            return rootView;
        }
    }
    public static class Politica3 extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public Politica3() {
        }

        public static Politica3 newInstance(int sectionNumber) {
            Politica3 fragment = new Politica3();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_politicas, container, false);
            SmartImageView sv=(SmartImageView)rootView.findViewById(R.id.img_politica);
            Rect rect=new Rect(sv.getLeft(),sv.getTop(),sv.getRight(),sv.getBottom());
            if(url_politica3.equals("0")){
                sv.setImageUrl("",rect,R.drawable.logo_biochem);
            }else{
                sv.setImageUrl(url_politica3,rect);
            }
            return rootView;
        }
    }
    public static class Politica4 extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public Politica4() {
        }

        public static Politica4 newInstance(int sectionNumber) {
            Politica4 fragment = new Politica4();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_politicas, container, false);
            SmartImageView sv=(SmartImageView)rootView.findViewById(R.id.img_politica);
            Rect rect=new Rect(sv.getLeft(),sv.getTop(),sv.getRight(),sv.getBottom());
            if(url_politica4.equals("0")){
                sv.setImageUrl("",rect,R.drawable.logo_biochem);
            }else{
                sv.setImageUrl(url_politica4,rect);
            }
            return rootView;
        }
    }
    public static class Politica5 extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public Politica5() {
        }

        public static Politica5 newInstance(int sectionNumber) {
            Politica5 fragment = new Politica5();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_politicas, container, false);
            SmartImageView sv=(SmartImageView)rootView.findViewById(R.id.img_politica);
            Rect rect=new Rect(sv.getLeft(),sv.getTop(),sv.getRight(),sv.getBottom());
            if(url_politica5.equals("0")){
                sv.setImageUrl("",rect,R.drawable.logo_biochem);
            }else{
                sv.setImageUrl(url_politica5,rect);
            }
            return rootView;
        }
    }

    public static class Politica6 extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public Politica6() {
        }

        public static Politica6 newInstance(int sectionNumber) {
            Politica6 fragment = new Politica6();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_politicas, container, false);
            //TextView tviu=(TextView)rootView.findViewById(R.id.tV);
            //tviu.setText("-> "+id_politica+" -> "+url_politica6);
            SmartImageView sv=(SmartImageView)rootView.findViewById(R.id.img_politica);
            Rect rect=new Rect(sv.getLeft(),sv.getTop(),sv.getRight(),sv.getBottom());
            if(url_politica6.equals("0")){
                sv.setImageUrl("",rect,R.drawable.logo_biochem);
            }else{
                sv.setImageUrl(url_politica6,rect);
            }
            return rootView;
        }
    }
    public static class Politica7 extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public Politica7() {
        }

        public static Politica7 newInstance(int sectionNumber) {
            Politica7 fragment = new Politica7();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_politicas, container, false);
            //TextView tviu=(TextView)rootView.findViewById(R.id.tV);
            //tviu.setText("-> "+id_politica+" -> "+url_politica6);
            SmartImageView sv=(SmartImageView)rootView.findViewById(R.id.img_politica);
            Rect rect=new Rect(sv.getLeft(),sv.getTop(),sv.getRight(),sv.getBottom());
            if(url_politica7.equals("0")){
                sv.setImageUrl("",rect,R.drawable.logo_biochem);
            }else{
                sv.setImageUrl(url_politica7,rect);
            }
            return rootView;
        }
    }
    public static class Politica8 extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public Politica8() {
        }

        public static Politica8 newInstance(int sectionNumber) {
            Politica8 fragment = new Politica8();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_politicas, container, false);
            //TextView tviu=(TextView)rootView.findViewById(R.id.tV);
            //tviu.setText("-> "+id_politica+" -> "+url_politica6);
            SmartImageView sv=(SmartImageView)rootView.findViewById(R.id.img_politica);
            Rect rect=new Rect(sv.getLeft(),sv.getTop(),sv.getRight(),sv.getBottom());
            if(url_politica8.equals("0")){
                sv.setImageUrl("",rect,R.drawable.logo_biochem);
            }else{
                sv.setImageUrl(url_politica8,rect);
            }
            return rootView;
        }
    }

    public static class Politica9 extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public Politica9() {
        }

        public static Politica9 newInstance(int sectionNumber) {
            Politica9 fragment = new Politica9();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_politicas, container, false);
            //TextView tviu=(TextView)rootView.findViewById(R.id.tV);
            //tviu.setText("-> "+id_politica+" -> "+url_politica6);
            SmartImageView sv=(SmartImageView)rootView.findViewById(R.id.img_politica);
            Rect rect=new Rect(sv.getLeft(),sv.getTop(),sv.getRight(),sv.getBottom());
            if(url_politica9.equals("0")){
                sv.setImageUrl("",rect,R.drawable.logo_biochem);
            }else{
                sv.setImageUrl(url_politica9,rect);
            }
            return rootView;
        }
    }

    public static class Politica10 extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public Politica10() {
        }

        public static Politica10 newInstance(int sectionNumber) {
            Politica10 fragment = new Politica10();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_politicas, container, false);
            //TextView tviu=(TextView)rootView.findViewById(R.id.tV);
            //tviu.setText("-> "+id_politica+" -> "+url_politica6);
            SmartImageView sv=(SmartImageView)rootView.findViewById(R.id.img_politica);
            Rect rect=new Rect(sv.getLeft(),sv.getTop(),sv.getRight(),sv.getBottom());
            if(url_politica10.equals("0")){
                sv.setImageUrl("",rect,R.drawable.logo_biochem);
            }else{
                sv.setImageUrl(url_politica10,rect);
            }
            return rootView;
        }
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);

        }

        @Override
        public Fragment getItem(int position) {
            //return Politica1.newInstance(position + 1);
            if(position==0){
                if(!url_politica1.equals("0")){
                    return Politica1.newInstance(position + 1);
                }
                else if(!url_politica2.equals("0")){
                    return Politica2.newInstance(position + 1);
                }
                else if(!url_politica3.equals("0")){
                    return Politica3.newInstance(position + 1);
                }
                else if(!url_politica4.equals("0")){
                    return Politica4.newInstance(position + 1);
                }
                else if(!url_politica5.equals("0")){
                    return Politica5.newInstance(position + 1);
                }
                else if(!url_politica6.equals("0")){
                    return Politica6.newInstance(position + 1);
                }
                else if(!url_politica7.equals("0")){
                    return Politica7.newInstance(position + 1);
                }
                else if(!url_politica8.equals("0")){
                    return Politica8.newInstance(position + 1);
                }
                else if(!url_politica9.equals("0")){
                    return Politica9.newInstance(position + 1);
                }
                else{
                    return Politica10.newInstance(position + 1);
                }
            }
            else if(position==1){
                //return Politica2.newInstance(position + 1);
                if(!url_politica2.equals("0")){
                    return Politica2.newInstance(position + 1);
                }
                else if(!url_politica3.equals("0")){
                    return Politica3.newInstance(position + 1);
                }
                else if(!url_politica4.equals("0")){
                    return Politica4.newInstance(position + 1);
                }
                else if(!url_politica5.equals("0")){
                    return Politica5.newInstance(position + 1);
                }
                else if(!url_politica6.equals("0")){
                    return Politica6.newInstance(position + 1);
                }
                else if(!url_politica7.equals("0")){
                    return Politica7.newInstance(position + 1);
                }
                else if(!url_politica8.equals("0")){
                    return Politica8.newInstance(position + 1);
                }
                else if(!url_politica9.equals("0")){
                    return Politica9.newInstance(position + 1);
                }
                else{
                    return Politica10.newInstance(position + 1);
                }
            }
            else if(position==2){
                //return Politica3.newInstance(position + 1);
                if(!url_politica3.equals("0")){
                    return Politica3.newInstance(position + 1);
                }
                else if(!url_politica4.equals("0")){
                    return Politica4.newInstance(position + 1);
                }
                else if(!url_politica5.equals("0")){
                    return Politica5.newInstance(position + 1);
                }
                else if(!url_politica6.equals("0")){
                    return Politica6.newInstance(position + 1);
                }
                else if(!url_politica7.equals("0")){
                    return Politica7.newInstance(position + 1);
                }
                else if(!url_politica8.equals("0")){
                    return Politica8.newInstance(position + 1);
                }
                else if(!url_politica9.equals("0")){
                    return Politica9.newInstance(position + 1);
                }
                else{
                    return Politica10.newInstance(position + 1);
                }
            }
            else if(position==3){
                //return Politica4.newInstance(position + 1);
                if(!url_politica4.equals("0")){
                    return Politica4.newInstance(position + 1);
                }
                else if(!url_politica5.equals("0")){
                    return Politica5.newInstance(position + 1);
                }
                else if(!url_politica6.equals("0")){
                    return Politica6.newInstance(position + 1);
                }
                else if(!url_politica7.equals("0")){
                    return Politica7.newInstance(position + 1);
                }
                else if(!url_politica8.equals("0")){
                    return Politica8.newInstance(position + 1);
                }
                else if(!url_politica9.equals("0")){
                    return Politica9.newInstance(position + 1);
                }
                else{
                    return Politica10.newInstance(position + 1);
                }
            }
            else if(position==4){
                //return Politica5.newInstance(position + 1);
                if(!url_politica5.equals("0")){
                    return Politica5.newInstance(position + 1);
                }
                else if(!url_politica6.equals("0")){
                    return Politica6.newInstance(position + 1);
                }
                else if(!url_politica7.equals("0")){
                    return Politica7.newInstance(position + 1);
                }
                else if(!url_politica8.equals("0")){
                    return Politica8.newInstance(position + 1);
                }
                else if(!url_politica9.equals("0")){
                    return Politica9.newInstance(position + 1);
                }
                else{
                    return Politica10.newInstance(position + 1);
                }
            }
            else if(position==5){
                //return Politica6.newInstance(position + 1);
                if(!url_politica6.equals("0")){
                    return Politica6.newInstance(position + 1);
                }
                else if(!url_politica7.equals("0")){
                    return Politica7.newInstance(position + 1);
                }
                else if(!url_politica8.equals("0")){
                    return Politica8.newInstance(position + 1);
                }
                else if(!url_politica9.equals("0")){
                    return Politica9.newInstance(position + 1);
                }
                else{
                    return Politica10.newInstance(position + 1);
                }
            }
            else if(position==6){
                //return Politica7.newInstance(position + 1);
                if(!url_politica7.equals("0")){
                    return Politica7.newInstance(position + 1);
                }
                else if(!url_politica8.equals("0")){
                    return Politica8.newInstance(position + 1);
                }
                else if(!url_politica9.equals("0")){
                    return Politica9.newInstance(position + 1);
                }
                else{
                    return Politica10.newInstance(position + 1);
                }
            }
            else if(position==7){
                //return Politica8.newInstance(position + 1);
                if(!url_politica8.equals("0")){
                    return Politica8.newInstance(position + 1);
                }
                else if(!url_politica9.equals("0")){
                    return Politica9.newInstance(position + 1);
                }
                else{
                    return Politica10.newInstance(position + 1);
                }
            }
            else if(position==8){
                //return Politica9.newInstance(position + 1);
                if(!url_politica9.equals("0")){
                    return Politica9.newInstance(position + 1);
                }
                else{
                    return Politica10.newInstance(position + 1);
                }
            }
            else{return Politica10.newInstance(position + 1);}
        }

        @Override
        public int getCount() {
            return k;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
                case 3:
                    return "SECTION 4";
                case 4:
                    return "SECTION 5";
                case 5:
                    return "SECTION 6";
            }
            return null;
        }
    }
}
