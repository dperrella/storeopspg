package pkg.HR;

import java.io.FileNotFoundException;
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

import com.oreilly.servlet.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import pkg.beans.Function;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class ProfiloDett extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		List<Function> fun = new ArrayList<Function>();
		fun.clear();
		
		String btnActive = "";
		
		// Formato data
		DateFormat dateNum = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		String oggi = dateNum.format(date);

		HttpSession session = req.getSession();
		String username = (String) session.getAttribute("username");
		
		String dsfun = "";
		String tipoForm = ""+req.getParameter("tipoForm");
		tipoForm = Utility.noNull(tipoForm);
		
		dsfun = "Modifica Profilo "+username;
		
		// Messaggio 
		String msg = ""+req.getParameter("msg");
		msg = Utility.noNull(msg);
		String tpmsg = ""+req.getParameter("tpmsg");
		tpmsg = Utility.noNull(tpmsg);
		
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		
		String sql = "";
		String readonly = "";
		
		// Campi database
		String idusr="";
		String pwd="";
		String nome="";
		String cogn="";
		String tipo="";
		String foto="";
		String mail="";
		
		String cmp1="";
		String cmp2="";
		String cmp3="";
		String cmp4="";
		
		// Statistiche
		String trend = "";
		String consClmer = "";
		String percProv = "";
		
		try {
			
			ReadProperties prop = new ReadProperties("connection.properties");
			String lib = prop.get("lib");
			String libApp = (String) session.getAttribute("lib");
			String cdapp = prop.get("cdapp");
			
			
			conn = Utility.getConnHR();
			st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
  					  ResultSet.CONCUR_UPDATABLE);
			
			sql   = "select idusr, pwd, nome, cogn, tipo, foto, mail " +
					"from zutenti where idusr = '"+username+"' fetch first 1 rows only ";
			
			rs=st.executeQuery(sql);
			
			if(rs.next()) {
				
				idusr=rs.getString("idusr");
				pwd=rs.getString("pwd");
				nome=rs.getString("nome");
				cogn=rs.getString("cogn");
				tipo=rs.getString("tipo");
				foto=rs.getString("foto");
				mail=rs.getString("mail");
					
			}
			rs.close();
			
			// ====================
			// Lista funzioni
			sql  =  "select a.cdfun, b.cdfun aut, l.descr "+
					"from zfunlang l, zfunzioni a "+
					"left join zutefunz b on a.cdfun = b.cdfun and b.idusr = '"+(String)session.getAttribute("username")+"' "+
					"where l.cdfun = a.cdfun and l.lang = '"+(String)session.getAttribute("language")+"' "+
					"and cdapp = '"+cdapp+"' ";
					
			System.out.println(sql);
			rs=st.executeQuery(sql);
			
			String $cdfun = "";
			String $descr = "";
			String $aut = "";
			int nrRiga = 0;
			while(rs.next()) {
				
				nrRiga+=1;
				$cdfun = rs.getString("cdfun");
				$descr = rs.getString("descr");
				$aut = ""+rs.getString("aut");
				$aut = Utility.noNull($aut);
				
				if($aut.length()>0) {
					$aut = "checked";
				}
				
				fun.add(new Function($cdfun,$descr,"","","",$aut,""+nrRiga,"",""));
				
				$aut = "";
			}
			rs.close();
			
			st.close();
			conn.close();
		}
		catch(Exception e) {
			e.printStackTrace();
			
			// Messaggio
			tpmsg = "N";
			msg = "<strong>Attenzione</strong><br/>Si e&grave; verificato un errore!<br/><br/>Errore:<br/>"+e;			
		}
		
		if(tpmsg.equals("N"))
			msg = "<div class=\"alert alert-danger\">"+msg+"</div>";
		else if(tpmsg.equals("S"))
			msg = "<div class=\"alert alert-success\">"+msg+"</div>";
		
		obj.put("dsfun", dsfun);
		obj.put("tipoForm", tipoForm);
		
		obj.put("idusr", idusr);
		obj.put("pwd", pwd);
		obj.put("nome", nome);
		obj.put("cogn", cogn);
		obj.put("tipo", tipo);
		obj.put("foto", foto);
		obj.put("mail", mail);

		obj.put("msg", msg);
		obj.put("readonly", readonly);
		
		obj.put("btnActive", btnActive);
		
		obj.put("fun", fun);
		
		return new ModelAndView("/hr/main/profilo", obj);
	}
	
}
