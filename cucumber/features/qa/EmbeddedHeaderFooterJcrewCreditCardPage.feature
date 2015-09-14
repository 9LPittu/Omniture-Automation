@EmbeddedHeaderFooterJcrewCreditCardPage
Feature: Embedded Header Footer Jcrew Credit Card Page

  Scenario: Jcrew Credit Card Page Header Footer Links
    Given User is on homepage
    Then Click on footer link OUR CARDS
    And Click on sublink the j.crew credit card from OUR CARDS footer link
    And Verify user is on the j.crew credit card page
    Then Verify embedded header and footer are visible and functional