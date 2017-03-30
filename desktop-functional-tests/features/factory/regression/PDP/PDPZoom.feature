@PDPZoom

Feature: PDP Zoom

  Background: Open a PDP
    Given User goes to homepage
    And User closes email capture
    When User hovers on a random category from list
      | Women |
      | Men   |
      | Girls |
      | Boys  |
    And User selects random subcategory array
    And User selects random product from product array
    Then Verify product detail page is displayed

  Scenario: Hover image zooms image
    When User hovers selected image
    Then Verify selected image is zoomed

    When User hovers JCrew logo
    Then Verify selected image is not zoomed

  Scenario: Click image opens modal
    When User clicks selected image
    Then Verify zoom modal is displayed