Feature: Sauce Demo Application Login
  As a user, I want to be able to login to the application
  when I present valid credentials

  @SauceDemoLoginTest
  Scenario Outline: Sauce Demo App Login for iteration 1
    Given Launch Application using "<tc_id>"
    Then  verify login page displayed successfully
    When  User enters userCredentials in text field
    And   User clicks Login button
    Then  verify home page displayed successfully

    Examples:
      | tc_id                 |
      | SeleniumPracticeTest4 |

  @WebCrawlerQF
  Scenario Outline: Automation Script Executor <name>
    Given run functional test script for "<name>" from "<file>"
    Examples: 
      | name                                                     | file       |
      | TestCase - 024 Equity, Diversity & Inclusion at Unilever | webcrawler |
      | TestCase - 01 Our company                                | webcrawler |
