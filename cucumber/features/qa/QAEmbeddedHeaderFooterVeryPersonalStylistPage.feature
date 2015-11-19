@EmbeddedHeaderFooterVeryPersonalStylistPage
Feature: Embedded Header Footer Very Personal Stylist Page

  Scenario: Very Personal Stylist Page Header Footer Links
    Given User is on homepage
    Then Click on footer link Let Us Help You
    And Click on sublink Request A Style Guide from Let Us Help You footer link
    Then Verify user is on personal stylist page
    Then Verify embedded header and footer are visible and functional