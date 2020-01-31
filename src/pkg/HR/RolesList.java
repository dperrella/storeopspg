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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import pkg.beans.HR.Roles;
import pkg.utility.Utility;

public class RolesList extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		List<Roles> rol = new ArrayList<Roles>();
		
		// Formato data
		DateFormat dateIta = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		String oggi = dateIta.format(date);
		
		// Pulisce la lista
		rol.clear();
		
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		
		String sql = "";
		String cond = "";
		
		int count = 0;
		String msg = "";
		
		String q = ""+req.getParameter("q");
		q=Utility.noNull(q);
		
		try {
			
			if(q.length()>0) {
				cond+=" where id || upper(name) || upper(description) like '%"+q.toUpperCase()+"%' ";
			}
			
			conn = Utility.getConnHR();
			st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					  ResultSet.CONCUR_UPDATABLE);
			
			sql  =  "select * from hr_roles "+cond;
			System.out.println(sql);
			rs=st.executeQuery(sql);
			
			// Campi database
			String $id="";
			String $name="";
			String $description="";
			String $plan_view="";
			
			String $cmp1="";
			String $cmp2="";
			String $cmp3="";
			String $cmp4="";
			
			while(rs.next()) {
				
				count+=1;
				
				$id=rs.getString("id");
				$name=rs.getString("name");
				$description=rs.getString("description");
				$plan_view=rs.getString("plan_view");
				if($plan_view.equals("Y"))
					$plan_view = "SI";
				if($plan_view.equals("N"))
					$plan_view = "NO";
				
				
				rol.add(new Roles($id,$name,$description,$plan_view,$cmp1,$cmp2,$cmp3,$cmp4));
				
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
		
		obj.put("dsfun", "Lista Ruoli");
		obj.put("rol", rol);
		obj.put("count", count);
		obj.put("msg", msg);
		
		return new ModelAndView("/hr/employee/roles-list", obj);
	}
	
}
