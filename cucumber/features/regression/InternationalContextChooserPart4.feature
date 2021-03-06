@Context_Part4 @HighLevel
Feature: International Country Context - Part 4

  Background:
    Given User is on homepage with clean session
    And Handle the Email Capture pop up

  Scenario Outline: Context is displayed on search page
    Then click on change link from footer
    And User is on context chooser page
    And User is on internal /r/context-chooser page
    And Handle the Email Capture pop up
    Given user selects <country_group> at random from context chooser page
    Then user should land on country specific home page
    And user should see selected country in the footer
    And User presses search button
    When Enters dresses to the search field
    And Clicks on search button for input field
    Then User is in search results page
    And user should see selected country in the footer
    When Selects the first product from product grid list
    Then User is in product detail page

    Examples:
      |country_group|
      |PRICEBOOK|
      |NONPRICEBOOK|

  Scenario Outline: Forgot Password Page context validtaion
    Then click on change link from footer
    And User is on context chooser page
    And User is on internal /r/context-chooser page
    And Handle the Email Capture pop up
    Given user selects <country_group> at random from context chooser page
    Then user should land on country specific home page
    And user should see selected country in the footer
    When Goes to sign in page
    And Clicks on forgot password link
    And Verify user is in forgot password page
    And user should see selected country in the footer

    Examples:
      |country_group |
      |PRICEBOOK     |
      |NONPRICEBOOK  |


  #multipdp, shoppable tray
  #Commenting it out as there is a jira JCSC-1131 for context not showing in th url
#  Scenario Outline: international context validation for shoppable tray page
#    Then click on change link from footer
#    And User is on context chooser page
#    And User is on internal /r/context-chooser page
#    Given user selects <country_group> at random from context chooser page
#    Then user should land on country specific home page
#    And user should see selected country in the footer
#    And User clicks on hamburger menu
#    And User selects random tray from available categories
#      | Men   | THIS MONTH'S FEATURES | 1 Suit, 5 Ways |
#    And user should see country code in the url for international countries
#
#    Examples:
#    |country_group|
#    |PRICEBOOK|
#    |NONPRICEBOOK|