Feature: Footer Verification In Home Page

  Background:
    Given User is on homepage
    And Handle the Email Capture pop up

    #Moved from HomePage.feature
    #US9724_TC01 from BreadCrumbs.feature
  Scenario: Homepage embedded Header, Footer and logo
    Then Verify embedded headers are visible and functional
    Then Verify embedded footer is visible and functional
    And JCrew Logo is present
    Then Verify MENU header link is displayed
    And Verify SEARCH header link is displayed
    And Verify STORES header link is displayed
    And Verify BAG header link is displayed
    And Verify MENU, SEARCH, STORES header links including bag order is valid, ignore SIGN IN, MY ACCOUNT

   #tc-01 and tc-02
   #US13389_TC14
   #US13389_TC03
   #US13389_TC12
   #US13389_TC11

  Scenario: Verification of Footer section in the page
    Then Verify Contact Us header from footer is visible in homepage
    And Contact Us section twitter icon is displayed
    And Contact Us section phone icon is displayed
    And Contact Us section vps icon is displayed
    Then Verify Let Us Help You footer link is displayed
    And Verify The J.Crew Credit Card footer link is displayed
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
  	Then Verify Let Us Help You footer link is displayed
  	And user should see "Let Us Help You" content grouping in collapsed mode
    And Verify The J.Crew Credit Card footer link is displayed
    And user should see "The J.Crew Credit Card" content grouping in collapsed mode
    And Verify Our Stores footer link is displayed
    And user should see "Our Stores" content grouping in collapsed mode
    And Verify Our Brands footer link is displayed
    And user should see "Our Brands" content grouping in collapsed mode
    And Verify About J.Crew footer link is displayed
    And user should see "About J.Crew" content grouping in collapsed mode
    And Click on footer link Our Brands to open
    Then user should see "Our Brands" content grouping drawer should be opened
    And Click on footer link Our Brands to close
    Then user should see "Our Brands" content grouping drawer should be closed
    And Click on footer link Our Brands to open
    And Click on footer link The J.Crew Credit Card to open
    Then user should see "Our Brands" content grouping drawer should be closed
    Then user should see "The J.Crew Credit Card" content grouping drawer should be opened
    Then user should see email subscription field under LIKE BEING FIRST section
    And user should see default text in email field as 'Enter your email.'
    Then enter email address as "invalidemail" in email field
    And click on signup button in footer
    Then user should see message as "Please enter a valid email address."
    Then enter email address as "test@gmail.com" in email field
    And click on signup button in footer
    Then user should see message as "THANK YOU...Your email has been added to the jcrew.com email list. Stay tuned for news about special offers and more."

   #tc-04 starts below

  Scenario: Verification of Let Us Help You links display
    When Click on footer link Let Us Help You to open
    And Accordion should be expanded
    And Order Status sublink is displayed
    And Shipping & Handling sublink is displayed
    And Returns & Exchanges sublink is displayed
    And International Orders sublink is displayed
    And Size Charts sublink is displayed
    And Need Some Help? sublink is displayed
    And Request A Style Guide sublink is displayed
    When Click on footer link Let Us Help You to open
    And Click on sublink Order Status from Let Us Help You footer link
    Then Verify user is on order status page
    And User is on internal /help/order_status.jsp?sidecar=true page
    And Verify J crew breadcrumb is not displayed
    And Verify Embedded header is displayed
    And User presses back button
    When Click on footer link Let Us Help You to open
    And Click on sublink Shipping & Handling from Let Us Help You footer link
    And Verify user is on shipping & handling page
    And User is on internal /help/shipping_handling.jsp?sidecar=true page
    And User presses back button
    When Click on footer link Let Us Help You to open
    And Click on sublink Returns & Exchanges from Let Us Help You footer link
    And Verify user is on returns & exchanges page
    And User is on internal /help/returns_exchanges.jsp?sidecar=true page
    And User presses back button
    When Click on footer link Let Us Help You to open
    And Click on sublink International Orders from Let Us Help You footer link
    And User is on internal /help/international_orders.jsp?sidecar=true page
    And User presses back button
    When Click on footer link Let Us Help You to open
    And Click on sublink Size Charts from Let Us Help You footer link
    And User is on internal /r/size-charts page
    Then Breadcrumb should display J.Crew
    And Clicks on J.Crew Breadcrumb
    And Verify user is in homepage
    And User presses back button
    When Click on footer link Let Us Help You to open
    And Click on sublink Need Some Help? from Let Us Help You footer link
    And User is on internal /footie/contactus.jsp?sidecar=true page
    And Verify J crew breadcrumb is not displayed
    And Verify Embedded header is displayed
    And User presses back button
    When Click on footer link Let Us Help You to open
    And Click on sublink Request A Style Guide from Let Us Help You footer link
    And Verify user is on request a catalog page
    And Verify J crew breadcrumb is not displayed
    And Verify Embedded header is displayed
    And User presses back button

  #tc-05
  #Merge
  Scenario: Verification of The J.Crew Credit Card links functionality
    When Click on footer link The J.Crew Credit Card to open
    And The J.Crew Credit Card sublink is displayed
    And The J.Crew Gift Card sublink is displayed
    Then Click on footer link The J.Crew Credit Card to open
    And Click on sublink Apply Today & Get 15% Off* from The J.Crew Credit Card footer link
    And Verify user is on the j.crew credit card page
    And User is on internal /help/credit_card.jsp?sidecar=true page
    And Verify J crew breadcrumb is not displayed
    And Verify Embedded header is displayed
    And User presses back button
    Then Click on footer link The J.Crew Credit Card to open
    And Click on sublink Manage Your Card from The J.Crew Credit Card footer link
    And User is on internal https://d.comenity.net/jcrew/ page

  #tc-06
    #tc-07
  Scenario: Verification of About J.Crew links functionality
    When Click on footer link About J.Crew to open
    And Our Story sublink is displayed
    And Careers sublink is displayed
    And Social Responsibility sublink is displayed
    And Investor Relations sublink is displayed
    Then Click on footer link About J.Crew to open
    And Click on sublink Our Story from About J.Crew footer link
    And Verify user is on about us page
    And User is on internal /aboutus/jcrew.jsp?sidecar=true page
    And Verify J crew breadcrumb is not displayed
    And Verify Embedded header is displayed
    And User presses back button
    Then Click on footer link About J.Crew to open
    And Click on sublink Careers from About J.Crew footer link
    And Verify user is on careers page
    And User is on external https://jobs.jcrew.com/?sidecar=true page
    And User presses back button
    Then Click on footer link About J.Crew to open
    And Click on sublink Social Responsibility from About J.Crew footer link
    And Verify user is on social responsibility page
    And User is on internal /flatpages/social_responsibility.jsp?sidecar=true page
    And Verify J crew breadcrumb is not displayed
    And Verify Embedded header is displayed
    And User presses back button
    Then Click on footer link About J.Crew to open
    And Click on sublink Investor Relations from About J.Crew footer link
    And external http://investors.jcrew.com page is opened in a different tab

    #tc-08
  Scenario: Verification of Our Brands links display
    When Click on footer link Our Brands to open
    And J.Crew Factory sublink is displayed
    And Madewell sublink is displayed
    And Click on sublink J.Crew Factory from Our Brands footer link
    And Verify user is on the j.crew factory page
    And page url should contain srcCode=JCFooter
    And external https://factory.jcrew.com page is opened in a different tab
    Then Click on footer link Our Brands to open
    And Click on sublink Madewell from Our Brands footer link
    And Verify user is on the madewell page
    And page url should contain srcCode=JCFooter
    And external https://www.madewell.com/index.jsp page is opened in a different tab




  #US13389_TC14
  Scenario: Verify social sharing icons functionality
    And click on facebook icon in social sharing section
    And external facebook page is opened in a different tab
    And click on twitter icon in social sharing section
    And external twitter page is opened in a different tab
    And click on tumblr icon in social sharing section
    And external http://jcrew.tumblr.com/ page is opened in a different tab
    And click on instagram icon in social sharing section
    And external https://www.instagram.com/jcrew/ page is opened in a different tab
    And click on google icon in social sharing section
    And external https://plus.google.com/+JCrew page is opened in a different tab
    And click on pinterest icon in social sharing section
    And external https://www.pinterest.com/jcrew/ page is opened in a different tab
    And click on youtube icon in social sharing section
    And external youtube page is opened in a different tab

  
  #US13389_TC15
  Scenario: Verify legal links are displayed in footer section of all sidecar  pages
  	And user should see legal links section in the footer
  	And user should see "TERMS OF USE" in the legal links section of footer
  	And user should see "PRIVACY POLICY" in the legal links section of footer
  	And user should see "2017 J.Crew" in the legal links section of footer
    And click on "TERMS OF USE" in the legal links section of footer    
    And User is on internal /footer/termsofuse.jsp?sidecar=true page
    And Verify J crew breadcrumb is not displayed
    And Verify Embedded header is displayed
    And User presses back button
    And click on "PRIVACY POLICY" in the legal links section of footer
    And User is on internal /help/privacy_policy.jsp?sidecar=true page
    And Verify J crew breadcrumb is not displayed
    And Verify Embedded header is displayed
    And User presses back button
  	And "2017 J.Crew" should not be displayed as a link
    
  #US13389_TC16
  Scenario: Verify visit full site is displayed and functional in footer section of all sidecar  pages
   	And user should see visit full site displayed after legal links in footer section
   	And click on view full site link
   	And User is on internal /?sidecar=false page
