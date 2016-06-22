@Wishlist @HighLevel
Feature: Add items to Wishlist from PDP and Shoppable Tray

  Background:
    Given User is on homepage
    And Goes to sign in page
    And User provides login information
    And Hits sign in button
    When User is in My Account page
    And User clicks on WISHLIST link in My Account Page
    Then User should be in wishlist page
    And Deletes all previous wishlist items from the list

    #US9697_TC06_Part_2
  Scenario: Verify products can be added to wish list from Shoppable Tray
    Given User is on homepage
    And User clicks on hamburger menu
    And User selects random tray from available categories
      | Women | THIS MONTH'S FEATURES | looks we love  |
      | Men   | THIS MONTH'S FEATURES | 1 Suit, 5 Ways |
      | Girls | THIS MONTH'S FEATURES | Looks We Love  |
      | Boys  | THIS MONTH'S FEATURES | Looks We Love  |
    And User adds all products to wish list
    And User clicks on hamburger menu
    And User clicks on My Account link
    And User clicks on WISHLIST link in My Account Page
    Then Verify all products added from shop the look page are in wish list
    And Deletes browser cookies