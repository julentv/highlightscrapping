package julentv.books.google.scraping.pages.book;

import julentv.books.google.scraping.model.HighlightElements;
import julentv.books.google.scraping.model.Page;
import julentv.books.google.scraping.model.Word;
import julentv.books.highlights.BookHighlights;
import julentv.books.highlights.Highlight;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.List;
import java.util.Optional;
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

    public void highlight(BookHighlights bookHighlights) throws InterruptedException {
        initState();
        Page page = getPage();
        Optional<Page> previousPage = Optional.empty();
        for (Highlight textToHighlight : bookHighlights.getHighlights()) {
            if (goToChapter(textToHighlight.getChapter())) {
                page = getPage();
                previousPage = Optional.empty();
            }
            String line = textToHighlight.getLines().stream().reduce((s1, s2) -> s1 + " " + s2).orElse("");
            while (true) {
                if (page.contains(textToHighlight.getLines().get(0))) {
                    System.out.println("Adding note: " + line);
                    highlight(page.getHighlightElements(line));
                    break;
                } else {
                    String twoPages = previousPage.map(Page::getPageText).orElse("") + " " + page.getPageText();
                    if (twoPages.contains(line)) {
                        System.out.println("Adding note: " + line);
                        highlightInTwoPages(textToHighlight, page);
                        break;
                    }
                }
                goToNextPage();
                previousPage = Optional.of(page);
                page = getPage();
            }
        }
    }

    private void highlightInTwoPages(Highlight textToHighlight, Page page) throws InterruptedException {
        String[] words = textToHighlight.getLines().get(0).split(" ");

        int numberOfWordsInCurrentPage = page.getBeginningMatchingWords(words);
        int numberOfWordsInPreviousPage = words.length - numberOfWordsInCurrentPage;
        highlight(new HighlightElements(page.getWords().get(0), page.getWords().get(numberOfWordsInCurrentPage - 1)));
        goToPreviousPage();
        Page previousPage = getPage();
        highlight(new HighlightElements(previousPage.getWords().get(previousPage.getWords().size() - 1),
                previousPage.getWords().get(previousPage.getWords().size() - numberOfWordsInPreviousPage - 1)));
        goToNextPage();
    }

    private boolean goToChapter(String chapterName) throws InterruptedException {
        if (!currentChapter.equals(chapterName)) {
            driver.findElement(By.className("gb-sidebar-button-content")).click();
            driver.findElements(By.className("gb-result-snippet")).stream()
                    .filter(element -> chapterName.equals(element.getText()))
                    .findFirst().ifPresent(WebElement::click);
            driver.findElement(By.className("gb-sidepanel-close")).click();
            currentChapter = chapterName;
            Thread.sleep(1000);
            return true;
        }
        return false;
    }

    private void goToNextPage() {
        WebElement nextPageButton = driver.findElements(By.className("gb-pagination-controls-right")).get(0);
        nextPageButton.click();
    }

    private void goToPreviousPage() {
        WebElement nextPageButton = driver.findElements(By.className("gb-pagination-controls-left")).get(0);
        nextPageButton.click();
    }

    private Page getPage() throws InterruptedException {
        try {
            List<WebElement> words = driver.findElements(By.tagName("gbt"));
            int retries = 0;
            while (words.isEmpty() && retries < 20) {
                Thread.sleep(100);
                words = driver.findElements(By.tagName("gbt"));
                retries++;
            }
            return new Page(words.parallelStream().map(Word::new).collect(Collectors.toList()));
        } catch (Exception e) {
            throw new PageParsingException(e);
        }
    }

    private void highlight(HighlightElements highlightElements) throws InterruptedException {
        Actions builder = new Actions(driver);
        builder.moveToElement(highlightElements.getStartElement().getWebElement())
                .clickAndHold()
                .moveToElement(highlightElements.getEndElement().getWebElement())
                .release()
                .build().perform();

        Thread.sleep(500);
        driver.findElement(By.className("gb-selection-menu-highlight")).click();
    }
}
