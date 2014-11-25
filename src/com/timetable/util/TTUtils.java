package com.timetable.util;

import com.timetable.ttparser.ParserMain;

import javax.swing.text.*;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

import javax.swing.text.html.parser.ParserDelegator;
import java.io.IOException;
import java.io.Reader;

/**
 * @author epttwxz
 *	TTUtils class, utils for converting the HTML timetable to Java POJO modules
 *  - Traversal the elements in timetable
 *  - Capture the modules chunks
 */
public class TTUtils {
    public static void parser(){
        // Load module timetable 'file' in a reader
        Reader reader = ParserMain.getModuleReader("cs4004");
        HTMLEditorKit editorKit = new HTMLEditorKit();
        HTMLDocument htmlDoc = new HTMLDocument();
        HTMLEditorKit.Parser parser = new ParserDelegator();
        HTMLEditorKit.ParserCallback callback = htmlDoc.getReader(0);
        try {
            parser.parse(reader, callback, true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Parse Process
        ElementIterator iterator = new ElementIterator(htmlDoc);
        Element element;
        while((element = iterator.next())!=null){
            System.out.println(element);
            AttributeSet attributes = element.getAttributes();
            Object name = attributes.getAttribute(StyleConstants.NameAttribute);
            if(name instanceof HTML.Tag && name== HTML.Tag.TR){
                System.out.println("font found");
            }
        }
    }

}
