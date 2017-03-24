@International-Part2E
Feature: International Country Context - Part 2E
 
  Scenario: Context is displayed on search page
	Given User goes to homepage
	And User closes email capture
    Then Click on change link from footer
    And User is on context chooser page
    And User is on internal /r/context-chooser page
    When User selects CA country from context chooser page
    Then User should land on country specific home page
    And Verify selected country is in footer
    And User searches for a random search term
    Then User is in search results page
    And Verify selected country is in footer
    And Verify country code in the url for international countries