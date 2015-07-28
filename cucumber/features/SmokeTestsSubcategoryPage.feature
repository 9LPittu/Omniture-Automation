Feature: Smoke Tests Subcategory Page

  Background:
    Given User is on homepage
    And User clicks on hamburger menu
    And Selects Women Category from hamburger menu
    And Selects Shirts and Tops from Women Category in hamburger menu


  Scenario: Category Link
    Then User is in shirts and tops for women page

  Scenario: Product List Details
    And User is in shirts and tops for women page
    And User hovers a product
    Then Proper details are shown for the hovered product
