@EmbeddedHeaderFooterShippingAndHandlingPage
Feature: Embedded Header Footer Shipping And Handling Page

  Scenario: Shipping And Handling Page Header Footer Links
    Given User is on homepage
    And Click on footer link YOUR ORDERS
    And Click on sublink shipping & handling from YOUR ORDERS footer link
    And Verify user is on shipping & handling page
    Then Verify embedded header and footer are visible and functional