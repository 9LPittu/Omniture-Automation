@EmbeddedHeaderFooterShippingAndHandlingPage
Feature: Embedded Header Footer Shipping And Handling Page

  Scenario: Shipping And Handling Page Header Footer Links
    Given User is on homepage
    And Click on footer link Let Us Help You
    And Click on sublink Shipping & Handling from Let Us Help You footer link
    And Verify user is on shipping & handling page
    Then Verify embedded header and footer are visible and functional