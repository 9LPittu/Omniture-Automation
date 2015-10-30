@EmbeddedHeaderFooterOrderStatusPage
Feature: Embedded Header Footer Order Status Page

  Scenario: Order Status Page Header Footer Links
    Given User is on homepage
    And Click on footer link YOUR ORDERS
    And Click on sublink order status from YOUR ORDERS footer link
    Then Verify user is on order status page
    Then Verify embedded header and footer are visible and functional