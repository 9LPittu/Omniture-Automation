@Initialize @dom
Feature: Test Marketing Emails

  Scenario: Test DOM STH fulfilment flow
    Given Test data is read from excel file "JC_E2E_WebTest_Testdata_Sheet.xls"
    When I am on the DOM application login page
    Given I login DOM application as "esdomqa1" and "esdomqa1"
    When I select "sales orders" from menu
    And I enter order number "JC_E2E_WebTest_Testdata_Sheet.xls"
    And I click on apply button
    Then I should see the requested order in search results page
    And the order should be in "Allocated" status in search results page
    When I double click on order
    Then Order details page should display
    When I select "viewAdditionalDetails" option
    And I click on Line additional details
    And I select all line ids
    And I click on create DO
    When I select "distributionOrderDetails" from drop down
    And I copy DO numbers
    And I release DOs from either DC or store based on fulfillment facility
    

    
    
           
    
    
    
    
    
    
    
    
