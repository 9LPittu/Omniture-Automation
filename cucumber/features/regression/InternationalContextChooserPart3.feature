@Context_Part3 @HighLevel
Feature: International Country Context - Part 3

  Background:
    Given User is on homepage with clean session
    And Handle the Email Capture pop up

  Scenario Outline: context validation on sale landing page from Hamburger menu
    Then click on change link from footer
    And User is on context chooser page
    And User is on internal /r/context-chooser page
    And Handle the Email Capture pop up
    Given user selects <country_group> at random from context chooser page
    Then user should land on country specific home page
    And user should see selected country in the footer
    And User clicks on hamburger menu
    And Selects sale Category from hamburger menu
    And user should see selected country in the footer
    And user should see country code in the url for international countries
    And User clicks on <sale_category> subcategory from Sales
    And user should see selected country in the footer
    And Selects any product from product grid list
    And User is in product detail page
    Then Verify embedded footer is visible and functional
    And user should see country code in the url for international countries

    Examples:
      |country_group|sale_category|
      |PRICEBOOK    |Women        |
      |NONPRICEBOOK |Women        |
      |PRICEBOOK    |Men          |
      |NONPRICEBOOK |Men          |
      |PRICEBOOK    |Girls        |
      |NONPRICEBOOK |Girls        |
      |PRICEBOOK    |Boys         |
      |NONPRICEBOOK |Boys         |