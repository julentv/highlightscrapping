package julentv.books.highlights;

import java.util.List;

public class BookHighlights {
    private final String title;
    private final String author;
    private final List<Highlight> highlights;

    private BookHighlights(BookHighlightsBuilder builder) {
        this.title = builder.getTitle();
        this.author = builder.getAuthor();
        this.highlights = builder.getHighlights();
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public List<Highlight> getHighlights() {
        return highlights;
    }

    public static class BookHighlightsBuilder {
        private String title;
        private String author;
        private List<Highlight> highlights;

        public BookHighlightsBuilder title(String bookName) {
            this.title = bookName;
            return this;
        }

        public BookHighlightsBuilder author(String author) {
            this.author = author;
            return this;
        }

        public BookHighlightsBuilder highlights(List<Highlight> highlights) {
            this.highlights = highlights;
            return this;
        }

        public String getTitle() {
            return title;
        }

        public String getAuthor() {
            return author;
        }

        public List<Highlight> getHighlights() {
            return highlights;
        }

        public BookHighlights build() {
            return new BookHighlights(this);
        }
    }
}
