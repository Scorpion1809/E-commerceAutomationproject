package testcases;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginSearchAndCartPage;
import utils.ExtentReportManager;

public class LoginSearchAndCartTest extends BaseTest {

    @Test
    public void testLoginAndCompletePurchase() {
    	ExtentReportManager.startTest("Login, Add, Checkout, Confirmation Test");
        // Navigate to the login page
        homePage.navigateToLogin();
        LoginSearchAndCartPage loginSearchAndCartPage = new LoginSearchAndCartPage(driver);

        // Perform login action
        boolean isLoginSuccessful = loginSearchAndCartPage.loginUser("Ankitakmr", "password123");
        ExtentReportManager.logInfo("Entered credentials and clicked login");
        if (isLoginSuccessful) {
            ExtentReportManager.logInfo("✅ Login successful.");
        } else {
            ExtentReportManager.logInfo("❌ Login failed.");
        }
        Assert.assertTrue(isLoginSuccessful, "❌ Login failed!");

        // Select category and product, then add to cart
        loginSearchAndCartPage.selectCategory("Laptops");
        ExtentReportManager.logInfo("Select category");
        loginSearchAndCartPage.selectProduct("Sony vaio i5");
        ExtentReportManager.logInfo("Select product");
        loginSearchAndCartPage.addToCart();
        ExtentReportManager.logInfo("Added to cart");
        loginSearchAndCartPage.handleAlert("Product added.");

        // Navigate to cart and initiate the purchase process
        loginSearchAndCartPage.navigateToCart();
        loginSearchAndCartPage.placeOrder();
        ExtentReportManager.logInfo("Place order");

        // Fill in purchase details and confirm the purchase
        loginSearchAndCartPage.fillPurchaseFormAndConfirm(
            "Ankita Kumari",    // Name
            "India",          // Country
            "New Delhi",      // City
            "Visa",           // Credit Card
            "03",             // Month
            "2025"            // Year
        );
        //handleConfirmationModal();
        ExtentReportManager.logInfo("Confirmation");
        String confirmationText = loginSearchAndCartPage.handleConfirmationModal();
        Assert.assertTrue(confirmationText.contains("Thank you for your purchase!"), "❌ Purchase confirmation failed!");

        System.out.println("✅ Purchase process completed successfully.");
    }
    
    private void handleConfirmationModal() {
        // Wait for the modal to be visible
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement modal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("confirmationModal")));

        // Extract and print the confirmation message
        WebElement messageElement = modal.findElement(By.id("confirmationMessage"));
        String confirmationMessage = messageElement.getText();
        System.out.println("Confirmation Message: " + confirmationMessage);

        // Click the 'OK' button to dismiss the modal
        WebElement okButton = modal.findElement(By.xpath("//button[text()='OK']"));
        okButton.click();

        // Verify the modal is no longer visible
        wait.until(ExpectedConditions.invisibilityOf(modal));
    }
}
