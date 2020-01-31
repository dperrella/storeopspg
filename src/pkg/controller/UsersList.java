package pkg.controller;

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

import pkg.beans.Users;
import pkg.utility.Utility;

public class UsersList extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		HttpSession session = req.getSession();
		
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
		String cond2= "";
		
		
		
		int count = 0;
		String msg = "";
		
		String q = ""+req.getParameter("q");
		q=Utility.noNull(q);
		
		String noactive = ""+req.getParameter("noactive");
		noactive=Utility.noNull(noactive);
		
		obj.put("noactive", noactive);
		
		try {
			
			if(q.length()>0) {
				cond+=" and upper(idusr) || upper(nome) || upper(cogn) like '%"+q.toUpperCase()+"%' ";
			}
			if(noactive.length()>0) {
				cond2+=" and active = false ";
			}
			
			String arrayCustomers = (String) session.getAttribute("arrayCustomers");
			String arrayStores = (String) session.getAttribute("arrayStores");
			String arrayCountry = (String) session.getAttribute("arrayCountry");
			String role = (String) session.getAttribute("tipo");
			String is_admin = (String) session.getAttribute("is_admin");
			
			obj.put("is_admin", is_admin);
			
			conn = Utility.getConnAdmin();
			st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					  ResultSet.CONCUR_UPDATABLE);
			// admin
			if(role.trim().equals("2")) {
				sql  =  "select * from zutenti where idusr<>'' "+cond+" "+cond2+" order by idusr";
			}
			else {
				// altri admin
				String condUsers = "";
				if(role.trim().equals("4"))
					condUsers = "and tipo in('3','4') ";
				if(role.trim().equals("5"))
					condUsers = "and tipo in('3','4','5') ";
				if(role.trim().equals("6"))
					condUsers = "and tipo in('3','4','5','6') ";
				
				sql  =  "select * "
						+ "from zutenti u "
						+ "where u.idusr in(select a.idusr from rel_user_customers a where idcustomer in("+arrayCustomers+")) "
						+ "and u.idusr in(select a.idusr from stores s, rel_user_stores a " 
						+ "where id = idstore and s.country::int in("+arrayCountry+") and s.id_customer in("+arrayCustomers+")) "
						+ "and u.idusr in(select a.idusr from rel_user_stores a where idstore in("+arrayStores+")) and active = true "+cond+" "+condUsers+" "
						+ "order by idusr";
			}
			
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
				$foto=""+rs.getString("foto2");
				$foto=Utility.noNull($foto);
				if($foto.length()==0) {
					$foto = "../../images/default-icon-user.png";
				}
				$mail=rs.getString("mail");
				$db=rs.getString("db");
				$style=rs.getString("style");
				$logo=rs.getString("logo");
				$sysinf=rs.getString("sysinf");
				$language=""+rs.getString("language");
				$language=Utility.noNull($language);
				
				$cmp1=rs.getString("active");
				$cmp2=""+rs.getString("origin");
				$cmp2=Utility.noNull($cmp2);
				if($cmp2.length()==0)
					$cmp2 = "Suite";
					
				users.add(new Users($idusr,$pwd,$nome,$cogn,$tipo,$foto,$mail,$db,$style,$logo,$sysinf,$language,$cmp1,$cmp2,$cmp3,""+count));
				
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
		
		return new ModelAndView("/app/config/users-list", obj);
	}
	
}
