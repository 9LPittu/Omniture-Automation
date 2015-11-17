@PDP
Feature: Product Detail Page

  Scenario: Product Detail Page Validation
    Given User is on homepage
    And User clicks on hamburger menu
    And Selects Men Category from hamburger menu
    When User clicks on SWEATERS subcategory from Men Category
    And Selects the first product from product grid list
    Then User is in product detail page
    And A variation is selected
    And A color is selected
    And A size is selected
    And A wishlist button is present
    And Add to cart button is pressed
    And A minicart modal should appear with message '1 item has been added to your cart.'
    And Bag should have 1 item(s) added