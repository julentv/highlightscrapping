package julentv.books.google.scraping.pages.book;

import julentv.books.google.scraping.model.HighlightElements;
import julentv.books.google.scraping.model.Page;
import julentv.books.google.scraping.model.Word;
import julentv.books.highlights.Highlight;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.List;
import java.util.stream.Collectors;

public class BookPage {
    private final WebDriver driver;
    private boolean inited;
    private String currentChapter = "";

    public BookPage(WebDriver driver) {
        this.driver = driver;
    }

    private void initState() {
        if (!inited) {
            driver.switchTo().frame(0);
            inited = true;
        }
    }

    public void highlight(Highlight textToHighlight) throws InterruptedException {
        initState();
        goToChapter(textToHighlight.getChapter());
        Page page = getPage();
        while (!page.contains(textToHighlight.getLines().get(0))) {
            goToNextPage();
            page = getPage();
        }
        System.out.println("found!");
        highlight(textToHighlight.getLines().get(0), page);
    }

    private void goToChapter(String chapterName) throws InterruptedException {
        if (!currentChapter.equals(chapterName)) {
            driver.findElement(By.className("gb-sidebar-button-content")).click();
            driver.findElements(By.className("gb-result-snippet")).stream()
                    .filter(element -> chapterName.equals(element.getText()))
                    .findFirst().ifPresent(WebElement::click);
            driver.findElement(By.className("gb-sidepanel-close")).click();
            currentChapter = chapterName;
            Thread.sleep(1000);
        }
    }

    private void goToNextPage() {
        WebElement nextPageButton = driver.findElements(By.className("gb-pagination-controls-right")).get(0);
        nextPageButton.click();
    }

    private Page getPage() throws InterruptedException {
        List<WebElement> words = driver.findElements(By.tagName("gbt"));
        int retries = 0;
        while (words.isEmpty() && retries < 10) {
            Thread.sleep(100);
            words = driver.findElements(By.tagName("gbt"));
            retries++;
        }
        return new Page(words.stream().map(Word::new).collect(Collectors.toList()));
    }

    private void highlight(String textToHighlight, Page page) {
        HighlightElements highlightElements = page.getHighlightElements(textToHighlight);

        Actions builder = new Actions(driver);
        builder.moveToElement(highlightElements.getStartElement().getWebElement())
                .clickAndHold()
                .moveToElement(highlightElements.getEndElement().getWebElement())
                .release()
                .build().perform();

        driver.findElement(By.className("gb-selection-menu-highlight")).click();
    }
}
