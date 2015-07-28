Feature: Smoke Tests Product Detail Page

  Background:
    Given User is on homepage
    And User clicks on hamburger menu
    And Selects Women Category from hamburger menu
    And Selects Shirts and Tops from Women Category in hamburger menu
    And Selects the first product from product grid list

  Scenario: Product Detail Page Validation
    Given User is on a product detail page
    And A variation is selected
    And A color is selected
    And A size is selected
    And A wishlist button is present
    When Add to cart button is pressed
    Then A minicart modal should appear
    Then Bag should have 1 item(s) added