@SearchByItem
Feature: Search By Item Number Regression Tests

  Background:
    Given User is on homepage
    And User presses search button

  Scenario: validating search results page display by item number
    And Enters C3409 to the search field
    And Clicks on search button for input field
    Then User is in search results page
    And Verify 3 available colors for C3409 are displayed
    And Enters e3896 to the search field
    And Clicks on search button for input field
    And User is in search results page
    And Verify amount of items displayed is 2
    And Verifies Tall perforated drapey crepe dress product sale price is now $119.99
    And Enters dresses to the search field
    And Clicks on search button for input field
    Then User is in search results page

  Scenario: single product result goes to pdp directly
    And Enters e4859 to the search field
    And Clicks on search button for input field
    And User is in product detail page

  Scenario: multiple products with sale price should display two items
    And Enters e3835 to the search field
    And Clicks on search button for input field
    And User is in search results page
    And Verify amount of items displayed is 2
    And Verifies Petite perforated drapey crepe dress product sale price is now $119.99

  Scenario: single product with sale price should go to pdp
    And Enters b7701 to the search field
    And Clicks on search button for input field
    And User is in product detail page
    And Verify product sale price is now $325.00

  #Scenario: single product entering exact name should go to pdp
   # And Enters Carrie cami in tropical frond to the search field
   # And Clicks on search button for input field
   # And User is in product detail page
