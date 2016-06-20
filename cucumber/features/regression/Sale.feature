 @HighLevel
Feature: Sale Regression Suite

  Background:
    Given User is on homepage
    And User clicks on hamburger menu
    And Selects sale Category from hamburger menu

   # US9874_TC06
  Scenario: New in Sale link functional validation
    And User clicks on NEW IN SALE subcategory from Sales
    Then User is in Sale results page
    And Gender selectors are displayed
    And The products are sorted by New in sale
    And pagination is displayed on the page
    And the page url should contain "/r/search/?N=21+227"

   #US9874_TC07,US9874_TC08, US9874_TC09, US9874_TC10, US15673_TC03
  Scenario Outline: Sale category links functional validation
    And User is in sale landing page
    And Sale title is displayed
    And User clicks on sale department <SaleCategory>
    Then User is in Sale results page
    And Refine button is displayed
    And default filter name displayed is <SaleCategory>
    And the page url should contain "<URL>"

    Examples:
      |SaleCategory|URL|
      |women|/r/search/?N=21+17|
      |men  |/r/search/?N=21+16|
      |boys |/r/search/?N=21+18|
      |girls|/r/search/?N=21+19|

  Scenario: Pagination is functional on sale page
    And User clicks on WOMEN subcategory from Sales
    And pagination is displayed on the page
    And left pagination text should be PREV
    And left pagination text PREV should be in disabled state by default
    And right pagination text should be NEXT
    And right pagination text NEXT should be in active state
    And pagination dropdown should display current page number
    And select random page number from pagination dropdown
    And user should be displayed with correct page when page number is changed

  #Merged in one scenario
  #Scenario Outline: Refine page should display the first sort option as new in sale and selected by default
  #Scenario Outline: Sort by options user is able to select only one selection at a time on sale page
  #Scenario Outline: New In Sale in sort by options is functional on sale page
  #Scenario Outline: Price: Low to High in sort by options is functional on sale page
  #Scenario Outline: Price: High to Low in sort by options is functional on sale page
  Scenario: Sort functionality
    And User is in sale landing page
    And Sale title is displayed
    And First promo is displayed with promo message and promo code
    And User clicks on sale department women
    Then User is in Sale results page
    And Refine button is displayed
    And default filter name displayed is women
    And click on REFINE button
    And User is in refine page
    And Category,Size,Color,Price filter refinements should appear
    And Click on Category refinement
    And Verify Category refinement drawer remains open
    And Click on Size refinement
    And Verify Size refinement drawer remains open
    And Click on Color refinement
    And Verify Color refinement drawer remains open
    And Click on Price refinement
    And Verify Price refinement drawer remains open
    Then NEW IN SALE sort option is selected by default
    Then first sort option is NEW IN SALE
    Then PRICE: LOW TO HIGH is displayed as sort option
    Then PRICE: HIGH TO LOW is displayed as sort option
    Then select PRICE: HIGH TO LOW checkbox
    Then sort by options NEW IN SALE and PRICE: LOW TO HIGH are unchecked
    Then Click on done button for refinement filter menu
    Then sale prices are sorted correctly when PRICE: HIGH TO LOW is selected
    And the page url should contain "&Nsrt=2&"
    Then select PRICE: LOW TO HIGH checkbox
    And the page url should contain "&Nsrt=1&"
    Then sort by options NEW IN SALE and PRICE: HIGH TO LOW are unchecked
    Then Click on done button for refinement filter menu
    Then sale prices are sorted correctly when PRICE: LOW TO HIGH is selected
    Then select NEW IN SALE checkbox
    Then sort by options PRICE: LOW TO HIGH and PRICE: HIGH TO LOW are unchecked
    And the page url should contain "&Nsrt=3&"

  #US15452_TC001 & US15452_TC002 #US9874_TC05
  Scenario: 'Sale' in menu nav should link to Sale Landing page
    And User is in sale landing page
    And New in Sale Category link and carat sign is displayed
    And women Category link and carat sign is displayed
    And men Category link and carat sign is displayed
    And girls Category link and carat sign is displayed
    And boys Category link and carat sign is displayed
    And User clicks on sale department women
    Then User is in Sale results page
    And User clicks on hamburger menu
    And Hamburger Menu Women Link is present
    And Hamburger Menu Men Link is present
    And Hamburger Menu Girls Link is present
    And Hamburger Menu Boys Link is present
    And Hamburger Menu sale Link is present
    And Hamburger Menu Wedding Link is present
    And Hamburger Menu Blog Link is present

    #US15452_TC003 Moved to BreadCrumbs.feature

    #US15452_TC004
  Scenario Outline:'sale' breadcrumb functionality from sale PDP
    And User clicks on sale department <Sale_Dept>
    Then User is in Sale results page
    And Selects any product from product grid list
    And User is in product detail page
    When Clicks on Sale Breadcrumb
    And User is in sale landing page

    Examples:
      |Sale_Dept|
      |New in Sale|
      |women    |
      |men      |
      |girls    |
      |boys     |

    #US9874_TC01 (#US15452_TC005 is US9874), #US9874_TC03, #US9874_TC04(no automation)
    #US9874_TC02 no automation , verification of match up with mock
    #Merged with: Scenario Outline: Sale category links functional validation
    #Scenario: Verify header copy "sale" should be displayed on the sale page.

    #US9874_TC11
  @Sale
  Scenario Outline: Validation of second promo display
    And User is in sale landing page
    And Second promo is displayed
    And <promo_link> sale category link is displayed and "<URL>" is valid in the second promo
    When <promo_link> in sale page is clicked
    Then the page url should contain "<URL>"

    Examples:
      |promo_link|URL|
      |women|N=21+17|
      |men  |N=21+16|
#      |boys |N=21+18|
#      |girls|N=21+19|

    #US9874_TC12, #US9874_TC13, #US9874_TC14
#    Scenario: Details link display and functionality
#      And User is in sale landing page
#      And Details link is displayed
#      And Click on details link
#      And Promo legal text is displayed
#      And Click on details section close icon
#      And Details section is closed

    #US15673_TC01
  Scenario: Sale Refine Single Select
    And User clicks on sale department random
    Then User is in Sale results page
    And Refine button is clicked
    And User is in refine page
    And Click on Category refinement
    And Select random single option from Category refinement
    Then Verify selected value is displayed next to Category refinement
    Then Verify that Category refinement is closed