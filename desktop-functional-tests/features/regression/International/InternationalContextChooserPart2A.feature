@International-Part2A
Feature: International Country Context - Part 2A

  Background:
    Given User goes to homepage
    And User closes email capture

  Scenario Outline: International context validation on gender landing pages and sale landing page
    Then Click on change link from footer
    And User is on context chooser page
    And User is on internal /r/context-chooser page
    Given User selects <country_group> at random from context chooser page
    Then User should land on country specific home page
    And Verify selected country is in footer
    And User clicks on <topnav> link from top nav
    And Verify selected country is in footer
    And Verify country code in the url for international countries

    Examples:
      | country_group | topnav |
      | PRICEBOOK     | Women  |
      | NON-PRICEBOOK | Women  |
      | PRICEBOOK     | Men    |
      | NON-PRICEBOOK | Men    |
      | PRICEBOOK     | Girls  |
      | NON-PRICEBOOK | Girls  |
      | PRICEBOOK     | Boys   |
      | NON-PRICEBOOK | Boys   |
      | PRICEBOOK     | sale   |
      | NON-PRICEBOOK | sale   |