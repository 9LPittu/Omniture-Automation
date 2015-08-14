@Checkout
Feature: Checkout Process

  Scenario: Checkout signed in user
    Given User is on homepage
    And Goes to sign in page
    When User enters test_register_user@test.com as email
    And User enters test1234 as password
    And Hits sign in button
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
    And Validates billing section is present in review page
    And Inputs credit card security code
    And Validates shipping section is present in review page
    And Clicks on place your order
    Then User should be in order summary page