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
import pkg.beans.Users;
import pkg.utility.Utility;

public class UsersList extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		List<Users> users = new ArrayList<Users>();
		
		// Formato data
		DateFormat dateIta = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		String oggi = dateIta.format(date);
		
		// Pulisce la lista
		users.clear();
		
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
				cond+=" where upper(idusr) || upper(nome) || upper(cogn) like '%"+q.toUpperCase()+"%' ";
			}
			
			conn = Utility.getConnHR();
			st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					  ResultSet.CONCUR_UPDATABLE);
			
			sql  =  "select * from zutenti "+cond;
			System.out.println(sql);
			rs=st.executeQuery(sql);
			
			// Campi database
			String $idusr="";
			String $pwd="";
			String $nome="";
			String $cogn="";
			String $tipo="";
			String $foto="";
			String $mail="";
			String $db="";
			String $style="";
			String $logo="";
			String $sysinf="";
			String $language="";
			
			String $cmp1="";
			String $cmp2="";
			String $cmp3="";
			String $cmp4="";
			
			while(rs.next()) {
				
				count+=1;
				
				$idusr=rs.getString("idusr");
				$pwd=rs.getString("pwd");
				$nome=rs.getString("nome");
				$cogn=rs.getString("cogn");
				$tipo=rs.getString("tipo");
				$foto=rs.getString("foto");
				$mail=rs.getString("mail");
				$db=rs.getString("db");
				$style=rs.getString("style");
				$logo=rs.getString("logo");
				$sysinf=rs.getString("sysinf");
				$language=""+rs.getString("language");
				$language=Utility.noNull($language);
				
				users.add(new Users($idusr,$pwd,$nome,$cogn,$tipo,$foto,$mail,$db,$style,$logo,$sysinf,$language,$cmp1,$cmp2,$cmp3,$cmp4));
				
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
		
		obj.put("dsfun", "Lista Utenti");
		obj.put("users", users);
		obj.put("count", count);
		obj.put("msg", msg);
		
		return new ModelAndView("/hr/config/users-list", obj);
	}
	
}
