@MiniCart

Feature: Add products from category, search and sale, check mini bag and checkout

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
    Then Verify mini bag contains a message to show more and 3 items
    And Verify first item is the recently added product
    And Verify message link matches button link
    And Verify subtotal in mini bag matches items

    When User opens menu
    And User goes to home using menu drawer
    And User hovers over bag
    Then Verify each item links to product PDP

    When User clicks in bag
    And User clicks check out button
    And User selects guest check out
    And Guest user fills shipping address and continue
    And User selects random shipping method and continue
    And User fills payment method and continue
    And User reviews and places order
    Then User gets an order confirmation number