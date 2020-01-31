package pkg.counter2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import pkg.beans.Customers;
import pkg.beans.Tables;
import pkg.beans.Tables2;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class DoorsList extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		List<Tables2> tab = new ArrayList<Tables2>();
		tab.clear();
		
		HttpSession session = req.getSession();
		//Legge il file di properties associato alla lingua sul profilo
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");

		Connection conn = null;
		PreparedStatement st = null;
		
		ResultSet rs = null;
		
		String sql = "";
		
		String idstore = ""+req.getParameter("idstore");
		idstore = Utility.noNull(idstore);
		if(idstore.length()==0)
			idstore = "0";
		obj.put("idstore", idstore);
		
		String idcus = ""+session.getAttribute("idcus");
		idcus=Utility.noNull(idcus);
		if(idcus.length()==0)
			idcus = "0";
		obj.put("idcus", idcus);
		

		String tipoForm = ""+req.getParameter("tipoForm");
		tipoForm=Utility.noNull(tipoForm);
		String azione = ""+req.getParameter("azione");
		azione=Utility.noNull(azione);
		
		
		String msg = "";
		
		try {
			// Apre la connessione
			conn = Utility.getConnData();
	
			if(azione.length()>0) {
				
				if(tipoForm.equals("M")) {
					
					String door = req.getParameter("door");
					String desc = req.getParameter("desc");
					
					sql   = "delete from rel_store_door "
							+ "where idstore = "+idstore+" and iddoor = "+door+" ";
					st=conn.prepareStatement(sql);
					st.executeUpdate();
					st.close();
					
					sql   = "insert into rel_store_door "
							+ "(idstore, iddoor, description) "
							+ "values "
							+ "("+idstore+" , "+door+" , '"+desc.replaceAll("'", "''")+"') ";
					st=conn.prepareStatement(sql);
					st.executeUpdate();
					st.close();
					
				}
				
			}
			
			
			sql   = "select *, a.description ds_aisle, r.description as ds_pers "
					+ "from doors d "
					+ "left join rel_store_door r on d.door_type_id = r.iddoor and r.idstore = "+idstore+" "
					+ "left join aisle_type a on d.aisle_type = a.id "
					+ "where id_customer = "+idcus+" ";
			st=conn.prepareStatement(sql);
			rs=st.executeQuery();
			
			String enabled = "";
			String imported = "";
			while(rs.next()) {
				
				if(rs.getBoolean("enabled")) {
					enabled = "<i class=\"fa fa-circle text-success\"></i>";
				}
				else {
					enabled = "<i class=\"fa fa-circle text-danger\"></i>";
				}
				
				if(rs.getBoolean("imported")) {
					imported = "<i class=\"icon-cloud-download\"></i> Imported";
				}
				else {
					imported = "<i class=\"icon-note\"></i> Manual";
				}
				String aisle_type = ""+rs.getString("ds_aisle");
				aisle_type = Utility.noNull(aisle_type);
				
				String pers = ""+rs.getString("ds_pers");
				pers = Utility.noNull(pers);
				
				String description = rs.getString("door_description");
				
				if(pers.length()>0)
					description = pers;
				
				tab.add(new Tables2("DOO",rs.getString("door_type_id"),description,
						rs.getString("account_id"),aisle_type,enabled,imported,"","","","","",""));
			}
			rs.close();
			st.close();
			
		}
		catch(Exception e) {
			e.printStackTrace();
			
			msg = "<div class=\"wrapper\"><div class=\"alert alert-danger\"><strong>"+e.getMessage()+"</strong></div></div>";
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
		
		obj.put("tab", tab);
		obj.put("msg", msg);
		
		return new ModelAndView("/counter2/anag/doors-list", obj);
	}
	
}
