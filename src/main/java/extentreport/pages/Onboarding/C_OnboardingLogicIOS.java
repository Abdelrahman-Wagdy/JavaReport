package pages.Onboarding;

import core.drivers_initializer.drivers.AppiumMobileDriver;
import io.appium.java_client.MobileBy;
import utils.element_helpers.ActionsHelper;
import utils.element_helpers.VerifyHelper;
import utils.element_helpers.WaitHelper;

import static utils.readers.PropertyReader.passwordArray;
import static utils.readers.PropertyReader.readPasswordFromPropertyFile;


public class C_OnboardingLogicIOS extends C_OnboardingAbstract {

    public C_OnboardingLogicIOS(AppiumMobileDriver appiumMobileDriver) {
        super(appiumMobileDriver);
        CHOOSE_LANGUAGE_TITLE = MobileBy.AccessibilityId("Please choose your language");
        ENGLISH_LANGUAGE = MobileBy.AccessibilityId("button.tap.en");
        CONTINUE_BTN_IN_CHOOSE_LANGUAGE = MobileBy.xpath("//XCUIElementTypeButton[@name='Continue']");
        CONTINUE_BTN_IN_OOBE = MobileBy.AccessibilityId("button.tap.Continue");
        TERMS_AND_CONDITIONS_ACCEPT = MobileBy.AccessibilityId("button.tap.Accept");
        TERMS_AND_CONDITIONS_TITLE = MobileBy.AccessibilityId("Accept Terms and Conditions and Privacy Policy");
    }

    @Override
    public void allowPermission() {
        // Auto Handling For Permissions on iOS
    }

    @Override
    public void selectEnglishLanguage() {
        //
    }

    @Override
    public void tapOnContinueBTN() {
        ActionsHelper.clickWhileVisible(appiumMobileDriver, CONTINUE_BTN_IN_CHOOSE_LANGUAGE);
        System.out.println("tapping on continue button");
    }


    @Override
    public void acceptTermsAndCondition() {
        ActionsHelper.clickWhileVisible(appiumMobileDriver, TERMS_AND_CONDITIONS_ACCEPT);
        System.out.println("accepting terms and conditions");
    }


    @Override
    public void tapOnOOBEContinueBTN() {
        ActionsHelper.clickWhileVisible(appiumMobileDriver, CONTINUE_BTN_IN_OOBE);
        System.out.println("tapping on continue button");
    }

    @Override
    public void tapOnApproveBTN() {
        // not in iOS
    }

    @Override
    public void validateTermsAndConditionsScreen() {
        WaitHelper.waitSpecificTimeForElementVisibility(appiumMobileDriver, TERMS_AND_CONDITIONS_TITLE, 15);
        VerifyHelper.verifyElementContainsText(appiumMobileDriver, TERMS_AND_CONDITIONS_TITLE, "terms and conditions");
        VerifyHelper.verifyElementExists(appiumMobileDriver, TERMS_AND_CONDITIONS_ACCEPT);
    }

}
