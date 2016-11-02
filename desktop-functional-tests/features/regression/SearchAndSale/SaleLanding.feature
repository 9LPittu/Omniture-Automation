@sale
Feature: Sale Landing functionality

  Background:
    Given User goes to homepage
    And User closes email capture
    When User clicks on sale link from top nav

  Scenario: Verify Sale Landing Page
    Then Verify sale landing page is displayed
    And Verify url is /r/sale
    
   Scenario: Verify category links are displayed on Sale Landing page
    Then Verify sale landing page is displayed
    And Verify following links are displayed on sale landing page
    |women|
	|men|
	|girls|
	|boys|
	|new in sale|
	
	
	Scenario Outline: Verify category links are functional on Sale Landing page
    Then Verify sale landing page is displayed
    When User selects <saleCategory> dept from sales
	Then Verify Sale array page is displayed
	And Verify url is <url>
  	Examples:
  	|saleCategory|url|
  	|women|/r/search/?N=21+17&Ntrm=&Nsrt=3&Npge=1&Nrpp=60|
	|men|/r/search/?N=21+16&Ntrm=&Nsrt=3&Npge=1&Nrpp=60|
	|girls|/r/search/?N=21+19&Ntrm=&Nsrt=3&Npge=1&Nrpp=60|
	|boys|/r/search/?N=21+18&Ntrm=&Nsrt=3&Npge=1&Nrpp=60|
	
      
	

	
	
