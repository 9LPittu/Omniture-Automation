@Category
Feature: Category and Sub Category Page Validations

  Background:
    Given User goes to homepage
    And User closes email capture
    
    When User hovers on women category from header
    
  Scenario: subcategory page validations
   	And User selects sweaters subcategory array
   	And User closes email capture
    Then Verify user is in category array page
    And Verify refine dropdown displayed in array page
    And Verify refine dropdown text is sweaters

    When User expands refine dropdown
    And Verify refine dropdown is open
    
   