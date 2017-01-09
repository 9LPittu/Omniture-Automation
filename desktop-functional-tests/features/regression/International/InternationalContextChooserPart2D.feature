@International-Part2D @HighLevel
Feature: International Country Context - Part 2D

  Background:
    Given User goes to homepage
    And User closes email capture

  Scenario Outline: context validation on sale landing page from Hamburger menu
    Then Click on change link from footer
    And User is on context chooser page
    And User is on internal /r/context-chooser page
    Given User selects <country_group> at random from context chooser page
    Then User should land on country specific home page
    And Verify selected country is in footer
	And User clicks on sale link from top nav
    And Verify selected country is in footer
    And Verify country code in the url for international countries

    Examples:
      | country_group |
      | PRICEBOOK     |
      | NON-PRICEBOOK |
