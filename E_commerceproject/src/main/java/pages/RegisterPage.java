package pages;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class RegisterPage extends BasePage {
    @FindBy(id = "sign-username") WebElement usernameField;
    @FindBy(id = "sign-password") WebElement passwordField;
    @FindBy(css = "button[onclick='register()']") WebElement registerButton;

    public RegisterPage(WebDriver driver) {
        super(driver);
    }

    public String registerUser(String username, String password) {
        enterText(usernameField, username);
        enterText(passwordField, password);
        clickElement(registerButton);

        // Wait for the alert popup and capture its text
        String alertMessage = "";
        try {
            Thread.sleep(3000); // Small wait to ensure alert appears
            Alert alert = driver.switchTo().alert();
            alertMessage = alert.getText();
            System.out.println("Alert Message: " + alertMessage);
            alert.accept(); // Close the popup
        } catch (Exception e) {
            System.out.println("No alert found or issue handling alert.");
        }

        // Wait for 5 seconds before closing the browser
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return alertMessage;
    }
}
