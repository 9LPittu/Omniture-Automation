@Category
Feature: Smoke Tests Category Page
  Background:
    Given User is on homepage
    And User clicks on hamburger menu
    And Selects Women Category from hamburger menu

  Scenario: Test Subcategory Page
    When User clicks on sweaters Subcategory from Women Category
    Then User should be in sweaters page for women
    And Category title for Sweaters should match below global promo