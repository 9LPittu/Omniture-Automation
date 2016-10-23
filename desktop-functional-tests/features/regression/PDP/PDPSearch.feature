@PDP @Search
Feature: Search is functional from PDP

  Background:
    Given User goes to homepage
    And User closes email capture

  Scenario: User is able to search from PDP as guest
    When User hovers on women category from header
    And User selects sweaters subcategory array
    Then Verify user is in category array page
    When User selects random product from product array
    Then Verify product detail page is displayed
    When User searches specific term dresses
    And User is in search results page

  Scenario: User is able to search from PDP as registered user
    When User clicks on sign in using header
    Then User goes to sign in page
    And User fills user data and signs in
    When User hovers on women category from header
	And User selects sweaters subcategory array
    Then Verify user is in category array page
    When User selects random product from product array
    Then Verify product detail page is displayed
    When User searches specific term dresses
    And User is in search results page