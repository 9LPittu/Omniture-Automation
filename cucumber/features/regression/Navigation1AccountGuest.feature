@Account
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

  # TODO: incomplete; script need to be updated for validation

  #US9724_TC10, US9724_TC11: Validate breadcrumbs functionality and display on sign in/ register for email page
#  Scenario: Create New Account
#    Then Breadcrumb should display J.Crew
#    And Clicks on J.Crew Breadcrumb
#    And Verify user is in homepage
#    And User presses back button
#    When Clicks on create new account
#    And Fills required account data in create account page
#    And Clicks on create new account in create account page
#    Then Verify user is in homepage
#    And Deletes browser cookies

  #US9890: Account Register and Errors

  #US9890_TC01, #US9890_TC02
  Scenario: Validate registration benefits copy is displayed in user registration section on SignIn page
    And Registration benefits copy is displayed as Three reasons why you should: Quick checkout, shareable wishlists and easy order tracking.
    And First name field is displayed in registration section
    And First name field allows maximum 30 characters
    When User clicks on create an account button
    Then An error message saying Please enter first name. should appear under first name field
    Then An error message saying Please enter last name. should appear under last name field
    Then An error message saying Please enter a valid email address. should appear under email field
    Then An error message saying Please enter password. should appear under password field





