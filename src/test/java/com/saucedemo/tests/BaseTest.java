// ABOUTME: Base test class that all test classes extend.
// ABOUTME: Handles browser setup, teardown, and login helper for each test.
package com.saucedemo.tests;

import com.saucedemo.pages.LoginPage;
import com.saucedemo.utils.ConfigReader;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;

public class BaseTest {

    protected WebDriver driver;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        String browser = ConfigReader.getProperty("browser");

        switch (browser.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--start-maximized");
                driver = new ChromeDriver(options);
                break;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
            case "edge":
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                break;
            default:
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
        }

        int implicitWait = Integer.parseInt(ConfigReader.getProperty("implicit.wait"));
        int pageLoadTimeout = Integer.parseInt(ConfigReader.getProperty("page.load.timeout"));

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(pageLoadTimeout));
        driver.manage().window().maximize();

        driver.get(ConfigReader.getProperty("base.url"));
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    /**
     * Helper method to log in with default credentials.
     * Many tests need to be logged in first, so this avoids repeating login steps.
     */
    protected void loginWithDefaultUser() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(
                ConfigReader.getProperty("standard.username"),
                ConfigReader.getProperty("standard.password")
        );
    }
}
