@GoldSAccountMobile
Feature: S_Account gold mobile

  Scenario: s_account validation in Jcrew gold
    Given User is on Jcrewgold home page
    And Verify page source contains s_account variable
    And Get the s_account value

  Scenario: s_account validation in Madewell gold
    Given User is on Madewellgoldmobile home page
    And Verify page source contains s_account variable
    And Get the s_account value

  Scenario: s_account validation in Factory gold
    Given User is on Factorygoldmobile home page
    And Verify page source contains s_account variable
    And Get the s_account value
