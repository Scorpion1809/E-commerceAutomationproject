package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {
    @FindBy(id = "signin2") WebElement signUpButton;
    @FindBy(id = "login2")
	public WebElement loginButton;
    @FindBy(id = "cartur") WebElement cartButton;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void navigateToRegister() {
        clickElement(signUpButton);
    }

    public void navigateToLogin() {
        clickElement(loginButton);
    }

    public void navigateToCart() {
        clickElement(cartButton);
    }
    
    
}
