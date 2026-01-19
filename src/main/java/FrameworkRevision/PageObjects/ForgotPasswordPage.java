package FrameworkRevision.PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import FrameworkRevision.PageComponent.BasePage;

public class ForgotPasswordPage extends BasePage {
	
	WebDriver driver;

	public ForgotPasswordPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	

}
