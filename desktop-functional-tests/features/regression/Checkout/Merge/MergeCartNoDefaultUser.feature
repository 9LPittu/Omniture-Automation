@MergeCheckout
Feature: No Default user gets confirmation to merge cart

  Background: Clean bag for user
    Given User goes to homepage
    And User closes email capture
    When User opens menu
    And User clicks user button SIGN IN TO MY ACCOUNT in drawer
    And User fills form with no default user and signs in
    Then Verify user is in My Account main page
    And This script cleans bag for current user

    When User goes to homepage
    And User clicks on menu
    And User clicks on Clothing in drawer
    And User selects random item from submenu
    And User clicks on random product in category array
    Then Verify a product detail page is displayed

    When User selects a color
    And User selects size
    And User clicks ADD TO BAG button
    And User clicks on menu
    When User goes back in drawer
    And User clicks user button My Account in drawer
    And User signs out

  Scenario: User checks out with a merged bag
    Given User goes to homepage
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
    And User signs in with no default user and checks out
    Then Verify user is in Merge Cart page
    And Verify user is welcome to Merge Cart page
    And Verify 'Save to Wishlist & Continue' button is available in Merge Cart Page    
    And Verify 'Add items to bag & Review Order' button is available in Merge Cart Page
    And Verify previously added items are in Merge Cart page

    When User clicks Add items to bag & Review Order
    Then Verify shopping bag is displayed
    And Verify madewell logo is visible
    And Verify that title is Shopping Bag
    And Verify products added matches with products in bag

    When User clicks in CHECK OUT NOW button
    Then Verify madewell logo is visible
    Then Verify select shipping address page is displayed

    When User selects a shipping address and continues
    Then Verify Shipping And Gift Options page is displayed

    When User selects a random shipping method
    And User continues to Payment Method page
    Then Verify Billing page is displayed

    When User continues to review page
    Then Verify user is in review page

    When User fills security code
    And User clicks on PLACE MY ORDER
    Then Verify user gets a confirmation number
  
  Scenario: User checks out by saving items to wishlist
    Given User goes to homepage
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
    
    And User signs in with no default user and checks out
    Then Verify user is in Merge Cart page
    And Verify user is welcome to Merge Cart page
    And Verify 'Save to Wishlist & Continue' button is available in Merge Cart Page    
    And Verify 'Add items to bag & Review Order' button is available in Merge Cart Page
    And Verify previously added items are in Merge Cart page

    When User clicks Save to Wishlist & Continue
    
    Then Verify select shipping address page is displayed
    
    And User clicks bag in header
    Then Verify previously added item is not shown in bag page