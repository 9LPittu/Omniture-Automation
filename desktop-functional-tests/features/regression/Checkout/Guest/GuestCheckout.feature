@GuestCheckout-Part2
Feature: Checkout - Guest user is able to checkout

  Scenario: Guest user is able to checkout
    Given User goes to homepage
    And User closes email capture
    When User opens menu
	And User selects random category from list
    	|Women|
    	|Men|
    	|Girls|
    	|Boys|
    And User selects random subcategory array
    And User selects random product from product array
    And Verify product detail page is displayed
    
    When User selects random color
    And User selects random size        
    And User adds product to bag

    When User clicks in bag

    Then Verify products added matches with products in bag
    And Verify all products have edit and remove buttons
    And Verify bag has a promo code section
    And Verify bag has a gift card section
    And Verify bag has a order summary section
    And Verify bag has a paypal button
    And Verify bag has a help section with phone 800 562 0258 for questions

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
    And Verify that this shipping methods are available including Thursday cut
      | method                                       | price  | text                                                              | thursday |
      | Economy (6-8 business days)                  | FREE   | Your order will be delivered by the United States Postal Service. | false    |
      | Standard (3-6 business days)                 | $8.00  |                                                                   | false    |
      | Expedited (2-3 business days)                | $20.00 |                                                                   | false    |
      | Overnight (1-2 business days)                | $25.00 | 																  | false    |
      | Saturday		                             | $35.00 |                                                                   | true     |
    And Verify Shipping Options Page contains gift option section
    And Verify that this shipping method is selected by default
      | method                                       | price  | text                                                              |
      | Economy (6-8 business days) 				 | FREE   | Your order will be delivered by the United States Postal Service. |

    When User selects a random shipping method and continues
    Then Verify Billing page is displayed
    And Verify available payment methods from list
      | Credit/Debit Card |
      | PayPal            |      
      | MasterPass		  |
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