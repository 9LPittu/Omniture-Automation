@SaleEnablelinks
Feature: Sale Enable Links in Menu Nav

  Background:
    Given User is on homepage
    And User clicks on hamburger menu
    And Selects SALE Category from hamburger menu

  #Scenario: New in Sale link functional validation
    #And User clicks on NEW IN SALE subcategory from sale Category
    #And User is in sale results page
    #And User is in search results page
    
  Scenario Outline: Sale category links functional validation
  	And User selects <Category> category
  	Then verify SALE page is displayed
  	And verify REFINE button is displayed
  	And verify default filter name displayed is <Category>
  	Then click on REFINE button
  	And verify refinement page is displayed
  	Then verify <FilterNames> displayed on refinement page   	
  	Then verify NEW IN SALE checkbox is selected by default
  Examples:
  |Category|FilterNames|
  |Women|Category;Size;Color;Price;New In Sale|
  #|Men|
  #|Boys|
  #|Girls|