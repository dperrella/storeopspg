package pkg.servlet.nrf;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import pkg.beans.nrf.ConnectedStores;
import pkg.beans.nrf.Country;
import pkg.beans.nrf.Pie;
import pkg.beans.nrf.Heatmap;
import pkg.beans.nrf.Trend;
import pkg.beans.nrf.Linechart;
import pkg.utility.Utility;

/**
 * Servlet implementation class GetRegioni
 */
@WebServlet("/nrf/GetStores")
public class GetStores extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetStores() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Connection conn = null;
		
		try {
			
			String year = ""+request.getParameter("year");
			String month = ""+request.getParameter("month");
			String kpi = ""+request.getParameter("kpi");
			String country = ""+request.getParameter("country");
			String continent = ""+request.getParameter("continent");
			String area = ""+request.getParameter("area");
			
			year = Utility.noNull(year);
			month = Utility.noNull(month);
			kpi = Utility.noNull(kpi);			
			country = Utility.noNull(country);
			continent = Utility.noNull(continent);
			area = Utility.noNull(area);
			
			if(year.length()==0)
				year=null;
			if(month.length()==0)
				month=null;
			if(kpi.length()==0)
				kpi=null;
			if(country.length()==0)
				country=null;
			if(continent.length()==0)
				continent=null;
			if(area.length()==0)
				area=null;
			
			conn=Utility.getConnData();
			
			ArrayList<ConnectedStores> sto = new ArrayList<>();
			sto = ConnectedStores.getStores(conn, country, continent, area);
			
			JSONObject responseJson = new JSONObject();
			JSONArray jsonArray = new JSONArray();
			
			for(ConnectedStores store : sto) {
				
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("idStore", store.getIdStore());
				jsonObj.put("color", store.getColor());
				jsonObj.put("uid", store.getUid());
				jsonObj.put("lat", store.getLat());
				jsonObj.put("lon", store.getLon());
				jsonArray.add(jsonObj);
				
			}
			responseJson.put("listings", jsonArray);
			
			/////////////
			
			ArrayList<Pie> pie = new ArrayList<>();
			pie = Pie.getPie(conn, year, month, country, continent, area, kpi);
			
			JSONArray jsonArray2 = new JSONArray();
			
			for(Pie p : pie) {
				
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("name", p.getName());
				jsonObj.put("value", p.getValue());
				jsonArray2.add(jsonObj);
				
			}
			responseJson.put("pie", jsonArray2);
			
			/////////////
			
			String from = "";
			String to = "";
			
			if(month==null) {
				from = year+"-01-01";
				to = year+"-12-31";
			}
			else {
				
				Calendar calendar = Calendar.getInstance();         
				calendar.set(Calendar.MONTH, Integer.parseInt(month)-1);
				calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
				Date dFrom = calendar.getTime();
				calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
				Date dTo = calendar.getTime();
				
				SimpleDateFormat iso = new SimpleDateFormat("yyyy-MM-dd");
				from = iso.format(dFrom);
				to = iso.format(dTo);
				
			}
			
			
			ArrayList<Heatmap> heatmap = new ArrayList<>();
			heatmap = Heatmap.getHeatmap(conn, from, to, country, continent, area, kpi);
			
			JSONArray jsonArray3 = new JSONArray();
			
			ArrayList l = new ArrayList();
			
			for(Heatmap hm : heatmap) {
				
				JSONObject jsonObj = new JSONObject();
				
				if(hm.getValue()>0)
					l.add(hm.getValue());
				
				ArrayList<Integer> test = new ArrayList<Integer>();
				test.add(hm.getHour());
				test.add(hm.getDow());
				test.add(hm.getValue());
				jsonArray3.add(test);
				
				
			}
			responseJson.put("heatmap", jsonArray3);
			responseJson.put("max", getMax(l));
			
			
			
			/////////////
			
			ArrayList<Country> cou = new ArrayList<>();
			cou = Country.getCountry(conn, continent);
			
			JSONArray jsonArray4 = new JSONArray();
			
			for(Country c : cou) {
				
				JSONObject jsonObj = new JSONObject();
				
				jsonObj.put("area", c.getArea());
				jsonObj.put("country", c.getCountry());
				jsonObj.put("nrStores", c.getNrStores());
				jsonObj.put("idContinent", c.getIdContinent());
				jsonObj.put("idNaz", c.getIdNaz());
				jsonObj.put("idArea", c.getIdArea());
				
				jsonArray4.add(jsonObj);
				
			}
			responseJson.put("country", jsonArray4);
			
			/////////////
			
			ArrayList<Trend> trend = new ArrayList<>();
			trend = Trend.getTrend(conn, year, month, country, continent, area);
			
			JSONArray jsonArray5 = new JSONArray();
			
			for(Trend t : trend) {
				
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("name", t.getName());
				jsonObj.put("value", t.getValue());
				jsonArray5.add(jsonObj);
				
			}
			responseJson.put("trend", jsonArray5);
			
			/////////////
			
			ArrayList<Linechart> linechart = new ArrayList<>();
			linechart = Linechart.getLinechart(conn, year, month, country, continent, area, kpi);
			
			JSONArray jsonArray6 = new JSONArray();
			
			for(Linechart ln : linechart) {
				
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("name", ln.getName());
				jsonObj.put("index", ln.getIndex());
				jsonObj.put("value", ln.getValue());
				jsonObj.put("serie", ln.getSerie());
				
				jsonArray6.add(jsonObj);
				
			}
			responseJson.put("linechart", jsonArray6);
			
			/////////////
			
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().write(responseJson.toString());
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(conn!=null)
					conn.close();
			}
			catch(Exception e) {}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}

	public int getMax(ArrayList list){
	    int max = Integer.MIN_VALUE;
	    for(int i=0; i<list.size(); i++){
	        if((int)list.get(i) > max){
	            max = (int)list.get(i);
	        }
	    }
	    return max;
	}
}
