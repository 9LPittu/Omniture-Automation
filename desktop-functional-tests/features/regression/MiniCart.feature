@MiniCart
Feature: Check mini bag when adding products from category, search and sale

  Background:
    Given User goes to homepage
    And User closes email capture

  Scenario: Mini Cart show stack items
    When User adds to bag a random product using a main category
    And User hovers over bag
    Then Verify mini bag contains 1 item
    And Verify first item is the recently added product

    When User adds to bag a random product from a search
    And User hovers over bag
    Then Verify mini bag contains 2 item
    And Verify first item is the recently added product

    When User adds to bag a random product from sale
    And User hovers over bag
    Then Verify mini bag contains 3 item
    And Verify first item is the recently added product

    When User adds to bag a random product using a main category
    And User hovers over bag
    Then Verify subtotal in mini bag matches items
    And Verify mini bag contains a message to show more and 3 items
    And Verify first item is the recently added product
    And Verify message link matches button link

    When User goes to homepage
    And User hovers over bag
    Then Verify each item links to product PDP

    When User clicks in bag
    And User clicks check out button
    And User checks out as guest
    And User fills shipping data and continues
    And User selects a random shipping method and continues
    And User fills payment method and continue
    And User clicks on PLACE MY ORDER
    Then Verify user gets a confirmation number