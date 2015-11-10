@Category
Feature: Category Page

  Scenario: Category page is functional
    Given User is on homepage
    And User clicks on hamburger menu
    And Selects Women Category from hamburger menu
    When User clicks on SWEATERS subcategory from Women Category
    Then User should be in sweaters page for women
    And Category title for SWEATERS should match below global promo
    And View All Section is present and collapsed
    And User clicks on expand icon
    And Accordion should be expanded
    And Collapse icon is displayed
    And Selects cardigans subcategory
    And CARDIGANS option becomes selected
    And Refine modal autocloses
    And Products displayed are cardigans from sweaters category

# TODO: This is how the feature could look to avoid having UI details at this level
#   Scenario: Categoy page is functional
#    Given User is on homepage
#    When User selects SWEATERS for Women
#    Then SWEATERS subcategory page is displayed properly
#
#   Scenario: Change to another category
#     Given User is on homepage
#     And User selects SWEATERS for Women
#     When User refines by cardigans
#     Then cardigans products are displayed