package mx.com.sybrem.appbiochem;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.snowdream.android.widget.SmartImageView;

import java.util.List;

/**
 * Created by Sistemas on 13/07/2017 0013.
 */

public class RepVentaAdapter extends RecyclerView.Adapter<RepVentaAdapter.RepVentaAdapterViewHolder> {

    private List<RepVenta> items;

    public static class RepVentaAdapterViewHolder extends RecyclerView.ViewHolder{
        public ImageView imagen;
        //public SmartImageView imagen;
        public TextView nombre;
        public TextView visitas;
        public Rect rect;

        public RepVentaAdapterViewHolder(View v) {
            super(v);
            /*imagen=(SmartImageView)v.findViewById(R.id.imagenProducto);
            rect=new Rect(imagen.getLeft(),imagen.getTop(),imagen.getRight(),imagen.getBottom());*/
            imagen = (ImageView) v.findViewById(R.id.imagenProducto);
            nombre = (TextView) v.findViewById(R.id.nombreProducto);
            visitas = (TextView) v.findViewById(R.id.vendido);
        }
    }

    public RepVentaAdapter(List<RepVenta> items) {
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public RepVentaAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.reportillo, viewGroup, false);
        return new RepVentaAdapterViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RepVentaAdapterViewHolder viewHolder, int i) {
        viewHolder.imagen.setImageResource(items.get(i).getImagen());
        viewHolder.nombre.setText(items.get(i).getNombreProducto());
        viewHolder.visitas.setText("Vendido: "+String.valueOf(items.get(i).getVendido()));
    }

}
