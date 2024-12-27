package pages;

import io.qameta.allure.Step;
import locators.LoginLocator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Listeners;

@Listeners(io.qameta.allure.testng.AllureTestNg.class)
public class LoginPage extends BasePage {
    private LoginLocator locator;

    public LoginPage(WebDriver driver) {
        super(driver);
        locator = new LoginLocator();
        PageFactory.initElements(driver, locator);
    }


    @Step("Entering email: {email}")
    public void enterEmail(String email) {
        enterText(locator.emailField, email);
    }

    @Step("Entering password for the email: {email}")
    public void enterPassword(String password) {
        enterText(locator.passwordField, password);
    }

    @Step("Clicking on the Login button")
    public void clickLoginButton() {
        click(locator.loginButton);
    }

    public void ff() {
        click(locator.logout);
    }
}
