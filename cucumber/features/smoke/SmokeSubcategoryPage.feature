@Subcategory
Feature: Subcategory Page

  Background:
    Given User is on homepage
    And User clicks on hamburger menu
    And Selects Women Category from hamburger menu
    And User clicks on SWEATERS subcategory from Women Category
    And User should be in subcategory page

  Scenario: Subcategory Page functionality
    When User hovers a product
    Then Proper details are shown for the hovered product
