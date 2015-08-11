@Category
Feature: Smoke Tests Category Page
  Background:
    Given User is on homepage
    And User clicks on hamburger menu
    And Selects Women Category from hamburger menu
    And User clicks on sweaters Subcategory from Women Category
    And User should be in sweaters page for women

  Scenario: Verify Subcategory Page
    Then Category title for Sweaters should match below global promo

  Scenario: Verify View All Option is collapsed
    Then View All Section is present and collapsed

  Scenario: Verify view all option expands
    Given User clicks on expand icon
    Then Accordion should be expanded
    And Collapse icon is displayed
    
  Scenario: Verify category sort
    Given User clicks on expand icon
    And Selects cardigans subcategory
    Then cardigans option becomes bold
    And Refine modal autocloses
    And Array page displays cardigans
    And Products displayed are from cardigans
    
    