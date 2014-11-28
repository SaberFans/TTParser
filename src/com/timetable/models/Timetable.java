package com.timetable.models;

/**
 * @author epttwxz
 *	Timetable class,
 *  save the module's timetable
 */
public class Timetable {
	String[] daysInWeek;
	
	Module module;
	
	public Timetable(){
		
	}
	public String[] getDaysInWeek() {
		return daysInWeek;
	}
	public void setDaysInWeek(String[] daysInWeek) {
		this.daysInWeek = daysInWeek;
	}
}
