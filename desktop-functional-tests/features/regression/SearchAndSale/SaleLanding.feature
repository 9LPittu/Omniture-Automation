@SaleLanding @HighLevel
Feature: Sale Landing functionality

  Background:
    Given User goes to homepage
    And User closes email capture
    When User clicks on sale link from top nav
    Then Verify sale landing page is displayed

   
  Scenario: Verify url, title and category links on Sale landing page
   	And Verify url is /r/sale
    And Verify following links are displayed on sale landing page
    |women|
	|men|
	|girls|
	|boys|
	|new in sale|
	And Verify Sale title is displayed on sale landing page
	
  Scenario: Verify first promo on Sale landing page
	And Verify first promo is displayed
	When User clicks on details link on first promo
	Then Verify promo details pop up is open
	When User clicks on close icon on promo detail pop up
	Then Verify promo details pop up is closed


  Scenario Outline: Verify category links are functional on Sale Landing page
    When User selects <saleCategory> dept from sales
	Then Verify Sale array page is displayed
	And Verify url is <url>
	And Verify filters are displayed on array page
	And Verify gender filter displays <saleCategory>
	And Verify that search result number is greater than 0
  
  	Examples:
  	|saleCategory|url|
  	|women|/r/search/?N=21+17&Ntrm=&Nsrt=3&Npge=1&Nrpp=60|
	|men|/r/search/?N=21+16&Ntrm=&Nsrt=3&Npge=1&Nrpp=60|
	|girls|/r/search/?N=21+19&Ntrm=&Nsrt=3&Npge=1&Nrpp=60|
	|boys|/r/search/?N=21+18&Ntrm=&Nsrt=3&Npge=1&Nrpp=60|
	
	
  Scenario: Verify New In sale link is fuctional on sale landing page
	When User selects new in sale dept from sales
	Then Verify Sale array page is displayed
	And Verify url is /r/search/?N=21+227&Ntrm=&Nsrt=3&Npge=1&Nrpp=60
	And Verify filters are displayed on array page
	And Verify following gender selectors are displayed
	|women|
	|men|
	|girls|
	|boys|
	And Verify Sort By filter displays New in Sale
	And Verify that search result number is greater than 0
	
  Scenario Outline: Verify Second Promo
	Then Verify Second promo is displayed on sale landing page
	When User clicks on <gender> link from second promo
	Then Verify Sale array page is displayed
	And Verify gender filter displays <gender>
	And Verify that search result number is greater than 0

	Examples:
	|gender|
	|women|
	|men|
	|girls|
	|boys|
