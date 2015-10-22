@SearchByItem
Feature: Smoke Tests Search By Item Number Page

  Background:
    Given User is on homepage
    And User presses search button

  Scenario: validating search results page display by item number
    And Enters C3409 to the search field
    And Clicks on search button for input field
    Then User is in search results page
    And Verify 2 available colors for C3409 are displayed
    And Enters 21421 to the search field
    And Clicks on search button for input field
    And User is in search results page
    And Verify amount of items displayed is 2
    And Verifies Silk cami product sale price is now $39.99
    And Enters dresses to the search field
    And Clicks on search button for input field
    Then User is in search results page

  Scenario: single product result goes to pdp directly
    And Enters B8752 to the search field
    And Clicks on search button for input field
    And User is in product detail page

  Scenario: multiple products with sale price should display two items
    And Enters 03140 to the search field
    And Clicks on search button for input field
    And User is in search results page
    And Verify amount of items displayed is 2
    And Verifies Nadia long dress in silk chiffon product sale price is now $249.99

  Scenario: single product with sale price should go to pdp
    And Enters 61170 to the search field
    And Clicks on search button for input field
    And User is in product detail page
    And Verify product sale price is now $73.00

  #Scenario: single product entering exact name should go to pdp
   # And Enters Carrie cami in tropical frond to the search field
   # And Clicks on search button for input field
   # And User is in product detail page
