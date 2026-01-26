package cucumber.stepDefinations;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoginTestStepDefinations {

	@Given("Start message is displayed as {string}")
	public void start_message_is_displayed_as(String message) {
		System.out.println("Precondition message " + message.toUpperCase());
	}

	@Given("Application is launched with {string} browser")
	public void application_is_launched_with_browser(String browser) {
		System.out.println(browser);
	}

	@When("Enter credentials {string} and {string}")
	public void enter_credentials_username_and_password(String username, String password) {
		System.out.println("username : " + username + ". and password : " + password);
	}

	@When("Click on login button")
	public void click_on_login_button() {
		System.out.println("Clicked on Login");
	}

	@Then("Incorrect login message is displayed with message {string}")
	public void incorrect_login_message_is_displayed_with_message(String message) {
		System.out.println(message);
	}

}