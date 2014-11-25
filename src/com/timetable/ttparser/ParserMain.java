package com.timetable.ttparser;

import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.parser.ParserDelegator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by yang on 24/11/2014.
 */
public class ParserMain {
	static public String	MODULE_TIMETABLE	= "http://www.timetable.ul.ie/mod_res.asp?T1=";
	static public String	STUDENT_TIMETABLE	= "";
	static public String	MODULE_DETAILS		= "";

	static public Reader getModuleReader(String moduleId) {
		
		// Create the connection to 
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
		
		
		// Create BufferedReader from URL connection
		try {
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			return reader;
		} catch (IOException e) {
			System.out.println("Error occured in get input stream from opening a connection to "
					+ MODULE_TIMETABLE + moduleId);
		}
		return null;
	}

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
			System.out.println("IOException occured in parsing the module(moduleID: "
					+ moduleId);
		}
	}

	static public void parseTR() {

		// HTMLEditorKit.Parser parser = new ParserDelegator();
		// parser.parse(reader, new HTMLTableParser(), true);
		// reader.close();
	}

	static public void main(String[] args) {
		BufferedWriter writer = null;
		String moduleID = "cs4004";

	}
}

class HTMLTableParser extends HTMLEditorKit.ParserCallback {

	private boolean	encounteredATableRow	= false;

	public void handleText(char[] data, int pos) {
		if (encounteredATableRow)
			System.out.println(new String(data));
	}

	public void handleStartTag(HTML.Tag t, MutableAttributeSet a, int pos) {
		if (t == HTML.Tag.TR)
			encounteredATableRow = true;
	}

	public void handleEndTag(HTML.Tag t, int pos) {
		if (t == HTML.Tag.TR)
			encounteredATableRow = false;
	}
}