@Sale
Feature: Sale Regression Suite

  Background:
    Given User is on homepage
    And User clicks on hamburger menu
    And Selects sale Category from hamburger menu
 
  Scenario: New in Sale link functional validation
    And User clicks on NEW IN SALE subcategory from sale Category
    Then User is in Sale results page
    And Gender selectors are displayed
    And The products are sorted by New in sale
    And pagination is displayed on the page
    And the page url should contain "/r/search/?N=21+227"

  Scenario Outline: Sale category links functional validation
    And User clicks on <SaleCategory> subcategory from sale Category
    Then User is in Sale results page
    And Refine button is displayed
    And default filter name displayed is <SaleCategory>
    And Refine button is clicked
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
    Then NEW IN SALE checkbox is selected by default
    Then Click on done button for refinement filter menu
    And pagination is displayed on the page
  	And the page url should contain "<URL>"

  Examples:
    |SaleCategory|URL|
    |WOMEN|/r/search/?N=21+17|
    |MEN  |/r/search/?N=21+16|
    |BOYS |/r/search/?N=21+18|
    |GIRLS|/r/search/?N=21+19|
  
  Scenario: Pagination is functional on sale page
    And User clicks on WOMEN subcategory from sale Category
    And pagination is displayed on the page
    And left pagination text should be PREV
    And left pagination text PREV should be in disabled state by default
    And right pagination text should be NEXT
    And right pagination text NEXT should be in active state
    And pagination dropdown should display current page number
    And select random page number from pagination dropdown
    And user should be displayed with correct page when page number is changed

  Scenario Outline: Refine page should display the first sort option as new in sale and selected by default
    And User clicks on <SaleCategory> subcategory from sale Category
    Then User is in Sale results page
    And Refine button is displayed
    And default filter name displayed is <SaleCategory>
    And Refine button is clicked
    And User is in refine page
    And Category,Size,Color,Price filter refinements should appear
    Then first sort option is NEW IN SALE
    Then first option NEW IN SALE is selected by default
    Then PRICE: LOW TO HIGH is displayed as sort option
    Then PRICE: HIGH TO LOW is displayed as sort option

  Examples:
    |SaleCategory|
    |WOMEN|

  Scenario Outline: Sort by options user is able to select only one selection at a time on sale page
    And User clicks on <SaleCategory> subcategory from sale Category
    Then User is in Sale results page
    And Refine button is clicked
    And User is in refine page  
    Then select PRICE: HIGH TO LOW checkbox
    Then sort by options NEW IN SALE and PRICE: LOW TO HIGH are unchecked
    Then select PRICE: LOW TO HIGH checkbox
    Then sort by options NEW IN SALE and PRICE: HIGH TO LOW are unchecked
    Then select NEW IN SALE checkbox
    Then sort by options PRICE: LOW TO HIGH and PRICE: HIGH TO LOW are unchecked

  Examples:
    |SaleCategory|
    |WOMEN|
      
  Scenario Outline: New In Sale in sort by options is functional on sale page
    And User clicks on <SaleCategory> subcategory from sale Category
    Then User is in Sale results page
    And Refine button is clicked
    And User is in refine page
    Then select NEW IN SALE checkbox
    Then Click on done button for refinement filter menu
    And Search results are displayed  
    And the page url should contain "&Nsrt=3&"

  Examples:
    |SaleCategory|
    |WOMEN|

  Scenario Outline: Price: Low to High in sort by options is functional on sale page
    And User clicks on <SaleCategory> subcategory from sale Category
    Then User is in Sale results page
    And Refine button is clicked
    And User is in refine page
    Then select PRICE: LOW TO HIGH checkbox
    Then Click on done button for refinement filter menu
    Then sale prices are sorted correctly when PRICE: LOW TO HIGH is selected
    And the page url should contain "&Nsrt=1&"

  Examples:
    |SaleCategory|
    |WOMEN|

  Scenario Outline: Price: High to Low in sort by options is functional on sale page
    And User clicks on <SaleCategory> subcategory from sale Category
    Then User is in Sale results page
    And Refine button is clicked
    And User is in refine page
    Then select PRICE: HIGH TO LOW checkbox
    Then Click on done button for refinement filter menu
    Then sale prices are sorted correctly when PRICE: HIGH TO LOW is selected
    And the page url should contain "&Nsrt=2&"

  Examples:
    |SaleCategory|
    |WOMEN|
