Feature: Jcrew Welcome Mat Functionality

  @WelocmeMatDemo
  Scenario Outline: 
    This is to validte Welcome Mat information for jcrew specifice to the given country

    Given User opens browser window
    When User input the "<countryName>" specific URL
    Then valiate welcome mat window is avilable
    And verify "Header Text" in "<countryName>" welcome Mat
    And verify "Shipping Details" in "<countryName>" welcome Mat
    And verify "Local Currency Details" in "<countryName>" welcome Mat
    And verify "Contact Details" in "<countryName>" welcome Mat
    And verify "START SHOPPING" button is avilable in welocme Mat
    And verify "Take me to the U.S. site" button is avilable in welocme Mat
    And verify "Disclaimer" in "<countryName>" welcome Mat

    Examples: 
      | countryName |
      | Canada      |

  @WelcomMatAllCountries
  Scenario Outline: 
    This is to validte Welcome Mat information for jcrew specifice to the given country

    Given User opens browser window
    When User input the "<countryName>" specific URL
    Then valiate welcome mat window is avilable
    And verify "Header Text" in "<countryName>" welcome Mat
    And verify "Shipping Details" in "<countryName>" welcome Mat
    And verify "Local Currency Details" in "<countryName>" welcome Mat
    And verify "Contact Details" in "<countryName>" welcome Mat
    And verify "Disclaimer" in "<countryName>" welcome Mat
    And verify "START SHOPPING" button is avilable in welocme Mat
    And verify "Take me to the U.S. site" button is avilable in welocme Mat

    Examples: 
      | countryName              |
      | Canada                   |
      | Australia                |
      | Bangladesh               |
      | Brunei                   |
      | Cambodia                 |
      | China                    |
      | Hong Kong                |
      | India                    |
      | Indonesia                |
      | Japan                    |
      | Macao                    |
      | Maldives                 |
      | New Zealand              |
      | Pakistan                 |
      | Philippines              |
      | Singapore                |
      | South Korea              |
      | Sri Lanka                |
      | Taiwan                   |
      | Thailand                 |
      | Austria                  |
      | Belgium                  |
      | Bulgaria                 |
      | Croatia                  |
      | Cyprus                   |
      | Czech Republic           |
      | Denmark                  |
      | Estonia                  |
      | Finland                  |
      | France                   |
      | Germany                  |
      | Gibraltar                |
      | Greece                   |
      | Guernsey                 |
      | Hungary                  |
      | Iceland                  |
      | Ireland                  |
      | Italy                    |
      | Jersey                   |
      | Latvia                   |
      | Liechtenstein            |
      | Lithuania                |
      | Luxembourg               |
      | Malta                    |
      | Monaco                   |
      | Netherlands              |
      | Norway                   |
      | Poland                   |
      | Portugal                 |
      | Romania                  |
      | Russia                   |
      | Slovakia                 |
      | Slovenia                 |
      | Spain                    |
      | Sweden                   |
      | Switzerland              |
      | United Kingdom           |
      | Antigua and Barbuda      |
      | Aruba                    |
      | Barbados                 |
      | Belize                   |
      | Bermuda                  |
      | Bolivia                  |
      | Brazil                   |
      | Cayman Islands           |
      | Chile                    |
      | Colombia                 |
      | Costa Rica               |
      | Dominica                 |
      | Dominican Republic       |
      | Ecuador                  |
      | El Salvador              |
      | French Guiana            |
      | Grenada                  |
      | Guadeloupe               |
      | Guatemala                |
      | Honduras                 |
      | Jamaica                  |
      | Martinique               |
      | Mexico                   |
      | Montserrat               |
      | Nicaragua                |
      | Panama                   |
      | Paraguay                 |
      | Peru                     |
      | Saint Kitts And Nevis    |
      | Saint Lucia              |
      | Trinidad and Tobago      |
      | Turks And Caicos Islands |
      | Bahrain                  |
      | Egypt                    |
      | Israel                   |
      | Jordan                   |
      | Kuwait                   |
      | Oman                     |
      | Qatar                    |
      | Reunion                  |
      | Saudi Arabia             |
      | Turkey                   |
      | United Arab Emirates     |

  #@WriteReportToExcel
  Scenario: 
    Given temp
      | Canada                   |
      | Australia                |
      | Bangladesh               |
      | Brunei                   |
      | Cambodia                 |
      | China                    |
      | Hong Kong                |
      | India                    |
      | Indonesia                |
      | Japan                    |
      | Macao                    |
      | Maldives                 |
      | New Zealand              |
      | Pakistan                 |
      | Philippines              |
      | Singapore                |
      | South Korea              |
      | Sri Lanka                |
      | Taiwan                   |
      | Thailand                 |
      | Austria                  |
      | Belgium                  |
      | Bulgaria                 |
      | Croatia                  |
      | Cyprus                   |
      | Czech Republic           |
      | Denmark                  |
      | Estonia                  |
      | Finland                  |
      | France                   |
      | Germany                  |
      | Gibraltar                |
      | Greece                   |
      | Guernsey                 |
      | Hungary                  |
      | Iceland                  |
      | Ireland                  |
      | Italy                    |
      | Jersey                   |
      | Latvia                   |
      | Liechtenstein            |
      | Lithuania                |
      | Luxembourg               |
      | Malta                    |
      | Monaco                   |
      | Netherlands              |
      | Norway                   |
      | Poland                   |
      | Portugal                 |
      | Romania                  |
      | Russia                   |
      | Slovakia                 |
      | Slovenia                 |
      | Spain                    |
      | Sweden                   |
      | Switzerland              |
      | United Kingdom           |
      | Antigua and Barbuda      |
      | Aruba                    |
      | Barbados                 |
      | Belize                   |
      | Bermuda                  |
      | Bolivia                  |
      | Brazil                   |
      | Cayman Islands           |
      | Chile                    |
      | Colombia                 |
      | Costa Rica               |
      | Dominica                 |
      | Dominican Republic       |
      | Ecuador                  |
      | El Salvador              |
      | French Guiana            |
      | Grenada                  |
      | Guadeloupe               |
      | Guatemala                |
      | Honduras                 |
      | Jamaica                  |
      | Martinique               |
      | Mexico                   |
      | Montserrat               |
      | Nicaragua                |
      | Panama                   |
      | Paraguay                 |
      | Peru                     |
      | Saint Kitts And Nevis    |
      | Saint Lucia              |
      | Trinidad and Tobago      |
      | Turks And Caicos Islands |
      | Bahrain                  |
      | Egypt                    |
      | Israel                   |
      | Jordan                   |
      | Kuwait                   |
      | Oman                     |
      | Qatar                    |
      | Reunion                  |
      | Saudi Arabia             |
      | Turkey                   |
      | United Arab Emirates     |
