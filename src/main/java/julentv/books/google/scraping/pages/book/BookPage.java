package julentv.books.google.scraping.pages.book;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.awt.*;
import java.util.List;
import java.util.Optional;

public class BookPage {
    private final WebDriver driver;
    private boolean inited;

    public BookPage(WebDriver driver) {
        this.driver = driver;
    }

    private void initState() {
        if (!inited) {
            driver.switchTo().frame(0);
        }
    }

    public void move() throws InterruptedException, AWTException {
        initState();
        goToNextPage();
        Thread.sleep(1500);
        highlight();
    }

    private void goToNextPage() {
        WebElement nextPageButton = driver.findElements(By.className("gb-pagination-controls-right")).get(0);
        nextPageButton.click();
    }

    private void highlight() {
        List<WebElement> words = driver.findElements(By.tagName("gbt"));
        Optional<WebElement> wordSi = words.stream().filter(word -> word.getText() != null && "Si".equals(word.getText())).findFirst();
        Optional<WebElement> wordVentas = words.stream().filter(word -> word.getText() != null && "ventas".equals(word.getText())).findFirst();

        Actions builder = new Actions(driver);
        builder.moveToElement(wordSi.get())
                .clickAndHold()
                .moveToElement(wordVentas.get())
                .release()
                .build().perform();

        driver.findElement(By.className("gb-selection-menu-highlight")).click();
    }
}
