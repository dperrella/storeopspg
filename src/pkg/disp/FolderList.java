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
import pkg.utility.Utility;

public class FolderList extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		List<Tables> json = new ArrayList<Tables>();
		json.clear();
		

		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		String sql = "";
		
		String folder = req.getParameter("folder");
		
		String q = ""+req.getParameter("q");
		q=Utility.noNull(q);
		String msg = "";
		
		try {
			// Apre la connessione
			conn = Utility.getConnDispatcher();
			st=conn.createStatement();
			
			String cond = "";
			if(q.length()>0)
				cond=" and zipfile||jsonfile like '%"+q.replaceAll("'", "''")+"%' ";
			sql   = "select * from neo.downloaded_neo where folder = '"+folder+"' and status = 4 "+cond;
			rs=st.executeQuery(sql);
			while(rs.next()) {
				json.add(new Tables(rs.getString("folder"),rs.getString("zipfile"),rs.getString("jsonfile"),rs.getString("id"),"","",""));
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
		
		obj.put("json", json);
		obj.put("msg", msg);
		
		
		
		return new ModelAndView("/disp/main/folder-list", obj);
	}
	
}
