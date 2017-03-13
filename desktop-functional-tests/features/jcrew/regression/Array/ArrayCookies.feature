@Array @HighLevel

Feature: Cookie validation in array pages

  Scenario Outline: Verify cookie path for category array pages
    Given User is on homepage with siteid
    And User closes email capture

	When User hovers on <gender> category from header
    And User selects <category> subcategory array

    Then Verify user is in <category> category array page
    And Verify jcrew_siteid cookie path value is /
    And Verify jcrew_srccode cookie path value is /

    Examples:
      | gender | category |
      | Women  | sweaters |
      | Men    | shorts   |