@SaleLanding1
Feature: Sale Landing functionality-Part1

  Background:
    Given User goes to homepage
    And User closes email capture
    When User clicks on crew clearance link from top nav
    Then Verify sale landing page is displayed

   
  Scenario: Verify url, title and category links on Sale landing page
   	And Verify url is /r/sale
    And Verify following links are displayed on sale landing page
    |women|
	|men|
	|girls|
	|boys|
	And Verify crew clearance title is displayed on sale landing page
	
   Scenario Outline: Verify category links are functional on Sale Landing page
    When User selects <saleCategory> dept from sales
	Then Verify Sale array page is displayed
	And Verify url is <url>
	And Verify filters are displayed on array page
	And Verify gender filter displays <saleCategory>
	And Verify that search result number is greater than 0
  
  	Examples:
  	|saleCategory|url|
  	|women|/r/search/?N=217+17&Ntrm=&Nsrt=3&Npge=1&Nrpp=60|
	|men|/r/search/?N=217+16&Ntrm=&Nsrt=3&Npge=1&Nrpp=60|
	|girls|/r/search/?N=217+19&Ntrm=&Nsrt=3&Npge=1&Nrpp=60|
	|boys|/r/search/?N=217+18&Ntrm=&Nsrt=3&Npge=1&Nrpp=60|