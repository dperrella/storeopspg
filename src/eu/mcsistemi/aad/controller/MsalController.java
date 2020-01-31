package eu.mcsistemi.aad.controller;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;


import com.microsoft.aad.adal4j.AuthenticationResult;
import eu.mcsistemi.aad.AuthHelper;
import pkg.beans.signin.Users;
import pkg.utility.Utility;

public class MsalController extends AbstractController {

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
        Map<String, Object> obj = new HashMap<String, Object>();
       
        HttpSession session = req.getSession();
        AuthenticationResult result = (AuthenticationResult) session.getAttribute(AuthHelper.PRINCIPAL_SESSION_NAME);
        if (result == null) {
        	
        	System.out.println("result = null");
            obj.put("error", new Exception("AuthenticationResult not found in session."));
            
            ModelAndView model = new ModelAndView("/signinV2.htm");
        	model.setViewName("redirect:/signinV2.htm");
        	
        	return model;
        	
        } else {
        	
            try {
                String tenant = session.getServletContext().getInitParameter("tenant");
                String client = session.getServletContext().getFilterRegistration("BasicFilter").getInitParameter("client_id");
                
                obj.put("tenant", tenant);
                obj.put("client", client);
                obj.put("userInfo", result.getUserInfo());
                
                String name = "";
                try {
                	name = result.getUserInfo().getGivenName().toUpperCase()+" "+result.getUserInfo().getFamilyName().toUpperCase();
                }
                catch(Exception e) {
                	name = "Unknown Unknown";
                }
        		String idusr = result.getUserInfo().getDisplayableId().toUpperCase();
        		String id_token = result.getIdToken();
        		String access_token = result.getAccessToken();
        		String foto = "../../images/default-icon-user.png";
        		String origin = "Microsoft";
        		
        		System.out.println(name);
        		System.out.println(idusr);
        		System.out.println(id_token);
        		System.out.println(access_token);
        		System.out.println(foto);
        		System.out.println(origin);
        		
        		session.setAttribute("name", name);
        		session.setAttribute("idusr", idusr);
        		session.setAttribute("id_token", id_token);
        		session.setAttribute("access_token", access_token);
        		session.setAttribute("foto", foto);
        		session.setAttribute("origin", origin);
        		
        		Connection conn = null;
        		
        		try {
        			
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
                
            } catch (Exception e) {
            	
            	System.out.println("result != null");
                e.printStackTrace();
                
                obj.put("error", new Exception(e.getMessage()));
                ModelAndView model = new ModelAndView("/signinV2.htm");
            	model.setViewName("redirect:/signinV2.htm");
            	
            	return model;
            }
        }
        
        return new ModelAndView("/msal/auth", obj);
	}

}
