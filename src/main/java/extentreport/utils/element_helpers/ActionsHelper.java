package utils.element_helpers;

import core.drivers_initializer.drivers.AppiumMobileDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public abstract class ActionsHelper {
    public static boolean isElementDisplay(AppiumMobileDriver appiumMobileDriver,By locator) {
        boolean isDisplyed = false;
        try {
            new WebDriverWait( appiumMobileDriver.getDriver(), 30).until(ExpectedConditions.visibilityOf(appiumMobileDriver.getDriver().findElement(locator)));
            isDisplyed = true;
        } catch (Exception e) {
            System.out.println("Element not Displayed " + locator);
            e.printStackTrace();
            isDisplyed = false;
        }
        return isDisplyed;
    }

    /*Click on single element */
    public static void clickWhileVisible(AppiumMobileDriver appiumMobileDriver, By locator) {
        WaitHelper.waitVisibility(appiumMobileDriver, locator);
        appiumMobileDriver.getDriver().findElement(locator).click();
    }


    public static void enter(AppiumMobileDriver driver) {
        Actions actions = new Actions(driver.getDriver());
        actions.sendKeys(Keys.ENTER).build().perform();
    }
}
