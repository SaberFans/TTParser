package com.timetable.util;

import javax.swing.text.*;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.parser.ParserDelegator;

import com.timetable.models.Module;
import com.timetable.models.ModuleTime;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author epttwxz ----------------------------------------------------------
 *         TTUtils class, Util Class for Parsing the HTML timetable to Java POJO
 *         - Traversal the elements in timetable - Capture the modules chunks
 */
public class TTUtils {
	static public String		MODULE_TIMETABLE	= "http://www.timetable.ul.ie/mod_res.asp?T1=";
	static public String		STUDENT_TIMETABLE	= "http://www.timetable.ul.ie/";
	static public String		MODULE_DETAIL_TABLE	= "http://www.timetable.ul.ie/";
	static public String[]		DAY_IN_WEEK			= { "MON", "TUE", "WED", "THU", "FRI" };

	// Get Module timetable into a Reader
	static public Reader getModuleReader(String moduleId) {

		// Create the connection to
		URL url = null;
		URLConnection conn = null;
		try {
			url = new URL(MODULE_TIMETABLE + moduleId);
			conn = url.openConnection();
		} catch (MalformedURLException e1) {
			System.out.println("MalformedURLException occured in initializing URL, with url: "
					+ MODULE_TIMETABLE + moduleId);
		} catch (IOException e) {
			System.out.println("IOException occured in URL.openConnection, with url: "
					+ MODULE_TIMETABLE + moduleId);
		}

		// Create BufferedReader from URL connection
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			return reader;
		} catch (IOException e) {
			System.out
					.println("Error occured in get input stream from opening a connection to "
							+ MODULE_TIMETABLE + moduleId);
		}
		return null;
	}

	// Save module timetable into local file
	static public void saveModuleTime(String moduleId) {
		URL url = null;
		URLConnection conn = null;
		try {
			url = new URL(MODULE_TIMETABLE + moduleId);
			conn = url.openConnection();
		} catch (MalformedURLException e1) {
			System.out.println("Error occured in initializing URL, with url: "
					+ MODULE_TIMETABLE + moduleId);
		} catch (IOException e) {
			System.out.println("Error occured in URL.openConnection, with url: "
					+ MODULE_TIMETABLE + moduleId);
		}

		// Save to local file
		File file = new File("./tdata/" + moduleId + ".html");

		String line;
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(
				conn.getInputStream()));
				BufferedWriter writer = new BufferedWriter(new FileWriter(file));) {
			while ((line = reader.readLine()) != null) {
				writer.write(line);
				writer.write("\n");
			}
		} catch (IOException e) {
			System.out
					.println("IOException occured in saving the module timetable (moduleID: "
							+ moduleId);
		}
	}

	static public List<Module> extractModule() {
		List<Module> moduleList = new ArrayList<>();

		return moduleList;
	}

	// Parse module timetable into ModuleTime Plain Old Java Object
	static public List<ModuleTime> parseModuleTime(String moduleCode) {
		List<ModuleTime> moduleList = new ArrayList<>();

		try (Reader reader = getModuleReader(moduleCode);) {
			HTMLEditorKit.Parser parser = new ParserDelegator();
			HTMLTableParser callback = new HTMLTableParser();
			parser.parse(reader, callback, true);
			System.out.println(callback.dates);
			System.out.println(callback.modules);
			int day = 0;
			for (Iterator<String> it = callback.modules.iterator(); it.hasNext();) {

				String separator = null;
				while (it.hasNext()) {
					if ((separator = it.next()).equals("/"))
						day++;
					else
						break;
				}
				if(!it.hasNext())
					break;
				String startTime = separator;
				String dash = it.next();
				String endTime = it.next();
				String group = null;

				String type = it.next();
				dash = it.next();

				if (!type.equals("LEC")) {
					group = it.next();
				}
				String moduleTeacher = it.next();
				String location = it.next();
				String duration = it.next();

				// create moduletime based on parsing
				ModuleTime moduleTime = new ModuleTime(startTime, endTime);
				moduleTime.setType(type);
				moduleTime.setGroup(group);
				moduleTime.setModuleTeacher(moduleTeacher);
				moduleTime.setRoom(location);
				moduleTime.setDuration(duration);
				moduleTime.setDayInWeek(TTUtils.DAY_IN_WEEK[day]);
				moduleList.add(moduleTime);

			}
			return moduleList;

		} catch (IOException e) {
			System.out.println("Exception occured parsing the TR element");
			e.printStackTrace();
			return moduleList;
		}

	}

	// Callback for catching the start tag and end tag to intercept the content
	static class HTMLTableParser extends HTMLEditorKit.ParserCallback {
		public List<String>		dates					= new ArrayList<>();
		public List<String>		modules					= new ArrayList<>();
		private boolean			encounteredATableRow	= false;
		public static String	DAY_SEPARATOR			= "/";
		private boolean			enterTimeGrid			= false;

		public void handleText(char[] data, int pos) {
			if (encounteredATableRow) {
				String token = new String(data);
				char nbsp = 160;
				token = token.replace(nbsp, ' ');

				if (token.trim().length() != 0) {
					if (dates.size() == 6)
						modules.add(token.trim());
					if (dates.size() < 6)
						dates.add(token.trim());
				}

			}
		}

		public void handleStartTag(HTML.Tag t, MutableAttributeSet a, int pos) {
			if (t == HTML.Tag.TD)
				encounteredATableRow = true;
		}

		public void handleEndTag(HTML.Tag t, int pos) {
			if (t == HTML.Tag.TD) {
				encounteredATableRow = false;

				if (enterTimeGrid)
					modules.add(DAY_SEPARATOR);
				else if (dates.size() == 6)
					enterTimeGrid = true;
			}
		}
	}

}
