package test3.ncxchile.cl.helpers;

/**
 * Created by Diego on 09-01-2015.
 */

import org.xml.sax.SAXException;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;

import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;

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
    private EstadoVisual estadoVisual;
    private String fixture="";

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
            if (qName.equalsIgnoreCase("registrosTabla")) {
                comuna = new Comuna();
                comuna.setId(Long.parseLong(attributes.getValue("id")));
                comuna.setNombre(attributes.getValue("nombre"));
            }
        }
        if(fixture.equalsIgnoreCase("instituciones")){
            if (qName.equalsIgnoreCase("registrosTabla")) {
                institucion = new Institucion();
                institucion.setId(Long.parseLong(attributes.getValue("id")));
                institucion.setNombre(attributes.getValue("nombre"));
            }
        }
        if(fixture.equalsIgnoreCase("marcas")){
            if (qName.equalsIgnoreCase("registrosTabla")) {
                marca = new Marca();
                marca.setId(Long.parseLong(attributes.getValue("id")));
                marca.setNombre(attributes.getValue("nombre"));
            }
        }
        if(fixture.equalsIgnoreCase("motivos")){
            if (qName.equalsIgnoreCase("registrosTabla")) {
                motivo = new Motivo();
                motivo.setId(Long.parseLong(attributes.getValue("id")));
                motivo.setNombre(attributes.getValue("nombre"));
            }
        }
        if(fixture.equalsIgnoreCase("motivos_fiscalia")){
            if (qName.equalsIgnoreCase("registrosTabla")) {
                motivoFiscalia = new MotivoFiscalia();
                motivoFiscalia.setId(Long.parseLong(attributes.getValue("id")));
                motivoFiscalia.setNombre(attributes.getValue("nombre"));
            }
        }
        if(fixture.equalsIgnoreCase("tipo_vehiculo")) {
            if (qName.equalsIgnoreCase("registrosTabla")) {
                tipoVehiculo = new TipoVehiculo();
                tipoVehiculo.setId(Long.parseLong(attributes.getValue("id")));
                tipoVehiculo.setNombre(attributes.getValue("nombre"));
            }
        }
        if(fixture.equalsIgnoreCase("tribunal")) {
            if (qName.equalsIgnoreCase("registrosTabla")) {
                tribunal = new Tribunal();
                tribunal.setId(Long.parseLong(attributes.getValue("id")));
                tribunal.setNombre(attributes.getValue("nombre"));
            }
        }
        if(fixture.equalsIgnoreCase("estado_visual")) {
            if (qName.equalsIgnoreCase("registrosTabla")) {
                estadoVisual = new EstadoVisual();
                estadoVisual.setIdEstadoVisual(Integer.parseInt(attributes.getValue("id")));
                String[] params= attributes.getValue("nombre").split("|");
                estadoVisual.setNombre(params[0]);
                estadoVisual.setRespuestaBinaria(Boolean.parseBoolean(params[1]));
            }
        }
    }

    public void characters(char[] ch, int start, int length)
            throws SAXException {
        tempVal = new String(ch, start, length);
    }

    public void endElement(String uri, String localName, String qName)
            throws SAXException {

        if (qName.equalsIgnoreCase("grueros"))
            objects.add(gruero);
        if (qName.equalsIgnoreCase("comunas"))
            objects.add(comuna);
        if (qName.equalsIgnoreCase("instituciones"))
            objects.add(comuna);
        if (qName.equalsIgnoreCase("marcas"))
            objects.add(marca);
        if (qName.equalsIgnoreCase("motivos"))
            objects.add(motivo);
        if (qName.equalsIgnoreCase("tiposVehiculo"))
            objects.add(tipoVehiculo);
        if (qName.equalsIgnoreCase("tribunales"))
            objects.add(tribunal);
        if (qName.equalsIgnoreCase("estadosVisuales"))
            objects.add(estadoVisual);
    }
}

