@GuestCheckout-Part2
Feature: Checkout - User is able to remove item from shopping bag during Guest checkout

  Scenario: Checkout - Guest user is able to remove item from shopping bag
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
    
    When User removes first item from bag    
    Then Verify products added matches with products in bag
    And Verify Order Subtotal is updated when item is removed    