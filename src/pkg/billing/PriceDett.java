package pkg.billing;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import pkg.beans.Users;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class PriceDett extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		HttpSession session = req.getSession();
		//Legge il file di properties associato alla lingua sul profilo
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");
		
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		/**/
		Connection conn2 = null;
		PreparedStatement st2 = null;
		ResultSet rs2 = null;		
		/**/
		
		String sql = "";
		
		int count = 0;
		String msg = "";
		
		String tipoForm = req.getParameter("tipoForm");
		String azione = ""+req.getParameter("azione");
		azione = Utility.noNull(azione);
		
		String btnActive = "";
		String key = "";
		
		DateFormat iso = new SimpleDateFormat("yyyy-MM-dd");
		
		String code = "";
		boolean cal = false;
		String col="";
		String sym="";
		String sign="";
		String kpi1="";
		String kpi2="";
		/**/
		
		// Campi database
		String $code="";
		String $codeold="";
		String $name="";
		String $desc="";
		String $symb="";
		String $calc="";
		boolean $calc_=false;
		String active = "";
		String purchase_price_code="";
		String sell_price_code="";
		String billing="";
		
		String $cod = "";
		String $des = "";
		boolean $act = true;
		
		String $pur = "";
		String $sel = ""; 
		String description = "";
		String $bil = "";
		String accountposition = "";
		String storeposition = "";
		String likestring = "";
		int $iid_ = 0;
		int $bil_ = 0;
		
		try {
			
			conn = Utility.getConnAdmin();
			conn2 = Utility.getConnAdmin();
			
			if(azione.length()>0) {
				
				if(tipoForm.equals("I")) {
					
					// Inserimento
					System.out.println("INS...");
					
					$cod = Utility.noNull(""+req.getParameter("code"));
					$des = Utility.noNull(""+req.getParameter("description"));
					$pur = Utility.noNull(""+req.getParameter("validity_date"));
					$sel = Utility.noNull(""+req.getParameter("currency_code"));
					$bil = Utility.noNull(""+req.getParameter("price_type"));
					$act = Boolean.parseBoolean(req.getParameter("active"));

					
						sql   = "INSERT INTO public.price_list(code, description, validity_date, currency_code, price_list_type, active) "+
								"    VALUES (?, ?, ?, ?, ?, ?);";
						
						// Prepara il cursore
						st = conn.prepareStatement(sql); 
						// Passa i parametri
						
						st.setString(1, $cod);
						st.setString(2, $des);
						st.setDate(3, java.sql.Date.valueOf($pur));
						st.setString(4, $sel);
						st.setString(5, $bil);
						st.setBoolean(6, $act);
					
						// Esegue l'sql
						System.out.println("payment_type_ins = " + sql);
						st.executeUpdate();

					msg="<div class=\"alert alert-success\"><strong>"+propLang.get("msgSuccess")+"</strong></div>"+
						"<div class=\"line line-dashed b-b line-lg pull-in\"></div>";
					tipoForm = "M";
					//tipoForm = "I";
									
				}
				else if(tipoForm.equals("M")) {
					
					
					// Modifica
					System.out.println("MOD...");
					
					$cod = Utility.noNull(""+req.getParameter("code"));
					$des = Utility.noNull(""+req.getParameter("description"));
					$pur = Utility.noNull(""+req.getParameter("validity_date"));
					$sel = Utility.noNull(""+req.getParameter("currency_code"));
					$bil = Utility.noNull(""+req.getParameter("price_type"));
					$act = Boolean.parseBoolean(req.getParameter("active"));
					$codeold = Utility.noNull(""+req.getParameter("idcard"));
					
					if ("".equals($codeold)) {
						$codeold = $cod;
					}
					
					sql  =  "UPDATE public.price_list SET code=?, description=?, validity_date=?, currency_code=?, price_list_type=?, active=? "+
							"   where code = ? ";
					// Prepara il cursore
					st = conn.prepareStatement(sql);
					
					// Passa i parametri			
					st.setString(1, $cod);
					st.setString(2, $des);
					st.setDate(3, java.sql.Date.valueOf($pur));
					st.setString(4, $sel);
					st.setString(5, $bil);
					st.setBoolean(6, $act);
					st.setString(7, $codeold);
					
					System.out.println("sql = ..." + sql);
					// Esegue l'sql
					st.executeUpdate();			
					
					msg="<div class=\"alert alert-success\"><strong>"+propLang.get("msgSuccess")+"</strong></div>"+
						"<div class=\"line line-dashed b-b line-lg pull-in\"></div>";

					tipoForm = "M";
						
				}
				else if(tipoForm.equals("E")) {
					
					// Cancellazione
					System.out.println("ELI...");
					
					$cod = Utility.noNull(""+req.getParameter("idcard"));
					
					if ("".equals($cod)) {
						$cod = Utility.noNull(""+req.getParameter("code"));
					}
					
					sql   = "delete from price_list where code = ? ";
					// Prepara il cursore
					st = conn.prepareStatement(sql);
					
					// Passa i parametri
					st.setString(1, $cod);
					
					// Esegue l'sql
					st.executeUpdate();
					
					msg="<div class=\"alert alert-success\"><strong>"+propLang.get("msgSuccess")+"</strong></div>"+
						"<div class=\"line line-dashed b-b line-lg pull-in\"></div>";
					tipoForm = "I";
					
				}
				
			}
			
			if(tipoForm.equals("V")||tipoForm.equals("M")||tipoForm.equals("E")) {
				
				key = "readonly";
				
				
				// Messaggio conferma cancellazione record
				if(tipoForm.equals("E")) {
					msg = "<div class=\"alert alert-warning\"><strong>"+propLang.get("msgDelete")+"</strong></div>";	
				}
				// Bottone non attivo in visualizzazione
				if(tipoForm.equals("V")) {
					btnActive = "disabled";	
				}

				$cod = Utility.noNull(""+req.getParameter("idcard"));
				
				if ("".equals($cod)) {
					$cod = Utility.noNull(""+req.getParameter("code"));
				}
				// ===================
				// Dettaglio / Modifica / Elimina ruolo
				sql  =  "select * from price_list where code = ? ";
				
				// Prepara il cursore
				st = conn.prepareStatement(sql);
				st.setString(1, $cod);

				// Esegue l'sql
				System.out.println("sql Read = " + sql);
				rs=st.executeQuery();
				
				if(rs.next()) {
					code=rs.getString("code");
					description=rs.getString("description");
					purchase_price_code=rs.getString("validity_date");
					sell_price_code=rs.getString("currency_code");
					billing=rs.getString("price_list_type");
					active=rs.getString("active");
				}
				rs.close();

				
			}
			
			
			
			/**/
			
			String Prices = "";
			sql  =  "select * from price_list_type where active = true";
			st = conn.prepareStatement(sql);
			rs=st.executeQuery();
			while(rs.next()) {
				Prices += "<option value='"+rs.getString("code")+"'>"+rs.getString("description")+"</option>";
			}
			rs.close();			
			obj.put("Prices", Prices);
			
			String Currency = "";
			sql  =  "select * from currency where active = true";
			st = conn.prepareStatement(sql);
			rs=st.executeQuery();
			while(rs.next()) {
				Currency += "<option value='"+rs.getString("code")+"'>"+rs.getString("description")+"</option>";
			}
			rs.close();			
			obj.put("Currency", Currency);			
			
			/**/

			
			if(st!=null)
				st.close();
			conn.close();
		}
		catch(Exception e) {
			e.printStackTrace();
			
			// Messaggio
			msg = "<div class=\"alert alert-danger\">"+propLang.get("msgError")+e+"</div>";			
		}
		
		//obj.put("dsfun", "Dettaglio Finacial Calendar");
		
		obj.put("tipoForm", tipoForm);
		obj.put("count", count);
		obj.put("msg", msg);
		obj.put("btnActive", btnActive);
		obj.put("key", key);
		obj.put("Codeold", $codeold);	
		
		obj.put("Code", code);
		obj.put("Description", description);
		obj.put("Purchase_price_code", purchase_price_code);
		obj.put("Sell_price_code", sell_price_code);
		obj.put("Billing", billing);
		obj.put("Active", active);
		obj.put("Idcard", req.getParameter("idcard"));

		
		return new ModelAndView("/app/billing/price-dett", obj);
	}
	
}
