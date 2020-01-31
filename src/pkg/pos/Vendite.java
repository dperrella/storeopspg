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
import java.util.GregorianCalendar;
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

public class Vendite extends AbstractController {
	
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
				
		String combocli = "";
		String list_app_store = "";
		
		String sql = "";
		Connection connext = null;
		Connection conn = null;
		Statement st = null;
		Statement st2 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		
		
		try {
		
		connext = Utility.getConnPosext();
		st = connext.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		
		conn = Utility.getConnPos();
		st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

		
		sql = "select * from rel_user_stores where upper(idusr) = '"+username.toUpperCase()+"'";
		//System.out.println("sql1 = " + sql);
		rs = st.executeQuery(sql);
		while(rs.next()) {
			/*
			if ("".equals(list_app_store)){
				list_app_store = "'"+rs.getString("idstore")+"'";
			}else{
				list_app_store = list_app_store + ",'" + rs.getString("idstore")+"'";
			}
			*/
			combocli = combocli + "<option value='"+rs.getString("idstore")+"'>"+rs.getString("idstore").toUpperCase()+"</option>";
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
		
		Calendar now = GregorianCalendar.getInstance();
		DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		//Date date = format.parse(data_);
		//now.setTime(date);
		
		int year = 0;
		int month = 0;
		int day = 0;
		
		year = now.get(Calendar.YEAR);
		month = now.get(Calendar.MONTH);
		day = now.get(Calendar.DAY_OF_MONTH);
		month = month + 1;	
		
		Calendar cal = Calendar.getInstance();
	    cal.set(Calendar.YEAR, year);
	    cal.set(Calendar.MONTH, Calendar.DECEMBER);
	    cal.set(Calendar.DAY_OF_MONTH, 31);
		
	    int ordinalYear = Calendar.getInstance().get(Calendar.YEAR);
	    int ordinalDay = cal.get(Calendar.DAY_OF_YEAR);
	    int weekDay = cal.get(Calendar.DAY_OF_WEEK) - 1; // Sunday = 0
	    int numberOfWeeks = (ordinalDay - weekDay + 10) / 7;
		
	    String[] types_ = new String[100];
	   // System.out.println("number of week = " + numberOfWeeks);
		String StoresDate = "";
		
		String sm_week_dis_da = " | Settimana dal: ";
		String sm_week_dis_a = " al: ";
	    
        for (int p = 1; p <= numberOfWeeks; p++){
        	String dates = GetDates(p, ordinalYear);
        	//System.out.println("dates = "+dates);
        		types_ = dates.split("£");
        		StoresDate = StoresDate + "<option value="+p+" value2="+types_[0]+" value3="+types_[1]+">"+p+sm_week_dis_da+Utility.getTurnDate(types_[0])+sm_week_dis_a+Utility.getTurnDate(types_[1])+"</option>";
        }
        
        
        
	    
        obj.put("ComboCli", combocli);
		obj.put("Listlog", listlog);
		obj.put("username", username);
		obj.put("desc", desc);
		obj.put("tipo", tipo);
		obj.put("foto", foto);
		obj.put("mail", mail);
		obj.put("StoresDate", StoresDate);
		
		
		
		return new ModelAndView("/pos/main/vendite", obj);
	}
	
	
	
	private String GetDates(int noW, int yeaR){
		String Dates = "";
		String Dates1;
		String Dates2;
	
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, yeaR);        
		cal.set(Calendar.WEEK_OF_YEAR, noW);        
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		Dates1 = sdf.format(cal.getTime());

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");   
		cal.add(Calendar.DATE, 6);    
		String convertedDate=dateFormat.format(cal.getTime());    
		Dates2 = convertedDate;
		
		Dates = Dates1 + "£" + Dates2;
		
		//System.out.println("dates = " + Dates);
		
		return Dates;
	}	
	
	
}
