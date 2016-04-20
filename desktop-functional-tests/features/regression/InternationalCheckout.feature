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

    When User navigates to a subcategory from main category
    Then Verify context in the array page

    When User selects random product from array
    Then Verify context in the product detail page

    When User adds selected product to bag
    And User clicks in bag
    Then Verify that shopping bag has expected context

    When User clicks check out button
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
