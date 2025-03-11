package pages;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.logging.FileHandler;

public class LoginSearchAndCartPage extends BasePage {

    // Login Elements
    @FindBy(id = "loginusername") WebElement usernameField;
    @FindBy(id = "loginpassword") WebElement passwordField;
    @FindBy(css = "button[onclick='logIn()']") WebElement loginButton;
    @FindBy(id = "nameofuser") WebElement welcomeMessage;

    // Search Elements
    @FindBy(css = ".list-group-item") List<WebElement> categories;
    @FindBy(css = ".card-title a") List<WebElement> products;

    @FindBy(id = "name") WebElement nameField;
    @FindBy(id = "country") WebElement countryField;
    @FindBy(id = "city") WebElement cityField;
    @FindBy(id = "card") WebElement creditCardField;
    @FindBy(id = "month") WebElement monthField;
    @FindBy(id = "year") WebElement yearField;
    @FindBy(css = "button[onclick='purchaseOrder()']") WebElement purchaseButton;
    
    //Confirmation
    @FindBy(id = "orderModal") WebElement confirmationModal;
    @FindBy(xpath = "//h2[contains(text(),'Thank you for your purchase!')]") WebElement confirmationMessage;
    @FindBy(css = ".lead.text-muted") WebElement orderDetails;
    @FindBy(xpath = "//button[text()='OK']") WebElement okButton;
    
    // Add to Cart Elements
    @FindBy(css = "h2.name") WebElement productTitle;
    @FindBy(css = "a.btn-success") WebElement addToCartButton;
    @FindBy(css = ".btn-success")
	public WebElement placeOrderButton;
    @FindBy(id = "cartur") WebElement cartLink;
    @FindBy(css = "button[onclick='purchaseOrder()']") WebElement confirmPurchaseButton;
    public LoginSearchAndCartPage(WebDriver driver) {
        super(driver);
    }

    // Navigate to Cart
    public void navigateToCart() {
        clickElement(cartLink);
        System.out.println("Navigated to cart.");
    }

    // Place Order
    public void placeOrder() {
        clickElement(placeOrderButton);
        System.out.println("Clicked on Place Order.");
    }

    // Confirm Purchase
    public void confirmPurchase() {
        clickElement(confirmPurchaseButton);
        System.out.println("Confirmed the purchase.");
    }
    // Login Action
    public boolean loginUser(String username, String password) {
        System.out.println("Attempting to log in with username: " + username);

        // Wait for the username field to be present and visible
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Timeout adjusted to 10 seconds

        try {
            // Wait until the username field is visible and clickable
            wait.until(ExpectedConditions.visibilityOf(usernameField));
            wait.until(ExpectedConditions.elementToBeClickable(usernameField));
            // Scroll into view in case it's not fully visible
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", usernameField);
            enterText(usernameField, username); // Enter the username
        } catch (Exception e) {
            System.out.println("❌ Username field not visible or clickable: " + e.getMessage());
            return false;
        }

        // Clear existing text and input password
        enterText(passwordField, password);

        // Wait for the login button to be clickable
        try {
            wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        } catch (Exception e) {
            System.out.println("❌ Login button not clickable: " + e.getMessage());
            return false;
        }

        // Click the login button
        System.out.println("Clicking login button...");
        clickElement(loginButton);

        // Wait for the login process to complete (look for the "welcomeMessage" element)
        try {
            wait.until(ExpectedConditions.visibilityOf(welcomeMessage)); // Wait for the success message
            System.out.println("✅ Login successful.");
            captureScreenshot("screenshots/login_page.png");
            return true;
        } catch (Exception e) {
            System.out.println("❌ Login failed: " + e.getMessage());
            return false;
        }
    }

    // Search Product
    public void selectCategory(String category) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        for (WebElement cat : categories) {
            if (cat.getText().equalsIgnoreCase(category)) {
                wait.until(ExpectedConditions.elementToBeClickable(cat));
                clickElement(cat); // Use clickElement() to ensure clickability
                break;
            }
        }
        //captureScreenshot("screenshots/product_category.png");
    }

    public void selectProduct(String productName) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        for (WebElement product : products) {
            if (product.getText().equalsIgnoreCase(productName)) {
                wait.until(ExpectedConditions.elementToBeClickable(product));
                clickElement(product); // Use clickElement() to ensure clickability
                break;
            }
        }
        //captureScreenshot("screenshots/selected_product.png");
    }

    // Add to Cart
    public void addToCart() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(addToCartButton));
        clickElement(addToCartButton);

        // Ensure "Place Order" button is visible after adding product to cart
        if (isElementVisible(placeOrderButton)) {
            System.out.println("✅ Product added to cart.");
        } else {
            System.out.println("❌ Product not added to cart.");
        }
        captureScreenshot("screenshots/product_in_cart.png");
    }
    
    public void handleAlert(String expectedText) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.alertIsPresent());
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            if (alertText.equals(expectedText)) {
                alert.accept();
                System.out.println("✅ Alert handled: " + alertText);
            } else {
                System.out.println("❌ Unexpected alert text: " + alertText);
            }
        } catch (NoAlertPresentException e) {
            System.out.println("❌ No alert found.");
        }
    }
    
    public void fillPurchaseFormAndConfirm(String name, String country, String city, String creditCard, String month, String year) {
        enterText(nameField, name);
        enterText(countryField, country);
        enterText(cityField, city);
        enterText(creditCardField, creditCard);
        enterText(monthField, month);
        enterText(yearField, year);
        clickElement(purchaseButton);
        System.out.println("Purchase form submitted.");
        captureScreenshot("screenshots/purchase_filldetails.png");
    }
    
    public String handleConfirmationModal() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(confirmationModal));

        String message = confirmationMessage.getText();
        String details = orderDetails.getText();
        System.out.println("🔹 Confirmation Message: " + message);
        System.out.println("🔹 Order Details: " + details);

        captureScreenshot("screenshots/purchase_confirmation.png");

        clickElement(okButton);
        wait.until(ExpectedConditions.invisibilityOf(confirmationModal));

        return message;
    }

    // Screenshot Capture
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
