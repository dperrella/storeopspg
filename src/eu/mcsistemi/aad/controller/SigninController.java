package eu.mcsistemi.aad.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;


import com.microsoft.aad.adal4j.AuthenticationResult;
import eu.mcsistemi.aad.AuthHelper;
import pkg.utility.Utility;

public class SigninController extends AbstractController {

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
        Map<String, Object> obj = new HashMap<String, Object>();
        HttpSession session = req.getSession();
        
        long ts = new Date().getTime();
        obj.put("ts", ts);
        
        System.out.println(req.getRequestURI());
        
        String action = Utility.noNull(req.getParameter("action"));
        obj.put("action", action);
        
        String userID = Utility.noNull(req.getParameter("userID"));
        obj.put("userID", userID);
        
        
        String message = "";
        
        if(action.equalsIgnoreCase("signout")) {
        	
        	if (session != null) {
        	    session.invalidate();
        	}
        	session = req.getSession();
        	
        	
        	obj.put("error", new Exception("Logout successful."));
        	
        }
        
        switch (action) {
        case "signout":
        	message = "<div class=\"alert alert-warning\"><button type=\"button\" class=\"close\" data-dismiss=\"alert\">&times;</button>" + 
        			"<i class=\"fa fa-ban-circle\"></i><strong>Your account signed out</strong></div>";
        	break;
        case "error":
        	message = "<div class=\"alert alert-warning\"><button type=\"button\" class=\"close\" data-dismiss=\"alert\">&times;</button>" + 
        			"<i class=\"fa fa-ban-circle\"></i><strong>Login Error</strong></div>";
        	break;
        }
        
        obj.put("message",message);
        
        ModelAndView model = getModel(session, obj);
        return model;
        	
      
        
        
	}
    
    public static ModelAndView getModel(HttpSession session, Map<String, Object> obj) {
    	
    	
    	ModelAndView model = null;
    	
    	String origin = ""+session.getAttribute("origin");
		origin = Utility.noNull(origin);
		origin = origin.toUpperCase();
		
		if(origin.length()==0) {
			
			System.out.println("origin.length()==0");
			
			obj.put("error", new Exception("Origin not found in session."));
			return new ModelAndView("/signinV2", obj);
		}
		else if(origin.equalsIgnoreCase("Microsoft")) {
			
			System.out.println("origin.equalsIgnoreCase(\"Microsoft\")");
			
			AuthenticationResult result = (AuthenticationResult) session.getAttribute(AuthHelper.PRINCIPAL_SESSION_NAME);
	        if (result == null) {
	        	//obj.put("error", new Exception("AuthenticationResult not found in session."));
	        	return new ModelAndView("/signinV2", obj);
	        }
	        else {
	        	System.out.println("result != null");	
	        	obj.put("userInfo", result.getUserInfo());
	        	
	        	model = new ModelAndView("/app/main/index.htm");
	        	model.setViewName("redirect:/app/main/index.htm");
	        	
	        	return model;
	        }
		}
		else {
			if(session.getAttribute("username")==null) {
				
				System.out.println("session.getAttribute(\"username\")==null");
				
				obj.put("error", new Exception("Username not found in session."));
				return new ModelAndView("/signinV2", obj);
			}
			else {
				
				model = new ModelAndView("/app/main/index.htm");
	        	model.setViewName("redirect:/app/main/index.htm");
	        	
	        	return model;
	        	
			}
		}
    	
    }

}
