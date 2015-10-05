@Category
Feature: Category Page

  Scenario: Category page is functional
    Given User is on homepage
    And User clicks on hamburger menu
    And Selects WOMEN Category from hamburger menu
    And User clicks on SWEATERS subcategory from Women Category
    And User should be in sweaters page for women
    Then Category title for SWEATERS should match below global promo
    And View All Section is present and collapsed
    When User clicks on expand icon
    Then Accordion should be expanded
    And Collapse icon is displayed
    When Selects cardigans subcategory
    Then CARDIGANS option becomes selected
    And Refine modal autocloses
    And Products displayed are cardigans from sweaters category