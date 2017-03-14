@ShoppableTray2 @HighLevel
Feature: Add items to Wishlist from PDP

  Background:
    Given User goes to homepage
    And User closes email capture
    
    When User clicks on sign in using header
    And User fills form and signs in
    Then Verify user is in My Account main page

    When User clicks on MY WISHLIST link in My Account Page
    Then Verify user is in wishlist page

    Then Deletes all previous wishlist items from the list
    And User goes to homepage



Scenario: Verify ability to add to wishlist
   And User adds all products to wish list
   Then Verify items count matches in wishlist