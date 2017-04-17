@shoppingbag3 @HighLevel
Feature: Stackable promo is not applied for promo excluded product

  Background:
  	Given User goes to homepage
    And User closes email capture    
  
  #id 209  
  Scenario: Stackable promo is not applied for promo excluded product
    When User searches for promo excluded product
    Then Verify product detail page is displayed
    
    When User selects random color
    And User selects random size        
    And User adds product to bag
    
    When User clicks in bag
    Then Verify products added matches with products in bag
    
    When User adds a promo code STACK10P
    Then Verify the applied promo code is inactive