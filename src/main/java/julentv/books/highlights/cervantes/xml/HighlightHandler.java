package julentv.books.highlights.cervantes.xml;

import julentv.books.highlights.Highlight;
import org.apache.commons.lang3.StringUtils;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HighlightHandler extends DefaultHandler {
    private static final String TYPE_PREFIX = "Type: ";
    private static final String HIGHLIGHT_TYPE = "HIGHLIGHT";
    private static final String TEXT_PREFIX = "Text: ";
    public static final String CHAPTER_PREFIX = "Chapter: ";
    private StringBuilder data = null;

    private final List<Highlight> highlights = new ArrayList<>();
    private boolean isHighlight;
    private String content;
    private String chapter;

    public List<Highlight> getHighlights() {
        return highlights;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        // create the data container
        data = new StringBuilder();
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        if (qName.equalsIgnoreCase("p")) {
            String value = data.toString();
            if (value.startsWith(TYPE_PREFIX)) {
                String type = value.replace(TYPE_PREFIX, "");
                if (type.equalsIgnoreCase(HIGHLIGHT_TYPE)) {
                    isHighlight = true;
                }
            } else if (value.startsWith(TEXT_PREFIX)) {
                content = value.replace(TEXT_PREFIX, "");
            } else if (value.startsWith(CHAPTER_PREFIX)) {
                chapter = value.replace(CHAPTER_PREFIX, "");
            }

        } else if (qName.equalsIgnoreCase("div")) {
            if (isHighlight) {
                highlights.add(createHighlightFromText(content));
            }
            restartState();
        }
    }

    @Override
    public void characters(char chars[], int start, int length) {
        data.append(new String(chars, start, length));
    }

    private Highlight createHighlightFromText(String content) {
        return new Highlight(Stream.of(content.split("\n"))
                .map(String::trim)
                .filter(StringUtils::isNotEmpty)
                .collect(Collectors.toList()), chapter);
    }

    private void restartState() {
        isHighlight = false;
        content = null;
    }
}
