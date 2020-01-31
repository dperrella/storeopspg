package pkg.pos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import pkg.beans.pos.Sm_DevnotE;
import pkg.beans.pos.Sm_DevnotP;
import pkg.beans.pos.Sm_Listlogs;
import pkg.beans.Users;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class Fidelitycardupd extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		List<Users> listlog = new ArrayList<Users>();
		
		listlog.clear();
		
		HttpSession session = req.getSession();
		//Legge il file di properties associato alla lingua sul profilo
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");
		

		/**/
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		conn = Utility.getConnPos();
		st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		String sql = "";
		
		String mai = "";
		String tel = "";
		String add = "";
		String sex = "";
		String cog = "";
		String nom = "";
		String fis = "";
		String cat = "";
		String sta = "";		
		String idf = "";
		String com = "";
		String bir = "";
		String fco = "";
		String comuni = "";
		String category= "";
		
		try {

		String us = session.getAttribute("username").toString().toUpperCase();

		sql = "select * from fidelitycard where upper(idfidelity) = upper('"+req.getParameter("fidcard")+"')";
		rs = st.executeQuery(sql);
		
		if(rs.next()) {
			idf = rs.getString("idfidelity");
			nom = ""+rs.getString("fidelityname");
			nom=Utility.noNull(nom);
			cog = ""+rs.getString("fidelitysurname");
			cog=Utility.noNull(cog);
			sex = rs.getString("fidelitysex");
			add = ""+rs.getString("fidelityaddress");
			add=Utility.noNull(add);
			
			if (!"".equals(add)){
				add = add.toUpperCase();
			}
			
			tel = rs.getString("fidelitytel");
			mai = rs.getString("fidelitymail");
			fis = ""+rs.getString("fidelitycodfiscale");
			fis=Utility.noNull(fis);
			cat = ""+rs.getString("fidelitycategory");		
			cat=Utility.noNull(cat);
			sta = rs.getString("fidelitystatus");
			com = rs.getString("fidelitycity");
			
			bir = rs.getString("fidelitybirth");
			bir=Utility.noNull(bir);
			fco = rs.getString("fidelitycode");
		}
		rs.close();
		
		
		sql = "select distinct * from comuni order by comune";
		rs = st.executeQuery(sql);
		
		while(rs.next()) {
			comuni = comuni + "<option value='"+rs.getString("id_com")+"'>"+rs.getString("comune").toUpperCase()+"</option>";
		}
		rs.close();
		
		
		sql = "select distinct * from categoryfid order by descriptioncategoryfid";
		rs = st.executeQuery(sql);
		
		while(rs.next()) {
			category = category + "<option value='"+rs.getString("idcategoryfid")+"'>"+rs.getString("descriptioncategoryfid").toUpperCase()+"</option>";
		}
		rs.close();
			
		
		obj.put("Idf", idf);
		obj.put("Nom", nom);
		obj.put("Cog", cog);
		obj.put("Sex", sex);
		obj.put("Add", add);
		obj.put("Tel", tel);
		obj.put("Mai", mai);
		obj.put("Fis", fis);
		obj.put("Cat", cat);
		obj.put("Sta", sta);
		obj.put("Com", com);
		obj.put("Bir", Utility.getTurnDate(bir));		
		obj.put("Fco", fco);
		
		obj.put("Comuni", comuni);
		obj.put("Category", category);
		
		//session.setAttribute("Idlog", req.getParameter("id"));		
		
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			
			try {
				if(st!=null)
					st.close();
				if(conn!=null)
					conn.close();
			}
			catch(Exception e) {}
		}		
		/**/
		
		return new ModelAndView("/pos/main/fidelity-card-upd", obj);
	}
	
}
