package pkg.analytics;

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

import pkg.beans.Customers;
import pkg.beans.Report;
import pkg.beans.Roles;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class RelReportUserList extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		HttpSession session = req.getSession();
		//Legge il file di properties associato alla lingua sul profilo
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");

		String language = (String) session.getAttribute("language");
		
		List<Report> rep = new ArrayList<Report>();
		List<Customers> cus = new ArrayList<Customers>();
		List<Roles> rol = new ArrayList<Roles>();
		
		
		// Pulisce la lista
		rep.clear();
		cus.clear();
		rol.clear();
		
		Connection conn = null;
		Connection connAdmin = null;
		PreparedStatement st = null;
		PreparedStatement stAdmin = null;
		
		
		ResultSet rs = null;
		
		String sql = "";
		
		int count = 0;
		String msg = "";
		
		String q = ""+req.getParameter("q");
		q=Utility.noNull(q);
		
		String id_customer = ""+req.getParameter("id_customer");
		id_customer = Utility.noNull(id_customer);
		
		String azione = ""+req.getParameter("azione");
		azione = Utility.noNull(azione);
		
		// Report
		String id_report = ""+req.getParameter("idreport");
		id_report = Utility.noNull(id_report);
		// Roles
		String id_role = ""+req.getParameter("id_role");
		id_role = Utility.noNull(id_role);
				
		obj.put("id_customer", id_customer);
		obj.put("id_role", id_role);
		
		if(id_role.length()==0)
			id_role = "0";
		if(id_customer.length()==0)
			id_customer = "0";
		
		try {
			// Apre la connessione
			conn = Utility.getConnAnalytics();
			connAdmin = Utility.getConnAdmin();
			
			if(azione.length()>0) {
				
				// Cancella il contenuto della tabella an_report_user per il report
				sql   = "delete from an_report_user where id_customer = ? and id_role = ? ";
				// Prepara il cursore
				st = conn.prepareStatement(sql);
				
				// Passa i parametri
				st.setInt(1, Integer.parseInt(id_customer));
				st.setInt(2, Integer.parseInt(id_role));
				
				// Esegue l'sql
				st.executeUpdate();
					
				String temp[] = id_report.split(",");
				
				for(int c=0; c<temp.length; c++) {
					
					if(temp[c].length()>0) {
						
						sql   = "insert into an_report_user "+
								"(id_report, id_role, id_customer) "+
								"values "+
								"(?, ?, ?) ";
						// Prepara il cursore
						st = conn.prepareStatement(sql);
						
						// Passa i parametri
						st.setString(1, temp[c]);
						st.setInt(2, Integer.parseInt(id_role));
						st.setInt(3, Integer.parseInt(id_customer));
						
						// Esegue l'sql
						st.executeUpdate();
					
					}
					
				}
				
				msg="<div class=\"line line-dashed b-b line-lg pull-in\"></div>"+
						"<div class=\"alert alert-success\"><strong>"+propLang.get("msgSuccess")+"</strong></div>";
				
			}
			
			
			
			// ==================
			// Lista Reports
			// sql da eseguire
			sql  =  "select a.id, x.id_report, a.code, b.description, a.id_report_type, a.id_subreport_type, "+
					"alarm1, alarm2, storico, benchmark, icon, c.description ds_report_type, "+
					"d.description ds_subreport_type "+
					"from an_report a "+
					"left join an_report_user x on a.id = x.id_report and x.id_customer = '"+id_customer+"' and x.id_role = "+id_role+" "+
					", lang_report b, lang_report_type c, lang_subreport_type d "+
					"where a.id = b.id_report and b.lang = '"+language+"' "+
					"and a.id_report_type = c.id_report_type and c.lang = '"+language+"' "+
					"and a.id_subreport_type = d.id_subreport_type and d.lang = '"+language+"' ";
			System.out.println(sql);
			// Prepara il cursore
			st = conn.prepareStatement(sql);
			
			// Esegue l'sql
			rs=st.executeQuery();
			
			// Campi database
			String $id="";
			String $code="";
			String $description="";
			String $id_report_type="";
			String $ds_report_type="";
			String $id_subreport_type="";
			String $ds_subreport_type="";
			String $alarm1="";
			String $alarm2="";
			String $historic="";
			String $benchmark="";
			String $icon="";
			
			String $cmp1="";
			String $cmp2="";
			String $cmp3="";
			String $cmp4="";
			
			while(rs.next()) {
				
				count+=1;
				
				if(rs.getString("id").equals(rs.getString("id_report"))) {
					$cmp1 = "checked";
				}
				count+=1;
				
				$id=rs.getString("id");
				$code=rs.getString("code");
				$description=rs.getString("description");
				$id_report_type=rs.getString("id_report_type");
				$ds_report_type=rs.getString("ds_report_type");
				$id_subreport_type=rs.getString("id_subreport_type");
				$ds_subreport_type=rs.getString("ds_subreport_type");
				$alarm1=rs.getString("alarm1");
				$alarm2=rs.getString("alarm2");
				$historic=rs.getString("storico");
				$benchmark=rs.getString("benchmark");
				$icon=rs.getString("icon");
				
				rep.add(new Report($id,$code,$description,$id_report_type,$ds_report_type,
						$id_subreport_type,$ds_subreport_type,$alarm1,$alarm2,
						$historic,$benchmark,$icon,$cmp1,$cmp2,$cmp3,$cmp4));
				
				$cmp1 = "";
			}
			rs.close();
			st.close();
			// Fine Lista Reports
			// ==================
			
			// sql da eseguire
			sql  =  "select * from roles ";
			
			// Prepara il cursore
			stAdmin = connAdmin.prepareStatement(sql);
			
			// Esegue l'sql
			rs=stAdmin.executeQuery();
			
			// Campi database
			String $rol_id="";
			String $name="";
			String $rol_description="";
			String $is_admin="";
		
			$cmp1="";
			$cmp2="";
			$cmp3="";
			$cmp4="";
			
			while(rs.next()) {
				
				$rol_id=rs.getString("id");
				$name=rs.getString("name");
				$rol_description=rs.getString("description");
				$is_admin="";
				
				rol.add(new Roles($rol_id,$name,$rol_description,$is_admin,$cmp1,$cmp2,$cmp3,$cmp4));
				
			}
			rs.close();
			
			// sql da eseguire
			sql  =  "select a.*, b.id_customer from customers a "
				+ "left join an_report_user b on a.id = b.id_customer and b.id_report = '"+id_report+"' and b.id_role = "+id_role+" ";
			
			System.out.println(sql);
			
			// Prepara il cursore
			st = conn.prepareStatement(sql);
			
			// Esegue l'sql
			rs=st.executeQuery();
			
			// Campi database
			String $companyname;
			String $cus_description;
			String $contact;
			String $brandname;
			String $brandlogo;
			String $account_code;
			String $cus_id;
			String $phone;
			String $mail;
			String $mobile;
			String $id_optempl;
			String $customer_country_id;
			String $agreement;
			String $wskey;
			String $wsdecrypt_key;
			String $wscrypt;
			
			$cmp1="";
			$cmp2="";
			$cmp3="";
			$cmp4="";
			
			while(rs.next()) {
				
				if(rs.getString("id").equals(rs.getString("id_customer"))) {
					$cmp1 = "checked";
				}
				count+=1;
				
				$companyname=rs.getString("companyname");
				$cus_description=rs.getString("description");
				$contact=rs.getString("contact");
				$brandname=rs.getString("brandname");
				$brandlogo=rs.getString("brandlogo");
				$account_code=rs.getString("account_code");
				$cus_id=rs.getString("id");
				$phone=rs.getString("phone");
				$mail=rs.getString("mail");
				$mobile=rs.getString("mobile");
				$id_optempl=rs.getString("id_optempl");
				$customer_country_id=rs.getString("customer_country_id");
				$agreement=rs.getString("agreement");
				$wskey=rs.getString("wskey");
				$wsdecrypt_key=rs.getString("wsdecrypt_key");
				$wscrypt=rs.getString("wscrypt");
				
				
				cus.add(new Customers($companyname,$cus_description,$contact,$brandname,
						$brandlogo,$account_code,$cus_id,$phone,$mail,$mobile,$id_optempl,
						$customer_country_id,$agreement,$wskey,$wsdecrypt_key,$wscrypt,
						$cmp1,$cmp2,$cmp3,$cmp4));
				
				$cmp1 = "";
				
			}
			rs.close();
			
			if(st!=null)
				st.close();
			if(stAdmin!=null)
				stAdmin.close();
			conn.close();
			connAdmin.close();
		}
		catch(Exception e) {
			e.printStackTrace();
			
			// Messaggio
			msg = "<div class=\"alert alert-danger\"><strong>Attenzione</strong><br/>Si e&grave; verificato un errore!<br/><br/>Errore:<br/>"+e+"</div>";			
		}
		
		obj.put("dsfun", "Report / Utenti");
		obj.put("rep", rep);
		obj.put("cus", cus);
		obj.put("rol", rol);
		
		obj.put("count", count);
		obj.put("msg", msg);
		
		return new ModelAndView("/app/rel/rel_report_user-list", obj);
	}
	
}
