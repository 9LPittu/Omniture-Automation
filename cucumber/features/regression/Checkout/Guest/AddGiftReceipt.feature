@GuestCheckout-Part1
Feature: Checkout - User is able to gift receipt to order during Guest checkout

  Scenario: Checkout - User is able to gift receipt to order during Guest checkout
    Given User is on homepage with clean session
    And Handle the Email Capture pop up
    
    And User clicks on hamburger menu
    And user selects any category from hamburger menu
    And user selects any subcategory
    And user selects any item from array page, select any color and size
    Then User is in product detail page      
    
    And Add to cart button is pressed
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

    When User fills QAS shipping data and continues
    Then Verify QAS page is displayed    

    When User selects a suggested address and continues
    Then Verifies user is in shipping method page
    
    And user select random shipping method on shipping & gift options page
    And User selects gift option and adds message: This is an automated execution message for a gift receipt    
    Then Verify gift receipt info message is 'Your message will be printed on the invoice and prices will be omitted.'         
    And Clicks continue button on shipping method page
    And Verify user is in billing page
    
    Then Verify no additional charges are applied for gift receipt    