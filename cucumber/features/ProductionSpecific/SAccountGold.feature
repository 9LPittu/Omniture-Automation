@GoldSAccount
  Feature: S_Account in gold

    Scenario Outline: s_account validation in Jcrew gold
      Given User is on <pageurl> home page
      And Verify page source contains s_account
      And Get the s_account value

    Examples:
      |pageurl|
      |Jcrewgold|
      |Madewellgold|
      |Factorygold |








