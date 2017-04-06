@PDPMonogram @Highlevel

Feature: PDP Monogram behavior

  Background: Open a PDP
    Given User goes to homepage
    And User closes email capture
    When User hovers on a random category from list
      | Women |
      | Men   |
      | Girls |
      | Boys  |

  Scenario: Monogram in PDP
    #id 143
    Then Verify shop monogram shop is available

    When User selects monogram shop from our shops
    And User selects random product from product array
    Then Verify product detail page is displayed
    And Verify user can add monogram to product

    When User selects random size
    And User adds monogram to product
    And User fills monogram options
    And User adds product to bag
    And User clicks in bag
    Then Verify shopping bag is displayed
    And Verify items quantity and prices

    When Click on change link from footer
    Then User is on context chooser page
    And User is on internal /r/context-chooser page

    When User selects CA country from context chooser page
    Then Verify user is in homepage

    When User clicks in bag
    Then Verify shopping bag is displayed
    #id 144
    And Verify monogram products are unavailable in bag

