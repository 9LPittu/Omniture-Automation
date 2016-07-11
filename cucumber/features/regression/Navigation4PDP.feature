@PDP @HighLevel
Feature: Multiple Items Random

  Background:
    Given User bag is cleared
    And User is on homepage
    And Handle the Email Capture pop up

  Scenario: Validate PDP's work for Women
    When User clicks on hamburger menu
    And user selects WOMEN category from hamburger menu
    And user selects any subcategory
    And user selects any item from array page, select any color and size
    Then User is in product detail page
    
    Then Verify 'size & fit details' link is displayed above the 'Add to Bag' button    
    
    Then Verify 'SIZE & FIT' drawer is displayed below the 'Add to Bag' button   
    Then Verify 'SIZE & FIT' drawer is expanded state
    When user clicks on 'SIZE & FIT' drawer    
    Then Verify 'SIZE & FIT' drawer is collapsed state
    
    Then Verify 'PRODUCT DETAILS' drawer is displayed below the 'SIZE & FIT' drawer    
    Then Verify 'PRODUCT DETAILS' drawer is collapsed state   
    When user clicks on 'PRODUCT DETAILS' drawer
    Then Verify 'PRODUCT DETAILS' drawer is expanded state
    Then Verify item details are displayed in the 'PRODUCT DETAILS' drawer    
    When user clicks on 'PRODUCT DETAILS' drawer
    Then Verify 'PRODUCT DETAILS' drawer is collapsed state

  Scenario: Validate PDP's work for Men
    When User clicks on hamburger menu
    And user selects MEN category from hamburger menu
    And user selects any subcategory
    And user selects any item from array page, select any color and size
    Then User is in product detail page
    
    Then Verify 'size & fit details' link is displayed above the 'Add to Bag' button    
    
    Then Verify 'SIZE & FIT' drawer is displayed below the 'Add to Bag' button   
    Then Verify 'SIZE & FIT' drawer is expanded state
    When user clicks on 'SIZE & FIT' drawer    
    Then Verify 'SIZE & FIT' drawer is collapsed state
    
    Then Verify 'PRODUCT DETAILS' drawer is displayed below the 'SIZE & FIT' drawer    
    Then Verify 'PRODUCT DETAILS' drawer is collapsed state   
    When user clicks on 'PRODUCT DETAILS' drawer
    Then Verify 'PRODUCT DETAILS' drawer is expanded state
    Then Verify item details are displayed in the 'PRODUCT DETAILS' drawer    
    When user clicks on 'PRODUCT DETAILS' drawer
    Then Verify 'PRODUCT DETAILS' drawer is collapsed state

  Scenario: Validate PDP's work for Girls
    When User clicks on hamburger menu
    And user selects GIRLS category from hamburger menu
    And user selects any subcategory
    And user selects any item from array page, select any color and size
    Then User is in product detail page
    
    Then Verify 'size & fit details' link is displayed above the 'Add to Bag' button    
    
    Then Verify 'SIZE & FIT' drawer is displayed below the 'Add to Bag' button   
    Then Verify 'SIZE & FIT' drawer is expanded state
    When user clicks on 'SIZE & FIT' drawer    
    Then Verify 'SIZE & FIT' drawer is collapsed state
    
    Then Verify 'PRODUCT DETAILS' drawer is displayed below the 'SIZE & FIT' drawer    
    Then Verify 'PRODUCT DETAILS' drawer is collapsed state   
    When user clicks on 'PRODUCT DETAILS' drawer
    Then Verify 'PRODUCT DETAILS' drawer is expanded state
    Then Verify item details are displayed in the 'PRODUCT DETAILS' drawer    
    When user clicks on 'PRODUCT DETAILS' drawer
    Then Verify 'PRODUCT DETAILS' drawer is collapsed state

  Scenario: Validate PDP's work for Boys
    When User clicks on hamburger menu
    And user selects BOYS category from hamburger menu
    And user selects any subcategory
    And user selects any item from array page, select any color and size
    Then User is in product detail page
    
    Then Verify 'size & fit details' link is displayed above the 'Add to Bag' button    
    
    Then Verify 'SIZE & FIT' drawer is displayed below the 'Add to Bag' button   
    Then Verify 'SIZE & FIT' drawer is expanded state
    When user clicks on 'SIZE & FIT' drawer    
    Then Verify 'SIZE & FIT' drawer is collapsed state
    
    Then Verify 'PRODUCT DETAILS' drawer is displayed below the 'SIZE & FIT' drawer    
    Then Verify 'PRODUCT DETAILS' drawer is collapsed state   
    When user clicks on 'PRODUCT DETAILS' drawer
    Then Verify 'PRODUCT DETAILS' drawer is expanded state
    Then Verify item details are displayed in the 'PRODUCT DETAILS' drawer    
    When user clicks on 'PRODUCT DETAILS' drawer
    Then Verify 'PRODUCT DETAILS' drawer is collapsed state

  Scenario: Validate PDP's work for sweaters search
    When User presses search button
    And Enters sweaters to the search field
    And Clicks on search button for input field
    Then Search results are displayed
    And Selects the first product from product grid list
    And User is in product detail page
    
    Then Verify 'size & fit details' link is displayed above the 'Add to Bag' button    
    
    Then Verify 'SIZE & FIT' drawer is displayed below the 'Add to Bag' button   
    Then Verify 'SIZE & FIT' drawer is expanded state
    When user clicks on 'SIZE & FIT' drawer    
    Then Verify 'SIZE & FIT' drawer is collapsed state
    
    Then Verify 'PRODUCT DETAILS' drawer is displayed below the 'SIZE & FIT' drawer    
    Then Verify 'PRODUCT DETAILS' drawer is collapsed state   
    When user clicks on 'PRODUCT DETAILS' drawer
    Then Verify 'PRODUCT DETAILS' drawer is expanded state
    Then Verify item details are displayed in the 'PRODUCT DETAILS' drawer    
    When user clicks on 'PRODUCT DETAILS' drawer
    Then Verify 'PRODUCT DETAILS' drawer is collapsed state

#  Scenario: Verify preorder button is displayed
#    When User presses search button
#    And Enters 49923 to the search field
#    And Hits enter in search field
#    And Selects the first product from product grid list
#    Then Verify product is a pre-order one
#    And Preorder button is displayed
#    And A color is selected
#    And A size is selected
#    # step for estimated ship date message needs to be created here.
#    When Preorder button is pressed
#    Then A minicart modal should appear with message '1 item has been added to your cart.'