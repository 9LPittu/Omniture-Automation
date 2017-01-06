@Quickshop2 @HighLevel
Feature: Quick Shop full details,close links and variations are functional

  Background:
    Given User goes to homepage
    And User closes email capture

  Scenario: View full detail link in QS is functional
    When User hovers on a random category and subcategory from list
      |women|sweaters|
      |men|sweaters|
      |girls|dresses|
    Then Verify user is in category array page

    When User selects random quick shop from product array
    Then Verify quick shop modal is displayed
    And Selects random size
    And Verify view full details link is displayed in QS modal

    When User clicks on view full details link
    Then Verify product detail page is displayed
    And Verify product name on PDP matches with QS

  Scenario: Verify user able to change variations in quickshop
    When User hovers on a random category and subcategory from list
      |women|sweaters|
      |men|casual shirts|
    Then Verify user is in category array page

    When User selects random quick shop with variation product from product array
    Then Verify quick shop modal is displayed
    And Verify variations is displayed in QS modal
    And Verify user able to change variations

  Scenario: View Close(X) link in QS is functional
    When User hovers on a random category and subcategory from list
      |women|sweaters|
      |men|casual shirts|
      |girls|dresses|
    Then Verify user is in category array page

    When User selects random quick shop from product array
    Then Verify quick shop modal is displayed
    And Verify close is displayed in QS modal

    When User clicks on X icon
    Then Verify quick shop modal is not displayed
