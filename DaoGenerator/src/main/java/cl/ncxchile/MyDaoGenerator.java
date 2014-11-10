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

        addTareaActaAccion(schema);

        //addCustomerOrder(schema);

        addMotivo(schema);
        addMotivoFiscalia(schema);
        addTipoVehiculo(schema);

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

    private static void addTareaActaAccion(Schema schema) {

        Entity tarea = schema.addEntity("Tarea");
        tarea.addIdProperty();

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
