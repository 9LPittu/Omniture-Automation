@shoppingbag4 @HighLevel
Feature: Site wise promo functionality for free shipping product

  Background:
  	Given User goes to homepage
    And User closes email capture    
  
  #id 211  
  Scenario: Site wise promo functionality for free shipping product
    When User searches for free shipping product
    Then Verify product detail page is displayed
    
    When User selects random color
    And User selects random size        
    And User adds product to bag
    
    When User clicks in bag
    Then Verify products added matches with products in bag
    
    When User adds promo code TEST-FS in shopping bag page
    Then Verify the applied promo code is inactive