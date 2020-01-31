package pkg.billing;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

public class CustomerCardDataDett extends AbstractController {
	
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
		String id="";
		String paymentcode="";
		String idcustomer="";
		String country="";
		String cardc="";
		String cardn="";
		String month="";
		String year="";
		String cvv="";
		String name="";
		String surname="";
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
		String $act = "";
		String $yea = "";
		String $cvv = "";
		String $nam = "";
		String $sur = "";
		
		String $pur = "";
		String $sel = ""; 
		String description = "";
		String $bil = "";
		String accountposition = "";
		String storeposition = "";
		String likestring = "";
		int $iid_ = 0;
		int $des_ = 0;
		
		try {
			
			conn = Utility.getConnAdmin();
			conn2 = Utility.getConnAdmin();
			
			if(azione.length()>0) {
				
				if(tipoForm.equals("I")) {
					
					// Inserimento
					System.out.println("INS...");
					
					$cod = Utility.noNull(""+req.getParameter("payment_code"));
					$des = Utility.noNull(""+req.getParameter("idcustomer"));
					$des_ = Integer.parseInt($des);
					$pur = Utility.noNull(""+req.getParameter("country"));
					$sel = Utility.noNull(""+req.getParameter("card_code"));
					$bil = Utility.noNull(""+req.getParameter("card_number"));
					$act = Utility.noNull(""+req.getParameter("month_deadline"));
					$yea = Utility.noNull(""+req.getParameter("year_deadline"));
					$cvv = Utility.noNull(""+req.getParameter("cvv"));
					$nam = Utility.noNull(""+req.getParameter("name"));
					$sur = Utility.noNull(""+req.getParameter("surname"));
					
						sql   = "INSERT INTO public.customer_card_data(payment_code, idcustomer, country, card_code, card_number, month_deadline, year_deadline, cvv, name, surname) "+
								"    VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
						
						// Prepara il cursore
						st = conn.prepareStatement(sql); 
						// Passa i parametri
						
						st.setString(1, $cod);
						st.setInt(2, $des_);
						st.setString(3, $pur);
						st.setString(4, $sel);
						st.setString(5, $bil);
						st.setString(6, $act);
						st.setString(7, $yea);
						st.setString(8, $cvv);
						st.setString(9, $nam);
						st.setString(10, $sur);
					
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
					
					$cod = Utility.noNull(""+req.getParameter("payment_code"));
					$des = Utility.noNull(""+req.getParameter("idcustomer"));
					$des_ = Integer.parseInt($des);
					$pur = Utility.noNull(""+req.getParameter("country"));
					$sel = Utility.noNull(""+req.getParameter("card_code"));
					$bil = Utility.noNull(""+req.getParameter("card_number"));
					$act = Utility.noNull(""+req.getParameter("month_deadline"));
					$yea = Utility.noNull(""+req.getParameter("year_deadline"));
					$cvv = Utility.noNull(""+req.getParameter("cvv"));
					$nam = Utility.noNull(""+req.getParameter("name"));
					$sur = Utility.noNull(""+req.getParameter("surname"));
					$codeold = Utility.noNull(""+req.getParameter("idcard"));
					
					if ("".equals($codeold)) {
						$codeold = $cod;
					}
					
					sql  =  "UPDATE public.customer_card_data SET payment_code=?, idcustomer=?, country=?, card_code=?, card_number=?, month_deadline=?, year_deadline=?, cvv=?, name=?, surname=? "+
							"   where id::text = ? ";
					// Prepara il cursore
					st = conn.prepareStatement(sql);
					
					// Passa i parametri			
					st.setString(1, $cod);
					st.setInt(2, $des_);
					st.setString(3, $pur);
					st.setString(4, $sel);
					st.setString(5, $bil);
					st.setString(6, $act);
					st.setString(7, $yea);
					st.setString(8, $cvv);
					st.setString(9, $nam);
					st.setString(10, $sur);
					st.setString(11, $codeold);
					
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
					
					sql   = "delete from customer_card_data where id::text = ? ";
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
				sql  =  "select * from customer_card_data where id::text = ? ";
				
				// Prepara il cursore
				st = conn.prepareStatement(sql);
				st.setString(1, $cod);

				// Esegue l'sql
				System.out.println("sql Read = " + sql);
				rs=st.executeQuery();
				
				if(rs.next()) {
					id=rs.getString("id");
					paymentcode=rs.getString("payment_code");
					idcustomer=rs.getString("idcustomer"); 
					country=rs.getString("country");
					cardc=rs.getString("card_code");
					cardn=rs.getString("card_number");
					month=rs.getString("month_deadline");
					year=rs.getString("year_deadline");
					cvv=rs.getString("cvv");
					name=rs.getString("name");
					surname=rs.getString("surname");
				}
				rs.close();

				
			}
			
			/**/
			
			String Customers = "";
			sql  =  "select * from customers ";
			st = conn.prepareStatement(sql);
			rs=st.executeQuery();
			while(rs.next()) {
				Customers += "<option value='"+rs.getString("id")+"'>"+rs.getString("description")+"</option>";
			}
			
			rs.close();			
			obj.put("Customers", Customers);			
			
			String Countrys = "";
			sql  =  "select * from nazioni ";
			st = conn.prepareStatement(sql);
			rs=st.executeQuery();
			while(rs.next()) {
				Countrys += "<option value='"+rs.getString("id_naz")+"'>"+rs.getString("nome_nazione")+"</option>";
			}
			rs.close();			
			obj.put("Countrys", Countrys);
						
			String Paymenttype = "";
			sql  =  "select * from payments_type where active = true ";
			st = conn.prepareStatement(sql);
			rs=st.executeQuery();
			while(rs.next()) {
				Paymenttype += "<option value='"+rs.getString("code")+"'>"+rs.getString("description")+"</option>";
			}
			rs.close();			
			obj.put("Paymenttype", Paymenttype);

			String Cardcode = "";
			sql  =  "select * from card_circuits where active = true ";
			st = conn.prepareStatement(sql);
			rs=st.executeQuery();
			while(rs.next()) {
				Cardcode += "<option value='"+rs.getString("code")+"'>"+rs.getString("description")+"</option>";
			}
			rs.close();			
			obj.put("Cardcode", Cardcode);
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
		
		int years = Calendar.getInstance().get(Calendar.YEAR);
		int _years = years + 5;
		String Years_ = "";
		for (int i=years; i<=_years; i++) {
			Years_ += "<option value='"+i+"'>"+i+"</option>";
		}
		
		
		obj.put("Years_", Years_);
		obj.put("tipoForm", tipoForm);
		obj.put("count", count);
		obj.put("msg", msg);
		obj.put("btnActive", btnActive);
		obj.put("key", key);
		obj.put("Codeold", $codeold);	
		
		obj.put("Id", id);
		obj.put("Paymentcode", paymentcode);
		obj.put("Idcustomer", idcustomer);
		obj.put("Country", country);
		obj.put("Cardc", cardc);
		obj.put("Cardn", cardn);
		obj.put("Months", month);
		obj.put("Year", year);
		obj.put("Cvv", cvv);
		obj.put("Name", name);
		obj.put("Surname", surname);
		obj.put("Idcard", req.getParameter("idcard"));

		
		return new ModelAndView("/app/billing/customer_card_data-dett", obj);
	}
	
}
