@PA
Feature: Popular in Your Area - Gender Preference fo Wormen 
   
Scenario:  Popular in Your Area_Validate if Women's gender score is 1 all the items should displayed for women in the feeds
	When user executes feed validation feedPopularinYourAreaWomenGenderQuery sql in DB
   And user verify feed validation and log the results for the query feedPopularinYourAreaWomenGenderQuery
