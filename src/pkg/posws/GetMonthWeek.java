package pkg.posws;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pkg.utility.Utility;


@WebServlet("/GetMonthWeek")
public class GetMonthWeek extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public GetMonthWeek() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		int years = Integer.parseInt(request.getParameter("year"));
		//int years = request.getParameter("year");

		Calendar now = GregorianCalendar.getInstance();
		DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		String resp = "";
		String StoresMonth = "";

		
		Calendar cal = Calendar.getInstance();
	    cal.set(Calendar.YEAR, years);
	    cal.set(Calendar.MONTH, Calendar.DECEMBER);
	    cal.set(Calendar.DAY_OF_MONTH, 31);
		
	    int ordinalYear = Calendar.getInstance().get(Calendar.YEAR);
	    int ordinalDay = cal.get(Calendar.DAY_OF_YEAR);
	    int weekDay = cal.get(Calendar.DAY_OF_WEEK) - 1; // Sunday = 0
	    int numberOfWeeks = (ordinalDay - weekDay + 10) / 7;
		
	    String[] types_ = new String[100];
	    System.out.println("number of week = " + numberOfWeeks);
		String StoresDate = "";
		
		String sm_week_dis_da = " | Settimana dal: ";
		String sm_week_dis_a = " al: ";
	    
        for (int p = 1; p <= numberOfWeeks; p++){
        	String dates = GetDates(p, years);
        	//System.out.println("dates = "+dates);
        		types_ = dates.split("£");
        		StoresDate = StoresDate + "<option value="+p+" value2="+types_[0]+" value3="+types_[1]+">"+p+sm_week_dis_da+Utility.getTurnDate(types_[0])+sm_week_dis_a+Utility.getTurnDate(types_[1])+"</option>";
        }
        
        /**/
    	int anno = years;

        boolean bisestile = ( anno>1584 && ( (anno%400==0) || (anno%4==0 && anno%100!=0) ) );

        if (bisestile){
        	//System.out.println("L'anno " + anno + " e' bisestile");
      	  StoresMonth = "<option value=\"1\" value2=\"01-01\" value3=\"31-01\">GENNAIO</option> "+
				    "<option value=\"2\" value2=\"01-02\" value3=\"29-02\">FEBBRAIO</option> "+
					"<option value=\"3\" value2=\"01-03\" value3=\"31-03\">MARZO</option> "+
					"<option value=\"4\" value2=\"01-04\" value3=\"30-04\">APRILE</option> "+
					"<option value=\"5\" value2=\"01-05\" value3=\"31-05\">MAGGIO</option> "+
					"<option value=\"6\" value2=\"01-06\" value3=\"30-06\">GIUGNO</option> "+
					"<option value=\"7\" value2=\"01-07\" value3=\"31-07\">LUGLIO</option> "+
					"<option value=\"8\" value2=\"01-08\" value3=\"31-08\">AGOSTO</option> "+
					"<option value=\"9\" value2=\"01-09\" value3=\"30-09\">SETTEMBRE</option> "+
					"<option value=\"10\" value2=\"01-10\" value3=\"31-10\">OTTOBRE</option> "+
					"<option value=\"11\" value2=\"01-11\" value3=\"30-11\">NOVEMBRE</option> "+
					"<option value=\"12\" value2=\"01-12\" value3=\"31-12\">DICEMBRE</option> ";        	
        }else{ 
        	//System.out.println("L'anno " + anno + " non e' bisestile");
        	  StoresMonth = "<option value=\"1\" value2=\"01-01\" value3=\"31-01\">GENNAIO</option> "+
        				    "<option value=\"2\" value2=\"01-02\" value3=\"28-02\">FEBBRAIO</option> "+
							"<option value=\"3\" value2=\"01-03\" value3=\"31-03\">MARZO</option> "+
							"<option value=\"4\" value2=\"01-04\" value3=\"30-04\">APRILE</option> "+
							"<option value=\"5\" value2=\"01-05\" value3=\"31-05\">MAGGIO</option> "+
							"<option value=\"6\" value2=\"01-06\" value3=\"30-06\">GIUGNO</option> "+
							"<option value=\"7\" value2=\"01-07\" value3=\"31-07\">LUGLIO</option> "+
							"<option value=\"8\" value2=\"01-08\" value3=\"31-08\">AGOSTO</option> "+
							"<option value=\"9\" value2=\"01-09\" value3=\"30-09\">SETTEMBRE</option> "+
							"<option value=\"10\" value2=\"01-10\" value3=\"31-10\">OTTOBRE</option> "+
							"<option value=\"11\" value2=\"01-11\" value3=\"30-11\">NOVEMBRE</option> "+
							"<option value=\"12\" value2=\"01-12\" value3=\"31-12\">DICEMBRE</option> ";
        }
	    /**/

		resp = StoresDate + "$" + StoresMonth;

		response.setContentType("text/plain");
		
		response.getWriter().write(resp);
		
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	private String GetDates(int noW, int yeaR){
		String Dates = "";
		String Dates1;
		String Dates2;
	
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, yeaR);        
		cal.set(Calendar.WEEK_OF_YEAR, noW);        
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		Dates1 = sdf.format(cal.getTime());

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");   
		cal.add(Calendar.DATE, 6);    
		String convertedDate=dateFormat.format(cal.getTime());    
		Dates2 = convertedDate;
		
		Dates = Dates1 + "£" + Dates2;
		
		//System.out.println("dates = " + Dates);
		
		return Dates;
	}	

}
