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
    And User should be in /account/home.jsp menu link page

  Scenario: Validate fields in my details form
    When User clicks on MY DETAILS link in My Account Page
    And My Details form should display
    And User should be in /r/account/details menu link page

    Then Verify first_name is displayed in My details form

#   Validate  appropriate page is displayed upon selecting options from dropdown in mydetails






