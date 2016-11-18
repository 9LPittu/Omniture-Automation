@ATPDomestic1
Feature: View should be displayed for Regular Item but not for Backordered in Domestic Context

  Background: Clean bag for user
    Given User is on homepage with clean session
    And Handle the Email Capture pop up
    
    When click on SIGN IN from header
    And User fills form with no default user and signs in
    And User bag is cleared
    And User goes to homepage
	
	And User goes to homepage
    And click on MY ACCOUNT from header
    When user clicks on "Sign Out" from My Account dropdown 
    Then Verify user is in homepage
   
   Scenario: ATP view should be displayed for regular item 
    #Add item to the bag
    When User is on homepage
    And User clicks on hamburger menu
    And user selects any category from hamburger menu
	And user selects any subcategory
	
	And user selects any item from array page, select any color and size
    Then User is in product detail page    
    And product name and price should match with array page
         
    When Add to cart button is pressed
    When User clicks on item bag
    Then Verify products added matches with products in bag
    
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
	
	#Billing Method change
	
    And Submits payment data in billing page
    Then Verify user is in review page
    
    Then Verify user is in review page

    And Inputs credit card security code
    And User clicks on place your order button
    Then User should be in order confirmation page
    And verify order number is generated
    
    
    Scenario: No ATP view for BACKORDER item
    #ATP_02
    Given User goes to homepage
    
    When User navigates to backordered product
    When Add to cart button is pressed
    Then A minicart modal should appear with message '1 item has been added to your cart.'
    And Bag should have 1 item(s) added
    When User clicks on item bag
    And Clicks on checkout    
    And page url should contain /checkout2/shoppingbag.jsp
    And User signs in with no default user and checks out
    Then Verify Shipping Address page is displayed
    And Presses continue button on shipping address
   
    And Verify that all shipping methods are available including Thursday cut
    And Verify all shipping methods show estimated shipping time range
    And Verify that economy shipping method is selected by default
    