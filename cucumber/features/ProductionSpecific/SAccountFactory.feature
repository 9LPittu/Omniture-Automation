@f
Feature: S_Account

  Background:
    Given User is on Factory home page

  Scenario: s_account validation in source code
    And Verify page source contains s_account variable
    And Get the s_account value
    #the below is valid only for production
    And Validate the s_account value in production to be jcrewcom,jcrewglobalrollup