@ATPDomestic3

Feature: View should be displayed for Express users in Domestic Context

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

  Scenario: ATP view should be displayed for express checkout if the user login
    #ATP_11
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
    And User signs in and checks out
    Then Verify user is in review page
    And Verify checkout breadcrumb is REVIEW
    And Verify that Review title is Checkout
    And Validates billing section is present in review page
    And Validates shipping section is present in review page
    #ATP_25
    And Verify all shipping methods are available in review page
    And Verify all shipping methods show estimated shipping date in review page

    When User fills security code
    And User clicks on PLACE MY ORDER
    Then Verify user gets a confirmation number
    And Verify that title is Order Complete
    And Verify that confirmation message is visible