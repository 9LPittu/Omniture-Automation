@WelcomeMat
Feature: International: Welcome Mat for ROW
#US15294_TC01,US15294_TC03, US15294_TC05
Scenario: Welcome mat display validation for international home page
  Given User is on clean session international /uk/ page
  And Welcome mat page is displayed
  And Welcome mat header message is displayed as Hello, Canada for canada, Around the World for the ROW
  And JCrew Logo is present on the welcome mat
  #US15294_TC04
  #application(bronze, gold) not displaying corresponding flag and country at this time
  #And corresponding country name and flag is displayed
  And Welcome mat content is displayed
  #US15294-06 (?)
  And User clicks on start shopping button on welcome mat
  And User is on internal /uk/ page


Scenario: Welcome mat display validation for international category page
  Given User is on clean session international /uk/c/womens_category/sweaters page
  And Welcome mat page is displayed
  And JCrew Logo is present on the welcome mat

Scenario: Welcome mat display validation for international PDP page
  Given User is on clean session international /uk/p/womens_category/sweaters/pullover/floral-sequinsleeve-sweater/E2983 page
  And Welcome mat page is displayed

 #Scenario: Welcome mat display validation for international multi PDP page(bronze multipdp not vaailable, old not behaving as expected)
Scenario: Welcome mat display validation for international search array page
  Given User is on clean session international /uk/r/search/?N=0&Nloc=en&Ntrm=dresses&Npge=1&Nrpp=60&Nsrt=0 page
  And Welcome mat page is displayed

Scenario: Welcome mat display validation for international sale landing page
  Given User is on clean session international /uk/r/search/?N=21+17&Ntrm=&Nsrt=3&Npge=1&Nrpp=60 page
  And Welcome mat page is displayed

#US15294_TC02, (for US, welcome mat should not be displayed.for canada, it will but with diff message)
Scenario: Welcome mat display validation for US
  Given User is on homepage with clean session
  And Welcome mat page is not displayed







