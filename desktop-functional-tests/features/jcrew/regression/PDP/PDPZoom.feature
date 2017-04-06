@PDPZoom @Highlevel

Feature: PDP Images zoom and thumbnails

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

  #id 124
  Scenario: Hover image zooms image and thumbnails are present
    When User hovers selected image
    Then Verify selected image is zoomed

    When User hovers JCrew logo
    Then Verify selected image is not zoomed
    #id 125
    And Verify product has thumbnail selected by default
    And Verify only non EIEC shots are displayed as thumbnails

  #id 124
  Scenario: Click image opens modal and thumbnails are present
    #id 125
    Then Verify product has thumbnail selected by default
    And Verify only non EIEC shots are displayed as thumbnails

    When User clicks selected image
    Then Verify zoom modal is displayed