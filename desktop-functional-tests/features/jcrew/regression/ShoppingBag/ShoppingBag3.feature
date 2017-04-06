@shoppingbag3 @HighLevel
Feature: Stackable promo is applied for promo excluded product

  Background:
  	Given User goes to homepage
    And User closes email capture    
     
  Scenario: Stackable promo is applied for promo excluded product
    When User searches for promo excluded product
    Then Verify product detail page is displayed
    
    When User selects random color
    And User selects random size        
    And User adds product to bag
    
    When User clicks in bag
    Then Verify products added matches with products in bag
    
    When User adds a promo code STACK10P in shopping bag page
    Then Verify the applied promo code is active