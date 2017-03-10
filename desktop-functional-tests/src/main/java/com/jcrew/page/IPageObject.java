package com.jcrew.page;

import com.jcrew.pojo.Country;
import com.jcrew.utils.StateHolder;

/**
 * Created by ngarcia on 3/10/17.
 */
public interface IPageObject {

    StateHolder stateHolder = StateHolder.getInstance();
    Country country = stateHolder.get("context");
    boolean verifyURL();
}
