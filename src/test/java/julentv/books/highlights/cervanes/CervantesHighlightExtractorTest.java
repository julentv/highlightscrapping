package julentv.books.highlights.cervanes;

import julentv.books.highlights.BookHighlights;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.net.URL;

public class CervantesHighlightExtractorTest {

    private static final String COMPLEX_NAME = "[Gran Super Terror 05] Bachman, Richard - Rabia [3987] (r2.3).epub.html";
    private static final String SIMPLE_NAME = "Bolano, Roberto - Los detectives salvajes [6910] (r1.3).epub.html";
    private CervantesHighlightExtractor extractor;

    @Before
    public void setUp() {
        extractor = new CervantesHighlightExtractor();
    }

    @Test
    public void titleExtractionFromComplexName() {
        URL fileUrl = CervantesHighlightExtractorTest.class.getResource(COMPLEX_NAME);
        StringBuilder folder = getFileFolder(fileUrl);
        BookHighlights highlights = extractor.getHighlights(COMPLEX_NAME, folder.toString());

        Assert.assertEquals("Rabia", highlights.getTitle());
    }

    @Test
    public void titleExtractionFromSimpleName() {
        URL fileUrl = CervantesHighlightExtractorTest.class.getResource(SIMPLE_NAME);
        StringBuilder folder = getFileFolder(fileUrl);
        BookHighlights highlights = extractor.getHighlights(SIMPLE_NAME, folder.toString());

        Assert.assertEquals("Los detectives salvajes", highlights.getTitle());
    }

    private StringBuilder getFileFolder(URL fileUrl) {
        String path = fileUrl.getPath();
        StringBuilder folder = new StringBuilder();
        String[] splitedPath = path.split("/");
        for (int i = 0; i < splitedPath.length - 1; i++) {
            folder.append(splitedPath[i]).append("/");
        }
        folder.deleteCharAt(folder.length()-1);
        return folder;
    }

    @Test
    public void authorExtractionFromComplexName() {
        URL fileUrl = CervantesHighlightExtractorTest.class.getResource(COMPLEX_NAME);
        BookHighlights highlights = extractor.getHighlights(COMPLEX_NAME, fileUrl.getPath());

        Assert.assertEquals("Bachman, Richard", highlights.getAuthor());
    }

    @Test
    public void authorExtractionFromSimpleName() {
        URL fileUrl = CervantesHighlightExtractorTest.class.getResource(SIMPLE_NAME);
        BookHighlights highlights = extractor.getHighlights(SIMPLE_NAME, fileUrl.getPath());

        Assert.assertEquals("Bolano, Roberto", highlights.getAuthor());
    }
}