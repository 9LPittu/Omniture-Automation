@EmbeddedHeaderFooterAccountAddressBookPage
Feature: Embedded Header Footer Address Book Preferences Page

  Scenario: Account Address Book Page Header Footer Links
    Given User is on homepage
    And Goes to sign in page
    And User provides login information
    And Hits sign in button
    Then User is in My Account page
    And User clicks on ADDRESS BOOK link in My Account Page
    And User should be in address_book.jsp menu link page
    Then Verify embedded header and footer are visible and functional