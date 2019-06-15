package julentv.books;

import julentv.books.google.api.GoogleBookIdProvider;
import julentv.books.google.scraping.pages.Highlighter;
import julentv.books.highlights.BookHighlights;
import julentv.books.highlights.HighlightExtractor;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class HighlightAdapter {

    private final GoogleBookIdProvider idProvider;
    private final HighlightExtractor highlightExtractor;
    private final Highlighter highlighter;

    public HighlightAdapter(GoogleBookIdProvider idProvider, HighlightExtractor highlightExtractor, Highlighter highlighter) {
        this.idProvider = idProvider;
        this.highlightExtractor = highlightExtractor;
        this.highlighter = highlighter;
    }

    public void adaptHighlights(String fileName, String filePath) throws GeneralSecurityException, IOException, InterruptedException {
        System.out.println("Moving highlights in: " + filePath + "/" + fileName);
        BookHighlights highlights = highlightExtractor.getHighlights(fileName, filePath);
        String googleBookId = idProvider.getIdFromTitle(highlights.getMetadata().getTitle());
        System.out.println("book with title: " + highlights.getMetadata().getTitle() + ", and google id: " + googleBookId);
        highlighter.performHighlights(googleBookId,highlights);
    }
}
