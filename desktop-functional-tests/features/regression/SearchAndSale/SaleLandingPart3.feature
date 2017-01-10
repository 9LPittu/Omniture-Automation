@SaleLanding3 @HighLevel
Feature: Sale Landing functionality-Part3

  Background:
    Given User goes to homepage
    And User closes email capture
    When User clicks on sale link from top nav
    Then Verify sale landing page is displayed
  Scenario Outline: Verify Gender Selectors are functional on New In Sale page
	When User selects new in sale dept from sales
	Then Verify Sale array page is displayed
	When User clicks on <gender> gender selector
	Then Verify Sale array page is displayed
	And Verify gender filter displays <gender>
	And Verify that search result number is greater than 0

	Examples:
	|gender|
	|women|
	|men|
	|girls|
	|boys|
