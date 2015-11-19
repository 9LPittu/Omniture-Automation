@EmbeddedHeaderFooterReturnsAndExchangesPage
Feature: Embedded Header Footer Returns And Exchanges Page

  Scenario: Returns And Exchanges Page Header Footer Links
    Given User is on homepage
    And Click on footer link Let Us Help You
    And Click on sublink Returns & Exchanges from Let Us Help You footer link
    And Verify user is on returns & exchanges page
    Then Verify embedded header and footer are visible and functional