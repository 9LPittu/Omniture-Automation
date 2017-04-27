@PromoCode @HighLevel
Feature: User is able to add promo code in shopping bag

  Scenario: User is able to add promo code in shopping bag
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

    When User clicks in bag
    Then Verify shopping bag is displayed
    Then Verify products added matches with products in bag

    #id 203
    When User adds a promo code Test-10p
    Then Verify promo details contains: 10% off (no min)
    And Verify promo name contains: TEST-10P
    #id 206
    And Verify promo code applied 10 percent from subtotal
    And Verify remove button is displayed in promo section
    And Verify promo message is updated in the summary section

    When User adds a promo code STACK-FS
    Then Verify second promo details contains: Free shipping
    And Verify second promo name contains: STACK-FS

    When User edits first added item from bag
    Then Verify product detail page is displayed

    When User selects random color
    And User selects random size
    And Update Bag button is pressed
    And User clicks in bag
    Then Verify shopping bag is displayed
    #id 207
    And Verify promo code applied 10 percent from subtotal
    And Verify promo details contains: 10% off (no min)
    And Verify promo name contains: TEST-10P
    Then Verify second promo details contains: Free shipping
    And Verify second promo name contains: STACK-FS