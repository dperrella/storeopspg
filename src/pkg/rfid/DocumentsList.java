package pkg.rfid;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import pkg.beans.Documents;
import pkg.utility.Utility;

public class DocumentsList extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		HttpSession session = req.getSession();
		
		List<Documents> doc = new ArrayList<Documents>();
		
		// Pulisce la lista
		doc.clear();
		
		String username = session.getAttribute("username").toString().toUpperCase();
		
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		String sql = "";
		
		int count = 0;
		String msg = "";
		
		String q = ""+req.getParameter("q");
		q=Utility.noNull(q);
		
		try {
			// Apre la connessione
			conn = Utility.getConnRfid();
			
			//String iddevices = getDevicesFromUser(conn, username);
			String warehouses = getWarehouseFromUser(conn, username);
			if(warehouses.length()==0)
				warehouses = "''";
			
			String cond1 = "";
			String cond2 = "";
			
			DateFormat dateIta = new SimpleDateFormat("dd-MM-yyyy");
			
			// sql da eseguire (ORIGIN)
			sql  =  "select a.* , b.description as origin_descr, c.description as dest_descr, d.description as doc_descr, "
					+ "1 as verso "
					+ "from documents a, warehouse b, warehouse c, doctype d "
					+ "where destination_warehouse in("+warehouses+") "
					+ "and idstep = 0 "
					+ "and a.origin_warehouse = b.id "
					+ "and a.destination_warehouse = c.id "
					+ "and a.doctype::integer = d.id "
					+ "and status = 'X' "
					+ "union "
					+ "select a.* , b.description as origin_descr, c.description as dest_descr, d.description as doc_descr, "
					+ "-1 as verso "
					+ "from documents a, warehouse b, warehouse c, doctype d "
					+ "where origin_warehouse in("+warehouses+") "
					+ "and idstep = 1 "
					+ "and a.origin_warehouse = b.id "
					+ "and a.destination_warehouse = c.id "
					+ "and a.doctype::integer = d.id "
					+ "and status = 'X'";
			System.out.println(sql);
			
			// l'sql ha parametri passati?
			boolean hasCond = false;
			if(q.length()>0) {
				hasCond = true;
				cond1=" where b.description || c.description || d.description like ? ";
				cond2=" where b.description || c.description || d.description like ? ";
			}
			
			// Prepara il cursore
			st = conn.prepareStatement(sql);
			
			// Se ha parametri li passa all'sql
			if(hasCond==true) {
				st.setString(1, "%" + q + "%");
			}
			
			// Esegue l'sql
			rs=st.executeQuery();
			
			// Campi database
			String $id="";
			String $docnumber="";
			String $doctype="";
			String $docdate="";
			String $idstep="";
			String $origin_warehouse="";
			String $destination_warehouse="";
			String $idflow="";
			String $status="";
			String $docref="";
			String $id_prec="";
			
			String $cmp1="";
			String $cmp2="";
			String $cmp3="";
			String $cmp4="";
			
			String $cmp5="";
			String $cmp6="";
			String $cmp7="";
			String $cmp8="";
			
			while(rs.next()) {
				
				count+=1;
				
				$id=rs.getString("id");
				$docnumber=rs.getString("docnumber");
				$doctype=rs.getString("doctype");
				$docdate=dateIta.format(rs.getDate("docdate"));
				$idstep=rs.getString("idstep");
				$origin_warehouse=rs.getString("origin_warehouse");
				$destination_warehouse=rs.getString("destination_warehouse");
				$idflow=rs.getString("idflow");
				$status=rs.getString("status");
				$docref=rs.getString("docref");
				$id_prec=rs.getString("id_prec");
				
				$cmp1=rs.getString("origin_descr");
				$cmp2=rs.getString("dest_descr");
				$cmp3=rs.getString("doc_descr");
				
				String tots = getTots(conn, $id, "");
				String temp[] = tots.split("@@##");
				$cmp4 = temp[0];
				$cmp5 = temp[1];
				
				doc.add(new Documents($id,$docnumber,$doctype,$docdate,
						$idstep,$origin_warehouse,$destination_warehouse,
						$idflow,$status,$docref,$id_prec,
						$cmp1,$cmp2,$cmp3,$cmp4,
						$cmp5,$cmp6,$cmp7,$cmp8));
				
			}
			rs.close();
			st.close();
			
			conn.close();
		}
		catch(Exception e) {
			e.printStackTrace();
			
			// Messaggio
			msg = "<div class=\"alert alert-danger\"><strong>Attenzione</strong><br/>Si e&grave; verificato un errore!<br/><br/>Errore:<br/>"+e+"</div>";			
		}
		
		obj.put("dsfun", "Lista Documenti");
		obj.put("doc", doc);
		obj.put("count", count);
		obj.put("msg", msg);
		
		return new ModelAndView("/rfid/docs/docs-list", obj);
	}
	
	private static String getDevicesFromUser(Connection conn, String username) {
		
		String devices = "";
		
		Statement st = null;
		ResultSet rs = null;
		String sql = "";
		
		try {
			st=conn.createStatement();
			sql   = "select iddevice from rel_user_device where idusr = '"+username+"' ";
			rs=st.executeQuery(sql);
			while(rs.next()) {
				devices+=rs.getString("iddevice");
			}
			rs.close();
			if(devices.length()>0) 
				devices = devices.substring(0,devices.length()-1);
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
		
		return devices;
		
	}
	
	private static String getWarehouseFromUser(Connection conn, String username) {
		
		String origin_warehouse = "";
		
		Statement st = null;
		ResultSet rs = null;
		String sql = "";
		
		try {
			st=conn.createStatement();
			sql   = "select origin_warehouse "
					+"from devices a, rel_user_devices b "
					+"where a.iddevice = b.iddevice "
					+"and b.idusr = '"+username+"'";
			rs=st.executeQuery(sql);
			while(rs.next()) {
				origin_warehouse+="'"+rs.getString("origin_warehouse")+"',";
			}
			rs.close();
			if(origin_warehouse.length()>0) 
				origin_warehouse = origin_warehouse.substring(0,origin_warehouse.length()-1);
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
		
		return origin_warehouse;
		
	}
	
	public String getTots(Connection conn, String iddoc, String idflow) {
		
		String tots = "";
		
		String cond = "";
		if(idflow.length()>0)
			cond=" and idflow = '"+idflow+"' ";
		
		Statement st = null;
		ResultSet rs = null;
		String sql = "";
		
		String p1 = "";
    	String p2 = "";

	    try {
	    	
	    	st=conn.createStatement();
			sql  = "select coalesce(sum(sum1),null,0) as sum1, coalesce(sum(sum2),null,0) as sum2 "
					+ "from vw_totdoc where id_documents = '"+iddoc+"' "+cond;
			    
			rs=st.executeQuery(sql);
		    while(rs.next()) {
		    	p1 = Utility.noNull(rs.getString("sum1"));
		    	p2 = Utility.noNull(rs.getString("sum2"));
		    	
		    	if ("".equals(p1) || p1 == ""){
		    		p1 = "0";
		    	}
		    	
		    	if ("".equals(p2) || p2 == ""){
		    		p2 = "0";
		    	}
		    	
		    	tots = p1 + "@@##" + p2;
		    }
		    rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    finally {
	    	try {
		    	if(st!=null)
		    		st.close();
	    	}
	    	catch(Exception e) {}
	    }
		
		return tots;

	}	
}
