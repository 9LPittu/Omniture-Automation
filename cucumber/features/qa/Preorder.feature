@Preorder
Feature: Preorder Product Feature

  Scenario Outline:
    Given User goes to <pre-order item pdp> page
    Then Verify product is a pre-order one
    And Preorder button is displayed
    And A color is selected
    And A size is selected
    #step for estimated ship date message needs to be created here.
    And Preorder button is pressed
    Then A minicart modal should appear with message '1 item has been added to your cart.'

    Examples:
    |pre-order item pdp|
    |/p/womens_category/shoes/wedges/seville-wedge-espadrilles/C6037|
