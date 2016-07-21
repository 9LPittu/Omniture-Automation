@International-Part2
Feature: International Country Context - Part 2

  Background:
    Given User goes to homepage
    And User closes email capture

  Scenario Outline: Multiple Pages During Checkout Context validations
    Then Click on change link from footer
    And User is on context chooser page
    And User is on internal /r/context-chooser page
    Given User selects <country_group> at random from context chooser page
    Then User should land on country specific home page
    And Verify selected country is in footer
    And User navigates to a subcategory from main category
    And Verify context in the array page
    And Verify selected country is in footer
    And User selects random product from product array
    And Verify product detail page is displayed
    And Verify selected country is in footer
    And Verify context in the product detail page
    And User adds selected product to bag
    And User clicks in bag
    And User is in shopping bag page
    And Verify that shopping bag has expected context
    And User clicks check out button
    And Verify selected country is in footer
    And User selects guest check out
    And Verify selected country is in footer
    And Guest user fills shipping address and continue
    And Verify selected country is in footer
    And User selects random shipping method and continue
    And Verify selected country is in footer
    And User fills payment method and continue
    And Verify selected country is in footer
    And User reviews and places order
    And Verify selected country is in footer
    Then User gets an order confirmation number
    And Verify selected country is in footer

    Examples:
      | country_group |
      | PRICEBOOK     |
      | NON-PRICEBOOK |

  Scenario Outline: context validation on sale landing page from Hamburger menu
    Then Click on change link from footer
    And User is on context chooser page
    And User is on internal /r/context-chooser page
    Given User selects <country_group> at random from context chooser page
    Then User should land on country specific home page
    And Verify selected country is in footer
    And User opens menu
    And User selects sale category from menu
    And Verify selected country is in footer
    And Verify country code in the url for international countries

    Examples:
      | country_group |
      | PRICEBOOK     |
      | NON-PRICEBOOK |

  Scenario Outline: Context is displayed on search page
    Then Click on change link from footer
    And User is on context chooser page
    And User is on internal /r/context-chooser page
    Given User selects <country_group> at random from context chooser page
    Then User should land on country specific home page
    And Verify selected country is in footer
    And User searches for a random search term
    Then User is in search results page
    And Verify selected country is in footer
    And Verify country code in the url for international countries

    Examples:
      | country_group |
      | PRICEBOOK     |
      | NON-PRICEBOOK |

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