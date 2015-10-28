@EmbeddedHeaderFooterWishlistPage
Feature: Embedded Header Footer Wishlist Page

  Scenario: Wishlist Page Header Footer Links
    Given User is on homepage
    And Goes to sign in page
    When User enters wishlist@test.org as email
    And User enters test1234 as password
    And Hits sign in button
    Then User is in My Account page
    And User clicks on WISHLIST link in My Account Page
    And User should be in wishlist page
    Then Verify embedded header and footer are visible and functional