@InternationalCheckout
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
    When User navigates to a subcategory from main category
    Then Verify context in the array page
    And User should see selected country in the footer
    And User should see country code in the url for international countries
    And Verify proper currency symbol is displayed on product grid list

    When User selects random product from product array
    Then User is in product detail page
    And User should see selected country in the footer
    And Verify context in the product detail page
    And Verify proper currency symbol is displayed on PDP page

    When User adds selected product to bag
    #Sale
    And User navigates to specific women sale page
    Then Verify proper currency symbol is displayed on product grid list

    When User selects random product from array
    And User is in product detail page
    Then Verify proper currency symbol is displayed on PDP page
    And User adds selected product to bag

    #Search
    When User searches specific term dresses
    Then User is in search results page
    And Verify proper currency symbol is displayed on product grid list

    When User selects random product from array
    Then User is in product detail page
    Then Verify proper currency symbol is displayed on PDP page

    When User adds selected product to bag
    And User hovers over bag
    Then Verify proper currency symbol is displayed on minibag

    When User clicks in bag
    Then User is in shopping bag page
    And Verify proper currency symbol is displayed on item section on Checkout page
    And Verify proper currency symbol is displayed on summary section on Checkout page
    And Verify proper currency symbol is displayed on shipping method section on Checkout page

    When User clicks check out button
    And User selects guest check out
    Then Verify proper currency symbol is displayed on item section on Checkout page
    And Verify proper currency symbol is displayed on summary section on Checkout page
    And Verify proper currency symbol is displayed on shipping method section on Checkout page

    When Guest user fills shipping address and continue
    Then Verify proper currency symbol is displayed on item section on Checkout page
    And Verify proper currency symbol is displayed on summary section on Checkout page
    And Verify proper currency symbol is displayed on shipping method section on Checkout page
    And Verifies is in shipping method page

    When User selects random shipping method and continue
    Then Verify user is in billing page
    And Verify proper currency symbol is displayed on item section on Checkout page
    And Verify proper currency symbol is displayed on summary section on Checkout page
    And Verify proper currency symbol is displayed on shipping method section on Checkout page

    When User fills payment method and continue
    Then Verify user is in review page
    And Verify proper currency symbol is displayed on item section on Checkout page
    And Verify proper currency symbol is displayed on summary section on Checkout page
    And Verify proper currency symbol is displayed on shipping method section on Checkout page

    When User reviews and places order
    Then User gets an order confirmation number
    And Verify proper currency symbol is displayed on item section on Checkout page
    And Verify proper currency symbol is displayed on summary section on Checkout page
    And Verify proper currency symbol is displayed on shipping method section on Checkout page
    Examples:
      | country_group |
      | PRICEBOOK     |
      | NON-PRICEBOOK |


  Scenario Outline: PDP message validation for sold out item, item with variations, vps item and shipping restriction item
    Given User goes to international homepage for <country_group>
    Then Verify welcome mat is displayed
    And Verify country context matches selected country

    When User clicks on Start Shopping
    And User searches for the item soldout.item
    Then User is in product detail page
    And Verify sold out message is displayed on PDP

    When User searches for the item shipping.restriction.item
    Then User is in product detail page
    And Verify shipping restriction message is displayed on PDP

    When User searches for the item variations.item
    And User selects first product from search results
    Then User is in product detail page
    And Verify PDP message is displayed for the selected country

    When User selects random variant on the PDP page
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



