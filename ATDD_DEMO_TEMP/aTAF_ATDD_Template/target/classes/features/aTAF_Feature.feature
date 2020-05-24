
Feature: 

	Background: Manual Execution Effort For Automation Metrics
				Given Manual Execution Effort is 00:10:05
	
@Smoke 
	Scenario Outline: eating


  Given there are <start> cucumbers
  When I eat <eat> cucumbers
  Then I should have <left> cucumbers

  Examples:
    | start | eat | left |
    |    12 |   5 |    7 |
    |    20 |   5 |   15 |
		