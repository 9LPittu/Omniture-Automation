@AccountDev @HighLevel
Feature: My Account Page

  Background:
    Given User is on homepage
    And Handle the Email Capture pop up
    And Goes to sign in page
    And User provides loyalty login information
    And Check box is enabled
    And Hits sign in button
    And User is in My Account page
    When Verifies page displays My Account title
    When User clicks on MY DETAILS link in My Account Page
    And User should be in /r/account/details menu link page

    