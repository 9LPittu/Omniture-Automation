@AccountDropdown1 @HighLevel
Feature: Account Dropdown functionality - Part 1

  Background:
    Given User is on homepage with clean session
    And Handle the Email Capture pop up
  
  Scenario: Sign In link displayed in header and functional for multiple pages
  	And Verify SIGN IN header link is displayed
  	And click on SIGN IN from header
  	And User is on internal /r/login page
  	And User presses back button
  	
    When User clicks on hamburger menu
    And user selects any category from hamburger menu
    And user selects any subcategory
    
    And Verify SIGN IN header link is displayed
  	And click on SIGN IN from header
  	And User is on internal /r/login page
  	And User presses back button
    
    And user selects any item from array page, select any color and size
    And User is in product detail page
    And Add to cart button is pressed
    
    And Verify SIGN IN header link is displayed
  	And click on SIGN IN from header
  	And User is on internal /r/login page
  	And User presses back button
    
    And User clicks on item bag
    Then User should be in shopping bag page
    
    And Verify SIGN IN header link is displayed
  	And click on SIGN IN from header
  	And User is on internal /r/login page
  	And User presses back button
    
    And Clicks on checkout
    And Verify SIGN IN header link is displayed
  	And click on SIGN IN from header
  	And User is on internal /r/login page
  	
  	And User clicks on item bag
    Then User should be in shopping bag page
    
    And Clicks on checkout    
    And Selects to checkout as guest
    
    Then Verify SIGN IN header link is displayed
  	And Verify SIGN IN header link points to the url /r/login
    
    And Fills shipping address    
    And Presses continue button on shipping address
    And Verifies user is in shipping method page
    
  	Then Verify SIGN IN header link is displayed
  	And Verify SIGN IN header link points to the url /r/login
    
    And Uses default value for shipping method
    And Uses default value for gifts option
    And Clicks continue button on shipping method page
    
    And Verify SIGN IN header link is displayed
    And Verify SIGN IN header link points to the url /r/login
    And Fills required payment data in billing page
    And Submits payment data in billing page
    And User clicks on place your order button
    And User should be in order confirmation page
    
  	Then Verify SIGN IN header link is displayed
  	And Verify SIGN IN header link points to the url /r/login
  
  Scenario: Sign In link displayed in header and functional for search page
    And User presses search button
    When Enters Dresses to the search field
    And Clicks on search button for input field
    And User is in search results page
    
    And Verify SIGN IN header link is displayed
  	And click on SIGN IN from header
  	And User is on internal /r/login page  	
  
  Scenario: Sign In link displayed in header and functional for sale page
    And User clicks on hamburger menu
    And Selects sale Category from hamburger menu
    And User is in sale landing page
    
    And Verify SIGN IN header link is displayed
  	And click on SIGN IN from header
  	And User is on internal /r/login page
  	And User presses back button
  	
  	And user clicks on random sale department from below list
  	   |women|
       |men  |
       |boys |
       |girls|
    Then User is in Sale results page
    
    And Verify SIGN IN header link is displayed
  	And click on SIGN IN from header
  	And User is on internal /r/login page  	
   
  Scenario: Sign In link displayed in header and functional on Department/Gender Landing Pages
    And user clicks on random top nav gender link from below list    
    	 |Women |
         |Men   |
         |Boys  |
         |Girls |
    
    And Verify SIGN IN header link is displayed
  	And click on SIGN IN from header
  	And User is on internal /r/login page
  
#  Scenario: Sign In link displayed in header and functional on Shoppable tray page
#    And User clicks on hamburger menu
#    And User selects random tray from available categories
#      | Women | THIS MONTH'S FEATURES | looks we love  |
#      | Men   | THIS MONTH'S FEATURES | 1 Suit, 5 Ways |
#      | Girls | THIS MONTH'S FEATURES | Looks We Love  |
#      | Boys  | THIS MONTH'S FEATURES | Looks We Love  |
#    Then Verifies initial multiple pdp page state
#    
#    And Verify SIGN IN header link is displayed
#  	And click on SIGN IN from header
#  	And User is on internal /r/login page
  
  Scenario: My Account dropdown open and close is functional on multiple pages
  	And click on SIGN IN from header
  	And User is on internal /r/login page
  	And User fills form and signs in
    
    Then User is in My Account home page
    And Verify MY ACCOUNT header link is displayed

    Then Verify embedded headers links is visible
    And click on MY ACCOUNT from header
    Then user should see My Account dropdown is opened    
    #JCSC-11715 fix
    And user should see welcome message and desktop options in My Account dropdown on jsp pages

    When User clicks on hamburger menu
    And user selects any category from hamburger menu
    And user selects any subcategory
    
    Then Verify embedded headers links is visible
    And click on MY ACCOUNT from header
    Then user should see My Account dropdown is opened
    Then user should see welcome message, My Details, Sign Out and close button in My Account dropdown
    When user clicks on "close" from My Account dropdown
    Then user should see My Account dropdown is closed
    
    And user selects any item from array page, select any color and size
    And User is in product detail page
    And Add to cart button is pressed
    
    Then Verify embedded headers links is visible
    And click on MY ACCOUNT from header
    Then user should see My Account dropdown is opened
    Then user should see welcome message, My Details, Sign Out and close button in My Account dropdown
    When user clicks on "close" from My Account dropdown
    Then user should see My Account dropdown is closed
    
    And User clicks on item bag
    
    Then Verify embedded headers links is visible
    And click on MY ACCOUNT from header
    Then user should see My Account dropdown is opened
    Then user should see welcome message, My Details, Sign Out and close button in My Account dropdown
    When user clicks on "close" from My Account dropdown
    Then user should see My Account dropdown is closed
    
    And Clicks on checkout
    And page url should contain /checkout2/shoppingbag.jsp
    
    Then Verify embedded headers links is visible
    And click on MY ACCOUNT from header
    Then user should see My Account dropdown is opened
    Then user should see welcome message, My Details, Sign Out and close button in My Account dropdown
    When user clicks on "close" from My Account dropdown
    Then user should see My Account dropdown is closed
    
    And click on 'CHANGE' button of 'SHIPPING DETAILS' section on 'Review' page
    And User is on internal /checkout2/shipping.jsp page
    
    Then Verify embedded headers links is visible
    And click on MY ACCOUNT from header
    Then user should see My Account dropdown is opened
    Then user should see welcome message, My Details, Sign Out and close button in My Account dropdown
    When user clicks on "close" from My Account dropdown
    Then user should see My Account dropdown is closed
    
    And Presses continue button on shipping address
    And Verifies user is in shipping method page
    
    Then Verify embedded headers links is visible
    And click on MY ACCOUNT from header
    Then user should see My Account dropdown is opened
    Then user should see welcome message, My Details, Sign Out and close button in My Account dropdown
    When user clicks on "close" from My Account dropdown
    Then user should see My Account dropdown is closed
    
    And Clicks continue button on shipping method page
    And page url should contain /checkout2/shippingmethod.jsp
    
    Then Verify embedded headers links is visible
    And click on MY ACCOUNT from header
    Then user should see My Account dropdown is opened
    Then user should see welcome message, My Details, Sign Out and close button in My Account dropdown
    When user clicks on "close" from My Account dropdown
    Then user should see My Account dropdown is closed
    
    And Submits payment data in billing page
    And User is on internal /checkout2/billing.jsp page
    And Inputs credit card security code
    And User clicks on place your order button
    Then User should be in order confirmation page
    And verify order number is generated
    
    Then Verify embedded headers links is visible
    And click on MY ACCOUNT from header
    Then user should see My Account dropdown is opened
    Then user should see welcome message, My Details, Sign Out and close button in My Account dropdown
    When user clicks on "close" from My Account dropdown
    Then user should see My Account dropdown is closed
    
    Given User is on homepage
    And click on MY ACCOUNT from header
    When user clicks on "Sign Out" from My Account dropdown 
    And Verify user is in homepage
  
  Scenario: My Account dropdown open and close is functional on search page
    And click on SIGN IN from header
  	And User is on internal /r/login page
  	And User provides login information
    And Hits sign in button    
    Then User is in My Account home page
    
    Given User is on homepage
    And User presses search button
    When Enters Dresses to the search field
    And Clicks on search button for input field
    And User is in search results page
    
    And Verify MY ACCOUNT header link is displayed
    And click on MY ACCOUNT from header
    Then user should see My Account dropdown is opened
    Then user should see welcome message, My Details, Sign Out and close button in My Account dropdown
    When user clicks on "close" from My Account dropdown
    Then user should see My Account dropdown is closed
    
    Given User is on homepage
    And click on MY ACCOUNT from header
    When user clicks on "Sign Out" from My Account dropdown
    And Verify user is in homepage
