package com.jcrew.utils.omniture;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ngarcia on 12/5/16.
 */
@XStreamAlias("sitemapindex")
public class SitemapIndex {

    @XStreamImplicit
    private List<SiteMap> sitemap = new ArrayList<>();

    public SitemapIndex() {
    }

    public List<SiteMap> getSitemap() {
        return sitemap;
    }

    public void add(SiteMap map){
        sitemap.add(map);
    }
}
