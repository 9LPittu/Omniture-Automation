package com.jcrew.steps;

import cucumber.api.java.en.Then;

public class MacroSteps {

    private HeaderSteps headerSteps = new HeaderSteps();
    private FooterSteps footerSteps = new FooterSteps();
    private HamburgerMenuSteps hamburgerMenuSteps = new HamburgerMenuSteps();
    private NavigationSteps navigationSteps = new NavigationSteps();
    private StoreLocatorSteps storeLocatorSteps = new StoreLocatorSteps();
    private ShoppingBagSteps shoppingBagSteps = new ShoppingBagSteps();
    private SizeChartsPageSteps sizeChartsPageSteps = new SizeChartsPageSteps();
    private VeryPersonalStylistPageSteps veryPersonalStylistPageSteps = new VeryPersonalStylistPageSteps();
    private OrderStatusPageSteps orderStatusPageSteps = new OrderStatusPageSteps();
    private ShippingAndHandlingPageSteps shippingAndHandlingPageSteps = new ShippingAndHandlingPageSteps();
    private ReturnsAndExchangesPageSteps returnsAndExchangesPageSteps = new ReturnsAndExchangesPageSteps();
    private RequestACatalogPageSteps requestACatalogPageSteps = new RequestACatalogPageSteps();
    private AboutUsPageSteps aboutUsPageSteps = new AboutUsPageSteps();
    private CareersPageSteps careersPageSteps = new CareersPageSteps();
    private SocialResponsibilityPageSteps socialResponsibilityPageSteps = new SocialResponsibilityPageSteps();
    private HelpStoreLocatorPageSteps helpStoreLocatorPageSteps = new HelpStoreLocatorPageSteps();
    private JcrewCreditCardPageSteps jcrewCreditCardPageSteps = new JcrewCreditCardPageSteps();
    private JcrewGiftCardPageSteps jcrewGiftCardPageSteps = new JcrewGiftCardPageSteps();
    private JCrewFactoryPageSteps jCrewFactoryPageSteps = new JCrewFactoryPageSteps();
    private MadewellPageSteps madewellPageSteps = new MadewellPageSteps();


    @Then("^Verify embedded header and footer are visible and functional$")
    public void verify_embedded_header_and_footer_are_visible_and_functional() throws Throwable {
        headerSteps.verify_header_link_is_displayed("MENU");
        headerSteps.verify_header_link_is_displayed("SEARCH");
        headerSteps.verify_header_link_is_displayed("SEARCH");
        headerSteps.verify_header_link_is_displayed("BAG");
        headerSteps.verify_order_is_valid("MENU, SEARCH, STORES, BAG", "SIGN IN, MY ACCOUNT");

        footerSteps.verify_footer_link_is_displayed("LET US HELP YOU");
        footerSteps.verify_footer_link_is_displayed("YOUR ORDERS");
        footerSteps.verify_footer_link_is_displayed("THE J.CREW STYLE GUIDE");
        footerSteps.verify_footer_link_is_displayed("OUR STORES");
        footerSteps.verify_footer_link_is_displayed("OUR CARDS");
        footerSteps.verify_footer_link_is_displayed("OUR BRANDS");
        footerSteps.verify_footer_links_order_is_valid(
                "LET US HELP YOU, YOUR ORDERS, THE J.CREW STYLE GUIDE, ABOUT J.CREW, OUR STORES, OUR CARDS, OUR BRANDS",
                "GET TO KNOW US");

        hamburgerMenuSteps.user_clicks_on_hamburger_menu();
        hamburgerMenuSteps.hamburger_menu_category_link_is_present("WOMEN");
        hamburgerMenuSteps.closes_hamburger_menu();

        headerSteps.presses_search_button();
        headerSteps.search_drawer_is_open();

        headerSteps.user_clicks_on_stores_link();
        storeLocatorSteps.verify_user_is_on_stores_page();

        navigationSteps.user_presses_back_button();
        headerSteps.user_clicks_on_item_bag();
        shoppingBagSteps.user_should_be_in_shopping_bag_page();

        navigationSteps.user_presses_back_button();
        footerSteps.click_on_footer_link("LET US HELP YOU");
        footerSteps.verify_footer_sub_text_is_displayed("800 562 0258", "LET US HELP YOU");
        footerSteps.click_on_footer_sub_link("our size charts", "LET US HELP YOU");
        sizeChartsPageSteps.verify_user_is_on_size_charts_page();

        navigationSteps.user_presses_back_button();
        footerSteps.click_on_footer_link("LET US HELP YOU");
        footerSteps.click_on_footer_sub_link("very personal stylist", "LET US HELP YOU");
        veryPersonalStylistPageSteps.verify_user_is_on_very_personal_stylist_page();

        navigationSteps.user_presses_back_button();
        footerSteps.click_on_footer_link("YOUR ORDERS");
        footerSteps.click_on_footer_sub_link("order status", "YOUR ORDERS");
        orderStatusPageSteps.verify_user_is_on_order_status_page();

        navigationSteps.user_presses_back_button();
        footerSteps.click_on_footer_link("YOUR ORDERS");
        footerSteps.click_on_footer_sub_link("shipping & handling", "YOUR ORDERS");
        shippingAndHandlingPageSteps.verify_user_is_on_shipping_handling_page();

        navigationSteps.user_presses_back_button();
        footerSteps.click_on_footer_link("YOUR ORDERS");
        footerSteps.click_on_footer_sub_link("returns & exchanges", "YOUR ORDERS");
        returnsAndExchangesPageSteps.verify_user_is_on_returns_exchanges_page();

        navigationSteps.user_presses_back_button();
        footerSteps.click_on_footer_link("THE J.CREW STYLE GUIDE");
        footerSteps.click_on_footer_sub_link("request one", "THE J.CREW STYLE GUIDE");
        requestACatalogPageSteps.verify_user_is_on_request_a_catalog_page();

        navigationSteps.user_presses_back_button();
        footerSteps.click_on_footer_link("ABOUT J.CREW");
        footerSteps.click_on_footer_sub_link("our story", "ABOUT J.CREW");
        aboutUsPageSteps.verify_user_is_on_about_us_page();

        navigationSteps.user_presses_back_button();
        footerSteps.click_on_footer_link("ABOUT J.CREW");
        footerSteps.click_on_footer_sub_link("careers", "ABOUT J.CREW");
        careersPageSteps.verify_user_is_on_careers_page();

        navigationSteps.user_presses_back_button();
        footerSteps.click_on_footer_link("ABOUT J.CREW");
        footerSteps.click_on_footer_sub_link("social responsibility", "ABOUT J.CREW");
        socialResponsibilityPageSteps.verify_user_is_on_social_responsibility_page();

        navigationSteps.user_presses_back_button();
        footerSteps.click_on_footer_link("OUR STORES");
        footerSteps.click_on_footer_sub_link("store locator", "OUR STORES");
        helpStoreLocatorPageSteps.verify_user_is_on_help_store_locator_page();

        navigationSteps.user_presses_back_button();
        footerSteps.click_on_footer_link("OUR CARDS");
        footerSteps.click_on_footer_sub_link("the j.crew credit card", "OUR CARDS");
        jcrewCreditCardPageSteps.verify_user_is_on_the_j_crew_credit_card_page();

        navigationSteps.user_presses_back_button();
        footerSteps.click_on_footer_link("OUR CARDS");
        footerSteps.click_on_footer_sub_link("the j.crew gift card", "OUR CARDS");
        jcrewGiftCardPageSteps.verify_user_is_on_the_j_crew_gift_card_page();

        navigationSteps.user_presses_back_button();
        footerSteps.click_on_footer_link("OUR BRANDS");
        footerSteps.click_on_footer_sub_link("j.crew factory", "OUR BRANDS");
        jCrewFactoryPageSteps.verify_user_is_on_the_j_crew_factory_page();

        navigationSteps.user_presses_back_button();
        footerSteps.click_on_footer_link("OUR BRANDS");
        footerSteps.click_on_footer_sub_link("madewell", "OUR BRANDS");
        madewellPageSteps.verify_user_is_on_the_madewell_page();

    }
}
