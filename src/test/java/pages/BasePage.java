package pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class BasePage {
    WebDriver driver;
    WebDriverWait wait;
    private Actions actions;
    private Select select;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        actions = new Actions(driver);
    }

    public void waitUntilElementIsVisible(WebElement el) {
        wait.until(ExpectedConditions.visibilityOf(el));
    }

    public void waitUntilElementIsClickable(WebElement el) {
        wait.until(ExpectedConditions.elementToBeClickable(el));
    }

    public void enterText(WebElement el, String text) {
        waitUntilElementIsVisible(el);
        highlightElement(el, "green");
        el.sendKeys(Keys.CONTROL + "a");
        el.sendKeys(Keys.DELETE);
        el.sendKeys(text);
    }

    public void click(WebElement el) {
        waitUntilElementIsClickable(el);
        highlightElement(el, "blue");
        el.click();
    }

    public void highlightElement(WebElement el, String color) {
        String originalStyle = el.getAttribute("style");
        String newStyle = "background-color: yellow; border: 1px solid " + color + ";" + originalStyle;
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Change the style
        js.executeScript("var tmpArguments = arguments;setTimeout(function () {tmpArguments[0].setAttribute('style', '" + newStyle + "');},0);",
                el);

        // Change the style back after few miliseconds
        js.executeScript("var tmpArguments = arguments;setTimeout(function () {tmpArguments[0].setAttribute('style', '"
                + originalStyle + "');},400);", el);
    }

}
