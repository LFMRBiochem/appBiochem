package mx.com.sybrem.appbiochem;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

import java.util.Timer;
import java.util.TimerTask;

public class Marketing extends AppCompatActivity {

    private ImageSwitcher imageSwitcher;
    private  static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "bladeTablet.db";

    private int[] gallery = { R.drawable.auripetnombre, R.drawable.auripetofertas, R.drawable.auripetventajas,
            R.drawable.palaidnombre, R.drawable.palaidofert, R.drawable.palaidventajas };

    private int position;

    private static final Integer DURATION = 10000;

    private Timer timer = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marketing);



        final MyDBHandler dbHandler = new MyDBHandler(this, null, null, DATABASE_VERSION);
        dbHandler.checkDBStatus(); // Tiene como fin forzar la creacion de la base de datos.

        dbHandler.registraFechaMarketing();

        Button btnSkip = (Button) findViewById(R.id.buttonSaltar);
        btnSkip.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                //Se pasan los datos del usuario que se logeo
                String usuarioLogeado = "";
                Intent intent = getIntent();
                Bundle extras = intent.getExtras();
                usuarioLogeado = (String) extras.get("usuario");
                String tipo_usuario = dbHandler.tipoUsuario(usuarioLogeado);

                if (tipo_usuario.toString().equals("A")) {
                    Intent explicit_intent;
                    explicit_intent = new Intent(Marketing.this, NavDrawerActivity.class);
                    explicit_intent.putExtra("usuario", usuarioLogeado);
                    startActivity(explicit_intent);
                    return;
                } else {

                    String ruta = dbHandler.rutaSeleccionada();

                    if(ruta.length() > 0) {
                        Intent explicit_intent;
                        explicit_intent = new Intent(Marketing.this, NavDrawerActivity.class);
                        explicit_intent.putExtra("usuario", usuarioLogeado);
                        startActivity(explicit_intent);
                        return;
                    }

                    else{
                        Intent explicit_intent;
                        explicit_intent = new Intent(Marketing.this, EligeRuta.class);
                        explicit_intent.putExtra("usuario", usuarioLogeado);
                        startActivity(explicit_intent);
                        return;
                    }
                }
            }
        });

        imageSwitcher = (ImageSwitcher) findViewById(R.id.imageSwitcher);
        imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {

            public View makeView() {
                return new ImageView(Marketing.this);
            }
        });

        // Set animations
        // https://danielme.com/2013/08/18/diseno-android-transiciones-entre-activities/
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        Animation fadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        imageSwitcher.setInAnimation(fadeIn);
        imageSwitcher.setOutAnimation(fadeOut);

        if (timer != null) {
            timer.cancel();
        }
        position = 0;
        startSlider();
    }

    // ////////////////////BUTTONS
    /**
     * starts or restarts the slider
     *
     * @param button
     */
    public void start(View button) {
        if (timer != null) {
            timer.cancel();
        }
        position = 0;
        startSlider();
    }

    public void stop(View button) {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    public void startSlider() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {

            public void run() {
                // avoid exception:
                // "Only the original thread that created a view hierarchy can touch its views"
                runOnUiThread(new Runnable() {
                    public void run() {
                        imageSwitcher.setImageResource(gallery[position]);
                        position++;
                        if (position == gallery.length) {
                            position = 0;
                        }
                    }
                });
            }

        }, 0, DURATION);
    }

    // Stops the slider when the Activity is going into the background
    @Override
    protected void onPause() {
        super.onPause();
        if (timer != null) {
            timer.cancel();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (timer != null) {
            startSlider();
        }

    }
}
