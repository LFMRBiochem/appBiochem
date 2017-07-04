package mx.com.sybrem.appbiochem;

import android.app.Dialog;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;


public class Lista_ClientesFragment extends Fragment {


    ListView lv;
    ArrayAdapter<String> adapter;
    private String[] clientes;
    private String[] desgloce;

    Button btn_Cerrar;

    private static final String ARG_SECTION_NUMBER = "section_number";

    public Lista_ClientesFragment() {
    }

    public static Lista_ClientesFragment newInstance(int sectionNumber) {
        Lista_ClientesFragment fragment = new Lista_ClientesFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_lista__clientes, container, false);
        cargaView(rootView);
        cargaLista();
        return rootView;
    }
    private void cargaView(View rootview){

        lv=(ListView)rootview.findViewById(R.id.LstClientes);

    }
    private void cargaLista(){
        final MyDBHandler db=new MyDBHandler(getActivity(),null,null,1);
        db.checkDBStatus();
        clientes = db.getClientes();
        adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,clientes);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> a, View v, int position, long id){
                int posicion = position;
                String itemValue = (String)lv.getItemAtPosition(posicion);

                String[] separated = itemValue.split("-");
                String cve_cliente = "";
                cve_cliente = separated[0];
                String nom_cliente=separated[1];

                desgloce = db.getListadoClientesDesgloce(cve_cliente);

                final Dialog dia=new Dialog(getActivity());
                dia.getWindow().getAttributes().windowAnimations=R.style.PauseDialogAnimation;
                dia.setTitle(nom_cliente);
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