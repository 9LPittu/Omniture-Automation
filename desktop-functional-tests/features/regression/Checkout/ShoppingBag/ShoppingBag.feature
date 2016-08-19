@ShoppingBag
Feature: Editing items from shopping bag

  Scenario: Multiple shopping bag functions
    Given User goes to homepage
    And User closes email capture
    When User opens menu
    And User selects random category from list
    	|Women|
    	|Men|
    	|Girls|
    	|Boys|
    And User selects random subcategory array
    And User selects random product from product array
    And Verify product detail page is displayed
    
    When User selects random color
    And User selects random size        
    And User adds product to bag

    When User goes to homepage
    And User clicks on sale link from top nav
    And User selects random sale category
    When User selects random product from array
    And Verify product detail page is displayed
    
    When User selects random color
    And User selects random size        
    And User adds product to bag

    When User searches for a random search term
    Then Verify product detail page is displayed
    
    When User selects random color
    And User selects random size        
    And User adds product to bag

    When User clicks in bag
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
    Then Verify that page contains a selected color    
    Then Verify that page contains a selected size
    
    Then Verify UPDATE BAG button is displayed     
    
    When User selects random color
    And User selects random size
    When Update Bag button is pressed
    
    When User clicks in bag
    Then Verify shopping bag is displayed
    And Verify products added matches with products in bag
    Then Verify edited item is displayed first in shopping bag