package pkg.HR;

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

import pkg.beans.HR.RelEmpBad;
import pkg.utility.Utility;

public class RelEmpBadList extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		List<RelEmpBad> rel = new ArrayList<RelEmpBad>();
		HttpSession session = req.getSession();
		
		// Formato data
		DateFormat dateIta = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		String oggi = dateIta.format(date);
		
		// Pulisce la lista
		rel.clear();
		
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		
		String sql = "";
		String cond = "";
		
		int count = 0;
		String msg = "";
		
		String q = ""+req.getParameter("q");
		q=Utility.noNull(q);
		
		String id_employee = ""+req.getParameter("id_employee");
		id_employee=Utility.noNull(id_employee);
		
		try {
			
			if(q.length()>0) {
				cond+=" and a.id_employ || upper(c.name) || upper(c.surname) like '%"+q.toUpperCase()+"%' ";
			}
			
			if(id_employee.length()>0){
				cond+=" and a.id_employ = '"+id_employee+"' ";
			}
			
			conn = Utility.getConnHR();
			st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					  ResultSet.CONCUR_UPDATABLE);
			
			sql  =  "select a.id, a.id_employ, a.id_badge, a.date_end, b.number, c.name, c.surname, "+
					"To_timestamp(a.date_end) dataFine "+
					"from rel_emp_badge a, hr_badge b, hr_employee c "+
					"where a.id_employ = c.id and a.id_badge = b.id "
					+ "and c.id_customer = "+session.getAttribute("idcus")+" "+cond+" ";
			
			System.out.println(sql);
			rs=st.executeQuery(sql);
			
			// Campi database
			String $id_relempbadge="";
			String $id_employee="";
			String $id_badge="";
			String $bad_number="";
			String $dateend="";
			String $emp_name="";
			String $emp_surname="";
			
			String $cmp1="";
			String $cmp2="";
			String $cmp3="";
			String $cmp4="";
			
			while(rs.next()) {
				
				count+=1;
				
				$id_relempbadge=rs.getString("id");
				$id_employee=rs.getString("id_employ");
				$id_badge=rs.getString("id_badge");
				$bad_number=rs.getString("number");
				$dateend=rs.getString("dataFine");
				$emp_name=rs.getString("name");
				$emp_surname=rs.getString("surname");
				
				rel.add(new RelEmpBad($id_relempbadge,$id_employee,$id_badge,
						$bad_number,$dateend,$emp_name,$emp_surname,
						$cmp1,$cmp2,$cmp3,$cmp4));
				
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
		
		obj.put("dsfun", "Dipendenti / Badge");
		obj.put("rel", rel);
		obj.put("count", count);
		obj.put("msg", msg);
		
		return new ModelAndView("/hr/employee/relempbad-list", obj);
	}
	
}
