@Category @HighLevel
Feature: Cookie validation in array pages

  Scenario Outline: Verify cookie path for category array pages
    Given User is on homepage with siteid
    And Handle the Email Capture pop up

    When User clicks on hamburger menu
    And user selects <gender> category from hamburger menu
    When User clicks on <category> subcategory from <gender> Category

    Then User should be in subcategory page
    And Verify jcrew_siteid cookie path value is /
    And Verify jcrew_srccode cookie path value is /


    Examples:
      | gender | category |
      | Women  | sweaters |


