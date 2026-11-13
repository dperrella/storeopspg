package pkg.utility;

import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.json.JSONException;

import pkg.counterAS.PivotData;
import pkg.properties.ReadProperties;

public class Pivot {
    
    private static final class Row {
        private final String data;
        private final Map<String, Integer> valueByStore = new HashMap<>();

        public Row(String data) {
            this.data = data;
        }

        public void addStore(String store, int value) {
        	valueByStore.merge(store, value, Integer::sum);
        }

        public int getValueFor(String store) {
            return valueByStore.getOrDefault(store, 0);
        }

        public String getData() {
            return data;
        }
    }

    public static class PivotReport {

        private final Map<String, Row> rowsByData = new HashMap<>();
        private SortedSet<String> stores = new TreeSet<>();

        private PivotReport() {}

        private void addData(PivotData d) {
        	rowsByData.computeIfAbsent(d.getData(), Row::new).addStore(d.getStore(), d.getValue());
            stores.add(d.getStore());
        }

        public static PivotReport create(List<PivotData> list) {
            PivotReport report = new PivotReport();
            list.forEach(report::addData);
            return report;
        }

        public String toString() {
            String headers = "Date\t" + String.join("\t", stores);
            String rows =  rowsByData.values()
                                            .stream()
                                            .sorted(Comparator.comparing(Row::getData))
                                            .map(this::rowToString)
                                            .collect(Collectors.joining("\n"));
            return headers + "\n" + rows;
        }

        private String rowToString(Row row) {
            return row.getData() + "\t" +
                stores.stream()
                       .map(store -> Integer.toString(row.getValueFor(store)))
                       .collect(Collectors.joining("\t"));
        }
        
//        public ArrayList<String> toArray() {
//        	
//        	ArrayList<String> arr = new ArrayList<String>();
//        	
//        	String headers = "Date;" + String.join(";", stores);
//        	arr.add(headers);
//        	
//            String rows =  rowsByData.values()
//                                            .stream()
//                                            .sorted(Comparator.comparing(Row::getData))
//                                            .map(this::rowToString)
//                                            .collect(Collectors.joining("\n"));
//            
//            for(String row : rows.split("\n")) {
//            	arr.add(row);
//            }
//            
//            return arr;
//        }
    }


    public static void main(String[] args) throws FileNotFoundException, NumberFormatException, JSONException, ParseException {
        List<PivotData> list = createListOfData();
        PivotReport report = PivotReport.create(list);
        
        String data = report.toString();
        String arr[] = data.split("\n");
        for(String row : arr) {
        	System.out.println(row);
        }
        
    }

    private static List<PivotData> createListOfData() throws FileNotFoundException, NumberFormatException, JSONException, ParseException {
        
    	System.out.println(new Date());
		List<PivotData> reports = new ArrayList<PivotData>();
		
		
		String from = "2020-09-01";
		String to = "2020-09-7";
		String type = "1";
		String timeGrouped = "D";
        
		reports = fillZeroHeatmap(from,to, "IT");
		
		//reports = fillZero(from,to,"1",type,timeGrouped);
		
//		ReadProperties prop = new ReadProperties("connection.properties");
//		String SAAS_endpoint = "https://suiteapis.azurewebsites.net/api/ssas";
//		
//        String jsonParm = "{\"account_id\": \"4650\",\"stores\": [\"1\"],\"kpi\": [\"EAS\"],\"from_date\": \""+from+"\",\"to_date\": \""+to+"\",\"time_grouped\": \""+timeGrouped+"\",\"groupByDoor\" : false}";
//		System.out.println(jsonParm);
//        org.json.JSONObject json = Utility.consumeRest(SAAS_endpoint, "POST", jsonParm, "");
//		
//        System.out.println(new Date());
//		
//        org.json.JSONArray data = json.getJSONArray("listings");
//		
//		SimpleDateFormat iso = new SimpleDateFormat("yyyy-MM-dd");
//		SimpleDateFormat us = new SimpleDateFormat("M/d/yyyy hh:mm:ss aa");
//		
//		//reports = fillZero(from,to,"31,3,27,7,8,25,18,1,6,10,28,5,32,204,202,21,12,9,26,15,29,34,14",type,timeGrouped);
//		reports = fillZero(from,to,"1",type,timeGrouped);
//		
//		System.out.println(new Date());
//		for (int i = 0; i < data.length(); i++) {
//			// type 2
//			
//			if(type.equals("2")) {
//				if(timeGrouped.equals("D"))
//					reports.add(new PivotData(data.getJSONObject(i).getString("storeId"), iso.format(us.parse(data.getJSONObject(i).getString("date"))),(int) data.getJSONObject(i).getLong("value")));
//				else if(timeGrouped.equals("H"))
//					reports.add(new PivotData(data.getJSONObject(i).getString("storeId"), String.format("%02d", data.getJSONObject(i).getInt("time")),(int) data.getJSONObject(i).getLong("value")));
//			}
//			// type 1
//			if(type.equals("1")) {
//				if(timeGrouped.equals("D"))
//					reports.add(new PivotData(iso.format(us.parse(data.getJSONObject(i).getString("date"))), data.getJSONObject(i).getString("storeId"),(int) data.getJSONObject(i).getLong("value")));
//				else if(timeGrouped.equals("H"))
//					reports.add(new PivotData(String.format("%02d", data.getJSONObject(i).getInt("time")), data.getJSONObject(i).getString("storeId"),(int) data.getJSONObject(i).getLong("value")));
//			}
//		}
//		System.out.println(new Date());
        
		
        return reports;
    }
    
    public static List<PivotData> fillZero(String from, String to, String stores, String type, String time) throws ParseException {
    	DateFormat iso = new SimpleDateFormat("yyyy-MM-dd");
    	
    	List<PivotData> reports = new ArrayList<PivotData>();
    	
    	int diff = 0;
    	Calendar c = null;
    	c = Calendar.getInstance(Locale.GERMAN);
    	c.setTime(iso.parse(from));
    	if(time.equals("D")) 
	    	diff = (Utility.getDateDiffDays3(from, to))+1;
	    else if(time.equals("H")) 
    		diff = 24;
    	
		String s[] = stores.split(",");
		switch(type) {
		case "1":
			for(int i=0;i<diff;i++) {
	    		for(String store : s) {
	    			if(time.equals("D"))
	    				reports.add(new PivotData(iso.format(c.getTime()),store,0));
	    			else if(time.equals("H"))
	    				reports.add(new PivotData(String.format("%02d", i),store,0));
	    		}
	    		if(time.equals("D"))
	    			c.add(Calendar.DATE, 1);
	    	}
			break;
		case "2":
			for(String store : s) {
    			for(int i=0;i<diff;i++) {
    				if(time.equals("D"))
    					reports.add(new PivotData(store,iso.format(c.getTime()),0));
    				else if(time.equals("H"))
    					reports.add(new PivotData(store,String.format("%02d", i),0));
    				
    				if(time.equals("D"))
    					c.add(Calendar.DATE, 1);
    			}
    			c.setTime(iso.parse(from));
    		}
			break;	
		}
    	return reports;
    	
    }
    
    public static List<PivotData> fillZeroHeatmap(String from, String to, String lang) throws ParseException, FileNotFoundException {
    	DateFormat iso = new SimpleDateFormat("yyyy-MM-dd");
    	ReadProperties propLang = new ReadProperties(lang+".properties");
		
    	List<PivotData> reports = new ArrayList<PivotData>();
    	
    	int diff = 0;
    	Calendar c = null;
    	c = Calendar.getInstance(Locale.GERMAN);
    	c.setTime(iso.parse(from));
    	diff = (Utility.getDateDiffDays3(from, to))+1;
	    
    	int diff2 = 24;
    	
		for(int i=0;i<diff;i++) {
    		for(int h=0;h<diff2;h++) {
    			reports.add(new PivotData(String.format("%02d",c.get(Calendar.DAY_OF_WEEK)),String.format("%02d",h),0));
    		}
    		c.add(Calendar.DATE, 1);
    	}
	
    	return reports;
    	
    }
//    public static List<PivotData> fillValue(String x, String y, String z) throws ParseException {
//    	
//    	List<PivotData> reports = new ArrayList<PivotData>();
//    	
//    	String xx[] = x.split(",");
//		String yy[] = y.split(",");
//		String zz[] = z.split(",");
//		for(String xxx : xx) {
//			for(String yyy : yy) {
//				for(String zzz : zz) {
//					reports.add(new PivotData(xxx,yyy,Integer.parseInt(zzz)));
//				}
//			}
//		}
//    	return reports;
//    	
//    }
}
