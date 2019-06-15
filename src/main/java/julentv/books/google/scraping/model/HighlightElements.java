package julentv.books.google.scraping.model;

public class HighlightElements {
    private final Word startElement;
    private final Word endElement;

    public HighlightElements(Word startElement, Word endElement) {
        this.startElement = startElement;
        this.endElement = endElement;
    }

    public Word getStartElement() {
        return startElement;
    }

    public Word getEndElement() {
        return endElement;
    }
}
