package julentv.books.google.scraping.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LogInPage {
    private final WebDriver driver;

    public LogInPage(WebDriver driver) {
        this.driver = driver;
    }

    public void login(String email, String password) throws InterruptedException {
        WebElement emailBox = driver.findElement(By.id("identifierId"));
        emailBox.sendKeys(email);

        WebElement nextButton = driver.findElement(By.id("identifierNext"));
        nextButton.click();

        Thread.sleep(1000);
        WebElement passwordBox = driver.findElements(By.name("password")).get(0);
        passwordBox.sendKeys(password);

        WebElement passwordNextButton = driver.findElement(By.id("passwordNext"));
        passwordNextButton.click();
    }
}
