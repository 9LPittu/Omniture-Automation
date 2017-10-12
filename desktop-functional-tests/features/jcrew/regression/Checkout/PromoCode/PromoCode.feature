@Sanity @PromoCode @HighLevel
Feature: Checkout - Guest user is able to checkout with promo code

  Scenario: Checkout - Guest user is able to checkout with promo code
    Given User goes to homepage
    And User closes email capture
    
    When User navigates to promo applicable product
    And User adds product to bag

    When User clicks in bag
    Then Verify products added matches with products in bag

    When User clicks in CHECK OUT NOW button
    And User checks out as guest
    Then Verify Shipping Page is displayed

    When User fills QAS shipping data and continues
    Then Verify QAS page is displayed

    When User selects a suggested address and continues
    Then Verify Shipping And Gift Options page is displayed

    When User selects a random shipping method and continues
    Then Verify Billing page is displayed

    When User adds a promo code Test-invalid
    Then Verify promo message says: The promotion code you entered is not valid or has expired. Please try the code again or call 800 562 0258 for help.

    When User adds a promo code Test-10p
    Then Verify promo details contains: 10% off (no min)
    And Verify promo name contains: TEST-10P
    And Verify promo code applied 10 percent from subtotal
    And Verify remove button is displayed in promo section
    And Verify promo message is updated in the summary section    

    When User fills payment method as guest and continues
    Then Verify user is in review page

    When User clicks on PLACE MY ORDER
    Then Verify user gets a confirmation number