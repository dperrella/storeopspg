package eu.mcsistemi.Entity;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import groovy.transform.ToString;

public class WeekOpeningPlan {
	
	private ArrayList<DailyOpeningPlan> weeklyPlan;
	
	public WeekOpeningPlan() {
		weeklyPlan = new ArrayList<>();
	}
	
	public WeekOpeningPlan(JSONArray weekPlan) {
		weeklyPlan  = new ArrayList<>();
		for (int i = 0 ; i < weekPlan.length(); i++) {
	        JSONObject day = weekPlan.getJSONObject(i);
	        try {
		        JSONObject open = day.getJSONObject("open");
		        JSONObject close = day.getJSONObject("close");
		        
		        int dayId = open.getInt("day");
		        String openTime = open.getString("time");
		        String closeTime = close.getString("time");;
		        String dayName = "";
		        switch (i) {
				case 0:
					dayName = "Sunday"; 
					break;
				case 1:
					dayName = "Monday";
					break;
				case 2:
					dayName = "Tuesday";
					break;
				case 3:
					dayName = "Wednesday";
					break;
				case 4:
					dayName = "Thursday";
					break;
				case 5:
					dayName = "Friday";
					break;
				case 6:
					dayName = "Saturday";
					break;
				default:
					break;
				}
		        weeklyPlan.add(new DailyOpeningPlan(dayId, dayName, openTime, closeTime));
	        }
	        catch(Exception e) {
	        	//e.printStackTrace();
	        }
	        
	    }
	}

	public ArrayList<DailyOpeningPlan> getWeeklyPlan() {
		return weeklyPlan;
	}
	
	public String toString() {
		String toReturn = "";
		int i = 0;
		for (DailyOpeningPlan dailyOpeningPlan : weeklyPlan) {
			toReturn = toReturn + "   Day " + i + ":" + dailyOpeningPlan.toString();
			i++;
		}
		return toReturn;
	}
	

}
