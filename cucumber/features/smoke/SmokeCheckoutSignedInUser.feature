#@CheckoutSignedInUser
Feature: Checkout Process Signed in User

  Background:
    Given User is on homepage
    And Goes to sign in page
    When User provides login information
    And Hits sign in button
    And User goes to homepage
    And User clicks on hamburger menu
    And Selects Men Category from hamburger menu
    And User clicks on SWEATERS subcategory from Men Category
    And Click on product Rugged cotton sweatshirt sweater to display PDP
    And User is in product detail page
    And A variation is selected
    And A color is selected
    And A size is selected
    And A wishlist button is present
    And Add to cart button is pressed
    And A minicart modal should appear with message '1 item has been added to your cart.'
    And Bag should have item(s) added

  Scenario: Checkout signed in user
    Given User clicks on item bag
    And Verifies edit button is present
    And Verifies remove button is present
    And Verifies that total amount and subtotal values are numbers
    And Clicks on checkout
#    And Validates billing section is present in review page
#    And Inputs credit card security code
#    And Validates shipping section is present in review page
#    And Clicks on place your order
#    Then User should be in order summary page


  Scenario: Express Checkout
    Given User goes to homepage
    And User clicks on item bag
    And Verifies edit button is present
    And Verifies remove button is present
    And Verifies that total amount and subtotal values are numbers
    And Clicks on checkout
#    And Validates billing section is present in review page
#    And Inputs credit card security code
#    And Validates shipping section is present in review page
#    And Clicks on place your order
#    Then User should be in order summary page