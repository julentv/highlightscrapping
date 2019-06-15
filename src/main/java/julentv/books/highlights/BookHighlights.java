package julentv.books.highlights;

import java.util.List;

public class BookHighlights {
    private final BookMetadata metadata;
    private final List<Highlight> highlights;

    private BookHighlights(BookHighlightsBuilder builder) {
        this.metadata = builder.getMetadata();
        this.highlights = builder.getHighlights();
    }

    public BookMetadata getMetadata() {
        return metadata;
    }

    public List<Highlight> getHighlights() {
        return highlights;
    }

    public static class BookHighlightsBuilder {
        private BookMetadata metadata;
        private List<Highlight> highlights;

        public BookHighlightsBuilder metadata(BookMetadata metadata) {
            this.metadata = metadata;
            return this;
        }

        public BookHighlightsBuilder highlights(List<Highlight> highlights) {
            this.highlights = highlights;
            return this;
        }

        public BookMetadata getMetadata() {
            return metadata;
        }

        public List<Highlight> getHighlights() {
            return highlights;
        }

        public BookHighlights build() {
            return new BookHighlights(this);
        }
    }
}
