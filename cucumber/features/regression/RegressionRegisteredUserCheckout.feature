@RegressionCheckout
Feature: Registered User Checkout Process

  Background:
    #below steps removes items from the bag
    Given User is on homepage   
    And Goes to sign in page
    When User enters testuser1@example.org as email
    And User enters test1234 as password
    And Hits sign in button
    And User bag is cleared
    
    #below steps deletes the non-default credit cards 
    And User is on homepage
    And User clicks on hamburger menu
	When User clicks on My Account link
    And User clicks on PAYMENT METHODS link in My Account Page
    And delete non-default credit cards
    And User clicks on SIGN OUT link in My Account Page
    
    #Add item to the bag
    And User is on homepage
    And User clicks on hamburger menu
    And Selects Men Category from hamburger menu
    And User clicks on SWEATERS subcategory from Men Category
    And Selects the first product from product grid list
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
    And Selects the first product from product grid list
    And User is in product detail page
    And product name and price should match with array page
    And A color is selected
    And A size is selected
    And Add to cart button is pressed
    And A minicart modal should appear with message '1 item has been added to your cart.'
    And click on checkout from minicart modal
    Then page title should contain "Shopping Bag"
    And Move to mobile site
    And breadcrumb should display "J.Crew"
    And items count should be displayed as 2 in the bag
    And Clicks on checkout
    And User is on /checkout2/shoppingbag.jsp page
    And enter email address as "express_user@jcrew.com" on sign in page    
    And enter password as "jcrew@123"
    And click on SIGN IN & CHECK OUT button
    And User is on /checkout2/signin.jsp page
    And items count should be 2 on the review page
    And product name and price on review page should be displayed correctly
    And Inputs credit card security code
    Then Clicks on place your order
    Then User should be in order confirmation page
    And verify order number is generated
  
  Scenario: Registered user adding new credit card with existing address
    And User is on homepage
    And User clicks on hamburger menu
    And Selects Women Category from hamburger menu
    And User clicks on BLAZERS subcategory from Women Category
    And Selects the first product from product grid list
    And User is in product detail page
    And product name and price should match with array page
    And A color is selected
    And A size is selected
    And Add to cart button is pressed
    And A minicart modal should appear with message '1 item has been added to your cart.'
    And click on checkout from minicart modal
    Then page title should contain "Shopping Bag"
    And Move to mobile site
    And breadcrumb should display "J.Crew"
    And items count should be displayed as 2 in the bag
    And Clicks on checkout
    And User is on /checkout2/shoppingbag.jsp page
    And enter email address as "express_user@jcrew.com" on sign in page
    And enter password as "jcrew@123"
    And click on SIGN IN & CHECK OUT button
    And User is on /checkout2/signin.jsp page
    And click on 'CHANGE' button of 'BILLING DETAILS' section on 'Review' page
    And click 'Add New Card' on billing page
    And enter "Visa_Card1" details on billing page
    And click on 'SAVE & CONTINUE' button
    And User is on /checkout2/billing.jsp page
    And items count should be 2 on the review page
    And product name and price on review page should be displayed correctly
    Then Clicks on place your order
    Then User should be in order confirmation page
    And verify order number is generated