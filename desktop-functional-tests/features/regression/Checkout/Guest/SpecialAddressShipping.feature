@GuestCheckout-Part3
Feature: Checkout - Expected Shipping Options for special addresses

  Scenario: Expected Shipping Options for APO
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

    When User clicks in CHECK OUT NOW button
    And User checks out as guest
    Then Verify Shipping Page is displayed

    When User fills APO shipping data and continues
    Then Verify Shipping And Gift Options page is displayed
    And Verify that this shipping methods are available including Thursday cut
      | method                      | price | text | thursday |
      | Economy (6-8 business days) | $5.00 |      | false    |