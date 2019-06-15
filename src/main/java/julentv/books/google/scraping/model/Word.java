package julentv.books.google.scraping.model;

import org.openqa.selenium.WebElement;

public class Word {
    private final String text;
    private final WebElement webElement;

    public Word(WebElement webElement) {
        this.webElement = webElement;
        this.text = webElement.getText();
    }

    public String getText() {
        return text;
    }

    public WebElement getWebElement() {
        return webElement;
    }
}
