@Initialize
@dom
Feature: Test Marketing Emails

  Scenario: Test DOM sth flow
    When I am on the DOM application login page
    Given I login DOM application as "esdomqa1" and "esdomqa1"
    When I select "sales orders" from menu
#    And I enter order number "3050040290"
    And I enter order number
    And I click on apply button
    Then I should see the requested order in search results page
    And the order should be in "Allocated" status in search results page
    When I double click on order
    Then Order details page should display
    When I select "viewAdditionalDetails" option
    And I click on Line additional details
    And I select all line ids
    And I click on create DO
    When I select "distributionOrderDetails" option
    And I copy DO numbers
    And I release DOs from either DC or store based on fulfillment facility
#    And I select distribution orders from menu
#    And I enter copied distribution id
#    And I click on apply button in distribution orders page
#    And I double click DO in distribution orders page
#    And I click on release DO button
#    Then order status should change to "Released" in Distribution orders page
#    When I select "sales orders" from menu
#    And the order should be in "Released" status in search results page