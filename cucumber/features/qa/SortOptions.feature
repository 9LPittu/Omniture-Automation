@SortOptions
Feature: Sort Options

  Background:
    Given User is on homepage
    And User clicks on hamburger menu
    And Selects SALE Category from hamburger menu  
    
  Scenario Outline: Refine page should display the first sort option as new in sale and selected by default
  	And User selects <SaleCategory> category
  	Then verify SALE page is displayed
  	And verify REFINE button is displayed
  	And verify default filter name displayed is <SaleCategory>
  	Then click on REFINE button
  	And verify refinement page is displayed
  	Then verify CATEGORY filter displayed on refinement page
  	Then verify SIZE filter displayed on refinement page
  	Then verify COLOR filter displayed on refinement page  	
  	Then verify PRICE filter displayed on refinement page 	
  	Then verify first sort option is NEW IN SALE
  	Then verify first option NEW IN SALE is selected by default
  	Then verify PRICE: LOW TO HIGH is displayed as sort option
  	Then verify PRICE: HIGH TO LOW is displayed as sort option  	
  Examples:
  |SaleCategory|
  |Women|
  
  Scenario Outline: Sort by options user is able to select only one selection at a time
  	And User selects <SaleCategory> category
  	Then verify SALE page is displayed
  	Then click on REFINE button
  	And verify refinement page is displayed  	
  	Then verify other sort by options are unchecked when PRICE: LOW TO HIGH is selected
  	Then verify other sort by options are unchecked when PRICE: HIGH TO LOW is selected
  	Then verify other sort by options are unchecked when NEW IN SALE is selected
  Examples:
  |SaleCategory|
  |Women|
  
  Scenario Outline: Price: Low to High in sort by options is functional
  	And User selects <SaleCategory> category
  	Then verify SALE page is displayed
  	Then click on REFINE button
  	And verify refinement page is displayed
  	Then select PRICE: LOW TO HIGH checkbox
  	Then click on DONE button on Refine page
  	Then verify sale prices are sorted correctly when PRICE: LOW TO HIGH is selected
  Examples:
  |SaleCategory|
  |Women|	
  
  Scenario Outline: Price: High to Low in sort by options is functional
  	And User selects <SaleCategory> category
  	Then verify SALE page is displayed
  	Then click on REFINE button
  	And verify refinement page is displayed
  	Then select PRICE: HIGH TO LOW checkbox
  	Then click on DONE button on Refine page
  	Then verify sale prices are sorted correctly when PRICE: HIGH TO LOW is selected
  Examples:
  |SaleCategory|
  |Women|