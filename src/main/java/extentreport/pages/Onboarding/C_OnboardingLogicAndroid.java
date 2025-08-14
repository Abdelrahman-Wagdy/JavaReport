package pages.Onboarding;

import core.drivers_initializer.drivers.AppiumMobileDriver;
import org.openqa.selenium.By;
import utils.element_helpers.ActionsHelper;
import utils.element_helpers.VerifyHelper;
import utils.element_helpers.WaitHelper;

import static utils.readers.PropertyReader.readPasswordFromPropertyFile;


public class C_OnboardingLogicAndroid extends C_OnboardingAbstract {

    public C_OnboardingLogicAndroid(AppiumMobileDriver appiumMobileDriver) {
        super(appiumMobileDriver);
        TERMS_AND_CONDITIONS_ACCEPT = By.id("legalDocumentsAcceptButton");
        TERMS_AND_CONDITIONS_TITLE = By.id("legalDocumentsTitle");
        CONTINUE_BTN = By.id("continueButton");
        ENGLISH_LANGUAGE = By.xpath("//android.widget.TextView[contains(@resource-id,\"textLanguageItem\") and @text=\"English\"]");
        ALLOW_NOTIFICATION_BTN = By.id("buttonSecondary");
        ALLOW_PERMISSION = By.id("com.android.permissioncontroller:id/permission_allow_button");
        APPROVE_BTN = By.xpath("//android.widget.Button[contains(@resource-id,\"buttonPrimary\") and @text=\"Approve\"]");
        SCROLL_DOWN_ICON = By.xpath("//android.widget.ImageView[contains(@resource-id,\"scrollDownIcon\")]");
    }

    @Override
    public void allowPermission() {
            ActionsHelper.clickWhileVisible(appiumMobileDriver,ALLOW_PERMISSION);
            System.out.println("tapping on allow permission");
    }
    @Override
    public void selectEnglishLanguage() {
            ActionsHelper.clickWhileVisible(appiumMobileDriver, ENGLISH_LANGUAGE);
            System.out.println("selecting english language");
    }

    @Override
    public void tapOnContinueBTN() {
            ActionsHelper.clickWhileVisible(appiumMobileDriver, CONTINUE_BTN);
            System.out.println("tapping on continue button");

    }

    @Override
    public void acceptTermsAndCondition() {
            ActionsHelper.clickWhileVisible(appiumMobileDriver, TERMS_AND_CONDITIONS_ACCEPT);
            System.out.println("accepting terms and conditions");
    }

    @Override
    public void tapOnOOBEContinueBTN() {

    }

    @Override
    public void tapOnApproveBTN() {
            ActionsHelper.clickWhileVisible(appiumMobileDriver, APPROVE_BTN);
            System.out.println("tapping on approve button");
    }

    @Override
    public void validateTermsAndConditionsScreen() {
            VerifyHelper.verifyElementContainsText(appiumMobileDriver, TERMS_AND_CONDITIONS_TITLE, "terms and conditions");

            while (ActionsHelper.isElementDisplay(appiumMobileDriver, SCROLL_DOWN_ICON)){
                ActionsHelper.clickWhileVisible(appiumMobileDriver, SCROLL_DOWN_ICON);
            System.out.println("Scrolling to accept terms and conditions button");
            }
            VerifyHelper.verifyElementExists(appiumMobileDriver, TERMS_AND_CONDITIONS_ACCEPT);
    }
}
