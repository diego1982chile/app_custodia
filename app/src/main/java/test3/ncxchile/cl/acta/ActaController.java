package test3.ncxchile.cl.acta;

import android.content.Context;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.util.Xml;
import android.widget.Toast;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlSerializer;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

import test3.ncxchile.cl.db.Global;
import test3.ncxchile.cl.greenDAO.*;
import test3.ncxchile.cl.login.R;
import test3.ncxchile.cl.models.DatosPDF;
import test3.ncxchile.cl.session.SessionManager;
import test3.ncxchile.cl.validators.RutValidator;

/**
 * Created by android-developer on 28-10-2014.
 */
public class ActaController {

    private Context localContext;

    public ActaController(Context context) {

        localContext=context;

        // Inicializar TareaDao        
    }

    public void insertarActa(final Acta acta) {
        Global.daoSession.getActaDao().insert(acta);
    }


    public JSONObject parseJson(Acta acta){

        JSONObject actaJson=new JSONObject();

        JSONObject vehiculoDataJson=new JSONObject();
        JSONArray especiasJson=new JSONArray();
        JSONArray fichaEstadoVisualJson=new JSONArray();
        JSONObject vehiculoJson=new JSONObject();
        JSONObject propietarioJson=new JSONObject();
        JSONObject conductorJson=new JSONObject();
        JSONObject personaJson=new JSONObject();
        JSONArray telefonosJson=new JSONArray();
        JSONArray correosJson=new JSONArray();
        JSONObject autoridadJson=new JSONObject();
        JSONObject direccionJson=new JSONObject();
        JSONObject estadoVisualJson= new JSONObject();
        JSONObject fichaEstadoVisualItemJson= new JSONObject();
        JSONObject tribunalJson= new JSONObject();

        try {
            JSONObject actaTemplateJson= new JSONObject(acta.getActaJson());

            // Los datos que no son suceptibles de modificaciones en la tablet, son preservados, usando la plantilla del actaJson

            // Setear datos del acta
            actaJson.putOpt("fechaFirma",acta.getFechaFirma().getTime());
            actaJson.putOpt("oficioRemisor",acta.getOficioRemisor());
            actaJson.putOpt("parte",acta.getParte());
            actaJson.putOpt("idOt",actaTemplateJson.optLong("idOt"));
            actaJson.putOpt("observacionImgenes",acta.getObservacionImgenes());
            actaJson.putOpt("montoFactura",actaTemplateJson.optLong("montoFactura"));
            actaJson.putOpt("gruaExterna",actaTemplateJson.optLong("gruaExterna"));

            //Setear vehiculoData
            if(acta.getVehiculoData()!=null){
                // Setear especies
                if(acta.getVehiculoData().getEspeciasList()!=null){
                    for(int i=0;i<acta.getVehiculoData().getEspeciasList().size();++i)
                        especiasJson.put(i,acta.getVehiculoData().getEspeciasList().get(i).getNombre());
                }

                vehiculoDataJson.putOpt("especias",especiasJson);

                if(acta.getVehiculoData().getVehiculo()!=null) {
                    // Setear vehiculo
                    vehiculoJson.putOpt("tamano", acta.getVehiculoData().getVehiculo().getTamano());
                    vehiculoJson.putOpt("modelo", acta.getVehiculoData().getVehiculo().getModelo());
                    vehiculoJson.putOpt("numeroMotor", acta.getVehiculoData().getVehiculo().getNumeroMotor());
                    vehiculoJson.putOpt("matricula", acta.getVehiculoData().getVehiculo().getMatricula());
                    vehiculoJson.putOpt("kilometraje", acta.getVehiculoData().getVehiculo().getKilometraje());
                    vehiculoJson.putOpt("marca", acta.getVehiculoData().getVehiculo().getMarca());
                    vehiculoJson.putOpt("anio", acta.getVehiculoData().getVehiculo().getAnio());
                    vehiculoJson.putOpt("origenVehiculo", acta.getVehiculoData().getVehiculo().getOrigenVehiculo());
                    vehiculoJson.putOpt("color", acta.getVehiculoData().getVehiculo().getColor());

                    // Setear ficha visual
                    if (acta.getVehiculoData().getVehiculo().getFichaEstadoVisualList() != null) {
                        for (int i = 0; i < acta.getVehiculoData().getVehiculo().getFichaEstadoVisualList().size(); ++i){
                            fichaEstadoVisualItemJson= new JSONObject();
                            estadoVisualJson= new JSONObject();

                            FichaEstadoVisual fichaEstadoVisual=acta.getVehiculoData().getVehiculo().getFichaEstadoVisualList().get(i);

                            EstadoVisual estadoVisual= Global.daoSession.getEstadoVisualDao().getById(fichaEstadoVisual.getIdEstadoVisual());
                            fichaEstadoVisualItemJson.put("idFichaEstadoVisual",JSONObject.NULL);
                            fichaEstadoVisualItemJson.put("idFicha",JSONObject.NULL);
                            estadoVisualJson.putOpt("idEstadoVisual",estadoVisual.getId());
                            estadoVisualJson.putOpt("nombre",estadoVisual.getNombre());
                            estadoVisualJson.putOpt("respuestaBinaria",estadoVisual.getRespuestaBinaria());
                            fichaEstadoVisualItemJson.putOpt("valor",fichaEstadoVisual.getValor());
                            if(fichaEstadoVisual.getObservacion()!=null)
                                fichaEstadoVisualItemJson.putOpt("observacion",fichaEstadoVisual.getObservacion());
                            else
                                fichaEstadoVisualItemJson.putOpt("observacion",JSONObject.NULL);
                            fichaEstadoVisualItemJson.putOpt("estadoVisual",estadoVisualJson);
                            fichaEstadoVisualJson.put(i,fichaEstadoVisualItemJson);
                        }
                    }

                    JSONObject vehiculoDataTemplateJson = actaTemplateJson.optJSONObject("vehiculoData");

                    // Los datos que no son suceptibles de modificaciones en la tablet, son preservados, usando la plantilla del actaJson
                    if (vehiculoDataTemplateJson != null) {
                        // Setear parqueadero
                        JSONObject vehiculoTemplateJson = vehiculoDataTemplateJson.optJSONObject("vehiculo");
                        if (vehiculoTemplateJson != null) {
                            JSONObject parqueaderoTemplateJson = vehiculoDataJson.optJSONObject("parqueadero");
                            vehiculoJson.putOpt("parqueadero", parqueaderoTemplateJson);
                            vehiculoJson.putOpt("carpetaVehiculo", vehiculoTemplateJson.optLong("carpetaVehiculo"));
                            vehiculoJson.putOpt("modificado", vehiculoTemplateJson.optBoolean("modificado"));
                            vehiculoJson.putOpt("id", vehiculoTemplateJson.optLong("id"));
                            vehiculoJson.putOpt("clonado", vehiculoTemplateJson.optLong("clonado"));
                            vehiculoJson.putOpt("vin", vehiculoTemplateJson.optLong("vin"));
                            vehiculoJson.putOpt("caracteristicas", vehiculoTemplateJson.optString("caracteristicas"));
                            vehiculoJson.putOpt("puedeRodar", vehiculoTemplateJson.optBoolean("puedeRodar"));
                        }
                    }

                    vehiculoJson.putOpt("fichaEstadoVisual", fichaEstadoVisualJson);
                    vehiculoDataJson.putOpt("vehiculo", vehiculoJson);

                    // Setear propietario
                    if (acta.getVehiculoData().getClientePropietario().size() > 0) {
                        telefonosJson= new JSONArray();
                        correosJson= new JSONArray();

                        propietarioJson.putOpt("licencia", acta.getVehiculoData().getClientePropietario().get(0).getLicencia());
                        propietarioJson.putOpt("rut", acta.getVehiculoData().getClientePropietario().get(0).getPersona().getRut());
                        propietarioJson.putOpt("nombre", acta.getVehiculoData().getClientePropietario().get(0).getPersona().getNombre());
                        propietarioJson.putOpt("nombreCompleto", acta.getVehiculoData().getClientePropietario().get(0).getPersona().getNombre()+" "+
                                                              acta.getVehiculoData().getClientePropietario().get(0).getPersona().getApellidoPaterno()+" "+
                                                              acta.getVehiculoData().getClientePropietario().get(0).getPersona().getApellidoMaterno());
                        propietarioJson.putOpt("apellidoPaterno", acta.getVehiculoData().getClientePropietario().get(0).getPersona().getApellidoPaterno());
                        propietarioJson.putOpt("apellidoMaterno", acta.getVehiculoData().getClientePropietario().get(0).getPersona().getApellidoMaterno());
                        for (int i = 0; i < acta.getVehiculoData().getClientePropietario().get(0).getPersona().getTelefonos().size(); ++i)
                            telefonosJson.put(i, acta.getVehiculoData().getClientePropietario().get(0).getPersona().getTelefonos().get(i).getEmail());
                        propietarioJson.putOpt("telefonos", telefonosJson);
                        for (int i = 0; i < acta.getVehiculoData().getClientePropietario().get(0).getPersona().getCorreos().size(); ++i)
                            correosJson.put(i, acta.getVehiculoData().getClientePropietario().get(0).getPersona().getCorreos().get(i).getEmail());
                        propietarioJson.putOpt("usuario",acta.getVehiculoData().getClientePropietario().get(0).getPersona().getUsuario());
                        propietarioJson.putOpt("correos", correosJson);
                        direccionJson=new JSONObject();
                        if(acta.getVehiculoData().getClientePropietario().get(0).getPersona().getDireccion()!=null){
                            direccionJson.putOpt("calle",acta.getVehiculoData().getClientePropietario().get(0).getPersona().getDireccion().getCalle());
                            direccionJson.putOpt("numeracion",acta.getVehiculoData().getClientePropietario().get(0).getPersona().getDireccion().getNumeracion());
                            direccionJson.putOpt("interseccion",acta.getVehiculoData().getClientePropietario().get(0).getPersona().getDireccion().getInterseccion());
                            direccionJson.putOpt("referencias",acta.getVehiculoData().getClientePropietario().get(0).getPersona().getDireccion().getReferencias());
                            direccionJson.putOpt("comuna",acta.getVehiculoData().getClientePropietario().get(0).getPersona().getDireccion().getComuna());
                            direccionJson.putOpt("comunaVO",JSONObject.NULL);
                        }
                    }
                    vehiculoDataJson.putOpt("propietario", propietarioJson);

                    // Setear conductor
                    if (acta.getVehiculoData().getClientePropietario().size() > 1) {
                        telefonosJson= new JSONArray();
                        correosJson= new JSONArray();

                        conductorJson.putOpt("licencia", acta.getVehiculoData().getClientePropietario().get(1).getLicencia());
                        conductorJson.putOpt("rut", acta.getVehiculoData().getClientePropietario().get(1).getPersona().getRut());
                        conductorJson.putOpt("nombre", acta.getVehiculoData().getClientePropietario().get(1).getPersona().getNombre());
                        propietarioJson.putOpt("nombreCompleto", acta.getVehiculoData().getClientePropietario().get(1).getPersona().getNombre()+" "+
                                acta.getVehiculoData().getClientePropietario().get(1).getPersona().getApellidoPaterno()+" "+
                                acta.getVehiculoData().getClientePropietario().get(1).getPersona().getApellidoMaterno());
                        conductorJson.putOpt("apellidoPaterno", acta.getVehiculoData().getClientePropietario().get(1).getPersona().getApellidoPaterno());
                        conductorJson.putOpt("apellidoMaterno", acta.getVehiculoData().getClientePropietario().get(1).getPersona().getApellidoMaterno());
                        for (int i = 0; i < acta.getVehiculoData().getClientePropietario().get(1).getPersona().getTelefonos().size(); ++i)
                            telefonosJson.put(i, acta.getVehiculoData().getClientePropietario().get(1).getPersona().getTelefonos().get(i).getEmail());
                        conductorJson.putOpt("telefonos", telefonosJson);
                        for (int i = 0; i < acta.getVehiculoData().getClientePropietario().get(1).getPersona().getCorreos().size(); ++i)
                            correosJson.put(i, acta.getVehiculoData().getClientePropietario().get(1).getPersona().getCorreos().get(i).getEmail());
                        propietarioJson.putOpt("usuario",acta.getVehiculoData().getClientePropietario().get(1).getPersona().getUsuario());
                        conductorJson.putOpt("correos", correosJson);
                        direccionJson=new JSONObject();
                        if(acta.getVehiculoData().getClientePropietario().get(1).getPersona().getDireccion()!=null){
                            direccionJson.putOpt("calle",acta.getVehiculoData().getClientePropietario().get(1).getPersona().getDireccion().getCalle());
                            direccionJson.putOpt("numeracion",acta.getVehiculoData().getClientePropietario().get(1).getPersona().getDireccion().getNumeracion());
                            direccionJson.putOpt("interseccion",acta.getVehiculoData().getClientePropietario().get(1).getPersona().getDireccion().getInterseccion());
                            direccionJson.putOpt("referencias",acta.getVehiculoData().getClientePropietario().get(1).getPersona().getDireccion().getReferencias());
                            direccionJson.putOpt("comuna",acta.getVehiculoData().getClientePropietario().get(1).getPersona().getDireccion().getComuna());
                            direccionJson.putOpt("comunaVO",JSONObject.NULL);
                        }
                    }
                    vehiculoDataJson.putOpt("conductor", conductorJson);
                }
                if (acta.getVehiculoData().getClientePropietario().size() < 1)
                    vehiculoDataJson.putOpt("propietario", JSONObject.NULL);
                if (acta.getVehiculoData().getClientePropietario().size() < 2)
                    vehiculoDataJson.putOpt("conductor", JSONObject.NULL);

                // Setear parqueadero
                JSONObject parqueaderoTemplateJson= vehiculoDataJson.optJSONObject("parqueadero");
                vehiculoDataJson.putOpt("parqueadero",parqueaderoTemplateJson);

                actaJson.putOpt("vehiculoData",vehiculoDataJson);

                actaJson.putOpt("fechaParte",acta.getFechaParte().getTime());
                actaJson.putOpt("servicio",actaTemplateJson.optLong("servicio"));
                actaJson.putOpt("cargaInicial",actaTemplateJson.optLong("cargaInicial"));
                actaJson.putOpt("existImage",acta.getExistImage());
                actaJson.putOpt("idSolicitud",actaTemplateJson.optLong("idSolicitud"));
                actaJson.putOpt("fechaCreacion",acta.getFechaCreacion().getTime());
                actaJson.putOpt("causaRetiro",acta.getCausaRetiro());
                actaJson.putOpt("numeroPatente",acta.getNumeroPatente());

                // Datos de autoridad
                if(acta.getAutoridad()!=null){
                    telefonosJson= new JSONArray();
                    correosJson= new JSONArray();

                    autoridadJson.putOpt("cargo",acta.getAutoridad().getCargo());
                    for(int i=0;i<acta.getAutoridad().getPersona().getCorreos().size();++i)
                        correosJson.put(i, acta.getAutoridad().getPersona().getCorreos().get(i).getEmail());
                    autoridadJson.putOpt("correos",correosJson);
                    autoridadJson.putOpt("numeroFuncionario",acta.getAutoridad().getNumeroFuncionario());
                    autoridadJson.putOpt("apellidoPaterno",acta.getAutoridad().getPersona().getApellidoPaterno());
                    autoridadJson.putOpt("nombre",acta.getAutoridad().getPersona().getNombre());

                    if(acta.getAutoridad().getPersona().getDireccion()!=null){
                        direccionJson.putOpt("calle",acta.getAutoridad().getPersona().getDireccion().getCalle());
                        direccionJson.putOpt("numeracion",acta.getAutoridad().getPersona().getDireccion().getNumeracion());
                        direccionJson.putOpt("interseccion",acta.getAutoridad().getPersona().getDireccion().getInterseccion());
                        direccionJson.putOpt("referencias",acta.getAutoridad().getPersona().getDireccion().getReferencias());
                        direccionJson.putOpt("comuna",acta.getAutoridad().getPersona().getDireccion().getComuna());
                        direccionJson.putOpt("comunaVO",JSONObject.NULL);
                        autoridadJson.putOpt("direccion",direccionJson);
                    }
                    else{
                        autoridadJson.putOpt("direccion",JSONObject.NULL);
                    }

                    for(int i=0;i<acta.getAutoridad().getPersona().getTelefonos().size();++i)
                        telefonosJson.put(i,acta.getAutoridad().getPersona().getTelefonos().get(i).getEmail());
                    autoridadJson.putOpt("telefonos",telefonosJson);
                    autoridadJson.putOpt("institucion",acta.getAutoridad().getInstitucion());
                    autoridadJson.putOpt("usuario",acta.getAutoridad().getPersona().getUsuario());
                    autoridadJson.putOpt("rut",acta.getPersona().getRut());
                    autoridadJson.putOpt("unidadPolicial",acta.getAutoridad().getUnidadPolicial());
                    autoridadJson.putOpt("apellidoMaterno",acta.getAutoridad().getPersona().getApellidoMaterno());
                }

                actaJson.putOpt("autoridad",autoridadJson);

                actaJson.putOpt("numeroFactura",actaTemplateJson.optLong("numeroFactura"));

                actaJson.putOpt("nue",acta.getNue());
                actaJson.putOpt("existVideo",acta.getExistVideo());

                personaJson=new JSONObject();

                if(acta.getPersona()!=null){
                    telefonosJson= new JSONArray();
                    correosJson= new JSONArray();

                    correosJson= new JSONArray();
                    for(int i=0;i<acta.getPersona().getCorreos().size();++i)
                        correosJson.put(i, acta.getPersona().getCorreos().get(i).getEmail());
                    personaJson.putOpt("correos",correosJson);
                    personaJson.putOpt("apellidoPaterno",acta.getPersona().getApellidoPaterno());
                    personaJson.putOpt("apellidoMaterno",acta.getPersona().getApellidoMaterno());
                    personaJson.putOpt("rut",acta.getPersona().getRut());
                    personaJson.putOpt("usuario",acta.getPersona().getUsuario());
                    telefonosJson= new JSONArray();
                    for(int i=0;i<acta.getPersona().getTelefonos().size();++i)
                        telefonosJson.put(i, acta.getPersona().getTelefonos().get(i).getEmail());
                    personaJson.putOpt("telefonos",telefonosJson);
                    direccionJson=new JSONObject();

                    if(acta.getPersona().getDireccion()!=null){
                        direccionJson.putOpt("calle",acta.getPersona().getDireccion().getCalle());
                        direccionJson.putOpt("numeracion", acta.getPersona().getDireccion().getNumeracion());
                        direccionJson.putOpt("interseccion", acta.getPersona().getDireccion().getInterseccion());
                        direccionJson.putOpt("referencias",acta.getPersona().getDireccion().getReferencias());
                        direccionJson.putOpt("comuna",acta.getPersona().getDireccion().getComuna());
                        direccionJson.putOpt("comunaVO",JSONObject.NULL);
                        personaJson.putOpt("direccion",direccionJson);
                    }
                    else{
                        autoridadJson.putOpt("direccion",JSONObject.NULL);
                    }

                    personaJson.putOpt("nombre",acta.getPersona().getNombre());
                }

                actaJson.putOpt("gruero",personaJson);

                // Setear datos del retiro

                direccionJson=new JSONObject();
                if(acta.getDireccion()!=null){
                    direccionJson.putOpt("calle",acta.getDireccion().getCalle());
                    direccionJson.putOpt("numeracion",acta.getDireccion().getNumeracion());
                    direccionJson.putOpt("interseccion",acta.getDireccion().getInterseccion());
                    direccionJson.putOpt("referencias",acta.getDireccion().getReferencias());
                    direccionJson.putOpt("comuna",acta.getDireccion().getComuna());
                    direccionJson.putOpt("comunaVO",JSONObject.NULL);
                    actaJson.putOpt("direccion",direccionJson);
                }
                else{
                    autoridadJson.putOpt("direccion",JSONObject.NULL);
                }

                actaJson.putOpt("fiscalia",acta.getFiscalia());
                actaJson.putOpt("ruc",acta.getRuc());
                actaJson.putOpt("unidadPolicial",acta.getUnidadPolicial());
                actaJson.putOpt("idGrua",actaTemplateJson.optLong("idGrua"));
                actaJson.putOpt("actaIncautacion",acta.getActaIncautacion());
                actaJson.putOpt("id",actaTemplateJson.optLong("id"));
                actaJson.putOpt("nombreExterno",actaTemplateJson.optLong("nombreExterno"));
                actaJson.putOpt("observacion",actaTemplateJson.optLong("observacion"));

                Tribunal tribunal=Global.daoSession.getTribunalDao().getById(acta.getTribunalID());

                if(tribunal!=null){
                    tribunalJson.putOpt("id",tribunal.getId());
                    tribunalJson.putOpt("nombre",tribunal.getNombre());
                    tribunalJson.putOpt("rut",null);
                    tribunalJson.putOpt("tipoInstitucion",null);
                    tribunalJson.putOpt("representante",null);
                    tribunalJson.putOpt("direccion",null);
                    tribunalJson.putOpt("telefonos",Arrays.asList());
                    tribunalJson.putOpt("correos",Arrays.asList());
                }
                actaJson.putOpt("tribunal", tribunalJson);
            }

            ArrayList<String> actaKeys = new ArrayList<String>();
            ArrayList<String> actaTemplateKeys = new ArrayList<String>();
            Iterator it1= actaTemplateJson.keys();
            Iterator it2= actaJson.keys();

            String key;

            while(it1.hasNext()){
                key=String.valueOf(it1.next());
                actaTemplateKeys.add(key);
            }

            while(it2.hasNext()){
                key=String.valueOf(it2.next());
                actaKeys.add(key);
            }

            for(int i=0;i<actaTemplateKeys.size();++i){
                if(!actaKeys.contains(actaTemplateKeys.get(i)))
                    actaJson.putOpt(actaTemplateKeys.get(i),actaTemplateJson.getString(actaTemplateKeys.get(i)));
            }

            System.out.println("actaJson=" + actaJson.toString(1));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return actaJson;
    }

    public void crearActaFromJson(final JSONObject actaJson, final Tarea tareaActual) {

        Global.daoSession.runInTx(new Runnable() {
            @Override
            public void run() {

                Acta acta= new Acta();

                try {
                    // Guardar el json del acta
                    acta.setActaJson(actaJson.toString());

                    // Datos del acta
                    acta.setOficioRemisor(actaJson.optString("oficioRemisor"));
                    acta.setParte(actaJson.optString("parte"));
                    acta.setIdOt(actaJson.optInt("idOt"));
                    acta.setMontoFactura(actaJson.optInt("montoFactura"));
                    acta.setGruaExterna(actaJson.optBoolean("gruaExterna"));
                    acta.setServicio(actaJson.optInt("servicio"));
                    acta.setCargaInicial(actaJson.optBoolean("cargaInicial"));
                    acta.setIdSolicitud(actaJson.optInt("idSolicitud"));
                    acta.setFechaCreacion(new Date(actaJson.optLong("fechaCreacion")));
                    acta.setCausaRetiro(actaJson.optString("causaRetiro"));
                    acta.setIdGrua(actaJson.optInt("idGrua"));

                    // Setear grua en la sesión

                    Global.sessionManager.setGrua(actaJson.optString("idGrua"));

                    acta.setFiscalia(actaJson.optBoolean("fiscalia"));

                    acta.setTarea(tareaActual);

                    // Datos del vehiculo

                    JSONObject vehiculoDataJson= actaJson.getJSONObject("vehiculoData");
                    JSONObject vehiculoJson= vehiculoDataJson.getJSONObject("vehiculo");

                    VehiculoData vehiculoData= new VehiculoData();
                    Vehiculo vehiculo= new Vehiculo();

                    vehiculo.setTamano(vehiculoJson.optString("tamano"));
                    vehiculo.setModelo(vehiculoJson.optString("modelo"));
                    vehiculo.setMatricula(vehiculoJson.optString("matricula"));
                    vehiculo.setMarca(vehiculoJson.optString("marca"));
                    vehiculo.setCarpetaVehiculo(vehiculoJson.optString("carpetaVehiculo"));
                    vehiculo.setServicio(vehiculoJson.optInt("servicio"));
                    vehiculo.setAnio(vehiculoJson.optInt("anio"));
                    vehiculo.setOrigenVehiculo(vehiculoJson.optBoolean("origenVehiculo"));
                    vehiculo.setColor(vehiculoJson.optString("color"));
                    vehiculo.setCaracteristicas(vehiculoJson.optString("caracteristicas"));
                    vehiculo.setPuedeRodar(vehiculoJson.optBoolean("puedeRodar"));

                    long kmts = -1;

                    if (!vehiculoJson.isNull("kilometraje")) {
                        kmts = vehiculoJson.optLong("kilometraje");
                    }

                    vehiculo.setKilometraje(kmts);


                    Global.daoSession.getVehiculoDao().insert(vehiculo);
                    vehiculoData.setVehiculo(vehiculo);
                    Global.daoSession.getVehiculoDataDao().insert(vehiculoData);
                    acta.setVehiculoData(vehiculoData);

                    // Datos de la autoridad
                    JSONObject autoridadJson= actaJson.optJSONObject("autoridad");
                    JSONArray telefonosJson= autoridadJson.optJSONArray("telefonos");
                    JSONArray correosJson= autoridadJson.optJSONArray("correos");
                    JSONObject direccionJson= autoridadJson.optJSONObject("direccion");

                    Autoridad autoridad= new Autoridad();
                    Persona persona = new Persona();
                    Telefonos telefonos= new Telefonos();
                    Correos correos=new Correos();
                    Direccion direccion=new Direccion();

                    if(autoridadJson!=null){

                        persona.setNombre(autoridadJson.getString("nombre"));
                        persona.setApellidoPaterno(autoridadJson.getString("apellidoPaterno"));
                        persona.setApellidoMaterno(autoridadJson.getString("apellidoMaterno"));

                        String rut = autoridadJson.getString("rut");

                        persona.setRut(RutValidator.parseRut(rut));
                        persona.setUsuario(autoridadJson.getString("usuario"));
                        Global.daoSession.getPersonaDao().insertOrReplace(persona);

                        if(telefonosJson.length()>0) {
                            telefonos.setEmail(telefonosJson.get(0).toString());
                            Global.daoSession.getTelefonosDao().insert(telefonos);
                            persona.getTelefonos().add(telefonos);
                        }

                        if(correosJson.length()>0){
                            correos.setEmail(correosJson.get(0).toString());
                            Global.daoSession.getCorreosDao().insert(correos);
                            persona.getCorreos().add(correos);
                        }

                        if(direccionJson!=null){
                            direccion.setCalle(direccionJson.optString("calle"));
                            direccion.setComuna(direccionJson.optString("comuna"));
                            direccion.setNumeracion(direccionJson.optString("numeracion"));
                            direccion.setInterseccion(direccionJson.optString("interseccion"));
                            direccion.setReferencias(direccionJson.optString("referencias"));
                            Global.daoSession.getDireccionDao().insert(direccion);
                            persona.setDireccion(direccion);
                        }

                        autoridad.setCargo(autoridadJson.getString("cargo"));
                        autoridad.setUnidadPolicial(autoridadJson.getString("unidadPolicial"));
                        autoridad.setNumeroFuncionario(autoridadJson.getString("numeroFuncionario"));
                        autoridad.setInstitucion(autoridadJson.getString("institucion"));
                        autoridad.setPersona(persona);
                        Global.daoSession.getAutoridadDao().insert(autoridad);
                        acta.setAutoridad(autoridad);
                    }

                    // Datos del gruero
                    JSONObject grueroJson= actaJson.optJSONObject("gruero");

                    if(grueroJson!=null){
                        persona = new Persona();
                        telefonos= new Telefonos();
                        correos=new Correos();
                        direccion=new Direccion();

                        telefonosJson= grueroJson.optJSONArray("telefonos");
                        correosJson= grueroJson.optJSONArray("correos");
                        direccionJson= grueroJson.optJSONObject("direccion");

                        persona.setNombre(grueroJson.optString("nombre"));
                        persona.setApellidoPaterno(grueroJson.optString("apellidoPaterno"));
                        persona.setApellidoMaterno(grueroJson.optString("apellidoMaterno"));
                        persona.setRut(RutValidator.parseRut(grueroJson.optString("rut")));
                        persona.setUsuario(grueroJson.optString("usuario"));
                        Global.daoSession.getPersonaDao().insertOrReplace(persona);

                        if(telefonosJson.length()>0){
                            telefonos.setEmail(telefonosJson.get(0).toString());
                            Global.daoSession.getTelefonosDao().insertOrReplace(telefonos);
                            persona.getTelefonos().add(telefonos);
                        }
                        if(correosJson.length()>0){
                            correos.setEmail(correosJson.get(0).toString());
                            Global.daoSession.getCorreosDao().insertOrReplace(correos);
                            persona.getCorreos().add(correos);
                        }
                        if(direccionJson!=null){
                            direccion.setCalle(direccionJson.optString("calle"));
                            direccion.setComuna(direccionJson.getString("comuna"));
                            direccion.setNumeracion(direccionJson.optString("numeracion"));
                            direccion.setInterseccion(direccionJson.optString("interseccion"));
                            direccion.setReferencias(direccionJson.optString("referencias"));
                            Global.daoSession.getDireccionDao().insert(direccion);
                            persona.setDireccion(direccion);
                        }

                        acta.setPersona(persona);
                    }

                    direccionJson= actaJson.optJSONObject("direccion");
                    direccion= new Direccion();
                    // Datos del retiro

                    if(direccionJson!=null){
                        direccion.setCalle(direccionJson.optString("calle"));
                        direccion.setComuna(direccionJson.optString("comuna"));
                        direccion.setNumeracion(direccionJson.optString("numeracion"));
                        direccion.setInterseccion(direccionJson.optString("interseccion"));
                        direccion.setReferencias(direccionJson.optString("referencias"));
                        Global.daoSession.getDireccionDao().insert(direccion);
                        acta.setDireccion(direccion);
                    }

                    Global.daoSession.getActaDao().insert(acta);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void crearActa(DatosPDF datosPDF){

    }

    public void completarActa(final DatosPDF datosPDF) {

        // Obtener acta de la tarea activa
        final Acta acta= Global.daoSession.getActaDao().getByIdTarea(Global.sessionManager.getTareaActiva());

        // Si el acta no está previamente creada, crearla
        if(acta==null) {
            crearActa(datosPDF);
            return;
        }

        // get user data from session
        HashMap<String, String> user = Global.sessionManager.getUserDetails();

        // name
        final String rutGruero = user.get(SessionManager.KEY_RUT);
        // email
        final String nombreGruero = user.get(SessionManager.KEY_NOMBRE);
        // apellido paterno
        final String apellidoPaternoGruero = user.get(SessionManager.KEY_APELLIDO_PATERNO);
        // email
        final String apellidoMaternoGruero = user.get(SessionManager.KEY_APELLIDO_MATERNO);

        Global.daoSession.runInTx(new Runnable() {
            @Override
            public void run() {

                // Actualizar autoridad
                acta.getAutoridad().getPersona().setRut(datosPDF.getView1_01());
                acta.getAutoridad().getPersona().setNombre(datosPDF.getView1_02());
                acta.getAutoridad().getPersona().setApellidoPaterno(datosPDF.getView1_02_apellidopaterno());
                acta.getAutoridad().getPersona().setApellidoMaterno(datosPDF.getView1_02_apellidomaterno());

                Telefonos telefonos= new Telefonos(null,datosPDF.getView1_02_telefonos(),0);
                telefonos.setTelefonosID(acta.getAutoridad().getPersonaID());
                Global.daoSession.getTelefonosDao().insert(telefonos);
                acta.getAutoridad().getPersona().getTelefonos().add(telefonos);

                Correos correos= new Correos(null,datosPDF.getView1_02_correos(),0);
                correos.setCorreosID(acta.getAutoridad().getPersonaID());
                Global.daoSession.getCorreosDao().insert(correos);
                acta.getAutoridad().getPersona().getCorreos().add(correos);

                acta.getAutoridad().setInstitucion(datosPDF.getView1_03());
                acta.getAutoridad().setCargo(datosPDF.getView1_04());
                acta.getAutoridad().setUnidadPolicial(datosPDF.getView1_05());
                acta.getAutoridad().setNumeroFuncionario(datosPDF.getView1_06());

                // Actualizar direccion
                acta.setFiscalia(datosPDF.getView2_00());
                acta.getDireccion().setCalle(datosPDF.getView2_02());
                acta.getDireccion().setNumeracion(datosPDF.getView2_03());
                acta.getDireccion().setInterseccion(datosPDF.getView2_04());
                acta.getDireccion().setComuna(datosPDF.getView2_05());
                acta.getDireccion().setReferencias(datosPDF.getView1_00());

                // Actualizar información policial

                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy",new Locale("es","CL"));
                    acta.setFechaParte(sdf.parse(datosPDF.getView3_01()));
                } catch (ParseException e) {
                    e.printStackTrace();
                };

                acta.setParte(datosPDF.getView3_02());
                acta.setUnidadPolicial(datosPDF.getView3_03());
                acta.setNue(datosPDF.getView3_04());
                acta.setRuc(datosPDF.getView3_05());
                acta.setActaIncautacion(datosPDF.getView3_06());
                acta.setOficioRemisor(datosPDF.getView3_07());

                acta.setTribunalID(datosPDF.getView3_08());

                // Actualizar vehículo
                acta.getVehiculoData().getVehiculo().setTamano(datosPDF.getView4_00());
                acta.getVehiculoData().getVehiculo().setMatricula(datosPDF.getView4_01());
                acta.getVehiculoData().getVehiculo().setMarca(datosPDF.getView4_02());
                acta.getVehiculoData().getVehiculo().setModelo(datosPDF.getView4_03());
                if(!datosPDF.getView4_04().toString().equals(""))
                    acta.getVehiculoData().getVehiculo().setAnio(Integer.parseInt(datosPDF.getView4_04()));
                acta.getVehiculoData().getVehiculo().setColor(datosPDF.getView4_05());
                if(!datosPDF.getView4_06().toString().equals(""))
                    acta.getVehiculoData().getVehiculo().setKilometraje(Long.parseLong(datosPDF.getView4_06()));
                acta.getVehiculoData().getVehiculo().setNumeroMotor(datosPDF.getView4_07());
                acta.getVehiculoData().getVehiculo().setNumeroChasis(datosPDF.getView4_08());
                acta.getVehiculoData().getVehiculo().setOrigenVehiculo(datosPDF.getView4_09());

                // Actualizar información visual del vehículo
                acta.setExistImage(datosPDF.isView5_01());
                acta.setExistVideo(datosPDF.isView5_02());
                acta.setObservacionImgenes(datosPDF.getView5_03());

                FichaEstadoVisual fichaEstadoVisual;

                for(int i=0;i<datosPDF.getView5().size();++i){
                    fichaEstadoVisual=datosPDF.getView5().get(i);
                    fichaEstadoVisual.setIdVehiculo(acta.getVehiculoData().getVehiculo().getId());
                    Global.daoSession.getFichaEstadoVisualDao().insert(fichaEstadoVisual);
                    acta.getVehiculoData().getVehiculo().getFichaEstadoVisualList().add(fichaEstadoVisual);
                }

                Correos correoConductor, correoPropietario;
                Telefonos fonoConductor, fonoPropietario;
                Persona propietario=new Persona();
                Persona conductor;

                // Si el rut del propietario no es vacio
                if(!datosPDF.getView6_01().toString().equals("")) {

                    // Primero agregar persona propietario
                    propietario = new Persona(null, datosPDF.getView6_02(), datosPDF.getView6_01(), datosPDF.getView6_02_paterno(), datosPDF.getView6_02_materno(), "", 0, 0, 0);
                    Global.daoSession.getPersonaDao().insertOrReplace(propietario);

                    propietario.getCorreos();
                    correoPropietario = new Correos(null, datosPDF.getView6_04(), 0);
                    correoPropietario.setCorreosID(propietario.getId());
                    Global.daoSession.getCorreosDao().insertOrReplace(correoPropietario);
                    propietario.getCorreos().add(correoPropietario);

                    propietario.getTelefonos();
                    fonoPropietario = new Telefonos(null, datosPDF.getView6_05(), 0);
                    fonoPropietario.setTelefonosID(propietario.getId());
                    Global.daoSession.getTelefonosDao().insertOrReplace(fonoPropietario);
                    propietario.getTelefonos().add(fonoPropietario);

                    // Agregar clientePropietario
                    acta.getVehiculoData().getClientePropietario();
                    Cliente clientePropietario = new Cliente(null, datosPDF.getView6_03(), 0, 0);
                    clientePropietario.setClientePropietarioID(acta.getVehiculoDataID());
                    clientePropietario.setPersonaID(propietario.getId());
                    Global.daoSession.getClienteDao().insertOrReplace(clientePropietario);
                    acta.getVehiculoData().getClientePropietario().add(clientePropietario);
                }

                // Si el rut del conductor no es vacio
                if(!datosPDF.getView6_06().toString().equals("")) {

                    // Primero agregar persona conductor
                    if(!datosPDF.getView6_06().toString().equals(datosPDF.getView6_06().toString())){
                        conductor = new Persona(null, datosPDF.getView6_07(), datosPDF.getView6_06(), datosPDF.getView6_06_paterno(), datosPDF.getView6_06_materno(), "", 0, 0, 0);
                        Global.daoSession.getPersonaDao().insertOrReplace(conductor);

                        conductor.getCorreos();
                        correoConductor = new Correos(null, datosPDF.getView6_09(), 0);
                        correoConductor.setCorreosID(conductor.getId());
                        Global.daoSession.getCorreosDao().insertOrReplace(correoConductor);
                        conductor.getCorreos().add(correoConductor);

                        conductor.getTelefonos();
                        fonoConductor = new Telefonos(null, datosPDF.getView6_10(), 0);
                        fonoConductor.setTelefonosID(conductor.getId());
                        Global.daoSession.getTelefonosDao().insertOrReplace(fonoConductor);
                        conductor.getTelefonos().add(fonoConductor);
                    }
                    else{
                        conductor=propietario;
                    }
                    // Agregar clientePropietario
                    acta.getVehiculoData().getClientePropietario();
                    Cliente clienteConductor = new Cliente(null, datosPDF.getView6_08(), 0, 0);
                    clienteConductor.setClientePropietarioID(acta.getVehiculoDataID());
                    clienteConductor.setPersonaID(conductor.getId());
                    Global.daoSession.getClienteDao().insertOrReplace(clienteConductor);
                    acta.getVehiculoData().getClientePropietario().add(clienteConductor);
                }
                //acta.getVehiculoData().getClientePropietario().set.setPersona(conductor);

                Persona gruero= Global.daoSession.getPersonaDao().getByRut(rutGruero);

                if(gruero==null){
                    gruero= new Persona(null,nombreGruero,rutGruero,apellidoPaternoGruero,apellidoMaternoGruero,"",0,0,0);
                    Global.daoSession.getPersonaDao().insertOrReplace(gruero);
                }
                acta.setPersona(gruero);

                for(int i=0;i<datosPDF.getView8_01().size();++i) {
                    acta.getVehiculoData().getEspeciasList();
                    Especias especias= new Especias(null, (String) datosPDF.getView8_01().get(i),0);
                    especias.setEspeciasID(acta.getVehiculoDataID());
                    Global.daoSession.getEspeciasDao().insert(especias);
                    acta.getVehiculoData().getEspeciasList().add(especias);
                }

                Global.daoSession.getActaDao().update(acta);
            }
        });
    }

    public Image firmarActa(byte[] firmaAutoridad, byte[] firmaGruero){

        SessionManager session = new SessionManager(localContext);

        // Obtener acta de la tarea activa
        Acta acta= Global.daoSession.getActaDao().getByIdTarea(session.getTareaActiva());

        String text1 = Base64.encodeToString(firmaAutoridad, Base64.DEFAULT);
        String text2 = Base64.encodeToString(firmaGruero, Base64.DEFAULT);

        acta.setFechaFirma(new Date());

        Image imagen = null;

        try {
            imagen = Image.getInstance(Base64.decode(text1,Base64.DEFAULT));
        } catch (BadElementException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }

        //System.out.println("text1.length(): "+text1.length());

        test3.ncxchile.cl.greenDAO.Firma firma= new test3.ncxchile.cl.greenDAO.Firma(null, text1, text2);
        Global.daoSession.getFirmaDao().insert(firma);
        acta.setFirma(firma);

        Global.daoSession.getActaDao().update(acta);

        return imagen;
    }
    
    public void escribirXML(DatosPDF datosPdf, File storageDir, String nombreArchivo) throws IOException{

        File newxmlfile = new File(storageDir.getAbsolutePath(), nombreArchivo);

        newxmlfile.createNewFile();
        //we have to bind the new file with a FileOutputStream
        FileOutputStream fileos = null;
        fileos = new FileOutputStream(newxmlfile);
        //we create a XmlSerializer in order to write xml data
        XmlSerializer serializer = Xml.newSerializer();
        try {
            serializer.setOutput(fileos, "UTF-8");
            serializer.startDocument(null, true);
            serializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
            serializer.startTag(null, "tns:acta");
            serializer.attribute(null, "xsi:schemaLocation", "http://www.cmvrc.cl/schemas/ common common.xsd ");
            serializer.attribute(null, "xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
            serializer.attribute(null, "xmlns:tns", "http://www.cmvrc.cl/schemas/common");
            serializer.startTag(null, "tns:id");
            serializer.text("0");
            serializer.endTag(null, "tns:id");

            serializer.startTag(null, "tns:vehiculoData");
            serializer.startTag(null, "tns:vehiculo");
            serializer.startTag(null, "tns:marca");
            serializer.text(datosPdf.getView4_02().toString());
            serializer.endTag(null, "tns:marca");
            serializer.startTag(null, "tns:modelo");
            serializer.text(datosPdf.getView4_03().toString());
            serializer.endTag(null, "tns:modelo");
            serializer.startTag(null, "tns:anio");
            serializer.text(datosPdf.getView4_04().toString());
            serializer.endTag(null, "tns:anio");
            serializer.startTag(null, "tns:color");
            serializer.text(datosPdf.getView4_05().toString());
            serializer.endTag(null, "tns:color");
            serializer.startTag(null, "tns:matricula");
            serializer.text(datosPdf.getView4_01().toString());
            serializer.endTag(null, "tns:matricula");
            serializer.startTag(null, "tns:modificado");
            serializer.endTag(null, "tns:modificado");
            serializer.startTag(null, "tns:caracteristicas");
            serializer.endTag(null, "tns:caracteristicas");
            serializer.startTag(null, "tns:numeroChasis");
            serializer.text(datosPdf.getView4_08().toString());
            serializer.endTag(null, "tns:numeroChasis");
            serializer.startTag(null, "tns:numeroMotor");
            serializer.text(datosPdf.getView4_07().toString());
            serializer.endTag(null, "tns:numeroMotor");
            serializer.startTag(null, "tns:tamano");
            serializer.text(datosPdf.getView4_00().toString());
            serializer.endTag(null, "tns:tamano");
            serializer.startTag(null, "tns:kilometraje");
            serializer.text(datosPdf.getView4_06().toString());
            serializer.endTag(null, "tns:kilometraje");
            serializer.startTag(null, "tns:carpetaVehiculo");
            serializer.text(datosPdf.getView1_00().toString());
            serializer.endTag(null, "tns:carpetaVehiculo");
            serializer.startTag(null, "tns:id");
            serializer.endTag(null, "tns:id");
            serializer.startTag(null, "tns:servicio");
            serializer.endTag(null, "tns:servicio");
            serializer.startTag(null, "tns:vin");
            serializer.endTag(null, "tns:vin");

            serializer.startTag(null, "tns:fichaEstadoVisual");
            serializer.startTag(null, "tns:idFichaEstadoVisual");
            serializer.endTag(null, "tns:idFichaEstadoVisual");
            serializer.startTag(null, "tns:idFicha");
            serializer.endTag(null, "tns:idFicha");
            serializer.startTag(null, "tns:fichaEstadoVisual");

            serializer.endTag(null, "tns:fichaEstadoVisual");
            serializer.endTag(null, "tns:fichaEstadoVisual");

            serializer.endTag(null, "tns:vehiculo");
            serializer.endTag(null, "tns:vehiculoData");

            serializer.startTag(null, "autoridad_solicitante");
            serializer.attribute(null, "numero_campos", "7");
            serializer.startTag(null, "v01_num_orden");
            serializer.text(datosPdf.getView1_00().toString());
            serializer.endTag(null, "v01_num_orden");
            serializer.startTag(null, "v01_rut_autoridad");
            serializer.text(datosPdf.getView1_01().toString());
            serializer.endTag(null, "v01_rut_autoridad");
            serializer.startTag(null, "v01_nombre_autoridad");
            serializer.text(datosPdf.getView1_02().toString());
            serializer.endTag(null, "v01_nombre_autoridad");
            serializer.startTag(null, "v01_institucion");
            serializer.text(datosPdf.getView1_03().toString());
            serializer.endTag(null, "v01_institucion");
            serializer.startTag(null, "v01_cargo");
            serializer.text(datosPdf.getView1_04().toString());
            serializer.endTag(null, "v01_cargo");
            serializer.startTag(null, "v01_unidad");
            serializer.text(datosPdf.getView1_05().toString());
            serializer.endTag(null, "v01_unidad");
            serializer.startTag(null, "v01_numfunc");
            serializer.text(datosPdf.getView1_06().toString());
            serializer.endTag(null, "v01_numfunc");
            serializer.endTag(null, "autoridad_solicitante");

            serializer.startTag(null, "info_retiro");
            serializer.attribute(null, "numero_campos", "6");
            serializer.startTag(null, "v02_motivo");
            serializer.text(datosPdf.getView2_01().toString());
            serializer.endTag(null, "v02_motivo");
            serializer.startTag(null, "v02_avocalle");
            serializer.text(datosPdf.getView2_02().toString());
            serializer.endTag(null, "v02_avocalle");
            serializer.startTag(null, "v02_numexacta");
            serializer.text(datosPdf.getView2_03().toString());
            serializer.endTag(null, "v02_numexacta");
            serializer.startTag(null, "v02_entrecalles");
            serializer.text(datosPdf.getView2_04().toString());
            serializer.endTag(null, "v02_entrecalles");
            serializer.startTag(null, "v02_comuna");
            serializer.text(datosPdf.getView2_05().toString());
            serializer.endTag(null, "v02_comuna");
            serializer.startTag(null, "v02_referencias");
            serializer.text(datosPdf.getView2_06().toString());
            serializer.endTag(null, "v02_referencias");
            serializer.endTag(null, "info_retiro");

            serializer.startTag(null, "info_policial");
            serializer.attribute(null, "numero_campos", "8");
            serializer.startTag(null, "v03_fechaparte");
            serializer.text(datosPdf.getView3_01().toString());
            serializer.endTag(null, "v03_fechaparte");
            serializer.startTag(null, "v03_numeroparte");
            serializer.text(datosPdf.getView3_02().toString());
            serializer.endTag(null, "v03_numeroparte");
            serializer.startTag(null, "v03_numerounidadpolicial");
            serializer.text(datosPdf.getView3_03().toString());
            serializer.endTag(null, "v03_numerounidadpolicial");
            serializer.startTag(null, "v03_nue");
            serializer.text(datosPdf.getView3_04().toString());
            serializer.endTag(null, "v03_nue");
            serializer.startTag(null, "v03_ruc");
            serializer.text(datosPdf.getView3_05().toString());
            serializer.endTag(null, "v03_ruc");
            serializer.startTag(null, "v03_actaincautacion");
            serializer.text(datosPdf.getView3_06().toString());
            serializer.endTag(null, "v03_actaincautacion");
            serializer.startTag(null, "v03_oficioremisor");
            serializer.text(datosPdf.getView3_07().toString());
            serializer.endTag(null, "v03_oficioremisor");
            serializer.startTag(null, "v03_tribunalcompetente");
            Tribunal tribunal=Global.daoSession.getTribunalDao().getById(datosPdf.getView3_08());
            serializer.text(tribunal.getNombre());
            serializer.endTag(null, "v03_tribunalcompetente");
            serializer.endTag(null, "info_policial");

            serializer.startTag(null, "info_vehiculo");
            serializer.attribute(null, "numero_campos", "9");
            serializer.startTag(null, "v04_kilometraje");
            serializer.text(datosPdf.getView4_06().toString());
            serializer.endTag(null, "v04_kilometraje");
            serializer.startTag(null, "v04_numeromotor");
            serializer.text(datosPdf.getView4_07().toString());
            serializer.endTag(null, "v04_numeromotor");
            serializer.startTag(null, "v04_origen");
            String origen="Nacional";
            if(datosPdf.getView4_09())
                origen="Extranjero";
            serializer.text(origen);
            serializer.endTag(null, "v04_origen");
            serializer.endTag(null, "info_vehiculo");

            serializer.startTag(null, "info_visual");
            serializer.attribute(null, "numero_campos", "14");
            if (datosPdf.isView5_01() && datosPdf.isView5_02()) {
                serializer.startTag(null, "v05_imgvid");
                serializer.text("Se adjuntan imágenes y videos");
                serializer.endTag(null, "v05_imgvid");
            } else {
                serializer.startTag(null, "v05_imgvid");
                serializer.text(datosPdf.getView5_03());
                serializer.endTag(null, "v05_imgvid");
            }
            for(int i=0;i<datosPdf.getView5().size();++i){
                EstadoVisual estadoVisual=Global.daoSession.getEstadoVisualDao().getById(datosPdf.getView5().get(i).getIdEstadoVisual());
                if(estadoVisual.getTipo()==1){
                    serializer.startTag(null, estadoVisual.getNombre());
                    serializer.text(datosPdf.getView5().get(i).getObservacion());
                    serializer.endTag(null, estadoVisual.getNombre());
                }
                else{
                    serializer.startTag(null, estadoVisual.getNombre());
                    if(datosPdf.getView5().get(i).getValor())
                        serializer.text("Si");
                    else
                        serializer.text("No");
                    serializer.endTag(null, estadoVisual.getNombre());
                }
            }

            serializer.endTag(null, "info_visual");

            serializer.startTag(null, "info_propietarioconductor");
            serializer.attribute(null, "numero_campos", "10");
            serializer.startTag(null, "v06_rutpropietario");
            serializer.text(datosPdf.getView6_01().toString());
            serializer.endTag(null, "v06_rutpropietario");
            serializer.startTag(null, "v06_nombrepropietario");
            serializer.text(datosPdf.getView6_02().toString());
            serializer.endTag(null, "v06_nombrepropietario");
            serializer.startTag(null, "v06_licenciapropietario");
            serializer.text(datosPdf.getView6_03().toString());
            serializer.endTag(null, "v06_licenciapropietario");
            serializer.startTag(null, "v06_correopropietario");
            serializer.text(datosPdf.getView6_04().toString());
            serializer.endTag(null, "v06_correopropietario");
            serializer.startTag(null, "v06_telefonopropietario");
            serializer.text(datosPdf.getView6_05().toString());
            serializer.endTag(null, "v06_telefonopropietario");
            serializer.startTag(null, "v06_rutconductor");
            serializer.text(datosPdf.getView6_06().toString());
            serializer.endTag(null, "v06_rutconductor");
            serializer.startTag(null, "v06_nombreconductor");
            serializer.text(datosPdf.getView6_07().toString());
            serializer.endTag(null, "v06_nombreconductor");
            serializer.startTag(null, "v06_licenciaconductor");
            serializer.text(datosPdf.getView6_08().toString());
            serializer.endTag(null, "v06_licenciaconductor");
            serializer.startTag(null, "v06_correoconductor");
            serializer.text(datosPdf.getView6_09().toString());
            serializer.endTag(null, "v06_correoconductor");
            serializer.startTag(null, "v06_telefonoconductor");
            serializer.text(datosPdf.getView6_10().toString());
            serializer.endTag(null, "v06_telefonoconductor");
            serializer.endTag(null, "info_propietarioconductor");

            serializer.startTag(null, "info_grua");
            serializer.attribute(null, "numero_campos", "3");
            serializer.startTag(null, "v07_id");
            serializer.text(datosPdf.getView7_01().toString());
            serializer.endTag(null, "v07_id");
            serializer.startTag(null, "v07_nombre");
            serializer.text(datosPdf.getView7_02().toString());
            serializer.endTag(null, "v07_nombre");
            serializer.startTag(null, "v07_rut");
            serializer.text(datosPdf.getView7_03().toString());
            serializer.endTag(null, "v07_rut");
            serializer.endTag(null, "info_grua");

            serializer.startTag(null, "especies");
            serializer.attribute(null, "numero_campos", "1");
            serializer.startTag(null, "v08_especies");
            serializer.text(datosPdf.getView8_01().toString());
            serializer.endTag(null, "v08_especies");
            serializer.endTag(null, "especies");
            serializer.endTag(null, "tns:acta");

            serializer.endDocument();

            serializer.flush();
            //fileos.flush();
            fileos.close();

            Toast.makeText(localContext, "xml created", Toast.LENGTH_LONG);
        } catch (Exception e) {
            Log.e("Exception", "error occurred while creating xml file");
        }

    }

    public void escribirPdf(DatosPDF datosPdf, File storageDir, String nombre_archivo, DrawingView mDrawingView, DrawingView mDrawingView2, Document documento ) throws IOException, DocumentException {

        File file = new File(storageDir.getAbsolutePath(), nombre_archivo);

        // Creamos el flujo de datos de salida para el fichero donde
        // guardaremos el pdf.
        FileOutputStream ficheroPdf = new FileOutputStream(file.getAbsolutePath());

        // Asociamos el flujo que acabamos de crear al documento.
        PdfWriter writer = PdfWriter.getInstance(documento, ficheroPdf);

        // Incluimos el píe de página y una cabecera

        HeaderFooter pie = new HeaderFooter(new Phrase("Orden de servicio Nº: " + Global.sessionManager.getServicio()), false);

        documento.setFooter(pie);

        // Abrimos el documento.
        documento.open();

        // Añadimos un título con la fuente por defecto.
        documento.add(new Paragraph("Soc. Concesionaria Centro Metropolitano de Vehículos Retirados de Circulación"));
        documento.add(new Paragraph("S.A."));
        documento.add(new Paragraph("R.U.T: 76.101.714-4"));
        documento.add(new Paragraph("WWW.CUSTODIAMETROPOLITANA.CL"));
        documento.add(new Paragraph("Camino Lo Echevers 920 Quilicura - Santiago"));
        documento.add(new Paragraph("FONO: 800 000 106"));

        Bitmap bitmap = BitmapFactory.decodeResource(localContext.getResources(), R.drawable.logo_pdf);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        Image imagen = Image.getInstance(stream.toByteArray());
        imagen.setAbsolutePosition(450f, 720f);
        documento.add(imagen);

        Paragraph preface = new Paragraph();
        Font h1 = FontFactory.getFont(FontFactory.HELVETICA, 28, Font.BOLD);
        Chunk chunk = new Chunk("Acta de recepción");
        preface.setAlignment(Element.ALIGN_CENTER);
        preface.setSpacingBefore(30f);
        preface.setFont(h1);
        preface.add(chunk);
        documento.add(preface);

        Paragraph preface2 = new Paragraph();
        Chunk chunk2 = new Chunk("Orden de Servicio N° " + Global.sessionManager.getServicio());
        preface2.setAlignment(Element.ALIGN_CENTER);
        preface2.setFont(h1);
        preface2.setSpacingBefore(15f);
        preface2.setSpacingAfter(15f);
        preface2.add(chunk2);
        documento.add(preface2);

        // Información del Acta
        PdfPTable tabla = new PdfPTable(1);
        tabla.setWidthPercentage(100f);
        PdfPTable tabla2 = new PdfPTable(2);
        PdfPTable tabla3 = new PdfPTable(2);
        tabla.addCell("Información del acta");
        SimpleDateFormat fecha = new SimpleDateFormat("dd-MM-yyyy");
        String fechaHoy = fecha.format(new Date());
        SimpleDateFormat hora = new SimpleDateFormat("HH:mm:ss");
        String horaHoy = hora.format(new Date());
        tabla2.addCell("Fecha de recepción: " + fechaHoy);
        tabla2.addCell("Hora de recepción: " + horaHoy);

        tabla3.addCell("Fecha de firma del Acta: " + fechaHoy);
        tabla3.addCell("Hora de firma del Acta: " + horaHoy);
        tabla.addCell(tabla2);
        tabla.addCell(tabla3);

        // Autoridad que solicita
        PdfPTable tabla4 = new PdfPTable(1);
        tabla4.setWidthPercentage(100f);
        tabla4.addCell("Autoridad que solicita");
        PdfPTable tabla5 = new PdfPTable(2);
        tabla5.addCell("Nombre: " + datosPdf.getView1_02().toString()+" "+datosPdf.getView1_02_apellidopaterno().toString());
        tabla5.addCell("Rut: " + datosPdf.getView1_01().toString());
        PdfPTable tabla6 = new PdfPTable(2);
        tabla6.addCell("Teléfono: " + datosPdf.getView1_02_telefono().toString());
        tabla6.addCell("Correo: " + datosPdf.getView1_02_correo().toString());
        PdfPTable tabla7 = new PdfPTable(2);
        tabla7.addCell("Institución: " + datosPdf.getView1_03().toString());
        tabla7.addCell("Cargo: " + datosPdf.getView1_04().toString());
        PdfPTable tabla8 = new PdfPTable(2);
        tabla8.addCell("Unidad jurisdiccional: " + datosPdf.getView1_05().toString());
        tabla8.addCell("Número de funcionario: " + datosPdf.getView1_06().toString());
        PdfPTable tabla9 = new PdfPTable(2);
        tabla9.addCell("Fiscalía: ");
        tabla9.addCell(" ");
        tabla4.addCell(tabla5);
        tabla4.addCell(tabla6);
        tabla4.addCell(tabla7);
        tabla4.addCell(tabla8);
        tabla4.addCell(tabla9);

        // Información operador que recibe
        PdfPTable tabla10 = new PdfPTable(1);
        tabla10.setWidthPercentage(100f);
        tabla10.addCell("Información operador que recibe");
        PdfPTable tabla11 = new PdfPTable(2);
        tabla11.addCell("Nombre: " + datosPdf.getView7_02().toString());
        tabla11.addCell("Grua Nº: " + datosPdf.getView7_01().toString());
        PdfPTable tabla12 = new PdfPTable(2);
        tabla12.addCell("Rut: " + datosPdf.getView7_03().toString());
        tabla12.addCell(" ");
        tabla10.addCell(tabla11);
        tabla10.addCell(tabla12);

        // Información del retiro
        PdfPTable tabla13 = new PdfPTable(1);
        tabla13.setWidthPercentage(100f);
        tabla13.addCell("Información del retiro");
        PdfPTable tabla14 = new PdfPTable(2);
        tabla14.addCell("Dirección: " + datosPdf.getView2_02().toString() + ", " + datosPdf.getView2_03().toString());
        tabla14.addCell("Entre calles: " + datosPdf.getView2_04().toString());
        PdfPTable tabla15 = new PdfPTable(2);
        tabla15.addCell("Comuna: " + datosPdf.getView2_05().toString());
        tabla15.addCell("Motivo del Retiro: " + datosPdf.getView2_01().toString());
        PdfPTable tabla16 = new PdfPTable(2);
        tabla16.addCell("Otras Referencias: " + datosPdf.getView2_06().toString());
        tabla16.addCell(" ");
        tabla13.addCell(tabla14);
        tabla13.addCell(tabla15);
        tabla13.addCell(tabla16);

        // Información del vehículo
        PdfPTable tabla17 = new PdfPTable(1);
        tabla17.setWidthPercentage(100f);
        tabla17.addCell("Información del vehículo");
        PdfPTable tabla18 = new PdfPTable(2);
        tabla18.addCell("N° de placa patente: " + datosPdf.getView4_01().toString());
        tabla18.addCell("Marca: " + datosPdf.getView4_02().toString());
        PdfPTable tabla19 = new PdfPTable(2);
        tabla19.addCell("Modelo: " + datosPdf.getView4_03().toString());
        tabla19.addCell("Color: " + datosPdf.getView4_05().toString());
        PdfPTable tabla20 = new PdfPTable(2);
        tabla20.addCell("N° de chasis: " + datosPdf.getView4_08().toString());
        tabla20.addCell("N° de motor: " + datosPdf.getView4_07().toString());
        PdfPTable tabla21 = new PdfPTable(2);
        tabla21.addCell("Tipo: No identificado");
        tabla21.addCell("Kilometraje: " + datosPdf.getView4_06().toString());
        tabla17.addCell(tabla18);
        tabla17.addCell(tabla19);
        tabla17.addCell(tabla20);
        tabla17.addCell(tabla21);

        // Estado Visual
        PdfPTable tabla22 = new PdfPTable(1);
        tabla22.setWidthPercentage(100f);
        tabla22.addCell("Estado Visual");

        for(int i=0;i<datosPdf.getView5().size();++i){
            PdfPTable tablaFichaEstadoVisual = new PdfPTable(1);
            FichaEstadoVisual fichaEstadoVisual=datosPdf.getView5().get(i);

            EstadoVisual estadoVisual=Global.daoSession.getEstadoVisualDao().getById(fichaEstadoVisual.getIdEstadoVisual());
            if(estadoVisual.getTipo()==1) {
                tablaFichaEstadoVisual.addCell(estadoVisual.getNombre() + ": " + fichaEstadoVisual.getObservacion());
            }
            else {
                if(fichaEstadoVisual.getValor())
                    tablaFichaEstadoVisual.addCell(estadoVisual.getNombre() + ": Si");
                else
                    tablaFichaEstadoVisual.addCell(estadoVisual.getNombre() + ": No");
            }
            tabla22.addCell(tablaFichaEstadoVisual);
        }

        PdfPTable tabla36 = new PdfPTable(1);
        String origen="Nacional";
        if(datosPdf.getView4_09())
            origen="Extranjero";
        tabla36.addCell("Origen: "+ origen);
        PdfPTable tabla37 = new PdfPTable(1);

        if (datosPdf.isView5_01() == true && datosPdf.isView5_02() == true) {
            tabla37.addCell("Asociación de Imagenes/Videos: Se adjuntan imágenes y videos");
        } else {
            tabla37.addCell("Asociación de Imagenes/Videos: " + datosPdf.getView5_03());
        }

        tabla22.addCell(tabla36);
        tabla22.addCell(tabla37);

        // Información Policial
        PdfPTable tabla38 = new PdfPTable(1);
        tabla38.setWidthPercentage(100f);
        tabla38.addCell("Información Policial");
        PdfPTable tabla39 = new PdfPTable(2);
        tabla39.addCell("Fecha Parte Policial: "+ datosPdf.getView3_01().toString());
        tabla39.addCell("N° Parte Policial: " + datosPdf.getView3_02().toString());
        PdfPTable tabla40 = new PdfPTable(2);
        tabla40.addCell("N° Unidad Polical: " + datosPdf.getView3_03().toString());
        tabla40.addCell("R.U.C.: " + datosPdf.getView3_05().toString());
        PdfPTable tabla41 = new PdfPTable(2);
        tabla41.addCell("N.U.E.: " + datosPdf.getView3_04().toString());
        tabla41.addCell("Acta de incautación: " + datosPdf.getView3_06().toString());
        PdfPTable tabla42 = new PdfPTable(2);
        Tribunal tribunal=Global.daoSession.getTribunalDao().getById(datosPdf.getView3_08());
        tabla42.addCell("Tribunal Competente: " + tribunal.getNombre());
        tabla42.addCell("Oficio remitente: " + datosPdf.getView3_07().toString());
        tabla38.addCell(tabla39);
        tabla38.addCell(tabla40);
        tabla38.addCell(tabla41);
        tabla38.addCell(tabla42);

        // Conductor del vehículo
        PdfPTable tabla43 = new PdfPTable(1);
        tabla43.setWidthPercentage(100f);
        tabla43.addCell("Conductor del vehículo");
        PdfPTable tabla44 = new PdfPTable(2);
        tabla44.addCell("Nombre: " + datosPdf.getView6_07().toString());
        tabla44.addCell("Rut: " + datosPdf.getView6_06().toString());
        PdfPTable tabla45 = new PdfPTable(2);
        tabla45.addCell("Teléfono: " + datosPdf.getView6_10().toString());
        tabla45.addCell("Correo: " + datosPdf.getView6_09().toString());
        PdfPTable tabla46 = new PdfPTable(1);
        tabla46.addCell("Licencia: " + datosPdf.getView6_08().toString());
        tabla43.addCell(tabla44);
        tabla43.addCell(tabla45);
        tabla43.addCell(tabla46);

        // Propietario del Vehículo
        PdfPTable tabla47 = new PdfPTable(1);
        tabla47.setWidthPercentage(100f);
        tabla47.addCell("Propietario del Vehículo");
        PdfPTable tabla48 = new PdfPTable(2);
        tabla48.addCell("Nombre: " + datosPdf.getView6_02().toString());
        tabla48.addCell("Rut: " + datosPdf.getView6_01().toString());
        PdfPTable tabla49 = new PdfPTable(2);
        tabla49.addCell("Teléfono: " + datosPdf.getView6_05().toString());
        tabla49.addCell("Correo: " + datosPdf.getView6_04().toString());
        PdfPTable tabla50 = new PdfPTable(1);
        tabla50.addCell("Licencia: " + datosPdf.getView6_03().toString());
        tabla47.addCell(tabla48);
        tabla47.addCell(tabla49);
        tabla47.addCell(tabla50);

        // Especias del vehículo
        PdfPTable tabla51 = new PdfPTable(1);
        tabla51.setWidthPercentage(100f);
        tabla51.addCell("Especies del vehiculo");
        PdfPTable tabla52 = new PdfPTable(1);
        String especies_pdf="";

        for (int i = 0; i < datosPdf.getView8_01().size(); i++) {
            if (i == 0) {
                especies_pdf = datosPdf.getView8_01().get(0).toString();
            } else {
                especies_pdf = especies_pdf + ", " + datosPdf.getView8_01().get(i).toString();
            }
        }
        if(datosPdf.getView8_01().isEmpty()){
            tabla52.addCell("No hay especies registradas");
        }else{
            tabla52.addCell("Especies: " + especies_pdf);
        }
        tabla51.addCell(tabla52);

        Bitmap bm = mDrawingView.getDrawingCache();
        ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, stream2);
        Image imagen2 = Image.getInstance(stream2.toByteArray());
        imagen2.setWidthPercentage(10f);

        PdfPTable tabla53 = new PdfPTable(1);
        tabla53.setSpacingBefore(200f);
        tabla53.setWidthPercentage(100f);
        tabla53.addCell("Firma de la Autoridad");
        tabla53.addCell(imagen2);

        Bitmap bm2 = mDrawingView2.getDrawingCache();
        ByteArrayOutputStream stream3 = new ByteArrayOutputStream();
        bm2.compress(Bitmap.CompressFormat.PNG, 100, stream3);
        Image imagen3 = Image.getInstance(stream3.toByteArray());
        imagen3.setWidthPercentage(10f);

        Image testImage = firmarActa(stream2.toByteArray(), stream3.toByteArray());

        testImage.setWidthPercentage(10f);

        PdfPTable tabla54 = new PdfPTable(1);
        tabla54.setWidthPercentage(100f);
        tabla54.setSpacingBefore(10f);
        tabla54.addCell("Firma conductor grua");
        tabla54.addCell(imagen3);


        tabla.setSpacingAfter(10f);
        tabla4.setSpacingAfter(10f);
        tabla10.setSpacingAfter(10f);
        tabla13.setSpacingAfter(10f);
        tabla17.setSpacingAfter(10f);
        tabla22.setSpacingAfter(10f);

        tabla38.setSpacingAfter(10f);
        tabla43.setSpacingAfter(10f);
        tabla47.setSpacingAfter(10f);
        tabla51.setSpacingAfter(10f);

        documento.add(tabla);
        documento.add(tabla4);
        documento.add(tabla10);
        documento.add(tabla13);
        documento.add(tabla17);


        documento.add(tabla22);

        documento.add(tabla38);
        documento.add(tabla43);
        documento.add(tabla47);
        documento.add(tabla51);
        documento.add(tabla53);
        documento.add(tabla54);

        // Habilitar siguiente accion
    }

    public Acta getActaByTarea(Long idTarea){
        return Global.daoSession.getActaDao().getByIdTarea(idTarea);
    }

};
