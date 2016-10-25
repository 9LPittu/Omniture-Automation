@RegressionCheckout @HighLevel
Feature: Checkout - Guest User Checkout Process

  Background:
    Given User is on homepage with clean session
    And Handle the Email Capture pop up
    And User bag is cleared
    And User is on homepage

  Scenario Outline: Guest checkout with no items in the cart
    And User clicks on hamburger menu
    
    And user selects any category from hamburger menu
	And user selects any subcategory
	
	And user selects any item from array page, select any color and size
    Then User is in product detail page    
    And product name and price should match with array page    
    
    And Add to cart button is pressed
    Then A minicart modal should appear with message '1 item has been added to your cart.'
    And Bag should have 1 item(s) added
    
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
    Then page title should contain "Shopping Bag"
    And items count should be displayed as 2 in the bag
    
    And Move to mobile site
    
    Then make sure that subtotal is less than creditcard threshold
    
    And Clicks on checkout
    Then page url should contain /checkout2/shoppingbag.jsp
    And click on CHECK OUT AS A GUEST button
    
    And enter first name on shipping address page    
    And enter last name on shipping address page
    And enter address line1 as "<shipping_address1>" on shipping address page
    And enter address line2 as "<shipping_address2>" on shipping address page
    And enter zip code as "<shipping_zipcode>" on shipping address page
    And enter phone number on shipping address page
    
    And Presses continue button on shipping address
    
    And User is on internal /checkout2/shipping.jsp page
    Then Verifies user is in shipping method page
    
    When user select random shipping method on shipping & gift options page
    And Clicks continue button on shipping method page
    Then Verify user is in billing page
    
    When enter "Visa_Card" details on billing page
    And enter email address as "jcrewcolab@gmail.com"
    And Submits payment data in billing page
    Then User is on internal /checkout2/billing.jsp page
    And items count should be displayed as 2 in the bag
    
    Then product name and price on review page should be displayed correctly    
    
    When User clicks on place your order button
    Then User should be in order confirmation page
    And verify order number is generated
    Examples:
    |shipping_address1|shipping_address2|shipping_zipcode|
    |904 Oak Gln||92168|
  
  Scenario Outline: Guest checkout and adding new billing address
    And User clicks on hamburger menu
    
    And user selects any category from hamburger menu
	And user selects any subcategory
	
	And user selects any item from array page, select any color and size    
    Then User is in product detail page    
    
    When Add to cart button is pressed
    Then A minicart modal should appear with message '1 item has been added to your cart.'
    And Bag should have 1 item(s) added
    
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
    Then page title should contain "Shopping Bag"
    And items count should be displayed as 2 in the bag
    
    And Move to mobile site
    
    Then make sure that subtotal is less than creditcard threshold
    
    And Clicks on checkout
    Then page url should contain /checkout2/shoppingbag.jsp    
    And click on CHECK OUT AS A GUEST button
    
    And enter first name on shipping address page    
    And enter last name on shipping address page
    And enter address line1 as "<shipping_address1>" on shipping address page    
    And enter address line2 as "<shipping_address2>" on shipping address page
    And enter zip code as "<shipping_zipcode>" on shipping address page
    And enter phone number on shipping address page             
    And Presses continue button on shipping address
    
    Then user should see QAS verification in the shipping address page    
    And click on 'USE ADDRESS AS ENTERED' button in the shipping address page    
    
    And Verifies user is in shipping method page
    And user select random shipping method on shipping & gift options page
    And Clicks continue button on shipping method page
    
    Then Verify user is in billing page
    And enter "Visa_Card" details on billing page  
    And enter email address as "jcrewcolab@gmail.com"
    
    And click on 'ADD NEW BILLING ADDRESS' on Billing page
    And select country as "<billing_country>" in the Add New Billing Address form    
    And enter first name in the Add New Billing Address form    
    And enter last name in the Add New Billing Address form
    And enter "<billing_address1>" as address line1 in the Add New Billing Address form
    And enter "<billing_address2>" as address line2 in the Add New Billing Address form
    And enter "<billing_zipcode>" as zipcode in the Add New Billing Address form    
    And enter phone number in the Add New Billing Address form
    And click on 'SAVE' button in the Add New Billing Address form        
    
    Then user should see QAS verification in the Billing page    
    And click on 'USE ADDRESS AS ENTERED' button in the Billing page 
    
    And Submits payment data in billing page
    Then User is on internal /checkout2/billing.jsp page
    And items count should be displayed as 2 in the bag    
    And product name and price on review page should be displayed correctly
    
    When User clicks on place your order button
    Then User should be in order confirmation page
    And verify order number is generated  
    Examples:
    |shipping_address1|shipping_address2|shipping_zipcode|billing_country|billing_address1|billing_address2|billing_zipcode|
    |107-12 Continental Avenue||11375|United States|1 Pennsylvania Plaza||10119|