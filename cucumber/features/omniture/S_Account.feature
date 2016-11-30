@s_account
Feature: S_Account gold mobile

  Scenario: s_account validation in Jcrew gold
    Given User is on homepage with clean session
    And Handle the Email Capture pop up
    And Verify page source contains s_account
    And Verify correct s_account value is displayed
