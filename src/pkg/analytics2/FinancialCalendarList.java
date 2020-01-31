package pkg.analytics2;

import java.sql.Connection;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.sql.PreparedStatement;
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

import com.jcraft.jsch.Session;

import pkg.beans.Customers;
import pkg.beans.FinancialCalendar;
import pkg.utility.Utility;


public class FinancialCalendarList extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		HttpSession session = req.getSession();
		List<FinancialCalendar> fc = new ArrayList<FinancialCalendar>();
		List<Customers> cus = new ArrayList<Customers>();
		
		// Pulisce la lista
		fc.clear();
		cus.clear();
		
		Connection conn = null;
		PreparedStatement st = null;
		
		Connection connAdmin = null;
		PreparedStatement stAdmin = null;
		
		ResultSet rs = null;
		
		String sql = "";
		
		int count = 0;
		String msg = "";
		
		String q = ""+req.getParameter("q");
		q=Utility.noNull(q);
		
		String idcus = ""+req.getParameter("idcus");
		idcus=Utility.noNull(idcus);
		
		if(idcus.length()==0)
			idcus = "0";
		
		try {
			// Apre la connessione
			conn = Utility.getConnData();
			// Apre la connessione Admin
			connAdmin = Utility.getConnAdmin();
						
			
			// ==================
			// Lista Customers
			sql  =  "select * , '"+(String)session.getAttribute("username")+"' as user from customers a where "
					+ "id in(select idcustomer from rel_user_customers "
					+ "where idusr = '"+(String)session.getAttribute("username")+"') "
					+ "union all "
					+ "select * from customerscountry b where idusr = '"+(String)session.getAttribute("username")+"' ";
			
			// Prepara il cursore
			stAdmin = connAdmin.prepareStatement(sql);
			
			// Esegue l'sql
			rs=stAdmin.executeQuery();
			
			// Campi database
			String $companyname;
			String $description;
			String $contact;
			String $brandname;
			String $brandlogo;
			String $account_code;
			String $id;
			String $phone;
			String $mail;
			String $mobile;
			String $id_optempl;
			String $customer_country_id;
			String $agreement;
			String $wskey;
			String $wsdecrypt_key;
			String $wscrypt;
			
			String $cmp1="";
			String $cmp2="";
			String $cmp3="";
			String $cmp4="";
			
			while(rs.next()) {
				
				count+=1;
				
				$companyname=rs.getString("companyname");
				$description=rs.getString("description");
				$contact=rs.getString("contact");
				$brandname=rs.getString("brandname");
				$brandlogo=rs.getString("brandlogo");
				$account_code=rs.getString("account_code");
				$id=rs.getString("id");
				$phone=rs.getString("phone");
				$mail=rs.getString("mail");
				$mobile=rs.getString("mobile");
				$id_optempl=rs.getString("id_optempl");
				$customer_country_id=rs.getString("customer_country_id");
				$agreement=rs.getString("agreement");
				$wskey=rs.getString("wskey");
				$wsdecrypt_key=rs.getString("wsdecrypt_key");
				$wscrypt=rs.getString("wscrypt");
				
				cus.add(new Customers($companyname,$description,$contact,$brandname,
						$brandlogo,$account_code,$id,$phone,$mail,$mobile,$id_optempl,
						$customer_country_id,$agreement,$wskey,$wsdecrypt_key,$wscrypt,
						$cmp1,$cmp2,$cmp3,$cmp4));
				
			}
			// Fine Lista Utenti
			// ==================
			
			
			// sql da eseguire
			sql  =  "select year, caltype, min(fromdate) fromdate, max(todate) todate from financial_calendar2 "
					+ "where customer_id = "+idcus+" "
					+ "group by year, caltype "
					+ "order by year ";
			
			// l'sql ha parametri passati?
			boolean hasCond = false;
			if(q.length()>0) {
				hasCond = true;
				sql+=" where description like ? ";
			}
			
			// Prepara il cursore
			st = conn.prepareStatement(sql);
			
			// Se ha parametri li passa all'sql
			if(hasCond==true) {
				st.setString(1, "%" + q + "%");
			}
			
			// Esegue l'sql
			rs=st.executeQuery();
			
			DateFormat ita = new SimpleDateFormat("dd-MM-yyyy");
			
			while(rs.next()) {
				count+=1;
				
				
				fc.add(new FinancialCalendar(rs.getString("caltype"),rs.getInt("year"), 0, 
						ita.format(rs.getDate("fromdate")),
						ita.format(rs.getDate("todate")),
						0,
						"","","",""));
				
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
		
		obj.put("dsfun", "Financial Calendar");
		obj.put("fc", fc);
		obj.put("cus", cus);
		obj.put("idcus", Integer.parseInt(idcus));
		
		obj.put("count", count);
		obj.put("msg", msg);
		
		return new ModelAndView("/analytics2/config/financial-calendar", obj);
	}
	
}
