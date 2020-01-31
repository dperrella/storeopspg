package pkg.utility;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Calendar;

public class ImportHR {

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		String idcustomer = "4";
		String file = "/users/danieleperrella/desktop/NUVOLARI.csv";
		
		importa(idcustomer, file);
	}

	private static void importa(String idcustomer, String file) throws SQLException {
		
		Connection conn = null;
		
		try {
			
			conn = Utility.getConnHR();
			conn.setAutoCommit(false);
			
			FileInputStream in = null;
			in = new FileInputStream(file);
			
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
	        String line;
	        
	        String idstore = "";
        	String iddep = "";
        	
	        while ((line = reader.readLine()) != null) {
	        	String[] vals = line.split(";");
	        	
	        	String type = vals[0];
	        	if(type.equals("S")) {
	        		System.out.println("STORE "+vals[1] );
	        		// Inserimento Store / Department + return id stores / id hr_departments
	        		idstore = setStore(idcustomer, vals[1]);
	        		iddep = setDep(conn, idcustomer, vals[1], idstore);
	        	}
	        	else {
	        		System.out.println("EMPLO "+vals[2]+" "+vals[3]);
	        		// Inserimento employee return id hr_employee
	        		String idemp = setEmp(conn, idcustomer, vals[3], vals[2], vals[4], iddep);
	        		
	        		// inserimento in rel_dep_employee
	        		setRel(conn, iddep, idemp);
	        		
	        		try {
	        			String hours = vals[5];
	        			if(hours.length()>0) {
	        				// inserimento in contracts
	        				setContract(conn, idemp, vals[5]);
	        			}
	        		}
	        		catch(Exception err) {
	        			// non presenti ore contratto
	        		}
	        	}
	        	
	        	
	        }
			
			//conn.rollback();
			conn.commit();
		}
		catch(Exception e) {
			e.printStackTrace();
			conn.rollback();
		}
		finally {
			try {
				if(conn!=null)
					conn.close();
			}
			catch(Exception e) {}
		}
	}
	
	// ================
	// SET STORE
	private static String setStore(String idcus, String description) {
		
		String idstore = "";
		String sql = "";
		
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			conn = Utility.getConnControl();
			st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					  ResultSet.CONCUR_UPDATABLE);
			
			String $id = Utility.getNewProgr(conn, "stores", "id", "");
			
			sql   = "INSERT INTO stores( "
					+ "id, description, store_code, id_customer, country, region, province, "
					+ "city, address, postalcode, sphone, smail, id_optempl, sapid, customeridstore,"
					+ "brandname, brandlogo, notes, contact, cphone, cmobile, cmail, flagclose, flag_analytics ) "
					+ "values ("
					+ $id+" , '"+description.replaceAll("'", "''")+"' , "
					+ "'"+$id+"', "+idcus+" , "
					+ "0 , 0 , 0 , 0 , "
					+ "'', '' , "
					+ "'', '' , "
					+ "0 , '', '' , "
					+ "'"+description.replaceAll("'", "''")+"', '' , "
					+ "'', '' , "
					+ "'', '' , "
					+ "'' , false, false ) RETURNING id ";
			System.out.println(sql);
			
			rs = st.executeQuery(sql);
			if(rs.next()) {
				idstore = rs.getString(1);
				System.out.println("ID: "+rs.getString(1));
			}
			else {
				idstore = "-1";
				System.out.println("ID: -1");
			}
			rs.close();
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
		
		return idstore;
		
	}
	
	// =============
	// SET DEPARTMENTS
	private static String setDep(Connection conn, String idcus, String description, String idstore) {
		
		String iddep = "";
		String sql = "";
		
		Statement st = null;
		ResultSet rs = null;
		try {
			st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					  ResultSet.CONCUR_UPDATABLE);
			
			sql   = "INSERT INTO public.hr_departments( " + 
					"description, department_code, minnum_intervals, tollerance, nocturne, " + 
					"id_store, department_type, id_customer) " + 
					"VALUES ('"+description.replaceAll("'", "''")+"' , '"+idstore+"' , 0 , 10 , 22 , "+idstore+" , 1 , "+idcus+") RETURNING id ";
			System.out.println(sql);
			
			rs = st.executeQuery(sql);
			if(rs.next()) {
				iddep = rs.getString(1);
				System.out.println("ID: "+rs.getString(1));
			}
			else {
				iddep = "-1";
				System.out.println("ID: -1");
			}
			rs.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(st!=null)
					st.close();
			}
			catch(Exception e) {}
		}
		
		return iddep;
		
	}
	
	// =============
	// SET EMPLOYEE
	private static String setEmp(Connection conn, String idcus, String name, String surname, String cf, String iddep) {
		
		String idemp = "";
		String sql = "";
		
		Statement st = null;
		ResultSet rs = null;
		try {
			st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					  ResultSet.CONCUR_UPDATABLE);
			
			sql   = "insert into hr_employee "
					+ "("
					+ "name, surname, address, locality, "
					+ "province, zipcode, birthdate, sex, "
					+ "birthlocality, birthprovince, state, fcode, "
					+ "phone, email, id_customer "
					+ ")"
					+ "values "
					+ "( "
					+ "'"+name.replaceAll("'", "''")+"', '"+surname.replaceAll("'", "''")+"', '', '', "
					+ "'', '', null, '', "
					+ "'', '', '', '"+cf.trim()+"', "
					+ "'', '', "+idcus+" "
					+ ") RETURNING id ";
			System.out.println(sql);
			
			rs = st.executeQuery(sql);
			if(rs.next()) {
				idemp = rs.getString(1);
				System.out.println("ID: "+rs.getString(1));
			}
			else {
				idemp = "-1";
				System.out.println("ID: -1");
			}
			rs.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(st!=null)
					st.close();
			}
			catch(Exception e) {}
		}
		
		return idemp;
		
	}
	
	// =============
	// SET REL_DEP_EMPLOYEE
	private static void setRel(Connection conn, String iddep, String idemp) {
		
		String sql = "";
		
		Statement st = null;
		try {
			st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					  ResultSet.CONCUR_UPDATABLE);
			
			String $date_start = "";
			
			 
			Calendar cal = Calendar.getInstance(); // creates calendar
		    cal.set(2018, Calendar.JUNE, 1);
		    
		    $date_start = ""+(cal.getTimeInMillis() / 1000);
		    
			sql   = "insert into rel_dep_employee "+
					"(id_employee, id_departments, id_roles, datestart, dateend) "+
					"values "+
					"('"+idemp+"', '"+iddep+"', '6089dea4-f0ec-4554-b8c7-d7b445e26c94', "+$date_start+", null ) ";
			
			System.out.println(sql);
			st.executeUpdate(sql);
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(st!=null)
					st.close();
			}
			catch(Exception e) {}
		}
		
	}
	
	// =============
	// SET CONTRACT
	private static void setContract(Connection conn, String idemp, String ore) {
		
		String sql = "";
		
		Statement st = null;
		try {
			st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					  ResultSet.CONCUR_UPDATABLE);
			
		    sql   = "insert into hr_contracts "+
					"(id_employee, datada, dataal, monteore, "+
					"saldo_ore, data_saldo) values "+
					"('"+idemp.replaceAll("'", "''")+"', '2018-06-01', "+
					"null, "+ore+",null,null) ";
			System.out.println(sql);
			st.executeUpdate(sql);
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(st!=null)
					st.close();
			}
			catch(Exception e) {}
		}
		
	}
}
