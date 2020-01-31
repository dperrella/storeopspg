package pkg.controller;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;


import pkg.beans.signin.Users;
import pkg.utility.Utility;

public class Auth extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		HttpSession session = req.getSession();
		
		String name = req.getParameter("name").toUpperCase();
		String idusr = req.getParameter("idusr").toUpperCase();
		String id_token = req.getParameter("id_token");
		String access_token = req.getParameter("access_token");
		String foto = req.getParameter("foto");
		String origin = req.getParameter("origin");
		
		System.out.println(name);
		System.out.println(idusr);
		System.out.println(id_token);
		System.out.println(access_token);
		System.out.println(origin);
		
		session.setAttribute("name", name);
		session.setAttribute("idusr", idusr);
		session.setAttribute("id_token", id_token);
		session.setAttribute("access_token", access_token);
		session.setAttribute("foto", foto);
		session.setAttribute("origin", origin);
		
		Connection conn = null;
		
		session.setAttribute("redirect", "login-esegui-redirect.htm");
		
		try {
			
//			String clientId = "96014a6f-3040-4e2c-ac0f-f497788b5421";
//		    String clientSecret = "TYGNz2g.+f__dvmKDSA7U32rRK*Pi0j-";
//
//		    String resourceUrl = "https://graph.windows.net";
//		    String authorityUrl = "https://login.microsoftonline.com/057b692d-19f3-4f35-be28-764d78ae6997/oauth2/authorize";
//		   
//		    ExecutorService executorService = Executors.newFixedThreadPool(1);
//		    AuthenticationContext authContext = new AuthenticationContext(authorityUrl, false, executorService);

		    
		    
//			String authority = "https://login.microsoftonline.com/contoso.onmicrosoft.com/";
//			ExecutorService service = Executors.newFixedThreadPool(1);
//			AuthenticationContext context = new AuthenticationContext(authority, true, service);
			
			
			
			conn = Utility.getConnAdmin();
			
			if(!Users.userExist(conn, idusr)) {
				
				System.out.println("Utente non esistente... lo scrivo con active = false");
				
				String temp[] = name.split(" ");
				String nome = "";
				String cogn = "";
				if(temp.length==1) {
					nome = temp[0];
				}
				if(temp.length==2) {
					nome = temp[0];
					cogn = temp[1];
				}
				
				Users user = new Users();
				user.setIdusr(idusr);
				user.setNome(nome);
				user.setCogn(cogn);
				user.setMail(idusr);
				user.setFoto(foto);
				user.setActive(false);
				user.setOrigin(origin);
				user.setLanguage("EN");
				user.add(conn);
				
			}
			else {
				System.out.println("Utente esistente...");
				
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			if(conn!=null)
				conn.close();
		}
		
		obj.put("name", name);
		obj.put("idusr", idusr);
		obj.put("id_token", id_token);
		obj.put("access_token", access_token);
		obj.put("foto", foto);
		obj.put("origin", origin);
		
		return new ModelAndView("/auth", obj);
	}
	
}
