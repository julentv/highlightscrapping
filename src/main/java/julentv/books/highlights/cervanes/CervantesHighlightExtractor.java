package julentv.books.highlights.cervanes;

import julentv.books.highlights.BookHighlights;
import julentv.books.highlights.HighlightExtractor;

public class CervantesHighlightExtractor implements HighlightExtractor {

    @Override
    public BookHighlights getHighlights(String fileName, String path) {
        return new BookHighlights.BookHighlightsBuilder()
                .title(extractBookName(fileName))
                .author(extractAuthor(fileName))
                .build();
    }

    private String extractBookName(String fileName) {
        return fileName.split("-")[1].split("\\[")[0].trim();
    }

    private String extractAuthor(String fileName) {
        String[] split = fileName.split("-")[0].split("\\]");
        return split[split.length - 1].trim();
    }
}
