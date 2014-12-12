package com.timetable.ws;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Path;

import com.timetable.models.Module;

@Path("/ttresource")

public class TTResourceService {
	

	@Path("/studenttable/{id}")
	public List<Module> getStudentTimetable(){
		List<Module> timetable = new ArrayList<>();
		return timetable;
	}


}
