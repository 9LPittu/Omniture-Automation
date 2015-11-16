@Category
Feature: Category Page

  Scenario: Category page is functional
    Given User is on homepage
    And User clicks on hamburger menu
    And Selects Women Category from hamburger menu
    When Chooses a random subcategory
    Then User should be in subcategory page
    And View All Section is present and collapsed
    And User clicks on expand icon
    And Accordion should be expanded
    And Collapse icon is displayed
    And Chooses a random filter
    And filter becomes selected
    And Refine modal autocloses
    And Products displayed match subcategory

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