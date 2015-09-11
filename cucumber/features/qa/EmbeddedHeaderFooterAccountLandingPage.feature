@EmbeddedHeaderFooterAccountLandingPage
Feature: Embedded Header Footer Account Landing Page

  Scenario: Account Landing Page Header Footer Links
    Given User is on homepage
    And Goes to sign in page
    When User enters test_checkout_desktop_signed_in@test.net as email
    And User enters test1234 as password
    And Hits sign in button
    Then User is in My Account page
    Then Verify embedded header and footer are visible and functional