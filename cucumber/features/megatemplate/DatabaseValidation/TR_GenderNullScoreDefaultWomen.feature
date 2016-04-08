@TR
Feature: Top Rated - Gender score is null item should default to women’s 
   
Scenario:  Top Rated_Validate if gender score is null item should default to women’s
	When user executes feed validation feedTopratedWomenGenderNullQuery sql in DB
   And user verify feed validation and log the results for the query feedTopratedWomenGenderNullQuery
   
