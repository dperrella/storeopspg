package pkg.pos;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import groovy.ui.Console;

import pkg.properties.ReadProperties;
import pkg.utility.Utility;
import pkg.beans.pos.Sm_Listlogs;
import pkg.beans.pos.Sm_Listupdat;
import pkg.beans.pos.Sm_DevnotP;
import pkg.beans.pos.Sm_DevnotP2;
import pkg.beans.pos.Sm_Peoplnu;
import pkg.beans.pos.Sm_DevnotE;
import pkg.beans.pos.Stores_;

import com.google.gson.Gson;

public class reportlist extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		Map<String, Object> obj = new HashMap<String, Object>();
		
		List<Sm_Listlogs> listlog = new ArrayList<Sm_Listlogs>();
		List<Sm_Listupdat> listup = new ArrayList<Sm_Listupdat>();
		List<Sm_DevnotP> devnp = new ArrayList<Sm_DevnotP>();
		List<Sm_DevnotP2> devnp2 = new ArrayList<Sm_DevnotP2>();
		List<Sm_Peoplnu> pepl = new ArrayList<Sm_Peoplnu>();
		List<Sm_DevnotE> devne = new ArrayList<Sm_DevnotE>();
		
		listlog.clear();
		listup.clear();
		devnp.clear();
		devnp2.clear();
		pepl.clear();
		devne.clear();
		
		HttpSession session = req.getSession();
		
		String ling = session.getAttribute("language").toString();

		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");
		
		/**/
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		conn = Utility.getConnPos();
		st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		String sql = "";
		String id_ = "";
		String idC = "";
		String RidC = "";
		String idCountry = "";
		String param = "";
		String param_ = "";
		String id_customer = "";
		String stores_ = "";
		String box1 = "";
		String box2 = "";
		String box3 = "";
		String box4 = "";
		String tab1 = "";
		String tab2 = "";		
		String tab3 = "";
		String tab4 = "";
		String tab5 = "";		
		String timing = "";
		

		String filezpl = "Ciao Pietro";


		String seq_p_n = "", seq_p_s = ""; 
		/**/
		try {

		String us = session.getAttribute("username").toString().toUpperCase();

		sql = "SELECT last_value from package_number ";
		
		rs = st.executeQuery(sql);
		while(rs.next()) {
			seq_p_n = rs.getString("last_value");
		}
		rs.close();
		//fine lista log
		sql = "SELECT last_value from package_session ";
		
		rs = st.executeQuery(sql);
		while(rs.next()) {
			seq_p_s = rs.getString("last_value");
		}
		rs.close();

		
		
		String a = "", b = "", c = "", d = "", e = "";
		sql = "select * from package order by datetime desc";
		
		if (!"".equals(param)){
			sql = sql + param;
		}
		
		rs = st.executeQuery(sql);
		while(rs.next()) {
			a = rs.getString("datetime");
			b = rs.getString("package_session");
			c = rs.getString("package_number");
			d = rs.getString("percorso");
			e = rs.getString("percorsodown");
			pepl.add(new Sm_Peoplnu(a,b,c,d,e));
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
		
		obj.put("Pepl", pepl);
		session.setAttribute("RIDC", RidC);
		session.setAttribute("Box1", box1);
		session.setAttribute("Box2", box2);
		session.setAttribute("Box3", box3);
		session.setAttribute("Box4", box4);
		session.setAttribute("Tab1", tab1);
		session.setAttribute("Tab2", tab2);
		session.setAttribute("Tab3", tab3);
		session.setAttribute("Tab4", tab4);
		session.setAttribute("Tab5", tab5);
		session.setAttribute("Timing", timing);
		session.setAttribute("FileZPL", filezpl);
		session.setAttribute("SeqN", seq_p_n);
		session.setAttribute("SeqS", seq_p_s);
		
		
		return new ModelAndView("/pos/main/report-list", obj);

	}
	
}

