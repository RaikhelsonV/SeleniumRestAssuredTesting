package pages;

import locators.LoginLocator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends BasePage{
    private LoginLocator locator;

    public LoginPage(WebDriver driver) {
        super(driver);
        locator = new LoginLocator();
        PageFactory.initElements(driver, locator);
    }

    public void enterEmail(String email){
        enterText(locator.emailField, email);

    }
    public void enterPassword(String password){
        enterText(locator.passwordField, password);

    }
    public void clickLoginButton(){
        click(locator.loginButton);
    }

    public  void ff(){
        click(locator.logout);
    }
}
