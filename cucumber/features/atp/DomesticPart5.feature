@ATPDomestic5
Feature: Verify view for Monogram Items in Domestic Context - 150 Threshold

  Background: Clean bag for user
    Given User is on homepage with clean session
    And Handle the Email Capture pop up
    
    When click on SIGN IN from header
    And User fills form with no default user and signs in
    And User bag is cleared
    And User goes to homepage
	
    And click on MY ACCOUNT from header
    When User selects Sign Out from my details dropdown
    Then Verify user is in homepage
   
   Scenario: No ATP view should be displayed for monogram item
    #ATP_03
    When User is on homepage
    When User navigates to expensive monogram product
    And User adds monogram to product
    And User fills monogram options
    When Add to cart button is pressed
    And items count should be displayed as 1 in the bag
    When User clicks on item bag
    And Verify subtotal is greater than 150 USD
    And Clicks on checkout    
    And page url should contain /checkout2/shoppingbag.jsp
    
    And User signs in with no default user and checks out
    Then Verify Shipping Address page is displayed
    
    And Presses continue button on shipping address
    And Verifies user is in shipping method page
	
    And validate correct shipping methods displayed on the page
    
    And Clicks continue button on shipping method page
    Then Verify Billing page is displayed
	
    And Submits payment data in billing page
    Then Verify user is in review page
    
    And Inputs credit card security code
    And User clicks on place your order button
    Then User should be in order confirmation page
    And verify order number is generated
    
    Scenario: ATP view should be displayed for regular and monogram item
    #ATP_05
    When User navigates to regular product
    When Add to cart button is pressed
    And items count should be displayed as 1 in the bag
   
	When User navigates to expensive monogram product
    And User adds monogram to product
    And User fills monogram options
    When Add to cart button is pressed
    And items count should be displayed as 2 in the bag
    When User clicks on item bag
    And Verify subtotal is greater than 150 USD
    
    And Clicks on checkout    
    And page url should contain /checkout2/shoppingbag.jsp
    
    And User signs in with no default user and checks out
    Then Verify Shipping Address page is displayed

    And Presses continue button on shipping address
    And Verifies user is in shipping method page

    
    And Verify that all shipping methods are available including Thursday cut
    Then Verify all shipping methods show estimated shipping date
    And Clicks continue button on shipping method page
    Then Verify Billing page is displayed
	
    And Submits payment data in billing page
    Then Verify user is in review page
   
    And Inputs credit card security code
    And User clicks on place your order button
    Then User should be in order confirmation page
    And verify order number is generated
    