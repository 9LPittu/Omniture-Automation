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
    #When Enters soldout.item to the search field
    And User searches for a random search term
    And User searches for a sold out item
    And user selects first product from search results
    Then User is in product detail page
    Then user should see PDP page with soldout message which includes phone number
    And User presses search button
    When Enters variations.item to the search field
    And Clicks on search button for input field
    And user selects first product from search results
    Then User is in product detail page
    And user should see the PDP messages for the selected country
    And user selects random variant on the PDP page
    Then User is in product detail page
    And user should see the PDP messages for the selected country
    And User presses search button
    When Enters vps.item to the search field
    And Clicks on search button for input field
    And user selects first product from search results
    Then User is in product detail page
    Then user should see PDP page with message for vps item
    And User presses search button
    When Enters shipping.restriction.item to the search field
    And Clicks on search button for input field
    And user selects first product from search results
    Then User is in product detail page
    Then user should see PDP page with shipping restriction message

    Examples:
      | country_group |
      | PRICEBOOK     |
     # | NON-PRICEBOOK |
