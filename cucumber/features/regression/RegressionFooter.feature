@Footer
Feature: Footer Tests

  Background:
    Given User is on homepage

   #tc-01 and tc-02
   #US13389_TC14
  Scenario: Verification of Footer section in the page
    Then Verify CONTACT US header from footer is visible in homepage
    And Contact Us section twitter icon is displayed
    And Contact Us section phone icon is displayed
    And Contact Us section vps icon is displayed
    Then Verify Let Us Help You footer link is displayed
    And Verify Our Cards footer link is displayed
    And Verify Our Stores footer link is displayed
    And Verify Our Brands footer link is displayed
    And Verify About J.Crew footer link is displayed
    And Verify LIKE BEING FIRST? footer header legend is displayed
    And Verify email field is displayed
    And Verify Get To Know Us footer link is displayed
    And Verify facebook icon is displayed under Get To Know Us section
    And Verify twitter icon is displayed under Get To Know Us section
    And Verify tumblr icon is displayed under Get To Know Us section
    And Verify pinterest icon is displayed under Get To Know Us section
    And Verify instagram icon is displayed under Get To Know Us section
    And Verify google icon is displayed under Get To Know Us section
    And Verify youtube icon is displayed under Get To Know Us section

  #US13389_TC03    
  Scenario: Verify content groupings are displayed in footer section of all sidecar  pages
  	Then Verify Let Us Help You footer link is displayed
  	And user should see "Let Us Help You" content grouping in collapsed mode
    And Verify Our Cards footer link is displayed
    And user should see "Our Cards" content grouping in collapsed mode
    And Verify Our Stores footer link is displayed
    And user should see "Our Stores" content grouping in collapsed mode
    And Verify Our Brands footer link is displayed
    And user should see "Our Brands" content grouping in collapsed mode
    And Verify About J.Crew footer link is displayed
    And user should see "About J.Crew" content grouping in collapsed mode    
    
   #tc-04 starts below
  Scenario: Verification of Let Us Help You links display
    When Click on footer link Let Us Help You
    And Accordion should be expanded
    And Order Status sublink is displayed
    And Shipping & Handling sublink is displayed
    And Returns & Exchanges sublink is displayed
    And International Orders sublink is displayed
    And Size Charts sublink is displayed
    And Contact Us sublink is displayed
    And Request A Style Guide sublink is displayed

  Scenario: Verification of Let Us Help You Order Status link functionality
    When Click on footer link Let Us Help You
    And Click on sublink Order Status from Let Us Help You footer link
    Then Verify user is on order status page
    And User is on internal /help/order_status.jsp?sidecar=true page

  Scenario: Verification of Let Us Help You Shipping & Handling link functionality
    Then Click on footer link Let Us Help You
    And Click on sublink Shipping & Handling from Let Us Help You footer link
    And Verify user is on shipping & handling page
    And User is on internal /help/shipping_handling.jsp?sidecar=true page

  Scenario: Verification of Let Us Help You Returns And Exchanges link functionality
    Then Click on footer link Let Us Help You
    And Click on sublink Returns & Exchanges from Let Us Help You footer link
    And Verify user is on returns & exchanges page
    And User is on internal /help/returns_exchanges.jsp?sidecar=true page

  Scenario: Verification of Let Us Help You International Orders link functionality
    Then Click on footer link Let Us Help You
    And Click on sublink International Orders from Let Us Help You footer link
    And User is on internal /help/international_orders.jsp?sidecar=true page

  Scenario: Verification of Let Us Help You Size Charts link functionality
    Then Click on footer link Let Us Help You
    And Click on sublink Size Charts from Let Us Help You footer link
    And User is on internal /r/size-charts page

  Scenario: Verification of Let Us Help You Contact Us link functionality
    Then Click on footer link Let Us Help You
    And Click on sublink Contact Us from Let Us Help You footer link
    And User is on internal /footie/contactus.jsp?sidecar=true page

  Scenario: Verification of Let Us Help You Request A Style Guide link functionality
    Then Click on footer link Let Us Help You
    And Click on sublink Request A Style Guide from Let Us Help You footer link
    And Verify user is on request a catalog page

  #tc-05
  Scenario: Verification of Our Cards links display
    When Click on footer link Our Cards
    And The J.Crew Credit Card sublink is displayed
    And The J.Crew Gift Card sublink is displayed

  Scenario: Verification of Our Cards The J.Crew Credit Card link functionality
    Then Click on footer link Our Cards
    And Click on sublink The J.Crew Credit Card from Our Cards footer link
    And Verify user is on the j.crew credit card page
    And User is on internal /help/credit_card.jsp?sidecar=true page

  Scenario: Verification of Our Cards The J.Crew Gift Card link functionality
    Then Click on footer link Our Cards
    And Click on sublink The J.Crew Gift Card from Our Cards footer link
    And Verify user is on the j.crew gift card page
    And User is on internal /help/gift_card.jsp?sidecar=true page

  #tc-06
  Scenario: Verification of Our Stores links display and functional
    When Click on footer link Our Stores
    And Store Locator sublink is displayed
    And Click on sublink Store Locator from Our Stores footer link
    And Verify user is on help store locator page
    And User is on external https://stores.jcrew.com/?sidecar=true page

    #tc-07
  Scenario: Verification of About J.Crew links display
    When Click on footer link About J.Crew
    And Our Story sublink is displayed
    And Careers sublink is displayed
    And Social Responsibility sublink is displayed
    And Investor Relations sublink is displayed

  Scenario: Verification of Our Story About J.Crew link functionality
    Then Click on footer link About J.Crew
    And Click on sublink Our Story from About J.Crew footer link
    And Verify user is on about us page
    And User is on internal /aboutus/jcrew.jsp?sidecar=true page

  Scenario: Verification of About J.Crew Careers link functionality
    And Verify user is in homepage
    Then Click on footer link About J.Crew
    And Click on sublink Careers from About J.Crew footer link
    And Verify user is on careers page
    And User is on external https://jobs.jcrew.com/?sidecar=true page

  Scenario: Verification of About J.Crew Social Responsibility link functionality
    Then Click on footer link About J.Crew
    And Click on sublink Social Responsibility from About J.Crew footer link
    And Verify user is on social responsibility page
    And User is on internal /flatpages/social_responsibility.jsp?sidecar=true page

  Scenario: Verification of  About J.Crew Investor Relations link functionality
    Then Click on footer link About J.Crew
    And Click on sublink Investor Relations from About J.Crew footer link
    And User is on external http://investors.jcrew.com page

    #tc-08
  Scenario: Verification of Our Brands links display
    When Click on footer link Our Brands
    And J.Crew Factory sublink is displayed
    And Madewell sublink is displayed

  Scenario: Verifying Our Brands J.Crew Factory link funtionality
    Then Click on footer link Our Brands
    And Click on sublink J.Crew Factory from Our Brands footer link
    And Verify user is on the j.crew factory page
    And User is on external https://factory.jcrew.com/index.jsp?srcCode=JCFooter page

  Scenario: Verifying Our Brands Madewell link funtionality
    Then Click on footer link Our Brands
    And Click on sublink Madewell from Our Brands footer link
    And Verify user is on the madewell page
    And User is on external https://www.madewell.com/index.jsp?srcCode=JCFooter page
  
  #US13389_TC11  
  Scenario: Verify Tapping twice on the drawer opens and closes the drawer in  content groupings
    And user taps on "Our Brands" content grouping
    Then user should see "Our Brands" content grouping drawer should be opened
    And user taps on "Our Brands" content grouping
    Then user should see "Our Brands" content grouping drawer should be closed
    And user taps on "Our Brands" content grouping
    And user taps on "Our Cards" content grouping
    Then user should see "Our Brands" content grouping drawer should be closed
    Then user should see "Our Cards" content grouping drawer should be opened
    
  #US13389_TC12  
  Scenario: Verify email subscription field  is displaying under Like being First section and verify email subscription in the footer with invalid email address
   	Then user should see email subscription field under LIKE BEING FIRST section
   	And user should see default text in email field as 'Enter your email.'
   	Then enter email address as "invalidemail" in email field
   	And click on signup button in footer
   	Then user should see message as "Please enter a valid email address."   	
  
  Scenario: Verify email subscription in the footer with valid email address
  	Then enter email address as "test@gmail.com" in email field
   	And click on signup button in footer
   	Then user should see message as "THANK YOU...Your email has been added to the jcrew.com email list. Stay tuned for news about special offers and more."
   	
  #US13389_TC13
  # Scenario Outline: Ship To section is visible and functional in footer
    # (SHIP TO is not present, change in application. For more information look SC-572)
    # Then user should see Ship To section in footer
    # And verify country name is displayed in the ship to section of footer
    # And verify change link is displayed in the ship to section of footer
    # Then click on change link from footer
    # And User is on /intl/context_chooser.jsp?sidecar=true page
    # And select country as "<country>"
    # Then user should see "<country>" in footer
    # Examples:
      # |country|
      # |Canada|
   
  #US13389_TC14
  Scenario: Verify facebook icon in social sharing section is functional
    And click on facebook icon in social sharing section
    And User is on external facebook page
  
  Scenario: Verify twitter icon in social sharing section is functional
    And click on twitter icon in social sharing section
    And User is on external twitter page
  
  Scenario: Verify tumblr icon in social sharing section is functional
    And click on tumblr icon in social sharing section
    And User is on external http://jcrew.tumblr.com/ page
  
  Scenario: Verify pinterest icon in social sharing section is functional
    And click on pinterest icon in social sharing section
    And User is on external https://www.pinterest.com/jcrew/ page
  
  Scenario: Verify instagram icon in social sharing section is functional
    And click on instagram icon in social sharing section
    And User is on external https://www.instagram.com/jcrew/ page
  
  Scenario: Verify google icon in social sharing section is functional
    And click on google icon in social sharing section
    And User is on external https://plus.google.com/+JCrew page
  
  Scenario: Verify youtube icon in social sharing section is functional
    And click on youtube icon in social sharing section
    And User is on external youtube page
  
  #US13389_TC15 
  Scenario: Verify legal links are displayed in footer section of all sidecar  pages
  	And user should see legal links section in the footer
  	And user should see "TERMS OF USE" in the legal links section of footer
  	And user should see "PRIVACY POLICY" in the legal links section of footer
  	And user should see "2016 J.Crew" in the legal links section of footer
  
  Scenario: Verify TERMS OF USE legal link is functional in footer section of all sidecar pages
    And click on "TERMS OF USE" in the legal links section of footer    
    And User is on internal /footer/termsofuse.jsp?sidecar=true page
    
  Scenario: Verify PRIVACY POLICY legal link is functional in footer section of all sidecar pages
    And click on "PRIVACY POLICY" in the legal links section of footer
    And User is on internal /help/privacy_policy.jsp?sidecar=true page
  
  Scenario: Verify copyright text in the legal links section is not link
  	And "2016 J.Crew" should not be displayed as a link
    
  #US13389_TC16  
  Scenario: Verify visit full site is displayed and functional in footer section of all sidecar  pages
   	And user should see visit full site displayed after legal links in footer section
   	And click on view full site link
   	And User is on internal /index.jsp?sidecar=false page