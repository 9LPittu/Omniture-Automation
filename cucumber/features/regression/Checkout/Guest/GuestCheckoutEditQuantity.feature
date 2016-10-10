@GuestCheckout-Part3
Feature: Checkout - User is able to edit quantity for item from shopping bag during Guest checkout

  Scenario: Checkout - User is able to edit quantity for item from shopping bag during Guest checkout
    Given User goes to homepage
    And Handle the Email Capture pop up
    
    #Add Item 1 to bag
    And User clicks on hamburger menu    
    And user selects any category from hamburger menu
	And user selects any subcategory
    
    And user selects any item from array page, select any color and size
    Then User is in product detail page
    
    And Add to cart button is pressed
    And Bag should have 1 item(s) added
    
    #Add Item 2 to bag
    And User clicks on hamburger menu    
    And user selects any category from hamburger menu
	And user selects any subcategory
    
    And user selects any item from array page, select any color and size
    Then User is in product detail page
    
    And Add to cart button is pressed
    And Bag should have 2 item(s) added

    When User clicks on item bag
    Then User should be in shopping bag page    
    And Verify products added matches with products in bag
    
    When User edits quantity of first item from bag    
    Then Verify items quantity and prices    
    And Verify Order Subtotal is updated when item quantity is changed    
    
    When User edits first added item from bag      
    Then User is in product detail page
    
    And Verify update bag button is present    
    When Update Bag button is pressed
    
    When User clicks on item bag
    Then User should be in shopping bag page
    And Verify products added matches with products in bag