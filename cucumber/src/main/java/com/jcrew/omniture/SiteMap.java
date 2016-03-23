package com.jcrew.omniture;

public class SiteMap {

    private String loc;

    public SiteMap() {
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
