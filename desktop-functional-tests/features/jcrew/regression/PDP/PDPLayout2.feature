@PDP2 @HighLevel
Feature: PDP layout from search array page

  Background:
    Given User goes to homepage
    And User closes email capture

  Scenario: PDP layout from search array page
    When User searches specific term dresses
    And User is in search results page

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
    And Verify product has recommended products
    And Verify endcaps displayed in PDP

    #id 139
    When User clicks on any recommended product
    Then Verify product detail page is displayed
    And Verify product detail page from recommendation is displayed

    When User selects random color
    And User selects random size
    And User adds product to bag
    And User clicks in bag
    Then Verify products added matches with products in bag