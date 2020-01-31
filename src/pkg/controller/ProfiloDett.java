package pkg.controller;

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
		
		String btnActive = "";
		
		List<Function> fun = new ArrayList<Function>();
		fun.clear();
		
		// Formato data
		DateFormat dateNum = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		String oggi = dateNum.format(date);

		HttpSession session = req.getSession();
		String username = (String) session.getAttribute("username");
		
		String dsfun = "";
		String tipoForm = ""+req.getParameter("tipoForm");
		tipoForm = Utility.noNull(tipoForm);
		
		
		
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
			
			ReadProperties propLang = new ReadProperties(session.getAttribute("language")+".properties");
			dsfun = propLang.get("prof_button")+" "+username;
			
			conn = Utility.getConnAdmin();
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
			// Lista applicazioni
			/*
			sql  =  "select a.cdapp, b.cdapp aut, a.nome, a.descr "+
					"from zapp a "+
					"left join zuteapp b on a.cdapp = b.cdapp and b.idusr = '"+username+"' ";
			*/
			
			// Per ogni applicazione in zapp imposto le autorizzazioni 
			String apps = session.getAttribute("arrayApp").toString();
			String temp[]=apps.split(";");
			String cond = "";
			for(int i=0;i<temp.length;i++){
				cond+="'"+temp[i]+"',";
			}
			if(cond.length()>0) 
				cond=cond.substring(0,cond.length()-1);
			
			if(!username.equals("DEVELOP")) {
				cond="where a.cdapp in("+cond+") ";
			}
			else {
				cond="";
			}
			
			sql  =  "select a.cdapp, b.cdapp aut, a.nome, a.descr "+
					"from zapp a "+
					"left join zuteapp b on a.cdapp = b.cdapp and b.idusr = '"+username+"' "+
					cond+
					"order by a.cdapp ";
					
			System.out.println(sql);
			rs=st.executeQuery(sql);
			
			String $cdfun = "";
			String $nomeApp = "";
			String $descr = "";
			String $aut = "";
			int nrRiga = 0;
			while(rs.next()) {
				
				nrRiga+=1;
				$cdfun = rs.getString("cdapp");
				$nomeApp = rs.getString("nome");
				$descr = rs.getString("descr");
				$aut = ""+rs.getString("aut");
				$aut = Utility.noNull($aut);
				
				if($aut.length()>0) {
					$aut = "checked";
				}
				
				fun.add(new Function($cdfun,$descr,$nomeApp,"","",$aut,""+nrRiga,"",""));
				
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
		
		return new ModelAndView("/app/main/profilo", obj);
	}
	
}
