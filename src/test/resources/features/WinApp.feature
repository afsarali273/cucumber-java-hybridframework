#Author: your.email@your.domain.com
#Keywords Summary :
#Feature: List of scenarios.
#Scenario: Business rule through list of steps with arguments.
#Given: Some precondition step
#When: Some key actions
#Then: To observe outcomes or validation
#And,But: To enumerate more Given,When,Then steps
#Scenario Outline: List of steps for data-driven as an Examples and <placeholder>
#Examples: Container for s table
#Background: List of steps run before each of the scenarios
#""" (Doc Strings)
#| (Data Tables)
#@ (Tags/Labels):To group Scenarios
#<> (placeholder)
#""
## (Comments)
#Sample Feature Definition Template

@WinRegression

Feature: Validating Notepad functionality
  As a user, I want to be able to invoke notepad application
  and perform basic functionalities

  @WinRegression1
  Scenario Outline: Validating Notepad 'Help' tab
#    Given Launch Notepad Application
    When User clicks "<button1>" buttonName
    When User clicks "<button2>" buttonName
    When User clicks "<button3>" buttonName

    Examples: 
      | button1 | button2        | button3   |
      | Help	| About Notepad  | OK        |
      

  @WinRegression2
  Scenario Outline: Validating Notepad 'Edit' tab
#    Given Launch Notepad Application
    When User clicks "<Button4>" buttonName
    And  User clicks "<AccessbilityID1>" buttonAccessibleID
    And  User clicks "<Button4>" buttonName
    And  User clicks "<AccessbilityID2>" buttonAccessibleID
    Then User validates the "<text>" in the "<FieldName>" Field

    Examples: 
      | Button4|AccessbilityID1 |AccessbilityID2|text|FieldName  |
      | Edit   |26              |16             |null|Text Editor|