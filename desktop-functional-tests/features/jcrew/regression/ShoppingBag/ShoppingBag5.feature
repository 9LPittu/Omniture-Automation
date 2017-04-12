@shoppingbag5 @HighLevel
Feature: Non stackable promo and stackable promo combinations allowed on billing page

  Background:
  	Given User goes to homepage
    And User closes email capture    
  
  #id 215  
  Scenario: Non stackable promo and stackable promo combinations allowed on billing page
    When User navigates to regular product
    Then Verify product detail page is displayed
    
    When User selects random color
    And User selects random size
    And User adds product to bag
    
    When User clicks in bag
    Then Verify products added matches with products in bag
    
    When User clicks in CHECK OUT NOW button
    And User checks out as guest
    Then Verify Shipping Page is displayed

    When User fills shipping data and continues
    Then Verify Shipping And Gift Options page is displayed

    When User selects a random shipping method and continues
    Then Verify Billing page is displayed
    
    #both stackable promos
    When User adds a promo code STACK10P
    Then Verify the applied promo code is active
    And Verify second promo text box is displayed in promo section
    When User adds a promo code STACK-FS
    Then Verify promo message says: These promotion codes cannot be combined.
    And User removes the already applied promo
    
    #both non-stackable promos
    When User adds a promo code TEST-10P
    Then Verify the applied promo code is active
    And Verify second promo text box is displayed in promo section
    When User adds a promo code TEST-FS
    Then Verify promo message says: These promotion codes cannot be combined.
    And User removes the already applied promo
    
    #stackable & non-stackable promos
    When User adds a promo code STACK10P
    Then Verify the applied promo code is active
    And Verify second promo text box is displayed in promo section
    And Verify order total is calculated correctly after promo is applied
    When User adds a promo code TEST-10P
    Then Verify the applied promo code is active
    And Verify order total is calculated correctly after promo is applied