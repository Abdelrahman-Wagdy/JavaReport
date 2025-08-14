package pages.Onboarding;

import core.drivers_initializer.drivers.AppiumMobileDriver;
import org.openqa.selenium.By;

public abstract class C_OnboardingAbstract {


    public C_OnboardingAbstract(AppiumMobileDriver appiumMobileDriver) {
        super();
        this.appiumMobileDriver = appiumMobileDriver;

    }

    public abstract void allowPermission();
    public abstract void acceptTermsAndCondition();
    public abstract void tapOnContinueBTN();
    public abstract void selectEnglishLanguage();
    public abstract void tapOnOOBEContinueBTN();
    public abstract void tapOnApproveBTN();
    public abstract void validateTermsAndConditionsScreen();

    //Variables
    public AppiumMobileDriver  appiumMobileDriver;
    public static By TERMS_AND_CONDITIONS_ACCEPT;
    public static By CONTINUE_BTN;
    public static By ENGLISH_LANGUAGE;
    public static By ALLOW_NOTIFICATION_BTN;
    public static By APPROVE_BTN;
    public static By SCROLL_DOWN_ICON;
    public static By ALLOW_PERMISSION;
    public static By TERMS_AND_CONDITIONS_TITLE;
    public static By CHOOSE_LANGUAGE_TITLE;
    public static By CONTINUE_BTN_IN_CHOOSE_LANGUAGE;
    public static By CONTINUE_BTN_IN_OOBE;
    public static String PLATFORM;

}
