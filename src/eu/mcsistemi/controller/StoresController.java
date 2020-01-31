package eu.mcsistemi.controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import eu.mcsistemi.dao.StoreDAO;
import eu.mcsistemi.dao.StoreDAOImpl;
import eu.mcsistemi.dao.UserDAO;
import eu.mcsistemi.dao.UserDAOImpl;
import eu.mcsistemi.model.Database;
import eu.mcsistemi.model.Store;
import eu.mcsistemi.model.User;


	public class StoresController extends AbstractController {

	    @Override
	    protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse res) throws Exception {
	        Map<String, Object> obj = new HashMap<String, Object>();
	        Database database = Database.getInstance();
	        database.openConnection();
	        //TODO Aggiungere getUserId e getCustomerId
	        //String userId = req.getParameter("userId");
	        //int customerId = Integer.parseInt(req.getParameter("customerId"));
	        
	        HttpSession session = req.getSession();
	        
	        String username = session.getAttribute("username").toString();
	        int idcus = Integer.parseInt(session.getAttribute("idcus").toString());
	        
	        UserDAO userDao = new UserDAOImpl();
	        StoreDAO storeDao = new StoreDAOImpl();
			List<Store> storeList = new LinkedList<>(storeDao.getStoresByUserAndCustomerId(database.getConnection(), username,idcus));
	        obj.put("storeList", storeList);
	        return new ModelAndView("/source/anag/audit-list", obj);
	       
	        
	    }

	}

