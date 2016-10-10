@MergeCheckout
Feature: Checkout - Users with clean bag do not get a Merge Cart page

  Scenario: Checkout - Registered user does not get to a merge cart page
    Given User goes to homepage    
    And Handle the Email Capture pop up
    
    When click on SIGN IN from header
    And User fills form and signs in
    And User bag is cleared
    
    And User goes to homepage
    And click on MY ACCOUNT from header
    When user clicks on "Sign Out" from My Account dropdown 
    Then Verify user is in homepage

    Given User goes to homepage
    And User clicks on hamburger menu    
    And user selects any category from hamburger menu
	And user selects any subcategory
    
    And user selects any item from array page, select any color and size
    Then User is in product detail page
    
    And Add to cart button is pressed
    And Bag should have 1 item(s) added 
    
    When User clicks on item bag
    And Clicks on checkout
    
    And User signs in and checks out
    
    Then Verify user is in review page
    And page url should contain /checkout2/signin.jsp
    And Verify jcrew logo is visible
    And Verify checkout breadcrumb is REVIEW
    And Verify that Review title is Checkout
    And Verify products added matches with products in bag

  Scenario: Checkout - Guest user does not get to a merge cart page
    Given User goes to homepage
    And Handle the Email Capture pop up
    And User bag is cleared

    Given User goes to homepage
    And User clicks on hamburger menu    
    And user selects any category from hamburger menu
	And user selects any subcategory
    
    And user selects any item from array page, select any color and size
    Then User is in product detail page
    
    And Add to cart button is pressed
    And Bag should have 1 item(s) added 
    
    When User clicks on item bag
    And Clicks on checkout
    
    And User checks out as guest
    
    Then Verify Shipping Address page is displayed    
    And Verify jcrew logo is visible
    And Verify Shipping Page url is /checkout2/signin.jsp
    And Verify that Shipping title is Checkout
    And Verify checkout breadcrumb is SHIPPING ADDRESS