package julentv.books.google.scraping.pages;

import julentv.books.google.scraping.pages.book.BookPage;
import julentv.books.highlights.BookHighlights;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;

public class Highlighter {
    private static final String DRIVER = "D:\\dev\\workspace\\highlightsscrapping\\lib\\geckodriver.exe";

    private final String user;
    private final String password;

    public Highlighter(String user, String password) {
        this.user = user;
        this.password = password;
    }

    public void performHighlights(String googleBookId, BookHighlights highlights) throws InterruptedException {
        System.setProperty("webdriver.gecko.driver", DRIVER);
        WebDriver driver = new FirefoxDriver();   //print results
        logIn(driver);
        BookPage bookPage = openBook(driver, googleBookId);
        bookPage.highlight(highlights);
    }

    private BookPage openBook(WebDriver driver, String googleBookId) throws InterruptedException {
        driver.get("https://play.google.com/books/reader?id=" + googleBookId + "&pg=GBS.PA1");
        Thread.sleep(2000);
        return PageFactory.initElements(driver, BookPage.class);
    }

    private void logIn(WebDriver driver) throws InterruptedException {
        driver.get("https://play.google.com/books/uploads");
        InitialPage initialPage = PageFactory.initElements(driver, InitialPage.class);
        LogInPage logInPage = initialPage.goToLogin();
        logInPage.login(user, password);
    }
}
