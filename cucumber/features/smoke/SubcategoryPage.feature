@Subcategory
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

#  Even though the following Scenario validates that all product are displayed correctly in the product grid list
#  for the moment it will be commented out as it makes the tests to run 10 minutes more and smoke test should not take
#  that amount of time for execution, they could be reused later for functional testing if needed.

#  Scenario: Product Array is Valid
#    And User is in shirts and tops for women page
#    Then Product array should be displayed with correct values