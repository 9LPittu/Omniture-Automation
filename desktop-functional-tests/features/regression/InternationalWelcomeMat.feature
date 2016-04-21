@International @wip

Feature: Welcome mat takes you to the international page

  Scenario Outline: User gets to international PDP
    Given User lands on international page from list for <country_group>
      | PDP      |
      | category |
      | sale     |
      | login    |
    Then Verify welcome mat is displayed
    And Verify country context matches selected country
    And Verify welcome message

    When User clicks on Start Shopping
    Then Verify international page url

    Examples:
      | country_group |
      | PRICEBOOK     |
      | NON-PRICEBOOK |

  Scenario Outline: User gets to international PDP
    Given User lands on international page from list for <country_group>
      | PDP      |
      | category |
      | sale     |
      | login    |
    Then Verify welcome mat is displayed
    And Verify country context matches selected country
    And Verify welcome message

    When User clicks on Start Shopping
    Then Verify international page url

    Examples:
      | country_group |
      | PRICEBOOK     |
      | NON-PRICEBOOK |