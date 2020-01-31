package pkg.analytics2;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import pkg.beans.Kpi;
import pkg.beans.Stores;
import pkg.beans.Tables;
import pkg.beans.Users;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class DataExport extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		HttpSession session = req.getSession();
		//Legge il file di properties associato alla lingua sul profilo
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");

		List<Tables> kpi = new ArrayList<Tables>();
		List<Tables> sto = new ArrayList<Tables>();
		List<Tables> ev = new ArrayList<Tables>();
		
		// Pulisce la lista
		kpi.clear();
		sto.clear();
		ev.clear();
		
		Connection conn = null;
		PreparedStatement st = null;
		
		ResultSet rs = null;
		
		String sql = "";
		
		int count = 0;
		String msg = "";
		
		String q = ""+req.getParameter("q");
		q=Utility.noNull(q);
		
		String whereIAm = req.getParameter("app");
		obj.put("whereIAm", whereIAm);
		
		
		try {
			// Apre la connessione
			conn = Utility.getConnData();
			
			// token per ws 
			sql   = "select wskey, wsdecrypt_key, wscrypt, account_code from customers "+
					"where id = "+session.getAttribute("idcus")+" ";
			st = conn.prepareStatement(sql);
			rs=st.executeQuery();
			String wskey = "";
			String account_code = "";
			if(rs.next()) {
				wskey = rs.getString("wskey");
				account_code = rs.getString("account_code");
			}
			rs.close();
			obj.put("wskey", wskey);
			obj.put("account_code", account_code);
			obj.put("id_customer", session.getAttribute("idcus").toString());
			
			// ==================
			// Lista kpi
			sql   = "select trim(xcdel) xcdel, xdtab, xdesc, coalesce( trim(c.kpi) , '') idkpi "+
					"from ztables a , rel_cus_kpi b "+
					"left join pref_user_kpi c on trim(b.idkpi) = trim(c.kpi) "+
					"and c.idusr = '"+session.getAttribute("username")+"' and c.idcus = "+session.getAttribute("idcus")+" "+
					"where xcdtb = 'KPI' and b.idcus = "+session.getAttribute("idcus")+" "+
					"and trim(xcdel) = trim(b.idkpi) ";
			
			// Prepara il cursore
			st = conn.prepareStatement(sql);
			
			// Esegue l'sql
			rs=st.executeQuery();
			
			// Campi datebase
			String chk = "";
			String xdesc = "";
			String info = "";
			while(rs.next()) {
				
				xdesc = ""+rs.getString("xdesc");
				xdesc = Utility.noNull(xdesc);
				
				if(xdesc.trim().length()>0) {
					info = "<span class=\"pull-right\" data-toggle=\"popover\" "+
							"data-html=\"true\" data-placement=\"left\" data-content=\"<div class='scrollable' style='height:40px'>"+xdesc+"</div>\" title=\"\" data-original-title='<button type=\"button\" class=\"close pull-right\" data-dismiss=\"popover\">&times;</button>"+rs.getString("xcdel")+"'><i class=\"icon-info\"></i></span>";
				}
				
				if(rs.getString("xcdel").equals(rs.getString("idkpi"))) {
					chk = "checked";
				}
				count+=1;
				
				kpi.add(new Tables("KPI",rs.getString("xcdel"),rs.getString("xdtab"),info,"","",""));
				
			}
			rs.close();
			// Fine Lista Kpi
			// ==================
			
			
			// ==================
			// Lista eventi
			sql   = "select * "+
					"from raw_data_events ";
			
			// Prepara il cursore
			st = conn.prepareStatement(sql);
			
			// Esegue l'sql
			rs=st.executeQuery();
			
			while(rs.next()) {
				
				ev.add(new Tables("EV",rs.getString("code"),rs.getString("description"),"","","",""));
				
			}
			rs.close();
			// Fine Lista Eventi
			// ==================
			
			
			// ==================
			// Lista negozi
			sql   = "select * "+
					"from stores where id_customer = "+session.getAttribute("idcus")+" ";
			
			// Prepara il cursore
			st = conn.prepareStatement(sql);
			
			// Esegue l'sql
			rs=st.executeQuery();
			
			while(rs.next()) {
				
				sto.add(new Tables("STO",rs.getString("id"),rs.getString("store_name"),rs.getString("store_id"),"","",""));
				
			}
			rs.close();
			// Fine Lista Eventi
			// ==================
			
			
			
			if(st!=null)
				st.close();
			
			conn.close();
		}
		catch(Exception e) {
			e.printStackTrace();
			
			// Messaggio
			msg = "<div class=\"alert alert-danger\"><strong>Attenzione</strong><br/>Si e&grave; verificato un errore!<br/><br/>Errore:<br/>"+e+"</div>";			
		}
		
		obj.put("dsfun", "Lista Kpi");
		obj.put("kpi", kpi);
		obj.put("sto", sto);
		obj.put("ev", ev);
		
		obj.put("count", count);
		obj.put("msg", msg);
		
		return new ModelAndView("/analytics2/main/data-export", obj);
	}
	
}
