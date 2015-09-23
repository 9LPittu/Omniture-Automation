@PDP
Feature: Smoke Tests Product Detail Page

  Background:
    Given User is on homepage
    And User clicks on hamburger menu
    And Selects WOMEN Category from hamburger menu
    And User clicks on SWEATERS subcategory from Women Category
    And Selects the first product from product grid list

  Scenario: Product Detail Page Validation
    Given User is in product detail page
    And A variation is selected
    And A color is selected
    And A size is selected
    And A wishlist button is present
    When Add to cart button is pressed
    Then A minicart modal should appear with message '1 item has been added to your cart.'
    Then Bag should have 1 item(s) added