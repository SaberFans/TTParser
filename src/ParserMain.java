import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.parser.ParserDelegator;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by yang on 24/11/2014.
 */
public class ParserMain {
    static public void main(String[]args){
        try {
            // connect the Timetable site through URL
            URL url = new URL("http://www.timetable.ul.ie/mod_res.asp?T1=cs4004");
            URLConnection conn = url.openConnection();
            Reader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));


            HTMLEditorKit.Parser parser = new ParserDelegator();
            parser.parse(reader, new HTMLTableParser(), true);
            reader.close();



        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
class HTMLTableParser extends HTMLEditorKit.ParserCallback {

    private boolean encounteredATableRow = false;

    public void handleText(char[] data, int pos) {
        if(encounteredATableRow) System.out.println(new String(data));
    }

    public void handleStartTag(HTML.Tag t, MutableAttributeSet a, int pos) {
        if(t == HTML.Tag.TR) encounteredATableRow = true;
    }

    public void handleEndTag(HTML.Tag t, int pos) {
        if(t == HTML.Tag.TR) encounteredATableRow = false;
    }
}