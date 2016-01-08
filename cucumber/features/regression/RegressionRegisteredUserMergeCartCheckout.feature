@RegressionCheckout
Feature: Registered User Checkout Process - merge cart and adding new shipping address

  Background:
    #below steps removes items from the bag
    Given User is on homepage   
    And Goes to sign in page
    When User enters express_user@jcrew.com as email
    And User enters jcrew@123 as password
    And Hits sign in button
    And User bag is cleared
    
    #below steps deletes the non-default addresses 
    And User is on homepage
    And User clicks on hamburger menu
	When User clicks on My Account link
	And click on "ADDRESS BOOK" link in My Account page
	And delete non-default addresses
	
	#below steps deletes the non-default credit cards
    And click on "PAYMENT METHODS" link in My Account page
    And delete non-default credit cards
    
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
	And User is on homepage
    And User clicks on hamburger menu
	When User clicks on My Account link
	And click on "SIGN OUT" link in My Account page

  Scenario Outline: Registered user checkout with no items in the bag
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
    And items count should be displayed as 1 in the bag
    And Clicks on checkout
    And page url should contain /checkout2/shoppingbag.jsp
    And enter email address as "express_user@jcrew.com" on sign in page    
    And enter password as "jcrew@123"
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
    And validate shipping methods displayed on the page    
    And select shipping method on shipping & gift options page
    And Clicks continue button on shipping method page    
    And Submits payment data in billing page
    And page url should contain /checkout2/billing.jsp
    And items count should be 2 on the review page
    And Inputs credit card security code
    Then Clicks on place your order
    Then User should be in order confirmation page
    And verify order number is generated
    Examples:
    |address1|address2|zipcode|
    |107-12 Continental Avenue||11375|