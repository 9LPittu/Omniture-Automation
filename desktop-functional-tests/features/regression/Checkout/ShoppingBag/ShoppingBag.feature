@ShoppingBag
Feature: Editing items from shopping bag

  Scenario: Multiple shopping bag functions
    Given User goes to homepage
    And User closes email capture
    When User opens menu
    And User clicks on Clothing in drawer
    And User clicks on Shirts & Tops in drawer
    And User clicks on random product with colors in category array
    Then Verify a product detail page is displayed

    When User selects a color
    And User selects size
    And User clicks ADD TO BAG button
    Then Verify that add to bag confirmation message is displayed
    And Verify that the number of items in bag is updated with plus 1

    Given User goes to homepage
    When User clicks sale from top nav
    Then Verify that search array is displayed
    And User clicks on first product in search array
    Then Verify a product detail page is displayed

    When User selects a color
    And User selects size
    And User clicks ADD TO BAG button
    Then Verify that add to bag confirmation message is displayed
    And Verify that the number of items in bag is updated with plus 1

    When User searches for dress
    Then Verify that search array is displayed
    And User clicks on first product in search array
    Then Verify a product detail page is displayed

    When User selects a color
    And User selects size
    And User clicks ADD TO BAG button
    Then Verify that add to bag confirmation message is displayed
    And Verify that the number of items in bag is updated with plus 1

    When User clicks bag in header
    Then Verify products added matches with products in bag
    And Verify all products have edit and remove buttons

    #User is able to edit quantity from shopping bag
    When User edits quantity of first item from bag
    Then Verify products added matches with products in bag
    And Verify items quantity and prices

    #User is able to remove item from shopping bag
    When User removes first item from bag
    Then Verify products added matches with products in bag

    #User is able to edit item from shopping bag
    When User edits last item from bag
    #This edit is not ready for automation, needs to be revisited
