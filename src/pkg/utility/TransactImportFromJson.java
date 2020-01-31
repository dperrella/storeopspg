package pkg.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import pkg.beans.Rfid_documents;
import pkg.beans.Rfid_scanner;
import pkg.beans.Rfid_scannerdetail;

public class TransactImportFromJson {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String idflow = "9";
		String idstep = "10";
		
		//importa(idflow, idstep, "9");
		importa(idflow, idstep, "10");
		importa(idflow, idstep, "11");
	}	
	
	private static void importa(String idflow, String idstep, String docnum) {
		
		Connection conn = null;
		Statement st = null;
		
		try {
			
			conn=Utility.getConnRfid();
			st = conn.createStatement();
			// Testata documento
			Rfid_documents doc = new Rfid_documents();
			doc.setId_flow(Integer.parseInt(idflow));
			doc.setId_step(Integer.parseInt(idstep));
			doc.setDocnum(Integer.parseInt(docnum));
			doc.selectRowFromDocnum(conn);
			
			String iddoc = doc.getIddoc();
			System.out.println("iddoc:"+iddoc);
			
			JSONParser parser = new JSONParser();
			Object obj = null;
			// Legge il file
			try {
				obj = parser.parse(new FileReader("/Users/danieleperrella/Desktop/"+docnum+".json"));
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			JSONObject responseJson = (JSONObject) obj;
			
			//String strJson = readJson("/Users/danieleperrella/Desktop/"+docnum+".json");
			//JSONParser parser = new JSONParser();
			//Object _obj = parser.parse(strJson);
			//JSONObject responseJson = (JSONObject) _obj;
			
			JSONArray data = (JSONArray) responseJson.get("data");
			if(data.size()==0) {
				System.out.println("lista vuota");
			}
			else {
				System.out.println("lista piena, scrivo scannerdetail...");
				
				Rfid_scanner scan = new Rfid_scanner();
				scan.setIddoc(iddoc);
				scan.setId_flow(Integer.parseInt(idflow));
				scan.setId_step(Integer.parseInt(idstep));
				scan.insert(conn);
				
				int idscan = scan.getIdscan();
				System.out.println("idscan:"+idscan);
				
				Iterator<String> iterator = data.iterator();
				
				Rfid_scannerdetail scandet = new Rfid_scannerdetail();
				scandet.setIdscan(idscan);
				
				while (iterator.hasNext()) {
				    
					Object obj2 = iterator.next();
				    JSONObject jsonObject2 = (JSONObject) obj2;
				    
				    scandet.setBarcode((String) jsonObject2.get("skuid"));
				    scandet.setEpc((String) jsonObject2.get("epc"));
				    scandet.setNrscan(1);
				    scandet.insert(conn);
				    
				}
				
				
				ResultSet rs=st.executeQuery("select * from scan_clean("+idscan+", '')");
				rs.close();
				
				// passa lo stato del documento a 2
				doc.setStatus(2);
				doc.updateStatus(conn);
				
				
			}
			
			
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(conn!=null)
					conn.close();
				if(st!=null)
					st.close();
			}
			catch(Exception e) {}
		}
		
		
		
	}
	
	private static String readJson(String nomeFile) {
		
		File file = new File(nomeFile);

        FileInputStream fis = null;
        String str = "";

        try {
            fis = new FileInputStream(file);
            int content;
            while ((content = fis.read()) != -1) {
                // convert to char and display it
                str += (char) content;
            }


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null)
                    fis.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        
        return str;
        
		
	}
	
}
