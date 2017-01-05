@GuestCheckout-Part2 @HighLevel
Feature: Checkout - Guest user is able to checkout

  Scenario: Checkout - Guest user is able to checkout
    Given User is on homepage with clean session
    When User clicks on hamburger menu
    And user selects any category from hamburger menu
	And user selects any subcategory
    And Handle the Email Capture pop up
    
    And user selects any item from array page, select any color and size
    Then User is in product detail page
    
    When Add to cart button is pressed
    Then A minicart modal should appear with message '1 item has been added to your cart.'
    And Bag should have 1 item(s) added   

    When User clicks on item bag
	Then User should be in shopping bag page
    And Verify products added matches with products in bag
    And Verify all products have edit and remove buttons
    And Verify bag has a promo code section
    And Verify bag has a gift card section
    And Verify bag has a order summary section
    And Verify bag has a paypal button
    And Verify bag has a help section with phone 800 562 0258 for questions    

    When User fills zip code field with 10003
    Then Verify estimated tax is populated
    And Verify estimated total sum

    And Clicks on checkout
    And click on CHECK OUT AS A GUEST button
    Then Verify Shipping Address page is displayed

    When User fills QAS shipping data and continues
    Then Verify QAS page is displayed

    When User selects a suggested address and continues
    Then Verifies user is in shipping method page
    And validate correct shipping methods displayed on the page 
    And Verify Shipping Options Page contains gift option section    
    And Verify default value for shipping method        

    When user select random shipping method on shipping & gift options page
    And Clicks continue button on shipping method page
    Then Verify Billing page is displayed
    And Verify available payment methods from list    
      | Credit/Debit Card |
      | PayPal            |      
      | MasterPass		  |
      
    Then Verify accepted cards from list    
      | jccc |
      | visa |
      | mc   |
      | amex |
      | disc |
      | jcb  |

    When User fills payment method as guest and continues
    Then Verify user is in review page

    When User clicks on place your order button
    Then User should be in order confirmation page
    And verify order number is generated 