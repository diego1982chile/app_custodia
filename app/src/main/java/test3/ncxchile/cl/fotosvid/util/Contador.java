package test3.ncxchile.cl.fotosvid.util;

import android.content.SharedPreferences;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

/**
 * Created by Martin on 25-10-13.
 */
public class Contador {

    private static Contador instance = null;
    private HashMap<String, Integer> contadores;
    private String ordenServicio;
    private SharedPreferences props;


    private Contador(String ordenServicio) {
        this.props = ApplicationContext.getContext().getSharedPreferences(ImagenesCMVRCConstants.FICHERO, 0);
        this.ordenServicio = ordenServicio;
        contadores = this.load();
    }

    public static Contador getInstance(String ordenServicio) {
        if (instance == null || !instance.getOrdenServicio().equals(ordenServicio)) {
            instance = new Contador(ordenServicio);
        }
        return instance;
    }


    private String getOrdenServicio() {
        return ordenServicio;
    }

    public synchronized Integer getNextVale(String categoria) {
        if (!contadores.containsKey(categoria)) {
            contadores.put(categoria, 0);
        }
        Integer nextValue = contadores.get(categoria);
        contadores.put(categoria, nextValue + 1);
        try {
            this.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return nextValue + 1;
    }

    public synchronized Integer getValue(String categoria) {

        if (!contadores.containsKey(categoria)) {
            contadores.put(categoria, 0);
        }
        return contadores.get(categoria);
    }

    public synchronized void preValue(String categoria) {

        if (!contadores.containsKey(categoria)) {
            contadores.put(categoria, 0);
        }
        Integer setValue = contadores.get(categoria);
        contadores.put(categoria, setValue - 1);
    }

    private void save() throws IOException {

        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(byteOut);
        out.writeObject(this.contadores);
        out.flush();
        out.close();
        byteOut.flush();
        byte[] bytes = byteOut.toByteArray();
        byteOut.close();
        byte[] encoded = Base64.encode(bytes, Base64.DEFAULT);
        String base64 = new String(encoded);
        SharedPreferences.Editor editor = this.props.edit();
        editor.putString(this.ordenServicio, base64);
        editor.commit();

    }

    private HashMap<String, Integer> load() {

        String base64 = this.props.getString(ordenServicio, "");
        if (base64.trim().length() == 0) {
            return new HashMap<String, Integer>();
        }
        byte[] decoded = Base64.decode(base64.getBytes(), Base64.DEFAULT);
        try {
            ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(decoded));

            HashMap<String, Integer> result = (HashMap<String, Integer>) in.readObject();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return new HashMap<String, Integer>();
        }
    }
}
