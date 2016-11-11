@Quickshop @HighLevel
Feature: Quickshop wishlist is functional

  Background:
    Given User goes to homepage
    And User closes email capture

  Scenario: Add to Wishlist from QS is functional for guest user
    When User hovers on women category from header
    And User selects sweaters subcategory array
    Then Verify user is in category array page

    When User selects random quick shop from product array
    Then Verify quick shop modal is displayed
    And Selects random color
    And Selects random size
    And User clicks on Wishlist button
    Then User goes to sign in page

    When User fills form and signs in
    Then Verify user is in sweaters category array page

Scenario: Add to Wishlist from QS is functional for registered user
  When User clicks on sign in using header
  Then User goes to sign in page
  And User fills user data and signs in
  Then Verify user is in My Account main page

  When User clicks on My Wishlist link in My Account Page
  Then User should be in /wishlist menu link page
  And Deletes all previous wishlist items from the list
  And User goes to homepage

  When User hovers on women category from header
  And User selects sweaters subcategory array
  Then Verify user is in category array page

  When User selects random quick shop from product array
  Then Verify quick shop modal is displayed
  And Selects random color
  And Selects random size
  And User clicks on Wishlist button

  When User clicks on X icon
  And User goes to homepage

  And User goes to Wishlist using header
  Then Verify user is in wishlist page
  And Verify product name on wishlist matches with QS