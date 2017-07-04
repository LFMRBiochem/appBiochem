package mx.com.sybrem.appbiochem;

import android.animation.Animator;
import android.content.Intent;
import android.graphics.Rect;
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

import com.github.snowdream.android.widget.SmartImageView;

public class Novedades extends AppCompatActivity {

    SmartImageView simv;
    String novedad_id,url_novedad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final MyDBHandler dbHandler = new MyDBHandler(Novedades.this, null, null, 1);
        dbHandler.checkDBStatus(); // Tiene como fin forzar la creacion de la base de datos.

        final String usuario = dbHandler.ultimoUsuarioRegistrado();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novedades);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
                explicit_intent = new Intent(Novedades.this, NavDrawerActivity.class);
                explicit_intent.putExtra("usuario", usuario);
                startActivity(explicit_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                finish();
                return;
            }
        });
        Intent intent=getIntent();
        Bundle extras = intent.getExtras();
        novedad_id = (String)extras.get("n_id");

        if(novedad_id.equals("promo_mes")){
            url_novedad="http://www.sybrem.com.mx/adsnet/syncmovil/img_novedades/promo_mes.jpg";
        }
        else{
            url_novedad="http://www.sybrem.com.mx/adsnet/syncmovil/img_novedades/prod_new.jpg";
        }

        simv=(SmartImageView)findViewById(R.id.img_novedad);
        Rect rect2=new Rect(simv.getLeft(),simv.getTop(),simv.getRight(),simv.getBottom());
        simv.setImageUrl(url_novedad,rect2);
    }

}
