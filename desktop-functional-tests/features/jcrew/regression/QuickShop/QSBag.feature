@Quickshop @HighLevel
Feature: Quick Shop bag is functional

  Background:
    Given User goes to homepage
    And User closes email capture
  @Sanity
  Scenario: Verify guest user able to add item to bag from quick shop
    When User hovers on a random category and subcategory from list
      |women|sweaters|
      |men|dress shirts|
      |girls|dresses|
    Then Verify user is in category array page

    When User selects random quick shop from product array
    Then Verify quick shop modal is displayed

    And Selects random color
    And Selects random size
    And Selects random quantity from Quickshop
    And User clicks on Add to Bag button

    When User clicks on X icon
    And User clicks in bag

    Then Verify shopping bag is displayed
    And Verify products added matches with products in bag

  
