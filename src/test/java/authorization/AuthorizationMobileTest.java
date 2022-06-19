package authorization;

import baseTestsWithSteps.BaseAuthorizationTest;
import helpers.TestTag;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Dimension;

@Tags({
        @Tag(TestTag.SMOKE),
        @Tag(TestTag.MOBILE),
        @Tag(TestTag.AUTHORIZATION),
})
public class AuthorizationMobileTest extends BaseAuthorizationTest {

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
    @DisplayName("Авторизация через почту с мобильного устройства")
    protected void authorizationMobileTest() {
        closeRegionQuestion();
        //пауза, чтобы хедер успел прогрузиться
        pause();
        registrationMobile();
        logoutMobile();
        authorizationMobile();
        checkAuthorizationMobile();
    }
}
