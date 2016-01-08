@RegressionCheckout
Feature: Registered User Checkout Process

  Background:
    #below steps removes items from the bag
    Given User is on homepage   
    And Goes to sign in page
    When User enters express_user@jcrew.com as email
    And User enters jcrew@123 as password
    And Hits sign in button
    And User bag is cleared
    
    #below steps deletes the non-default credit cards 
    And User is on homepage
    And User clicks on hamburger menu
	When User clicks on My Account link
    And click on "PAYMENT METHODS" link in My Account page
    And delete non-default credit cards
    And click on "SIGN OUT" link in My Account page
    
    #Add item to the bag
    And User is on homepage
    And User clicks on hamburger menu
    And Selects Men Category from hamburger menu
    And User clicks on SWEATERS subcategory from Men Category
    And Selects the first product from product grid list
    And User is in product detail page
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
    And page url should contain /checkout2/shoppingbag.jsp
    And enter email address as "express_user@jcrew.com" on sign in page    
    And enter password as "jcrew@123"
    And click on SIGN IN & CHECK OUT button
    And page url should contain /checkout2/signin.jsp
    And items count should be 2 on the review page
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
    And page url should contain /checkout2/shoppingbag.jsp
    And enter email address as "express_user@jcrew.com" on sign in page
    And enter password as "jcrew@123"
    And click on SIGN IN & CHECK OUT button
    And page url should contain /checkout2/signin.jsp
    And click on 'CHANGE' button of 'BILLING DETAILS' section on 'Review' page
    And click 'Add New Card' on billing page
    And enter "Visa_Card1" details on billing page
    And click on 'SAVE & CONTINUE' button
    And page url should contain /checkout2/billing.jsp
    And items count should be 2 on the review page
    Then Clicks on place your order
    Then User should be in order confirmation page
    And verify order number is generated