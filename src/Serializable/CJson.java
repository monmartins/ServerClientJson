package Serializable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONArray;
import org.json.JSONObject;


public class CJson {
	public String serialize(JSONObject obj) {
		return "";
		
	}

	public JSONArray deserialize(String path) {
		String content;
		JSONArray objArray = null;
		try {
			content = new String ( Files.readAllBytes( Paths.get(path) ) );
			objArray = new JSONArray(content);
			return objArray;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return objArray;
	}
}
