package mx.com.sybrem.appbiochem;

/**
 * Created by LFMR on 01/mar/2017.
 */

public class Ordenes {

    private String num_orden;
    private String fecha_registro;
    private String nom_producto;
    private String cve_cat_producto;
    private String cantidad_pedida;
    private String precio_unitario;
    private String num_partida_oc;
    private String total_comprobado;
    private String nombre_agente;
    private String por_comprobar;
    private String cuenta_contable;
    private String saldo_total;

    public String getnum_orden(){
        return num_orden;
    }
    public String getfecha_registro(){
        return fecha_registro;
    }
    public String getnom_producto(){
        return nom_producto;
    }
    public String getcve_cat_producto(){
        return cve_cat_producto;
    }
    public String getcantidad_pedida(){
        return cantidad_pedida;
    }
    public String getprecio_unitario(){
        return precio_unitario;
    }
    public String getnum_partida_oc(){
        return num_partida_oc;
    }
    public String gettotal_comprobado(){
        return total_comprobado;
    }
    public String getNombre_agente(){
        return nombre_agente;
    }
    public String getPor_comprobar(){ return por_comprobar; }
    public String getCuenta_contable(){ return cuenta_contable; }
    public String getSaldo_total() {return saldo_total; }
}
