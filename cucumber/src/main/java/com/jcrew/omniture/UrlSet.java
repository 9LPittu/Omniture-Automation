package com.jcrew.omniture;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nadiapaolagarcia on 3/23/16.
 */

@XStreamAlias("urlset")
public class UrlSet {

    @XStreamImplicit
    private List<Url> url = new ArrayList<>();

    public UrlSet() {
    }

    public List<Url> getUrlList() {
        return url;
    }

    public void add(Url urlItem){
        url.add(urlItem);
    }
}
