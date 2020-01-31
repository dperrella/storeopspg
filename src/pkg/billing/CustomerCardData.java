package pkg.billing;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import pkg.beans.Tables;
import pkg.beans.Tables2;
import pkg.utility.Utility;

public class CustomerCardData extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		List<Tables2> tab = new ArrayList<Tables2>();
		tab.clear();
		

		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		String sql = "";
		
		String q = ""+req.getParameter("q");
		q=Utility.noNull(q);
		String msg = "";
		String monthString = "";
		
		try {
			// Apre la connessione
			conn = Utility.getConnAdmin();
			st=conn.createStatement();

			//sql   = "select * from products ";
			sql = "select customer_card_data.*, payments_type.description payment_description, card_circuits.description card_description, " + 
					"customers.description customer_description, nazioni.nome_nazione nazione " + 
					"from customer_card_data " + 
					"left join payments_type on payment_code = payments_type.code " + 
					"left join card_circuits on card_code = card_circuits.code " + 
					"left join customers on idcustomer = customers.id " + 
					"left join nazioni on country::text = nazioni.id_naz::text ";
			rs=st.executeQuery(sql);
			while(rs.next()) {		
				
				switch (rs.getString("month_deadline")) {
	            case "01":  monthString = "January";
	                     break;
	            case "02":  monthString = "February";
	                     break;
	            case "03":  monthString = "March";
	                     break;
	            case "04":  monthString = "April";
	                     break;
	            case "05":  monthString = "May";
	                     break;
	            case "06":  monthString = "June";
	                     break;
	            case "07":  monthString = "July";
	                     break;
	            case "08":  monthString = "August";
	                     break;
	            case "09":  monthString = "September";
	                     break;
	            case "10": monthString = "October";
	                     break;
	            case "11": monthString = "November";
	                     break;
	            case "12": monthString = "December";
	                     break;
	        }
				
			
				tab.add(new Tables2(rs.getString("id"),rs.getString("payment_description"),rs.getString("customer_description"),rs.getString("nazione"),rs.getString("card_description"),rs.getString("card_number"),monthString,rs.getString("year_deadline"),rs.getString("cvv"),rs.getString("name"),rs.getString("surname"),"",""));
			}
			rs.close();
			
		}
		catch(Exception e) {
			e.printStackTrace();
			
			msg = "<div class=\"wrapper\"><div class=\"alert alert-danger\"><strong>"+e.getMessage()+"</strong></div></div>";
		}
		finally {
			try {
				if(conn!=null)
					conn.close();
				if(st!=null)
					st.close();
			}
			catch(Exception e) {}
		}
		
		obj.put("tab", tab);
		obj.put("msg", msg);
		
		
		
		return new ModelAndView("/app/billing/customer_card_data", obj);
	}
	
}
