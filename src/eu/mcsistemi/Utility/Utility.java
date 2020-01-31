package eu.mcsistemi.Utility;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class Utility {
	
	public static String getStringFromJson(JSONObject jsonObj, String key) {
		if (jsonObj.has(key))
			return jsonObj.getString(key);
		else
			return null;
	}
	
	public static Object getIntFromJson(JSONObject jsonObj, String key) {
		if (jsonObj.has(key))
			return jsonObj.getInt(key);
		else
			return null;
	}
	
	public static Double getDoubleFromJson(JSONObject jsonObj, String key) {
		if (jsonObj.has(key))
			return jsonObj.getDouble(key);
		else
			return null;
	}
	
	public static Object getBooleanFromJson(JSONObject jsonObj, String key) {
		if (jsonObj.has(key))
			return jsonObj.getBoolean(key);
		else
			return null;
	}
	
	public static JSONArray getJsonArrayFromJson(JSONObject jsonObj, String key) {
		if (jsonObj.has(key))
			return jsonObj.getJSONArray(key);
		else
			return null;
	}
	
	public static JSONObject getJsonObjectFromJson(JSONObject jsonObj, String key) {
		if (jsonObj.has(key))
			return jsonObj.getJSONObject(key);
		else
			return null;
	}
	
	public static ArrayList<String> getStringArrayFromJson(JSONObject jsonObj, String key){
		ArrayList<String> arrayListToReturn = new ArrayList<>();
		if (jsonObj.has(key)) {
			JSONArray arrJson = jsonObj.getJSONArray(key);
			for(int i = 0; i < arrJson.length(); i++)
				arrayListToReturn.add(arrJson.getString(i));
		}
		return arrayListToReturn;
	}
	
	public static ArrayList<Integer> getIntegerArrayFromJson(JSONObject jsonObj, String key){
		ArrayList<Integer> arrayListToReturn = new ArrayList<>();
		if (jsonObj.has(key)) {
			JSONArray arrJson = jsonObj.getJSONArray(key);
			for(int i = 0; i < arrJson.length(); i++)
				arrayListToReturn.add(arrJson.getInt(i));
		}
		return arrayListToReturn;
	}
	
	public static ArrayList<Double> getDoubleArrayFromJson(JSONObject jsonObj, String key){
		ArrayList<Double> arrayListToReturn = new ArrayList<>();
		if (jsonObj.has(key)) {
			JSONArray arrJson = jsonObj.getJSONArray(key);
			for(int i = 0; i < arrJson.length(); i++)
				arrayListToReturn.add(arrJson.getDouble(i));
		}
		return arrayListToReturn;
	}
	
	public static ArrayList<Boolean> getBooleanArrayFromJson(JSONObject jsonObj, String key){
		ArrayList<Boolean> arrayListToReturn = new ArrayList<>();
		if (jsonObj.has(key)) {
			JSONArray arrJson = jsonObj.getJSONArray(key);
			for(int i = 0; i < arrJson.length(); i++)
				arrayListToReturn.add(arrJson.getBoolean(i));
		}
		return arrayListToReturn;
	}
	
	public static JSONObject getPlaceAnswer(String placeId) throws IOException{
		System.out.println(placeId);
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(Constants.detailCallUrl + placeId + Constants.apiKey);
		System.out.println(Constants.detailCallUrl + placeId + Constants.apiKey);
		CloseableHttpResponse response = httpclient.execute(httpGet);
	    HttpEntity entity = response.getEntity();
	    String content = EntityUtils.toString(entity);
	    //System.out.println("Http answer" + content);
	    JSONObject answer = new JSONObject(content);
	    JSONObject placesAnswer = Utility.getJsonObjectFromJson(answer, "result");
	    EntityUtils.consume(entity);
	    response.close();
	    return placesAnswer;
	}
	
	public static JSONObject getPlacesFromText(String text) throws IOException{
		text = text.replace(" ", "+");
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(Constants.textCallUrl + text + Constants.apiKey);
		System.out.println(Constants.textCallUrl + text + Constants.apiKey);
		CloseableHttpResponse response = httpclient.execute(httpGet);
	    HttpEntity entity = response.getEntity();
	    String content = EntityUtils.toString(entity);
	    //System.out.println("Http answer" + content);
	    JSONObject answer = new JSONObject(content);
	    EntityUtils.consume(entity);
	    response.close();
	    return answer;
	}

}
