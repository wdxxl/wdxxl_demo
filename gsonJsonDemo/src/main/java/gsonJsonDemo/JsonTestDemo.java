package gsonJsonDemo;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;

public class JsonTestDemo {

	public static void main(String[] args) throws IOException {
		JSONObject jsonObj = new JSONObject(getFileContent());
		Iterator<String> temp = jsonObj.keys();
		while (temp.hasNext()) {
			String key = temp.next();
			Object jsonObjKey = jsonObj.get(key);
			if(jsonObjKey instanceof JSONArray){
				JSONArray jsonArray = (JSONArray)jsonObjKey;
				for(Object obj :jsonArray){
					System.out.println(key + " -2:2- " + obj.toString());
				}
			}else{
				System.out.println(key + " -1:1- " + jsonObjKey);
			}

		}
	}

	private static String getFileContent() throws IOException {
		InputStream is = new FileInputStream(System.getProperty("user.dir") + "/json/source.json");
		BufferedReader buf = new BufferedReader(new InputStreamReader(is));
		String line = buf.readLine();
		StringBuilder sb = new StringBuilder();
		while (line != null) {
			sb.append(line).append("\n");
			line = buf.readLine();
		}
		String fileAsString = sb.toString();
		System.out.println("Contents : " + fileAsString);
		return fileAsString;
	}
}
