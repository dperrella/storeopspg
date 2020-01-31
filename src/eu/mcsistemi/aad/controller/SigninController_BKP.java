package eu.mcsistemi.aad.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;


import com.microsoft.aad.adal4j.AuthenticationResult;
import eu.mcsistemi.aad.AuthHelper;

public class SigninController_BKP extends AbstractController {

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
        Map<String, Object> obj = new HashMap<String, Object>();
        HttpSession session = req.getSession();
 
		AuthenticationResult result = (AuthenticationResult) session.getAttribute(AuthHelper.PRINCIPAL_SESSION_NAME);
        if (result == null) {
        	System.out.println("result = null");
        	
        	obj.put("error", new Exception("AuthenticationResult not found in session."));
        	return new ModelAndView("/error", obj);
        }
        else {
        	
        	System.out.println("result != null");
        	
        	obj.put("userInfo", result.getUserInfo());
        	
        }
        return new ModelAndView("/signinV2", obj);
	}

}
