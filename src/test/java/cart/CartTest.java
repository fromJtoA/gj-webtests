package cart;

import baseTestsWithSteps.BaseCartTest;
import helpers.TestTag;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Dimension;

@Tags({
        @Tag(TestTag.DESKTOP),
        @Tag(TestTag.CART),
})
public class CartTest extends BaseCartTest {

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
    @DisplayName("Добавление в корзину и заказ двух случайных товаров после регистрации")
    protected void addInCartTwoItemsAfterRegistration() {
        chooseOmsk();
        //пауза, чтобы хедер успел прогрузиться
        pause();
        registration();
        clickRandomItem();
        getSizeAndContinueAddItems();
        clickRandomItem();
        getSizeAndGoToCart();
        checkCart();
        checkErrorSum();
        makeOrderAfterRegistration();
        //пауза, чтобы успела прогрузиться страница checkout/orderConfirmation
        pause();
        checkOrder();
    }

    @Test
    @DisplayName("Добавление в корзину и заказ трех случайных товаров до регистрации")
    protected void addInCartThreeItemsBeforeRegistration() {
        chooseOmsk();
        //пауза, чтобы хедер успел прогрузиться
        pause();
        clickRandomItem();
        getSizeAndContinueAddItems();
        clickRandomItem();
        getSizeAndContinueAddItems();
        clickRandomItem();
        getSizeAndGoToCart();
        checkCart();
        checkErrorSum();
        makeOrderBeforeRegistration();
        //пауза, чтобы успела прогрузиться страница checkout/orderConfirmation
        pause();
        checkOrder();
    }
}
