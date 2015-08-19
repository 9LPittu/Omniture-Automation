Feature: Navigation Feature

  Scenario: Check Header And Navigation Functionality
    Given User goes to /c/womens_category/sweaters page
    Then Validate global promo is displaying on top of the page
    And Hamburger menu is present
    And Search Link is present
    And Stores Link is present
    And Bag Link is present