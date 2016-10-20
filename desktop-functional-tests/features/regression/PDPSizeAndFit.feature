@PDPSizeAndFit
Feature: PDP Size and Fit functionality

  Background:
    Given User goes to homepage
    And User closes email capture

  Scenario: Validate PDP Size & Fit for Women
    When User hovers on women category from header
    And User selects random subcategory array
    And User selects random product from product array
    And Verify product detail page is displayed
    And Verify SIZE & FIT is displayed between Add to Bag and PRODUCT DETAILS
    Then Verify 'SIZE & FIT' drawer is expanded state
    When user clicks on 'SIZE & FIT' drawer
    Then Verify 'SIZE & FIT' drawer is collapsed state

    And Verify SIZE & FIT is displayed between Add to Bag and PRODUCT DETAILS
    Then Verify 'PRODUCT DETAILS' drawer is collapsed state
    When user clicks on 'PRODUCT DETAILS' drawer
    Then Verify 'PRODUCT DETAILS' drawer is expanded state
    Then Verify item details are displayed in the 'PRODUCT DETAILS' drawer    
    When user clicks on 'PRODUCT DETAILS' drawer
    Then Verify 'PRODUCT DETAILS' drawer is collapsed state
  
  Scenario: Validate PDP Size & Fit for Men
    When User hovers on men category from header
    When User selects random subcategory array
    And User selects random product from product array
    And Verify product detail page is displayed

    And Verify SIZE & FIT is displayed between Add to Bag and PRODUCT DETAILS
    Then Verify 'SIZE & FIT' drawer is expanded state
    When user clicks on 'SIZE & FIT' drawer    
    Then Verify 'SIZE & FIT' drawer is collapsed state

    And Verify SIZE & FIT is displayed between Add to Bag and PRODUCT DETAILS
    Then Verify 'PRODUCT DETAILS' drawer is collapsed state   
    When user clicks on 'PRODUCT DETAILS' drawer
    Then Verify 'PRODUCT DETAILS' drawer is expanded state
    Then Verify item details are displayed in the 'PRODUCT DETAILS' drawer    
    When user clicks on 'PRODUCT DETAILS' drawer
    Then Verify 'PRODUCT DETAILS' drawer is collapsed state
    
  Scenario: Validate PDP Size & Fit for Boys
    When User hovers on boys category from header
    And User selects random subcategory array
    And User selects random product from product array
    And Verify product detail page is displayed

    And Verify SIZE & FIT is displayed between Add to Bag and PRODUCT DETAILS
    Then Verify 'SIZE & FIT' drawer is expanded state
    When user clicks on 'SIZE & FIT' drawer    
    Then Verify 'SIZE & FIT' drawer is collapsed state

    And Verify SIZE & FIT is displayed between Add to Bag and PRODUCT DETAILS
    Then Verify 'PRODUCT DETAILS' drawer is collapsed state
    When user clicks on 'PRODUCT DETAILS' drawer
    Then Verify 'PRODUCT DETAILS' drawer is expanded state
    Then Verify item details are displayed in the 'PRODUCT DETAILS' drawer    
    When user clicks on 'PRODUCT DETAILS' drawer
    Then Verify 'PRODUCT DETAILS' drawer is collapsed state
    
 Scenario: Validate PDP Size & Fit for Girls
    When User hovers on girls category from header
    When User selects random subcategory array
    And User selects random product from product array
    And Verify product detail page is displayed

   And Verify SIZE & FIT is displayed between Add to Bag and PRODUCT DETAILS
    Then Verify 'SIZE & FIT' drawer is expanded state
    When user clicks on 'SIZE & FIT' drawer    
    Then Verify 'SIZE & FIT' drawer is collapsed state

   And Verify SIZE & FIT is displayed between Add to Bag and PRODUCT DETAILS
    Then Verify 'PRODUCT DETAILS' drawer is collapsed state   
    When user clicks on 'PRODUCT DETAILS' drawer
    Then Verify 'PRODUCT DETAILS' drawer is expanded state
    Then Verify item details are displayed in the 'PRODUCT DETAILS' drawer    
    When user clicks on 'PRODUCT DETAILS' drawer
    Then Verify 'PRODUCT DETAILS' drawer is collapsed state