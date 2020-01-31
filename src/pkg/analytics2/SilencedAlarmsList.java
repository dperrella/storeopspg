package pkg.analytics2;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
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

import pkg.beans.Function;
import pkg.beans.Tables;
import pkg.beans.Tables2;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class SilencedAlarmsList extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		// Formato numero
		java.text.DecimalFormat df = new java.text.DecimalFormat("###,###,##0.00");
				
		HttpSession session = req.getSession();
		//Legge il file di properties associato alla lingua sul profilo
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");
		
		List<Tables2> alarms = new ArrayList<Tables2>();
		
		// Formato data
		DateFormat dateIta = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		String oggi = dateIta.format(date);
		
		// Pulisce la lista
		alarms.clear();
		
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		
		String sql = "";
		String cond = "";
		String cond2= "";
		String idcus = ""+session.getAttribute("idcus");
		idcus = Utility.noNull(idcus);
		if(idcus.trim().length()>0)
			cond2 = "and id_customer = "+idcus+" ";
		
		int count = 0;
		String msg = "";
		
		String q = ""+req.getParameter("q");
		q=Utility.noNull(q);
		
		String order = ""+req.getParameter("order");
		order=Utility.noNull(order);
		String sort = ""+req.getParameter("sort");
		sort=Utility.noNull(sort);
	
		if(order.length()>0) {
			order = "order by "+order+" "+sort;
		}
		else
			order = "order by fromdate desc ";
		
		if(sort.length()==0)
			sort = "desc";
		else
			sort = "";
		
		obj.put("sort", sort);
		
		// Paginazione
		String pag = ""+req.getParameter("pag");
		pag = Utility.noNull(pag);

		if(pag.length()==0) {
			pag = "1";
		}
		
		System.out.println(pag);
		
		int rowIni = 0;
		int rowFin = 0;
		int nrPag = 0;

		int numRow = 0;
		String prev = "";
		String next = "";
		String nrRecord = "20";
		
		try {
			
			if(q.length()>0) {
				cond+=" and concat(upper(s.description) , ' ' , upper(c.description)) like '%"+q.toUpperCase()+"%' ";
			}
			
			conn = Utility.getConnData();
			st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					  ResultSet.CONCUR_UPDATABLE);
			
			// Numero di record totali
			sql  =  "select count(*) num " + 
					"from silenced_trigger, stores2 s " + 
					"where id_store in(select idstore from rel_user_stores " + 
					"where idusr = '"+(String)session.getAttribute("username")+"') "+
					"and s.id = id_store "+cond2+" "+" "+cond;
			System.out.println(sql);
			rs=st.executeQuery(sql);
			if(rs.next()) {
				numRow=rs.getInt("num");
			}
			rs.close();
			
			nrPag = (int) Math.ceil(numRow/Double.parseDouble(nrRecord));
			
			int max = 0;
			
			for (int i=1; i<=nrPag; i++) {
				
				max+=Integer.parseInt(nrRecord);
				
				if(i==Integer.parseInt(pag)) {
					
					prev = ""+(i-1);
					next = ""+(i+1);
					
					rowIni = (max-Integer.parseInt(nrRecord));
					rowFin = Integer.parseInt(nrRecord);
					
				}
			}
			
			System.out.println(nrPag);
			
			sql  =  "select b.id,a.id_store,fromdate, todate, code, c.description, s.description dssto "
					+ "from silenced_trigger a, stores2 s, an_alarm_report b "
					+ "left join lang_alarm_report c on b.id = id_alarm_report and lang = '"+session.getAttribute("language").toString()+"' "
					+ "where a.id_store in(select idstore from rel_user_stores "
					+ "where idusr = '"+(String)session.getAttribute("username")+"') "
					+ "and a.id_trigger = b.id and s.id = a.id_store "+cond2+" "+cond+" "
					+ " "+order+" "
					+ "limit "+rowFin+" offset "+rowIni+" ";
			System.out.println(sql);
			
			// Prepara il cursore
			st = conn.createStatement();
		
			// Esegue l'sql
			rs=st.executeQuery(sql);
			
			String $fromdate = "";
			String $todate = "";
			String $code = "";
			String $description = "";
			String $dssto = "";
			String $id_trigger = "";
			String $id_store = "";
			
			String button = "";
			while(rs.next()) {
				
				$fromdate = rs.getString("fromdate");
				$todate = rs.getString("todate");
				$code = rs.getString("code");
				$description = rs.getString("description");
				$dssto = rs.getString("dssto");
				$id_trigger = rs.getString("id");
				$id_store = rs.getString("id_store");
				
				button = "<div class=\"checkbox i-checks\">\r\n" + 
						"                              <label>\r\n" + 
						"                                <input type=\"checkbox\" name=\"chk\" value=\""+rs.getString("id")+";"+rs.getString("id_store")+"\"><i></i>\r\n" + 
						"                              </label>\r\n" + 
						"                            </div>";
				
				alarms.add(new Tables2("","","",
						$code, $description, $dssto,
						$fromdate, $todate, $id_trigger, $id_store,
						button, "", ""));
			
			}
			
			rs.close();
			st.close();
			
			conn.close();
		}
		catch(Exception e) {
			e.printStackTrace();
			
			// Messaggio
			msg = "<div class=\"alert alert-danger\"><strong>Attenzione</strong><br/>Si e&grave; verificato un errore!<br/><br/>Errore:<br/>"+e+"</div>";			
		}
		
		obj.put("dsfun", "Silenced Alarms");
		obj.put("alarms", alarms);
		obj.put("count", count);
		obj.put("msg", msg);
		
		obj.put("tot", numRow);
		obj.put("nrPag", nrPag);
		obj.put("prev", prev);
		obj.put("next", next);
		obj.put("ini", rowIni);
		obj.put("fin", rowFin);
		
		
		return new ModelAndView("/analytics2/main/silenced-alarms-list", obj);
	}
	
}
