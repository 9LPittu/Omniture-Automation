@GuestCheckout-Part3
Feature: Checkout - Guest user is able to checkout with promo code

  Scenario: Checkout - Guest user is able to checkout with promo code
    Given User goes to homepage
    And Handle the Email Capture pop up
    
    And User clicks on hamburger menu    
    And user selects any category from hamburger menu
	And user selects any subcategory
    
    And user selects any item from array page, select any color and size
    And User is in product detail page
    
    And Add to cart button is pressed
    And Bag should have 1 item(s) added
    
    When User clicks on item bag
    Then User should be in shopping bag page
    
    Then Verify products added matches with products in bag

    And Clicks on checkout
    And click on CHECK OUT AS A GUEST button
    Then Verify Shipping Page is displayed

    When User fills QAS shipping data and continues
    Then Verify QAS page is displayed

    When User selects a suggested address and continues
    Then Verifies is in shipping method page

    And select shipping method on shipping & gift options page
    And Clicks continue button on shipping method page
    Then Verify Billing page is displayed

    When User adds a promo code Test-invalid in Payment Method page
    Then Verify promo message says: The promotion code you entered is not valid or has expired. Please try the code again or call 800 562 0258 for help.

    When User adds a promo code Test-10p in Payment Method page
    Then Verify promo details contains: 10% off (no min)
    And Verify promo name contains: TEST-10P
    And Verify promo code applied 10 percent from subtotal
    And Verify remove button is displayed in promo section
    And Verify promo message is updated in the summary section     

    When User fills payment method as guest and continues
    Then Verify user is in review page

    When Clicks on place your order
    Then User should be in order confirmation page
    And verify order number is generated