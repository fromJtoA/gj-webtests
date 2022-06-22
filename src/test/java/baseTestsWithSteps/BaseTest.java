package baseTestsWithSteps;

import com.google.common.io.Resources;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.security.SecureRandom;
import java.util.List;

public abstract class BaseTest {
    public WebDriver driver;
    public WebDriverWait wait;
    private final SecureRandom rnd = new SecureRandom();
    public final String BASE_URL = "https://www.gloria-jeans.ru/";
    private final long WAIT_DURATION = 10;
    private final long PAUSE_MLS = 2000;
    private final long LONG_PAUSE_MLS = 4000;
    public final String PASSWORD = "fF1234";
    public final String engLetters = "abcdefghijklmnopqrstuvwxyz";
    public String NAME;
    public String EMAIL;

    @BeforeAll
    static void prepareData() {
        System.setProperty("webdriver.chrome.driver", Resources.getResource("chromedriver.exe").getPath());
    }

    protected void setDriverAndSetWait() {
        driver = new ChromeDriver();
        driver.manage().deleteAllCookies();
        driver.get(BASE_URL);
        wait = new WebDriverWait(driver, WAIT_DURATION);
    }

    protected void waitAndClick(By by) {
        wait.until(ExpectedConditions.elementToBeClickable(by));
        driver.findElement(by).click();
    }

    protected void waitAndClick(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
        element.click();
    }

    protected String getRandomString(String letters, int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++)
            sb.append(letters.charAt(rnd.nextInt(letters.length())));
        return sb.toString();
    }

    private WebElement getRandomElement(List<WebElement> elements) {
        int random = rnd.nextInt(elements.size());
        return elements.get(random);
    }

    protected void tryWaitVisibilityAndScrollTo(By by) {
        try {
            wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
            scrollTo(by);
        } catch (Exception ignored) {
        }
    }

    protected void waitVisibilityAndScrollTo(By by) {
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
        scrollTo(by);
    }

    protected void clickRandomElement(By byOfElements) {
        tryWaitVisibilityAndScrollTo(byOfElements);
        if (isDisplay(byOfElements)) {
            pause();
            wait.until(ExpectedConditions.elementToBeClickable(byOfElements));
            getRandomElement(driver.findElements((byOfElements))).click();
        }
    }

    protected void clickElement(By byOfElement) {
        tryWaitVisibilityAndScrollTo(byOfElement);
        pause();
        if (isDisplay(byOfElement)) {
            waitAndClick(byOfElement);
        }
    }

    protected void clickBtnHeaderCatalog() {
        clickElement(By.cssSelector(".header__catalog-button.js-navigation-menu-button"));
    }

    protected void moveToClickRandomElement(By byOfElements) {
        tryWaitVisibilityAndScrollTo(byOfElements);
        if (isDisplay(byOfElements)) {
            List<WebElement> elements = driver.findElements((byOfElements));
            wait.until(ExpectedConditions.elementToBeClickable(byOfElements));
            WebElement element = getRandomElement(elements);
            scrollTo(element);
            pause();
            waitAndClick(element);

        }
    }

    protected void checkClassAndClickRandomSize(By byOfElements) {
        tryWaitVisibilityAndScrollTo(byOfElements);
        if (isDisplay(byOfElements)) {
            List<WebElement> elements = driver.findElements((byOfElements));
            wait.until(ExpectedConditions.elementToBeClickable(byOfElements));
            WebElement element;
            int count = 0;
            int countEnd = 25;
            do {
                count++;
                element = getRandomElement(elements);
            } while (element.getAttribute("class").contains("block-size__item--not-active") & count != countEnd);
            waitAndClick(element);
            //обработка случая, когда товара нет в наличии, т. е. все размеры неактивны
            if (count == countEnd) {
                try {
                    waitAndClick(By.cssSelector(".dropdown__close-button.js-close-button"));
                } catch (Exception ignored) {
                }
                scrollTo(By.cssSelector(".header-menu__source"));
                clickRandomItem();
                checkClassAndClickRandomSize(By.cssSelector(".block-size__item"));
            }
        }
    }

    protected void checkClassAndClickRandomSizeMobile(By byOfElements) {
        tryWaitVisibilityAndScrollTo(byOfElements);
        if (isDisplay(byOfElements)) {
            List<WebElement> elements = driver.findElements((byOfElements));
            wait.until(ExpectedConditions.elementToBeClickable(byOfElements));
            WebElement element;
            int count = 0;
            int countEnd = 25;
            do {
                count++;
                element = getRandomElement(elements);
            } while (element.getAttribute("class").contains("block-size__item--not-active") & count != countEnd);
            pause();
            waitAndClick(element);
            //обработка случая, когда товара нет в наличии, т. е. все размеры неактивны
            if (count == countEnd) {
                try {
                    waitAndClick(By.cssSelector(".dropdown__close-button.js-close-button"));
                } catch (Exception ignored) {
                }
                clickLogo();
                clickBtnHeaderCatalog();
                clickRandomItemMobile();
                checkClassAndClickRandomSizeMobile(By.cssSelector(".block-size__item"));
            }
        }
    }

    protected void clickLogo() {
        scrollTo(By.cssSelector(".icon.icon--logo"));
        pause();
        waitAndClick(By.cssSelector(".icon.icon--logo"));
    }

    protected void scrollTo(By by) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", driver.findElement(by));
    }

    protected void scrollTo(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
    }

    @Step("Проверка видимости элемента с {0}")
    public boolean isDisplay(By by) {
        try {
            return driver.findElement(by).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    @Step("Генерация случайного имени")
    String getName() {
        String rusLetters = "аАбБвВгГдДеЕёЁжЖзЗиИйЙкКлЛмМнНоОпПрРсСтТуУ";
        NAME = "АТ" + getRandomString(rusLetters, 16);
        return NAME;
    }

    @Step("Генерация случайной почты")
    String getEmail() {
        EMAIL = getRandomString(engLetters, 8) + "@" + getRandomString(engLetters, 8) + "." + getRandomString(engLetters, 3);
        return EMAIL;
    }

    protected void registrationBase() {
        waitAndClick(By.cssSelector(".modal-pop-up__header-block__item:nth-child(2)"));
        driver.findElement(By.name("firstName")).click();
        driver.findElement(By.name("firstName")).sendKeys(getName());
        waitAndClick(By.cssSelector(".wrapper-text-zone__input--load-server"));
        driver.findElement(By.cssSelector(".wrapper-text-zone__input--load-server")).sendKeys(getEmail());
        waitAndClick(By.cssSelector(".send-form:nth-child(1) > .text-input:nth-child(3) .wrapper-text-zone__input"));
        driver.findElement(By.cssSelector(".send-form:nth-child(1) > .text-input:nth-child(3) .wrapper-text-zone__input")).sendKeys(PASSWORD);
        waitAndClick(By.cssSelector(".text-input:nth-child(4) .checkbox__text"));
        waitAndClick(By.cssSelector(".send-form__button-block:nth-child(5) > .js-button-form"));
        try {
            waitAndClick(By.cssSelector(".caption-12:nth-child(4)"));
        } catch (Exception ignored) {
        }
    }

    protected void closeRegionQuestion() {
        if (isDisplay(By.cssSelector(".uk-button--black"))) {
            waitAndClick(By.cssSelector(".uk-button--black"));
        }
    }

    protected void pause() {
        new Actions(driver).pause(PAUSE_MLS).perform();
    }

    protected void longPause() {
        new Actions(driver).pause(LONG_PAUSE_MLS).perform();
    }

    @Step("Регистрация через почту ")
    protected void registration() {
        waitAndClick((By.cssSelector(".header-body__account-button")));
        registrationBase();
    }

    @Step("Регистрация через почту с мобильного устройства")
    protected void registrationMobile() {
        waitAndClick(By.cssSelector(".header-body__burger"));
        waitAndClick(By.cssSelector(".unset-link"));
        registrationBase();
    }

    @Step("Нажатие на случайные: кнопку хедера, кнопки левого меню (если есть) и клик по случайному товару")
    protected void clickRandomItem() {
        clickRandomElement(By.cssSelector(".header-menu__source"));
        clickRandomElement(By.cssSelector("a.kind-people"));
        clickRandomElement(By.cssSelector("a.kind-age"));
        //для случая нажатия кнопки "Акции"
        moveToClickRandomElement(By.cssSelector(".promotions-list-item"));
        moveToClickRandomElement(By.cssSelector(".listing-item__container.js-product-ref-link"));
    }

    @Step("Нажатие на «Каталог товаров» - «Sale» или «Акции», клик по случайному товару с мобильного устройства")
    protected void clickRandomItemMobile() {
        clickRandomElement(By.cssSelector("a.header-menu__source"));
        //для случая нажатия кнопки "Акции"
        moveToClickRandomElement(By.cssSelector(".promotions-list-item"));
        moveToClickRandomElement(By.cssSelector(".listing-item__container.js-product-ref-link"));
    }

    @Step("Проверка авторизации : на кнопке \"Профиль\" написано имя пользовотеля")
    protected void checkAuthorization() {
        Assertions.assertDoesNotThrow(() -> wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(NAME))));
    }
}
