@QS
Feature: Quick Shop Layout

  Background:
    Given User goes to homepage
    And User closes email capture

  Scenario: QS layout
    When User hovers on women category from header
    And User selects sweaters subcategory array
    Then Verify user is in sweaters category array page

    When User selects random quick shop from product array
    Then Verify quick shop modal is displayed
    And Verify product name on QS matches with category array

#    And Verify color swatchs displayed in QS
    And Verify size chips displayed in QS
#    And Verify size chart displayed in QS
#    And Verify quantity displayed in QS
#    And Verify Add to Bag displayed in QS
#    And Verify Wishlist displayed in QS
