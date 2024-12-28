package locators;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginLocator {
    @FindBy(css = "input[formcontrolname='email']")
    public WebElement emailField;
    @FindBy(css = "input[formcontrolname='password']")
    public WebElement passwordField;
    @FindBy(css = "button[type='submit']")
    public WebElement loginButton;

    @FindBy(css = "button[type='button']>.mdc-button__label")
    public WebElement registerButton;

    @FindBy(css = "a:nth-of-type(1)")
    public WebElement logout;


}
