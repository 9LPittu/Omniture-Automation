@shoppingbag6 @HighLevel
Feature: Site wise promo and stackable promo functionality on shopping bag page

  Background:
  	Given User goes to homepage
    And User closes email capture    
  
  #id 224, 225
  Scenario Outline: Site wise promo and stackable promo functionality on shopping bag page
    When User navigates to regular product
    Then Verify product detail page is displayed  
    
    And User adds product to bag
    
    When User clicks in bag
    Then Verify shopping bag is displayed
    And Verify products added matches with products in bag
        
    When User adds a promo code STACK-FS-50
    Then Verify the applied promo code is active
    And Verify second promo text box is displayed in promo section
    And Verify order total is calculated correctly after promo is applied
    
    When User adds a promo code <promocode>
    Then Verify the applied promo code is active
    And Verify order total is calculated correctly after promo is applied
  Examples:
  	|promocode   |
  	|TEST-10P    |
  	|TEST-15PF-FS|