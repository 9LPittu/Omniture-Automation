@GuestCheckout-Part2
Feature: Checkout - User is able to edit item from shopping bag during Guest checkout

  Scenario: Checkout - User is able to edit item from shopping bag during Guest checkout
    Given User goes to homepage
    And Handle the Email Capture pop up
    
    #Add item 1 to bag
    When User navigates to product 1 with multiple colors and multiple sizes
    And A color is selected
    And A size is selected  
    And Add to cart button is pressed
    
    #Add item 2 to bag
    When User navigates to product 2 with multiple colors and multiple sizes
    And A color is selected
    And A size is selected        
    And Add to cart button is pressed

    When User clicks on item bag
    Then User should be in shopping bag page
    And Move to mobile site
    
    And Verify products added matches with products in bag
    
    When User edits last item from bag    
    Then user should see that previously selected color is retained    
    And user should see that previously selected size is retained
    
    And Verify update bag button is present
    
    When user selects a new color
    And user selects a new size
    When Update Bag button is pressed
    
    When User clicks on item bag
    Then User should be in shopping bag page
    And Verify products added matches with products in bag
    Then Verify edited item is displayed first in shopping bag    