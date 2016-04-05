@MiniCart

Feature: Mini Cart functionality

  Background:
    Given User goes to homepage
    And User closes email capture

  Scenario: Mini Cart show stack items
    When User opens menu
    And User adds to bag a random product using a main category
    And User hovers over bag
    Then Verify mini bag contains 1 item
    And Verify first item is the recently added product
    When User opens menu
    And User goes back to categories menu
    And User adds to bag a random product using a main category
    And User hovers over bag
    Then Verify mini bag contains 2 item
    And Verify first item is the recently added product
    When User opens menu
    And User goes back to categories menu
    And User adds to bag a random product using a main category
    And User hovers over bag
    Then Verify mini bag contains 3 item
    And Verify first item is the recently added product
    When User opens menu
    And User goes back to categories menu
    And User adds to bag a random product using a main category
    And User hovers over bag
    Then Verify mini bag contains only 3 items and a message to show more
    And Verify first item is the recently added product
    And Verify message link matches button link
    And Verify subtotal in mini bag matches items
    And Verify each item links to product PDP