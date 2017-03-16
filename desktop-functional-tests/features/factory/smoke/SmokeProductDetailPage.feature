@PDP
Feature: Product Detail Page

  Background:
    Given User goes to homepage
    And User closes email capture
    And User hovers on a random category from list
      | Women |
      | Men   |
      | Girls |
      #| Boys  | boys sweaters category does not have any product
    And User selects sweaters subcategory array

  Scenario: Product Detail Page Validation
    And User closes email capture
    And User selects random product from product array
    And Verify product detail page is displayed

    When User adds to bag if product is not sold out

    When User clicks in bag
    Then Verify shopping bag is displayed