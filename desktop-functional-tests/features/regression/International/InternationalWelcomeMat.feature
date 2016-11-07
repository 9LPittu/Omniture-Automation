@International @HighLevel

Feature: Welcome mat takes you to the international page or US site

  Scenario Outline: User gets to international page
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

  Scenario Outline: User gets to US homepage
    Given User lands on international page from list for <country_group>
      | PDP      |
      | category |
      | sale     |
      | login    |
    Then Verify welcome mat is displayed
    And Verify country context matches selected country
    And Verify welcome message

    When User clicks on Take me to US site
    Then Verify user is in homepage

    Examples:
      | country_group |
      | PRICEBOOK     |
      | NON-PRICEBOOK |