@EmbeddedHeaderFooterAccountMyDetailsPage
Feature: Embedded Header Footer Account My Details Page

  Scenario: Account My Details Page Header Footer Links
    Given User is on homepage
    And Goes to sign in page
    When User enters account@test.org as email
    And User enters test1234 as password
    And Hits sign in button
    Then User is in My Account page
    And User clicks on MY DETAILS link in My Account Page
    And User should be in account_detail.jsp menu link page
    Then Verify embedded header and footer are visible and functional