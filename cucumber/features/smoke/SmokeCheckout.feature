@Checkout
Feature: Checkout Process

  Background:
    Given User is on homepage
    And User clicks on hamburger menu
    And Selects Men Category from hamburger menu
    And User clicks on SWEATERS subcategory from Men Category
    And Click on product Marled lambswool sweater to display PDP
    And User is in product detail page
    And A variation is selected
    And A color is selected
    And A size is selected
    And A wishlist button is present
    And Add to cart button is pressed
    And A minicart modal should appear with message '1 item has been added to your cart.'


  Scenario: Guest checkout
    Given Bag should have 1 item(s) added
    And User clicks on item bag
    And Verifies edit button is present
    And Verifies remove button is present
    And Verifies that total amount and subtotal values are similar
    And Clicks on checkout

  Scenario: Registered Checkout Mobile Not Signed User
    Given Bag should have item(s) added
    And User clicks on item bag
    And Verifies edit button is present
    And Verifies remove button is present
    Given Verifies that total amount and subtotal values are numbers
    And Clicks on checkout
