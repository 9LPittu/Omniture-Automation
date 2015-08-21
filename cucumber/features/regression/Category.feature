@Category
Feature: Regression Tests Category Feature

  Background:
    Given User is on homepage
    And User clicks on hamburger menu
    And Selects Women Category from hamburger menu

  Scenario: Check Category Header Title Not Present
    Given User clicks on SHIRTS & TOPS subcategory from Women Category
    And User should be in shirtsandtops page for women
    Then Category header should not be present

  Scenario: Check category header image
    Given User clicks on SWEATERS subcategory from Women Category
    Then User should be in sweaters page for women
    And An image should be displayed for SWEATERS

  Scenario: Check Product Tiles
    Given User clicks on SHIRTS & TOPS subcategory from Women Category
    And User should be in shirtsandtops page for women
    Then Verifies Carrie cami product is displayed
    And Verifies Carrie cami product list price is $98.00
    And Verifies Carrie cami is available in 7 colors
    And An image is displayed for Carrie cami product


  Scenario: Check Product Tiles for Sale Variations
    Given User clicks on SHIRTS & TOPS subcategory from Women Category
    And User should be in shirtsandtops page for women
    Then Verifies Grosgrain ribbon top product is displayed
    And Verifies Grosgrain ribbon top product price was $98.00
    And Verifies Grosgrain ribbon top product sale price is now $29.99
    And Verifies Grosgrain ribbon top is available in 4 colors
    And An image is displayed for Grosgrain ribbon top product


  Scenario: Check sign posts on category pages
    Given User clicks on J.CREW IN GOOD COMPANY subcategory from Women Category
    And User should be in ingoodcompany page for women
    #  Post sign validation for accordion menu pending due to bug found where the accordion menu values displayed
    #  did not match with the post signs set.
    #Then Verifies accordion menu contains same items as in sign post items
    Then User clicks on expand icon
    And Selects woolrich subcategory
    Then WOOLRICH option becomes selected
    And Refine modal autocloses
    And Array page displays WOOLRICH
    And Products displayed are woolrich from ingoodcompany category

  Scenario: Check End cap navigation
    Given User goes to /c/womens_category/dresses page
    Then Verifies end cap navigation menu to say SHOP WOMEN
    And Verifies navigation draw options are What's New, Clothing, Shoes & Accessories, Sizings
    Then Taps on What's New drawer and opens and all other drawer options are closed
    And Verifies What's New drawer is open
    Then Taps on Clothing drawer and opens and all other drawer options are closed
    And Verifies Clothing drawer is open
    Then Taps on collapse button for Clothing
    And All drawers are closed
