@AddToBagMultipleItemsRandom
Feature: Multiple Items Random

  Scenario: Add to bag multiple items
    Given User is on homepage
    Then User clicks on hamburger menu
    And Selects WOMEN Category from hamburger menu
    And Chooses a random subcategory
    And Selects any product from product grid list
    And Selects any variation for the product if existent
    And Selects any color for the product
    And Selects any size for the product
    Then Add to cart button is pressed
    And A minicart modal should appear with message '1 item has been added to your cart.'
    And Bag should have 1 item(s) added

    Then User clicks on hamburger menu
    And User clicks on back link
    And Selects MEN Category from hamburger menu
    And Chooses a random subcategory
    And Selects any product from product grid list
    And Selects any variation for the product if existent
    And Selects any color for the product
    And Selects any size for the product
    Then Add to cart button is pressed
    And A minicart modal should appear with message '1 item has been added to your cart.'
    And Bag should have 2 item(s) added

    Then User clicks on hamburger menu
    And User clicks on back link
    And Selects GIRLS Category from hamburger menu
    And Chooses a random subcategory
    And Selects any product from product grid list
    And Selects any variation for the product if existent
    And Selects any color for the product
    And Selects any size for the product
    Then Add to cart button is pressed
    And A minicart modal should appear with message '1 item has been added to your cart.'
    And Bag should have 3 item(s) added

    Then User clicks on hamburger menu
    And User clicks on back link
    And Selects BOYS Category from hamburger menu
    And Chooses a random subcategory
    And Selects any product from product grid list
    And Selects any variation for the product if existent
    And Selects any color for the product
    And Selects any size for the product
    Then Add to cart button is pressed
    And A minicart modal should appear with message '1 item has been added to your cart.'
    And Bag should have 4 item(s) added

    Then User goes to homepage
    And User presses search button
    And Enters sweaters to the search field
    And Clicks on search button for input field
    Then Search results are displayed
    And Selects any product from product grid list
    And Selects any variation for the product if existent
    And Selects any color for the product
    And Selects any size for the product
    Then Add to cart button is pressed
    And A minicart modal should appear with message '1 item has been added to your cart.'
    And Bag should have 5 item(s) added

    Then User clicks on item bag
    And User should be in shopping bag page
