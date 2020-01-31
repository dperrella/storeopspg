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

import pkg.beans.HR.RelUserEmp;
import pkg.utility.Utility;

public class RelUserEmpList extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		List<RelUserEmp> rel = new ArrayList<RelUserEmp>();
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
				cond+=" and a.id_employee || upper(c.name) || upper(c.surname) like '%"+q.toUpperCase()+"%' ";
			}
			
			if(id_employee.length()>0){
				cond+=" and a.id_employee = '"+id_employee+"' ";
			}
			
			conn = Utility.getConnHR();
			st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					  ResultSet.CONCUR_UPDATABLE);
			
			sql  =  "select a.id, a.id_employee, a.id_user, c.name, c.surname, b.nome, b.cogn "+
					"from rel_emp_user a, zutenti b, hr_employee c "+
					"where a.id_employee = c.id and a.id_user = b.idusr "+cond+" and c.id_customer = "+session.getAttribute("idcus")+" ";
			
			System.out.println(sql);
			rs=st.executeQuery(sql);
			
			// Campi database
			String $id="";
			String $id_employee="";
			String $id_user="";
			String $des_employee="";
			String $des_user="";
			
			String $cmp1="";
			String $cmp2="";
			String $cmp3="";
			String $cmp4="";
			
			while(rs.next()) {
				
				count+=1;
				
				$id=rs.getString("id");
				$id_employee=rs.getString("id_employee");
				$id_user=rs.getString("id_user");
				$des_employee=rs.getString("name")+" "+rs.getString("surname");
				$des_user=rs.getString("nome")+" "+rs.getString("cogn");
				
				rel.add(new RelUserEmp($id,$id_employee,$id_user,
						$des_employee,$des_user,
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
		
		obj.put("dsfun", "Utenti / Dipendenti");
		obj.put("rel", rel);
		obj.put("count", count);
		obj.put("msg", msg);
		
		return new ModelAndView("/hr/config/reluseremp-list", obj);
	}
	
}
