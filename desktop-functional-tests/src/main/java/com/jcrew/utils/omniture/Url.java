package com.jcrew.utils.omniture;

/**
 * Created by ngarcia on 12/5/16.
 */
public class Url {

    private String loc;

    public Url() {
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    @Override
    public String toString() {
        return loc;
    }
}
