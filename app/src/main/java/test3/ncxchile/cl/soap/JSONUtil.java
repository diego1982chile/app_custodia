package test3.ncxchile.cl.soap;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import test3.ncxchile.cl.greenDAO.Acta;


public class JSONUtil {

	private static void changeId(JsonObject obj, String key) {
		String f = key + "ID";
		String id1 = obj.get(f).toString();
		obj.remove(f);

		JsonObject el1 = obj.get(key).getAsJsonObject();
		el1.addProperty("id", id1);
	}

	private static void delete(JsonObject obj, String key) {
		String f = key + "ID";
		String id1 = obj.get(f).toString();
		obj.remove(f);

		JsonObject el1 = obj.get(key).getAsJsonObject();
		el1.remove("id");
	}

	private static String replaceFecha(String json, String key) {
		int pos = json.indexOf("fechaCreacion\":");
		String pre = json.substring(0, pos);
		String post = json.substring(pos);
		int n1 = post.indexOf(':');
		int n2 = post.indexOf(',');

		System.out.println(pre);
		System.out.println(post);

		String timestamp = post.substring(n1 + 1, n2);
		long timeStamp = Long.parseLong(timestamp);
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(timeStamp);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

		String fecha = sdf.format(calendar.getTime());
		System.out.println(timestamp);
		System.out.println(fecha);

		String result = pre + "fechaCreacion\": \"" + fecha + "\""
				+ post.substring(n2);

		System.out.println(result);

		return result;
	}

	public static Acta jsonToActa(String json) {
		json = replaceFecha(json, "fechaCreacion");
		
		System.out.println("JSONUtil.jsontToActa: json origen=" + json);

		Gson gson = new GsonBuilder().setPrettyPrinting()
				.setExclusionStrategies(new JSONExclStrat())
				.setDateFormat("yyyy-MM-dd hh:mm:ss").setPrettyPrinting()
				.serializeNulls().create();
		
		Acta acta = gson.fromJson(json, Acta.class);
		System.out.println("JSONUtil.jsontToActa: Acta = " + acta);
		return acta;
	}


    public static String actaToJson(Acta acta) {

		System.out.println("JSONUtil.jsontToActa: Acta = " + acta);
		
		Gson gson = new GsonBuilder().setPrettyPrinting()
				.setExclusionStrategies(new JSONExclStrat())
				.setDateFormat("yyyy-MM-dd hh:mm:ss").setPrettyPrinting()
				.serializeNulls().create();

		JsonObject obj = gson.toJsonTree(acta).getAsJsonObject();
		delete(obj, "vehiculoData");
		delete(obj, "autoridad");
		delete(obj, "direccion");

		String result = obj.toString();
        JSONObject jsonObj = null;
        try {
            jsonObj = new JSONObject(result);

            String tempResult =jsonObj.toString(5);

            System.out.println("JSONUtil.jsontToActa: json resultado =" + tempResult);


            return result;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
	}

}
