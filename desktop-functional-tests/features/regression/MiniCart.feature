@MiniCart

Feature: Mini Cart functionality

  Background:
    Given User goes to homepage
    #And User closes email capture

  Scenario: Mini Cart show stack items
    When User opens menu
    And User adds to bag a random product using a category from list
      | Women |
      | Men   |
      | Girls |
      | Boys  |
    And User hovers over bag
    Then Verify mini bag contains 1 item
    When User opens menu
    And User goes back to categories menu
    And User adds to bag a random product using a category from list
      | Women |
      | Men   |
      | Girls |
      | Boys  |
    And User hovers over bag
    Then Verify mini bag contains 2 item
    When User opens menu
    And User goes back to categories menu
    And User adds to bag a random product using a category from list
      | Women |
      | Men   |
      | Girls |
      | Boys  |
    And User hovers over bag
    Then Verify mini bag contains 3 item
    When User opens menu
    And User goes back to categories menu
    And User adds to bag a random product using a category from list
      | Women |
      | Men   |
      | Girls |
      | Boys  |
    And User hovers over bag
    Then Verify mini bag contains 4 item
