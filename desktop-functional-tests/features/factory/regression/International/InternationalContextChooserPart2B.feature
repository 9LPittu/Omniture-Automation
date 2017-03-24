@International-Part2B
Feature: International Country Context - Part 2B

  Background:
    Given User goes to homepage
    And User closes email capture

  Scenario: Multiple Pages During Checkout Context validations
    Then Click on change link from footer
    And User is on context chooser page
    And User is on internal /r/context-chooser page
    When User selects CA country from context chooser page
    Then User should land on country specific home page
    And Verify selected country is in footer
    And User navigates to a subcategory from main category
    And Verify context in the array page
    And Verify selected country is in footer
    And User selects random product from product array
    And Verify product detail page is displayed
    And Verify selected country is in footer
    And Verify context in the product detail page
    And User adds selected product to bag
    And User clicks in bag
    And Verify user is in shopping bag page
    And Verify that shopping bag has expected context
    And User clicks check out button
    And Verify selected country is in footer
    And User checks out as guest
    And Verify selected country is in footer
    And User fills shipping data and continues
    And Verify selected country is in footer
    And User selects a random shipping method and continues
    And Verify selected country is in footer
    And User fills payment method and continue
    And Verify selected country is in footer
    And User clicks on PLACE MY ORDER
    And Verify selected country is in footer
    Then Verify user gets a confirmation number
    And Verify selected country is in footer