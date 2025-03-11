package testcases;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Assert;
import org.testng.annotations.Test;

import utils.ExtentReportManager;

public class RegisterTest extends BaseTest {
    @Test
    public void testUserRegistration() {
    	ExtentReportManager.startTest("User Registration Test");
        homePage.navigateToRegister();
        ExtentReportManager.logInfo("Navigated to registration page");
        String alertMessage = registerPage.registerUser("Ankitakmr", "password123");
        ExtentReportManager.logInfo("Received Alert: " + alertMessage);
        // Print the alert message
        System.out.println("Received Alert: " + alertMessage);

        // Check if registration was successful or user already exists
        if (alertMessage.contains("Sign up successful")) {
            System.out.println("✅ Registration successful. Waiting 5 seconds before closing browser...");
            ExtentReportManager.logInfo("✅ Registration successful.");
        } else if (alertMessage.contains("This user already exist")) {
            System.out.println("⚠️ User already exists. Please try a different username.");
            ExtentReportManager.logInfo("⚠️ User already exists.");
        } else {
            Assert.fail("Unexpected alert message: " + alertMessage);
            ExtentReportManager.logInfo("❌ Unexpected alert message: " + alertMessage);
        }
        captureScreenshot("screenshots/sign_up.png");
        // Close browser after registration process
        driver.quit();
    }
    public void captureScreenshot(String filePath) {
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
        	FileUtils.copyFile(src, new File(filePath));
            System.out.println("📸 Screenshot saved at: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
