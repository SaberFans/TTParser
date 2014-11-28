package com.timetable.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ModuleTime {
	Date	startTime;
	Date	endTime;
	
	ModuleTime(String start, String end) {
		SimpleDateFormat formatter = new SimpleDateFormat("hh:mm");
		try {
			startTime = formatter.parse(start);
			endTime = formatter.parse(end);
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
	}
	String getStartTime(){
		return endTime.toString();
	}
	String getEndTime(){
		return endTime.toString();
	}
	public static void main(String[] args) {
	
		ModuleTime t = new ModuleTime("11:00", "12:00");
		System.out.println(t.startTime+" " +t.endTime);
	}
	
}
