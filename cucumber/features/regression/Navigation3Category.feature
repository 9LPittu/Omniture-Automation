@Category
Feature: Regression Tests Category Feature

  Background:
    Given User is on homepage
    And User clicks on hamburger menu
    And Selects Women Category from hamburger menu

  Scenario: Check Category Header Title should be Present
    When User clicks on SWEATERS subcategory from Women Category
    Then User should be in sweaters page for women
    And Category header should be present

    #removing this scenario, since image is not being displayed anymore
  #Scenario: Check category header image
    #When User clicks on SWEATERS subcategory from Women Category
    #Then User should be in sweaters page for women
    #And An image should be displayed for SWEATERS

  Scenario: Check Product Information
    When User clicks on SHIRTS & TOPS subcategory from Women Category
    Then User should be in shirtsandtops page for women
    And Verifies product information is displayed
    And Verifies product image is displayed

  Scenario: Check Product Tiles for Sale Variations
    When User clicks on SWEATERS subcategory from Women Category
    Then User should be in sweaters page for women
    And Verifies Tipped lightweight wool Jackie shell product is displayed
    And Verifies Tipped lightweight wool Jackie shell product price was $59.50
    And Verifies Tipped lightweight wool Jackie shell product sale price is now $49.50
    And Verifies product image is displayed

  Scenario: Check sign posts on category pages
    When User clicks on J.CREW IN GOOD COMPANY subcategory from Women Category
    Then User should be in ingoodcompany page for women
    And Verifies accordion menu contains same items as in sign post items, first item should not be present in post sign
    And Chooses a random filter
    When filter becomes selected
    Then Refine modal autocloses
    And Products displayed match subcategory

  # Scenario: Check End cap navigation
    # When User clicks on SWEATERS subcategory from Women Category
    # Then Verifies navigation draw options are What's New, Clothing, Shoes & Accessories, Sizings
    # And Taps on What's New drawer and opens and all other drawer options are closed
    # And Verifies What's New drawer is open
    # When Taps on Clothing drawer and opens and all other drawer options are closed
    # Then Verifies Clothing drawer is open
    # When Taps on collapse button for Clothing
    # Then All drawers are closed
