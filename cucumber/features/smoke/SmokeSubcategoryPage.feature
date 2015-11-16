@Subcategory
Feature: Subcategory Page

  Scenario: Subcategory Page functionality
    Given User is on homepage
    And User clicks on hamburger menu
    And Chooses a random category
    And Chooses a random subcategory
    And User should be in subcategory page
    When User hovers a product
    Then Proper details are shown for the hovered product

  # TODO: Needs to be data driven
  # Following Scenario was found to be an issue in CI, adding a test that will verify that it never happens again.
  #  Scenario: Zero size should be valid
  #    And User goes to /shirtsandtops/topsblouses/tiered-crepe-top/E3043 page
  #    And A color is selected
  #    And Size 0 is selected by user
  #    And Add to cart button is pressed
  #    Then A minicart modal should appear with message '1 item has been added to your cart.'