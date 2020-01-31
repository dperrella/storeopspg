package pkg.control;

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

import pkg.beans.Tables2;
import pkg.beans.Report;
import pkg.beans.Stores;
import pkg.beans.Tables;
import pkg.beans.Customers;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class Desk2 extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		// Formato numero
		java.text.DecimalFormat df = new java.text.DecimalFormat("###,###,##0.00");
		
		HttpSession session = req.getSession();
		//Legge il file di properties associato alla lingua sul profilo
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");
		
		// Formato data
		DateFormat dateNum = new SimpleDateFormat("yyyyMMdd");
		DateFormat dateIta = new SimpleDateFormat("dd-MM-yyyy");
		Date date = new Date();
		String oggi = dateNum.format(date);
		String oggiIta = dateIta.format(date);
		
		String currentYear = oggi.substring(0,4);
		String currentMonth = oggi.substring(4,6);
		String currentWeek = "";
		
		// campi passati dalla session
		String username = (String) session.getAttribute("username");
		String desc = (String) session.getAttribute("desc");
		String tipo = (String) session.getAttribute("tipo");
		String foto = (String) session.getAttribute("foto");
		String mail = (String) session.getAttribute("mail");
		String db = (String) session.getAttribute("db");
		
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		
		int nr_stores = 0;
		int nr_customers = 0;
		int nr_devices = 0;
		
		try {
			
			ReadProperties prop = new ReadProperties("connection.properties");
			conn = Utility.getConnAdmin();
			
			// ==================
			// NUMERO NEGOZI
			// ==================
			// sql da eseguire
			sql  =  "select count(a.id) nrstores "
					+ "from stores a, customers c "
					+ "where a.id_customer = c.id "
					+ "and a.id in(select idstore from rel_user_stores "
					+ "where idusr = '"+(String)session.getAttribute("username")+"') ";
			
			// Prepara il cursore
			st = conn.prepareStatement(sql);
			
			// Esegue l'sql
			rs=st.executeQuery();
			
			if(rs.next()) {
				nr_stores=rs.getInt("nrstores");
			}
			rs.close();
			st.close();
			
			
			// ==================
			// NUMERO CLIENTI
			// ==================
			sql  =  "select count(id) nrcustomers from customers where "
					+ "id in(select idcustomer from rel_user_customers "
					+ "where idusr = '"+(String)session.getAttribute("username")+"') ";
			// Prepara il cursore
			st = conn.prepareStatement(sql);
			
			// Esegue l'sql
			rs=st.executeQuery();
			
			if(rs.next()) {
				nr_customers = rs.getInt("nrcustomers");
			}
			rs.close();
			st.close();
			
			
			// ==================
			// NUMERO DISPOSITIVI
			// ==================
			sql  =  "select count(a.id) nrdevices "
					+ "from devices a, stores b, customers c, devicetype d "
					+ "where a.id_store = b.id and b.id_customer = c.id "
					+ "and a.device_type = d.id "
					+ "and a.id_store in(select idstore from rel_user_stores "
					+ "where idusr = '"+(String)session.getAttribute("username")+"') ";
			// Prepara il cursore
			st = conn.prepareStatement(sql);
			
			// Esegue l'sql
			rs=st.executeQuery();
			
			if(rs.next()) {
				nr_devices = rs.getInt("nrdevices");
			}
			rs.close();
			st.close();

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
		
		obj.put("oggi", oggiIta);
		obj.put("currentYear", currentYear);
		
		obj.put("username", username);
		obj.put("desc", desc);
		obj.put("tipo", tipo);
		obj.put("foto", foto);
		obj.put("mail", mail);
		
		obj.put("nr_stores", nr_stores);
		obj.put("nr_customers", nr_customers);
		obj.put("nr_devices", nr_devices);
		
		
		return new ModelAndView("/control/main/index2", obj);
	}
	
}
