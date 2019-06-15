package julentv.books.highlights.cervantes;

public class CervantesMetadataExtractor {

    public String extractAuthor(String fileName) {
        String[] split = fileName.split("-")[0].split("\\]");
        return split[split.length - 1].trim();
    }

    public String extractBookTitle(String fileName) {
        return fileName.split("-")[1].split("\\[")[0].trim();
    }
}
