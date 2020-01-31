<%@ page 
language="java"
import="pkg.properties.ReadProperties"
import="java.sql.*"
import="java.util.Date"
import="java.text.DateFormat"
import="java.text.SimpleDateFormat"
import="pkg.utility.Utility"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>
<%!
private static String getDoorId(Connection conn, String id_customer, String account_id, String id, String description) {
	
	String doorid = "";
	
	Statement st = null;
	String sql = "";
	
	try {
		st=conn.createStatement();
		if(id.length()==0)
			doorid = Utility.getNewProgr(conn, "doors", "door_type_id", "where id_customer = "+id_customer+" ");
		else
			doorid = id;
		
		System.out.println("doorid: "+doorid);
		sql = "insert into doors "+
			"(id_customer, account_id, door_description, door_type_id, enabled, imported) "+
			"values "+
			"("+id_customer+" , '"+account_id+"', '"+description.replaceAll("'", "''")+"', "+doorid+", true, false) ";
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
	
	
	return doorid;
}
%>
<%
Connection conn = null;
PreparedStatement st = null;
ResultSet rs = null;
String sql = "";

String id_device = request.getParameter("id_device");
String id_customer = request.getParameter("id_customer");
String account_id = request.getParameter("account_id");
String linNum = request.getParameter("linNum");


try {
	conn=Utility.getConnAdmin();

	sql   = "delete from device_lines where id_device = ? ";
	// Prepara il cursore
	st = conn.prepareStatement(sql);
	
	// Passa i parametri
	st.setInt(1, Integer.parseInt(id_device));
	
	// Esegue l'sql
	st.executeUpdate();
	
	
	// Inserimento in rel_user_customers
	String[] cd = request.getParameterValues("nrrig");
	String id_line = "";
	String door_id = "";
	String id = "";
	String description = "";
	String status = "";
	String entrance = "";
	if(cd!=null) {
		// prendiamo tutti i valori dell'input multiplo
		if (cd.length>0) {
			
			System.out.println(cd.length);
			
			// riversiamo l'array
			for(int i=0; i<cd.length; i++) {
				
				id_line = request.getParameterValues("id_line")[i];
				try {
					door_id = ""+request.getParameter("doorid"+id_line);
					door_id = Utility.noNull(door_id);
				}
				catch(Exception e) {}
				try {
					id = ""+request.getParameter("id"+id_line);
					id = Utility.noNull(id);
				}
				catch(Exception e) {}
				
				try {
					description = ""+request.getParameter("description"+id_line);
					description = Utility.noNull(description);
				}
				catch(Exception e) {}
				try {
					status = ""+request.getParameter("status"+id_line);
					status = Utility.noNull(status);
				}
				catch(Exception e) {}
				
				if(status.equals("ON"))
					status="";
				else
					status="A";
				
				try {
					entrance = ""+request.getParameter("entrance"+id_line);
					entrance = Utility.noNull(entrance);
				}
				catch(Exception e) {}
				
				if(!entrance.equals("ON"))
					entrance="false";
				else
					entrance="true";
				
				
				if(description.length()>0) {
					// write door
					door_id = getDoorId(conn, id_customer, account_id, id, description);
				}
				
				
				
				sql   = "insert into device_lines "+
						"(id_device, id_line, status, door_id, entrance) "+
						"values "+
						"(?, ?, ?, ?, ?) ";
				// Prepara il cursore
				st = conn.prepareStatement(sql);
				
				// Passa i parametri
				st.setInt(1, Integer.parseInt(id_device));
				st.setInt(2, Integer.parseInt(id_line));
				st.setString(3, status);
				if(door_id.length()==0)
					st.setNull(4, java.sql.Types.INTEGER);
				else
					st.setInt(4, Integer.parseInt(door_id));
				st.setBoolean(5, Boolean.parseBoolean(entrance));
				
				// Esegue l'sql
				st.executeUpdate();
				
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
%>