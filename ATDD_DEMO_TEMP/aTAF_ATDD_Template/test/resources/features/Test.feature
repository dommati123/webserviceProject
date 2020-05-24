
Feature: Google search Text

  Background: Manual Execution Effort For Automation Metrics
    Given Manual Execution Effort is 00:05:05

 @Gui 
  Scenario: Open chrome browser
   
    Given Open Chrome Browser
    When Write Text on the Google searchTextBox
    And Click on the GoogleSearchButton
    Then Close chrome Browser

 
