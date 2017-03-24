@International
Feature: Welcome mat takes you to the international page or US site

  Scenario: User gets to international page
    Given User lands on international page from below list for CA country    
      | PDP      |
      | category |
      | sale     |
      | login    |
    Then Verify welcome mat is displayed
    And Verify country context matches selected country
    And Verify welcome message

    When User clicks on Start Shopping
    Then Verify international page url

  Scenario: User gets to US homepage
    Given User lands on international page from below list for CA country
      | PDP      |
      | category |
      | sale     |
      | login    |
    Then Verify welcome mat is displayed
    And Verify country context matches selected country
    And Verify welcome message

    When User clicks on Take me to US site
    Then Verify user is in homepage