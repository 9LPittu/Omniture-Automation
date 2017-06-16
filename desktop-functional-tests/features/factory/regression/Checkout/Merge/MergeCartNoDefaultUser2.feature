@MergeCheckout4
Feature: Checkout - No Default user gets confirmation to merge cart2

  Background: Clean bag for user
    Given User goes to homepage
    And User closes email capture
    When User clicks on sign in using header
    And User fills form with no default user and signs in
    Then Verify user is in My Account main page
    And This script cleans bag for current user
    And User goes to homepage
    
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
    
    When User clicks in My Account
	And User clicks on sign out link in Account detail Page
    
  Scenario: Checkout - User checks out by saving items to wishlist
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
    
    And User signs in with no default user and checks out
    Then Verify user is in Merge Cart page
    And Verify user is welcome to Merge Cart page
    And Verify 'Save to Wishlist & Continue' button is available in Merge Cart Page    
    And Verify 'Add items to bag & Review Order' button is available in Merge Cart Page
    And Verify previously added items are in Merge Cart page

    When User clicks Save to Wishlist & Continue
    
    Then Verify select shipping address page is displayed
    
    When User clicks in bag
    Then Verify previously added item is not shown in bag page