@Jcrew
  Feature: S_Account

    Background:
      Given User is on homepage

    Scenario: s_account validation in source code
      And Verify page source contains s_account variable
      And Get the s_account value
      And Validate the s_account value in production to be jcrewcom,jcrewglobalrollup