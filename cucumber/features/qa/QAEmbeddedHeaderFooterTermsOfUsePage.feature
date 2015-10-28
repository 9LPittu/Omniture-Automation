@EmbeddedHeaderFooterTermsOfUsePage
Feature: Embedded Header Footer Contact Us Page

  Scenario: Contact Us Page Header Footer Links
    Given User is on homepage
    Then Click on TERMS OF USE bottom link from footer
    Then Verify user is on terms of use page
    Then Verify embedded header and footer are visible and functional