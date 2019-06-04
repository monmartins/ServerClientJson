package Serializable;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;


public class CJson {
	public String serialize(JSONObject obj) {
		obj.put("name", "foo");
		obj.put("num", new Integer(100));
		obj.put("balance", new Double(1000.21));
		obj.put("is_vip", new Boolean(true));
		return obj.toString();
		
	}

	public JSONArray deserialize(String path) {
		JSONTokener tok;
		String content;
		JSONArray objArray = null;
		try {
			content = new String ( Files.readAllBytes( Paths.get(path) ) );
//				tok = new JSONTokener();
			objArray = new JSONArray(content);
			return objArray;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return objArray;
	}
}
