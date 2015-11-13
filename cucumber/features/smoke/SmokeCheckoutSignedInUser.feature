@CheckoutSignedInUser
Feature: Checkout Process Signed in User

  Background:
    Given User is on homepage
    And Goes to sign in page
    And User provides login information
    And Hits sign in button
    And User is on homepage
    And User bag is cleared
    And User goes to homepage
    And User clicks on hamburger menu
    And Chooses a random category
    And Chooses a random subcategory
    And Selects any product from product grid list
    And User is in product detail page
    And A variation is selected
    And A color is selected
    And A size is selected
    And Add to cart button is pressed
    And A minicart modal should appear with message '1 item has been added to your cart.'
    And Bag should have item(s) added

  Scenario: Checkout signed in user
    When User clicks on item bag
    And Verifies edit button is present
    And Verifies remove button is present
    And Verifies that total amount and subtotal values are numbers
    And Clicks on checkout

  Scenario: Express Checkout
    When User goes to homepage
    And User clicks on item bag
    And Verifies edit button is present
    And Verifies remove button is present
    And Verifies that total amount and subtotal values are numbers
    And Clicks on checkout