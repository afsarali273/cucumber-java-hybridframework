Feature: Sauce Demo Application Login with Playwright
  As a user, I want to be able to login to the application using Playwright
  when I present valid credentials

  @PlaywrightSauceDemoLoginTest
  Scenario Outline: Playwright Sauce Demo App Login for iteration 1
    Given Launch Playwright Application using "<tc_id>"
    Then  verify playwright login page displayed successfully
    When  User enters userCredentials in playwright text field
    And   User clicks playwright Login button
    Then  verify playwright home page displayed successfully

    Examples:
      | tc_id                 |
      | SeleniumPracticeTest4 |

#  @PlaywrightWebCrawlerQF
#  Scenario Outline: Playwright Automation Script Executor <name>
#    Given run playwright functional test script for "<name>" from "<file>"
#    Examples:
#      | name                                                     | file       |
#      | TestCase - 024 Equity, Diversity & Inclusion at Unilever | webcrawler |
#      | TestCase - 01 Our company                                | webcrawler |

  @PlaywrightFormTest
  Scenario Outline: Playwright Form Automation Test
    Given Launch Playwright Application using "<tc_id>"
    When  User enters userCredentials in playwright text field
    And   User clicks playwright Login button
    Then  verify playwright home page displayed successfully
    And   enter playwright automation practice form details
    Then  submit the playwright entered details

    Examples:
      | tc_id                 |
      | SeleniumPracticeTest4 |