@PromoCode @HighLevel
Feature: Checkout - Guest user is able to checkout with two promo's (stack promo and promo related to MASTERCARD payment)

  Scenario: Development
  Checkout - with Stack and MASTERCARD promo
    Given User goes to homepage
    And User closes email capture
    And User hovers on a random category from list
      | Women |
      | Men   |
      | Girls |
      | Boys  |
    And User selects random subcategory array
    And User selects random product from product array
    And Verify product detail page is displayed

    When User selects random color
    And User selects random size
    And User adds product to bag
    And User clicks in bag
    Then Verify shopping bag is displayed
    And Verify products added matches with products in bag

    When User clicks in CHECK OUT NOW button
    And User checks out as guest
    Then Verify Shipping Page is displayed

    When User fills shipping data and continues
    Then Verify Shipping And Gift Options page is displayed

    When User selects a random shipping method and continues
    Then Verify Billing page is displayed

    When User fills payment method as guest with mastercard and continues
    Then Verify user is in review page
	When User adds a promo code STACK-FS
	Then Verify second promo text box is displayed in promo section
    When User adds a promo code MASTERCARD
    Then Verify promo details contains: 25% off order with a MasterCard
    And Verify promo code applied 25 percent from subtotal
