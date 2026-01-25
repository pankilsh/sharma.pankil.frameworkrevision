Feature: Verify Login Funtionality

  Background:
    Given Start message is displayed as "This is precondition"

  @Regression
  Scenario Outline: Login to App with invalid credentials on chrome
    Given Application is launched with "chrome" browser
    When Enter credentials "<username>" and "<password>"
    And Click on login button
    Then Incorrect login message is displayed with message "Incorrect username or password."

    Examples:
      | username | password |
      | pankil   | password |
      | poonam   | pasword  |
      | anvi     | passw    |

  @Smoke
  Scenario Outline: Login to App with invalid credentials on edge
    Given Application is launched with "edge" browser
    When Enter credentials "<username>" and "<password>"
    And Click on login button
    Then Incorrect login message is displayed with message "Incorrect username"

    Examples:
      | username | password |
      | pankil   | password |
      | poonam   | pasword  |
      | anvi     | passw    |

      
Example:
