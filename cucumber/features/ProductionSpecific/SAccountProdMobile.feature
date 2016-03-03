@ProdSAccountMobile
Feature: S_Account in production

  Scenario: s_account value verification in Jcrew production
    Given User is on Jcrewprod home page
    And Verify page source contains s_account variable
    And Validate the s_account value in production to be jcrewcom,jcrewglobalrollup

  Scenario: s_account value verification in Madewell Production
    Given User is on Madewellprod home page
    And Verify page source contains s_account variable
    And Validate the s_account value in production to be https://smetrics.madewell.com/b/ss/jcrewmwcom

  Scenario: s_account value verification in Factory Production
    Given User is on Factoryprod home page
    And Verify page source contains s_account variable
    And Validate the s_account value in production to be https://smetrics.jcrew.com/b/ss/jcrewfactorycom,jcrewglobalrollup
