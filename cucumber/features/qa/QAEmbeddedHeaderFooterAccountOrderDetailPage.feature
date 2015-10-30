@EmbeddedHeaderFooterAccountOrderDetailPage
Feature: Embedded Header Footer Order Detail Preferences Page

  Scenario: Account Order Detail Page Header Footer Links
    Given User is on homepage
    And Goes to sign in page
    When User enters account@test.org as email
    And User enters test1234 as password
    And Hits sign in button
    Then User is in My Account page
    And User clicks on ORDER HISTORY link in My Account Page
    And User should be in reg_user_order_history.jsp menu link page
    Then User selects an order listed for review
    And User should be in reg_user_order_detail.jsp menu link page
    Then Verify embedded header and footer are visible and functional