@SearchByItem
Feature: Smoke Tests Search By Item Number Page

  Background:
    Given User is on homepage
    And User presses search button

  Scenario Outline: validating search results page display by item number
    And Enters <product number> to the search field
    And Clicks on search button for input field
    Then User is in search results page
    And Verify <No.of colors> available colors for <product number> are displayed
    And Enters <sale sku product number> to the search field
    And Clicks on search button for input field
    And User is in search results page
    And Verify amount of items displayed is <number of items>
    And Verifies <product> product sale price is <sale price>
    And Enters <search term> to the search field
    And Clicks on search button for input field
    Then User is in search results page

    Examples:
    |product number|No.of colors|sale sku product number|number of items|product|sale price|search term|
    |C3409         |2    |21421                  |2                     |Silk cami|now $39.99|dresses  |


  Scenario Outline: single product result goes to pdp directly
    And Enters <one sku product number> to the search field
    And Clicks on search button for input field
    And User is in product detail page

    Examples:
    |one sku product number|
    |B8752                 |


  Scenario Outline: multiple products with sale price should display two items
    And Enters <product number with more than one sku> to the search field
    And Clicks on search button for input field
    And User is in search results page
    And Verify amount of items displayed is <no.of items>
    And Verifies <corresponding product name> product sale price is <amount>

    Examples:
   |product number with more than one sku|no.of items|corresponding product name|amount|
   |03140                                |2           |Nadia long dress in silk chiffon|now $249.99|


  Scenario Outline: single product with sale price should go to pdp
    And Enters <single product number with sale price> to the search field
    And Clicks on search button for input field
    And User is in product detail page
    And Verify product sale price is <amount>

    Examples:
    |single product number with sale price|amount|
    |61170                         |now $73.00|


  Scenario Outline: single product entering exact name should go to pdp
    And Enters <single product name> to the search field
    And Clicks on search button for input field
    And User is in product detail page

    Examples:
    |single product name|
    |Carrie cami in tropical frond|
