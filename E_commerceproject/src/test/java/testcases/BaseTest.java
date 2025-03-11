package testcases;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import pages.*;
import utils.ExtentReportManager;

public class BaseTest {
    protected WebDriver driver;
    protected HomePage homePage;
    protected RegisterPage registerPage;
    protected LoginSearchAndCartPage loginSearchAndCartPage; // Use the combined page class

    @BeforeSuite
    public void setupExtentReports() {
        ExtentReportManager.setupReport();
    }
    @BeforeMethod
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\KESHA\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.demoblaze.com/");

        // Initialize the page objects
        homePage = new HomePage(driver);
        registerPage = new RegisterPage(driver);
        loginSearchAndCartPage = new LoginSearchAndCartPage(driver); // Now initializing the combined page class
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
    @AfterSuite
    public void generateExtentReport() {
        ExtentReportManager.generateReport();
    }
}
