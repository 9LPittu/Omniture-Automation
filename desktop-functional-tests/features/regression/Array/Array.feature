@Array
Feature: Array page functionality

  Background:
    Given User goes to homepage
    And User closes email capture

  Scenario: Category Array  - Display and Refinement
    When User opens menu
    And User selects women category from menu
    And User selects sweaters subcategory array
    Then Verify user is in sweaters category array page
    And Verify category contains items count
    And Verify refine dropdown displayed in array page
    And Verify refine dropdown text is VIEW ALL

    When User expands refine dropdown
    And Verify refine dropdown is open
    And Verify refine options matches available lists

    When User selects a random refinement option
    Then Verify refine dropdown is closed
    And Verify refinement option was selected
    And Verify category contains items count


  #Business is yet to take ca call on View All link in Top nav. Till then, this scenario will be commented
#  Scenario Outline: Verify that category links are displayed on top nav in the gender landing page
#    When User clicks on <gender> link from top nav
#    Then Verify that top nav options contains view all
#    And Verify that top nav contains less or equal to 8 options
#
#    When User clicks on view all link from top nav
#    Then Verify menu drawer is displayed
#    And Verify menu drawer title is <gender>
#
#    Examples:
#      | gender |
#      | women  |
#      | men    |
#      | girls  |
#      | boys   |