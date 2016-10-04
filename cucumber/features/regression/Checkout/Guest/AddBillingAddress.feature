@GuestCheckout-Part1
Feature: Checkout - Guest user is able to add billing address

  Scenario: Checkout - Guest user is able to add billing address
    
    Given User goes to homepage
    And Handle the Email Capture pop up
    
    And User clicks on hamburger menu    
    And user selects any category from hamburger menu
	And user selects any subcategory
    
    And user selects any item from array page, select any color and size
    And User is in product detail page
    
    And Add to cart button is pressed
    And A minicart modal should appear with message '1 item has been added to your cart.'
    And Bag should have 1 item(s) added
    
    And click on checkout from minicart modal
    Then page title should contain "Shopping Bag"
    And items count should be displayed as 1 in the bag
    And Move to mobile site

    Then Verify products added matches with products in bag

    And Clicks on checkout
    And click on CHECK OUT AS A GUEST button
    Then Verify Shipping Page is displayed

    When User fills shipping data and continues    
    Then Verifies is in shipping method page

    And select shipping method on shipping & gift options page
    And Clicks continue button on shipping method page
    Then Verify user is in billing page

    When User adds new billing address
    Then Verify Billing Address page is displayed

    When User fills billing address and continues
    Then Verify Billing page is displayed

    When User fills payment method as guest and continues
    Then Verify user is in review page
    And Verify added billing address matches review page

    And Clicks on place your order
    Then User should be in order confirmation page
    And verify order number is generated