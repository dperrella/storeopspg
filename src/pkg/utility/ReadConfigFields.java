package pkg.utility;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import pkg.beans.Rfid_config;

public class ReadConfigFields {

	public static List<Rfid_config> getConfigFields(String fld_config) {
		
		List<Rfid_config> config = new ArrayList<Rfid_config>();
		config.clear();
		
		try {
		
			if(fld_config!=null) {
				
				JSONParser parser = new JSONParser();
				Object _obj = parser.parse(fld_config);
		
				JSONObject jsonObject = (JSONObject) _obj;
				System.out.println(jsonObject);
		
				// loop array
				JSONArray msg = (JSONArray) jsonObject.get("config");
				Iterator<String> iterator = msg.iterator();
				int count = 0;
				while (iterator.hasNext()) {
				    
					count+=1;
					Object obj2 = iterator.next();
				    
				    JSONObject jsonObject2 = (JSONObject) obj2;
				    //System.out.println(jsonObject2);
				    
				    String name = (String) jsonObject2.get("name");
				    //System.out.println(name);
				    
				    String type = (String) jsonObject2.get("type");
				    //System.out.println(type);
		
				    String label = (String) jsonObject2.get("label");
				    //System.out.println(label);
				    
				    String value = (String) jsonObject2.get("value");
				    //System.out.println(value);
				    
				    String cmp1="";
				    String cmp2="";
				    if(type.equalsIgnoreCase("text"))
				    	cmp1 = "selected";
				    else
				    	cmp2 = "selected";
				    
				    config.add(new Rfid_config(name,type,label,value,cmp1,cmp2,""+count,""));
				    
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return config;
	}
	
	public static String getConfig(HttpServletRequest req) {
		
		String config = "";
		
		JSONObject responseJson = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		
		try {
			String[] nrrig = req.getParameterValues("config_nrrig");
			if(nrrig!=null) {
				// prendiamo tutti i valori dell'input multiplo
				if (nrrig.length>0) {
					
					String[] cd = req.getParameterValues("config_name");
					String config_name = "";
					String config_type = "";
					String config_value = "";
					String config_label = "";
					
					if(cd!=null) {
						
						// riversiamo l'array
						for(int i=0; i<cd.length; i++) {
						
							config_name = req.getParameterValues("config_name")[i];
							config_type = req.getParameterValues("config_type")[i];
							config_value = req.getParameterValues("config_value")[i];
							
							if(config_name.length()>0) {
								System.out.println(config_name);
								System.out.println(config_type);
								System.out.println(config_value);
								System.out.println("---");
								
								JSONObject jsonObj = new JSONObject();
								jsonObj.put("name", config_name.trim().replaceAll(" ", "_"));
								jsonObj.put("type", config_type.trim());
								jsonObj.put("label", config_name.trim());
								jsonObj.put("value", config_value.trim());
								jsonArray.add(jsonObj);
								
							}
							
						}
						
					}
					
				}
				
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		responseJson.put("config", jsonArray);
		//System.out.println(responseJson.toString());
		
		return responseJson.toString();
		
	}
	
	public static List<Rfid_config> getConfigFieldsByName(String fld_config, String jsonName) {
		
		List<Rfid_config> config = new ArrayList<Rfid_config>();
		config.clear();
		
		try {
		
			if(fld_config!=null) {
				
				JSONParser parser = new JSONParser();
				Object _obj = parser.parse(fld_config);
		
				JSONObject jsonObject = (JSONObject) _obj;
				System.out.println(jsonObject);
		
				// loop array
				JSONArray msg = (JSONArray) jsonObject.get(jsonName);
				Iterator<String> iterator = msg.iterator();
				int count = 0;
				while (iterator.hasNext()) {
				    
					count+=1;
					Object obj2 = iterator.next();
				    
				    JSONObject jsonObject2 = (JSONObject) obj2;
				    //System.out.println(jsonObject2);
				    
				    String name = (String) jsonObject2.get("name");
				    //System.out.println(name);
				    
				    String type = (String) jsonObject2.get("type");
				    //System.out.println(type);
		
				    String label = (String) jsonObject2.get("label");
				    //System.out.println(label);
				    
				    String value = (String) jsonObject2.get("value");
				    //System.out.println(value);
				    
				    String cmp1="";
				    String cmp2="";
				    if(type.equalsIgnoreCase("text"))
				    	cmp1 = "selected";
				    else
				    	cmp2 = "selected";
				    
				    config.add(new Rfid_config(name,type,label,value,cmp1,cmp2,""+count,""));
				    
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return config;
	}
		
}
