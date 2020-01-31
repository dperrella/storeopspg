package pkg.utility;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import pkg.beans.Rfid_extra;

public class ReadExtraFields {

	public static List<Rfid_extra> getExtraFields(String fld_extra) {
		
		List<Rfid_extra> extra = new ArrayList<Rfid_extra>();
		extra.clear();
		
		try {
			
			if(fld_extra!=null) {
				
				JSONParser parser = new JSONParser();
				Object _obj = parser.parse(fld_extra);
		
				JSONObject jsonObject = (JSONObject) _obj;
				System.out.println(jsonObject);
		
				// loop array
				JSONArray msg = (JSONArray) jsonObject.get("extra");
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
				    
				    extra.add(new Rfid_extra(name,type,label,value,cmp1,cmp2,""+count,""));
				    
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return extra;
	}
	
	public static String getExtra(HttpServletRequest req) {
		
		String extra = "";
		
		JSONObject responseJson = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		
		try {
			String[] nrrig = req.getParameterValues("nrrig");
			if(nrrig!=null) {
				// prendiamo tutti i valori dell'input multiplo
				if (nrrig.length>0) {
					
					String[] cd = req.getParameterValues("extra_name");
					String extra_name = "";
					String extra_type = "";
					String extra_value = "";
					String extra_label = "";
					
					if(cd!=null) {
						
						// riversiamo l'array
						for(int i=0; i<cd.length; i++) {
						
							extra_name = req.getParameterValues("extra_name")[i];
							extra_type = req.getParameterValues("extra_type")[i];
							
							extra_value = req.getParameterValues("extra_value")[i];
							
							if(extra_name.length()>0) {
								System.out.println(extra_name);
								System.out.println(extra_type);
								System.out.println(extra_value);
								System.out.println("---");
								
								JSONObject jsonObj = new JSONObject();
								jsonObj.put("name", extra_name.trim().replaceAll(" ", "_"));
								jsonObj.put("type", extra_type.trim());
								jsonObj.put("label", extra_name.trim());
								jsonObj.put("value", extra_value.trim());
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
		
		responseJson.put("extra", jsonArray);
		//System.out.println(responseJson.toString());
		
		return responseJson.toString();
		
	}
		
	
	
	// --------
	
	public static List<Rfid_extra> getExtraFields_doc_top(String fld_extra) {
		
		List<Rfid_extra> extra = new ArrayList<Rfid_extra>();
		extra.clear();
		
		try {
			
			if(fld_extra!=null) {
				
				JSONParser parser = new JSONParser();
				Object _obj = parser.parse(fld_extra);
		
				JSONObject jsonObject = (JSONObject) _obj;
				System.out.println(jsonObject);
		
				// loop array
				JSONArray msg = (JSONArray) jsonObject.get("extra_doc_top");
				Iterator<String> iterator = msg.iterator();
				int count = 0;
				while (iterator.hasNext()) {
				    
					count+=1;
					Object obj2 = iterator.next();
				    
				    JSONObject jsonObject2 = (JSONObject) obj2;
				    //System.out.println(jsonObject2);
				    
				    String name = (String) jsonObject2.get("name_doc_top");
				    //System.out.println(name);
				    
				    String type = (String) jsonObject2.get("type_doc_top");
				    //System.out.println(type);
		
				    String label = (String) jsonObject2.get("label_doc_top");
				    //System.out.println(label);
				    
				    String value = (String) jsonObject2.get("value_doc_top");
				    //System.out.println(value);
				    
				    String cmp1="";
				    String cmp2="";
				    if(type.equalsIgnoreCase("text"))
				    	cmp1 = "selected";
				    else
				    	cmp2 = "selected";
				    
				    extra.add(new Rfid_extra(name,type,label,value,cmp1,cmp2,""+count,""));
				    
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return extra;
	}
	
	public static String getExtra_doc_top(HttpServletRequest req) {
		
		String extra = "";
		
		JSONObject responseJson = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		
		try {
			String[] nrrig = req.getParameterValues("nrrig_doc_top_");
			if(nrrig!=null) {
				// prendiamo tutti i valori dell'input multiplo
				if (nrrig.length>0) {
					
					String[] cd = req.getParameterValues("extra_doc_top_name");
					String extra_name = "";
					String extra_type = "";
					String extra_value = "";
					String extra_label = "";
					
					if(cd!=null) {
						
						// riversiamo l'array
						for(int i=0; i<cd.length; i++) {
						
							extra_name = req.getParameterValues("extra_doc_top_name")[i];
							extra_type = req.getParameterValues("extra_doc_top_type")[i];
							
							extra_value = req.getParameterValues("extra_doc_top_value")[i];
							
							if(extra_name.length()>0) {
								System.out.println(extra_name);
								System.out.println(extra_type);
								System.out.println(extra_value);
								System.out.println("---");
								
								JSONObject jsonObj = new JSONObject();
								jsonObj.put("name_doc_top", extra_name.trim().replaceAll(" ", "_"));
								jsonObj.put("type_doc_top", extra_type.trim());
								jsonObj.put("label_doc_top", extra_name.trim());
								jsonObj.put("value_doc_top", extra_value.trim());
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
		
		responseJson.put("extra_doc_top", jsonArray);
		//System.out.println(responseJson.toString());
		
		return responseJson.toString();
		
	}
	
	
	
	// --------

	public static List<Rfid_extra> getExtraFields_doc_detail(String fld_extra) {
		
		List<Rfid_extra> extra = new ArrayList<Rfid_extra>();
		extra.clear();
		
		try {
			
			if(fld_extra!=null) {
				
				JSONParser parser = new JSONParser();
				Object _obj = parser.parse(fld_extra);
		
				JSONObject jsonObject = (JSONObject) _obj;
				System.out.println(jsonObject);
		
				// loop array
				JSONArray msg = (JSONArray) jsonObject.get("extra_doc_detail");
				Iterator<String> iterator = msg.iterator();
				int count = 0;
				while (iterator.hasNext()) {
				    
					count+=1;
					Object obj2 = iterator.next();
				    
				    JSONObject jsonObject2 = (JSONObject) obj2;
				    //System.out.println(jsonObject2);
				    
				    String name = (String) jsonObject2.get("name_doc_detail");
				    //System.out.println(name);
				    
				    String type = (String) jsonObject2.get("type_doc_detail");
				    //System.out.println(type);
		
				    String label = (String) jsonObject2.get("label_doc_detail");
				    //System.out.println(label);
				    
				    String value = (String) jsonObject2.get("value_doc_detail");
				    //System.out.println(value);
				    
				    String cmp1="";
				    String cmp2="";
				    if(type.equalsIgnoreCase("text"))
				    	cmp1 = "selected";
				    else
				    	cmp2 = "selected";
				    
				    extra.add(new Rfid_extra(name,type,label,value,cmp1,cmp2,""+count,""));
				    
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return extra;
	}
	
	public static String getExtra_doc_detail(HttpServletRequest req) {
		
		String extra = "";
		
		JSONObject responseJson = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		
		try {
			String[] nrrig = req.getParameterValues("nrrig_doc_detail_");
			if(nrrig!=null) {
				// prendiamo tutti i valori dell'input multiplo
				if (nrrig.length>0) {
					
					String[] cd = req.getParameterValues("extra_doc_detail_name");
					String extra_name = "";
					String extra_type = "";
					String extra_value = "";
					String extra_label = "";
					
					if(cd!=null) {
						
						// riversiamo l'array
						for(int i=0; i<cd.length; i++) {
						
							extra_name = req.getParameterValues("extra_doc_detail_name")[i];
							extra_type = req.getParameterValues("extra_doc_detail_type")[i];
							extra_value = req.getParameterValues("extra_doc_detail_value")[i];
							
							if(extra_name.length()>0) {
								System.out.println(extra_name);
								System.out.println(extra_type);
								System.out.println(extra_value);
								System.out.println("---");
								
								JSONObject jsonObj = new JSONObject();
								jsonObj.put("name_doc_detail", extra_name.trim().replaceAll(" ", "_"));
								jsonObj.put("type_doc_detail", extra_type.trim());
								jsonObj.put("label_doc_detail", extra_name.trim());
								jsonObj.put("value_doc_detail", extra_value.trim());
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
		
		responseJson.put("extra_doc_detail", jsonArray);
		//System.out.println(responseJson.toString());
		
		return responseJson.toString();
		
	}
	
	
}
