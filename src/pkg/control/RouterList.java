package pkg.control;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import pkg.beans.DevicesRfid;
import pkg.utility.Utility;

public class RouterList extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		Map<String, Object> obj = new HashMap<String, Object>();
		
		List<DevicesRfid> dev = new ArrayList<DevicesRfid>();
		
		// Pulisce la lista
		dev.clear();
		
		String sql = "";
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		
		int count = 0;
		String msg = "";
		
		String iddevice = "";
	    String name = "";
	    String description = "";
	    String rifidi = "";
	    String ip = "";
	    String port = "";
	    String urlws = "";
	    String origin_warehouse = "";
	   
	    String mac = "";
	    String idflow_default = "";
	    String idstep_default = "";
	    String id_dev_printer = "";
	    String devicetype = "";
	    String idname = "";

		
		try {
			// Apre la connessione
			conn = Utility.getConnControl();
			st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			
			// sql da eseguire
			sql  =  "select *, routertype.description routerdescr from routers left join routertype on idrouter = routers_type ";					
			System.out.println("sql = " + sql);
			rs = st.executeQuery(sql);
			
			// Campi database

			
			while(rs.next()) {
				
				count+=1;
				
				iddevice=Utility.noNull(rs.getString("id"));
				name=Utility.noNull(rs.getString("customer_description"));
				description=Utility.noNull(rs.getString("store_description"));
				ip=Utility.noNull(rs.getString("routerdescr"));
				port=Utility.noNull(rs.getString("ip_address_router"));
				
				
				dev.add(new DevicesRfid(iddevice,name,description,rifidi,ip,port,urlws,origin_warehouse,mac,idflow_default,idstep_default,id_dev_printer,devicetype,idname,"","","",""));
				
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
		
		obj.put("dsfun", "Lista Ruoli");
		obj.put("dev", dev);
		obj.put("count", count);
		obj.put("msg", msg);
		
		return new ModelAndView("/app/anag/router-list", obj);
	}
	
}
