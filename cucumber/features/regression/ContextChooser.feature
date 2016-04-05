@HeaderAndFooter
Feature: Context Chooser Validations

  Background:

    Given User is on homepage with clean session
    And Handle the Email Capture pop up
    And Goes to sign in page
    When User enters usautomation1@jcrew.com as email
    And User enters jcrew@123 as password
    And Hits sign in button
    And User bag is cleared
   
  Scenario: Currency Symbol check for all pages for Registered user checkout
    And User is on homepage
    And User clicks on hamburger menu
    And Selects Women Category from hamburger menu
    And User clicks on BLAZERS subcategory from Women Category
    And Verify proper currency symbol is displayed on product grid list
    And Selects the first product with regular price from product grid list
    And User is in product detail page
    And Verify proper currency symbol is displayed on PDP page
    And product name and price should match with array page
    And A color is selected
    And A size is selected
    And Add to cart button is pressed
    And A minicart modal should appear with message '1 item has been added to your cart.'
    And click on checkout from minicart modal
    Then page title should contain "Shopping Bag"
    And Move to mobile site
   	And Verify proper currency symbol is displayed on item section on Checkout page
    And Verify proper currency symbol is displayed on summary section on Checkout page
    And Clicks on checkout
    And click on 'CHANGE' button of 'SHIPPING DETAILS' section on 'Review' page
    And Verify proper currency symbol is displayed on item section on Checkout page
    And Verify proper currency symbol is displayed on summary section on Checkout page
    And Presses continue button on shipping address
    And Verify proper currency symbol is displayed on item section on Checkout page
    And Verify proper currency symbol is displayed on summary section on Checkout page
    And Verify proper currency symbol is displayed on shipping method section on Checkout page
    And Uses default value for shipping method
    And Uses default value for gifts option
    And Clicks continue button on shipping method page
    And Verify proper currency symbol is displayed on item section on Checkout page
    And Verify proper currency symbol is displayed on summary section on Checkout page
    And Verify proper currency symbol is displayed on shipping method section on Checkout page
   	And Presses continue button on Billing page
    And Inputs credit card security code
    And Verify proper currency symbol is displayed on item section on Checkout page
    And Verify proper currency symbol is displayed on summary section on Checkout page
    And Verify proper currency symbol is displayed on shipping method section on Checkout page
    Then Clicks on place your order
    And Verify proper currency symbol is displayed on item section on Checkout page
    And Verify proper currency symbol is displayed on summary section on Checkout page
    And Verify proper currency symbol is displayed on shipping method section on Checkout page
    Then User should be in order confirmation page
    And verify order number is generated
    And Deletes browser cookies