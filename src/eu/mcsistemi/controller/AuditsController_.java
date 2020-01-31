package eu.mcsistemi.controller;

import java.sql.Connection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import eu.mcsistemi.dao.AuditDAO;
import eu.mcsistemi.dao.AuditDAOImpl;
import eu.mcsistemi.model.Audit;
import eu.mcsistemi.model.Database;

public class AuditsController_ extends AbstractController {

	 @Override
	    protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
	        Map<String, Object> obj = new HashMap<String, Object>();
	        Database database = Database.getInstance();
	        database.openConnection();
	        Connection connection = database.getConnection();
	        int storeId = Integer.parseInt(req.getParameter("storeId"));
	        AuditDAO auditDao = new AuditDAOImpl();
			List<Audit> auditList = new LinkedList<>(auditDao.getAuditsByStoreId(connection, storeId));
	        obj.put("auditList", auditList);
	        return new ModelAndView("/auditspage", obj);
	        
	    }
}
