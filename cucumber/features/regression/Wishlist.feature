
@Wishlist
Feature: Ability to link to PDP from Wishlist

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
    When User presses search button
    And Enters A1447 to the search field
    And Clicks on search button for input field
    And Color BLACK is selected by user
    And Size 6 is selected by user
    And Wishlist button is pressed
    Then Verify update message for wishlist is displayed and go to wishlist page
    And Verify product Marlie dress in classic faille color is BLACK size is 6 and quantity is 1 in wishlist page
    When Edit wishlist for product Marlie dress in classic faille
    And User is in product detail page
    And Verify color BLACK is selected
    And Verify size 6 is selected
    And Verify 1 items are specified as quantity
    And Verify update wishlist button is displayed
    When Color SEA SPRAY is selected by user
    And Size 8 is selected by user
    And Wishlist button is pressed
    Then Verify update message for wishlist is displayed and go to wishlist page
    And Verify product Marlie dress in classic faille color is SEA SPRAY size is 8 and quantity is 1 in wishlist page
    When Edit wishlist for product Marlie dress in classic faille
    Then User is in product detail page
    When Add to cart button is pressed
    Then A minicart modal should appear with message '1 item has been added to your cart.'
    And Deletes browser cookies

    #US9697_TC06_Part_2
  Scenario Outline: Verify products can be added to wish list from multiple pdp pages
    Given User is on homepage
    And User clicks on hamburger menu
    And Selects <Category> Category from hamburger menu
    And User clicks on THIS MONTH'S FEATURES subcategory from <Category> Category
    And User clicks on <Option> from featured this month
    And User selects random shop the look page for <Category>
    And User adds all products to wish list
    And User clicks on hamburger menu
    And User clicks on My Account link
    And User clicks on WISHLIST link in My Account Page
    Then Verify all products added from shop the look page are in wish list
    And Deletes browser cookies

    Examples:
      | Category |Option|
      |Women|looks we love |
      |Men  |1 Suit, 5 Ways|
      |Girls|Looks We Love |
      |Boys |Looks We Love |