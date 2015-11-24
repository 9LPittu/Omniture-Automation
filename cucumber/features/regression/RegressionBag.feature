@Bag
Feature: Edit Product In Bags Scenarios

  Background:
    Given User is on homepage

  Scenario: Verify on edit mode add to bag changes to update bag
    When User clicks on hamburger menu
    And Selects Women Category from hamburger menu
    And User clicks on DRESSES subcategory from Women Category
    And Click on product Marlie dress in classic faille to display PDP
    And User is in product detail page
    And Color BLACK is selected by user
    And Size 6 is selected by user
    And Add to cart button is pressed
    And User clicks on item bag
    And Clicks edit button on item bag page
    Then Verify color BLACK is selected
    And Verify size 6 is selected
    And Verify update bag button is present
    When Color CORAL SUNSET is selected by user
    And Size 2 is selected by user
    Then Verify color CORAL SUNSET is selected
    And Verify size 2 is selected

  Scenario: Verify shopping bag page reflects changes made to edited item
    When User clicks on hamburger menu
    And Selects Women Category from hamburger menu
    And User clicks on DRESSES subcategory from Women Category
    And Click on product Marlie dress in classic faille to display PDP
    Then User is in product detail page
    And Color BLACK is selected by user
    And Size 6 is selected by user
    And Add to cart button is pressed
    And User clicks on item bag
    When Clicks edit button on item bag page
    And Quantity 3 is selected by user
    And Update Bag button is pressed
    Then User should be in shopping bag page
    And Verify color BLACK is displayed in shopping bag
    And Verify size 6 is displayed in shopping bag
    And Verify 3 items are specified as quantity in shopping bag

  Scenario: signed in Edit link redirects user to PDP
    When Goes to sign in page
    And User provides login information
    And Hits sign in button
    And User is on homepage
    And User bag is cleared
    And User goes to homepage
    When User clicks on hamburger menu
    And Selects Women Category from hamburger menu
    And User clicks on DRESSES subcategory from Women Category
    And Click on product Marlie dress in classic faille to display PDP
    Then User is in product detail page
    And Color BLACK is selected by user
    And Size 6 is selected by user
    When Add to cart button is pressed
    And User clicks on item bag
    And Clicks edit button on item bag page
    Then Verify color BLACK is selected
    And Verify size 6 is selected