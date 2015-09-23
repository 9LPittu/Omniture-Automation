@Subcategory
Feature: Smoke Tests Subcategory Page

  Background:
    Given User is on homepage
    And User clicks on hamburger menu
    And Selects WOMEN Category from hamburger menu
    And User clicks on SWEATERS subcategory from Women Category


  Scenario: Category Link
    And User should be in sweaters page for women

  Scenario: Product List Details
    And User should be in sweaters page for women
    And User hovers a product
    Then Proper details are shown for the hovered product


    #Following Scenario was found to be an issue in CI, adding a test that will verify that it never happens again.
  Scenario: Zero size should be valid
    And User should be in sweaters page for women
    Then Click on product Indigo gauze popover to display PDP
    And A color is selected
    And Size 0 is selected by user
    Then Add to cart button is pressed
    And A minicart modal should appear with message '1 item has been added to your cart.'