package FrameworkRevision.PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import FrameworkRevision.PageComponent.BasePage;

public class LandingPage extends BasePage{
	
	WebDriver driver;
	
	public LandingPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(id = "userEmail")
	private WebElement email;
	
	@FindBy(id = "userPassword")
	private WebElement password;
	
	@FindBy(id ="login")
	private WebElement login;
	
	@FindBy(css = "a.forgot-password-link")
	private WebElement forgotPassword;
	
	public void setEmail(String user) {
		email.sendKeys(user);
	}
	
	public void setPassword(String password) {
		this.password.sendKeys(password);
	}
	
	public ProductCatalougePage login() {
		login.click();
		return new ProductCatalougePage(driver);
	}
	
	public ForgotPasswordPage clickForgotPassword() {
		forgotPassword.click();
		return new ForgotPasswordPage(driver);
	}
	

}
