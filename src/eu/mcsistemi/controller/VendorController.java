package eu.mcsistemi.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

public class VendorController extends AbstractController {
	   @Override
	    protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
	        Map<String, Object> obj = new HashMap<String, Object>();
	        
	        HttpSession session = req.getSession();
	        
	        String username = session.getAttribute("username").toString();
	        
	        
	        obj.put("userId",username);
	        return new ModelAndView("/source/anag/vendor-list", obj);
	    }
}
