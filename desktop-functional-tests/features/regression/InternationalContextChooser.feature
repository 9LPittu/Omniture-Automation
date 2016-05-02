@International

 Feature: International Country Context

   Background:
     Given User goes to homepage
     And User closes email capture

   Scenario: Context chooser flag should be displayed and functional in the footer section.
     Then User should see Ship To section in footer
     And verify country name is displayed in the ship to section of footer
     And verify change link is displayed in the ship to section of footer
     Then click on change link from footer
     And User is on context chooser page
     And User is on internal /r/context-chooser page
     And UNITED STATES & CANADA region is displayed
     And ASIA PACIFIC region is displayed
     And EUROPE region is displayed
     And LATIN AMERICA & THE CARIBBEAN region is displayed
     And MIDDLE EAST & AFRICA region is displayed
     And Verify countries displayed correctly under each region
       |UNITED STATES & CANADA|
       |ASIA PACIFIC|
       |EUROPE|
       |LATIN AMERICA & THE CARIBBEAN|
       |MIDDLE EAST & AFRICA|

     And click on "terms of use" link from terms section on the context chooser page
     And User is on internal /footer/termsofuse.jsp?sidecar=true page
     And click on "privacy policy" link from terms section on the context chooser page
     And User is on internal /help/privacy_policy.jsp?sidecar=true page
     And click on "SEE ALL FAQ & HELP" button from FAQ section on the context chooser page
     And User is on internal /help/international_orders.jsp?sidecar=true page
     And click on "borderfree.com" link from FAQ section on the context chooser page
     And external http://www.pitneybowes.com/us/borderfree-is-now-part-of-pitney-bowes.html page is opened in a different tab