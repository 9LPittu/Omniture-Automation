@Search
Feature: Search Regression Suite

  Background:
    Given User is on homepage
    And User presses search button

#    TODO: Data driven
#    Scenario:sale price and full price item search
#    And Enters C9122 to the search field
#    And Clicks on search button for input field
#    Then User is in search results page
#    And Verify 9 available colors for C9122 are displayed
#    And Enters e3896 to the search field
#    And Clicks on search button for input field
#    And User is in search results page
#    And Verify amount of items displayed is 2
#    And Verifies Tall perforated drapey crepe dress product sale price is now $119.99
#    And Enters dresses to the search field
#    And Clicks on search button for input field
#    Then User is in search results page

#  Scenario: single product result goes to pdp directly
#    And Enters A9932 to the search field
#    And Clicks on search button for input field
#    And User is in product detail page
#
#  Scenario: multiple products with sale price should display two items
#    And Enters e3835 to the search field
#    And Clicks on search button for input field
#    And User is in search results page
#    And Verify amount of items displayed is 2
#    And Verifies Petite perforated drapey crepe dress product sale price is now $119.99
#
#  Scenario: single product with sale price should go to pdp
#    And Enters b7701 to the search field
#    And Clicks on search button for input field
#    And User is in product detail page
#    And Verify product sale price is now $325.00

#  Scenario: single product entering exact name should go to pdp
#    And Enters Carrie cami in tropical frond to the search field
#    And Clicks on search button for input field
#    And User is in product detail page

  Scenario: Search Refine Single Select
    And Enters dresses to the search field
    And Clicks on search button for input field
    Then Gender selectors are displayed
    Then Clicks on gender selector
    Then User is in gender refine array page
    Then Refine button is clicked
    Given Click on Category refinement
    Then Validate View All option is selected under Category refinement
    Then Select collection single option from Category refinement
    And Verify collection value is displayed next to Category refinement
    Then Click on Category refinement
    And Validate View All option is NOT selected under Category refinement

  Scenario: Search Refine multi select, single select
    And Enters dresses to the search field
    And Clicks on search button for input field
    Then Gender selectors are displayed
    Then Clicks on gender selector
    Then User is in gender refine array page
    Then Refine button is clicked
    Given Click on Size refinement
    Then Validate View All option is selected under Size refinement
    Then Select xx-small multiple option from Size refinement
    And Validate View All option is NOT selected under Size refinement
    And Verify xx-small value is displayed next to Size refinement
    Then Select x-small multiple option from Size refinement
    And Verify 2 selected value is displayed next to Size refinement
    And Verify Size refinement drawer remains open

  Scenario: Search Refine multi select, multiple selections
    And Enters dresses to the search field
    And Clicks on search button for input field
    Then Gender selectors are displayed
    Then Clicks on gender selector
    Then User is in gender refine array page
    Then Refine button is clicked
    Given Click on Size refinement
    Then Validate View All option is selected under Size refinement
    Then Select xx-small multiple option from Size refinement
    And Validate View All option is NOT selected under Size refinement
    And Verify xx-small value is displayed next to Size refinement
    Then Select x-small multiple option from Size refinement
    Then Click on Size refinement close drawer icon
    And Verify 2 selected value is displayed next to Size refinement

  Scenario: Search Refine Breadcrumbs and results
    And Enters dresses to the search field
    And Clicks on search button for input field
    Then Gender selectors are displayed
    Then Clicks on gender selector
    Then User is in gender refine array page
    Then Refine button is clicked
    Given Click on Size refinement
    Then Validate View All option is selected under Size refinement
    Then Select xx-small multiple option from Size refinement
    And Validate View All option is NOT selected under Size refinement
    And Verify xx-small value is displayed next to Size refinement
    Then Select x-small multiple option from Size refinement
    Then Click on Size refinement close drawer icon
    And Verify 2 selected value is displayed next to Size refinement
    Then Click on done button for refinement filter menu
    Then Verify number of results is less than 100
    And Verify xx-small option breadcrumb is created
    And Verify x-small option breadcrumb is created

  Scenario Outline: search box functionality
    And Enters <search term> to the search field
    And Clicks on search button for input field
    Then User is in search results page
    And Gender selectors are displayed
    And User scrolls down the page
    And User scrolls up the page
    And Search drawer is open
    And User clicks on search close icon
    When User presses search button
    And Search drawer is open
    And User is in search results page
    And Dresses is populated
    When Enters <edited search term> to the search field
    And Hits enter in search field
    Then Verifies <corresponding> product is displayed

    Examples:
      |search term|edited search term|corresponding|
      |dresses    |yellow dresses    | Nadia dress in silk chiffon|

  Scenario Outline: search term should display search array & validate with sale product
    And Enters <search_term> to the search field
    And Clicks on search button for input field
    Then User is in search results page
    And Selects the first product from product grid list
    And User is in product detail page

    Examples:
      | search_term |
      | skirts      |
      | shoes       |

  Scenario Outline: search term should display search array & validate with regular priced product
    And Enters <search_term> to the search field
    And Clicks on search button for input field
    Then User is in search results page
    And User selects a product with no sale price
    And Verify product name is the one it was selected

    Examples:
      | search_term |
      | skirts      |


