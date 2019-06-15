package julentv.books.highlights.cervantes;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CervantesMetadataExtractorTest {
    private static final String COMPLEX_NAME = "[Gran Super Terror 05] Bachman, Richard - Rabia [3987] (r2.3).epub.html";
    private static final String SIMPLE_NAME = "Bolano, Roberto - Los detectives salvajes [6910] (r1.3).epub.html";
    private CervantesMetadataExtractor extractor;

    @Before
    public void setUp() {
        extractor = new CervantesMetadataExtractor();
    }

    @Test
    public void titleExtractionFromComplexName() {
        String bookTitle = extractor.extractBookTitle(COMPLEX_NAME);
        Assert.assertEquals("Rabia", bookTitle);
    }

    @Test
    public void titleExtractionFromSimpleName() {
        String bookTitle = extractor.extractBookTitle(SIMPLE_NAME);
        Assert.assertEquals("Los detectives salvajes", bookTitle);
    }

    @Test
    public void authorExtractionFromComplexName() {
        String authorName = extractor.extractAuthor(COMPLEX_NAME);

        Assert.assertEquals("Bachman, Richard", authorName);
    }

    @Test
    public void authorExtractionFromSimpleName() {
        String authorName = extractor.extractAuthor(SIMPLE_NAME);
        Assert.assertEquals("Bolano, Roberto", authorName);
    }
}