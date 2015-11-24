@Category
Feature: Category Page

  Background:
    Given User is on homepage
    And User clicks on hamburger menu
    And Selects Women Category from hamburger menu

  Scenario: Sub category page is functional
    When User clicks on SWEATERS subcategory from Women Category
    Then User should be in subcategory page

  Scenario: Filter for subcategory works
    When User clicks on SWEATERS subcategory from Women Category
    Then View All Section is present and collapsed
    When User clicks on expand icon
    Then Accordion should be expanded
    And Collapse icon is displayed
    When Chooses a random filter
    Then filter becomes selected
    And Refine modal autocloses
    And Products displayed match subcategory
