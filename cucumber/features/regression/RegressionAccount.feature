@Account
Feature: Account

  Background:
    Given User is on homepage
    And Goes to sign in page

  Scenario: Error message validation for incorrect email and password
    When Login page is loaded
    And User enters invalid as email
    And Changes focus to password field
    Then An error message saying Please enter a valid email address. should appear
    And Sign in button should be deactivated
    When User enters test@example.org as email
    Then An error message saying Whoops, that's not right... Please try your password again. should appear
    And Sign in button should be deactivated

  # TODO: incomplete; script need to be updated for validation
  Scenario: Create New Account
    When Clicks on create new account
    And Fills required account data in create account page
    And Clicks on create new account in create account page
    Then Verify user is in homepage
