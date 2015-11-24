#@PDP
Feature: Multiple Items Random

  Background:
   Given User is on homepage

# TODO: Check how to make randomness work
#  Scenario: Multiple item add to bag on guest user mode validate products on shopping bag
#    Then User clicks on hamburger menu
#    And Selects Women Category from hamburger menu
#    And Chooses a random subcategory
#    And Selects any product from product grid list
#    And Selects any variation for the product if existent
#    And Selects any color for the product
#    And Selects any size for the product
#    Then Add to cart button is pressed
#    And A minicart modal should appear with message '1 item has been added to your cart.'
#    And Bag should have 1 item(s) added
#
#    Then User clicks on hamburger menu
#    And User clicks on back link
#    And Selects Men Category from hamburger menu
#    And Chooses a random subcategory
#    And Selects any product from product grid list
#    And Selects any variation for the product if existent
#    And Selects any color for the product
#    And Selects any size for the product
#    Then Add to cart button is pressed
#    And A minicart modal should appear with message '1 item has been added to your cart.'
#    And Bag should have 2 item(s) added
#
#    Then User clicks on hamburger menu
#    And User clicks on back link
#    And Selects Girls Category from hamburger menu
#    And Chooses a random subcategory
#    And Selects any product from product grid list
#    And Selects any variation for the product if existent
#    And Selects any color for the product
#    And Selects any size for the product
#    Then Add to cart button is pressed
#    And A minicart modal should appear with message '1 item has been added to your cart.'
#    And Bag should have 3 item(s) added
#
#    Then User clicks on hamburger menu
#    And User clicks on back link
#    And Selects Boys Category from hamburger menu
#    And Chooses a random subcategory
#    And Selects any product from product grid list
#    And Selects any variation for the product if existent
#    And Selects any color for the product
#    And Selects any size for the product
#    Then Add to cart button is pressed
#    And A minicart modal should appear with message '1 item has been added to your cart.'
#    And Bag should have 4 item(s) added
#
#    Then User goes to homepage
#    And User presses search button
#    And Enters sweaters to the search field
#    And Clicks on search button for input field
#    Then Search results are displayed
#    And Selects any product from product grid list
#    And Selects any variation for the product if existent
#    And Selects any color for the product
#    And Selects any size for the product
#    Then Add to cart button is pressed
#    And A minicart modal should appear with message '1 item has been added to your cart.'
#    And Bag should have 5 item(s) added
#
#    Then User clicks on item bag
#    And User should be in shopping bag page
#    And Move to mobile site
#    Then Verify all selected products are displayed

  Scenario: Verify preorder button is displayed
    Given User presses search button
    And Enters 49923 to the search field
    And Hits enter in search field
    And Selects the first product from product grid list
    Then Verify product is a pre-order one
    And Preorder button is displayed
    And A color is selected
    And A size is selected
    # step for estimated ship date message needs to be created here.
    And Preorder button is pressed
    Then A minicart modal should appear with message '1 item has been added to your cart.'