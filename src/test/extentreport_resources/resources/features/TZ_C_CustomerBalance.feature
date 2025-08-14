@test @TZ
Feature: Login functionality

  Scenario: Successful Onboarding for Vodacom Consumer
    Given The user allows permission for Vodacom Consumer
    When The user selects the language for Vodacom Consumer
    And The user is introduced to some information about the app for Vodacom Consumer
    And The user taps on the allow sms button for Vodacom Consumer
    And The user allows permission for Vodacom Consumer
    And The user accepts terms and conditions for Vodacom Consumer