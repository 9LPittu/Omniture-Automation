@SearchSale
Feature: Smoke Tests Search Sale Page

  Background:
    Given User is on homepage
    And User presses search button
   # And Enters dresses to the search field
   # And Clicks on search button for input field
   #Then User is in search results page
    
  Scenario Outline: validating directing to pdp
    And Enters <search term> to the search field
    And Clicks on search button for input field
    Then User is in search results page
   #And Selects the first product from product grid list
    And Selects any product from product grid list
    And User is in product detail page

    Examples:
    |search term|
    |skirts  |
    |shoes      |
    
  Scenario Outline: corresponding pdp should be displayed and functional
     And Enters <search term> to the search field
     And Clicks on search button for input field
     Then User is in search results page
     And User selects a product with no sale price and verifies is in corresponding valid pdp
     And A color is selected
     And A size is selected
     #And Quantity is selected
     # quantity not there on mobile site

  Examples:
    |search term|
    |skirts   |
   # |dresses    |
     
    