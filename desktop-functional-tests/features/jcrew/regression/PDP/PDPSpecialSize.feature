@PDPSpecialSize @HighLevel

Feature: Verify Add to Bag functionality of Special Size item

  Scenario: Verify Add to Bag functionality of Special Size item
    Given User goes to homepage
    When User closes email capture
    And User hovers on women category from header
    And User selects skirts subcategory array
    Then Verify user is in category array page

    When User selects random variation product from product array
    Then Verify product detail page is displayed
    And Verify product variations are displayed

    When User selects random variant on the PDP page
    And User selects random color
    And User selects random size
    And User adds product to bag
    And User clicks in bag
    Then Verify products added matches with products in bag