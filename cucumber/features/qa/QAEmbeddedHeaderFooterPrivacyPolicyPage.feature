@EmbeddedHeaderFooterPrivacyPolicyPage
Feature: Embedded Header Footer Contact Us Page

  Scenario: Contact Us Page Header Footer Links
    Given User is on homepage
    Then Click on PRIVACY POLICY bottom link from footer
    Then Verify user is on privacy policy page
    Then Verify embedded header and footer are visible and functional