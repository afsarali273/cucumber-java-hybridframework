@iOSAndriodEcommAppJourneys
Feature: Qualiframe Andriod and iOS MobileAppTesting
  As a user, I want to be able to login,access into the iOS apps with iOS Sauce Labs On-Demand Cloud, 
  iOS Real device, iOS Simulator and iOS mobile browsers.

  @Android
  Scenario Outline: Verify new personal user able to sign-in to the application
    Given enter email address and password for new user using "<tc_id>"
    When enter user details
    Then user click on agree button
    
    Examples: 
      | tc_id                 |
      | FloowDriveMobileTest1 |
 
  @iOS
  Scenario Outline: Verify user allows to make the payment and order successfully
    Given Launch the mobile app for the registered user for the hotel management app
    And I select the food available in the menu
    When I able to enters the Credit card details "<CreditCardNumber>", "<CardName>", "<IssueOn>", "<ExpireOn>"
    Then I should able to make the order successful

    Examples: 
      | CreditCardNumber    | CardName   | IssueOn | ExpireOn |
      | 4382 6723 9075 8201 | Vin Diesel | 07/25   | 07/29    |
