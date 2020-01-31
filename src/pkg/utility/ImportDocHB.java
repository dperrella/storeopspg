package pkg.utility;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

import pkg.properties.ReadProperties;

public class ImportDocHB {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Connection conn = null;
		Statement st = null;
		
		String sql = "";
		try {
			
			ReadProperties prop = new ReadProperties("connection.properties");
			String usr = "postgres";
			String psw = "Tammurriata$";
			String url = prop.get("urlAdmin");
			String drv = prop.get("drv");
			String database  = "smartrfid2";
			
			Class.forName(drv);
			conn = DriverManager.getConnection(url+database, usr, psw);
			st = conn.createStatement();
			
			String csvFile = "/Users/danieleperrella/hb/doc_hb2.csv";
	        BufferedReader br = null;
	        String line = "";
	        String cvsSplitBy = ";";

	        try {

	            br = new BufferedReader(new FileReader(csvFile));
	            while ((line = br.readLine()) != null) {

	                // use comma as separator
	                String[] sku = line.split(cvsSplitBy);
	                
	                for(int i=0;i<Integer.parseInt(sku[0]);i++) {
	                	
	                	sql = "insert into documents_detail "+
	                		"(sku, iddoc) "+
	                		"values "+
	                		"('"+sku[1]+"', 1) ";
	                	st.executeUpdate(sql);
	                	
	                }

	                //System.out.println("sku [qta= " + sku[0] + " , sku=" + sku[1] + "]");

	            }

	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            if (br != null) {
	                try {
	                    br.close();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	        }
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(st!=null)
					st.close();
				if(conn!=null)
					conn.close();
			}
			catch(Exception e) {}
		}
		
	}

}
