@Bag
Feature: Edit Product In Bags Scenarios

  Background:
    Given User is on homepage

  Scenario: Verify on edit mode add to bag changes to update bag
    When User clicks on hamburger menu
    And Selects Women Category from hamburger menu
    And User clicks on SWEATERS subcategory from Women Category
    And Click on product Back-zip crewneck sweater to display PDP
    And User is in product detail page
    And Color CERULEAN COVE is selected by user
    And Size SMALL is selected by user
    And Add to cart button is pressed
    And User clicks on item bag
    And Clicks edit button on item bag page
    Then Verify color CERULEAN COVE is selected
    And Verify size SMALL is selected
    And Verify update bag button is present
    When Color NEON WATERMELON is selected by user
    And Size LARGE is selected by user
    Then Verify color NEON WATERMELON is selected
    And Verify size LARGE is selected

  Scenario: Verify shopping bag page reflects changes made to edited item
    When User clicks on hamburger menu
    And Selects Women Category from hamburger menu
    And User clicks on SWEATERS subcategory from Women Category
    And Click on product Back-zip crewneck sweater to display PDP
    Then User is in product detail page
    And Color CERULEAN COVE is selected by user
    And Size SMALL is selected by user
    And Add to cart button is pressed
    And User clicks on item bag
    When Clicks edit button on item bag page
    And Update Bag button is pressed
    Then User should be in shopping bag page
    And Verify color CERULEAN COVE is displayed in shopping bag
    And Verify size SMALL is displayed in shopping bag
    And Verify 1 items are specified as quantity in shopping bag

  Scenario: signed in Edit link redirects user to PDP
    When Goes to sign in page
    And User provides login information
    And Hits sign in button
    And User is on homepage
    And User bag is cleared
    And User goes to homepage
    When User clicks on hamburger menu
    And Selects Women Category from hamburger menu
    And User clicks on SWEATERS subcategory from Women Category
    And Click on product Back-zip crewneck sweater to display PDP
    Then User is in product detail page
    And Color CERULEAN COVE is selected by user
    And Size SMALL is selected by user
    When Add to cart button is pressed
    And User clicks on item bag
    And Clicks edit button on item bag page
    Then Verify color CERULEAN COVE is selected
    And Verify size SMALL is selected