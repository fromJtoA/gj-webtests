package authorization;

import baseTestsWithSteps.BaseAuthorizationTest;
import helpers.TestTag;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Dimension;

@Tags({
        @Tag(TestTag.SMOKE),
        @Tag(TestTag.DESKTOP),
        @Tag(TestTag.AUTHORIZATION),
})
public class AuthorizationTest extends BaseAuthorizationTest {

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
    @DisplayName("Авторизация через почту")
    protected void authorizationTest() {
        closeRegionQuestion();
        //пауза, чтобы хедер успел прогрузиться
        pause();
        registration();
        logout();
        authorization();
        checkAuthorization();
    }
}
