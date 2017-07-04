package mx.com.sybrem.appbiochem;// Imports de los manejadores de la base de datos, contents y el cursor (Content y Cursor se usaran despuŽs)
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/*************************************************************************************
 * Created by mrendonr on 23/08/16.                                                  *
 * Clase usada para la ejecuci—n de los querys con la base de datos local            *
 * 26/Agosto/2016: Se agrega el metodo getClientes().                                *
 * 29/Agosto/2016: Se agregan los TAGS para cada columna leida x JSON                *
 * 30/Agosto/2016: Se agrega la rutina json para la importaci—n de los catalogos     *
 * 26/Octubre/2016: Se agregan las rutinas y tablas q corresponden al Estado de cuenta
 *************************************************************************************/
public class MyDBHandler extends SQLiteOpenHelper
{
    // final: Definici—n de constantes

    //Definici—n de la base de datos
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "bladeTablet.db";

    // Definici—n de los nombres de las tablas de la base de datos
    public static final String TABLE_VN_CAT_CLIENTES = "vn_cat_clientes";
    public static final String TABLE_IN_CAT_PRODUCTOS = "in_cat_productos";
    public static final String TABLE_VN_CAT_CONDUCTOS= "vn_cat_conductos";
    public static final String TABLE_VN_PEDIDOS_ENCABEZADO = "vn_pedidos_encabezado";
    public static final String TABLE_VN_PEDIDOS_PARTIDAS = "vn_pedidos_partidas";
    public static final String TABLE_GL_ACCESOS = "gl_accesos";
    public static final String TABLE_GL_SYNC = "gl_sync";
    public static final String TABLE_GL_BITACORA_ACCESOS = "gl_bitacora_accesos";
    public static final String TABLE_VN_CAT_RUTAS = "vn_cat_rutas";
    public static final String TABLE_IN_CENTROS_INVENTARIOS = "in_centros_inventarios";
    public static final String TABLE_VN_CAT_AGENTES_PARAMETROS = "vn_cat_agentes_parametros";
    public static final String TABLE_VN_RUTAS_ASOCIACIONES = "vn_rutas_asociaciones";
    public static final String TABLE_VN_PAQUETES_PARTIDAS = "vn_paquetes_partidas";
    public static final String TABLE_GL_CAT_LOCALIDADES="gl_cat_localidades";
    public static final String TABLE_GL_CAT_ENTIDADES="gl_cat_entidades";
    public static final String TABLE_VN_CLIENTES_SEGUIMIENTO="vn_clientes_seguimiento";

    // ++++++++++++++++++++++++++ Tablas version 2 modulo Pagos +++++++++++++++++++++++++++++++++++
    public static final String TABLE_VN_CAT_TIPOS_PAGO = "vn_cat_tipos_pago";
    public static final String TABLE_VN_CAT_BANCOS_CLIENTES = "vn_cat_bancos_clientes";
    public static final String TABLE_VN_DOCUMENTOS_DEPOSITOS = "vn_documentos_depositos";
    public static final String TABLE_VN_DOCUMENTOS_RESPALDOS = "vn_documentos_respaldos";
    public static final String TABLE_VN_DOCUMENTOS_ENCABEZADO = "vn_documentos_encabezado";
    public static final String TABLE_VN_DOCUMENTOS_PARTIDAS = "vn_documentos_partidas";
    public static final String TABLE_TS_CAT_BANCOS = "ts_cat_bancos";
    // ++++++++++++++++++++++++++ Fin de la adicion de tablas para el modulo de pagos +++++++++++++
    public static final String TABLE_VN_SEGUIMIENTO_DE_PEDIDOS = "vn_seguimiento_de_pedidos";
    public static final String TABLE_VN_CAT_AGENTES = "vn_cat_agentes";
    public static final String TABLE_GL_RUTA_SELECCIONADA = "gl_ruta_seleccionada";
    public static final String TABLE_VN_REGISTRO_VISITA = "vn_registro_visita";
    public static final String TABLE_GL_MARKETING = "gl_marketing";
    public static final String TABLE_VN_SEGUIMIENTO_DE_PAGOS = "vn_seguimiento_de_pagos";
    public static final String TABLE_VN_PROGRAMA_RUTAS_SEMANALES = "vn_programa_rutas_semanales";
    public static final String TABLE_VN_POLITICAS="vn_politicas";

    // Definici—n de los nombres de columnas de cada tabla y los TAG JSON

    // gl_bitacora_accesos == ESTA TABLA NO TIENE TAG DEBIDO A QUE NO HAY SINCRONIZACION DE LA MISMA
    public static final String COL_GLBITACORAACCESOS_ID = "_id";
    public static final String COL_GLBITACORAACCESOS_CVEUSUARIO = "cve_usuario";
    public static final String COL_GLBITACORAACCESOS_FECHAREGISTRO = "fecha_registro";

    // vn_cat_clientes:
    public static final String COL_VNCATCLIENTES_ID = "_id";
    public static final String COL_VNCATCLIENTES_CVEUSUARIO = "cve_usuario";
    public static final String COL_VNCATCLIENTES_NOMBRE = "nombre";
    public static final String COL_VNCATCLIENTES_RFC = "rfc";
    public static final String COL_VNCATCLIENTES_TIPOCONTRIBUYENTE = "tipo_contribuyente";
    public static final String COL_VNCATCLIENTES_CALLEDOMICILIO = "calle_domicilio";
    public static final String COL_VNCATCLIENTES_COLONIA = "colonia";
    public static final String COL_VNCATCLIENTES_CODIGOPOSTAL = "codigo_postal";
    public static final String COL_VNCATCLIENTES_TELEFONOS = "telefonos";
    public static final String COL_VNCATCLIENTES_FAX = "fax";
    public static final String COL_VNCATCLIENTES_EMAIL = "email";
    public static final String COL_VNCATCLIENTES_DOMICILIOBODEGA = "domicilio_bodega";
    public static final String COL_VNCATCLIENTES_REPRESENTANTELEGAL = "representante_legal";
    public static final String COL_VNCATCLIENTES_ATENCIONVENTAS = "atencion_ventas";
    public static final String COL_VNCATCLIENTES_ATENCIONPAGOS = "atencion_pagos";
    public static final String COL_VNCATCLIENTES_CVELOCALIDAD = "cve_localidad";
    public static final String COL_VNCATCLIENTES_COMENTARIOS = "comentarios";
    public static final String COL_VNCATCLIENTES_CVEUSUARIOVENTAS = "cve_usuario_ventas";
    public static final String COL_VNCATCLIENTES_CONTRASENA = "contrasena";
    public static final String COL_VNCATCLIENTES_ENTRE = "entre";
    public static final String COL_VNCATCLIENTES_ENTRE2 = "entre2";
    public static final String COL_VNCATCLIENTES_ESTATUS = "estatus";
    public static final String COL_VNCATCLIENTES_DOMICILIOENTREGA = "domicilio_entregas";
    public static final String COL_VNCATCLIENTES_ULTIMAACTUALIZACION = "ultima_actualizacion";
    public static final String COL_VNCATCLIENTES_CVEUSUARIOACTUALIZACION = "cve_usuario_actualizacion";
    public static final String COL_VNCATCLIENTES_LOCALIZACION = "localizacion";
    public static final String COL_VNCATCLIENTES_MOROSO = "moroso";
    public static final String COL_VNCATCLIENTES_COMENTARIOSCLIENTE = "comentarios_cliente";
    public static final String COL_VNCATCLIENTES_HISTORIALCOMENTARIOS = "historial_comentarios";
    public static final String COL_VNCATCLIENTES_OPINION_SERVICIO = "opinion_servicio";
    public static final String COL_VNCATCLIENTES_COMENTARIOS_SERVICIO = "comentarios_servicio";
    public static final String COL_VNCATCLIENTES_FECHAREGISTRO = "fecha_registro";
    public static final String COL_VNCATCLIENTES_ESTATUSMOVIMIENTOS = "estatus_movimientos";

    // TAG JSON de vn_cat_clientes
    public static final String TAG_VNCATCLIENTES_CVEUSUARIO = "cve_usuario";
    public static final String TAG_VNCATCLIENTES_NOMBRE = "nombre";
    public static final String TAG_VNCATCLIENTES_RFC = "rfc";
    public static final String TAG_VNCATCLIENTES_TIPOCONTRIBUYENTE = "tipo_contribuyente";
    public static final String TAG_VNCATCLIENTES_CALLEDOMICILIO = "calle_domicilio";
    public static final String TAG_VNCATCLIENTES_COLONIA = "colonia";
    public static final String TAG_VNCATCLIENTES_CODIGOPOSTAL = "codigo_postal";
    public static final String TAG_VNCATCLIENTES_TELEFONOS = "telefonos";
    public static final String TAG_VNCATCLIENTES_FAX = "fax";
    public static final String TAG_VNCATCLIENTES_EMAIL = "email";
    public static final String TAG_VNCATCLIENTES_DOMICILIOBODEGA = "domicilio_bodega";
    public static final String TAG_VNCATCLIENTES_REPRESENTANTELEGAL = "representante_legal";
    public static final String TAG_VNCATCLIENTES_ATENCIONVENTAS = "atencion_ventas";
    public static final String TAG_VNCATCLIENTES_ATENCIONPAGOS = "atencion_pagos";
    public static final String TAG_VNCATCLIENTES_CVELOCALIDAD = "cve_localidad";
    public static final String TAG_VNCATCLIENTES_COMENTARIOS = "comentarios";
    public static final String TAG_VNCATCLIENTES_CVEUSUARIOVENTAS = "cve_usuario_ventas";
    public static final String TAG_VNCATCLIENTES_CONTRASENA = "contrasena";
    public static final String TAG_VNCATCLIENTES_ENTRE = "entre";
    public static final String TAG_VNCATCLIENTES_ENTRE2 = "entre2";
    public static final String TAG_VNCATCLIENTES_ESTATUS = "estatus";
    public static final String TAG_VNCATCLIENTES_DOMICILIOENTREGA = "domicilio_entregas";
    public static final String TAG_VNCATCLIENTES_ULTIMAACTUALIZACION = "ultima_actualizacion";
    public static final String TAG_VNCATCLIENTES_CVEUSUARIOACTUALIZACION = "cve_usuario_actualizacion";
    public static final String TAG_VNCATCLIENTES_LOCALIZACION = "localizacion";
    public static final String TAG_VNCATCLIENTES_MOROSO = "moroso";
    public static final String TAG_VNCATCLIENTES_COMENTARIOSCLIENTE = "comentarios_cliente";
    public static final String TAG_VNCATCLIENTES_HISTORIALCOMENTARIOS = "historial_comentarios";
    public static final String TAG_VNCATCLIENTES_OPINION_SERVICIO = "opinion_servicio";
    public static final String TAG_VNCATCLIENTES_COMENTARIOS_SERVICIO = "comentarios_servicio";
    public static final String TAG_VNCATCLIENTES_FECHAREGISTRO = "fecha_registro";
    public static final String TAG_VNCATCLIENTES_ESTATUSMOVIMIENTOS = "estatus_movimientos";

    // in_cat_productos:
    public static final String COL_INCATPRODUCTOS_ID = "_id";
    public static final String COL_INCATPRODUCTOS_CVECATPRODUCTO = "cve_cat_producto";
    public static final String COL_INCATPRODUCTOS_CVECOMPANIA = "cve_compania";
    public static final String COL_INCATPRODUCTOS_CVE_GRUPO = "cve_grupo";
    public static final String COL_INCATPRODUCTOS_CVESUBGRUPO = "cve_subgrupo";
    public static final String COL_INCATPRODUCTOS_CVE_FAMILIA = "cve_familia";
    public static final String COL_INCATPRODUCTOS_CVEPRODUCTO = "cve_producto";
    public static final String COL_INCATPRODUCTOS_ORIGEN = "origen";
    public static final String COL_INCATPRODUCTOS_NOMPRODUCTO = "nom_producto";
    public static final String COL_INCATPRODUCTOS_CVEPOSICIONFINANCIERA = "cve_posicion_financiera";
    public static final String COL_INCATPRODUCTOS_CVECENTROCOSTOELABORA = "cve_centro_costo_elabora";
    public static final String COL_INCATPRODUCTOS_CVECLASIFICACION = "cve_clasificacion";
    public static final String COL_INCATPRODUCTOS_CVEUNIDADMEDIDA = "cve_unidad_medida";
    public static final String COL_INCATPRODUCTOS_CVEPROVEEDORPREFERENTE = "cve_proveedor_preferente";
    public static final String COL_INCATPRODUCTOS_ULTIMOCOSTO = "ultimo_costo";
    public static final String COL_INCATPRODUCTOS_COSTOPROMEDIO = "costo_promedio";
    public static final String COL_INCATPRODUCTOS_COSTOESTANDAR = "costo_estandar";
    public static final String COL_INCATPRODUCTOS_PRECIOUNITARIO = "precio_unitario";
    public static final String COL_INCATPRODUCTOS_PORCENTAJEIMPUESTO = "porcentaje_impuesto";
    public static final String COL_INCATPRODUCTOS_PIEZASPORPAQUETE = "piezas_por_paquete";
    public static final String COL_INCATPRODUCTOS_VENTAMINIMA = "venta_minima";
    public static final String COL_INCATPRODUCTOS_PIEZASPORLOTE = "piezas_por_lote";
    public static final String COL_INCATPRODUCTOS_MINPIEZASPORLOTE = "min_piezas_por_lote";
    public static final String COL_INCATPRODUCTOS_DIASFABRICACION = "dias_fabricacion";
    public static final String COL_INCATPRODUCTOS_PESOUNITARIO = "peso_unitario";
    public static final String COL_INCATPRODUCTOS_ESVENTA = "es_venta";
    public static final String COL_INCATPRODUCTOS_CONSIDERARMARGEN = "considerar_margen";
    public static final String COL_INCATPRODUCTOS_ESTATUS = "estatus";
    public static final String COL_INCATPRODUCTOS_CODIGOBARRASJPG = "codigo_barras_jpg";
    public static final String COL_INCATPRODUCTOS_CODIGOBARRASCHR = "codigo_barras_chr";
    public static final String COL_INCATPRODUCTOS_MINIMO = "minimo";
    public static final String COL_INCATPRODUCTOS_MAXIMO = "maximo";
    public static final String COL_INCATPRODUCTOS_REORDEN = "reorden";
    public static final String COL_INCATPRODUCTOS_COMENTARIOS = "comentarios";
    public static final String COL_INCATPRODUCTOS_USOS = "usos";
    public static final String COL_INCATPRODUCTOS_DOSIS = "dosis";
    public static final String COL_INCATPRODUCTOS_VENTAJAS = "ventajas";
    public static final String COL_INCATPRODUCTOS_FORMULA = "formula";
    public static final String COL_INCATPRODUCTOS_IMAGEN = "imagen";
    public static final String COL_INCATPRODUCTOS_PIEZASLOGIS = "piezas_logis";
    public static final String COL_INCATPRODUCTOS_ABREVIATURA = "abreviatura";
    public static final String COL_INCATPRODUCTOS_IEPS = "ieps";
    public static final String COL_INCATPRODUCTOS_INDICACIONES = "indicaciones";
    public static final String COL_INCATPRODUCTOS_ESPECIESCULTIVOS = "especies_cultivos";
    public static final String COL_INCATPRODUCTOS_IDCATEGORIA = "id_categoria";
    public static final String COL_INCATPRODUCTOS_IMAGENENCABEZADO = "imagen_encabezado";
    public static final String COL_INCATPRODUCTOS_PORCENTAJEPAQUETE =  "porcentaje_paquete";
    public static final String COL_INCATPRODUCTOS_INFTECBASICA =  "inf_tec_basica";
    public static final String COL_INCATPRODUCTOS_INFCOMERCIAL =  "inf_comercial";
    public static final String COL_INCATPRODUCTOS_INFTECCOMPLETA =  "inf_tec_completa";
    public static final String COL_INCATPRODUCTOS_INTRODUCCION =  "introduccion";



    // TAG JSON de in_cat_productos:
    public static final String TAG_INCATPRODUCTOS_CVECATPRODUCTO = "cve_cat_producto";
    public static final String TAG_INCATPRODUCTOS_CVECOMPANIA = "cve_compania";
    public static final String TAG_INCATPRODUCTOS_CVE_GRUPO = "cve_grupo";
    public static final String TAG_INCATPRODUCTOS_CVESUBGRUPO = "cve_subgrupo";
    public static final String TAG_INCATPRODUCTOS_CVE_FAMILIA = "cve_familia";
    public static final String TAG_INCATPRODUCTOS_CVEPRODUCTO = "cve_producto";
    public static final String TAG_INCATPRODUCTOS_ORIGEN = "origen";
    public static final String TAG_INCATPRODUCTOS_NOMPRODUCTO = "nom_producto";
    public static final String TAG_INCATPRODUCTOS_CVEPOSICIONFINANCIERA = "cve_posicion_financiera";
    public static final String TAG_INCATPRODUCTOS_CVECENTROCOSTOELABORA = "cve_centro_costo_elabora";
    public static final String TAG_INCATPRODUCTOS_CVECLASIFICACION = "cve_clasificacion";
    public static final String TAG_INCATPRODUCTOS_CVEUNIDADMEDIDA = "cve_unidad_medida";
    public static final String TAG_INCATPRODUCTOS_CVEPROVEEDORPREFERENTE = "cve_proveedor_preferente";
    public static final String TAG_INCATPRODUCTOS_ULTIMOCOSTO = "ultimo_costo";
    public static final String TAG_INCATPRODUCTOS_COSTOPROMEDIO = "costo_promedio";
    public static final String TAG_INCATPRODUCTOS_COSTOESTANDAR = "costo_estandar";
    public static final String TAG_INCATPRODUCTOS_PRECIOUNITARIO = "precio_unitario";
    public static final String TAG_INCATPRODUCTOS_PORCENTAJEIMPUESTO = "porcentaje_impuesto";
    public static final String TAG_INCATPRODUCTOS_PIEZASPORPAQUETE = "piezas_por_paquete";
    public static final String TAG_INCATPRODUCTOS_VENTAMINIMA = "venta_minima";
    public static final String TAG_INCATPRODUCTOS_PIEZASPORLOTE = "piezas_por_lote";
    public static final String TAG_INCATPRODUCTOS_MINPIEZASPORLOTE = "min_piezas_por_lote";
    public static final String TAG_INCATPRODUCTOS_DIASFABRICACION = "dias_fabricacion";
    public static final String TAG_INCATPRODUCTOS_PESOUNITARIO = "peso_unitario";
    public static final String TAG_INCATPRODUCTOS_ESVENTA = "es_venta";
    public static final String TAG_INCATPRODUCTOS_CONSIDERARMARGEN = "considerar_margen";
    public static final String TAG_INCATPRODUCTOS_ESTATUS = "estatus";
    public static final String TAG_INCATPRODUCTOS_CODIGOBARRASJPG = "codigo_barras_jpg";
    public static final String TAG_INCATPRODUCTOS_CODIGOBARRASCHR = "codigo_barras_chr";
    public static final String TAG_INCATPRODUCTOS_MINIMO = "minimo";
    public static final String TAG_INCATPRODUCTOS_MAXIMO = "maximo";
    public static final String TAG_INCATPRODUCTOS_REORDEN = "reorden";
    public static final String TAG_INCATPRODUCTOS_COMENTARIOS = "comentarios";
    public static final String TAG_INCATPRODUCTOS_USOS = "usos";
    public static final String TAG_INCATPRODUCTOS_DOSIS = "dosis";
    public static final String TAG_INCATPRODUCTOS_VENTAJAS = "ventajas";
    public static final String TAG_INCATPRODUCTOS_FORMULA = "formula";
    public static final String TAG_INCATPRODUCTOS_IMAGEN = "imagen";
    public static final String TAG_INCATPRODUCTOS_PIEZASLOGIS = "piezas_logis";
    public static final String TAG_INCATPRODUCTOS_ABREVIATURA = "abreviatura";
    public static final String TAG_INCATPRODUCTOS_IEPS = "ieps";
    public static final String TAG_INCATPRODUCTOS_INDICACIONES = "indicaciones";
    public static final String TAG_INCATPRODUCTOS_ESPECIESCULTIVOS = "especies_cultivos";
    public static final String TAG_INCATPRODUCTOS_IDCATEGORIA = "id_categoria";
    public static final String TAG_INCATPRODUCTOS_IMAGENENCABEZADO = "imagen_encabezado";
    public static final String TAG_INCATPRODUCTOS_PORCENTAJEPAQUETE =  "porcentaje_paquete";
    public static final String TAG_INCATPRODUCTOS_INFTECBASICA =  "inf_tec_basica";
    public static final String TAG_INCATPRODUCTOS_INFCOMERCIAL =  "inf_comercial";
    public static final String TAG_INCATPRODUCTOS_INFTECCOMPLETA =  "inf_tec_completa";
    public static final String TAG_INCATPRODUCTOS_INTRODUCCION =  "introduccion";


    // vn_cat_conductos:
    public static final String COL_VNCATCONDUCTOS_ID = "_id";
    public static final String COL_VNCATCONDUCTOS_CVECONDUCTO = "cve_conducto";
    public static final String COL_VNCATCONDUCTOS_CVE_COMPANIA = "cve_compania";
    public static final String COL_VNCATCONDUCTOS_NOMBRECONDUCTO = "nombre_conducto";
    public static final String COL_VNCATCONDUCTOS_MOSTRAR = "mostrar";
    public static final String COL_VNCATCONDUCTOS_CONTRATO = "contrato";
    public static final String COL_VNCATCONDUCTOS_CONCEPTO = "concepto";

    // TAG JSON de vn_cat_conductos:
    public static final String TAG_VNCATCONDUCTOS_ID = "_id";
    public static final String TAG_VNCATCONDUCTOS_CVECONDUCTO = "cve_conducto";
    public static final String TAG_VNCATCONDUCTOS_CVE_COMPANIA = "cve_compania";
    public static final String TAG_VNCATCONDUCTOS_NOMBRECONDUCTO = "nombre_conducto";
    public static final String TAG_VNCATCONDUCTOS_MOSTRAR = "mostrar";
    public static final String TAG_VNCATCONDUCTOS_CONTRATO = "contrato";
    public static final String TAG_VNCATCONDUCTOS_CONCEPTO = "concepto";


    // vn_pedidos_encabezado:
    public static final String COL_VNPEDIDOSENCABEZADO_ID = "_id";
    public static final String COL_VNPEDIDOSENCABEZADO_CVECOMPANIA = "cve_compania";
    public static final String COL_VNPEDIDOSENCABEZADO_NUMPEDIDO = "num_pedido";
    public static final String COL_VNPEDIDOSENCABEZADO_NUM_ANEXO = "num_anexo";
    public static final String COL_VNPEDIDOSENCABEZADO_TIPOPEDIDO = "tipo_pedido";
    public static final String COL_VNPEDIDOSENCABEZADO_ESTATUS = "estatus";
    public static final String COL_VNPEDIDOSENCABEZADO_FECHAPEDIDO = "fecha_pedido";
    public static final String COL_VNPEDIDOSENCABEZADO_CVEMONEDA = "cve_moneda";
    public static final String COL_VNPEDIDOSENCABEZADO_CVECLIENTE = "cve_cliente";
    public static final String COL_VNPEDIDOSENCABEZADO_CVEAGENTE = "cve_agente";
    public static final String COL_VNPEDIDOSENCABEZADO_CVEUSUARIOCAPTURA = "cve_usuario_captura";
    public static final String COL_VNPEDIDOSENCABEZADO_FECHAREQUERIMIENTO = "fecha_requerimiento";
    public static final String COL_VNPEDIDOSENCABEZADO_SUMA = "suma";
    public static final String COL_VNPEDIDOSENCABEZADO_DESCUENTO = "descuento";
    public static final String COL_VNPEDIDOSENCABEZADO_SUBTOTAL = "subtotal";
    public static final String COL_VNPEDIDOSENCABEZADO_IMPUESTO = "impuesto";
    public static final String COL_VNPEDIDOSENCABEZADO_TOTAL = "total";
    public static final String COL_VNPEDIDOSENCABEZADO_COMENTARIOS = "comentarios";
    public static final String COL_VNPEDIDOSENCABEZADO_COMENTARIOSCXC = "comentarios_cxc";
    public static final String COL_VNPEDIDOSENCABEZADO_ASISTENCIA = "asistencia";
    public static final String COL_VNPEDIDOSENCABEZADO_TOTALIEPS = "total_ieps";
    public static final String COL_VNPEDIDOSENCABEZADO_IEPS3 = "ieps_3";
    public static final String COL_VNPEDIDOSENCABEZADO_IEPS35 = "ieps_3_5";
    public static final String COL_VNPEDIDOSENCABEZADO_FAMILIA = "familia";
    public static final String COL_VNPEDIDOSENCABEZADO_VERIFICADO = "verificado";
    public static final String COL_VNPEDIDOSENCABEZADO_CVEUSUARIOVERIFICADO = "cve_usuario_verificado";
    public static final String COL_VNPEDIDOSENCABEZADO_FECHAVERIFICACION = "fecha_verificacion";
    public static final String COL_VNPEDIDOSENCABEZADO_FECHAAUTORIZACION = "fecha_autorizaci—n";
    public static final String COL_VNPEDIDOSENCABEZADO_COMENTARIOSLIB = "comentarios_lib";
    public static final String COL_VNPEDIDOSENCABEZADO_PORCENTAJEGENERAL = "porcentaje_general";
    public static final String COL_VNPEDIDOSENCABEZADO_PEDIDOMESANTERIOR = "pedido_mes_anterior";
    public static final String COL_VNPEDIDOSENCABEZADO_SURTIDO = "surtido";
    public static final String COL_VNPEDIDOSENCABEZADO_COMENTARIOSSURTIDO = "comentarios_surtido";
    public static final String COL_VNPEDIDOSENCABEZADO_USUARIOSURTIO = "usuario_surtio";
    public static final String COL_VNPEDIDOSENCABEZADO_FECHASURTIDO = "fecha_surtido";
    public static final String COL_VNPEDIDOSENCABEZADO_IMPRESO = "impreso";
    public static final String COL_VNPEDIDOSENCABEZADO_LATITUDE = "latitude";
    public static final String COL_VNPEDIDOSENCABEZADO_LONGITUDE = "longitude";
    public static final String COL_VNPEDIDOSENCABEZADO_FECHADEPAGO = "fecha_de_pago";

    // TAG JSON de vn_pedidos_encabezado:
    public static final String TAG_VNPEDIDOSENCABEZADO_ID = "_id";
    public static final String TAG_VNPEDIDOSENCABEZADO_CVECOMPANIA = "cve_compania";
    public static final String TAG_VNPEDIDOSENCABEZADO_NUMPEDIDO = "num_pedido";
    public static final String TAG_VNPEDIDOSENCABEZADO_NUM_ANEXO = "num_anexo";
    public static final String TAG_VNPEDIDOSENCABEZADO_TIPOPEDIDO = "tipo_pedido";
    public static final String TAG_VNPEDIDOSENCABEZADO_ESTATUS = "estatus";
    public static final String TAG_VNPEDIDOSENCABEZADO_FECHAPEDIDO = "fecha_pedido";
    public static final String TAG_VNPEDIDOSENCABEZADO_CVEMONEDA = "cve_moneda";
    public static final String TAG_VNPEDIDOSENCABEZADO_CVECLIENTE = "cve_cliente";
    public static final String TAG_VNPEDIDOSENCABEZADO_CVEAGENTE = "cve_agente";
    public static final String TAG_VNPEDIDOSENCABEZADO_CVEUSUARIOCAPTURA = "cve_usuario_captura";
    public static final String TAG_VNPEDIDOSENCABEZADO_FECHAREQUERIMIENTO = "fecha_requerimiento";
    public static final String TAG_VNPEDIDOSENCABEZADO_SUMA = "suma";
    public static final String TAG_VNPEDIDOSENCABEZADO_DESCUENTO = "descuento";
    public static final String TAG_VNPEDIDOSENCABEZADO_SUBTOTAL = "subtotal";
    public static final String TAG_VNPEDIDOSENCABEZADO_IMPUESTO = "impuesto";
    public static final String TAG_VNPEDIDOSENCABEZADO_TOTAL = "total";
    public static final String TAG_VNPEDIDOSENCABEZADO_COMENTARIOS = "comentarios";
    public static final String TAG_VNPEDIDOSENCABEZADO_COMENTARIOSCXC = "comentarios_cxc";
    public static final String TAG_VNPEDIDOSENCABEZADO_ASISTENCIA = "asistencia";
    public static final String TAG_VNPEDIDOSENCABEZADO_TOTALIEPS = "total_ieps";
    public static final String TAG_VNPEDIDOSENCABEZADO_IEPS3 = "ieps_3";
    public static final String TAG_VNPEDIDOSENCABEZADO_IEPS35 = "ieps_3_5";
    public static final String TAG_VNPEDIDOSENCABEZADO_FAMILIA = "familia";
    public static final String TAG_VNPEDIDOSENCABEZADO_VERIFICADO = "verificado";
    public static final String TAG_VNPEDIDOSENCABEZADO_CVEUSUARIOVERIFICADO = "cve_usuario_verificado";
    public static final String TAG_VNPEDIDOSENCABEZADO_FECHAVERIFICACION = "fecha_verificacion";
    public static final String TAG_VNPEDIDOSENCABEZADO_FECHAAUTORIZACION = "fecha_autorizaci—n";
    public static final String TAG_VNPEDIDOSENCABEZADO_COMENTARIOSLIB = "comentarios_lib";
    public static final String TAG_VNPEDIDOSENCABEZADO_PORCENTAJEGENERAL = "porcentaje_general";
    public static final String TAG_VNPEDIDOSENCABEZADO_PEDIDOMESANTERIOR = "pedido_mes_anterior";
    public static final String TAG_VNPEDIDOSENCABEZADO_SURTIDO = "surtido";
    public static final String TAG_VNPEDIDOSENCABEZADO_COMENTARIOSSURTIDO = "comentarios_surtido";
    public static final String TAG_VNPEDIDOSENCABEZADO_USUARIOSURTIO = "usuario_surtio";
    public static final String TAG_VNPEDIDOSENCABEZADO_FECHASURTIDO = "fecha_surtido";
    public static final String TAG_VNPEDIDOSENCABEZADO_IMPRESO = "impreso";
    public static final String TAG_VNPEDIDOSENCABEZADO_LATITUDE = "latitude";
    public static final String TAG_VNPEDIDOSENCABEZADO_LONGITUDE = "longitude";
    public static final String TAG_VNPEDIDOSENCABEZADO_FECHADEPAGO = "fecha_de_pago";


    // vn_pedidos_partidas:
    public static final String COL_VNPEDIDOSPARTIDAS_ID = "_id";
    public static final String COL_VNPEDIDOSPARTIDAS_CVECOMPANIA = "cve_compania";
    public static final String COL_VNPEDIDOSPARTIDAS_NUMPEDIDO = "num_pedido";
    public static final String COL_VNPEDIDOSPARTIDAS_NUMPARTIDA = "num_partida";
    public static final String COL_VNPEDIDOSPARTIDAS_CVECATPRODUCTO = "cve_cat_producto";
    public static final String COL_VNPEDIDOSPARTIDAS_CANTIDAD = "cantidad";
    public static final String COL_VNPEDIDOSPARTIDAS_CANTIDADENTREGADA = "cantidad_entregada";
    public static final String COL_VNPEDIDOSPARTIDAS_PRECIOUNITARIO = "precio_unitario";
    public static final String COL_VNPEDIDOSPARTIDAS_PORCENTAJEDESCUENTO = "porcentaje_descuento";
    public static final String COL_VNPEDIDOSPARTIDAS_PORCENTAJEIMPUESTO = "porcentaje_impuesto";
    public static final String COL_VNPEDIDOSPARTIDAS_PORCENTAJECOMISION = "porcentaje_comision";
    public static final String COL_VNPEDIDOSPARTIDAS_SUMA = "suma";
    public static final String COL_VNPEDIDOSPARTIDAS_DESCUENTO = "descuento";
    public static final String COL_VNPEDIDOSPARTIDAS_SUBTOTAL = "subtotal";
    public static final String COL_VNPEDIDOSPARTIDAS_IMPUESTO = "impuesto";
    public static final String COL_VNPEDIDOSPARTIDAS_TOTAL = "total";
    public static final String COL_VNPEDIDOSPARTIDAS_DOCUMENTOREFERENCIA = "documento_referencia";
    public static final String COL_VNPEDIDOSPARTIDAS_TIPODOCUMENTOREFERENCIA = "tipo_documento_referencia";
    public static final String COL_VNPEDIDOSPARTIDAS_PARTIDADOCUMENTOREFERENCIA = "partida_documento_referencia";
    public static final String COL_VNPEDIDOSPARTIDAS_CVECENTROCOSTO = "cve_centro_costo";
    public static final String COL_VNPEDIDOSPARTIDAS_PLANVENTAS = "plan_ventas";
    public static final String COL_VNPEDIDOSPARTIDAS_NUMPAQUTE = "num_paquete";
    public static final String COL_VNPEDIDOSPARTIDAS_CONSIDERARBACKORDER = "considerar_backorder";
    public static final String COL_VNPEDIDOSPARTIDAS_MEMBACKORDER = "mem_backorder";
    public static final String COL_VNPEDIDOSPARTIDAS_CVECONDUCTO = "cve_conducto";
    public static final String COL_VNPEDIDOSPARTIDAS_TIPOCONDUCTO = "tipo_conducto";
    public static final String COL_VNPEDIDOSPARTIDAS_ESTATUS = "estatus";
    public static final String COL_VNPEDIDOSPARTIDAS_PORCENTAJECOMISIONPROMOTOR = "porcentaje_comision_promotor";
    public static final String COL_VNPEDIDOSPARTIDAS_IEPS = "ieps";
    public static final String COL_VNPEDIDOSPARTIDAS_ESPAQUETE = "es_paquete";
    public static final String COL_VNPEDIDOSPARTIDAS_DESCRIPCIONCOMPLEMENTARIA = "descripcion_complementaria";

    // TAG JSON vn_pedidos_partidas:
    public static final String TAG_VNPEDIDOSPARTIDAS_ID = "_id";
    public static final String TAG_VNPEDIDOSPARTIDAS_CVECOMPANIA = "cve_compania";
    public static final String TAG_VNPEDIDOSPARTIDAS_NUMPEDIDO = "num_pedido";
    public static final String TAG_VNPEDIDOSPARTIDAS_NUMPARTIDA = "num_partida";
    public static final String TAG_VNPEDIDOSPARTIDAS_CVECATPRODUCTO = "cve_cat_producto";
    public static final String TAG_VNPEDIDOSPARTIDAS_CANTIDAD = "cantidad";
    public static final String TAG_VNPEDIDOSPARTIDAS_CANTIDADENTREGADA = "cantidad_entregada";
    public static final String TAG_VNPEDIDOSPARTIDAS_PRECIOUNITARIO = "precio_unitario";
    public static final String TAG_VNPEDIDOSPARTIDAS_PORCENTAJEDESCUENTO = "porcentaje_descuento";
    public static final String TAG_VNPEDIDOSPARTIDAS_PORCENTAJEIMPUESTO = "porcentaje_impuesto";
    public static final String TAG_VNPEDIDOSPARTIDAS_PORCENTAJECOMISION = "porcentaje_comision";
    public static final String TAG_VNPEDIDOSPARTIDAS_SUMA = "suma";
    public static final String TAG_VNPEDIDOSPARTIDAS_DESCUENTO = "descuento";
    public static final String TAG_VNPEDIDOSPARTIDAS_SUBTOTAL = "subtotal";
    public static final String TAG_VNPEDIDOSPARTIDAS_IMPUESTO = "impuesto";
    public static final String TAG_VNPEDIDOSPARTIDAS_TOTAL = "total";
    public static final String TAG_VNPEDIDOSPARTIDAS_DOCUMENTOREFERENCIA = "documento_referencia";
    public static final String TAG_VNPEDIDOSPARTIDAS_TIPODOCUMENTOREFERENCIA = "tipo_documento_referencia";
    public static final String TAG_VNPEDIDOSPARTIDAS_PARTIDADOCUMENTOREFERENCIA = "partida_documento_referencia";
    public static final String TAG_VNPEDIDOSPARTIDAS_CVECENTROCOSTO = "cve_centro_costo";
    public static final String TAG_VNPEDIDOSPARTIDAS_PLANVENTAS = "plan_ventas";
    public static final String TAG_VNPEDIDOSPARTIDAS_NUMPAQUTE = "num_paquete";
    public static final String TAG_VNPEDIDOSPARTIDAS_CONSIDERARBACKORDER = "considerar_backorder";
    public static final String TAG_VNPEDIDOSPARTIDAS_MEMBACKORDER = "mem_backorder";
    public static final String TAG_VNPEDIDOSPARTIDAS_CVECONDUCTO = "cve_conducto";
    public static final String TAG_VNPEDIDOSPARTIDAS_TIPOCONDUCTO = "tipo_conducto";
    public static final String TAG_VNPEDIDOSPARTIDAS_ESTATUS = "estatus";
    public static final String TAG_VNPEDIDOSPARTIDAS_PORCENTAJECOMISIONPROMOTOR = "porcentaje_comision_promotor";
    public static final String TAG_VNPEDIDOSPARTIDAS_IEPS = "ieps";
    public static final String TAG_VNPEDIDOSPARTIDAS_ESPAQUETE = "es_paquete";
    public static final String TAG_VNPEDIDOSPARTIDAS_DESCRIPCIONCOMPLEMENTARIA = "descripcion_complementaria";

    // gl_accesos:
    public static final String COL_GLACCESOS_ID = "_id";
    public static final String COL_GLACCESOS_CVEUSUARIO = "cve_usuario";
    public static final String COL_GLACCESOS_PASSWORD = "password";
    public static final String COL_GLACCESOS_TIPOUSUARIO = "tipo_usuario";
    public static final String COL_GLACCESOS_ESTATUS = "estatus";
    public static final String COL_GLACCESOS_ACTUALIZO_PASSWORD = "actualizo_password";
    public static final String COL_GLACCESOS_ULTIMAACTUALIZACION = "ultima_actualizacion";
    public static final String COL_GLACCESOS_IMEI = "imei";

    // TAG JSON para gl_accesos:
    public static final String TAG_GLACCESOS_ID = "_id";
    public static final String TAG_GLACCESOS_CVEUSUARIO = "cve_usuario";
    public static final String TAG_GLACCESOS_PASSWORD = "password";
    public static final String TAG_GLACCESOS_TIPOUSUARIO = "tipo_usuario";
    public static final String TAG_GLACCESOS_ESTATUS = "estatus";
    public static final String TAG_GLACCESOS_ACTUALIZO_PASSWORD = "actualizo_password";
    public static final String TAG_GLACCESOS_ULTIMAACTUALIZACION = "ultima_actualizacion";
    public static final String TAG_GLACCESOS_IMEI = "imei";


    // gl_sync: == ESTA TABLA NO TIENE TAG DEBIDO A QUE NO HAY SINCRONIZACION DE LA MISMA
    public static final String COL_GLSYNC_ID = "_id";
    public static final String COL_GLSYNC_CVEUSUARIO = "cve_usuario";
    public static final String COL_GLSYNC_FECHASYNC = "fecha_sync";
    public static final String COL_GLSYNC_EXITOSYNC = "exito_sync";

    // vn_cat_rutas:
    public static final String COL_VNCATRUTAS_ID = "_id";
    public static final String COL_VNCATRUTAS_CVECOMPANIA = "cve_compania";
    public static final String COL_VNCATRUTAS_NUMRUTA = "num_ruta";
    public static final String COL_VNCATRUTAS_CVEAGENTE = "cve_agente";
    public static final String COL_VNCATRUTAS_NOMBRERUTA = "nombre_ruta";
    public static final String COL_VNCATRUTAS_DIASVENCIMIENTOCARTERA = "dias_vencimiento_cartera";
    public static final String COL_VNCATRUTAS_CUOTAVENTA = "cuota_venta";
    public static final String COL_VNCATRUTAS_CUOTACOBRANZA = "cuota_cobranza";
    public static final String COL_VNCATRUTAS_CUOTAMANO = "cuota_mano";
    public static final String COL_VNCATRUTAS_CUOTAPEDIDOS = "cuota_pedidos";
    public static final String COL_VNCATRUTAS_DIASINVENTARIO = "dias_inventario";

    // TAG JSON de vn_cat_rutas:
    public static final String TAG_VNCATRUTAS_ID = "_id";
    public static final String TAG_VNCATRUTAS_CVECOMPANIA = "cve_compania";
    public static final String TAG_VNCATRUTAS_NUMRUTA = "num_ruta";
    public static final String TAG_VNCATRUTAS_CVEAGENTE = "cve_agente";
    public static final String TAG_VNCATRUTAS_NOMBRERUTA = "nombre_ruta";
    public static final String TAG_VNCATRUTAS_DIASVENCIMIENTOCARTERA = "dias_vencimiento_cartera";
    public static final String TAG_VNCATRUTAS_CUOTAVENTA = "cuota_venta";
    public static final String TAG_VNCATRUTAS_CUOTACOBRANZA = "cuota_cobranza";
    public static final String TAG_VNCATRUTAS_CUOTAMANO = "cuota_mano";
    public static final String TAG_VNCATRUTAS_CUOTAPEDIDOS = "cuota_pedidos";
    public static final String TAG_VNCATRUTAS_DIASINVENTARIO = "dias_inventario";

    // in_centros_iventarios
    public static final String COL_INCENTROSINVENTARIOS_ID = "_id";
    public static final String COL_INCENTROSINVENTARIOS_CVECOMPANIA = "cve_compania";
    public static final String COL_INCENTROSINVENTARIOS_CVECENTROCOSTO = "cve_centro_costo";
    public static final String COL_INCENTROSINVENTARIOS_CVECATPRODUCTO = "cve_cat_producto";
    public static final String COL_INCENTROSINVENTARIOS_NUMLOTE = "num_lote";
    public static final String COL_INCENTROSINVENTARIOS_NUMLOTEINTERNO = "num_lote_interno";
    public static final String COL_INCENTROSINVENTARIOS_EXISTENCIAS = "existencias";
    public static final String COL_INCENTROSINVENTARIOS_COSTOUNITARIO = "costo_unitario";

    // TAG JSON de in_centros_inventarios
    public static final String TAG_INCENTROSINVENTARIOS_ID = "_id";
    public static final String TAG_INCENTROSINVENTARIOS_CVECOMPANIA = "cve_compania";
    public static final String TAG_INCENTROSINVENTARIOS_CVECENTROCOSTO = "cve_centro_costo";
    public static final String TAG_INCENTROSINVENTARIOS_CVECATPRODUCTO = "cve_cat_producto";
    public static final String TAG_INCENTROSINVENTARIOS_NUMLOTE = "num_lote";
    public static final String TAG_INCENTROSINVENTARIOS_NUMLOTEINTERNO = "num_lote_interno";
    public static final String TAG_INCENTROSINVENTARIOS_EXISTENCIAS = "existencias";
    public static final String TAG_INCENTROSINVENTARIOS_COSTOUNITARIO = "costo_unitario";

    // vn_cat_agentes_parametros
    public static final String COL_VNCATAGENTESPARAMETROS_ID = "_id";
    public static final String COL_VNCATAGENTESPARAMETROS_CVECOMPANIA = "cve_compania";
    public static final String COL_VNCATAGENTESPARAMETROS_CVEAGENTE = "cve_agente";
    public static final String COL_VNCATAGENTESPARAMETROS_CVECENTROCOSTO = "cve_centro_costo";
    public static final String COL_VNCATAGENTESPARAMETROS_AGENTETIPO = "agente_tipo";
    public static final String COL_VNCATAGENTESPARAMETROS_GRUPOCOMISION = "grupo_comision";
    public static final String COL_VNCATAGENTESPARAMETROS_CONSIGNACION = "consignacion";
    public static final String COL_VNCATAGENTESPARAMETROS_ESTATUS = "estatus";

    // TAG JSON de vn_cat_agentes_parametros
    public static final String TAG_VNCATAGENTESPARAMETROS_ID = "_id";
    public static final String TAG_VNCATAGENTESPARAMETROS_CVECOMPANIA = "cve_compania";
    public static final String TAG_VNCATAGENTESPARAMETROS_CVEAGENTE = "cve_agente";
    public static final String TAG_VNCATAGENTESPARAMETROS_CVECENTROCOSTO = "cve_centro_costo";
    public static final String TAG_VNCATAGENTESPARAMETROS_AGENTETIPO = "agente_tipo";
    public static final String TAG_VNCATAGENTESPARAMETROS_GRUPOCOMISION = "grupo_comision";
    public static final String TAG_VNCATAGENTESPARAMETROS_CONSIGNACION = "consignacion";
    public static final String TAG_VNCATAGENTESPARAMETROS_ESTATUS = "estatus";

    // vn_rutas_asociaciones
    public static final String COL_VNRUTASASOCIACIONES_ID = "_id";
    public static final String COL_VNRUTASASOCIACIONES_CVECOMPANIA = "cve_compania";
    public static final String COL_VNRUTASASOCIACIONES_NUMRUTA = "num_ruta";
    public static final String COL_VNRUTASASOCIACIONES_CVECLIENTE = "cve_cliente";
    public static final String COL_VNRUTASASOCIACIONES_DIASVISITA = "dias_visita";
    public static final String COL_VNRUTASASOCIACIONES_CVELOCALIDAD = "cve_localidad";

    // TAG JSON de vn_rutas_asociaciones
    public static final String TAG_VNRUTASASOCIACIONES_ID = "_id";
    public static final String TAG_VNRUTASASOCIACIONES_CVECOMPANIA = "cve_compania";
    public static final String TAG_VNRUTASASOCIACIONES_NUMRUTA = "num_ruta";
    public static final String TAG_VNRUTASASOCIACIONES_CVECLIENTE = "cve_cliente";
    public static final String TAG_VNRUTASASOCIACIONES_DIASVISITA = "dias_visita";
    public static final String TAG_VNRUTASASOCIACIONES_CVELOCALIDAD = "cve_localidad";

    // vn_paquetes_partidas
    public static final String COL_VNPAQUETESPARTIDAS_ID = "_id";
    public static final String COL_VNPAQUETESPARTIDAS_CVECOMPANIA = "cve_compania";
    public static final String COL_VNPAQUETESPARTIDAS_NUMPAQUETE = "num_paquete";
    public static final String COL_VNPAQUETESPARTIDAS_NUMPARTIDA = "num_partida";
    public static final String COL_VNPAQUETESPARTIDAS_CVECATPRODUCTO = "cve_cat_producto";
    public static final String COL_VNPAQUETESPARTIDAS_CANTIDAD = "cantidad";
    public static final String COL_VNPAQUETESPARTIDAS_PORCENTAJEDESCUENTO = "porcentaje_descuento";

    // TAG JSON vn_paquetes_partidas
    public static final String TAG_VNPAQUETESPARTIDAS_ID = "_id";
    public static final String TAG_VNPAQUETESPARTIDAS_CVECOMPANIA = "cve_compania";
    public static final String TAG_VNPAQUETESPARTIDAS_NUMPAQUETE = "num_paquete";
    public static final String TAG_VNPAQUETESPARTIDAS_NUMPARTIDA = "num_partida";
    public static final String TAG_VNPAQUETESPARTIDAS_CVECATPRODUCTO = "cve_cat_producto";
    public static final String TAG_VNPAQUETESPARTIDAS_CANTIDAD = "cantidad";
    public static final String TAG_VNPAQUETESPARTIDAS_PORCENTAJEDESCUENTO = "porcentaje_descuento";

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // + Se agregan las columnas y TAG para sincronizacion de las tablas relacionadas en el       +
    // + el modulo de pagos.                                                                      +
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    // Tabla vn_cat_tipos_pago ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    public static final String COL_VNCATTIPOSPAGO_ID = "_id";
    public static final String COL_VNCATTIPOSPAGO_CVETIPOPAGO = "cve_tipo_pago";
    public static final String COL_VNCATTIPOSPAGO_NOMBRETIPOPAGO = "nombre_tipo_pago";

    // TAG JSON de vn_cat_tipos_pago  +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    public static final String TAG_VNCATTIPOSPAGO_CVETIPOPAGO = "cve_tipo_pago";
    public static final String TAG_VNCATTIPOSPAGO_NOMBRETIPOPAGO = "nombre_tipo_pago";

    // Tabla vn_cat_bancos_clientes +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    public static final String COL_VNCATBANCOSCLIENTES_ID = "_id";
    public static final String COL_VNCATBANCOSCLIENTES_CVEBANCOEMISOR = "cve_banco_emisor";
    public static final String COL_VNCATBANCOSCLIENTES_NOMBREBANCO = "nombre_banco";

    // TAG JSON de vn_cat_bancos_clientes +++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    public static final String TAG_VNCATBANCOSCLIENTES_CVEBANCOEMISOR = "cve_banco_emisor";
    public static final String TAG_VNCATBANCOSCLIENTES_NOMBREBANCO = "nombre_banco";

    // Tabla vn_documentos_depositos ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    public static final String COL_VNDOCUMENTOSDEPOSITOS_ID = "_id";
    public static final String COL_VNDOCUMENTOSDEPOSITOS_PERSONADEPOSITO = "persona_deposito";
    public static final String COL_VNDOCUMENTOSDEPOSITOS_DESCRIPCION = "descripcion";

    // TAG JSON vn_documentos_depositos +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    public static final String TAG_VNDOCUMENTOSDEPOSITOS_PERSONADEPOSITO = "persona_deposito";
    public static final String TAG_VNDOCUMENTOSDEPOSITOS_DESCRIPCION = "descripcion";

    // Tabla vn_documentos_respaldos ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    public static final String COL_VNDOCUMENTOSRESPALDOS_ID = "_id";
    public static final String COL_VNDOCUMENTOSRESPALDOS_DOCTORESPALDO = "docto_respaldo";
    public static final String COL_VNDOCUMENTOSRESPALDOS_DESCRIPCION = "descripcion";

    // TAG JSON vn_documentos_respaldos +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    public static final String TAG_VNDOCUMENTOSRESPALDOS_DOCTORESPALDO = "docto_respaldo";
    public static final String TAG_VNDOCUMENTOSRESPALDOS_DESCRIPCION = "descripcion";

    // Tabla vn_documentos_encabezado +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    public static final String COL_VNDOCUMENTOSENCABEZADO_ID = "_id";
    public static final String COL_VNDOCUMENTOSENCABEZADO_CVECOMPANIA = "cve_compania";
    public static final String COL_VNDOCUMENTOSENCABEZADO_CVEDOCUMENTO = "cve_documento";
    public static final String COL_VNDOCUMENTOSENCABEZADO_NUMDOCUMENTO = "num_documento";
    public static final String COL_VNDOCUMENTOSENCABEZADO_FECHADOCUMENTO = "fecha_documento";
    public static final String COL_VNDOCUMENTOSENCABEZADO_FECHAREGISTRO = "fecha_registro";
    public static final String COL_VNDOCUMENTOSENCABEZADO_TIPODOCUMENTO = "tipo_documento";
    public static final String COL_VNDOCUMENTOSENCABEZADO_SUMA = "suma";
    public static final String COL_VNDOCUMENTOSENCABEZADO_DESCUENTO = "descuento";
    public static final String COL_VNDOCUMENTOSENCABEZADO_SUBTOTAL = "subtotal";
    public static final String COL_VNDOCUMENTOSENCABEZADO_TOTAL = "total";
    public static final String COL_VNDOCUMENTOSENCABEZADO_CVECLIENTE = "cve_cliente";
    public static final String COL_VNDOCUMENTOSENCABEZADO_CVEAGENTE = "cve_agente";
    public static final String COL_VNDOCUMENTOSENCABEZADO_CVEUSUARIO = "cve_usuario";
    public static final String COL_VNDOCUMENTOSENCABEZADO_CVEMONEDA = "cve_moneda";
    public static final String COL_VNDOCUMENTOSENCABEZADO_TIPOCAMBIO = "tipo_cambio";
    public static final String COL_VNDOCUMENTOSENCABEZADO_COMENTARIOS = "comentarios";
    public static final String COL_VNDOCUMENTOSENCABEZADO_ESTATUS = "estatus";
    public static final String COL_VNDOCUMENTOSENCABEZADO_TOTALPAGADO = "total_pagado";
    public static final String COL_VNDOCUMENTOSENCABEZADO_RECIBOPAGO = "recibo_pago";
    public static final String COL_VNDOCUMENTOSENCABEZADO_CONCILIADO = "conciliado";
    public static final String COL_VNDOCUMENTOSENCABEZADO_FECHACONCILIACION = "fecha_conciliacion";
    public static final String COL_VNDOCUMENTOSENCABEZADO_REFERENCIACONCILIACION = "referencia_conciliacion";
    public static final String COL_VNDOCUMENTOSENCABEZADO_EXISTEACLARACION = "existe_aclaracion";
    public static final String COL_VNDOCUMENTOSENCABEZADO_PERSONADEPOSITO = "persona_deposito";
    public static final String COL_VNDOCUMENTOSENCABEZADO_DOCTORESPALDO = "docto_respaldo";
    public static final String COL_VNDOCUMENTOSENCABEZADO_CVEUSUARIOCONCILIACION = "cve_usuario_conciliacion";
    public static final String COL_VNDOCUMENTOSENCABEZADO_AUDITORIA = "auditoria";
    public static final String COL_VNDOCUMENTOSENCABEZADO_COMENTARIOSAUDITORIA = "comentarios_auditoria";
    public static final String COL_VNDOCUMENTOSENCABEZADO_COMENTARIOSOTROS = "comentarios_otros";
    public static final String COL_VNDOCUMENTOSENCABEZADO_CVEUSUARIODESCONCILIACION = "cve_usuario_desconciliacion";
    public static final String COL_VNDOCUMENTOSENCABEZADO_COMENTARIOSDESCONCILIACION = "comentarios_desconciliacion";
    public static final String COL_VNDOCUMENTOSENCABEZADO_IEPS3 = "ieps_3";
    public static final String COL_VNDOCUMENTOSENCABEZADO_IEPS35 = "ieps_3_5";
    public static final String COL_VNDOCUMENTOSENCABEZADO_TOTALIEPS = "total_ieps";
    public static final String COL_VNDOCUMENTOSENCABEZADO_IEPS6 = "ieps_6";
    public static final String COL_VNDOCUMENTOSENCABEZADO_IEPS7 = "ieps_7";
    public static final String COL_VNDOCUMENTOSENCABEZADO_LATITUDE = "latitude";
    public static final String COL_VNDOCUMENTOSENCABEZADO_LONGITUDE = "longitude";
    public static final String COL_VNDOCUMENTOSENCABEZADO_TIPOCOBRANZA = "tipo_cobranza";

    // TAG JSON de vn_documentos_encabezado +++++++++++++++++++++++++++++++++++++++++++++++++++++++
    public static final String TAG_VNDOCUMENTOSENCABEZADO_CVECOMPANIA = "cve_compania";
    public static final String TAG_VNDOCUMENTOSENCABEZADO_CVEDOCUMENTO = "cve_documento";
    public static final String TAG_VNDOCUMENTOSENCABEZADO_NUMDOCUMENTO = "num_documento";
    public static final String TAG_VNDOCUMENTOSENCABEZADO_FECHADOCUMENTO = "fecha_documento";
    public static final String TAG_VNDOCUMENTOSENCABEZADO_FECHAREGISTRO = "fecha_registro";
    public static final String TAG_VNDOCUMENTOSENCABEZADO_TIPODOCUMENTO = "tipo_documento";
    public static final String TAG_VNDOCUMENTOSENCABEZADO_SUMA = "suma";
    public static final String TAG_VNDOCUMENTOSENCABEZADO_DESCUENTO = "descuento";
    public static final String TAG_VNDOCUMENTOSENCABEZADO_SUBTOTAL = "subtotal";
    public static final String TAG_VNDOCUMENTOSENCABEZADO_TOTAL = "total";
    public static final String TAG_VNDOCUMENTOSENCABEZADO_CVECLIENTE = "cve_cliente";
    public static final String TAG_VNDOCUMENTOSENCABEZADO_CVEAGENTE = "cve_agente";
    public static final String TAG_VNDOCUMENTOSENCABEZADO_CVEUSUARIO = "cve_usuario";
    public static final String TAG_VNDOCUMENTOSENCABEZADO_CVEMONEDA = "cve_moneda";
    public static final String TAG_VNDOCUMENTOSENCABEZADO_TIPOCAMBIO = "tipo_cambio";
    public static final String TAG_VNDOCUMENTOSENCABEZADO_COMENTARIOS = "comentarios";
    public static final String TAG_VNDOCUMENTOSENCABEZADO_ESTATUS = "estatus";
    public static final String TAG_VNDOCUMENTOSENCABEZADO_TOTALPAGADO = "total_pagado";
    public static final String TAG_VNDOCUMENTOSENCABEZADO_RECIBOPAGO = "recibo_pago";
    public static final String TAG_VNDOCUMENTOSENCABEZADO_CONCILIADO = "conciliado";
    public static final String TAG_VNDOCUMENTOSENCABEZADO_FECHACONCILIACION = "fecha_conciliacion";
    public static final String TAG_VNDOCUMENTOSENCABEZADO_REFERENCIACONCILIACION = "referencia_conciliacion";
    public static final String TAG_VNDOCUMENTOSENCABEZADO_EXISTEACLARACION = "existe_aclaracion";
    public static final String TAG_VNDOCUMENTOSENCABEZADO_PERSONADEPOSITO = "persona_deposito";
    public static final String TAG_VNDOCUMENTOSENCABEZADO_DOCTORESPALDO = "docto_respaldo";
    public static final String TAG_VNDOCUMENTOSENCABEZADO_CVEUSUARIOCONCILIACION = "cve_usuario_conciliacion";
    public static final String TAG_VNDOCUMENTOSENCABEZADO_AUDITORIA = "auditoria";
    public static final String TAG_VNDOCUMENTOSENCABEZADO_COMENTARIOSAUDITORIA = "comentarios_auditoria";
    public static final String TAG_VNDOCUMENTOSENCABEZADO_COMENTARIOSOTROS = "comentarios_otros";
    public static final String TAG_VNDOCUMENTOSENCABEZADO_CVEUSUARIODESCONCILIACION = "cve_usuario_desconciliacion";
    public static final String TAG_VNDOCUMENTOSENCABEZADO_COMENTARIOSDESCONCILIACION = "comentarios_desconciliacion";
    public static final String TAG_VNDOCUMENTOSENCABEZADO_IEPS3 = "ieps_3";
    public static final String TAG_VNDOCUMENTOSENCABEZADO_IEPS35 = "ieps_3_5";
    public static final String TAG_VNDOCUMENTOSENCABEZADO_TOTALIEPS = "total_ieps";
    public static final String TAG_VNDOCUMENTOSENCABEZADO_IEPS6 = "ieps_6";
    public static final String TAG_VNDOCUMENTOSENCABEZADO_IEPS7 = "ieps_7";
    public static final String TAG_VNDOCUMENTOSENCABEZADO_LATITUDE = "latitude";
    public static final String TAG_VNDOCUMENTOSENCABEZADO_LONGITUDE = "longitude";
    public static final String TAG_VNDOCUMENTOSENCABEZADO_TIPOCOBRANZA = "tipo_cobranza";

    // Tabla vn_documentos_patidas ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    public static final String COL_VNDOCUMENTOSPARTIDAS_ID = "_id";
    public static final String COL_VNDOCUMENTOSPARTIDAS_CVECOMPANIA = "cve_compania";
    public static final String COL_VNDOCUMENTOSPARTIDAS_CVEDOCUMENTO = "cve_documento";
    public static final String COL_VNDOCUMENTOSPARTIDAS_NUMDOCUMENTO = "num_documento";
    public static final String COL_VNDOCUMENTOSPARTIDAS_NUMPARTIDA = "num_partida";
    public static final String COL_VNDOCUMENTOSPARTIDAS_CVETIPOPAGO = "cve_tipo_pago";
    public static final String COL_VNDOCUMENTOSPARTIDAS_CVEBANCOEMISOR = "cve_banco_emisor";
    public static final String COL_VNDOCUMENTOSPARTIDAS_CUENTACHEQUECLIENTE = "cuenta_cheque_cliente";
    public static final String COL_VNDOCUMENTOSPARTIDAS_NUMCHEQUE = "num_cheque";
    public static final String COL_VNDOCUMENTOSPARTIDAS_CVEBANCO = "cve_banco";
    public static final String COL_VNDOCUMENTOSPARTIDAS_REFERENCIA = "referencia";
    public static final String COL_VNDOCUMENTOSPARTIDAS_FECHA_BANCO = "fecha_banco";
    public static final String COL_VNDOCUMENTOSPARTIDAS_TOTAL = "total";

    // TAG JSON vn_documentos_partidas ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    public static final String TAG_VNDOCUMENTOSPARTIDAS_CVECOMPANIA = "cve_compania";
    public static final String TAG_VNDOCUMENTOSPARTIDAS_CVEDOCUMENTO = "cve_documento";
    public static final String TAG_VNDOCUMENTOSPARTIDAS_NUMDOCUMENTO = "num_documento";
    public static final String TAG_VNDOCUMENTOSPARTIDAS_NUMPARTIDA = "num_partida";
    public static final String TAG_VNDOCUMENTOSPARTIDAS_CVETIPOPAGO = "cve_tipo_pago";
    public static final String TAG_VNDOCUMENTOSPARTIDAS_CVEBANCOEMISOR = "cve_banco_emisor";
    public static final String TAG_VNDOCUMENTOSPARTIDAS_CUENTACHEQUECLIENTE = "cuenta_cheque_cliente";
    public static final String TAG_VNDOCUMENTOSPARTIDAS_NUMCHEQUE = "num_cheque";
    public static final String TAG_VNDOCUMENTOSPARTIDAS_CVEBANCO = "cve_banco";
    public static final String TAG_VNDOCUMENTOSPARTIDAS_REFERENCIA = "referencia";
    public static final String TAG_VNDOCUMENTOSPARTIDAS_FECHA_BANCO = "fecha_banco";
    public static final String TAG_VNDOCUMENTOSPARTIDAS_TOTAL = "total";

    // Tabla ts_cat_bancos ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    public static final String COL_TSCATBANCOS_ID = "_id";
    public static final String COL_TSCATBANCOS_CVECOMPANIA = "cve_compania";
    public static final String COL_TSCATBANCOS_CVEBANCO = "cve_banco";
    public static final String COL_TSCATBANCOS_NOMBRECORTO = "nombre_corto";
    public static final String COL_TSCATBANCOS_NOMBREBANCO = "nombre_banco";
    public static final String COL_TSCATBANCOS_MOSTRARVENTAS = "mostrar_ventas";
    public static final String COL_TSCATBANCOS_CVE = "cve";

    // TAG JSON ts_cat_bancos +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    public static final String TAG_TSCATBANCOS_CVECOMPANIA = "cve_compania";
    public static final String TAG_TSCATBANCOS_CVEBANCO = "cve_banco";
    public static final String TAG_TSCATBANCOS_NOMBRECORTO = "nombre_corto";
    public static final String TAG_TSCATBANCOS_NOMBREBANCO = "nombre_banco";
    public static final String TAG_TSCATBANCOS_MOSTRARVENTAS = "mostrar_ventas";
    public static final String TAG_TSCATBANCOS_CVE = "cve";

    // Tabla vn_seguimiento_de_pedido /////////////////////////////////////////////////////////////
    public static final String COL_VNSEGUIMIENTODEPEDIDOS_ID = "_id";
    public static final String COL_VNSEGUIMIENTODEPEDIDOS_ALMACEN = "almacen";
    public static final String COL_VNSEGUIMIENTODEPEDIDOS_NUMPEDIDO = "num_pedido";
    public static final String COL_VNSEGUIMIENTODEPEDIDOS_FECHA = "fecha";
    public static final String COL_VNSEGUIMIENTODEPEDIDOS_CVECLIENTE = "cve_cliente";
    public static final String COL_VNSEGUIMIENTODEPEDIDOS_NUMFACTURA = "num_factura";
    public static final String COL_VNSEGUIMIENTODEPEDIDOS_FECHAFACTURA = "fecha_factura";
    public static final String COL_VNSEGUIMIENTODEPEDIDOS_MONTOFACTURA = "monto_factura";
    public static final String COL_VNSEGUIMIENTODEPEDIDOS_MONTOPEDIDO = "monto_pedido";
    public static final String COL_VNSEGUIMIENTODEPEDIDOS_GUIA = "guia";
    public static final String COL_VNSEGUIMIENTODEPEDIDOS_CONDUCTO = "conducto";
    public static final String COL_VNSEGUIMIENTODEPEDIDOS_FECHAGUIA = "fecha_guia";
    public static final String COL_VNSEGUIMIENTODEPEDIDOS_CONFIRMOENVIO = "confirmo_envio";
    public static final String COL_VNSEGUIMIENTODEPEDIDOS_FECHACONFENVIO = "fecha_conf_envio";
    public static final String COL_VNSEGUIMIENTODEPEDIDOS_CONFIRMORECEPCION = "confirmo_recepcion";
    public static final String COL_VNSEGUIMIENTODEPEDIDOS_FECHACONFRECEPCION = "fecha_conf_recepcion";

    // TAG JSON vn_seguimiento_de_pedido /////////////////////////////////////////////////////////////
    public static final String TAG_VNSEGUIMIENTODEPEDIDOS_ALMACEN = "almacen";
    public static final String TAG_VNSEGUIMIENTODEPEDIDOS_NUMPEDIDO = "num_pedido";
    public static final String TAG_VNSEGUIMIENTODEPEDIDOS_FECHA = "fecha";
    public static final String TAG_VNSEGUIMIENTODEPEDIDOS_CVECLIENTE = "cve_cliente";
    public static final String TAG_VNSEGUIMIENTODEPEDIDOS_NUMFACTURA = "num_factura";
    public static final String TAG_VNSEGUIMIENTODEPEDIDOS_FECHAFACTURA = "fecha_factura";
    public static final String TAG_VNSEGUIMIENTODEPEDIDOS_MONTOFACTURA = "monto_factura";
    public static final String TAG_VNSEGUIMIENTODEPEDIDOS_MONTOPEDIDO = "monto_pedido";
    public static final String TAG_VNSEGUIMIENTODEPEDIDOS_GUIA = "guia";
    public static final String TAG_VNSEGUIMIENTODEPEDIDOS_CONDUCTO = "conducto";
    public static final String TAG_VNSEGUIMIENTODEPEDIDOS_FECHAGUIA = "fecha_guia";
    public static final String TAG_VNSEGUIMIENTODEPEDIDOS_CONFIRMOENVIO = "confirmo_envio";
    public static final String TAG_VNSEGUIMIENTODEPEDIDOS_FECHACONFENVIO = "fecha_conf_envio";
    public static final String TAG_VNSEGUIMIENTODEPEDIDOS_CONFIRMORECEPCION = "confirmo_recepcion";
    public static final String TAG_VNSEGUIMIENTODEPEDIDOS_FECHACONFRECEPCION = "fecha_conf_recepcion";

    // Tabla vn_cat_agentes /////////////////////////////////////////////////////////////////////////
    public static final String COL_VNCATAGENTES_ID = "_id";
    public static final String COL_VNCATAGENTES_CVEUSUARIO = "cve_usuario";
    public static final String COL_VNCATAGENTES_NOMBRE = "nombre";

    // TAG JSON vn_cat_agentes //////////////////////////////////////////////////////////////////////
    public static final String TAG_VNCATAGENTES_CVEUSUARIO = "cve_usuario";
    public static final String TAG_VNCATAGENTES_NOMBRE = "nombre";

    // Tabla gl_ruta_seleccionada ///////////////////////////////////////////////////////////////////
    public static final String COL_GLRUTASELECCIONADA_ID = "_id";
    public static final String COL_GLRUTASELECCIONADA_NUMRUTA = "num_ruta";
    public static final String COL_GLRUTASELECCIONADA_CVEAGENTE = "cve_agente";

    // Tabla vn_registro_visita /////////////////////////////////////////////////////////////////////
    public static final String COL_VNREGISTROVISITA_ID = "_id";
    public static final String COL_VNREGISTROVISITA_CVECLIENTE = "cve_cliente";
    public static final String COL_VNREGISTROVISITA_COMENTARIOS = "comentarios";
    public static final String COL_VNREGISTROVISITA_FECHAREGISTRO = "fecha_registro";
    public static final String COL_VNREGISTROVISITA_CVEUSUARIO = "cve_usuario";
    public static final String COL_VNREGISTROVISITA_CVECOMPANIA = "cve_compania";
    public static final String COL_VNREGISTROVISITA_LATITUDE = "latitude";
    public static final String COL_VNREGISTROVISITA_LONGITUDE = "longitude";
    public static final String COL_VNREGISTROVISITA_SINCRONIZADO = "sincronizado";
    public static final String COL_VNREGISTROVISITA_TIPO_PROBLEMA="tipo_problema";
    public static final String COL_VNREGISTROVISITA_ULTIMA_VISITA="ultima_visita";

    // TAG JSON vn_registro_visita //////////////////////////////////////////////////////////////////
    public static final String TAG_VNREGISTROVISITA_CVECLIENTE = "cve_cliente";
    public static final String TAG_VNREGISTROVISITA_COMENTARIOS = "comentarios";
    public static final String TAG_VNREGISTROVISITA_FECHAREGISTRO = "fecha_registro";
    public static final String TAG_VNREGISTROVISITA_CVEUSUARIO = "cve_usuario";
    public static final String TAG_VNREGISTROVISITA_CVECOMPANIA = "cve_compania";
    public static final String TAG_VNREGISTROVISITA_LATITUDE = "latitude";
    public static final String TAG_VNREGISTROVISITA_LONGITUDE = "longitude";
    public static final String TAG_VNREGISTROVISITA_SINCRONIZADO = "sincronizado";
    public static final String TAG_VNREGISTROVISITA_TIPO_PROBLEMA="tipo_problema";

    // Tabla gl_marketing ///////////////////////////////////////////////////////////////////////////
    public static final String COL_GLMARKETING_ID = "_id";
    public static final String COL_GLMARKETING_FECHA = "fecha";

    // Tabla vn_seguimiento_de_pagos ////////////////////////////////////////////////////////////////
    public static final String COL_VNSEGUIMIENTODEPAGOS_ID = "_id";
    public static final String COL_VNSEGUIMIENTODEPAGOS_CVECLIENTE = "cve_cliente";
    public static final String COL_VNSEGUIMIENTODEPAGOS_NUMDOCUMENTO = "num_documento";
    public static final String COL_VNSEGUIMIENTODEPAGOS_FECHADOCUMENTO = "fecha_documento";
    public static final String COL_VNSEGUIMIENTODEPAGOS_MONTO = "monto";
    public static final String COL_VNSEGUIMIENTODEPAGOS_CVETIPOPAGO = "cve_tipo_pago";
    public static final String COL_VNSEGUIMIENTODEPAGOS_BANCOER = "bancoer";
    public static final String COL_VNSEGUIMIENTODEPAGOS_REFERENCIA = "referencia";
    public static final String COL_VNSEGUIMIENTODEPAGOS_FECHABANCO = "fecha_banco";
    public static final String COL_VNSEGUIMIENTODEPAGOS_CAPTURO = "capturo";
    public static final String COL_VNSEGUIMIENTODEPAGOS_FECHACONCILIACION = "fecha_conciliacion";

    // TAG JSON Tabla vn_seguimiento_de_pagos ///////////////////////////////////////////////////////
    public static final String TAG_VNSEGUIMIENTODEPAGOS_CVECLIENTE = "cve_cliente";
    public static final String TAG_VNSEGUIMIENTODEPAGOS_NUMDOCUMENTO = "num_documento";
    public static final String TAG_VNSEGUIMIENTODEPAGOS_FECHADOCUMENTO = "fecha_documento";
    public static final String TAG_VNSEGUIMIENTODEPAGOS_MONTO = "monto";
    public static final String TAG_VNSEGUIMIENTODEPAGOS_CVETIPOPAGO = "cve_tipo_pago";
    public static final String TAG_VNSEGUIMIENTODEPAGOS_BANCOER = "bancoer";
    public static final String TAG_VNSEGUIMIENTODEPAGOS_REFERENCIA = "referencia";
    public static final String TAG_VNSEGUIMIENTODEPAGOS_FECHABANCO = "fecha_banco";
    public static final String TAG_VNSEGUIMIENTODEPAGOS_CAPTURO = "capturo";
    public static final String TAG_VNSEGUIMIENTODEPAGOS_FECHACONCILIACION = "fecha_conciliacion";

    // Tabla vn_programa_rutas_semanales ////////////////////////////////////////////////////////////
    public static final String COL_VNPROGRAMARUTASSEMANALES_ID = "_id";
    public static final String COL_VNPROGRAMARUTASSEMANALES_FECHARUTA = "fecha_ruta";
    public static final String COL_VNPROGRAMARUTASSEMANALES_NUMERODIA = "numero_dia";
    public static final String COL_VNPROGRAMARUTASSEMANALES_CVECLIENTE = "cve_cliente";
    public static final String COL_VNPROGRAMARUTASSEMANALES_POBLACION = "poblacion";
    public static final String COL_VNPROGRAMARUTASSEMANALES_ESTADO = "estado";
    public static final String COL_VNPROGRAMARUTASSEMANALES_VTAVET = "vta_vet";
    public static final String COL_VNPROGRAMARUTASSEMANALES_FECHAVTAVET = "fecha_vta_vet";
    public static final String COL_VNPROGRAMARUTASSEMANALES_VTAAGR = "vta_agr";
    public static final String COL_VNPROGRAMARUTASSEMANALES_FECHAVTAAGR = "fecha_vta_agr";
    /*
    public static final String COL_VNPROGRAMARUTASSEMANALES_COBVET = "cob_vet";
    public static final String COL_VNPROGRAMARUTASSEMANALES_FECHACOBVET = "fecha_cob_vet";
    public static final String COL_VNPROGRAMARUTASSEMANALES_COBAGR = "cob_agr";
    public static final String COL_VNPROGRAMARUTASSEMANALES_FECHACOBAGR= "fecha_cob_agr";
    */
    public static final String COL_VNPROGRAMARUTASSEMANALES_SALDO= "saldo";
    public static final String COL_VNPROGRAMARUTASSEMANALES_SALDOVIEJO= "saldo_viejo";
    public static final String COL_VNPROGRAMARUTASSEMANALES_DIASSALDOVIEJO= "dias_saldo_viejo";
    public static final String COL_VNPROGRAMARUTASSEMANALES_CANTIDADADULTO= "cantidad_adulto";
    /*
    public static final String COL_VNPROGRAMARUTASSEMANALES_FECHAADULTO= "fecha_adulto";
    */
    public static final String COL_VNPROGRAMARUTASSEMANALES_CANTIDADPUPPY= "cantidad_puppy";
    /*
    public static final String COL_VNPROGRAMARUTASSEMANALES_FECHAPUPPY= "fecha_puppy";
    */
    public static final String COL_VNPROGRAMARUTASSEMANALES_ORDEN= "orden";
    public static final String COL_VNPROGRAMARUTASSEMANALES_HORAPRIMERAVISITA= "hora_primera_visita";

    // TAG JSON Tabla vn_programa_rutas_semanales ////////////////////////////////////////////////////////////
    public static final String TAG_VNPROGRAMARUTASSEMANALES_FECHARUTA = "fecha_ruta";
    public static final String TAG_VNPROGRAMARUTASSEMANALES_NUMERODIA = "numero_dia";
    public static final String TAG_VNPROGRAMARUTASSEMANALES_CVECLIENTE = "cve_cliente";
    public static final String TAG_VNPROGRAMARUTASSEMANALES_POBLACION = "poblacion";
    public static final String TAG_VNPROGRAMARUTASSEMANALES_ESTADO = "estado";
    public static final String TAG_VNPROGRAMARUTASSEMANALES_VTAVET = "vta_vet";
    public static final String TAG_VNPROGRAMARUTASSEMANALES_FECHAVTAVET = "fecha_vta_vet";
    public static final String TAG_VNPROGRAMARUTASSEMANALES_VTAAGR = "vta_agr";
    public static final String TAG_VNPROGRAMARUTASSEMANALES_FECHAVTAAGR = "fecha_vta_agr";
    /*
    public static final String TAG_VNPROGRAMARUTASSEMANALES_COBVET = "cob_vet";
    public static final String TAG_VNPROGRAMARUTASSEMANALES_FECHACOBVET = "fecha_cob_vet";
    public static final String TAG_VNPROGRAMARUTASSEMANALES_COBAGR = "cob_agr";
    public static final String TAG_VNPROGRAMARUTASSEMANALES_FECHACOBAGR= "fecha_cob_agr";
    */
    public static final String TAG_VNPROGRAMARUTASSEMANALES_SALDO= "saldo";
    public static final String TAG_VNPROGRAMARUTASSEMANALES_SALDOVIEJO= "saldo_viejo";
    public static final String TAG_VNPROGRAMARUTASSEMANALES_DIASSALDOVIEJO= "dias_saldo_viejo";
    public static final String TAG_VNPROGRAMARUTASSEMANALES_CANTIDADADULTO= "cantidad_adulto";
    /*
    public static final String TAG_VNPROGRAMARUTASSEMANALES_FECHAADULTO= "fecha_adulto";
    */
    public static final String TAG_VNPROGRAMARUTASSEMANALES_CANTIDADPUPPY= "cantidad_puppy";
    /*
    public static final String TAG_VNPROGRAMARUTASSEMANALES_FECHAPUPPY= "fecha_puppy";
    */
    public static final String TAG_VNPROGRAMARUTASSEMANALES_ORDEN= "orden";
    public static final String TAG_VNPROGRAMARUTASSEMANALES_HORAPRIMERAVISITA= "hora_primera_visita";



    ////////TABLA PARA LOS ESTADOS
    public static final String COL_GLCATENTIDADES_ID="_id";
    public static final String COL_GLCATENTIDADES_CVE_ENTIDAD="cve_entidad";
    public static final String COL_GLCATENTIDADES_NOMBRE_ENTIDAD="nombre_entidad";

    ////////TAG PARA LOS ESTADOS
    public static final String TAG_GLCATENTIDADES_CVE_ENTIDAD="cve_entidad";
    public static final String TAG_GLCATENTIDADES_NOMBRE_ENTIDAD="nombre_entidad";

    ////////TABLA PARA LAS LOCALIDADES
    public static final String COL_GLCATLOCALIDADES_ID="_id";
    public static final String COL_GLCATLOCALIDADES_CVE_LOCALIDAD="cve_localidad";
    public static final String COL_GLCATLOCALIDADES_CVE_ENTIDAD="cve_entidad";
    public static final String COL_GLCATLOCALIDADES_NOMBRE_LOCALIDAD="nombre_localidad";

    ////////TAG PARA LAS LOCALIDADES
    public static final String TAG_GLCATLOCALIDADES_CVE_LOCALIDAD="cve_localidad";
    public static final String TAG_GLCATLOCALIDADES_CVE_ENTIDAD="cve_entidad";
    public static final String TAG_GLCATLOCALIDADES_NOMBRE_LOCALIDAD="nombre_localidad";

    ///////TABLA PARA EL SEGUIMIENTO A CLIENTES
    //campos del servidor Biochem
    public static final String COL_VNCLIENTESSEGUIMIENTO_ID="_id";
    public static final String COL_VNCLIENTESSEGUIMIENTO_CVE_COMPANIA="cve_compania";
    public static final String COL_VNCLIENTESSEGUIMIENTO_FECHA_REGISTRO="fecha_registro";
    public static final String COL_VNCLIENTESSEGUIMIENTO_CVE_USUARIO_CAPTURA="cve_usuario_captura";
    public static final String COL_VNCLIENTESSEGUIMIENTO_NOMBRE_CLIENTE="nombre_cliente";
    public static final String COL_VNCLIENTESSEGUIMIENTO_RFC="rfc";
    public static final String COL_VNCLIENTESSEGUIMIENTO_DOMICILIO="domicilio";
    public static final String COL_VNCLIENTESSEGUIMIENTO_COLONIA="colonia";
    public static final String COL_VNCLIENTESSEGUIMIENTO_CP="cp";
    public static final String COL_VNCLIENTESSEGUIMIENTO_TELEFONO="telefono";
    public static final String COL_VNCLIENTESSEGUIMIENTO_EMAIL="email";
    public static final String COL_VNCLIENTESSEGUIMIENTO_TIPO_CONTRIBUYENTE="tipo_contribuyente";
    public static final String COL_VNCLIENTESSEGUIMIENTO_ENTIDAD="entidad";
    public static final String COL_VNCLIENTESSEGUIMIENTO_LOCALIDAD="localidad";
    public static final String COL_VNCLIENTESSEGUIMIENTO_LATITUDE="latitude";
    public static final String COL_VNCLIENTESSEGUIMIENTO_LONGITUDE="longitude";
    public static final String COL_VNCLIENTESSEGUIMIENTO_SINCRONIZADO="sincronizado";

    //TAGS JSON
    /*
    //campos del servidor Biochem
    public static final String TAG_VNCLIENTESSEGUIMIENTO_ID="_id";
    public static final String TAG_VNCLIENTESSEGUIMIENTO_CVE_COMPANIA="cve_compania";
    public static final String TAG_VNCLIENTESSEGUIMIENTO_CVE_CLIENTE="cve_cliente";
    public static final String TAG_VNCLIENTESSEGUIMIENTO_SOLICITUD="solicitud";
    public static final String TAG_VNCLIENTESSEGUIMIENTO_ATENCION_BIOCHEM="atencion_biochem";
    public static final String TAG_VNCLIENTESSEGUIMIENTO_ENVIO_A="envio_a";
    public static final String TAG_VNCLIENTESSEGUIMIENTO_FECHA_REGISTRO="fecha_registro";
    public static final String TAG_VNCLIENTESSEGUIMIENTO_CVE_USUARIO_CAPTURA="cve_usuario_captura";
    public static final String TAG_VNCLIENTESSEGUIMIENTO_CC="cc";
    public static final String TAG_VNCLIENTESSEGUIMIENTO_CC2="cc2";

    //campos de la aplicacion
    public static final String TAG_VNCLIENTESSEGUIMIENTO_NOMBRE_CLIENTE="nombre_cliente";
    public static final String TAG_VNCLIENTESSEGUIMIENTO_RFC="rfc";
    public static final String TAG_VNCLIENTESSEGUIMIENTO_DOMICILIO="domicilio";
    public static final String TAG_VNCLIENTESSEGUIMIENTO_COLONIA="colonia";
    public static final String TAG_VNCLIENTESSEGUIMIENTO_CP="cp";
    public static final String TAG_VNCLIENTESSEGUIMIENTO_TELEFONO="telefono";
    public static final String TAG_VNCLIENTESSEGUIMIENTO_EMAIL="email";
    public static final String TAG_VNCLIENTESSEGUIMIENTO_TIPO_CONTRIBUYENTE="tipo_contribuyente";
    public static final String TAG_VNCLIENTESSEGUIMIENTO_ENTIDAD="entidad";
    public static final String TAG_VNCLIENTESSEGUIMIENTO_LOCALIDAD="localidad";
    public static final String TAG_VNCLIENTESSEGUIMIENTO_LATITUDE="latitude";
    public static final String TAG_VNCLIENTESSEGUIMIENTO_LONGITUDE="longitude";
    public static final String TAG_VNCLIENTESSEGUIMIENTO_SINCRONIZADO="sincronizado";
     */

    // Tabla ct_reserva ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    /*public static final String COL_CTRESERVA_ID = "_id";
    public static final String COL_CTRESERVA_UUID = "uuid";
    public static final String COL_CTRESERVA_RFC = "rfc";
    public static final String COL_CTRESERVA_TOTAL = "total";
    public static final String COL_CTRESERVA_FECHAEXP = "fecha_exp";
    public static final String COL_CTRESERVA_NOMBREPROVEEDOR = "nombre_proveedor";
    public static final String COL_CTRESERVA_IDRESERVA = "id_reserva";

    // TAG ct_reserva +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    public static final String TAG_CTRESERVA_UUID = "uuid";
    public static final String TAG_CTRESERVA_RFC = "rfc";
    public static final String TAG_CTRESERVA_TOTAL = "total";
    public static final String TAG_CTRESERVA_FECHAEXP = "fecha_exp";
    public static final String TAG_CTRESERVA_NOMBREPROVEEDOR = "nombre_proveedor";
    public static final String TAG_CTRESERVA_IDRESERVA = "id_reserva";*/



    // Tabla vn_politicas +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    public static final String COL_VNPOLITICAS_ID="_id";
    public static final String COL_VNPOLITICAS_ID_POLITICA="id_politica";
    public static final String COL_VNPOLITICAS_TITULO="titulo";
    public static final String COL_VNPOLITICAS_ID_IMAGEN="id_imagen";
    public static final String COL_VNPOLITICAS_IMAGEN="imagen";

    // TAG vn_politicas +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    public static final String TAG_VNPOLITICAS_ID_POLITICA="id_politica";
    public static final String TAG_VNPOLITICAS_TITULO="titulo";
    public static final String TAG_VNPOLITICAS_ID_IMAGEN="id_imagen";
    public static final String TAG_VNPOLITICAS_IMAGEN="imagen";

    // En el contexto de la clase genera la Base de datos verificando la versi—n. En el caso de la base creada con
    // Esta versi—n ya registrada no efectua operacion ninguna
    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    // Se ajusta el onCreate para ejecutar los querys de creaci—n de las tablas
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        // Bloque para la tabla local gl_bitacora_accesos
        String CREATE_GL_BITACORA_ACCESOS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_GL_BITACORA_ACCESOS + "(" +
                COL_GLBITACORAACCESOS_ID + " INTEGER PRIMARY KEY, " +
                COL_GLBITACORAACCESOS_CVEUSUARIO + " TEXT, " +
                COL_GLBITACORAACCESOS_FECHAREGISTRO + " TEXT " +
                ")";
        db.execSQL(CREATE_GL_BITACORA_ACCESOS_TABLE);

        // Bloque TABLE_VN_CAT_CLIENTES = "vn_cat_clientes"
        String CREATE_VN_CAT_CLIENTES_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_VN_CAT_CLIENTES + "(" +
                COL_VNCATCLIENTES_ID + " INTEGER PRIMARY KEY, " +
                COL_VNCATCLIENTES_CVEUSUARIO + " TEXT, " +
                COL_VNCATCLIENTES_NOMBRE + " TEXT, " +
                COL_VNCATCLIENTES_RFC + " TEXT, " +
                COL_VNCATCLIENTES_TIPOCONTRIBUYENTE + " TEXT, " +
                COL_VNCATCLIENTES_CALLEDOMICILIO + " TEXT, " +
                COL_VNCATCLIENTES_COLONIA + " TEXT, " +
                COL_VNCATCLIENTES_CODIGOPOSTAL + " TEXT, " +
                COL_VNCATCLIENTES_TELEFONOS + " TEXT, " +
                COL_VNCATCLIENTES_FAX + " TEXT, " +
                COL_VNCATCLIENTES_EMAIL + " TEXT, " +
                COL_VNCATCLIENTES_DOMICILIOBODEGA + " TEXT, " +
                COL_VNCATCLIENTES_REPRESENTANTELEGAL  + " TEXT, " +
                COL_VNCATCLIENTES_ATENCIONVENTAS + " TEXT, " +
                COL_VNCATCLIENTES_ATENCIONPAGOS + " TEXT, " +
                COL_VNCATCLIENTES_CVELOCALIDAD + " INTEGER, " +
                COL_VNCATCLIENTES_COMENTARIOS + " BLOB, " +
                COL_VNCATCLIENTES_CVEUSUARIOVENTAS + " TEXT, " +
                COL_VNCATCLIENTES_CONTRASENA + " BLOB, " +
                COL_VNCATCLIENTES_ENTRE + " TEXT, " +
                COL_VNCATCLIENTES_ENTRE2 + " TEXT, " +
                COL_VNCATCLIENTES_ESTATUS + " INTEGER, " +
                COL_VNCATCLIENTES_DOMICILIOENTREGA + " TEXT, " +
                COL_VNCATCLIENTES_ULTIMAACTUALIZACION + " TEXT, " +
                COL_VNCATCLIENTES_CVEUSUARIOACTUALIZACION + " TEXT, " +
                COL_VNCATCLIENTES_LOCALIZACION + " TEXT, " +
                COL_VNCATCLIENTES_MOROSO + " INTEGER, " +
                COL_VNCATCLIENTES_COMENTARIOSCLIENTE + " TEXT, " +
                COL_VNCATCLIENTES_HISTORIALCOMENTARIOS + " TEXT, " +
                COL_VNCATCLIENTES_OPINION_SERVICIO + " TEXT, " +
                COL_VNCATCLIENTES_COMENTARIOS_SERVICIO + " TEXT, " +
                COL_VNCATCLIENTES_FECHAREGISTRO + " TEXT, " +
                COL_VNCATCLIENTES_ESTATUSMOVIMIENTOS + " TEXT " +
                ")";
        db.execSQL(CREATE_VN_CAT_CLIENTES_TABLE);

        // Bloque TABLE_IN_CAT_PRODUCTOS = "in_cat_productos"
        String CREATE_IN_CAT_PRODUCTOS_TABLE = "CREATE TABLE  IF NOT EXISTS " + TABLE_IN_CAT_PRODUCTOS + "(" +
                COL_INCATPRODUCTOS_ID + " INTEGER PRIMARY KEY, " +
                COL_INCATPRODUCTOS_CVECATPRODUCTO + " INTEGER, " +
                COL_INCATPRODUCTOS_CVECOMPANIA + " TEXT, " +
                COL_INCATPRODUCTOS_CVE_GRUPO + " TEXT, " +
                COL_INCATPRODUCTOS_CVESUBGRUPO + " TEXT, " +
                COL_INCATPRODUCTOS_CVE_FAMILIA + " TEXT, " +
                COL_INCATPRODUCTOS_CVEPRODUCTO + " TEXT, " +
                COL_INCATPRODUCTOS_ORIGEN + " TEXT, " +
                COL_INCATPRODUCTOS_NOMPRODUCTO + " TEXT, " +
                COL_INCATPRODUCTOS_CVEPOSICIONFINANCIERA + " TEXT, " +
                COL_INCATPRODUCTOS_CVECENTROCOSTOELABORA + " TEXT, " +
                COL_INCATPRODUCTOS_CVECLASIFICACION + " TEXT, " +
                COL_INCATPRODUCTOS_CVEUNIDADMEDIDA + " TEXT, " +
                COL_INCATPRODUCTOS_CVEPROVEEDORPREFERENTE + " INTEGER, " +
                COL_INCATPRODUCTOS_ULTIMOCOSTO + " REAL, " +
                COL_INCATPRODUCTOS_COSTOPROMEDIO + " REAL, " +
                COL_INCATPRODUCTOS_COSTOESTANDAR + " REAL, " +
                COL_INCATPRODUCTOS_PRECIOUNITARIO + " REAL, " +
                COL_INCATPRODUCTOS_PORCENTAJEIMPUESTO + " REAL, " +
                COL_INCATPRODUCTOS_PIEZASPORPAQUETE + " INTEGER, " +
                COL_INCATPRODUCTOS_VENTAMINIMA + " INTEGER, " +
                COL_INCATPRODUCTOS_PIEZASPORLOTE + " INTEGER, " +
                COL_INCATPRODUCTOS_MINPIEZASPORLOTE + " INTEGER, " +
                COL_INCATPRODUCTOS_DIASFABRICACION + " INTEGER, " +
                COL_INCATPRODUCTOS_PESOUNITARIO + " REAL, " +
                COL_INCATPRODUCTOS_ESVENTA + " INTEGER, " +
                COL_INCATPRODUCTOS_CONSIDERARMARGEN + " INTEGER, " +
                COL_INCATPRODUCTOS_ESTATUS + " TEXT, " +
                COL_INCATPRODUCTOS_CODIGOBARRASJPG + " BLOB, " +
                COL_INCATPRODUCTOS_CODIGOBARRASCHR + " TEXT, " +
                COL_INCATPRODUCTOS_MINIMO + " REAL, " +
                COL_INCATPRODUCTOS_MAXIMO + " REAL, " +
                COL_INCATPRODUCTOS_REORDEN + " REAL, " +
                COL_INCATPRODUCTOS_COMENTARIOS + " TEXT, " +
                COL_INCATPRODUCTOS_USOS + " TEXT, " +
                COL_INCATPRODUCTOS_DOSIS + " BLOB, " +
                COL_INCATPRODUCTOS_VENTAJAS + " TEXT, " +
                COL_INCATPRODUCTOS_FORMULA + " BLOB, " +
                COL_INCATPRODUCTOS_IMAGEN + " TEXT, " +
                COL_INCATPRODUCTOS_PIEZASLOGIS + " INTEGER, " +
                COL_INCATPRODUCTOS_ABREVIATURA + " TEXT, " +
                COL_INCATPRODUCTOS_IEPS + " TEXT, " +
                COL_INCATPRODUCTOS_INDICACIONES + " BLOB, " +
                COL_INCATPRODUCTOS_ESPECIESCULTIVOS + " TEXT, " +
                COL_INCATPRODUCTOS_IDCATEGORIA + " INTEGER, " +
                COL_INCATPRODUCTOS_IMAGENENCABEZADO + " TEXT, " +
                COL_INCATPRODUCTOS_PORCENTAJEPAQUETE + " REAL, " +
                COL_INCATPRODUCTOS_INFTECBASICA + " TEXT, " +
                COL_INCATPRODUCTOS_INFCOMERCIAL + " TEXT, " +
                COL_INCATPRODUCTOS_INFTECCOMPLETA + " TEXT, " +
                COL_INCATPRODUCTOS_INTRODUCCION + " TEXT " +
                ")";
        db.execSQL(CREATE_IN_CAT_PRODUCTOS_TABLE);

        // Bloque:  TABLE_VN_CAT_CONDUCTOS = "vn_cat_conductos"
        String CREATE_VN_CAT_CONDUCTOS_TABLE = "CREATE TABLE  IF NOT EXISTS " + TABLE_VN_CAT_CONDUCTOS + "(" +
                COL_VNCATCONDUCTOS_ID + " INTEGER PRIMARY KEY, " +
                COL_VNCATCONDUCTOS_CVECONDUCTO + " INTEGER, " +
                COL_VNCATCONDUCTOS_CVE_COMPANIA + " TEXT, " +
                COL_VNCATCONDUCTOS_NOMBRECONDUCTO + " TEXT, " +
                COL_VNCATCONDUCTOS_MOSTRAR + " INTEGER, " +
                COL_VNCATCONDUCTOS_CONTRATO + " INTEGER, " +
                COL_VNCATCONDUCTOS_CONCEPTO + " INTEGER " +
                ")";
        db.execSQL(CREATE_VN_CAT_CONDUCTOS_TABLE);

        // Bloque: TABLE_VN_PEDIDOS_ENCABEZADO = "vn_pedidos_encabezado"
        String CREATE_VN_PEDIDOS_ENCABEZADO_TABLE = "CREATE TABLE  IF NOT EXISTS " + TABLE_VN_PEDIDOS_ENCABEZADO + "(" +
                COL_VNPEDIDOSENCABEZADO_ID + " INTEGER PRIMARY KEY, " +
                COL_VNPEDIDOSENCABEZADO_CVECOMPANIA + " TEXT, " +
                COL_VNPEDIDOSENCABEZADO_NUMPEDIDO + " INTEGER, " +
                COL_VNPEDIDOSENCABEZADO_NUM_ANEXO + " INTEGER, " +
                COL_VNPEDIDOSENCABEZADO_TIPOPEDIDO + " TEXT, " +
                COL_VNPEDIDOSENCABEZADO_ESTATUS + " TEXT, " +
                COL_VNPEDIDOSENCABEZADO_FECHAPEDIDO + " TEXT, " +
                COL_VNPEDIDOSENCABEZADO_CVEMONEDA + " TEXT, " +
                COL_VNPEDIDOSENCABEZADO_CVECLIENTE + " TEXT, " +
                COL_VNPEDIDOSENCABEZADO_CVEAGENTE + " TEXT, " +
                COL_VNPEDIDOSENCABEZADO_CVEUSUARIOCAPTURA + " TEXT, " +
                COL_VNPEDIDOSENCABEZADO_FECHAREQUERIMIENTO + " TEXT, " +
                COL_VNPEDIDOSENCABEZADO_SUMA + " REAL, " +
                COL_VNPEDIDOSENCABEZADO_DESCUENTO + " REAL, " +
                COL_VNPEDIDOSENCABEZADO_SUBTOTAL + " REAL, " +
                COL_VNPEDIDOSENCABEZADO_IMPUESTO + " REAL, " +
                COL_VNPEDIDOSENCABEZADO_TOTAL + " REAL, " +
                COL_VNPEDIDOSENCABEZADO_COMENTARIOS + " BLOB, " +
                COL_VNPEDIDOSENCABEZADO_COMENTARIOSCXC + " BLOB, " +
                COL_VNPEDIDOSENCABEZADO_ASISTENCIA + " INTEGER, " +
                COL_VNPEDIDOSENCABEZADO_TOTALIEPS + " TEXT, " +
                COL_VNPEDIDOSENCABEZADO_IEPS3 + " TEXT, " +
                COL_VNPEDIDOSENCABEZADO_IEPS35 + " TEXT, " +
                COL_VNPEDIDOSENCABEZADO_FAMILIA + " TEXT, " +
                COL_VNPEDIDOSENCABEZADO_VERIFICADO + " TEXT, " +
                COL_VNPEDIDOSENCABEZADO_CVEUSUARIOVERIFICADO + " TEXT, " +
                COL_VNPEDIDOSENCABEZADO_FECHAVERIFICACION + " TEXT, " +
                COL_VNPEDIDOSENCABEZADO_FECHAAUTORIZACION + " TEXT, " +
                COL_VNPEDIDOSENCABEZADO_COMENTARIOSLIB + " BLOB, " +
                COL_VNPEDIDOSENCABEZADO_PORCENTAJEGENERAL + " REAL, " +
                COL_VNPEDIDOSENCABEZADO_PEDIDOMESANTERIOR + " TEXT, " +
                COL_VNPEDIDOSENCABEZADO_SURTIDO + " TEXT, " +
                COL_VNPEDIDOSENCABEZADO_COMENTARIOSSURTIDO + " BLOB, " +
                COL_VNPEDIDOSENCABEZADO_USUARIOSURTIO + " TEXT, " +
                COL_VNPEDIDOSENCABEZADO_FECHASURTIDO + " TEXT, " +
                COL_VNPEDIDOSENCABEZADO_IMPRESO + " TEXT, " +
                COL_VNPEDIDOSENCABEZADO_LATITUDE + " TEXT, " +
                COL_VNPEDIDOSENCABEZADO_LONGITUDE + " TEXT, " +
                COL_VNPEDIDOSENCABEZADO_FECHADEPAGO + " TEXT " +
                ")";
        db.execSQL(CREATE_VN_PEDIDOS_ENCABEZADO_TABLE);

        // Bloque: TABLE_VN_PEDIDOS_PARTIDAS = "vn_pedidos_partidas"
        String CREATE_VN_PEDIDOS_PARTIDAS_TABLE = "CREATE TABLE  IF NOT EXISTS " + TABLE_VN_PEDIDOS_PARTIDAS + "(" +
                COL_VNPEDIDOSPARTIDAS_ID + " INTEGER PRIMARY KEY, " +
                COL_VNPEDIDOSPARTIDAS_CVECOMPANIA + " TEXT, " +
                COL_VNPEDIDOSPARTIDAS_NUMPEDIDO + " INTEGER, " +
                COL_VNPEDIDOSPARTIDAS_NUMPARTIDA + " INTEGER, " +
                COL_VNPEDIDOSPARTIDAS_CVECATPRODUCTO + " INTEGER, " +
                COL_VNPEDIDOSPARTIDAS_CANTIDAD + " REAL, " +
                COL_VNPEDIDOSPARTIDAS_CANTIDADENTREGADA + " REAL, " +
                COL_VNPEDIDOSPARTIDAS_PRECIOUNITARIO + " REAL, " +
                COL_VNPEDIDOSPARTIDAS_PORCENTAJEDESCUENTO + " REAL, " +
                COL_VNPEDIDOSPARTIDAS_PORCENTAJEIMPUESTO + " REAL, " +
                COL_VNPEDIDOSPARTIDAS_PORCENTAJECOMISION + " REAL, " +
                COL_VNPEDIDOSPARTIDAS_SUMA + " REAL, " +
                COL_VNPEDIDOSPARTIDAS_DESCUENTO + " REAL, " +
                COL_VNPEDIDOSPARTIDAS_SUBTOTAL + " REAL, " +
                COL_VNPEDIDOSPARTIDAS_IMPUESTO + " REAL, " +
                COL_VNPEDIDOSPARTIDAS_TOTAL + " REAL, " +
                COL_VNPEDIDOSPARTIDAS_DOCUMENTOREFERENCIA + " INTEGER, " +
                COL_VNPEDIDOSPARTIDAS_TIPODOCUMENTOREFERENCIA + " TEXT, " +
                COL_VNPEDIDOSPARTIDAS_PARTIDADOCUMENTOREFERENCIA + " INTEGER, " +
                COL_VNPEDIDOSPARTIDAS_CVECENTROCOSTO + " TEXT, " +
                COL_VNPEDIDOSPARTIDAS_PLANVENTAS + " TEXT, " +
                COL_VNPEDIDOSPARTIDAS_NUMPAQUTE + " INTEGER, " +
                COL_VNPEDIDOSPARTIDAS_CONSIDERARBACKORDER + " INTEGER, " +
                COL_VNPEDIDOSPARTIDAS_MEMBACKORDER + " INTEGER, " +
                COL_VNPEDIDOSPARTIDAS_CVECONDUCTO + " INTEGER, " +
                COL_VNPEDIDOSPARTIDAS_TIPOCONDUCTO + " TEXT, " +
                COL_VNPEDIDOSPARTIDAS_ESTATUS + " TEXT, " +
                COL_VNPEDIDOSPARTIDAS_PORCENTAJECOMISIONPROMOTOR + " REAL, " +
                COL_VNPEDIDOSPARTIDAS_IEPS + " REAL, " +
                COL_VNPEDIDOSPARTIDAS_ESPAQUETE + " INTEGER, " +
                COL_VNPEDIDOSPARTIDAS_DESCRIPCIONCOMPLEMENTARIA + " TEXT " +
                ")";
        db.execSQL(CREATE_VN_PEDIDOS_PARTIDAS_TABLE);

        // Bloque: TABLE_GL_ACCESOS = "gl_accesos"
        String CREATE_GL_ACCESOS_TABLE = "CREATE TABLE  IF NOT EXISTS " + TABLE_GL_ACCESOS + "(" +
                COL_GLACCESOS_ID + " INTEGER PRIMARY KEY, " +
                COL_GLACCESOS_CVEUSUARIO + " TEXT, " +
                COL_GLACCESOS_PASSWORD + " BLOB, " +
                COL_GLACCESOS_TIPOUSUARIO + " TEXT, " +
                COL_GLACCESOS_ESTATUS + " INTEGER, " +
                COL_GLACCESOS_ACTUALIZO_PASSWORD + " INTEGER, " +
                COL_GLACCESOS_ULTIMAACTUALIZACION + " TEXT, " +
                COL_GLACCESOS_IMEI + " TEXT " +
                ")";
        db.execSQL(CREATE_GL_ACCESOS_TABLE);

        // Bloque para la tabla local GL_SYNC
        String CREATE_GL_SYNC_TABLE = "CREATE TABLE  IF NOT EXISTS " + TABLE_GL_SYNC + "(" +
                COL_GLSYNC_ID + " INTEGER PRIMARY KEY, " +
                COL_GLSYNC_CVEUSUARIO + " TEXT, " +
                COL_GLSYNC_FECHASYNC + " TEXT, " +
                COL_GLSYNC_EXITOSYNC + " INTEGER " +
                ")";
        db.execSQL(CREATE_GL_SYNC_TABLE);

        // Bloque para la tabla local de vn_cat_rutas:
        String CREATE_VN_CAT_RUTAS_TABLE = "CREATE TABLE  IF NOT EXISTS " + TABLE_VN_CAT_RUTAS + "(" +
                COL_VNCATRUTAS_ID + " INTEGER PRIMARY KEY, " +
                COL_VNCATRUTAS_CVECOMPANIA + " TEXT, " +
                COL_VNCATRUTAS_NUMRUTA + " INTEGER, " +
                COL_VNCATRUTAS_CVEAGENTE + " TEXT, " +
                COL_VNCATRUTAS_NOMBRERUTA + " TEXT, " +
                COL_VNCATRUTAS_DIASVENCIMIENTOCARTERA + " INTEGER, " +
                COL_VNCATRUTAS_CUOTAVENTA + " REAL, " +
                COL_VNCATRUTAS_CUOTACOBRANZA + " REAL, " +
                COL_VNCATRUTAS_CUOTAMANO + " REAL, " +
                COL_VNCATRUTAS_CUOTAPEDIDOS + " INTEGER, " +
                COL_VNCATRUTAS_DIASINVENTARIO + " INTEGER " +
                ")";
        db.execSQL(CREATE_VN_CAT_RUTAS_TABLE);

        // Bloque para la tabla local in_centros_inventarios:
        String CREATE_IN_CENTROS_INVENTARIOS = "CREATE TABLE  IF NOT EXISTS " + TABLE_IN_CENTROS_INVENTARIOS + "(" +
                COL_INCENTROSINVENTARIOS_ID + " INTEGER PRIMARY KEY, " +
                COL_INCENTROSINVENTARIOS_CVECOMPANIA + " TEXT, " +
                COL_INCENTROSINVENTARIOS_CVECENTROCOSTO + " TEXT, " +
                COL_INCENTROSINVENTARIOS_CVECATPRODUCTO + " INTEGER, " +
                COL_INCENTROSINVENTARIOS_NUMLOTE + " TEXT, " +
                COL_INCENTROSINVENTARIOS_NUMLOTEINTERNO + " TEXT, " +
                COL_INCENTROSINVENTARIOS_EXISTENCIAS + " REAL, " +
                COL_INCENTROSINVENTARIOS_COSTOUNITARIO + " REAL " +
                ")";
        db.execSQL(CREATE_IN_CENTROS_INVENTARIOS);

        // Bloque para la tabla local in_centros_inventarios:
        String CREATE_VN_CAT_AGENTES_PARAMETROS = "CREATE TABLE  IF NOT EXISTS " + TABLE_VN_CAT_AGENTES_PARAMETROS + "(" +
                COL_VNCATAGENTESPARAMETROS_ID + " INTEGER PRIMARY KEY, " +
                COL_VNCATAGENTESPARAMETROS_CVECOMPANIA + " TEXT, " +
                COL_VNCATAGENTESPARAMETROS_CVEAGENTE + " TEXT, " +
                COL_VNCATAGENTESPARAMETROS_CVECENTROCOSTO + " TEXT, " +
                COL_VNCATAGENTESPARAMETROS_AGENTETIPO + " TEXT, " +
                COL_VNCATAGENTESPARAMETROS_GRUPOCOMISION + " TEXT, " +
                COL_VNCATAGENTESPARAMETROS_CONSIGNACION + " TEXT, " +
                COL_VNCATAGENTESPARAMETROS_ESTATUS + " INTEGER " +
                ")";
        db.execSQL(CREATE_VN_CAT_AGENTES_PARAMETROS);

        // Bloque para la tabla local in_centros_inventarios:
        String CREATE_VN_RUTAS_ASOCIACIONES = "CREATE TABLE  IF NOT EXISTS " + TABLE_VN_RUTAS_ASOCIACIONES + "(" +
                COL_VNRUTASASOCIACIONES_ID + " INTEGER PRIMARY KEY, " +
                COL_VNRUTASASOCIACIONES_CVECOMPANIA + " TEXT, " +
                COL_VNCATAGENTESPARAMETROS_CVEAGENTE + " TEXT, " +
                COL_VNRUTASASOCIACIONES_NUMRUTA + " INTEGER, " +
                COL_VNRUTASASOCIACIONES_CVECLIENTE + " TEXT, " +
                COL_VNRUTASASOCIACIONES_DIASVISITA + " TEXT, " +
                COL_VNRUTASASOCIACIONES_CVELOCALIDAD + " TEXT " +
                ")";
        db.execSQL(CREATE_VN_RUTAS_ASOCIACIONES);

        // Bloque para la tabla local vn_paquetes_partidas
        String CREATE_VN_PAQUETES_PARTIDAS = "CREATE TABLE  IF NOT EXISTS " + TABLE_VN_PAQUETES_PARTIDAS + "(" +
                COL_VNPAQUETESPARTIDAS_ID + " INTEGER PRIMARY KEY, " +
                COL_VNPAQUETESPARTIDAS_CVECOMPANIA + " TEXT, " +
                COL_VNPAQUETESPARTIDAS_NUMPAQUETE + " TEXT, " +
                COL_VNPAQUETESPARTIDAS_NUMPARTIDA + " INTEGER, " +
                COL_VNPAQUETESPARTIDAS_CVECATPRODUCTO + " INTEGER, " +
                COL_VNPAQUETESPARTIDAS_CANTIDAD + " REAL, " +
                COL_VNPAQUETESPARTIDAS_PORCENTAJEDESCUENTO + " REAL " +
                ")";
        db.execSQL(CREATE_VN_PAQUETES_PARTIDAS);

        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        // + Bloque de código para las tablas de Pagos de acuerdo a la nueva lista  B.D. 2.0      +
        // + ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

        // Tabla vn_cat_tipos_pago ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        String CREATE_VN_CAT_TIPOS_PAGO = "CREATE TABLE IF NOT EXISTS " + TABLE_VN_CAT_TIPOS_PAGO + "(" +
                COL_VNCATTIPOSPAGO_ID + " INTEGER PRIMARY KEY, " +
                COL_VNCATTIPOSPAGO_CVETIPOPAGO + " INTEGER, " +
                COL_VNCATTIPOSPAGO_NOMBRETIPOPAGO + " TEXT " +
                ")";
        db.execSQL(CREATE_VN_CAT_TIPOS_PAGO);

        // Tabla vn_cat_bancos_clientes +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        String CREATE_VN_CAT_BANCOS_CLIENTES = "CREATE TABLE IF NOT EXISTS " + TABLE_VN_CAT_BANCOS_CLIENTES + "(" +
                COL_VNCATBANCOSCLIENTES_ID + " INTEGER PRIMARY KEY, " +
                COL_VNCATBANCOSCLIENTES_CVEBANCOEMISOR + " TEXT, " +
                COL_VNCATBANCOSCLIENTES_NOMBREBANCO + " TEXT " +
                ")";
        db.execSQL(CREATE_VN_CAT_BANCOS_CLIENTES);


        // Tabla vn_documentos_depositos ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        String CREATE_VN_DOCUMENTOS_DEPOSITOS = "CREATE TABLE IF NOT EXISTS " + TABLE_VN_DOCUMENTOS_DEPOSITOS + "(" +
                COL_VNDOCUMENTOSDEPOSITOS_ID + " INTEGER PRIMARY KEY, " +
                COL_VNDOCUMENTOSDEPOSITOS_PERSONADEPOSITO + " INTEGER, " +
                COL_VNDOCUMENTOSDEPOSITOS_DESCRIPCION + " TEXT " +
                ")";
        db.execSQL(CREATE_VN_DOCUMENTOS_DEPOSITOS);

        // Tabla vn_documentos_respaldos ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        String CREATE_VN_DOCUMENTOS_RESPALDOS = "CREATE TABLE IF NOT EXISTS " + TABLE_VN_DOCUMENTOS_RESPALDOS + "(" +
                COL_VNDOCUMENTOSRESPALDOS_ID + " INTEGER PRIMARY KEY, " +
                COL_VNDOCUMENTOSRESPALDOS_DOCTORESPALDO + " INTEGER, " +
                COL_VNDOCUMENTOSRESPALDOS_DESCRIPCION + " TEXT " +
                ")";
        db.execSQL(CREATE_VN_DOCUMENTOS_RESPALDOS);

        // Tabla vn_documentos_encabezado +++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        String CREATE_VN_DOCUMENTOS_ENCABEZADO = "CREATE TABLE IF NOT EXISTS " + TABLE_VN_DOCUMENTOS_ENCABEZADO + "(" +
                COL_VNDOCUMENTOSENCABEZADO_ID + " INTEGER PRIMARY KEY, " +
                COL_VNDOCUMENTOSENCABEZADO_CVECOMPANIA + " TEXT, " +
                COL_VNDOCUMENTOSENCABEZADO_CVEDOCUMENTO + " TEXT, " +
                COL_VNDOCUMENTOSENCABEZADO_NUMDOCUMENTO + " INTEGER, " +
                COL_VNDOCUMENTOSENCABEZADO_FECHADOCUMENTO + " TEXT, " +
                COL_VNDOCUMENTOSENCABEZADO_FECHAREGISTRO + " TEXT, " +
                COL_VNDOCUMENTOSENCABEZADO_TIPODOCUMENTO + " TEXT, " +
                COL_VNDOCUMENTOSENCABEZADO_SUMA + " DECIMAL, " +
                COL_VNDOCUMENTOSENCABEZADO_DESCUENTO + " DECIMAL, " +
                COL_VNDOCUMENTOSENCABEZADO_SUBTOTAL + " DECIMAL, " +
                COL_VNDOCUMENTOSENCABEZADO_TOTAL + " DECIMAL, " +
                COL_VNDOCUMENTOSENCABEZADO_CVECLIENTE + " TEXT, " +
                COL_VNDOCUMENTOSENCABEZADO_CVEAGENTE + " TEXT, " +
                COL_VNDOCUMENTOSENCABEZADO_CVEUSUARIO + " TEXT, " +
                COL_VNDOCUMENTOSENCABEZADO_CVEMONEDA + " TEXT, " +
                COL_VNDOCUMENTOSENCABEZADO_TIPOCAMBIO + " DECIMAL, " +
                COL_VNDOCUMENTOSENCABEZADO_COMENTARIOS + " TEXT, " +
                COL_VNDOCUMENTOSENCABEZADO_ESTATUS + " TEXT, " +
                COL_VNDOCUMENTOSENCABEZADO_TOTALPAGADO + " DECIMAL, " +
                COL_VNDOCUMENTOSENCABEZADO_RECIBOPAGO + " TEXT, " +
                COL_VNDOCUMENTOSENCABEZADO_CONCILIADO + " INTEGER, " +
                COL_VNDOCUMENTOSENCABEZADO_FECHACONCILIACION + " TEXT, " +
                COL_VNDOCUMENTOSENCABEZADO_REFERENCIACONCILIACION + " INTEGER, " +
                COL_VNDOCUMENTOSENCABEZADO_EXISTEACLARACION + " INTEGER, " +
                COL_VNDOCUMENTOSENCABEZADO_PERSONADEPOSITO + " INTEGER, " +
                COL_VNDOCUMENTOSENCABEZADO_DOCTORESPALDO + " INTEGER, " +
                COL_VNDOCUMENTOSENCABEZADO_CVEUSUARIOCONCILIACION + " TEXT, " +
                COL_VNDOCUMENTOSENCABEZADO_AUDITORIA + " TEXT, " +
                COL_VNDOCUMENTOSENCABEZADO_COMENTARIOSAUDITORIA + " TEXT, " +
                COL_VNDOCUMENTOSENCABEZADO_COMENTARIOSOTROS + " TEXT, " +
                COL_VNDOCUMENTOSENCABEZADO_CVEUSUARIODESCONCILIACION + " TEXT, " +
                COL_VNDOCUMENTOSENCABEZADO_COMENTARIOSDESCONCILIACION + " TEXT, " +
                COL_VNDOCUMENTOSENCABEZADO_IEPS3 + " TEXT, " +
                COL_VNDOCUMENTOSENCABEZADO_IEPS35 + " TEXT, " +
                COL_VNDOCUMENTOSENCABEZADO_TOTALIEPS + " TEXT, " +
                COL_VNDOCUMENTOSENCABEZADO_IEPS6 + " TEXT, " +
                COL_VNDOCUMENTOSENCABEZADO_IEPS7 + " TEXT, " +
                COL_VNDOCUMENTOSENCABEZADO_LATITUDE + " TEXT, " +
                COL_VNDOCUMENTOSENCABEZADO_LONGITUDE + " TEXT, " +
                COL_VNDOCUMENTOSENCABEZADO_TIPOCOBRANZA + " TEXT " +
                ")";
        db.execSQL(CREATE_VN_DOCUMENTOS_ENCABEZADO);

        // Tabla vn_documentos_partidas +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        String CREATE_VN_DOCUMENTOS_PARTIDAS = "CREATE TABLE IF NOT EXISTS " + TABLE_VN_DOCUMENTOS_PARTIDAS + "(" +
                COL_VNDOCUMENTOSPARTIDAS_ID + " INTEGER PRIMARY KEY, " +
                COL_VNDOCUMENTOSPARTIDAS_CVECOMPANIA + " TEXT, " +
                COL_VNDOCUMENTOSPARTIDAS_CVEDOCUMENTO + " TEXT, " +
                COL_VNDOCUMENTOSPARTIDAS_NUMDOCUMENTO + " INTEGER, " +
                COL_VNDOCUMENTOSPARTIDAS_NUMPARTIDA + " INTEGER, " +
                COL_VNDOCUMENTOSPARTIDAS_CVETIPOPAGO + " TEXT, " +
                COL_VNDOCUMENTOSPARTIDAS_CVEBANCOEMISOR + " TEXT, " +
                COL_VNDOCUMENTOSPARTIDAS_CUENTACHEQUECLIENTE + " TEXT, " +
                COL_VNDOCUMENTOSPARTIDAS_NUMCHEQUE + " TEXT, " +
                COL_VNDOCUMENTOSPARTIDAS_CVEBANCO + " TEXT, " +
                COL_VNDOCUMENTOSPARTIDAS_REFERENCIA + " TEXT, " +
                COL_VNDOCUMENTOSPARTIDAS_FECHA_BANCO + " TEXT, " +
                COL_VNDOCUMENTOSPARTIDAS_TOTAL + " DECIMAL " +
                ")";
        db.execSQL(CREATE_VN_DOCUMENTOS_PARTIDAS);

        // Tabla ts_cat_bancos ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        String CREATE_TS_CAT_BANCOS = "CREATE TABLE IF NOT EXISTS " + TABLE_TS_CAT_BANCOS + "(" +
                COL_TSCATBANCOS_ID + " INTEGER PRIMARY KEY, " +
                COL_TSCATBANCOS_CVECOMPANIA + " TEXT, " +
                COL_TSCATBANCOS_CVEBANCO + " TEXT, " +
                COL_TSCATBANCOS_NOMBRECORTO + " TEXT, " +
                COL_TSCATBANCOS_NOMBREBANCO + " TEXT, " +
                COL_TSCATBANCOS_MOSTRARVENTAS + " INTEGER, " +
                COL_TSCATBANCOS_CVE + " TEXT " +
                ")";
        db.execSQL(CREATE_TS_CAT_BANCOS);

        // Tabla vn_seguimiento_de_pedidos ////////////////////////////////////////////////////////
        String CREATE_VN_SEGUIMIENTO_DE_PEDIDOS = "CREATE TABLE IF NOT EXISTS " + TABLE_VN_SEGUIMIENTO_DE_PEDIDOS + "(" +
                COL_VNSEGUIMIENTODEPEDIDOS_ID + " INTEGER PRIMARY KEY, " +
                COL_VNSEGUIMIENTODEPEDIDOS_ALMACEN + " TEXT, " +
                COL_VNSEGUIMIENTODEPEDIDOS_NUMPEDIDO + " INTEGER, " +
                COL_VNSEGUIMIENTODEPEDIDOS_FECHA + " TEXT, " +
                COL_VNSEGUIMIENTODEPEDIDOS_CVECLIENTE + " TEXT, " +
                COL_VNSEGUIMIENTODEPEDIDOS_NUMFACTURA + " INTEGER, " +
                COL_VNSEGUIMIENTODEPEDIDOS_FECHAFACTURA + " TEXT, " +
                COL_VNSEGUIMIENTODEPEDIDOS_MONTOFACTURA + " REAL, " +
                COL_VNSEGUIMIENTODEPEDIDOS_MONTOPEDIDO + " REAL, " +
                COL_VNSEGUIMIENTODEPEDIDOS_GUIA + " TEXT, " +
                COL_VNSEGUIMIENTODEPEDIDOS_CONDUCTO + " TEXT, " +
                COL_VNSEGUIMIENTODEPEDIDOS_FECHAGUIA + " TEXT, " +
                COL_VNSEGUIMIENTODEPEDIDOS_CONFIRMOENVIO + " TEXT, " +
                COL_VNSEGUIMIENTODEPEDIDOS_FECHACONFENVIO + " TEXT, " +
                COL_VNSEGUIMIENTODEPEDIDOS_CONFIRMORECEPCION + " TEXT, " +
                COL_VNSEGUIMIENTODEPEDIDOS_FECHACONFRECEPCION + " TEXT " +
                ")";
        db.execSQL(CREATE_VN_SEGUIMIENTO_DE_PEDIDOS);


        // Tabla vn_cat_agentes ///////////////////////////////////////////////////////////////////
        String CREATE_VN_CAT_AGENTES= "CREATE TABLE IF NOT EXISTS " + TABLE_VN_CAT_AGENTES + "(" +
                COL_VNCATAGENTES_ID + " INTEGER PRIMARY KEY, " +
                COL_VNCATAGENTES_CVEUSUARIO + " TEXT, " +
                COL_VNCATAGENTES_NOMBRE + " TEXT " +
                ")";
        db.execSQL(CREATE_VN_CAT_AGENTES);

        // Tabla gl_ruta_seleccionada ///////////////////////////////////////////////////////////////////
        String CREATE_GL_RUTA_SELECCIONADA= "CREATE TABLE IF NOT EXISTS " + TABLE_GL_RUTA_SELECCIONADA + "(" +
                COL_GLRUTASELECCIONADA_ID + " INTEGER PRIMARY KEY, " +
                COL_GLRUTASELECCIONADA_NUMRUTA + " TEXT, " +
                COL_GLRUTASELECCIONADA_CVEAGENTE + " TEXT " +
                ")";
        db.execSQL(CREATE_GL_RUTA_SELECCIONADA);

        // Tabla vn_registro_visita ///////////////////////////////////////////////////////////////
        String CREATE_VN_REGISTRO_VISITA= "CREATE TABLE IF NOT EXISTS " + TABLE_VN_REGISTRO_VISITA + "(" +
                COL_VNREGISTROVISITA_ID + " INTEGER PRIMARY KEY, " +
                COL_VNREGISTROVISITA_CVECLIENTE + " TEXT, " +
                COL_VNREGISTROVISITA_COMENTARIOS + " TEXT, " +
                COL_VNREGISTROVISITA_FECHAREGISTRO + " TEXT, " +
                COL_VNREGISTROVISITA_CVEUSUARIO + " TEXT, " +
                COL_VNREGISTROVISITA_CVECOMPANIA + " TEXT, " +
                COL_VNREGISTROVISITA_LATITUDE + " TEXT, " +
                COL_VNREGISTROVISITA_LONGITUDE + " TEXT, " +
                COL_VNREGISTROVISITA_SINCRONIZADO + " TEXT, " +
                COL_VNREGISTROVISITA_TIPO_PROBLEMA + " TEXT, " +
                COL_VNREGISTROVISITA_ULTIMA_VISITA + " TEXT" +
                ")";
        db.execSQL(CREATE_VN_REGISTRO_VISITA);

        // Tabla gl_marketing ///////////////////////////////////////////////////////////////////
        String CREATE_GL_MARKETING= "CREATE TABLE IF NOT EXISTS " + TABLE_GL_MARKETING + "(" +
                COL_GLMARKETING_ID + " INTEGER PRIMARY KEY, " +
                COL_GLMARKETING_FECHA + " TEXT " +
                ")";
        db.execSQL(CREATE_GL_MARKETING);

        // Tabla vn_seguimiento_de_pagos /////////////////////////////////////////////////////////
        String CREATE_VN_SEGUIMIENTO_DE_PAGOS= "CREATE TABLE IF NOT EXISTS " + TABLE_VN_SEGUIMIENTO_DE_PAGOS + "(" +
                COL_VNSEGUIMIENTODEPAGOS_ID + " INTEGER PRIMARY KEY, " +
                COL_VNSEGUIMIENTODEPAGOS_CVECLIENTE + " TEXT, " +
                COL_VNSEGUIMIENTODEPAGOS_NUMDOCUMENTO + " TEXT, " +
                COL_VNSEGUIMIENTODEPAGOS_FECHADOCUMENTO + " TEXT, " +
                COL_VNSEGUIMIENTODEPAGOS_MONTO + " REAL, " +
                COL_VNSEGUIMIENTODEPAGOS_CVETIPOPAGO + " TEXT, " +
                COL_VNSEGUIMIENTODEPAGOS_BANCOER + " TEXT, " +
                COL_VNSEGUIMIENTODEPAGOS_REFERENCIA + " TEXT, " +
                COL_VNSEGUIMIENTODEPAGOS_FECHABANCO + " TEXT, " +
                COL_VNSEGUIMIENTODEPAGOS_CAPTURO + " TEXT, " +
                COL_VNSEGUIMIENTODEPAGOS_FECHACONCILIACION + " TEXT " +
                ")";
        db.execSQL(CREATE_VN_SEGUIMIENTO_DE_PAGOS);

        // Bloque para la tabla local vn_programa_rutas_semanales
        String CREATE_VN_PROGRAMA_RUTAS_SEMANALES = "CREATE TABLE IF NOT EXISTS " + TABLE_VN_PROGRAMA_RUTAS_SEMANALES + "(" +
                COL_VNPROGRAMARUTASSEMANALES_ID + " INTEGER PRIMARY KEY, " +
                COL_VNPROGRAMARUTASSEMANALES_FECHARUTA + " TEXT, " +
                COL_VNPROGRAMARUTASSEMANALES_NUMERODIA + " INTEGER, " +
                COL_VNPROGRAMARUTASSEMANALES_CVECLIENTE + " TEXT, " +
                COL_VNPROGRAMARUTASSEMANALES_POBLACION + " TEXT, " +
                COL_VNPROGRAMARUTASSEMANALES_ESTADO + " TEXT, " +
                COL_VNPROGRAMARUTASSEMANALES_VTAVET + " REAL, " +
                COL_VNPROGRAMARUTASSEMANALES_FECHAVTAVET + " TEXT, " +
                COL_VNPROGRAMARUTASSEMANALES_VTAAGR + " REAL, " +
                COL_VNPROGRAMARUTASSEMANALES_FECHAVTAAGR + " TEXT, " +
                /*
                COL_VNPROGRAMARUTASSEMANALES_COBVET + " REAL, " +
                COL_VNPROGRAMARUTASSEMANALES_FECHACOBVET + " TEXT, " +
                COL_VNPROGRAMARUTASSEMANALES_COBAGR + " REAL, " +
                COL_VNPROGRAMARUTASSEMANALES_FECHACOBAGR + " TEXT, " +
                */
                COL_VNPROGRAMARUTASSEMANALES_SALDO + " REAL, " +
                COL_VNPROGRAMARUTASSEMANALES_SALDOVIEJO + " REAL, " +
                COL_VNPROGRAMARUTASSEMANALES_DIASSALDOVIEJO + " REAL, " +
                COL_VNPROGRAMARUTASSEMANALES_CANTIDADADULTO + " REAL, " +
                /*
                COL_VNPROGRAMARUTASSEMANALES_FECHAADULTO + " TEXT, " +
                */
                COL_VNPROGRAMARUTASSEMANALES_CANTIDADPUPPY + " REAL, " +
                /*
                COL_VNPROGRAMARUTASSEMANALES_FECHAPUPPY + " TEXT, " +
                */
                COL_VNPROGRAMARUTASSEMANALES_ORDEN + " INTEGER, " +
                COL_VNPROGRAMARUTASSEMANALES_HORAPRIMERAVISITA + " TEXT " +
                ")";
        db.execSQL(CREATE_VN_PROGRAMA_RUTAS_SEMANALES);

        //Bloque para la tabla gl_cat_entidades
        String CREATE_GL_CAT_ENTIDADES_TABLE="CREATE TABLE IF NOT EXISTS "+TABLE_GL_CAT_ENTIDADES+"("+
                COL_GLCATENTIDADES_ID+" INTEGER PRIMARY KEY, "+
                COL_GLCATENTIDADES_CVE_ENTIDAD+" TEXT,"+
                COL_GLCATENTIDADES_NOMBRE_ENTIDAD+" TEXT "+
                ")";
        db.execSQL(CREATE_GL_CAT_ENTIDADES_TABLE);

        //Bloque para la tabla gl_cat_localidades
        String CREATE_GL_CAT_LOCALIDADES_TABLE="CREATE TABLE IF NOT EXISTS "+TABLE_GL_CAT_LOCALIDADES+"("+
                COL_GLCATLOCALIDADES_ID+" INTEGER PRIMARY KEY, "+
                COL_GLCATLOCALIDADES_CVE_LOCALIDAD+" TEXT, "+
                COL_GLCATLOCALIDADES_CVE_ENTIDAD+" TEXT, "+
                COL_GLCATLOCALIDADES_NOMBRE_LOCALIDAD+" TEXT "+
                ")";
        db.execSQL(CREATE_GL_CAT_LOCALIDADES_TABLE);

        //Bloque para la tabla vn_clientes_seguimiento
        String CREATE_VN_CLIENTES_SEGUIMIENTO_TABLE="CREATE TABLE IF NOT EXISTS "+TABLE_VN_CLIENTES_SEGUIMIENTO+"("+
                COL_VNCLIENTESSEGUIMIENTO_ID+" INTEGER PRIMARY KEY, "+
                COL_VNCLIENTESSEGUIMIENTO_CVE_COMPANIA+" TEXT, "+
                COL_VNCLIENTESSEGUIMIENTO_FECHA_REGISTRO+" TEXT, "+
                COL_VNCLIENTESSEGUIMIENTO_CVE_USUARIO_CAPTURA+" TEXT, "+
                COL_VNCLIENTESSEGUIMIENTO_NOMBRE_CLIENTE+" TEXT, "+
                COL_VNCLIENTESSEGUIMIENTO_RFC+" TEXT, "+
                COL_VNCLIENTESSEGUIMIENTO_DOMICILIO+" TEXT, "+
                COL_VNCLIENTESSEGUIMIENTO_COLONIA+" TEXT, "+
                COL_VNCLIENTESSEGUIMIENTO_CP+" TEXT, "+
                COL_VNCLIENTESSEGUIMIENTO_TELEFONO+" TEXT, "+
                COL_VNCLIENTESSEGUIMIENTO_EMAIL+" TEXT, "+
                COL_VNCLIENTESSEGUIMIENTO_TIPO_CONTRIBUYENTE+" TEXT, "+
                COL_VNCLIENTESSEGUIMIENTO_ENTIDAD+" TEXT, "+
                COL_VNCLIENTESSEGUIMIENTO_LOCALIDAD+" TEXT,"+
                COL_VNCLIENTESSEGUIMIENTO_LATITUDE+" TEXT, "+
                COL_VNCLIENTESSEGUIMIENTO_LONGITUDE+" TEXT, "+
                COL_VNCLIENTESSEGUIMIENTO_SINCRONIZADO+" TEXT "+
                ")";
        db.execSQL(CREATE_VN_CLIENTES_SEGUIMIENTO_TABLE);

        //Bloque para la tabla vn_politicas
        String CREATE_VN_POLITICAS="CREATE TABLE IF NOT EXISTS "+TABLE_VN_POLITICAS+"("+
                COL_VNPOLITICAS_ID+" INTEGER PRIMARY KEY, "+
                COL_VNPOLITICAS_ID_POLITICA+" TEXT, "+
                COL_VNPOLITICAS_TITULO+" TEXT, "+
                COL_VNPOLITICAS_ID_IMAGEN+" TEXT, "+
                COL_VNPOLITICAS_IMAGEN+" TEXT "+
                ")";
        db.execSQL(CREATE_VN_POLITICAS);


    } // Cierre del onCreate

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        // OJO!!! Esta rutina es espec“fica para la acci—n de actualizaci—n de BD. Hoy solo elimina y regenera la BD X cambio de versi—n

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VN_CAT_CLIENTES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_IN_CAT_PRODUCTOS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VN_CAT_CONDUCTOS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VN_PEDIDOS_ENCABEZADO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VN_PEDIDOS_PARTIDAS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GL_ACCESOS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GL_SYNC);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VN_CAT_RUTAS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GL_BITACORA_ACCESOS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_IN_CENTROS_INVENTARIOS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VN_CAT_AGENTES_PARAMETROS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VN_RUTAS_ASOCIACIONES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VN_PAQUETES_PARTIDAS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GL_MARKETING);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VN_CAT_AGENTES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GL_RUTA_SELECCIONADA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VN_REGISTRO_VISITA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VN_SEGUIMIENTO_DE_PEDIDOS);

        //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        //+ Faltan las tablas de pagos en proceso de analisis                                     +
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VN_CAT_BANCOS_CLIENTES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VN_DOCUMENTOS_DEPOSITOS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VN_DOCUMENTOS_RESPALDOS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VN_DOCUMENTOS_ENCABEZADO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VN_DOCUMENTOS_PARTIDAS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TS_CAT_BANCOS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VN_CAT_TIPOS_PAGO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VN_SEGUIMIENTO_DE_PAGOS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VN_PROGRAMA_RUTAS_SEMANALES);

        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_GL_CAT_ENTIDADES);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_GL_CAT_LOCALIDADES);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_VN_CLIENTES_SEGUIMIENTO);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_VN_POLITICAS);

        onCreate(db);
    }

    // Vacia las tablas de catalogos para dejarlas listas para sincronizacion.
    public void resetCatalogs()
    {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM  " + TABLE_VN_CAT_CLIENTES);
        db.execSQL("DELETE FROM  " + TABLE_IN_CAT_PRODUCTOS);
        db.execSQL("DELETE FROM  " + TABLE_VN_CAT_CONDUCTOS);
        //db.execSQL("DELETE FROM  " + TABLE_VN_PEDIDOS_ENCABEZADO);
        //db.execSQL("DELETE FROM  " + TABLE_VN_PEDIDOS_PARTIDAS);
        //db.execSQL("DELETE FROM  " + TABLE_GL_ACCESOS);
        db.execSQL("DELETE FROM  " + TABLE_GL_SYNC);
       // db.execSQL("DELETE FROM  " + TABLE_VN_CAT_RUTAS);
        db.execSQL("DELETE FROM  " + TABLE_IN_CENTROS_INVENTARIOS);
        db.execSQL("DELETE FROM  " + TABLE_VN_CAT_AGENTES_PARAMETROS);
        db.execSQL("DELETE FROM  " + TABLE_VN_RUTAS_ASOCIACIONES);
        db.execSQL("DELETE FROM  " + TABLE_VN_PAQUETES_PARTIDAS);
        //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        //+ Faltan las tablas de pagos en proceso de analisis                                     +
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        db.execSQL("DELETE FROM  " + TABLE_VN_CAT_TIPOS_PAGO);
        db.execSQL("DELETE FROM  " + TABLE_VN_CAT_BANCOS_CLIENTES);
        db.execSQL("DELETE FROM  " + TABLE_VN_DOCUMENTOS_DEPOSITOS);
        db.execSQL("DELETE FROM  " + TABLE_VN_DOCUMENTOS_RESPALDOS);
        db.execSQL("DELETE FROM  " + TABLE_TS_CAT_BANCOS);
        db.execSQL("DELETE FROM  " + TABLE_VN_RUTAS_ASOCIACIONES);
        db.execSQL("DELETE FROM  " + TABLE_VN_SEGUIMIENTO_DE_PEDIDOS);
        //db.execSQL("DELETE FROM  " + TABLE_VN_CAT_AGENTES);
        db.execSQL("DELETE FROM  " + TABLE_VN_SEGUIMIENTO_DE_PAGOS);
        db.execSQL("DELETE FROM  " + TABLE_VN_PROGRAMA_RUTAS_SEMANALES);

        db.execSQL("DELETE FROM  " + TABLE_GL_CAT_ENTIDADES);
        db.execSQL("DELETE FROM  " + TABLE_GL_CAT_LOCALIDADES);
        //db.execSQL("DELETE FROM  " + TABLE_VN_CLIENTES_SEGUIMIENTO);
        db.execSQL("DELETE FROM  " + TABLE_VN_POLITICAS);

        db.close();
    }


    // Vacia las tablas de catalogos para dejarlas listas para sincronizacion.
    public void resetPedidos()
    {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("update  " + TABLE_VN_PEDIDOS_ENCABEZADO + " set impreso = '1' where impreso = '0'");

        db.close();
    }

    // Vacia las tablas de visitas para dejarlas listas para sincronizacion.
    public void resetProspectos()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("update  " + TABLE_VN_CLIENTES_SEGUIMIENTO + " set sincronizado = '1' where sincronizado = '0'");
        db.close();
    }
    // Vacia las tablas de visitas para dejarlas listas para sincronizacion.
    public void resetVisitas()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("update  " + TABLE_VN_REGISTRO_VISITA + " set sincronizado = '1' where sincronizado = '0'");
        db.close();
    }

    // Vacia la tabla de accesos si esta contiene informacion. Esta es por la primera ves que se crea la BD / Acceso
    public void resetGlAccesos()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM  " + TABLE_GL_ACCESOS);
        db.execSQL("DELETE FROM  " + TABLE_VN_CAT_RUTAS);
        db.execSQL("DELETE FROM  " + TABLE_VN_CAT_AGENTES);
        db.close();
    }
    public void borraReserva()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.close();
    }


    // Inserta un registro individual en la tabla vn_cat_clientes. Recibe un objeto de tipo JSON con estructura del catalogo de clientes
    public void insertaVnCatClientes(JSONObject jsonObj)
    {
        ContentValues values = new ContentValues();
        try {
            // Log.d("*** Depuracion: ", "Entra al Try de insertaVnCatClientes ");
            values.put(COL_VNCATCLIENTES_CVEUSUARIO, jsonObj.getString(TAG_VNCATCLIENTES_CVEUSUARIO));
            values.put(COL_VNCATCLIENTES_NOMBRE, jsonObj.getString(TAG_VNCATCLIENTES_NOMBRE));
            values.put(COL_VNCATCLIENTES_RFC, jsonObj.getString(TAG_VNCATCLIENTES_RFC));
            values.put(COL_VNCATCLIENTES_TIPOCONTRIBUYENTE, jsonObj.getString(TAG_VNCATCLIENTES_TIPOCONTRIBUYENTE));
            values.put(COL_VNCATCLIENTES_CALLEDOMICILIO, jsonObj.getString(TAG_VNCATCLIENTES_CALLEDOMICILIO));
            values.put(COL_VNCATCLIENTES_COLONIA, jsonObj.getString(TAG_VNCATCLIENTES_COLONIA));
            values.put(COL_VNCATCLIENTES_CODIGOPOSTAL, jsonObj.getString(TAG_VNCATCLIENTES_CODIGOPOSTAL));
            values.put(COL_VNCATCLIENTES_TELEFONOS, jsonObj.getString(TAG_VNCATCLIENTES_TELEFONOS));
            values.put(COL_VNCATCLIENTES_FAX, jsonObj.getString(TAG_VNCATCLIENTES_FAX));
            values.put(COL_VNCATCLIENTES_EMAIL, jsonObj.getString(TAG_VNCATCLIENTES_EMAIL));
            values.put(COL_VNCATCLIENTES_DOMICILIOBODEGA, jsonObj.getString(TAG_VNCATCLIENTES_DOMICILIOBODEGA));
            values.put(COL_VNCATCLIENTES_REPRESENTANTELEGAL, jsonObj.getString(TAG_VNCATCLIENTES_REPRESENTANTELEGAL));
            values.put(COL_VNCATCLIENTES_ATENCIONVENTAS, jsonObj.getString(TAG_VNCATCLIENTES_ATENCIONVENTAS));
            values.put(COL_VNCATCLIENTES_ATENCIONPAGOS, jsonObj.getString(TAG_VNCATCLIENTES_ATENCIONPAGOS));
            values.put(COL_VNCATCLIENTES_CVELOCALIDAD, jsonObj.getString(TAG_VNCATCLIENTES_CVELOCALIDAD));
            values.put(COL_VNCATCLIENTES_COMENTARIOS, jsonObj.getString(TAG_VNCATCLIENTES_COMENTARIOS));
            values.put(COL_VNCATCLIENTES_CVEUSUARIOVENTAS, jsonObj.getString(TAG_VNCATCLIENTES_CVEUSUARIOVENTAS));
            values.put(COL_VNCATCLIENTES_CONTRASENA, jsonObj.getString(TAG_VNCATCLIENTES_CONTRASENA));
            values.put(COL_VNCATCLIENTES_ENTRE, jsonObj.getString(TAG_VNCATCLIENTES_ENTRE));
            values.put(COL_VNCATCLIENTES_ENTRE2, jsonObj.getString(TAG_VNCATCLIENTES_ENTRE2));
            values.put(COL_VNCATCLIENTES_ESTATUS, jsonObj.getString(TAG_VNCATCLIENTES_ESTATUS));
            values.put(COL_VNCATCLIENTES_DOMICILIOENTREGA, jsonObj.getString(TAG_VNCATCLIENTES_DOMICILIOENTREGA));
            values.put(COL_VNCATCLIENTES_ULTIMAACTUALIZACION, jsonObj.getString(TAG_VNCATCLIENTES_ULTIMAACTUALIZACION));
            values.put(COL_VNCATCLIENTES_CVEUSUARIOACTUALIZACION, jsonObj.getString(TAG_VNCATCLIENTES_CVEUSUARIOACTUALIZACION));
            values.put(COL_VNCATCLIENTES_LOCALIZACION, jsonObj.getString(TAG_VNCATCLIENTES_LOCALIZACION));
            values.put(COL_VNCATCLIENTES_MOROSO, jsonObj.getString(TAG_VNCATCLIENTES_MOROSO));
            values.put(COL_VNCATCLIENTES_COMENTARIOSCLIENTE, jsonObj.getString(TAG_VNCATCLIENTES_COMENTARIOSCLIENTE));
            values.put(COL_VNCATCLIENTES_HISTORIALCOMENTARIOS, jsonObj.getString(TAG_VNCATCLIENTES_HISTORIALCOMENTARIOS));
            values.put(COL_VNCATCLIENTES_OPINION_SERVICIO, jsonObj.getString(TAG_VNCATCLIENTES_OPINION_SERVICIO));
            values.put(COL_VNCATCLIENTES_COMENTARIOS_SERVICIO, jsonObj.getString(TAG_VNCATCLIENTES_COMENTARIOS_SERVICIO));
            values.put(COL_VNCATCLIENTES_FECHAREGISTRO, jsonObj.getString(TAG_VNCATCLIENTES_FECHAREGISTRO));
            values.put(COL_VNCATCLIENTES_ESTATUSMOVIMIENTOS, jsonObj.getString(TAG_VNCATCLIENTES_ESTATUSMOVIMIENTOS));
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        } // fin del catch

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_VN_CAT_CLIENTES, null, values);
        db.close();
    } // FIN DE insertaVnCatClientes

    // Inserta un registro individual en la tabla movil  in_cat_productos. Recibe un objeto de tipo JSON con estructura del catalogo de productos
    public void insertaInCatProductos(JSONObject jsonObj)
    {
        ContentValues values = new ContentValues();
        try
        {
            values.put(COL_INCATPRODUCTOS_CVECATPRODUCTO, jsonObj.getString(TAG_INCATPRODUCTOS_CVECATPRODUCTO));
            values.put(COL_INCATPRODUCTOS_CVECOMPANIA, jsonObj.getString(TAG_INCATPRODUCTOS_CVECOMPANIA));
            values.put(COL_INCATPRODUCTOS_CVE_GRUPO, jsonObj.getString(TAG_INCATPRODUCTOS_CVE_GRUPO));
            values.put(COL_INCATPRODUCTOS_CVESUBGRUPO, jsonObj.getString(TAG_INCATPRODUCTOS_CVESUBGRUPO));
            values.put(COL_INCATPRODUCTOS_CVE_FAMILIA, jsonObj.getString(TAG_INCATPRODUCTOS_CVE_FAMILIA));
            values.put(COL_INCATPRODUCTOS_CVEPRODUCTO, jsonObj.getString(TAG_INCATPRODUCTOS_CVEPRODUCTO));
            values.put(COL_INCATPRODUCTOS_ORIGEN, jsonObj.getString(TAG_INCATPRODUCTOS_ORIGEN));
            values.put(COL_INCATPRODUCTOS_NOMPRODUCTO, jsonObj.getString(TAG_INCATPRODUCTOS_NOMPRODUCTO));
            values.put(COL_INCATPRODUCTOS_CVEPOSICIONFINANCIERA, jsonObj.getString(TAG_INCATPRODUCTOS_CVEPOSICIONFINANCIERA));
            values.put(COL_INCATPRODUCTOS_CVECENTROCOSTOELABORA, jsonObj.getString(TAG_INCATPRODUCTOS_CVECENTROCOSTOELABORA));
            values.put(COL_INCATPRODUCTOS_CVECLASIFICACION, jsonObj.getString(TAG_INCATPRODUCTOS_CVECLASIFICACION));
            values.put(COL_INCATPRODUCTOS_CVEUNIDADMEDIDA, jsonObj.getString(TAG_INCATPRODUCTOS_CVEUNIDADMEDIDA));
            values.put(COL_INCATPRODUCTOS_CVEPROVEEDORPREFERENTE, jsonObj.getString(TAG_INCATPRODUCTOS_CVEPROVEEDORPREFERENTE));
            values.put(COL_INCATPRODUCTOS_ULTIMOCOSTO, jsonObj.getString(TAG_INCATPRODUCTOS_ULTIMOCOSTO));
            values.put(COL_INCATPRODUCTOS_COSTOPROMEDIO, jsonObj.getString(TAG_INCATPRODUCTOS_COSTOPROMEDIO));
            values.put(COL_INCATPRODUCTOS_COSTOESTANDAR, jsonObj.getString(TAG_INCATPRODUCTOS_COSTOESTANDAR));
            values.put(COL_INCATPRODUCTOS_PRECIOUNITARIO, jsonObj.getString(TAG_INCATPRODUCTOS_PRECIOUNITARIO));
            values.put(COL_INCATPRODUCTOS_PORCENTAJEIMPUESTO, jsonObj.getString(TAG_INCATPRODUCTOS_PORCENTAJEIMPUESTO));
            values.put(COL_INCATPRODUCTOS_PIEZASPORPAQUETE, jsonObj.getString(TAG_INCATPRODUCTOS_PIEZASPORPAQUETE));
            values.put(COL_INCATPRODUCTOS_VENTAMINIMA, jsonObj.getString(TAG_INCATPRODUCTOS_VENTAMINIMA));
            values.put(COL_INCATPRODUCTOS_PIEZASPORLOTE, jsonObj.getString(TAG_INCATPRODUCTOS_PIEZASPORLOTE));
            values.put(COL_INCATPRODUCTOS_MINPIEZASPORLOTE, jsonObj.getString(TAG_INCATPRODUCTOS_MINPIEZASPORLOTE));
            values.put(COL_INCATPRODUCTOS_DIASFABRICACION, jsonObj.getString(TAG_INCATPRODUCTOS_DIASFABRICACION));
            values.put(COL_INCATPRODUCTOS_PESOUNITARIO, jsonObj.getString(TAG_INCATPRODUCTOS_PESOUNITARIO));
            values.put(COL_INCATPRODUCTOS_ESVENTA, jsonObj.getString(TAG_INCATPRODUCTOS_ESVENTA));
            values.put(COL_INCATPRODUCTOS_CONSIDERARMARGEN, jsonObj.getString(TAG_INCATPRODUCTOS_CONSIDERARMARGEN));
            values.put(COL_INCATPRODUCTOS_ESTATUS, jsonObj.getString(TAG_INCATPRODUCTOS_ESTATUS));
            values.put(COL_INCATPRODUCTOS_CODIGOBARRASCHR, jsonObj.getString(TAG_INCATPRODUCTOS_CODIGOBARRASCHR));
            values.put(COL_INCATPRODUCTOS_CODIGOBARRASJPG, jsonObj.getString(TAG_INCATPRODUCTOS_CODIGOBARRASJPG));
            values.put(COL_INCATPRODUCTOS_MINIMO, jsonObj.getString(TAG_INCATPRODUCTOS_MINIMO));
            values.put(COL_INCATPRODUCTOS_MAXIMO, jsonObj.getString(TAG_INCATPRODUCTOS_MAXIMO));
            values.put(COL_INCATPRODUCTOS_REORDEN, jsonObj.getString(TAG_INCATPRODUCTOS_REORDEN));
            values.put(COL_INCATPRODUCTOS_COMENTARIOS, jsonObj.getString(TAG_INCATPRODUCTOS_COMENTARIOS));
            values.put(COL_INCATPRODUCTOS_USOS, jsonObj.getString(TAG_INCATPRODUCTOS_USOS));
            values.put(COL_INCATPRODUCTOS_DOSIS, jsonObj.getString(TAG_INCATPRODUCTOS_DOSIS));
            values.put(COL_INCATPRODUCTOS_VENTAJAS, jsonObj.getString(TAG_INCATPRODUCTOS_VENTAJAS));
            values.put(COL_INCATPRODUCTOS_FORMULA, jsonObj.getString(TAG_INCATPRODUCTOS_FORMULA));
            values.put(COL_INCATPRODUCTOS_IMAGEN, jsonObj.getString(TAG_INCATPRODUCTOS_IMAGEN));
            values.put(COL_INCATPRODUCTOS_PIEZASLOGIS, jsonObj.getString(TAG_INCATPRODUCTOS_PIEZASLOGIS));
            values.put(COL_INCATPRODUCTOS_ABREVIATURA, jsonObj.getString(TAG_INCATPRODUCTOS_ABREVIATURA));
            values.put(COL_INCATPRODUCTOS_IEPS, jsonObj.getString(TAG_INCATPRODUCTOS_IEPS));
            values.put(COL_INCATPRODUCTOS_INDICACIONES, jsonObj.getString(TAG_INCATPRODUCTOS_INDICACIONES));
            values.put(COL_INCATPRODUCTOS_ESPECIESCULTIVOS, jsonObj.getString(TAG_INCATPRODUCTOS_ESPECIESCULTIVOS));
            values.put(COL_INCATPRODUCTOS_IDCATEGORIA, jsonObj.getString(TAG_INCATPRODUCTOS_IDCATEGORIA));
            values.put(COL_INCATPRODUCTOS_IMAGENENCABEZADO, jsonObj.getString(TAG_INCATPRODUCTOS_IMAGENENCABEZADO));
            values.put(COL_INCATPRODUCTOS_PORCENTAJEPAQUETE, jsonObj.getString(TAG_INCATPRODUCTOS_PORCENTAJEPAQUETE));
            values.put(COL_INCATPRODUCTOS_INFTECBASICA, jsonObj.getString(TAG_INCATPRODUCTOS_INFTECBASICA));
            values.put(COL_INCATPRODUCTOS_INFCOMERCIAL, jsonObj.getString(TAG_INCATPRODUCTOS_INFCOMERCIAL));
            values.put(COL_INCATPRODUCTOS_INFTECCOMPLETA, jsonObj.getString(TAG_INCATPRODUCTOS_INFTECCOMPLETA));
            values.put(COL_INCATPRODUCTOS_INTRODUCCION, jsonObj.getString(TAG_INCATPRODUCTOS_INTRODUCCION));

        }
        catch (JSONException e)
        {
            e.printStackTrace();
        } // fin del catch

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_IN_CAT_PRODUCTOS, null, values);
        db.close();

    } // FIN DE insertaInCatProductos

    // Inserta un registro individual en la tabla movil  vn_cat_conductos. Recibe un objeto de tipo JSON con estructura del catalogo de conductos
    public void insertaVnCatConductos(JSONObject jsonObj)
    {
        ContentValues values = new ContentValues();
        try
        {
            values.put(COL_VNCATCONDUCTOS_CVECONDUCTO, jsonObj.getString(TAG_VNCATCONDUCTOS_CVECONDUCTO));
            values.put(COL_VNCATCONDUCTOS_CVE_COMPANIA, jsonObj.getString(TAG_VNCATCONDUCTOS_CVE_COMPANIA));
            values.put(COL_VNCATCONDUCTOS_NOMBRECONDUCTO, jsonObj.getString(TAG_VNCATCONDUCTOS_NOMBRECONDUCTO));
            values.put(COL_VNCATCONDUCTOS_MOSTRAR, jsonObj.getString(TAG_VNCATCONDUCTOS_MOSTRAR));
            values.put(COL_VNCATCONDUCTOS_CONTRATO, jsonObj.getString(TAG_VNCATCONDUCTOS_CONTRATO));
            values.put(COL_VNCATCONDUCTOS_CONCEPTO, jsonObj.getString(TAG_VNCATCONDUCTOS_CONCEPTO));

        }
        catch (JSONException e)
        {
            e.printStackTrace();
        } // fin del catch

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_VN_CAT_CONDUCTOS, null, values);
        db.close();

    } // FIN DE insertaVnCatConductos


    // Inserta un registro individual en la tabla movil  vn_cat_rutas. Recibe un objeto de tipo JSON con estructura del catalogo de rutas
    public void insertaVnCatRutas(JSONObject jsonObj)
    {
        ContentValues values = new ContentValues();
        try
        {
            values.put(COL_VNCATRUTAS_CVECOMPANIA, jsonObj.getString(TAG_VNCATRUTAS_CVECOMPANIA));
            values.put(COL_VNCATRUTAS_NUMRUTA, jsonObj.getString(TAG_VNCATRUTAS_NUMRUTA));
            values.put(COL_VNCATRUTAS_CVEAGENTE, jsonObj.getString(TAG_VNCATRUTAS_CVEAGENTE));
            values.put(COL_VNCATRUTAS_NOMBRERUTA, jsonObj.getString(TAG_VNCATRUTAS_NOMBRERUTA));
            values.put(COL_VNCATRUTAS_DIASVENCIMIENTOCARTERA, jsonObj.getString(TAG_VNCATRUTAS_DIASVENCIMIENTOCARTERA));
            values.put(COL_VNCATRUTAS_CUOTAVENTA, jsonObj.getString(TAG_VNCATRUTAS_CUOTAVENTA));
            values.put(COL_VNCATRUTAS_CUOTACOBRANZA, jsonObj.getString(TAG_VNCATRUTAS_CUOTACOBRANZA));
            values.put(COL_VNCATRUTAS_CUOTAMANO, jsonObj.getString(TAG_VNCATRUTAS_CUOTAMANO));
            values.put(COL_VNCATRUTAS_CUOTAPEDIDOS, jsonObj.getString(TAG_VNCATRUTAS_CUOTAPEDIDOS));
            values.put(COL_VNCATRUTAS_DIASINVENTARIO, jsonObj.getString(TAG_VNCATRUTAS_DIASINVENTARIO));

        }
        catch (JSONException e)
        {
            e.printStackTrace();
        } // fin del catch

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_VN_CAT_RUTAS, null, values);
        db.close();

    } // FIN DE insertaVnCatRutas

    // Inserta un registro individual en la tabla movil  gl_accesos. Recibe un objeto de tipo JSON con estructura del catalogo de accesos
    public void insertaGlAccesos(JSONObject jsonObj)
    {
        ContentValues values = new ContentValues();
        try
        {
            values.put(COL_GLACCESOS_CVEUSUARIO, jsonObj.getString(TAG_GLACCESOS_CVEUSUARIO));
            values.put(COL_GLACCESOS_PASSWORD, jsonObj.getString(TAG_GLACCESOS_PASSWORD));
            values.put(COL_GLACCESOS_TIPOUSUARIO, jsonObj.getString(TAG_GLACCESOS_TIPOUSUARIO));
            values.put(COL_GLACCESOS_ESTATUS, jsonObj.getString(TAG_GLACCESOS_ESTATUS));
            values.put(COL_GLACCESOS_ACTUALIZO_PASSWORD, jsonObj.getString(TAG_GLACCESOS_ACTUALIZO_PASSWORD));
            values.put(COL_GLACCESOS_ULTIMAACTUALIZACION, jsonObj.getString(TAG_GLACCESOS_ULTIMAACTUALIZACION));
            values.put(COL_GLACCESOS_IMEI, jsonObj.getString(TAG_GLACCESOS_IMEI));

        }
        catch (JSONException e)
        {
            e.printStackTrace();
        } // fin del catch

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_GL_ACCESOS, null, values);
        db.close();

    } // FIN DE insertaGlAccesos

    // Inserta un registro individual en la tabla movil  insertaInCentrosInventarios. Recibe un objeto de tipo JSON con estructura del in_centros_inventarios2
    public void insertaInCentrosInventarios(JSONObject jsonObj)
    {
        ContentValues values = new ContentValues();
        try
        {
            values.put(COL_INCENTROSINVENTARIOS_CVECOMPANIA, jsonObj.getString(TAG_INCENTROSINVENTARIOS_CVECOMPANIA));
            values.put(COL_INCENTROSINVENTARIOS_CVECENTROCOSTO, jsonObj.getString(TAG_INCENTROSINVENTARIOS_CVECENTROCOSTO));
            values.put(COL_INCENTROSINVENTARIOS_CVECATPRODUCTO, jsonObj.getString(TAG_INCENTROSINVENTARIOS_CVECATPRODUCTO));
            values.put(COL_INCENTROSINVENTARIOS_NUMLOTE, jsonObj.getString(TAG_INCENTROSINVENTARIOS_NUMLOTE));
            values.put(COL_INCENTROSINVENTARIOS_NUMLOTEINTERNO, jsonObj.getString(TAG_INCENTROSINVENTARIOS_NUMLOTEINTERNO));
            values.put(COL_INCENTROSINVENTARIOS_EXISTENCIAS, jsonObj.getString(TAG_INCENTROSINVENTARIOS_EXISTENCIAS));
            values.put(COL_INCENTROSINVENTARIOS_COSTOUNITARIO, jsonObj.getString(TAG_INCENTROSINVENTARIOS_COSTOUNITARIO));

        }
        catch (JSONException e)
        {
            e.printStackTrace();
        } // fin del catch

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_IN_CENTROS_INVENTARIOS, null, values);
        db.close();

    } // FIN DE insertaInCentrosInventarios

    // Inserta un registro individual en la tabla movil  insertaVnCatAgentesParametros. Recibe un objeto de tipo JSON con estructura de vn_cat_agentes_parametros
    public void insertaVnCatAgentesParametros(JSONObject jsonObj)
    {
        ContentValues values = new ContentValues();
        try
        {
            values.put(COL_VNCATAGENTESPARAMETROS_CVECOMPANIA, jsonObj.getString(TAG_VNCATAGENTESPARAMETROS_CVECOMPANIA));
            values.put(COL_VNCATAGENTESPARAMETROS_CVEAGENTE, jsonObj.getString(TAG_VNCATAGENTESPARAMETROS_CVEAGENTE));
            values.put(COL_VNCATAGENTESPARAMETROS_CVECENTROCOSTO, jsonObj.getString(TAG_VNCATAGENTESPARAMETROS_CVECENTROCOSTO));
            values.put(COL_VNCATAGENTESPARAMETROS_AGENTETIPO, jsonObj.getString(TAG_VNCATAGENTESPARAMETROS_AGENTETIPO));
            values.put(COL_VNCATAGENTESPARAMETROS_GRUPOCOMISION, jsonObj.getString(TAG_VNCATAGENTESPARAMETROS_GRUPOCOMISION));
            values.put(COL_VNCATAGENTESPARAMETROS_CONSIGNACION, jsonObj.getString(TAG_VNCATAGENTESPARAMETROS_CONSIGNACION));
            values.put(COL_VNCATAGENTESPARAMETROS_ESTATUS, jsonObj.getString(TAG_VNCATAGENTESPARAMETROS_ESTATUS));

        }
        catch (JSONException e)
        {
            e.printStackTrace();
        } // fin del catch

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_VN_CAT_AGENTES_PARAMETROS, null, values);
        db.close();

    } // FIN DE insertaVnCatAgentesParametros

    // Inserta un registro individual en la tabla movil  insertaVnRutasAsociaciones. Recibe un objeto de tipo JSON con estructura de vn_rutas_asociaciones
    public void insertaVnRutasAsociaciones(JSONObject jsonObj)
    {
        ContentValues values = new ContentValues();
        try
        {
            values.put(COL_VNRUTASASOCIACIONES_CVECOMPANIA, jsonObj.getString(TAG_VNRUTASASOCIACIONES_CVECOMPANIA));
            values.put(COL_VNRUTASASOCIACIONES_NUMRUTA, jsonObj.getString(TAG_VNRUTASASOCIACIONES_NUMRUTA));
            values.put(COL_VNRUTASASOCIACIONES_CVECLIENTE, jsonObj.getString(TAG_VNRUTASASOCIACIONES_CVECLIENTE));
            values.put(COL_VNRUTASASOCIACIONES_DIASVISITA, jsonObj.getString(TAG_VNRUTASASOCIACIONES_DIASVISITA));
            values.put(COL_VNRUTASASOCIACIONES_CVELOCALIDAD, jsonObj.getString(TAG_VNRUTASASOCIACIONES_CVELOCALIDAD));

        }
        catch (JSONException e)
        {
            e.printStackTrace();
        } // fin del catch

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_VN_RUTAS_ASOCIACIONES, null, values);
        db.close();

    } // FIN DE insertaVnRutasAsociaciones

    // Inserta un registro individual en la tabla movil  insertaVnPaquetesPartidas. Recibe un objeto de tipo JSON con estructura de vn_rutas_asociaciones
    public void insertaVnPaquetesPartidas(JSONObject jsonObj)
    {
        ContentValues values = new ContentValues();
        try
        {
            values.put(COL_VNPAQUETESPARTIDAS_CVECOMPANIA, jsonObj.getString(TAG_VNPAQUETESPARTIDAS_CVECOMPANIA));
            values.put(COL_VNPAQUETESPARTIDAS_NUMPAQUETE, jsonObj.getString(TAG_VNPAQUETESPARTIDAS_NUMPAQUETE));
            values.put(COL_VNPAQUETESPARTIDAS_NUMPARTIDA, jsonObj.getString(TAG_VNPAQUETESPARTIDAS_NUMPARTIDA));
            values.put(COL_VNPAQUETESPARTIDAS_CVECATPRODUCTO, jsonObj.getString(TAG_VNPAQUETESPARTIDAS_CVECATPRODUCTO));
            values.put(COL_VNPAQUETESPARTIDAS_CANTIDAD, jsonObj.getString(TAG_VNPAQUETESPARTIDAS_CANTIDAD));
            values.put(COL_VNPAQUETESPARTIDAS_PORCENTAJEDESCUENTO, jsonObj.getString(TAG_VNPAQUETESPARTIDAS_PORCENTAJEDESCUENTO));

        }
        catch (JSONException e)
        {
            e.printStackTrace();
        } // fin del catch

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_VN_PAQUETES_PARTIDAS, null, values);
        db.close();

    } // FIN DE insertaVnRutasAsociaciones

    // ++++ Inserta registros a la tabla vn_cat_tipos_pago importada de la BD de Sybrem x Objecto JSON ++++
    public void insertatiposPago(JSONObject jsonObj)
    {
        ContentValues values = new ContentValues();
        try
        {
            values.put(COL_VNCATTIPOSPAGO_CVETIPOPAGO, jsonObj.getString(TAG_VNCATTIPOSPAGO_CVETIPOPAGO));
            values.put(COL_VNCATTIPOSPAGO_NOMBRETIPOPAGO, jsonObj.getString(TAG_VNCATTIPOSPAGO_NOMBRETIPOPAGO));
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        } // fin del catch

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_VN_CAT_TIPOS_PAGO, null, values);
        db.close();

    } // ++++ Fin de insertatiposPago ++++

    // ++++ Inserta los registros a la tabla vn_cat_bancos_clientes importada de la BD de Sybrem x Objeto JSON ++++
    public void insertabancosClientes(JSONObject jsonObj)
    {
        ContentValues values = new ContentValues();
        try
        {
            values.put(COL_VNCATBANCOSCLIENTES_CVEBANCOEMISOR, jsonObj.getString(TAG_VNCATBANCOSCLIENTES_CVEBANCOEMISOR));
            values.put(COL_VNCATBANCOSCLIENTES_NOMBREBANCO, jsonObj.getString(TAG_VNCATBANCOSCLIENTES_NOMBREBANCO));
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        } // fin del catch

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_VN_CAT_BANCOS_CLIENTES, null, values);
        db.close();
    } // ++++ Fin del insertabancosClientes

    // ++++ Inserta los registros a la tabla vn_documentos_depositos importada de la BD de Sybrem x Objecto JSON ++++
    public void insertadoctosDepositos(JSONObject jsonObj)
    {
        ContentValues values = new ContentValues();
        try
        {
            values.put(COL_VNDOCUMENTOSDEPOSITOS_PERSONADEPOSITO, jsonObj.getString(TAG_VNDOCUMENTOSDEPOSITOS_PERSONADEPOSITO));
            values.put(COL_VNDOCUMENTOSDEPOSITOS_DESCRIPCION, jsonObj.getString(TAG_VNDOCUMENTOSDEPOSITOS_DESCRIPCION));
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        } // fin del catch

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_VN_DOCUMENTOS_DEPOSITOS, null, values);
        db.close();
    } // ++++ Fin de insertadoctosDepositos

    // ++++ Inserta los registros a la tabla vn_documentos_respaldos importada de la BD de Sybrem Objecto JSON ++++
    public void insertadoctosRespaldos(JSONObject jsonObj)
    {
        ContentValues values = new ContentValues();
        try
        {
            values.put(COL_VNDOCUMENTOSRESPALDOS_DOCTORESPALDO, jsonObj.getString(TAG_VNDOCUMENTOSRESPALDOS_DOCTORESPALDO));
            values.put(COL_VNDOCUMENTOSRESPALDOS_DESCRIPCION, jsonObj.getString(TAG_VNDOCUMENTOSRESPALDOS_DESCRIPCION));
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        } // fin del catch

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_VN_DOCUMENTOS_RESPALDOS, null, values);
        db.close();

    } // ++++ Fin de insertadoctosRespaldos

    // ++++ Inserta los registros a la tabla vn_documentos_encabezado importada de la BD de Sybrem Objeto JSON ++++
    public void insertadocEncabezado(JSONObject jsonObj)
    {
        ContentValues values = new ContentValues();
        try
        {
            values.put(COL_VNDOCUMENTOSENCABEZADO_CVECOMPANIA, jsonObj.getString(TAG_VNDOCUMENTOSENCABEZADO_CVECOMPANIA));
            values.put(COL_VNDOCUMENTOSENCABEZADO_CVEDOCUMENTO, jsonObj.getString(TAG_VNDOCUMENTOSENCABEZADO_CVEDOCUMENTO));
            values.put(COL_VNDOCUMENTOSENCABEZADO_NUMDOCUMENTO, jsonObj.getString(TAG_VNDOCUMENTOSENCABEZADO_NUMDOCUMENTO));
            values.put(COL_VNDOCUMENTOSENCABEZADO_FECHADOCUMENTO, jsonObj.getString(TAG_VNDOCUMENTOSENCABEZADO_FECHADOCUMENTO));
            values.put(COL_VNDOCUMENTOSENCABEZADO_FECHAREGISTRO, jsonObj.getString(TAG_VNDOCUMENTOSENCABEZADO_FECHAREGISTRO));
            values.put(COL_VNDOCUMENTOSENCABEZADO_TIPODOCUMENTO, jsonObj.getString(TAG_VNDOCUMENTOSENCABEZADO_TIPODOCUMENTO));
            values.put(COL_VNDOCUMENTOSENCABEZADO_SUMA, jsonObj.getString(TAG_VNDOCUMENTOSENCABEZADO_SUMA));
            values.put(COL_VNDOCUMENTOSENCABEZADO_DESCUENTO, jsonObj.getString(TAG_VNDOCUMENTOSENCABEZADO_DESCUENTO));
            values.put(COL_VNDOCUMENTOSENCABEZADO_SUBTOTAL, jsonObj.getString(TAG_VNDOCUMENTOSENCABEZADO_SUBTOTAL));
            values.put(COL_VNDOCUMENTOSENCABEZADO_TOTAL, jsonObj.getString(TAG_VNDOCUMENTOSENCABEZADO_TOTAL));
            values.put(COL_VNDOCUMENTOSENCABEZADO_CVECLIENTE, jsonObj.getString(TAG_VNDOCUMENTOSENCABEZADO_CVECLIENTE));
            values.put(COL_VNDOCUMENTOSENCABEZADO_CVEAGENTE, jsonObj.getString(TAG_VNDOCUMENTOSENCABEZADO_CVEAGENTE));
            values.put(COL_VNDOCUMENTOSENCABEZADO_CVEUSUARIO, jsonObj.getString(TAG_VNDOCUMENTOSENCABEZADO_CVEUSUARIO));
            values.put(COL_VNDOCUMENTOSENCABEZADO_CVEMONEDA, jsonObj.getString(TAG_VNDOCUMENTOSENCABEZADO_CVEMONEDA));
            values.put(COL_VNDOCUMENTOSENCABEZADO_TIPOCAMBIO, jsonObj.getString(TAG_VNDOCUMENTOSENCABEZADO_TIPOCAMBIO));
            values.put(COL_VNDOCUMENTOSENCABEZADO_COMENTARIOS, jsonObj.getString(TAG_VNDOCUMENTOSENCABEZADO_COMENTARIOS));
            values.put(COL_VNDOCUMENTOSENCABEZADO_ESTATUS, jsonObj.getString(TAG_VNDOCUMENTOSENCABEZADO_ESTATUS));
            values.put(COL_VNDOCUMENTOSENCABEZADO_TOTALPAGADO, jsonObj.getString(TAG_VNDOCUMENTOSENCABEZADO_TOTALPAGADO));
            values.put(COL_VNDOCUMENTOSENCABEZADO_RECIBOPAGO, jsonObj.getString(TAG_VNDOCUMENTOSENCABEZADO_RECIBOPAGO));
            values.put(COL_VNDOCUMENTOSENCABEZADO_CONCILIADO, jsonObj.getString(TAG_VNDOCUMENTOSENCABEZADO_CONCILIADO));
            values.put(COL_VNDOCUMENTOSENCABEZADO_FECHACONCILIACION, jsonObj.getString(TAG_VNDOCUMENTOSENCABEZADO_FECHACONCILIACION));
            values.put(COL_VNDOCUMENTOSENCABEZADO_REFERENCIACONCILIACION, jsonObj.getString(TAG_VNDOCUMENTOSENCABEZADO_REFERENCIACONCILIACION));
            values.put(COL_VNDOCUMENTOSENCABEZADO_EXISTEACLARACION, jsonObj.getString(TAG_VNDOCUMENTOSENCABEZADO_EXISTEACLARACION));
            values.put(COL_VNDOCUMENTOSENCABEZADO_PERSONADEPOSITO, jsonObj.getString(TAG_VNDOCUMENTOSENCABEZADO_PERSONADEPOSITO));
            values.put(COL_VNDOCUMENTOSENCABEZADO_CVEUSUARIOCONCILIACION, jsonObj.getString(TAG_VNDOCUMENTOSENCABEZADO_CVEUSUARIOCONCILIACION));
            values.put(COL_VNDOCUMENTOSENCABEZADO_AUDITORIA, jsonObj.getString(TAG_VNDOCUMENTOSENCABEZADO_AUDITORIA));
            values.put(COL_VNDOCUMENTOSENCABEZADO_COMENTARIOSAUDITORIA, jsonObj.getString(TAG_VNDOCUMENTOSENCABEZADO_COMENTARIOSAUDITORIA));
            values.put(COL_VNDOCUMENTOSENCABEZADO_CVEUSUARIODESCONCILIACION, jsonObj.getString(TAG_VNDOCUMENTOSENCABEZADO_CVEUSUARIODESCONCILIACION));
            values.put(COL_VNDOCUMENTOSENCABEZADO_COMENTARIOSDESCONCILIACION, jsonObj.getString(TAG_VNDOCUMENTOSENCABEZADO_COMENTARIOSDESCONCILIACION));
            values.put(COL_VNDOCUMENTOSENCABEZADO_IEPS3, jsonObj.getString(TAG_VNDOCUMENTOSENCABEZADO_IEPS3));
            values.put(COL_VNDOCUMENTOSENCABEZADO_IEPS35, jsonObj.getString(TAG_VNDOCUMENTOSENCABEZADO_IEPS35));
            values.put(COL_VNDOCUMENTOSENCABEZADO_TOTALIEPS, jsonObj.getString(TAG_VNDOCUMENTOSENCABEZADO_TOTALIEPS));
            values.put(COL_VNDOCUMENTOSENCABEZADO_IEPS6, jsonObj.getString(TAG_VNDOCUMENTOSENCABEZADO_IEPS6));
            values.put(COL_VNDOCUMENTOSENCABEZADO_IEPS7, jsonObj.getString(TAG_VNDOCUMENTOSENCABEZADO_IEPS7));
            values.put(COL_VNDOCUMENTOSENCABEZADO_LATITUDE, jsonObj.getString(TAG_VNDOCUMENTOSENCABEZADO_LATITUDE));
            values.put(COL_VNDOCUMENTOSENCABEZADO_LONGITUDE, jsonObj.getString(TAG_VNDOCUMENTOSENCABEZADO_LONGITUDE));
            values.put(COL_VNDOCUMENTOSENCABEZADO_TIPOCOBRANZA, jsonObj.getString(TAG_VNDOCUMENTOSENCABEZADO_TIPOCOBRANZA));
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        } // fin del catch

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_VN_DOCUMENTOS_ENCABEZADO, null, values);
        db.close();
    } // ++ Fin de insertadocEncabezado

    // ++++ Inserta los registros de la BD a la tabla ts_cat_bancos de la BD de Sybrem
    public void insertacatBancos(JSONObject jsonObj)
    {
        ContentValues values = new ContentValues();
        try
        {
            values.put(COL_TSCATBANCOS_CVECOMPANIA, jsonObj.getString(TAG_TSCATBANCOS_CVECOMPANIA));
            values.put(COL_TSCATBANCOS_CVEBANCO, jsonObj.getString(TAG_TSCATBANCOS_CVEBANCO));
            values.put(COL_TSCATBANCOS_NOMBRECORTO, jsonObj.getString(TAG_TSCATBANCOS_NOMBRECORTO));
            values.put(COL_TSCATBANCOS_NOMBREBANCO, jsonObj.getString(TAG_TSCATBANCOS_NOMBREBANCO));
            values.put(COL_TSCATBANCOS_MOSTRARVENTAS, jsonObj.getString(TAG_TSCATBANCOS_MOSTRARVENTAS));
            values.put(COL_TSCATBANCOS_CVE, jsonObj.getString(TAG_TSCATBANCOS_CVE));
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        } // fin del catch

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_TS_CAT_BANCOS, null, values);
        db.close();
    } // ++++ Fin de insertacatBancos

    public void insertaVnSeguimientoDePedidos(JSONObject jsonObj)
    {
        ContentValues values = new ContentValues();
        try
        {
            values.put(COL_VNSEGUIMIENTODEPEDIDOS_ALMACEN, jsonObj.getString(TAG_VNSEGUIMIENTODEPEDIDOS_ALMACEN));
            values.put(COL_VNSEGUIMIENTODEPEDIDOS_NUMPEDIDO, jsonObj.getString(TAG_VNSEGUIMIENTODEPEDIDOS_NUMPEDIDO));
            values.put(COL_VNSEGUIMIENTODEPEDIDOS_FECHA, jsonObj.getString(TAG_VNSEGUIMIENTODEPEDIDOS_FECHA));
            values.put(COL_VNSEGUIMIENTODEPEDIDOS_CVECLIENTE, jsonObj.getString(TAG_VNSEGUIMIENTODEPEDIDOS_CVECLIENTE));
            values.put(COL_VNSEGUIMIENTODEPEDIDOS_NUMFACTURA, jsonObj.getString(TAG_VNSEGUIMIENTODEPEDIDOS_NUMFACTURA));
            values.put(COL_VNSEGUIMIENTODEPEDIDOS_FECHAFACTURA, jsonObj.getString(TAG_VNSEGUIMIENTODEPEDIDOS_FECHAFACTURA));
            values.put(COL_VNSEGUIMIENTODEPEDIDOS_MONTOFACTURA, jsonObj.getString(TAG_VNSEGUIMIENTODEPEDIDOS_MONTOFACTURA));
            values.put(COL_VNSEGUIMIENTODEPEDIDOS_MONTOPEDIDO, jsonObj.getString(TAG_VNSEGUIMIENTODEPEDIDOS_MONTOPEDIDO));
            values.put(COL_VNSEGUIMIENTODEPEDIDOS_GUIA, jsonObj.getString(TAG_VNSEGUIMIENTODEPEDIDOS_GUIA));
            values.put(COL_VNSEGUIMIENTODEPEDIDOS_CONDUCTO, jsonObj.getString(TAG_VNSEGUIMIENTODEPEDIDOS_CONDUCTO));
            values.put(COL_VNSEGUIMIENTODEPEDIDOS_FECHAGUIA, jsonObj.getString(TAG_VNSEGUIMIENTODEPEDIDOS_FECHAGUIA));
            values.put(COL_VNSEGUIMIENTODEPEDIDOS_CONFIRMOENVIO, jsonObj.getString(TAG_VNSEGUIMIENTODEPEDIDOS_CONFIRMOENVIO));
            values.put(COL_VNSEGUIMIENTODEPEDIDOS_FECHACONFENVIO, jsonObj.getString(TAG_VNSEGUIMIENTODEPEDIDOS_FECHACONFENVIO));
            values.put(COL_VNSEGUIMIENTODEPEDIDOS_CONFIRMORECEPCION, jsonObj.getString(TAG_VNSEGUIMIENTODEPEDIDOS_CONFIRMORECEPCION));
            values.put(COL_VNSEGUIMIENTODEPEDIDOS_FECHACONFRECEPCION, jsonObj.getString(TAG_VNSEGUIMIENTODEPEDIDOS_FECHACONFRECEPCION));
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        } // fin del catch

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_VN_SEGUIMIENTO_DE_PEDIDOS, null, values);
        db.close();
    }

    public void insertaVnCatAgentes(JSONObject jsonObj)
    {
        ContentValues values = new ContentValues();
        try
        {
            values.put(COL_VNCATAGENTES_CVEUSUARIO, jsonObj.getString(TAG_VNCATAGENTES_CVEUSUARIO));
            values.put(COL_VNCATAGENTES_NOMBRE, jsonObj.getString(TAG_VNCATAGENTES_NOMBRE));

        }
        catch (JSONException e)
        {
            e.printStackTrace();
        } // fin del catch

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_VN_CAT_AGENTES, null, values);
        db.close();
    }

    public void insertaVnSeguimientoDePagos(JSONObject jsonObj)
    {
        ContentValues values = new ContentValues();
        try
        {
            values.put(COL_VNSEGUIMIENTODEPAGOS_CVECLIENTE, jsonObj.getString(TAG_VNSEGUIMIENTODEPAGOS_CVECLIENTE));
            values.put(COL_VNSEGUIMIENTODEPAGOS_NUMDOCUMENTO, jsonObj.getString(TAG_VNSEGUIMIENTODEPAGOS_NUMDOCUMENTO));
            values.put(COL_VNSEGUIMIENTODEPAGOS_FECHADOCUMENTO, jsonObj.getString(TAG_VNSEGUIMIENTODEPAGOS_FECHADOCUMENTO));
            values.put(COL_VNSEGUIMIENTODEPAGOS_MONTO, jsonObj.getString(TAG_VNSEGUIMIENTODEPAGOS_MONTO));
            values.put(COL_VNSEGUIMIENTODEPAGOS_CVETIPOPAGO, jsonObj.getString(TAG_VNSEGUIMIENTODEPAGOS_CVETIPOPAGO));
            values.put(COL_VNSEGUIMIENTODEPAGOS_BANCOER, jsonObj.getString(TAG_VNSEGUIMIENTODEPAGOS_BANCOER));
            values.put(COL_VNSEGUIMIENTODEPAGOS_REFERENCIA, jsonObj.getString(TAG_VNSEGUIMIENTODEPAGOS_REFERENCIA));
            values.put(COL_VNSEGUIMIENTODEPAGOS_FECHABANCO, jsonObj.getString(TAG_VNSEGUIMIENTODEPAGOS_FECHABANCO));
            values.put(COL_VNSEGUIMIENTODEPAGOS_CAPTURO, jsonObj.getString(TAG_VNSEGUIMIENTODEPAGOS_CAPTURO));
            values.put(COL_VNSEGUIMIENTODEPAGOS_FECHACONCILIACION, jsonObj.getString(TAG_VNSEGUIMIENTODEPAGOS_FECHACONCILIACION));
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        } // fin del catch

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_VN_SEGUIMIENTO_DE_PAGOS, null, values);
        db.close();
    }

    // Inserta un registro individual en la tabla movil  vn_programa_rutas_semanales
    public void insertaVnProgramaRutasSemanales(JSONObject jsonObj)
    {
        ContentValues values = new ContentValues();
        try
        {
            values.put(COL_VNPROGRAMARUTASSEMANALES_FECHARUTA, jsonObj.getString(TAG_VNPROGRAMARUTASSEMANALES_FECHARUTA));
            values.put(COL_VNPROGRAMARUTASSEMANALES_NUMERODIA, jsonObj.getString(TAG_VNPROGRAMARUTASSEMANALES_NUMERODIA));
            values.put(COL_VNPROGRAMARUTASSEMANALES_CVECLIENTE, jsonObj.getString(TAG_VNPROGRAMARUTASSEMANALES_CVECLIENTE));
            values.put(COL_VNPROGRAMARUTASSEMANALES_POBLACION, jsonObj.getString(TAG_VNPROGRAMARUTASSEMANALES_POBLACION));
            values.put(COL_VNPROGRAMARUTASSEMANALES_ESTADO, jsonObj.getString(TAG_VNPROGRAMARUTASSEMANALES_ESTADO));
            values.put(COL_VNPROGRAMARUTASSEMANALES_VTAVET, jsonObj.getString(TAG_VNPROGRAMARUTASSEMANALES_VTAVET));
            values.put(COL_VNPROGRAMARUTASSEMANALES_FECHAVTAVET, jsonObj.getString(TAG_VNPROGRAMARUTASSEMANALES_FECHAVTAVET));
            values.put(COL_VNPROGRAMARUTASSEMANALES_VTAAGR, jsonObj.getString(TAG_VNPROGRAMARUTASSEMANALES_VTAAGR));
            values.put(COL_VNPROGRAMARUTASSEMANALES_FECHAVTAAGR, jsonObj.getString(TAG_VNPROGRAMARUTASSEMANALES_FECHAVTAAGR));
            /*
            values.put(COL_VNPROGRAMARUTASSEMANALES_COBVET, jsonObj.getString(TAG_VNPROGRAMARUTASSEMANALES_COBVET));
            values.put(COL_VNPROGRAMARUTASSEMANALES_FECHACOBVET, jsonObj.getString(TAG_VNPROGRAMARUTASSEMANALES_FECHACOBVET));
            values.put(COL_VNPROGRAMARUTASSEMANALES_COBAGR, jsonObj.getString(TAG_VNPROGRAMARUTASSEMANALES_COBAGR));
            values.put(COL_VNPROGRAMARUTASSEMANALES_FECHACOBAGR, jsonObj.getString(TAG_VNPROGRAMARUTASSEMANALES_FECHACOBAGR));
            */
            values.put(COL_VNPROGRAMARUTASSEMANALES_SALDO, jsonObj.getString(TAG_VNPROGRAMARUTASSEMANALES_SALDO));
            values.put(COL_VNPROGRAMARUTASSEMANALES_SALDOVIEJO, jsonObj.getString(TAG_VNPROGRAMARUTASSEMANALES_SALDOVIEJO));
            values.put(COL_VNPROGRAMARUTASSEMANALES_DIASSALDOVIEJO, jsonObj.getString(TAG_VNPROGRAMARUTASSEMANALES_DIASSALDOVIEJO));
            values.put(COL_VNPROGRAMARUTASSEMANALES_CANTIDADADULTO, jsonObj.getString(TAG_VNPROGRAMARUTASSEMANALES_CANTIDADADULTO));
            /*
            values.put(COL_VNPROGRAMARUTASSEMANALES_FECHAADULTO, jsonObj.getString(TAG_VNPROGRAMARUTASSEMANALES_FECHAADULTO));
            */
            values.put(COL_VNPROGRAMARUTASSEMANALES_CANTIDADPUPPY, jsonObj.getString(TAG_VNPROGRAMARUTASSEMANALES_CANTIDADPUPPY));
            /*
            values.put(COL_VNPROGRAMARUTASSEMANALES_FECHAPUPPY, jsonObj.getString(TAG_VNPROGRAMARUTASSEMANALES_FECHAPUPPY));
            */
            values.put(COL_VNPROGRAMARUTASSEMANALES_ORDEN, jsonObj.getString(TAG_VNPROGRAMARUTASSEMANALES_ORDEN));
            values.put(COL_VNPROGRAMARUTASSEMANALES_HORAPRIMERAVISITA, jsonObj.getString(TAG_VNPROGRAMARUTASSEMANALES_HORAPRIMERAVISITA));
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        } // fin del catch

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_VN_PROGRAMA_RUTAS_SEMANALES, null, values);
        db.close();

    } // FIN DE insertaVnProgramaRutasSemanales

    //Inserta las entidades en la tabla entidades
    public void insertaGlCatEntidades(JSONObject jsonObj){
        ContentValues values=new ContentValues();
        try{
            values.put(COL_GLCATENTIDADES_CVE_ENTIDAD,jsonObj.getString(TAG_GLCATENTIDADES_CVE_ENTIDAD));
            values.put(COL_GLCATENTIDADES_NOMBRE_ENTIDAD,jsonObj.getString(TAG_GLCATENTIDADES_NOMBRE_ENTIDAD));
        }
        catch(JSONException e){
            e.printStackTrace();
        }
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_GL_CAT_ENTIDADES, null, values);
        db.close();
    }
    //Inserta las localidades en la tabla localidades
    public void insertaGlCatLocalidades(JSONObject jsonObj){
        ContentValues values=new ContentValues();
        try{
            values.put(COL_GLCATLOCALIDADES_CVE_LOCALIDAD,jsonObj.getString(TAG_GLCATLOCALIDADES_CVE_LOCALIDAD));
            values.put(COL_GLCATLOCALIDADES_CVE_LOCALIDAD,jsonObj.getString(TAG_GLCATLOCALIDADES_CVE_LOCALIDAD));
            values.put(COL_GLCATLOCALIDADES_CVE_ENTIDAD,jsonObj.getString(TAG_GLCATLOCALIDADES_CVE_ENTIDAD));
            values.put(COL_GLCATLOCALIDADES_NOMBRE_LOCALIDAD,jsonObj.getString(TAG_GLCATLOCALIDADES_NOMBRE_LOCALIDAD));
        }
        catch(JSONException e){
            e.printStackTrace();
        }
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_GL_CAT_LOCALIDADES, null, values);
        db.close();
    }

    //Inserta los datos de la tabla de la recerva
    /*public void insertaCtReserva(JSONObject jsonObj){
        ContentValues values=new ContentValues();
        try{
            values.put(COL_CTRESERVA_UUID,jsonObj.getString(TAG_CTRESERVA_UUID));
            values.put(COL_CTRESERVA_RFC,jsonObj.getString(TAG_CTRESERVA_RFC));
            values.put(COL_CTRESERVA_TOTAL,jsonObj.getString(TAG_CTRESERVA_TOTAL));
            values.put(COL_CTRESERVA_FECHAEXP,jsonObj.getString(TAG_CTRESERVA_FECHAEXP));
            values.put(COL_CTRESERVA_NOMBREPROVEEDOR,jsonObj.getString(TAG_CTRESERVA_NOMBREPROVEEDOR));
            values.put(COL_CTRESERVA_IDRESERVA,jsonObj.getString(TAG_CTRESERVA_IDRESERVA));
        }
        catch(JSONException e){
            e.printStackTrace();
        }
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_CT_RESERVA, null, values);
        db.close();
    }*/


    //Inserta los datos de la tabla de las politicas
    public void insertaVnPoliticas(JSONObject jsonObj){
        ContentValues values=new ContentValues();
        try{
            values.put(COL_VNPOLITICAS_ID_POLITICA,jsonObj.getString(TAG_VNPOLITICAS_ID_POLITICA));
            values.put(COL_VNPOLITICAS_TITULO,jsonObj.getString(TAG_VNPOLITICAS_TITULO));
            values.put(COL_VNPOLITICAS_ID_IMAGEN,jsonObj.getString(TAG_VNPOLITICAS_ID_IMAGEN));
            values.put(COL_VNPOLITICAS_IMAGEN,jsonObj.getString(TAG_VNPOLITICAS_IMAGEN));
        }
        catch(JSONException e){
            e.printStackTrace();
        }
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_VN_POLITICAS, null, values);
        db.close();
    }

    // Al colocarse al inicio del c—digo provoca y forza la creaci—n f’sica de la BD as’ como sus tablas.
    public void checkDBStatus()
    {
        String query = "SELECT * FROM " + TABLE_GL_SYNC;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.close();
        db.close();
    }

    // Inserta el usuario entrante en la bitacora interna del sistema
    public void registraBitacora(String cveUsuario)
    {
        ContentValues values = new ContentValues();

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fechaRegistro = sdf.format(cal.getTime());
        String Query = "";

        values.put(COL_GLBITACORAACCESOS_CVEUSUARIO, cveUsuario.toString());
        values.put(COL_GLBITACORAACCESOS_FECHAREGISTRO, fechaRegistro.toString());
        SQLiteDatabase db = this.getWritableDatabase();

        // Medida de depuración: antes de registrar el usuario se borran los accesos que tengan mas de dos meses de la bitacora
        Query = "DELETE FROM  " + TABLE_GL_BITACORA_ACCESOS + " where datetime(fecha_registro) < datetime(fecha_registro, '-2 month')";
        db.execSQL(Query);
        db.insert(TABLE_GL_BITACORA_ACCESOS, null, values);
        db.close();
    }

    // Inserta en la tabla gl_ruta_seleccionada cuando no es agente
    public void registraRutaSeleccionada(String numRuta){
        ContentValues values = new ContentValues();

        Cursor cursor = getReadableDatabase().rawQuery("select cve_agente from vn_cat_rutas where num_ruta = '"+ numRuta +"';", null);
        cursor.moveToFirst();
        String cve_agente = "";
        while (!cursor.isAfterLast()) {
            cve_agente = cursor.getString(cursor.getColumnIndex("cve_agente"));
            cursor.moveToNext();
        }
        cursor.close();

        String Query = "";

        values.put(COL_GLRUTASELECCIONADA_NUMRUTA, numRuta.toString());
        values.put(COL_GLRUTASELECCIONADA_CVEAGENTE, cve_agente.toString());
        SQLiteDatabase db = this.getWritableDatabase();

        // Medida de depuración: antes de registrar el usuario se borran los accesos que tengan mas de dos meses de la bitacora
        Query = "DELETE FROM  " + TABLE_GL_RUTA_SELECCIONADA + " ;";
        db.execSQL(Query);
        db.insert(TABLE_GL_RUTA_SELECCIONADA, null, values);
        db.close();
    }

    // Metodo para obtener la ruta que se selecciono cuando no eres agente
    public String rutaSeleccionada()
    {
        String num_ruta = "";
        String Query = "select num_ruta from "+ TABLE_GL_RUTA_SELECCIONADA +";";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(Query, null);

        while (cursor.moveToNext())
        {
            num_ruta = cursor.getString(cursor.getColumnIndex("num_ruta"));
        }

        return num_ruta;
    }

    // Metodo para obtener el agente que se selecciono cuando no eres agente
    public String agenteSeleccionado()
    {
        String cve_agente = "";
        String Query = "select cve_agente from "+ TABLE_GL_RUTA_SELECCIONADA +";";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(Query, null);

        while (cursor.moveToNext())
        {
            cve_agente = cursor.getString(cursor.getColumnIndex("cve_agente"));
        }

        return cve_agente;
    }

    // Inserta en la tabla gl_ruta_seleccionada cuando no es agente
    public void registraFechaMarketing(){
        ContentValues values = new ContentValues();

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String fechaHoy = sdf.format(cal.getTime());

        values.put(COL_GLMARKETING_FECHA, fechaHoy.toString());
        SQLiteDatabase db = this.getWritableDatabase();

        // Medida de depuración: antes de registrar el usuario se borran los accesos que tengan mas de dos meses de la bitacora
        db.insert(TABLE_GL_MARKETING, null, values);
        db.close();
    }

    // Metodo para obtener la ultima fecha que se mostro el marketing
    public boolean marketingFecha()
    {
        boolean mandar = true;
        String fecha = "";
        String Query = "select fecha from "+ TABLE_GL_MARKETING +" order by fecha desc limit 1;";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(Query, null);

        while (cursor.moveToNext())
        {
            fecha = cursor.getString(cursor.getColumnIndex("fecha"));
        }

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String fechaHoy = sdf.format(cal.getTime());

        if(fecha.equals(fechaHoy)){
            mandar = false;
        }

        return mandar;
    }

    // Metodo que regresa la clave del ultimo usuario que fue registrado en la bitácora interna del sistema móvil en este dispositivo
    public String ultimoUsuarioRegistrado()
    {
        String cveUsuario = "";
        String Query = "select cve_usuario from gl_bitacora_accesos order by datetime(fecha_registro) desc limit 1";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(Query, null);

        while (cursor.moveToNext())
        {
            cveUsuario = cursor.getString(0);
        }

        return cveUsuario;
    }

    //Método para obtener las entidades del país
    public String[] getEntidades(){
        Cursor cursor = getReadableDatabase().rawQuery("select '' || cve_entidad || '- ' || nombre_entidad as entidad from gl_cat_entidades order by cve_entidad;", null);
        cursor.moveToFirst();
        ArrayList<String> nombreEntidad = new ArrayList<String>();
        while(!cursor.isAfterLast()) {
            nombreEntidad.add(cursor.getString(cursor.getColumnIndex("entidad")));
            cursor.moveToNext();
        }
        cursor.close();
        return nombreEntidad.toArray(new String[nombreEntidad.size()]);
    }
    //Método para obtener las localidades de las entidades del país
    public String[] getLocalidades(String cve_entidad){
        Cursor cursor = getReadableDatabase().rawQuery("select '' || cve_localidad || '- ' || nombre_localidad as localidad from gl_cat_localidades where cve_entidad='"+cve_entidad+"' order by cve_localidad;", null);
        cursor.moveToFirst();
        ArrayList<String> nombreLocalidad = new ArrayList<String>();
        while(!cursor.isAfterLast()) {
            nombreLocalidad.add(cursor.getString(cursor.getColumnIndex("localidad")));
            cursor.moveToNext();
        }
        cursor.close();
        return nombreLocalidad.toArray(new String[nombreLocalidad.size()]);
    }


    // Metodo que es usado para obtener los datos de los clientes de la tabla vn_cat_clientes los coloca en un array y los regresa en la funci—n.
    public String[] getClientes()
    {
        Cursor cursor = getReadableDatabase().rawQuery("select '' || cve_usuario || '- ' || nombre as cliente from vn_cat_clientes order by nombre;", null);
        cursor.moveToFirst();
        ArrayList<String> nombreCliente = new ArrayList<String>();
        while(!cursor.isAfterLast()) {
            nombreCliente.add(cursor.getString(cursor.getColumnIndex("cliente")));
            cursor.moveToNext();
        }
        cursor.close();
        return nombreCliente.toArray(new String[nombreCliente.size()]);
    }

    public String[] getClientes2()
    {
        Cursor cursor = getReadableDatabase().rawQuery("select '' || cve_usuario || '- ' || nombre as cliente from vn_cat_clientes where estatus!=0 order by nombre;", null);
        cursor.moveToFirst();
        ArrayList<String> nombreCliente = new ArrayList<String>();
        while(!cursor.isAfterLast()) {
            nombreCliente.add(cursor.getString(cursor.getColumnIndex("cliente")));
            cursor.moveToNext();
        }
        cursor.close();
        return nombreCliente.toArray(new String[nombreCliente.size()]);
    }

    // Metodo que es usado para obtener los datos de los productos de la tabla vn_cat_clientes los coloca en un array y los regresa en la funci—n.
    public String[] getProductos(String familia, String subtipo)
    {
        String familia_check = "";
        String cve_familia = "";
        String subtipo_check = "";
        String cve_cat_producto = "";
        familia_check = familia;
        subtipo_check = subtipo;

        if(subtipo_check.toString().equals("Normal") || subtipo_check.toString().equals("Muestras Gratis")) {
            if (familia_check.toString().equals("Agricola")) {
                cve_familia = "'01'";
                cve_cat_producto = " and cve_cat_producto not in (10219,10220,12062,12063,12299,12300,12449,12450,12451,12452,12610,12611,12612,12613,12614,12615,12627,12628,12634,11349,12656) ";
            } else {
                cve_familia = "'02', '03', '04', '05'";
            }
        }

        if(subtipo_check.toString().equals("Paq. Granos")){
            cve_familia = "'01'";
            //cve_cat_producto = " and cve_cat_producto in (10219,10220,12062,12063,12299,12300,12449,12450,12451,12452,12610,12611,12612,12613,12614,12615,12627,12628,12634,11349,12656) ";
            cve_cat_producto = "and nom_producto LIKE '%Paquete V%'";
        }

        Cursor cursor = getReadableDatabase().rawQuery("select '' || cve_cat_producto || '- ' || nom_producto as producto from in_cat_productos where cve_compania = '019' and estatus = 'A' and es_venta = '1' and cve_familia in (" + cve_familia + ") "+ cve_cat_producto +"  order by nom_producto;", null);
        cursor.moveToFirst();
        ArrayList<String> nombreProducto = new ArrayList<String>();
        while (!cursor.isAfterLast()) {
            nombreProducto.add(cursor.getString(cursor.getColumnIndex("producto")));
            cursor.moveToNext();
        }
        cursor.close();

        return nombreProducto.toArray(new String[nombreProducto.size()]);

    }

    // Metodo que es usado para obtener los datos de los productos veterinarios de la tabla vn_cat_clientes los coloca en un array y los regresa en la funci—n.
    public String[] getProductosVeterinarios()
    {
        Cursor cursor = getReadableDatabase().rawQuery("select '' || cve_cat_producto || '- ' || nom_producto as producto from in_cat_productos where cve_compania = '019' and estatus = 'A' and es_venta = '1' and cve_producto != '01' order by nom_producto;", null);
        cursor.moveToFirst();
        ArrayList<String> nombreProducto = new ArrayList<String>();
        while(!cursor.isAfterLast()) {
            nombreProducto.add(cursor.getString(cursor.getColumnIndex("producto")));
            cursor.moveToNext();
        }
        cursor.close();
        return nombreProducto.toArray(new String[nombreProducto.size()]);
    }

    // Metodo que es usado para obtener los datos de los conductos de la tabla vn_cat_conductos los coloca en un array y los regresa en la funci—n.
    public String[] getConductos()
    {
        Cursor cursor = getReadableDatabase().rawQuery("select '' || cve_conducto || '-' || nombre_conducto as conducto from vn_cat_conductos where cve_compania = '019' order by nombre_conducto;", null);
        cursor.moveToFirst();
        ArrayList<String> nombreConducto = new ArrayList<String>();
        while(!cursor.isAfterLast()) {
            nombreConducto.add(cursor.getString(cursor.getColumnIndex("conducto")));
            cursor.moveToNext();
        }
        cursor.close();
        return nombreConducto.toArray(new String[nombreConducto.size()]);
    }

    // Metodo para validar el usuario y contrasena en el acceso
    public boolean validacion(String campoUsuario, String campoPassword) {

        String usuario = campoUsuario;
        String password = campoPassword;
        Boolean valida = false;

        Cursor cursor = getReadableDatabase().rawQuery("select cve_usuario, password from gl_accesos where cve_usuario = '" + usuario + "' and password = '" + password + "' and estatus = '1';", null);
        cursor.moveToFirst();
        String usuarioCheck = new String();
        String passwordCheck = new String();
        while (!cursor.isAfterLast()) {
            usuarioCheck = cursor.getString(cursor.getColumnIndex("cve_usuario"));
            passwordCheck = cursor.getString(cursor.getColumnIndex("password"));
            cursor.moveToNext();
        }
        cursor.close();

        if(usuario.toString().equalsIgnoreCase(usuarioCheck) && password.toString().equalsIgnoreCase(passwordCheck)){
            valida = true;
        }

        return valida;

    }

    public String num_ruta(String agente){
        String Agente = agente;

        Log.d("agente ",Agente);

        Cursor cursor = getReadableDatabase().rawQuery("select num_ruta from vn_cat_rutas where cve_compania = '019' and cve_agente = '"+ Agente +"';", null);
        cursor.moveToFirst();
        String num_ruta = "";
        while (!cursor.isAfterLast()) {
            num_ruta = cursor.getString(cursor.getColumnIndex("num_ruta"));
            cursor.moveToNext();
        }
        cursor.close();


        if(num_ruta.length() <= 0){
            num_ruta = "0";
        }


        return num_ruta;
    }

    // Metodo para verificar el siguiente numero de pedido
    public String siguiente(){
        Cursor cursor = getReadableDatabase().rawQuery("select case when max(num_pedido) is null then 1 else max(num_pedido) + 1 end as siguiente from vn_pedidos_encabezado  where  cve_compania = '019';", null);
        cursor.moveToFirst();
        String siguiente_pedido = "";
        while (!cursor.isAfterLast()) {
            siguiente_pedido = cursor.getString(cursor.getColumnIndex("siguiente"));
            cursor.moveToNext();
        }
        cursor.close();

        return siguiente_pedido;

    }

    //Metodo para verificar el numero de pedido actual
    public String pedido_actual(){
        Cursor cursor = getReadableDatabase().rawQuery("select case when max(num_pedido) is null then 1 else max(num_pedido) end as num_pedido from vn_pedidos_encabezado  where  cve_compania = '019';", null);
        cursor.moveToFirst();
        String num_pedido = "";
        while (!cursor.isAfterLast()) {
            num_pedido = cursor.getString(cursor.getColumnIndex("num_pedido"));
            cursor.moveToNext();
        }
        cursor.close();

        return num_pedido;

    }

    //Metodo para sacar la siguiente partida
    public String partida_actual(){
        Cursor cursor = getReadableDatabase().rawQuery("select case when max(num_pedido) is null then 1 else max(num_pedido) end as num_pedido from vn_pedidos_encabezado  where  cve_compania = '019';", null);
        cursor.moveToFirst();
        String num_pedido = "";
        while (!cursor.isAfterLast()) {
            num_pedido = cursor.getString(cursor.getColumnIndex("num_pedido"));
            cursor.moveToNext();
        }
        cursor.close();

        Cursor cursor2 = getReadableDatabase().rawQuery("select case when max(num_partida) is null then 1 else max(num_partida) end as num_partida from vn_pedidos_partidas  where  cve_compania = '019' and num_pedido = "+ num_pedido +";", null);
        cursor2.moveToFirst();
        String num_partida = "";
        while (!cursor2.isAfterLast()) {
            num_partida = cursor2.getString(cursor2.getColumnIndex("num_partida"));
            cursor2.moveToNext();
        }
        cursor2.close();

        return num_partida;

    }

    //Metodo para sacar el conducto
    public String conducto_pedido(){
        Cursor cursor = getReadableDatabase().rawQuery("select case when max(num_pedido) is null then 1 else max(num_pedido) end as num_pedido from vn_pedidos_encabezado  where  cve_compania = '019';", null);
        cursor.moveToFirst();
        String num_pedido = "";
        while (!cursor.isAfterLast()) {
            num_pedido = cursor.getString(cursor.getColumnIndex("num_pedido"));
            cursor.moveToNext();
        }
        cursor.close();

        Cursor cursor2 = getReadableDatabase().rawQuery("select cve_conducto from vn_pedidos_partidas  where  cve_compania = '019' and num_pedido = "+ num_pedido +";", null);
        cursor2.moveToFirst();
        String cve_conducto = "";
        while (!cursor2.isAfterLast()) {
            cve_conducto = cursor2.getString(cursor2.getColumnIndex("cve_conducto"));
            cursor2.moveToNext();
        }
        cursor2.close();

        return cve_conducto;

    }

    //Metodo para validar que no se esten combinando productos de diferente familia
    public boolean validaProductosFamilia(String cve, String cve_familia){
        String cve_cat_producto = cve;
        String cve_familia_seleccionada = cve_familia;
        String familia_consulta = "";
        Boolean valida_familia = false;
        String cve_familia_producto = "";

        Cursor cursor = getReadableDatabase().rawQuery("select cve_familia from in_cat_productos where cve_compania = '019' and cve_cat_producto = '"+ cve_cat_producto +"';", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            cve_familia_producto = cursor.getString(cursor.getColumnIndex("cve_familia"));
            cursor.moveToNext();
        }
        cursor.close();

        if(cve_familia_producto.toString().equals("01")){
            familia_consulta = "Agricola";
        }
        else {
            familia_consulta = "Veterinaria";
        }

        if(cve_familia_seleccionada.toString().equalsIgnoreCase(familia_consulta)){
            valida_familia = true;
        }

        return valida_familia;
    }

    //Metodo para obtener el precio del producto
    public double precioProducto (String cve){
        String cve_cat_producto = cve;
        double precio_unitario = 0.0;

        Cursor cursor = getReadableDatabase().rawQuery("select precio_unitario from in_cat_productos where cve_compania = '019' and cve_cat_producto = '"+ cve_cat_producto +"';", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            precio_unitario = cursor.getDouble(cursor.getColumnIndex("precio_unitario"));
            cursor.moveToNext();
        }
        cursor.close();

        return precio_unitario;
    }

    //Metodo para obtener si el producto es un paquete
    public boolean esPaquete (String cve){
        String cve_cat_producto = cve;
        double cve_producto = 0;
        boolean paquete = false;

        Cursor cursor = getReadableDatabase().rawQuery("select cve_producto from in_cat_productos where cve_compania = '019' and cve_cat_producto = '"+ cve_cat_producto +"';", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            cve_producto = cursor.getDouble(cursor.getColumnIndex("cve_producto"));
            cursor.moveToNext();
        }
        cursor.close();

        if(cve_producto >= 7000){
            paquete = true;
        }

        return paquete;
    }

    //Metodo para sacar la existencia de un producto cuando es venta de mano
    public String existenciaMano ( String cve, String cliente){
        String cve_cat_producto = cve;
        String cve_cliente = cliente;
        String existencias = "";
        String cve_centro_costo = "";

        //Log.d("cliente ",cve_cliente);
        //Log.d("producto ",cve_cat_producto);


        Cursor cursor = getReadableDatabase().rawQuery("select vcap.cve_centro_costo from vn_rutas_asociaciones vra " +
                "inner join vn_cat_rutas vcr " +
                "on vra.cve_compania = vcr.cve_compania " +
                "and vra.num_ruta = vcr.num_ruta " +
                "inner join vn_cat_agentes_parametros vcap " +
                "on vcr.cve_compania = vcap.cve_compania " +
                "and vcr.cve_agente = vcap.cve_agente " +
                "where vra.cve_compania = '019' and vra.cve_cliente = '"+ cve_cliente +"';", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            cve_centro_costo = cursor.getString(cursor.getColumnIndex("cve_centro_costo"));
            cursor.moveToNext();
        }
        cursor.close();

        Cursor cursor2 = getReadableDatabase().rawQuery("select existencias from in_centros_inventarios where cve_compania = '019' " +
                " and cve_centro_costo = '"+ cve_centro_costo +"' and cve_cat_producto = '"+ cve_cat_producto +"';", null);
        cursor2.moveToFirst();
        while (!cursor2.isAfterLast()) {
            existencias = cursor2.getString(cursor2.getColumnIndex("existencias"));
            cursor2.moveToNext();
        }
        cursor2.close();

        if(existencias.toString() == ""){
            existencias = "0";
        }

        return existencias;
    }

    //Metodo para sacar las piezas por paquete de un producto cuando es venta de almacen
    public boolean piezasPaquete (String cve, Integer cant){
        String cve_cat_producto = cve;
        Integer cantidad = cant;
        Integer piezas_por_paquete = 0;
        Integer resto = 0;
        boolean pasa = false;

        Cursor cursor = getReadableDatabase().rawQuery("select piezas_por_paquete from in_cat_productos where cve_compania = '019' and cve_cat_producto = '"+ cve_cat_producto +"';", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            piezas_por_paquete = cursor.getInt(cursor.getColumnIndex("piezas_por_paquete"));
            cursor.moveToNext();
        }
        cursor.close();

        resto = cantidad%piezas_por_paquete;

        if(resto==0){
            pasa = true;
        }

        return pasa;
    }

    // Metodo que es usado para obtener el listado de las ofertas de la tabla in_cat_productos los coloca en un array y los regresa en la funci—n.
    public String[] getListadoOfertasAgricola()
    {
        String cve_producto = "";
        Cursor cursor = getReadableDatabase().rawQuery("select cve_producto, cve_producto || '-' || nom_producto || '  Precio: ' || precio_unitario as nom_producto from in_cat_productos where cve_compania = '019' and estatus = 'A' and es_venta = '1' and cve_producto >= '7000' and cve_producto <= '7999' and cve_familia = '01' order by cve_producto;", null);
        cursor.moveToFirst();
        ArrayList<String> nombreOferta = new ArrayList<String>();
        while(!cursor.isAfterLast()) {
            cve_producto = cursor.getString(cursor.getColumnIndex("cve_producto"));
            nombreOferta.add(cursor.getString(cursor.getColumnIndex("nom_producto")));

            cursor.moveToNext();
        }
        cursor.close();
        return nombreOferta.toArray(new String[nombreOferta.size()]);
    }

    //Metodo para desglosar las ofertas agricola
    public String[] getListadoOfertasAgricolaDesgloce(String cve)
    {
        String cve_producto = "";
        cve_producto = cve;
        Cursor cursor = getReadableDatabase().rawQuery("SELECT '- (' || icp.cve_producto || ') ' || icp.nom_producto || ' Cantidad: ' || vpp.cantidad || ' Porc. Desc: ' || vpp.porcentaje_descuento as desgloce FROM vn_paquetes_partidas vpp " +
                "INNER JOIN in_cat_productos icp ON vpp.cve_compania = icp.cve_compania " +
                "AND vpp.cve_cat_producto = icp.cve_cat_producto " +
                "AND vpp.cve_compania = '019' " +
                "AND vpp.num_paquete = '"+ cve_producto +"' " +
                "ORDER BY vpp.num_partida;", null);
        cursor.moveToFirst();
        ArrayList<String> desgloce = new ArrayList<String>();
        while(!cursor.isAfterLast()) {
            desgloce.add(cursor.getString(cursor.getColumnIndex("desgloce")));

            cursor.moveToNext();
        }
        cursor.close();
        return desgloce.toArray(new String[desgloce.size()]);
    }

    // Metodo que es usado para obtener el listado de las ofertas de la tabla in_cat_productos los coloca en un array y los regresa en la funci—n.
    public String[] getListadoOfertasVeterinaria()
    {
        String cve_producto = "";
        Cursor cursor = getReadableDatabase().rawQuery("select cve_producto,  cve_producto || '-' || nom_producto || '  Precio: ' || precio_unitario as nom_producto from in_cat_productos where cve_compania = '019' and estatus = 'A' and es_venta = '1' and cve_producto >= '7000' and cve_producto <= '7999' and cve_familia != '01' order by cve_producto;", null);
        cursor.moveToFirst();
        ArrayList<String> nombreOferta = new ArrayList<String>();
        while(!cursor.isAfterLast()) {
            cve_producto = cursor.getString(cursor.getColumnIndex("cve_producto"));
            nombreOferta.add(cursor.getString(cursor.getColumnIndex("nom_producto")));

            cursor.moveToNext();
        }
        cursor.close();
        return nombreOferta.toArray(new String[nombreOferta.size()]);
    }

    // Metodo para obtener el desgloce de las ofertas veterinaria
    public String[] getListadoOfertasVeterinariaDesgloce(String cve)
    {
        String cve_producto = "";
        cve_producto = cve;
        Cursor cursor = getReadableDatabase().rawQuery("SELECT '- (' || icp.cve_producto || ') ' || icp.nom_producto || ' Cantidad: ' || vpp.cantidad || ' Porc. Desc: ' || vpp.porcentaje_descuento as desgloce FROM vn_paquetes_partidas vpp " +
                "INNER JOIN in_cat_productos icp ON vpp.cve_compania = icp.cve_compania " +
                "AND vpp.cve_cat_producto = icp.cve_cat_producto " +
                "AND vpp.cve_compania = '019' " +
                "AND vpp.num_paquete = '"+ cve_producto +"' " +
                "ORDER BY vpp.num_partida;", null);
        cursor.moveToFirst();
        ArrayList<String> desgloce = new ArrayList<String>();
        while(!cursor.isAfterLast()) {
            desgloce.add(cursor.getString(cursor.getColumnIndex("desgloce")));

            cursor.moveToNext();
        }
        cursor.close();
        return desgloce.toArray(new String[desgloce.size()]);
    }

    // Metodo que es usado para obtener el listado de los productos de la tabla in_cat_productos los coloca en un array y los regresa en la funci—n.
    public String[] getListadoClientes()
    {
        Cursor cursor = getReadableDatabase().rawQuery("select cve_usuario || '-' || nombre as cliente from vn_cat_clientes where estatus = '1' order by nombre;", null);
        cursor.moveToFirst();
        ArrayList<String> clientes = new ArrayList<String>();
        while(!cursor.isAfterLast()) {
            clientes.add(cursor.getString(cursor.getColumnIndex("cliente")));
            cursor.moveToNext();
        }
        cursor.close();
        return clientes.toArray(new String[clientes.size()]);
    }

    // Metodo que es usado para obtener el listado de los productos de la tabla in_cat_productos los coloca en un array y los regresa en la funci—n.
    public String[] getListadoClientesDesgloce(String cve)
    {
        String cve_cliente = cve;
        Cursor cursor = getReadableDatabase().rawQuery("select 'Direccion: ' || calle_domicilio as calle,  'Colonia: ' || colonia as colonia, 'CP: ' || codigo_postal as cp, 'RFC: ' || rfc as rfc, 'Tel: ' || telefonos as telefonos, 'Email: ' || email as email from vn_cat_clientes where estatus = '1' and cve_usuario = "+ cve_cliente +" order by nombre;", null);
        cursor.moveToFirst();
        ArrayList<String> desgloce = new ArrayList<String>();
        while(!cursor.isAfterLast()) {
            desgloce.add(cursor.getString(cursor.getColumnIndex("calle")));
            desgloce.add(cursor.getString(cursor.getColumnIndex("colonia")));
            desgloce.add(cursor.getString(cursor.getColumnIndex("cp")));
            desgloce.add(cursor.getString(cursor.getColumnIndex("rfc")));
            desgloce.add(cursor.getString(cursor.getColumnIndex("telefonos")));
            desgloce.add(cursor.getString(cursor.getColumnIndex("email")));
            cursor.moveToNext();
        }
        cursor.close();
        return desgloce.toArray(new String[desgloce.size()]);
    }

    // Metodo que es usado para obtener el listado de los clientes de la tabla vn_cat_clientes los coloca en un array y los regresa en la funci—n.
    public String[] getListadoProductosAgricola()
    {
        Cursor cursor = getReadableDatabase().rawQuery("select cve_producto, cve_producto || '-' || nom_producto as producto from in_cat_productos where cve_compania = '019' and es_venta = '1' and estatus = 'A' and cve_producto < '7000' and cve_familia = '01' order by nom_producto;", null);
        cursor.moveToFirst();
        ArrayList<String> productos = new ArrayList<String>();
        while(!cursor.isAfterLast()) {
            productos.add(cursor.getString(cursor.getColumnIndex("producto")));
            cursor.moveToNext();
        }
        cursor.close();
        return productos.toArray(new String[productos.size()]);
    }

    // Metodo que es usado para obtener el listado de los clientes de la tabla vn_cat_clientes los coloca en un array y los regresa en la funci—n.
    public String[] getListadoProductosDesgloce(String cve)
    {
        String cve_producto = "";
        cve_producto = cve;

        Cursor cursor = getReadableDatabase().rawQuery("select 'Precio: ' || precio_unitario as precio, 'Piezas por paquete: ' || piezas_por_paquete as piezas_por_paquete, 'UM: ' || cve_unidad_medida as cve_unidad_medida from in_cat_productos where cve_compania = '019' and es_venta = '1' and estatus = 'A' and cve_producto < '7000' and cve_producto ='"+cve_producto+"' order by nom_producto;", null);
        cursor.moveToFirst();
        ArrayList<String> productosDesgloce = new ArrayList<String>();
        while(!cursor.isAfterLast()) {
            productosDesgloce.add(cursor.getString(cursor.getColumnIndex("precio")));
            productosDesgloce.add(cursor.getString(cursor.getColumnIndex("piezas_por_paquete")));
            productosDesgloce.add(cursor.getString(cursor.getColumnIndex("cve_unidad_medida")));
            cursor.moveToNext();
        }
        cursor.close();
        return productosDesgloce.toArray(new String[productosDesgloce.size()]);
    }


    // Metodo que es usado para obtener el listado de los clientes de la tabla vn_cat_clientes los coloca en un array y los regresa en la funci—n.
    public String[] getListadoProductosVeterinaria()
    {
        Cursor cursor = getReadableDatabase().rawQuery("select cve_producto, cve_producto || '-' || nom_producto as producto from in_cat_productos where cve_compania = '019' and es_venta = '1' and estatus = 'A' and cve_producto < '7000' and cve_familia != '01' order by nom_producto;", null);
        cursor.moveToFirst();
        ArrayList<String> productos = new ArrayList<String>();
        String cve_producto = "";
        while(!cursor.isAfterLast()) {
            productos.add(cursor.getString(cursor.getColumnIndex("producto")));
            cursor.moveToNext();
        }
        cursor.close();
        return productos.toArray(new String[productos.size()]);
    }

    //Metodo para obtener el tipo de usuario que se logea, esto para ver si es agente, coordinador o usuario normal
    public String tipoUsuario (String cve){
        String cve_usuario = cve;
        String tipo_usuario = "";

        Cursor cursor = getReadableDatabase().rawQuery("select tipo_usuario from gl_accesos where cve_usuario = '"+ cve_usuario +"';", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            tipo_usuario = cursor.getString(cursor.getColumnIndex("tipo_usuario"));
            cursor.moveToNext();
        }
        cursor.close();

        return tipo_usuario;
    }

    //Metodo para sacar el listado de los clientes de seguimiento de pedidos
    public String[] getListadoSeguimiento()
    {
        Cursor cursor = getReadableDatabase().rawQuery("select 'Pedido: ' || vsp.num_pedido || '-' || vcc.nombre as nombre from vn_seguimiento_de_pedidos vsp " +
                "inner join vn_cat_clientes vcc " +
                "on vsp.cve_cliente = vcc.cve_usuario " +
                "group by num_pedido " +
                "order by nombre;", null);
        cursor.moveToFirst();
        ArrayList<String> clientes_seguimiento = new ArrayList<String>();
        while(!cursor.isAfterLast()) {
            clientes_seguimiento.add(cursor.getString(cursor.getColumnIndex("nombre")));
            cursor.moveToNext();
        }
        cursor.close();
        return clientes_seguimiento.toArray(new String[clientes_seguimiento.size()]);
    }

    //Metodo para sacar el desgloce del pedido seleccionado
    public String[] getListadoSeguimientoDesgloce(String num)
    {
        String num_pedido = num;
        Cursor cursor = getReadableDatabase().rawQuery("select 'Almacen: ' || almacen as almacen, 'Fecha pedido: ' || fecha as fecha, " +
                "'Monto Pedido: ' || monto_pedido as monto_pedido, 'Factura: ' || num_factura as num_factura, 'Monto factura: ' || monto_factura as monto_factura, " +
                "'Fecha factura: ' || fecha_factura as fecha_factura, 'Guia: ' || guia as guia, 'Conducto: ' || conducto as conducto, " +
                "'Fecha Guia: ' || fecha_guia as fecha_guia, 'Confirmo Envio: ' || confirmo_envio as confirmo_envio, 'Fecha Confirmo Envio: ' || fecha_conf_envio as fecha_conf_envio, " +
                "'Confirmo Recepcion: ' || confirmo_recepcion as confirmo_recepcion, 'Fecha Confirmo Recepcion: ' || fecha_conf_recepcion as fecha_conf_recepcion " +
                "from vn_seguimiento_de_pedidos where num_pedido = "+ num_pedido +" " +
                "group by num_pedido", null);
        cursor.moveToFirst();
        ArrayList<String> clientes_seguimiento_desgloce = new ArrayList<String>();
        String cve_producto = "";
        while(!cursor.isAfterLast()) {
            clientes_seguimiento_desgloce.add(cursor.getString(cursor.getColumnIndex("almacen")));
            clientes_seguimiento_desgloce.add(cursor.getString(cursor.getColumnIndex("fecha")));
            clientes_seguimiento_desgloce.add(cursor.getString(cursor.getColumnIndex("monto_pedido")));
            clientes_seguimiento_desgloce.add(cursor.getString(cursor.getColumnIndex("num_factura")));
            clientes_seguimiento_desgloce.add(cursor.getString(cursor.getColumnIndex("monto_factura")));
            clientes_seguimiento_desgloce.add(cursor.getString(cursor.getColumnIndex("fecha_factura")));
            clientes_seguimiento_desgloce.add(cursor.getString(cursor.getColumnIndex("guia")));
            clientes_seguimiento_desgloce.add(cursor.getString(cursor.getColumnIndex("conducto")));
            clientes_seguimiento_desgloce.add(cursor.getString(cursor.getColumnIndex("fecha_guia")));
            clientes_seguimiento_desgloce.add(cursor.getString(cursor.getColumnIndex("confirmo_envio")));
            clientes_seguimiento_desgloce.add(cursor.getString(cursor.getColumnIndex("fecha_conf_envio")));
            clientes_seguimiento_desgloce.add(cursor.getString(cursor.getColumnIndex("confirmo_recepcion")));
            clientes_seguimiento_desgloce.add(cursor.getString(cursor.getColumnIndex("fecha_conf_recepcion")));

            cursor.moveToNext();
        }
        cursor.close();
        return clientes_seguimiento_desgloce.toArray(new String[clientes_seguimiento_desgloce.size()]);
    }

    //Metodo para sacar el listado de los clientes de seguimiento de pedidos que no estan confirmados
    public String[] getListadoSeguimientoNoConfirmados()
    {
        Cursor cursor = getReadableDatabase().rawQuery("select 'Pedido: ' || vsp.num_pedido || '-' || vcc.nombre as nombre from vn_seguimiento_de_pedidos vsp " +
                "inner join vn_cat_clientes vcc " +
                "on vsp.cve_cliente = vcc.cve_usuario " +
                " where vsp.confirmo_recepcion = '' " +
                "group by vsp.num_pedido " +
                "order by nombre;", null);
        cursor.moveToFirst();
        ArrayList<String> clientes_seguimiento = new ArrayList<String>();
        while(!cursor.isAfterLast()) {
            clientes_seguimiento.add(cursor.getString(cursor.getColumnIndex("nombre")));
            cursor.moveToNext();
        }
        cursor.close();
        return clientes_seguimiento.toArray(new String[clientes_seguimiento.size()]);
    }

    //Metodo para sacar el listado de los clientes de seguimiento de pedidos
    public String[] getListadoPedidosApp()
    {
        Cursor cursor = getReadableDatabase().rawQuery("select 'Pedido: ' || vpe.num_pedido || '-' || vcc.nombre as nombre from vn_pedidos_encabezado vpe " +
                "inner join vn_cat_clientes vcc on vpe.cve_cliente = vcc.cve_usuario " +
                "order by vpe.num_pedido;", null);
        cursor.moveToFirst();
        ArrayList<String> clientes_pedidos = new ArrayList<String>();
        while(!cursor.isAfterLast()) {
            clientes_pedidos.add(cursor.getString(cursor.getColumnIndex("nombre")));
            cursor.moveToNext();
        }
        cursor.close();
        return clientes_pedidos.toArray(new String[clientes_pedidos.size()]);
    }

    //Metodo para sacar el desgloce del pedido seleccionado
    public String[] getListadoSeguimientoDesgloceApp(String num)
    {
        String num_pedido = num;
        Cursor cursor = getReadableDatabase().rawQuery("select vpe.impreso, 'Partida: ' || vpp.num_partida || ' ' || 'Producto: ' || icp.nom_producto || ' ' || 'Cantidad: ' || vpp.cantidad || ' ' || " +
                " 'Precio: ' || vpp.precio_unitario || ' ' || 'Suma: ' || vpp.suma || ' ' || 'Porc Desc: ' || vpp.porcentaje_descuento || ' ' || 'Descuento: ' || vpp.descuento || ' ' || 'Total: ' || vpp.total as desgloce, 'SUMA: ' || vpe.suma as suma, 'DESCUENTO: ' || vpe.descuento as descuento, 'TOTAL: ' || vpe.total as total " +
                "from vn_pedidos_encabezado vpe inner join vn_pedidos_partidas vpp on vpe.num_pedido = vpp.num_pedido " +
                "inner join in_cat_productos icp on vpp.cve_cat_producto = icp.cve_cat_producto " +
                "where vpe.num_pedido = "+ num_pedido +"", null);
        cursor.moveToFirst();
        ArrayList<String> clientes_pedido_desgloce = new ArrayList<String>();
        String impreso = "";
        String palabra_impreso = "";
        String suma = "";
        String descuento = "";
        String total = "";
        while(!cursor.isAfterLast()) {
            impreso = cursor.getString(cursor.getColumnIndex("impreso"));
            suma = cursor.getString(cursor.getColumnIndex("suma"));
            descuento = cursor.getString(cursor.getColumnIndex("descuento"));
            total = cursor.getString(cursor.getColumnIndex("total"));
            if(impreso.toString().equals("1")) {
                palabra_impreso = "Sincronizado: SI";
            }
            else{
                palabra_impreso = "Sincronizado: NO";
            }
            clientes_pedido_desgloce.add(palabra_impreso);
            clientes_pedido_desgloce.add(cursor.getString(cursor.getColumnIndex("desgloce")));

            cursor.moveToNext();
        }
        clientes_pedido_desgloce.add(suma);
        clientes_pedido_desgloce.add(descuento);
        clientes_pedido_desgloce.add(total);
        cursor.close();
        return clientes_pedido_desgloce.toArray(new String[clientes_pedido_desgloce.size()]);
    }

    //Metodo para sacar el listado de los pagos capturados con la app
    public String[] getListadoPagosApp()
    {
        Cursor cursor = getReadableDatabase().rawQuery("select 'Pago: ' || vde.num_documento || '-' || vcc.nombre as nombre from vn_documentos_encabezado vde " +
                "inner join vn_cat_clientes vcc on vde.cve_cliente = vcc.cve_usuario " +
                "order by vde.num_documento;", null);
        cursor.moveToFirst();
        ArrayList<String> clientes_pagos = new ArrayList<String>();
        while(!cursor.isAfterLast()) {
            clientes_pagos.add(cursor.getString(cursor.getColumnIndex("nombre")));
            cursor.moveToNext();
        }
        cursor.close();
        return clientes_pagos.toArray(new String[clientes_pagos.size()]);
    }

    //Metodo para sacar el desgloce del pedido seleccionado
    public String[] getListadoPagosDesgloceApp(String num)
    {
        String num_documento = num;
        Cursor cursor = getReadableDatabase().rawQuery("select vde.auditoria, 'Monto: ' || vde.total || ' Comentarios: ' || vde.comentarios || " +
                "' Tipo de pago: ' || vctp.nombre_tipo_pago || ' Fecha de Pago: ' || vdp.fecha_banco || " +
                "' Referencia: ' || vdp.referencia || ' Banco Emisor: ' || vdp.cve_banco_emisor || ' Banco Receptor: ' || vdp.cve_banco as desgloce " +
                "from vn_documentos_encabezado vde inner join vn_documentos_partidas vdp on vde.cve_documento = vdp.cve_documento " +
                "and vde.num_documento = vdp.num_documento " +
                "inner join vn_cat_tipos_pago vctp on vdp.cve_tipo_pago = vctp.cve_tipo_pago " +
                "where vde.cve_documento = 'PAG' and vde.num_documento = "+ num_documento +"", null);
        cursor.moveToFirst();
        ArrayList<String> pagos_desgloce = new ArrayList<String>();
        String impreso = "";
        String palabra_impreso = "";
        while(!cursor.isAfterLast()) {
            impreso = cursor.getString(cursor.getColumnIndex("auditoria"));

            if(impreso.toString().equals("SYNC")) {
                palabra_impreso = "Sincronizado: NO";
            }
            else{
                palabra_impreso = "Sincronizado: SI";
            }
            pagos_desgloce.add(palabra_impreso);
            pagos_desgloce.add(cursor.getString(cursor.getColumnIndex("desgloce")));

            cursor.moveToNext();
        }

        cursor.close();
        return pagos_desgloce.toArray(new String[pagos_desgloce.size()]);
    }
    //Metodo para sacar el listado de los prospectos capturadas con la app
    public String[] getListadoProspectosApp()
    {
        Cursor cursor = getReadableDatabase().rawQuery("select nombre_cliente as nombre from vn_clientes_seguimiento "+
                "order by _id;", null);
        cursor.moveToFirst();
        ArrayList<String> clientes_prospectos = new ArrayList<String>();
        while(!cursor.isAfterLast()) {
            clientes_prospectos.add(cursor.getString(cursor.getColumnIndex("nombre")));
            cursor.moveToNext();
        }
        cursor.close();
        return clientes_prospectos.toArray(new String[clientes_prospectos.size()]);
    }

    //Metodo para sacar el listado de las visitas capturadas con la app
    public String[] getListadoVisitasApp()
    {
        Cursor cursor = getReadableDatabase().rawQuery("select 'Visita: ' || vrv._id || '-' || vcc.nombre as nombre from vn_registro_visita vrv " +
                "inner join vn_cat_clientes vcc on vrv.cve_cliente = vcc.cve_usuario " +
                "order by vcc.nombre;", null);
        cursor.moveToFirst();
        ArrayList<String> clientes_visitas = new ArrayList<String>();
        while(!cursor.isAfterLast()) {
            clientes_visitas.add(cursor.getString(cursor.getColumnIndex("nombre")));
            cursor.moveToNext();
        }
        cursor.close();
        return clientes_visitas.toArray(new String[clientes_visitas.size()]);
    }

    //Metodo para sacar el desgloce de la visita seleccionada
    public String[] getListadoVisitasDesgloceApp(String num)
    {
        String visita = num;
        Cursor cursor = getReadableDatabase().rawQuery("select sincronizado, 'Fecha: ' || fecha_registro || ' Comentarios: ' " +
                "|| comentarios as desgloce from vn_registro_visita where _id = "+ visita +"", null);
        cursor.moveToFirst();
        ArrayList<String> visita_desgloce = new ArrayList<String>();
        String impreso = "";
        String palabra_impreso = "";
        while(!cursor.isAfterLast()) {
            impreso = cursor.getString(cursor.getColumnIndex("sincronizado"));

            if(impreso.toString().equals("0")) {
                palabra_impreso = "Sincronizado: NO";
            }
            else{
                palabra_impreso = "Sincronizado: SI";
            }
            visita_desgloce.add(palabra_impreso);
            visita_desgloce.add(cursor.getString(cursor.getColumnIndex("desgloce")));

            cursor.moveToNext();
        }

        cursor.close();
        return visita_desgloce.toArray(new String[visita_desgloce.size()]);
    }

    //Metodo para sacar el listado de los agentes
    public String[] getListadoAgentes()
    {
        Cursor cursor = getReadableDatabase().rawQuery("select vcr.num_ruta || '-' || vca.nombre as nombre from vn_cat_agentes vca " +
                "inner join vn_cat_rutas vcr on vca.cve_usuario = vcr.cve_agente " +
                "order by vca.nombre", null);
        cursor.moveToFirst();
        ArrayList<String> agentes = new ArrayList<String>();
        while(!cursor.isAfterLast()) {
            agentes.add(cursor.getString(cursor.getColumnIndex("nombre")));
            cursor.moveToNext();
        }
        cursor.close();
        return agentes.toArray(new String[agentes.size()]);
    }

    //Metodo para sacar el listado de los pagos (vn_seguimiento_de_pagos)
    public String[] getListadoSeguimientoDePagos()
    {
        Cursor cursor = getReadableDatabase().rawQuery("select 'PAGO: ' || vsp.num_documento || ' Cliente: ' || vcc.nombre as informacion " +
                "from vn_seguimiento_de_pagos vsp inner join vn_cat_clientes vcc on vsp.cve_cliente = vcc.cve_usuario " +
                "inner join vn_cat_tipos_pago vctp on vsp.cve_tipo_pago = vctp.cve_tipo_pago " +
                "GROUP BY vsp.num_documento " +
                "ORDER BY vsp.num_documento", null);
        cursor.moveToFirst();
        ArrayList<String> informacion = new ArrayList<String>();
        while(!cursor.isAfterLast()) {
            informacion.add(cursor.getString(cursor.getColumnIndex("informacion")));
            cursor.moveToNext();
        }
        cursor.close();
        return informacion.toArray(new String[informacion.size()]);
    }

    //Metodo para sacar el desgloce de los pagos
    public String[] getListadoSeguimientoDePagosDesgloce(String num)
    {
        String num_pago = num;
        String conciliado = "";
        Cursor cursor = getReadableDatabase().rawQuery("select 'PAGO: ' || vsp.num_documento as pago, ' Cliente: ' || vcc.nombre as cliente, " +
                "'Fecha documento: ' || vsp.fecha_documento as fecha_documento, 'Monto: ' || vsp.monto as monto, 'Tipo: ' || vctp.nombre_tipo_pago as nombre_tipo_pago, " +
                "'Bancos (E/R)' || vsp.bancoer as bancos, 'Referencia: ' || vsp.referencia as referencia, 'Fecha de banco: ' || vsp.fecha_banco as fecha_banco, " +
                "'Usuario Captura: ' || vsp.capturo as usuario_captura, vsp.fecha_conciliacion from vn_seguimiento_de_pagos vsp inner join vn_cat_clientes vcc on vsp.cve_cliente = vcc.cve_usuario " +
                "inner join vn_cat_tipos_pago vctp on vsp.cve_tipo_pago = vctp.cve_tipo_pago " +
                "WHERE vsp.num_documento = "+ num_pago +" " +
                "GROUP BY vsp.num_documento " +
                "ORDER BY vsp.num_documento", null);
        cursor.moveToFirst();
        ArrayList<String> desgloce = new ArrayList<String>();
        String fecha_dto = "";
        String Fecha="";
        while(!cursor.isAfterLast()) {
            desgloce.add(cursor.getString(cursor.getColumnIndex("pago")));
            desgloce.add(cursor.getString(cursor.getColumnIndex("cliente")));

            //desgloce.add(cursor.getString(cursor.getColumnIndex("fecha_documento")));
            String f=cursor.getString(cursor.getColumnIndex("fecha_documento"));
            String[]f2=f.split(":");
            String txtFecha=f2[0];
            Fecha=f2[1];
            try {
                DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
                DateFormat outputFormat = new SimpleDateFormat("dd-MMMMMMMMMMMMM-yyyy");
                Date date = inputFormat.parse(Fecha);
                String outputDateStr = outputFormat.format(date);
                fecha_dto=outputDateStr;
                String Fecha_documento=txtFecha+": "+fecha_dto;
                desgloce.add(Fecha_documento);
            }catch(ParseException e){
                e.printStackTrace();
            }

            desgloce.add(cursor.getString(cursor.getColumnIndex("monto")));
            desgloce.add(cursor.getString(cursor.getColumnIndex("nombre_tipo_pago")));
            desgloce.add(cursor.getString(cursor.getColumnIndex("bancos")));
            desgloce.add(cursor.getString(cursor.getColumnIndex("referencia")));
            desgloce.add(cursor.getString(cursor.getColumnIndex("fecha_banco")));
            desgloce.add(cursor.getString(cursor.getColumnIndex("usuario_captura")));

            conciliado = cursor.getString(cursor.getColumnIndex("fecha_conciliacion"));

            if(conciliado.toString().equals("0000-00-00 00:00:00")){
                desgloce.add("Conciliado: NO");
            }
            else{
                desgloce.add("Conciliado: SI");
            }

            cursor.moveToNext();
        }
        cursor.close();
        return desgloce.toArray(new String[desgloce.size()]);
    }

    //Metodo para guardar el alta del nuevo cliente (encabezado) en a tabla local
    public void insertaNuevoCliente(String sqlInsert){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(sqlInsert);
        Log.d("insertaNuevoCliente():", sqlInsert);
        db.close();
    }

    //Metodo para guardar el pedido (encabezado) en a tabla local
    public void insertaEncabezado(String sqlInsert){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(sqlInsert);
        Log.d("insertaEncabezado():", sqlInsert);
        db.close();
    }

    //Metodo para guardar el pedido (encabezado) en a tabla local
    public void insertaPartidas(String sqlInsert){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(sqlInsert);
        Log.d("insertaPartidas():", sqlInsert);
        db.close();
    }

    // Metodo para generar la lista de los pedidos a enviar al servidor. de vn_pedidos_encabezado / partidas a vn_cotizador_pedidos / partidas
    public String transmitePedidos()
    {
        String JSonString = "[";
        String  query = "select vpe.num_pedido, vpe.cve_cliente, vpe.cve_agente, vpe.fecha_pedido as fecha_registro, " +
                "vpe.comentarios, vpe.suma, vpe.descuento, vpe.total, vpe.estatus, vpe.tipo_pedido as tipo_venta, " +
                "vpe.familia, (date() || \" \" || time()) as fecha_sincronizacion, vpp.num_partida, vpp.cve_cat_producto, vpp.cantidad, " +
                "vpp.precio_unitario, vpp.porcentaje_descuento, vpp.suma, vpp.descuento, vpp.total, " +
                "vpp.cve_centro_costo, vpe.cve_usuario_captura, vpp.cve_conducto, vpe.latitude, vpe.longitude " +
                "from vn_pedidos_encabezado vpe " +
                "inner join vn_pedidos_partidas vpp on " +
                "vpe.cve_compania = vpp.cve_compania and vpe.num_pedido = vpp.num_pedido " +
                "where vpe.cve_compania = '019' and vpe.impreso = 0";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        try
        {
            while (cursor.moveToNext())
            {
                JSonString += "{" + "\"" + "num_pedido" + "\":\"" + cursor.getString(0) + "\"," + // Inicia la lista del encabezado
                        "\"" + "cve_cliente" + "\":\"" + cursor.getString(1) + "\"," +
                        "\"" + "cve_agente" + "\":\"" + cursor.getString(2) + "\"," +
                        "\"" + "fecha_registro" + "\":\"" + cursor.getString(3) + "\"," +
                        "\"" + "comentarios" + "\":\"" + cursor.getString(4) + "\"," +
                        "\"" + "sumaE" + "\":\"" + cursor.getString(5) + "\"," +
                        "\"" + "descuentoE" + "\":\"" + cursor.getString(6) + "\"," +
                        "\"" + "totalE" + "\":\"" + cursor.getString(7) + "\"," +
                        "\"" + "estatus" + "\":\"" + cursor.getString(8) + "\"," +
                        "\"" + "tipo_venta" + "\":\"" + cursor.getString(9) + "\"," +
                        "\"" + "familia" + "\":\"" + cursor.getString(10) + "\"," +
                        "\"" + "fecha_sincronizacion" + "\":\"" + cursor.getString(11) + "\"," + // Termina la lista del encabezado
                        "\"" + "num_partida" + "\":\"" + cursor.getString(12) + "\"," +
                        "\"" + "cve_cat_producto" + "\":\"" + cursor.getString(13) + "\"," +
                        "\"" + "cantidad" + "\":\"" + cursor.getString(14) + "\"," +
                        "\"" + "precio_unitario" + "\":\"" + cursor.getString(15) + "\"," +
                        "\"" + "porcentaje_descuento" + "\":\"" + cursor.getString(16) + "\"," +
                        "\"" + "suma" + "\":\"" + cursor.getString(17) + "\"," +
                        "\"" + "descuento" + "\":\"" + cursor.getString(18) + "\"," +
                        "\"" + "total" + "\":\"" + cursor.getString(19) + "\"," +
                        "\"" + "almacen" + "\":\"" + cursor.getString(20) + "\"," +
                        "\"" + "cve_usuario_captura" + "\":\"" + cursor.getString(21) + "\"," +
                        "\"" + "cve_conducto" + "\":\"" + cursor.getString(22) + "\"," +
                        "\"" + "latitude" + "\":\"" + cursor.getString(23) + "\"," +
                        "\"" + "longitude" + "\":\"" + cursor.getString(24) + "\"},\n";
            }
        }
        finally
        {
            if (JSonString.length() > 1)
            {
                JSonString  = JSonString.substring(0, JSonString.length()-2);
            }
            JSonString += "]";
            cursor.close();
            //Log.d("Analisis de generacion de codigo total ======>", JSonString);
        }
        return JSonString;
    } // Fin de transmite pedidos

    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    /**********************************************************************************************
     * Metodos auxuliares para el llenado de los ListView de Pagos                                *
     **********************************************************************************************/
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    // ++++ Metodo para obtener en un array los metodos de pago de la tabla vn_cat_tipos_pago
    public String[] getTiposPago()
    {
        Cursor cursor = getReadableDatabase().rawQuery("select '' || cve_tipo_pago || '- ' || nombre_tipo_pago as tipo_pago from vn_cat_tipos_pago order by cve_tipo_pago", null);
        cursor.moveToFirst();
        ArrayList<String> tiposPago = new ArrayList<String>();
        while(!cursor.isAfterLast()) {
            tiposPago.add(cursor.getString(cursor.getColumnIndex("tipo_pago")));
            cursor.moveToNext();
        }
        cursor.close();
        return tiposPago.toArray(new String[tiposPago.size()]);
    }

    // ++++ Metodo para obtener en un array el catalogo de Bancos de los clientes  de la tabla vn_cat_bancos clientes
    public String[] getBancosClientes()
    {
        Cursor cursor = getReadableDatabase().rawQuery("select '' || cve_banco_emisor || '- ' || nombre_banco as banco from vn_cat_bancos_clientes order by nombre_banco", null);
        cursor.moveToFirst();
        ArrayList<String> bancosClientes = new ArrayList<String>();
        while(!cursor.isAfterLast()) {
            bancosClientes.add(cursor.getString(cursor.getColumnIndex("banco")));
            cursor.moveToNext();
        }
        cursor.close();
        return bancosClientes.toArray(new String[bancosClientes.size()]);
    }

    // ++++ Metodo para obtener en un array el catalogo de Bancos de deposito a Cuentas Biochem  de la tabla ts_cat_bancos
    public String[] getBancoDeposito()
    {
        Cursor cursor = getReadableDatabase().rawQuery("select '' || cve_banco || '- ' || nombre_corto as banco from ts_cat_bancos where mostrar_ventas = 1  order by nombre_corto", null);
        cursor.moveToFirst();
        ArrayList<String> bancoDeposito = new ArrayList<String>();
        while(!cursor.isAfterLast()) {
            bancoDeposito.add(cursor.getString(cursor.getColumnIndex("banco")));
            cursor.moveToNext();
        }
        cursor.close();
        return bancoDeposito.toArray(new String[bancoDeposito.size()]);
    }

    // ++++ Metodo para obtener en un array el catalogo de las personas que pueden depositar en banco de la tabla vn_documentos_depositos
    public String[] getDocumentosDeposito()
    {
        Cursor cursor = getReadableDatabase().rawQuery("select '' || persona_deposito || '- ' || descripcion as depositante from vn_documentos_depositos order by persona_deposito", null);
        cursor.moveToFirst();
        ArrayList<String> documentosDepositos = new ArrayList<String>();
        while(!cursor.isAfterLast()) {
            documentosDepositos.add(cursor.getString(cursor.getColumnIndex("depositante")));
            cursor.moveToNext();
        }
        cursor.close();
        return documentosDepositos.toArray(new String[documentosDepositos.size()]);
    }

    // ++++ Metodo para obtener en un array el catalogo de los documentos que pueden amparar un deposito en banco de la tabla vn_documentos_respaldos
    public String[] getDocumentosRespaldos()
    {
        Cursor cursor = getReadableDatabase().rawQuery("select '' || docto_respaldo || '- ' || descripcion as respaldo from vn_documentos_respaldos order by docto_respaldo", null);
        cursor.moveToFirst();
        ArrayList<String> documentosRespaldos = new ArrayList<String>();
        while(!cursor.isAfterLast()) {
            documentosRespaldos.add(cursor.getString(cursor.getColumnIndex("respaldo")));
            cursor.moveToNext();
        }
        cursor.close();
        return documentosRespaldos.toArray(new String[documentosRespaldos.size()]);
    }

    // ++++ Metodo para obtener en un array el catalogo "virtual" de tipos de cobranza (No existe tabla se toma dinamicamente los valores)
    public String[] getTipoCobranza()
    {
        ArrayList<String> tipoCobranza = new ArrayList<String>();
        tipoCobranza.add("P- En persona"); // Agrega un elemento al array
        tipoCobranza.add("T- Telefonica"); // Agrega un elemento al array
        return tipoCobranza.toArray(new String[tipoCobranza.size()]);
    }

    // ++++ Metodo para obtener el folio siguiente como numero de pago registrado en la tabla de vn_documentos_encabezado
    public long getSiguientePago()
    {
        Cursor cursor = getReadableDatabase().rawQuery("select case when max(num_documento) is null then 1 else max(num_documento) + 1 end as proximo_pago from vn_documentos_encabezado  where  cve_compania = '019' and cve_documento = 'PAG'", null);
        cursor.moveToFirst();
        long proximoPago = 0;
        while (!cursor.isAfterLast()) {
            proximoPago  = Long.parseLong(cursor.getString(cursor.getColumnIndex("proximo_pago")));
            cursor.moveToNext();
        }
        cursor.close();

        return proximoPago;
    }

    // ++++ Inserta el Pago capturado en la BD local
    public boolean registraPago(ContentValues documentosEncabezado, ContentValues documentosPartidas)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_VN_DOCUMENTOS_ENCABEZADO, null, documentosEncabezado);
        db.insert(TABLE_VN_DOCUMENTOS_PARTIDAS, null, documentosPartidas);
        db.close();

        return true;
    }
    public boolean registraProspecto(ContentValues cvVnCliSeg)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_VN_CLIENTES_SEGUIMIENTO, null, cvVnCliSeg);
        db.close();

        return true;
    }

    // ++++ Metodo para generar la cadena JSON para transmisión de pagos ++++
    public String transmitePagos()
    {
        String JSonString = "[";

        String query = "select vde.cve_compania, vde.cve_documento, vde.num_documento, vde.fecha_documento, vde.fecha_registro, " +
                "vde.tipo_documento, vde.suma, vde.descuento, vde.subtotal, vde.total, " +
                "vde.cve_cliente, vde.cve_agente, vde.cve_usuario, vde.cve_moneda, vde.tipo_cambio, " +
                "vde.comentarios, vde.estatus, vde.total_pagado, vde.recibo_pago, vde.conciliado, " +
                "vde.fecha_conciliacion, vde.referencia_conciliacion, vde.existe_aclaracion, vde.persona_deposito, vde.docto_respaldo, " +
                "vde.cve_usuario_conciliacion, vde.auditoria, vde.comentarios_auditoria, vde.comentarios_otros, vde.cve_usuario_desconciliacion, " +
                "vde.comentarios_desconciliacion, vde.ieps_3, vde.ieps_3_5, vde.total_ieps, vde.ieps_6, vde.ieps_7, " +
                "vde.latitude, vde.longitude, vde.tipo_cobranza, " +
                "vdp.num_partida, vdp.cve_tipo_pago, vdp.cve_banco_emisor, vdp.cuenta_cheque_cliente, vdp.num_cheque, " +
                "vdp.cve_banco, vdp.referencia, vdp.fecha_banco " +
                "from vn_documentos_encabezado vde " +
                "inner join vn_documentos_partidas vdp on " +
                "vde.cve_compania = vdp.cve_compania and " +
                "vde.cve_documento = vdp.cve_documento and " +
                "vde.num_documento = vdp.num_documento " +
                "where vde.cve_compania = '019' and vde.cve_documento = 'PAG' and auditoria = 'SYNC'";


        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        try
        {
            while (cursor.moveToNext())
            {
                JSonString += "{" + "\"" + "cve_compania" + "\":\"" + cursor.getString(0) + "\"," + // Inicia la lista del encabezado
                        "\"" + "cve_documento" + "\":\"" + cursor.getString(1) + "\"," +
                        "\"" + "num_documento" + "\":\"" + cursor.getString(2) + "\"," +
                        "\"" + "fecha_documento" + "\":\"" + cursor.getString(3) + "\"," +
                        "\"" + "fecha_registro" + "\":\"" + cursor.getString(4) + "\"," +
                        "\"" + "tipo_documento" + "\":\"" + cursor.getString(5) + "\"," +
                        "\"" + "suma" + "\":\"" + cursor.getString(6) + "\"," +
                        "\"" + "descuento" + "\":\"" + cursor.getString(7) + "\"," +
                        "\"" + "subtotal" + "\":\"" + cursor.getString(8) + "\"," +
                        "\"" + "total" + "\":\"" + cursor.getString(9) + "\"," +
                        "\"" + "cve_cliente" + "\":\"" + cursor.getString(10) + "\"," +
                        "\"" + "cve_agente" + "\":\"" + cursor.getString(11) + "\"," +
                        "\"" + "cve_usuario" + "\":\"" + cursor.getString(12) + "\"," +
                        "\"" + "cve_moneda" + "\":\"" + cursor.getString(13) + "\"," +
                        "\"" + "tipo_cambio" + "\":\"" + cursor.getString(14) + "\"," +
                        "\"" + "comentarios" + "\":\"" + cursor.getString(15) + "\"," +
                        "\"" + "estatus" + "\":\"" + cursor.getString(16) + "\"," +
                        "\"" + "total_pagado" + "\":\"" + cursor.getString(17) + "\"," +
                        "\"" + "recibo_pago" + "\":\"" + cursor.getString(18) + "\"," +
                        "\"" + "conciliado" + "\":\"" + cursor.getString(19) + "\"," +
                        "\"" + "fecha_conciliacion" + "\":\"" + cursor.getString(20) + "\"," +
                        "\"" + "referencia_conciliacion" + "\":\"" + cursor.getString(21) + "\"," +
                        "\"" + "existe_aclaracion" + "\":\"" + cursor.getString(22) + "\"," +
                        "\"" + "persona_deposito" + "\":\"" + cursor.getString(23) + "\"," +
                        "\"" + "docto_respaldo" + "\":\"" + cursor.getString(24) + "\"," +
                        "\"" + "cve_usuario_conciliacion" + "\":\"" + cursor.getString(25) + "\"," +
                        "\"" + "auditoria" + "\":\"" + cursor.getString(26) + "\"," +
                        "\"" + "comentarios_auditoria" + "\":\"" + cursor.getString(27) + "\"," +
                        "\"" + "comentarios_otros" + "\":\"" + cursor.getString(28) + "\"," +
                        "\"" + "cve_usuario_desconciliacion" + "\":\"" + cursor.getString(29) + "\"," +
                        "\"" + "comentarios_desconciliacion" + "\":\"" + cursor.getString(30) + "\"," +
                        "\"" + "ieps_3" + "\":\"" + cursor.getString(31) + "\"," +
                        "\"" + "ieps_3_5" + "\":\"" + cursor.getString(32) + "\"," +
                        "\"" + "total_ieps" + "\":\"" + cursor.getString(33) + "\"," +
                        "\"" + "ieps_6" + "\":\"" + cursor.getString(34) + "\"," +
                        "\"" + "ieps_7" + "\":\"" + cursor.getString(35) + "\"," +
                        "\"" + "latitude" + "\":\"" + cursor.getString(36) + "\"," +
                        "\"" + "longitude" + "\":\"" + cursor.getString(37) + "\"," +
                        "\"" + "tipo_cobranza" + "\":\"" + cursor.getString(38) + "\"," + // Aqui terina el encabezado del pago
                        "\"" + "num_partida" + "\":\"" + cursor.getString(39) + "\"," + // Aqui inicia la partida del pago
                        "\"" + "cve_tipo_pago" + "\":\"" + cursor.getString(40) + "\"," +
                        "\"" + "cve_banco_emisor" + "\":\"" + cursor.getString(41) + "\"," +
                        "\"" + "cuenta_cheque_cliente" + "\":\"" + cursor.getString(42) + "\"," +
                        "\"" + "num_cheque" + "\":\"" + cursor.getString(43) + "\"," +
                        "\"" + "cve_banco" + "\":\"" + cursor.getString(44) + "\"," +
                        "\"" + "referencia" + "\":\"" + cursor.getString(45) + "\"," +
                        "\"" + "fecha_banco" + "\":\"" + cursor.getString(46) + "\"},\n"; // Aqui termina la partida del pago
            }
        }
        finally
        {
            if (JSonString.length() > 1)
            {
                JSonString  = JSonString.substring(0, JSonString.length()-2);
            }
            JSonString += "]";
            cursor.close();
            /*
            Log.d("=====> MODO DEBUG <===== [1.1]", String.valueOf(JSonString.length()));
            String xmlS = JSonString;

            if(xmlS.length() > 4000) {
                for(int i=0;i<xmlS.length();i+=4000){
                    if(i+4000<xmlS.length())
                        Log.d("rescounter"+i,xmlS.substring(i, i+4000));
                    else
                        Log.d("rescounter"+i,xmlS.substring(i, xmlS.length()));
                }
            } else
                Log.d("resinfo",xmlS);

            Log.d("=====> MODO DEBUG <===== [1.2]", JSonString);
            */
        }
        return JSonString;

    }

    // ++++ Le quita a marca a los pagos ya sincronizados haciendo update al campo audotoria VARCHAR(10)
    public void resetPagos()
    {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("update  " + TABLE_VN_DOCUMENTOS_ENCABEZADO + " set auditoria = 'TRN' where auditoria = 'SYNC'");
        db.close();
    }

    //Metodo para guardar la visita en la tabla local
    public void insertaVisita(String sqlInsert){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(sqlInsert);
        db.close();
    }//Fin Para guardar la visita en la tabla local

    // Metodo para generar la lista de las visitas a enviar al servidor. de vn_registro_visita
    public String transmiteVisitas()
    {
        String JSonString = "[";
        String  query = "select cve_cliente, comentarios, fecha_registro, cve_usuario, cve_compania, latitude, longitude, tipo_problema, ultima_visita from vn_registro_visita where sincronizado = '0'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        try
        {
            while (cursor.moveToNext())
            {
                JSonString += "{" + "\"" + "cve_cliente" + "\":\"" + cursor.getString(0) + "\"," + // Inicia la lista del encabezado
                        "\"" + "comentarios" + "\":\"" + cursor.getString(1) + "\"," +
                        "\"" + "fecha_registro" + "\":\"" + cursor.getString(2) + "\"," +
                        "\"" + "cve_usuario" + "\":\"" + cursor.getString(3) + "\"," +
                        "\"" + "cve_compania" + "\":\"" + cursor.getString(4) + "\"," +
                        "\"" + "latitude" + "\":\"" + cursor.getString(5) + "\"," +
                        "\"" + "longitude" + "\":\"" + cursor.getString(6) + "\","+
                        "\"" + "tipo_problema" + "\":\"" + cursor.getString(7) + "\","+
                        "\"" + "ultima_visita" + "\":\"" + cursor.getString(8) + "\"},\n";
            }
        }
        finally
        {
            if (JSonString.length() > 1)
            {
                JSonString  = JSonString.substring(0, JSonString.length()-2);
            }
            JSonString += "]";
            cursor.close();
            //Log.d("Analisis de generacion de codigo total ======>", JSonString);
        }
        return JSonString;
    } // Fin de transmite visitas

    // Metodo para generar la lista de las prospectos a enviar al servidor. de vn_clientes_seguimiento
    public String transmiteProspectos()
    {

        String JSonString = "[";
        String  query = "select cve_compania, fecha_registro, cve_usuario_captura, " +
                "nombre_cliente, rfc, domicilio, colonia, cp, telefono, email, tipo_contribuyente," +
                "entidad, localidad, latitude, longitude from vn_clientes_seguimiento where sincronizado = '0'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        try
        {
            while (cursor.moveToNext())
            {
                JSonString += "{" + "\"" + "cve_compania" + "\":\"" + cursor.getString(0) + "\"," + // Inicia la lista del encabezado
                        "\"" + "fecha_registro" + "\":\"" + cursor.getString(1) + "\"," +
                        "\"" + "cve_usuario_captura" + "\":\"" + cursor.getString(2) + "\","+
                        "\"" + "nombre_cliente" + "\":\"" + cursor.getString(3) + "\","+
                        "\"" + "rfc" + "\":\"" + cursor.getString(4) + "\","+
                        "\"" + "domicilio" + "\":\"" + cursor.getString(5) + "\","+
                        "\"" + "colonia" + "\":\"" + cursor.getString(6) + "\","+
                        "\"" + "cp" + "\":\"" + cursor.getString(7) + "\","+
                        "\"" + "telefono" + "\":\"" + cursor.getString(8) + "\","+
                        "\"" + "email" + "\":\"" + cursor.getString(9) + "\","+
                        "\"" + "tipo_contribuyente" + "\":\"" + cursor.getString(10) + "\","+
                        "\"" + "entidad" + "\":\"" + cursor.getString(11) + "\","+
                        "\"" + "localidad" + "\":\"" + cursor.getString(12) + "\","+
                        "\"" + "latitude" + "\":\"" + cursor.getString(13) + "\","+
                        "\"" + "longitude" + "\":\"" + cursor.getString(14) + "\"},\n";
            }
        }
        finally
        {
            if (JSonString.length() > 1)
            {
                JSonString  = JSonString.substring(0, JSonString.length()-2);
            }
            JSonString += "]";
            cursor.close();
            //Log.d("Analisis de generacion de codigo total ======>", JSonString);
        }
        return JSonString;
    } // Fin de transmite prospectos

    // Metodo para validar el usuario y contrasena en el acceso
    public boolean checkAccesos() {

        Boolean info = false;

        Cursor cursor = getReadableDatabase().rawQuery("select cve_usuario from gl_accesos order by cve_usuario limit 1", null);
        cursor.moveToFirst();
        String usuarioCheck = new String();
        while (!cursor.isAfterLast()) {
            usuarioCheck = cursor.getString(cursor.getColumnIndex("cve_usuario"));
            cursor.moveToNext();
        }
        cursor.close();

        if(usuarioCheck.toString().length() >= 1){
            info = true;
        }

        return info;

    }

    // Metodo para validar si el usuario (agente ya tiene la informacion descargada)
    public boolean checkInformacionAgente() {

        Boolean info = false;

        String tipo_usuario = "";
        String dato_cliente = "";


        Cursor cursor = getReadableDatabase().rawQuery("select cve_usuario from vn_cat_clientes where cve_usuario != '' limit 1", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            dato_cliente = cursor.getString(cursor.getColumnIndex("cve_usuario"));
            cursor.moveToNext();
        }
        cursor.close();

        if(dato_cliente.length() == 0){
            info = true;
        }

        return info;

    }

    // Metodo para ver si se quedo algo pendiente de enviar en la parte de pagos, pedidos y visitas
    public boolean checkPendienteEnvio() {

        Boolean info = false;

        String pedido = "";
        String pago = "";
        String visita = "";
        String prospecto="";

        //Se verifica primero en la parte de pedidos
        Cursor cursor = getReadableDatabase().rawQuery("select num_pedido from vn_pedidos_encabezado where impreso = '0' limit 1", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            pedido = cursor.getString(cursor.getColumnIndex("num_pedido"));
            cursor.moveToNext();
        }
        cursor.close();

        //Se verifica en la parte de pagos
        Cursor cursor2 = getReadableDatabase().rawQuery("select num_documento from vn_documentos_encabezado where auditoria = 'SYNC' limit 1", null);
        cursor2.moveToFirst();
        while (!cursor2.isAfterLast()) {
            pago = cursor2.getString(cursor2.getColumnIndex("num_documento"));
            cursor2.moveToNext();
        }
        cursor2.close();

        //Se verifica en la parte de visitas
        Cursor cursor3 = getReadableDatabase().rawQuery("select cve_cliente from vn_registro_visita where sincronizado = '0' limit 1", null);
        cursor3.moveToFirst();
        while (!cursor3.isAfterLast()) {
            visita = cursor3.getString(cursor3.getColumnIndex("cve_cliente"));
            cursor3.moveToNext();
        }
        cursor3.close();

        //Se verifica en la parte de prospectos
        Cursor cursor4 = getReadableDatabase().rawQuery("select cve_usuario_captura from vn_clientes_seguimiento where sincronizado = '0' limit 1", null);
        cursor4.moveToFirst();
        while (!cursor4.isAfterLast()) {
            prospecto = cursor4.getString(cursor4.getColumnIndex("cve_usuario_captura"));
            cursor4.moveToNext();
        }
        cursor4.close();




        ///Se realiza la validacion si alguno de los tres tiene informacion la variable se va a verdadero
        if(pedido.length() > 0){
            info = true;
        }

        if(pago.length() > 0){
            info = true;
        }

        if(visita.length() > 0){
            info = true;
        }

        if(prospecto.length() > 0){
            info = true;
        }



        return info;

    }

    // Metodo para obtener el inventario de mano de los agentes
    public String[] getInventarioMano()
    {
        Cursor cursor = getReadableDatabase().rawQuery("SELECT 'PRODUCTO: ' || icp.nom_producto || ' EXISTENCIA: ' || ici.existencias AS informacion FROM in_cat_productos icp " +
                "INNER JOIN in_centros_inventarios ici ON icp.cve_compania = ici.cve_compania AND icp.cve_cat_producto = ici.cve_cat_producto " +
                "ORDER BY icp.nom_producto", null);
        cursor.moveToFirst();
        ArrayList<String> inventario = new ArrayList<String>();
        while(!cursor.isAfterLast()) {
            inventario.add(cursor.getString(cursor.getColumnIndex("informacion")));
            cursor.moveToNext();
        }
        cursor.close();
        return inventario.toArray(new String[inventario.size()]);
    }

    // Metodo para obtener la fecha de la ruta
    public String getFechaRuta(Integer dia)
    {
        String fecha_ruta = "";
        String fecha_="";
        Date fecha=null;
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        Cursor cursor = getReadableDatabase().rawQuery("SELECT fecha_ruta FROM vn_programa_rutas_semanales " +
                "WHERE numero_dia = '"+ dia +"'", null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast()) {
            try {
                DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
                DateFormat outputFormat = new SimpleDateFormat("dd-MMM-yyyy");


                fecha_ruta = cursor.getString(cursor.getColumnIndex("fecha_ruta"));

                Date date = inputFormat.parse(fecha_ruta);
                String outputDateStr = outputFormat.format(date);
                //fecha=format.parse(fecha_ruta);
                fecha_=outputDateStr;
            }
            catch(ParseException e){
                e.printStackTrace();
            }

            cursor.moveToNext();
        }
        cursor.close();

        return fecha_;
    }

    // Metodo para la ruta del dia 1 del agente
    public String[] getRutaSemanal(Integer dia)
    {
        int partida = 1;
        String cambio_fecha = "";
        String fecha = "";
        Cursor cursor = getReadableDatabase().rawQuery("SELECT vprs.fecha_ruta, vcc.nombre, vprs.poblacion, vprs.estado, vprs.vta_vet, " +
                "vprs.fecha_vta_vet, vprs.vta_agr, vprs.fecha_vta_agr,  " +
                "vprs.saldo, vprs.saldo_viejo, vprs.dias_saldo_viejo, vprs.cantidad_adulto, " +
                "vprs.cantidad_puppy " +
                "FROM vn_cat_clientes vcc " +
                "INNER JOIN vn_programa_rutas_semanales vprs " +
                "ON vcc.cve_usuario = vprs.cve_cliente " +
                "WHERE vprs.numero_dia = '"+ dia +"' " +
                "ORDER BY vprs.fecha_ruta, vprs.numero_dia, vprs.orden, vcc.nombre", null);
        cursor.moveToFirst();
        ArrayList<String> informacion = new ArrayList<String>();
        while(!cursor.isAfterLast()) {
            /*
            fecha = cursor.getString(cursor.getColumnIndex("fecha_ruta"));
            if(!cambio_fecha.equals(fecha)){

                informacion.add("Fecha: "+fecha);

                cambio_fecha = fecha.toString();
            }
            */

            informacion.add(partida+"- Cliente: "+cursor.getString(cursor.getColumnIndex("nombre"))+"  Poblacion: "+cursor.getString(cursor.getColumnIndex("poblacion"))+"  Estado: "+cursor.getString(cursor.getColumnIndex("estado")));
            informacion.add("   Ult Vta Vet: "+cursor.getString(cursor.getColumnIndex("vta_vet"))+"  Fecha: "+cursor.getString(cursor.getColumnIndex("fecha_vta_vet"))+" /  Ult Vta Agr: "+cursor.getString(cursor.getColumnIndex("vta_agr"))+"  Fecha: "+cursor.getString(cursor.getColumnIndex("fecha_vta_agr")));
            informacion.add("   Saldo: "+cursor.getString(cursor.getColumnIndex("saldo"))+" /  Saldo Atrazado: "+cursor.getString(cursor.getColumnIndex("saldo_viejo"))+"  Dias: "+cursor.getString(cursor.getColumnIndex("dias_saldo_viejo")));
            informacion.add("   Ultima Compra Pzas (Adulto): "+cursor.getString(cursor.getColumnIndex("cantidad_adulto"))+" /  Ultima Compra Pzas (Puppy): "+cursor.getString(cursor.getColumnIndex("cantidad_puppy")));
            informacion.add("");
            partida++;

            cursor.moveToNext();
        }
        cursor.close();
        return informacion.toArray(new String[informacion.size()]);
    }
    public String getRutaImagenProductos(String cve_producto){
        Cursor cursor = getReadableDatabase().rawQuery("SELECT imagen FROM in_cat_productos where cve_producto='"+cve_producto+"'", null);

        cursor.moveToFirst();
        String ruta_imagen="";
        while (!cursor.isAfterLast()){
            ruta_imagen=cursor.getString(cursor.getColumnIndex("imagen"));
            cursor.moveToNext();
        }
        cursor.close();
        return ruta_imagen;
    }
    public String getImagenProductos(String ruta_img){
        String rutaServer="http://www.sybrem.com.mx/adsnet/inventarios/imagenes_productos/"+ruta_img;
        return rutaServer;
    }

    // Metodo que es usado para obtener los datos de los clientes de la tabla vn_cat_clientes los coloca en un array y los regresa en la funci—n.
    public String[] getProveedores()
    {
        Cursor cursor = getReadableDatabase().rawQuery("SELECT nombre_proveedor FROM ct_reserva GROUP BY nombre_proveedor", null);
        cursor.moveToFirst();
        ArrayList<String> nombreProveedor = new ArrayList<String>();
        while(!cursor.isAfterLast()) {
            nombreProveedor.add(cursor.getString(cursor.getColumnIndex("nombre_proveedor")));
            cursor.moveToNext();
        }
        cursor.close();
        return nombreProveedor.toArray(new String[nombreProveedor.size()]);
    }

    public String rfcProveedor(String proveedor){
        Cursor cursor = getReadableDatabase().rawQuery("SELECT rfc FROM ct_reserva WHERE nombre_proveedor = '"+proveedor+"' LIMIT 1", null);
        cursor.moveToFirst();
        String rfc = "";
        while(!cursor.isAfterLast()) {
            rfc = cursor.getString(cursor.getColumnIndex("rfc"));
            cursor.moveToNext();
        }
        cursor.close();

        return rfc;
    }

    public String nombreProveedor(String rfc){
        Cursor cursor = getReadableDatabase().rawQuery("SELECT nombre_proveedor FROM ct_reserva WHERE rfc = '"+rfc+"' LIMIT 1", null);
        cursor.moveToFirst();
        String proveedor = "";
        while(!cursor.isAfterLast()) {
            proveedor = cursor.getString(cursor.getColumnIndex("nombre_proveedor"));
            cursor.moveToNext();
        }
        cursor.close();

        return proveedor;
    }

    public Integer idReserva(String uuid){
        Cursor cursor = getReadableDatabase().rawQuery("SELECT id_reserva FROM ct_reserva WHERE uuid = '"+uuid+"'", null);
        cursor.moveToFirst();
        Integer id_reserva = 0;
        while(!cursor.isAfterLast()) {
            id_reserva = cursor.getInt(cursor.getColumnIndex("id_reserva"));
            cursor.moveToNext();
        }
        cursor.close();

        return id_reserva;
    }

    public Double totalComprobado(Integer partida, String oc) {
        Cursor cursor = getReadableDatabase().rawQuery("SELECT total_comprobado FROM cm_ordenes_compra WHERE num_orden = '"+oc+"' AND " +
                "num_partida_oc = '"+partida+"'", null);
        cursor.moveToFirst();
        Double total_comprobado = 0.0;
        while(!cursor.isAfterLast()) {
            total_comprobado = cursor.getDouble(cursor.getColumnIndex("total_comprobado"));
            cursor.moveToNext();
        }
        cursor.close();

        return total_comprobado;
    }

    public Double precioUnitarioOC(Integer partida, String oc) {
        Cursor cursor = getReadableDatabase().rawQuery("SELECT precio_unitario FROM cm_ordenes_compra WHERE num_orden = '"+oc+"' AND " +
                "num_partida_oc = '"+partida+"'", null);
        cursor.moveToFirst();
        Double precio_unitario = 0.0;
        while(!cursor.isAfterLast()) {
            precio_unitario = cursor.getDouble(cursor.getColumnIndex("precio_unitario"));
            cursor.moveToNext();
        }
        cursor.close();

        return precio_unitario;
    }

    public String buscaFactura(String proveedor, String rfc, String monto, String fecha, String hora){

        String fecha_armada = "";

        if(hora.toString().equals("")){
            fecha_armada = fecha;
        }
        else{
            fecha_armada = fecha+" "+hora;
        }

        //Checamos en la parte del monto si tiene un punto decimal
        Integer punto = monto.indexOf(".");

        if(punto == -1){
            monto = monto+".00";
        }

        Cursor cursor = getReadableDatabase().rawQuery("SELECT COUNT(*) AS cantidad, uuid FROM ct_reserva WHERE nombre_proveedor = '"+proveedor+"' AND rfc='"+rfc+"' " +
                "AND fecha_exp LIKE '%"+fecha_armada+"%' AND total = '"+monto+"'", null);
        cursor.moveToFirst();
        String uuid = "";
        String cantidad = "";
        while(!cursor.isAfterLast()) {
            uuid = cursor.getString(cursor.getColumnIndex("uuid"));
            cantidad = cursor.getString(cursor.getColumnIndex("cantidad"));
            cursor.moveToNext();
        }
        cursor.close();

        int cantidadInt = Integer.parseInt(cantidad);

        if(cantidadInt > 1) {
            return "1";
        }
        if(cantidadInt == 0) {
            return "0";
        }

        //verificamos si la comprobacion no esta guardada local
        Cursor cursor2 = getReadableDatabase().rawQuery("SELECT uuid FROM ct_comprobacion WHERE uuid = '"+uuid+"'", null);
        cursor2.moveToFirst();
        String uuid2 = "";
        while(!cursor2.isAfterLast()) {
            uuid2 = cursor2.getString(cursor2.getColumnIndex("uuid"));
            cursor2.moveToNext();
        }
        cursor2.close();

        if(uuid2.length() != 0){
            return "2";
        }

        return uuid;
    }

    //Metodo para guardar la comprobacion en la tabla local
    public void insertaComprobacion(String sqlInsert){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(sqlInsert);
        db.close();
    }//Fin Para guardar la visita en la tabla local

    //Metodo para actualizar la tabla de ordenes de compra
    public void actualizaComprobacion(String sqlUpdate){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(sqlUpdate);
        db.close();
    }//Fin Para guardar la visita en la tabla local

    // Metodo para generar la lista de las visitas a enviar al servidor. de vn_registro_visita
    public String transmiteComprobaciones()
    {
        String JSonString = "[";
        String  query = "SELECT oc, partida, id_reserva, usuario, fecha_registro FROM ct_comprobacion WHERE sincronizado = '0' ORDER BY oc, partida;";

        //Log.d("--->", query);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        try
        {
            while (cursor.moveToNext())
            {
                JSonString += "{" + "\"" + "oc" + "\":\"" + cursor.getString(0) + "\"," + // Inicia la lista del encabezado
                        "\"" + "partida" + "\":\"" + cursor.getString(1) + "\"," +
                        "\"" + "id_reserva" + "\":\"" + cursor.getString(2) + "\"," +
                        "\"" + "usuario" + "\":\"" + cursor.getString(3) + "\"," +
                        "\"" + "fecha_registro" + "\":\"" + cursor.getString(4) + "\"},\n";
            }
        }
        finally
        {
            if (JSonString.length() > 1)
            {
                JSonString  = JSonString.substring(0, JSonString.length()-2);
            }
            JSonString += "]";
            cursor.close();
            //Log.d("Analisis de generacion de codigo total ======>", JSonString);
        }
        return JSonString;
    } // Fin de transmite visitas

    //obtiene la información básica de los productos
    public String[] getInfoBasica(String cve_product){
        String cve_producto = "";
        cve_producto = cve_product;


        Cursor cursor = getReadableDatabase().rawQuery("select inf_tec_basica from in_cat_productos where cve_compania = '019' and es_venta = '1' and estatus = 'A' and cve_producto < '7000' and cve_producto = '"+ cve_producto +"' order by nom_producto;", null);
        cursor.moveToFirst();
        ArrayList<String> productosDesgloce = new ArrayList<String>();
        while(!cursor.isAfterLast()) {
            productosDesgloce.add(cursor.getString(cursor.getColumnIndex("inf_tec_basica")));
            cursor.moveToNext();
        }
        cursor.close();
        return productosDesgloce.toArray(new String[productosDesgloce.size()]);
    }

    //obtiene la información comercial de los productos
    public String[] getInfoComercial(String cve_product){
        String cve_producto = "";
        cve_producto = cve_product;


        Cursor cursor = getReadableDatabase().rawQuery("select inf_comercial from in_cat_productos where cve_compania = '019' and es_venta = '1' and estatus = 'A' and cve_producto < '7000' and cve_producto = '"+ cve_producto +"' order by nom_producto;", null);
        cursor.moveToFirst();
        ArrayList<String> productosDesgloce = new ArrayList<String>();
        while(!cursor.isAfterLast()) {
            productosDesgloce.add(cursor.getString(cursor.getColumnIndex("inf_comercial")));
            cursor.moveToNext();
        }
        cursor.close();
        return productosDesgloce.toArray(new String[productosDesgloce.size()]);
    }
    //obtiene la información de introduccion de los productos cuando se presiona el boton de "Apoyo Comercial"
    public String[] getIntroProductos(String cve_product){
        String cve_producto = "";
        cve_producto = cve_product;


        Cursor cursor = getReadableDatabase().rawQuery("select introduccion from in_cat_productos where cve_compania = '019' and es_venta = '1' and estatus = 'A' and cve_producto < '7000' and cve_producto = '"+ cve_producto +"' order by nom_producto;", null);
        cursor.moveToFirst();
        ArrayList<String> productosDesgloce = new ArrayList<String>();
        while(!cursor.isAfterLast()) {
            productosDesgloce.add(cursor.getString(cursor.getColumnIndex("introduccion")));
            cursor.moveToNext();
        }
        cursor.close();
        return productosDesgloce.toArray(new String[productosDesgloce.size()]);
    }

    //obtiene la información de las ventajas de los productos cuando se presiona el boton de "Apoyo Comercial"
    public String[] getVentajasProductos(String cve_product){
        String cve_producto = "";
        cve_producto = cve_product;


        Cursor cursor = getReadableDatabase().rawQuery("select ventajas from in_cat_productos where cve_compania = '019' and es_venta = '1' and estatus = 'A' and cve_producto < '7000' and cve_producto = '"+ cve_producto +"' order by nom_producto;", null);
        cursor.moveToFirst();
        ArrayList<String> productosDesgloce = new ArrayList<String>();
        while(!cursor.isAfterLast()) {
            productosDesgloce.add(cursor.getString(cursor.getColumnIndex("ventajas")));
            cursor.moveToNext();
        }
        cursor.close();
        return productosDesgloce.toArray(new String[productosDesgloce.size()]);
    }

    //obtiene la información de las indicaciones de los productos cuando se presiona el boton de "Apoyo Comercial"
    public String[] getIndicacionesProductos(String cve_product){
        String cve_producto = "";
        cve_producto = cve_product;


        Cursor cursor = getReadableDatabase().rawQuery("select indicaciones from in_cat_productos where cve_compania = '019' and es_venta = '1' and estatus = 'A' and cve_producto < '7000' and cve_producto = '"+ cve_producto +"' order by nom_producto;", null);
        cursor.moveToFirst();
        ArrayList<String> productosDesgloce = new ArrayList<String>();
        while(!cursor.isAfterLast()) {
            productosDesgloce.add(cursor.getString(cursor.getColumnIndex("indicaciones")));
            cursor.moveToNext();
        }
        cursor.close();
        return productosDesgloce.toArray(new String[productosDesgloce.size()]);
    }

    //obtiene la información completa de los productos
    public String[] getInfoCompleta(String cve_product){
        String cve_producto = "";
        cve_producto = cve_product;

        Cursor cursor = getReadableDatabase().rawQuery("select inf_tec_completa from in_cat_productos where cve_compania = '019' and es_venta = '1' and estatus = 'A' and cve_producto < '7000' and cve_producto = '"+ cve_producto +"' order by nom_producto;", null);
        cursor.moveToFirst();
        ArrayList<String> productosDesgloce = new ArrayList<String>();
        while(!cursor.isAfterLast()) {
            productosDesgloce.add(cursor.getString(cursor.getColumnIndex("inf_tec_completa")));
            cursor.moveToNext();
        }
        cursor.close();
        return productosDesgloce.toArray(new String[productosDesgloce.size()]);
    }

    // Metodo que es usado para obtener las OC que se tienen pendiente
    public String[] getOC()
    {
        Cursor cursor = getReadableDatabase().rawQuery("select '# Orden: ' || num_orden || ' Fecha: ' || fecha_registro || ' Monto: ' || sum(precio_unitario-total_comprobado) AS orden from cm_ordenes_compra where precio_unitario <> total_comprobado group by num_orden order by fecha_registro", null);
        cursor.moveToFirst();
        ArrayList<String> ordenes = new ArrayList<String>();
        while(!cursor.isAfterLast()) {
            ordenes.add(cursor.getString(cursor.getColumnIndex("orden")));
            cursor.moveToNext();
        }
        cursor.close();
        return ordenes.toArray(new String[ordenes.size()]);
    }

    // Metodo para sacar las partidas de la OC seleccionada
    public String getPartidasOC(String oc, Integer partida)
    {
        Cursor cursor = getReadableDatabase().rawQuery("select num_partida_oc || ' ' || nom_producto || ' Monto: ' || (precio_unitario - total_comprobado) AS info from cm_ordenes_compra where num_orden = '"+oc+"' " +
                "and num_partida_oc = '"+partida+"';", null);
        cursor.moveToFirst();
        String informacion = "";
        while(!cursor.isAfterLast()) {
            informacion = cursor.getString(cursor.getColumnIndex("info"));
            cursor.moveToNext();
        }
        cursor.close();
        return informacion;
    }




    public String[] getTipoPoliticas(){

        Cursor cursor = getReadableDatabase().rawQuery("select id_politica || '-' || titulo as tipo_politica from vn_politicas group by titulo;", null);
        cursor.moveToFirst();
        ArrayList<String> politicas = new ArrayList<String>();
        while(!cursor.isAfterLast()) {
            politicas.add(cursor.getString(cursor.getColumnIndex("tipo_politica")));
            cursor.moveToNext();
        }
        cursor.close();
        return politicas.toArray(new String[politicas.size()]);
    }

    public String[] getImagenesPoliticas(String tipo_politica){
        Cursor cursor = getReadableDatabase().rawQuery("SELECT imagen FROM vn_politicas WHERE id_politica='"+tipo_politica+"'", null);
        cursor.moveToFirst();
        ArrayList<String> imgpoliticas = new ArrayList<String>();
        while(!cursor.isAfterLast()) {
            String cadenaImagen=cursor.getString(cursor.getColumnIndex("imagen"));

            if(cadenaImagen==null || cadenaImagen.isEmpty()){
                imgpoliticas.add("0");
                cursor.moveToNext();
            }else{
                imgpoliticas.add(cursor.getString(cursor.getColumnIndex("imagen")));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return imgpoliticas.toArray(new String[imgpoliticas.size()]);
    }

} // Fin de la definici—n clase: MyDBHandler