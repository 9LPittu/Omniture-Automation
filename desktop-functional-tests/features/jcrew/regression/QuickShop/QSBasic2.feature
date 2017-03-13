@Quickshop7 @HighLevel
Feature: Quick Shop full details,close links and variations are functional2

  Background:
    Given User goes to homepage
    And User closes email capture

 
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
