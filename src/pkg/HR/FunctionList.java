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

import pkg.beans.Function;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class FunctionList extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		ReadProperties prop = new ReadProperties("connection.properties");
		String cdapp = prop.get("cdapp");
		
		List<Function> fun = new ArrayList<Function>();
		
		// Formato data
		DateFormat dateIta = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		String oggi = dateIta.format(date);
		
		// Pulisce la lista
		fun.clear();
		
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
				cond+=" and description like '%"+q+"%' ";
			}
			
			conn = Utility.getConnHR();
			st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					  ResultSet.CONCUR_UPDATABLE);
			
			sql  =  "select * from zfunzioni where cdapp = '"+cdapp+"' "+cond;
			rs=st.executeQuery(sql);
			
			// Campi database
			String $cdfun="";
			String $descr="";
			String $tipo="";
			String $percorso="";
			String $icon="";
		
			String $cmp1="";
			String $cmp2="";
			String $cmp3="";
			String $cmp4="";
			
			while(rs.next()) {
				
				count+=1;
				
				$cdfun=rs.getString("cdfun");
				$descr=rs.getString("descr");
				$tipo=rs.getString("tipo");
				$percorso=rs.getString("percorso");
				$icon=rs.getString("icon");
				
				fun.add(new Function($cdfun,$descr,$tipo,$percorso,$icon,$cmp1,$cmp2,$cmp3,$cmp4));
				
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
		
		obj.put("dsfun", "Lista Funzioni");
		obj.put("fun", fun);
		obj.put("count", count);
		obj.put("msg", msg);
		
		return new ModelAndView("/hr/config/function-list", obj);
	}
	
}
