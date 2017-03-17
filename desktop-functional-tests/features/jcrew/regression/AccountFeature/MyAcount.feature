@Account
Feature: My Account page validations

  Background:
    Given User goes to homepage
    And User closes email capture
    When User clicks on sign in using header
    Then User goes to sign in page

  Scenario Outline: Validate rewards link is visible only for JCCC associated users in my account page

    When User fills <userCategory> category data and signs in
    Then Verify user is in My Account main page

    And Verify J.Crew Card Rewards Status reward link for <userCategory> user in My account page

    When User clicks on J.Crew Card Rewards Status reward link from My Account Page
    Then User should be in /r/account/jccc-rewards menu link page

    Examples:
      |userCategory |
      |loyalty  |
      |noLoyalty|