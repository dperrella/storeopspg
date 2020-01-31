package eu.mcsistemi.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import pkg.properties.ReadProperties;
import pkg.utility.Utility;

public class AuditItemsController extends AbstractController {
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, Object> obj = new HashMap<String, Object>();
		
		HttpSession session = req.getSession();
		String idusr = (String)session.getAttribute("username");
		String id = ""+req.getParameter("id");
		id = Utility.noNull(id);
		id=id.replaceAll("\\?cd=undefined", "");
		String tipoForm = req.getParameter("tipoForm");
		tipoForm=tipoForm.replaceAll("\\?cd=undefined", "");
		
		//Legge il file di properties associato alla lingua sul profilo
		ReadProperties propLang = new ReadProperties(session.getAttribute("language").toString()+".properties");
		
		obj.put("tipoForm", tipoForm);
		obj.put("id", id);
		obj.put("idusr", idusr);
		
		return new ModelAndView("/source/anag/audit-items", obj);
	}
}
