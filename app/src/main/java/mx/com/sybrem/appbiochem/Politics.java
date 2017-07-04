package mx.com.sybrem.appbiochem;

import android.animation.Animator;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

public class Politics extends AppCompatActivity {

    ListView lv;
    private String[] tiposPoliticas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final MyDBHandler dbHandler = new MyDBHandler(Politics.this, null, null, 1);
        dbHandler.checkDBStatus(); // Tiene como fin forzar la creacion de la base de datos.

        final String usuario = dbHandler.ultimoUsuarioRegistrado();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_politics);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Biochem");
        //setSupportActionBar(toolbar);

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


                Intent explicit_intent;
                explicit_intent = new Intent(Politics.this, NavDrawerActivity.class);
                explicit_intent.putExtra("usuario", usuario);
                startActivity(explicit_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                finish();
                return;
            }
        });


        tiposPoliticas=dbHandler.getTipoPoliticas();
        lv=(ListView)findViewById(R.id.LstPolitics);
        ArrayAdapter adaptadorPoliticas = new ArrayAdapter(this, android.R.layout.simple_list_item_1,tiposPoliticas);
        lv.setAdapter(adaptadorPoliticas);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> a, View v, int position, long id){
                int posicion = position;
                String itemValue = (String)lv.getItemAtPosition(posicion);

                String[] separated = itemValue.split("-");
                String id_politic = "";
                id_politic = separated[0];

                Intent explicit_intent;
                explicit_intent = new Intent(Politics.this, Politicas.class);
                explicit_intent.putExtra("id_politic", id_politic);
                startActivity(explicit_intent);
            }
        });



    }

}
