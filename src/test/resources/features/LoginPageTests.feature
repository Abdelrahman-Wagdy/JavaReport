Feature: Testing the login page
  Background: The app launched on the device
    Given the app is launched

  Scenario: Validate that the user can login with valid credentials
    Given that the user is logged in successfully
    Then the user navigates to the "products" page

 Scenario Outline: Validate that the error message is displayed when entering invalid credentials
   Given that the user enters "<username>" and "<password>"
   Then the error message for invalid credentials should be displayed

   Examples:
      |  username   | password |
      |  standArd_user   | secret_sauce |
      |  standard_user   | secretsauce |

  Scenario: Validate that blocked user will have error message displayed when trying to log in
    Given that the user enters "locked_out_user" and "secret_sauce"
    Then the error message for locked out user should be displayed

  Scenario: Validate that server problem error message will be displayed when server error occurs
    Given that the user enters "problem_user" and "secret_sauce"
    Then the error message should be displayed with server problem



