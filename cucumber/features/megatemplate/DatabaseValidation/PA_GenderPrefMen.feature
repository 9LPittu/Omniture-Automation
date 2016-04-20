@PA
Feature: Popular in Your Area -  Gender prefernce for Men 
  
Scenario:  Popular in Your Area_Validate if men's gender score is 1 all the items should displayed for men in the feeds
	When user executes feed validation feedPopularinYourAreaMenGenderQuery sql in DB
   And user verify feed validation and log the results for the query feedPopularinYourAreaMenGenderQuery
   
					