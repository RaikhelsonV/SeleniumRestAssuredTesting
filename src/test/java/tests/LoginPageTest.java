package tests;

import config.SetupDriver;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.LoginPage;

@Epic("Login Tests")
@Feature("Login Functionality")
public class LoginPageTest extends SetupDriver {
    LoginPage lp;

    @BeforeClass
    public void init() {
        lp = new LoginPage(driver);
    }

    @Test
    @Story("Valid Login")
    @Description("Test for valid login with correct credentials")
    public void testLogin() {
        lp.enterEmail("val@val");
        lp.enterPassword("123");
        lp.clickLoginButton();
        lp.ff();
        Assert.assertEquals(driver.getTitle(), "ProductStoreSystem");
    }

}
