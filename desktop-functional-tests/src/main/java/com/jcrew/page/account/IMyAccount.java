package com.jcrew.page.account;

/**
 * Created by ngarcia on 3/2/17.
 */
public interface IMyAccount {

    void clickInMenuOption(String menuOption);
    boolean isMyAccountMainPage();
    boolean isOrderHistoryPage();
    boolean shouldRewardDisplayed(String category);
    boolean isMenuLinkPresent(String link);
    void click_reward_link(String link);
    void click_menu_link(String link);
    boolean isInMenuLinkPage(String page);

}
