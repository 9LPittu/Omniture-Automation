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

    When User adds to bag a random product using a main category
    And User clicks in bag
    And User clicks check out button
    And User selects guest check out
    And Guest user fills shipping address and continue
    And User selects random shipping method and continue
    And User fills payment method and continue
    And User reviews and places order
    Then User gets an order confirmation number

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
    Then User should see PDP page with soldout message which includes phone number
    And User searches for the shipping.restriction.item
    Then User is in product detail page
    Then user should see PDP page with shipping restriction message
    And User searches for the variations.item
    And User selects first product from search results
    Then User is in product detail page
    And user should see the PDP messages for the selected country
    And user selects random variant on the PDP page
    Then User is in product detail page
    And user should see the PDP messages for the selected country
    #currently vps is set up only in bronze in canada context. Will run the below when data is available
#    And User searches for the vps.item
#    Then User is in product detail page
#    Then user should see PDP page with message for vps item

    Examples:
      | country_group |
      | PRICEBOOK     |
      | NON-PRICEBOOK |



