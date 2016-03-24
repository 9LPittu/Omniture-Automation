package com.jcrew.omniture;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nadiapaolagarcia on 3/22/16.
 */

@XStreamAlias("sitemapindex")
public class SitemapIndexXML {

    @XStreamImplicit
    private List<SiteMap> sitemap = new ArrayList<>();

    public SitemapIndexXML() {
    }

    public List<SiteMap> getSitemap() {
        return sitemap;
    }

    public void add(SiteMap map){
        sitemap.add(map);
    }
}

