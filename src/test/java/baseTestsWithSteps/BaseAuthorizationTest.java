package baseTestsWithSteps;

import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

public abstract class BaseAuthorizationTest extends BaseTest {

    @Step("Выход из учетной записи с мобильного устройства")
    protected void logoutMobile() {
        waitAndClick(By.cssSelector(".header-body__burger"));
        waitAndClick(By.cssSelector(".burger-account__logout"));
    }

    @Step("Авторизация через почту с мобильного устройства")
    protected void authorizationMobile() {
        waitAndClick(By.cssSelector(".header-body__burger"));
        waitAndClick(By.cssSelector(".unset-link"));
        authorizationBase();
    }

    @Step("Проверка авторизации с мобильного устройства: на кнопке \"Профиль\" написано имя пользовотеля")
    protected void checkAuthorizationMobile() {
        closeRegionQuestion();
        //пауза чтобы кнопка бургер-меню ожила
        longPause();
        waitAndClick(By.cssSelector(".header-body__burger"));
        checkAuthorization();
    }

    @Step("Выход из учетной записи ")
    protected void logout() {
        WebElement hoverable = driver.findElement(By.cssSelector(".header-body__account-button"));
        new Actions(driver).moveToElement(hoverable).perform();
        waitAndClick(By.cssSelector("a.modal-account-choice.caption--red"));
    }

    protected void authorizationBase() {
        waitAndClick(By.cssSelector(".js-password-form > .text-input:nth-child(1) .js-email-validation"));
        driver.findElement(By.cssSelector(".js-password-form > .text-input:nth-child(1) .js-email-validation")).sendKeys(EMAIL);
        waitAndClick(By.name("password"));
        driver.findElement(By.name("password")).sendKeys(PASSWORD);
        waitAndClick(By.cssSelector(".margin-bottom-16:nth-child(4)"));
    }

    @Step("Авторизация через почту ")
    protected void authorization() {
        waitAndClick(By.cssSelector(".header-body__account-button"));
        authorizationBase();
    }

    @Step("Проверка авторизации : на кнопке \"Профиль\" написано имя пользовотеля")
    protected void checkAuthorization() {
        Assertions.assertDoesNotThrow(() -> wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(NAME))));
    }
}
