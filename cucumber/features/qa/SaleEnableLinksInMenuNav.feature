@SaleEnablelinks
Feature: Sale Enable Links in Menu Nav

  Background:
    Given User is on homepage
    And User clicks on hamburger menu
    And Selects sale Category from hamburger menu

  #Scenario: New in Sale link functional validation
    #And User clicks on NEW IN SALE subcategory from sale Category
    #And User is in sale results page
    #And User is in search results page
    
  Scenario Outline: Sale category links functional validation
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
  	Then verify accordion drawer is displayed when CATEGORY filter is clicked
  	Then verify accordion drawer is displayed when SIZE filter is clicked
  	Then verify accordion drawer is displayed when COLOR filter is clicked
  	Then verify accordion drawer is displayed when PRICE filter is clicked
  	Then verify NEW IN SALE checkbox is selected by default  	
  	Then verify CATEGORY accordion drawer is not displayed
  	Then verify SIZE accordion drawer is not displayed
  	Then verify COLOR accordion drawer is not displayed
  Examples:
  |SaleCategory|
  |Women|
 # |Men|
 # |Boys|
  #|Girls|