@Sanity @International-Part1 @HighLevel
Feature: International Country Context - Part 1

  Background:
    Given User goes to homepage
    And User closes email capture

  Scenario: Context chooser page validation
    Then User should see Ship To section in footer
    And Verify country name is displayed in the ship to section of footer
    And Verify change link is displayed in the ship to section of footer

    When Click on change link from footer
    Then User is on context chooser page
    And User is on internal /r/context-chooser page
    And UNITED STATES & CANADA region is displayed
    And ASIA PACIFIC region is displayed
    And EUROPE region is displayed
    And LATIN AMERICA & THE CARIBBEAN region is displayed
    And MIDDLE EAST & AFRICA region is displayed
    And Verify countries displayed correctly under each region
      | UNITED STATES & CANADA        |
      | ASIA PACIFIC                  |
      | EUROPE                        |
      | LATIN AMERICA & THE CARIBBEAN |
      | MIDDLE EAST & AFRICA          |

    When Click on "terms of use" link from terms section on the context chooser page
    Then Verify user is navigated to url /footer/termsofuse.jsp?sidecar=true on external page

    When Click on "privacy policy" link from terms section on the context chooser page
    Then Verify user is navigated to url /help/privacy_policy.jsp?sidecar=true on external page

    When Click on SEE ALL FAQ & HELP button from FAQ section on the context chooser page
    Then User is on internal /help/international_orders.jsp?sidecar=true page

    When User presses browser back button
    And Click on borderfree.com link from FAQ section on the context chooser page
    And External https://www.borderfree.com/#/ page is opened in a different tab


  