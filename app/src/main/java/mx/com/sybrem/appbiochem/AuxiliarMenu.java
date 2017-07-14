package mx.com.sybrem.appbiochem;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;


public class AuxiliarMenu extends AppCompatActivity implements View.OnClickListener {

    private  static final int DATABASE_VERSION = 2;
    public String fI="", fF="", fIandroid="";
    private int dia, mes, año;
    TextView tvFi,tvFf;
    Button btFI, btFF, btnGuardar;
    long md=0, md2=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auxiliar_menu);

        final MyDBHandler dbHandler = new MyDBHandler(this, null, null, DATABASE_VERSION);
        dbHandler.checkDBStatus(); // Tiene como fin forzar la creacion de la base de datos.

        final String usuarioLog = dbHandler.ultimoUsuarioRegistrado();
        String TipoUsuario = dbHandler.tipoUsuario(usuarioLog);
        String ruta = "";

        // Verifica si existe conexion a Internet
        if (!CheckNetwork.isInternetAvailable(AuxiliarMenu.this)) //returns true if internet available
        {
            String msj = "Sin conexión a internet.\nSe requiere conexión a internet para acceder a \"Mi Contabilidad\".";
            //Crea ventana de alerta.
            AlertDialog.Builder dialog1 = new AlertDialog.Builder(this);
            dialog1.setMessage(msj);
            //dialog1.setNegativeButton("",null);
            //Establece el boton de Aceptar y que hacer si se selecciona.
            dialog1.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent explicit_intent;
                    explicit_intent = new Intent(AuxiliarMenu.this, NavDrawerActivity.class);
                    explicit_intent.putExtra("usuario", usuarioLog);
                    startActivity(explicit_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                    finish();
                }
            });
            //Muestra la ventana esperando respuesta.
            dialog1.show();
        }
        // Si existe una conexion a internet, hace lo siguiente
        else{
            Toast toast = Toast.makeText(getApplicationContext(), "Seleccione un rango de fechas para generar el auxiliar contable", Toast.LENGTH_LONG);
            toast.show();
            tvFi=(TextView)findViewById(R.id.textViewFI);
            tvFf=(TextView)findViewById(R.id.textViewFF);;
            Button btnSalir = (Button)findViewById(R.id.btnSalir);
            btFI=(Button)findViewById(R.id.buttonFI);
            btFF=(Button)findViewById(R.id.buttonFF);
            btnGuardar=(Button)findViewById(R.id.btnSaved);

            // Se selecciona la fecha de inicio para el auxiliar
            btFI.setOnClickListener(this);
            // Se selecciona la fecha de fin para el auxiliar
            btFF.setOnClickListener(this);

            //Damos la opcion de salir
            btnSalir.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    final MyDBHandler dbHandler = new MyDBHandler(AuxiliarMenu.this, null, null, 1);
                    dbHandler.checkDBStatus(); // Tiene como fin forzar la creacion de la base de datos.

                    String usuario = dbHandler.ultimoUsuarioRegistrado();

                    Intent explicit_intent;
                    explicit_intent = new Intent(AuxiliarMenu.this, NavDrawerActivity.class);
                    explicit_intent.putExtra("usuario", usuario);
                    startActivity(explicit_intent);
                    return;
                }
            });

            btnGuardar.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    final MyDBHandler dbHandler = new MyDBHandler(AuxiliarMenu.this, null, null, 1);
                    dbHandler.checkDBStatus(); // Tiene como fin forzar la creacion de la base de datos.
                    String msj="";
                    String usuario = dbHandler.ultimoUsuarioRegistrado();
                    AlertDialog.Builder dialog1 = new AlertDialog.Builder(AuxiliarMenu.this);
                    if(fI.equals("")){
                        msj="Debe seleccionar la fecha de inicio y la fecha final para poder generar el auxiliar contable";
                        dialog1.setMessage(msj);
                        //Establece el boton de Aceptar y que hacer si se selecciona.
                        dialog1.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                return;
                            }
                        });
                        dialog1.show();
                        return;
                        //System.exit(0);
                    }
                    else if(fF.equals("")){
                        msj="Debe seleccionar la fecha final para poder generar el auxiliar contable";
                        dialog1.setMessage(msj);
                        //Establece el boton de Aceptar y que hacer si se selecciona.
                        dialog1.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                return;
                            }
                        });
                        dialog1.show();
                        return;
                    }
                    else{
                        Intent explicit_intent;
                        explicit_intent = new Intent(AuxiliarMenu.this, GeneraAuxiliar.class);
                        explicit_intent.putExtra("usuario", usuario);
                        explicit_intent.putExtra("fi", fI);
                        explicit_intent.putExtra("ff",fF);
                        //startActivity(explicit_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                        startActivity(explicit_intent);
                        return;
                    }

                }
            });


        }


    }//Fin del OnCreate

    @Override
    public void onClick(View v){
        final Calendar c = Calendar.getInstance();
        dia=c.get(Calendar.DAY_OF_MONTH);
        mes=c.get(Calendar.MONTH);
        año=c.get(Calendar.YEAR);
        if(v==btFI){
            // Se resetea la fecha final para evitar discrepancias en el rango que se seleccione
            fF="";
            tvFf.setText("Fecha final: ");
            SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
            String dateInString = "01-01-2017 00:00:01";
            try {
                Date date = sdf.parse(dateInString);
                md=date.getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            DatePickerDialog dpd=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener(){
                @Override
                public void onDateSet(DatePicker view, int año, int mes, int dia) {
                    int diaS=dia+1;
                    int mesS=mes+1;
                    String mesString=mesS+"";
                    String diaString=diaS+"";
                    String diaString2=dia+"";
                    String mesTextView="";
                    mesString=(mesString.length()==1)?"0"+mesString:mesString;
                    diaString=(diaString.length()==1)?"0"+diaString:diaString;
                    diaString2=(diaString2.length()==1)?"0"+diaString2:diaString2;
                    fI=año+"-"+mesString+"-"+diaString2;
                    fIandroid=diaString+"-"+mesString+"-"+año+" 00:00:01";
                    switch(mes){
                        case 0:mesTextView="Enero";break;
                        case 1:mesTextView="Febrero";break;
                        case 2:mesTextView="Marzo";break;
                        case 3:mesTextView="Abril";break;
                        case 4:mesTextView="Mayo";break;
                        case 5:mesTextView="Junio";break;
                        case 6:mesTextView="Julio";break;
                        case 7:mesTextView="Agosto";break;
                        case 8:mesTextView="Septiembre";break;
                        case 9:mesTextView="Octubre";break;
                        case 10:mesTextView="Noviembre";break;
                        case 11:mesTextView="Diciembre";break;
                    }
                    tvFi.setText("Fecha de inicio: "+dia+" de "+mesTextView+" del "+año);
                }
            },año,mes,dia);
            dpd.getDatePicker().setMinDate(md);
            dpd.getDatePicker().setMaxDate(System.currentTimeMillis() - 86400000);
            dpd.show();
        }
        if(v==btFF) {
            if(!fIandroid.equals("")){
                SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
                String dateInString = fIandroid;
                try {
                    Date date = sdf.parse(dateInString);
                    md2 = date.getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                DatePickerDialog dpd = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int año, int mes, int dia) {
                        int mesS=mes+1;
                        String mesString=mesS+"";
                        String diaString=dia+"";
                        mesString=(mesString.length()==1)?"0"+mesString:mesString;
                        diaString=(diaString.length()==1)?"0"+diaString:diaString;
                        fF = año + "-" + mesString + "-" + diaString;
                        String mesTextView="";
                        switch(mes){
                            case 0:mesTextView="Enero";break;
                            case 1:mesTextView="Febrero";break;
                            case 2:mesTextView="Marzo";break;
                            case 3:mesTextView="Abril";break;
                            case 4:mesTextView="Mayo";break;
                            case 5:mesTextView="Junio";break;
                            case 6:mesTextView="Julio";break;
                            case 7:mesTextView="Agosto";break;
                            case 8:mesTextView="Septiembre";break;
                            case 9:mesTextView="Octubre";break;
                            case 10:mesTextView="Noviembre";break;
                            case 11:mesTextView="Diciembre";break;
                        }
                        tvFf.setText("Fecha final: " + dia + " de " + mesTextView + " del " + año);
                    }
                }, año, mes, dia);
                dpd.getDatePicker().setMinDate(md2);
                dpd.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
                dpd.show();
            }
            else{
                String msj = "Debe seleccionar la fecha de inicio primero";
                //Crea ventana de alerta.
                AlertDialog.Builder dialog1 = new AlertDialog.Builder(this);
                dialog1.setMessage(msj);
                //dialog1.setNegativeButton("",null);
                //Establece el boton de Aceptar y que hacer si se selecciona.
                dialog1.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        return;
                    }
                });
                //Muestra la ventana esperando respuesta.
                dialog1.show();
            }
        }
    }
}
