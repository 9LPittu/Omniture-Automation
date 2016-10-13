@PDPSizeAndFit
Feature: PDP Size and Fit functionality

  Background:
    Given User goes to homepage
    And User closes email capture

  Scenario: Validate PDP Size & Fit for Women
    When User opens menu
    And User selects women category from menu
    When User selects random subcategory array
    And User selects random product from product array
    And Verify product detail page is displayed
    Then Verify 'SIZE & FIT' section is displayed below the 'Add to Bag' button
    Then Verify 'SIZE & FIT' drawer is expanded state
    When user clicks on 'SIZE & FIT' drawer    
    Then Verify 'SIZE & FIT' drawer is collapsed state
    
    Then Verify 'PRODUCT DETAILS' section is displayed below the 'SIZE & FIT' drawer
    Then Verify 'PRODUCT DETAILS' drawer is collapsed state   
    When user clicks on 'PRODUCT DETAILS' drawer
    Then Verify 'PRODUCT DETAILS' drawer is expanded state
    Then Verify item details are displayed in the 'PRODUCT DETAILS' drawer    
    When user clicks on 'PRODUCT DETAILS' drawer
    Then Verify 'PRODUCT DETAILS' drawer is collapsed state
  
  Scenario: Validate PDP Size & Fit for Men
    When User opens menu
    And User selects men category from menu
    When User selects random subcategory array
    And User selects random product from product array
    And Verify product detail page is displayed

    Then Verify 'SIZE & FIT' section is displayed below the 'Add to Bag' button
    Then Verify 'SIZE & FIT' drawer is expanded state
    When user clicks on 'SIZE & FIT' drawer    
    Then Verify 'SIZE & FIT' drawer is collapsed state
    
    Then Verify 'PRODUCT DETAILS' section is displayed below the 'SIZE & FIT' drawer
    Then Verify 'PRODUCT DETAILS' drawer is collapsed state   
    When user clicks on 'PRODUCT DETAILS' drawer
    Then Verify 'PRODUCT DETAILS' drawer is expanded state
    Then Verify item details are displayed in the 'PRODUCT DETAILS' drawer    
    When user clicks on 'PRODUCT DETAILS' drawer
    Then Verify 'PRODUCT DETAILS' drawer is collapsed state
    
  Scenario: Validate PDP Size & Fit for Boys
    When User opens menu
    And User selects boys category from menu
    When User selects random subcategory array
    And User selects random product from product array
    And Verify product detail page is displayed
    
    Then Verify 'SIZE & FIT' section is displayed below the 'Add to Bag' button
    Then Verify 'SIZE & FIT' drawer is expanded state
    When user clicks on 'SIZE & FIT' drawer    
    Then Verify 'SIZE & FIT' drawer is collapsed state
    
    Then Verify 'PRODUCT DETAILS' section is displayed below the 'SIZE & FIT' drawer
    Then Verify 'PRODUCT DETAILS' drawer is collapsed state   
    When user clicks on 'PRODUCT DETAILS' drawer
    Then Verify 'PRODUCT DETAILS' drawer is expanded state
    Then Verify item details are displayed in the 'PRODUCT DETAILS' drawer    
    When user clicks on 'PRODUCT DETAILS' drawer
    Then Verify 'PRODUCT DETAILS' drawer is collapsed state
    
 Scenario: Validate PDP Size & Fit for Girls
    When User opens menu
    And User selects girls category from menu
    When User selects random subcategory array
    And User selects random product from product array
    And Verify product detail page is displayed
    
    Then Verify 'SIZE & FIT' section is displayed below the 'Add to Bag' button
    Then Verify 'SIZE & FIT' drawer is expanded state
    When user clicks on 'SIZE & FIT' drawer    
    Then Verify 'SIZE & FIT' drawer is collapsed state
    
    Then Verify 'PRODUCT DETAILS' section is displayed below the 'SIZE & FIT' drawer
    Then Verify 'PRODUCT DETAILS' drawer is collapsed state   
    When user clicks on 'PRODUCT DETAILS' drawer
    Then Verify 'PRODUCT DETAILS' drawer is expanded state
    Then Verify item details are displayed in the 'PRODUCT DETAILS' drawer    
    When user clicks on 'PRODUCT DETAILS' drawer
    Then Verify 'PRODUCT DETAILS' drawer is collapsed state