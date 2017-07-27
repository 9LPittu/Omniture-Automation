@promocode3 @HighLevel
Feature: Validate user should not be able to apply same promotion code twice in one order

  Scenario: Validate user should not be able to apply same promotion code twice in one order
    Given User goes to homepage
    And User closes email capture
    
    When User navigates to regular product   
    And User adds product to bag
    
    When User clicks in bag
    Then Verify shopping bag is displayed
    Then Verify products added matches with products in bag

    #stackable promo
    When User adds a promo code STACK10P
    Then Verify the applied promo code STACK10P is active
    And Verify second promo text box is displayed in promo section
    
    When User adds a promo code STACK10P
    Then Verify promo message says: These promotion codes cannot be combined.
    
    And User removes the already applied promo
    
    #non-stackable promo
    When User adds a promo code TEST-10P
    Then Verify the applied promo code TEST-10P is active
    And Verify second promo text box is displayed in promo section
    
    When User adds a promo code TEST-10P
    Then Verify promo message says: These promotion codes cannot be combined.    