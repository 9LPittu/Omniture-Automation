@MergeCheckout @HighLevel
Feature: Checkout - Default user gets confirmation to merge cart

  Background: Clean bag for user
    Given User is on homepage with clean session
    And Handle the Email Capture pop up
    
    When click on SIGN IN from header
    And User fills form and signs in    
    And User bag is cleared
    And User goes to homepage

    And User clicks on hamburger menu    
    And user selects any category from hamburger menu
	And user selects any subcategory
    
    And user selects any item from array page, select any color and size
    Then User is in product detail page
    
    And Add to cart button is pressed
    And A minicart modal should appear with message '1 item has been added to your cart.'
    And Bag should have 1 item(s) added 
    
    And User goes to homepage
    And click on MY ACCOUNT from header
    When User selects SIGN OUT from my details dropdown
    Then Verify user is in homepage

  Scenario: Checkout - User checks out only with recently added products
    Given User goes to homepage
    When User clicks on hamburger menu    
    And user selects any category from hamburger menu
	And user selects any subcategory
    
    And user selects any item from array page, select any color and size
    Then User is in product detail page
    
    And Add to cart button is pressed
    And Bag should have 1 item(s) added 
    
    When User clicks on item bag
    And Clicks on checkout
    
    And User signs in and checks out
    
    Then Verify user is in Merge Cart page    
    And Verify user is welcome to Merge Cart page    
    And user should see 'SAVE TO WISHLIST & CONTINUE' button on the page    
	And user should see 'ADD ITEMS TO BAG & REVIEW ORDER' button on the page
    And Verify previously added items are in Merge Cart page    

    When User clicks on save to wishlist button and continue checkout    
    Then Verify user is in review page
    And page url should contain /checkout2/mergebags.jsp
    And Verify checkout breadcrumb is REVIEW
    And Verify that Review title is Checkout
    And Verify products added matches with products in bag

    And Inputs credit card security code
    And User clicks on place your order button
    Then User should be in order confirmation page
    And verify order number is generated

  Scenario: Checkout - User checks out with a merged bag
    Given User goes to homepage
    And User clicks on hamburger menu    
    And user selects any category from hamburger menu
	And user selects any subcategory
    
    And user selects any item from array page, select any color and size
    Then User is in product detail page
    
    And Add to cart button is pressed
    Then Bag should have 1 item(s) added 
    
    When User clicks on item bag
    And Clicks on checkout
    
    And User signs in and checks out
    
    Then Verify user is in Merge Cart page
    And Verify user is welcome to Merge Cart page
    And user should see 'SAVE TO WISHLIST & CONTINUE' button on the page    
	And user should see 'ADD ITEMS TO BAG & REVIEW ORDER' button on the page
    And Verify previously added items are in Merge Cart page

    And click on ADD ITEMS TO BAG & REVIEW ORDER button
    Then User should be in shopping bag page
    And Verify jcrew logo is visible     
    Then page title should contain "Shopping Bag"
    And Verify products added matches with products in bag

    And Clicks on checkout
    Then Verify user is in review page
    And Verify checkout breadcrumb is REVIEW
    And Verify that Review title is Checkout
    And Verify products added matches with products in bag

    And Inputs credit card security code
    And User clicks on place your order button
    Then User should be in order confirmation page
    And verify order number is generated