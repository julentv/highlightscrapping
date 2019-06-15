package julentv.books.highlights;

public class BookMetadata {
    private final String title;
    private final String author;

    private BookMetadata(BookMetadataBuilder builder) {
        this.title = builder.getTitle();
        this.author = builder.getAuthor();
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public static class BookMetadataBuilder {
        private String title;
        private String author;

        public String getTitle() {
            return title;
        }

        public BookMetadataBuilder title(String title) {
            this.title = title;
            return this;
        }

        public String getAuthor() {
            return author;
        }

        public BookMetadataBuilder author(String author) {
            this.author = author;
            return this;
        }

        public BookMetadata build() {
            return new BookMetadata(this);
        }
    }
}
