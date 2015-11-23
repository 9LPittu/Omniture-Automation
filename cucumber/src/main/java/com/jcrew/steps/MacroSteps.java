package com.jcrew.steps;

import cucumber.api.java.en.Then;

public class MacroSteps {

    private final HeaderSteps headerSteps = new HeaderSteps();
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
}
