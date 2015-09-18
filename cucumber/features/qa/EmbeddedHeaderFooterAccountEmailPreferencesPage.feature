@EmbeddedHeaderFooterAccountEmailPreferencesPage
Feature: Embedded Header Footer Account Email Preferences Page

  Scenario: Account Email Preferences Page Header Footer Links
    Given User is on homepage
    And Goes to sign in page
    When User enters account@test.org as email
    And User enters test1234 as password
    And Hits sign in button
    Then User is in My Account page
    And User clicks on EMAIL PREFERENCES link in My Account Page
    And User should be in email_preferences.jsp menu link page
    Then Verify embedded header and footer are visible and functional