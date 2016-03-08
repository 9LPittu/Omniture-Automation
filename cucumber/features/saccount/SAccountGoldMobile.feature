@GoldSAccountMobile
Feature: S_Account gold mobile

  Scenario: s_account validation in Jcrew gold
    Given User is on Jcrewgold home page
    And Verify page source contains s_account
    And Get the s_account value


  Scenario Outline: s_account validation in madewell and factory gold
    Given User is on <pageurl> home page
    And Verify page source contains src

    Examples:
      |pageurl|
      |Madewellgoldmobile|
      |Factorygoldmobile |