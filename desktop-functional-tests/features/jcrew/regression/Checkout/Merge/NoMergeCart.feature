@MergeCheckout @HighLevel
Feature: Checkout - Users with clean bag do not get a Merge Cart page

  Scenario: Checkout - Registered user does not get to a merge cart page
    Given User goes to homepage
    And User closes email capture
    When User clicks on sign in using header
    And User fills form and signs in
    And This script cleans bag for current user
    
    And User goes to homepage
    And User signs out using header

    Given User goes to homepage
    And User hovers on a random category from list
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
    And User clicks in CHECK OUT NOW button
    And User signs in and checks out
    Then Verify user is in review page
    And Verify Review Page url is /checkout2/signin.jsp
    And Verify jcrew logo is visible
    And Verify checkout breadcrumb is REVIEW
    And Verify that Review title is Checkout
    And Verify products added matches with products in bag

  Scenario: Checkout - Guest user does not get to a merge cart page
    Given User goes to homepage
    And User closes email capture
    When This script cleans bag for current user

    Given User goes to homepage
    And User hovers on a random category from list
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
    And User clicks in CHECK OUT NOW button
    And User checks out as guest
    Then Verify Shipping Page is displayed    
    And Verify jcrew logo is visible
    And Verify Shipping Page url is /checkout2/signin.jsp
    And Verify that Shipping title is Checkout
    And Verify checkout breadcrumb is SHIPPING ADDRESS