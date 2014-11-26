package com.timetable.ttparser;

import com.timetable.util.TTUtils;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.ElementIterator;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.parser.ParserDelegator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;

/**
 * @author epttwxz
 *
 */
public class ParserMain {
	static public String	MODULE_TIMETABLE	= "http://www.timetable.ul.ie/mod_res.asp?T1=";
	static public String	STUDENT_TIMETABLE	= "";
	static public String	MODULE_DETAILS		= "";

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

	// call the HTMLTableParser callback
	static public void parseTR() {

		Reader reader = getModuleReader("cs4004");

		HTMLEditorKit.Parser parser = new ParserDelegator();
		try {
			parser.parse(reader, new HTMLTableParser(), true);
		} catch (IOException e) {
			System.out.println("IOException occured in parsing the module(moduleID: "
					+ "cs4004");
			e.printStackTrace();
		}
		try {
			reader.close();
		} catch (IOException e) {
			System.out.println("Exception occured in closing the reader");
			e.printStackTrace();
		}
	}

	static public void parseToHTML() {
		Reader reader = new StringReader(
				"<html><body><ul></ul><table><tr><td>addf</td></tr></table></body></html>");// getModuleReader("cs4004");

		HTMLEditorKit htmlKit = new HTMLEditorKit();
		HTMLDocument htmlDoc = (HTMLDocument) htmlKit.createDefaultDocument();
		HTMLEditorKit.Parser parser = new ParserDelegator();
		try {
			// parsing reader content into htmlDoc
			parser.parse(reader, htmlDoc.getReader(0), true);

			// After parsing the htmlDoc should contain the HTML DOM elements
			// for visiting
			for (HTMLDocument.Iterator it = htmlDoc.getIterator(HTML.Tag.UL); it.isValid(); it
					.next()) {
				System.out.println("breaking");
				int start = it.getStartOffset();
				int size = it.getEndOffset() - start;

				System.out.println(htmlDoc.getText(start, size));
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	static public void main(String[] args) {
		Reader reader = getModuleReader("cs4004");

		TTUtils.parseIntoStringReader();
	}
}

// Callback for catching the start tag and end tag to intercept the content
class HTMLTableParser extends HTMLEditorKit.ParserCallback {

	private boolean	encounteredATableRow	= false;

	public void handleText(char[] data, int pos) {
		if (encounteredATableRow)
			System.out.println(new String(data));
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