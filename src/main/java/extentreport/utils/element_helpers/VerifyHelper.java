package utils.element_helpers;

import core.drivers_initializer.drivers.AppiumMobileDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.testng.asserts.SoftAssert;

public abstract class VerifyHelper {

    public static void verifyElementExists(AppiumMobileDriver appiumMobileDriver, By locator) {
        WaitHelper.waitVisibility(appiumMobileDriver, locator);
        SoftAssert softAssert = new SoftAssert();
        WaitHelper.waitVisibility(appiumMobileDriver, locator);
        try {
            softAssert.assertTrue(appiumMobileDriver.getDriver().findElement(locator).isDisplayed());
            System.out.println("Element is Displayed " + locator);
        } catch (NoSuchElementException e) {
            softAssert.fail("Failed to locate the element: " + locator.toString());
        } catch (Exception e) {
            softAssert.fail("An unexpected error occurred: " + e.getMessage());
        } finally {
            softAssert.assertAll();
        }
    }
    public static void verifyElementContainsText(AppiumMobileDriver appiumMobileDriver, By locator, String expectedText) {
        WaitHelper.waitVisibility(appiumMobileDriver, locator);
        SoftAssert softAssert = new SoftAssert();
        try {
            String actualText = appiumMobileDriver.getDriver()
                    .findElement(locator).getText().toLowerCase();
            softAssert.assertTrue(
                    actualText.toLowerCase().contains(expectedText),
                    "Text mismatch: Expected text to contain '" + expectedText + "' but found '" + actualText + "' for element: " + locator.toString()
            );
            System.out.println("Expected text to contain: '" + expectedText + "'  -  Actual: '" + actualText + "'");
        } catch (NoSuchElementException e) {
            softAssert.fail("Failed to locate the element: " + locator.toString());
        } catch (Exception e) {
            softAssert.fail("An unexpected error occurred: " + e.getMessage());
        } finally {
            softAssert.assertAll();
        }
    }
}