@PA
Feature: Popular in Your Area - Gender score null defult is Women 

   
Scenario:  Popular in Your Area_Validate if gender score is null item should default to women’s
	When user executes feed validation feedPopularinYourAreaWomenGenderNullQuery sql in DB
   And user verify feed validation and log the results for the query feedPopularinYourAreaWomenGenderNullQuery
   
