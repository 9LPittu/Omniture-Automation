@MergeCheckout
Feature: Checkout - Default user gets confirmation to merge cart

  Background: Clean bag for user
    Given User goes to homepage
    And User closes email capture
    When User clicks on sign in using header
    And User fills form and signs in    
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
    
    And User signs out using header

  Scenario: Checkout - User checks out only with recently added products
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
    
    Then Verify user is in Merge Cart page
    And Verify user is welcome to Merge Cart page
    And Verify 'Save to Wishlist & Continue' button is available in Merge Cart Page    
    And Verify 'Add items to bag & Review Order' button is available in Merge Cart Page
    And Verify previously added items are in Merge Cart page

    When User clicks Save to Wishlist & Continue
    Then Verify user is in review page
    And Verify Review Page url is /checkout2/mergebags.jsp
    And Verify checkout breadcrumb is REVIEW
    And Verify that Review title is Checkout
    And Verify products added matches with products in bag

    When User fills security code
    And User clicks on PLACE MY ORDER
    Then Verify user gets a confirmation number
    And Verify that title is Order Complete
    And Verify that confirmation message is visible

  Scenario: Checkout - User checks out with a merged bag
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
    Then Verify user is in Merge Cart page
    And Verify user is welcome to Merge Cart page
    And Verify 'Save to Wishlist & Continue' button is available in Merge Cart Page    
    And Verify 'Add items to bag & Review Order' button is available in Merge Cart Page
    And Verify previously added items are in Merge Cart page

    When User clicks Add items to bag & Review Order
    Then Verify shopping bag is displayed
    And Verify jcrew logo is visible    
    And Verify that title is Shopping Bag
    And Verify products added matches with products in bag

    When User clicks in CHECK OUT NOW button
    Then Verify user is in review page
    And Verify checkout breadcrumb is REVIEW
    And Verify that Review title is Checkout
    And Verify products added matches with products in bag

    When User fills security code
    And User clicks on PLACE MY ORDER
    Then Verify user gets a confirmation number
    And Verify that title is Order Complete
    And Verify that confirmation message is visible