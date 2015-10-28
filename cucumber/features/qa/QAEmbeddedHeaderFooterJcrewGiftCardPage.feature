@EmbeddedHeaderFooterJcrewGiftCardPage
Feature: Embedded Header Footer Jcrew Gift Card Page

  Scenario: Jcrew Gift Card Page Header Footer Links
    Given User is on homepage
    Then Click on footer link OUR CARDS
    And Click on sublink the j.crew gift card from OUR CARDS footer link
    And Verify user is on the j.crew gift card page
    Then Verify embedded header and footer are visible and functional