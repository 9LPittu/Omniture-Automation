@Wishlist
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

  Scenario: Wishlist should update properly
    #Open related Jira: SC-696
    When User clicks on hamburger menu
    And user selects WOMEN category from hamburger menu
    And user selects NEW ARRIVALS subcategory
    And Selects the first product with available colors from product grid list
    And User is in product detail page
    And A color is selected
    And A size is selected
    And Wishlist button is pressed
    Then Verify update message for wishlist is displayed and go to wishlist page
    And in wishlist page, user should see the color selected on the PDP page
    And in wishlist page, user should see the size selected on the PDP page
    And in wishlist page, user should see the quantity as 1
    And edit item from wishlist
    And User is in product detail page
    Then user should see that previously selected color is retained
    And user should see that previously selected size is retained
    And Verify 1 items are specified as quantity
    And Verify update wishlist button is displayed
    And user selects a new color
    And user selects a new size
    And Wishlist button is pressed
    Then Verify update message for wishlist is displayed and go to wishlist page
    And in wishlist page, user should see the color selected on the PDP page
    And in wishlist page, user should see the size selected on the PDP page
    And in wishlist page, user should see the quantity as 1
    And edit item from wishlist
    Then User is in product detail page
    When Add to cart button is pressed
    Then A minicart modal should appear with message '1 item has been added to your cart.'
    And Deletes browser cookies

    #US9697_TC06_Part_2
  Scenario: Verify products can be added to wish list from Shoppable Tray
    Given User is on homepage
    And User clicks on hamburger menu
    And User selects random tray from available categories
      |Women|THIS MONTH'S FEATURES|looks we love |
      |Men  |THIS MONTH'S FEATURES|1 Suit, 5 Ways|
      |Girls|THIS MONTH'S FEATURES|Looks We Love |
      |Boys |THIS MONTH'S FEATURES|Looks We Love |
    And User adds all products to wish list
    And User clicks on hamburger menu
    And User clicks on My Account link
    And User clicks on WISHLIST link in My Account Page
    Then Verify all products added from shop the look page are in wish list
    And Deletes browser cookies