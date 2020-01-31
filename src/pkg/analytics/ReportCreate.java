package pkg.analytics;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import pkg.beans.Eventcode;
import pkg.beans.Tables;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class ReportCreate extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		HttpSession session = req.getSession();
		//Legge il file di properties associato alla lingua sul profilo
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");

		List<Tables> rep_type = new ArrayList<Tables>();
		List<Eventcode> ec = new ArrayList<Eventcode>();
		
		rep_type.clear();
		ec.clear();
		
		// Formato data
		DateFormat dateNum = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		String oggi = dateNum.format(date);
				
		// campi passati dalla session
		String username = (String) session.getAttribute("username");
		String language = (String) session.getAttribute("language");
		
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		String sql = "";
		String msg = "";
		
		
		// REQUEST Dalla pagina
		// ------------
		// report_type
		String azione=""+req.getParameter("azione");
		azione=Utility.noNull(azione);
		
		try {
			
			ReadProperties prop = new ReadProperties("connection.properties");
			conn = Utility.getConnAnalytics();
			
			if(azione.length()>0) {
				
				String id="";
				String report_type=req.getParameter("report_type");
				String subreport_type=req.getParameter("subreport_type");
				String code=req.getParameter("code");
				String description_it=req.getParameter("description_it");
				String description_en=req.getParameter("description_en");
				String alarm1=req.getParameter("alarm1");
				String alarm2=req.getParameter("alarm2");
				String confronto=""+req.getParameter("confronto");
				confronto=Utility.noNull(confronto);
				String icon=req.getParameter("icon");
				
				boolean historic = false;
				boolean benchmark = false;
				
				if(confronto.equals("H")) {
					historic=true;
					benchmark=false;
				}
				if(confronto.equals("B")) {
					benchmark=true;
					historic=false;
				}
				
				
				sql   = "insert into an_report "+
						"(code, description, id_report_type, id_subreport_type, alarm1, alarm2, storico, benchmark, icon) "+
						"values "+
						"(?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING id ";
				
				// Prepara il cursore
				st = conn.prepareStatement(sql);
				
				// Passa i parametri
				st.setString(1, code);
				st.setString(2, description_it);
				st.setInt(3, Integer.parseInt(report_type));
				st.setInt(4, Integer.parseInt(subreport_type));
				st.setString(5, alarm1);
				st.setString(6, alarm2);
				st.setBoolean(7, historic);
				st.setBoolean(8, benchmark);
				st.setString(9, icon);
				
				// Esegue l'sql
				rs = st.executeQuery();
				
				if(rs.next()) {
					id = rs.getString(1);
					System.out.println("ID: "+rs.getString(1));
				}
				else {
					id = "-1";
					System.out.println("ID: -1");
				}
				rs.close();
				st.close();
				
				// -----------------
				
				// Inserisco i record della lingua
				sql   = "insert into lang_report "+
						"(id_report, lang, description) "+
						"values "+
						"(?, ?, ?) ";
				// Prepara il cursore
				st = conn.prepareStatement(sql);
				
				// Passa i parametri
				st.setString(1, id);
				st.setString(2, "IT");
				st.setString(3, description_it);
				
				// Esegue l'sql
				st.executeUpdate();
				
				// -----------------
				
				// Inserisco i record della lingua
				sql   = "insert into lang_report "+
						"(id_report, lang, description) "+
						"values "+
						"(?, ?, ?) ";
				// Prepara il cursore
				st = conn.prepareStatement(sql);
				
				// Passa i parametri
				st.setString(1, id);
				st.setString(2, "EN");
				st.setString(3, description_en);
				
				// Esegue l'sql
				st.executeUpdate();
				
				// -----------------
				
				msg="<div class=\"alert alert-success\"><strong>"+propLang.get("msgSuccess")+"</strong></div>"+
						"<div class=\"line line-dashed b-b line-lg pull-in\"></div>";
			}
			
			// =================
			// lista eventcode
			/*
			sql   = "select c.* "+
					"from rel_user_customers a, rel_customer_alarms b, "+
					"analytics_eventcode c "+
					"where a.idcustomer = b.idcustomer "+
					"and c.code = b.alarmcode "+
					"and a.idusr = ? ";	
			// Prepara il cursore
			st = conn.prepareStatement(sql);
			
			st.setString(1, (String)session.getAttribute("username"));
			*/
			sql  =  "select * from analytics_eventcode order by CAST(coalesce(code, '0') AS integer) ";
			
			// Prepara il cursore
			st = conn.prepareStatement(sql);
			
			// Esegue l'sql
			rs=st.executeQuery();
			
			// Campi database
			String $code="";
			String $description="";
			String $range_description="";
		
			String $cmp1="";
			String $cmp2="";
			String $cmp3="";
			String $cmp4="";
			
			while(rs.next()) {
				
				$code=rs.getString("code");
				$description=rs.getString("description");
				$range_description=rs.getString("range_description");
				
				ec.add(new Eventcode($code,$description,$range_description,$cmp1,$cmp2,$cmp3,$cmp4));
				
			}
			rs.close();
			st.close();
			
			
			// =================
			// lista report_type 
			sql   = "select a.id, b.description "+
					"from an_report_type a, lang_report_type b "+
					"where b.lang = '"+language+"' and a.id=b.id_report_type ";
					
			// Prepara il cursore
			st = conn.prepareStatement(sql);
			
			// Esegue l'sql
			rs=st.executeQuery();
			
			String $id_report_type = "";
			String $ds_report_type = "";
			
			while(rs.next()) {
				
				$id_report_type = rs.getString("id");
				$ds_report_type = rs.getString("description");
				
				rep_type.add(new Tables("",$id_report_type,$ds_report_type,"","","",""));
				
			}
			rs.close();
			st.close();
			
			conn.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		obj.put("rep_type", rep_type);
		obj.put("ec", ec);
		
		obj.put("msg", msg);
		
		
		return new ModelAndView("/app/report/report-create", obj);
	}
	
}
