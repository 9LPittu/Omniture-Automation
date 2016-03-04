@Navigate
Feature: Sign In Page

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
    And Deletes

  # TODO: incomplete; script need to be updated for validation

  #US9724_TC10, US9724_TC11: Validate breadcrumbs functionality and display on sign in/ register for email page
  Scenario: Create New Account
    Then Breadcrumb should display J.Crew
    And Clicks on J.Crew Breadcrumb
    And Verify user is in homepage
    And User presses back button
    When Clicks on create new account
    And Fills required account data in create account page
    And Clicks on create new account in create account page
    Then Verify user is in homepage

