@Search @HighLevel
Feature: Search Regression Suite

  Background:
    Given User is on homepage
    And Handle the Email Capture pop up
    And User presses search button

    #Repeated
    #Scenario: single product with sale price should go to pdp
  Scenario: single product result goes to pdp directly
    When Enters A0640 to the search field
    And Clicks on search button for input field
    Then User is in product detail page

  Scenario: multiple products with sale price should display two items
    When Enters 05389 to the search field
    And Clicks on search button for input field
    And User is in search results page
    Then Verify amount of items displayed is 3

    #US15673_TC01
    #US15673_TC02
    #Merged
    #Scenario: Search Refine multi select, single select
    #Scenario: Search Refine multi select, multiple selections
    #Scenario: Search Refine Breadcrumbs and results
    #Scenario: Refine page should display the first sort option as relevance and selected by default
  Scenario: Search Refine Single Select
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
    And Click on Category refinement
    And Select random single option from Category refinement
    Then Verify selected value is displayed next to Category refinement
    Then Verify that Category refinement is closed
    And Click on Size refinement
    And select available random option from Size refinement
    And user should see single value name/number of values selected next to Size refinement
    And select available random option from Size refinement
    And user should see single value name/number of values selected next to Size refinement
    And Verify Size refinement drawer remains open
    And Click on Size refinement close drawer icon
    And user should see single value name/number of values selected next to Size refinement
    When Click on done button for refinement filter menu
    Then Verify number of results is less than 100
    And user should see the selected options in the breadcrumb

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
    Then Verify yellow product is displayed


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