package stepdefs.Consumer.CommonVodacomConsumerStepDefs;

import core.drivers_initializer.drivers.BaseAndroidDriver;
import core.drivers_initializer.drivers.BaseIOSDriver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import pages.Onboarding.C_OnboardingAbstract;
import pages.Onboarding.C_OnboardingLogicAndroid;
import pages.Onboarding.C_OnboardingLogicIOS;

import static pages.Onboarding.C_OnboardingAbstract.PLATFORM;
import static stepdefs.Hooks.appiumMobileDriver;

public class Common_C_OnboardingScreen {
    C_OnboardingAbstract onboardingAbstract;

    public Common_C_OnboardingScreen() {

        if (appiumMobileDriver instanceof BaseAndroidDriver) {
            onboardingAbstract = new C_OnboardingLogicAndroid(appiumMobileDriver);
            PLATFORM = "Android";
        } else if (appiumMobileDriver instanceof BaseIOSDriver) {
            onboardingAbstract = new C_OnboardingLogicIOS(appiumMobileDriver);
            PLATFORM = "iOS";
        }
    }

    @Given("The user allows permission for Vodacom Consumer")
    public void theUserAllowsCallsPermissionForVodacomConsumer() {
        if (PLATFORM == "Android") {
            onboardingAbstract.allowPermission();
        } else {
            System.out.println("iOS");
        }
    }

    @When("The user selects the language for Vodacom Consumer")
    public void theUserSelectsTheLanguageForVodacomConsumer() {
        if (PLATFORM == "Android") {
            onboardingAbstract.selectEnglishLanguage();
        }
        onboardingAbstract.tapOnContinueBTN();
    }

    @And("The user is introduced to some information about the app for Vodacom Consumer")
    public void theUserIsIntroducedToSomeInformationAboutTheAppForVodacomConsumer() {
        if (PLATFORM == "Android") {
            onboardingAbstract.tapOnContinueBTN();
            onboardingAbstract.tapOnContinueBTN();
            onboardingAbstract.tapOnContinueBTN();
        } else if (PLATFORM == "iOS") {
            onboardingAbstract.tapOnOOBEContinueBTN();
            onboardingAbstract.tapOnOOBEContinueBTN();
            onboardingAbstract.tapOnOOBEContinueBTN();
        }

    }

    @And("The user accepts terms and conditions for Vodacom Consumer")
    public void theUserAcceptsTermsAndConditionsForVodacomConsumer() {
        onboardingAbstract.validateTermsAndConditionsScreen();
        onboardingAbstract.acceptTermsAndCondition();
    }

    @And("The user taps on the allow sms button for Vodacom Consumer")
    public void theUserTapsOnTheAllowSmsButtonForVodacomConsumer() {
        if (PLATFORM == "Android") {
            onboardingAbstract.tapOnApproveBTN();
        }
    }
}