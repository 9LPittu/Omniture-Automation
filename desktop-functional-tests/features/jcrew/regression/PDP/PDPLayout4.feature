@PDP5 @HighLevel
Feature: PDP Layout4

  Background:
    Given User goes to homepage
    And User closes email capture

    When User hovers on a random category from list
      | Women |
      | Men   |
    And User selects sweaters subcategory array
    Then Verify user is in category array page

   
  Scenario: PDP layout from sale array page
    When User clicks on sale link from top nav
    And User selects random sale category

    When User selects random product from array
    Then Verify product detail page is displayed
    And Verify product name on PDP matches with category array
    And Verify Item code displayed in PDP
    And Verify price matches with category array
    And Verify that page contains a selected color
    And Verify size chips are displayed
    And Verify quantity dropdown is displayed
    And Verify Add To Bag button is displayed
    And Verify Wishlist button is displayed
    And Verify social icons displayed in PDP

    And Verify SIZE & FIT is displayed between Add to Bag and PRODUCT DETAILS
    And Verify PRODUCT DETAILS is displayed between SIZE & FIT and reviews

    And Verify reviews displayed in PDP
    And Verify baynotes displayed in PDP
    And Verify endcaps displayed in PDP

    When User selects random color
    And User selects random size
    And User adds product to bag

    When User clicks in bag
    Then Verify products added matches with products in bag

  Scenario: Variation PDP layout
    When User selects random variation product from product array
    Then Verify product detail page is displayed
    And Verify product name on PDP matches with category array
    And Verify Item code displayed in PDP
    And Verify Variations displayed in PDP
    And Verify price matches with category array
    And Verify that page contains a selected color
    And Verify size chips are displayed
    And Verify quantity dropdown is displayed
    And Verify Add To Bag button is displayed
    And Verify Wishlist button is displayed
    And Verify social icons displayed in PDP

    And Verify SIZE & FIT is displayed between Add to Bag and PRODUCT DETAILS
    And Verify PRODUCT DETAILS is displayed between SIZE & FIT and reviews

    And Verify reviews displayed in PDP
    And Verify baynotes displayed in PDP
    And Verify endcaps displayed in PDP