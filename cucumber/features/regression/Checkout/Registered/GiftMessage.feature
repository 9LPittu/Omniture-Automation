@RegisteredCheckout @HighLevel
Feature: Checkout - Registered user is able to add a gift message

  Background: Clean bag for user
    Given User is on homepage with clean session
    And Handle the Email Capture pop up
    
    When click on SIGN IN from header
    And User fills form with no default user and signs in
    And User bag is cleared
    And User goes to homepage
	
	And User goes to homepage
    And click on MY ACCOUNT from header
    When User selects SIGN OUT from my details dropdown
    Then Verify user is in homepage
	
  Scenario: Checkout - Registered user is able to add a gift message
    Given User goes to homepage
    And User clicks on hamburger menu    
    And user selects any category from hamburger menu
	And user selects any subcategory
    
    And user selects any item from array page, select any color and size
    And User is in product detail page
    
    And Add to cart button is pressed
    And A minicart modal should appear with message '1 item has been added to your cart.'
    And Bag should have 1 item(s) added 
    
    When User clicks on item bag
    Then Verify products added matches with products in bag

    And Clicks on checkout
    And User signs in with no default user and checks out
    Then Verify Shipping Address page is displayed

    And Presses continue button on shipping address
    And Verifies user is in shipping method page

    And user select random shipping method on shipping & gift options page
    And User selects gift option and adds message: This is an automated execution message for a gift
    And Clicks continue button on shipping method page
    Then Verify Billing page is displayed

    And click 'Add New Card' on billing page
    Then Verify Billing Payment page is displayed    

    When User fills billing payment with mastercard and continues    
    Then Verify user is in review page

    When User edits details for billing
    Then Verify Billing page is displayed
    And Verify card has been added    

    When User edits recently added card    
    Then Verify Billing Payment page is displayed

    When User edits billing payment information and continues    
    Then Verify Billing Payment page is displayed
    And Verify card has been edited    

    When User removes Mastercard card    
    Then Verify card has been removed    

    And Presses continue button on Billing page
    Then Verify user is in review page

    And Inputs credit card security code
    And User clicks on place your order button
    Then User should be in order confirmation page
    And verify order number is generated
    And Verify Gift message is This is an automated execution message for a gift    