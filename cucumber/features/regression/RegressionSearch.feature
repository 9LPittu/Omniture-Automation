@Search
Feature: Search Regression Suite

  Background:
    Given User is on homepage
    And User presses search button

  Scenario: single product result goes to pdp directly
    When Enters A0640 to the search field
    And Clicks on search button for input field
    Then User is in product detail page

  Scenario: multiple products with sale price should display two items
    When Enters 05389 to the search field
    And Clicks on search button for input field
    And User is in search results page
    Then Verify amount of items displayed is 3

  Scenario: single product with sale price should go to pdp
    When Enters A0640 to the search field
    And Clicks on search button for input field
    Then User is in product detail page

    #US15673_TC01
  Scenario: Search Refine Single Select
    When Enters dresses to the search field
    And Clicks on search button for input field
    Then Gender selectors are displayed
    When Clicks on gender selector
    Then User is in gender refine array page
    When Refine button is clicked
    And Click on Category refinement
    And Select random single option from Category refinement
    Then Verify selected value is displayed next to Category refinement
    Then Verify that Category refinement is closed

  Scenario: Search Refine multi select, single select
    When Enters dresses to the search field
    And Clicks on search button for input field
    Then Gender selectors are displayed
    And Clicks on gender selector
    And User is in gender refine array page
    When Refine button is clicked
    And Click on Size refinement
    When Select xx-small multiple option from Size refinement
    And Verify xx-small value is displayed next to Size refinement
    When Select x-small multiple option from Size refinement
    Then Verify 2 selected value is displayed next to Size refinement
    And Verify Size refinement drawer remains open

  Scenario: Search Refine multi select, multiple selections
    When Enters dresses to the search field
    And Clicks on search button for input field
    Then Gender selectors are displayed
    When Clicks on gender selector
    Then User is in gender refine array page
    When Refine button is clicked
    And Click on Size refinement
    When Select xx-small multiple option from Size refinement
    And Verify xx-small value is displayed next to Size refinement
    When Select x-small multiple option from Size refinement
    And Click on Size refinement close drawer icon
    Then Verify 2 selected value is displayed next to Size refinement

  Scenario: Search Refine Breadcrumbs and results
    When Enters dresses to the search field
    And Clicks on search button for input field
    Then Gender selectors are displayed
    When Clicks on gender selector
    Then User is in gender refine array page
    When Refine button is clicked
    And Click on Size refinement
    When Select xx-small multiple option from Size refinement
    And Verify xx-small value is displayed next to Size refinement
    When Select x-small multiple option from Size refinement
    And Click on Size refinement close drawer icon
    Then Verify 2 selected value is displayed next to Size refinement
    When Click on done button for refinement filter menu
    Then Verify number of results is less than 100
    And Verify xx-small option breadcrumb is created
    And Verify x-small option breadcrumb is created

  Scenario: Search box functionality
    When Enters dresses to the search field
    And Clicks on search button for input field
    Then User is in search results page
    And Gender selectors are displayed
    When User scrolls down the page
    And User scrolls up the page
    Then Search drawer is open
    When User clicks on search close icon
    And User presses search button
    Then Search drawer is open
    And User is in search results page
    And Dresses is populated
    When Enters yellow dresses to the search field
    And Hits enter in search field
    Then Verifies Kylie long dress in silk chiffon product is displayed

  Scenario: Search term should display search array & validate with regular priced product
    When Enters skirts to the search field
    And Clicks on search button for input field
    Then User is in search results page
    When User selects a product with no sale price
    Then Verify product name is the one it was selected

  Scenario Outline: Search term should display search array & validate with sale product
    When Enters <search_term> to the search field
    And Clicks on search button for input field
    Then User is in search results page
    When Selects the first product from product grid list
    Then User is in product detail page

    Examples:
      | search_term |
      | skirts      |
      | shoes       |

    #US15673_TC02
  Scenario: Refine page should display the first sort option as relevance and selected by default
    When Enters dresses to the search field
    And Clicks on search button for input field
    Then Gender selectors are displayed
    When Clicks on gender selector
    Then User is in gender refine array page
    When Refine button is clicked
    And Category,Size,Color,Price filter refinements should appear
    Then first sort option is RELEVANCE
    Then First option RELEVANCE is selected by default
    Then PRICE: LOW TO HIGH is displayed as sort option
    Then PRICE: HIGH TO LOW is displayed as sort option