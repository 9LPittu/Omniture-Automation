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
  Examples:
  |SaleCategory|
  |Women|