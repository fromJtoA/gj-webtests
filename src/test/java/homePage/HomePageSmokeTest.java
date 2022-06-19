package homePage;

import baseTestsWithSteps.BaseTest;
import helpers.TestTag;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

@Tags({
        @Tag(TestTag.SMOKE),
        @Tag(TestTag.DESKTOP),
        @Tag(TestTag.HOME_PAGE),
})
public class HomePageSmokeTest extends BaseTest {

    @BeforeEach
    protected void init() {
        setDriverAndSetWait();
        driver.manage().window().setSize(new Dimension(1920, 1080));
    }

    @AfterEach
    protected void closeTub() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    @DisplayName("Валидация URL адресов у ссылок домашней страницы по коду ответа сервера, " +
            "ссылки с пустым атрибутом href и ссылки на другие ресурсы игнорируются")
    protected void checkURLs() {
        Assertions.assertDoesNotThrow(() -> {
            List<WebElement> links = driver.findElements(By.tagName("a"));
            for (WebElement link : links) {
                String url = link.getAttribute("href");
                System.out.println(url);
                if (url == null || url.isEmpty()) {
                    System.out.println("URL is either not configured for anchor tag or it is empty");
                    continue;
                }
                if (!url.startsWith(BASE_URL)) {
                    System.out.println("URL belongs to another domain, skipping it.");
                    continue;
                }

                HttpURLConnection huc = (HttpURLConnection) (new URL(url).openConnection());
                huc.setRequestMethod("HEAD");
                huc.connect();
                int respCode = huc.getResponseCode();
                if (respCode >= 400) {
                    System.out.println(url + " is a broken link");
                    throw new Exception(url + " is a broken link");
                } else {
                    System.out.println(url + " is a valid link");
                }
            }
        });
    }

    @Test
    @DisplayName("Строка поиска, проверка URL с поисковым значением")
    protected void checkSearch() {
        String request = getRandomString(engLetters, 3);
        closeRegionQuestion();
        waitAndClick(By.id("js-site-search-input"));
        driver.findElement(By.id("js-site-search-input")).sendKeys(request);
        driver.findElement(By.id("js-site-search-input")).sendKeys(Keys.ENTER);
        Assertions.assertDoesNotThrow(() -> wait.until(ExpectedConditions.urlContains("search?text=" + request)));
    }
}

