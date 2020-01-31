package pkg.pos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
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

import pkg.beans.Kpi;
import pkg.beans.Tables2;
import pkg.beans.Users;
import pkg.beans.Tables;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class Desk extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		HttpSession session = req.getSession();
		//List<Sm_Listlogs> listlog = new ArrayList<Sm_Listlogs>();
		List<Users> listlog = new ArrayList<Users>();
		List<Tables> cus = new ArrayList<Tables>();
		
		
		listlog.clear();
		cus.clear();

		//Legge il file di properties associato alla lingua sul profilo
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");
		
		// Dalla request
		String idcus = ""+req.getParameter("idcus");
		idcus = Utility.noNull(idcus);
		int nr_customers = 0;
		
		// Se dalla request è vuoto legge dalla session
		if(idcus.length()==0) {
			idcus = ""+session.getAttribute("idcus");
			idcus = Utility.noNull(idcus);
		}
		
		// campi passati dalla session
		String username = (String) session.getAttribute("username");
		String desc = (String) session.getAttribute("desc");
		String tipo = (String) session.getAttribute("tipo");
		String foto = (String) session.getAttribute("foto");
		String mail = (String) session.getAttribute("mail");
		String db = (String) session.getAttribute("db");
		String first_ = "";
		
		Connection connAdmin = null;
		Statement stAdmin = null;
		ResultSet rs = null;
		String sql = "";
		try {
			
			if(idcus.length()>0) {
				session.setAttribute("idcus", idcus);
				nr_customers = 1;
			}
			
			ReadProperties prop = new ReadProperties("connection.properties");
			connAdmin = Utility.getConnAdmin();
			stAdmin = connAdmin.createStatement();
			
			// ==================
			// NUMERO CLIENTI e lista clienti
			// ==================
			sql  =  "select id, description from customers where "
					+ "id in(select idcustomer from rel_user_customers "
					+ "where idusr = '"+(String)session.getAttribute("username")+"') ";
			// Esegue l'sql
			rs=stAdmin.executeQuery(sql);
			String cus_id = "";
			
			while(rs.next()) {
				if(idcus.length()==0) {
					nr_customers += 1;
				}
				cus.add(new Tables("CUS",rs.getString("id"),rs.getString("description"),"","","",""));
				cus_id = rs.getString("id");
			}
			rs.close();
			stAdmin.close();
			
			System.out.println(nr_customers);
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		
		String list_app_store = "";
		
		String pezzi = "";
		String scontrini = "";
		String incasso = "";
		String scontrino_medio = "";
		String pezzi_medi = "";
		
		sql = "";
		String combocli = "";
		Connection connext = null;
		Connection conn = null;
		Connection conn2 = null;
		Statement st = null;
		Statement st2 = null;
		rs = null;
		ResultSet rs2 = null;
		connext = Utility.getConnPosext();
		st2 = connext.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		
		try {
		
		conn = Utility.getConnPos();
		st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

		sql = "select id idstore, idusr from vw_rel_user_stores where upper(idusr) = '"+username.toUpperCase()+"'";
		rs = st.executeQuery(sql);
		while(rs.next()) {
			if ("".equals(list_app_store)){
				list_app_store = "'"+rs.getString("idstore")+"'";
			}else{
				list_app_store = list_app_store + ",'" + rs.getString("idstore")+"'";
			}
		}
		rs.close();
		
		System.out.println("idcus.len = " + idcus.length());
		if(idcus.length()>0) {
		
			//sql = "select idclient, descrizione from app_stores where idclient in("+list_app_store+") order by idclient ";
			sql = "select store_code idclient, description descrizione from stores where id_customer = "+idcus;
			System.out.println("sql = " + sql);
			rs = st2.executeQuery(sql);
			while(rs.next()) {
				if ("".equals(combocli)) {
					combocli = "<option value='"+rs.getString("idclient")+"'>"+rs.getString("idclient")+" - "+rs.getString("descrizione").toUpperCase()+"</option>";
					first_ = rs.getString("idclient");
				}else {
					combocli = combocli + "<option value='"+rs.getString("idclient")+"'>"+rs.getString("idclient")+" - "+rs.getString("descrizione").toUpperCase()+"</option>";	
				}
				
			}
		
		}

		rs.close();		
		
		/*date now*/
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		DateFormat dateIso = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String nows = dateFormat.format(date);
		String nows2 = dateIso.format(date);
		/**/
		
		sql = "select * from totali_giornalieri where idclient = '"+first_+"' and datenew = '"+nows2+"'";
		System.out.println("sqltot_gio = " + sql);
		rs = st2.executeQuery(sql);
		if(rs.next()) {
			incasso = rs.getString("incasso");
			pezzi = rs.getString("pezzi");
			scontrini = rs.getString("scontrini");
			scontrino_medio = rs.getString("scontrino_medio");
			pezzi_medi = rs.getString("pezzi_medi");
		}
		
		
		}catch(Exception e) {
			
			e.printStackTrace();
			
		}finally {
			
			try {
				if(st!=null)
					st.close();
				if(conn!=null)
					conn.close();
			}
			catch(Exception e) {}
		}		
		
		rs.close();		
		
		
		
		obj.put("ComboCli", combocli);
		obj.put("Incasso", incasso.replace(".", ","));
		obj.put("Pezzi", pezzi);
		obj.put("Scontrini", scontrini);
		obj.put("ScontrinoMedio", scontrino_medio.replace(".", ","));
		obj.put("PezziMedi", pezzi_medi.replace(".", ","));
		
		obj.put("cus", cus);
		obj.put("idcus", idcus);
		obj.put("nr_customers", nr_customers);
		
		obj.put("First_", first_);
		
		
		return new ModelAndView("/pos/main/index", obj);
	}
	
}
