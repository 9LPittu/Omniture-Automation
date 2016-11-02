@QS
Feature: Quick Shop Layout

  Background:
    Given User goes to homepage
    And User closes email capture

  Scenario: QS layout from category array page
    When User hovers on women category from header
    And User selects sweaters subcategory array
    Then Verify user is in sweaters category array page

    When User selects random quick shop from product array
    Then Verify quick shop modal is displayed
    And Verify product name on QS matches with category array
    And Verify view full details link is displayed
    And Verify color swatchs is displayed
    And Verify size chart is displayed
    And Verify size chips is displayed
    And Verify quantity is displayed
    And Verify Add to Bag is displayed
    And Verify Wishlist is displayed


  Scenario: Variation quickshop layout
    When User hovers on women category from header
    And User selects sweaters subcategory array
    Then Verify user is in sweaters category array page

    When User selects random quick shop with variation product from product array
    Then Verify quick shop modal is displayed
    And Verify product name on QS matches with category array
    And Verify view full details link is displayed
    And Verify variations is displayed
    And Verify color swatchs is displayed
    And Verify size chart is displayed
    And Verify size chips is displayed
    And Verify quantity is displayed
    And Verify Add to Bag is displayed
    And Verify Wishlist is displayed
