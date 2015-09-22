@Wishlist
Feature: Ability to link to PDP from Wishlist

  Background:
    Given User is on homepage
    And Goes to sign in page
    When User enters wishlist@test.org as email
    And User enters test1234 as password
    And Hits sign in button
    Then User is in My Account page
    And User clicks on WISHLIST link in My Account Page
    And User should be in wishlist page
    And Verify product Rustic cotton fisherman sweater color is DEEP NAVY size is MEDIUM and quantity is 1 in wishlist page
    Then Edit wishlist for product Rustic cotton fisherman sweater

  Scenario: User should see corresponding PDP
    Given User is in product detail page
    Then Verify color DEEP NAVY is selected
    And Verify size MEDIUM is selected

  Scenario: Corresponding PDP should be editable
    Given User is in product detail page
    Then Verify color DEEP NAVY is selected
    And Verify size MEDIUM is selected
    And Verify 1 items are specified as quantity
    And Verify update wishlist button is displayed
    Then Color HTHR IVORY is selected by user
    And Size LARGE is selected by user
    Then Verify color HTHR IVORY is selected
    And Verify size LARGE is selected

  Scenario: Wishlist should update properly
    Given User is in product detail page
    Then Color HTHR IVORY is selected by user
    And Size LARGE is selected by user
    And Wishlist button is pressed
    Then Verify update message for wishlist is displayed and go to wishlist page
    And Verify product Rustic cotton fisherman sweater color is HTHR IVORY size is LARGE and quantity is 1 in wishlist page
    Then Edit wishlist for product Rustic cotton fisherman sweater
    Then User is in product detail page
    Then Color DEEP NAVY is selected by user
    And Size MEDIUM is selected by user
    And Wishlist button is pressed
    Then Verify update message for button wishlist is displayed

