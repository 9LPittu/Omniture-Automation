@Checkout
Feature: Checkout Process

  Background:
    Given User is on homepage
    And User clicks on hamburger menu
    And Selects Women Category from hamburger menu
    And Selects Shirts and Tops from Women Category in hamburger menu
    And Selects the first product from product grid list
    And User is on a product detail page
    And A variation is selected
    And A color is selected
    And A size is selected
    And A wishlist button is present
    And Add to cart button is pressed
    And A minicart modal should appear with message '1 item has been added to your cart.'
    And Bag should have 1 item(s) added
    And User clicks on item bag
    And Verifies edit button is present
    And Verifies remove button is present
    And Verifies that total amount and subtotal values are similar
    And Clicks on checkout

  Scenario: Guest checkout Screenshot
    Given Selects to checkout as guest
    And Fills shipping address
    And Presses continue button on shipping address
    And Verifies is in shipping method page
    And Uses default value for shipping method
    And Uses default value for gifts option
    And Clicks continue button on shipping method page
    And Fills required payment data in billing page
    And Clicks on place your order
    Then User should be in order summary page

  Scenario: Registered Checkout Mobile Not Signed User Screenshot
    Given User provides username test1@test.net as and password as test1234
    And Clicks sign in and checkout
    Then Click save to wishlist and continue checkout if user is in merge bag page
    And Validates billing section is present in review page
    And Inputs credit card security code
    And Validates shipping section is present in review page
    And Clicks on place your order
    Then User should be in order summary page


