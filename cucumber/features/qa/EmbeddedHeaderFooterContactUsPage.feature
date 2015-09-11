@EmbeddedHeaderFooterContactUsPage
Feature: Embedded Header Footer Contact Us Page

  Scenario: Contact Us Page Header Footer Links
    Given User is on homepage
    Then Click on CONTACT US bottom link from footer
    Then Verify user is on contact us page
    Then Verify embedded header and footer are visible and functional