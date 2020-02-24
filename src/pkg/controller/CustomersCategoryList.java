package pkg.controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import pkg.beans.Customers;
import pkg.beans.CustomersCategory;
import pkg.utility.Utility;

public class CustomersCategoryList extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		String idcus = req.getParameter("idcus");
		String description = req.getParameter("description");
		obj.put("idcus", idcus);
		obj.put("description", description);
		obj.put("dsfun", "Lista Customers");
		
		return new ModelAndView("/app/anag/customers-category", obj);
	}
	
}
