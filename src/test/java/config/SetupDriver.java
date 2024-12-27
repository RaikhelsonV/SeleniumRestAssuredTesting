package config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;


import java.time.Duration;


public class SetupDriver {
    public static WebDriver driver;
    private String chrome_driver_path = "C:/Users/Serhii/Desktop/Valerie/chromedriver.exe";
    private String base_url = "https://productstoresystem-production.up.railway.app/auth";


    @BeforeClass(alwaysRun = true)
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", chrome_driver_path);
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(chromeOptions);

        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(10));

        driver.get(base_url);
        System.out.println("Page title is: " + driver.getTitle());


    }

    @AfterClass
    public void quitDriver() {
        driver.close();
        driver.quit();
    }


}
