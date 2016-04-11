@RegressionCheckout
Feature: Registered User Checkout Process

  Background:
    #below steps removes items from the bag
    Given User is on homepage with clean session
    And Handle the Email Capture pop up
    And Goes to sign in page
    And User provides login information
    And Hits sign in button
    And User bag is cleared
    
    #below steps deletes the non-default credit cards 
    And User is on homepage
    And User clicks on hamburger menu
    When User clicks on My Account link
    And User clicks on PAYMENT METHODS link in My Account Page
    And delete non-default credit cards
    And User clicks on SIGN OUT link in My Account Page
    And Verify user is in homepage
    And User is signed out
    
    #Add item to the bag
    And User is on homepage
    And User clicks on hamburger menu
    And Selects Men Category from hamburger menu
    And User clicks on SWEATERS subcategory from Men Category
    And Selects the first product with regular price from product grid list
    And User is in product detail page
    And product name and price should match with array page
    And A color is selected
    And A size is selected    
    And Add to cart button is pressed
    And A minicart modal should appear with message '1 item has been added to your cart.'
    And Bag should have 1 item(s) added

  Scenario: Registered user checkout with no items in the bag
    And User is on homepage
    And User clicks on hamburger menu
    And Selects Women Category from hamburger menu
    And User clicks on BLAZERS subcategory from Women Category
    And Selects the first product with regular price from product grid list
    And User is in product detail page
    And product name and price should match with array page
    And A color is selected
    And A size is selected
    And Add to cart button is pressed
    And A minicart modal should appear with message '1 item has been added to your cart.'
    And click on checkout from minicart modal
    Then page title should contain "Shopping Bag"
    And Move to mobile site
    And items count should be displayed as 2 in the bag
    And Clicks on checkout
    And page url should contain /checkout2/shoppingbag.jsp
    And enter any email address on sign in page
    And enter corresponding password
    And click on SIGN IN & CHECK OUT button
    And page url should contain /checkout2/signin.jsp
    And items count should be 2 on the review page
    And product name and price on review page should be displayed correctly
    And Inputs credit card security code
    Then Clicks on place your order
    Then User should be in order confirmation page
    And verify order number is generated
    And Deletes browser cookies
  
  Scenario: Registered user adding new credit card with existing address
    And User is on homepage
    And User clicks on hamburger menu
    And Selects Women Category from hamburger menu
    And User clicks on BLAZERS subcategory from Women Category
    And Selects the first product with available colors and regular price from product grid list
    And User is in product detail page
    And product name and price should match with array page
    And A color is selected
    And A size is selected
    And Add to cart button is pressed
    And A minicart modal should appear with message '1 item has been added to your cart.'
    And click on checkout from minicart modal
    Then page title should contain "Shopping Bag"
    And Move to mobile site
    And items count should be displayed as 2 in the bag
    And Clicks on checkout
    And page url should contain /checkout2/shoppingbag.jsp
    And enter any email address on sign in page
    And enter corresponding password
    And click on SIGN IN & CHECK OUT button
    And page url should contain /checkout2/signin.jsp
    And click on 'CHANGE' button of 'BILLING DETAILS' section on 'Review' page
    And click 'Add New Card' on billing page
    And enter "Visa_Card1" details on billing page
    And click on 'SAVE & CONTINUE' button
    And User is on external /checkout2/billing.jsp page
    And items count should be 2 on the review page
    And product name and price on review page should be displayed correctly
    Then Clicks on place your order
    Then User should be in order confirmation page
    And verify order number is generated
    And Deletes browser cookies