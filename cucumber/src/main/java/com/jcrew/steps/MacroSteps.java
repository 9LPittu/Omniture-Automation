package com.jcrew.steps;

import cucumber.api.java.en.Then;

public class MacroSteps {

    private final HeaderSteps headerSteps = new HeaderSteps();
    private final FooterSteps footerSteps = new FooterSteps();
    private final HamburgerMenuSteps hamburgerMenuSteps = new HamburgerMenuSteps();
    private final ShoppingBagSteps shoppingBagSteps = new ShoppingBagSteps();

    @Then("^Verify embedded headers are visible and functional$")
    public void verify_embedded_header_and_footer_are_visible_and_functional() throws Throwable {
        headerSteps.verify_header_link_is_displayed("MENU");
        headerSteps.verify_header_link_is_displayed("SEARCH");
        headerSteps.verify_header_link_is_displayed("STORES");
        headerSteps.verify_header_bag_icon_is_displayed();
        hamburgerMenuSteps.user_clicks_on_hamburger_menu();
        hamburgerMenuSteps.hamburger_menu_category_link_is_present("Women");
        hamburgerMenuSteps.closes_hamburger_menu();
        headerSteps.verify_order_including_bag_is_valid("MENU, SEARCH, STORES", "SIGN IN, MY ACCOUNT");

        headerSteps.presses_search_button();
        headerSteps.search_drawer_is_open();
        headerSteps.user_clicks_on_item_bag();
        shoppingBagSteps.user_should_be_in_shopping_bag_page();
    }
    
    @Then("^Verify embedded footer is visible and functional$")
    public void verify_embedded_footer_visible_and_functional() throws Throwable{
    	
    	footerSteps.click_con_header_from_footer("Contact Us");
    	
    	footerSteps.verify_icon_and_text_is_displayed("twitter");
    	footerSteps.verify_icon_and_text_is_displayed("phone");
    	footerSteps.verify_icon_and_text_is_displayed("vps");
    	
    	footerSteps.verify_footer_link_is_displayed("Let Us Help You");
    	footerSteps.verify_footer_link_is_displayed("Our Cards");
    	footerSteps.verify_footer_link_is_displayed("Our Stores");    	
    	footerSteps.verify_footer_link_is_displayed("Our Brands");
    	footerSteps.verify_footer_link_is_displayed("About J.Crew");
    	
    	footerSteps.verify_footer_header_is_displayed("LIKE BEING FIRST?");
    	footerSteps.verify_email_field_is_displayed();
    	
    	footerSteps.verify_footer_link_is_displayed("Get To Know Us");
    	
    	footerSteps.verify_social_icons_are_displayed("facebook");
    	footerSteps.verify_social_icons_are_displayed("twitter");
    	footerSteps.verify_social_icons_are_displayed("tumblr");
    	footerSteps.verify_social_icons_are_displayed("pinterest");
    	footerSteps.verify_social_icons_are_displayed("instagram");
    	footerSteps.verify_social_icons_are_displayed("google");
    	footerSteps.verify_social_icons_are_displayed("youtube");    	
    }
    
}
