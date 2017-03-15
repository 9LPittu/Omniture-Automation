@CheckoutGuest
Feature: Smoke Checkout - Guest user is able to checkout

  Scenario: Smoke Checkout - Guest user is able to checkout
    Given User goes to homepage
    And User closes email capture
    And User hovers on a random category from list
      | Women |
      | Men   |
    And User selects sweaters subcategory array
    And User closes email capture
    And User selects random product from product array
    And Verify product detail page is displayed

    When User selects random color
    And User selects random size
    And User adds product to bag

    When User clicks in bag
    Then Verify shopping bag is displayed
    Then Verify products added matches with products in bag
    And Verify bag has a promo code section
    And Verify bag has a gift card section
    And Verify bag has a order summary section
    And Verify bag has a paypal button

    When User fills zip code field with 10003
    Then Verify estimated tax is populated
    And Verify estimated total sum

    When User clicks in CHECK OUT NOW button
    And User checks out as guest
    Then Verify Shipping Page is displayed

    When User fills QAS shipping data and continues
    Then Verify QAS page is displayed

    When User selects a suggested address and continues
    Then Verify Shipping And Gift Options page is displayed
    And Verify Shipping Options Page contains gift option section

    When User selects a random shipping method and continues
    Then Verify Billing page is displayed
    And Verify available payment methods from list
      | Credit/Debit Card |
      | PayPal            |

    And Verify accepted cards from list
      | jccc |
      | visa |
      | mc   |
      | amex |
      | disc |
      | jcb  |

    When User fills payment method as guest and continues
    Then Verify user is in review page

    When User clicks on PLACE MY ORDER
    Then Verify user gets a confirmation number