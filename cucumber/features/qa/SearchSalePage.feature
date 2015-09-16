@SearchSale
Feature: Smoke Tests Search Sale Page

  Background:
    Given User is on homepage
    And User presses search button
    And Enters dresses to the search field
    And Clicks on search button for input field
    Then User is in search results page
    
  Scenario: validating directing to pdp
    And Selects the first product from product grid list
    And User is in product detail page
    
  Scenario: corresponding pdp should be displayed and functional  
     And Selects a product with no sale price
     And User is in corresponding valid pdp
     And A color is selected
     And A size is selected 
    # And Quantity is selected
     
    