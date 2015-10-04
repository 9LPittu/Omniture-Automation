@Demo
Feature: Sample Parameterization

  Background:
    Given User is on homepage

  Scenario Outline: sign in page should be verified and signed in
    Given Goes to sign in page
    When User enters <Username> as email
    And User enters <Password> as password
    And Check box is enabled
    And Hits sign in button
    Then User is in My Account page
    And User clicks on SIGN OUT link in My Account Page
    And Verify user is in homepage

    Examples:
      |Username|Password|
      |test1@test.net|test1234|
      |test@example.org|test1234|
      |account@test.org|test1234|

     # |2@example.org|test1234|