@Preorder
Feature: Preorder Product Feature

  Scenario: Verify preorder button is displayed
    Given User goes to /p/womens_category/sweaters/pullover/striped-leather-panel-swing-sweater/C9220 page
    Then Verify product is a pre-order one
    And Preorder button is displayed
    And A color is selected
    And A size is selected
    #step for estimated ship date message needs to be created here.
    And Preorder button is pressed
    Then A minicart modal should appear with message '1 item has been added to your cart.'
