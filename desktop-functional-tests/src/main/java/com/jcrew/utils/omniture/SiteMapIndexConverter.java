package com.jcrew.utils.omniture;

import com.jcrew.utils.omniture.SiteMap;
import com.jcrew.utils.omniture.SitemapIndex;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

/**
 * Created by ngarcia on 12/5/16.
 */
public class SiteMapIndexConverter implements Converter {
    @Override
    public void marshal(Object o, HierarchicalStreamWriter hierarchicalStreamWriter, MarshallingContext marshallingContext) {

    }

    @Override
    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext unmarshallingContext) {
        SitemapIndex index = new SitemapIndex();

        while (reader.hasMoreChildren()) {
            reader.moveDown();

            SiteMap map = new SiteMap();
            if (reader.hasMoreChildren()) {
                reader.moveDown();
                map.setLoc(reader.getValue());
                System.out.println(reader.getValue());
                reader.moveUp();
            }

            index.add(map);
        }

        return index;
    }

    @Override
    public boolean canConvert(Class aClass) {
        return aClass.equals(SitemapIndex.class);
    }
}
