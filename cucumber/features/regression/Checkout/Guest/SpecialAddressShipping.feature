@GuestCheckout-Part3
Feature: Checkout - Expected Shipping Options for special addresses

  Scenario: Checkout - Expected Shipping Options for APO
    Given User is on homepage with clean session
    And Handle the Email Capture pop up
    
    And User clicks on hamburger menu    
    And user selects any category from hamburger menu
	And user selects any subcategory
    
    And user selects any item from array page, select any color and size
    Then User is in product detail page
    
    When Add to cart button is pressed
    Then A minicart modal should appear with message '1 item has been added to your cart.'
    And Bag should have 1 item(s) added   

    When User clicks on item bag
	Then User should be in shopping bag page
    And Verify products added matches with products in bag

    And Clicks on checkout
    And click on CHECK OUT AS A GUEST button
    Then Verify Shipping Address page is displayed

    When User fills APO shipping data and continues
    Then Verifies user is in shipping method page
    And Verify default value for shipping method