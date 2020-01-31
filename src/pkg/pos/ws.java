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

import pkg.beans.pos.Sm_Listlogs;
import pkg.beans.Users;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class ws extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		//List<Sm_Listlogs> listlog = new ArrayList<Sm_Listlogs>();
		List<Users> listlog = new ArrayList<Users>();
		
		listlog.clear();
		
		HttpSession session = req.getSession();
		//Legge il file di properties associato alla lingua sul profilo
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");
		
		// campi passati dalla session
		String username = (String) session.getAttribute("username");
		String desc = (String) session.getAttribute("desc");
		String tipo = (String) session.getAttribute("tipo");
		String foto = (String) session.getAttribute("foto");
		String mail = (String) session.getAttribute("mail");
		String db = (String) session.getAttribute("db");

		String list_app_store = "";
		String sql = "";
		String combocli = "";
		Connection connext = null;
		Connection conn = null;
		Connection conn2 = null;
		Statement st = null;
		Statement st2 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		
		try {
		
		connext = Utility.getConnPos();
		st = connext.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		
		
		conn = Utility.getConnPos();
		st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		
		conn2 = Utility.getConnPosext();
		st2 = conn2.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		
		
		
		sql = "select id idstore, idusr from vw_rel_user_stores where upper(idusr) = '"+username.toUpperCase()+"'";
		rs = st.executeQuery(sql);
		while(rs.next()) {
			if ("".equals(list_app_store)){
				list_app_store = "'"+rs.getString("idstore")+"'";
			}else{
				list_app_store = list_app_store + ",'" + rs.getString("idstore")+"'";
			}
		}
		rs.close();
		
		String idcus = session.getAttribute("idcus").toString();
		String first_ = "";
		
		System.out.println("idcus.len = " + idcus.length());
		if(idcus.length()>0) {
		
			//sql = "select idclient, descrizione from app_stores where idclient in("+list_app_store+") order by idclient ";
			sql = "select store_code idclient, description descrizione from stores where id_customer = "+idcus;
			System.out.println("sql = " + sql);
			rs = st2.executeQuery(sql);
			while(rs.next()) {
				if ("".equals(combocli)) {
					combocli = "<option value='"+rs.getString("idclient")+"'>"+rs.getString("idclient")+" - "+rs.getString("descrizione").toUpperCase()+"</option>";
					first_ = rs.getString("idclient");
				}else {
					combocli = combocli + "<option value='"+rs.getString("idclient")+"'>"+rs.getString("idclient")+" - "+rs.getString("descrizione").toUpperCase()+"</option>";	
				}
				
			}
		
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
		
		
		obj.put("ComboCli", combocli);
		

		return new ModelAndView("/pos/main/ws", obj);
	}
	
}
