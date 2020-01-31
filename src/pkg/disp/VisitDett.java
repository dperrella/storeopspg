package pkg.disp;

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

import pkg.beans.Tables;
import pkg.beans.Tables2;
import pkg.utility.Utility;

public class VisitDett extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
				
		List<Tables> tab = new ArrayList<Tables>();
		tab.clear();
		
		String store_description = req.getParameter("store_description");
		String datatype = req.getParameter("datatype");

		obj.put("store_description", store_description);
		obj.put("datatype", datatype);
		
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		String sql = "";
		
		String msg = "";
		
		try {
			// Apre la connessione
			conn = Utility.getConnVisit();
			st=conn.createStatement();
			
			sql   = "select * from storeyear where store_description = '"+store_description.replaceAll("'", "''")+"' "
					+ "and datatype = "+datatype+" order by anno, mese";
			rs=st.executeQuery(sql);
			while(rs.next()) {
				tab.add(new Tables("VIS",rs.getString("anno"),rs.getString("mese"),"","","",""));
			}
			rs.close();
			
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
		
		return new ModelAndView("/disp/main/visit-dett", obj);
	}
	
}
