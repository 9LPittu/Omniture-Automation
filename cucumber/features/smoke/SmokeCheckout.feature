@Checkout
Feature: Checkout Process

  Background:
    Given User is on homepage
    And User clicks on hamburger menu
    And Selects Men Category from hamburger menu
    And Chooses a random subcategory
    And Selects any product from product grid list
    And User is in product detail page
    And A variation is selected
    And A color is selected
    And A size is selected
    And A wishlist button is present
    When Add to cart button is pressed
    Then A minicart modal should appear with message '1 item has been added to your cart.'
    And Bag should have 1 item(s) added

  Scenario: Guest checkout
    When User clicks on item bag
    Then Verifies edit button is present
    And Verifies remove button is present
    And Verifies that total amount and subtotal values are similar
    And Clicks on checkout

  Scenario: Registered Checkout Mobile Not Signed User
    When User clicks on item bag
    And Verifies edit button is present
    And Verifies remove button is present
    And Verifies that total amount and subtotal values are numbers
    And Clicks on checkout
