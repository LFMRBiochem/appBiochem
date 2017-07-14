package mx.com.sybrem.appbiochem;

/**
 * Created by Sistemas on 13/07/2017 0013.
 */

public class RepVenta {

    private int imagen;
    private String nombreProducto;
    private String vendido;

    public RepVenta(int imagen, String nombre, String vendido){
        this.imagen = imagen;
        this.nombreProducto = nombre;
        this.vendido = vendido;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public String getVendido() {
        return vendido;
    }

    public int getImagen() {
        return imagen;
    }
}
