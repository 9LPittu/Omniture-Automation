@shoppingbag1
Feature: Stackable promo functionality for free shipping product

  Scenario: Stackable promo(STACK-FS) functionality for free shipping product
  	Given User goes to homepage
    And User closes email capture
    
    When User searches for free shipping product
    Then Verify product detail page is displayed
    
    When User selects random color
    And User selects random size        
    And User adds product to bag
    
    When User clicks in bag
    Then Verify products added matches with products in bag
    
    When User adds a promo code STACK-FS in shopping bag page
    Then Verify the applied promo code is inactive  
     