@RegressionCheckout @HighLevel
Feature: Registered User Checkout Process - merge cart and adding new shipping address

  Background:    
    Given User is on homepage with clean session
    And Handle the Email Capture pop up    
    And Goes to sign in page
    And User provides login information
    And Hits sign in button
    And User bag is cleared

    And User is on homepage
    And User clicks on hamburger menu
    When User clicks on My Account link

    #below steps deletes the non-default credit cards
    And click on "PAYMENT METHODS" link in My Account page
    And delete non-default credit cards

    #below steps deletes the non-default addresses
    And click on "ADDRESS BOOK" link in My Account page
    And delete non-default addresses
    
    And User is on homepage
    And User clicks on hamburger menu
    And user selects any category from hamburger menu
	And user selects any subcategory
	And user selects any item from array page, select any color and size
    And User is in product detail page
    And product name and price should match with array page
    And Add to cart button is pressed
    And A minicart modal should appear with message '1 item has been added to your cart.'
    And Bag should have 1 item(s) added
    And User is on homepage
    And User clicks on hamburger menu
    When User clicks on My Account link
    And click on "SIGN OUT" link in My Account page
    And Verify user is in homepage
    And User is signed out

  Scenario Outline: Registered user checkout with no items in the bag
    And User is on homepage
    And User clicks on hamburger menu
    And user selects any category from hamburger menu
	And user selects any subcategory
	And user selects any item from array page, select any color and size
    And User is in product detail page
    And product name and price should match with array page    
    And Add to cart button is pressed
    And A minicart modal should appear with message '1 item has been added to your cart.'
    And click on checkout from minicart modal
    Then page title should contain "Shopping Bag"
    And Move to mobile site
    And items count should be displayed as 1 in the bag
    And Clicks on checkout
    And page url should contain /checkout2/shoppingbag.jsp
    And enter login information on sign in page    
    And click on SIGN IN & CHECK OUT button
    And page url should contain /checkout2/signin.jsp
    And user should see 'SAVE TO WISHLIST & CONTINUE' button on the page
    And user should see 'ADD ITEMS TO BAG & REVIEW ORDER' button on the page
    And click on ADD ITEMS TO BAG & REVIEW ORDER button
    And page url should contain /checkout2/mergebags.jsp
    And Clicks on checkout    
    And Bag should have 2 item(s) added
    And click on 'CHANGE' button of 'SHIPPING DETAILS' section on 'Review' page
    And click on 'ADD NEW SHIPPING ADDRESS' on Shipping Address page
    And page url should contain /checkout2/shipping.jsp
    And enter first name in the Add New Shipping Address form        
    And enter last name in the Add New Shipping Address form
    And enter "<address1>" as address line1 in the Add New Shipping Address form
    And enter "<address2>" as address line2 in the Add New Shipping Address form
    And enter "<zipcode>" as zipcode in the Add New Shipping Address form
    And enter phone number in the Add New Shipping Address form
    Then click on 'SAVE & CONTINUE' button in the Add New Shipping Address form
    Then user should see QAS verification in the shipping address page    
    And click on 'USE ADDRESS AS ENTERED' button in the shipping address page
    And page url should contain /checkout2/shippingmethod.jsp
    And Verifies is in shipping method page
    And validate correct shipping methods displayed on the page
    And select shipping method on shipping & gift options page
    And Clicks continue button on shipping method page    
    And Submits payment data in billing page
    And page url should contain /checkout2/billing.jsp
    And items count should be 2 on the review page
    And product name and price on review page should be displayed correctly
    And Inputs credit card security code
    Then Clicks on place your order
    Then User should be in order confirmation page
    And verify order number is generated
    Examples:
    |address1|address2|zipcode|
    |107-12 Continental Avenue||11375|