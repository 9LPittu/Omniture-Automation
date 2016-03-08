@ProdSAccount
Feature: S_Account in production

  Scenario Outline: s_account value verification in Jcrew production
    Given User is on <pageurl> home page
    And Verify page source contains s_account
    And Validate the s_account value in production to be <value>

   Examples:
    |pageurl|value|
    |Jcrewprod|jcrewcom,jcrewglobalrollup|
    |Madewellprod|jcrewmwcom             |
    |Factoryprod |jcrewfactorycom,jcrewglobalrollup|




