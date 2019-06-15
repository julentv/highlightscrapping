package julentv.books.google.scraping.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class InitialPage {
    private final WebDriver driver;

    public InitialPage(WebDriver driver) {
        this.driver = driver;
    }

    public LogInPage goToLogin() {
        WebElement loginButton = driver.findElement(By.id("gb_70"));
        loginButton.click();

        return PageFactory.initElements(driver, LogInPage.class);
    }
}
