package pkg.utility;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.json.JSONException;

import pkg.counterAS.PivotData;
import pkg.properties.ReadProperties;

public class Pivot_BKP20201214 {
    
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
        
        
        //System.out.println(report);
//        for(String row : report.toArray()) {
//        	System.out.println(row);
//        }
        
        
    }

    private static List<PivotData> createListOfData() throws FileNotFoundException, NumberFormatException, JSONException, ParseException {
        List<PivotData> reports = new ArrayList<PivotData>();

//        reports.add(new PivotData("04/12/2018","Mr.Bob", 1));
//        reports.add(new PivotData("04/12/2018","Mr.Jhon", 3));
//        reports.add(new PivotData("04/12/2018","Mr.Bob", 4));
//        reports.add(new PivotData("05/12/2018","Mr.Jhon", 4));
//        reports.add(new PivotData("06/12/2018","Mr.Bob", 0));
//        reports.add(new PivotData("06/12/2018","Mr.Jhon", 1));
//        reports.add(new PivotData("07/12/2018","Mr.Bob",0));
//        reports.add(new PivotData("07/12/2018","Mr.Smith",9));

        ReadProperties prop = new ReadProperties("connection.properties");
		String SAAS_endpoint = prop.get("saas_endpoint")+"api/counter";
		
        String jsonParm = "{\"account_id\": \"4650\",\"stores\": [\"31\",\"3\",\"27\",\"7\",\"8\",\"25\",\"18\",\"1\",\"6\",\"10\",\"28\",\"5\",\"32\",\"204\",\"202\",\"21\",\"12\",\"9\",\"26\",\"15\",\"29\",\"34\"],\"kpi\": [\"EAS\"],\"from_date\": \"2020-11-01\",\"to_date\": \"2020-12-09\",\"time_grouped\": \"D\",\"groupByDoor\" : false}";
		System.out.println(jsonParm);
        org.json.JSONObject json = Utility.consumeRest(SAAS_endpoint, "POST", jsonParm, "");
		org.json.JSONObject result = json.getJSONObject("Result");
		org.json.JSONArray data = result.getJSONArray("Dt");
		
		SimpleDateFormat iso = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat us = new SimpleDateFormat("M/d/yyyy");
		
		System.out.println(new Date());
		for (int i = 0; i < data.length(); i++) {
			//org.json.JSONObject rows = data.getJSONObject(i);
			
//			String value = (Utility.noNull(""+data.getJSONObject(i).get("value"))) == "" ? "0" : ""+data.getJSONObject(i).get("value");
//			value = Utility.noNull(value);
//			if(value.length()==0)
//				value = "0";
			reports.add(new PivotData(data.getJSONObject(i).getString("store_name"), iso.format(us.parse(data.getJSONObject(i).getString("date"))),Integer.parseInt((Utility.noNull(""+data.getJSONObject(i).get("value"))) == "" ? "0" : ""+data.getJSONObject(i).get("value"))));
			//reports.add(new PivotData(""+data.getJSONObject(i).get("door_description"), iso.format(us.parse(data.getJSONObject(i).getString("date"))),Integer.parseInt((Utility.noNull(""+data.getJSONObject(i).get("value"))) == "" ? "0" : ""+data.getJSONObject(i).get("value"))));
			System.out.println(new Date());
		}
		System.out.println(new Date());
        
        return reports;
    }
}
