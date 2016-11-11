@Quickshop @HighLevel
Feature: Quick Shop Layout

  Background:
    Given User goes to homepage
    And User closes email capture

  Scenario: QS layout from category array page
    When User hovers on a random category and subcategory from list
      |women|sweaters|
      |men|casual shirts|
      |girls|dresses|
    Then Verify user is in category array page

    When User selects random quick shop from product array
    Then Verify quick shop modal is displayed

    And Verify product name on QS matches with category array
    And Verify view full details link is displayed in QS modal
    And Verify color swatches is displayed in QS modal
    And Verify size chart is displayed in QS modal
    And Verify size chips is displayed in QS modal
    And Verify quantity is displayed in QS modal
    And Verify product details is displayed in QS modal
    And Verify product details drawer is closed by default
    And Verify Add to Bag is displayed in QS modal
    And Verify Wishlist is displayed in QS modal


  Scenario: Variation quickshop layout
    When User hovers on a random category and subcategory from list
      |women|sweaters|
      |men|casual shirts|
    Then Verify user is in category array page

    When User selects random quick shop with variation product from product array
    Then Verify quick shop modal is displayed

    And Verify product name on QS matches with category array
    And Verify view full details link is displayed in QS modal
    And Verify variations is displayed in QS modal
    And Verify color swatches is displayed in QS modal
    And Verify size chart is displayed in QS modal
    And Verify size chips is displayed in QS modal
    And Verify quantity is displayed in QS modal
    And Verify product details is displayed in QS modal
    And Verify product details drawer is closed by default
    And Verify Add to Bag is displayed in QS modal
    And Verify Wishlist is displayed in QS modal