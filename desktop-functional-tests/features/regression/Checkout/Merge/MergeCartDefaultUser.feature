@MergeCheckout
Feature: Default user gets confirmation to merge cart

  Background: Clean bag for user
    Given User goes to homepage
    And User closes email capture
    When User opens menu
    And User clicks user button SIGN IN TO MY ACCOUNT in drawer
    And User fills form and signs in
    And This script cleans bag for current user
    And User goes to homepage

    When User opens menu
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

  Scenario: User checks out only with recently added products
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
    And User signs in and checks out
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
    Then Verify user is in review page
    And Verify checkout breadcrumb is REVIEW
    And Verify that Review title is Checkout
    And Verify products added matches with products in bag

    When User fills security code
    And User clicks on PLACE MY ORDER
    Then Verify user gets a confirmation number
    And Verify that title is Order Complete
    And Verify that confirmation message is visible