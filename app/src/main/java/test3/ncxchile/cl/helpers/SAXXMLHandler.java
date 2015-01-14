package test3.ncxchile.cl.helpers;

/**
 * Created by Diego on 09-01-2015.
 */

import org.xml.sax.SAXException;

import java.util.ArrayList;
import java.util.List;
import org.xml.sax.Attributes;

import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.SAXException;

import test3.ncxchile.cl.greenDAO.Comuna;
import test3.ncxchile.cl.greenDAO.Institucion;
import test3.ncxchile.cl.greenDAO.Marca;
import test3.ncxchile.cl.greenDAO.Motivo;
import test3.ncxchile.cl.greenDAO.MotivoFiscalia;
import test3.ncxchile.cl.greenDAO.TipoVehiculo;
import test3.ncxchile.cl.greenDAO.Tribunal;
import test3.ncxchile.cl.greenDAO.User;
import test3.ncxchile.cl.models.EstadoVisual;

/**
 * Created by Diego on 09-01-2015.
 */
public class SAXXMLHandler extends DefaultHandler {

    private List<Object> objects;
    private String tempVal;
    // to maintain context
    private User gruero;
    private Comuna comuna;
    private Institucion institucion;
    private Marca marca;
    private Motivo motivo;
    private MotivoFiscalia motivoFiscalia;
    private TipoVehiculo tipoVehiculo;
    private Tribunal tribunal;
    private test3.ncxchile.cl.greenDAO.EstadoVisual estadoVisual;
    private String fixture="";
    private boolean id=false;
    private boolean valor=false;

    public SAXXMLHandler() {
        objects = new ArrayList<Object>();
    }

    public void setFixture(String fixture){
        this.fixture=fixture;
    }

    public List<Object> getObjects() {
        return objects;
    }

    // Event Handlers

    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {

        // reset
        tempVal = "";
        if(fixture.equalsIgnoreCase("grueros")){
            if (qName.equalsIgnoreCase("registrosTabla")) {
                gruero = new User();
                gruero.setId(null);
                gruero.setRut(Integer.parseInt(attributes.getValue("rut")));
                gruero.setDv(attributes.getValue("dv"));
                gruero.setApellidoPaterno(attributes.getValue("apellidoPaterno"));
                gruero.setApellidoMaterno(attributes.getValue("apellidoMaterno"));
            }
        }
        if(fixture.equalsIgnoreCase("comunas")){
            if (qName.equalsIgnoreCase("registrosTabla"))
                comuna = new Comuna();
        }
        if(fixture.equalsIgnoreCase("instituciones")){
            if (qName.equalsIgnoreCase("registrosTabla"))
                institucion = new Institucion();
        }
        if(fixture.equalsIgnoreCase("marcas")){
            if (qName.equalsIgnoreCase("registrosTabla"))
                marca = new Marca();
        }
        if(fixture.equalsIgnoreCase("motivos")){
            if (qName.equalsIgnoreCase("registrosTabla"))
                motivo = new Motivo();
        }
        if(fixture.equalsIgnoreCase("motivos_fiscalia")){
            if (qName.equalsIgnoreCase("registrosTabla"))
                motivoFiscalia = new MotivoFiscalia();
        }
        if(fixture.equalsIgnoreCase("tipos_vehiculo")) {
            if (qName.equalsIgnoreCase("registrosTabla"))
                tipoVehiculo = new TipoVehiculo();
        }
        if(fixture.equalsIgnoreCase("tribunales")) {
            if (qName.equalsIgnoreCase("registrosTabla"))
                tribunal = new Tribunal();
        }
        if(fixture.equalsIgnoreCase("estados_visuales")) {
            if (qName.equalsIgnoreCase("registrosTabla"))
                estadoVisual = new test3.ncxchile.cl.greenDAO.EstadoVisual();
        }
        if (qName.equalsIgnoreCase("id"))
            id=true;
        if (qName.equalsIgnoreCase("valor"))
            valor=true;
    }

    public void characters(char[] ch, int start, int length) throws SAXException {
        tempVal = new String(ch, start, length);
        System.out.println("tempVal: "+tempVal);
        if(fixture.equalsIgnoreCase("comunas")){
            if(id) {
                comuna.setId(Long.parseLong(tempVal));
                id=false;
            }
            if(valor){
                comuna.setNombre(tempVal);
                valor=false;
            }
        }
        if(fixture.equalsIgnoreCase("instituciones")){
            if(id) {
                institucion.setId(Long.parseLong(tempVal));
                id=false;
            }
            if(valor){
                institucion.setNombre(tempVal);
                valor=false;
            }
        }
        if(fixture.equalsIgnoreCase("marcas")){
            if(id) {
                marca.setId(Long.parseLong(tempVal));
                id=false;
            }
            if(valor){
                marca.setNombre(tempVal);
                valor=false;
            }
        }
        if(fixture.equalsIgnoreCase("motivos")){
            if(id) {
                motivo.setId(Long.parseLong(tempVal));
                id=false;
            }
            if(valor){
                motivo.setNombre(tempVal);
                valor=false;
            }
        }
        if(fixture.equalsIgnoreCase("motivos_fiscalia")){
            if(id) {
                motivoFiscalia.setId(Long.parseLong(tempVal));
                id=false;
            }
            if(valor){
                motivoFiscalia.setNombre(tempVal);
                valor=false;
            }
        }
        if(fixture.equalsIgnoreCase("tipos_vehiculo")){
            if(id) {
                tipoVehiculo.setId(Long.parseLong(tempVal));
                id=false;
            }
            if(valor){
                tipoVehiculo.setNombre(tempVal);
                valor=false;
            }
        }
        if(fixture.equalsIgnoreCase("tribunales")){
            if(id) {
                tribunal.setId(Long.parseLong(tempVal));
                id=false;
            }
            if(valor){
                tribunal.setNombre(tempVal);
                valor=false;
            }
        }
        if(fixture.equalsIgnoreCase("estados_visuales")){
            if(id) {
                estadoVisual.setId(Long.parseLong(tempVal));
                id=false;
            }
            if(valor){
                String[] params= tempVal.split("\\|");

                estadoVisual.setNombre(params[0]);
                if(params.length>1)
                    estadoVisual.setRespuestaBinaria(true);
                else
                    estadoVisual.setRespuestaBinaria(false);
                valor=false;
            }
        }
    }

    public void endElement(String uri, String localName, String qName)
            throws SAXException {

        if(fixture.equalsIgnoreCase("grueros")) {
            if (qName.equalsIgnoreCase("registrosTabla"))
                objects.add(gruero);
        }
        if(fixture.equalsIgnoreCase("comunas")) {
            if (qName.equalsIgnoreCase("registrosTabla"))
                objects.add(comuna);
        }
        if(fixture.equalsIgnoreCase("instituciones")) {
            if (qName.equalsIgnoreCase("registrosTabla"))
                objects.add(institucion);
        }
        if(fixture.equalsIgnoreCase("marcas")) {
            if (qName.equalsIgnoreCase("registrosTabla"))
                objects.add(marca);
        }
        if(fixture.equalsIgnoreCase("motivos")) {
            if (qName.equalsIgnoreCase("registrosTabla"))
                objects.add(motivo);
        }
        if(fixture.equalsIgnoreCase("motivos_fiscalia")) {
            if (qName.equalsIgnoreCase("registrosTabla"))
                objects.add(motivoFiscalia);
        }
        if(fixture.equalsIgnoreCase("tipos_vehiculo")) {
            if (qName.equalsIgnoreCase("registrosTabla"))
                objects.add(tipoVehiculo);
        }
        if(fixture.equalsIgnoreCase("tribunales")) {
            if (qName.equalsIgnoreCase("registrosTabla"))
                objects.add(tribunal);
        }
        if(fixture.equalsIgnoreCase("estados_visuales")) {
            if (qName.equalsIgnoreCase("registrosTabla"))
                objects.add(estadoVisual);
        }
    }
}

