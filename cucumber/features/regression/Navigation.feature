Feature: Navigation Feature

  Scenario: Check Header And Navigation Functionality
    Given User goes to /c/womens_category/sweaters page
    Then Validate global promo is displaying on top of the page
    And Hamburger menu is present
    And Search Link is present
    And Stores Link is present
    And Bag Link is present
    And JCrew Logo is present
    And Category title for SWEATERS should match below global promo
    And View All Section is present and collapsed
    And Verifies position of elements is the expected
    Then User clicks on hamburger menu
    And Closes hamburger menu
    Then User presses search button
    And Enters dresses to the search field
    And Clicks on search button for input field
    Then User is in search results page
    Then User presses back button
    And Category title for SWEATERS should match below global promo
    Then User clicks on stores link
    Then User is on external page https://stores.jcrew.com/
    Then User presses back button
    And Category title for SWEATERS should match below global promo
    Then User clicks on item bag
    Then User should be in shopping bag page
