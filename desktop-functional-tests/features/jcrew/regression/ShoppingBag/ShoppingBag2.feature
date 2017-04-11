@shoppingbag2 @HighLevel
Feature: Stackable and Non-stackable promos functionality for free shipping product and regular product

  Background:
  	Given User goes to homepage
    And User closes email capture    
  
  #id 213 
  Scenario: Stackable and Non-stackable promos functionality for free shipping product and regular product
    When User searches for free shipping product
    Then Verify product detail page is displayed
    
    When User selects random color
    And User selects random size        
    And User adds product to bag
    
    When User navigates to regular product   
    And User adds product to bag
    
    When User clicks in bag
    Then Verify products added matches with products in bag
    
    When User adds a promo code STACK-FS in shopping bag page
    Then Verify the applied promo code is active
    
    When User adds promo code Test-10P in shopping bag page
    Then Verify the applied promo code is active
    And Verify promo details contains: 10% off (no min)
    And Verify promo code applied 10 percent from subtotal
    
    And Verify the estimated shipping is 0.00