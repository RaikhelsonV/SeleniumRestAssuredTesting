package config;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;


import java.time.Duration;


public class SetupDriver {
    public static WebDriver driver;
    private String base_url = "https://productstoresystem-production.up.railway.app/auth";
    private String chrome_driver_path = "C:/Users/Serhii/Desktop/Valerie/chromedriver.exe";


    @BeforeClass(alwaysRun = true)
    public void setUp() {
//        System.setProperty("webdriver.chrome.driver", chrome_driver_path);
        WebDriverManager.chromedriver().setup();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--remote-allow-origins=*");
        chromeOptions.addArguments("--headless");
        chromeOptions.addArguments("--no-sandbox");
        chromeOptions.addArguments("--disable-dev-shm-usage");
        chromeOptions.addArguments("--disable-gpu");
        chromeOptions.addArguments("--window-size=1920x1080");
        chromeOptions.addArguments("--disable-setuid-sandbox");
        chromeOptions.addArguments("--remote-debugging-port=9222");

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
