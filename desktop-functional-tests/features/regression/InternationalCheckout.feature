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
    And Verify selected country is in footer
    And Verify country code in the url for international countries
    And Verify proper currency symbol is displayed on product grid list

    When User selects random product from product array
    Then Verify product detail page is displayed
    And Verify selected country is in footer
    And Verify context in the product detail page
    And Verify proper currency symbol is displayed on PDP page

    When User adds selected product to bag
    #Sale
    And User navigates to a random sale page
    Then Verify proper currency symbol is displayed on search grid list

    When User selects random product from array
    And Verify product detail page is displayed
    Then Verify proper currency symbol is displayed on PDP page

    When User adds selected product to bag
    #Search
    And User searches specific term dresses
    Then User is in search results page
    And Verify proper currency symbol is displayed on search grid list

    When User selects random product from array
    Then Verify product detail page is displayed
    Then Verify proper currency symbol is displayed on PDP page

    When User adds selected product to bag
    And User hovers over bag
    Then Verify proper currency symbol is displayed on minibag

    When User clicks in bag
    Then User is in shopping bag page
    And Verify currency symbol of each item matches the context
    And Verify currency symbol of subtotal in summary matches the context
    And Verify currency symbol of shipping in summary matches the context
    And Verify currency symbol of total matches the context

    When User clicks check out button
    And User selects guest check out
    Then Verify currency symbol of each item matches the context
    And Verify currency symbol of subtotal in summary matches the context
    And Verify currency symbol of shipping in summary matches the context
    And Verify currency symbol of total matches the context

    When Guest user fills shipping address and continue
    Then Verifies is in shipping method page
    And Verify currency symbol of each item matches the context
    And Verify currency symbol of subtotal in summary matches the context
    And Verify currency symbol of shipping in summary matches the context
    And Verify currency symbol of total matches the context

    When User selects random shipping method and continue
    Then Verify user is in billing page
    And Verify currency symbol of each item matches the context
    And Verify currency symbol of subtotal in summary matches the context
    And Verify currency symbol of shipping in summary matches the context
    And Verify currency symbol of total matches the context

    When User fills payment method and continue
    Then Verify user is in review page
    And Verify currency symbol of each item matches the context
    And Verify currency symbol of subtotal in summary matches the context
    And Verify currency symbol of shipping in summary matches the context
    And Verify currency symbol of total matches the context

    When User reviews and places order
    Then User gets an order confirmation number
    And Verify currency symbol of each item matches the context
    And Verify currency symbol of subtotal in summary matches the context
    And Verify currency symbol of shipping in summary matches the context
    And Verify currency symbol of total matches the context
    Examples:
      | country_group |
      | PRICEBOOK     |
      | NON-PRICEBOOK |
