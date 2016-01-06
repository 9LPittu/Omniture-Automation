@RegressionCheckout
Feature: Guest User Checkout Process

  Background:
    Given User is on homepage
    And User bag is cleared
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

  Scenario Outline: Guest checkout with no items in the cart
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
    And breadcrumb should display "J.Crew"    
    And items count should be displayed as 2 in the bag
    And Clicks on checkout
    And page url should contain /checkout2/shoppingbag.jsp
    And click on CHECK OUT AS A GUEST button
    And enter first name on shipping address page    
    And enter last name on shipping address page
    And enter address line1 as "<shipping_address1>" on shipping address page
    And enter address line2 as "<shipping_address2>" on shipping address page
    And enter zip code as "<shipping_zipcode>" on shipping address page
    And enter phone number on shipping address page
    And Presses continue button on shipping address
    And page url should contain /checkout2/shipping.jsp
    And Verifies is in shipping method page
    And select shipping method on shipping & gift options page
    And Clicks continue button on shipping method page
    And Verify user is in billing page
    And enter "Visa_Card" details on billing page
    And enter email address as "jcrewcolab@gmail.com"
    And Submits payment data in billing page
    And page url should contain /checkout2/billing.jsp
    And items count should be 2 on the review page
    Then Clicks on place your order
    Then User should be in order confirmation page
    And verify order number is generated
    Examples:
    |shipping_address1|shipping_address2|shipping_zipcode|
    |904 Oak Gln||92168|
  
  Scenario Outline: Guest checkout and adding new billing address
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
    And breadcrumb should display "J.Crew"
    And items count should be displayed as 2 in the bag
    And Clicks on checkout
    And page url should contain /checkout2/shoppingbag.jsp
    And click on CHECK OUT AS A GUEST button
    And enter first name on shipping address page    
    And enter last name on shipping address page
    And enter address line1 as "<shipping_address1>" on shipping address page    
    And enter address line2 as "<shipping_address2>" on shipping address page
    And enter zip code as "<shipping_zipcode>" on shipping address page
    And enter phone number on shipping address page             
    And Presses continue button on shipping address
    Then user should see QAS verification window in the shipping address page
    And click on 'USE ADDRESS AS ENTERED' button in the shipping address page    
    And Verifies is in shipping method page
    And select shipping method on shipping & gift options page
    And Clicks continue button on shipping method page
    And Verify user is in billing page
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
    Then click on 'SAVE' button in the Add New Billing Address form        
    Then user should see QAS verification window in the Billing page
    And click on 'USE ADDRESS AS ENTERED' button in the Billing page 
    And Submits payment data in billing page
    And page url should contain /checkout2/billing.jsp
    And items count should be 2 on the review page
    Then Clicks on place your order
    Then User should be in order confirmation page
    And verify order number is generated  
    Examples:
    |shipping_address1|shipping_address2|shipping_zipcode|billing_country|billing_address1|billing_address2|billing_zipcode|
    |107-12 Continental Avenue||11375|United States|1 Pennsylvania Plaza||10119|