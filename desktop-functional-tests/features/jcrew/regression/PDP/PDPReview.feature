@Reviews @HighLevel
Feature: Product Review page from PDP

  Background:
    Given User goes to homepage
    And User closes email capture

  Scenario: Guest user gets product review after signing in
    When User hovers on women category from header
    And User selects sweaters subcategory array
    Then Verify user is in category array page
    When User selects random product from product array
    Then Verify product detail page is displayed

    When User clicks on write a review button
    Then User goes to sign in page

    When User fills form and signs in
    Then Verify product review page is displayed

  Scenario: Registered user gets product review page
    When User clicks on sign in using header
    Then User goes to sign in page
    And User fills user data and signs in
    Then Verify user is in My Account main page
    When User hovers on women category from header
    And User selects sweaters subcategory array
    Then Verify user is in category array page
    When User selects random product from product array
    Then Verify product detail page is displayed

    When User clicks on write a review button
    Then Verify product review page is displayed

  Scenario: Product with Fit slider
    When User navigates to fit slider product
    Then Verify product detail page is displayed
    #id 141
    And Verify product size and fit shows overall fit slider
    #id 145
    And Verify reviews summary is displayed