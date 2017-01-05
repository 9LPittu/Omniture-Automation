@GuestCheckout-Part1 @HighLevel
Feature: Checkout - Guest user is able to edit data in review page

  Scenario: Checkout - Guest user is able to edit data in review page
    Given User is on homepage with clean session
    When User clicks on hamburger menu
    And user selects any category from hamburger menu
	And user selects any subcategory
    And Handle the Email Capture pop up
    
    And user selects any item from array page, select any color and size
    Then User is in product detail page
    
    When Add to cart button is pressed
    Then A minicart modal should appear with message '1 item has been added to your cart.'
    And Bag should have 1 item(s) added
    
    And click on checkout from minicart modal
    Then page title should contain "Shopping Bag"
    And items count should be displayed as 1 in the bag
    
    And Move to mobile site

    Then Verify products added matches with products in bag

    And Clicks on checkout
    And click on CHECK OUT AS A GUEST button
    Then Verify Shipping Address page is displayed

    When User fills shipping data and continues
    Then Verifies user is in shipping method page

    And user select random shipping method on shipping & gift options page
    And Clicks continue button on shipping method page
    Then Verify user is in billing page

    When User fills payment method as guest and continues
    Then Verify user is in review page

    When User edits details for billing
    Then Verify Billing page is displayed

    And Submits payment data in billing page
    Then Verify user is in review page

    When User edits details for shipping
    Then Verify Shipping Address page is displayed

    When Presses continue button on shipping address
    Then Verifies user is in shipping method page

    And Clicks continue button on shipping method page
    Then Verify Billing page is displayed

    When Submits payment data in billing page
    Then Verify user is in review page

    When User edits details for gifting
    Then Verifies user is in shipping method page

    And Clicks continue button on shipping method page
    Then Verify Billing page is displayed

    When Submits payment data in billing page
    Then Verify user is in review page

    When User edits details for order
    Then User should be in shopping bag page