package julentv.books.highlights.cervantes;

import julentv.books.highlights.BookHighlights;
import julentv.books.highlights.HighlightExtractor;
import julentv.books.highlights.cervantes.xml.CervantesXmlHighlightExtractor;

public class CervantesHighlightExtractor implements HighlightExtractor {

    private final CervantesMetadataExtractor metadataExtractor;
    private final CervantesXmlHighlightExtractor xmlHighlightExtractor;

    public CervantesHighlightExtractor(CervantesMetadataExtractor metadataExtractor, CervantesXmlHighlightExtractor xmlHighlightExtractor) {
        this.metadataExtractor = metadataExtractor;
        this.xmlHighlightExtractor = xmlHighlightExtractor;
    }

    @Override
    public BookHighlights getHighlights(String fileName, String path) {
        return new BookHighlights.BookHighlightsBuilder()
                .title(metadataExtractor.extractBookTitle(fileName))
                .author(metadataExtractor.extractAuthor(fileName))
                .highlights(xmlHighlightExtractor.extractHighlights(fileName, path))
                .build();
    }


}
