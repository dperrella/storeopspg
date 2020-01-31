package pkg.rfid;

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
import pkg.beans.Rfid_devices;

public class DocumentList extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		HttpSession session = req.getSession();
		//Legge il file di properties associato alla lingua sul profilo
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");
		
		/*
		// campi passati dalla session
		List<Rfid_devices> dev = new ArrayList<Rfid_devices>();
		
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "";
		
		try {
			
			ReadProperties prop = new ReadProperties("connection.properties");
			conn = Utility.getConnRfid();
			
			Rfid_devices bean_dev = new Rfid_devices();
			dev = bean_dev.selectAll(conn, "");
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
		
		obj.put("dev", dev);
		*/
		
		return new ModelAndView("/rfid/main/document-list", obj);
	}
	
}
