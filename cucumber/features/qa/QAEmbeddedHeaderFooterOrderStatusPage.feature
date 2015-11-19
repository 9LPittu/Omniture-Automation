@EmbeddedHeaderFooterOrderStatusPage
Feature: Embedded Header Footer Order Status Page

  Scenario: Order Status Page Header Footer Links
    Given User is on homepage
    And Click on footer link Let Us Help You
    And Click on sublink Order Status from Let Us Help You footer link
    Then Verify user is on order status page
    Then Verify embedded header and footer are visible and functional