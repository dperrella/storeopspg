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

import pkg.beans.Tables;
import pkg.utility.Utility;

public class SearchDocs extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		List<Tables> wha = new ArrayList<Tables>();
		wha.clear();
		
		HttpSession session = req.getSession();
		
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
			
			sql   = "select origin_warehouse, c.description "
					+"from devices a, rel_user_devices b, warehouse c "
					+"where a.iddevice = b.iddevice and c.id = a.origin_warehouse "
					+"and b.idusr = '"+username+"'";
			st = conn.prepareStatement(sql);
			rs=st.executeQuery();
			while(rs.next()) {
				wha.add(new Tables("WHA",rs.getString("origin_warehouse"),
						rs.getString("description"),"","","",""));
			}
			rs.close();
			
			
			
			
			conn.close();
		}
		catch(Exception e) {
			e.printStackTrace();
			
			// Messaggio
			msg = "<div class=\"alert alert-danger\"><strong>Attenzione</strong><br/>Si e&grave; verificato un errore!<br/><br/>Errore:<br/>"+e+"</div>";			
		}
		
		obj.put("dsfun", "Cerca Documenti");
		obj.put("msg", msg);
		obj.put("wha", wha);
		
		return new ModelAndView("/rfid/docs/search-docs", obj);
	}
	
	
	
	
	
	
}
