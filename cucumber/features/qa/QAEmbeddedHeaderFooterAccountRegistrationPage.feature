@EmbeddedHeaderFooterAccountRegistrationPage
Feature: Embedded Header Footer Account Registration

  Scenario: Account Registration Header Footer Links
    Given User is on homepage
    And Goes to sign in page
    And Clicks on create new account
    Then Verify embedded header and footer are visible and functional