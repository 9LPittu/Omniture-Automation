@Quickshop @HighLevel
Feature: Quick Shop Messaging

  Background:
    Given User goes to homepage
    And User closes email capture

  Scenario: Verify Error message on Quick shop if size and color not selected and adds to bag
    When User hovers on a random category and subcategory from list
      |women|sweaters|
      |men|casual shirts|
      |girls|dresses|
    Then Verify user is in category array page

    When User selects random quick shop from product array
    Then Verify quick shop modal is displayed

    When User clicks on Add to Bag button
    Then Verify PLEASE SELECT A SIZE message displayed
    And Verify Add to Bag button is in disabled state
    And Verify Wishlist button is in disabled state

  Scenario: Verify Error message on Quick shop if size and color not selected and adds to wishlist
    When User hovers on a random category and subcategory from list
      |women|sweaters|
      |men|casual shirts|
      |girls|dresses|
    Then Verify user is in category array page

    When User selects random quick shop from product array
    Then Verify quick shop modal is displayed

    When User clicks on Wishlist button
    Then Verify PLEASE SELECT A SIZE message displayed
    And Verify Add to Bag button is in disabled state
    And Verify Wishlist button is in disabled state

