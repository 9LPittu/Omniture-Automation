@GuestCheckout-Part2A @HighLevel
Feature: Checkout - User is able to edit item from shopping bag during Guest checkout

  Scenario: Checkout - User is able to edit item from shopping bag during Guest checkout
    Given User goes to homepage
    And User closes email capture
    
    #Add item 1 to bag
    When User navigates to product 1 with multiple colors and multiple sizes
    When User selects random color
    And User selects random size        
    And User adds product to bag
    
    #Add item 2 to bag
    When User navigates to product 2 with multiple colors and multiple sizes
    When User selects random color
    And User selects random size        
    And User adds product to bag

    When User clicks in bag
    Then Verify shopping bag is displayed
    And Verify products added matches with products in bag
    
    When User edits last item from bag    
    Then Verify that page contains a selected color    
    Then Verify that page contains a selected size
    
    Then Verify Update Bag button is displayed

    
    When User selects random color
    And User selects random size
    When Update Bag button is pressed
    
    When User clicks in bag
    Then Verify shopping bag is displayed
    And Verify products added matches with products in bag
    Then Verify edited item is displayed first in shopping bag