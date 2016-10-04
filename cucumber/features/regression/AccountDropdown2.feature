@AccountDropdown2 @HighLevel
Feature: Account Dropdown functionality_2

  Background:
    Given User is on homepage with clean session
    And Handle the Email Capture pop up
  
  Scenario: My Account dropdown open and close is functional on sale page
  	And click on SIGN IN from header
  	And User is on internal /r/login page
  	And User provides login information
    And Hits sign in button    
    Then User is in My Account home page
    
    And User clicks on hamburger menu
    And Selects sale Category from hamburger menu
    And User is in sale landing page
    
    And Verify MY ACCOUNT header link is displayed
    And click on MY ACCOUNT from header
    Then user should see My Account dropdown is opened
    Then user should see welcome message, My Details, Sign Out and close button in My Account dropdown
    When user clicks on "close" from My Account dropdown
    Then user should see My Account dropdown is closed
  	
  	And user clicks on random sale department from below list
  	   |women|
       |men  |
       |boys |
       |girls|
    Then User is in Sale results page
    
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
  
  Scenario: My Account dropdown open and close is functional in Department/Gender Landing Pages
    And click on SIGN IN from header
  	And User is on internal /r/login page
  	And User provides login information
    And Hits sign in button    
    Then User is in My Account home page
    
    Given User is on homepage
    And user clicks on random top nav gender link from below list    
    	 |Women |
         |Men   |
         |Boys  |
         |Girls |
    
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

#  Scenario: My Account dropdown open and close is functional in Shoppable tray page
#  	And click on SIGN IN from header
#  	And User is on internal /r/login page
#  	And User provides login information
#    And Hits sign in button    
#    Then User is in My Account home page
#    
#    And User clicks on hamburger menu
#    And User selects random tray from available categories
#      | Women | THIS MONTH'S FEATURES | looks we love  |
#      | Men   | THIS MONTH'S FEATURES | 1 Suit, 5 Ways |
#      | Girls | THIS MONTH'S FEATURES | Looks We Love  |
#      | Boys  | THIS MONTH'S FEATURES | Looks We Love  |
#    Then Verifies initial multiple pdp page state
#    
#    And Verify MY ACCOUNT header link is displayed
#    And click on MY ACCOUNT from header
#    Then user should see My Account dropdown is opened
#    Then user should see welcome message, My Details, Sign Out and close button in My Account dropdown
#    When user clicks on "close" from My Account dropdown
#    Then user should see My Account dropdown is closed
#    
#    Given User is on homepage
#    And click on MY ACCOUNT from header
#    When user clicks on "Sign Out" from My Account dropdown
#    And Verify user is in homepage 
  
  Scenario: Verify tapping on My Details link under My Account dropdown should navigate user to My Account page    
    And click on SIGN IN from header
  	And User is on internal /r/login page
  	And User provides login information
    And Hits sign in button    
    Then User is in My Account home page
    
    And click on MY ACCOUNT from header
    When user clicks on "My Details" from My Account dropdown
    And User should be in /r/account/details menu link page
    And User presses back button
    And User clicks on SIGN OUT link in My Account Page
    
    And Verify user is in homepage
    And Verify SIGN IN header link is displayed

  Scenario: Rewards visibility in Account dropdown
  	And click on SIGN IN from header
  	And User is on internal /r/login page
  	
  	And login with rewards user credentials
  	
  	And Hits sign in button
    Then User is in My Account home page
    
    And click on MY ACCOUNT from header
    Then user should see welcome message, My Details, Sign Out and close button in My Account dropdown
    Then user should see rewards info in the My Account dropdown
    And user should see date, rewards balance, total points, points to next reward and Manage your account link in rewards section    
    When user clicks on "Manage your account" from My Account dropdown
    And J.crew credit card https://d.comenity.net/jcrew/ page is opened in a different tab    
    
    And click on MY ACCOUNT from header
    When user clicks on "Sign Out" from My Account dropdown
    And Verify user is in homepage
  
  Scenario: Sign out link from My Account dropdown is functional
  	And click on SIGN IN from header
  	And User is on internal /r/login page
  	And User provides login information
    And Hits sign in button    
    Then User is in My Account home page
    And User bag is cleared
    
    When User clicks on hamburger menu
    And user selects any category from hamburger menu
    And user selects any subcategory 
    And user selects any item from array page, select any color and size
    And User is in product detail page
    And Add to cart button is pressed
    
    When user navigates to random page from below list    
    |Category|
    |PDP|
    |Search|
    |Sale|
    |MyAccount|
    |Women|
    |Men|
    |Girls|
    |Boys|
    |Women_Feature|
    |ShoppingBag|
    |ShippingAddress|
    |ShippingMethod|
    |Billing|
    |Review|
  
    And click on MY ACCOUNT from header
    When user clicks on "Sign Out" from My Account dropdown  
    And Verify user is in homepage
    And Verify SIGN IN header link is displayed
  
  Scenario: Sign In and My Account dropdown functionality for international context
  	Then click on change link from footer
    And User is on context chooser page
    And User is on internal /r/context-chooser page
    
  	When user selects randomly an international country  	
  	Then user should land on country specific home page
    And user should see selected country in the footer
    
    And Verify SIGN IN header link is displayed
  	And click on SIGN IN from header
  	And User is on internal /r/login page
  	And User presses back button
  	
  	And click on SIGN IN from header
  	And User provides login information
    And Hits sign in button    
    Then User is in My Account home page
    
    And Verify MY ACCOUNT header link is displayed
    And click on MY ACCOUNT from header
    Then user should see My Account dropdown is opened
    Then user should see welcome message, My Details, Sign Out and close button in My Account dropdown
    When user clicks on "close" from My Account dropdown
    Then user should see My Account dropdown is closed
    
    Given User is on international homepage    
    And click on MY ACCOUNT from header
    When user clicks on "My Details" from My Account dropdown
    And User should be in /r/account/details menu link page

    
    And click on MY ACCOUNT from header
    When user clicks on "Sign Out" from My Account dropdown  
    And Verify user is in homepage
    And Verify SIGN IN header link is displayed
  
  Scenario: Rewards should not be shown in Account dropdown for international country
  	Then click on change link from footer
    And User is on context chooser page
    And User is on internal /r/context-chooser page
    
  	When user selects randomly an international country
  	Then user should land on country specific home page
    And user should see selected country in the footer
  
  	And click on SIGN IN from header
  	And User is on internal /r/login page
  	
  	And login with rewards user credentials
  	
  	And Hits sign in button
    Then User is in My Account home page
    
    And click on MY ACCOUNT from header
    Then user should see welcome message, My Details, Sign Out and close button in My Account dropdown
    Then user should NOT see rewards info in the My Account dropdown