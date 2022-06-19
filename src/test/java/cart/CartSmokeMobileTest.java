package cart;

import baseTestsWithSteps.BaseCartTest;
import helpers.TestTag;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Dimension;

@Tags({
        @Tag(TestTag.SMOKE),
        @Tag(TestTag.MOBILE),
        @Tag(TestTag.CART),
})
public class CartSmokeMobileTest extends BaseCartTest {

    @BeforeEach
    protected void init() {
        setDriverAndSetWait();
        driver.manage().window().setSize(new Dimension(550, 840));
    }

    @AfterEach
    protected void closeTub() {
        if (driver != null) {
            driver.quit();
        }
    }


    @Test
    @DisplayName("Добавление в корзину и заказ случайного товара после регистрации с мобильного устройства")
    protected void addInCartAfterRegistration() {
        chooseOmsk();
        //пауза, чтобы хедер успел прогрузиться
        pause();
        registrationMobile();
        clickBtnHeaderCatalog();
        clickRandomItemMobile();
        getSizeAndGoToCartMobile();
        checkCart();
        checkErrorSumMobile();
        makeOrderAfterRegistrationMobile();
        //пауза, чтобы успела прогрузиться страница checkout/orderConfirmation
        longPause();
        checkOrder();
    }

    @Test
    @DisplayName("Добавление в корзину и заказ случайного товара до регистрации с мобильного устройства")
    protected void addInCartBeforeRegistration() {
        chooseOmsk();
        //пауза, чтобы хедер успел прогрузиться
        pause();
        clickBtnHeaderCatalog();
        clickRandomItemMobile();
        getSizeAndGoToCartMobile();
        checkCart();
        checkErrorSumMobile();
        makeOrderBeforeRegistrationMobile();
        //пауза, чтобы успела прогрузиться страница checkout/orderConfirmation
        longPause();
        checkOrder();
    }
}
