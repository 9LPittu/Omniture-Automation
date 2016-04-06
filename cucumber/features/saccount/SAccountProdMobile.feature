@ProdSAccountMobile
Feature: S_Account in production

  Scenario: s_account value verification in Jcrew production
    Given User is on Jcrewprod home page
    And Verify page source contains s_account
    And Validate the s_account value in production to be jcrewcom,jcrewglobalrollup

  Scenario Outline: s_account value verification in Madewell and Factory Production
    Given User is on <pageurl> home page
    And Verify page source contains <srcurl>

  Examples:
    |pageurl|srcurl|
    |Factoryprod|https://smetrics.jcrew.com/b/ss/jcrewfactorycom,jcrewglobalrollup|
    |Madewellprodmobile|https://smetrics.madewell.com/b/ss/jcrewmwcom|



