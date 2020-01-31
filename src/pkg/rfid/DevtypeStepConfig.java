package pkg.rfid;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import pkg.beans.Rfid_config;
import pkg.utility.ReadConfigFields;
import pkg.utility.Utility;

public class DevtypeStepConfig extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		List<Rfid_config> config = new ArrayList<Rfid_config>();
		config.clear();
		
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		String sql = "";
		
		String id_devicetype = ""+req.getParameter("id_devicetype");
		id_devicetype=Utility.noNull(id_devicetype);
		id_devicetype=id_devicetype.replaceAll("\\?cd=undefined", "");
		String idflow = ""+req.getParameter("idflow");
		idflow=Utility.noNull(idflow);
		idflow=idflow.replaceAll("\\?cd=undefined", "");
		String idstep = ""+req.getParameter("idstep");
		idstep=Utility.noNull(idstep);
		idstep=idstep.replaceAll("\\?cd=undefined", "");
		
		try {
			
			conn = Utility.getConnRfid();
			st=conn.createStatement();
			
			sql   = "select config from rel_devicetype_step "
					+ "where id_devicetype = "+id_devicetype+" "
					+ "and id_flow = "+idflow+" "
					+ "and id_step = "+idstep+" ";
			rs=st.executeQuery(sql);
			if(rs.next()) {
				config = ReadConfigFields.getConfigFields(rs.getString("config"));
			}
			rs.close();
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(conn!=null)
					conn.close();
			}
			catch(Exception e) {}
		}
		
		obj.put("id_devicetype", id_devicetype);
		obj.put("idflow", idflow);
		obj.put("idstep", idstep);

		obj.put("config", config);
		
		return new ModelAndView("/rfid/rel/devtype-step-config", obj);
	}
	
}
