@EmbeddedHeaderFooterForgotPasswordPage
Feature: Embedded Header Footer Forgot Password

  Scenario: Forgot Password Page Header Footer Links
    Given User is on homepage
    And Goes to sign in page
    And Clicks on forgot password link
    And Verify user is in forgot password page
    Then Verify embedded header and footer are visible and functional