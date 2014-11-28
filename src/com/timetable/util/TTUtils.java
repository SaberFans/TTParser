package com.timetable.util;

import javax.swing.text.*;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.parser.ParserDelegator;

import com.timetable.models.Module;

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
import java.util.List;

/**
 * @author epttwxz TTUtils class, Util Class for Parsing the HTML timetable to
 *         Java POJO - Traversal the elements in timetable - Capture the modules
 *         chunks
 */
public class TTUtils {

	static public String	MODULE_TIMETABLE	= "http://www.timetable.ul.ie/mod_res.asp?T1=";
	static public String	STUDENT_TIMETABLE	= "";
	static public String	MODULE_DETAIL_TABLE	= "";

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
	static public void parseModule(String moduleId) {
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
	static public List<Module> extractModule(){
		List<Module> moduleList = new ArrayList<>();
		
		return moduleList;
	}
	static public void parse() {

		Reader reader = getModuleReader("cs4004");
		try {
			HTMLEditorKit.Parser parser = new ParserDelegator();
			HTMLTableParser callback = new HTMLTableParser();
			parser.parse(reader, callback, true);
			System.out.println(callback.dates);
			System.out.println(callback.modules);
			reader.close();
			
			
		} catch (IOException e) {
			System.out.println("Exception occured parsing the TR element");
			e.printStackTrace();
		}
	}

	// Callback for catching the start tag and end tag to intercept the content
	static class HTMLTableParser extends HTMLEditorKit.ParserCallback {
		public List<String>	dates					= new ArrayList<>();
		public List<String>	modules					= new ArrayList<>();
		private boolean		encounteredATableRow	= false;

		public void handleText(char[] data, int pos) {
			if (encounteredATableRow) {
				String token = new String(data);
				String nbsp = (char) 160 + "";
				token = token.replace((char) 160, ' ');

				if (token.trim().length() != 0) {
					if (dates.size() == 6)
						modules.add(token);
					if (dates.size() < 6)
						dates.add(token);
				}

			}
		}

		public void handleStartTag(HTML.Tag t, MutableAttributeSet a, int pos) {
			if (t == HTML.Tag.TD)
				encounteredATableRow = true;
		}

		public void handleEndTag(HTML.Tag t, int pos) {
			if (t == HTML.Tag.TD)
				encounteredATableRow = false;
		}
	}

}
