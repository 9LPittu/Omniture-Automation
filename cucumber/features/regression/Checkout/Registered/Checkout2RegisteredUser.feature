@RegressionCheckout @HighLevel
Feature: Checkout - Registered User Checkout Process

  Background:
    Given User is on homepage with clean session
    And Handle the Email Capture pop up
    
    And Goes to sign in page
    And User provides login information
    And Hits sign in button
    And User bag is cleared
    
    #below steps deletes the non-default credit cards 
    When User is on homepage
    And User clicks on hamburger menu
    
    When User clicks on My Account link    
    And User clicks on PAYMENT METHODS link in My Account Page
    And delete non-default credit cards
    And User clicks on SIGN OUT link in My Account Page
    And Verify user is in homepage
    And User is signed out
    
    #Add item to the bag
    When User is on homepage
    And User clicks on hamburger menu
    And user selects any category from hamburger menu
	And user selects any subcategory
	
	And user selects any item from array page, select any color and size
    Then User is in product detail page    
    And product name and price should match with array page
         
    When Add to cart button is pressed
    Then A minicart modal should appear with message '1 item has been added to your cart.'
    And Bag should have 1 item(s) added

  Scenario: Registered user checkout with no items in the bag
    And User is on homepage
    And User clicks on hamburger menu
    And user selects any category from hamburger menu
	And user selects any subcategory
	
	And user selects any item from array page, select any color and size
    Then User is in product detail page    
    And product name and price should match with array page
        
    When Add to cart button is pressed
    Then A minicart modal should appear with message '1 item has been added to your cart.'
    And click on checkout from minicart modal
    And page title should contain "Shopping Bag"
    
    And Move to mobile site
    
    Then make sure that subtotal is less than creditcard threshold
    And items count should be displayed as 2 in the bag
    
    And Clicks on checkout    
    And page url should contain /checkout2/shoppingbag.jsp
    
    And enter login information on sign in page
    And click on SIGN IN & CHECK OUT button
    And page url should contain /checkout2/signin.jsp
    And items count should be displayed as 2 in the bag
    And product name and price on review page should be displayed correctly
    
    And Inputs credit card security code
    And User clicks on place your order button
    Then User should be in order confirmation page
    And verify order number is generated
    And Deletes browser cookies
  
  Scenario: Registered user adding new credit card with existing address
    When User is on homepage
    And User clicks on hamburger menu
    And user selects any category from hamburger menu
	And user selects any subcategory
	
	And user selects any item from array page, select any color and size
    Then User is in product detail page
    And product name and price should match with array page    
    And Add to cart button is pressed
    
    When A minicart modal should appear with message '1 item has been added to your cart.'
    Then click on checkout from minicart modal
    And page title should contain "Shopping Bag"
    
    And Move to mobile site
    
    Then make sure that subtotal is less than creditcard threshold
    And items count should be displayed as 2 in the bag
    And Clicks on checkout
    And page url should contain /checkout2/shoppingbag.jsp
    And enter login information on sign in page
    And click on SIGN IN & CHECK OUT button
    And page url should contain /checkout2/signin.jsp
    
    And click on 'CHANGE' button of 'BILLING DETAILS' section on 'Review' page
    And click 'Add New Card' on billing page
    And enter "Visa_Card1" details on billing page
    And click on 'SAVE & CONTINUE' button
    And User is on external /checkout2/billing.jsp page
    And items count should be displayed as 2 in the bag
    And product name and price on review page should be displayed correctly
    
    And User clicks on place your order button
    Then User should be in order confirmation page
    And verify order number is generated
    And Deletes browser cookies