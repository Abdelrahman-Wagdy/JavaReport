package stepdefs;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import loginpage.LoginServices;
import loginpage.ProductsPageServices;
import org.testng.Assert;

public class LoginPageSteps {

    LoginServices loginServices = new LoginServices();

    @Given("the app is launched")
    public void theAppIsLaunched() {
        Assert.assertTrue(loginServices.loginPageDisplayed());
    }

    @Given("that the user is logged in successfully")
    public void thatTheUserIsLoggedInSuccessfully() {
        thatTheUserEntersUsernameAndPassword("standard_user", "secret_sauce");
    }

    @Given("that the user enters {string} and {string}")
    public void thatTheUserEntersUsernameAndPassword(String username, String password) {
        loginServices.enterLoginDetails(username, password);
    }

    @Then("the error message for invalid credentials should be displayed")
    public void theErrorMessageForInvalidCredentialsShouldBeDisplayed() {
        Assert.assertEquals(loginServices.errorTextMessage(),
                "Username and password do not match any user in this service.",
                "User logged in with invalid credentials");
    }

    @Then("the error message for locked out user should be displayed")
    public void theErrorMessageForLockedOutUserShouldBeDisplayed() {
        Assert.assertEquals(loginServices.errorTextMessage(),
                "Sorry, this user has been locked out.",
                "Error message isn't displayed for locked out user");
    }

    @Then("the error message should be displayed with server problem")
    public void theErrorMessageShouldBeDisplayedWithServerProblem() {
        Assert.assertEquals(loginServices.errorTextMessage(),
                "Server error",
                "Server error message isn't displayed");
    }

    @Then("the user navigates to the {string} page")
    public void theUserNavigatesToThePage(String page) {
        String pageTitle = "";
        String expectedPageTitle = switch (page) {
            case "products" -> {
                pageTitle = new ProductsPageServices().getPageTitle();
                yield "PRODUCTS";
            }
            case "cart" -> {
                pageTitle = new ProductsPageServices().getPageTitle();
                yield "Cart";
            }
            case "checkout: information" -> {
                pageTitle = new ProductsPageServices().getPageTitle();
                yield "CHECKOUT: INFORMATION";
            }
            case "cart checkout" -> {
                pageTitle = new ProductsPageServices().getPageTitle();
                yield "Cart - Checkout";
            }
            default -> throw new IllegalArgumentException("Invalid page input is given");
        };

        Assert.assertEquals(pageTitle, expectedPageTitle);
    }
}
