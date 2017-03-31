@shoppingbag1
Feature: Stackable promo functionality for free shipping and regular products

  Background:
  	Given User goes to homepage
    And User closes email capture
    
  Scenario: Stackable promo(STACK-FS) functionality for free shipping product
    When User searches for free shipping product
    Then Verify product detail page is displayed
    
    When User selects random color
    And User selects random size        
    And User adds product to bag
    
    When User clicks in bag
    Then Verify products added matches with products in bag
    
    When User adds a promo code STACK-FS in shopping bag page
    Then Verify the applied promo code is inactive
    
  Scenario: Stackable promo(STACK-FS) functionality for free shipping product and regular product
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
    And Verify the estimated shipping is 0.00    