package pkg.controller;

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

import com.microsoft.aad.adal4j.AuthenticationResult;

import eu.mcsistemi.aad.AuthHelper;
import pkg.beans.signin.Users;
import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class Desk extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		HttpSession session = req.getSession();
		
		System.out.println(req.getRequestURI());
        
		
		///////////////////////////////////////
//		String origin = ""+session.getAttribute("origin");
//		origin = Utility.noNull(origin);
//		origin = origin.toUpperCase();
//		
//		if(origin.length()==0) {
//			
//			System.out.println("origin.length()==0");
//			
//			obj.put("error", new Exception("Origin not found in session."));
//			
//			ModelAndView model = new ModelAndView("/signinV2");
//        	model.setViewName("redirect:/signinV2.htm");
//        	
//        	return model;
//		}
//		else if(origin.equalsIgnoreCase("Microsoft")) {
//			
//			System.out.println("origin.equalsIgnoreCase(\"Microsoft\")");
//			
//			AuthenticationResult result = (AuthenticationResult) session.getAttribute(AuthHelper.PRINCIPAL_SESSION_NAME);
//	        if (result == null) {
//	        	obj.put("error", new Exception("AuthenticationResult not found in session."));
//	        	
//	        	ModelAndView model = new ModelAndView("/signinV2");
//	        	model.setViewName("redirect:/signinV2.htm");
//	        	
//	        	return model;
//	        }
//	        else {
//	        	
//	        	System.out.println("result != null");	
//	        	obj.put("userInfo", result.getUserInfo());
//	        }
//		}
//		else {
//			if(session.getAttribute("username")==null) {
//				
//				System.out.println("session.getAttribute(\"username\")==null");
//				
//				obj.put("error", new Exception("Username not found in session."));
//				
//				ModelAndView model = new ModelAndView("/signinV2");
//	        	model.setViewName("redirect:/signinV2.htm");
//	        	
//	        	return model;
//			}
//		}
		////////////////////////////////////////
        
        
		//Legge il file di properties associato alla lingua sul profilo
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");
		
		// campi passati dalla session
		String username = (String) session.getAttribute("username");
		String desc = (String) session.getAttribute("desc");
		String tipo = (String) session.getAttribute("tipo");
		String foto = (String) session.getAttribute("foto");
		String mail = (String) session.getAttribute("mail");
		
		Connection conn = null;
		Users user = new Users();
		
		try {
			conn = Utility.getConnAdmin();
			
			user.setIdusr(username);
			user.selectRow(conn);
			
			obj.put("_idusr", user.getIdusr());
			obj.put("_nome", user.getNome());
			obj.put("_cogn", user.getCogn());
			obj.put("_foto", user.getFoto());
			obj.put("_active", user.isActive());
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			if(conn!=null)
				conn.close();
		}
		
		
		obj.put("username", username);
		obj.put("desc", desc);
		obj.put("tipo", tipo);
		obj.put("foto", foto);
		obj.put("mail", mail);
		
		if(user.isActive()) {
			
			String fromHalo = ""+session.getAttribute("fromHalo");
			fromHalo = Utility.noNull(fromHalo);
			if(fromHalo.length()>0)
				obj.put("fromHalo", true);
			else
				obj.put("fromHalo", false);
			
			return new ModelAndView("/app/main/index", obj);
		}
		else {
			
			String _signout = "";
			String url = ""+req.getRequestURL();
			String uri = req.getRequestURI();
			
			String url2 = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath();
			System.out.println(url2);
				
			if(user.getOrigin().equalsIgnoreCase("Microsoft")) {
				_signout = "https://login.microsoftonline.com/common/oauth2/v2.0/logout?client_id=cd25dc42-1537-4e99-9607-6bf04b8749a8&post_logout_redirect_uri="+url2+"/signinV2.htm?action=signout";
				//_signout = "https://login.microsoftonline.com/057b692d-19f3-4f35-be28-764d78ae6997/oauth2/logout?client_id=cd25dc42-1537-4e99-9607-6bf04b8749a8&post_logout_redirect_uri="+url2+"/signin.jsp?msg=O";
			}
			else {
				_signout = url2+"/signinV2.htm?action=signout";
			}
			obj.put("_signout", _signout);
			
			return new ModelAndView("/app/main/index-no-auth", obj);
		}
	}
	
}
