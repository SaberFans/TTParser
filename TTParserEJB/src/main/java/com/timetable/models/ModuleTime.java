package com.timetable.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.timetable.util.TTUtils;


public class ModuleTime {
	// private static final Logger logger =
	// LoggerFactory.getLogger(ModuleTime.class);
	private Date	startTime;
	private Date	endTime;
	private String	type;
	private String	group;

	private String	moduleTeacher;
	private Date	dayInWeek;
	private String	room;
	private String	duration;

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

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
		SimpleDateFormat formatter = new SimpleDateFormat("hh:mm");
		return formatter.format(startTime);
	}

	public String getEndTime() {
		SimpleDateFormat formatter = new SimpleDateFormat("hh:mm");
		return formatter.format(endTime);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getModuleTeacher() {
		return moduleTeacher;
	}

	public void setModuleTeacher(String moduleTeacher) {
		this.moduleTeacher = moduleTeacher;
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

	// Variant getter for dayInWeek
	public Date getDayInWeek() {
		return dayInWeek;
	}

	public String getDayInWeekString() {
		SimpleDateFormat format = new SimpleDateFormat("EEEE");
		return format.format(this.dayInWeek);
	}

	// Variant setter for dayInWeek
	public void setDayInWeek(String dayInWeek) {
		SimpleDateFormat format = new SimpleDateFormat("EEE");
		try {
			this.dayInWeek = format.parse(dayInWeek);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public void setDayInWeek(Date dayInWeek) {
		this.dayInWeek = dayInWeek;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		SimpleDateFormat formatter = new SimpleDateFormat("hh:mm");
		builder.append(getDayInWeekString())
				.append(", ")
				.append(getStartTime()).append("-").append(getEndTime())
				.append(", ")
				.append(getType())
				.append(", ")
				.append(getModuleTeacher())
				.append(", ")
				.append(getRoom());

		return builder.toString();
	}

	public static void main(String[] args) {

		// ModuleTime t = new ModuleTime("11:00", "12:00");

		List<ModuleTime> list = TTUtils.parseModuleTime("AC4213");
		for (ModuleTime time : list) {
			System.out.println(time);
		}

	}

}
