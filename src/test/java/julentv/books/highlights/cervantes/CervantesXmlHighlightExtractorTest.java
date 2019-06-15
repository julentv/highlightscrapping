package julentv.books.highlights.cervantes;

import julentv.books.highlights.Highlight;
import julentv.books.highlights.cervantes.xml.CervantesXmlHighlightExtractor;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.net.URL;
import java.util.List;

public class CervantesXmlHighlightExtractorTest {
    private static final String SIMPLE_NAME = "Bolano, Roberto - Los detectives salvajes [6910] (r1.3).epub.html";
    private static final String FIRST_HIGHLIGHT = "El método era el idóneo para que nadie fuera amigo de nadie o para que las amistades se cimentaran en la enfermedad y el rencor";
    private static final String SECOND_HIGHLIGHT = "Sobre ti? -ahora Brígida sonreía y esa, supuse, era su victoria.\n" +
            "-Sobre mí, claro -le dije mientras vaciaba el vaso de tequila.\n" +
            "-Que vas a morir joven, Juan, que vas a desgraciar a Rosario";

    private CervantesXmlHighlightExtractor extractor;

    @Before
    public void setUp() {
        extractor = new CervantesXmlHighlightExtractor();
    }

    @Test
    public void obtainHighlights() {
        URL url = CervantesXmlHighlightExtractorTest.class.getResource(SIMPLE_NAME);
        List<Highlight> highlights = extractor.extractHighlights(SIMPLE_NAME, getFileFolder(url));

        Assert.assertEquals(2, highlights.size());
        Assert.assertEquals(FIRST_HIGHLIGHT, highlights.get(0).toString());
        Assert.assertEquals(SECOND_HIGHLIGHT, highlights.get(1).toString());

        Assert.assertEquals("I. Mexicanos perdidos en México (1975)", highlights.get(0).getChapter());
        Assert.assertEquals("II. Otro", highlights.get(1).getChapter());

    }

    private String getFileFolder(URL fileUrl) {
        String path = fileUrl.getPath();
        StringBuilder folder = new StringBuilder();
        String[] splitedPath = path.split("/");
        for (int i = 0; i < splitedPath.length - 1; i++) {
            folder.append(splitedPath[i]).append("/");
        }
        folder.deleteCharAt(folder.length() - 1);
        return folder.toString();
    }
}