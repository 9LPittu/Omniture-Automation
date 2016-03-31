@Bag
Feature: Edit Product In Bags Scenarios

  Background:
    Given User is on homepage

  Scenario: Verify on edit mode add to bag changes to update bag
    When "sql.query.for.retreiving.items.with.multiple.colors.sizes" is run and search for item fetched from DB
    And User is in product detail page
    And A color is selected
    And A size is selected
    And Add to cart button is pressed
    And User clicks on item bag
    And Clicks edit button on item bag page
    Then user should see that previously selected color is retained
    And user should see that previously selected size is retained
    And Verify update bag button is present
    And user selects a new color
    And user selects a new size
    Then user should see that previously selected color is retained
    And user should see that previously selected size is retained
    And Deletes browser cookies

  Scenario: Verify shopping bag page reflects changes made to edited item
  	And User bag is cleared
    When User clicks on hamburger menu
    And user selects any category from hamburger menu
	And user selects any subcategory
    And user selects any item from array page, select any color and size
    Then User is in product detail page
    And Add to cart button is pressed
    And User clicks on item bag
    When Clicks edit button on item bag page
    And Update Bag button is pressed
    Then User should be in shopping bag page
    And in shopping bag page, user should see the color selected on the PDP page
    And in shopping bag page, user should see the size selected on the PDP page
    And Verify 1 items are specified as quantity in shopping bag
    And Deletes browser cookies

  Scenario: signed in Edit link redirects user to PDP
    And Handle the Email Capture pop up
    When Goes to sign in page
    And User provides login information
    And Hits sign in button
    And User is on homepage
    And User bag is cleared
    And User goes to homepage
    When User clicks on hamburger menu
    And user selects any category from hamburger menu
	And user selects any subcategory
    And user selects any item from array page, select any color and size
    Then User is in product detail page
    When Add to cart button is pressed
    And User clicks on item bag
    And Clicks edit button on item bag page
    Then user should see that previously selected color is retained
    And user should see that previously selected size is retained
    And Deletes browser cookies
