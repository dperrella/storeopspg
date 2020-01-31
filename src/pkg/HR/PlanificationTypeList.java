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
import pkg.beans.HR.PlanificationType;
import pkg.utility.Utility;

public class PlanificationTypeList extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		List<PlanificationType> plt = new ArrayList<PlanificationType>();
		
		// Formato data
		DateFormat dateIta = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		String oggi = dateIta.format(date);
		
		// Pulisce la lista
		plt.clear();
		
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
				cond+=" where id || upper(desription) like '%"+q.toUpperCase()+"%' ";
			}
			
			conn = Utility.getConnHR();
			st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					  ResultSet.CONCUR_UPDATABLE);
			
			sql  =  "select * from hr_planification_type "+cond;
			System.out.println(sql);
			rs=st.executeQuery(sql);
			
			// Campi database
			String $id="";
			String $description="";
			String $min_time="";
			String $max_time="";
			String $direction="";
			String $approvation="";
			String $color="";
			
			String $cmp1="";
			String $cmp2="";
			String $cmp3="";
			String $cmp4="";
			
			while(rs.next()) {
				
				count+=1;
				
				$id=rs.getString("id");
				$description=rs.getString("description");
				$min_time=rs.getString("min_time");
				$max_time=rs.getString("max_time");
				$direction=rs.getString("direction");
				$approvation=rs.getString("approvation");
				$color=rs.getString("color");
				
				plt.add(new PlanificationType($id,$description,$min_time,$max_time,$direction,$approvation,$color,$cmp1,$cmp2,$cmp3,$cmp4));
				
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
		
		obj.put("dsfun", "Lista Tipi Pianificazioni");
		obj.put("plt", plt);
		obj.put("count", count);
		obj.put("msg", msg);
		
		return new ModelAndView("/hr/planner/planification-type-list", obj);
	}
	
}
