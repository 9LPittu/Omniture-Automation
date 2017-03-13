@PDP3
Feature: PDP Layout4

  Background:
    Given User goes to homepage
    And User closes email capture

  Scenario: PDP layout from sale array page
    When User clicks on sale link from top nav
    And User selects random sale category
    And  User selects random product from array
    Then Verify product detail page is displayed

    And Verify product name on PDP matches with category array
    And Verify Item code displayed in PDP
    And Verify price matches with category array
    And Verify color swatchs displayed in PDP
    And Verify size chips displayed in PDP
    And Verify quantity displayed in PDP
    And Verify Add to Bag displayed in PDP
    And Verify Wishlist displayed in PDP
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
