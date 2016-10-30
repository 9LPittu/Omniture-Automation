@QS
Feature: Quick Shop Basic

  Background:
    Given User goes to homepage
    And User closes email capture

  Scenario: View full detail link in QS is funcitonal
    When User hovers on women category from header
    And User selects sweaters subcategory array
    Then Verify user is in sweaters category array page

    When User selects random quick shop from product array
    Then Verify quick shop modal is displayed
    And Verify view full details link is displayed
