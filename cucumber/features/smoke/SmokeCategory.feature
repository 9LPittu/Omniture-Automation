@Category
Feature: Category Page

  Scenario: Sub category page is functional
    Given User is on homepage
    And User clicks on hamburger menu
    And Selects Women Category from hamburger menu
    When User clicks on SWEATERS subcategory from Women Category
    Then User should be in subcategory page

  Scenario: Filter for subcategory works
    Given User is on homepage
    And User clicks on hamburger menu
    And Selects Women Category from hamburger menu
    When User clicks on SWEATERS subcategory from Women Category
    And View All Section is present and collapsed
    And User clicks on expand icon
    And Accordion should be expanded
    And Collapse icon is displayed
    And Chooses a random filter
    And filter becomes selected
    And Refine modal autocloses
    And Products displayed match subcategory
