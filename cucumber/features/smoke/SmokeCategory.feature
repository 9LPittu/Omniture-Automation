@Category
Feature: Category and Sub Category Page Validations

  Background:
    Given User is on homepage
    And User clicks on hamburger menu
    And Selects Women Category from hamburger menu

  Scenario: subcategory page validations
    When User clicks on SWEATERS subcategory from Women Category
    Then User should be in subcategory page
    And Handle the Email Capture pop up
    Then Viewing filter is present
    And Accordion should be collapsed
    When User clicks on expand icon
    Then Accordion should be expanded
    When Chooses a random filter
    Then filter becomes selected
    And Refine modal autocloses
    And Products displayed match subcategory
    When User hovers a product
    Then Verifies product information is displayed
