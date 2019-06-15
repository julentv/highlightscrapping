package julentv.books.google.scraping.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.WebElement;

import java.util.Arrays;

import static org.mockito.Mockito.when;

public class PageTest {

    @Mock
    private WebElement webElement1;
    @Mock
    private WebElement webElement2;
    @Mock
    private WebElement webElement3;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(webElement1.getText()).thenReturn("first");
        when(webElement2.getText()).thenReturn("second");
        when(webElement3.getText()).thenReturn("third");
    }

    @Test
    public void allMatchingWords() {
        Page page = new Page(Arrays.asList(new Word(webElement1), new Word(webElement2), new Word(webElement3)));
        String[] strings = {"first", "second", "third"};
        Assert.assertEquals(3, page.getBeginningMatchingWords(strings));
    }

    @Test
    public void noneMatchingWords() {
        Page page = new Page(Arrays.asList(new Word(webElement1), new Word(webElement2), new Word(webElement3)));
        String[] strings = {"asd", "dsa"};
        Assert.assertEquals(0, page.getBeginningMatchingWords(strings));
    }

    @Test
    public void onlyOneMatching() {
        Page page = new Page(Arrays.asList(new Word(webElement1), new Word(webElement2), new Word(webElement3)));
        String[] strings = {"second", "third", "first"};
        Assert.assertEquals(1, page.getBeginningMatchingWords(strings));
    }
}