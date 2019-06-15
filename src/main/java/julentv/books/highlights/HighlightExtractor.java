package julentv.books.highlights;

public interface HighlightExtractor {

    BookHighlights getHighlights(String fileName, String path);
}
