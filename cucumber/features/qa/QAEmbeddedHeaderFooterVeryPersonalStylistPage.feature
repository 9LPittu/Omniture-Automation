@EmbeddedHeaderFooterVeryPersonalStylistPage
Feature: Embedded Header Footer Very Personal Stylist Page

  Scenario: Very Personal Stylist Page Header Footer Links
    Given User is on homepage
    Then Click on footer link LET US HELP YOU
    And Click on sublink very personal stylist from LET US HELP YOU footer link
    Then Verify user is on personal stylist page
    Then Verify embedded header and footer are visible and functional