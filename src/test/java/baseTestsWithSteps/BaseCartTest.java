package baseTestsWithSteps;

import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.ExpectedConditions;

public abstract class BaseCartTest extends BaseTest {

    @Step("Выбор города Омска, т.к. там доступна курьерская доставка, можно купить товар, которого нет в Омске")
    protected void chooseOmsk() {
        if (isDisplay(By.cssSelector(".zone-choice__bottom--item"))) {
            waitAndClick(By.cssSelector(".zone-choice__bottom--item"));
            waitAndClick(By.cssSelector("span[data-region-code='55']"));
        }
    }

    protected void clickBtnAddInCart() {
        waitAndClick(By.cssSelector(".press-button.js-product-details-add-to-cart"));
    }

    protected void clickBtnGoCart() {
        waitAndClick(By.cssSelector(".pop-up-basket__buttons-basket"));
    }

    protected void clickBtnContinueAddItems() {
        waitAndClick(By.cssSelector(".uk-button.js-close"));
    }

    @Step("Выбор случайного доступного размера (если есть размеры), переход в корзину")
    protected void getSizeAndGoToCart() {
        checkClassAndClickRandomSize(By.cssSelector(".block-size__item"));
        clickBtnAddInCart();
        clickBtnGoCart();
    }

    @Step("Выбор случайного доступного размера (если есть размеры), переход в корзину с мобильного устройства")
    protected void getSizeAndGoToCartMobile() {
        checkClassAndClickRandomSizeMobile(By.cssSelector(".block-size__item"));
        clickBtnAddInCart();
        longPause();
        clickBtnGoCart();
    }

    @Step("Выбор случайного доступного размера (если есть размеры), продолжить добавлять товары")
    protected void getSizeAndContinueAddItems() {
        checkClassAndClickRandomSize(By.cssSelector(".block-size__item"));
        clickBtnAddInCart();
        clickBtnContinueAddItems();
    }

    @Step("Проверка перехода на страницы корзины /cart или /cart/confirmation и наличия товара в корзине")
    protected void checkCart() {
        Assertions.assertDoesNotThrow(() -> {
            wait.until(ExpectedConditions.urlContains("cart"));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("p.caption.caption-16")));
        });
    }

    @Step("Проверка перехода на страницу корзины /cart и отсутствия товара в корзине")
    protected void checkCartAfterOrder() {
        Assertions.assertDoesNotThrow(() -> {
            wait.until(ExpectedConditions.urlContains("cart"));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("p.caption.caption-16")));
        });
    }

    @Step("Проверка ошибки \"сумма корзины < минимальной суммы\", добавление дополнительного товара при ошибке")
    protected void checkErrorSum() {
        tryWaitVisibilityAndScrollTo(By.cssSelector(".error-sum.js-error-summ-info-block"));
        if (isDisplay(By.cssSelector(".error-sum.js-error-summ-info-block"))) {
            clickRandomItem();
            getSizeAndGoToCart();
            checkErrorSum();
        }
    }

    @Step("Закрытие баннера, переход в корзину")
    protected void closeBannerAndGoToCard() {
        try {
            String parentWindow = driver.getWindowHandle();
            driver.switchTo().frame(driver.findElement(By.cssSelector("iframe.flockapi-wrapper_iframe")));
            waitAndClick(By.cssSelector(".ExchangeBanner-close.js-close"));
            waitAndClick(By.cssSelector(".ExchangeBanner-close.js-popup-close"));
            driver.switchTo().window(parentWindow);
            pause();
            waitAndClick(By.cssSelector(".header-basket.js-header-minicart"));
        } catch (Exception ignored) {
        }
    }

    protected void chooseDeliveryConfirmation() {
        //если полигон нужной доставки будет выбран по дефолту, то полигон не будет кликабельным и тест упадет
        try {
            waitAndClick(By.cssSelector("div[data-delivery-code='558'] div.checkout-polygon__checkbox"));
        } catch (Exception ignored) {
        }
    }

    protected void choosePaymentMethodConfirmation() {
        scrollTo(By.cssSelector(".checkout-polygon.js-payment-method.cash"));
        pause();
        waitAndClick(By.cssSelector(".checkout-polygon.js-payment-method.cash"));
    }

    protected void inputEmailConfirmation() {
        waitAndClick(By.cssSelector(".wrapper-text-zone__input"));
        driver.findElement(By.cssSelector(".wrapper-text-zone__input.js-checkout-email")).sendKeys(getEmail());
    }

    protected void inputAddressConfirmation() {
        String address = "г Омск, ул Омская, д 22";
        scrollTo(By.cssSelector(".wrapper-text-zone__input.js-suggestions-addr"));
        pause();
        waitAndClick(By.cssSelector(".wrapper-text-zone__input.js-suggestions-addr"));
        driver.findElement(By.cssSelector(".wrapper-text-zone__input.js-suggestions-addr")).sendKeys(address);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".suggestions-suggestions")));
        driver.findElement(By.cssSelector(".wrapper-text-zone__input.js-suggestions-addr")).sendKeys(Keys.ENTER);
    }

    protected void inputSurnameConfirmation() {
        scrollTo(By.cssSelector(".wrapper-text-zone__input.js-dadata-surname"));
        waitAndClick(By.cssSelector(".wrapper-text-zone__input.js-dadata-surname"));
        driver.findElement(By.cssSelector(".wrapper-text-zone__input.js-dadata-surname")).sendKeys(getName());
    }

    protected void inputNameConfirmation() {
        scrollTo(By.cssSelector(".wrapper-text-zone__input.js-dadata-name"));
        waitAndClick(By.cssSelector(".wrapper-text-zone__input.js-dadata-name"));
        driver.findElement(By.cssSelector(".wrapper-text-zone__input.js-dadata-name")).sendKeys(getName());
    }

    protected void inputPhoneConfirmation() {
        String phone = "0000000000";
        scrollTo(By.cssSelector(".wrapper-text-zone__input.js-mask-input"));
        pause();
        waitAndClick(By.cssSelector(".wrapper-text-zone__input.js-mask-input"));
        driver.findElement(By.cssSelector(".wrapper-text-zone__input.js-mask-input")).sendKeys(phone);
    }

    protected void clickBtnFromCartToConfirmation() {
        //иногда кидает сразу на /cart/confirmation вместо /cart, там этой кнопки нет
        try {
            waitAndClick(By.cssSelector("a.press-button"));
        } catch (Exception ignored) {
        }
    }

    protected void clickBtnFromConfirmationToCheckout() {
        pause();
        waitAndClick(By.cssSelector(".checkout-information__button.js-checkout-submit-button"));
    }

    @Step("Создание заказа зарегестрированным пользователем")
    protected void makeOrderAfterRegistration() {
        clickBtnFromCartToConfirmation();
        chooseDeliveryConfirmation();
        inputAddressConfirmation();
        inputSurnameConfirmation();
        inputPhoneConfirmation();
        choosePaymentMethodConfirmation();
        clickBtnFromConfirmationToCheckout();
    }

    @Step("Создание заказа незарегестрированным пользователем")
    protected void makeOrderBeforeRegistration() {
        clickBtnFromCartToConfirmation();
        inputEmailConfirmation();
        chooseDeliveryConfirmation();
        inputAddressConfirmation();
        inputSurnameConfirmation();
        inputNameConfirmation();
        inputPhoneConfirmation();
        choosePaymentMethodConfirmation();
        clickBtnFromConfirmationToCheckout();
    }

    @Step("Создание заказа незарегестрированным пользователем с мобильного устройства")
    protected void makeOrderBeforeRegistrationMobile() {
        clickBtnFromCartToConfirmation();
        inputEmailConfirmation();
        chooseDeliveryConfirmation();
        //всплывающий элемент "перейти к оплате" перекрывает элемент, нужно листать еще ниже
        scrollTo(By.cssSelector(".wrapper-text-zone__input.js-mask-input"));
        longPause();
        inputAddressConfirmation();
        inputNameConfirmation();
        inputSurnameConfirmation();
        //всплывающее окно "перейти к оплате" перекрывает элемент, нужно листать еще ниже
        scrollTo(By.cssSelector(".checkout-polygon.js-payment-method.cash"));
        inputPhoneConfirmation();
        choosePaymentMethodConfirmation();
        clickBtnFromConfirmationToCheckout();
    }

    @Step("Создание заказа зарегестрированным пользователем с мобильного устройства")
    public void makeOrderAfterRegistrationMobile() {
        clickBtnFromCartToConfirmation();
        chooseDeliveryConfirmation();
        //всплывающий элемент "перейти к оплате" перекрывает элемент, нужно листать еще ниже
        scrollTo(By.cssSelector(".wrapper-text-zone__input.js-mask-input"));
        longPause();
        inputAddressConfirmation();
        inputSurnameConfirmation();
        //всплывающее окно "перейти к оплате" перекрывает элемент, нужно листать еще ниже
        scrollTo(By.cssSelector(".checkout-polygon.js-payment-method.cash"));
        inputPhoneConfirmation();
        choosePaymentMethodConfirmation();
        clickBtnFromConfirmationToCheckout();
    }

    @Step("Проверка перехода на страницу подтверждения заказа checkout/orderConfirmation")
    protected void checkOrder() {
        Assertions.assertDoesNotThrow(
                () -> wait.until(ExpectedConditions.urlContains("checkout/orderConfirmation")));
    }

    @Step("Проверка ошибки \"сумма корзины < минимальной суммы\", добавление дополнительного товара при ошибке с мобильного устройства")
    protected void checkErrorSumMobile() {
        tryWaitVisibilityAndScrollTo(By.cssSelector(".error-sum.js-error-summ-info-block"));
        if (isDisplay(By.cssSelector(".error-sum.js-error-summ-info-block"))) {
            clickLogo();
            clickBtnHeaderCatalog();
            clickRandomItemMobile();
            getSizeAndGoToCartMobile();
            checkErrorSumMobile();
        }
    }
}
