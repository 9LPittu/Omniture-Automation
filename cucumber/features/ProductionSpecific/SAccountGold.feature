@Jcrew
  Feature: S_Account

#    Background:
#      Given User is on homepage

    Scenario: s_account validation in Jcrew gold
      Given User is on Jcrewgold home page
      And Verify page source contains s_account variable
      And Get the s_account value

    Scenario: s_account value verification in Jcrew production
      Given User is on Jcrewprod home page
      And Verify page source contains s_account variable
      And Validate the s_account value in production to be jcrewcom,jcrewglobalrollup

    Scenario: s_account validation in Madewell gold
      Given User is on Madewellgold home page
      And Verify page source contains s_account variable
      And Get the s_account value

    Scenario: s_account value verification in Madewell Production
      Given User is on Madewellprod home page
      And Verify page source contains s_account variable
      And Validate the s_account value in production to be jcrewmwcom

    Scenario: s_account validation in Factory gold
      Given User is on Factorygold home page
      And Verify page source contains s_account variable
      And Get the s_account value

    Scenario: s_account value verification in Factory Production
      Given User is on Factoryprod home page
      And Verify page source contains s_account variable
      And Validate the s_account value in production to be jcrewfactorycom,jcrewglobalrollup



