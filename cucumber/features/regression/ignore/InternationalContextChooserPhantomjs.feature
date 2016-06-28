Feature: International Country Context

  Background:
    Given User is on homepage with clean session
    And Handle the Email Capture pop up

  Scenario Outline: international context validation on gender landing pages
    Then click on change link from footer
    And User is on context chooser page
    And User is on internal /r/context-chooser page
    Given user selects <country_group> at random from context chooser page
    Then user should land on country specific home page
    And user should see selected country in the footer
    And User clicks on <topnav> link from top nav
    And user should see selected country in the footer
    And user should see country code in the url for international countries

    Examples:
      | country_group | topnav |
      | PRICEBOOK     | Women  |
      | NONPRICEBOOK  | Women  |
      | PRICEBOOK     | Men    |
      | NONPRICEBOOK  | Men    |
      | PRICEBOOK     | Girls  |
      | NONPRICEBOOK  | Girls  |
      | PRICEBOOK     | Boys   |
      | NONPRICEBOOK  | Boys   |
      | PRICEBOOK     | sale   |
      | NONPRICEBOOK  | sale   |