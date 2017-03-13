package com.jcrew.page.account;

import com.jcrew.pojo.User;

/**
 * Created by ngarcia on 3/2/17.
 */
public interface IAccountDetail {
    boolean isAccountDetailPage();
    boolean isBirthField(String isEnabled);
    String getBirthdayCopy();
    void updateDetails(String filedName, String updateType);
    User getUserDetails();
    String getErrorMessage(String field);
    void selectDate(String dateType, String value);
    void saveUpdates();
    void clickLeftNavLinks(String link);
    void click_reward_link(String link);
    String getConfirmatonMsg();
    void clickChangePassword();
    void fillChangePasswordFileds();
    boolean shouldRewardDisplayed(String category);
    boolean isMenuLinkPresent(String link);
}
