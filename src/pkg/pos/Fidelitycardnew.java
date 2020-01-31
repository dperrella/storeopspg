package pkg.pos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import pkg.beans.pos.Sm_DevnotE;
import pkg.beans.pos.Sm_Listlogs;
import pkg.beans.Users;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class Fidelitycardnew extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		List<Users> listlog = new ArrayList<Users>();
		
		listlog.clear();
		
		HttpSession session = req.getSession();
		//Legge il file di properties associato alla lingua sul profilo
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");

		
		/**/
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		conn = Utility.getConnPos();
		st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		String sql = "";
		
		String comuni = "";
		String category= "";
		
		try {

		String us = session.getAttribute("username").toString().toUpperCase();

		sql = "select distinct * from comuni order by comune";
		rs = st.executeQuery(sql);
		
		while(rs.next()) {
			comuni = comuni + "<option value='"+rs.getString("id_com")+"'>"+rs.getString("comune").toUpperCase()+"</option>";
		}
		rs.close();
		
		
		sql = "select distinct * from categoryfid order by descriptioncategoryfid";
		rs = st.executeQuery(sql);
		
		while(rs.next()) {
			category = category + "<option value='"+rs.getString("idcategoryfid")+"'>"+rs.getString("descriptioncategoryfid").toUpperCase()+"</option>";
		}
		rs.close();
		
		
		obj.put("Comuni", comuni);
		obj.put("Category", category);
		
		//session.setAttribute("Idlog", req.getParameter("id"));		
		
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
		/**/
		
		
		
		return new ModelAndView("/pos/main/fidelity-card-new", obj);
	}
	
}
