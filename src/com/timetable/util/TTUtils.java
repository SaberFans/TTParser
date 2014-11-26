package com.timetable.util;

import com.timetable.ttparser.ParserMain;

import javax.swing.JEditorPane;
import javax.swing.text.*;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.parser.ParserDelegator;

import org.w3c.dom.html.HTMLElement;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * @author epttwxz TTUtils class, utils for converting the HTML timetable to
 *         Java POJO modules - Traversal the elements in timetable - Capture the
 *         modules chunks
 */
public class TTUtils {
	public static void parseIntoStringReader() {
		// Load module timetable 'file' in a reader
		Reader reader = ParserMain.getModuleReader("cs4004");
		StringWriter strreader = new StringWriter();
		HTMLEditorKit editorKit = new HTMLEditorKit();
		HTMLDocument htmlDoc = (HTMLDocument) editorKit.createDefaultDocument();
		htmlDoc.putProperty("IgnoreCharsetDirective", true);

		try {
			editorKit.read(reader, htmlDoc, 0);
			

		} catch (IOException | BadLocationException e1) {
			e1.printStackTrace();
		}

	}

	public static void parser() {
		// Load module timetable 'file' in a reader
		Reader reader = ParserMain.getModuleReader("cs4004");

		JEditorPane p = new JEditorPane();
		p.setContentType("text/html");

		// Document text is provided below.
		// p.setText("<html>   <head>     <title>An example HTMLDocument</title>     <style type='text/css'>       div { background-color: silver; }       ul { color: red; }     </style>   </head>   <body>  <table><tr><td>la</td></tr></table>    <div id='BOX'>       <p>Paragraph 1</p>       <p>Paragraph 2</p>     </div>   </body> </html>\\");
		p.setText("<html><body><table><tr><td>123456789</td></tr><tr><td><font>abcdefghijk</font></td></tr></table></body></html>\\");
		// HTMLDocument htmlDoc = (HTMLDocument) p.getDocument();

		HTMLEditorKit editorKit = new HTMLEditorKit();
		HTMLDocument htmlDoc = (HTMLDocument) editorKit.createDefaultDocument();

		HTMLEditorKit.Parser parser = new ParserDelegator();
		HTMLEditorKit.ParserCallback callback = htmlDoc.getReader(0);
		try {
			htmlDoc.putProperty("IgnoreCharsetDirective", true);

			// editorKit.read(reader, htmlDoc, 0);
			parser.parse(reader, callback, true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(HTML.Tag.DIV.isBlock());
		System.out.println(htmlDoc.getIterator(HTML.Tag.A));

		// ElementIterator it = new ElementIterator(htmlDoc);
		// while (true) {
		// Element el = it.next();
		//
		// if (el == null)
		// break;
		// System.out.println(el.getName());
		// int start = el.getStartOffset();
		// int end = el.getEndOffset();
		// int length = htmlDoc.getLength();
		// String text;
		// try {
		// text = htmlDoc.getText(start, length-1);
		// //System.out.println(el.getAttributes() + " inline text:" +
		// text.trim());
		// } catch (BadLocationException e) {
		//
		// e.printStackTrace();
		// }
		//
		// }

		// Parse Process
		ElementIterator iterator = new ElementIterator(htmlDoc);
		Element element;
		while (true) {
			if ((element = iterator.next()) != null)
				System.out.println();
			else
				break;

			AttributeSet attributes = element.getAttributes();
			Object name = attributes.getAttribute(StyleConstants.NameAttribute);
			System.out.println(name);
			// if (name == HTML.Tag.FONT) {
			// System.out.println("font found");
			// }
		}
	}

}
