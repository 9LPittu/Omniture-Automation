@RegisteredCheckout
Feature: Default shipping, Billing and Shipping Method on Review page for registered user

  Scenario: Verify default shipping, billing and shipping method on Review page  for registered user
  	Given User goes to homepage
  	And User closes email capture
  	
    When User opens menu
    And User clicks user button SIGN IN TO MY ACCOUNT in drawer
    And User fills form and signs in
    And This script cleans bag for current user
    
    And User goes to homepage
    When User opens menu
    And User clicks user button My Account in drawer
    And User signs out
    
    And User goes to homepage
    When User opens menu
    And User clicks on Clothing in drawer
    And User clicks on Tees & More in drawer
    And User clicks on random product in category array
    Then Verify a product detail page is displayed
    
    When User selects a color
    And User selects size
    And User clicks ADD TO BAG button
    
    And User clicks bag in header
    And User clicks in CHECK OUT NOW button    
    And User signs in and checks out
    
    Then Verify user is in review page
    And Verify checkout breadcrumb is REVIEW
    And Verify that Review title is Checkout
    Then Verify billing address display on review page
    Then Verify shipping address display on review page

    When User fills security code
    And User clicks on PLACE MY ORDER
    Then Verify user gets a confirmation number
    And Verify that title is Order Complete
    And Verify that confirmation message is visible
    
  Scenario: Verify default shipping, billing and shipping method on Review page  for registered user from My Account login
  	Given User goes to homepage
  	And User closes email capture
  	
    When User opens menu
    And User clicks user button SIGN IN TO MY ACCOUNT in drawer
    And User fills form and signs in
    And This script cleans bag for current user
    And User goes to homepage
    When User opens menu
    
    And User clicks on Clothing in drawer
    And User clicks on Tees & More in drawer
    And User clicks on random product in category array
    Then Verify a product detail page is displayed
    
    When User selects a color
    And User selects size
    And User clicks ADD TO BAG button
    
    And User clicks bag in header
    And User clicks in CHECK OUT NOW button
    
    Then Verify user is in review page
    And Verify checkout breadcrumb is REVIEW
    And Verify that Review title is Checkout
    Then Verify billing address display on review page
    Then Verify shipping address display on review page

    When User fills security code
    And User clicks on PLACE MY ORDER
    Then Verify user gets a confirmation number
    And Verify that title is Order Complete
    And Verify that confirmation message is visible