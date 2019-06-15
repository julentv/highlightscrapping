package julentv.books.google.scraping.model;

import org.openqa.selenium.WebElement;

public class Word {
    private final String text;
    private final WebElement webElement;

    public Word(WebElement webElement) {
        this.webElement = webElement;
        this.text = webElement.getText().replaceAll("[^a-zA-Z0-9 ]", "");
    }

    public String getText() {
        return text;
    }

    public WebElement getWebElement() {
        return webElement;
    }
}
