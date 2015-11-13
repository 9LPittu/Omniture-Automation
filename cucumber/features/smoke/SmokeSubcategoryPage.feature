@Subcategory
Feature: Subcategory Page

  Background:
    Given User is on homepage
    And User clicks on hamburger menu
    And Chooses a random category
    And Chooses a random subcategory

  Scenario: Subcategory Page functionality
    And User should be in subcategory page
    When User hovers a product
    Then Proper details are shown for the hovered product

  #Following Scenario was found to be an issue in CI, adding a test that will verify that it never happens again.
  Scenario: Zero size should be valid
    And User should be in subcategory page
    And Selects any product from product grid list
    And A color is selected
    And Size 0 is selected by user
    And Add to cart button is pressed
    Then A minicart modal should appear with message '1 item has been added to your cart.'