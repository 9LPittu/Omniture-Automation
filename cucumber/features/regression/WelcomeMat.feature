@WelcomeMat
Feature: International: Welcome Mat for ROW

Scenario:  Welcome mat display validation for international home page
  Given User is on clean session international /uk/ page
  And Welcome mat page is displayed

Scenario: Welcome mat display validation for international category page
  Given User is on clean session international /uk/c/womens_category/sweaters page
  And  Welcome mat page is displayed

Scenario: Welcome mat display validation for international PDP page
  Given User is on clean session international /uk/p/womens_category/sweaters/pullover/floral-sequinsleeve-sweater/E2983 page
  And  Welcome mat page is displayed

 #Scenario: Welcome mat display validation for international multi PDP page(bronze multipdp not vaailable, old not behaving as expected)
Scenario: Welcome mat display validation for international search array page
  Given User is on clean session international /uk/r/search/?N=0&Nloc=en&Ntrm=dresses&Npge=1&Nrpp=60&Nsrt=0 page
  And  Welcome mat page is displayed

  Scenario: Welcome mat display validation for international sale landing page
  Given User is on clean session international /uk/r/search/?N=21+17&Ntrm=&Nsrt=3&Npge=1&Nrpp=60 page
  And  Welcome mat page is displayed






