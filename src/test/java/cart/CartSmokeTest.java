package cart;

import baseTestsWithSteps.BaseCartTest;
import helpers.TestTag;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Dimension;

@Tags({
        @Tag(TestTag.SMOKE),
        @Tag(TestTag.DESKTOP),
        @Tag(TestTag.CART),
})
public class CartSmokeTest extends BaseCartTest {

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
    @DisplayName("Добавление в корзину и заказ случайного товара после регистрации")
    protected void addInCartAfterRegistration() {
        chooseOmsk();
        //пауза, чтобы хедер успел прогрузиться
        pause();
        registration();
        clickRandomItem();
        getSizeAndGoToCart();
        checkCart();
        checkErrorSum();
        makeOrderAfterRegistration();
        //пауза, чтобы успела прогрузиться страница checkout/orderConfirmation
        longPause();
        checkOrder();
        pause();
        closeBannerAndGoToCard();
        pause();
        checkCartAfterOrder();
    }

    @Test
    @DisplayName("Добавление в корзину и заказ случайного товара до регистрации")
    protected void addInCartBeforeRegistration() {
        chooseOmsk();
        //пауза, чтобы хедер успел прогрузиться
        pause();
        clickRandomItem();
        getSizeAndGoToCart();
        checkCart();
        checkErrorSum();
        makeOrderBeforeRegistration();
        //пауза, чтобы успела прогрузиться страница checkout/orderConfirmation
        longPause();
        checkOrder();
        pause();
        closeBannerAndGoToCard();
        pause();
        checkCartAfterOrder();
    }
}
