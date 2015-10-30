@SearchSale
Feature: Regression Tests Search Sale Page

  Background:
    Given User is on homepage
    And User presses search button

  Scenario Outline: validating directing to pdp
    And Enters <search_term> to the search field
    And Clicks on search button for input field
    Then User is in search results page
    And Selects the first product from product grid list
    And User is in product detail page

    Examples:
      | search_term |
      | skirts      |
      | shoes       |

  
  Scenario Outline: corresponding pdp should be displayed and functional
    And Enters <search_term> to the search field
    And Clicks on search button for input field
    Then User is in search results page
    And User selects a product with no sale price
    And Verify product name is the one it was selected
    And A color is selected
    And A size is selected

    Examples:
      | search_term |
      | skirts      |

    