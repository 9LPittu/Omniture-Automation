@International-Part2C @HighLevel
Feature: International Country Context - Part 2C

  Scenario Outline: PDP message validation for sold out item, item with variations, vps item and shipping restriction item
    Given User goes to international homepage for <country_group>
    Then Verify welcome mat is displayed
    And Verify country context matches selected country

    When User clicks on Start Shopping
    And User closes email capture
    And User searches for the item soldout.item
    Then Verify product detail page is displayed
    And Verify sold out message is displayed on PDP

    When User searches for the item shipping.restriction.item
    Then Verify product detail page is displayed
    And Verify shipping restriction message is displayed on PDP

    When User searches for the item variations.item
    And User selects first product from search results
    Then Verify product detail page is displayed
    And Verify PDP message is displayed for the selected country

    When User selects random variant on the PDP page
    Then Verify product detail page is displayed
    And Verify PDP message is displayed for the selected country
  #    currently vps is set up only in bronze in canada context. Will run the below when data is available
  #    And User searches for the vps.item
  #    Then Verify product detail page is displayed
  #   Then Verify VPS item message is displayed on PDP
    Examples:
      | country_group |
      | PRICEBOOK     |
      | NON-PRICEBOOK |
