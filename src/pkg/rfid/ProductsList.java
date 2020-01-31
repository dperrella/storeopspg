package pkg.rfid;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import java.sql.*;

import pkg.beans.Products;
import pkg.beans.Tables;
import pkg.utility.Utility;

public class ProductsList extends AbstractController {
	
	public static DateFormat dateIso = new SimpleDateFormat("yyyy-MM-dd");
	public static Date date = new Date();
	public static String oggi = dateIso.format(date);
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		List<Products> prod = new ArrayList<Products>();
		List<Tables> dev = new ArrayList<Tables>();
		
		prod.clear();
		dev.clear();
		
		HttpSession session = req.getSession();
		
		String username = session.getAttribute("username").toString().toUpperCase();
		
		String idprinter = ""+req.getParameter("idprinter");
		idprinter = Utility.noNull(idprinter);
		obj.put("idprinter",idprinter);
		
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		String sql = "";
		String cond = "";
		
		String sku = ""+req.getParameter("sku");
		sku = Utility.noNull(sku);
		if(sku.length()>0)
			cond+=" where CONCAT(edipro,ds1pro,codpro) like '%"+sku.replaceAll("'", "''").toUpperCase()+"%' ";
		
		try {
			
			conn = Utility.getConnRfid();
			st=conn.createStatement();
			
			// Lista Stampanti
			sql   = "select * from devices where devicetype = 4 ";
			rs=st.executeQuery(sql);
			String selected = "";
			while(rs.next()) {
				if(idprinter.equals(rs.getString("iddevice"))) 
					selected = "selected ";
				dev.add(new Tables("DEV", rs.getString("iddevice"), rs.getString("description"), selected, "","",""));
				
				selected = "";
			}
			rs.close();
			
			if(idprinter.length()>0) {
				// Legge dati stampante
				String p_name = "";
				String p_description = "";
				String p_ip = "";
				String p_port = "";
				//sql   = "select * from printers where id = '"+idprinter+"' ";
				sql   = "select * from devices where iddevice = '"+idprinter+"' ";
				//System.out.println("sql = " + sql);
				rs=st.executeQuery(sql);
				if(rs.next()) {
					p_name = rs.getString("name");
					p_description = rs.getString("description");
					p_ip = rs.getString("ip");
					p_port = rs.getString("port");
				}
				rs.close();
				
				obj.put("p_name", p_name);
				obj.put("p_description", p_description);
				obj.put("p_ip", p_ip);
				obj.put("p_port", p_port);
			}
			
			// -------
			// Legge i dettagli dello step
			String $codpro = "";
	        String $ds1pro = "";
	        String $edipro = "";
	        
		    sql   = "select * from products "+cond+" limit 100";
		    //System.out.println("sql = " + sql);
		    rs=st.executeQuery(sql);
		    int count = 0;
		    while(rs.next()) {
		    	
		    	count+=1;
		    	
		    	$codpro = rs.getString("codpro");
		    	$ds1pro = rs.getString("ds1pro");
			    $edipro = rs.getString("edipro");
			    
			    prod.add(new Products($codpro,$ds1pro,$edipro,""+count,"","",""));
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
		
		obj.put("username", username);
		obj.put("idprinter", idprinter);
		obj.put("prod", prod);
		obj.put("dev", dev);
		
		return new ModelAndView("/rfid/anag/products-list", obj);
	}
	
}

