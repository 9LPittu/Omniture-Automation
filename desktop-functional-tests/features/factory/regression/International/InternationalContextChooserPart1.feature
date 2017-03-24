@International-Part1
Feature: International Country Context - Part 1

  Background:
    Given User goes to homepage
    And User closes email capture

  Scenario: Context chooser page validation
    Then User should see Ship To section in footer
    And Verify country name is displayed in the ship to section of footer
    And Verify change link is displayed in the ship to section of footer
    Then Click on change link from footer
    And User is on context chooser page
    And User is on internal /r/context-chooser page
    
    And Click on "terms of use" link from terms section on the context chooser page
    Then Verify user is navigated to url /footer/termsofuse.jsp?sidecar=true on external page
    And Click on "privacy policy" link from terms section on the context chooser page
    Then Verify user is navigated to url /help/privacy_policy.jsp?sidecar=true on external page