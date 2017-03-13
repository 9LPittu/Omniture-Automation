@Home
Feature: Home Page

  Background:
    Given User goes to homepage
	And User closes email capture
  Scenario: Verification of Header and Footer section in the Home page
    Then Verify jcrew logo is visible
    Then Verify that top nav options contains Women
    Then Verify that top nav options contains Men
    Then Verify that top nav options contains Boys
    Then Verify that top nav options contains Girls
    Then Verify that top nav options contains Clearance
    Then Verify Returns & Exchanges link is displayed under Let Us Help You in footer
	Then Verify Shipping & Handling link is displayed under Let Us Help You in footer
	Then Verify Need Some Help link is displayed under Let Us Help You in footer
	Then Verify The J.Crew Gift Card link is displayed under Let Us Help You in footer

	Then Verify About Us link is displayed under About Factory in footer
	Then Verify Careers link is displayed under About Factory in footer

    Then Verify J.Crew link is displayed under Our Brands in footer
	Then Verify Madewell link is displayed under Our Brands in footer
	Then Verify J.Crew Mercantile link is displayed under Our Brands in footer

