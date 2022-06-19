package homePage;

import baseTestsWithSteps.BaseTest;
import helpers.TestTag;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.support.ui.ExpectedConditions;

@Tags({
        @Tag(TestTag.DESKTOP),
        @Tag(TestTag.HOME_PAGE),
})
public class HomePageTest extends BaseTest {

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
    @DisplayName("Работа кнопки \"Вверх\" и ее исчезновение вверху страницы")
    protected void checkUpBtn() {
        closeRegionQuestion();
        waitVisibilityAndScrollTo(By.cssSelector(".footer-block"));
        waitAndClick(By.cssSelector(".back-to-top"));
        Assertions.assertDoesNotThrow(() -> {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".header-padding")));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".back-to-top")));
        });
    }

    @Test
    @DisplayName("Добавление товара в избранное, проверка видимости товара в избранном")
    protected void checkWishlist() {
        closeRegionQuestion();
        clickRandomItem();
        clickRandomElement(By.cssSelector(".block-size__item"));
        waitAndClick(By.cssSelector(".icon--heart.product-info__wish"));
        waitVisibilityAndScrollTo(By.cssSelector(".header-body__wish"));
        pause();
        waitAndClick(By.cssSelector(".header-body__wish"));
        Assertions.assertDoesNotThrow(() -> wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".list-card__link"))));
    }
}

