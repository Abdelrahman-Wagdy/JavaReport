package utils.element_helpers;

import core.drivers_initializer.drivers.AppiumMobileDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public abstract class WaitHelper {

    public static boolean waitVisibility(AppiumMobileDriver appiumMobileDriver, By locator) {
        boolean isElementPresent = false;
            WebDriver driver = appiumMobileDriver.getDriver();
            WebDriverWait wait = new WebDriverWait(driver, 60); // Updated to use Duration

            // Wait for the element to be visible
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

            if (element != null) {
                isElementPresent = true;
            }

        return isElementPresent;
    }
    public static void waitSpecificTimeForElementVisibility(AppiumMobileDriver appiumMobileDriver, By locator, int seconds) {
        try {
            System.out.println("Waiting for element visibility: " + locator.toString());
            WebDriverWait wait = new WebDriverWait(appiumMobileDriver.getDriver(), seconds);
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            System.out.println("Element is visible: " + element.toString());
        } catch (TimeoutException e) {
            System.out.println("Timeout error: Element not found within " + seconds + " seconds: " + locator.toString());
        } catch (Exception e) {
            System.out.println("Unexpected error occurred: " + e.getMessage());
        }
    }

}
