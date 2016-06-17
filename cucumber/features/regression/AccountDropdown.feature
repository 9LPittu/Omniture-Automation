@AccountDropdown @HighLevel
Feature: Account Dropdown functionality

  Background:
    Given User is on homepage
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
  	And User presses back button
    
    And Selects to checkout as guest
    
    And Verify SIGN IN header link is displayed
  	And click on SIGN IN from header
  	And User is on internal /r/login page
  	And User presses back button
    
    And Fills shipping address    
    And Presses continue button on shipping address    
    And Verifies is in shipping method page
    
    And Verify SIGN IN header link is displayed
  	And click on SIGN IN from header
  	And User is on internal /r/login page
  	And User presses back button
    
    And Uses default value for shipping method
    And Uses default value for gifts option
    And Clicks continue button on shipping method page
    
    And Verify SIGN IN header link is displayed
  	And click on SIGN IN from header
  	And User is on internal /r/login page
  	And User presses back button
    
    And Fills required payment data in billing page
    And Submits payment data in billing page
    And Clicks on place your order
    And User should be in order confirmation page
    
    And Verify SIGN IN header link is displayed
  	And click on SIGN IN from header
  	And User is on internal /r/login page
  
  Scenario: Sign In link displayed in header and functional for search page
    And User presses search button
    When Enters Dresses to the search field
    And Clicks on search button for input field
    And User is in search results page
    
    And Verify SIGN IN header link is displayed
  	And click on SIGN IN from header
  	And User is on internal /r/login page  	
  
  Scenario Outline: Sign In link displayed in header and functional for sale page
    And User clicks on hamburger menu
    And Selects sale Category from hamburger menu
    And User is in sale landing page
    
    And Verify SIGN IN header link is displayed
  	And click on SIGN IN from header
  	And User is on internal /r/login page
  	And User presses back button
  	
  	And User clicks on sale department <SaleCategory>
    Then User is in Sale results page
    
    And Verify SIGN IN header link is displayed
  	And click on SIGN IN from header
  	And User is on internal /r/login page  	
  	Examples:
      |SaleCategory|
      |women|
      |men  |
      |boys |
      |girls|
   
  Scenario Outline: Sign In link displayed in header and functional on Department/Gender Landing Pages
    And User clicks on <gender> link from top nav
    
    And Verify SIGN IN header link is displayed
  	And click on SIGN IN from header
  	And User is on internal /r/login page
    Examples:
      |gender|
      |Women |
      |Men   |
      |Boys  |
      |Girls |
  
  Scenario: Sign In link displayed in header and functional on Shoppable tray page
    And User clicks on hamburger menu
    And User selects random tray from available categories
#      | Women | THIS MONTH'S FEATURES | looks we love  |
      | Men   | THIS MONTH'S FEATURES | 1 Suit, 5 Ways |
#      | Girls | THIS MONTH'S FEATURES | Looks We Love  |
#      | Boys  | THIS MONTH'S FEATURES | Looks We Love  |
    Then Verifies initial multiple pdp page state
    
    And Verify SIGN IN header link is displayed
  	And click on SIGN IN from header
  	And User is on internal /r/login page
  
  Scenario: My Account dropdown open and close is functional on multiple pages
  	And click on SIGN IN from header
  	And User is on internal /r/login page
  	And User provides login information
    And Hits sign in button
    
    Then User is in My Account page
    And Verify MY ACCOUNT header link is displayed
    When Clicks on JCrew Logo
    And Verify user is in homepage
    
    Then Verify embedded headers links is visible
    And click on MY ACCOUNT from header
    Then user should see My Account dropdown is opened
    Then user should see welcome message, My Details, Sign Out and close button in My Account dropdown
    When user clicks on "close" from My Account dropdown
    Then user should see My Account dropdown is closed
    
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
    And Verifies is in shipping method page
    
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
    Then Clicks on place your order
    Then User should be in order confirmation page
    And verify order number is generated
    
    Then Verify embedded headers links is visible
    And click on MY ACCOUNT from header
    Then user should see My Account dropdown is opened
    Then user should see welcome message, My Details, Sign Out and close button in My Account dropdown
    When user clicks on "close" from My Account dropdown
    Then user should see My Account dropdown is closed
    
  Scenario: My Account dropdown open and close is functional on search page
    And click on SIGN IN from header
  	And User is on internal /r/login page
  	And User provides login information
    And Hits sign in button    
    Then User is in My Account page
    
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
  
  Scenario Outline: My Account dropdown open and close is functional on sale page
  	And click on SIGN IN from header
  	And User is on internal /r/login page
  	And User provides login information
    And Hits sign in button    
    Then User is in My Account page
    
    And User clicks on hamburger menu
    And Selects sale Category from hamburger menu
    And User is in sale landing page
    
    And Verify MY ACCOUNT header link is displayed
    And click on MY ACCOUNT from header
    Then user should see My Account dropdown is opened
    Then user should see welcome message, My Details, Sign Out and close button in My Account dropdown
    When user clicks on "close" from My Account dropdown
    Then user should see My Account dropdown is closed
  	
  	And User clicks on sale department <SaleCategory>
    Then User is in Sale results page
    
    And Verify MY ACCOUNT header link is displayed
    And click on MY ACCOUNT from header
    Then user should see My Account dropdown is opened
    Then user should see welcome message, My Details, Sign Out and close button in My Account dropdown
    When user clicks on "close" from My Account dropdown
    Then user should see My Account dropdown is closed  	
  	Examples:
      |SaleCategory|
      |women|
      |men  |
      |boys |
      |girls|      
   
  Scenario Outline: My Account dropdown open and close is functional in Department/Gender Landing Pages
    And click on SIGN IN from header
  	And User is on internal /r/login page
  	And User provides login information
    And Hits sign in button    
    Then User is in My Account page
    
    When Clicks on JCrew Logo
    And User clicks on <gender> link from top nav
    
    And Verify MY ACCOUNT header link is displayed
    And click on MY ACCOUNT from header
    Then user should see My Account dropdown is opened
    Then user should see welcome message, My Details, Sign Out and close button in My Account dropdown
    When user clicks on "close" from My Account dropdown
    Then user should see My Account dropdown is closed
    
    And click on MY ACCOUNT from header
    When user clicks on "Sign Out" from My Account dropdown
    Examples:
      |gender|
      |Women |
      |Men   |
      |Boys  |
      |Girls |

  Scenario: My Account dropdown open and close is functional in Shoppable tray page
  	And click on SIGN IN from header
  	And User is on internal /r/login page
  	And User provides login information
    And Hits sign in button    
    Then User is in My Account page
    
    And User clicks on hamburger menu
    And User selects random tray from available categories
#      | Women | THIS MONTH'S FEATURES | looks we love  |
      | Men   | THIS MONTH'S FEATURES | 1 Suit, 5 Ways |
#      | Girls | THIS MONTH'S FEATURES | Looks We Love  |
#      | Boys  | THIS MONTH'S FEATURES | Looks We Love  |
    Then Verifies initial multiple pdp page state
    
    And Verify MY ACCOUNT header link is displayed
    And click on MY ACCOUNT from header
    Then user should see My Account dropdown is opened
    Then user should see welcome message, My Details, Sign Out and close button in My Account dropdown
    When user clicks on "close" from My Account dropdown
    Then user should see My Account dropdown is closed    
  
  Scenario: Verify tapping on My Details link under My Account dropdown should navigate user to My Account page    
    And click on SIGN IN from header
  	And User is on internal /r/login page
  	And User provides login information
    And Hits sign in button    
    Then User is in My Account page
    
    And click on MY ACCOUNT from header
    When user clicks on "My Details" from My Account dropdown
    And User is on internal /account/account_detail.jsp?sidecar=true page
    
    And User clicks on SIGN OUT link in My Account Page
    
    And Verify user is in homepage
    And Verify SIGN IN header link is displayed
  
  Scenario: Rewards visibility in Account dropdown
  	And click on SIGN IN from header
  	And User is on internal /r/login page
  	
  	And login with rewards user credentials
  	
  	And Hits sign in button
    Then User is in My Account page
    
    And click on MY ACCOUNT from header
    Then user should see welcome message, My Details, Sign Out and close button in My Account dropdown
    Then user should see rewards info in the My Account dropdown
    And user should see date, rewards balance, total points, points to next reward and Manage your account link in rewards section    
    When user clicks on "Manage your account" from My Account dropdown
    And external https://d.comenity.net/jcrew/ page is opened in a different tab
  
  Scenario: Sign out link from My Account dropdown is functional
  	And click on SIGN IN from header
  	And User is on internal /r/login page
  	And User provides login information
    And Hits sign in button    
    Then User is in My Account page
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
    Then User is in My Account page
    
    And Verify MY ACCOUNT header link is displayed
    And click on MY ACCOUNT from header
    Then user should see My Account dropdown is opened
    Then user should see welcome message, My Details, Sign Out and close button in My Account dropdown
    When user clicks on "close" from My Account dropdown
    Then user should see My Account dropdown is closed
    
    And click on MY ACCOUNT from header
    When user clicks on "My Details" from My Account dropdown
    And User is on internal /account/account_detail.jsp?sidecar=true page
    
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
    Then User is in My Account page
    
    And click on MY ACCOUNT from header
    Then user should see welcome message, My Details, Sign Out and close button in My Account dropdown
    Then user should NOT see rewards info in the My Account dropdown