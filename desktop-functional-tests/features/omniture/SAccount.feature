@s_account
Feature: S_Account Verification

  Scenario: s_account verification
    Given User goes to homepage
    And User closes email capture

    Then Verify page source contains s_account
    And Verify correct value is displayed for the omniture variable s_account