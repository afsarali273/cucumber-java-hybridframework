Feature: Enhanced Sauce Demo Application Testing with Playwright
  As a QA Engineer, I want to test the Sauce Demo application comprehensively
  using Playwright for modern web automation capabilities

  Background:
    Given Launch Playwright Application using "SeleniumPracticeTest4"

  @PlaywrightLogin @Smoke
  Scenario: Successful login with valid credentials
    Then verify playwright login page displayed successfully
    When User enters userCredentials in playwright text field
    And User clicks playwright Login button
    Then verify playwright home page displayed successfully
    And verify playwright page title contains "Swag Labs"
    And take playwright home page screenshot

  @PlaywrightLogin @Negative
  Scenario: Login with invalid credentials
    Then verify playwright login page displayed successfully
    When User enters invalid playwright credentials
    And User clicks playwright Login button
    Then verify playwright error message is displayed
    And take playwright login page screenshot

  @PlaywrightInventory @Functional
  Scenario: Product inventory operations
    When User enters userCredentials in playwright text field
    And User clicks playwright Login button
    Then verify playwright home page displayed successfully
    When User adds first product to cart using playwright
    Then verify cart count is 1 using playwright
    When User sorts products by name Z-A using playwright
    Then verify products are sorted correctly using playwright
    And take playwright inventory screenshot

  @PlaywrightCart @E2E
  Scenario: Complete shopping flow
    When User enters userCredentials in playwright text field
    And User clicks playwright Login button
    Then verify playwright home page displayed successfully
    When User adds multiple products to cart using playwright:
      | Sauce Labs Backpack    |
      | Sauce Labs Bike Light  |
      | Sauce Labs Bolt T-Shirt|
    Then verify cart count is 3 using playwright
    When User goes to cart using playwright
    Then verify cart page is displayed using playwright
    And verify all added products are in cart using playwright

  @PlaywrightLogout @Smoke
  Scenario: User logout functionality
    When User enters userCredentials in playwright text field
    And User clicks playwright Login button
    Then verify playwright home page displayed successfully
    When User logs out using playwright
    Then verify playwright login page displayed successfully

  @PlaywrightResponsive @CrossBrowser
  Scenario Outline: Cross-browser responsive testing
    When User sets viewport to "<width>x<height>" using playwright
    Then verify playwright login page displayed successfully
    When User enters userCredentials in playwright text field
    And User clicks playwright Login button
    Then verify playwright home page displayed successfully
    And verify page layout is responsive using playwright

    Examples:
      | width | height |
      | 1920  | 1080   |
      | 1366  | 768    |
      | 768   | 1024   |
      | 375   | 667    |

  @PlaywrightPerformance @NonFunctional
  Scenario: Performance and load time validation
    When User measures page load time using playwright
    Then verify page loads within 3 seconds using playwright
    When User enters userCredentials in playwright text field
    And User clicks playwright Login button
    And User measures navigation time using playwright
    Then verify navigation completes within 2 seconds using playwright

  @PlaywrightAccessibility @A11Y
  Scenario: Accessibility testing
    Then verify playwright login page accessibility
    When User enters userCredentials in playwright text field
    And User clicks playwright Login button
    Then verify playwright home page accessibility
    And verify all interactive elements are keyboard accessible using playwright

  @PlaywrightDataDriven @Regression
  Scenario Outline: Data-driven login testing
    Then verify playwright login page displayed successfully
    When User enters "<username>" and "<password>" using playwright
    And User clicks playwright Login button
    Then verify login result is "<expected_result>" using playwright

    Examples:
      | username        | password     | expected_result |
      | standard_user   | secret_sauce | success         |
      | locked_out_user | secret_sauce | error           |
      | problem_user    | secret_sauce | success         |
      | invalid_user    | wrong_pass   | error           |

  @PlaywrightVisual @Visual
  Scenario: Visual regression testing
    Then verify playwright login page displayed successfully
    And take visual baseline screenshot for login page using playwright
    When User enters userCredentials in playwright text field
    And User clicks playwright Login button
    Then verify playwright home page displayed successfully
    And take visual baseline screenshot for inventory page using playwright
    And compare visual changes using playwright