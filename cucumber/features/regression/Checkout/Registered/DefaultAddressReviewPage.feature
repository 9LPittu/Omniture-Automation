@RegisteredCheckout @HighLevel
Feature: Checkout - Default shipping, Billing and Shipping Method on Review page for registered user

  Scenario: Checkout - Verify default shipping, billing and shipping method on Review page for registered user
  	Given User is on homepage with clean session
  	And Handle the Email Capture pop up
  	
    When click on SIGN IN from header
    Then User is on internal /r/login page
    And User fills form and signs in    
    And User bag is cleared
    
    And User goes to homepage
    And click on MY ACCOUNT from header
    When user clicks on "Sign Out" from My Account dropdown 
    Then Verify user is in homepage
    
    And User goes to homepage
    And User clicks on hamburger menu    
    And user selects any category from hamburger menu
	And user selects any subcategory
    
    And user selects any item from array page, select any color and size
    And User is in product detail page
    
    And Add to cart button is pressed
    And A minicart modal should appear with message '1 item has been added to your cart.'
    And Bag should have 1 item(s) added 
    
    When User clicks on item bag
    And Clicks on checkout    
    And User signs in and checks out    
    
    Then Verify user is in review page
    And Verify checkout breadcrumb is REVIEW    
    And Verify that Review title is Checkout    
    Then Validates billing section is present in review page
    And Validates shipping section is present in review page

    And Inputs credit card security code
    And User clicks on place your order button
    
    Then User should be in order confirmation page
    And verify order number is generated  
    
    And User goes to homepage
    And click on MY ACCOUNT from header
    When user clicks on "Sign Out" from My Account dropdown 
    
  Scenario: Checkout - Verify default shipping, billing and shipping method on Review page  for registered user from My Account login
  	Given User is on homepage with clean session
  	And Handle the Email Capture pop up
  	
    When click on SIGN IN from header
    Then User is on internal /r/login page
    And User fills form and signs in    
    And User bag is cleared
    
    And User goes to homepage
    And User clicks on hamburger menu    
    And user selects any category from hamburger menu
	And user selects any subcategory
    
    And user selects any item from array page, select any color and size
    And User is in product detail page
    
    And Add to cart button is pressed
    And A minicart modal should appear with message '1 item has been added to your cart.'
    And Bag should have 1 item(s) added 
    
    When User clicks on item bag
    And Clicks on checkout    
    
    Then Verify user is in review page
    And Verify checkout breadcrumb is REVIEW    
    And Verify that Review title is Checkout    
    Then Validates billing section is present in review page
    And Validates shipping section is present in review page

    And Inputs credit card security code
    And User clicks on place your order button
    
    Then User should be in order confirmation page
    And verify order number is generated