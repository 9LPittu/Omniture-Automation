@International

Feature: User is able to checkout in international context

  Scenario Outline: User is able to checkout in international page
    Given User goes to international homepage for <country_group>
    Then Verify welcome mat is displayed
    And Verify country context matches selected country
    And Verify welcome message

    When User clicks on Start Shopping
    And User closes email capture
    Then Verify user is in international homepage

    #Category
    And User navigates to a subcategory from main category
    Then Verify proper currency symbol is displayed on product grid list
    And User selects random product from array
    And User is in product detail page
    And Verify proper currency symbol is displayed on PDP page
    And User adds selected product to bag
    #Sale
    And User opens menu
    And User selects sale category from menu
    And User selects women dept from sales
    Then Verify proper currency symbol is displayed on product grid list
    And User selects random product from array
    And User is in product detail page
    And Verify proper currency symbol is displayed on PDP page
    And User adds selected product to bag
    #Search
    And User searches for a random search term
    Then User is in search results page















   When User adds to bag a random product using a main category
#    And User clicks in bag
#    And User clicks check out button
#    And User selects guest check out
#    And Guest user fills shipping address and continue
#    And User selects random shipping method and continue
#    And User fills payment method and continue
#    And User reviews and places order
#    Then User gets an order confirmation number
#
    Examples:
      | country_group |
      | PRICEBOOK     |
      | NON-PRICEBOOK |


  Scenario Outline: PDP message validation for sold out item, item with variations, vps item and shipping restriction item
    Given User goes to international homepage for <country_group>
    Then Verify welcome mat is displayed
    And Verify country context matches selected country
    When User clicks on Start Shopping
    And User searches for the soldout.item
    Then User is in product detail page
    Then Verify sold out message is displayed on PDP
    And User searches for the shipping.restriction.item
    Then User is in product detail page
    Then Verify shipping restriction message is displayed on PDP
    And User searches for the variations.item
    And User selects first product from search results
    Then User is in product detail page
    And Verify PDP message is displayed for the selected country
    And User selects random variant on the PDP page
    Then User is in product detail page
    And Verify PDP message is displayed for the selected country
#    currently vps is set up only in bronze in canada context. Will run the below when data is available
#    And User searches for the vps.item
#    Then User is in product detail page
#   Then Verify VPS item message is displayed on PDP

    Examples:
      | country_group |
      | PRICEBOOK     |
      | NON-PRICEBOOK |



