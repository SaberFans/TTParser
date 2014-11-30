package com.timetable.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.timetable.util.TTUtils;

public class ModuleTime {
	Date	startTime;
	Date	endTime;
	String	type;
	String	group;
	String	duration;

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	String	location;

	String	room;

	public ModuleTime(String start, String end) {
		SimpleDateFormat formatter = new SimpleDateFormat("hh:mm");
		try {
			startTime = formatter.parse(start);
			endTime = formatter.parse(end);
		} catch (ParseException e) {

			e.printStackTrace();
		}
	}

	public String getStartTime() {
		return endTime.toString();
	}

	public String getEndTime() {
		return endTime.toString();
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public static void main(String[] args) {

		// ModuleTime t = new ModuleTime("11:00", "12:00");

		TTUtils.parse();

	}

}
