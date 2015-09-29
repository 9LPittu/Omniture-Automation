@AddToBagRandom
Feature: Add to Bag

  Scenario: Random product
    Given User is on homepage
    Then User clicks on hamburger menu
    And Chooses a random category
    And Chooses a random subcategory
    And Selects any product from product grid list
    Then Verify product name is the one it was selected
    And Verify amount of colors listed is correct
    And Verify price list is correct
    And Verify price sale is correct
    And Verify price was is correct
    And Verify variations listed are the expected ones
    And Selects any variation for the product if existent
    And Selects any color for the product
    And Selects any size for the product
    Then Add to cart button is pressed
    And A minicart modal should appear with message '1 item has been added to your cart.'
    And Bag should have 1 item(s) added
    Then User clicks on item bag
    And Verify product name is the same as the one displayed in pdp page
    And Verify color is the one selected from pdp page
    And Verify size is the one selected from pdp page
    And Verify price is the same as the one displayed in pdp page
    And Verify variation is the one selected from pdp page

#    And Verify the product added to bag is displayed in shopping bag with the selected values