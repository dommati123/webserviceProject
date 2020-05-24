Feature: TestingFeature

	Background: Manual Execution Effort For Automation Metrics
		Given Manual Execution Effort is 10:10:06
		
	@smoke @fast
	Scenario Outline: eating
  Given there are <start> cucumbers
  When I eat <eat> cucumbers
  Then I should have <left> cucumbers

  Examples:
    | start | eat | left |
    |    12 |   5 |    7 |
  
		