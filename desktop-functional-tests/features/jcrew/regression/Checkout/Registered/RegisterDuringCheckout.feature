@RegisteredCheckout @HighLevel
Feature: Checkout - Register during checkout process

  Scenario: Checkout - Register a user during checkout and place order
  	Given User goes to homepage
    And User closes email capture 
	
	When User hovers on a random category from list
    	|Women|
    	|Men|
    	|Girls|
    	|Boys|
    And User selects random subcategory array
    And User selects random product from product array
    Then Verify product detail page is displayed
    
    When User selects random color
    And User selects random size        
    And User adds product to bag

    When User clicks in bag
    Then Verify products added matches with products in bag
    
    When User clicks in CHECK OUT NOW button
    Then Verify Sign In Page url is checkout2/shoppingbag.jsp
    
    When User clicks on sign in using header
    Then User goes to sign in page
    And Selected country matches the current country context

    When first name field is filled with new data
    And last name field is filled with new data
    And email field is filled with new data
    And password field is filled with new data
    And User selects country from US group
    And User clicks Create An Account button
	Then Verify user is in homepage
	
	When User clicks in bag
    Then Verify products added matches with products in bag
    
    When User clicks in CHECK OUT NOW button
    Then Verify Shipping Page is displayed

    When User fills QAS shipping data and continues
    Then Verify QAS page is displayed

    When User selects a suggested address and continues
    Then Verify Shipping And Gift Options page is displayed

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

    When User fills payment method as registered user and continues
    Then Verify user is in review page

    When User clicks on PLACE MY ORDER
    Then Verify user gets a confirmation number
