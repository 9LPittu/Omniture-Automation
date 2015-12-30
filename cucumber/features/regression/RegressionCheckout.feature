@RegressionCheckout
Feature: Checkout Process

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

  Scenario Outline: Guest checkout
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
    And items count should be displayed as 2 in the bag
    And Clicks on checkout        
    And page url should contain /checkout2/shoppingbag.jsp
    And click on CHECK OUT AS A GUEST button
    And enter first name as "<firstname>" on shipping address page
    And enter last name as "<lastname>" on shipping address page
    And enter address line1 as "<addressline1>"
    And enter address line2 as "<addressline2>"
    And enter zip code as "<zipcode>"
    And enter phone number as "<phonenumber>"
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
    |firstname|lastname|addressline1|addressline2|zipcode|phonenumber|
    |John|Lewis|904 Oak Gln||92168|123456789|
  
  Scenario: Registered user checkout
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