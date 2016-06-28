#@Context_Part2
Feature: International Country Context - Part 2

  Background:
    Given User is on homepage with clean session
    And Handle the Email Capture pop up

  Scenario Outline: Multiple Pages During Checkout Context validations
    Then click on change link from footer
    And User is on context chooser page
    And User is on internal /r/context-chooser page
    Given user selects <country_group> at random from context chooser page
    Then user should land on country specific home page
    And user should see selected country in the footer
    When User clicks on hamburger menu
    And user selects any category from hamburger menu
	And user selects any subcategory
    And user should see selected country in the footer
	And user should see country code in the url for international countries
	And user selects any item from array page, select any color and size
    And User is in product detail page
    And user should see selected country in the footer
    And user should see country code in the url for international countries
    And Add to cart button is pressed
    And User clicks on item bag
    Then User should be in shopping bag page
    Then Verify embedded headers links is visible
    Then Verify embedded footer is visible and functional
    And user should see selected country in the footer
    And Clicks edit button on item bag page
	And User is in product detail page
    Then Update Bag button is pressed
	Then User should be in shopping bag page
    And Clicks on checkout
    Then Verify embedded headers links is visible
    Then Verify embedded footer is visible and functional
    And user should see selected country in the footer
    And Selects to checkout as guest
    Then Verify embedded headers links is visible
    Then Verify embedded footer is visible and functional
    And user should see selected country in the footer
    When user fills selected country shipping address
    And Presses continue button on shipping address
    And Verifies is in shipping method page
    Then Verify embedded headers links is visible
    Then Verify embedded footer is visible and functional
    And user should see selected country in the footer
    And Uses default value for shipping method
    #And Uses default value for gifts option
    And Clicks continue button on shipping method page
    And Verify user is in billing page
    Then Verify embedded headers links is visible
    Then Verify embedded footer is visible and functional
    And user should see selected country in the footer
    And Fills required payment data in billing page
    And Submits payment data in billing page
    Then Verify embedded headers links is visible
    Then Verify embedded footer is visible and functional
    And user should see selected country in the footer
    And Clicks on place your order
    And User should be in order confirmation page
    Then Verify embedded headers links is visible
    Then Verify embedded footer is visible and functional
    And user should see selected country in the footer

    Examples:
      |country_group|
      |PRICEBOOK|
      |NONPRICEBOOK|

  Scenario Outline: context validation on sale landing page from Hamburger menu
    Then click on change link from footer
    And User is on context chooser page
    And User is on internal /r/context-chooser page
    Given user selects <country_group> at random from context chooser page
    Then user should land on country specific home page
    And user should see selected country in the footer
    And User clicks on hamburger menu
    And Selects sale Category from hamburger menu
    And user should see selected country in the footer
    And user should see country code in the url for international countries
    And User clicks on <sale_category> subcategory from Sales
    And user should see selected country in the footer
    And Selects any product from product grid list
    And User is in product detail page
    Then Verify embedded footer is visible and functional
    And user should see country code in the url for international countries

    Examples:
      |country_group|sale_category|
      |PRICEBOOK    |Women        |
      |NONPRICEBOOK |Women        |
      |PRICEBOOK    |Men          |
      |NONPRICEBOOK |Men          |
      |PRICEBOOK    |Girls        |
      |NONPRICEBOOK |Girls        |
      |PRICEBOOK    |Boys         |
      |NONPRICEBOOK |Boys         |

  Scenario Outline: Context is displayed on search page
    Then click on change link from footer
    And User is on context chooser page
    And User is on internal /r/context-chooser page
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