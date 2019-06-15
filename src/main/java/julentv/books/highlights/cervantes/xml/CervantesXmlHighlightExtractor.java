package julentv.books.highlights.cervantes.xml;

import org.xml.sax.InputSource;
import julentv.books.highlights.Highlight;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class CervantesXmlHighlightExtractor {

    public List<Highlight> extractHighlights(String fileName, String path) {
        try {
            File file = new File(path + "/" + fileName);
            InputStream inputStream = new FileInputStream(file);
            Reader reader = new InputStreamReader(inputStream, StandardCharsets.ISO_8859_1);

            InputSource is = new InputSource(reader);
            is.setEncoding("UTF-8");

            SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
            SAXParser saxParser = saxParserFactory.newSAXParser();
            HighlightHandler handler = new HighlightHandler();
            saxParser.parse(is, handler);

            return handler.getHighlights();
        } catch (Exception e) {
            throw new HighlightExtractionException(e);
        }
    }
}
