package FrameworkRevision.PageComponent;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {
	WebDriver driver;
	WebDriverWait wait;

	public BasePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public void waitForElementToAppear(WebElement element) {
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	public void waitForElementToDisappear(WebElement element) {
		wait.until(ExpectedConditions.invisibilityOf(element));
	}

	public void waitForAlertToAppear() {
		wait.until(ExpectedConditions.alertIsPresent());
	}

	public String getCSSOfElement(WebElement element, String cssProperty) {
		return element.getCssValue(cssProperty);
	}

	public boolean isLinkBroken(String href) throws MalformedURLException, IOException, URISyntaxException {
		HttpURLConnection connect = (HttpURLConnection) new URI(href).toURL().openConnection();
		connect.connect();
		connect.setRequestMethod("HEAD");
		boolean isLinkBroken = connect.getResponseCode() == 200 ? true : false;
		connect.disconnect();
		return isLinkBroken;
	}

}
