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

public class VisitList extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		List<Tables2> tab = new ArrayList<Tables2>();
		tab.clear();
		

		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		String sql = "";
		
		String q = ""+req.getParameter("q");
		q=Utility.noNull(q);
		String msg = "";
		
		try {
			// Apre la connessione
			conn = Utility.getConnVisit();
			st=conn.createStatement();
			
			String cond = "";
			if(q.length()>0)
				cond=" where store_description like '%"+q.replaceAll("'", "''")+"%' ";
			sql   = "select * from storelist "+cond;
			rs=st.executeQuery(sql);
			while(rs.next()) {
				tab.add(new Tables2("VIS",rs.getString("store_description"),"",rs.getString("vis"),rs.getString("con"),
						rs.getString("eas"),rs.getString("deact"),rs.getString("eas_on"),rs.getString("eas_off"),"","","",""));
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
		
		
		
		return new ModelAndView("/disp/main/visit-list", obj);
	}
	
}
