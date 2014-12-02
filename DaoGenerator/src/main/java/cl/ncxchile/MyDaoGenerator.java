package cl.ncxchile;

import java.io.IOException;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;
import de.greenrobot.daogenerator.ToMany;

public class MyDaoGenerator {

    public static void main(String args[]) throws Exception {
        Schema schema = new Schema(3, "co.woofy.upload");

        /*
        Entity upload = schema.addEntity("Upload");
        upload.addIdProperty();
        upload.addStringProperty("text");
        */

        /*
        addTareaActaAccion(schema);

        //addCustomerOrder(schema);

        addMotivo(schema);
        addMotivoFiscalia(schema);
        addTipoVehiculo(schema);*/

        //addVehiculoDetalleEstadoVisual(schema);

        //addCorreosTelefonosPersona(schema);

        //addVehiculoDataClientePersona(schema);

        //addUserName(schema);

        addTareaActaAccion(schema);

        new DaoGenerator().generateAll(schema, "upload/src-gen");
    }

    private static void addMotivoFiscalia(Schema schema) {
        Entity motivoFiscalia = schema.addEntity("MotivoFiscalia");
        motivoFiscalia.addIdProperty();
        motivoFiscalia.addStringProperty("nombre");
    }

    private static void addMotivo(Schema schema) {
        Entity motivo = schema.addEntity("Motivo");
        motivo.addIdProperty();
        motivo.addStringProperty("nombre");
    }

    private static void addTipoVehiculo(Schema schema) {
        Entity tipoVehiculo = schema.addEntity("TipoVehiculo");
        tipoVehiculo.addIdProperty();
        tipoVehiculo.addStringProperty("nombre");
    }

    private static void addVehiculoDetalleEstadoVisual(Schema schema) {

        Entity vehiculo = schema.addEntity("Vehiculo");
        vehiculo.addIdProperty();

        Entity detalleEstadoVisual = schema.addEntity("DetalleEstadoVisual");
        detalleEstadoVisual.addIdProperty();
        detalleEstadoVisual.addBooleanProperty("valor");
        detalleEstadoVisual.addStringProperty("observacion");

        Property idVehiculo = detalleEstadoVisual.addLongProperty("idVehiculo").notNull().getProperty();
        detalleEstadoVisual.addToOne(vehiculo, idVehiculo);

        ToMany vehiculoToDetalleEstadoVisual = vehiculo.addToMany(detalleEstadoVisual, idVehiculo);
        vehiculoToDetalleEstadoVisual.setName("detalleEstadoVisual");
        vehiculoToDetalleEstadoVisual.orderAsc(idVehiculo);
    }

    private static void addCorreosTelefonosPersona(Schema schema) {
        Entity persona = schema.addEntity("Persona");
        persona.addIdProperty();

        Entity correos = schema.addEntity("Correos");
        correos.addIdProperty();
        correos.addStringProperty("Email");

        Property idPersona = correos.addLongProperty("correosID").notNull().getProperty();
        correos.addToOne(persona, idPersona);

        ToMany personaToCorreos = persona.addToMany(correos, idPersona);
        personaToCorreos.setName("correos");
        personaToCorreos.orderAsc(idPersona);

        Entity telefonos = schema.addEntity("Telefonos");
        telefonos.addIdProperty();
        telefonos.addStringProperty("Email");

        Property idPersona2 = telefonos.addLongProperty("telefonosID").notNull().getProperty();
        telefonos.addToOne(persona, idPersona2);

        ToMany personaToTelefonos = persona.addToMany(telefonos, idPersona2);
        personaToTelefonos.setName("telefonos");
        personaToTelefonos.orderAsc(idPersona2);
    }

    private static void addVehiculoDataClientePersona(Schema schema) {

        Entity vehiculoData = schema.addEntity("VehiculoData");
        vehiculoData.addIdProperty();

        Entity especias = schema.addEntity("Especias");
        especias.addIdProperty();
        especias.addStringProperty("nombre");

        Property especiasID = especias.addLongProperty("EspeciasID").notNull().getProperty();
        especias.addToOne(vehiculoData, especiasID);

        Entity cliente = schema.addEntity("Cliente");
        cliente.addIdProperty();
        cliente.addStringProperty("Licencia");

        Entity persona = schema.addEntity("Persona");
        persona.addIdProperty();

        Property idPersona = cliente.addLongProperty("personaID").notNull().getProperty();
        cliente.addToOne(persona, idPersona);

        Property idVehiculoData = cliente.addLongProperty("ClientePropietarioID").notNull().getProperty();
        cliente.addToOne(vehiculoData, idVehiculoData);

        ToMany vehicuoDataToCliente = vehiculoData.addToMany(cliente, idVehiculoData);
        vehicuoDataToCliente.setName("ClientePropietario");
        vehicuoDataToCliente.orderAsc(idVehiculoData);
    }

    private static void addActaFirma(Schema schema) {
        Entity firma = schema.addEntity("Firma");
        firma.addIdProperty();
        firma.addStringProperty("FirmaAutoridad");
        firma.addStringProperty("FirmaGruero");

        Entity acta = schema.addEntity("Acta");
        acta.addIdProperty();
        Property firmaId = acta.addLongProperty("FirmaID").notNull().getProperty();
        acta.addToOne(firma, firmaId);
    }

    private static void addUserName(Schema schema) {
        Entity userName = schema.addEntity("UserName");
        userName.addIdProperty();
        userName.addLongProperty("Rut");
        userName.addStringProperty("UserName");
    }

    private static void addTareaActaAccion(Schema schema) {

        Entity tarea = schema.addEntity("Tarea");
        tarea.addIdProperty();
        tarea.addIntProperty("servicio");
        tarea.addStringProperty("fecha");
        tarea.addStringProperty("hora");
        tarea.addDateProperty("timeStamp");
        tarea.addStringProperty("tamano");
        tarea.addStringProperty("direccion");
        tarea.addStringProperty("comuna");
        tarea.addStringProperty("estado");
        tarea.addStringProperty("recinto");
        tarea.addIntProperty("status");

        Entity accion = schema.addEntity("Accion");
        accion.addIdProperty();
        Property timeStamp=accion.addDateProperty("timeStamp").notNull().getProperty();
        accion.addFloatProperty("longitud");
        accion.addFloatProperty("latitud");
        accion.addBooleanProperty("sincronizada");

        Property idTarea = accion.addLongProperty("idTarea").notNull().getProperty();

        accion.addToOne(tarea, idTarea);

        ToMany tareaToAcciones = tarea.addToMany(accion, idTarea);
        tareaToAcciones.setName("acciones");
        tareaToAcciones.orderAsc(timeStamp);

        Entity acta = schema.addEntity("Acta");
        acta.addIdProperty();
        acta.addLongProperty("tareaId").notNull().getProperty();

        Property idActa = accion.addLongProperty("idActa").getProperty();

        acta.addToOne(tarea, idTarea);

        accion.addToOne(acta, idActa);

        ToMany actaToAcciones = acta.addToMany(accion, idActa);
        actaToAcciones.setName("accion");
        actaToAcciones.orderAsc(timeStamp);
    }

    private static void addCustomerOrder(Schema schema) {
        Entity customer = schema.addEntity("Customer");
        customer.addIdProperty();
        customer.addStringProperty("name").notNull();

        Entity order = schema.addEntity("Order");
        order.setTableName("ORDERS"); // "ORDER" is a reserved keyword
        order.addIdProperty();
        Property orderDate = order.addDateProperty("date").getProperty();

        Property customerId = order.addLongProperty("customerId").notNull().getProperty();
        order.addToOne(customer, customerId);

        ToMany customerToOrders = customer.addToMany(order, customerId);
        customerToOrders.setName("orders");
        customerToOrders.orderAsc(orderDate);
    }
}
